package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SaveCashbackResponse {

	@JsonProperty("message")
	private String message;

	@JsonProperty("cashback_id")
	private String cashbackId;

	@JsonProperty("balance")
	private BigDecimal balanceAmount;
	
	@JsonProperty("currency")
	private String baseCurrency;
	
	@JsonProperty("sms_text")
	private String smsText;

	@JsonProperty("push_text")
	private String pushText;

	@JsonProperty("customer_name")
	private String customerName;

	@JsonProperty("email_id")
	private String customerEmail;

	@JsonProperty("mobile_number")
	private String customerMobile;

	@JsonProperty("issuing_concept")
	private String issuingConcept;

	@JsonProperty("reedemable_concept")
	private String redeemableConcept;

	@JsonProperty("source_app")
	private String sourceApp;

	@JsonProperty("territory")
	private String territory;
	
	@JsonProperty("expiry_period")
	private int expiryPeriod;

	@JsonProperty("expiry_date")
	private LocalDate expiryDate;

	@JsonProperty("reedemable_start_date")
	private String redeemableStartDate;

	@JsonProperty("order_reference_date")
	private String orderReferenceNo;

	@JsonProperty("campaign_name")
	private String campaignName;

	@JsonProperty("version")
	private String version;
	
	@JsonProperty("file_id")
	private BigDecimal fileId;
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public BigDecimal getFileId() {
		return fileId;
	}

	public void setFileId(BigDecimal fileId) {
		this.fileId = fileId;
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


	public String getRedeemableConcept() {
		return redeemableConcept;
	}

	public void setRedeemableConcept(String reedemableConcept) {
		this.redeemableConcept = reedemableConcept;
	}

	public int getExpiryPeriod() {
		return expiryPeriod;
	}

	public void setExpiryPeriod(int expiryPeriod) {
		this.expiryPeriod = expiryPeriod;
	}

	

	public String getRedeemableStartDate() {
		return redeemableStartDate;
	}

	public void setRedeemableStartDate(String redeemableStartDate) {
		this.redeemableStartDate = redeemableStartDate;
	}

	public String getOrderReferenceNo() {
		return orderReferenceNo;
	}

	public void setOrderReferenceNo(String orderReferenceNo) {
		this.orderReferenceNo = orderReferenceNo;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getCashbackId() {
		return cashbackId;
	}

	public void setCashbackId(String cashbackId) {
		this.cashbackId = cashbackId;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getIssuingConcept() {
		return issuingConcept;
	}

	public void setIssuingConcept(String issuingConcept) {
		this.issuingConcept = issuingConcept;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

}
