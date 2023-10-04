package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ReferencesResponse {
	@ApiModelProperty(position = 1)
	@JsonProperty("type")
	private String type;
	
	@ApiModelProperty(position = 2)
	@JsonProperty("reference_id")
	private String referenceId;
	
	@ApiModelProperty(position = 3)
	@JsonProperty("redeemable_concept")
	private String redeemableConcept;
	
	@ApiModelProperty(position = 4)
	@JsonProperty("expiration_date")
	private LocalDate expirationDate;
	
	@ApiModelProperty(position = 5)
	@JsonProperty("amount")
	private BigDecimal amount;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}

	public String getRedeemableConcept() {
		return redeemableConcept;
	}

	public void setRedeemableConcept(String redeemableConcept) {
		this.redeemableConcept = redeemableConcept;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
}
