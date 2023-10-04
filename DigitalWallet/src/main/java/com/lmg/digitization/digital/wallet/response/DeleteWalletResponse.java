package com.lmg.digitization.digital.wallet.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteWalletResponse {
	
	@JsonProperty("shukran_id")
	private String shukranId;
	
	@JsonProperty("wallet_status")
	private String walletStatus;
	
	@JsonProperty("wallet_id")
	private String walletId;
	
	@JsonProperty("delete_date")
	private LocalDateTime deleteDate;
	
	@JsonProperty("email_id")
	private String emailId;
	
	@JsonProperty("mobile_no")
	private String mobileNo;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("remarks")
	private String remarks;

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getWalletStatus() {
		return walletStatus;
	}

	public void setWalletStatus(String walletStatus) {
		this.walletStatus = walletStatus;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public LocalDateTime getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(LocalDateTime deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
