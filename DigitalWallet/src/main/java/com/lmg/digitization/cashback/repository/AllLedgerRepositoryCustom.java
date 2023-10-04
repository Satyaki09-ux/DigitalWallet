package com.lmg.digitization.cashback.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lmg.digitization.cashback.entity.AllLedger;
import com.lmg.digitization.cashback.response.AllLedgerTransactionDetail;
import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;

public interface AllLedgerRepositoryCustom {

	Page<AllLedger> search(String shukranId, String currency, Integer noofdays, Integer pagenumber, Integer size,
			List<String> status, List<String> concept, List<String> source);
}
