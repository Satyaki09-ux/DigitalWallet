package com.lmg.digitization.cashback.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.lmg.digitization.digital.wallet.enums.Source;

@Entity
@Table(name = "LMG_CASHBACK_LEDGER")
public class CashbackLedger {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LMG_DIGITIZATION_LEDGER_SEQ")
	@SequenceGenerator(sequenceName = "LedgerSequence", allocationSize = 1, name = "LMG_DIGITIZATION_LEDGER_SEQ")
	@Column(name = "CASHBACK_LEDGER_ID", columnDefinition = "NUMERIC(25,0)")
	private BigInteger ledgerId;

	@Column(name = "CASHBACK_ID", columnDefinition = "VARCHAR(25)")
	private String cashbackId;

	@Column(name = "REF_NO", columnDefinition = "VARCHAR(25) NOT NULL")
	private String referenceNumber;

	@Column(name = "STR_CST_CENTER", columnDefinition = "VARCHAR(10) NOT NULL")
	private String store;

	@Column(name = "DC_DY_BSN", columnDefinition = "DATE NOT NULL")
	private LocalDate businessDate;

	@Column(name = "ORDER_NUMBER", columnDefinition = "VARCHAR(50)")
	private String orderNumber;

	@Column(name = "TRN_SRC", columnDefinition = "VARCHAR(30) NOT NULL")
	private String source;

	@Column(name = "TY_TRN", columnDefinition = "VARCHAR(50)")
	private String transactionId;

	@Column(name = "TRN", columnDefinition = "VARCHAR(5)")
	private String tranVersion;

	@Column(name = "AMOUNT", columnDefinition = "NUMERIC(13,2) NOT NULL")
	private BigDecimal amount;

	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(30)")
	private String shukranId;

	@Column(name = "EXPIRY_DATE", columnDefinition = "DATE NOT NULL")
	private LocalDate expiryDate;

	@Column(name = "STATUS", columnDefinition = "VARCHAR(20) NOT NULL")
	private String status;

	@Column(name = "CURRENCY", columnDefinition = "VARCHAR(3) NOT NULL")
	private String currency;

	@Column(name = "CONCEPT", columnDefinition = "VARCHAR(20) NOT NULL")
	private String concept;

	@Column(name = "REDEEMABLE_CONCEPT", columnDefinition = "VARCHAR(20) NULL")
	private String redeemableConcept;
	
	@Column(name = "OPNG_BAL", columnDefinition = "NUMERIC(13,2) NOT NULL")
	private BigDecimal openingBalance;

	@Column(name = "CLSNG_BAL", columnDefinition = "NUMERIC(13,2) NOT NULL")
	private BigDecimal closingBalance;

	@Column(name = "SOURCE_REF", columnDefinition = "VARCHAR(20)")
	private String sourceReference;

	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME")
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "MODIFIED_DATE", columnDefinition = "DATETIME")
	private LocalDateTime modifiedDate;

	/**
	 * @return the ledgerId
	 */
	public BigInteger getLedgerId() {
		return ledgerId;
	}

	/**
	 * @param ledgerId the ledgerId to set
	 */
	public void setLedgerId(BigInteger ledgerId) {
		this.ledgerId = ledgerId;
	}

	/**
	 * @return the walletId
	 */
	public String getCashbackId() {
		return cashbackId;
	}

	/**
	 * @param walletId the walletId to set
	 */
	public void setCashbackId(String cashbackId) {
		this.cashbackId = cashbackId;
	}

	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * @return the store
	 */
	public String getStore() {
		return store;
	}

	/**
	 * @param store the store to set
	 */
	public void setStore(String store) {
		this.store = store;
	}

	/**
	 * @return the businessDate
	 */
	public LocalDate getBusinessDate() {
		return businessDate;
	}

	/**
	 * @param businessDate the businessDate to set
	 */
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

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the amount
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * @return the shukranId
	 */
	public String getShukranId() {
		return shukranId;
	}

	/**
	 * @param shukranId the shukranId to set
	 */
	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	/**
	 * @return the expiryDate
	 */
	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the concept
	 */
	public String getConcept() {
		return concept;
	}

	/**
	 * @param concept the concept to set
	 */
	public void setConcept(String concept) {
		this.concept = concept;
	}

	/**
	 * @return the openingBalance
	 */
	public BigDecimal getOpeningBalance() {
		return openingBalance;
	}

	/**
	 * @param openingBalance the openingBalance to set
	 */
	public void setOpeningBalance(BigDecimal openingBalance) {
		this.openingBalance = openingBalance;
	}

	/**
	 * @return the closingBalance
	 */
	public BigDecimal getClosingBalance() {
		return closingBalance;
	}

	/**
	 * @param closingBalance the closingBalance to set
	 */
	public void setClosingBalance(BigDecimal closingBalance) {
		this.closingBalance = closingBalance;
	}

	/**
	 * @return the createdDate
	 */
	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getSourceReference() {
		return sourceReference;
	}

	public void setSourceReference(String sourceReference) {
		this.sourceReference = sourceReference;
	}

	public String getTranVersion() {
		return tranVersion;
	}

	public void setTranVersion(String tranVersion) {
		this.tranVersion = tranVersion;
	}

	public String getRedeemableConcept() {
		return redeemableConcept;
	}

	public void setRedeemableConcept(String redeemableConcept) {
		this.redeemableConcept = redeemableConcept;
	}

}
