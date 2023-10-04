package com.lmg.digitization.digital.wallet.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ConvertCardRequest extends IssueDigitalWalletRequest{

	@NotNull(message = "Card number cannot be blank")
	@JsonProperty("card_number")
	private String cardNumber;
	
	@NotNull(message = "Card Vendor cannot be blank")
	@JsonProperty("card_vendor")
	private String cardVendor;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardVendor() {
		return cardVendor;
	}

	public void setCardVendor(String cardVendor) {
		this.cardVendor = cardVendor;
	}
	
}
