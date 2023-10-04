package com.lmg.digitization.cashback.controller;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.Size;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.lmg.digitization.cashback.request.CreateCampaignRequest;
import com.lmg.digitization.cashback.request.FetchWalletBalanceRequest;
import com.lmg.digitization.cashback.request.FileUploadSummaryRequest;
import com.lmg.digitization.cashback.request.SaveCashbackRequestComposite;
import com.lmg.digitization.cashback.response.CashbackRejectionDetailsResponse;
import com.lmg.digitization.cashback.response.CreateCampaignResponse;
import com.lmg.digitization.cashback.response.ExpireBalanceEntriesResponse;
import com.lmg.digitization.cashback.response.FetchBalanceWithoutReferenceResponse;
import com.lmg.digitization.cashback.response.FetchMonthlyBalanceResponse;
import com.lmg.digitization.cashback.response.FetchWalletBalanceCompositeResponse;
import com.lmg.digitization.cashback.response.FileUploadSummaryResponse;
import com.lmg.digitization.cashback.response.SaveCashbackResponse;
import com.lmg.digitization.cashback.response.SaveCashbackCompositeResponse;
import com.lmg.digitization.cashback.service.CampaignService;
import com.lmg.digitization.cashback.service.PromocashService;
import com.lmg.digitization.digital.wallet.response.BaseResponse;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Validated
@RequestMapping("/v1/promocash/")
public class PromocashController {

	@Autowired
	private PromocashService promocashService;

	@Autowired
	private CampaignService campaignService;

	@PostMapping("submitcashback/{shukranId}")
	@ApiOperation(value = "Promocash submit cashback service", response = SaveCashbackRequestComposite.class)
	@ApiResponses(value = { @ApiResponse(code = 400,response = BaseResponse.class, message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public SaveCashbackCompositeResponse saveCashback(@PathVariable("shukranId") @Size(min = 8, max = 30) String shukranId,
			@Valid @RequestBody SaveCashbackRequestComposite scr) {
		return promocashService.saveCashback(shukranId, scr);
	}

	@PostMapping("campaign/{channel}")
	@ApiOperation(value = "Create campaign service", response = CreateCampaignResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class,message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public CreateCampaignResponse createCampaign(@PathVariable("channel") String channel,
			@Valid @RequestBody CreateCampaignRequest scr) {
		return campaignService.saveCampaign(channel, scr);
	}

	@PutMapping("campaign/{campaign-id}")
	@ApiOperation(value = "Create campaign service", response = CreateCampaignResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public CreateCampaignResponse updateCampaign(@PathVariable("campaign-id") long promoId,
			@Valid @RequestBody CreateCampaignRequest scr) {
		return campaignService.updateCampaign(promoId, scr);
	}

	@PostMapping(path = "issuecashback/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
	@ApiOperation(value = "Upload cashbackdata", response = SaveCashbackResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public BaseResponse upload(@RequestPart("file") MultipartFile file) {
		return promocashService.upload(file);
	}

    /* API for fetching the balance of the customer - both digital wallet and cashback balance */
	@PostMapping("wallet-balance/{shukranId}")
	@ApiOperation(value = "Fetch Wallet balance - DW & Cashback", response = FetchWalletBalanceCompositeResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400,response = BaseResponse.class, message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public FetchWalletBalanceCompositeResponse fetchWalletBalance(@PathVariable("shukranId") @Size(min = 8, max = 30) String shukranId,
			@Valid @RequestBody FetchWalletBalanceRequest fwbr) {
		return promocashService.fetchWalletBalance(shukranId, fwbr);
	}
	
	/* API for fetching the monthly balance of the customer - both digital wallet and cashback balance */
	@PostMapping("monthwise-wallet-balance/{shukranId}")
	@ApiOperation(value = "Fetch Monthly Wallet balance - DW & Cashback", response = FetchMonthlyBalanceResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class,message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public FetchMonthlyBalanceResponse fetchMonthlyBalance(@PathVariable("shukranId") @Size(min = 8, max = 30) String shukranId, 
			@Valid @RequestBody FetchWalletBalanceRequest fwbr) {
		return promocashService.fetchMonthlyBalance(shukranId, fwbr);
	}
	
	/* API for fetching the cashback & DW balance without references */
	@PostMapping("wallet-balance-without-reference/{shukranId}")
	@ApiOperation(value = "Fetch Monthly Wallet balance - DW & Cashback", response = FetchBalanceWithoutReferenceResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400,response = BaseResponse.class, message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public FetchBalanceWithoutReferenceResponse fetchBalanceWithoutReference(@PathVariable("shukranId") @Size(min = 8, max = 30) String shukranId, 
			@Valid @RequestBody FetchWalletBalanceRequest fwbr) {
		return promocashService.fetchBalanceWithoutReference(shukranId, fwbr);
	}
	
	/* API to get fileupload summary */
	@PostMapping("fileupload-summary")
	@ApiOperation(value = "Get all upload summary", response = FileUploadSummaryResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class,message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public FileUploadSummaryResponse getFileUploadSummary(@Valid @RequestBody FileUploadSummaryRequest uploadSummaryRequest) {
		return promocashService.getFileUploadSummary(uploadSummaryRequest);
	}
	
	/* API to get exception report */
	@GetMapping("fileupload-exception/{fileId}")
	@ApiOperation(value = "Get all exceptions details of a fileId", response = CashbackRejectionDetailsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class,message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public CashbackRejectionDetailsResponse getFileUploadException(@PathVariable("fileId") BigDecimal  fileId) {
		return promocashService.getCashbackRejectionDetails(fileId);
	}
	
	/* API to expire the balance entries */
	@GetMapping("expire-balance-entries")
	@ApiOperation(value = "Get all exceptions details of a fileId", response = ExpireBalanceEntriesResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class,message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public ExpireBalanceEntriesResponse expireBalanceEntries() {
		 return promocashService.expireBalanceEntries();
	}

}
