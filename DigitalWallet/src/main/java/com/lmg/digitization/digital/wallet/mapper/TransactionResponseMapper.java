package com.lmg.digitization.digital.wallet.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;
import com.lmg.digitization.digital.wallet.response.TransactionDetail;

@Mapper
public interface TransactionResponseMapper {

	@Mapping(source = "ledgerId", target = "transactionId")
	@Mapping(source = "referenceNumber", target = "walletRef")
	@Mapping(source = "concept", target = "concept")
	@Mapping(source = "source", target = "source")
	@Mapping(source = "businessDate", target = "businessDate")
	TransactionDetail map(DigitizationLedger ledger);

	List<TransactionDetail> map(List<DigitizationLedger> ledger);
}
