package com.lmg.digitization.digital.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

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
import com.lmg.digitization.digital.wallet.request.RevertIssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.response.RevertRedeemOnInvoiceResponse;

@Service
@Transactional
public class RevertIssueWalletService {

	public static final Logger LOGGER = ESAPI.getLogger(RevertRedeemWalletService.class);

	@Autowired
	private DigitalWalletRepository digitalwalletRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private DigitizationLedgerRepository digitizationLedgerRepository;

	@Autowired
	private NotificationService notificationService;

	public RevertRedeemOnInvoiceResponse revertIssue(RevertIssueDigitalWalletRequest req, String shukranId) {

		DigitizationLedger ledger = digitizationLedgerRepository
				.findTopByShukranIdAndCurrencyAndOrderNumberOrderByCreatedDateDesc(shukranId, req.getBaseCurrency(), req.getInvoiceNumber())
				.orElseThrow(() -> new DigitizationException(ErrorCodes.REVERT_ISSUE_ORDERNUMBER_FAILURE));

		DigitalWalletTransactionModel redeemedReferences = transactionRepository
				.findTopByIssuedInvoiceOrderByCreateDateDesc(req.getInvoiceNumber());
		this.validateRevetIssueOnInvoice(ledger, req, shukranId, redeemedReferences);

		if (Status.ISSUED.toString().equalsIgnoreCase(redeemedReferences.getStatus())) {
			redeemedReferences.setStatus(Status.REVERT_ISSUED.toString());
			redeemedReferences.setModifiedDate(LocalDateTime.now());
			redeemedReferences.setBalanceAmount(BigDecimal.ZERO);
			redeemedReferences.setRemarks(req.getCancelReason());
		}
		transactionRepository.save(redeemedReferences);
		DWWalletModel wallet = this.updateWallet(shukranId, req.getBaseCurrency(), ledger);
		this.saveRevertIssueLedger(wallet, redeemedReferences, ledger.getAmount(), req);
		LOGGER.info(Logger.EVENT_SUCCESS,"Revert redeem transaction against Invoice: {} and refers: {}"+ JsonSanitizer.sanitize(req.getInvoiceNumber())+" "+
				redeemedReferences.getWalletReferenceId());
		notificationService.sendNotification(wallet, req, NotificationType.REVERT_ISSUE_WALLET.toString());
		return new RevertRedeemOnInvoiceResponse(wallet.getWalletId(), wallet.getBalanceAmount(), wallet.getBaseCurrency(),
				wallet.getShukranId(), Status.REVERT_ISSUED.toString());

	}

	private void validateRevetIssueOnInvoice(DigitizationLedger ledger, RevertIssueDigitalWalletRequest req, String shukranId,
			DigitalWalletTransactionModel redeemedReferences) {
		if (Status.REVERT_ISSUED.toString().equals(ledger.getStatus())) {
			throw new DigitizationException(ErrorCodes.REVERT_ISSUE_ALREADY_REVETED);
		}

		if (req.getIssuedAmount().compareTo(ledger.getAmount()) != 0 || !req.getBaseCurrency().equals(ledger.getCurrency())
				|| !shukranId.equals(ledger.getShukranId()) || !Status.ISSUED.toString().equals(ledger.getStatus())
				|| Objects.isNull(redeemedReferences) || !Status.ISSUED.toString().equalsIgnoreCase(redeemedReferences.getStatus())) {
			throw new DigitizationException(ErrorCodes.REVERT_ISSUE_FAILURE);
		}

	}

	private DWWalletModel updateWallet(String shukranId, String currency, DigitizationLedger ledger) {
		DWWalletModel wallet = digitalwalletRepository.findByShukranIdAndBaseCurrency(shukranId, currency).orElseThrow(
				() -> new DigitizationException(String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, currency),
						DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE));
		wallet.setOpeningAmount(wallet.getBalanceAmount());
		wallet.setBalanceAmount(wallet.getBalanceAmount().subtract(ledger.getAmount()));
		wallet.setIssuedAmount(wallet.getIssuedAmount().subtract(ledger.getAmount()));
		wallet.setModifiedDate(LocalDateTime.now());
		digitalwalletRepository.save(wallet);
		return wallet;
	}

	private void saveRevertIssueLedger(DWWalletModel model, DigitalWalletTransactionModel transaction, BigDecimal issuedAmount,
			RevertIssueDigitalWalletRequest req) {
		DigitizationLedger ledgerModel = new DigitizationLedger();
		ledgerModel.setShukranId(transaction.getShukranId());
		ledgerModel.setWalletId(transaction.getWalletID());
		ledgerModel.setReferenceNumber(transaction.getWalletReferenceId());
		ledgerModel.setStore(StringUtils.stripToEmpty(req.getStoreId()));
		ledgerModel.setBusinessDate(LocalDate.now());
		ledgerModel.setOrderNumber(StringUtils.stripToEmpty(req.getInvoiceNumber()));
		ledgerModel.setSource(req.getSource());
		ledgerModel.setExpiryDate(transaction.getExpirationDate());
		ledgerModel.setCurrency(req.getBaseCurrency());
		ledgerModel.setConcept(req.getConcept());
		ledgerModel.setCreatedDate(LocalDateTime.now());
		ledgerModel.setModifiedDate(LocalDateTime.now());
		ledgerModel.setOpeningBalance(model.getBalanceAmount().add(issuedAmount));
		ledgerModel.setAmount(issuedAmount);
		ledgerModel.setClosingBalance(model.getBalanceAmount());
		ledgerModel.setTranVersion(model.getVersion().toString());
		ledgerModel.setStatus(Status.REVERT_ISSUED.toString());
		ledgerModel.setTransactionId(req.getTransactionId());
		LOGGER.info(Logger.EVENT_SUCCESS,"Creating new ledger for revert redeem transaction: {}"+ ledgerModel);
		digitizationLedgerRepository.save(ledgerModel);
	}
}
