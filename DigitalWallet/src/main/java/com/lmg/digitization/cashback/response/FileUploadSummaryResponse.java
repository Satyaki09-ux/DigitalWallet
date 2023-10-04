package com.lmg.digitization.cashback.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.response.BaseResponse;

public class FileUploadSummaryResponse extends BaseResponse{
	@JsonProperty("summary")
	public List<FileUploadResponse> fileUploadResponse;

	public List<FileUploadResponse> getFileUploadResponse() {
		return fileUploadResponse;
	}

	public void setFileUploadResponse(List<FileUploadResponse> fileUploadResponse) {
		this.fileUploadResponse = fileUploadResponse;
	}
}
