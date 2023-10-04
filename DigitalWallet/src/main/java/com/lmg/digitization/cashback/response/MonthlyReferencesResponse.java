package com.lmg.digitization.cashback.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class MonthlyReferencesResponse {
	
	@ApiModelProperty(position = 1)
	@JsonProperty("month")
	private String month;
	
	@ApiModelProperty(position = 2)
	@JsonProperty("references")
	private List<ReferencesResponse> references;

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public List<ReferencesResponse> getReferences() {
		return references;
	}

	public void setReferences(List<ReferencesResponse> references) {
		this.references = references;
	}
}
