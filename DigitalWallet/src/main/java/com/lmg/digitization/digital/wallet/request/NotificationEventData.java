package com.lmg.digitization.digital.wallet.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationEventData {

	@JsonProperty("username")
	private String userName;

	@JsonProperty("concept_name")
	private String conceptName;

	@JsonProperty("order_no")
	private String orderNo;

	@JsonProperty("subject_line")
	private String subjectLine;

	@JsonProperty("voucher_no")
	private String voucherNo;

	@JsonProperty("verification_code")
	private String verificationCode;

	@JsonProperty("comment")
	private String comment;

	@JsonProperty("expiry_amount")
	private BigDecimal expiryAmount;

	@JsonProperty("shukran_id")
	private String shukranId;

	@JsonProperty("credit_note_number")
	private String creditNoteNumber;

	@JsonProperty("redeemed_amount")
	private BigDecimal redeemedAmount;

	@JsonProperty("available_amount")
	private BigDecimal availableAmount;

	@JsonProperty("issued_amount")
	private BigDecimal issuedAmount;

	@JsonProperty("tender_amount")
	private BigDecimal tenderAmount;

	@JsonProperty("reverted_amount")
	private BigDecimal revertedAmount;

	@JsonProperty("currency")
	private String currency;

	@JsonProperty("business_date")
	private LocalDate businessDate;

	@JsonProperty("store_id")
	private String storeId;

	@JsonProperty("store_name")
	private String storeName;

	@JsonProperty("territory")
	private String territory;

	@JsonProperty("expiry_date")
	private LocalDate expiryDate;

	@JsonProperty("extended_expiry_date")
	private LocalDate extendedExpiryDate;

	@JsonProperty("wallet_id")
	private String walletId;

	@JsonProperty("created_date")
	private LocalDateTime createdDate;

	@JsonProperty("language")
	private String language;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getConceptName() {
		return conceptName;
	}

	public void setConceptName(String conceptName) {
		this.conceptName = conceptName;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getVoucherNo() {
		return voucherNo;
	}

	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public BigDecimal getExpiryAmount() {
		return expiryAmount;
	}

	public void setExpiryAmount(BigDecimal expiryAmount) {
		this.expiryAmount = expiryAmount;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getCreditNoteNumber() {
		return creditNoteNumber;
	}

	public void setCreditNoteNumber(String creditNoteNumber) {
		this.creditNoteNumber = creditNoteNumber;
	}

	public BigDecimal getRedeemedAmount() {
		return redeemedAmount;
	}

	public void setRedeemedAmount(BigDecimal redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

	public BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public BigDecimal getTenderAmount() {
		return tenderAmount;
	}

	public void setTenderAmount(BigDecimal tenderAmount) {
		this.tenderAmount = tenderAmount;
	}

	public BigDecimal getRevertedAmount() {
		return revertedAmount;
	}

	public void setRevertedAmount(BigDecimal revertedAmount) {
		this.revertedAmount = revertedAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public LocalDate getBusinessDate() {
		return businessDate;
	}

	public void setBusinessDate(LocalDate businessDate) {
		this.businessDate = businessDate;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getTerritory() {
		return territory;
	}

	public void setTerritory(String territory) {
		this.territory = territory;
	}

	public LocalDate getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(LocalDate expiryDate) {
		this.expiryDate = expiryDate;
	}

	public LocalDate getExtendedExpiryDate() {
		return extendedExpiryDate;
	}

	public void setExtendedExpiryDate(LocalDate extendedExpiryDate) {
		this.extendedExpiryDate = extendedExpiryDate;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public String getSubjectLine() {
		return subjectLine;
	}

	public void setSubjectLine(String subjectLine) {
		this.subjectLine = subjectLine;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NotificationEventData [userName=").append(userName).append(", conceptName=").append(conceptName)
		.append(", orderNo=").append(orderNo).append(", subjectLine=").append(subjectLine).append(", voucherNo=").append(voucherNo)
		.append(", verificationCode=").append(verificationCode).append(", comment=").append(comment).append(", expiryAmount=")
		.append(expiryAmount).append(", shukranId=").append(shukranId).append(", creditNoteNumber=").append(creditNoteNumber)
		.append(", redeemedAmount=").append(redeemedAmount).append(", availableAmount=").append(availableAmount)
		.append(", issuedAmount=").append(issuedAmount).append(", tenderAmount=").append(tenderAmount).append(", revertedAmount=")
		.append(revertedAmount).append(", currency=").append(currency).append(", businessDate=").append(businessDate)
		.append(", storeId=").append(storeId).append(", storeName=").append(storeName).append(", territory=").append(territory)
		.append(", expiryDate=").append(expiryDate).append(", extendedExpiryDate=").append(extendedExpiryDate).append(", walletId=")
		.append(walletId).append(", createdDate=").append(createdDate).append(", language=").append(language).append("]");
		return builder.toString();
	}

}
