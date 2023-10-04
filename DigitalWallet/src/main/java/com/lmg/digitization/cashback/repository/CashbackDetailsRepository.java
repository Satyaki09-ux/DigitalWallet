package com.lmg.digitization.cashback.repository;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lmg.digitization.cashback.entity.CashbackDetails;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;

@Repository
public interface CashbackDetailsRepository extends JpaRepository<CashbackDetails, Long> {


	
	Optional<CashbackDetails> findByShukranIdAndBaseCurrency(String shukranId, String currency);
	@Query(value = "SELECT SUM(BALANCE) from LMG_CASHBACK_DETAILS WHERE SHUKRAN_ID=?1 and CURRENCY=?2", nativeQuery = true)
	BigDecimal selectTotals(String shukranId, String currency);
	
	@Query(value = "select count(*) as total from lmg_cashback_entry where SHUKRAN_ID=?1 and RETURN_ORDER_REF_NO=?2 and TRANSACTION_ID=?3", nativeQuery = true)
	BigDecimal getSameTransactionNumber(String shukranId, String orderReferenceNo,String transactionId );
	
}

