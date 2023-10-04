package com.lmg.digitization.digital.wallet.util;

import org.springframework.stereotype.Component;

import com.lmg.digitization.cashback.constants.PromocashConstants;
import com.lmg.digitization.cashback.entity.CashbackDetails;
import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.DigitalCreditNote;
import com.lmg.digitization.digital.wallet.enums.Source;
import com.lmg.digitization.digital.wallet.enums.Status;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;

@Component
public class DigitizationUtil {

	public void validateDeleteWalletRequest(String shukranId) {
		this.nullCheckStringAndThrowException(shukranId, "Shukran Id");
	}

	private void nullCheckStringAndThrowException(String obj, String name) {
		if (null == obj || obj.isEmpty()) {
			throw new DigitizationException(DigitalWalletConstants.INVALID_REQUEST_PARAMETER_MSSG + name
					+ DigitalWalletConstants.OPENING_PARENTHSESIS + obj + DigitalWalletConstants.CLOSING_PARENTHESIS,
					DigitalWalletConstants.INVALID_REQUEST_PARAETER_CODE,
					new Throwable(DigitalWalletConstants.INVALID_REQUEST_PARAMETER_MSSG + name
							+ DigitalWalletConstants.OPENING_PARENTHSESIS + obj
							+ DigitalWalletConstants.CLOSING_PARENTHESIS));
		}
	}

	public void validateStatus(DigitalCreditNote issueDcn) {
		if (Status.REDEEMED == issueDcn.getStatus()) {
			throw new DigitizationException(String.format("The DCN Number(%s) is already redeemed on %s",
					issueDcn.getDcnId(), issueDcn.getRedemptionDate()), "ERR_REDEMED_ALREADY");
		}

		if (Status.CONVERTED == issueDcn.getStatus()) {
			throw new DigitizationException(String.format("The DCN Number(%s) is already converted on %s",
					issueDcn.getDcnId(), issueDcn.getRedemptionDate()), "ERR_CONVERTED_ALREADY");
		}

		if (Status.EXPIRED == issueDcn.getStatus()) {
			throw new DigitizationException(String.format("The DCN Number(%s) is expired on %s", issueDcn.getDcnId(),
					issueDcn.getRedemptionDate()), "ERR_DCN_EXPIRED");
		}
	}

	public void validateCurrency(String currency, DWWalletModel wallet) {
		if (!wallet.getBaseCurrency().equalsIgnoreCase(currency)) {
			throw new DigitizationException(
					String.format("The Digital Wallet Currency(%s) is not the wallet's base currency(%s)", currency,
							wallet.getBaseCurrency()),
					"ERR_INVALID_CURRENCY");
		}
	}

	public void validateCashbackCurrency(String currency, CashbackDetails cashback) {
		if (!cashback.getBaseCurrency().equalsIgnoreCase(currency)) {
			throw new DigitizationException(
					String.format("The Cashback Currency(%s) is not the wallet's base currency(%s)", currency,
							cashback.getBaseCurrency()),
					"ERR_INVALID_CURRENCY");
		}
	}
	public void validateVersion(Long walletVersion, Long version) {
		if (!walletVersion.equals(version)) {
			throw new DigitizationException("The Digital Wallet has been updated.Please re initiate the Redemption",
					"ERR_INVALID_VERSION");
		}
	}

	public void validateCashbackVersion(Long walletVersion, Long version) {
		if (!walletVersion.equals(version)) {
			throw new DigitizationException("The Cashback has been updated.Please re initiate the Redemption",
					"ERR_INVALID_VERSION");
		}
	}
	public void validateSource(String source) {
		try {
			Source.valueOf(source);
		} catch (Exception ex) {
			throw new DigitizationException(String.format("Invalid Source %s", source), "ERR_INVALID_SOURCE");
		}
	}

	public void validateWallet(DWWalletModel model) {
		if (!DigitalWalletConstants.ACTIVE_WALLET_STATUS.equalsIgnoreCase(model.getWalletStatus())) {
			throw new DigitizationException(String.format("The wallet is already deleted: (%s)", model.getWalletId()),
					DigitalWalletConstants.WALLET_ALREADY_DELETED_CODE);
		}
	}
	
	public void validateCashback(CashbackDetails model) {
		if (!PromocashConstants.ACTIVE_CASHBACK_STATUS.equalsIgnoreCase(model.getCashbackStatus())) {
			throw new DigitizationException(String.format("The cashback is already deleted: (%s)", model.getCashbackId()),
					PromocashConstants.WALLET_ALREADY_DELETED_CODE);
		}
	}
	
}
