package com.lmg.digitization.digital.wallet.controller;

import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.FAILURE;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.enums.ErrorCodes;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.response.BaseResponse;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

	public static final Logger LOG = ESAPI.getLogger(ExceptionController.class);

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<BaseResponse> exception(Exception ex) {
		LOG.error(Logger.EVENT_FAILURE,"Application Exception on request unable to process", ex.getCause());
		List<String> errorCode = new ArrayList<>();
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatus(DigitalWalletConstants.FAILURE);
		baseResponse.setErrorMessage(ex.getMessage());
		baseResponse.setErrorCode(errorCode);
		return new ResponseEntity<>(baseResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(DigitizationException.class)
	public ResponseEntity<BaseResponse> handleSSResourceNotFoundException(DigitizationException ex) {
		LOG.error(Logger.EVENT_FAILURE,"Bussiness Exception unable to process request errorCode: {}, errorMessage: {}"+ ex.getErrorCode() + ex.getErrorMessage());
		BaseResponse baseResponse = new BaseResponse();
		List<String> errorCode = new ArrayList<>();
		errorCode.add(ex.getErrorCode());
		baseResponse.setStatus(DigitalWalletConstants.FAILURE);
		baseResponse.setErrorMessage(ex.getErrorMessage());
		baseResponse.setErrorCode(errorCode);
		return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		BaseResponse baseResponse = new BaseResponse();
		List<String> errorCode = new ArrayList<>();
		errorCode.add(DigitalWalletConstants.ERROR_CODE_INVALID_PARAMETER);
		baseResponse.setStatus(DigitalWalletConstants.FAILURE);
		baseResponse.setErrorCode(errorCode);
		// Get all errors
		String errorMessages = ex
				.getBindingResult().getFieldErrors().stream().map(fieldError -> new StringBuilder("[")
						.append(fieldError.getField()).append("] ").append(fieldError.getDefaultMessage()).toString())
				.collect(Collectors.joining(","));
		baseResponse.setErrorMessage(errorMessages);

		return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		LOG.error(Logger.EVENT_FAILURE,"JsonParseException Exception unable to process request", ex);
		BaseResponse baseResponse = new BaseResponse();
		List<String> errorCode = new ArrayList<>();
		errorCode.add(ErrorCodes.INVALID_JSON.getCode());
		baseResponse.setStatus(FAILURE);
		baseResponse.setErrorMessage(ErrorCodes.INVALID_JSON.getMessage());
		baseResponse.setErrorCode(errorCode);
		return new ResponseEntity<>(baseResponse, HttpStatus.BAD_REQUEST);
	}
}