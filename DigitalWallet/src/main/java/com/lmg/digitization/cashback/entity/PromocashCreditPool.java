package com.lmg.digitization.cashback.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "LMG_PROMOCASH_CREDIT_POOL")
public class PromocashCreditPool {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(25)", nullable = false)
	private String shukranId;

	@Column(name = "TRANSACTION_DATE", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime transactionDate;

	@Column(name = "CREDIT_POOL", columnDefinition = "NUMERIC(25,0)", nullable = false)
	private BigDecimal creditPoolAmount;

	@Column(name = "TYPE", columnDefinition = "CHAR(2)", nullable = false)
	private String type;

}
