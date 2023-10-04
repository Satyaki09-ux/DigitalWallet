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

import org.hibernate.annotations.DynamicUpdate;

import com.lmg.digitization.digital.wallet.enums.Source;

@Entity
@DynamicUpdate
@Table(name = "LMG_DIGITAL_WALLET_ENTRY")
public class DigitalWalletTransactionModel {
	@Id
	@Column(name = "WALLET_REFERENCE_ID", columnDefinition = "VARCHAR(25)")
	private String walletReferenceId;

	@Column(name = "SHUKRAN_ID", columnDefinition = "VARCHAR(30) NOT NULL")
	private String shukranId;

	@Column(name = "WALLET_ID", columnDefinition = "VARCHAR(30) NOT NULL")
	private String walletID;

	@Column(name = "TRANSACTION_DATE", columnDefinition = "DATETIME NOT NULL")
	private LocalDate transactionDate;

	@Column(name = "REDEMPTION_DATE", columnDefinition = "DATETIME")
	private LocalDate redemptionDate;

	@Column(name = "ISSUED_INVOICE_NO", columnDefinition = "VARCHAR(50)")
	private String issuedInvoice;

	@Column(name = "TRANSACTION_ID", columnDefinition = "VARCHAR(50)")
	private String transactionId;

	@Column(name = "ISSUED_AMOUNT", columnDefinition = "NUMERIC(13,2) NOT NULL")
	private BigDecimal issuedAmount;

	@Column(name = "REDEEMED_AMOUNT", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal redeemedAmount;

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

	@Column(name = "BALANCE_AMOUNT", columnDefinition = "NUMERIC(13,2) NOT NULL")
	private BigDecimal balanceAmount;

	@Column(name = "BASE_CURRENCY", columnDefinition = "VARCHAR(3) NOT NULL")
	private String baseCurrency;

	@Column(name = "EXPIRATION_DATE", columnDefinition = "DATETIME NOT NULL")
	private LocalDate expirationDate;

	@Column(name = "STATUS", columnDefinition = "VARCHAR(20) NOT NULL")
	private String status;

	@Column(name = "SOURCE_REF", columnDefinition = "VARCHAR(25) NOT NULL")
	private String sourceReference;

	@Column(name = "SOURCE_FUNCTION", columnDefinition = "VARCHAR(100) NOT NULL")
	private String sourceFunction;

	@Column(name = "SOURCE_APP", columnDefinition = "VARCHAR(30) NOT NULL")
	private String sourceApp;

	@Column(name = "CONCEPT_CODE", columnDefinition = "VARCHAR(5)")
	private String conceptCode;

	@Column(name = "TERRITORY_CODE", columnDefinition = "VARCHAR(1)")
	private String territoryCode;

	@Column(name = "STORE_CODE", columnDefinition = "VARCHAR(5)")
	private String storeCode;

	@Column(name = "CREATED_DATE", columnDefinition = "DATETIME")
	private LocalDateTime createDate;

	@Column(name = "MODIFIED_DATE", columnDefinition = "DATETIME")
	private LocalDateTime modifiedDate;

	@Column(name = "REMARKS", columnDefinition = "VARCHAR(250)")
	private String remarks;

	@Column(name = "IS_EXPIRY_EXTENDED", columnDefinition = "NUMERIC(1,0) default 0")
	private Boolean isExpiryExtended;

	@Column(name = "VERSION", columnDefinition = "NUMERIC(13,0)")
	private Long version;

	public DigitalWalletTransactionModel() {
		super();
	}

	public DigitalWalletTransactionModel(DigitalWalletTransactionModel model) {
		super();
		shukranId = model.shukranId;
		walletID = model.walletID;
		baseCurrency = model.baseCurrency;
		expirationDate = model.expirationDate;
		status = model.status;
		createDate = model.createDate;
		modifiedDate = model.modifiedDate;
		isExpiryExtended = model.isExpiryExtended;
	}

	public String getWalletID() {
		return walletID;
	}

	public void setWalletID(String walletID) {
		this.walletID = walletID;
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

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
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

	public String getSourceReference() {
		return sourceReference;
	}

	public void setSourceReference(String sourceReference) {
		this.sourceReference = sourceReference;
	}

	public String getSourceFunction() {
		return sourceFunction;
	}

	public void setSourceFunction(String sourceFunction) {
		this.sourceFunction = sourceFunction;
	}

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	public String getConceptCode() {
		return conceptCode;
	}

	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}

	public String getTerritoryCode() {
		return territoryCode;
	}

	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
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

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public void setExpiryExtended(boolean isExpiryExtended) {
		this.isExpiryExtended = isExpiryExtended;
	}

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public Boolean getIsExpiryExtended() {
		return isExpiryExtended;
	}

	public void setIsExpiryExtended(Boolean isExpiryExtended) {
		this.isExpiryExtended = isExpiryExtended;
	}

	public String getWalletReferenceId() {
		return walletReferenceId;
	}

	public void setWalletReferenceId(String walletReferenceId) {
		this.walletReferenceId = walletReferenceId;
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

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getIssuedInvoice() {
		return issuedInvoice;
	}

	public void setIssuedInvoice(String issuedInvoice) {
		this.issuedInvoice = issuedInvoice;
	}

}
