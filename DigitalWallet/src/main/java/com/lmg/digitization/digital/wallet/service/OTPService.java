package com.lmg.digitization.digital.wallet.service;

import java.time.LocalDateTime;
import java.util.Optional;

import com.google.json.JsonSanitizer;
import org.apache.commons.lang3.StringUtils;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.OTPModel;
import com.lmg.digitization.digital.wallet.enums.NotificationType;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.notify.NotificationService;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.digital.wallet.repository.DigitalWalletRepository;
import com.lmg.digitization.digital.wallet.repository.OTPRepository;
import com.lmg.digitization.digital.wallet.request.OTPRequest;
import com.lmg.digitization.digital.wallet.request.OTPValidationRequest;
import com.lmg.digitization.digital.wallet.response.OTPResponse;
import com.lmg.digitization.digital.wallet.util.Utility;

@Service
@Transactional
public class OTPService {
	public static final Logger LOGGER = ESAPI.getLogger(OTPService.class.getName());
	@Autowired
	private OTPRepository otpRepository;

	@Autowired
	private DigitalWalletRepository digitalwalletRepository;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private AppProperties appProperties;

	public OTPResponse generateOTP(String shukranId, OTPRequest request) {
		OTPResponse response = new OTPResponse();
		DWWalletModel model = null;
		Optional<DWWalletModel> modelopt = digitalwalletRepository.findByShukranIdAndBaseCurrency(shukranId, request.getCurrency());
		if (!modelopt.isPresent()) {
			throw new DigitizationException(DigitalWalletConstants.INVALID_SHUKRAN_CODE, DigitalWalletConstants.INVALID_SHUKRAN_MSG);
		}
		model = modelopt.get();
		OTPModel otpm = otpRepository.findByShukranIdAndOtpTypeAndTransactionIdAndIsExpired(shukranId, request.getOtpType(),
				request.getTransactionId(), false);
		// update Customer Info Mobile and email if there is change in Shukran
		this.updateCustomerInfoIfChanged(request, model);
		if (null == otpm) {
			otpm = this.insertOTP(shukranId, request, model);
			response.setMessage(DigitalWalletConstants.GENERATED_OTP_MSG);
			response.setShukranId(shukranId);
		} else {
			if (otpm.getOtpExpiration().isBefore(LocalDateTime.now())) {
				this.saveOTP(otpm, true);
				otpm = this.insertOTP(shukranId, request, model);
			} else {
				this.saveOTP(otpm, false);
			}
			response.setMessage(DigitalWalletConstants.GENERATED_OTP_MSG);
			response.setShukranId(shukranId);

		}
		notificationService.sendOTP(otpm, NotificationType.SEND_OTP.toString(), request.getLanguage());
		return response;
	}

	public OTPModel insertOTP(String shukranId, OTPRequest request, DWWalletModel model) {
		OTPModel otp = new OTPModel();
		try {
			otp.setCreateDate(LocalDateTime.now());
			otp.setCustomerName(model.getCustomerName());
			otp.setIsExpired(false);
			otp.setIsRegenarated(false);
			otp.setIsValidated(false);
			otp.setMobileNumber(model.getMobileNumber());
			otp.setCustomerEmail(model.getEmailId());
			otp.setOtp(Utility.getRandomNumberString());
			otp.setOtpExpiration(LocalDateTime.now().plusMinutes(appProperties.getOtpExpiryMins()));
			otp.setOtpType(request.getOtpType());
			otp.setShukranId(shukranId);
			otp.setTransactionId(request.getTransactionId());
			otp.setConcept(request.getConcept());
			otp.setCurrency(request.getCurrency());
			otp.setAmount(request.getAmount());
			otpRepository.save(otp);

		}

		catch (Exception ex) {
			throw new DigitizationException(DigitalWalletConstants.OTP_FAILED, ex.getMessage(), ex);
		}
		return otp;
	}

	public OTPModel saveOTP(OTPModel otpm, boolean expired) {
		try {
			if (expired) {
				otpm.setIsExpired(expired);
			} else {
				otpm.setIsRegenarated(true);
				otpm.setOtpExpiration(LocalDateTime.now().plusMinutes(appProperties.getOtpExpiryMins()));
				otpm.setModifiedDate(LocalDateTime.now());
			}
			otpRepository.save(otpm);
		}

		catch (Exception ex) {
			throw new DigitizationException(DigitalWalletConstants.OTP_FAILED, ex.getMessage(), ex);
		}
		return otpm;
	}

	public OTPResponse validateOTP(String shukranId, OTPValidationRequest request) {

		OTPModel model = otpRepository.findByShukranIdAndOtpTypeAndOtpAndTransactionIdAndIsExpired(shukranId,
				request.getOtpType(), request.getOtp(), request.getTransactionId(), false);
		OTPResponse response = new OTPResponse();
		if (null == model) {
			response.setMessage(DigitalWalletConstants.WRONG_OTP_MSG);
			response.setShukranId(shukranId);
		} else {
			if (model.getOtpExpiration().isBefore(LocalDateTime.now())) {
				response.setMessage(DigitalWalletConstants.EXPIRED_OTP_MSG);
				response.setShukranId(shukranId);
			} else {
				model.setIsValidated(true);
				model.setValidationDate(LocalDateTime.now());
				model.setIsExpired(true);
				model.setModifiedDate(LocalDateTime.now());
				otpRepository.save(model);
				response.setMessage(DigitalWalletConstants.VALIDATED_OTP_MSG);
				response.setShukranId(shukranId);

			}

		}

		return response;
	}

	/**
	 *
	 * @param request
	 * @param customerWallet
	 *
	 *                       This method is for update customer info when there is
	 *                       change in email and mobile on a OTP request
	 */
	private void updateCustomerInfoIfChanged(OTPRequest request, DWWalletModel customerWallet) {
		boolean isMobileOREmailUpdated = false;
		if (StringUtils.isNotBlank(request.getMobileNumber()) && !request.getMobileNumber().equals(customerWallet.getMobileNumber())) {
			customerWallet.setMobileNumber(request.getMobileNumber());
			isMobileOREmailUpdated = true;
		}
		if (StringUtils.isNotBlank(request.getEmailId()) && !request.getEmailId().equals(customerWallet.getEmailId())) {
			customerWallet.setEmailId(request.getEmailId());
			isMobileOREmailUpdated = true;
		}

		if (isMobileOREmailUpdated) {
			digitalwalletRepository.save(customerWallet);
			LOGGER.info(Logger.EVENT_SUCCESS,"Customer Mobile and Email information is updated for ShukranId: {}"+" "+ JsonSanitizer.sanitize(customerWallet.getShukranId()));
		}

	}

}
