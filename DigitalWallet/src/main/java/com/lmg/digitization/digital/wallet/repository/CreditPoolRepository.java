package com.lmg.digitization.digital.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lmg.digitization.digital.wallet.entity.DWCreditPool;

public interface CreditPoolRepository  extends JpaRepository<DWCreditPool,Long>{

}
