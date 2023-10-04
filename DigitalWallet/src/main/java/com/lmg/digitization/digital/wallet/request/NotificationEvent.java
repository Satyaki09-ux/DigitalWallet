package com.lmg.digitization.digital.wallet.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class NotificationEvent {

	@JsonProperty("mobile_no")
	private String mobileNo;
	
	@JsonProperty("email_id")
	private String emailId;
	
	@JsonProperty("event_type")
	private String eventType;
	
	NotificationEventData data;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public NotificationEventData getData() {
		return data;
	}

	public void setData(NotificationEventData data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "NotificationEvent [mobileNo=" + mobileNo + ", emailId=" + emailId + ", notificationType="
				+ eventType + ", data=" + data + ", getMobileNo()=" + getMobileNo() + ", getEmailId()="
				+ getEmailId() + ", getNotificationType()=" + getEventType() + ", getData()=" + getData()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
	
}
