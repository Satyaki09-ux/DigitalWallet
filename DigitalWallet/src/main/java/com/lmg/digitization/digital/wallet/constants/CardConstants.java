package com.lmg.digitization.digital.wallet.constants;

public class CardConstants {
	private CardConstants() {
		super();
	}

	public static final String SUCCESS = "SUCCESS";
	public static final String FAILURE = "FAILURE";
	public static final String ALREADYEXISTS = "ALREADY EXISTS";
	public static final String INVALID = "COUPON DOES NOT EXIST";
	public static final String EXPIRED = "COUPON EXPIRED";
	public static final String REDEEMED = "COUPON ALREADY REDEEMED";
	public static final String INSUFFICIENTBALANCE = "INSUFFICIENT BALANCE";
	public static final String INVALIDCURRENCY = "INVALID CURRENCY";
	public static final String EMPLOYEENOTFOUND = "EMPLOYEE NOT FOUND";
	public static final String ERROR = "CARD ERROR";
	public static final String AUTH = "Authorization";
	public static final String XCLIENT="x-ibm-client-id";
	public static final String ACCEPTTYPE="application/json";
	public static final String CARDLITERALS="{cardNumber}";
	public static final String BEARERLITERALS="Bearer ";
}
