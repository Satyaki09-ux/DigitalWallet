package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.response.WalletBalanceResponse;

public class FetchWalletBalanceCompositeResponse {
	
	@JsonProperty("cashback")
	private List<CashbackBalanceConceptWiseResponse> cashback;
	
	@JsonProperty("digital_wallet")
	private WalletBalanceResponse digitalWallet;
	
	@JsonProperty("balance_within")
	private BigDecimal balanceWithin;
	
	@JsonProperty("total_balance")
	private BigDecimal totalBalance;
	
	@JsonProperty("base_currency")
	private String baseCurrency;
	
	public List<CashbackBalanceConceptWiseResponse> getCashback() {
		return cashback;
	}
	
	public void setCashback(List<CashbackBalanceConceptWiseResponse> cashback) {
		this.cashback = cashback;
	}
	
	public WalletBalanceResponse getDigitalWallet() {
		return digitalWallet;
	}
	
	public void setDigitalWallet(WalletBalanceResponse digitalWallet) {
		this.digitalWallet = digitalWallet;
	}
	
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

	public BigDecimal getBalanceWithin() {
		return balanceWithin;
	}

	public void setBalanceWithin(BigDecimal balanceWithin) {
		this.balanceWithin = balanceWithin;
	}
	
	
}
