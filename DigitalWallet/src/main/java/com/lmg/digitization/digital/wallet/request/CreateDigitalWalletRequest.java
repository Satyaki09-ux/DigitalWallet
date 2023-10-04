package com.lmg.digitization.digital.wallet.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.enums.Source;

import io.swagger.annotations.ApiModelProperty;

public class CreateDigitalWalletRequest {

	@NotBlank(message = "Shukran ID cannot be blank")
	@JsonProperty("shukran_id")
	private String shukranId;

	@NotNull(message = "Customer Name cannot be blank")
	@JsonProperty("customer_name")
	private String customerName;

	@ApiModelProperty(example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|SAR|BHD|OMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@NotBlank(message = "baseCurrency cannot be blank")
	@JsonProperty("base_currency")
	private String baseCurrency;

	@Size(min = 10, max = 12, message = "Mobile number can not be less than 10 characters")
	@JsonProperty("mobile_number")
	private String mobileNumber;

	// @Pattern(regexp = "POS|Hybris", flags = Pattern.Flag.CASE_INSENSITIVE,
	// message="Channel can either POS/Hybriss")
	@JsonProperty("source_channel")
	private Source sourceChannel;

	@Size(min = 5, max = 5, message = "Cost centre must be 5 characters")
	@JsonProperty("cost_centre")
	private String costCentre;

	@Email(message = "Email id should be valid")
	@JsonProperty("email_id")
	private String emailId;

	@NotNull(message = "Register ID cannot be blank")
	@JsonProperty("register_id")
	private String registerId;

	@PositiveOrZero
	@NotNull(message = "Amount must not be less than 0.0")
	@JsonProperty("amount")
	private BigDecimal amount;

	@NotNull(message = "Store Code cannot be blank")
	@JsonProperty("store_code")
	private String storeCode;

	@NotNull(message = "Transaction date cannot be blank")
	@JsonProperty("business_date")
	private LocalDate transactionDate;

	@NotNull(message = "Transaction Id cannot be blank")
	@JsonProperty("transaction_id")
	private String transactionId;

	@Pattern(regexp = "ORPOS|OMS|SSM|SCO|SSCO|HYBRIS", flags = Pattern.Flag.CASE_INSENSITIVE)
	@NotNull(message = "Source App cannot be blank")
	@JsonProperty("source_app")
	private String sourceApp;

	@Pattern(regexp = "2|4|26|1|3|29|6|21|27|5|17|25|35|8|34", flags = Pattern.Flag.CASE_INSENSITIVE)
	@ApiModelProperty(example = "3", allowableValues = "ShoeMart:2,HomeCentre:4,Sarah:26,BabyShop:1,Splash:3,HomeBox:29,Max:6,Centrepoint:35,SportsOne:27,Lifestyle:5,ShoeExpress:17,Lipsy:25,Iconic:8,SMInternational:34")
	@NotNull(message = "Concept Code cannot be blank")
	@JsonProperty("concept_code")
	private String conceptCode;

	@JsonProperty("version")
	private Long version;

	@JsonProperty("source_reference")
	private String sourceReference;

	@JsonProperty("reference_type")
	private String referenceType;

	@JsonProperty("source_function")
	private String sourceFunction;

	@JsonProperty("reference_source")
	private String referenceSource;

	@Pattern(regexp = "[1-7]", flags = Pattern.Flag.CASE_INSENSITIVE)
	@ApiModelProperty(example = "1", allowableValues = "UAE:1,KSA:2,Bahrain:3,Oman:4,Kuwait:5,Qatar:6,Egypt:7")
	@NotNull(message = "Territory Code cannot be blank")
	@JsonProperty("territory_code")
	private String territoryCode;

	@JsonProperty("applicability")
	private String applicability;

	@JsonProperty("remarks")
	private String remarks;

	private String language = "ENGLISH";

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Source getSourceChannel() {
		return sourceChannel;
	}

	public void setSourceChannel(Source sourceChannel) {
		this.sourceChannel = sourceChannel;
	}

	public String getCostCentre() {
		return costCentre;
	}

	public void setCostCentre(String costCentre) {
		this.costCentre = costCentre;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	public String getRegisterId() {
		return registerId;
	}

	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getStoreCode() {
		return storeCode;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getSourceApp() {
		return sourceApp;
	}

	public void setSourceApp(String sourceApp) {
		this.sourceApp = sourceApp;
	}

	public String getConceptCode() {
		return conceptCode;
	}

	public void setConceptCode(String conceptCode) {
		this.conceptCode = conceptCode;
	}

	public String getSourceReference() {
		return sourceReference;
	}

	public void setSourceReference(String sourceReference) {
		this.sourceReference = sourceReference;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}

	public String getSourceFunction() {
		return sourceFunction;
	}

	public void setSourceFunction(String sourceFunction) {
		this.sourceFunction = sourceFunction;
	}

	public String getReferenceSource() {
		return referenceSource;
	}

	public void setReferenceSource(String referenceSource) {
		this.referenceSource = referenceSource;
	}

	public String getTerritoryCode() {
		return territoryCode;
	}

	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}

	public String getApplicability() {
		return applicability;
	}

	public void setApplicability(String applicability) {
		this.applicability = applicability;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
