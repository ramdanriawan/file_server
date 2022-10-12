package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the view_document_delete database table.
 * 
 */
@Entity
@Table(name="VIEW_DOCUMENT_DELETE")
@NamedQuery(name="ViewDocumentDeleteEntity.findAll", query="SELECT v FROM ViewDocumentDeleteEntity v")
public class ViewDocumentDeleteEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private BigDecimal amount;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_ON")
	private Date createOn;

	private String curr;

	@Lob
	private String description;

	@Id
	@Column(name="ID_KEY")
	private Integer idKey;

	private String name;

	private String settled;

	@Column(name="TRX_FILE_NAME")
	private String trxFileName;

	@Column(name="TRX_OLD_TYPE")
	private String trxOldType;

	@Column(name="TRX_OLD_VOUCHER_ID")
	private String trxOldVoucherId;
	
	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_CLIENT_ID")
	private String trxClientId;
	
	@Transient
	private String action;

	@Transient
	private String amountStr;
	
	public ViewDocumentDeleteEntity() {
	}

	public BigDecimal getAmount() {
		return amount;
	}
	
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	
	public String getAmountStr() {
		if(amount == null)
			return null;
		
		return NumberFormat.getCurrencyInstance().format(amount).replace("$", "");
	}

	public void setAmountStr(String amountStr) {
		this.amountStr = amountStr;
	}

	public Date getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCurr() {
		return this.curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getIdKey() {
		return this.idKey;
	}

	public void setIdKey(Integer idKey) {
		this.idKey = idKey;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSettled() {
		return this.settled;
	}

	public void setSettled(String settled) {
		this.settled = settled;
	}

	public String getTrxFileName() {
		return this.trxFileName;
	}

	public void setTrxFileName(String trxFileName) {
		this.trxFileName = trxFileName;
	}

	public String getTrxOldType() {
		return this.trxOldType;
	}

	public void setTrxOldType(String trxOldType) {
		this.trxOldType = trxOldType;
	}

	public String getTrxOldVoucherId() {
		return this.trxOldVoucherId;
	}

	public void setTrxOldVoucherId(String trxOldVoucherId) {
		this.trxOldVoucherId = trxOldVoucherId;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTrxClientId() {
		return trxClientId;
	}

	public void setTrxClientId(String trxClientId) {
		this.trxClientId = trxClientId;
	}

	public String getTrxVoucherId() {
		return trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	
}