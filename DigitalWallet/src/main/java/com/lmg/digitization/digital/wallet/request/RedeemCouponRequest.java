package com.lmg.digitization.digital.wallet.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class RedeemCouponRequest {
	
	@NotNull(message = "Redeem Date cannot be blank")
	@JsonProperty("redeem_date")
	private LocalDate redeemDate;
	
	@NotNull(message = "Redeem Amount cannot be zero or blank")
	@JsonProperty("redeem_amount")
	private BigDecimal redeemedAmount;

	@ApiModelProperty(example = "AED", allowableValues = "AED, KSA, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|KSA|BHD|QMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@JsonProperty("currency")
	private String currency;
	
	@NotNull(message = "Concept Code can not be blank")
	@JsonProperty("id_con")
	private BigDecimal redeemedConcept;
	
	@NotNull(message = "Store ID can not be blank")
	@JsonProperty("id_store")
	private BigDecimal redeemedStore;
	
	@NotNull(message = "Invoice Number cannot be blank")
	@JsonProperty("invoice_number")
	private String invoiceNumber;

	public LocalDate getRedeemDate() {
		return redeemDate;
	}

	public void setRedeemDate(LocalDate redeemDate) {
		this.redeemDate = redeemDate;
	}

	public BigDecimal getRedeemedConcept() {
		return redeemedConcept;
	}

	public void setRedeemedConcept(BigDecimal redeemedConcept) {
		this.redeemedConcept = redeemedConcept;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public BigDecimal getRedeemedAmount() {
		return redeemedAmount;
	}

	public void setRedeemedAmount(BigDecimal redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

	public BigDecimal getRedeemedStore() {
		return redeemedStore;
	}

	public void setRedeemedStore(BigDecimal redeemedStore) {
		this.redeemedStore = redeemedStore;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
	
}
