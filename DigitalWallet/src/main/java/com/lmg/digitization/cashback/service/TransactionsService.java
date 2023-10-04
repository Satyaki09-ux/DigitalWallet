package com.lmg.digitization.cashback.service;

import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lmg.digitization.cashback.entity.AllLedger;
import com.lmg.digitization.cashback.mapper.AllTransactionResponseMapper;
import com.lmg.digitization.cashback.repository.AllLedgerRepository;
import com.lmg.digitization.cashback.repository.CashbackDetailsRepository;
import com.lmg.digitization.cashback.response.AllLedgerTransactionDetail;
import com.lmg.digitization.cashback.response.TransactionsResponse;
import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;
import com.lmg.digitization.digital.wallet.enums.ErrorCodes;
import com.lmg.digitization.digital.wallet.enums.Source;
import com.lmg.digitization.digital.wallet.enums.Status;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.repository.DigitalWalletRepository;
import com.lmg.digitization.digital.wallet.repository.DigitizationLedgerRepository;

@Service
public class TransactionsService {

	@Autowired
	private AllLedgerRepository ledgerRepository;

	@Autowired
	private DigitalWalletRepository digitalWalletRepository;

	@Autowired
	private CashbackDetailsRepository cashbackRepository;
	
	@Autowired
	private AllTransactionResponseMapper mapper;

	public TransactionsResponse searchTransactions(String shukranId, String currency, Integer noofdays,
			Integer pagenumber, Integer size, List<String> status, List<String> concept, List<String> source) {
		if (!CollectionUtils.isEmpty(status)) {
			for (String statusValue : status) {
				if (!EnumUtils.isValidEnum(Status.class, statusValue)) {
					throw new DigitizationException(ErrorCodes.INVALID_FIELD, statusValue);
				}
			}
		}

		if (!CollectionUtils.isEmpty(source)) {
			for (String sourceValue : source) {
				if (!EnumUtils.isValidEnum(Source.class, sourceValue)) {
					throw new DigitizationException(ErrorCodes.INVALID_FIELD, sourceValue);
				}
			}
		}

		Page<AllLedger> search = ledgerRepository.search(shukranId, currency, noofdays, pagenumber, size, status,
				concept, source);

		TransactionsResponse response = new TransactionsResponse();
		digitalWalletRepository.findByShukranIdAndBaseCurrency(shukranId, currency).ifPresent(wallet -> {
			response.setCurrency(wallet.getBaseCurrency());
			response.setWalletBalance(wallet.getBalanceAmount());
		});
		
		cashbackRepository.findByShukranIdAndBaseCurrency(shukranId, currency).ifPresent(cashback -> {
			response.setCashbackBalance(cashback.getBalanceAmount());
		});
		response.setNoOfDays(noofdays);
		response.setPageNumber(pagenumber);
		response.setPageSize(size);
		response.setTotalPages(search.getTotalPages());
		response.setShukranId(shukranId);
		response.setTotalTransactions(search.getNumberOfElements());
		List<AllLedgerTransactionDetail> details = mapper.map(search.getContent());
		response.setTransactions(details);
		return response;
	}

}
