package com.lmg.digitization.cashback.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class SaveCashbackRequest {

	@Positive
	@ApiModelProperty
	@NotNull(message = "Amount must not be less than 0.0")
	@JsonProperty("amount")
	private BigDecimal amount;

	@JsonProperty("applicability")
	private String applicability;
	
	@ApiModelProperty( allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|SAR|BHD|OMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Size(min = 3, max = 3, message = "Currency should always be 3 char")
	@JsonProperty("base_currency")
	private String baseCurrency;

	@ApiModelProperty
	@Size(min = 10, max = 200, message = "Sms text must be atleast 10 char")
	@JsonProperty("sms_text")
	private String smsText;

	@ApiModelProperty
	@Size(min = 10, max = 200, message = "Push text must be atleast 10 char")
	@JsonProperty("push_text")
	private String pushText;
	
	@ApiModelProperty
	@NotNull
	@JsonProperty("customer_name")
	private String customerName;

	@ApiModelProperty
	@JsonProperty("email_id")
	private String customerEmail;
	
	@ApiModelProperty
	@JsonProperty("mobile_number")
	private String customerMobile;
	

	@ApiModelProperty
	@Size(max = 2, message = "Issusing-Concept cannot be more than 2 char")
	@JsonProperty("issuing_concept")
	private String issuingConcept;

	@ApiModelProperty
	@Size(max = 2, message = "Redeemable-Concept cannot be more than 2 char")
	@JsonProperty("redeemable_concept")
	private String redeemableConcept;

	@ApiModelProperty( allowableValues = "HYBRIS, ORPOS")
	@Pattern(regexp = "HYBRIS|ORPOS", flags = Pattern.Flag.CASE_INSENSITIVE)
	@JsonProperty("source_app")
	private String sourceApp;
	
	@Pattern(regexp = "[1-7]", flags = Pattern.Flag.CASE_INSENSITIVE)
	@ApiModelProperty(allowableValues = "UAE:1,KSA:2,Bahrain:3,Oman:4,Kuwait:5,Qatar:6,Egypt:7")
	@NotNull(message = "Territory Code cannot be blank")
	@JsonProperty("territory")
	private String territory;

	@JsonProperty("expiry_period")
	private int expiryPeriod;

	@JsonProperty("expiry_date")
	private LocalDate expiryDate;

	@JsonProperty("transaction_id")
	private String transactionId;
	
	@JsonProperty("redeemable_start_date")
	private LocalDate redeemableStartDate;

	@ApiModelProperty
	@Size(min = 10, message = "Order-Reference-No must be atleast 10 char")
	@JsonProperty("order_reference_number")
	private String orderReferenceNumber;

	@ApiModelProperty
	@Size(min = 10, message = "Campaign name must be atleast 10 char")
	@JsonProperty("campaign_name")
	private String campaignName;

	private BigDecimal fileId=new BigDecimal("0");
	
	@JsonProperty("version")
	private String version;

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public String getPushText() {
		return pushText;
	}

	public void setPushText(String pushText) {
		this.pushText = pushText;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	

	public String getIssuingConcept() {
		return issuingConcept;
	}

	public void setIssuingConcept(String issuingConcept) {
		this.issuingConcept = issuingConcept;
	}

	public String getRedeemableConcept() {
		return redeemableConcept;
	}

	public void setRedeemableConcept(String redeemableConcept) {
		this.redeemableConcept = redeemableConcept;
	}

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public int getExpiryPeriod() {
		return expiryPeriod;
	}

	public void setExpiryPeriod(int expiryPeriod) {
		this.expiryPeriod = expiryPeriod;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getRedeemableStartDate() {
		return redeemableStartDate;
	}

	public void setRedeemableStartDate(LocalDate redeemableStartDate) {
		this.redeemableStartDate = redeemableStartDate;
	}

	public String getOrderReferenceNumber() {
		return orderReferenceNumber;
	}

	public void setOrderReferenceNumber(String orderReferenceNumber) {
		this.orderReferenceNumber = orderReferenceNumber;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public BigDecimal getFileId() {
		return fileId;
	}

	public void setFileId(BigDecimal fileId) {
		this.fileId = fileId;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getApplicability() {
		return applicability;
	}

	public void setApplicability(String applicability) {
		this.applicability = applicability;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	
	
	
}
