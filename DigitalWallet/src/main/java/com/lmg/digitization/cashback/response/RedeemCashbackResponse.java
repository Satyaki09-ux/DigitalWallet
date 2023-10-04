package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


import io.swagger.annotations.ApiModelProperty;

public class RedeemCashbackResponse {

	@ApiModelProperty(position = 1, dataType = "String", example = "111212100621090612222512")
	@JsonProperty("cashback_id")
	private String cashbackId;

	@ApiModelProperty(position = 2, example = "100.13")
	@JsonProperty("cashback_balance")
	private BigDecimal balance;

	@ApiModelProperty(position = 3)
	@JsonProperty("references_redeemed")
	private List<CashbackReferencesResponse> referencesRedeemed;

	@ApiModelProperty(position = 4)
	@JsonProperty("reference_reissued")
	private CashbackReferencesResponse referenceReIssued;

	public String getCashbackId() {
		return cashbackId;
	}

	public void setCashbackId(String cashbackId) {
		this.cashbackId = cashbackId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public List<CashbackReferencesResponse> getReferencesRedeemed() {
		return referencesRedeemed;
	}

	public void setReferencesRedeemed(List<CashbackReferencesResponse> referencesRedeemed) {
		this.referencesRedeemed = referencesRedeemed;
	}

	public CashbackReferencesResponse getReferenceReIssued() {
		return referenceReIssued;
	}

	public void setReferenceReIssued(CashbackReferencesResponse referenceReIssued) {
		this.referenceReIssued = referenceReIssued;
	}

	
}
