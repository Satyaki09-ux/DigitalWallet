package com.lmg.digitization.digital.wallet.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OTPResponse {
	@JsonProperty("shukraan_id")
	private String shukranId;

	@JsonProperty("message")
	private String message;

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	

}
