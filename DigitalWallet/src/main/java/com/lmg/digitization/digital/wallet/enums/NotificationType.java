package com.lmg.digitization.digital.wallet.enums;

public enum NotificationType {
	
	ISSUE_DCN("IssueDCN"), 
	REDEEM_DCN("RedeemDCN"), 
	CREATE_WALLET("CREATE_WALLET"),
	DELETE_WALLET("DeleteWallet"), 
	ISSUE_WALLET("IssueWallet"), 
	REVERT_ISSUE_WALLET("RevertIssueWallet"),
	REDEEM_WALLET("RedeemWallet"), 
	GET_WALLET_BALANCE("GetWalletBalance"),
	REVERT_REDEEM_WALLET("RevertRedeemWallet"),
	EXTEND_WALLET_EXPIRY("ExtendWalletExpiry"), 
	NON_SHUKRAN_TO_SHUKRAN("NonShukranToShukran"),
	MY_CREDIT_TO_DIGITAL_WALLET("MyCreditToDigitalWallet"), 
	PAPER_CREDIT_NOTE_TO_DIGITAL_WALLET("PaperCreditNoteToDigitalWallet"), 
	DCN_TO_DIGITAL_WALLET("DcnToDigitalWallet"),
	SEND_OTP("SendOTP"),
	ISSUE_CASHBACK("ISSUE_CASHBACK"),
	REDEEM_CASHBACK("REDEEM_CASHBACK"),
	REVERT_REDEEM_CASHBACK("REVERT_REDEEM_CASHBACK"),
	EXPIRED_CASHBACK("EXPIRED_CASHBACK");
	
	private final String value;
	
	NotificationType(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
