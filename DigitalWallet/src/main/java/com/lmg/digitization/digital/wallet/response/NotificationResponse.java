package com.lmg.digitization.digital.wallet.response;

import java.util.List;

public class NotificationResponse {

	private String urc;
	private List<NotifyResponse> notifyResponses;

	public NotificationResponse() {
		super();
	}

	public NotificationResponse(String urc, List<NotifyResponse> notifyResponses) {
		super();
		this.urc = urc;
		this.notifyResponses = notifyResponses;
	}

	public enum NotifyType {
		SMS, EMAIL, PUSH
	}

	public static class NotifyResponse {

		private NotifyType notifyType;
		private String status;
		private String errorMessage;

		public NotifyResponse(NotifyType notifyType, String status, String errorMessage) {
			super();
			this.notifyType = notifyType;
			this.status = status;
			this.errorMessage = errorMessage;
		}

		public NotifyResponse() {
			super();
		}

		public NotifyType getNotifyType() {
			return notifyType;
		}

		public void setNotifyType(NotifyType notifyType) {
			this.notifyType = notifyType;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getErrorMessage() {
			return errorMessage;
		}

		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}

		@Override
		public String toString() {
			return "NotifyResponse [notifyType=" + notifyType + ", status=" + status + ", errorMessage=" + errorMessage
					+ "]";
		}

	}

	public String getUrc() {
		return urc;
	}

	public void setUrc(String urc) {
		this.urc = urc;
	}

	public List<NotifyResponse> getNotifyResponses() {
		return notifyResponses;
	}

	public void setNotifyResponses(List<NotifyResponse> notifyResponses) {
		this.notifyResponses = notifyResponses;
	}

	@Override
	public String toString() {
		return "NotificationResponse [urc=" + urc + ", notifyResponses=" + notifyResponses + "]";
	}

}
