package com.lmg.digitization.digital.wallet.service;

import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.LANGUAGE;

import java.util.Objects;

import javax.transaction.Transactional;

import com.google.json.JsonSanitizer;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletTransactionModel;
import com.lmg.digitization.digital.wallet.enums.ErrorCodes;
import com.lmg.digitization.digital.wallet.enums.NotificationType;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.notify.NotificationService;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.digital.wallet.repository.DigitalWalletRepository;
import com.lmg.digitization.digital.wallet.repository.TransactionRepository;
import com.lmg.digitization.digital.wallet.request.ExtendExpiryRequest;
import com.lmg.digitization.digital.wallet.response.ExtendExpiryResponse;
import com.lmg.digitization.digital.wallet.util.ValidationUtils;

@Service
@Transactional
public class ExtendExpiryService {

	public static final Logger LOGGER = ESAPI.getLogger(ExtendExpiryService.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private ValidationUtils validationUtils;

	@Autowired
	private DigitalWalletRepository digitalwalletRepository;

	@Autowired
	private NotificationService notificationService;

	public ExtendExpiryResponse extendExpiry(ExtendExpiryRequest request) {
		Integer maximumDaysAllowed = appProperties.getExtendExpiryMaxDays();
		LOGGER.info(Logger.EVENT_SUCCESS,"DW Expiry for the Wallet Reference Id - {} NumberOfDaysExtend {}, Maximum allowd days {} "+
				JsonSanitizer.sanitize(request.getWalletReferenceId()) + JsonSanitizer.sanitize(String.valueOf(request.getNumberOfDaysExtend()))+" "+ maximumDaysAllowed);
		if (!validationUtils.isValidLanguage(request.getLanguage())) {
			throw new DigitizationException(ErrorCodes.INVALID_FIELD, LANGUAGE);
		}

		if (request.getNumberOfDaysExtend() > 0 && Objects.nonNull(maximumDaysAllowed)
				&& (request.getNumberOfDaysExtend() <= maximumDaysAllowed)) {
			DigitalWalletTransactionModel model = transactionRepository.findById(request.getWalletReferenceId())
					.orElseThrow(() -> new DigitizationException(
							String.format("The wallet reference is not available for the id (%s)",
									request.getWalletReferenceId()),
							DigitalWalletConstants.ERR_INVALID_SHUKRAN_CODE));

			if (Boolean.TRUE.equals(model.getIsExpiryExtended())) {
				throw new DigitizationException(ErrorCodes.ENTRY_ALREADY_EXDENED);
			}
			model.setExpirationDate(model.getExpirationDate().plusDays(request.getNumberOfDaysExtend()));
			model.setIsExpiryExtended(true);
			model.setStatus("EXTENDED");
			transactionRepository.save(model);
			ExtendExpiryResponse response = new ExtendExpiryResponse();
			BeanUtils.copyProperties(model, response);
			response.setStatus(DigitalWalletConstants.SUCCESS);
			DWWalletModel dwModel = digitalwalletRepository
					.findByShukranIdAndBaseCurrency(request.getShukranId(), request.getBaseCurrency())
					.orElseThrow(() -> new DigitizationException(
							String.format(DigitalWalletConstants.WALLET_NOT_AVAILABLE, request.getShukranId(), request.getBaseCurrency()),
							"ERR_INVALID_SHUKRAN"));

			notificationService.sendExpiryExtentionNotification(model, dwModel, NotificationType.EXTEND_WALLET_EXPIRY.toString(),
					request.getLanguage());
			return response;
		} else {
			throw new DigitizationException(ErrorCodes.DW_EXPIRY_EXCEED_MAX_DAYS, String.valueOf(maximumDaysAllowed));
		}
	}
}
