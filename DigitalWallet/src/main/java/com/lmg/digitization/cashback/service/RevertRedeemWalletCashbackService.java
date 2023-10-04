package com.lmg.digitization.cashback.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.google.json.JsonSanitizer;
import org.apache.commons.lang3.StringUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmg.digitization.cashback.entity.CashbackDetails;
import com.lmg.digitization.cashback.entity.CashbackEntry;
import com.lmg.digitization.cashback.entity.CashbackLedger;
import com.lmg.digitization.cashback.repository.CashbackDetailsRepository;
import com.lmg.digitization.cashback.repository.CashbackEntryRepository;
import com.lmg.digitization.cashback.repository.CashbackLedgerRepository;
import com.lmg.digitization.cashback.request.CashbackNotificationDetailsModel;
import com.lmg.digitization.cashback.request.CashbackNotificationRequest;
import com.lmg.digitization.cashback.response.RevertCashbackRedeemOnInvoiceResponse;
import com.lmg.digitization.cashback.response.RevertRedeemCashbackOnInvoiceResponse;
import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletTransactionModel;
import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;
import com.lmg.digitization.digital.wallet.enums.ErrorCodes;
import com.lmg.digitization.digital.wallet.enums.NotificationType;
import com.lmg.digitization.digital.wallet.enums.Status;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.notify.NotificationService;
import com.lmg.digitization.digital.wallet.repository.DigitalWalletRepository;
import com.lmg.digitization.digital.wallet.repository.DigitizationLedgerRepository;
import com.lmg.digitization.digital.wallet.repository.TransactionRepository;
import com.lmg.digitization.digital.wallet.request.RevertRedeemOnInvoiceRequest;
import com.lmg.digitization.digital.wallet.response.RevertRedeemOnInvoiceResponse;

@Service
@Transactional
public class RevertRedeemWalletCashbackService {

	public static final Logger LOGGER = ESAPI.getLogger(RevertRedeemWalletCashbackService.class);

	@Autowired
	private DigitalWalletRepository digitalwalletRepository;

	@Autowired
	private CashbackLedgerRepository cashbackLedgerRepository;

	@Autowired
	private CashbackDetailsRepository cashbackDetailsRepository;

	@Autowired
	private CashbackEntryRepository cashbackentryRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private DigitizationLedgerRepository digitizationLedgerRepository;

	@Autowired
	private NotificationService notificationService;

