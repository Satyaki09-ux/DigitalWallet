package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateDigitalWalletResponse {

	private String status;
	@JsonProperty("wallet_id")
	private String walletId;
	@JsonProperty("shukran_id")
	private String shukranId;
	@JsonProperty("balance_amount")
	private BigDecimal balanceAmount;
	@JsonProperty("mobile_no")
	private String mobileNumber;
	@JsonProperty("email_id")
	private String emailId;
	@JsonProperty("create_date")
	private LocalDateTime createDate;
	@JsonProperty("source_channel")
	private String sourceChannel;
	@JsonProperty("wallet_status")
	private String walletStatus;
	@JsonProperty("message")
	private String message;
	@JsonInclude(Include.NON_NULL)
	private List<TransactionResponse> transaction;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public String getWalletStatus() {
		return walletStatus;
	}

	public void setWalletStatus(String walletStatus) {
		this.walletStatus = walletStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<TransactionResponse> getTransaction() {
		return transaction;
	}

	public void setTransaction(List<TransactionResponse> transaction) {
		this.transaction = transaction;
	}

}
