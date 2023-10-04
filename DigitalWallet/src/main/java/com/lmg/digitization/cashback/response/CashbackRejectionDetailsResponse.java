package com.lmg.digitization.cashback.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.cashback.entity.CashbackRejectionDetails;

public class CashbackRejectionDetailsResponse{

	@JsonProperty("exception_details")
	List<CashbackRejectionDetails> cashbackRejectionDetails;

	public List<CashbackRejectionDetails> getCashbackRejectionDetails() {
		return cashbackRejectionDetails;
	}

	public void setCashbackRejectionDetails(List<CashbackRejectionDetails> cashbackRejectionDetails) {
		this.cashbackRejectionDetails = cashbackRejectionDetails;
	}
}
