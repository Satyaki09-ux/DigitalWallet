package com.lmg.digitization.cashback.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.lmg.digitization.cashback.entity.CashbackSuccessfulUploadDetails;
public interface CashbackSuccessfulFileUploadRepository extends JpaRepository<CashbackSuccessfulUploadDetails, Long> {

}
