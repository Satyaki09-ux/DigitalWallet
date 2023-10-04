package com.lmg.digitization.cashback.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CashbackNotificationDetailsModel {
	
	@JsonProperty("customer_name")
	private String customerName;
	
	@JsonProperty("cashback_id")
	private String cashbackId;
	
	@JsonProperty("amount")
	private Double amount;
	
	@JsonProperty("balance_amount")
	private Double balanceAmount;
	
	@JsonProperty("expiry_date")
	private LocalDate expiryDate;
	
	@JsonProperty("notification_date")
	private LocalDate notificationDate;
	
	@JsonProperty("notification_title")
	private String notificationTitle;
	
	@JsonProperty("issuing_concept")
	private String issuingConcept;
	
	@JsonProperty("redeemable_concept")
	private String redeemableConcept;
	
	@JsonProperty("campaign_name")
	private String campaignName;
	
	@JsonProperty("order_reference_number")
	private String orderReferenceNumber;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("language")
	private String language;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCashbackId() {
		return cashbackId;
	}

	public void setCashbackId(String cashbackId) {
		this.cashbackId = cashbackId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getNotificationDate() {
		return notificationDate;
	}

	public void setNotificationDate(LocalDate notificationDate) {
		this.notificationDate = notificationDate;
	}

	public String getNotificationTitle() {
		return notificationTitle;
	}

	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
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

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getOrderReferenceNumber() {
		return orderReferenceNumber;
	}

	public void setOrderReferenceNumber(String orderReferenceNumber) {
		this.orderReferenceNumber = orderReferenceNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
	
	
}
