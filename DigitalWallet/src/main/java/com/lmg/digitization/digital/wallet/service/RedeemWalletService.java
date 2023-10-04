package com.lmg.digitization.digital.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
public class RedeemWalletService {

	@Autowired
	private Utility utility;

	@Autowired
	private DigitizationUtil digitizationUtil;

	@Autowired
	private DigitalWalletRepository digitalwalletRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private DigitizationLedgerRepository ledgerRepo;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private DWRetryResponseService retryResponseService;

	public static final Logger LOGGER = ESAPI.getLogger(RedeemWalletService.class);

	@Transactional
	public RedeemWalletResponse redeemDigitalWallet(String shukranId, @Valid RedeemDigitalWalletRequest request) {

		LOGGER.info(Logger.EVENT_SUCCESS,"Redeem Wallet FLow Initiated for shukran id ({})"+ JsonSanitizer.sanitize(shukranId));
		digitizationUtil.validateSource(request.getSource());

		DWWalletModel wallet = digitalwalletRepository.findByShukranIdAndBaseCurrency(shukranId, request.getCurrency())
				.orElseThrow(() -> new DigitizationException(
						String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, request.getCurrency()),
						DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE));

		digitizationUtil.validateWallet(wallet);
		if (!request.getSource().equals("HYBRIS")) {
			digitizationUtil.validateVersion(wallet.getVersion(), request.getVersion());
		}
		digitizationUtil.validateCurrency(request.getCurrency(), wallet);

