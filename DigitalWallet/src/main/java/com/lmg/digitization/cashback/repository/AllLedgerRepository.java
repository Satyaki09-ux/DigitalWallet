package com.lmg.digitization.cashback.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.lmg.digitization.cashback.entity.AllLedger;
public interface AllLedgerRepository extends JpaRepository<AllLedger, Long>, AllLedgerRepositoryCustom {

}
