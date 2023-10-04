package com.lmg.digitization.cashback.response;

import java.math.BigInteger;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FileUploadResponse {
	
	@JsonProperty("item_id")
	private BigInteger id;
	
	@JsonProperty("blob_name")
	private String fileName;
	
	@JsonProperty("create_date")
	private LocalDate uploadedDate;
	
	@JsonProperty("total_reject")
	private Integer totalFailure;
	
	@JsonProperty("total_success")
	private Integer totalSuccess;
	
	public BigInteger getId() {
		return id;
	}
	public void setId(BigInteger id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public LocalDate getUploadedDate() {
		return uploadedDate;
	}
	public void setUploadedDate(LocalDate uploadedDate) {
		this.uploadedDate = uploadedDate;
	}
	public Integer getTotalFailure() {
		return totalFailure;
	}
	public void setTotalFailure(Integer totalFailure) {
		this.totalFailure = totalFailure;
	}
	public Integer getTotalSuccess() {
		return totalSuccess;
	}
	public void setTotalSuccess(Integer totalSuccess) {
		this.totalSuccess = totalSuccess;
	}
	
}
