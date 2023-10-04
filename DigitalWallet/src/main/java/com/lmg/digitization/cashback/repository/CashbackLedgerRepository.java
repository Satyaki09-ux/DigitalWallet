package com.lmg.digitization.cashback.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.lmg.digitization.cashback.entity.CashbackLedger;

public interface CashbackLedgerRepository extends JpaRepository<CashbackLedger,String>, AllLedgerRepositoryCustom{

	public CashbackLedger findByCashbackIdAndReferenceNumber(String walletId, String referenceNumber);

	public CashbackLedger findByCashbackIdAndTransactionId(String walletId, String transactionId);

	Optional<CashbackLedger> findTopByOrderNumberAndTransactionIdAndStatusOrderByCreatedDateDesc(String orderNumber,
			String transactioId, String status);

	Optional<CashbackLedger> findTopByOrderNumberOrderByCreatedDateDesc(String orderNumber);

	Optional<CashbackLedger> findTopByShukranIdAndCurrencyAndOrderNumberOrderByCreatedDateDesc(String shukranId, String currency,
			String orderNumber);
	Optional<CashbackLedger> findTopByShukranIdAndCurrencyAndOrderNumberAndTransactionIdOrderByCreatedDateDesc(String shukranId, String currency,
			String orderNumber, String transactionId);

	Optional<CashbackLedger> findTopByShukranIdAndCurrencyOrderByCreatedDateDesc(String shukranId, String currency);
	
	List<CashbackLedger> findAllByShukranIdAndCreatedDateBetweenOrderByCreatedDateDesc(String shukranId,LocalDateTime fromDate,
			LocalDateTime toDate);
	
	public List<CashbackLedger> findAllByStatusInAndExpiryDateLessThan(List<String> status, LocalDate now);

}