	@Transactional
	public RevertRedeemCashbackOnInvoiceResponse revertRedeem(RevertRedeemOnInvoiceRequest req, String shukranId) {
		RevertRedeemCashbackOnInvoiceResponse response = new RevertRedeemCashbackOnInvoiceResponse();
		
		Optional<DigitizationLedger> optionalLedger = digitizationLedgerRepository
				.findTopByShukranIdAndCurrencyAndOrderNumberOrderByCreatedDateDesc(shukranId, req.getCurrency(),
						req.getInvoiceNumber());
		DigitizationLedger ledger = null;
		if (optionalLedger.isPresent()) {
			ledger = optionalLedger.get();
			this.validateRevetRedeemOnInvoice(ledger, req, shukranId);
			List<DigitalWalletTransactionModel> redeemedReferences = transactionRepository
					.findAllByRedeemInvoice(req.getInvoiceNumber());
			
			List<String> walletRefIds = redeemedReferences.stream()
					.map(DigitalWalletTransactionModel::getWalletReferenceId).collect(Collectors.toList());
			List<DigitalWalletTransactionModel> updateRefs = new ArrayList<>();

			// Check if there is any re-issued reference created and make it invalid
			if (!walletRefIds.contains(ledger.getReferenceNumber())) {
				transactionRepository.findById(ledger.getReferenceNumber()).ifPresent(reIssuedRef -> {
					if (reIssuedRef.getStatus().equalsIgnoreCase(Status.ISSUED.toString())) {
						reIssuedRef.setStatus(Status.REVERT_REDEEMED.toString());
						reIssuedRef.setModifiedDate(LocalDateTime.now());
						walletRefIds.add(reIssuedRef.getWalletReferenceId());
						updateRefs.add(reIssuedRef);
					}
				});
			}
			// Process Redeemed reference and revert the redeem amount and re-issue
			redeemedReferences.forEach(ref -> {
				if (ref.getStatus().equalsIgnoreCase(Status.REDEEMED.toString())) {
					ref.setStatus(Status.ISSUED.toString());
					ref.setBalanceAmount(ref.getBalanceAmount().add(ref.getRedeemedAmount()));
					ref.setRedeemedAmount(BigDecimal.ZERO);
					ref.setModifiedDate(LocalDateTime.now());
				}
			});

			updateRefs.addAll(redeemedReferences);
			transactionRepository.saveAll(updateRefs);

			DWWalletModel wallet = this.updateWallet(shukranId, req.getCurrency(), ledger);
			this.saveRevertRedeemLedger(wallet, redeemedReferences.get(0), ledger.getAmount());
			LOGGER.info(Logger.EVENT_SUCCESS,"Revert redeem transaction against Invoice: {} and refers: {}" +JsonSanitizer.sanitize(req.getInvoiceNumber())+" "+
					walletRefIds);
			notificationService.sendNotification(wallet, req, NotificationType.REVERT_REDEEM_WALLET.toString());
			response.setWalletRevertRedeem(
					new RevertRedeemOnInvoiceResponse(wallet.getWalletId(), wallet.getBalanceAmount(),
							wallet.getBaseCurrency(), wallet.getShukranId(), Status.REVERT_REDEEMED.toString()));

		}

		Optional<CashbackLedger> optionalCashbackLedger = cashbackLedgerRepository
				.findTopByShukranIdAndCurrencyAndOrderNumberAndTransactionIdOrderByCreatedDateDesc(shukranId, req.getCurrency(),
						req.getInvoiceNumber(),req.getTransactionId());

		CashbackLedger cashbackLedger = null;
		if (optionalCashbackLedger.isPresent()) {
			cashbackLedger = optionalCashbackLedger.get();

			this.validateRevertCashbackRedeemOnInvoice(cashbackLedger, req, shukranId);
			List<CashbackEntry> redeemedCashbackReferences = cashbackentryRepository
					.findAllByRedeemInvoice(req.getInvoiceNumber());

			List<String> cashbackRefIds = redeemedCashbackReferences.stream().map(CashbackEntry::getCashbackReferenceId)
					.collect(Collectors.toList());
			List<CashbackEntry> updateCashbackRefs = new ArrayList<>();

			// Check if there is any re-issued reference created and make it invalid
			if (!cashbackRefIds.contains(cashbackLedger.getReferenceNumber())) {
				cashbackentryRepository.findByCashbackReferenceId(cashbackLedger.getReferenceNumber())
						.ifPresent(reIssuedRef -> {
							if (reIssuedRef.getStatus().equalsIgnoreCase(Status.ISSUED.toString())) {
								reIssuedRef.setStatus(Status.REVERT_REDEEMED.toString());
								reIssuedRef.setModifiedDate(LocalDateTime.now());
								cashbackRefIds.add(reIssuedRef.getCashbackReferenceId());
								updateCashbackRefs.add(reIssuedRef);
							}
						});
			}
			// Process Redeemed reference and revert the redeem amount and re-issue
			redeemedCashbackReferences.forEach(ref -> {
				
				if (ref.getStatus().equalsIgnoreCase(Status.REDEEMED.toString())) {
					ref.setStatus(Status.ISSUED.toString());
					ref.setBalanceAmount(ref.getBalanceAmount().add(ref.getRedeemedAmount()));
					ref.setRedeemedAmount(BigDecimal.ZERO);
					ref.setModifiedDate(LocalDateTime.now());
				}
			});

			updateCashbackRefs.addAll(redeemedCashbackReferences);
			cashbackentryRepository.saveAll(redeemedCashbackReferences);

			CashbackDetails cashback = this.updateCashback(shukranId, req.getCurrency(), cashbackLedger);
			this.saveRevertRedeemCashbackLedger(cashback, redeemedCashbackReferences.get(0),
					cashbackLedger.getAmount(), req.getTransactionId());
			
			LOGGER.info(Logger.EVENT_SUCCESS,"Revert redeem transaction against Invoice: {} and refers: {}"+JsonSanitizer.sanitize(req.getInvoiceNumber()) +" "+cashbackRefIds);
			response.setCashbackRevertRedeem(
					new RevertCashbackRedeemOnInvoiceResponse(cashback.getCashbackId(), cashback.getBalanceAmount(),
							cashback.getBaseCurrency(), cashback.getShukranId(), Status.REVERT_REDEEMED.toString()));
			
			CashbackNotificationRequest cashbackNotificationRequest = new CashbackNotificationRequest();
			CashbackNotificationDetailsModel cashbackNotificationDetailsModel = new CashbackNotificationDetailsModel();
			
			cashbackNotificationRequest.setNotificationType(NotificationType.REVERT_REDEEM_CASHBACK.toString());
			cashbackNotificationRequest.setCustomerEmail(cashback.getCustomerEmail());
			cashbackNotificationRequest.setCustomerMobile(cashback.getCustomerMobile());
			
			cashbackNotificationDetailsModel.setCustomerName(cashback.getCustomerName());
			cashbackNotificationDetailsModel.setAmount(req.getRedeemedAmount().doubleValue());
			cashbackNotificationDetailsModel.setCurrency(req.getCurrency());
			cashbackNotificationDetailsModel.setRedeemableConcept(req.getConcept());
			cashbackNotificationDetailsModel.setOrderReferenceNumber(req.getInvoiceNumber());
			cashbackNotificationDetailsModel.setBalanceAmount(cashback.getBalanceAmount().doubleValue());
			cashbackNotificationRequest.setData(cashbackNotificationDetailsModel);
			
			LOGGER.info(Logger.EVENT_SUCCESS,"Revert redeem cashback notification against Invoice: {} and refers: {}"+JsonSanitizer.sanitize(req.getInvoiceNumber())+" "+cashbackRefIds);
			notificationService.sendNotificationForCashback(cashbackNotificationRequest);
			
		}
		if(response.getCashbackRevertRedeem()==null && response.getWalletRevertRedeem()==null) {
			throw new DigitizationException(ErrorCodes.REVERT_ISSUE_ORDERNUMBER_TRANSACTIONID_FAILURE);
			
		}
		return response;
	}

