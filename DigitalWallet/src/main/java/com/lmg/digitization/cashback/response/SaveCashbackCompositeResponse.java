package com.lmg.digitization.cashback.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.response.IssueDigitalWalletResponse;

public class SaveCashbackCompositeResponse {
	@JsonProperty("cashback")
	private SaveCashbackResponse cashback;
	@JsonProperty("digital_wallet")
	private IssueDigitalWalletResponse digitalWallet;
	
	public SaveCashbackResponse getCashback() {
		return this.cashback;
	}
	
	public void setCashback(SaveCashbackResponse cashback) {
		this.cashback = cashback;
	}

	public IssueDigitalWalletResponse getDigitalWallet() {
		return digitalWallet;
	}

	public void setDigitalWallet(IssueDigitalWalletResponse digitalWallet) {
		this.digitalWallet = digitalWallet;
	}
	
	
}
