package com.lmg.digitization.cashback.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "LMG_CASHBACK_DETAILS")
public class CashbackDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(20)", nullable = false)
	private String shukranId;

	@Column(name = "ISSUED_AMOUNT", columnDefinition = "NUMERIC(13,2)", nullable = false)
	private BigDecimal issuedAmount;

	@Column(name = "TERRITORY", columnDefinition = "VARCHAR(10)", nullable = false)
	private String territory;

	@Column(name = "STATUS", columnDefinition = "VARCHAR(10)", nullable = false)
	private String cashbackStatus;

	@Column(name = "BASE_CURRENCY", columnDefinition = "VARCHAR(3)", nullable = false)
	private String baseCurrency;

	@Column(name = "BALANCE_AMOUNT", columnDefinition = "NUMERIC(13,2)", nullable = false)
	private BigDecimal balanceAmount;

	@Column(name = "OPENING_BALANCE", columnDefinition = "NUMERIC(13,2)", nullable = false)
	private BigDecimal openingAmount;
	
	@Column(name = "EXPIRED_AMOUNT", columnDefinition = "NUMERIC(13,2)", nullable = false)
	private BigDecimal expiredAmount;
	
	@Column(name = "REDEEMED_AMOUNT", columnDefinition = "NUMERIC(13,2)", nullable = false)
	private BigDecimal redeemAmount;

	@Column(name = "LAST_REDEEMEPTION_DATE", columnDefinition = "DATE")
	private LocalDate lastRedeemptionDate;

	@Column(name = "IS_MANUAL_PROCESS", columnDefinition = "CHAR(1)")
	private String isManualProcess;

	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "CREATED_DATE", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime createdDate;

	@UpdateTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "MODIFITED_DATE", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime modifiedDate;
	
	@Column(name = "CREATED_BY", columnDefinition = "VARCHAR(50)", nullable = false)
	private String createdBy;

	@Column(name = "FILE_ID", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal fileId;
	
	@Column(name = "CUSTOMER_NAME", columnDefinition = "VARCHAR(100)")
	private String customerName;
	
	@Column(name = "CUSTOMER_MOBILE", columnDefinition = "VARCHAR(12)")
	private String customerMobile;
	
	@Column(name = "CUSTOMER_EMAIL", columnDefinition = "VARCHAR(50)")
	private String customerEmail;
	
	@Column(name = "VERSION", columnDefinition = "INTEGER")
	private String version;
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CASHBACK_ID", columnDefinition = "VARCHAR(25)", nullable = false)
	private String cashbackId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public String getCashbackStatus() {
		return cashbackStatus;
	}

	public void setCashbackStatus(String cashbackStatus) {
		this.cashbackStatus = cashbackStatus;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getOpeningAmount() {
		return openingAmount;
	}

	public void setOpeningAmount(BigDecimal openingAmount) {
		this.openingAmount = openingAmount;
	}

	public BigDecimal getExpiredAmount() {
		return expiredAmount;
	}

	public void setExpiredAmount(BigDecimal expiredAmount) {
		this.expiredAmount = expiredAmount;
	}

	public BigDecimal getRedeemAmount() {
		return redeemAmount;
	}

	public void setRedeemAmount(BigDecimal redeemAmount) {
		this.redeemAmount = redeemAmount;
	}

	public LocalDate getLastRedeemptionDate() {
		return lastRedeemptionDate;
	}

	public void setLastRedeemptionDate(LocalDate lastRedeemptionDate) {
		this.lastRedeemptionDate = lastRedeemptionDate;
	}

	public String getIsManualProcess() {
		return isManualProcess;
	}

	public void setIsManualProcess(String isManualProcess) {
		this.isManualProcess = isManualProcess;
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

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public BigDecimal getFileId() {
		return fileId;
	}

	public void setFileId(BigDecimal fileId) {
		this.fileId = fileId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCashbackId() {
		return cashbackId;
	}

	public void setCashbackId(String cashbackId) {
		this.cashbackId = cashbackId;
	}

	
	
	
}
