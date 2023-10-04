package com.lmg.digitization.digital.wallet.notify;

import static com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants.UNIQUE_REFERENCE_CODE;

import java.net.URI;
import java.time.ZonedDateTime;

import com.google.json.JsonSanitizer;
import org.owasp.esapi.ESAPI;
import org.owasp.esapi.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.lmg.digitization.cashback.request.CashbackNotificationRequest;
import com.lmg.digitization.digital.wallet.enums.ErrorCodes;
import com.lmg.digitization.digital.wallet.exception.DigitizationException;
import com.lmg.digitization.digital.wallet.properties.AppProperties;
import com.lmg.digitization.digital.wallet.request.NotificationEvent;
import com.lmg.digitization.digital.wallet.response.NotificationResponse;
import com.lmg.digitization.digital.wallet.constants.DigitalWalletConstants;

@Component
public class NotificationClient {

	public static final Logger LOGGER = ESAPI.getLogger(NotificationClient.class);

	@Autowired
	private AppProperties appProperties;

	@Autowired
	private RestTemplate restTemplate;

	public void notification(NotificationEvent event) {
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(appProperties.getNotificationEndpointUrl())
				.build().expand(event.getEventType());
		URI uri = uriComponents.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Long epochMilli = ZonedDateTime.now().toInstant().toEpochMilli();
		headers.set(UNIQUE_REFERENCE_CODE, epochMilli.toString());
		LOGGER.info(Logger.EVENT_SUCCESS,"Notification URL : {} and notify URCN: {}"+ uri+" "+ epochMilli);
		HttpEntity<NotificationEvent> request = new HttpEntity<>(event, headers);
		try {
			NotificationResponse notificationResponse = restTemplate
					.exchange(uri, HttpMethod.POST, request, NotificationResponse.class).getBody();
			LOGGER.info(Logger.EVENT_SUCCESS,DigitalWalletConstants.NOTIFICATION_RESPONSE+ notificationResponse);
		} catch (RestClientResponseException ex) {
			LOGGER.error(Logger.EVENT_FAILURE,"Notification sending failed with Http error code:{} and response:{}"+ ex.getRawStatusCode()+" "+
					ex.getResponseBodyAsString());
			throw new DigitizationException(ErrorCodes.NOTIFCATION_FAILURE);
		} catch (RestClientException ex) {
			LOGGER.error(Logger.EVENT_FAILURE,"Notification service call failed"+ ex);
			throw new DigitizationException(ErrorCodes.NOTIFCATION_FAILURE);
		}
	}
	
	public void sendCashbackNotification(CashbackNotificationRequest request) {
		
		UriComponents uriComponents = UriComponentsBuilder.fromUriString(appProperties.getCashbackNotificationServiceEndpointUrl()).build();
		URI uri = uriComponents.toUri();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		Long epochMilli = ZonedDateTime.now().toInstant().toEpochMilli();
		headers.set(UNIQUE_REFERENCE_CODE, epochMilli.toString());
		LOGGER.info(Logger.EVENT_SUCCESS,"Cashback Notification Service URL : {} and notify URC : {}"+ uri+" "+epochMilli);
		HttpEntity<CashbackNotificationRequest> cashbackrequest = new HttpEntity<>(request, headers);
		try {
			NotificationResponse notificationResponse = restTemplate
					.exchange(uri, HttpMethod.POST, cashbackrequest, NotificationResponse.class).getBody();
			 if (null!=notificationResponse)
				 LOGGER.info(Logger.EVENT_SUCCESS,DigitalWalletConstants.NOTIFICATION_RESPONSE.concat(JsonSanitizer.sanitize(notificationResponse.toString()))) ;
			 else
				 LOGGER.info(Logger.EVENT_FAILURE,DigitalWalletConstants.NOTIFICATION_RESPONSE+ null) ;
		} catch (RestClientResponseException ex) {
			LOGGER.error(Logger.EVENT_FAILURE,"Cashback Notification sending failed with Http error code:{} and response:{}"+ ex.getRawStatusCode()+" "+ ex.getResponseBodyAsString());
			throw new DigitizationException(ErrorCodes.NOTIFCATION_FAILURE);
		} catch (RestClientException ex) {
			LOGGER.error(Logger.EVENT_FAILURE,"Cashback Notification service call failed", ex);
			throw new DigitizationException(ErrorCodes.NOTIFCATION_FAILURE);
		}

	}
}
