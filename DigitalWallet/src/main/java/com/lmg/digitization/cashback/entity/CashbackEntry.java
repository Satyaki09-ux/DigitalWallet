package com.lmg.digitization.cashback.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.lmg.digitization.digital.wallet.enums.Source;

@Entity
@Table(name = "LMG_CASHBACK_ENTRY")
public class CashbackEntry {
	
	public CashbackEntry() {
		super();
	}
	public CashbackEntry(CashbackEntry model) {
		super();
		this.cashbackId = model.cashbackId;
		this.shukranId = model.shukranId;
		this.campaignName=model.campaignName;
		this.expirationDate = model.expirationDate;
		this.redeemableConcept = model.redeemableConcept;
		this.territoryCode = model.territoryCode;
		this.applicability = model.applicability;
		this.isExpiryExtended=model.isExpiryExtended;
		this.issuingConcept=model.issuingConcept;
		this.orderReferenceNo=model.orderReferenceNo;
		this.orderReferenceDate=model.orderReferenceDate;
		this.pushText=model.pushText;
		this.smsText=model.smsText;
		this.redeemableStartDate=model.redeemableStartDate;
		this.redeemedAmount=BigDecimal.valueOf(0);
		this.returnOrderRefNo=model.returnOrderRefNo;
		this.source=model.source;
		this.sourceChannel=model.sourceChannel;
		this.sourceFunction=model.sourceFunction;
		this.transactionDate=model.transactionDate;
		this.transactionId=model.transactionId;
		this.version=model.version;
		
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name = "CASHBACK_ID" , columnDefinition = "VARCHAR(25)", nullable = false)
	private String cashbackId;
	
	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(30) NOT NULL")
	private String shukranId;
	
	@Column(name = "CASHBACK_REFERENCE_ID", columnDefinition = "VARCHAR(30) NOT NULL")
	private String cashbackReferenceId;
	
	@Column(name = "TRANSACTION_DATE" , columnDefinition = "DATE", nullable = false)
	private LocalDate transactionDate;
	
	@Column(name = "REDEMPTION_DATE" , columnDefinition = "DATE", nullable = true)
	private LocalDate redemptionDate;
	
	@Column(name = "RETURN_ORDER_REF_NO" , columnDefinition = "VARCHAR(25)", nullable = true)
	private String returnOrderRefNo;
	
	@Column(name = "REDEEM_INVOICE" , columnDefinition = "VARCHAR(25)")
	private String redeemInvoice;
	
	@Column(name = "ISSUED_AMOUNT" , columnDefinition = "NUMERIC(13,2)", nullable = false)
	private BigDecimal issuedAmount;
	
	@Column(name = "REDEEMED_AMOUNT" , columnDefinition = "NUMERIC(13,2)", nullable = false)
	private BigDecimal redeemedAmount;
	
	@Column(name = "BALANCE_AMOUNT" , columnDefinition = "NUMERIC(13,2)", nullable = false)
	private BigDecimal balanceAmount;
	
	@Column(name = "BASE_CURRENCY" , columnDefinition = "VARCHAR(25)", nullable = false)
	private String baseCurrency;
	
	@Column(name = "EXPIRATION_DATE" , columnDefinition = "DATE", nullable = false)
	private LocalDate expirationDate;
	
	@Column(name = "STATUS" , columnDefinition = "VARCHAR(20)", nullable = false)
	private String status;
	
	@Column(name = "SOURCE" , columnDefinition = "VARCHAR(25)", nullable = false)
	private String source;
	
	@Column(name = "REDEEMABLE_CONCEPT_CODE" , columnDefinition = "VARCHAR(3)", nullable = false)
	private String redeemableConcept;
	
	@Column(name = "TERRITORY_CODE" , columnDefinition = "VARCHAR(10)", nullable = false)
	private String territoryCode;
	
	@CreationTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "CREATED_DATE" , columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime createdDate;
	
	@UpdateTimestamp
	@ColumnDefault("CURRENT_TIMESTAMP")
	@Column(name = "MODIFIED_DATE" , columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime modifiedDate;
	
	@Column(name = "REMARKS" , columnDefinition = "VARCHAR(25)")
	private String remarks;
	
	@Column(name = "IS_EXPIRY_EXTENDED" , columnDefinition = "BIT", nullable = false)
	private Boolean isExpiryExtended;
	
	@Column(name = "APPLICABILITY" , columnDefinition = "VARCHAR(25)", nullable = false)
	private String applicability;
	
	@Column(name = "VERSION" , columnDefinition = "VARCHAR(3)", nullable = false)
	private String version;

	@Column(name = "TRANSACTION_ID" , columnDefinition = "VARCHAR(30)", nullable = false)
	private String transactionId;
	
	@Column(name = "REDEEMED_CONCEPT" , columnDefinition = "VARCHAR(30)")
	private String redeemedConcept;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "REDEEMED_SOURCE", columnDefinition = "VARCHAR(10)")
	private Source redeemedSource;
	
