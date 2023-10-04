package com.lmg.digitization.digital.wallet.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lmg.digitization.cashback.entity.AllLedger;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletCashbackTransactionModel;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletTransactionModel;

@Repository
public interface TransactionRepository extends JpaRepository<DigitalWalletTransactionModel, String> {

	List<DigitalWalletTransactionModel> findAllByWalletIDAndStatusInAndExpirationDateBetweenOrderByExpirationDateAsc(String walletID,
			List<String> status, LocalDate fromDate,
			LocalDate toDate);

	List<DigitalWalletTransactionModel> findAllByWalletIDAndStatusInOrderByExpirationDateAsc(String walletID,
			List<String> status);

	DigitalWalletTransactionModel findAllByWalletReferenceId(String walletReferenceId);

	List<DigitalWalletTransactionModel> findAllByRedeemInvoice(String redeemInvoice);

	DigitalWalletTransactionModel findTopByIssuedInvoiceOrderByCreateDateDesc(String issuedInvoice);
	
	@Query(nativeQuery = true, value = "select '' as Push_text,'' as SMS_TEXT,'' REDEEMABLE_START_DATE,EXPIRATION_DATE,'' as Campaign_name,'' as Applicability, 'digital_wallet' as TYPE,SHUKRAN_ID,WALLET_ID as WALLET_CASHBACK_ID,WALLET_REFERENCE_ID as WALLET_CASHBACK_REFERENCE_ID,CREATED_DATE,BALANCE_AMOUNT,ISSUED_AMOUNT,BASE_CURRENCY,'99' as redeemable_concept_code,redeemed_amount,status,version from LMG_DIGITAL_WALLET_ENTRY where SHUKRAN_ID=? and STATUS in ('ISSUED','EXTENDED') and cast(EXPIRATION_DATE as date)>=cast(getdate() as date) union all select push_text,sms_text,REDEEMABLE_START_DATE, EXPIRATION_DATE, Campaign_name, Applicability,'cashback' as TYPE,SHUKRAN_ID,CASHBACK_ID as WALLET_CASHBACK_ID,CASHBACK_REFERENCE_ID as WALLET_CASHBACK_REFERENCE_ID,CREATED_DATE,BALANCE_AMOUNT,ISSUED_AMOUNT,BASE_CURRENCY,redeemable_concept_code,redeemed_amount,status,version from lmg_cashback_entry where SHUKRAN_ID=? and STATUS in('ISSUED','EXTENDED') and (redeemable_concept_code ='99' or  redeemable_concept_code=?) and cast(EXPIRATION_DATE as date)>=cast(getdate() as date) order by EXPIRATION_DATE")
	List<Map> getTransactionReferences(String shukranId, String sukhranId1, String redeemableCocept);

	List<DigitalWalletTransactionModel> findAllByWalletIDAndStatusIn(String walletId, List<String> status);
	
	@Query(nativeQuery = true, value = "select SHUKRAN_ID as shukran_Id,ledger_id as transaction_Id, order_number as order_Number,amount,currency,concept,cast(dc_dy_bsn as date) as business_Date,status,upper(TRN_SRC) AS source,ref_no as reference_Number,STR_CST_CENTER as store, source_ref as source_Reference,cast(expiry_date as date) expiry_Date,CONVERT(varchar(50), create_date , 127) as create_Date, '' as redeemable_Concept,'WALLET' as instrument_Type from LMG_DIGITIZATION_LEDGER where shukran_id=? union all select SHUKRAN_ID as shukran_Id,cashback_ledger_id as transaction_Id, order_number as order_Number,amount,currency,concept,cast(dc_dy_bsn as date) as business_Date,status,upper(TRN_SRC) AS source,REF_NO as reference_Number,STR_CST_CENTER as store, source_ref as source_Reference,cast(expiry_date as date) as expiry_Date,CONVERT(varchar(50), create_date , 127) as create_Date, REDEEMABLE_CONCEPT as redeemable_Concept, 'CASHBACK' as instrument_Type from LMG_CASHBACK_LEDGER where shukran_id=?")
	List<AllLedger> getAllTransactions(String shukranId1,String ShukranId2);
}