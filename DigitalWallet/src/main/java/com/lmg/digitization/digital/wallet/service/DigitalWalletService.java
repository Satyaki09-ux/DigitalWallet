
package com.lmg.digitization.digital.wallet.service;

import java.net.URISyntaxException;

import org.springframework.stereotype.Service;

import com.lmg.digitization.digital.wallet.request.AccountStatementRequest;
import com.lmg.digitization.digital.wallet.request.ConvertCardRequest;
import com.lmg.digitization.digital.wallet.request.CreateDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.DeleteWalletRequest;
import com.lmg.digitization.digital.wallet.request.IssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.WalletBalanceRequest;
import com.lmg.digitization.digital.wallet.response.CreateDigitalWalletResponse;
import com.lmg.digitization.digital.wallet.response.DeleteWalletResponse;
import com.lmg.digitization.digital.wallet.response.DigitalAccountStatementResponse;
import com.lmg.digitization.digital.wallet.response.IssueDigitalWalletResponse;
import com.lmg.digitization.digital.wallet.response.WalletBalanceResponse;

@Service
public interface DigitalWalletService {
	
	public CreateDigitalWalletResponse createDigitalWallet(CreateDigitalWalletRequest digitalWalletRequest);

	public IssueDigitalWalletResponse issueDigitalWallet(IssueDigitalWalletRequest digitalWalletRequest);

	public WalletBalanceResponse getWalletBalance(String shukranId, WalletBalanceRequest numberOfDays);

	public DeleteWalletResponse deleteDigitalWallet(DeleteWalletRequest request, String shukranId);

	public IssueDigitalWalletResponse convertCardsDigitalWallet(String shukranId,ConvertCardRequest req) throws  URISyntaxException;

	public WalletBalanceResponse getWalletBalanceV2(String shukranId, WalletBalanceRequest numberOfDays);
	
	public DigitalAccountStatementResponse getDigitalAccountStatement(String shukranId, AccountStatementRequest request);
	
}