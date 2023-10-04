package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RevertRedeemResponse {

	@JsonProperty("wallet_id")
	private String walletId;
	
	@JsonProperty("balance_amount")
	private BigDecimal balanceAmount;
	
	@JsonProperty("Shukran_Id")
	private String shukranId;
	
	@JsonProperty("status")
	private String status;

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

