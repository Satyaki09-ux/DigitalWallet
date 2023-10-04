package com.lmg.digitization.cashback.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

@Entity
@Immutable
@Table(name = "VW_LMG_ALL_LEDGER")
public class AllLedger implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	private String transaction_id;
	private String shukran_id;
	private String order_number;
	private String amount;
	private String currency;
	private String concept;
	private String business_date;
	private String status;
	private String source;
	private String reference_number;
	private String store;
	private String source_reference;
	private String expiry_date;
	private String create_date;
	private String redeemable_concept;
	private String instrument_type;
	public String getTransaction_id() {
		return transaction_id;
	}
	public void setTransaction_id(String transaction_id) {
		this.transaction_id = transaction_id;
	}
	public String getShukran_id() {
		return shukran_id;
	}
	public void setShukran_id(String shukran_id) {
		this.shukran_id = shukran_id;
	}
	public String getOrder_number() {
		return order_number;
	}
	public void setOrder_number(String order_number) {
		this.order_number = order_number;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getConcept() {
		return concept;
	}
	public void setConcept(String concept) {
		this.concept = concept;
	}
	public String getBusiness_date() {
		return business_date;
	}
	public void setBusiness_date(String business_date) {
		this.business_date = business_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getReference_number() {
		return reference_number;
	}
	public void setReference_number(String reference_number) {
		this.reference_number = reference_number;
	}
	public String getStore() {
		return store;
	}
	public void setStore(String store) {
		this.store = store;
	}
	public String getSource_reference() {
		return source_reference;
	}
	public void setSource_reference(String source_reference) {
		this.source_reference = source_reference;
	}
	public String getExpiry_date() {
		return expiry_date;
	}
	public void setExpiry_date(String expiry_date) {
		this.expiry_date = expiry_date;
	}
	public String getCreate_date() {
		return create_date;
	}
	public void setCreate_date(String create_date) {
		this.create_date = create_date;
	}
	public String getRedeemable_concept() {
		return redeemable_concept;
	}
	public void setRedeemable_concept(String redeemable_concept) {
		this.redeemable_concept = redeemable_concept;
	}
	public String getInstrument_type() {
		return instrument_type;
	}
	public void setInstrument_type(String instrument_type) {
		this.instrument_type = instrument_type;
	}
	
	
	
}


    