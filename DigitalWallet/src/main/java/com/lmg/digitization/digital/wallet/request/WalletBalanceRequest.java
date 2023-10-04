package com.lmg.digitization.digital.wallet.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class WalletBalanceRequest {

	@ApiModelProperty(position = 2, example = "30", notes = "Number of Days for the Expiry")
	@JsonProperty("no_of_days")
	private Integer noOfDays = 0;

	@ApiModelProperty(position = 2, example = "2021-09-02", notes = "ISO Date format")
	@NotNull
	@Pattern(regexp = "^\\d{4}-([0]\\d|1[0-2])-([0-2]\\d|3[01])$", message = " should be in format[yyyy-MM-dd]")
	@JsonProperty("business_date")
	private String businessDate;

	@ApiModelProperty(example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|SAR|BHD|OMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@JsonProperty("base_currency")
	private String baseCurrency = "AED";

	@ApiModelProperty(position = 14, example = "ENGLISH", allowableValues = "ENGLISH, ARABIC")
	private String language = "ENGLISH";

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

}
