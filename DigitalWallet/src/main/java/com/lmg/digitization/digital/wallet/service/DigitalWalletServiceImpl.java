package com.lmg.digitization.digital.wallet.service;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.json.JsonSanitizer;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmg.digitization.cashback.entity.CashbackLedger;
import com.lmg.digitization.cashback.repository.CashbackDetailsRepository;
import com.lmg.digitization.cashback.repository.CashbackLedgerRepository;
import com.lmg.digitization.cashback.response.CashbackLedgerResponse;
import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.DWCreditPool;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletTransactionModel;
import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;
import com.lmg.digitization.digital.wallet.enums.NotificationType;
import com.lmg.digitization.digital.wallet.enums.Status;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.notify.NotificationService;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.digital.wallet.repository.CreditPoolRepository;
import com.lmg.digitization.digital.wallet.repository.DigitalWalletRepository;
import com.lmg.digitization.digital.wallet.repository.DigitizationLedgerRepository;
import com.lmg.digitization.digital.wallet.repository.TransactionRepository;
import com.lmg.digitization.digital.wallet.request.AccountStatementRequest;
import com.lmg.digitization.digital.wallet.request.ConvertCardRequest;
import com.lmg.digitization.digital.wallet.request.CreateDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.DeleteWalletRequest;
import com.lmg.digitization.digital.wallet.request.IssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.WalletBalanceRequest;
import com.lmg.digitization.digital.wallet.response.CreateDigitalWalletResponse;
import com.lmg.digitization.digital.wallet.response.DeleteWalletResponse;
import com.lmg.digitization.digital.wallet.response.DigitalAccountStatementResponse;
import com.lmg.digitization.digital.wallet.response.DigitizationLedgerResponse;
import com.lmg.digitization.digital.wallet.response.IssueDigitalWalletResponse;
import com.lmg.digitization.digital.wallet.response.TransactionResponse;
import com.lmg.digitization.digital.wallet.response.WalletBalanceResponse;
import com.lmg.digitization.digital.wallet.response.WalletReferences;
import com.lmg.digitization.digital.wallet.util.DigitizationUtil;
import com.lmg.digitization.digital.wallet.util.Utility;
import com.lmg.digitization.digital.wallet.util.ValidationUtils;

@Service
@Transactional
public class DigitalWalletServiceImpl implements DigitalWalletService {

	public static final Logger LOGGER = ESAPI.getLogger(DigitalWalletServiceImpl.class);

	@Autowired
	private DigitalWalletRepository digitalwalletRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private DigitizationLedgerRepository digitizationLedgerRepository;
	
	@Autowired
	private CashbackLedgerRepository cashbackLedgerRepository;

	@Autowired
	private CreditPoolRepository creditPoolRepo;

	@Autowired
	private Utility utility;

	@Autowired
	private GiftCardServiceClient cardService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private ValidationUtils validationUtils;

	@Autowired
	private DigitizationUtil digitizationUtil;
	
	@Autowired
	private CashbackDetailsRepository lmgCashbackDetailsRepository;

