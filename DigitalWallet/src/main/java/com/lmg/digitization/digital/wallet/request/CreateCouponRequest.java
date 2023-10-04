package com.lmg.digitization.digital.wallet.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;


public class CreateCouponRequest {
	
	@NotNull(message = "Coupon Code cannot be blank")
	@JsonProperty("id_cpn")
	private String couponCode;

	@NotNull(message = "Applicability cannot be blank")
	@JsonProperty("lvl_apl_prm")
	private String applicability;
	
	@NotNull(message = "Threshold amount can not be blank")
	@JsonProperty("fc_value_apl_th")
	private BigDecimal thresholdAmount;

	@NotNull(message = "Target Department can not be blank")
	@JsonProperty("dpt_trgt")
	private BigDecimal targetDepartment;

	@NotNull(message = "Eligible Concept can not be blank")
	@JsonProperty("id_con_el")
	private BigDecimal elegibleConcept;
	
	@NotNull(message = "Dicount Type is either percent or fixed amount")
	@JsonProperty("ty_disc")
	private String discountType;
	
	@JsonProperty("amt_dec")
	private BigDecimal discountAmount;
	
	@ApiModelProperty(example = "AED", allowableValues = "AED, KSA, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|KSA|BHD|QMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@JsonProperty("currency")
	private String currency;
	
	@JsonProperty("pe_dsc")
	private BigDecimal discountPercent;
	
	@NotNull(message = "Item level discount can be either Y or N")
	@JsonProperty("fl_from_itm_disc")
	private String isItemLevelDiscount;
	
	@NotNull(message = "Coupon Status can not be blank")
	@JsonProperty("st_cpn")
	private String status;
	
	@NotNull(message = "Expiry Date can not be blank")
	@JsonProperty("dt_exp_cpn")
	private LocalDate expiryDate;

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

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}
	
}
