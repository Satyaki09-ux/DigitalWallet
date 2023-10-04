package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class FetchBalanceWithoutReferenceResponse {
	
	@ApiModelProperty(position = 1)
	@JsonProperty("shukran_id")
	private String shukranId;
	
	@ApiModelProperty(position = 2, example = "100.13")
	@JsonProperty("cashback_balance")
	private BigDecimal cashbackBalance;
	
	@ApiModelProperty(position = 3, example = "100.13")
	@JsonProperty("wallet_balance")
	private BigDecimal walletBalance;
	
	@ApiModelProperty(position = 4, example = "100.13")
	@JsonProperty("total_balance")
	private BigDecimal totalBalance;

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public BigDecimal getCashbackBalance() {
		return cashbackBalance;
	}

	public void setCashbackBalance(BigDecimal cashbackBalance) {
		this.cashbackBalance = cashbackBalance;
	}

	public BigDecimal getWalletBalance() {
		return walletBalance;
	}

	public void setWalletBalance(BigDecimal walletBalance) {
		this.walletBalance = walletBalance;
	}

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

}
