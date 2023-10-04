package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class RedeemWalletResponse {

	@ApiModelProperty(position = 1, dataType = "String", example = "111212100621090612222512")
	@JsonProperty("wallet_id")
	private String walletId;

	@ApiModelProperty(position = 2, example = "100.13")
	@JsonProperty("wallet_balance")
	private BigDecimal balance;

	@ApiModelProperty(position = 3)
	@JsonProperty("references_redeemed")
	private List<WalletReferences> referencesRedeemed;

	@ApiModelProperty(position = 4)
	@JsonProperty("reference_reissued")
	private WalletReferences referenceReIssued;

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

	public List<WalletReferences> getReferencesRedeemed() {
		return referencesRedeemed;
	}

	public void setReferencesRedeemed(List<WalletReferences> referencesRedeemed) {
		this.referencesRedeemed = referencesRedeemed;
	}

	public WalletReferences getReferenceReIssued() {
		return referenceReIssued;
	}

	public void setReferenceReIssued(WalletReferences referenceReIssued) {
		this.referenceReIssued = referenceReIssued;
	}

}
