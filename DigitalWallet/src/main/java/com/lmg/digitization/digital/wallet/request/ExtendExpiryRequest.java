package com.lmg.digitization.digital.wallet.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class ExtendExpiryRequest {

	@NotEmpty(message = "is not empty")
	@JsonProperty("wallet_id")
	private String walletId;

	@NotEmpty(message = "is not empty")
	@JsonProperty("wallet_ref_id")
	private String walletReferenceId;

	@NotEmpty(message = "is not empty")
	@JsonProperty("source_channel")
	private String sourceChannel;

	@NotEmpty(message = "is not empty")
	@JsonProperty("shukran_id")
	private String shukranId;

	@ApiModelProperty(example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|SAR|BHD|OMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@NotBlank(message = " base_currency must not be empty")
	@JsonProperty("base_currency")
	private String baseCurrency;

	@Min(value = 1)
	@Max(value = 200)
	@JsonProperty("no_of_days_extend")
	private Integer numberOfDaysExtend;

	private String language ="ENGLISH";

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	/**
	 * @return the walletId
	 */
	public String getWalletId() {
		return walletId;
	}

	/**
	 * @param walletId the walletId to set
	 */
	public void setWalletId(String walletId) {
		this.walletId = walletId;
	}

	/**
	 * @return the walletReferenceId
	 */
	public String getWalletReferenceId() {
		return walletReferenceId;
	}

	/**
	 * @param walletReferenceId the walletReferenceId to set
	 */
	public void setWalletReferenceId(String walletReferenceId) {
		this.walletReferenceId = walletReferenceId;
	}

	/**
	 * @return the sourceChannel
	 */
	public String getSourceChannel() {
		return sourceChannel;
	}

	/**
	 * @param sourceChannel the sourceChannel to set
	 */
	public void setSourceChannel(String sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	/**
	 * @return the shukranId
	 */
	public String getShukranId() {
		return shukranId;
	}

	/**
	 * @param shukranId the shukranId to set
	 */
	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	/**
	 * @return the numberOfDaysExtend
	 */
	public Integer getNumberOfDaysExtend() {
		return numberOfDaysExtend;
	}

	/**
	 * @param numberOfDaysExtend the numberOfDaysExtend to set
	 */
	public void setNumberOfDaysExtend(Integer numberOfDaysExtend) {
		this.numberOfDaysExtend = numberOfDaysExtend;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

}
