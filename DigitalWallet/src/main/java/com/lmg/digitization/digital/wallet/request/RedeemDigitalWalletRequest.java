package com.lmg.digitization.digital.wallet.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class RedeemDigitalWalletRequest {

	@ApiModelProperty(position = 1, example = "34.55")
	@Min(value = 0, message = "Amount must be greater than Zero")
	@JsonProperty("redeem_amount")
	private BigDecimal redeemAmount;

	@ApiModelProperty(position = 2, example = "2021-09-02", notes = "ISO Date format")
	@NotNull
	@JsonProperty("business_date")
	private LocalDate businessDate;

	@ApiModelProperty(position = 2, example = "AED")
	@NotNull
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@NotBlank(message = " base_currency must not be empty")
	@JsonProperty("base_currency")
	private String currency;

	@ApiModelProperty(position = 3, example = "ORPOS", allowableValues = "ORPOS, HYBRIS, OMS, SSM, SCO, SSCO, SHUKRAN")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("source")
	private String source;

	@ApiModelProperty(position = 4, example = "21006")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("store_id")
	private String storeId;

	@ApiModelProperty(position = 5, example = "4", allowableValues = "ShoeMart:2,HomeCentre:4,Sarah:26,BabyShop:1,Splash:3,HomeBox:29,Max:6,Centrepoint:35, CentrePoint:21,SportsOne:27,Lifestyle:5,ShoeExpress:17,Lipsy:25")
	@NotNull
	@NotEmpty(message = "concept must not be empty")
	@JsonProperty("concept")
	private String concept;

	@ApiModelProperty(position = 6, example = "21006601005620210908")
	@NotNull
	@JsonProperty("invoiceNumber")
	private String invoiceNumber;

	@ApiModelProperty(position = 7, example = "6601005620210908")
	@JsonProperty("transaction_id")
	private String transactionId;

	@ApiModelProperty(position = 8, example = "1")
	@JsonProperty("version")
	private Long version;

	@ApiModelProperty(position = 8, example = "HYBRIS", notes = "HYBRIS/ Any HRMS id of the cashier")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("operator_id")
	private String operatorId;

	@ApiModelProperty(position = 9, example = "Redeem", notes = "As of now only redeem we are going to have OTP")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("otp_type")
	private String otpType;

	@ApiModelProperty(position = 10, example = "567423")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("otp_value")
	private String otpValue;

	@ApiModelProperty(position = 14, example = "ENGLISH", allowableValues = "ENGLISH, ARABIC")
	private String language = "ENGLISH";

	public BigDecimal getRedeemAmount() {
		return redeemAmount;
	}

	public void setRedeemAmount(BigDecimal redeemAmount) {
		this.redeemAmount = redeemAmount;
	}

	public LocalDate getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(LocalDate businessDate) {
		this.businessDate = businessDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getOtpType() {
		return otpType;
	}

	public void setOtpType(String otpType) {
		this.otpType = otpType;
	}

	public String getOtpValue() {
		return otpValue;
	}

	public void setOtpValue(String otpValue) {
		this.otpValue = otpValue;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
