package com.lmg.digitization.digital.wallet.mapper;

import com.lmg.digitization.digital.wallet.request.CreateDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.IssueDigitalWalletRequest;

public interface Converter {
	CreateDigitalWalletRequest convert(IssueDigitalWalletRequest sourceRequest);

	IssueDigitalWalletRequest convert(CreateDigitalWalletRequest soruceRequest);
}