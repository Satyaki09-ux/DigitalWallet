package com.lmg.digitization.digital.wallet.controller;

import java.net.URISyntaxException;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lmg.digitization.cashback.response.RedeemWalletCashbackResponse;
import com.lmg.digitization.cashback.response.RevertRedeemCashbackOnInvoiceResponse;
import com.lmg.digitization.cashback.response.TransactionsResponse;
import com.lmg.digitization.cashback.service.RevertRedeemWalletCashbackService;
import com.lmg.digitization.cashback.service.TransactionsService;
import com.lmg.digitization.digital.wallet.filter.PropertyLogger;
import com.lmg.digitization.digital.wallet.request.AccountStatementRequest;
import com.lmg.digitization.digital.wallet.request.ConvertCardRequest;
import com.lmg.digitization.digital.wallet.request.CreateCouponRequest;
import com.lmg.digitization.digital.wallet.request.CreateDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.DeleteWalletRequest;
import com.lmg.digitization.digital.wallet.request.ExtendExpiryRequest;
import com.lmg.digitization.digital.wallet.request.IssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.OTPRequest;
import com.lmg.digitization.digital.wallet.request.OTPValidationRequest;
import com.lmg.digitization.digital.wallet.request.RedeemCouponRequest;
import com.lmg.digitization.digital.wallet.request.RedeemDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.RevertIssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.RevertRedeemOnInvoiceRequest;
import com.lmg.digitization.digital.wallet.request.WalletBalanceRequest;
import com.lmg.digitization.digital.wallet.request.WalletConversionRequest;
import com.lmg.digitization.digital.wallet.response.BaseResponse;
import com.lmg.digitization.digital.wallet.response.CreateCouponResponse;
import com.lmg.digitization.digital.wallet.response.CreateDigitalWalletResponse;
import com.lmg.digitization.digital.wallet.response.DcnToDwConvertResponse;
import com.lmg.digitization.digital.wallet.response.DeleteWalletResponse;
import com.lmg.digitization.digital.wallet.response.DigitalAccountStatementResponse;
import com.lmg.digitization.digital.wallet.response.EmployeeResponse;
import com.lmg.digitization.digital.wallet.response.ExtendExpiryResponse;
import com.lmg.digitization.digital.wallet.response.IssueDigitalWalletResponse;
import com.lmg.digitization.digital.wallet.response.OTPResponse;
import com.lmg.digitization.digital.wallet.response.RedeemCouponResponse;
import com.lmg.digitization.digital.wallet.response.RedeemWalletResponse;
import com.lmg.digitization.digital.wallet.response.RevertRedeemOnInvoiceResponse;
import com.lmg.digitization.digital.wallet.response.RevertRedeemResponse;
import com.lmg.digitization.digital.wallet.response.WalletBalanceResponse;
import com.lmg.digitization.digital.wallet.response.WalletTransactionsResponse;
import com.lmg.digitization.digital.wallet.service.CouponService;
import com.lmg.digitization.digital.wallet.service.DWTransactionsService;
import com.lmg.digitization.digital.wallet.service.DcnToWalletConversionService;
import com.lmg.digitization.digital.wallet.service.DigitalWalletService;
import com.lmg.digitization.digital.wallet.service.ExtendExpiryService;
import com.lmg.digitization.digital.wallet.service.OTPService;
import com.lmg.digitization.digital.wallet.service.RedeemWalletService;
import com.lmg.digitization.digital.wallet.service.RedeemWalletServiceV2;
import com.lmg.digitization.digital.wallet.service.RevertIssueWalletService;
import com.lmg.digitization.digital.wallet.service.RevertRedeemWalletService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api
public class DigitalWalletController {

	@Autowired
	private DigitalWalletService service;

	@Autowired
	private DWTransactionsService transactionsService;

	@Autowired
	private RedeemWalletService redeemService;
	
	@Autowired
	private TransactionsService transactionService;
	
	@Autowired
	private RedeemWalletServiceV2 redeemServiceV2;
	
	@Autowired
	private RevertRedeemWalletService revertRedeemService;

	@Autowired
	private RevertIssueWalletService revertIssueWalletService;

	@Autowired
	private RevertRedeemWalletCashbackService revertRedeemWalletCashbackService;
	
