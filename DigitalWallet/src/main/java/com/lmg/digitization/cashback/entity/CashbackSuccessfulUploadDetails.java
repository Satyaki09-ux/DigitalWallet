
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
@Table(name = "LMG_CASHBACK_SUCCESSFUL_UPLOAD_DETAILS")
public class CashbackSuccessfulUploadDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "FILE_ID", columnDefinition = "NUMERIC(13,2)", nullable = false)
	private Long fileId;


	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(30)", nullable = false)
	private String shukranId;
	
	@Column(name = "TRANSACTION_ID", columnDefinition = "VARCHAR(30)", nullable = true)
	private String transactionId;
	
	@Column(name = "ORDER_REFERENCE", columnDefinition = "VARCHAR(30)", nullable = true)
	private String orderReference;
	
	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime createDate;

	@Column(name = "LINE_OBJECT", columnDefinition = "TEXT", nullable = true)
	private String lineObject;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getLineObject() {
		return lineObject;
	}

	public void setLineObject(String lineObject) {
		this.lineObject = lineObject;
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
