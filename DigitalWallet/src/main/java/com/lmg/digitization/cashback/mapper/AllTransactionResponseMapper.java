package com.lmg.digitization.cashback.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.lmg.digitization.cashback.entity.AllLedger;
import com.lmg.digitization.cashback.response.AllLedgerTransactionDetail;


@Mapper
public interface AllTransactionResponseMapper {

	
	@Mapping(source = "concept", target = "concept")
	@Mapping(source = "source", target = "source")
	@Mapping(source = "transaction_id", target = "transaction_id")
	
	AllLedgerTransactionDetail map(AllLedger ledger);

	List<AllLedgerTransactionDetail> map(List<AllLedger> ledger);
}
