package com.lmg.digitization.digital.wallet.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.lmg.digitization.digital.wallet.enums.Source;

public class DigitalWalletCashbackTransactionModel {

	private String walletCashbackReferenceId;
	private String shukranId;
	private String walletCashbackId;
	private LocalDate createdDate;
	private LocalDate redemptionDate;
	private LocalDate expirationDate;
	private String issuedInvoice;
	private String transactionId;
	private BigDecimal balanceAmount;
	private BigDecimal issuedAmount;
	private BigDecimal redeemedAmount;
	@Enumerated(EnumType.STRING)
	private Source redeemedSource;
	private String redeemedConcept;
	private String redeemableConcept;
	private String redeemedStore;
	private String redeemedUser;
	private String redeemInvoice;
	private String status;
	private String type;
	private Long version;

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getRedemptionDate() {
		return redemptionDate;
	}

	public void setRedemptionDate(LocalDate redemptionDate) {
		this.redemptionDate = redemptionDate;
	}

	public String getIssuedInvoice() {
		return issuedInvoice;
	}

	public void setIssuedInvoice(String issuedInvoice) {
		this.issuedInvoice = issuedInvoice;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public BigDecimal getRedeemedAmount() {
		return redeemedAmount;
	}

	public void setRedeemedAmount(BigDecimal redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

	public Source getRedeemedSource() {
		return redeemedSource;
	}

	public void setRedeemedSource(Source redeemedSource) {
		this.redeemedSource = redeemedSource;
	}

	public String getRedeemedConcept() {
		return redeemedConcept;
	}

	public void setRedeemedConcept(String redeemedConcept) {
		this.redeemedConcept = redeemedConcept;
	}

	public String getRedeemedStore() {
		return redeemedStore;
	}

	public void setRedeemedStore(String redeemedStore) {
		this.redeemedStore = redeemedStore;
	}

	public String getRedeemedUser() {
		return redeemedUser;
	}

	public void setRedeemedUser(String redeemedUser) {
		this.redeemedUser = redeemedUser;
	}

	public String getRedeemInvoice() {
		return redeemInvoice;
	}

	public void setRedeemInvoice(String redeemInvoice) {
		this.redeemInvoice = redeemInvoice;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWalletCashbackReferenceId() {
		return walletCashbackReferenceId;
	}

	public void setWalletCashbackReferenceId(String walletCashbackReferenceId) {
		this.walletCashbackReferenceId = walletCashbackReferenceId;
	}

	public String getWalletCashbackId() {
		return walletCashbackId;
	}

	public void setWalletCashbackId(String walletCashbackId) {
		this.walletCashbackId = walletCashbackId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getRedeemableConcept() {
		return redeemableConcept;
	}

	public void setRedeemableConcept(String redeemableConcept) {
		this.redeemableConcept = redeemableConcept;
	}
}