	@Override
	public CreateDigitalWalletResponse createDigitalWallet(CreateDigitalWalletRequest req) {
		LOGGER.info(Logger.EVENT_SUCCESS,"Create Digital Wallet service started for Shukran Id: {}"+ JsonSanitizer.sanitize(req.getShukranId()));
		if (!validationUtils.isValidConceptCode(req.getConceptCode())) {
			throw new DigitizationException("Invalid_Concept", req.getConceptCode());
		}
		CreateDigitalWalletResponse response = new CreateDigitalWalletResponse();
		IssueDigitalWalletRequest createreq = null;
		DWWalletModel model = null;

		Optional<DWWalletModel> modelOptional = digitalwalletRepository.findByShukranIdAndBaseCurrency(req.getShukranId(),
				req.getBaseCurrency());
		if (modelOptional.isPresent()) {
			throw new DigitizationException(String.format("Wallet is already exist against shukran id (%s) and currency (%s)",
					req.getShukranId(), req.getBaseCurrency()), "ERR_WALLET_ALREADY_EXIST");
		}
		try {
			model = new DWWalletModel();
			String walletid = utility.generateWalletId(req);
			BeanUtils.copyProperties(req, model);
			model.setEmailId(req.getEmailId());
			model.setMobileNumber(req.getMobileNumber());
			model.setBaseCurrency(req.getBaseCurrency());
			model.setSourceChannel(req.getSourceChannel());
			model.setWalletId(walletid);
			model.setBalanceAmount(req.getAmount());
			model.setCreateDate(LocalDateTime.now());
			LOGGER.info(Logger.EVENT_SUCCESS,"Timezone test: {}"+ LocalDateTime.now());
			model.setModifiedDate(LocalDateTime.now());
			model.setOpeningAmount(new BigDecimal("0.0"));
			model.setRedemptionAmount(new BigDecimal("0.0"));
			model.setIssuedAmount(req.getAmount());
			model.setExpiredAmount(new BigDecimal("0.0"));
			model.setWalletStatus(DigitalWalletConstants.ACTIVE_WALLET_STATUS);
			digitalwalletRepository.save(model);
			BeanUtils.copyProperties(req, response);
			response.setMessage(DigitalWalletConstants.WALLET_CREATED);
			response.setSourceChannel(req.getSourceChannel().toString());
			response.setWalletId(walletid);
			response.setCreateDate(model.getCreateDate());
			response.setStatus(DigitalWalletConstants.SUCCESS);
			response.setBalanceAmount(req.getAmount());
			response.setWalletStatus(DigitalWalletConstants.ACTIVE_WALLET_STATUS);
			createreq = new IssueDigitalWalletRequest();

			BeanUtils.copyProperties(req, createreq);
			IssueDigitalWalletResponse issueres = new IssueDigitalWalletResponse();
			BeanUtils.copyProperties(response, issueres);
			if (req.getAmount().compareTo(new BigDecimal("0.0")) > 0) {
				String walletReferenceId = (UUID.randomUUID().toString()).replace("-", "").substring(0, 25);
				this.saveCreditPool(req.getAmount(), req.getBaseCurrency(), true);
				response.setTransaction(this.saveTransaction(createreq, issueres, walletReferenceId));
				this.saveLedger(createreq, issueres, model, walletReferenceId);
				this.saveCreditPool(req.getAmount(), req.getBaseCurrency(), false);

			}
			LOGGER.info(Logger.EVENT_SUCCESS,"Create Digital Wallet service completed for Shukran Id: {}"+ JsonSanitizer.sanitize(req.getShukranId()));

		} catch (Exception ex) {
			LOGGER.error(Logger.EVENT_FAILURE,DigitalWalletConstants.FAILED_TO_FETCH_RECORD_MSSG, ex);
			throw new DigitizationException(DigitalWalletConstants.FAILED_TO_CREATE_DIGITAL_WALLET,
					DigitalWalletConstants.FAILED_TO_CREATE_DIGITAL_WALLET_CODE);

		}

		LOGGER.info(Logger.EVENT_SUCCESS,"Create Wallet service Thread name: {}"+ Thread.currentThread().getName());
		notificationService.sendNotificationCreateIssue(model, createreq, NotificationType.CREATE_WALLET.toString());

		return response;
	}

