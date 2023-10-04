package com.lmg.digitization.digital.wallet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.lmg.digitization.digital.wallet.entity.DigitalCreditNote;

@Repository
public interface DigitalCreditNoteRepository extends JpaRepository<DigitalCreditNote, String> {

}
