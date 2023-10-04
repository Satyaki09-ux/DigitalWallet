package com.lmg.digitization.digital.wallet.mapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.DigitalCreditNote;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletTransactionModel;
import com.lmg.digitization.digital.wallet.entity.OTPModel;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.digital.wallet.request.IssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.NotificationEvent;
import com.lmg.digitization.digital.wallet.request.NotificationEventData;
import com.lmg.digitization.digital.wallet.request.RedeemDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.RevertIssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.RevertRedeemOnInvoiceRequest;
import com.lmg.digitization.digital.wallet.util.ValidationUtils;

@Component
public class NotificationEventMapper {

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private ValidationUtils validationUtils;

	public NotificationEvent issueNotificationEventMapper(DWWalletModel model, IssueDigitalWalletRequest req,
			String notificationType) {
		NotificationEventData data = new NotificationEventData();
		if (notificationType.equals("CREATE_WALLET")) {
			data.setSubjectLine(DigitalWalletConstants.CREATE_SUBJECTLINE.concat(" at ")
					.concat((DateTimeFormatter.ISO_LOCAL_TIME).format(LocalDateTime.now())));
		} else {
			data.setSubjectLine(DigitalWalletConstants.ISSUE_SUBJECTLINE.concat(" at ")
					.concat((DateTimeFormatter.ISO_LOCAL_TIME).format(LocalDateTime.now())));
		}
		data.setIssuedAmount(req.getAmount());
		data.setCurrency(model.getBaseCurrency());
		data.setAvailableAmount(model.getBalanceAmount());
		data.setShukranId(model.getShukranId());
		data.setUserName(model.getCustomerName());
		data.setBusinessDate(req.getTransactionDate());
		data.setComment(req.getTransactionId());
		data.setStoreId(req.getStoreCode());
		data.setExpiryDate(LocalDate.now().plusDays(appProperties.getTransactionExpiryDays()));
		data.setConceptName(validationUtils.getStoreConceptName(req.getConceptCode()));
		data.setWalletId(model.getWalletId());
		data.setOrderNo(req.getInvoiceNumber());
		data.setLanguage(req.getLanguage());

		return this.buildEvent(model, notificationType, data);
	}

	public NotificationEvent redeemNotificationEventMapper(DWWalletModel model, RedeemDigitalWalletRequest request,
			String notificationType) {
		NotificationEventData data = new NotificationEventData();
		data.setSubjectLine(DigitalWalletConstants.REDEEMED_SUBJECTLINE.concat(" at ")
				.concat((DateTimeFormatter.ISO_LOCAL_TIME).format(LocalDateTime.now())));
		data.setRedeemedAmount(request.getRedeemAmount());
		data.setCurrency(model.getBaseCurrency());
		data.setAvailableAmount(model.getBalanceAmount());
		data.setComment(request.getInvoiceNumber());
		data.setUserName(model.getCustomerName());
		data.setConceptName(validationUtils.getStoreConceptName(request.getConcept()));
		data.setOrderNo(request.getInvoiceNumber());
		data.setLanguage(request.getLanguage());
		return this.buildEvent(model, notificationType, data);
	}

	public NotificationEvent notificationRevertRedeemEventMapper(DWWalletModel model, RevertRedeemOnInvoiceRequest req,
			String notificationType) {
		NotificationEventData data = new NotificationEventData();
		data.setSubjectLine(DigitalWalletConstants.ISSUE_SUBJECTLINE.concat(" at ")
				.concat((DateTimeFormatter.ISO_LOCAL_TIME).format(LocalDateTime.now())));

		data.setIssuedAmount(model.getIssuedAmount());
		data.setAvailableAmount(model.getBalanceAmount());
		data.setCreatedDate(model.getCreateDate());
		data.setWalletId(model.getWalletId());
		data.setCurrency(model.getBaseCurrency());
		data.setShukranId(model.getShukranId());
		data.setUserName(model.getCustomerName());
		data.setRedeemedAmount(req.getRedeemedAmount());
		data.setExpiryAmount(model.getExpiredAmount());
		data.setRevertedAmount(req.getRedeemedAmount());
		data.setBusinessDate(model.getCreateDate().toLocalDate());
		data.setComment(req.getInvoiceNumber());
		data.setConceptName(validationUtils.getStoreConceptName(req.getConcept()));
		data.setOrderNo(req.getInvoiceNumber());
		data.setLanguage(req.getLanguage());
		return this.buildEvent(model, notificationType, data);
	}

	public NotificationEvent notificationRevertIssueEventMapper(DWWalletModel model, RevertIssueDigitalWalletRequest req,
			String notificationType) {
		NotificationEventData data = new NotificationEventData();
		data.setSubjectLine(DigitalWalletConstants.ISSUE_SUBJECTLINE.concat(" at ")
				.concat((DateTimeFormatter.ISO_LOCAL_TIME).format(LocalDateTime.now())));

		data.setIssuedAmount(req.getIssuedAmount());
		data.setAvailableAmount(model.getBalanceAmount());
		data.setCreatedDate(model.getCreateDate());
		data.setWalletId(model.getWalletId());
		data.setCurrency(model.getBaseCurrency());
		data.setShukranId(model.getShukranId());
		data.setUserName(model.getCustomerName());
		data.setRedeemedAmount(req.getIssuedAmount());
		data.setExpiryAmount(model.getExpiredAmount());
		data.setRevertedAmount(req.getIssuedAmount());
		data.setBusinessDate(model.getCreateDate().toLocalDate());
		data.setComment(req.getCancelReason());
		data.setConceptName(validationUtils.getStoreConceptName(req.getConcept()));
		data.setOrderNo(req.getInvoiceNumber());
		data.setLanguage(req.getLanguage());

		return this.buildEvent(model, notificationType, data);
	}

