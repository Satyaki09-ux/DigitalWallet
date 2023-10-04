package com.lmg.digitization.digital.wallet.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.enums.Source;

import io.swagger.annotations.ApiModelProperty;

public class TransactionDetail {

	@JsonProperty("transactionId")
	private String transactionId;

	@JsonProperty("order_number")
	private String orderNumber;

	@JsonProperty("amount")
	private String amount;

	@ApiModelProperty(position = 3, example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@JsonProperty("currency")
	private String currency;

	@JsonProperty("concept")
	private String concept;
	@JsonProperty("business_date")
	private String businessDate;
	@JsonProperty("status")
	private String status;
	@JsonProperty("source")
	private Source source;
	@JsonProperty("wallet_ref")
	private String walletRef;
	@JsonProperty("store")
	private String store;
	@JsonProperty("source_reference")
	private String sourceReference;
	@JsonProperty("expiry_date")
	private LocalDate expiryDate;
	@JsonProperty("created_date")
	private LocalDateTime createdDate;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}

	public String getWalletRef() {
		return walletRef;
	}

	public void setWalletRef(String walletRef) {
		this.walletRef = walletRef;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public String getSourceReference() {
		return sourceReference;
	}

	public void setSourceReference(String sourceReference) {
		this.sourceReference = sourceReference;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

}