	@Column(name = "REDEEMED_STORE" , columnDefinition = "VARCHAR(30)")
	private String redeemedStore;
	
	@Column(name = "REDEEMED_USER" , columnDefinition = "VARCHAR(30)")
	private String redeemedUser;
	
	@Column(name = "ISSUING_CONCEPT", columnDefinition = "VARCHAR(10)", nullable = false)
	private String issuingConcept;
	
	@Column(name = "REDEEMABLE_START_DATE", columnDefinition = "DATE", nullable = false)
	private LocalDate redeemableStartDate;
	
	@Column(name = "SMS_TEXT", columnDefinition = "TEXT")
	private String smsText;

	@Column(name = "PUSH_TEXT", columnDefinition = "TEXT")
	private String pushText;

	@Column(name = "ORDER_REFERENCE_NO", columnDefinition = "VARCHAR(30)")
	private String orderReferenceNo;

	@Column(name = "SOURCE_CHANNEL", columnDefinition = "VARCHAR(30)")
	private String sourceChannel;
	
	@Column(name = "ORDER_REFERENCE_DATE", columnDefinition = "DATE")
	private Date orderReferenceDate;

	@Column(name = "CAMPAIGN_NAME", columnDefinition = "VARCHAR(50)")
	private String campaignName;
	
	@Column(name = "SOURCE_FUNCTION", columnDefinition = "VARCHAR(50)")
	private String sourceFunction;
	
	@Column(name = "SOURCE_REFERENCE", columnDefinition = "VARCHAR(50)")
	private String sourceReference;
	
	public String getCashbackId() {
		return cashbackId;
	}

	public void setCashbackId(String cashbackId) {
		this.cashbackId = cashbackId;
	}


	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public LocalDate getRedemptionDate() {
		return redemptionDate;
	}

	public void setRedemptionDate(LocalDate redemptionDate) {
		this.redemptionDate = redemptionDate;
	}

	public String getReturnOrderRefNo() {
		return returnOrderRefNo;
	}

	public void setReturnOrderRefNo(String returnOrderRefNo) {
		this.returnOrderRefNo = returnOrderRefNo;
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

	

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRedeemableConcept() {
		return redeemableConcept;
	}

	public void setRedeemableConcept(String redeemableConcept) {
		this.redeemableConcept = redeemableConcept;
	}

	public String getTerritoryCode() {
		return territoryCode;
	}

	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}

	public LocalDateTime getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDateTime createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Boolean getIsExpiryExtended() {
		return isExpiryExtended;
	}

	public void setIsExpiryExtended(Boolean isExpiryExtended) {
		this.isExpiryExtended = isExpiryExtended;
	}

	public String getApplicability() {
		return applicability;
	}

	public void setApplicability(String applicability) {
		this.applicability = applicability;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getCashbackReferenceId() {
		return cashbackReferenceId;
	}

	public void setCashbackReferenceId(String cashbackReferenceId) {
		this.cashbackReferenceId = cashbackReferenceId;
	}


	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getRedeemInvoice() {
		return redeemInvoice;
	}

	public void setRedeemInvoice(String redeemInvoice) {
		this.redeemInvoice = redeemInvoice;
	}

	public String getRedeemedConcept() {
		return redeemedConcept;
	}

	public void setRedeemedConcept(String redeemedConcept) {
		this.redeemedConcept = redeemedConcept;
	}

	public Source getRedeemedSource() {
		return redeemedSource;
	}

	public void setRedeemedSource(Source redeemedSource) {
		this.redeemedSource = redeemedSource;
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getIssuingConcept() {
		return issuingConcept;
	}

	public void setIssuingConcept(String issuingConcept) {
		this.issuingConcept = issuingConcept;
	}

	public LocalDate getRedeemableStartDate() {
		return redeemableStartDate;
	}

	public void setRedeemableStartDate(LocalDate redeemableStartDate) {
		this.redeemableStartDate = redeemableStartDate;
	}

	public String getSmsText() {
		return smsText;
	}

	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}

	public String getPushText() {
		return pushText;
	}

	public void setPushText(String pushText) {
		this.pushText = pushText;
	}


	public String getOrderReferenceNo() {
		return orderReferenceNo;
	}

	public void setOrderReferenceNo(String orderReferenceNo) {
		this.orderReferenceNo = orderReferenceNo;
	}

	public String getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public Date getOrderReferenceDate() {
		return orderReferenceDate;
	}

	public void setOrderReferenceDate(Date orderReferenceDate) {
		this.orderReferenceDate = orderReferenceDate;
	}

	public String getCampaignName() {
		return campaignName;
	}

	public void setCampaignName(String campaignName) {
		this.campaignName = campaignName;
	}

	public String getSourceFunction() {
		return sourceFunction;
	}

	public void setSourceFunction(String sourceFunction) {
		this.sourceFunction = sourceFunction;
	}

	public String getSourceReference() {
		return sourceReference;
	}

	public void setSourceReference(String sourceReference) {
		this.sourceReference = sourceReference;
	}
}
