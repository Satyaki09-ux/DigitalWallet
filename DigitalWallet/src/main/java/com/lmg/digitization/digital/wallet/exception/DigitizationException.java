package com.lmg.digitization.digital.wallet.exception;

import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.FIELD_NAME;

import com.lmg.digitization.digital.wallet.enums.ErrorCodes;

public class DigitizationException extends RuntimeException {

	private static final long serialVersionUID = 549397557660196883L;

	private final String errorMessage;
	private final String errorCode;

	public DigitizationException(String message, String errorCode, Throwable cause) {
		super(message, cause);
		this.errorCode = errorCode;
		errorMessage = message;
	}

	public DigitizationException(String message, String errorCode) {
		super(message, new Throwable(message));
		this.errorCode = errorCode;
		errorMessage = message;
	}

	public DigitizationException(ErrorCodes error) {
		super(error.getMessage());
		errorCode = error.getCode();
		errorMessage = error.getMessage();
	}

	public DigitizationException(ErrorCodes error, String fieldName) {
		super(error.getMessage());
		errorCode = error.getCode();
		errorMessage = error.getMessage().replace(FIELD_NAME, fieldName);
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public String getErrorCode() {
		return errorCode;
	}

}