package com.lmg.digitization.digital.wallet.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.cashback.response.CashbackLedgerResponse;

import io.swagger.annotations.ApiModelProperty;

public class DigitalAccountStatementResponse {
	
	@ApiModelProperty(position = 1, dataType = "String", example = "111212100621090612222512")
	@JsonProperty("shukran_id")
	private String shukranId;
	
	@JsonProperty("statementOfCashbackAccount")
	private List<CashbackLedgerResponse> statementOfCashbackAccount;
	
	@JsonProperty("statementOfDigitalWallet")
	private List<DigitizationLedgerResponse> statementOfDigitalWallet;
	
	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public List<CashbackLedgerResponse> getStatementOfCashbackAccount() {
		return statementOfCashbackAccount;
	}

	public void setStatementOfCashbackAccount(List<CashbackLedgerResponse> statementOfCashbackAccount) {
		this.statementOfCashbackAccount = statementOfCashbackAccount;
	}

	public List<DigitizationLedgerResponse> getStatementOfDigitalWallet() {
		return statementOfDigitalWallet;
	}

	public void setStatementOfDigitalWallet(List<DigitizationLedgerResponse> statementOfDigitalWallet) {
		this.statementOfDigitalWallet = statementOfDigitalWallet;
	}
}
