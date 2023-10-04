package com.lmg.digitization.cashback.request;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class CreateCampaignRequest {

	@JsonProperty("CHANNEL")
	private String channel;

	@NotNull
	@NotEmpty
	@JsonProperty("campaign_desc")
	private String campaignDesc;

	@NotNull
	@NotEmpty(message = "Concept id must not be empty")
	@JsonProperty("id_con")
	private String idCon;

	@NotNull
	@NotEmpty(message = "Geo Code must not be empty")
	@Size(min = 2, max = 2, message = "Geo Code must be only 2 characters")
	@JsonProperty("id_cd_geo")
	private String idCdGeo;

	@NotNull
	@NotEmpty(message = "Store id must not be empty")
	@Size(min = 5, max = 5, message = "Store id must be only 5 characters")
	@JsonProperty("id_str_rt")
	private String idStrRT;

	@JsonProperty("fl_hrs_happy")
	private String flHrsHappy;

	@NotNull
	@NotEmpty(message = "Eligibility level must not be empty")
	@ApiModelProperty(allowableValues = "INVOICE, CONCEPT, DEPARTMENT, ITEM")
	@Pattern(regexp = "INVOICE|CONCEPT|DEPARTMENT|ITEM", flags = Pattern.Flag.CASE_INSENSITIVE)
	@JsonProperty("lvl_el_prm")
	private String lvlElPrm;

	@NotNull
	@NotEmpty(message = "Applicability level must not be empty")
	@ApiModelProperty( allowableValues = "INVOICE, CONCEPT, DEPARTMENT, ITEM")
	@Pattern(regexp = "INVOICE|CONCEPT|DEPARTMENT|ITEM", flags = Pattern.Flag.CASE_INSENSITIVE)
	@JsonProperty("lvl_apl_prm")
	private String lvlAplPrm;

	@NotNull
	@JsonProperty("fc_value_th")
	private int fcValueTh;

	@NotNull
	@NotEmpty(message = "Source department id must not be empty")
	@Size(min = 3, max = 3, message = "Source deparment id must be only 3 characters")
	@JsonProperty("dpt_src")
	private String dptSrc;

	@NotNull
	@NotEmpty(message = "Target department id must not be empty")
	@Size(min = 3, max = 3, message = "Target deparment id must be only 3 characters")
	@JsonProperty("dpt_trgt")
	private String dptTrgt;

	@NotNull
	@NotEmpty(message = "Target department id must not be empty")
	@JsonProperty("id_con_el")
	private String idConEl;

	@NotNull
	@NotEmpty(message = "Target department id must not be empty")
	@JsonProperty("id_con_src")
	private String idConSrc;

	@ApiModelProperty(allowableValues = "Amount:0,Percentage:1")
	@Pattern(regexp = "[0-1]", flags = Pattern.Flag.CASE_INSENSITIVE)
	@NotNull
	@NotEmpty(message = "Is percentage must not be empty")
	@JsonProperty("is_perc")
	private String isPerc;

	@Positive
	@NotNull(message = "Discounted value must not be less than 0.0")
	@JsonProperty("dsc_val")
	private BigDecimal dscVal;

	@NotNull
	@NotEmpty(message = "Customer type must not be empty")
	@JsonProperty("ct_type")
	private String ctType;

	@NotNull
	@NotEmpty(message = "Pricing group id must not be empty")
	@JsonProperty("id_prgp")
	private String idPrgp;

	@NotNull
	@NotEmpty(message = "Flag for including Promotional/Discounted item in threshold must not be empty")
	@JsonProperty("fl_thres_itm_disc")
	private String flThresItmDisc;

	@NotNull
	@NotEmpty(message = "Flag for applying Promotional/Discounted item in threshold must not be empty")
	@JsonProperty("fl_prom_itm_disc")
	private String flPromItmDisc;

	@NotNull
	@NotEmpty(message = "Flag for multiples allowed if Amount Discount is running must not be empty")
	@JsonProperty("fl_amt_mul_alw")
	private String flAmtMulAlw;

	@ApiModelProperty(position = 2, notes = "ISO Date format")
	@NotNull
	@Pattern(regexp = "^\\d{4}-([0]\\d|1[0-2])-([0-2]\\d|3[01])$", message = " should be in format[yyyy-MM-dd]")
	@JsonProperty("dt_from")
	private String dateFrom;

	@JsonProperty("dt_to")
	private String dateTo;

	@JsonProperty("ts_start")
	private String tsStart;

	@JsonProperty("ts_end")
	private String tsEnd;

	@NotNull
	@NotEmpty(message = "Employee id who created campaign must not be empty")
	@JsonProperty("id_emp")
	private String idEmployee;

	@JsonProperty("ts_crt_rcrd")
	private LocalDateTime tsCrtRcrd;

	@NotNull
	@NotEmpty(message = "Flag only discount must not be empty")
	@JsonProperty("fl_only_discount")
	private String flOnlyDiscount;

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getCampaignDesc() {
		return campaignDesc;
	}

	public void setCampaignDesc(String campaignDesc) {
		this.campaignDesc = campaignDesc;
	}

	public String getIdCon() {
		return idCon;
	}

	public void setIdCon(String idCon) {
		this.idCon = idCon;
	}

	public String getIdCdGeo() {
		return idCdGeo;
	}

	public void setIdCdGeo(String idCdGeo) {
		this.idCdGeo = idCdGeo;
	}

	public String getIdStrRT() {
		return idStrRT;
	}

	public void setIdStrRT(String idStrRT) {
		this.idStrRT = idStrRT;
	}

	public String getFlHrsHappy() {
		return flHrsHappy;
	}

	public void setFlHrsHappy(String flHrsHappy) {
		this.flHrsHappy = flHrsHappy;
	}

	public String getLvlElPrm() {
		return lvlElPrm;
	}

	public void setLvlElPrm(String lvlElPrm) {
		this.lvlElPrm = lvlElPrm;
	}

	public String getLvlAplPrm() {
		return lvlAplPrm;
	}

	public void setLvlAplPrm(String lvlAplPrm) {
		this.lvlAplPrm = lvlAplPrm;
	}

	public int getFcValueTh() {
		return fcValueTh;
	}

	public void setFcValueTh(int fcValueTh) {
		this.fcValueTh = fcValueTh;
	}

	public String getDptSrc() {
		return dptSrc;
	}

	public void setDptSrc(String dptSrc) {
		this.dptSrc = dptSrc;
	}

	public String getDptTrgt() {
		return dptTrgt;
	}

	public void setDptTrgt(String dptTrgt) {
		this.dptTrgt = dptTrgt;
	}

	public String getIdConEl() {
		return idConEl;
	}

	public void setIdConEl(String idConEl) {
		this.idConEl = idConEl;
	}

	public String getIdConSrc() {
		return idConSrc;
	}

	public void setIdConSrc(String idConSrc) {
		this.idConSrc = idConSrc;
	}

	public String getIsPerc() {
		return isPerc;
	}

	public void setIsPerc(String isPerc) {
		this.isPerc = isPerc;
	}

	public BigDecimal getDscVal() {
		return dscVal;
	}

	public void setDscVal(BigDecimal dscVal) {
		this.dscVal = dscVal;
	}

	public String getCtType() {
		return ctType;
	}

	public void setCtType(String ctType) {
		this.ctType = ctType;
	}

	public String getIdPrgp() {
		return idPrgp;
	}

	public void setIdPrgp(String idPrgp) {
		this.idPrgp = idPrgp;
	}

	public String getFlThresItmDisc() {
		return flThresItmDisc;
	}

	public void setFlThresItmDisc(String flThresItmDisc) {
		this.flThresItmDisc = flThresItmDisc;
	}

	public String getFlPromItmDisc() {
		return flPromItmDisc;
	}

	public void setFlPromItmDisc(String flPromItmDisc) {
		this.flPromItmDisc = flPromItmDisc;
	}

	public String getFlAmtMulAlw() {
		return flAmtMulAlw;
	}

	public void setFlAmtMulAlw(String flAmtMulAlw) {
		this.flAmtMulAlw = flAmtMulAlw;
	}

	public String getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}

	public String getDateTo() {
		return dateTo;
	}

	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}

	public String getTsStart() {
		return tsStart;
	}

	public void setTsStart(String tsStart) {
		this.tsStart = tsStart;
	}

	public String getTsEnd() {
		return tsEnd;
	}

	public void setTsEnd(String tsEnd) {
		this.tsEnd = tsEnd;
	}

	public String getIdEmployee() {
		return idEmployee;
	}

	public void setIdEmployee(String idEmployee) {
		this.idEmployee = idEmployee;
	}

	public LocalDateTime getTsCrtRcrd() {
		return tsCrtRcrd;
	}

	public void setTsCrtRcrd(LocalDateTime tsCrtRcrd) {
		this.tsCrtRcrd = tsCrtRcrd;
	}

	public String getFlOnlyDiscount() {
		return flOnlyDiscount;
	}

	public void setFlOnlyDiscount(String flOnlyDiscount) {
		this.flOnlyDiscount = flOnlyDiscount;
	}

}
