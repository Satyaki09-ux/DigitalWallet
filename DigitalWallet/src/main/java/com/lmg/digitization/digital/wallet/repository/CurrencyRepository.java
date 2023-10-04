package com.lmg.digitization.digital.wallet.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lmg.digitization.digital.wallet.entity.Currency;



@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

	public Optional<Currency> findByIsoCurrencyCode(String isoCurrencyCode);

}
