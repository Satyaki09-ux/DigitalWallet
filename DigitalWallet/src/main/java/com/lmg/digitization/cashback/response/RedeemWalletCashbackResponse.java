package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.response.RedeemWalletResponse;

import io.swagger.annotations.ApiModelProperty;

public class RedeemWalletCashbackResponse {
	
	@ApiModelProperty(position = 1, example = "100.13")
	@JsonProperty("total_balance")
	private BigDecimal totalBalance;
	
	@ApiModelProperty(position = 2, example = "AED")
	@JsonProperty("base_currency")
	private String baseCurrency;
	 
	@JsonProperty("digital_wallet")
	private RedeemWalletResponse redeemWalletResponse;
	
	@JsonProperty("cashback")
	private RedeemCashbackResponse redeemCashbackResponse;

	public BigDecimal getTotalBalance() {
		return totalBalance;
	}

	public void setTotalBalance(BigDecimal totalBalance) {
		this.totalBalance = totalBalance;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public RedeemWalletResponse getRedeemWalletResponse() {
		return redeemWalletResponse;
	}

	public void setRedeemWalletResponse(RedeemWalletResponse redeemWalletResponse) {
		this.redeemWalletResponse = redeemWalletResponse;
	}

	public RedeemCashbackResponse getRedeemCashbackResponse() {
		return redeemCashbackResponse;
	}

	public void setRedeemCashbackResponse(RedeemCashbackResponse redeemCashbackResponse) {
		this.redeemCashbackResponse = redeemCashbackResponse;
	}
	
}
