package com.lmg.digitization.cashback.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lmg.digitization.cashback.entity.CashbackUploadDetails;

public interface CashbackUploadDetailsRepository extends JpaRepository<CashbackUploadDetails, Long> {

	@Query(nativeQuery = true, value = "select id,file_name,cast(upload_date as date) as upload_date,isnull(totalfailure,0) as total_failure,isnull(totalsuccess,0) as total_success from [lmg_promocash_upload_details] upload left join (select count(*) as totalfailure,file_id from lmg_rejection_details group by file_id) reject on upload.id=reject.file_id left join (select count(*) as totalsuccess,file_id from LMG_CASHBACK_SUCCESSFUL_UPLOAD_DETAILS group by file_id) success on upload.id=success.file_id where cast(upload_date as date) between ? and ?")
	List<Map> getSummaryReport(String sDate, String eDate);
}
