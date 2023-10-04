package com.lmg.digitization.cashback.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.request.IssueDigitalWalletRequest;

public class SaveCashbackRequestComposite {
	@JsonProperty("cashback")
	private SaveCashbackRequest cashback;
	@JsonProperty("digital_wallet")
	private IssueDigitalWalletRequest digitalWallet;
	
	public SaveCashbackRequest getCashback() {
		return cashback;
	}
	
	public void setCashback(SaveCashbackRequest cashback) {
		this.cashback = cashback;
	}

	public IssueDigitalWalletRequest getDigitalWallet() {
		return digitalWallet;
	}

	public void setDigitalWallet(IssueDigitalWalletRequest digitalWallet) {
		this.digitalWallet = digitalWallet;
	}
	
	
}
