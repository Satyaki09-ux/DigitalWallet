package com.lmg.digitization.digital.wallet.enums;

import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.ERROR_CODE_INVALID_PARAMETER;
import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.FUNCTIONAL;

public enum ErrorCodes {

	INVALID_FIELD(FUNCTIONAL, ERROR_CODE_INVALID_PARAMETER, "[fieldName] is In-valid"),
	DW_EXPIRY_EXCEED_MAX_DAYS(FUNCTIONAL, "ERROR_DW_EXTEND_EXPIRY",
			"Wallet entry Requested expiry days must be less than Maxmium allowed days [fieldName]"),
	ENTRY_ALREADY_EXDENED(FUNCTIONAL, "ERROR_ENTRY_ALREADY_EXPIERD",
			"Wallet entry expity date is already extended cannot extend again"),
	INVALID_JSON(FUNCTIONAL, "ERROR_INVALID_JSON_REQUEST", "Invalid JSON"),
	NOTIFCATION_FAILURE(FUNCTIONAL, "ERROR_NOTIFICATION_FAILURE", "Failed to send Notification messages"),
	REVERT_REDEEM_ORDERNUMBER_FAILURE(FUNCTIONAL, "REVERT_REDEEM_ORDERNUMBER_FAILURE", "InvoiceNumber not found"),
	REVERT_REDEEM_FAILURE(FUNCTIONAL, "REVERT_REDEEM_FAILURE", "Invalid Revert Request"),
	REVERT_REDEEM_ALREADY_REVETED(FUNCTIONAL, "REVERT_REDEEM_ORDERNUMBER_ALREADY_REVERTED",
			"REDEEM already reverted for the given Ordernumber"),
	CASHBACK_ALREADY_EXISTS(FUNCTIONAL, "ERR_CASHBACK_ALREADY_ISSUED",
			"Cashback has already provided against the transaction number"),
	REVERT_ISSUE_ORDERNUMBER_FAILURE(FUNCTIONAL, "REVERT_ISSUE_ORDERNUMBER_FAILURE", "InvoiceNumber not found"),
	REVERT_ISSUE_ORDERNUMBER_TRANSACTIONID_FAILURE(FUNCTIONAL, "REVERT_ISSUE_ORDERNUMBER_TRANSACTIONID_FAILURE", "InvoiceNumber/Transaction Id not found"),
	REVERT_ISSUE_FAILURE(FUNCTIONAL, "REVERT_ISSUE_FAILURE", "Invalid Revert Request"),
	REVERT_ISSUE_ALREADY_REVETED(FUNCTIONAL, "REVERT_ISSUE_ORDERNUMBER_ALREADY_REVERTED",
			"Issue wallet already reverted for the given Ordernumber");

	private final String type;
	private final String code;
	private final String message;

	private ErrorCodes(String type, String code, String message) {
		this.type = type;
		this.code = code;
		this.message = message;
	}

	public String getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
