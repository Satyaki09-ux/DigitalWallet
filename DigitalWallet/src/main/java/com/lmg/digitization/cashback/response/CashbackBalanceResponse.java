package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.response.WalletReferences;

import io.swagger.annotations.ApiModelProperty;

public class CashbackBalanceResponse {
	

	@ApiModelProperty(position = 1, dataType = "String", example = "111212100621090612222512")
	@JsonProperty("cashback_id")
	private String cashbackId;

	@ApiModelProperty(position = 2, example = "100.13")
	@JsonProperty("cashback_balance")
	private BigDecimal cashbackBalance;

	@ApiModelProperty(position = 3, example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@JsonProperty("currency")
	private String currency;

	@ApiModelProperty(position = 4, example = "1")
	@JsonProperty("version")
	private String version;
	
	public String getCashbackId() {
		return cashbackId;
	}

	public void setCashbackId(String cashbackId) {
		this.cashbackId = cashbackId;
	}

	public BigDecimal getCashbackBalance() {
		return cashbackBalance;
	}

	public void setCashbackBalance(BigDecimal cashbackBalance) {
		this.cashbackBalance = cashbackBalance;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
