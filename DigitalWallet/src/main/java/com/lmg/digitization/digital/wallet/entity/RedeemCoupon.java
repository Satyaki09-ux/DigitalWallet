package com.lmg.digitization.digital.wallet.entity;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="LMG_DIGITAL_REDEEM_COUPON")
public class RedeemCoupon {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "REDEEM_COUPON_ID", columnDefinition = "NUMERIC(25,0)")
	private BigInteger redeemCouponId;
	
	@Column(name = "COUPON_CODE", columnDefinition = "VARCHAR(30)")
	private String couponCode;
	
	@Column(name = "REDEEMED_DATE", columnDefinition = "DATE")
	private LocalDate redeemDate;
	
	@Column(name = "REDEEMED_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal redeemedAmount;
	
	@Column(name = "CURRENCY", columnDefinition = "VARCHAR(3)")
	private String currency;
	
	@Column(name = "REDEEMED_CONCEPT", columnDefinition = "VARCHAR(3)")
	private BigDecimal redeemedConcept;
	
	@Column(name = "REDEEMED_STORE", columnDefinition = "VARCHAR(10)")
	private BigDecimal redeemedStore;
	
	@Column(name = "INVOICE", columnDefinition = "VARCHAR(30)")
	private String invoiceNumber;
	
	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME")
	private LocalDateTime createDate;

	public BigInteger getRedeemCouponId() {
		return redeemCouponId;
	}

	public void setRedeemCouponId(BigInteger redeemCouponId) {
		this.redeemCouponId = redeemCouponId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public LocalDate getRedeemDate() {
		return redeemDate;
	}

	public void setRedeemDate(LocalDate redeemDate) {
		this.redeemDate = redeemDate;
	}

	public BigDecimal getRedeemedAmount() {
		return redeemedAmount;
	}

	public void setRedeemedAmount(BigDecimal redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

	public BigDecimal getRedeemedConcept() {
		return redeemedConcept;
	}

	public void setRedeemedConcept(BigDecimal redeemedConcept) {
		this.redeemedConcept = redeemedConcept;
	}

	public BigDecimal getRedeemedStore() {
		return redeemedStore;
	}

	public void setRedeemedStore(BigDecimal redeemedStore) {
		this.redeemedStore = redeemedStore;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
}
