package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class WalletReferences {

	@ApiModelProperty(position = 1, dataType = "String", example = "1112121006210")
	@JsonProperty("wallet_reference_id")
	private String walletRefenceId;

	@ApiModelProperty(position = 2, example = "2021-09-02", notes = "ISO Date format")
	@JsonProperty("expiration_date")
	private LocalDate expirationDate;

	@ApiModelProperty(position = 3, example = "50.01")
	@JsonProperty("amount")
	private BigDecimal balanceAmount;

	public String getWalletRefenceId() {
		return walletRefenceId;
	}

	public void setWalletRefenceId(String walletRefenceId) {
		this.walletRefenceId = walletRefenceId;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

}
