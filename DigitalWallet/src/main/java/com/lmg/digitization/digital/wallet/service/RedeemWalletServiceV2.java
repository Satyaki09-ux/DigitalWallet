package com.lmg.digitization.digital.wallet.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.google.json.JsonSanitizer;
import org.apache.commons.lang3.StringUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmg.digitization.cashback.constants.PromocashConstants;
import com.lmg.digitization.cashback.entity.CashbackDetails;
import com.lmg.digitization.cashback.entity.CashbackEntry;
import com.lmg.digitization.cashback.entity.CashbackLedger;
import com.lmg.digitization.cashback.repository.CashbackDetailsRepository;
import com.lmg.digitization.cashback.repository.CashbackEntryRepository;
import com.lmg.digitization.cashback.repository.CashbackLedgerRepository;
import com.lmg.digitization.cashback.request.CashbackNotificationDetailsModel;
import com.lmg.digitization.cashback.request.CashbackNotificationRequest;
import com.lmg.digitization.cashback.response.CashbackReferencesResponse;
import com.lmg.digitization.cashback.response.RedeemCashbackResponse;
import com.lmg.digitization.cashback.response.RedeemWalletCashbackResponse;
import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletCashbackTransactionModel;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletTransactionModel;
import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;
import com.lmg.digitization.digital.wallet.enums.IssuedType;
import com.lmg.digitization.digital.wallet.enums.NotificationType;
import com.lmg.digitization.digital.wallet.enums.Source;
import com.lmg.digitization.digital.wallet.enums.Status;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.notify.NotificationService;
import com.lmg.digitization.digital.wallet.repository.DigitalWalletRepository;
import com.lmg.digitization.digital.wallet.repository.DigitizationLedgerRepository;
import com.lmg.digitization.digital.wallet.repository.TransactionRepository;
import com.lmg.digitization.digital.wallet.request.RedeemDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.response.RedeemWalletResponse;
import com.lmg.digitization.digital.wallet.response.WalletReferences;
import com.lmg.digitization.digital.wallet.util.DigitizationUtil;
import com.lmg.digitization.digital.wallet.util.Utility;

@Service
public class RedeemWalletServiceV2 {

	@Autowired
	private Utility utility;

	@Autowired
	private DigitizationUtil digitizationUtil;

	@Autowired
	private DigitalWalletRepository digitalwalletRepository;

	@Autowired
	private CashbackDetailsRepository lmgCashbackDetailsRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CashbackEntryRepository cashbackTransactionRepository;

	@Autowired
	private DigitizationLedgerRepository ledgerRepo;

	@Autowired
	private CashbackLedgerRepository cashbackLedgerRepo;

	@Autowired
	private NotificationService notificationService;

	public static final Logger LOGGER = ESAPI.getLogger(RedeemWalletServiceV2.class);

