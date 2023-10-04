package com.lmg.digitization.digital.wallet.properties;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

	@Value("${extend.expiry.days.max}")
	private Integer extendExpiryMaxDays;

	@Value("#{${allowed.language}}")
	private List<String> allowedLanguage;

	@Value("${notification.service.endpoint.url}")
	private String notificationEndpointUrl;
	
	@Value("${notification.service.cashback.endpoint.url}")
	private String cashbackNotificationServiceEndpointUrl;

	@Value("${application.default.timezone}")
	private String appTimeZone;

	@Value("${digitalwallet.transaction.expiry.days}")
	private Integer transactionExpiryDays;

	@Value("${digitalwallet.otp.expiry.minutes}")
	private Integer otpExpiryMins;

	@Value("#{${dw.concepts}}")
	private Map<String, String> concepts;

	@Value("#{${dw.hybris.concepts}}")
	private Map<String, String> hybrisConcepts;

	@Value("${employee.validation.endpoint.url}")
	private String emplopyeeValidationUrl;

	@Value("${employee.validation.endpoint.username}")
	private String employeeValidationUserName;

	@Value("${employee.validation.endpoint.password}")
	private String employeeValidationPassword;

	@Value("${ogloba.card.enqiry.url}")
	private String oglobaCardEnquiryUrl;

	@Value("${ogloba.card.redemption.url}")
	private String oglobaCardRedemptionUrl;

	@Value("${ogloba.card.confirmation.url}")
	private String oglobaCardConfirmationUrl;

	@Value("${sts.token.generation.url}")
	private String stsToeknGenerationUrl;

	@Value("${sts.client.id}")
	private String stsClientId;

	@Value("${sts.client.secret}")
	private String stsClientSecret;

	@Value("${sts.grant.type}")
	private String stsGrantType;

	@Value("${sts.redirect_uri}")
	private String stsRedirectUri;

	@Value("${x.ibm.client.id}")
	private String xIbmClientId;

	@Value("${expiry.date}")
	private int expiryDate;

	@Value("${end.date}")
	private int endDate;

	public int getEndDate() {
		return endDate;
	}

	public void setEndDate(int endDate) {
		this.endDate = endDate;
	}

	public int getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(int expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Integer getExtendExpiryMaxDays() {
		return extendExpiryMaxDays;
	}

	public List<String> getAllowedLanguage() {
		return allowedLanguage;
	}

	public String getNotificationEndpointUrl() {
		return notificationEndpointUrl;
	}

	public String getAppTimeZone() {
		return appTimeZone;
	}

	public Integer getTransactionExpiryDays() {
		return transactionExpiryDays;
	}

	public Integer getOtpExpiryMins() {
		return otpExpiryMins;
	}

	public Map<String, String> getConcepts() {
		return concepts;
	}

	public void setConcepts(Map<String, String> concepts) {
		this.concepts = concepts;
	}

	public Map<String, String> getHybrisConcepts() {
		return hybrisConcepts;
	}

	public void setHybrisConcepts(Map<String, String> hybrisConcepts) {
		this.hybrisConcepts = hybrisConcepts;
	}

	public void setExtendExpiryMaxDays(Integer extendExpiryMaxDays) {
		this.extendExpiryMaxDays = extendExpiryMaxDays;
	}

	public void setAllowedLanguage(List<String> allowedLanguage) {
		this.allowedLanguage = allowedLanguage;
	}

	public void setNotificationEndpointUrl(String notificationEndpointUrl) {
		this.notificationEndpointUrl = notificationEndpointUrl;
	}

	public void setAppTimeZone(String appTimeZone) {
		this.appTimeZone = appTimeZone;
	}

	public void setTransactionExpiryDays(Integer transactionExpiryDays) {
		this.transactionExpiryDays = transactionExpiryDays;
	}

	public void setOtpExpiryMins(Integer otpExpiryMins) {
		this.otpExpiryMins = otpExpiryMins;
	}

	public String getEmplopyeeValidationUrl() {
		return emplopyeeValidationUrl;
	}

	public void setEmplopyeeValidationUrl(String emplopyeeValidationUrl) {
		this.emplopyeeValidationUrl = emplopyeeValidationUrl;
	}

	public String getEmployeeValidationUserName() {
		return employeeValidationUserName;
	}

	public void setEmployeeValidationUserName(String employeeValidationUserName) {
		this.employeeValidationUserName = employeeValidationUserName;
	}

	public String getEmployeeValidationPassword() {
		return employeeValidationPassword;
	}

	public void setEmployeeValidationPassword(String employeeValidationPassword) {
		this.employeeValidationPassword = employeeValidationPassword;
	}

	public String getOglobaCardEnquiryUrl() {
		return oglobaCardEnquiryUrl;
	}

	public void setOglobaCardEnquiryUrl(String oglobaCardEnquiryUrl) {
		this.oglobaCardEnquiryUrl = oglobaCardEnquiryUrl;
	}

	public String getOglobaCardRedemptionUrl() {
		return oglobaCardRedemptionUrl;
	}

	public void setOglobaCardRedemptionUrl(String oglobaCardRedemptionUrl) {
		this.oglobaCardRedemptionUrl = oglobaCardRedemptionUrl;
	}

	public String getOglobaCardConfirmationUrl() {
		return oglobaCardConfirmationUrl;
	}

	public void setOglobaCardConfirmationUrl(String oglobaCardConfirmationUrl) {
		this.oglobaCardConfirmationUrl = oglobaCardConfirmationUrl;
	}

	public String getStsToeknGenerationUrl() {
		return stsToeknGenerationUrl;
	}

	public void setStsToeknGenerationUrl(String stsToeknGenerationUrl) {
		this.stsToeknGenerationUrl = stsToeknGenerationUrl;
	}

	public String getStsClientId() {
		return stsClientId;
	}

	public void setStsClientId(String stsClientId) {
		this.stsClientId = stsClientId;
	}

	public String getStsClientSecret() {
		return stsClientSecret;
	}

	public void setStsClientSecret(String stsClientSecret) {
		this.stsClientSecret = stsClientSecret;
	}

	public String getStsGrantType() {
		return stsGrantType;
	}

	public void setStsGrantType(String stsGrantType) {
		this.stsGrantType = stsGrantType;
	}

	public String getStsRedirectUri() {
		return stsRedirectUri;
	}

	public void setStsRedirectUri(String stsRedirectUri) {
		this.stsRedirectUri = stsRedirectUri;
	}

	public String getxIbmClientId() {
		return xIbmClientId;
	}

	public void setxIbmClientId(String xIbmClientId) {
		this.xIbmClientId = xIbmClientId;
	}

	public String getCashbackNotificationServiceEndpointUrl() {
		return cashbackNotificationServiceEndpointUrl;
	}

	public void setCashbackNotificationServiceEndpointUrl(String cashbackNotificationServiceEndpointUrl) {
		this.cashbackNotificationServiceEndpointUrl = cashbackNotificationServiceEndpointUrl;
	}

}
