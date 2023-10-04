package com.lmg.digitization.digital.wallet.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DeleteWalletRequest {

	@NotBlank(message = "Remarks cannot be null")
	@JsonProperty("remarks")
	private String remarks;

	@NotBlank(message = "Source cannot be null")
	@JsonProperty("source")
	private String source;

	@NotBlank(message = "Channel cannot be null")
	@JsonProperty("channel")
	private String channel;

	@ApiModelProperty(example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|SAR|BHD|OMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@NotBlank(message = " base_currency must not be empty")
	@JsonProperty("base_currency")
	private String baseCurrency;

	private String language = "ENGLISH";

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

}
