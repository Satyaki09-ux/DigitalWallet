
package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionResponse {

	@JsonProperty("base_currency")
	private String baseCurrency;

	@JsonProperty("wallet_ref_id")
	private String walletReferenceId;

	@JsonProperty("expiry_date")
	private LocalDate expirationDate;

	@JsonProperty("amount")
	private BigDecimal issuedAmount;

	@JsonProperty("store_code")
	private String storeCode;

	@JsonProperty("business_date")
	private LocalDate transactionDate;

	@JsonProperty("transacion_id")
	private String transactionId;

	@JsonProperty("source_channel")
	private String sourceApp;

	@JsonProperty("version")
	private Long version;

	@JsonProperty("reference_source")
	private String sourceReference;

	@JsonProperty("source_function")
	private String sourceFunction;

	@JsonProperty("territory_code")
	private String territoryCode;

	@JsonProperty("remarks")
	private String remarks;

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

	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
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

	public String getTerritoryCode() {
		return territoryCode;
	}

	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getWalletReferenceId() {
		return walletReferenceId;
	}

	public void setWalletReferenceId(String walletReferenceId) {
		this.walletReferenceId = walletReferenceId;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

}
