package com.lmg.digitization.digital.wallet.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotifyRequest {

	@JsonProperty("username")
	private String userName;

	@JsonProperty("credit_note_number")
	private String creditNoteNumber;

	@JsonProperty("redeemed_amount")
	private String redeemedAmount;

	@JsonProperty("available_amount")
	private String availableAmount;

	@JsonProperty("tender_amount")
	private String tenderAmount;

	@JsonProperty("reverted_amount")
	private String revertedAmount;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("business_date")
	private String businessDate;

	@JsonProperty("store_id")
	private String storeId;

	@JsonProperty("store_name")
	private String storeName;

	@JsonProperty("territory")
	private String territory;

	@JsonProperty("expiry_date")
	private String expiryDate;

	@JsonProperty("wallet_id")
	private String walletId;

	@JsonProperty("mobile_no")
	private String mobileNo;

	@JsonProperty("email_id")
	private String emailId;

	@JsonProperty("created_date")
	private String createdDate;

	private String language ="ENGLISH";

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCreditNoteNumber() {
		return creditNoteNumber;
	}

	public void setCreditNoteNumber(String creditNoteNumber) {
		this.creditNoteNumber = creditNoteNumber;
	}

	public String getRedeemedAmount() {
		return redeemedAmount;
	}

	public void setRedeemedAmount(String redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

	public String getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}

	public String getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(String tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public String getRevertedAmount() {
		return revertedAmount;
	}

	public void setRevertedAmount(String revertedAmount) {
		this.revertedAmount = revertedAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}



}
