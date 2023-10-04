package com.lmg.digitization.digital.wallet.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class RevertRedeemOnInvoiceRequest {

	@ApiModelProperty(position = 1, example = "34.55")
	@Min(value = 0, message = "Amount must be greater than Zero")
	@JsonProperty("redeemed_amount")
	private BigDecimal redeemedAmount;

	@ApiModelProperty(position = 2, example = "AED")
	@NotBlank
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@JsonProperty("currency")
	private String currency;

	@ApiModelProperty(position = 3, example = "ORPOS", allowableValues = "ORPOS, HYBRIS, OMS, SSM, SCO, SSCO, SHUKRAN")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("source")
	private String source;

	@ApiModelProperty(position = 4, example = "4", allowableValues = "ShoeMart:2,HomeCentre:4,Sarah:26,BabyShop:1,Splash:3,HomeBox:29,Max:6,Centrepoint:35, CentrePoint:21,SportsOne:27,Lifestyle:5,ShoeExpress:17,Lipsy:25")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("concept")
	private String concept;

	@ApiModelProperty(position = 5, example = "21006601005620210908")
	@NotNull
	@JsonProperty("invoice_number")
	private String invoiceNumber;

	@ApiModelProperty(position = 6, example = "6601005620210908")
	@JsonProperty("transaction_id")
	private String transactionId;

	@ApiModelProperty(position = 7, example = "ENGLISH", allowableValues = "ENGLISH, ARABIC")
	private String language = "ENGLISH";

	public BigDecimal getRedeemedAmount() {
		return redeemedAmount;
	}

	public void setRedeemedAmount(BigDecimal redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
