package com.lmg.digitization.cashback.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileUploadSummaryRequest {

	@JsonProperty("start_date")
	private String startDate;
	@JsonProperty("end_date")
	private String endDate;
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
