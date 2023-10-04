package com.lmg.digitization.digital.wallet.response;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class DcnToDwConvertResponse {

	@ApiModelProperty(position = 1, dataType = "String", example = "111212100621090612222512")
	@JsonProperty("wallet_id")
	private String walletId;

	@ApiModelProperty(position = 2, example = "100.13")
	@JsonProperty("wallet_balance")
	private BigDecimal balance;

	@ApiModelProperty(position = 3, example = "11.13")
	@JsonProperty("issued_amount")
	private BigDecimal issuedAmount;

	@ApiModelProperty(position = 4, example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@JsonProperty("currency")
	private String currency;

	@ApiModelProperty(position = 5, example = "1")
	@JsonProperty("version")
	private Long version;

	@JsonProperty("references")
	private List<WalletReferences> references;

	public String getWalletId() {
		return walletId;
	}

	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public List<WalletReferences> getReferences() {
		return references;
	}

	public void setReferences(List<WalletReferences> references) {
		this.references = references;
	}

	public BigDecimal getIssuedAmount() {
		return issuedAmount;
	}

	public void setIssuedAmount(BigDecimal issuedAmount) {
		this.issuedAmount = issuedAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

}