	@Override
	public IssueDigitalWalletResponse issueDigitalWallet(IssueDigitalWalletRequest req) {
		LOGGER.info(Logger.EVENT_SUCCESS,"Issue Digital Wallet service started for Shukran Id {}"+ JsonSanitizer.sanitize(req.getShukranId()));
//		if (!validationUtils.isValidConceptCode(req.getConceptCode())) {
//			throw new DigitizationException("Invalid_Concept", req.getConceptCode());
//		}

		IssueDigitalWalletResponse response = new IssueDigitalWalletResponse();
		DWWalletModel model = null;
		Optional<DWWalletModel> modelopt = digitalwalletRepository.findByShukranIdAndBaseCurrency(req.getShukranId(),
				req.getBaseCurrency());
		if (modelopt.isPresent()) {
			model = modelopt.get();
		}
		if (Objects.isNull(req.getInvoiceNumber())) {
			req.setInvoiceNumber(req.getTransactionId());
		}
		try {
			response.setStatus(DigitalWalletConstants.SUCCESS);
			response.setMessage(DigitalWalletConstants.WALLET_ISSUED);
			if (null != model) {
				digitizationUtil.validateCurrency(req.getBaseCurrency(), model);
				model.setModifiedDate(LocalDateTime.now());
				model.setOpeningAmount(model.getBalanceAmount());
				model.setBalanceAmount(model.getBalanceAmount().add(req.getAmount()));
				model.setIssuedAmount(model.getIssuedAmount().add(req.getAmount()));
				digitalwalletRepository.save(model);
				BeanUtils.copyProperties(model, response);
				response.setSourceChannel(req.getSourceChannel().toString());

			} else {
				model = new DWWalletModel();
				CreateDigitalWalletRequest createreq = new CreateDigitalWalletRequest();
				BeanUtils.copyProperties(req, createreq);
				BeanUtils.copyProperties(req, model);
				String walletid = utility.generateWalletId(createreq);
				model.setEmailId(req.getEmailId());
				model.setMobileNumber(req.getMobileNumber());
				model.setBaseCurrency(req.getBaseCurrency());
				model.setIssuedAmount(req.getAmount());
				model.setExpiredAmount(new BigDecimal("0.0"));
				model.setShukranId(req.getShukranId());
				model.setWalletId(walletid);
				model.setBalanceAmount(req.getAmount());
				model.setCreateDate(LocalDateTime.now());
				model.setModifiedDate(LocalDateTime.now());
				model.setOpeningAmount(BigDecimal.ZERO);
				model.setRedemptionAmount(BigDecimal.ZERO);
				model.setWalletStatus(DigitalWalletConstants.ACTIVE_WALLET_STATUS);
				digitalwalletRepository.save(model);
				BeanUtils.copyProperties(req, response);
				response.setSourceChannel(req.getSourceChannel().toString());
				response.setCreateDate(model.getCreateDate());
				response.setShukranId(req.getShukranId());
				response.setMessage(DigitalWalletConstants.WALLET_CREATED);
				response.setWalletId(walletid);
				response.setStatus(DigitalWalletConstants.SUCCESS);
				response.setBalanceAmount(req.getAmount());
				response.setWalletStatus(DigitalWalletConstants.ACTIVE_WALLET_STATUS);

			}
			String walletReferenceId = (UUID.randomUUID().toString()).replace("-", "").substring(0, 25);
			response.setTransaction(this.saveTransaction(req, response, walletReferenceId));
			this.saveLedger(req, response, model, walletReferenceId);
			LOGGER.info(Logger.EVENT_SUCCESS,"Digital Wallet issued completed for Shukran Id {}"+ JsonSanitizer.sanitize(req.getShukranId()));
		} catch (Exception ex) {
			LOGGER.error(Logger.EVENT_FAILURE,"Failed Issue Digital-wallet shukran-id: {}"+JsonSanitizer.sanitize(req.getShukranId()), ex);
			throw new DigitizationException(DigitalWalletConstants.FAILED_TO_ISSUE_DIGITAL_WALLET,
					DigitalWalletConstants.FAILED_TO_ISSUE_DIGITAL_WALLET_CODE);

		}
		notificationService.sendNotificationCreateIssue(model, req, NotificationType.ISSUE_WALLET.toString());
		return response;
	}

