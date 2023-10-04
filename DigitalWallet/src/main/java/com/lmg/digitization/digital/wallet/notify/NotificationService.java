package com.lmg.digitization.digital.wallet.notify;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.lmg.digitization.cashback.request.CashbackNotificationRequest;
import com.lmg.digitization.digital.wallet.entity.DWWalletModel;
import com.lmg.digitization.digital.wallet.entity.DigitalCreditNote;
import com.lmg.digitization.digital.wallet.entity.DigitalWalletTransactionModel;
import com.lmg.digitization.digital.wallet.entity.OTPModel;
import com.lmg.digitization.digital.wallet.mapper.NotificationEventMapper;
import com.lmg.digitization.digital.wallet.request.IssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.NotificationEvent;
import com.lmg.digitization.digital.wallet.request.RedeemDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.RevertIssueDigitalWalletRequest;
import com.lmg.digitization.digital.wallet.request.RevertRedeemOnInvoiceRequest;

@Service
public class NotificationService {

	@Autowired
	private NotificationClient notificationClient;

	@Autowired
	private NotificationEventMapper notificationEventMapper;


	@Async
	public void sendNotificationCreateIssue(DWWalletModel model, IssueDigitalWalletRequest req, String notificationType) {
		NotificationEvent event = notificationEventMapper.issueNotificationEventMapper(model, req, notificationType);
		notificationClient.notification(event);
	}

	@Async
	public void sendNotificationRedeem(DWWalletModel model, RedeemDigitalWalletRequest request,
			String notificationType) {
		NotificationEvent event = notificationEventMapper.redeemNotificationEventMapper(model, request,
				notificationType);
		notificationClient.notification(event);
	}

	@Async
	public void sendNotification(DWWalletModel model, RevertRedeemOnInvoiceRequest req, String notificationType) {
		NotificationEvent event = notificationEventMapper.notificationRevertRedeemEventMapper(model, req, notificationType);
		notificationClient.notification(event);
	}

	@Async
	public void sendNotification(DWWalletModel model, RevertIssueDigitalWalletRequest req, String notificationType) {
		NotificationEvent event = notificationEventMapper.notificationRevertIssueEventMapper(model, req, notificationType);
		notificationClient.notification(event);
	}

	@Async
	public void sendNotification(DWWalletModel model, String notificationType) {
		NotificationEvent event = notificationEventMapper.notificationEventMapper(model, notificationType);
		notificationClient.notification(event);
	}
	@Async
	public void sendNotificationConvertion(DWWalletModel model, DigitalCreditNote dcn, String notificationType, String language) {
		NotificationEvent event = notificationEventMapper.notificationEventMapperConvertion(model, dcn, notificationType, language);
		notificationClient.notification(event);
	}

	@Async
	public void sendExpiryExtentionNotification(DigitalWalletTransactionModel model, DWWalletModel dwModel,
			String notificationType, String language) {
		NotificationEvent event = notificationEventMapper.extendedExpiryMapper(model, dwModel, notificationType, language);
		notificationClient.notification(event);
	}
	@Async
	public void sendOTP(OTPModel model, String notificationType, String language) {
		NotificationEvent event = notificationEventMapper.otpEventMapper(model, notificationType, language);
		notificationClient.notification(event);
	}
	@Async
	public void sendNotificationForCashback(CashbackNotificationRequest request) {
		notificationClient.sendCashbackNotification(request);
		
		
	}
}
