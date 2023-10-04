package com.lmg.digitization.cashback.request;

import com.fasterxml.jackson.annotation.JsonProperty;


public class CashbackNotificationRequest {
	
	@JsonProperty("customer_mobile")
	private String customerMobile;
	
	@JsonProperty("customer_email")
	private String customerEmail;
	
	@JsonProperty("notification_type")
	private String notificationType;
	
	private CashbackNotificationDetailsModel data;

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public CashbackNotificationDetailsModel getData() {
		return data;
	}

	public void setData(CashbackNotificationDetailsModel data) {
		this.data = data;
	}
	

}
