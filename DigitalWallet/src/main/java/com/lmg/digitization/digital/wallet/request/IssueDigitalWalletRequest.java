
package com.lmg.digitization.digital.wallet.request;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.lmg.digitization.digital.wallet.enums.Source;

import io.swagger.annotations.ApiModelProperty;

public class IssueDigitalWalletRequest {

	@ApiModelProperty(example = "1800000302323600588a3", position = 1)
	@NotBlank(message = "Shukran ID cannot be blank")
	@JsonProperty("shukran_id")
	private String shukranId;

	@ApiModelProperty(example = "Customer name")
	@NotNull(message = "Customer Name cannot be blank")
	@JsonProperty("customer_name")
	private String customerName;

	@ApiModelProperty(example = "AED", allowableValues = "AED, SAR, BHD, OMR, KWD, QAR, EGP")
	@Pattern(regexp = "AED|SAR|BHD|OMR|KWD|QAR|EGP", flags = Pattern.Flag.CASE_INSENSITIVE)
	@Size(min = 3, max = 3, message = "Base currency 3 characters")
	@JsonProperty("base_currency")
	@NotBlank(message = "baseCurrency cannot be blank")
	private String baseCurrency;

	@ApiModelProperty(example = "971566213619")
	@Size(min = 10, max = 12, message = "Mobile number can not be less than 10 characters")
	@JsonProperty("mobile_number")
	private String mobileNumber;

	@ApiModelProperty(example = "HYBRIS", allowableValues = "ORPOS, HYBRIS, OMS, SSM, SCO, SSCO, SHUKRAN ")
	@JsonProperty("source_channel")
	private Source sourceChannel;

	@ApiModelProperty(example = "CENTE")
	@Size(min = 5, max = 5, message = "Cost centre must be 5 characters")
	@JsonProperty("cost_centre")
	private String costCentre;

	@ApiModelProperty(example = "user@gmail.com")
	@Email(message = "Email should be valid")
	@JsonProperty("email_id")
	private String emailId;

	@NotNull(message = "Register ID cannot be blank")
	@ApiModelProperty(example = "23423")
	@JsonProperty("register_id")
	private String registerId;

	@Positive
	@ApiModelProperty(example = "9.00")
	@NotNull(message = "Amount must not be less than 0.0")
	@JsonProperty("amount")
	private BigDecimal amount;

	@ApiModelProperty(example = "22211")
	@NotNull(message = "Store Code cannot be blank")
	@Size(min = 5, max = 5, message = "Store must be 5 characters")
	@JsonProperty("store_code")
	private String storeCode;

	@ApiModelProperty(example = "2022-03-10")
	@NotNull(message = "Transaction date cannot be blank")
	@JsonProperty("business_date")
	private LocalDate transactionDate;

	@ApiModelProperty(position = 2, example = "21006601005620210908")
	@JsonProperty("invoiceNumber")
	private String invoiceNumber;

	@ApiModelProperty(position = 3, example = "01005620210908")
	@NotNull(message = "Transaction Id cannot be blank")
	@JsonProperty("transaction_id")
	private String transactionId;

	@ApiModelProperty(example = "HYBRIS")
	@Pattern(regexp = "ORPOS|OMS|SSM|SCO|SSCO|HYBRIS|SHKURAN", flags = Pattern.Flag.CASE_INSENSITIVE)
	// @NotNull(message = "Source App cannot be blank")
	@JsonProperty("source_app")
	private String sourceApp;

	@ApiModelProperty(position = 10, example = "4", notes = "concept where it is issued")
	@NotNull(message = "Concept Code cannot be blank")
	@JsonProperty("concept_code")
	private String conceptCode;

	@NotNull(message = "Version cannot be blank")
	@JsonProperty("version")
	private Long version;

	@ApiModelProperty(example = "Source Ref")
	@NotNull(message = "Source Reference cannot be blank")
	@JsonProperty("source_reference")
	private String sourceReference;

	@Pattern(regexp = "[1-2]", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Reference Type will be 1 for DW and 2 for DCN")
	@ApiModelProperty(example = "1", allowableValues = "DW:1,DSN:2")
	@NotNull(message = "Reference Type cannot be blank")
	@JsonProperty("reference_type")
	private String referenceType;

	@ApiModelProperty(example = "sourceFunction")
	@NotNull(message = "Source Functionality cannot be blank")
	@JsonProperty("source_function")
	private String sourceFunction;

	@Pattern(regexp = "[1-7]", flags = Pattern.Flag.CASE_INSENSITIVE)
	@ApiModelProperty(example = "1", allowableValues = "UAE:1,KSA:2,Bahrain:3,Oman:4,Kuwait:5,Qatar:6,Egypt:7")
	@NotNull(message = "Territory Code cannot be blank")
	@JsonProperty("territory_code")
	private String territoryCode;

	@NotNull(message = "Applicability  cannot be blank")
	@JsonProperty("applicability")
	private String applicability;

	@JsonProperty("remarks")
	private String remarks;

	@ApiModelProperty(position = 14, example = "ENGLISH", allowableValues = "ENGLISH, ARABIC")
	private String language = "ENGLISH";

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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

	public String getShukranId() {
		return shukranId;
	}

	public void setShukranId(String shukranId) {
		this.shukranId = shukranId;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

}
