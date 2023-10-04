package com.lmg.digitization.digital.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.google.json.JsonSanitizer;
import org.apache.commons.lang3.StringUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class RevertRedeemWalletService {

	public static final Logger LOGGER = ESAPI.getLogger(RevertRedeemWalletService.class);

	@Autowired
	private DigitalWalletRepository digitalwalletRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private DigitizationLedgerRepository digitizationLedgerRepository;

	@Autowired
	private NotificationService notificationService;

	@Transactional
	public RevertRedeemOnInvoiceResponse revertRedeem(RevertRedeemOnInvoiceRequest req, String shukranId) {

		DigitizationLedger ledger = digitizationLedgerRepository
				.findTopByShukranIdAndCurrencyAndOrderNumberOrderByCreatedDateDesc(shukranId, req.getCurrency(), req.getInvoiceNumber())
				.orElseThrow(() -> new DigitizationException(ErrorCodes.REVERT_REDEEM_ORDERNUMBER_FAILURE));

		this.validateRevetRedeemOnInvoice(ledger, req, shukranId);
		List<DigitalWalletTransactionModel> redeemedReferences = transactionRepository.findAllByRedeemInvoice(req.getInvoiceNumber());

		List<String> walletRefIds = redeemedReferences.stream().map(DigitalWalletTransactionModel::getWalletReferenceId)
				.collect(Collectors.toList());
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
		LOGGER.info(Logger.EVENT_SUCCESS,"Revert redeem transaction against Invoice: {} and refers: {}"+ JsonSanitizer.sanitize(req.getInvoiceNumber())+" "+ walletRefIds);
		notificationService.sendNotification(wallet, req, NotificationType.REVERT_REDEEM_WALLET.toString());
		return new RevertRedeemOnInvoiceResponse(wallet.getWalletId(), wallet.getBalanceAmount(), wallet.getBaseCurrency(),
				wallet.getShukranId(), Status.REVERT_REDEEMED.toString());

	}

	private DWWalletModel updateWallet(String shukranId, String currency, DigitizationLedger ledger) {
		DWWalletModel wallet = digitalwalletRepository.findByShukranIdAndBaseCurrency(shukranId, currency)
				.orElseThrow(
						() -> new DigitizationException(String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, currency),
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

	private void validateRevetRedeemOnInvoice(DigitizationLedger ledger, RevertRedeemOnInvoiceRequest req, String shukranId) {

		if (Status.REVERT_REDEEMED.toString().equals(ledger.getStatus())) {
			throw new DigitizationException(ErrorCodes.REVERT_REDEEM_ALREADY_REVETED);
		}

		if (req.getRedeemedAmount().compareTo(ledger.getAmount()) != 0 || !req.getCurrency().equals(ledger.getCurrency())
				|| !shukranId.equals(ledger.getShukranId()) || !Status.REDEEMED.toString().equals(ledger.getStatus())) {
			throw new DigitizationException(ErrorCodes.REVERT_REDEEM_FAILURE);
		}

	}

	private void saveRevertRedeemLedger(DWWalletModel model, DigitalWalletTransactionModel transaction, BigDecimal redeemedAmount) {
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
		LOGGER.info(Logger.EVENT_SUCCESS,"Creating new ledger for revert redeem transaction: {}"+ ledgerModel);
		digitizationLedgerRepository.save(ledgerModel);
	}

}
