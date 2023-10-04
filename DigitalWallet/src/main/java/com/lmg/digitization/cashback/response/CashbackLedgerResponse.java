package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CashbackLedgerResponse {
	
	@JsonProperty("ledger_id")
	private BigInteger ledgerId;
	
	@JsonProperty("cashback_id")
	private String cashbackId;
	
	@JsonProperty("ref_no")
	private String refNo;
	
	@JsonProperty("str_cst_center")
	private String store;
	
	@JsonProperty("dc_dy_bsn")
	private LocalDate businessDate;
	
	@JsonProperty("order_no")
	private String orderNumber;
	
	@JsonProperty("trn_src")
	private String trnSource;
	
	@JsonProperty("transaction_id")
	private String transactionId;
	
	@JsonProperty("tran_version")
	private String tranVersion;
	
	@JsonProperty("amount")
	private BigDecimal amount;
	
	@JsonProperty("shukran_Id")
	private String shukranId;
	
	@JsonProperty("expiry_date")
	private LocalDate expiryDate;
	
	@JsonProperty("status")
	private String status;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("concept")
	private String concept;
	
	@JsonProperty("redeemable_concept")
	private String redeemableConcept;
	
	@JsonProperty("opening_balance")
	private BigDecimal openingBalance;
	
	@JsonProperty("closing_balance")
	private BigDecimal closingBalance;
	
	@JsonProperty("source_reference")
	private String sourceReference;

	@JsonProperty("created_date")
	private LocalDateTime createdDate;
	
	@JsonProperty("modified_date")
	private LocalDateTime modifiedDate;

	public BigInteger getLedgerId() {
		return ledgerId;
	}

	public void setLedgerId(BigInteger ledgerId) {
		this.ledgerId = ledgerId;
	}

	public String getCashbackId() {
		return cashbackId;
	}

	public void setCashbackId(String cashbackId) {
		this.cashbackId = cashbackId;
	}

	public String getRefNo() {
		return refNo;
	}

	public void setRefNo(String refNo) {
		this.refNo = refNo;
	}

	public String getStore() {
		return store;
	}

	public void setStore(String store) {
		this.store = store;
	}

	public LocalDate getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(LocalDate businessDate) {
		this.businessDate = businessDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getTranVersion() {
		return tranVersion;
	}

	public void setTranVersion(String tranVersion) {
		this.tranVersion = tranVersion;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public String getRedeemableConcept() {
		return redeemableConcept;
	}

	public void setRedeemableConcept(String redeemableConcept) {
		this.redeemableConcept = redeemableConcept;
	}

	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	public BigDecimal getClosingBalance() {
		return closingBalance;
	}

	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}

	public String getSourceReference() {
		return sourceReference;
	}

	public void setSourceReference(String sourceReference) {
		this.sourceReference = sourceReference;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getTrnSource() {
		return trnSource;
	}

	public void setTrnSource(String trnSource) {
		this.trnSource = trnSource;
	}


	
}
