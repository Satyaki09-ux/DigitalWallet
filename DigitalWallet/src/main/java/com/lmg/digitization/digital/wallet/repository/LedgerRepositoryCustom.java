package com.lmg.digitization.digital.wallet.repository;

import java.util.List;

import org.springframework.data.domain.Page;

import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;

public interface LedgerRepositoryCustom {

	Page<DigitizationLedger> search(String shukranId, String currency, Integer noofdays, Integer pagenumber, Integer size,
			List<String> status, List<String> concept, List<String> source);
}