	private List<TransactionResponse> saveTransaction(IssueDigitalWalletRequest req,
			IssueDigitalWalletResponse response, String walletReferenceId) {
		ArrayList<TransactionResponse> transaction = new ArrayList<>();
		TransactionResponse dto = new TransactionResponse();
		DigitalWalletTransactionModel transactionModel = new DigitalWalletTransactionModel();
		BeanUtils.copyProperties(req, transactionModel);
		transactionModel.setWalletID(response.getWalletId());
		transactionModel.setCreateDate(LocalDateTime.now());
		transactionModel.setExpirationDate(LocalDate.now().plusDays(appProperties.getTransactionExpiryDays()));
		transactionModel.setIssuedAmount(req.getAmount());
		transactionModel.setWalletReferenceId(walletReferenceId);
		transactionModel.setModifiedDate(LocalDateTime.now());
		transactionModel.setBalanceAmount(req.getAmount());
		transactionModel.setTransactionDate(req.getTransactionDate());
		transactionModel.setStatus(Status.ISSUED.toString());
		transactionModel.setIsExpiryExtended(false);
		transactionModel.setTransactionId(req.getTransactionId());
		transactionModel.setIssuedInvoice(req.getInvoiceNumber());
		transactionRepository.save(transactionModel);
		BeanUtils.copyProperties(transactionModel, dto);
		transaction.add(dto);
		return transaction;

	}

	@Override
	public WalletBalanceResponse getWalletBalance(String shukranId, WalletBalanceRequest request) {
		
		Integer isWalletExist=digitalwalletRepository.isWalletExist(shukranId,request.getBaseCurrency());
		if (isWalletExist==null) {
			throw new DigitizationException(
					String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, request.getBaseCurrency()),
					DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE);
		}
		
