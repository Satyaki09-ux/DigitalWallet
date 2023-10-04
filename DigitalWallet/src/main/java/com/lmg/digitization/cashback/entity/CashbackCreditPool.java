package com.lmg.digitization.cashback.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LMG_CASHBACK_CREDIT_POOL")
public class CashbackCreditPool {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(20)", nullable = false)
	private String shukranId;

	@Column(name = "TRANSACTION_DATE", columnDefinition = "DATE", nullable = false)
	private LocalDate transactionDate;

	@Column(name = "CREDIT_POOL", columnDefinition = "NUMERIC(25,0)", nullable = false)
	private BigDecimal creditPoolAmount;

	@Column(name = "TYPE", columnDefinition = "CHAR(2)", nullable = false)
	private String type;

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public BigDecimal getCreditPoolAmount() {
		return creditPoolAmount;
	}

	public void setCreditPoolAmount(BigDecimal creditPoolAmount) {
		this.creditPoolAmount = creditPoolAmount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
