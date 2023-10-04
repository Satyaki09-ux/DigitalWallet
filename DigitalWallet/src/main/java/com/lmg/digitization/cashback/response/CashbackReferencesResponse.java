package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class CashbackReferencesResponse {

	@ApiModelProperty(position = 1, dataType = "String", example = "1112121006210")
	@JsonProperty("cashback_reference_id")
	private String cashbackRefenceId;

	@ApiModelProperty(position = 2, example = "2021-09-02", notes = "ISO Date format")
	@JsonProperty("expiration_date")
	private LocalDate expirationDate;

	@ApiModelProperty(position = 3, example = "50.01")
	@JsonProperty("amount")
	private BigDecimal balanceAmount;
	
	@ApiModelProperty(position = 4, dataType = "String", example = "1112121006210")
	@JsonProperty("redeemable_concept_code")
	private String redeemableConcept;

	public String getCashbackRefenceId() {
		return cashbackRefenceId;
	}

	public void setCashbackRefenceId(String cashbackRefenceId) {
		this.cashbackRefenceId = cashbackRefenceId;
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

	public String getRedeemableConcept() {
		return redeemableConcept;
	}

	public void setRedeemableConcept(String redeemableConcept) {
		this.redeemableConcept = redeemableConcept;
	}
	


}
