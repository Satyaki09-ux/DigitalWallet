package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OglobaResponse {
	@JsonProperty("card_type")
	private String cardType;
	@JsonProperty("mobile_number")
	private String mobileNumber;
	@JsonProperty("previous_balance")
	private BigDecimal previousBalance;
	@JsonProperty("balance")
	private BigDecimal balance;
	@JsonProperty("currency")
	private String currency;
	@JsonProperty("expire_date")
	private String expiryDate;
	@JsonProperty("card_number")
	private String cardNumber;
	@JsonProperty("card_status")
	private String cardStatus;
	@JsonProperty("max_reload_amount")
	private BigDecimal maxReloadAmount;
	@JsonProperty("access_token")
	private String token;
	@JsonProperty("reference_number")
	private String referenceNumber;
	@JsonProperty("ack")
	private boolean ack;
	@JsonProperty("error")
	private String error;
	public String getCardType() {
		return cardType;
	}
	public void setCardType(String cardType) {
		this.cardType = cardType;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public BigDecimal getPreviousBalance() {
		return previousBalance;
	}
	public void setPreviousBalance(BigDecimal previousBalance) {
		this.previousBalance = previousBalance;
	}
	public BigDecimal getBalance() {
		return balance;
	}
	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public BigDecimal getMaxReloadAmount() {
		return maxReloadAmount;
	}
	public void setMaxReloadAmount(BigDecimal maxReloadAmount) {
		this.maxReloadAmount = maxReloadAmount;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public boolean isAck() {
		return ack;
	}
	public void setAck(boolean ack) {
		this.ack = ack;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
}
