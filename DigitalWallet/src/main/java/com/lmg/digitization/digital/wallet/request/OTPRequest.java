package com.lmg.digitization.digital.wallet.request;

import java.math.BigDecimal;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class OTPRequest {
	@NotBlank
	@Size(min = 5, max = 20)
	@JsonProperty("otp_type")
	private String otpType;

	@NotBlank
	@Size(min = 6, max = 10)
	@JsonProperty("language")
	@ApiModelProperty(position = 14, example = "ENGLISH", allowableValues = "ENGLISH, ARABIC")
	private String language = "ENGLISH";

	@NotBlank
	@Size(min = 2, max = 50)
	@JsonProperty("transaction_id")
	private String transactionId;

	@NotBlank
	@Size(min = 2, max = 2)
	@JsonProperty("concept")
	private String concept;

	@ApiModelProperty(example = "AED", allowableValues = "AED, KSA, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|KSA|BHD|QMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@NotBlank
	@Size(min = 3, max = 3)
	@JsonProperty("currency")
	private String currency;

	@NotBlank
	@Size(min = 2, max = 30)
	@JsonProperty("amount")
	private BigDecimal amount;

	@ApiModelProperty(example = "971566213619")
	@Size(min = 10, max = 12, message = "Mobile number can not be less than 10 characters")
	@JsonProperty("mobile_number")
	private String mobileNumber;

	@ApiModelProperty(example = "user@gmail.com")
	@Email(message = "Email should be valid")
	@JsonProperty("email_id")
	private String emailId;

	public String getOtpType() {
		return otpType;
	}

	public void setOtpType(String otpType) {
		this.otpType = otpType;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

}
