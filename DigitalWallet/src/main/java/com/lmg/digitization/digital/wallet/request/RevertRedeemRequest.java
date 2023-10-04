package com.lmg.digitization.digital.wallet.request;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class RevertRedeemRequest {

	@NotBlank(message = "Shukran ID cannot be empty")
	@JsonProperty("shukran_id")
	private String shukranId;

	@ApiModelProperty(example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|SAR|BHD|OMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@NotBlank(message = " base_currency must not be empty")
	@JsonProperty("base_currency")
	private String baseCurrency;

	@JsonProperty("references_redeemed")
	private List<String> referencesRedeemed;

	@JsonProperty("reference_reissued")
	private String referenceReIssued;

	@NotBlank(message = "Reason for revert cannot be empty")
	@JsonProperty("revert_reason")
	private String revertReason;

	@NotBlank(message = "Wallet ID cannot be blank")
	@JsonProperty("wallet_id")
	private String walletId;

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getRevertReason() {
		return revertReason;
	}

	public void setRevertReason(String revertReason) {
		this.revertReason = revertReason;
	}

	public List<String> getReferencesRedeemed() {
		return referencesRedeemed;
	}

	public void setReferencesRedeemed(List<String> referencesRedeemed) {
		this.referencesRedeemed = referencesRedeemed;
	}

	public String getReferenceReIssued() {
		return referenceReIssued;
	}

	public void setReferenceReIssued(String referenceReIssued) {
		this.referenceReIssued = referenceReIssued;
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

}
