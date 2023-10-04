package com.lmg.digitization.digital.wallet.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "CO_CNY")
public class Currency {

	@Id
	@Column(name = "ID_CNY_ICD")
	private long currencyId;

	@Column(name = "CD_CNY_ISO")
	private String isoCurrencyCode;

	@Column(name = "DE_CNY_ISSG_NAT")
	private String currencyNationality;

	@Column(name = "TERRITORY_CODE")
	private String territoryCode;

	/**
	 * @return the currencyId
	 */
	public long getCurrencyId() {
		return currencyId;
	}

	/**
	 * @param currencyId the currencyId to set
	 */
	public void setCurrencyId(long currencyId) {
		this.currencyId = currencyId;
	}

	/**
	 * @return the isoCurrencyCode
	 */
	public String getIsoCurrencyCode() {
		return isoCurrencyCode;
	}

	/**
	 * @param isoCurrencyCode the isoCurrencyCode to set
	 */
	public void setIsoCurrencyCode(String isoCurrencyCode) {
		this.isoCurrencyCode = isoCurrencyCode;
	}

	/**
	 * @return the currencyNationality
	 */
	public String getCurrencyNationality() {
		return currencyNationality;
	}

	/**
	 * @param currencyNationality the currencyNationality to set
	 */
	public void setCurrencyNationality(String currencyNationality) {
		this.currencyNationality = currencyNationality;
	}

	public String getTerritoryCode() {
		return territoryCode;
	}

	public void setTerritoryCode(String territoryCode) {
		this.territoryCode = territoryCode;
	}

}