		Optional<RedeemWalletResponse> isReqAlreadyRedeemed = retryResponseService.isReqAlreadyRedeemed(shukranId, request);
		if (isReqAlreadyRedeemed.isPresent()) {
			return isReqAlreadyRedeemed.get();
		}

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
			LOGGER.info(Logger.EVENT_SUCCESS,"OTP Validations Status for shukran id ({}) is {}"+ JsonSanitizer.sanitize(shukranId)+" "+ isValidated);
		}
		List<String> status = new ArrayList<>();
		status.add(Status.ISSUED.toString());
		status.add(Status.EXTENDED.toString());
		List<DigitalWalletTransactionModel> activeReferences = transactionRepository
				.findAllByWalletIDAndStatusInOrderByExpirationDateAsc(wallet.getWalletId(), status);

		double sum = activeReferences.stream().mapToDouble(x -> x.getBalanceAmount().doubleValue()).sum();
		LOGGER.info(Logger.EVENT_SUCCESS,"Balance Amount in Wallet for shukran id ({}) is {}"+ JsonSanitizer.sanitize(shukranId)+" "+ sum);
		this.validateBalance(request, sum);

		DigitalWalletTransactionModel reissueReference = null;
		List<DigitalWalletTransactionModel> redeemList = new ArrayList<>();
		LOGGER.info(Logger.EVENT_SUCCESS,"Redeem Wallet Initiated for shukran id ({})"+JsonSanitizer.sanitize(shukranId));
		reissueReference = this.redeemAndReIssueWallet(request, wallet, activeReferences, reissueReference, redeemList);

		LOGGER.info(Logger.EVENT_SUCCESS,"Notifications Initiated for shukran id ({})"+ JsonSanitizer.sanitize(shukranId));
		notificationService.sendNotificationRedeem(wallet, request, NotificationType.REDEEM_WALLET.toString());

		return this.getRedemptionResponse(wallet, reissueReference, redeemList);
	}

	private DigitalWalletTransactionModel redeemAndReIssueWallet(RedeemDigitalWalletRequest request,
			DWWalletModel wallet, List<DigitalWalletTransactionModel> activeReferences,
			DigitalWalletTransactionModel reissueReference, List<DigitalWalletTransactionModel> redeemList) {
		BigDecimal redeemValue = request.getRedeemAmount();
		for (DigitalWalletTransactionModel reference : activeReferences) {
			if (redeemValue.compareTo(reference.getBalanceAmount()) >= 0) {
				redeemList.add(reference);
				redeemValue = redeemValue.subtract(reference.getBalanceAmount());
			} else {
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
		digitalwalletRepository.save(this.updateWalletRedemption(request.getRedeemAmount(), wallet));
		reissueReference = this.updateReIssuanceReference(request, reissueReference, redeemValue);
		this.insertLedger(wallet, reissueReference, redeemList, request);
		return reissueReference;
	}

	private RedeemWalletResponse getRedemptionResponse(DWWalletModel wallet,
			DigitalWalletTransactionModel reissueReference, List<DigitalWalletTransactionModel> redeemList) {
		RedeemWalletResponse response = new RedeemWalletResponse();
		response.setBalance(wallet.getBalanceAmount());
		response.setWalletId(wallet.getWalletId());
		response.setReferencesRedeemed(redeemList.stream().map(this::mapWalletReference)
				.sorted((o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()))
				.collect(Collectors.toList()));
		response.setReferenceReIssued(this.mapWalletReference(reissueReference));
		return response;
	}

	private void updateRedemptionReferences(RedeemDigitalWalletRequest request,
			DigitalWalletTransactionModel reissueReference, List<DigitalWalletTransactionModel> redeemList,
			BigDecimal redeemValue) {
		if (!redeemList.isEmpty()) {
			transactionRepository.saveAll(this.getActiveReferences(request, redeemList,
					redeemValue.compareTo(BigDecimal.ZERO) > 0 ? reissueReference : null, redeemValue));
		}
	}

	private DigitalWalletTransactionModel updateReIssuanceReference(RedeemDigitalWalletRequest request,
			DigitalWalletTransactionModel reissueReference, BigDecimal redeemValue) {
		if (reissueReference != null && redeemValue.compareTo(BigDecimal.ZERO) > 0) {
			reissueReference = this.reissueReference(request, redeemValue, reissueReference);
		}
		return reissueReference;
	}

	private DigitalWalletTransactionModel reissueReference(RedeemDigitalWalletRequest request,
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
		return transactionRepository.save(issuedReference);
	}

	private List<DigitalWalletTransactionModel> getActiveReferences(RedeemDigitalWalletRequest request,
			List<DigitalWalletTransactionModel> activeReferences,
			DigitalWalletTransactionModel partialRedeemedReference, BigDecimal balance) {
		activeReferences.forEach(x -> {
			x.setStatus(Status.REDEEMED.toString());
			x.setRedeemedStore(request.getStoreId());
			x.setRedeemedConcept(request.getConcept());
			x.setRedeemedSource(Source.valueOf(request.getSource()));
			x.setRedeemedUser(request.getOperatorId());
			x.setRedemptionDate(request.getBusinessDate());
			x.setRedeemInvoice(request.getInvoiceNumber());
			if (Objects.nonNull(partialRedeemedReference)
					&& partialRedeemedReference.getWalletReferenceId().equals(x.getWalletReferenceId())) {
				x.setRedeemedAmount(x.getBalanceAmount().subtract(balance));
				x.setBalanceAmount(balance);
			} else {
				x.setRedeemedAmount(x.getBalanceAmount());
				x.setBalanceAmount(BigDecimal.ZERO);
			}

		});
		return activeReferences;
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

	private DigitizationLedger getLedgerReferences(DWWalletModel wallet, DigitalWalletTransactionModel reference,
			String status, RedeemDigitalWalletRequest request) {
		DigitizationLedger ledger = new DigitizationLedger();
		ledger.setReferenceNumber(reference.getWalletReferenceId());
		ledger.setShukranId(reference.getShukranId());
		ledger.setCreatedDate(LocalDateTime.now());
		ledger.setWalletId(wallet.getWalletId());
		ledger.setExpiryDate(reference.getExpirationDate());
		ledger.setModifiedDate(LocalDateTime.now());
		ledger.setOpeningBalance(wallet.getBalanceAmount().add(request.getRedeemAmount()));
		ledger.setClosingBalance(wallet.getBalanceAmount());
		ledger.setAmount(request.getRedeemAmount());
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

	private WalletReferences mapWalletReference(DigitalWalletTransactionModel x) {
		if (x != null) {
			WalletReferences reference = new WalletReferences();
			reference.setBalanceAmount(x.getIssuedAmount());
			reference.setExpirationDate(x.getExpirationDate());
			reference.setWalletRefenceId(x.getWalletReferenceId());
			return reference;
		}
		return null;
	}

	private void insertLedger(DWWalletModel wallet, DigitalWalletTransactionModel reissueReference,
			List<DigitalWalletTransactionModel> redeemList, RedeemDigitalWalletRequest request) {
		DigitizationLedger ledgerReferences = this.getLedgerReferences(wallet, redeemList.get(redeemList.size() - 1),
				Status.REDEEMED.toString(), request);
		ledgerReferences.setReferenceNumber(reissueReference != null ? reissueReference.getWalletReferenceId()
				: ledgerReferences.getReferenceNumber());
		ledgerRepo.save(ledgerReferences);
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