	public NotificationEvent notificationEventMapper(DWWalletModel model, String notificationType) {
		NotificationEventData data = new NotificationEventData();
		data.setSubjectLine(DigitalWalletConstants.ISSUE_SUBJECTLINE.concat(" at ")
				.concat((DateTimeFormatter.ISO_LOCAL_TIME).format(LocalDateTime.now())));

		data.setIssuedAmount(model.getIssuedAmount());
		data.setAvailableAmount(model.getBalanceAmount());
		data.setCreatedDate(model.getCreateDate());
		data.setWalletId(model.getWalletId());
		data.setCurrency(model.getBaseCurrency());
		data.setShukranId(model.getShukranId());
		data.setUserName(model.getCustomerName());
		data.setRedeemedAmount(model.getRedemptionAmount());
		data.setExpiryAmount(model.getExpiredAmount());
		data.setRedeemedAmount(model.getRedemptionAmount());
		data.setBusinessDate(model.getCreateDate().toLocalDate());

		return this.buildEvent(model, notificationType, data);
	}

	public NotificationEvent notificationEventMapperConvertion(DWWalletModel model, DigitalCreditNote dcn,
			String notificationType, String language) {
		NotificationEventData data = new NotificationEventData();
		data.setSubjectLine(DigitalWalletConstants.ISSUE_SUBJECTLINE.concat(" at ")
				.concat((DateTimeFormatter.ISO_LOCAL_TIME).format(LocalDateTime.now())));

		data.setIssuedAmount(dcn.getRedeemedAmount());
		data.setCreditNoteNumber(dcn.getDcnId());
		data.setVoucherNo(dcn.getDcnId());
		data.setAvailableAmount(model.getBalanceAmount());
		data.setCreatedDate(model.getCreateDate());
		data.setWalletId(model.getWalletId());
		data.setCurrency(model.getBaseCurrency());
		data.setShukranId(model.getShukranId());
		data.setUserName(model.getCustomerName());
		data.setRedeemedAmount(model.getRedemptionAmount());
		data.setExpiryDate(dcn.getExpirationDate());
		data.setExpiryAmount(dcn.getRedeemedAmount());
		data.setBusinessDate(model.getCreateDate().toLocalDate());
		data.setOrderNo(dcn.getOrderNumber());
		data.setLanguage(language);
		return this.buildEvent(model, notificationType, data);
	}

	public NotificationEvent extendedExpiryMapper(DigitalWalletTransactionModel model, DWWalletModel dwModel,
			String notificationType, String language) {
		NotificationEventData data = new NotificationEventData();
		data.setSubjectLine(DigitalWalletConstants.EXTENDED_WALLET_SUBJECTLINE.concat(" at ")
				.concat((DateTimeFormatter.ISO_LOCAL_TIME).format(LocalDateTime.now())));
		data.setAvailableAmount(model.getBalanceAmount());
		data.setExtendedExpiryDate(model.getExpirationDate());
		data.setCurrency(model.getBaseCurrency());
		data.setAvailableAmount(dwModel.getBalanceAmount());
		data.setBusinessDate(model.getTransactionDate());
		data.setUserName(dwModel.getCustomerName());
		data.setTerritory(model.getTerritoryCode());
		data.setShukranId(model.getShukranId());
		data.setWalletId(model.getWalletID());
		data.setRedeemedAmount(dwModel.getRedemptionAmount());
		data.setComment(model.getTransactionId());
		data.setLanguage(language);
		return this.buildEvent(dwModel, notificationType, data);
	}

	public NotificationEvent otpEventMapper(OTPModel model, String notificationType, String language) {
		NotificationEventData data = new NotificationEventData();

		data.setSubjectLine(DigitalWalletConstants.OTP_WALLET_SUBJECTLINE.concat(" at ")
				.concat((DateTimeFormatter.ISO_LOCAL_TIME).format(LocalDateTime.now())));
		data.setVerificationCode(model.getOtp());
		data.setCurrency(model.getCurrency());
		data.setShukranId(model.getShukranId());
		data.setUserName(model.getCustomerName());
		data.setConceptName(validationUtils.getStoreConceptName(model.getConcept()));
		data.setRedeemedAmount(model.getAmount());
		data.setComment(model.getTransactionId());
		NotificationEvent event = new NotificationEvent();
		event.setMobileNo(model.getMobileNumber());
		event.setEmailId(model.getCustomerEmail());
		event.setEventType(notificationType);
		data.setLanguage(language);
		event.setData(data);
		return event;
	}

	private NotificationEvent buildEvent(DWWalletModel model, String notificationType, NotificationEventData data) {
		NotificationEvent event = new NotificationEvent();
		event.setEmailId(model.getEmailId());
		event.setMobileNo(model.getMobileNumber());
		event.setEventType(notificationType);
		event.setData(data);
		return event;
	}

}
