package com.lmg.digitization.cashback.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmg.digitization.cashback.entity.CashbackRejectionDetails;

public interface CashbackRejectionDetailsRepository extends JpaRepository<CashbackRejectionDetails, Long> {

	List<CashbackRejectionDetails> findAllByFileId(BigDecimal fileId);
}
