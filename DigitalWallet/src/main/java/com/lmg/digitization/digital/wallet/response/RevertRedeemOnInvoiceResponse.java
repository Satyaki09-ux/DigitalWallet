package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class RevertRedeemOnInvoiceResponse {

	public RevertRedeemOnInvoiceResponse(String walletId, BigDecimal balance, String currency, String shukranId, String status) {
		super();
		this.walletId = walletId;
		this.balance = balance;
		this.currency = currency;
		this.shukranId = shukranId;
		this.status = status;
	}

	public RevertRedeemOnInvoiceResponse() {
		super();
	}

	@ApiModelProperty(position = 1, dataType = "String", example = "1112121006215512")
	@JsonProperty("wallet_id")
	private String walletId;

	@ApiModelProperty(position = 2, example = "100.13")
	@JsonProperty("wallet_balance")
	private BigDecimal balance;

	@ApiModelProperty(position = 2, example = "AED")
	@JsonProperty("currency")
	private String currency;

	@JsonProperty("shukran_id")
	private String shukranId;

	@JsonProperty("status")
	private String status;

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
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
