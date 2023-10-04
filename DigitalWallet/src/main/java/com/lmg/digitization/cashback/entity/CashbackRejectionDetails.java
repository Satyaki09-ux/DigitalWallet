package com.lmg.digitization.cashback.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LMG_REJECTION_DETAILS")
public class CashbackRejectionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "FILE_ID", columnDefinition = "NUMERIC(13,2)", nullable = false)
	private BigDecimal fileId;

	@Column(name = "REASON", columnDefinition = "VARCHAR(1024)", nullable = false)
	private String reason;

	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(30)", nullable = false)
	private String shukranId;
	
	@Column(name = "TRANSACTION_ID", columnDefinition = "VARCHAR(30)", nullable = true)
	private String transactionId;
	
	@Column(name = "ORDER_REFERENCE", columnDefinition = "VARCHAR(30)", nullable = true)
	private String orderReference;
	
	@Column(name = "REJECTION_DATE", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime rejectionDate;

	@Column(name = "JSON_OBJECT", columnDefinition = "Text", nullable = true)
	private String jsonObject;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getFileId() {
		return fileId;
	}

	public void setFileId(BigDecimal fileId) {
		this.fileId = fileId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public LocalDateTime getRejectionDate() {
		return rejectionDate;
	}

	public void setRejectionDate(LocalDateTime rejectionDate) {
		this.rejectionDate = rejectionDate;
	}

	public String getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(String jsonObject) {
		this.jsonObject = jsonObject;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getOrderReference() {
		return orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

}
