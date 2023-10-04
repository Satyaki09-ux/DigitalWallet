package com.lmg.digitization.digital.wallet.service;

import java.util.List;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;
import com.lmg.digitization.digital.wallet.enums.ErrorCodes;
import com.lmg.digitization.digital.wallet.enums.Source;
import com.lmg.digitization.digital.wallet.enums.Status;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.mapper.TransactionResponseMapper;
import com.lmg.digitization.digital.wallet.repository.DigitalWalletRepository;
import com.lmg.digitization.digital.wallet.repository.DigitizationLedgerRepository;
import com.lmg.digitization.digital.wallet.response.TransactionDetail;
import com.lmg.digitization.digital.wallet.response.WalletTransactionsResponse;

@Service
public class DWTransactionsService {

	@Autowired
	private DigitizationLedgerRepository ledgerRepository;

	@Autowired
	private DigitalWalletRepository digitalWalletRepository;

	@Autowired
	private TransactionResponseMapper mapper;

	public WalletTransactionsResponse searchTransactions(String shukranId, String currency, Integer noofdays,
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

		Page<DigitizationLedger> search = ledgerRepository.search(shukranId, currency, noofdays, pagenumber, size, status,
				concept, source);

		WalletTransactionsResponse response = new WalletTransactionsResponse();
		digitalWalletRepository.findByShukranIdAndBaseCurrency(shukranId, currency).ifPresent(wallet -> {
			response.setCurrency(wallet.getBaseCurrency());
			response.setWalletBalance(wallet.getBalanceAmount());
		});
		response.setNoOfDays(noofdays);
		response.setPageNumber(pagenumber);
		response.setPageSize(size);
		response.setTotalPages(search.getTotalPages());
		response.setShukranId(shukranId);
		response.setTotalTransactions(search.getNumberOfElements());
		List<TransactionDetail> details = mapper.map(search.getContent());
		response.setTransactions(details);
		return response;
	}

}