	@Transactional
	public RedeemWalletCashbackResponse redeemWalletCashback(String shukranId,
			@Valid RedeemDigitalWalletRequest request) {
		
		LOGGER.info(Logger.EVENT_SUCCESS,"Redeem Wallet and Cashback FLow Initiated for shukran id ({})"+  JsonSanitizer.sanitize(shukranId));
		digitizationUtil.validateSource(request.getSource());

		Optional<DWWalletModel> optinalWallet = digitalwalletRepository.findByShukranIdAndBaseCurrency(shukranId,
				request.getCurrency());
		Optional<CashbackDetails> optionalCashbackDetails = lmgCashbackDetailsRepository
				.findByShukranIdAndBaseCurrency(shukranId, request.getCurrency());
		DWWalletModel wallet = null;
		CashbackDetails cashbackDetails = null;
		BigDecimal walletBalance = BigDecimal.ZERO;
		BigDecimal cashbackBalance = BigDecimal.ZERO;
		
		CashbackNotificationRequest cashbackNotificationRequest = new CashbackNotificationRequest();
		CashbackNotificationDetailsModel cashbackNotificationDetailsModel = new CashbackNotificationDetailsModel();
		cashbackNotificationRequest.setNotificationType(NotificationType.REDEEM_CASHBACK.toString());
		if (optinalWallet.isPresent()) {
			wallet = optinalWallet.get();
			walletBalance = wallet.getBalanceAmount();
		}
		if (optionalCashbackDetails.isPresent()) {
			cashbackDetails = optionalCashbackDetails.get();
			cashbackBalance = cashbackDetails.getBalanceAmount();
			cashbackNotificationRequest.setCustomerMobile(cashbackDetails.getCustomerMobile());
			cashbackNotificationRequest.setCustomerEmail(cashbackDetails.getCustomerEmail());
			cashbackNotificationDetailsModel.setCustomerName(cashbackDetails.getCustomerName());
		}
		if (wallet == null && cashbackDetails == null)
			throw new DigitizationException(
					String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, request.getCurrency()),
					DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE);
		if ((cashbackDetails != null && !cashbackDetails.getCashbackStatus().equalsIgnoreCase(PromocashConstants.ACTIVE_CASHBACK_STATUS))
				|| (wallet != null && !wallet.getWalletStatus().equalsIgnoreCase(DigitalWalletConstants.ACTIVE_WALLET_STATUS)))
			throw new DigitizationException("WALLET/CASHBACK DELETED", PromocashConstants.WALLET_ALREADY_DELETED_CODE);
		if (!request.getSource().equals("HYBRIS")) {
			if (wallet != null)
				digitizationUtil.validateVersion(wallet.getVersion(), request.getVersion());
			if (cashbackDetails != null)
				digitizationUtil.validateCashbackVersion(Long.valueOf(cashbackDetails.getVersion()),
						request.getVersion());
		}
		if (wallet != null)
			digitizationUtil.validateCurrency(request.getCurrency(), wallet);
		if (cashbackDetails != null)
			digitizationUtil.validateCashbackCurrency(request.getCurrency(), cashbackDetails);
		LOGGER.info(Logger.EVENT_SUCCESS,"OTP Validations Initiated for shukran id ({})"+ JsonSanitizer.sanitize(shukranId));
		boolean isValidated = false;
		if (request.getSource().equals("HYBRIS") && request.getOtpValue().equals("NR")) {
			LOGGER.info(Logger.EVENT_SUCCESS,"OTP Validation not required as source is hybris and otp is NR for shukran id ({}) is {}"+
					JsonSanitizer.sanitize(shukranId)+" "+ isValidated);
		} else {
			if (StringUtils.isBlank(request.getTransactionId())) {
				request.setTransactionId(request.getInvoiceNumber());
			}
			isValidated = utility.validateOTP(shukranId, request.getOtpType(), request.getOtpValue(),
					request.getTransactionId());
			LOGGER.info(Logger.EVENT_SUCCESS,"OTP Validations Status for shukran id ({}) is {}"+JsonSanitizer.sanitize(shukranId)+" "+ isValidated);
		}
		List<String> status = new ArrayList<>();
		status.add(Status.ISSUED.toString());
		status.add(Status.EXTENDED.toString());

