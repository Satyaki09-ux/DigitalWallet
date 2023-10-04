package com.lmg.digitization.digital.wallet.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmg.digitization.digital.wallet.entity.DigitizationLedger;
import com.lmg.digitization.digital.wallet.enums.Status;
import com.lmg.digitization.digital.wallet.repository.DigitizationLedgerRepository;
import com.lmg.digitization.digital.wallet.request.IssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.RedeemDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.response.IssueDigitalWalletResponse;
import com.lmg.digitization.digital.wallet.response.RedeemWalletResponse;
import com.lmg.digitization.digital.wallet.response.TransactionResponse;
import com.lmg.digitization.digital.wallet.response.WalletReferences;

@Service
public class DWRetryResponseService {

	@Autowired
	private DigitizationLedgerRepository ledgerRepository;

	public Optional<IssueDigitalWalletResponse> isDWIssueExist(IssueDigitalWalletRequest req) {
		if (StringUtils.isEmpty(req.getInvoiceNumber()) || StringUtils.isEmpty(req.getTransactionId())) {
			return Optional.empty();
		}
		return ledgerRepository.findTopByOrderNumberAndTransactionIdAndStatusOrderByCreatedDateDesc(
				req.getInvoiceNumber(), req.getTransactionId(), Status.ISSUED.toString()).map(this::map);
	}

	public Optional<RedeemWalletResponse> isReqAlreadyRedeemed(String shukranId, RedeemDigitalWalletRequest req) {
		if (StringUtils.isEmpty(req.getInvoiceNumber()) || StringUtils.isEmpty(req.getTransactionId())) {
			return Optional.empty();
		}
		return ledgerRepository
				.findTopByShukranIdAndCurrencyOrderByCreatedDateDesc(shukranId,
						req.getCurrency())
				.filter(ledger -> (req.getInvoiceNumber().equals(ledger.getOrderNumber())
						&& req.getTransactionId().equals(ledger.getTransactionId()) &&
						Status.REDEEMED.toString().equals(ledger.getStatus())))
				.map(this::mapRedeemResponse);
	}

	public IssueDigitalWalletResponse map(DigitizationLedger ledger) {
		IssueDigitalWalletResponse response = new IssueDigitalWalletResponse();
		List<TransactionResponse> list = new ArrayList<>();
		TransactionResponse trnRes = new TransactionResponse();
		response.setBalanceAmount(ledger.getClosingBalance());
		response.setCreateDate(ledger.getCreatedDate());
		response.setShukranId(ledger.getShukranId());
		response.setSourceChannel(ledger.getSource().toString());
		response.setStatus(ledger.getStatus());
		response.setWalletId(ledger.getWalletId());
		BeanUtils.copyProperties(ledger, trnRes);
		list.add(trnRes);
		response.setTransaction(list);
		return response;
	}

	public RedeemWalletResponse mapRedeemResponse(DigitizationLedger ledger) {
		RedeemWalletResponse redeemRes = new RedeemWalletResponse();
		redeemRes.setBalance(ledger.getClosingBalance());
		redeemRes.setWalletId(ledger.getWalletId());
		WalletReferences ref = new WalletReferences();
		ref.setWalletRefenceId(ledger.getReferenceNumber());
		ref.setBalanceAmount(ledger.getAmount());
		ref.setExpirationDate(ledger.getExpiryDate());
		redeemRes.setReferenceReIssued(ref);
		redeemRes.setReferencesRedeemed(null);
		return redeemRes;

	}
}
