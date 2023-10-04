package com.lmg.digitization.digital.wallet.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.DigitalCreditNote;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletTransactionModel;
import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;
import com.lmg.digitization.digital.wallet.enums.NotificationType;
import com.lmg.digitization.digital.wallet.enums.Source;
import com.lmg.digitization.digital.wallet.enums.Status;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.notify.NotificationService;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.digital.wallet.repository.DigitalCreditNoteRepository;
import com.lmg.digitization.digital.wallet.repository.DigitalWalletRepository;
import com.lmg.digitization.digital.wallet.repository.DigitizationLedgerRepository;
import com.lmg.digitization.digital.wallet.repository.TransactionRepository;
import com.lmg.digitization.digital.wallet.request.WalletConversionRequest;
import com.lmg.digitization.digital.wallet.response.DcnToDwConvertResponse;
import com.lmg.digitization.digital.wallet.response.WalletReferences;
import com.lmg.digitization.digital.wallet.util.DigitizationUtil;
import com.lmg.digitization.digital.wallet.util.Utility;

@Service
@Transactional
public class DcnToWalletConversionService {

	@Autowired
	private DigitalWalletRepository digitalwalletRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private DigitizationLedgerRepository ledgerRepo;

	@Autowired
	private DigitalCreditNoteRepository dcnRepository;

	@Autowired
	private DigitizationUtil digitizationUtil;

	@Autowired
	private Utility utility;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private NotificationService notificationService;

	public DcnToDwConvertResponse convertDCNToDW(String shukranId, String dcnId, WalletConversionRequest request) {
		DigitalCreditNote dcn = dcnRepository.findById(dcnId)
				.orElseThrow(() -> new DigitizationException(String.format("The DCN Number %s doesnot exists", dcnId),
						"ERR_INVALID_DCN"));
		DWWalletModel wallet = digitalwalletRepository.findByShukranIdAndBaseCurrency(shukranId, dcn.getCurrency()).orElseThrow(
				() -> new DigitizationException(String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, dcn.getCurrency()),
						"ERR_INVALID_SHUKRAN"));
		this.validateConversion(dcn, wallet);
		digitizationUtil.validateSource(request.getSource());

