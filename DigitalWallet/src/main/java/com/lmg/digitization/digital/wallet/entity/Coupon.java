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
@Table(name="LMG_DIGITAL_COUPON")
public class Coupon {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "COUPON_ID", columnDefinition = "NUMERIC(25,0)")
	private BigInteger couponId;

	@Column(name = "COUPON_CODE", columnDefinition = "VARCHAR(30)")
	private String couponCode;

	@Column(name = "APPLICABILITY", columnDefinition = "VARCHAR(30)")
	private String applicability;
	
	@Column(name = "STOREID", columnDefinition = "VARCHAR(30)")
	private String storeId;
	
	@Column(name = "THRESHOLD_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal thresholdAmount;

	@Column(name = "REDEEMED_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal redeemedAmount;
	
	@Column(name = "TARGET_DEPARTMENT", columnDefinition = "NUMERIC(13,0)")
	private BigDecimal targetDepartment;

	@Column(name = "ELIGLE_CONCEPT", columnDefinition = "NUMERIC(13,0)")
	private BigDecimal elegibleConcept;
	
	@Column(name = "DISCOUNT_TYPE", columnDefinition = "VARCHAR(10)")
	private String discountType;
	
	@Column(name = "DISCOUNT_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal discountAmount;
	
	@Column(name = "CURRENCY", columnDefinition = "VARCHAR(3)")
	private String currency;
	
	@Column(name = "DISCOUNT_PERCENT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal discountPercent;
	
	@Column(name = "IS_ITEM_LEVEL_DISCOUNT", columnDefinition = "VARCHAR(1)")
	private String isItemLevelDiscount;
	
	@Column(name = "STATUS", columnDefinition = "VARCHAR(10)")
	private String status;
	
	@Column(name = "EXPIRY_DATE", columnDefinition = "DATE")
	private LocalDate expiryDate;
	
	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME")
	private LocalDateTime createDate;
	
	@Column(name = "MODIFIED_DATE", columnDefinition = "DATETIME")
	private LocalDateTime modifiedDate;

	public BigInteger getCouponId() {
		return couponId;
	}

	public void setCouponId(BigInteger couponId) {
		this.couponId = couponId;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getApplicability() {
		return applicability;
	}

	public void setApplicability(String applicability) {
		this.applicability = applicability;
	}

	public BigDecimal getThresholdAmount() {
		return thresholdAmount;
	}

	public void setThresholdAmount(BigDecimal thresholdAmount) {
		this.thresholdAmount = thresholdAmount;
	}

	public BigDecimal getTargetDepartment() {
		return targetDepartment;
	}

	public void setTargetDepartment(BigDecimal targetDepartment) {
		this.targetDepartment = targetDepartment;
	}

	public BigDecimal getElegibleConcept() {
		return elegibleConcept;
	}

	public void setElegibleConcept(BigDecimal elegibleConcept) {
		this.elegibleConcept = elegibleConcept;
	}

	public String getDiscountType() {
		return discountType;
	}

	public void setDiscountType(String discountType) {
		this.discountType = discountType;
	}

	public BigDecimal getDiscountAmount() {
		return discountAmount;
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		this.discountAmount = discountAmount;
	}

	public BigDecimal getDiscountPercent() {
		return discountPercent;
	}

	public void setDiscountPercent(BigDecimal discountPercent) {
		this.discountPercent = discountPercent;
	}

	public String getIsItemLevelDiscount() {
		return isItemLevelDiscount;
	}

	public void setIsItemLevelDiscount(String isItemLevelDiscount) {
		this.isItemLevelDiscount = isItemLevelDiscount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public BigDecimal getRedeemedAmount() {
		return redeemedAmount;
	}

	public void setRedeemedAmount(BigDecimal redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	
}


