package com.lmg.digitization.cashback.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.json.JsonSanitizer;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.lmg.digitization.cashback.constants.PromocashConstants;
import com.lmg.digitization.cashback.entity.CashbackCreditPool;
import com.lmg.digitization.cashback.entity.CashbackDetails;
import com.lmg.digitization.cashback.entity.CashbackEntry;
import com.lmg.digitization.cashback.entity.CashbackLedger;
import com.lmg.digitization.cashback.entity.CashbackRejectionDetails;
import com.lmg.digitization.cashback.entity.CashbackSuccessfulUploadDetails;
import com.lmg.digitization.cashback.entity.CashbackUploadDetails;
import com.lmg.digitization.cashback.repository.CashbackCreditPoolRepository;
import com.lmg.digitization.cashback.repository.CashbackDetailsRepository;
import com.lmg.digitization.cashback.repository.CashbackEntryRepository;
import com.lmg.digitization.cashback.repository.CashbackLedgerRepository;
import com.lmg.digitization.cashback.repository.CashbackRejectionDetailsRepository;
import com.lmg.digitization.cashback.repository.CashbackSuccessfulFileUploadRepository;
import com.lmg.digitization.cashback.repository.CashbackUploadDetailsRepository;
import com.lmg.digitization.cashback.request.CashbackNotificationDetailsModel;
import com.lmg.digitization.cashback.request.CashbackNotificationRequest;
import com.lmg.digitization.cashback.request.FetchWalletBalanceRequest;
import com.lmg.digitization.cashback.request.FileUploadSummaryRequest;
import com.lmg.digitization.cashback.request.SaveCashbackRequest;
import com.lmg.digitization.cashback.request.SaveCashbackRequestComposite;
import com.lmg.digitization.cashback.response.CashbackBalanceConceptWiseResponse;
import com.lmg.digitization.cashback.response.CashbackBalanceResponse;
import com.lmg.digitization.cashback.response.CashbackReferencesResponse;
import com.lmg.digitization.cashback.response.CashbackRejectionDetailsResponse;
import com.lmg.digitization.cashback.response.ExpireBalanceEntriesResponse;
import com.lmg.digitization.cashback.response.FetchBalanceWithoutReferenceResponse;
import com.lmg.digitization.cashback.response.FetchMonthlyBalanceResponse;
import com.lmg.digitization.cashback.response.FetchWalletBalanceCompositeResponse;
import com.lmg.digitization.cashback.response.FileUploadResponse;
import com.lmg.digitization.cashback.response.FileUploadSummaryResponse;
import com.lmg.digitization.cashback.response.MonthlyReferencesResponse;
import com.lmg.digitization.cashback.response.ReferencesResponse;
import com.lmg.digitization.cashback.response.SaveCashbackResponse;
import com.lmg.digitization.cashback.response.SaveCashbackCompositeResponse;
import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletTransactionModel;
import com.lmg.digitization.digital.wallet.enums.ErrorCodes;
import com.lmg.digitization.digital.wallet.enums.NotificationType;
import com.lmg.digitization.digital.wallet.enums.Status;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.notify.NotificationService;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.digital.wallet.repository.DigitalWalletRepository;
import com.lmg.digitization.digital.wallet.repository.TransactionRepository;
import com.lmg.digitization.digital.wallet.request.IssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.WalletBalanceRequest;
import com.lmg.digitization.digital.wallet.response.BaseResponse;
import com.lmg.digitization.digital.wallet.response.WalletBalanceResponse;
import com.lmg.digitization.digital.wallet.service.DigitalWalletService;
import com.lmg.digitization.digital.wallet.util.Utility;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PromocashServiceImpl implements PromocashService {

	private static final Logger LOGGER = ESAPI.getLogger(PromocashServiceImpl.class);
	@Autowired
	private CashbackDetailsRepository lmgCashbackDetailsRepository;

	@Autowired
	private CashbackUploadDetailsRepository cashbackUploadRepository;

	@Autowired
	private CashbackEntryRepository lmgCashbackEntryRepository;

	@Autowired
	private CashbackLedgerRepository lmgCashbackLedgerRepository;

	@Autowired
	private CashbackCreditPoolRepository lmgCashbackCreditPoolRepository;

	@Autowired
	private DigitalWalletService digitalWalletService;

	@Autowired
	private DigitalWalletRepository dwRepository;
	
	@Autowired
	private TransactionRepository transactionRepository;
	
	@Autowired
	private Utility utility;

	@Autowired
	private AppProperties appProperties;
	
	@Autowired
	private NotificationService notificationService;

	@Autowired
	private CashbackRejectionDetailsRepository cashbackRejectionDetailsRepository;
	
	@Autowired
	private CashbackUploadDetailsRepository cashbackUploadDetailsRepository;
	
	@Autowired
	private CashbackSuccessfulFileUploadRepository cashbackSuccessfulFileUploadRepository;
	
	@Override
	@Transactional
	public SaveCashbackCompositeResponse saveCashback(String shukranId, SaveCashbackRequestComposite request) {
		SaveCashbackRequest saveCashbackRequest = request.getCashback();
		IssueDigitalWalletRequest digitalWalletRequest = request.getDigitalWallet();
		SaveCashbackCompositeResponse response = new SaveCashbackCompositeResponse();
		CashbackNotificationRequest cashbackNotificationRequest = new CashbackNotificationRequest();
		CashbackNotificationDetailsModel cashbackNotificationDetailsModel = new CashbackNotificationDetailsModel();
		String cashbackId=null;
		if (saveCashbackRequest != null) {
			if(lmgCashbackDetailsRepository.getSameTransactionNumber(shukranId,saveCashbackRequest.getOrderReferenceNumber(),saveCashbackRequest.getTransactionId()).intValue()>0) {
				throw new DigitizationException(ErrorCodes.CASHBACK_ALREADY_EXISTS);
				}
			
			cashbackNotificationRequest.setCustomerEmail(saveCashbackRequest.getCustomerEmail());		
			cashbackNotificationRequest.setCustomerMobile(saveCashbackRequest.getCustomerMobile());
			cashbackNotificationRequest.setNotificationType(NotificationType.ISSUE_CASHBACK.toString());	
			cashbackNotificationDetailsModel.setAmount(saveCashbackRequest.getAmount().doubleValue());
			cashbackNotificationDetailsModel.setCurrency(saveCashbackRequest.getBaseCurrency());
			cashbackNotificationDetailsModel.setCampaignName(saveCashbackRequest.getCampaignName());
			cashbackNotificationDetailsModel.setExpiryDate(saveCashbackRequest.getExpiryDate());
			cashbackNotificationDetailsModel.setIssuingConcept(saveCashbackRequest.getIssuingConcept());
			cashbackNotificationDetailsModel.setOrderReferenceNumber(saveCashbackRequest.getOrderReferenceNumber());
			cashbackNotificationDetailsModel.setRedeemableConcept(saveCashbackRequest.getRedeemableConcept());
			cashbackNotificationDetailsModel.setCustomerName(saveCashbackRequest.getCustomerName());
			
			CashbackDetails cashbackDetails=null;
			Optional<CashbackDetails> optionalCashbackDetails = lmgCashbackDetailsRepository.findByShukranIdAndBaseCurrency(shukranId,
					saveCashbackRequest.getBaseCurrency());
			if(optionalCashbackDetails.isPresent()) {
				cashbackDetails=optionalCashbackDetails.get();
			}
			if (saveCashbackRequest.getRedeemableStartDate() == null) {
				saveCashbackRequest.setRedeemableStartDate(LocalDate.now());
			}
			if (saveCashbackRequest.getExpiryDate() == null) {
				saveCashbackRequest.setExpiryDate(utility.getExpiryDay(saveCashbackRequest.getExpiryPeriod(), saveCashbackRequest.getRedeemableStartDate(),
						appProperties.getExpiryDate()));
			}
			if (cashbackDetails != null) {
				cashbackId=cashbackDetails.getCashbackId();
				cashbackDetails.setBalanceAmount(cashbackDetails.getBalanceAmount().add(saveCashbackRequest.getAmount()));
				lmgCashbackDetailsRepository.save(cashbackDetails);
				response.setCashback(setCashbackRespone(cashbackDetails,saveCashbackRequest));
				cashbackNotificationDetailsModel.setBalanceAmount(cashbackDetails.getBalanceAmount().doubleValue());
				

			} else {
				cashbackDetails = new CashbackDetails();
				try {
					BeanUtils.copyProperties(saveCashbackRequest, cashbackDetails);
					cashbackDetails.setShukranId(shukranId);
					cashbackDetails.setRedeemAmount(BigDecimal.valueOf(0.0));
					cashbackDetails.setLastRedeemptionDate(LocalDate.now());
					cashbackDetails.setIsManualProcess(saveCashbackRequest.getFileId() == null ? "N" : "Y");
					cashbackDetails.setCreatedDate(utility.getLocalDateTime(""));
					cashbackDetails.setCreatedBy(saveCashbackRequest.getCustomerName());
					cashbackId = (UUID.randomUUID().toString()).substring(0, 25).replace("-", "");
					cashbackDetails.setCashbackId(cashbackId);
					cashbackDetails.setBalanceAmount(saveCashbackRequest.getAmount());
					cashbackDetails.setCashbackStatus(PromocashConstants.ACTIVE_CASHBACK_STATUS);
					cashbackDetails.setExpiredAmount(BigDecimal.valueOf(0.0));
					cashbackDetails.setIssuedAmount(saveCashbackRequest.getAmount());
					cashbackDetails.setModifiedDate(utility.getLocalDateTime(""));
					cashbackDetails.setOpeningAmount(BigDecimal.valueOf(0.0));
					lmgCashbackDetailsRepository.save(cashbackDetails);
					response.setCashback(setCashbackRespone(cashbackDetails,saveCashbackRequest));
					cashbackNotificationDetailsModel.setBalanceAmount(cashbackDetails.getBalanceAmount().doubleValue());
				} catch (Exception ex) {
					throw new DigitizationException(PromocashConstants.FAILED_TO_SAVE_PROMOCASH,
							PromocashConstants.FAILED_TO_SAVE_PROMOCASH_CODE);
				}
			}
			String cashbackReference=saveCashbackEntry(saveCashbackRequest, shukranId, cashbackId);
			saveCashbackLedger(cashbackId, saveCashbackRequest, shukranId,cashbackReference);
			saveCreditPool(saveCashbackRequest, shukranId);
			cashbackNotificationRequest.setData(cashbackNotificationDetailsModel);
			notificationService.sendNotificationForCashback(cashbackNotificationRequest);

		}
		if (digitalWalletRequest != null)
			response.setDigitalWallet(digitalWalletService.issueDigitalWallet(digitalWalletRequest));
		return response;
	}

	public SaveCashbackResponse setCashbackRespone(CashbackDetails cashbackDetails, SaveCashbackRequest saveCashbackRequest) {
		SaveCashbackResponse cashbackResponse = new SaveCashbackResponse();
		BeanUtils.copyProperties(cashbackDetails, cashbackResponse);
		cashbackResponse.setExpiryDate(saveCashbackRequest.getExpiryDate());
		cashbackResponse.setPushText(saveCashbackRequest.getPushText());
		cashbackResponse.setSmsText(saveCashbackRequest.getSmsText());
		cashbackResponse.setRedeemableConcept(saveCashbackRequest.getRedeemableConcept());
		cashbackResponse.setRedeemableStartDate(saveCashbackRequest.getRedeemableStartDate().toString());
		cashbackResponse.setCampaignName(saveCashbackRequest.getCampaignName());
		cashbackResponse.setMessage(PromocashConstants.SUCCESS);
		cashbackResponse.setIssuingConcept(saveCashbackRequest.getIssuingConcept());
		cashbackResponse.setSourceApp(saveCashbackRequest.getSourceApp());
		cashbackResponse.setExpiryPeriod(saveCashbackRequest.getExpiryPeriod());
		return cashbackResponse;
	}
	
	
	/* Business logic to save cashback data into Cashback Ledger table */
	public boolean saveCashbackLedger(String cashbackId, SaveCashbackRequest saveCashbackRequest, String shukranId, String cashbackReferenceId) {
		CashbackLedger ledger = new CashbackLedger();
		
		BeanUtils.copyProperties(saveCashbackRequest, ledger);
		ledger.setShukranId(shukranId);
		ledger.setTransactionId(saveCashbackRequest.getTransactionId());
		ledger.setOrderNumber(saveCashbackRequest.getOrderReferenceNumber());
		ledger.setStore(saveCashbackRequest.getIssuingConcept());
		ledger.setBusinessDate(LocalDate.now());
		ledger.setConcept(saveCashbackRequest.getIssuingConcept());
		ledger.setCurrency(saveCashbackRequest.getBaseCurrency());
		ledger.setCreatedDate(LocalDateTime.now());
		ledger.setCashbackId(cashbackId);
		ledger.setExpiryDate(saveCashbackRequest.getExpiryDate());
		ledger.setModifiedDate(LocalDateTime.now());
		ledger.setOpeningBalance(new BigDecimal("0.0"));
		ledger.setClosingBalance(saveCashbackRequest.getAmount());
		ledger.setStatus(Status.ISSUED.toString());
		ledger.setReferenceNumber(cashbackReferenceId);
		ledger.setSourceReference(saveCashbackRequest.getSourceApp());
		ledger.setTranVersion("0");
		ledger.setSource(saveCashbackRequest.getSourceApp());
		ledger.setRedeemableConcept(saveCashbackRequest.getRedeemableConcept());
		lmgCashbackLedgerRepository.save(ledger);
		return true;
	}

	/* Business logic to save cashback data into Cashback Entry table */	
	public String saveCashbackEntry(SaveCashbackRequest saveCashbackRequest, String shukranId, String cashbackId) {
		CashbackEntry cashbackEntry = new CashbackEntry();
		BeanUtils.copyProperties(saveCashbackRequest, cashbackEntry);
		cashbackEntry.setCashbackId(cashbackId);
		cashbackEntry.setTransactionDate(LocalDate.now());
		cashbackEntry.setReturnOrderRefNo(saveCashbackRequest.getOrderReferenceNumber());
		cashbackEntry.setRedeemedAmount(BigDecimal.valueOf(0.0));
		cashbackEntry.setExpirationDate(saveCashbackRequest.getExpiryDate());
		cashbackEntry.setStatus(Status.ISSUED.toString());
		cashbackEntry.setSource(saveCashbackRequest.getSourceApp());
		cashbackEntry.setTerritoryCode(saveCashbackRequest.getTerritory());
		cashbackEntry.setCreatedDate(utility.getLocalDateTime(""));
		cashbackEntry.setModifiedDate(utility.getLocalDateTime(""));
		cashbackEntry.setIsExpiryExtended(false);
		String cashbackReferenceId=(UUID.randomUUID().toString()).substring(0, 25).replace("-", "");
		cashbackEntry.setCashbackReferenceId(cashbackReferenceId);
		cashbackEntry.setShukranId(shukranId);
		cashbackEntry.setBalanceAmount(saveCashbackRequest.getAmount());
		cashbackEntry.setIssuedAmount(saveCashbackRequest.getAmount());
		cashbackEntry.setRedeemableStartDate(saveCashbackRequest.getRedeemableStartDate());
		lmgCashbackEntryRepository.save(cashbackEntry);
		return cashbackReferenceId;
	}

	public boolean saveCreditPool(SaveCashbackRequest saveCashbackRequest, String shukranId) {
		CashbackCreditPool cashbackCreditPool = new CashbackCreditPool();
		cashbackCreditPool.setShukranId(shukranId);
		cashbackCreditPool.setTransactionDate(LocalDate.now());
		cashbackCreditPool.setCreditPoolAmount(saveCashbackRequest.getAmount());
		cashbackCreditPool.setType("Cr");
		lmgCashbackCreditPoolRepository.save(cashbackCreditPool);
		return true;
	}

	/* Business logic to upload excel file */	
	public BaseResponse upload(MultipartFile file) {
		BaseResponse response = new BaseResponse();
		CashbackUploadDetails upload = new CashbackUploadDetails();

		upload.setFileName(file.getOriginalFilename());
		upload.setIsProcess("N");
		upload.setUploadDate(LocalDateTime.now());
		upload.setUploadBy("System");
		upload = cashbackUploadRepository.save(upload);
		try {

			XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
			XSSFSheet sheet = workbook.getSheetAt(0);
			int lastRow = sheet.getPhysicalNumberOfRows();

			for (int i = 1; i < lastRow; i++) {
				try {
					parseExccelToModel(upload.getId(), sheet.getRow(i));
				}
				catch (Exception e) {
					LOGGER.error(Logger.EVENT_FAILURE,e.getMessage());
					saveException(upload.getId(),e.getMessage(),sheet.getRow(i));
					response.setStatus(PromocashConstants.FAILURE);
					response.setErrorMessage("Error occured while uploading data");
				}
			}
			if(response.getErrorMessage()==null) {
			response.setStatus(PromocashConstants.SUCCESS);
			}
			else
			{
				throw new DigitizationException(PromocashConstants.FILE_UPLOAD,
						PromocashConstants.FILE_UPLOAD_ERROR);
			}
		} catch (IllegalStateException|IOException e) {
			LOGGER.error(Logger.EVENT_FAILURE,e.getMessage());
			response.setStatus(PromocashConstants.FAILURE);
			response.setErrorMessage("Please provide all excel data as text.");
		}
		return response;
	}

	public void saveException(Long fileId, String message, Row row)
	{
		CashbackRejectionDetails rejection=new CashbackRejectionDetails();
		rejection.setFileId(new BigDecimal(fileId));
		rejection.setReason(message);
		rejection.setRejectionDate(LocalDateTime.now());
		rejection.setTransactionId((row.getCell(19).getStringCellValue()));
		rejection.setOrderReference(row.getCell(7).getStringCellValue());
		//rejection.setJsonObject(row.toString());
		rejection.setShukranId(row.getCell(0).getStringCellValue());
		
		cashbackRejectionDetailsRepository.save(rejection);
		
	}
	
	/* Business logic to iterate the records from excel and push it for cashback */	
	private void parseExccelToModel(Long fileId, Row row) {
		SaveCashbackRequestComposite request = new SaveCashbackRequestComposite();
		SaveCashbackRequest cashbackRequest = new SaveCashbackRequest();
		String shukranId = row.getCell(0).getStringCellValue();
		cashbackRequest.setAmount(new BigDecimal(row.getCell(1).getStringCellValue()));
		cashbackRequest.setCampaignName(row.getCell(2).getStringCellValue());
		cashbackRequest.setBaseCurrency(row.getCell(3).getStringCellValue());
		cashbackRequest.setExpiryPeriod(Integer.parseInt(row.getCell(4).getStringCellValue()));
		cashbackRequest.setExpiryDate(utility.getLocalDateFromString(row.getCell(5).getStringCellValue()));
		cashbackRequest.setFileId(new BigDecimal(fileId));
		cashbackRequest.setIssuingConcept((row.getCell(6).getStringCellValue()));
		cashbackRequest.setOrderReferenceNumber((row.getCell(7).getStringCellValue()));
		cashbackRequest.setPushText((row.getCell(8).getStringCellValue()));
		cashbackRequest.setRedeemableStartDate((utility.getLocalDateFromString(row.getCell(9).getStringCellValue())));
		cashbackRequest.setRedeemableConcept((row.getCell(10).getStringCellValue()));
		cashbackRequest.setSmsText((row.getCell(11).getStringCellValue()));
		cashbackRequest.setTerritory((row.getCell(12).getStringCellValue()));
		cashbackRequest.setVersion((row.getCell(13).getStringCellValue()));
		cashbackRequest.setCustomerEmail((row.getCell(14).getStringCellValue()));
		cashbackRequest.setCustomerMobile((row.getCell(15).getStringCellValue()));
		cashbackRequest.setCustomerName((row.getCell(16).getStringCellValue()));
		cashbackRequest.setApplicability((row.getCell(17).getStringCellValue()));
		cashbackRequest.setSourceApp((row.getCell(18).getStringCellValue()));
		cashbackRequest.setTransactionId((row.getCell(19).getStringCellValue()));
		request.setCashback(cashbackRequest);
		saveCashback(shukranId, request);
		saveSuccessfulRecords(fileId,shukranId,row);
		
	}
	
	public void saveSuccessfulRecords(Long fileId,String shukranId,Row row)
	{
		CashbackSuccessfulUploadDetails success=new CashbackSuccessfulUploadDetails();
		success.setCreateDate(LocalDateTime.now());
		success.setFileId(fileId);
		success.setLineObject(row.toString());
		success.setShukranId(shukranId);
		success.setTransactionId(row.getCell(19).getStringCellValue());
		success.setOrderReference(row.getCell(7).getStringCellValue());
		cashbackSuccessfulFileUploadRepository.save(success);
	}
	
	
	private CashbackReferencesResponse mapCashbackWalletReference(CashbackEntry x) {
		if (x != null) {
			CashbackReferencesResponse reference = new CashbackReferencesResponse();
			reference.setBalanceAmount(x.getBalanceAmount());
			reference.setExpirationDate(x.getExpirationDate());
			reference.setCashbackRefenceId(x.getCashbackReferenceId());
			reference.setRedeemableConcept(x.getRedeemableConcept());
			return reference;
		}
		return null;
	}
	
	public WalletBalanceResponse fetchDWBalance(String shukranId, FetchWalletBalanceRequest fwbr) {
		/* Retrieve DW balance - using DigitalWalletService method */
		FetchWalletBalanceCompositeResponse balance = new FetchWalletBalanceCompositeResponse();
		WalletBalanceRequest dwwalletBalanceRequest = new WalletBalanceRequest();
		dwwalletBalanceRequest.setBaseCurrency(fwbr.getBaseCurrency());
		dwwalletBalanceRequest.setBusinessDate(fwbr.getBusinessDate());
		dwwalletBalanceRequest.setLanguage(fwbr.getLanguage());
		dwwalletBalanceRequest.setNoOfDays(fwbr.getNoOfDays());
		Optional<DWWalletModel> optionalModel = dwRepository.findByShukranIdAndBaseCurrency(shukranId, fwbr.getBaseCurrency());
		WalletBalanceResponse dwwalletBalanceResponse=null;
		if(optionalModel.isPresent()) {	
			dwwalletBalanceResponse = digitalWalletService.getWalletBalance(shukranId, dwwalletBalanceRequest);
			balance.setDigitalWallet(dwwalletBalanceResponse);
		}
		return dwwalletBalanceResponse;
	}
	
	public CashbackBalanceResponse fetchTotalCashbackBalance(String shukranId, FetchWalletBalanceRequest fwbr) {
		
		/* Logic for retrieving cashback balance */
		CashbackBalanceResponse cashbackResponse = null;
		Optional<CashbackDetails> optionalCashbackModel = lmgCashbackDetailsRepository.findByShukranIdAndBaseCurrency(shukranId, fwbr.getBaseCurrency());
		if(optionalCashbackModel.isPresent()) {
			CashbackDetails cashbackModel=optionalCashbackModel.get();
			cashbackResponse = new CashbackBalanceResponse();
			cashbackResponse.setCashbackBalance(cashbackModel.getBalanceAmount());
			cashbackResponse.setVersion(cashbackModel.getVersion());
			cashbackResponse.setCashbackId(cashbackModel.getCashbackId());
			cashbackResponse.setCurrency(cashbackModel.getBaseCurrency());
		}
		return cashbackResponse;
	}

	@Override
	public FetchWalletBalanceCompositeResponse fetchWalletBalance(String shukranId,
			 FetchWalletBalanceRequest fwbr) {
		
		WalletBalanceResponse dwBalance = fetchDWBalance(shukranId,fwbr);
		CashbackBalanceResponse cashbackBalance = fetchTotalCashbackBalance(shukranId, fwbr);
		BigDecimal totalCashbackBalance=BigDecimal.ZERO;
		BigDecimal balanceWithin = BigDecimal.ZERO;
		if(dwBalance==null&& cashbackBalance==null) {
			LOGGER.error(Logger.EVENT_FAILURE,PromocashConstants.FAILED_TO_RETRIEVE_CASHBACK_DW.concat(JsonSanitizer.sanitize(shukranId)));
			throw new DigitizationException(String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, fwbr.getBaseCurrency()),
					DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE);
		} 
		FetchWalletBalanceCompositeResponse compositeResponse = new FetchWalletBalanceCompositeResponse();
		LocalDate startDate = LocalDate.parse(fwbr.getBusinessDate(), DateTimeFormatter.ISO_DATE);
		LocalDate endDate = LocalDate.parse(fwbr.getBusinessDate(), DateTimeFormatter.ISO_DATE)
				.plusDays(fwbr.getNoOfDays());
		
		List<String> status = new ArrayList<>();
		status.add(Status.ISSUED.toString());
		status.add(Status.EXTENDED.toString());
		List<CashbackEntry> expiredWalletReferences = null;
		if(cashbackBalance!=null) {
		expiredWalletReferences = lmgCashbackEntryRepository
					.findAllByCashbackIdAndStatusInAndExpirationDateBetweenOrderByExpirationDateAsc(cashbackBalance.getCashbackId(), status, startDate,
							endDate);
		
		HashMap<String, List<CashbackEntry>> referenceMap = new HashMap<String, List<CashbackEntry>>();
		for(CashbackEntry cashbackEntry : expiredWalletReferences) {
			if (referenceMap.containsKey(cashbackEntry.getRedeemableConcept())) {
				List<CashbackEntry> valueList = referenceMap.get(cashbackEntry.getRedeemableConcept());
				valueList.add(cashbackEntry);
				referenceMap.put(cashbackEntry.getRedeemableConcept(), valueList);
			}else
			{
				List<CashbackEntry> valueList = new ArrayList<CashbackEntry>();
				valueList.add(cashbackEntry);
				referenceMap.put(cashbackEntry.getRedeemableConcept(), valueList);
			}
		}
		List<CashbackBalanceConceptWiseResponse> balanceConceptWiseResponseList = new ArrayList<CashbackBalanceConceptWiseResponse>();
		if (fwbr.getRedeemableConcept() != null) {
			List<CashbackEntry> resultEntry = referenceMap.get(fwbr.getRedeemableConcept());

			if (resultEntry != null ) {
				CashbackBalanceConceptWiseResponse balanceConceptWiseResponse = new CashbackBalanceConceptWiseResponse();
				balanceConceptWiseResponse.setReferences(resultEntry.stream().map(this::mapCashbackWalletReference)
						.sorted((o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()))
						.collect(Collectors.toList()));
				balanceConceptWiseResponse.setConcept(fwbr.getRedeemableConcept());
				balanceConceptWiseResponse.setCashbackId(cashbackBalance.getCashbackId());
				balanceConceptWiseResponse.setVersion(cashbackBalance.getVersion());
				balanceConceptWiseResponse.setCurrency(cashbackBalance.getCurrency());
				balanceConceptWiseResponse.setCashbackBalance(resultEntry.stream().map(o -> o.getBalanceAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
				balanceWithin = balanceWithin.add(balanceConceptWiseResponse.getCashbackBalance());
				balanceConceptWiseResponseList.add(balanceConceptWiseResponse);
			}
			
			/* Adding redeemable concept 99 balance along with the requested concept */
			
			List<CashbackEntry> resultEntry99 = referenceMap.get(PromocashConstants.REDEEMABLE_CONCEPT_99);

			if (resultEntry99 != null  &&  !fwbr.getRedeemableConcept().equals(PromocashConstants.REDEEMABLE_CONCEPT_99)) {
				CashbackBalanceConceptWiseResponse balanceConceptWiseResponse = new CashbackBalanceConceptWiseResponse();
				balanceConceptWiseResponse.setReferences(resultEntry99.stream().map(this::mapCashbackWalletReference)
						.sorted((o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()))
						.collect(Collectors.toList()));
				balanceConceptWiseResponse.setConcept(PromocashConstants.REDEEMABLE_CONCEPT_99);
				balanceConceptWiseResponse.setCashbackId(cashbackBalance.getCashbackId());
				balanceConceptWiseResponse.setVersion(cashbackBalance.getVersion());
				balanceConceptWiseResponse.setCurrency(cashbackBalance.getCurrency());
				balanceConceptWiseResponse.setCashbackBalance(resultEntry99.stream().map(o -> o.getBalanceAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
				balanceWithin = balanceWithin.add(balanceConceptWiseResponse.getCashbackBalance());
				balanceConceptWiseResponseList.add(balanceConceptWiseResponse);
			}
		}else {
			for (List<CashbackEntry> conceptWiseEntryList : referenceMap.values()) {
				List<CashbackEntry> resultEntry = conceptWiseEntryList;
				
				if (resultEntry != null ) {
					String redeemableConcept = resultEntry.get(0).getRedeemableConcept();
					CashbackBalanceConceptWiseResponse balanceConceptWiseResponse = new CashbackBalanceConceptWiseResponse();
					balanceConceptWiseResponse.setReferences(resultEntry.stream().map(this::mapCashbackWalletReference)
							.sorted((o1, o2) -> o1.getExpirationDate().compareTo(o2.getExpirationDate()))
							.collect(Collectors.toList()));
					balanceConceptWiseResponse.setConcept(redeemableConcept);
					balanceConceptWiseResponse.setCashbackId(cashbackBalance.getCashbackId());
					balanceConceptWiseResponse.setVersion(cashbackBalance.getVersion());
					balanceConceptWiseResponse.setCurrency(cashbackBalance.getCurrency());
					balanceConceptWiseResponse.setCashbackBalance(resultEntry.stream().map(o -> o.getBalanceAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
					balanceWithin = balanceWithin.add(balanceConceptWiseResponse.getCashbackBalance());
					balanceConceptWiseResponseList.add(balanceConceptWiseResponse);
				}
			}
			
		}
		compositeResponse.setCashback(balanceConceptWiseResponseList);
		}
		if (dwBalance!=null) {
			balanceWithin = balanceWithin.add(dwBalance.getReferences().stream().map(o -> o.getBalanceAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b)));
		}
		if(fwbr.getRedeemableConcept()==null) {
			totalCashbackBalance=lmgCashbackEntryRepository.selectTotalCashback(shukranId, fwbr.getBaseCurrency());
		}
		else {
			totalCashbackBalance=lmgCashbackEntryRepository.selectCashbackTotalConcept(shukranId, fwbr.getBaseCurrency(), fwbr.getRedeemableConcept());
		}
		compositeResponse.setBaseCurrency(fwbr.getBaseCurrency());
		compositeResponse.setDigitalWallet(dwBalance);
		compositeResponse.setBalanceWithin(balanceWithin);
		compositeResponse.setTotalBalance(totalCashbackBalance.add(dwBalance != null ? dwBalance.getBalance() : BigDecimal.ZERO));
		return compositeResponse;
	}

	
	@Override
	public FetchMonthlyBalanceResponse fetchMonthlyBalance(String shukranId,  FetchWalletBalanceRequest fwbr) {
		
		/* Get current balances from the fetchWalletBalanceMethod*/
		
		LOGGER.info(Logger.EVENT_SUCCESS,"Fetching Current Cashback & Digital Wallent Balance for Shukran Id: {}"+ JsonSanitizer.sanitize(shukranId));
		
		WalletBalanceResponse dwBalance = fetchDWBalance(shukranId,fwbr);
		CashbackBalanceResponse cashbackBalance = fetchTotalCashbackBalance(shukranId, fwbr);
		
		
		if(dwBalance==null&& cashbackBalance==null) {
			LOGGER.error(Logger.EVENT_FAILURE,PromocashConstants.FAILED_TO_RETRIEVE_CASHBACK_DW.concat(JsonSanitizer.sanitize(shukranId)));
			throw new DigitizationException(String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, fwbr.getBaseCurrency()),
					DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE);
		} 
		FetchMonthlyBalanceResponse monthlyBalanceResponse = new FetchMonthlyBalanceResponse();
		List<ReferencesResponse> refResponseList = null;
		
		/* Check if cashback exists then also fetch all the references*/
		
		if(cashbackBalance != null) {
			LOGGER.info(Logger.EVENT_SUCCESS,"Cashback balance exists. Fetching cashback references for Shukran Id: {}"+ JsonSanitizer.sanitize(shukranId));
			monthlyBalanceResponse.setCashbackBalance(lmgCashbackEntryRepository.selectTotalCashback(shukranId, fwbr.getBaseCurrency()));
			refResponseList = new ArrayList<ReferencesResponse>(); 
			List<String> status = new ArrayList<>();
			status.add(Status.ISSUED.toString());
			status.add(Status.EXTENDED.toString());
			List<CashbackEntry> expiredWalletReferences = lmgCashbackEntryRepository
					.findAllByCashbackIdAndStatusIn(cashbackBalance.getCashbackId(), status);
			
			for (CashbackEntry cbModel: expiredWalletReferences) {
				ReferencesResponse refResponse = new ReferencesResponse();
				refResponse.setType(PromocashConstants.CASHBACK);
				refResponse.setRedeemableConcept(cbModel.getRedeemableConcept());
				refResponse.setReferenceId(cbModel.getCashbackId());
				refResponse.setExpirationDate(cbModel.getExpirationDate());
				refResponse.setAmount(cbModel.getBalanceAmount());
				refResponseList.add(refResponse);
			}
		}else {
			LOGGER.info(Logger.EVENT_SUCCESS,"No Cashback balance exists for Shukran Id: {}"+JsonSanitizer.sanitize(shukranId));
			
			monthlyBalanceResponse.setCashbackBalance(null);
		}
		
		/* Check if digital wallet exists then also fetch all the references*/

		if(dwBalance != null) {
			LOGGER.info(Logger.EVENT_SUCCESS,"Digital Wallet balance exists. Fetching Digital Wallet references for Shukran Id: {}"+ JsonSanitizer.sanitize(shukranId));
			
			monthlyBalanceResponse.setWalletBalance(dwBalance.getBalance());
			List<String> status = new ArrayList<>();
			status.add(Status.ISSUED.toString());
			status.add(Status.EXTENDED.toString());
			List<DigitalWalletTransactionModel> expiredWalletReferences = transactionRepository
					.findAllByWalletIDAndStatusIn(dwBalance.getWalletId(), status);
			if (refResponseList == null) {refResponseList = new ArrayList<ReferencesResponse>();}
			for (DigitalWalletTransactionModel dwModel: expiredWalletReferences) {
				ReferencesResponse refResponse = new ReferencesResponse();
				refResponse.setType(PromocashConstants.DIGITAL_WALLET);
				refResponse.setRedeemableConcept(null);
				refResponse.setReferenceId(dwModel.getWalletReferenceId());
				refResponse.setExpirationDate(dwModel.getExpirationDate());
				refResponse.setAmount(dwModel.getBalanceAmount());
				refResponseList.add(refResponse);
			}
		}else {
			LOGGER.info(Logger.EVENT_SUCCESS ,"No Digital Wallet balance exists for Shukran Id: {}"+ JsonSanitizer.sanitize(shukranId));

			monthlyBalanceResponse.setWalletBalance(null);
		}
		
		/* Create a map of month-year vs list of references (both DW & Cashback) belonging to that month-year */
		
		List<MonthlyReferencesResponse> monthlyReferencesResponses = new ArrayList<MonthlyReferencesResponse>();
		LinkedHashMap<String, List<ReferencesResponse>> monthMap= new LinkedHashMap<String, List<ReferencesResponse>>();
		refResponseList.sort(Comparator.comparing(ReferencesResponse::getExpirationDate));
		for (ReferencesResponse ref: refResponseList) {
			String monthYear = ref.getExpirationDate().getMonth().toString()+"-"+ref.getExpirationDate().getYear();
			if (monthMap.containsKey(monthYear)){
				List<ReferencesResponse> referenceResponseListForKey = monthMap.get(monthYear);
				referenceResponseListForKey.add(ref);
				monthMap.put(monthYear, referenceResponseListForKey);
			}else {
				List<ReferencesResponse> referenceResponseListForKey = new ArrayList<ReferencesResponse>();
				referenceResponseListForKey.add(ref);
				monthMap.put(monthYear, referenceResponseListForKey);
			}
		}
		
		/* Formatting the month-year vs monthly reference response map to our defined format */
			
		for (String key : monthMap.keySet()) {
			MonthlyReferencesResponse monthlyReferencesResponse = new MonthlyReferencesResponse();
			monthlyReferencesResponse.setMonth(key);
			monthlyReferencesResponse.setReferences(monthMap.get(key));
			monthlyReferencesResponses.add(monthlyReferencesResponse);
		}
		monthlyBalanceResponse.setMonthlyReferences(monthlyReferencesResponses);
		monthlyBalanceResponse.setShukranId(shukranId);
		
		return monthlyBalanceResponse;
	}
	
	@Override
	public FetchBalanceWithoutReferenceResponse fetchBalanceWithoutReference(String shukranId,
			 FetchWalletBalanceRequest fwbr) {
        /* Get current balances from the fetchWalletBalanceMethod*/
		
		LOGGER.info(Logger.EVENT_SUCCESS,"Fetching Current Cashback & Digital Wallent Balance for Shukran Id: {}"+JsonSanitizer.sanitize(shukranId) );
		
		WalletBalanceResponse dwBalance = fetchDWBalance(shukranId,fwbr);
		CashbackBalanceResponse cashbackBalance = fetchTotalCashbackBalance(shukranId, fwbr);
		
		
		if(dwBalance==null&& cashbackBalance==null) {
			LOGGER.error(Logger.EVENT_FAILURE,PromocashConstants.FAILED_TO_RETRIEVE_CASHBACK_DW.concat(JsonSanitizer.sanitize(shukranId)));
			throw new DigitizationException(String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, shukranId, fwbr.getBaseCurrency()),
					DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE);
		} 
		
		FetchBalanceWithoutReferenceResponse balanceWithoutReferenceResponse = new FetchBalanceWithoutReferenceResponse();
		BigDecimal totalBalance = BigDecimal.ZERO;
		
		/* Check Cashback Balance exists. If exists add to totalBalance */
		
		if(cashbackBalance!= null) {
			LOGGER.info(Logger.EVENT_SUCCESS,"Cashback balance exists for Shukran Id: {}"+ JsonSanitizer.sanitize(shukranId) );
			BigDecimal totalCashbackBalance=lmgCashbackEntryRepository.selectTotalCashback(shukranId, fwbr.getBaseCurrency());
			balanceWithoutReferenceResponse.setCashbackBalance(totalCashbackBalance);
			totalBalance = totalBalance.add(totalCashbackBalance);
		}else {
			LOGGER.info(Logger.EVENT_SUCCESS,"No Cashback balance exists for Shukran Id: {}"+ JsonSanitizer.sanitize(shukranId));
			balanceWithoutReferenceResponse.setCashbackBalance(null);
		}
		
		/* Check Digital Wallet Balance exists. If exists add to totalBalance */
		
		if(dwBalance != null) {
			LOGGER.info(Logger.EVENT_SUCCESS,"Digital Wallet balance exists for Shukran Id: {}"+ JsonSanitizer.sanitize(shukranId));
			balanceWithoutReferenceResponse.setWalletBalance(dwBalance.getBalance());
			totalBalance = totalBalance.add(dwBalance.getBalance());

		}else {
			LOGGER.info(Logger.EVENT_SUCCESS,"No Digital Wallet balance exists for Shukran Id: {}"+ JsonSanitizer.sanitize(shukranId));
			balanceWithoutReferenceResponse.setWalletBalance(null);
		}
		balanceWithoutReferenceResponse.setTotalBalance(totalBalance);
		balanceWithoutReferenceResponse.setShukranId(shukranId);
		return balanceWithoutReferenceResponse;
	}

	@Override
	public FileUploadSummaryResponse getFileUploadSummary(FileUploadSummaryRequest request) {
		@SuppressWarnings("rawtypes")
		List<Map> fileUploadSummaryDetails = cashbackUploadDetailsRepository.getSummaryReport(request.getStartDate(), request.getEndDate());
		FileUploadSummaryResponse response=new FileUploadSummaryResponse();
		List<FileUploadResponse> fileSummary=new ArrayList<>();
		for (@SuppressWarnings("rawtypes")
		Map fileUploadSummaryDetail : fileUploadSummaryDetails) {
			FileUploadResponse summary=new FileUploadResponse();
			summary.setFileName((String)fileUploadSummaryDetail.get("file_name"));
			summary.setId((BigInteger)fileUploadSummaryDetail.get("id"));
			summary.setTotalFailure((Integer)fileUploadSummaryDetail.get("total_failure"));
			summary.setTotalSuccess((Integer)fileUploadSummaryDetail.get("total_success"));
			summary.setUploadedDate(((Date)fileUploadSummaryDetail.get("upload_date")).toLocalDate());
			fileSummary.add(summary);
		}
		response.setFileUploadResponse(fileSummary);
		response.setStatus("Success");
		return response;
	}

	@Override
	public CashbackRejectionDetailsResponse getCashbackRejectionDetails(BigDecimal fileId) {
		CashbackRejectionDetailsResponse response=new CashbackRejectionDetailsResponse();
		response.setCashbackRejectionDetails(cashbackRejectionDetailsRepository.findAllByFileId(fileId));
		return response;
	}

	@Override
	public ExpireBalanceEntriesResponse expireBalanceEntries() {
		ExpireBalanceEntriesResponse expireBalanceEntriesResponse = new ExpireBalanceEntriesResponse();
		List<String> status = new ArrayList<>();
		status.add(Status.ISSUED.toString());
		status.add(Status.EXTENDED.toString());
		List<CashbackEntry> expiredWalletReferences = lmgCashbackEntryRepository
				.findAllByStatusInAndExpirationDateLessThan(status, LocalDate.now());

		
		Map<String, Map<String, List<CashbackEntry>>> shukranIdCurrencyMap = expiredWalletReferences
																			.stream()
																			.collect(Collectors.groupingBy(CashbackEntry::getShukranId,Collectors.groupingBy(CashbackEntry::getBaseCurrency)));
       
		expiredWalletReferences.forEach(expiredWalletReferencesobj -> expiredWalletReferencesobj.setStatus(PromocashConstants.EXPIRED));
		List<CashbackEntry> updateEntries = lmgCashbackEntryRepository.saveAll(expiredWalletReferences);	
		
		LOGGER.info(Logger.EVENT_SUCCESS,"Cashback Entries Updated. Total entries: {}"+ updateEntries.size());
		if (expiredWalletReferences.size() > 0)
		{
		List<CashbackLedger> expiredLedgerReferences = lmgCashbackLedgerRepository
				.findAllByStatusInAndExpiryDateLessThan(status, LocalDate.now());

		
		for (int i = 0; i < expiredLedgerReferences.size(); i++)
		{
			CashbackLedger ledger = new CashbackLedger();
			ledger.setShukranId(expiredLedgerReferences.get(i).getShukranId());
			ledger.setTransactionId(expiredLedgerReferences.get(i).getTransactionId());
			ledger.setOrderNumber(expiredLedgerReferences.get(i).getOrderNumber());
			ledger.setStore(expiredLedgerReferences.get(i).getConcept());
			ledger.setBusinessDate(expiredLedgerReferences.get(i).getBusinessDate());
			ledger.setConcept(expiredLedgerReferences.get(i).getConcept());
			ledger.setCurrency(expiredLedgerReferences.get(i).getCurrency());
			ledger.setCreatedDate(LocalDateTime.now());
			ledger.setCashbackId(expiredLedgerReferences.get(i).getCashbackId());
			ledger.setExpiryDate(expiredLedgerReferences.get(i).getExpiryDate());
			ledger.setModifiedDate(LocalDateTime.now());
			ledger.setAmount(expiredLedgerReferences.get(i).getAmount());
			ledger.setOpeningBalance(new BigDecimal("0.0"));
			ledger.setClosingBalance(expiredLedgerReferences.get(i).getAmount());
			ledger.setStatus(Status.EXPIRED.toString());
			ledger.setReferenceNumber(expiredLedgerReferences.get(i).getReferenceNumber());
			ledger.setSourceReference(expiredLedgerReferences.get(i).getSource());
			ledger.setTranVersion("0");
			ledger.setSource(expiredLedgerReferences.get(i).getSource());
			ledger.setRedeemableConcept(expiredLedgerReferences.get(i).getRedeemableConcept());
			lmgCashbackLedgerRepository.save(ledger);
		}
		

	
		LOGGER.info(Logger.EVENT_SUCCESS,"Cashback Ledger Updated. Total line items: {}"+ expiredLedgerReferences.size());
		}

		
		for (String key : shukranIdCurrencyMap.keySet()) {
			Map<String, List<CashbackEntry>> currencyMap = shukranIdCurrencyMap.get(key);
			String shukranId = key;
			for ( String curr: currencyMap.keySet()) {
				List<CashbackEntry> currencyEntries = currencyMap.get(curr);
				String currency = curr;
				BigDecimal sumAmount = currencyEntries.stream().map(o -> o.getBalanceAmount()).reduce(BigDecimal.ZERO, (a, b) -> a.add(b));
				Optional<CashbackDetails> optionalCashbackModel = lmgCashbackDetailsRepository.findByShukranIdAndBaseCurrency(shukranId, currency);
				CashbackDetails cashbackDetails = optionalCashbackModel.isPresent() ? optionalCashbackModel.get() : null;
				if(cashbackDetails != null)
				{
				BigDecimal balanceAmount = cashbackDetails.getBalanceAmount();
				cashbackDetails.setBalanceAmount(balanceAmount.subtract(sumAmount));
				lmgCashbackDetailsRepository.save(cashbackDetails);
				LOGGER.info(Logger.EVENT_SUCCESS,"Balance updated of shukranId: {} for currency: {}. New balance is :{}"+ shukranId+ " "+currency+" "+ balanceAmount.subtract(sumAmount));
				
				CashbackNotificationRequest cashbackNotificationRequest = new CashbackNotificationRequest();
				CashbackNotificationDetailsModel cashbackNotificationDetailsModel = new CashbackNotificationDetailsModel();
				cashbackNotificationRequest.setNotificationType(NotificationType.EXPIRED_CASHBACK.toString());
				cashbackNotificationRequest.setCustomerEmail(cashbackDetails.getCustomerEmail());
				cashbackNotificationRequest.setCustomerMobile(cashbackDetails.getCustomerMobile());
				cashbackNotificationDetailsModel.setCustomerName(cashbackDetails.getCustomerName());
				cashbackNotificationDetailsModel.setAmount(sumAmount.doubleValue());
				cashbackNotificationDetailsModel.setBalanceAmount(balanceAmount.subtract(sumAmount).doubleValue());
				cashbackNotificationDetailsModel.setCurrency(cashbackDetails.getBaseCurrency());
				cashbackNotificationRequest.setData(cashbackNotificationDetailsModel);
				notificationService.sendNotificationForCashback(cashbackNotificationRequest);

			}
		}
		}

		expireBalanceEntriesResponse.setCount(updateEntries.size());
		return expireBalanceEntriesResponse;
	}
}
