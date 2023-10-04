package com.lmg.digitization.cashback.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "LMG_PROMOCASH_UPLOAD_DETAILS")
public class CashbackUploadDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "FILE_NAME", columnDefinition = "VARCHAR(60)", nullable = false)
	private String fileName;

	@Column(name = "UPLOAD_BY", columnDefinition = "VARCHAR(50)", nullable = false)
	private String uploadBy;

	@Column(name = "IS_PROCESS", columnDefinition = "CHAR(1)", nullable = false)
	private String isProcess;

	@Column(name = "UPLOAD_DATE", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime uploadDate;

	@Column(name = "PROCESSED_DATE", columnDefinition = "DATETIME")
	private LocalDateTime processedDate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getUploadBy() {
		return uploadBy;
	}

	public void setUploadBy(String uploadBy) {
		this.uploadBy = uploadBy;
	}

	public String getIsProcess() {
		return isProcess;
	}

	public void setIsProcess(String isProcess) {
		this.isProcess = isProcess;
	}

	public LocalDateTime getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(LocalDateTime uploadDate) {
		this.uploadDate = uploadDate;
	}

	public LocalDateTime getProcessedDate() {
		return processedDate;
	}

	public void setProcessedDate(LocalDateTime processedDate) {
		this.processedDate = processedDate;
	}
}
