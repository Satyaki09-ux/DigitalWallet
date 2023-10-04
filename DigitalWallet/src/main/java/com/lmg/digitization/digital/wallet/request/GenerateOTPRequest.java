package com.lmg.digitization.digital.wallet.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GenerateOTPRequest {

	@JsonProperty("shukran_id")
	private String shukranId;

	private String language ="ENGLISH";

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}


}
