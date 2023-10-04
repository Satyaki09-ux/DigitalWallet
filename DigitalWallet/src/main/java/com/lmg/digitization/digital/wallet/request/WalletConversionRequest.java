package com.lmg.digitization.digital.wallet.request;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class WalletConversionRequest {

	@ApiModelProperty(position = 1, example = "21006")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("store_id")
	private String storeId;

	@ApiModelProperty(position = 2, example = "2021-09-02", notes = "ISO Date format")
	@NotNull
	@Pattern(regexp = "^\\d{4}-([0]\\d|1[0-2])-([0-2]\\d|3[01])$", message = " should be in format[yyyy-MM-dd]")
	@JsonProperty("business_date")
	private String businessDate;

	@ApiModelProperty(position = 3, example = "ORPOS", allowableValues = "ORPOS, HYBRIS, OMS, SSM, SCO, SSCO, SHUKRAN")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("source")
	private String source;

	@ApiModelProperty(position = 4, example = "10", allowableValues = "ShoeMart:2,HomeCentre:4,Sarah:26,BabyShop:1,Splash:3,HomeBox:29,Max:6,Centrepoint:35, CentrePoint:21,SportsOne:27,Lifestyle:5,ShoeExpress:17,Lipsy:25")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("concept")
	private String concept;

	@ApiModelProperty(position = 5, example = "HYBRIS", notes = "HYBRIS/ Any HRMS id of the cashier")
	@NotNull
	@NotEmpty(message = " must not be empty")
	@JsonProperty("operator_id")
	private String operatorId;

	@ApiModelProperty(position = 14, example = "ENGLISH", allowableValues = "ENGLISH, ARABIC")
	private String language = "ENGLISH";

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(String businessDate) {
		this.businessDate = businessDate;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	public String getOperatorId() {
		return operatorId;
	}

	public void setOperatorId(String operatorId) {
		this.operatorId = operatorId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
