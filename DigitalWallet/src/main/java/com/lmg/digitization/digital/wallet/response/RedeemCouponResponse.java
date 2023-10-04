package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RedeemCouponResponse extends CreateCouponResponse{
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("balance")
	private BigDecimal balance;


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}
	
}
