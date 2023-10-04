package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCouponResponse {

	@JsonProperty("coupon_id")
	private BigInteger couponId;
	
	@JsonProperty("id_cpn")
	private String couponCode;
	
	@JsonProperty("issued_store")
	private String storeId;
	
	@JsonProperty("lvl_apl_prm")
	private String applicability;
	
	@JsonProperty("fc_value_apl_th")
	private BigDecimal thresholdAmount;
	
	@JsonProperty("dpt_trgt")
	private BigDecimal targetDepartment;

	@JsonProperty("id_con_el")
	private BigDecimal elegibleConcept;

	@JsonProperty("ty_disc")
	private String discountType;
	
	@JsonProperty("amt_dec")
	private BigDecimal discountAmount;
	
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("pe_dsc")
	private BigDecimal discountPercent;
	
	@JsonProperty("fl_from_itm_disc")
	private String isItemLevelDiscount;
	
	@JsonProperty("st_cpn")
	private String status;

	@JsonProperty("dt_exp_cpn")
	private LocalDate expiryDate;
	
	@JsonProperty("created_date")
	private LocalDateTime createDate;
	
	@JsonProperty("modified_date")
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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	
}
