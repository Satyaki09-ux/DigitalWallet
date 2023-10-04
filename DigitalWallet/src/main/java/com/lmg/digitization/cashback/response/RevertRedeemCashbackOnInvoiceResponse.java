package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.response.RevertRedeemOnInvoiceResponse;

import io.swagger.annotations.ApiModelProperty;

public class RevertRedeemCashbackOnInvoiceResponse {

	private RevertRedeemOnInvoiceResponse walletRevertRedeem;
	private RevertCashbackRedeemOnInvoiceResponse cashbackRevertRedeem;
	public RevertRedeemOnInvoiceResponse getWalletRevertRedeem() {
		return walletRevertRedeem;
	}
	public void setWalletRevertRedeem(RevertRedeemOnInvoiceResponse walletRevertRedeem) {
		this.walletRevertRedeem = walletRevertRedeem;
	}
	public RevertCashbackRedeemOnInvoiceResponse getCashbackRevertRedeem() {
		return cashbackRevertRedeem;
	}
	public void setCashbackRevertRedeem(RevertCashbackRedeemOnInvoiceResponse cashbackRevertRedeem) {
		this.cashbackRevertRedeem = cashbackRevertRedeem;
	}
	
	
}
