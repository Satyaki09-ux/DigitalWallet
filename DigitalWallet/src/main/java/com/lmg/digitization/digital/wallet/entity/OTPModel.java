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
@Table(name = "LMG_DIGITAL_OTP")

public class OTPModel {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="OTP_ID" , columnDefinition = "NUMERIC(25,0)")
	private Long otpId;
	
	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(25)")
	private String shukranId;

	@Column(name = "MOBILE_NO", columnDefinition = "VARCHAR(12)")
	private String mobileNumber;
	
	@Column(name = "CUSTOMER_EMAIL", columnDefinition = "VARCHAR(50)")
	private String customerEmail;
	
	@Column(name = "CONCEPT", columnDefinition = "VARCHAR(2)")
	private String concept;

	@Column(name = "CURRENCY", columnDefinition = "VARCHAR(3)")
	private String currency;
	
	@Column(name = "AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal amount;
	
	@Column(name = "CUSTOMER_NAME", columnDefinition = "VARCHAR(25)")
	private String customerName;
	
	@Column(name = "OTP_TYPE", columnDefinition = "VARCHAR(10)")
	private String otpType;
	
	@Column(name = "OTP", columnDefinition = "VARCHAR(6)")
	private String otp;

	@Column(name = "OTP_EXPIRATION", columnDefinition = "DATETIME")
	private LocalDateTime otpExpiration;

	@Column(name = "TEMPLATE_ID", columnDefinition = "VARCHAR(10)")
	private String templateId;
	
	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME")
	private LocalDateTime createDate;
	
	@Column(name = "MODIFIED_DATE", columnDefinition = "DATETIME")
	private LocalDateTime modifiedDate;
	
	@Column(name = "IS_REGENARATED", columnDefinition="CHAR(1) default 0")
	private Boolean isRegenarated;
	
	@Column(name = "IS_VALIDATED", columnDefinition="CHAR(1) default 0")
	private Boolean isValidated;

	@Column(name = "VALIDATION_DATE", columnDefinition = "DATETIME")
	private LocalDateTime validationDate;
	
	@Column(name = "IS_EXPIRED", columnDefinition="CHAR(1) default 0")
	private Boolean isExpired;

	@Column(name = "TRANSACTION_ID", columnDefinition = "VARCHAR(100)")
	private String transactionId;

	public Long getOtpId() {
		return otpId;
	}

	public void setOtpId(Long otpId) {
		this.otpId = otpId;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getOtpType() {
		return otpType;
	}

	public void setOtpType(String otpType) {
		this.otpType = otpType;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public LocalDateTime getOtpExpiration() {
		return otpExpiration;
	}

	public void setOtpExpiration(LocalDateTime otpExpiration) {
		this.otpExpiration = otpExpiration;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
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

	public Boolean getIsRegenarated() {
		return isRegenarated;
	}

	public void setIsRegenarated(Boolean isRegenarated) {
		this.isRegenarated = isRegenarated;
	}

	public Boolean getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Boolean isExpired) {
		this.isExpired = isExpired;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Boolean getIsValidated() {
		return isValidated;
	}

	public void setIsValidated(Boolean isValidated) {
		this.isValidated = isValidated;
	}

	public LocalDateTime getValidationDate() {
		return validationDate;
	}

	public void setValidationDate(LocalDateTime validationDate) {
		this.validationDate = validationDate;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	
}
