package com.lmg.digitization.digital.wallet.request;



import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OglobaRedeemRequest {
	@JsonProperty("merchant_id")
	private String merchantId;
	@JsonProperty("terminal_id")
	private String terminalId;
	@JsonProperty("cashier_id")
	private String cashierId;
	@JsonProperty("transaction_number")
	private String transactionNumber;
	@JsonProperty("gen_code")
	private String genCode;
	@JsonProperty("track2Data")
	private String track2Data;
	@JsonProperty("amount")
	private BigDecimal amount;
	@JsonProperty("note")
	private String note;
	@JsonProperty("reason")
	private String reason;
	@JsonProperty("validity_date")
	private String validityDate;
	@JsonProperty("mobile_number")
	private String mobieNumber;
	@JsonProperty("reference_number")
	private String referenceNumber;
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public String getCashierId() {
		return cashierId;
	}
	public void setCashierId(String cashierId) {
		this.cashierId = cashierId;
	}
	public String getTransactionNumber() {
		return transactionNumber;
	}
	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public String getGenCode() {
		return genCode;
	}
	public void setGenCode(String genCode) {
		this.genCode = genCode;
	}
	public String getTrack2Data() {
		return track2Data;
	}
	public void setTrack2Data(String track2Data) {
		this.track2Data = track2Data;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getValidityDate() {
		return validityDate;
	}
	public void setValidityDate(String validityDate) {
		this.validityDate = validityDate;
	}
	public String getMobieNumber() {
		return mobieNumber;
	}
	public void setMobieNumber(String mobieNumber) {
		this.mobieNumber = mobieNumber;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	
	
	
	
}
