package com.lmg.digitization.cashback.response;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateCampaignResponse {

	@JsonProperty("MESSAGE")
	private String message;

	@JsonProperty("ID_PROM")
	private Long campaignId;

	@JsonProperty("CAMPAIGN_DESC")
	private String campaignDesc;

	@JsonProperty("CHANNEL")
	private String channel;

	@JsonProperty("ID_CON")
	private String idCon;

	@JsonProperty("ID_CD_GEO")
	private String idCdGeo;

	@JsonProperty("ID_STR_RT")
	private String idStrRT;

	@JsonProperty("FL_HRS_HAPPY")
	private String flHrsHappy;

	@JsonProperty("LVL_EL_PRM")
	private String lvlElPrm;

	@JsonProperty("LVL_APL_PRM")
	private String lvlAplPrm;

	@JsonProperty("FC_VALUE_TH")
	private int fcValueTh;

	@JsonProperty("DPT_SRC")
	private String dptSrc;

	@JsonProperty("DPT_TRGT")
	private String dptTrgt;

	@JsonProperty("ID_CON_EL")
	private String idConEl;

	@JsonProperty("ID_CON_SRC")
	private String idConSrc;

	@JsonProperty("IS_PERC")
	private String isPerc;

	@JsonProperty("DSC_VAL")
	private BigDecimal dscVal;

	@JsonProperty("CT_TYPE")
	private String ctType;

	@JsonProperty("ID_PRGP")
	private String idPrgp;

	@JsonProperty("FL_THRES_ITM_DISC")
	private String flThresItmDisc;

	@JsonProperty("FL_Prom_ITM_DISC")
	private String flPromItmDisc;

	@JsonProperty("FL_AMT_MUL_ALW")
	private String flAmtMulAlw;

	@JsonProperty("DT_FROM")
	private String dateFrom;

	@JsonProperty("DT_TO")
	private String dateTo;

	@JsonProperty("TS_START")
	private String tsStart;

	@JsonProperty("TS_END")
	private String tsEnd;

	@JsonProperty("ID_EMP")
	private String idEmployee;

	@JsonProperty("TS_CRT_RCRD")
	private String tsCrtRcrd;

	@JsonProperty("FL_ONLY_DISCOUNT")
	private String flOnlyDiscount;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getCampaignId() {
		return campaignId;
	}

	public void setCampaignId(Long campaignId) {
		this.campaignId = campaignId;
	}

	public String getCampaignDesc() {
		return campaignDesc;
	}

	public void setCampaignDesc(String campaignDesc) {
		this.campaignDesc = campaignDesc;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public String getTsCrtRcrd() {
		return tsCrtRcrd;
	}

	public void setTsCrtRcrd(String tsCrtRcrd) {
		this.tsCrtRcrd = tsCrtRcrd;
	}

	public String getFlOnlyDiscount() {
		return flOnlyDiscount;
	}

	public void setFlOnlyDiscount(String flOnlyDiscount) {
		this.flOnlyDiscount = flOnlyDiscount;
	}

}