		@SuppressWarnings("rawtypes")
		List<Map> allActiveReferences = transactionRepository.getTransactionReferences(shukranId, shukranId,
				request.getConcept());
		List<DigitalWalletCashbackTransactionModel> activeReferences = new ArrayList<>();
		for (@SuppressWarnings("rawtypes")
		Map activeReference : allActiveReferences) {
			DigitalWalletCashbackTransactionModel model = new DigitalWalletCashbackTransactionModel();
			model.setBalanceAmount((BigDecimal) activeReference.get("balance_amount"));
			model.setIssuedAmount((BigDecimal) activeReference.get("Issued_Amount"));
			model.setShukranId((String) activeReference.get("SHUKRAN_ID"));
			Timestamp createDate = ((Timestamp) activeReference.get("CREATED_DATE"));
			model.setCreatedDate(createDate.toLocalDateTime().toLocalDate());
			Timestamp expirationDate = ((Timestamp) activeReference.get("EXPIRATION_DATE"));
			model.setExpirationDate(expirationDate.toLocalDateTime().toLocalDate());
			model.setType((String) activeReference.get("TYPE"));
			model.setStatus((String) activeReference.get("STATUS"));
			model.setRedeemedConcept((String) activeReference.get("REDEEMABLE_CONCEPT_CODE"));
			model.setWalletCashbackReferenceId((String) activeReference.get("WALLET_CASHBACK_REFERENCE_ID"));
			model.setWalletCashbackId((String) activeReference.get("WALLET_CASHBACK_ID"));
			if (activeReference.get("version") == null) {
				model.setVersion(0L);
			} else {
				model.setVersion(((BigDecimal) activeReference.get("version")).longValue());
			}
			activeReferences.add(model);
		}
		double sum = activeReferences.stream().mapToDouble(x -> x.getBalanceAmount().doubleValue()).sum();
		LOGGER.info(Logger.EVENT_SUCCESS,"Balance Amount in Wallet for shukran id ({}) is {}"+ JsonSanitizer.sanitize(shukranId)+" "+ sum);
		this.validateBalance(request, sum);
        BigDecimal balenceAfterReedem = (cashbackBalance.subtract(request.getRedeemAmount()));
		DigitalWalletCashbackTransactionModel reissueReference = null;
		List<DigitalWalletCashbackTransactionModel> redeemList = new ArrayList<>();
		LOGGER.info(Logger.EVENT_SUCCESS,"Redeem Wallet Initiated for shukran id ({})"+ JsonSanitizer.sanitize(shukranId));
		reissueReference = this.redeemAndReIssueWalletCashback(request, wallet, cashbackDetails, activeReferences,
				reissueReference, redeemList, walletBalance, cashbackBalance);
		
		LOGGER.info(Logger.EVENT_SUCCESS,"Notifications Initiated for shukran id ({})"+ JsonSanitizer.sanitize(shukranId));
		notificationService.sendNotificationRedeem(wallet, request, NotificationType.REDEEM_WALLET.toString());	
		
		
		cashbackNotificationDetailsModel.setAmount(request.getRedeemAmount().doubleValue());
		cashbackNotificationDetailsModel.setCurrency(request.getCurrency());
		cashbackNotificationDetailsModel.setRedeemableConcept(request.getConcept());
		cashbackNotificationDetailsModel.setOrderReferenceNumber(request.getInvoiceNumber());
		cashbackNotificationDetailsModel.setBalanceAmount(balenceAfterReedem.doubleValue());
		cashbackNotificationRequest.setData(cashbackNotificationDetailsModel);
		notificationService.sendNotificationForCashback(cashbackNotificationRequest);
		
		return this.getRedemptionResponse(wallet, cashbackDetails, reissueReference, redeemList, request.getCurrency());
	}

	private DigitalWalletCashbackTransactionModel redeemAndReIssueWalletCashback(RedeemDigitalWalletRequest request,
			DWWalletModel wallet, CashbackDetails cashbackDetails,
			List<DigitalWalletCashbackTransactionModel> activeReferences,
			DigitalWalletCashbackTransactionModel reissueReference,
			List<DigitalWalletCashbackTransactionModel> redeemList, BigDecimal walletBalance,
			BigDecimal cashbackBalance) {
		BigDecimal redeemValue = request.getRedeemAmount();
		BigDecimal walletRedeem = BigDecimal.valueOf(0);
		BigDecimal cashbackRedeem = BigDecimal.valueOf(0);
		for (DigitalWalletCashbackTransactionModel reference : activeReferences) {
			if (redeemValue.compareTo(reference.getBalanceAmount()) >= 0) {
				if (reference.getType().equalsIgnoreCase(PromocashConstants.CASHBACK))
					cashbackRedeem = cashbackRedeem.add(reference.getBalanceAmount());
				else
					walletRedeem = walletRedeem.add(reference.getBalanceAmount());
				redeemList.add(reference);
				redeemValue = redeemValue.subtract(reference.getBalanceAmount());

			} else {
				if (reference.getType().equalsIgnoreCase(PromocashConstants.CASHBACK))
					cashbackRedeem = cashbackRedeem.add(redeemValue);
				else
					walletRedeem = walletRedeem.add(redeemValue);
				reissueReference = reference;
				redeemList.add(reference);
				redeemValue = redeemValue.subtract(reference.getBalanceAmount());

			}
			if (redeemValue.compareTo(BigDecimal.ZERO) <= 0) {
				break;
			}

		}
		redeemValue = redeemValue.negate();
		this.updateRedemptionReferences(request, reissueReference, redeemList, redeemValue);

		if (walletRedeem.compareTo(BigDecimal.ZERO) > 0)
			digitalwalletRepository.save(this.updateWalletRedemption(walletRedeem, wallet));
		if (cashbackRedeem.compareTo(BigDecimal.ZERO) > 0)
			lmgCashbackDetailsRepository.save(this.updateCashbackRedemption(cashbackRedeem, cashbackDetails));

		reissueReference = this.updateReIssuanceReference(request, reissueReference, redeemValue);
		this.insertLedger(wallet, cashbackDetails, reissueReference, redeemList, request, walletBalance,
				cashbackBalance,walletRedeem,cashbackRedeem);
		return reissueReference;
	}
