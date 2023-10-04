package com.lmg.digitization.digital.wallet.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
@ApiModel
public class BaseResponse {

	@JsonProperty("error_message")
	@ApiModelProperty(position = 3, example = "concept code is In-valid")
	private String errorMessage;

	@JsonProperty("status")
	@ApiModelProperty(position = 2, example = "FAILURE")
	private String status;

	@JsonProperty("error_code")
	@ApiModelProperty(position = 1, dataType="List", example = "[ERROR_DW_EXTEND_EXPIRY, ERROR_DW_REDEEM]")
	private List<String> errorCode;

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<String> getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(List<String> errorCode) {
		this.errorCode = errorCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}

