package com.lmg.digitization.cashback.service;


import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

import com.lmg.digitization.cashback.request.FetchWalletBalanceRequest;
import com.lmg.digitization.cashback.request.FileUploadSummaryRequest;
import com.lmg.digitization.cashback.request.SaveCashbackRequestComposite;
import com.lmg.digitization.cashback.response.CashbackRejectionDetailsResponse;
import com.lmg.digitization.cashback.response.ExpireBalanceEntriesResponse;
import com.lmg.digitization.cashback.response.FetchBalanceWithoutReferenceResponse;
import com.lmg.digitization.cashback.response.FetchMonthlyBalanceResponse;
import com.lmg.digitization.cashback.response.FetchWalletBalanceCompositeResponse;
import com.lmg.digitization.cashback.response.FileUploadSummaryResponse;
import com.lmg.digitization.cashback.response.SaveCashbackCompositeResponse;
import com.lmg.digitization.digital.wallet.response.BaseResponse;


public interface PromocashService {

	SaveCashbackCompositeResponse saveCashback(String sukhranId, SaveCashbackRequestComposite scr);
	BaseResponse upload(MultipartFile file);
	FetchWalletBalanceCompositeResponse fetchWalletBalance(String shukranId, FetchWalletBalanceRequest fwbr);
	FetchMonthlyBalanceResponse fetchMonthlyBalance(String shukranId,  FetchWalletBalanceRequest fwbr);
	FetchBalanceWithoutReferenceResponse fetchBalanceWithoutReference(String shukranId,
			 FetchWalletBalanceRequest fwbr);
	FileUploadSummaryResponse getFileUploadSummary(FileUploadSummaryRequest request);
	CashbackRejectionDetailsResponse getCashbackRejectionDetails(BigDecimal fileId);
	ExpireBalanceEntriesResponse expireBalanceEntries();
}
