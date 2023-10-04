package com.lmg.digitization.digital.wallet.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name = "LMG_CREDIT_POOL_WALLET")

public class DWCreditPool {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="CREDIT_POOL_ID" , columnDefinition = "NUMERIC(25,0)")
	private Long creditPoolId;
	
	@Column(name="CREDIT_POOL_AMOUNT" , columnDefinition = "NUMERIC(25,0)")
	private BigDecimal creditPoolAmount;
	
	@Column(name="EXPIRED_POOL_ID" , columnDefinition = "NUMERIC(25,0)")
	private BigDecimal expiredPoolAmount;
	
	@Column(name="CURRENCY" , columnDefinition = "VARCHAR(3)")
	private String baseCurrency;
	
	@Column(name="MODIFIED_DATE" , columnDefinition = "DATETIME")
	private LocalDateTime modifiedDate;

	public Long getCreditPoolId() {
		return creditPoolId;
	}

	public void setCreditPoolId(Long creditPoolId) {
		this.creditPoolId = creditPoolId;
	}

	public BigDecimal getCreditPoolAmount() {
		return creditPoolAmount;
	}

	public void setCreditPoolAmount(BigDecimal creditPoolAmount) {
		this.creditPoolAmount = creditPoolAmount;
	}

	public BigDecimal getExpiredPoolAmount() {
		return expiredPoolAmount;
	}

	public void setExpiredPoolAmount(BigDecimal expiredPoolAmount) {
		this.expiredPoolAmount = expiredPoolAmount;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
