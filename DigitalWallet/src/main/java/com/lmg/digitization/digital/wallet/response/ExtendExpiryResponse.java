package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExtendExpiryResponse {

	@JsonProperty("wallet_reference_id")
	private String walletReferenceId;

	@JsonProperty("wallet_id")
	private String walletID;

	@JsonProperty("transaction_date")
	private String transactionDate;

	@JsonProperty("redemption_date")
	private LocalDate redemptionDate;

	@JsonProperty("transaction_id")
	private String transactionId;

	@JsonProperty("redemption_ref_no")
	private String redemptionRefNo;

	@JsonProperty("issued_amount")
	private BigDecimal issuedAmount;

	@JsonProperty("redeemed_amount")
	private BigDecimal redeemedAmount;

	@JsonProperty("balance_amount")
	private BigDecimal balanceAmount;

	@JsonProperty("base_currency")
	private String baseCurrency;

	@JsonProperty("expiration_date")
	private LocalDate expirationDate;

	private String status;

	@JsonProperty("source_reference")
	private String sourceReference;

	@JsonProperty("source_function")
	private String sourceFunction;

	@JsonProperty("source_app")
	private String sourceApp;

	@JsonProperty("concept_code")
	private String conceptCode;

	@JsonProperty("territory_code")
	private String territoryCode;

	@JsonProperty("store_code")
	private String storeCode;

	@JsonProperty("create_date")
	private LocalDateTime createDate;

	@JsonProperty("modified_date")
	private LocalDateTime modifiedDate;

	private String remarks;

	@JsonProperty("is_expiry_extended")
	private Boolean isExpiryExtended;

	private String applicability;


	/**
	 * @return the walletID
	 */
	public String getWalletID() {
		return walletID;
	}

	/**
	 * @param walletID the walletID to set
	 */
	public void setWalletID(String walletID) {
		this.walletID = walletID;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate() {
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate(String transactionDate) {
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the redemptionDate
	 */
	public LocalDate getRedemptionDate() {
		return redemptionDate;
	}

	/**
	 * @param redemptionDate the redemptionDate to set
	 */
	public void setRedemptionDate(LocalDate redemptionDate) {
		this.redemptionDate = redemptionDate;
	}

	/**
	 * @return the transactionId
	 */
	public String getTransactionId() {
		return transactionId;
	}

	/**
	 * @param transactionId the transactionId to set
	 */
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	/**
	 * @return the redemptionRefNo
	 */
	public String getRedemptionRefNo() {
		return redemptionRefNo;
	}

	/**
	 * @param redemptionRefNo the redemptionRefNo to set
	 */
	public void setRedemptionRefNo(String redemptionRefNo) {
		this.redemptionRefNo = redemptionRefNo;
	}

	/**
	 * @return the issuedAmount
	 */
	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	/**
	 * @param issuedAmount the issuedAmount to set
	 */
	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	/**
	 * @return the redeemedAmount
	 */
	public BigDecimal getRedeemedAmount() {
		return redeemedAmount;
	}

	/**
	 * @param redeemedAmount the redeemedAmount to set
	 */
	public void setRedeemedAmount(BigDecimal redeemedAmount) {
		this.redeemedAmount = redeemedAmount;
	}

	/**
	 * @return the balanceAmount
	 */
	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	/**
	 * @param balanceAmount the balanceAmount to set
	 */
	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	/**
	 * @return the baseCurrency
	 */
	public String getBaseCurrency() {
		return baseCurrency;
	}

	/**
	 * @param baseCurrency the baseCurrency to set
	 */
	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	/**
	 * @return the expirationDate
	 */
	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the sourceReference
	 */
	public String getSourceReference() {
		return sourceReference;
	}

	/**
	 * @param sourceReference the sourceReference to set
	 */
	public void setSourceReference(String sourceReference) {
		this.sourceReference = sourceReference;
	}

	/**
	 * @return the sourceFunction
	 */
	public String getSourceFunction() {
		return sourceFunction;
	}

	/**
	 * @param sourceFunction the sourceFunction to set
	 */
	public void setSourceFunction(String sourceFunction) {
		this.sourceFunction = sourceFunction;
	}

	/**
	 * @return the sourceApp
	 */
	public String getSourceApp() {
		return sourceApp;
	}

	/**
	 * @param sourceApp the sourceApp to set
	 */
	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	/**
	 * @return the conceptCode
	 */
	public String getConceptCode() {
		return conceptCode;
	}

	/**
	 * @param conceptCode the conceptCode to set
	 */
	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}

	/**
	 * @return the territoryCode
	 */
	public String getTerritoryCode() {
		return territoryCode;
	}

	/**
	 * @param territoryCode the territoryCode to set
	 */
	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}

	/**
	 * @return the storeCode
	 */
	public String getStoreCode() {
		return storeCode;
	}

	/**
	 * @param storeCode the storeCode to set
	 */
	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	/**
	 * @return the createDate
	 */
	public LocalDateTime getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(LocalDateTime createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the modifiedDate
	 */
	public LocalDateTime getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(LocalDateTime modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the isExpiryExtended
	 */
	public Boolean getIsExpiryExtended() {
		return isExpiryExtended;
	}

	/**
	 * @param isExpiryExtended the isExpiryExtended to set
	 */
	public void setIsExpiryExtended(Boolean isExpiryExtended) {
		this.isExpiryExtended = isExpiryExtended;
	}

	/**
	 * @return the applicability
	 */
	public String getApplicability() {
		return applicability;
	}

	/**
	 * @param applicability the applicability to set
	 */
	public void setApplicability(String applicability) {
		this.applicability = applicability;
	}

	public String getWalletReferenceId() {
		return walletReferenceId;
	}

	public void setWalletReferenceId(String walletReferenceId) {
		this.walletReferenceId = walletReferenceId;
	}


}