//Need to do some research on this
	private void insertLedger(DWWalletModel wallet, CashbackDetails cashbackDetails,
			DigitalWalletCashbackTransactionModel reissueReference,
			List<DigitalWalletCashbackTransactionModel> redeemList, RedeemDigitalWalletRequest request,
			BigDecimal walletBalance, BigDecimal cashbackBalance, BigDecimal walletRedeem, BigDecimal cashbackRedeem) {
		List<DigitalWalletCashbackTransactionModel> redeemWalletList = new ArrayList<>();
		List<DigitalWalletCashbackTransactionModel> redeemCashbackList = new ArrayList<>();
		for (DigitalWalletCashbackTransactionModel reference : redeemList) {
			if (reference.getType().equalsIgnoreCase(PromocashConstants.DIGITAL_WALLET_TYPE))
				redeemWalletList.add(reference);
			else
				redeemCashbackList.add(reference);
		}

		if (!redeemWalletList.isEmpty()) {
			DigitizationLedger ledgerReferences = this.getLedgerReferences(wallet,
					redeemWalletList.get(redeemWalletList.size() - 1), Status.REDEEMED.toString(), request,
					walletRedeem);
			ledgerReferences
					.setReferenceNumber(reissueReference != null ? reissueReference.getWalletCashbackReferenceId()
							: ledgerReferences.getReferenceNumber());
			ledgerRepo.save(ledgerReferences);
		}
		if (!redeemCashbackList.isEmpty()) {
			CashbackLedger cashbackLedgerReferences = this.getCashbackLedgerReferences(cashbackDetails,
					redeemCashbackList.get(redeemCashbackList.size() - 1), Status.REDEEMED.toString(), request,
					cashbackRedeem);
			cashbackLedgerReferences
					.setReferenceNumber(reissueReference != null ? reissueReference.getWalletCashbackReferenceId()
							: cashbackLedgerReferences.getReferenceNumber());
			cashbackLedgerRepo.save(cashbackLedgerReferences);
		}
		
	}

	private RedeemWalletCashbackResponse getRedemptionResponse(DWWalletModel wallet, CashbackDetails cashbackDetails,
			DigitalWalletCashbackTransactionModel reissueReference,
			List<DigitalWalletCashbackTransactionModel> redeemList, String currency) {
		RedeemWalletCashbackResponse response = new RedeemWalletCashbackResponse();
		response.setTotalBalance(BigDecimal.valueOf(0));
		response.setBaseCurrency(currency);
		List<DigitalWalletCashbackTransactionModel> redeemWalletList = new ArrayList<>();
		List<DigitalWalletCashbackTransactionModel> redeemCashbackList = new ArrayList<>();
		for (DigitalWalletCashbackTransactionModel reference : redeemList) {
			if (reference.getType().equalsIgnoreCase(PromocashConstants.DIGITAL_WALLET_TYPE))
				redeemWalletList.add(reference);
			else
				redeemCashbackList.add(reference);
		}
		if (wallet != null) {
			RedeemWalletResponse walletResponse = new RedeemWalletResponse();
			response.setTotalBalance(response.getTotalBalance().add((wallet.getBalanceAmount())));
			walletResponse.setBalance(wallet.getBalanceAmount());
			walletResponse.setWalletId(wallet.getWalletId());
			walletResponse.setReferencesRedeemed(redeemWalletList.stream().map(this::mapWalletReference)
					.sorted((o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()))
					.collect(Collectors.toList()));
			if (reissueReference != null
					&& reissueReference.getType().equalsIgnoreCase(PromocashConstants.DIGITAL_WALLET_TYPE)) {
				walletResponse.setReferenceReIssued(this.mapWalletReference(reissueReference));
			}
			response.setRedeemWalletResponse(walletResponse);
		}
		if (cashbackDetails != null) {
			RedeemCashbackResponse cashbackResponse = new RedeemCashbackResponse();
			response.setTotalBalance(response.getTotalBalance().add((cashbackDetails.getBalanceAmount())));
			cashbackResponse.setBalance(cashbackDetails.getBalanceAmount());
			cashbackResponse.setCashbackId(cashbackDetails.getCashbackId());
			cashbackResponse.setReferencesRedeemed(redeemCashbackList.stream().map(this::mapCashbackReference)
					.sorted((o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()))
					.collect(Collectors.toList()));
			if (reissueReference != null && reissueReference.getType().equalsIgnoreCase("cashback")) {
				cashbackResponse.setReferenceReIssued(this.mapCashbackReference(reissueReference));
			}
			response.setRedeemCashbackResponse(cashbackResponse);
		}

		return response;
	}

	private List<DigitalWalletCashbackTransactionModel> updateRedemptionReferences(RedeemDigitalWalletRequest request,
			DigitalWalletCashbackTransactionModel reissueReference,
			List<DigitalWalletCashbackTransactionModel> redeemList, BigDecimal redeemValue) {
		for (DigitalWalletCashbackTransactionModel reference : redeemList) {

			if (reference.getType().equalsIgnoreCase(PromocashConstants.DIGITAL_WALLET_TYPE)) {
				DigitalWalletTransactionModel dwTransactionModel = transactionRepository
						.findAllByWalletReferenceId(reference.getWalletCashbackReferenceId());
				dwTransactionModel.setStatus(Status.REDEEMED.toString());
				dwTransactionModel.setRedeemedStore(request.getStoreId());
				dwTransactionModel.setRedeemedConcept(request.getConcept());
				dwTransactionModel.setRedeemedSource(Source.valueOf(request.getSource()));
				dwTransactionModel.setRedeemedUser(request.getOperatorId());
				dwTransactionModel.setRedemptionDate(request.getBusinessDate());
				dwTransactionModel.setRedeemInvoice(request.getInvoiceNumber());
				dwTransactionModel.setBalanceAmount(reference.getBalanceAmount());
				if (Objects.nonNull(reissueReference) && reissueReference.getWalletCashbackReferenceId()
						.equals(dwTransactionModel.getWalletReferenceId())) {
					dwTransactionModel.setRedeemedAmount(dwTransactionModel.getBalanceAmount().subtract(redeemValue));
					dwTransactionModel.setBalanceAmount(redeemValue);
				} else {
					dwTransactionModel.setRedeemedAmount(dwTransactionModel.getBalanceAmount());
					dwTransactionModel.setBalanceAmount(BigDecimal.ZERO);
				}
				transactionRepository.save(dwTransactionModel);

			} else {
				Optional<CashbackEntry> optionalCashbackEntry = cashbackTransactionRepository
						.findByCashbackReferenceId((reference.getWalletCashbackReferenceId()));
				if (optionalCashbackEntry.isPresent()) {
					CashbackEntry cahbackEntry = optionalCashbackEntry.get();
					cahbackEntry.setStatus(Status.REDEEMED.toString());
					cahbackEntry.setRedeemedStore(request.getStoreId());
					cahbackEntry.setRedeemedConcept(request.getConcept());
					cahbackEntry.setRedeemedSource(Source.valueOf(request.getSource()));
					cahbackEntry.setRedeemedUser(request.getOperatorId());
					cahbackEntry.setRedemptionDate(request.getBusinessDate());
					cahbackEntry.setRedeemInvoice(request.getInvoiceNumber());
					cahbackEntry.setBalanceAmount(reference.getBalanceAmount());

					if (Objects.nonNull(reissueReference) && reissueReference.getWalletCashbackReferenceId()
							.equals(cahbackEntry.getCashbackReferenceId())) {
						cahbackEntry.setRedeemedAmount(cahbackEntry.getBalanceAmount().subtract(redeemValue));
						cahbackEntry.setBalanceAmount(redeemValue);
					} else {
						cahbackEntry.setRedeemedAmount(cahbackEntry.getBalanceAmount());
						cahbackEntry.setBalanceAmount(BigDecimal.ZERO);
					}

					cashbackTransactionRepository.save(cahbackEntry);
				}
			}
		}

		return redeemList;

	}

	private DigitalWalletCashbackTransactionModel updateReIssuanceReference(RedeemDigitalWalletRequest request,
			DigitalWalletCashbackTransactionModel reissueReference, BigDecimal redeemValue) {
		DigitalWalletCashbackTransactionModel reIssue = null;
		if (reissueReference != null && redeemValue.compareTo(BigDecimal.ZERO) > 0) {
			reIssue = new DigitalWalletCashbackTransactionModel();
			if (reissueReference.getType().equalsIgnoreCase(PromocashConstants.DIGITAL_WALLET_TYPE)) {
				DigitalWalletTransactionModel model = new DigitalWalletTransactionModel();
				BeanUtils.copyProperties(reissueReference, model);
				model.setWalletID(reissueReference.getWalletCashbackId());
				model.setWalletReferenceId(reissueReference.getWalletCashbackReferenceId());
				DigitalWalletTransactionModel dw = this.reissueWalletReference(request, redeemValue, model);
				BeanUtils.copyProperties(reissueReference, reIssue);
				reIssue.setBalanceAmount(dw.getBalanceAmount());
				reIssue.setWalletCashbackReferenceId(dw.getWalletReferenceId());
				reIssue.setIssuedAmount(dw.getIssuedAmount());
				reIssue.setType(PromocashConstants.DIGITAL_WALLET_TYPE);
			} else {
				CashbackEntry reissueCashbackReference = this.reissueCashbackReference(request, redeemValue,
						reissueReference);
				BeanUtils.copyProperties(reissueReference, reIssue);
				if(reissueCashbackReference!=null) {
					reIssue.setBalanceAmount(reissueCashbackReference.getBalanceAmount());
					reIssue.setWalletCashbackReferenceId(reissueCashbackReference.getCashbackReferenceId());
					reIssue.setIssuedAmount(reissueCashbackReference.getIssuedAmount());
					reIssue.setRedeemableConcept(reissueCashbackReference.getRedeemableConcept());
					reIssue.setType(PromocashConstants.CASHBACK);
				}
			}

		}

		return reIssue;
	}

	private CashbackEntry reissueCashbackReference(RedeemDigitalWalletRequest request, BigDecimal reIssuanceValue,
			DigitalWalletCashbackTransactionModel reissueReference) {
		Optional<CashbackEntry> optionalCashbackEntry = cashbackTransactionRepository
				.findByCashbackReferenceId((reissueReference.getWalletCashbackReferenceId()));
		if (optionalCashbackEntry.isPresent()) {
			CashbackEntry issuedReference = new CashbackEntry(optionalCashbackEntry.get());
			String mode = Source.HYBRIS.toString().equalsIgnoreCase(request.getSource()) ? "1" : "2";
			String reference = utility.generateCreditNoteNumber(mode, "2", "1", request.getConcept(),
					request.getStoreId(), request.getBusinessDate());
			issuedReference.setCashbackReferenceId(reference);
			issuedReference.setTransactionDate(LocalDate.now());
			issuedReference.setTransactionDate(request.getBusinessDate());
			issuedReference.setOrderReferenceNo(request.getInvoiceNumber());
			issuedReference.setTransactionId(request.getTransactionId());
			issuedReference.setSource(request.getSource());
			issuedReference.setIssuingConcept(request.getConcept());
			issuedReference.setSourceChannel(request.getStoreId());
			issuedReference.setBalanceAmount(reIssuanceValue);
			issuedReference.setIssuedAmount(reIssuanceValue);
			issuedReference.setStatus(Status.ISSUED.toString());
			issuedReference.setSourceFunction(IssuedType.CHANGE_DUE.toString());
			issuedReference.setBaseCurrency(request.getCurrency());
			issuedReference.setCreatedDate(LocalDateTime.now());
			issuedReference.setModifiedDate(LocalDateTime.now());
			return cashbackTransactionRepository.save(issuedReference);
		}
		return null;
	}

	private DigitalWalletTransactionModel reissueWalletReference(RedeemDigitalWalletRequest request,
			BigDecimal reIssuanceValue, DigitalWalletTransactionModel reissueReference) {
		DigitalWalletTransactionModel issuedReference = new DigitalWalletTransactionModel(reissueReference);
		String mode = Source.HYBRIS.toString().equalsIgnoreCase(request.getSource()) ? "1" : "2";
		String reference = utility.generateCreditNoteNumber(mode, "2", "1", request.getConcept(), request.getStoreId(),
				request.getBusinessDate());
		issuedReference.setWalletReferenceId(reference);
		issuedReference.setTransactionDate(request.getBusinessDate());
		issuedReference.setIssuedInvoice(request.getInvoiceNumber());
		issuedReference.setTransactionId(request.getTransactionId());
		issuedReference.setSourceApp(request.getSource());
		issuedReference.setConceptCode(request.getConcept());
		issuedReference.setStoreCode(request.getStoreId());
		issuedReference.setBalanceAmount(reIssuanceValue);
		issuedReference.setIssuedAmount(reIssuanceValue);
		issuedReference.setStatus(Status.ISSUED.toString());
		issuedReference.setSourceFunction(IssuedType.CHANGE_DUE.toString());
		issuedReference.setSourceReference(reissueReference.getWalletReferenceId());
		issuedReference.setBaseCurrency(request.getCurrency());
		issuedReference.setCreateDate(LocalDateTime.now());
		issuedReference.setExpirationDate(reissueReference.getExpirationDate());
		issuedReference.setExpiryExtended(false);
		issuedReference.setIssuedInvoice(request.getInvoiceNumber());
		issuedReference.setModifiedDate(LocalDateTime.now());
		issuedReference.setShukranId(reissueReference.getShukranId());
		issuedReference.setTerritoryCode("1");
		issuedReference.setWalletID(reissueReference.getWalletID());
		issuedReference.setVersion(reissueReference.getVersion());
		return transactionRepository.save(issuedReference);
	}

	private DWWalletModel updateWalletRedemption(BigDecimal balance, DWWalletModel wallet) {
		wallet.setOpeningAmount(wallet.getBalanceAmount());
		wallet.setBalanceAmount(wallet.getBalanceAmount().subtract(balance));
		if (Objects.nonNull(wallet.getRedemptionAmount())) {
			wallet.setRedemptionAmount(wallet.getRedemptionAmount().add(balance));
		} else {
			wallet.setRedemptionAmount(balance);
		}
		wallet.setLastRedemptionDate(LocalDate.now());
		return wallet;
	}

	private CashbackDetails updateCashbackRedemption(BigDecimal balance, CashbackDetails cashback) {

		cashback.setOpeningAmount(cashback.getBalanceAmount());
		cashback.setBalanceAmount(cashback.getBalanceAmount().subtract(balance));
		if (Objects.nonNull(cashback.getRedeemAmount())) {
			cashback.setRedeemAmount(cashback.getRedeemAmount().add(balance));
		} else {
			cashback.setRedeemAmount(balance);
		}
		cashback.setLastRedeemptionDate(LocalDate.now());
		return cashback;
	}

	private WalletReferences mapWalletReference(DigitalWalletCashbackTransactionModel x) {
		if (x != null) {
			WalletReferences reference = new WalletReferences();
			reference.setBalanceAmount(x.getIssuedAmount());
			reference.setExpirationDate(x.getExpirationDate());
			reference.setWalletRefenceId(x.getWalletCashbackReferenceId());
			return reference;
		}
		return null;
	}

	private CashbackReferencesResponse mapCashbackReference(DigitalWalletCashbackTransactionModel x) {
		if (x != null) {
			CashbackReferencesResponse reference = new CashbackReferencesResponse();
			reference.setBalanceAmount(x.getIssuedAmount());
			reference.setExpirationDate(x.getExpirationDate());
			reference.setCashbackRefenceId(x.getWalletCashbackReferenceId());
			reference.setRedeemableConcept(x.getRedeemedConcept());
			return reference;
		}
		return null;
	}

	private DigitizationLedger getLedgerReferences(DWWalletModel wallet,
			DigitalWalletCashbackTransactionModel reference, String status, RedeemDigitalWalletRequest request,
			BigDecimal redeemableAmount) {
		DigitizationLedger ledger = new DigitizationLedger();
		ledger.setReferenceNumber(reference.getWalletCashbackReferenceId());
		ledger.setShukranId(reference.getShukranId());
		ledger.setCreatedDate(LocalDateTime.now());
		ledger.setWalletId(wallet.getWalletId());
		ledger.setExpiryDate(reference.getExpirationDate());
		ledger.setModifiedDate(LocalDateTime.now());
		ledger.setOpeningBalance(wallet.getBalanceAmount().add(redeemableAmount));
		ledger.setClosingBalance(wallet.getBalanceAmount());
		ledger.setAmount(redeemableAmount);
		ledger.setCurrency(request.getCurrency());
		ledger.setStatus(status);
		ledger.setBusinessDate(request.getBusinessDate());
		ledger.setConcept(StringUtils.stripToEmpty(request.getConcept()));
		ledger.setSource(Source.valueOf(request.getSource()));
		ledger.setStore(StringUtils.stripToEmpty(request.getStoreId()));
		ledger.setOrderNumber(StringUtils.stripToEmpty(request.getInvoiceNumber()));
		ledger.setTransactionId(request.getTransactionId());
		ledger.setTranVersion(wallet.getVersion().toString());
		return ledger;
	}

	private CashbackLedger getCashbackLedgerReferences(CashbackDetails cashback,
			DigitalWalletCashbackTransactionModel reference, String status, RedeemDigitalWalletRequest request,
			BigDecimal redeemableAmount) {
		CashbackLedger ledger = new CashbackLedger();
		ledger.setReferenceNumber(reference.getWalletCashbackReferenceId());
		ledger.setShukranId(reference.getShukranId());
		ledger.setCreatedDate(LocalDateTime.now());
		ledger.setCashbackId(cashback.getCashbackId());
		ledger.setExpiryDate(reference.getExpirationDate());
		ledger.setModifiedDate(LocalDateTime.now());
		ledger.setOpeningBalance(cashback.getBalanceAmount().add(redeemableAmount));
		ledger.setClosingBalance(cashback.getBalanceAmount());
		ledger.setAmount(redeemableAmount);
		ledger.setCurrency(request.getCurrency());
		ledger.setStatus(status);
		ledger.setBusinessDate(request.getBusinessDate());
		ledger.setConcept(StringUtils.stripToEmpty(request.getConcept()));
		ledger.setSource(request.getSource());
		ledger.setStore(StringUtils.stripToEmpty(request.getStoreId()));
		ledger.setOrderNumber(StringUtils.stripToEmpty(request.getInvoiceNumber()));
		ledger.setTransactionId(request.getTransactionId());
		ledger.setTranVersion(cashback.getVersion());
		ledger.setRedeemableConcept(reference.getRedeemedConcept());
		return ledger;
	}

	private void validateBalance(RedeemDigitalWalletRequest request, double sum) {
		if (request.getRedeemAmount().compareTo(BigDecimal.valueOf(sum)) > 0) {
			throw new DigitizationException(String.format(
					"Could not Redeem the Balance as the Wallet Balance (%.2f %s) is lesser than the redeem Amount (%.2f %s)",
					sum, request.getCurrency(), request.getRedeemAmount(), request.getCurrency()),
					"ERR_INVALID_BALANCE");
		}
	}

}