	/* Update Redeem amount, balance amount and opening amount of the customer */
	private CashbackDetails updateCashback(String shukranId, String currency, CashbackLedger cashbackLedger) {
		CashbackDetails cashback = cashbackDetailsRepository.findByShukranIdAndBaseCurrency(shukranId, currency)
				.orElseThrow(() -> new DigitizationException(
						String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, currency),
						DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE));
		cashback.setOpeningAmount(cashback.getBalanceAmount());
		cashback.setBalanceAmount(cashback.getBalanceAmount().add(cashbackLedger.getAmount()));
		if (Objects.nonNull(cashback.getRedeemAmount())) {
			cashback.setRedeemAmount(cashback.getRedeemAmount().subtract(cashbackLedger.getAmount()));
		} else {
			cashback.setRedeemAmount(BigDecimal.ZERO);
		}
		cashback.setModifiedDate(LocalDateTime.now());
		cashbackDetailsRepository.save(cashback);
		return cashback;
	}

	/* Validates if the invoice is already redeemed */
	
	private void validateRevertCashbackRedeemOnInvoice(CashbackLedger ledger, RevertRedeemOnInvoiceRequest req,
			String shukranId) {

		if (Status.REVERT_REDEEMED.toString().equals(ledger.getStatus())) {
			throw new DigitizationException(ErrorCodes.REVERT_REDEEM_ALREADY_REVETED);
		}

		if (!req.getCurrency().equals(ledger.getCurrency()) || !shukranId.equals(ledger.getShukranId())
				|| !Status.REDEEMED.toString().equals(ledger.getStatus())) {
			throw new DigitizationException(ErrorCodes.REVERT_REDEEM_FAILURE);
		}

	}

	
	/* Revert ledger information of the cashback */
	private void saveRevertRedeemCashbackLedger(CashbackDetails cashback, CashbackEntry transaction,
			BigDecimal redeemedAmount, String transactionId) {
		CashbackLedger ledgerModel = new CashbackLedger();
		ledgerModel.setShukranId(transaction.getShukranId());
		ledgerModel.setCashbackId(transaction.getCashbackId());
		ledgerModel.setReferenceNumber(transaction.getCashbackReferenceId());
		ledgerModel.setStore(StringUtils.stripToEmpty(transaction.getRedeemedStore()));
		ledgerModel.setBusinessDate(transaction.getRedemptionDate());
		ledgerModel.setOrderNumber(StringUtils.stripToEmpty(transaction.getRedeemInvoice()));
		ledgerModel.setSource(transaction.getRedeemedSource().toString());
		ledgerModel.setExpiryDate(transaction.getExpirationDate());
		ledgerModel.setCurrency(transaction.getBaseCurrency());
		ledgerModel.setConcept(transaction.getRedeemedConcept());
		ledgerModel.setCreatedDate(LocalDateTime.now());
		ledgerModel.setModifiedDate(LocalDateTime.now());
		ledgerModel.setOpeningBalance(cashback.getBalanceAmount().subtract(redeemedAmount));
		ledgerModel.setAmount(redeemedAmount);
		ledgerModel.setClosingBalance(cashback.getBalanceAmount());
		ledgerModel.setTranVersion(cashback.getVersion());
		ledgerModel.setStatus(Status.REVERT_REDEEMED.toString());
		ledgerModel.setTransactionId(transactionId);
		ledgerModel.setRedeemableConcept(transaction.getRedeemableConcept());
		LOGGER.info(Logger.EVENT_SUCCESS,"Creating new ledger for revert redeem transaction: {}" + ledgerModel);
		cashbackLedgerRepository.save(ledgerModel);
	}
	
	/* Update Digital Wallet core table (balance, opening balance, closing balance, redeem balance */
	private DWWalletModel updateWallet(String shukranId, String currency, DigitizationLedger ledger) {
		DWWalletModel wallet = digitalwalletRepository.findByShukranIdAndBaseCurrency(shukranId, currency)
				.orElseThrow(() -> new DigitizationException(
						String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, currency),
						DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE));
		wallet.setOpeningAmount(wallet.getBalanceAmount());
		wallet.setBalanceAmount(wallet.getBalanceAmount().add(ledger.getAmount()));
		if (Objects.nonNull(wallet.getRedemptionAmount())) {
			wallet.setRedemptionAmount(wallet.getRedemptionAmount().subtract(ledger.getAmount()));
		} else {
			wallet.setRedemptionAmount(BigDecimal.ZERO);
		}
		wallet.setModifiedDate(LocalDateTime.now());
		digitalwalletRepository.save(wallet);
		return wallet;
	}

	/* Validate if the Wallet Balance is already redeemed */
	private void validateRevetRedeemOnInvoice(DigitizationLedger ledger, RevertRedeemOnInvoiceRequest req,
			String shukranId) {

		if (Status.REVERT_REDEEMED.toString().equals(ledger.getStatus())) {
			throw new DigitizationException(ErrorCodes.REVERT_REDEEM_ALREADY_REVETED);
		}

		if (!req.getCurrency().equals(ledger.getCurrency()) || !shukranId.equals(ledger.getShukranId())
				|| !Status.REDEEMED.toString().equals(ledger.getStatus())) {
			throw new DigitizationException(ErrorCodes.REVERT_REDEEM_FAILURE);
		}

	}

	/* Reverts wallet redeem ledger of the customer */
	private void saveRevertRedeemLedger(DWWalletModel model, DigitalWalletTransactionModel transaction,
			BigDecimal redeemedAmount) {
		DigitizationLedger ledgerModel = new DigitizationLedger();
		ledgerModel.setShukranId(transaction.getShukranId());
		ledgerModel.setWalletId(transaction.getWalletID());
		ledgerModel.setReferenceNumber(transaction.getWalletReferenceId());
		ledgerModel.setStore(StringUtils.stripToEmpty(transaction.getStoreCode()));
		ledgerModel.setBusinessDate(transaction.getRedemptionDate());
		ledgerModel.setOrderNumber(StringUtils.stripToEmpty(transaction.getRedeemInvoice()));
		ledgerModel.setSource(transaction.getRedeemedSource());
		ledgerModel.setExpiryDate(transaction.getExpirationDate());
		ledgerModel.setCurrency(transaction.getBaseCurrency());
		ledgerModel.setConcept(transaction.getRedeemedConcept());
		ledgerModel.setCreatedDate(LocalDateTime.now());
		ledgerModel.setModifiedDate(LocalDateTime.now());
		ledgerModel.setOpeningBalance(model.getBalanceAmount().subtract(redeemedAmount));
		ledgerModel.setAmount(redeemedAmount);
		ledgerModel.setClosingBalance(model.getBalanceAmount());
		ledgerModel.setTranVersion(model.getVersion().toString());
		ledgerModel.setStatus(Status.REVERT_REDEEMED.toString());
		LOGGER.info(Logger.EVENT_SUCCESS,"Creating new ledger for revert redeem transaction: {}" + ledgerModel);
		digitizationLedgerRepository.save(ledgerModel);
	}

}
