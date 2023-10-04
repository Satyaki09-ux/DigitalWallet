package com.lmg.digitization.digital.wallet.request;

import java.math.BigDecimal;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.enums.Source;

import io.swagger.annotations.ApiModelProperty;

public class RevertIssueDigitalWalletRequest {

	@ApiModelProperty(example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@NotBlank(message = " base_currency must not be empty")
	@JsonProperty("base_currency")
	private String baseCurrency;

	@ApiModelProperty(position = 1, example = "34.55")
	@Min(value = 0, message = "Amount must be greater than Zero")
	@JsonProperty("issued_amount")
	private BigDecimal issuedAmount;

	@ApiModelProperty(position = 6, example = "21006601005620210908")
	@NotNull
	@JsonProperty("invoiceNumber")
	private String invoiceNumber;

	@ApiModelProperty(position = 3, example = "ORPOS", allowableValues = "ORPOS, HYBRIS, OMS, SSM, SCO, SSCO, SHUKRAN")
	@NotNull
	@JsonProperty("source")
	private Source source;

	@ApiModelProperty(position = 4, example = "21006")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("store_id")
	private String storeId;

	@ApiModelProperty(position = 7, example = "6601005620210908")
	@JsonProperty("transaction_id")
	private String transactionId;

	@NotEmpty(message = "is not empty")
	@JsonProperty("cancel_reason")
	private String cancelReason;

	@ApiModelProperty(position = 5, example = "4", allowableValues = "ShoeMart:2,HomeCentre:4,Sarah:26,BabyShop:1,Splash:3,HomeBox:29,Max:6,Centrepoint:35, CentrePoint:21,SportsOne:27,Lifestyle:5,ShoeExpress:17,Lipsy:25")
	@NotNull
	@NotEmpty(message = "concept must not be empty")
	@JsonProperty("concept")
	private String concept;

	@ApiModelProperty(position = 14, example = "ENGLISH", allowableValues = "ENGLISH, ARABIC")
	private String language ="ENGLISH";

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}


}
