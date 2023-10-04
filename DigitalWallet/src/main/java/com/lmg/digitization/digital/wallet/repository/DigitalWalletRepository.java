
package com.lmg.digitization.digital.wallet.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmg.digitization.digital.wallet.entity.DWWalletModel;

public interface DigitalWalletRepository extends JpaRepository<DWWalletModel, String> {

	Optional<DWWalletModel> findByShukranIdAndBaseCurrency(String shukranId, String currency);

	Optional<DWWalletModel> findByShukranId(String shukranId);
	
	@Override
	Optional<DWWalletModel> findById(String id);
	
	@Query(nativeQuery = true, value = "select 1 from lmg_digital_wallet where shukran_id=?1 and base_currency=?2")
	Integer isWalletExist(String shukhranId,String currency);
	
}
