
package com.lmg.digitization.digital.wallet.constants;

public class DigitalWalletConstants {

	private DigitalWalletConstants() {
		super();
	}

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	public static final String FAILED_TO_CREATE_DIGITAL_WALLET = "Failed to create digital wallet";
	public static final String FAILED_TO_ISSUE_DIGITAL_WALLET = "Failed to issue digital wallet";
	public static final String FAILED_TO_FETCH_RECORD_MSSG = "Failed to fetch the records";
	public static final String FAILED_TO_ISSUE_DIGITAL_WALLET_CODE = "FAILED_TO_ISSUE";
	public static final String FAILED_TO_CREATE_DIGITAL_WALLET_CODE = "FAILED_TO_CREATE";

	public static final String FAILED_TO_FETCH_RECORD_CODE = "FETCH_RECORD_FAILED";
	public static final String ACTIVE_WALLET_STATUS = "Active";
	public static final String INACTIVE_WALLET_STATUS = "Inactive";
	public static final String SUSPENDED_WALLET_CODE = "SUSPENDED";
	public static final String WALLET_ISSUED = "Wallet issued successfully";
	public static final String WALLET_CREATED = "Wallet created successfully";
	public static final String WALLET_ALREADY_CREATED = "Wallet is already available against this Sukhran Id";
	public static final String FAILED_TO_UPDATE_RECORD_CODE = "UPDATE_RECORD_FAILED";
	public static final String WALLET_ALREADY_DELETED_MSSG = "The wallet is already deleted";
	public static final String WALLET_ALREADY_DELETED_CODE = "WALLET_ALREADY_DELETED";
	public static final String WALLET_ALREADY_REVERTED_CODE = "WALLET_ALREADY_REVERTED";
	public static final String WALLET_ALREADY_SUSPENDED_MSSG = "The wallet is already suspended";
	public static final String WALLET_ALREADY_SUSPENDED_CODE = "WALLET_ALREADY_SUSPENDED";
	public static final String NO_WALLET_FOUND_FOR_SHUKRAN_MSSG = "No Wallet found for this Shukran Member";
	public static final String NO_WALLET_FOUND_FOR_SHUKRAN_CODE = "NO_WALLET_FOUND";
	public static final String OPENING_PARENTHSESIS = "(";
	public static final String CLOSING_PARENTHESIS = ")";
	public static final String INVALID_REQUEST_PARAMETER_MSSG = "Invalid request parameter ";
	public static final String INVALID_REQUEST_PARAETER_CODE = "INVALID_REQUEST_PARAMETER";
	public static final String REVERTED_ISSUE_SUCCESS_MSG = "Issued wallet reverted successfully";
	public static final String ERR_INVALID_SHUKRAN_CODE = "ERR_INVALID_SHUKRAN";
	public static final String WALLET_NOT_AVAILABLE = "The wallet is not available for the shukran id (%s) and currency (%s)";
	public static final String ERR_INVALID_WALLET_ID_CODE = "ERR_INVALID_WALLET_ID";
	public static final String ERR_INVALID_WALLET_REFERENCE_ID_CODE = "ERR_INVALID_WALLET_REFERENCE_ID";
	public static final String ERR_WALLET_ALREADY_CREATED_CODE = "ERR_WALLET_ALREADY_CREATED";

	public static final String FIELD_NAME = "fieldName";
	public static final String ACTIVE_WALLET_CODE = "Active";
	public static final String INACTIVE_WALLET_CODE = "Inactive";
	public static final String DELETED = "DELETED";
	public static final String WALLET_DELETED_SUCCESSFULLY = "Wallet deleted successfully";
	public static final String FAILED_TO_UPDATE_RECORD_MSSG = "Failed to update the records";
	public static final String ERROR_CODE_INVALID_PARAMETER = "ERROR_INVALID_PARAMETER";

	public static final String GENERATED_OTP_MSG = "OTP generated successfully";
	public static final String VALIDATED_OTP_MSG = "OTP validated successfully";
	public static final String WRONG_OTP_MSG = "Invalid OTP";
	public static final String EXPIRED_OTP_MSG = "OTP is already expired";
	public static final String OTP_FAILED = "OTP_FAILED";
	public static final String CANCELLED_ISSUE_STATUS = "Reverted";

	public static final String INVALID_SHUKRAN_CODE = "INVALID_SHUKRAN_ID";
	public static final String INVALID_SHUKRAN_MSG = "Shukran Id does not exist";
	public static final String FUNCTIONAL = "FUNCTIONAL";
	public static final String LANGUAGE = "language";

	public static final String OTP_WALLET_SUBJECTLINE = "Your Secrect Code for Redeem Wallet";
	public static final String CREATE_SUBJECTLINE = "Congrats! Your Shukran Pay Wallet is created";
	public static final String ISSUE_SUBJECTLINE = "Your Shukran Pay Wallet is updated";
	public static final String REDEEMED_SUBJECTLINE = "Your Shukran Pay Wallet is updated";
	public static final String REVERTED_REDEEMED_SUBJECTLINE = "Your Shukran Pay Wallet is updated";
	public static final String EXTENDED_WALLET_SUBJECTLINE = "Important notification about your Shukray Pay Wallet";

	public static final String UNIQUE_REFERENCE_CODE = "UniqueReferenceCode";
	public static final String CONCEPT = "concept";
	public static final String STORE = "store";
	public static final String CHANNEL = "channel";
	public static final String APPLICATION_NAME = "Digital-Wallet-Services";
	
	public static final String NOTIFICATION_RESPONSE ="Notification Response : {}";
	
	public static final String DATE_FORMAT="yyyy-MM-dd HH:mm:ss";

}
