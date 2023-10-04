package com.lmg.digitization.digital.wallet.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OTPValidationRequest {

	@NotBlank
	@Size(min=5, max=20)
	@JsonProperty("otp_type")
	private String otpType;

	@NotBlank
	@Size(min=6, max=6)
	@JsonProperty("otp")
	private String otp;

	@NotBlank
	@Size(min=2, max=30)
	@JsonProperty("transaction_id")
	private String transactionId;

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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	


}
