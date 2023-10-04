package com.lmg.digitization.digital.wallet.request;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class AccountStatementRequest {
	
	@ApiModelProperty(position = 2, example = "30", notes = "Number of Days for the Statement")
	@JsonProperty("no_of_days")
	private Integer noOfDays = 0;

	@ApiModelProperty(position = 2, example = "2021-09-02", notes = "ISO Date format")
	@NotNull
	@Pattern(regexp = "^\\d{4}-([0]\\d|1[0-2])-([0-2]\\d|3[01])$", message = " should be in format[yyyy-MM-dd]")
	@JsonProperty("start_date")
	private String startDate;
		
	public Integer getNoOfDays() {
		return noOfDays;
	}

	public void setNoOfDays(Integer noOfDays) {
		this.noOfDays = noOfDays;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

}
