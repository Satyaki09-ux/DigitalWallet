package com.lmg.digitization.cashback.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LMG_CAMPAIGN_DETAILS")
public class CashbackCampaignDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_PROM")
	private Long idProm;

	@Column(name = "CAMPAIGN_DESC", columnDefinition = "VARCHAR(100)", nullable = false)
	private String campaignDesc;

	@Column(name = "CHANNEL", columnDefinition = "VARCHAR(10)", nullable = false)
	private String channel;

	@Column(name = "ID_CON", columnDefinition = "VARCHAR(30)", nullable = false)
	private String idCon;

	@Column(name = "ID_CD_GEO", columnDefinition = "VARCHAR(10)", nullable = false)
	private String idCdGeo;

	@Column(name = "ID_STR_RT", columnDefinition = "VARCHAR(5)", nullable = false)
	private String idStrRT;

	@Column(name = "FL_HRS_HAPPY", columnDefinition = "CHAR(1)", nullable = false)
	private String flHrsHappy;

	@Column(name = "LVL_EL_PRM", columnDefinition = "VARCHAR(10)", nullable = false)
	private String lvlElPrm;

	@Column(name = "LVL_APL_PRM", columnDefinition = "VARCHAR(10)", nullable = false)
	private String lvlAplPrm;

	@Column(name = "FC_VALUE_TH", columnDefinition = "NUMERIC(13,0)")
	private int fcValueTh;

	@Column(name = "DPT_SRC", columnDefinition = "VARCHAR(5)")
	private String dptSrc;

	@Column(name = "DPT_TRGT", columnDefinition = "VARCHAR(5)")
	private String dptTrgt;

	@Column(name = "ID_CON_EL", columnDefinition = "VARCHAR(2)")
	private String idConEl;

	@Column(name = "ID_CON_SRC", columnDefinition = "VARCHAR(2)")
	private String idConSrc;

	@Column(name = "IS_PERC", columnDefinition = "CHAR(1)", nullable = false)
	private String isPerc;

	@Column(name = "DSC_VAL", columnDefinition = "NUMERIC(13,2)")
	private BigDecimal dscVal;

	@Column(name = "CT_TYPE", columnDefinition = "VARCHAR(10)", nullable = false)
	private String ctType;

	@Column(name = "ID_PRGP", columnDefinition = "VARCHAR(5)")
	private String idPrgp;

	@Column(name = "FL_THRES_ITM_DISC", columnDefinition = "CHAR(1)")
	private String flThresItmDisc;

	@Column(name = "FL_Prom_ITM_DISC", columnDefinition = "CHAR(1)")
	private String flPromItmDisc;

	@Column(name = "FL_AMT_MUL_ALW", columnDefinition = "CHAR(1)")
	private String flAmtMulAlw;

	@Column(name = "DT_FROM", columnDefinition = "DATE", nullable = false)
	private Date dateFrom;

	@Column(name = "DT_TO", columnDefinition = "DATE", nullable = false)
	private Date dateTo;

	@Column(name = "TS_START", columnDefinition = "TIME")
	private LocalTime tsStart;

	@Column(name = "TS_END", columnDefinition = "TIME")
	private LocalTime tsEnd;

	@Column(name = "ID_EMP", columnDefinition = "VARCHAR(50)", nullable = false)
	private String idEmployee;

	@Column(name = "TS_CRT_RCRD", columnDefinition = "DATETIME", nullable = false)
	private LocalDateTime tsCrtRcrd;

	@Column(name = "FL_ONLY_DISCOUNT", columnDefinition = "CHAR(1)", nullable = false)
	private String flOnlyDiscount;

	public Long getIdProm() {
		return idProm;
	}

	public void setIdProm(Long idProm) {
		this.idProm = idProm;
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

	public Date getDateFrom() {
		return dateFrom;
	}

	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	public Date getDateTo() {
		return dateTo;
	}

	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	public LocalTime getTsStart() {
		return tsStart;
	}

	public void setTsStart(LocalTime tsStart) {
		this.tsStart = tsStart;
	}

	public LocalTime getTsEnd() {
		return tsEnd;
	}

	public void setTsEnd(LocalTime tsEnd) {
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
