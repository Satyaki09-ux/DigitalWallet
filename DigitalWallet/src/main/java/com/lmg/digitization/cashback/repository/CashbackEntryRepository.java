package com.lmg.digitization.cashback.repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lmg.digitization.cashback.entity.CashbackEntry;



@Repository
public interface CashbackEntryRepository extends JpaRepository<CashbackEntry, Long>{

	Optional<CashbackEntry> findByCashbackReferenceId(String cashbackId);

	List<CashbackEntry> findAllByCashbackIdAndStatusInAndExpirationDateBetweenOrderByExpirationDateAsc(String cashbackId,
			List<String> status, LocalDate fromDate,
			LocalDate toDate);
	

	List<CashbackEntry> findAllByCashbackIdAndStatusInOrderByExpirationDateAsc(String cashbackId,
			List<String> status);

	CashbackEntry findAllByCashbackReferenceId(String cashbackReferenceId);

	List<CashbackEntry> findAllByRedeemInvoice(String redeemInvoice);

	CashbackEntry findTopByOrderReferenceNoOrderByCreatedDateDesc(String issuedInvoice);

	List<CashbackEntry> findAllByCashbackIdAndStatusIn(String cashbackId, List<String> status);

	List<CashbackEntry> findAllByCashbackIdAndStatusInAndExpirationDateBetweenAndRedeemableConceptOrderByExpirationDateAsc(
			String cashbackId, List<String> status, LocalDate startDate, LocalDate endDate, String redeemableConcept);
	
	@Query(value = "select isnull(sum(balance_amount),0) as cashbackTotal from LMG_CASHBACK_ENTRY WHERE SHUKRAN_ID=?1 and BASE_CURRENCY=?2 AND status in('ISSUED','EXTENDED') and (redeemable_concept_code ='99' or redeemable_concept_code=?3)", nativeQuery = true)
	BigDecimal selectCashbackTotalConcept(String shukranId, String currency, String redeemableConcept);
	
	@Query(value = "select isnull(sum(balance_amount),0) as cashbackTotal from LMG_CASHBACK_ENTRY WHERE SHUKRAN_ID=?1 and BASE_CURRENCY=?2 AND status in('ISSUED','EXTENDED')", nativeQuery = true)
	BigDecimal selectTotalCashback(String shukranId, String currency);

	List<CashbackEntry> findAllByStatusInAndExpirationDateLessThan(List<String> status, LocalDate now);
}
