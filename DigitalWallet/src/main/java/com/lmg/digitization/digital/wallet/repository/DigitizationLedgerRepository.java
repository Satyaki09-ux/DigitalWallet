package com.lmg.digitization.digital.wallet.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;

public interface DigitizationLedgerRepository extends JpaRepository<DigitizationLedger, Long>, LedgerRepositoryCustom {

	public DigitizationLedger findByWalletIdAndReferenceNumber(String walletId, String referenceNumber);

	public DigitizationLedger findByWalletIdAndTransactionId(String walletId, String transactionId);

	Optional<DigitizationLedger> findTopByOrderNumberAndTransactionIdAndStatusOrderByCreatedDateDesc(String orderNumber,
			String transactioId, String status);

	Optional<DigitizationLedger> findTopByOrderNumberOrderByCreatedDateDesc(String orderNumber);

	Optional<DigitizationLedger> findTopByShukranIdAndCurrencyAndOrderNumberOrderByCreatedDateDesc(String shukranId, String currency,
			String orderNumber);

	Optional<DigitizationLedger> findTopByShukranIdAndCurrencyOrderByCreatedDateDesc(String shukranId, String currency);
	
	List<DigitizationLedger> findAllByShukranIdAndCreatedDateBetweenOrderByCreatedDateDesc(String shukranId,LocalDateTime fromDate,
			LocalDateTime toDate);

}