	@Autowired
	private OTPService otpService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private ExtendExpiryService extendExpiryService;

	@Autowired
	private DcnToWalletConversionService dcnDwConversionService;

	@Autowired
	private PropertyLogger propertyLogger;

	@PostMapping("/{shukran-id}/events/create")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet Create Service", response = CreateDigitalWalletResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public CreateDigitalWalletResponse create(
			@ApiParam(value = "shukran-id", required = true) @PathVariable("shukran-id") @Size(min = 8, max = 30) String shukranId,
			@Valid @RequestBody CreateDigitalWalletRequest request) {
		return service.createDigitalWallet(request);
	}

	@PostMapping("{shukran-id}/events/issue")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet Issue credit wallet amount Service", response = CreateDigitalWalletResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public IssueDigitalWalletResponse issue(@PathVariable("shukran-id") String shukranId,
			@Valid @RequestBody IssueDigitalWalletRequest request) {
		return service.issueDigitalWallet(request);
	}

	@PostMapping("/{shukran-id}/events/redeem")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Digital Wallet Redeem Service", response = RedeemWalletResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public RedeemWalletResponse redeemWallet(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@Valid @RequestBody RedeemDigitalWalletRequest request) {
		return redeemService.redeemDigitalWallet(shukranId, request);
	}

	@PutMapping("/{shukran-id}")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Delete Digital Wallet", response = DeleteWalletResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public DeleteWalletResponse deleteDigitalWallet(@Valid @RequestBody DeleteWalletRequest request,
			@NotBlank @Valid @PathVariable("shukran-id") String shukranId) {
		return service.deleteDigitalWallet(request, shukranId);
	}

	@PostMapping("{shukran-id}/events/revert-redeem")
	@ApiOperation(value = "Digital Wallet Revert Redeem Service", response = RevertRedeemResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public RevertRedeemOnInvoiceResponse revertRedeemedOnInvoice(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@Valid @RequestBody RevertRedeemOnInvoiceRequest request) {
		return revertRedeemService.revertRedeem(request, shukranId);
	}

	@PostMapping("{shukran-id}/events/wallet-balance")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet Balance", response = WalletBalanceResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public WalletBalanceResponse getWalletBalance(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@RequestBody WalletBalanceRequest request) {
		return service.getWalletBalance(shukranId, request);
	}
	
	@PostMapping("{shukran-id}/events/wallet-balance_v2")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
			@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
			@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet Balance Version 2", response = WalletBalanceResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public WalletBalanceResponse getWalletBalanceV2(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@RequestBody WalletBalanceRequest request) {
		return service.getWalletBalanceV2(shukranId, request);
	}

	@PostMapping("/{shukran-id}/events/redeem_v2")
	@ApiOperation(value = "Digital Wallet Redeem Service", response = RedeemWalletResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public RedeemWalletCashbackResponse redeemWalletCashback(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@Valid @RequestBody RedeemDigitalWalletRequest request) {
		return redeemServiceV2.redeemWalletCashback(shukranId, request);
	}
	
	@PostMapping("{shukran-id}/events/revert-redeem-v2")
	@ApiOperation(value = "Digital Wallet Revert Redeem Service", response = RevertRedeemResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public RevertRedeemCashbackOnInvoiceResponse revertRedeemedForAllOnInvoice(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@Valid @RequestBody RevertRedeemOnInvoiceRequest request) {
		return revertRedeemWalletCashbackService.revertRedeem(request, shukranId);
	}

	@GetMapping("/{shukran-id}/transactions")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet transactions history", response = WalletTransactionsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public WalletTransactionsResponse getWalletBalance(
			@ApiParam(value = "shukran-id", required = true) @PathVariable("shukran-id") @Size(min = 8, max = 30) String shukranId,
			@RequestParam(value = "currency", required = true, defaultValue = "AED") @ApiParam(value = "shukran currency", defaultValue = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP", required = true) String currency,
			@ApiParam(value = "no of transaction days", required = false) @RequestParam(value = "noofdays", required = false, defaultValue = "30") @Min(1) @Max(365) Integer noofdays,
			@RequestParam(value = "pagenumber", required = false, defaultValue = "1") @Min(1) @Max(500) Integer pagenumber,
			@RequestParam(value = "size", required = false, defaultValue = "5") @Min(1) @Max(500) Integer size,
			@RequestParam(value = "status", required = false) @ApiParam(name = "status", allowMultiple = true, allowableValues = "ISSUED, REDEEMED, EXTENDED, EXPIRED, CONVERTED, REVERT_REDEEMED, REVERT_ISSUED") List<String> status,
			@RequestParam(value = "concept", required = false) @ApiParam(name = "concept", allowMultiple = true) List<String> concept,
			@RequestParam(value = "source", required = false) @ApiParam(name = "source", allowMultiple = true, allowableValues = "ORPOS, HYBRIS, OMS, SSM, SCO, SSCO, SHKURAN") List<String> source) {
		return transactionsService.searchTransactions(shukranId, currency, noofdays, pagenumber, size, status, concept,
				source);
	}
	
	@GetMapping("/{shukran-id}/transactions_v2")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet transactions history", response = WalletTransactionsResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public TransactionsResponse getAllTrasnactions(
			@ApiParam(value = "shukran-id", required = true) @PathVariable("shukran-id") @Size(min = 8, max = 30) String shukranId,
			@RequestParam(value = "currency", required = true, defaultValue = "AED") @ApiParam(value = "shukran currency", defaultValue = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP", required = true) String currency,
			@ApiParam(value = "no of transaction days", required = false) @RequestParam(value = "noofdays", required = false, defaultValue = "30") @Min(1) @Max(365) Integer noofdays,
			@RequestParam(value = "pagenumber", required = false, defaultValue = "1") @Min(1) @Max(500) Integer pagenumber,
			@RequestParam(value = "size", required = false, defaultValue = "5") @Min(1) @Max(500) Integer size,
			@RequestParam(value = "status", required = false) @ApiParam(name = "status", allowMultiple = true, allowableValues = "ISSUED, REDEEMED, EXTENDED, EXPIRED, CONVERTED, REVERT_REDEEMED, REVERT_ISSUED") List<String> status,
			@RequestParam(value = "concept", required = false) @ApiParam(name = "concept", allowMultiple = true) List<String> concept,
			@RequestParam(value = "source", required = false) @ApiParam(name = "source", allowMultiple = true, allowableValues = "ORPOS, HYBRIS, OMS, SSM, SCO, SSCO, SHKURAN") List<String> source) {
		return transactionService.searchTransactions(shukranId, currency, noofdays, pagenumber, size, status, concept,
				source);
	}
	

	@PutMapping("{shukran-id}/events/extend-expiry")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet Extend Expiry", response = ExtendExpiryResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public ExtendExpiryResponse extendExpiry(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@Valid @RequestBody ExtendExpiryRequest request) {
		return extendExpiryService.extendExpiry(request);
	}

	@PostMapping("digital-credit-notes-to-digital-wallet/{shukran-id}/dcn/{dcn-id}")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Convert Physical/ Digital Credit Note to Digital Wallet", response = DcnToDwConvertResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public DcnToDwConvertResponse convertDCNToDW(
			@PathVariable("shukran-id") @ApiParam(name = "shukran-id", type = "String", value = "shukran-id", example = "12349342093488", required = true) String shukranId,
			@ApiParam(name = "dcn-id", type = "String", value = "dcn-id", example = "12349342093488", required = true) @PathVariable("dcn-id") String dcnId,
			@Valid @RequestBody WalletConversionRequest request) {
		return dcnDwConversionService.convertDCNToDW(shukranId, dcnId, request);
	}

	@PostMapping("{shukran-id}/events/cancel-issue")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet Revert Issue Service", response = RevertIssueDigitalWalletRequest.class)
	@ApiResponses(value = { @ApiResponse(code = 400, message = "Undefined Request"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public RevertRedeemOnInvoiceResponse revertIssueDigitaWallet(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@Valid @RequestBody RevertIssueDigitalWalletRequest request) {
		return revertIssueWalletService.revertIssue(request, shukranId);
	}

	@PostMapping("{shukran-id}/otp/generate")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "OTP Generation", response = OTPResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Generate OTP Error"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public OTPResponse generateOTP(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@RequestBody OTPRequest request) {
		return otpService.generateOTP(shukranId, request);
	}

	@PostMapping("/{shukran-id}/otp/validate")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "OTP Validation", response = OTPResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Validate OTP Error"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public OTPResponse validateOTP(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@Valid @RequestBody OTPValidationRequest request) {
		return otpService.validateOTP(shukranId, request);
	}

	@PostMapping("/{shukran-id}/otp/resend")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "OTP Recreation", response = OTPResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Recreate OTP Error"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public OTPResponse resendOTP(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@RequestBody OTPRequest request) {
		return otpService.generateOTP(shukranId, request);
	}

	public String getPropValues() {
		return propertyLogger.getPropValues();
	}

	@PostMapping("/{store-id}/events/create/couponmgmt")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Coupon Create Service", response = CreateCouponResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public CreateCouponResponse create(
			@ApiParam(name = "store-id", type = "String", value = "Store Id", example = "123493", required = true) @PathVariable("store-id") String storeId,
			@Valid @RequestBody CreateCouponRequest request) {
		return couponService.createCoupon(storeId, request);
	}

	@PostMapping("/{coupon-code}/events/validate/couponmgmt")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Coupon Validation Service", response = RedeemCouponResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public RedeemCouponResponse validate(
			@ApiParam(name = "coupon-code", type = "String", value = "Coupon Code", example = "123493", required = true) @PathVariable("coupon-code") String couponCode,
			@Valid @RequestBody RedeemCouponRequest request) {
		return couponService.validateCoupon(couponCode, request);
	}

	@PostMapping("/{coupon-code}/events/redeem/couponmgmt")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Coupon Redeem Service", response = RedeemCouponResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public RedeemCouponResponse redeem(
			@ApiParam(name = "coupon-code", type = "String", value = "Coupon Code", example = "123493", required = true) @PathVariable("coupon-code") String couponCode,
			@Valid @RequestBody RedeemCouponRequest request) {
		return couponService.redeemCoupon(couponCode, request);
	}

	@GetMapping("validateemployee/{employee-code}/couponmgmt")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet transactions history", response = EmployeeResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public EmployeeResponse getWalletBalance(
			@ApiParam(value = "employee-code", required = true) @PathVariable("employee-code") @Size(min = 8, max = 30) String employeeCode) {
		return couponService.validateEmployee(employeeCode);
	}

	@PostMapping("{shukran-id}/events/convert-card")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Wallet Issue credit wallet amount Service", response = CreateDigitalWalletResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public IssueDigitalWalletResponse convert(@PathVariable("shukran-id") String shukranId,
			@Valid @RequestBody ConvertCardRequest request) throws URISyntaxException {
		return service.convertCardsDigitalWallet(shukranId, request);
	}
	
	@PostMapping("{shukran-id}/events/account-statement")
	@Consumes(MediaType.APPLICATION_JSON_VALUE)
	@Produces(MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Access Token", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "Bearer access_token"),
		@ApiImplicitParam(name = "x-ibm-client-id", value = "API gateway client-id", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "qw113exsssz44"),
		@ApiImplicitParam(name = "UniqueReferenceCode", value = "URC random string for tracing Individual request", required = true, allowEmptyValue = false, paramType = "header", dataTypeClass = String.class, example = "123456789-121") })
	@ApiOperation(value = "Digital Account Statement", response = DigitalAccountStatementResponse.class)
	@ApiResponses(value = { @ApiResponse(code = 400, response = BaseResponse.class, message = "Error code and Message"),
			@ApiResponse(code = 500, message = "Unexpected Error on Server"),
			@ApiResponse(code = 404, message = "URL not found"),
			@ApiResponse(code = 200, message = "Successfully Executed"),
			@ApiResponse(code = 401, message = "Unauthorized") })
	public DigitalAccountStatementResponse getStatement(
			@ApiParam(name = "shukran-id", type = "String", value = "Shukran Id of the User", example = "12349342093488", required = true) @PathVariable("shukran-id") String shukranId,
			@RequestBody AccountStatementRequest request) {
		return service.getDigitalAccountStatement(shukranId, request);
	}

}