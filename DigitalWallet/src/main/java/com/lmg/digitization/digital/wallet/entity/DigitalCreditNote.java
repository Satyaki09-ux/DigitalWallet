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

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.lmg.digitization.digital.wallet.enums.IssuedType;
import com.lmg.digitization.digital.wallet.enums.Source;
import com.lmg.digitization.digital.wallet.enums.Status;

@Entity
@Table(name = "LMG_DIGITAL_CREDIT_NOTE")
public class DigitalCreditNote {

	@Id
	@Column(name = "DCN_ID", columnDefinition = "VARCHAR(30)")
	private String dcnId;

	@Column(name = "ISSUED_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal issuedAmount;

	@Column(name = "REDEEMED_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal redeemedAmount;

	@Column(name = "BALANCE_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal balanceAmount;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", columnDefinition = "VARCHAR(20)")
	private Status status;

	@Column(name = "ORDER_NUMBER", columnDefinition = "VARCHAR(50)")
	private String orderNumber;

	@Column(name = "CURRENCY", columnDefinition = "VARCHAR(3)")
	private String currency;

	@Column(name = "CUSTOMER_MOBILE", columnDefinition = "VARCHAR(20)")
	private String customerMobile;

	@Column(name = "EXPIRATION_DATE", columnDefinition = "DATETIME")
	private LocalDate expirationDate;

	@Column(name = "CUSTOMER_EMAIL", columnDefinition = "VARCHAR(100)")
	private String customerEmail;

	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "CREATE_DATE", columnDefinition = "DATETIME")
	private LocalDateTime createDate;

	@UpdateTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "MODIFIED_DATE", columnDefinition = "DATETIME")
	private LocalDateTime modifiedDate;

	@Enumerated(EnumType.STRING)
	@Column(name = "ISSUED_SOURCE", columnDefinition = "VARCHAR(10)")
	private Source issuedSource;

	@Column(name = "ISSUED_CONCEPT", columnDefinition = "VARCHAR(30)")
	private Integer issuedConcept;

	@Column(name = "ISSUED_TYPE", columnDefinition = "VARCHAR(50)")
	@Enumerated(EnumType.STRING)
	private IssuedType issuedType;

	@Column(name = "ISSUED_STORE", columnDefinition = "VARCHAR(10)")
	private String issuedStore;

	@Column(name = "ISSUED_USER", columnDefinition = "VARCHAR(50)")
	private String issuedUser;

	@Enumerated(EnumType.STRING)
	@Column(name = "REDEEMED_SOURCE", columnDefinition = "VARCHAR(10)")
	private Source redeemedSource;

	@Column(name = "REDEEMED_CONCEPT", columnDefinition = "VARCHAR(50)")
	private String redeemedConcept;

	@Column(name = "REDEEMED_STORE", columnDefinition = "VARCHAR(10)")
	private String redeemedStore;

	@Column(name = "REDEEMED_USER", columnDefinition = "VARCHAR(50)")
	private String redeemedUser;

	@Column(name = "REDEEM_INVOICE", columnDefinition = "VARCHAR(50)")
	private String redeemInvoice;

	@Column(name = "REDEMPTION_DATE", columnDefinition = "DATETIME")
	private LocalDate redemptionDate;

	@Column(name = "VALID_CONCEPTS", columnDefinition = "VARCHAR(100)")
	private String validConcepts;

	@Column(name = "TRANSACTION_ID", columnDefinition = "VARCHAR(50)")
	private String transactionId;

	@Version
	@Column(name = "VERSION")
	private Long version;

	public String getDcnId() {
		return dcnId;
	}

	public void setDcnId(String dcnId) {
		this.dcnId = dcnId;
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

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
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

	public Source getIssuedSource() {
		return issuedSource;
	}

	public void setIssuedSource(Source issuedSource) {
		this.issuedSource = issuedSource;
	}

	public Integer getIssuedConcept() {
		return issuedConcept;
	}

	public void setIssuedConcept(Integer issuedConcept) {
		this.issuedConcept = issuedConcept;
	}

	public IssuedType getIssuedType() {
		return issuedType;
	}

	public void setIssuedType(IssuedType issuedType) {
		this.issuedType = issuedType;
	}

	public String getIssuedStore() {
		return issuedStore;
	}

	public void setIssuedStore(String issuedStore) {
		this.issuedStore = issuedStore;
	}

	public String getIssuedUser() {
		return issuedUser;
	}

	public void setIssuedUser(String issuedUser) {
		this.issuedUser = issuedUser;
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

	public LocalDate getRedemptionDate() {
		return redemptionDate;
	}

	public void setRedemptionDate(LocalDate redemptionDate) {
		this.redemptionDate = redemptionDate;
	}

	public String getValidConcepts() {
		return validConcepts;
	}

	public void setValidConcepts(String validConcepts) {
		this.validConcepts = validConcepts;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}