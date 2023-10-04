package com.lmg.digitization.digital.wallet.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicUpdate;

import com.lmg.digitization.digital.wallet.enums.Source;

@Entity
@DynamicUpdate
@Table(name = "LMG_DIGITAL_WALLET")
public class DWWalletModel {


	@Id
	@Column(name = "WALLET_ID", columnDefinition = "VARCHAR(25)", unique = true)
	private String walletId;

	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(25)", nullable = false)
	private String shukranId;

	@Column(name = "BASE_CURRENCY", columnDefinition = "VARCHAR(3)", nullable = false)
	private String baseCurrency;

	@Column(name = "MOBILE_NO", columnDefinition = "VARCHAR(12)")
	private String mobileNumber;

	@Column(name = "CUSTOMER_NAME", columnDefinition = "VARCHAR(200)")
	private String customerName;

	@Column(name = "CUSTOMER_EMAIL", columnDefinition = "VARCHAR(50)")
	private String emailId;

	@Enumerated(EnumType.STRING)
	@Column(name = "SOURCE_CHANNEL", columnDefinition = "VARCHAR(10)")
	private Source sourceChannel;

	@Column(name = "OPENING_BALANCE", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal openingAmount;

	@Column(name = "REDEMPTION_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal redemptionAmount;

	@Column(name = "BALANCE_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal balanceAmount;

	@Column(name = "ISSUED_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal issuedAmount;

	@Column(name = "EXPIRED_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal expiredAmount;

	@Column(name = "LAST_REDEMPTION_DATE", columnDefinition = "DATETIME")
	private LocalDate lastRedemptionDate;

	@Column(name = "CREATED_DATE", columnDefinition = "DATETIME")
	private LocalDateTime createDate;

	@Column(name = "MODIFIED_DATE", columnDefinition = "DATETIME")
	private LocalDateTime modifiedDate;

	@Version
	@Column(name = "VERSION", columnDefinition = "NUMERIC(13,0)")
	private Long version;

	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	private String walletStatus;

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public Source getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(Source sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public BigDecimal getOpeningAmount() {
		return openingAmount;
	}

	public void setOpeningAmount(BigDecimal openingAmount) {
		this.openingAmount = openingAmount;
	}

	public BigDecimal getRedemptionAmount() {
		return redemptionAmount;
	}

	public void setRedemptionAmount(BigDecimal redemptionAmount) {
		this.redemptionAmount = redemptionAmount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public LocalDate getLastRedemptionDate() {
		return lastRedemptionDate;
	}

	public void setLastRedemptionDate(LocalDate lastRedemptionDate) {
		this.lastRedemptionDate = lastRedemptionDate;
	}

	public LocalDateTime getCreateDate() {
		return createDate;
	}

	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getWalletStatus() {
		return walletStatus;
	}

	public void setWalletStatus(String walletStatus) {
		this.walletStatus = walletStatus;
	}

	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public BigDecimal getExpiredAmount() {
		return expiredAmount;
	}

	public void setExpiredAmount(BigDecimal expiredAmount) {
		this.expiredAmount = expiredAmount;
	}

}