		DWWalletModel model = digitalwalletRepository.findByShukranIdAndBaseCurrency(shukranId, request.getBaseCurrency())
				.orElseThrow(() -> new DigitizationException(
						String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, request.getBaseCurrency()),
						DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE));
		
		/*
		 * DWWalletModel model = digitalwalletRepository.findByShukranId(shukranId)
		 * .orElseThrow(() -> new DigitizationException(
		 * String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId,
		 * request.getBaseCurrency()),
		 * DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE));
		 */
		
		WalletBalanceResponse response = new WalletBalanceResponse();
		response.setBalance(model.getBalanceAmount());
		response.setVersion(model.getVersion());
		response.setWalletId(model.getWalletId());
		response.setCurrency(model.getBaseCurrency());
		LocalDate startDate = LocalDate.parse(request.getBusinessDate(), DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(request.getBusinessDate(), DateTimeFormatter.ISO_DATE)
				.plusDays(request.getNoOfDays());
		List<String> status = new ArrayList<>();
		status.add(Status.ISSUED.toString());
		status.add(Status.EXTENDED.toString());
		List<DigitalWalletTransactionModel> expiredWalletReferences = transactionRepository
				.findAllByWalletIDAndStatusInAndExpirationDateBetweenOrderByExpirationDateAsc(model.getWalletId(), status, startDate,
						endDate);

		response.setReferences(expiredWalletReferences.stream().map(this::mapWalletReference)
				.sorted((o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()))
				.collect(Collectors.toList()));
		return response;
	}

	@Override
	public DeleteWalletResponse deleteDigitalWallet(DeleteWalletRequest request, String shukranId) {

		LOGGER.info(Logger.EVENT_SUCCESS,"Delete Wallet service started for Shukran Id:{} "+ JsonSanitizer.sanitize(shukranId));
		DeleteWalletResponse response = new DeleteWalletResponse();
		DWWalletModel model = digitalwalletRepository.findByShukranIdAndBaseCurrency(shukranId, request.getBaseCurrency()).orElseThrow(
				() -> new DigitizationException(
						String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, request.getBaseCurrency()),
						"ERR_INVALID_SHUKRAN"));

		if (null != model) {
			if (model.getWalletStatus().equals(DigitalWalletConstants.ACTIVE_WALLET_CODE)) {
				model.setWalletStatus(DigitalWalletConstants.INACTIVE_WALLET_CODE);
				model.setModifiedDate(LocalDateTime.now());

				try {
					digitalwalletRepository.save(model);
					response.setRemarks(request.getRemarks());
					response.setShukranId(shukranId);
					response.setStatus(DigitalWalletConstants.DELETED);
					response.setDeleteDate(LocalDateTime.now());
					response.setEmailId(model.getEmailId());
					response.setMobileNo(model.getMobileNumber());
					response.setWalletStatus(DigitalWalletConstants.WALLET_DELETED_SUCCESSFULLY);
					response.setWalletId(model.getWalletId());
				} catch (Exception e) {
					LOGGER.error(Logger.EVENT_FAILURE,"Record update failed: ", e);
					throw new DigitizationException(DigitalWalletConstants.FAILED_TO_UPDATE_RECORD_MSSG,
							DigitalWalletConstants.FAILED_TO_UPDATE_RECORD_CODE);
				}
			} else {
				if (model.getWalletStatus().equals(DigitalWalletConstants.INACTIVE_WALLET_CODE)) {
					throw new DigitizationException(DigitalWalletConstants.WALLET_ALREADY_DELETED_MSSG,
							DigitalWalletConstants.WALLET_ALREADY_DELETED_CODE);
				} else if (model.getWalletStatus().equals(DigitalWalletConstants.SUSPENDED_WALLET_CODE)) {
					throw new DigitizationException(DigitalWalletConstants.WALLET_ALREADY_SUSPENDED_MSSG,
							DigitalWalletConstants.WALLET_ALREADY_SUSPENDED_CODE);
				}

			}
		}

		notificationService.sendNotification(model, NotificationType.DELETE_WALLET.toString());

		LOGGER.info(Logger.EVENT_SUCCESS,"Delete Wallet service ended for Shukran Id: {}"+ JsonSanitizer.sanitize(shukranId));

		return response;
	}

	public void saveLedger(IssueDigitalWalletRequest req, IssueDigitalWalletResponse response, DWWalletModel model,
			String walletReferenceId) {
		DigitizationLedger ledger = new DigitizationLedger();
		BeanUtils.copyProperties(req, ledger);
		ledger.setSource(req.getSourceChannel());
		ledger.setShukranId(response.getShukranId());
		ledger.setTransactionId(req.getTransactionId());
		ledger.setOrderNumber(req.getInvoiceNumber());
		if (null != req.getStoreCode()) {
			ledger.setStore(req.getStoreCode());
		} else {
			ledger.setStore(req.getCostCentre());
		}
		ledger.setBusinessDate(req.getTransactionDate());
		ledger.setConcept(req.getConceptCode());
		ledger.setCurrency(req.getBaseCurrency());
		ledger.setCreatedDate(LocalDateTime.now());
		ledger.setWalletId(response.getWalletId());
		ledger.setExpiryDate(LocalDate.now().plusDays(appProperties.getTransactionExpiryDays()));
		ledger.setModifiedDate(LocalDateTime.now());
		ledger.setOpeningBalance(new BigDecimal("0.0"));
		ledger.setClosingBalance(req.getAmount());
		ledger.setStatus(Status.ISSUED.toString());
		ledger.setReferenceNumber(walletReferenceId);
		ledger.setSourceReference(req.getSourceReference());
		ledger.setTranVersion(model.getVersion().toString());
		digitizationLedgerRepository.save(ledger);

	}

	private void saveCreditPool(BigDecimal amout, String currency, boolean credit) {
		DWCreditPool pool = new DWCreditPool();
		pool.setModifiedDate(LocalDateTime.now());
		if (credit) {
			pool.setCreditPoolAmount(amout);
		} else {
			pool.setCreditPoolAmount(amout.negate());
			pool.setExpiredPoolAmount(amout);
		}
		pool.setBaseCurrency(currency);
		creditPoolRepo.save(pool);
	}

	private WalletReferences mapWalletReference(DigitalWalletTransactionModel x) {
		if (x != null) {
			WalletReferences reference = new WalletReferences();
			reference.setBalanceAmount(x.getBalanceAmount());
			reference.setExpirationDate(x.getExpirationDate());
			reference.setWalletRefenceId(x.getWalletReferenceId());
			return reference;
		}
		return null;
	}

	@Override
	public IssueDigitalWalletResponse convertCardsDigitalWallet(String shukranId, ConvertCardRequest req)
			throws URISyntaxException {
		req.setAmount(cardService.getBalance(req));

		IssueDigitalWalletRequest request = new IssueDigitalWalletRequest();
		BeanUtils.copyProperties(req, request);
		request.setRemarks(req.getCardVendor().concat("-").concat(req.getCardNumber()));
		return this.issueDigitalWallet(request);

	}

	@Override
	public WalletBalanceResponse getWalletBalanceV2(String shukranId, WalletBalanceRequest request) {
		WalletBalanceResponse response = this.getWalletBalance(shukranId, request);

		BigDecimal find = lmgCashbackDetailsRepository.selectTotals(shukranId, request.getBaseCurrency());
		response.setBalance(response.getBalance().add(find));
		return response;
	}
	
	@Override
	public DigitalAccountStatementResponse getDigitalAccountStatement(String shukranId, AccountStatementRequest request) {

		LOGGER.info(Logger.EVENT_SUCCESS,"Get digital account statement service started for Shukran Id:{} "+ JsonSanitizer.sanitize(shukranId));

		DigitalAccountStatementResponse response = new DigitalAccountStatementResponse();

		response.setShukranId(shukranId);

		LocalDate startDate = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(request.getStartDate(), DateTimeFormatter.ISO_DATE)
				.plusDays(request.getNoOfDays());

		LocalDateTime startDateInLDT = startDate.atTime(0, 0, 0, 0);
		LocalDateTime endDateInDateLDT = endDate.atTime(23, 59, 59, 999);

		List<DigitizationLedger> digitizationLedger =	digitizationLedgerRepository.findAllByShukranIdAndCreatedDateBetweenOrderByCreatedDateDesc(shukranId, startDateInLDT, endDateInDateLDT);
		List<CashbackLedger> cashbackLedgers = cashbackLedgerRepository.findAllByShukranIdAndCreatedDateBetweenOrderByCreatedDateDesc(shukranId, startDateInLDT, endDateInDateLDT);

		response.setStatementOfDigitalWallet(digitizationLedger.stream().map(this::mapDigitizationLedgerResponse)
				.sorted((o1, o2) -> o1.getCreatedDate().compareTo(o2.getCreatedDate()))
				.collect(Collectors.toList()));

		response.setStatementOfCashbackAccount(cashbackLedgers.stream().map(this::mapCashbackLedgerResponse)
				.sorted((o1, o2) ->o1.getCreatedDate().compareTo(o2.getCreatedDate()))
				.collect(Collectors.toList()));

		LOGGER.info(Logger.EVENT_SUCCESS,"Get digital accountstatement service ended for Shukran Id:{} "+ JsonSanitizer.sanitize(shukranId));
		return response;
	}

	private DigitizationLedgerResponse mapDigitizationLedgerResponse(DigitizationLedger digitizationLedger) {
		if (digitizationLedger != null) {
			DigitizationLedgerResponse digitizationLedgerResponse = new DigitizationLedgerResponse();
			digitizationLedgerResponse.setLedgerId(digitizationLedger.getLedgerId());
			digitizationLedgerResponse.setWalletId(digitizationLedger.getWalletId());
			digitizationLedgerResponse.setReferenceNumber(digitizationLedger.getReferenceNumber());
			digitizationLedgerResponse.setStore(digitizationLedger.getStore());
			digitizationLedgerResponse.setBusinessDate(digitizationLedger.getBusinessDate());
			digitizationLedgerResponse.setOrderNumber(digitizationLedger.getOrderNumber());
			digitizationLedgerResponse.setSource(digitizationLedger.getSource());
			digitizationLedgerResponse.setTransactionId(digitizationLedger.getTransactionId());
			digitizationLedgerResponse.setTranVersion(digitizationLedger.getTranVersion());
			digitizationLedgerResponse.setAmount(digitizationLedger.getAmount());
			digitizationLedgerResponse.setShukranId(digitizationLedger.getShukranId());
			digitizationLedgerResponse.setExpiryDate(digitizationLedger.getExpiryDate());
			digitizationLedgerResponse.setStatus(digitizationLedger.getStatus());
			digitizationLedgerResponse.setCurrency(digitizationLedger.getCurrency());
			digitizationLedgerResponse.setConcept(digitizationLedger.getConcept());
			digitizationLedgerResponse.setOpeningBalance(digitizationLedger.getOpeningBalance());
			digitizationLedgerResponse.setClosingBalance(digitizationLedger.getClosingBalance());
			digitizationLedgerResponse.setSourceReference(digitizationLedger.getSourceReference());
			digitizationLedgerResponse.setCreatedDate(digitizationLedger.getCreatedDate());
			digitizationLedgerResponse.setModifiedDate(digitizationLedger.getModifiedDate());
			return digitizationLedgerResponse;
		}
		return null;
	}

	private CashbackLedgerResponse mapCashbackLedgerResponse(CashbackLedger cashbackLedger) {

		if (cashbackLedger != null) {

			CashbackLedgerResponse cashbackLedgerResponse = new CashbackLedgerResponse();

			cashbackLedgerResponse.setLedgerId(cashbackLedger.getLedgerId());
			cashbackLedgerResponse.setCashbackId(cashbackLedger.getCashbackId());
			cashbackLedgerResponse.setRefNo(cashbackLedger.getReferenceNumber());
			cashbackLedgerResponse.setStore(cashbackLedger.getStore());
			cashbackLedgerResponse.setBusinessDate(cashbackLedger.getBusinessDate());
			cashbackLedgerResponse.setOrderNumber(cashbackLedger.getOrderNumber());
			cashbackLedgerResponse.setTrnSource(cashbackLedger.getSource());
			cashbackLedgerResponse.setTransactionId(cashbackLedger.getTransactionId());
			cashbackLedgerResponse.setTranVersion(cashbackLedger.getTranVersion());
			cashbackLedgerResponse.setAmount(cashbackLedger.getAmount());
			cashbackLedgerResponse.setShukranId(cashbackLedger.getShukranId());
			cashbackLedgerResponse.setExpiryDate(cashbackLedger.getExpiryDate());
			cashbackLedgerResponse.setStatus(cashbackLedger.getStatus());
			cashbackLedgerResponse.setCurrency(cashbackLedger.getCurrency());
			cashbackLedgerResponse.setConcept(cashbackLedger.getConcept());
			cashbackLedgerResponse.setRedeemableConcept(cashbackLedger.getRedeemableConcept());
			cashbackLedgerResponse.setOpeningBalance(cashbackLedger.getOpeningBalance());
			cashbackLedgerResponse.setClosingBalance(cashbackLedger.getClosingBalance());
			cashbackLedgerResponse.setSourceReference(cashbackLedger.getSourceReference());
			cashbackLedgerResponse.setCreatedDate(cashbackLedger.getCreatedDate());
			cashbackLedgerResponse.setModifiedDate(cashbackLedger.getModifiedDate());

			return cashbackLedgerResponse;
		}
		return null;
	}

}
