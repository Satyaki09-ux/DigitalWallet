package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class FetchMonthlyBalanceResponse {
	
	@ApiModelProperty(position = 1)
	@JsonProperty("shukran_id")
	private String shukranId;
	
	@ApiModelProperty(position = 2, example = "100.13")
	@JsonProperty("cashback_balance")
	private BigDecimal cashbackBalance;
	
	@ApiModelProperty(position = 3, example = "100.13")
	@JsonProperty("wallet_balance")
	private BigDecimal walletBalance;
	
	@ApiModelProperty(position = 4)
	@JsonProperty("monthly_references")
	private List<MonthlyReferencesResponse> monthlyReferences;

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

	public List<MonthlyReferencesResponse> getMonthlyReferences() {
		return monthlyReferences;
	}

	public void setMonthlyReferences(List<MonthlyReferencesResponse> monthlyReferences) {
		this.monthlyReferences = monthlyReferences;
	}
}
