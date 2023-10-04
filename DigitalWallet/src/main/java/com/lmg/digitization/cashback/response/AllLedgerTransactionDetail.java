package com.lmg.digitization.cashback.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.enums.Source;

import io.swagger.annotations.ApiModelProperty;

public class AllLedgerTransactionDetail {

	@JsonProperty("transactionId")
	private String transaction_id;

	@JsonProperty("order_number")
	private String order_number;

	@JsonProperty("amount")
	private String amount;

	@ApiModelProperty(position = 3, example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@JsonProperty("currency")
	private String currency;

	@JsonProperty("concept")
	private String concept;
	@JsonProperty("business_date")
	private String business_date;
	@JsonProperty("status")
	private String status;
	@JsonProperty("source")
	private Source source;
	@JsonProperty("wallet_ref")
	private String reference_number;
	@JsonProperty("store")
	private String store;
	@JsonProperty("source_reference")
	private String source_reference;
	@JsonProperty("redeemable_concept")
	private String redeemable_concept;
	@JsonProperty("instrument_type")
	private String instrument_type;
	
	@JsonProperty("expiry_date")
	private String expiry_date;
	@JsonProperty("created_date")
	private String create_date;
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getBusiness_date() {
		return business_date;
	}
	public void setBusiness_date(String business_date) {
		this.business_date = business_date;
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
	public String getReference_number() {
		return reference_number;
	}
	public void setReference_number(String reference_number) {
		this.reference_number = reference_number;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getSource_reference() {
		return source_reference;
	}
	public void setSource_reference(String source_reference) {
		this.source_reference = source_reference;
	}
	public String getRedeemable_concept() {
		return redeemable_concept;
	}
	public void setRedeemable_concept(String redeemable_concept) {
		this.redeemable_concept = redeemable_concept;
	}
	public String getInstrument_type() {
		return instrument_type;
	}
	public void setInstrument_type(String instrument_type) {
		this.instrument_type = instrument_type;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}

	
}