		DigitalWalletTransactionModel transModel;
		while (true) {
			String mode = Source.HYBRIS.toString().equalsIgnoreCase(request.getSource()) ? "1" : "2";
			String reference = utility.generateCreditNoteNumber(mode, "2", "1", request.getConcept(),
					request.getStoreId(), LocalDate.parse(request.getBusinessDate(), DateTimeFormatter.ISO_DATE));
			if (!transactionRepository.existsById(reference)) {
				transModel = transactionRepository
						.save(this.createDigitalWalletReference(dcn, wallet, request, reference));
				break;
			}
		}
		digitalwalletRepository.save(this.updateWallet(dcn, wallet));
		ledgerRepo.save(this.saveLedger(shukranId, wallet, dcn, transModel.getWalletReferenceId(), request));
		dcnRepository.save(this.getConvertedDCN(shukranId, request, dcn));
		notificationService.sendNotificationConvertion(wallet, dcn,
				NotificationType.DCN_TO_DIGITAL_WALLET.toString(), request.getLanguage());
		return this.getDcnToDwResponse(wallet, transModel, dcn.getRedeemedAmount());
	}

	private DigitizationLedger saveLedger(String shukranId, DWWalletModel wallet, DigitalCreditNote dcn,
			String refNumber, WalletConversionRequest request) {
		DigitizationLedger ledger = new DigitizationLedger();
		ledger.setSource(Source.valueOf(request.getSource()));
		ledger.setReferenceNumber(refNumber);
		ledger.setShukranId(shukranId);
		ledger.setStore(request.getStoreId());
		ledger.setCreatedDate(LocalDateTime.now());
		ledger.setOrderNumber(dcn.getDcnId());
		ledger.setWalletId(wallet.getWalletId());
		ledger.setExpiryDate(LocalDate.now().plusDays(appProperties.getTransactionExpiryDays()));
		ledger.setModifiedDate(LocalDateTime.now());
		ledger.setOpeningBalance(wallet.getOpeningAmount());
		ledger.setClosingBalance(wallet.getBalanceAmount());
		ledger.setAmount(dcn.getBalanceAmount());
		ledger.setBusinessDate(LocalDate.now());
		ledger.setConcept(request.getConcept());
		ledger.setCurrency(dcn.getCurrency());
		ledger.setStatus(Status.CONVERTED.toString());
		ledger.setTranVersion(wallet.getVersion().toString());
		return ledger;
	}

	private DcnToDwConvertResponse getDcnToDwResponse(DWWalletModel wallet, DigitalWalletTransactionModel transModel,
			BigDecimal issuedAmount) {

		WalletReferences wr = new WalletReferences();
		wr.setWalletRefenceId(transModel.getWalletReferenceId());
		wr.setBalanceAmount(transModel.getBalanceAmount());
		wr.setExpirationDate(transModel.getExpirationDate());

		DcnToDwConvertResponse response = new DcnToDwConvertResponse();
		response.setBalance(wallet.getBalanceAmount());
		response.setVersion(wallet.getVersion());
		response.setWalletId(wallet.getWalletId());
		response.setIssuedAmount(issuedAmount);
		response.setCurrency(wallet.getBaseCurrency());
		response.setReferences(Collections.singletonList(wr));
		return response;
	}

	private DWWalletModel updateWallet(DigitalCreditNote dcn, DWWalletModel wallet) {
		wallet.setOpeningAmount(wallet.getBalanceAmount());
		wallet.setBalanceAmount(wallet.getBalanceAmount().add(dcn.getBalanceAmount()));
		wallet.setIssuedAmount(wallet.getIssuedAmount().add(dcn.getBalanceAmount()));
		return wallet;
	}

	private DigitalCreditNote getConvertedDCN(String shukranId, WalletConversionRequest request, DigitalCreditNote dcn) {
		dcn.setStatus(Status.CONVERTED);
		dcn.setRedeemedAmount(dcn.getBalanceAmount());
		dcn.setTransactionId(shukranId);
		dcn.setBalanceAmount(BigDecimal.ZERO);
		dcn.setRedeemedStore(request.getStoreId());
		dcn.setRedeemedConcept(request.getConcept());
		dcn.setRedeemedSource(Source.valueOf(request.getSource()));
		dcn.setRedeemedUser(request.getOperatorId());
		dcn.setRedemptionDate(LocalDate.parse(request.getBusinessDate(), DateTimeFormatter.ISO_DATE));
		return dcn;
	}

	private DigitalWalletTransactionModel createDigitalWalletReference(DigitalCreditNote dcn, DWWalletModel wallet,
			@Valid WalletConversionRequest request, String reference) {
		DigitalWalletTransactionModel references = new DigitalWalletTransactionModel();
		references.setWalletID(wallet.getWalletId());
		references.setWalletReferenceId(reference);
		references.setCreateDate(LocalDateTime.now());
		references.setExpirationDate(dcn.getExpirationDate());
		references.setIssuedAmount(dcn.getBalanceAmount());
		references.setModifiedDate(LocalDateTime.now());
		references.setBalanceAmount(dcn.getBalanceAmount());
		references.setTransactionDate(LocalDate.parse(request.getBusinessDate()));
		references.setStatus(Status.ISSUED.toString());
		references.setBaseCurrency(dcn.getCurrency());
		references.setConceptCode(dcn.getIssuedConcept().toString());
		references.setIsExpiryExtended(false);
		references.setSourceApp(request.getSource());
		references.setShukranId(wallet.getShukranId());
		references.setSourceReference(dcn.getDcnId());
		references.setSourceFunction(String.format("Conversion DCN to DW (%s)", dcn.getDcnId()));
		return references;
	}

	private void validateConversion(DigitalCreditNote dcn, DWWalletModel wallet) {
		digitizationUtil.validateWallet(wallet);
		digitizationUtil.validateStatus(dcn);
		digitizationUtil.validateCurrency(dcn.getCurrency(), wallet);
	}
}
