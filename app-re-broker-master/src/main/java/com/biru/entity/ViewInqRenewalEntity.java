package com.biru.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the view_inq_renewal database table.
 * 
 */
@Entity
@Table(name="VIEW_INQ_RENEWAL")
@NamedQuery(name="ViewInqRenewalEntity.findAll", query="SELECT v FROM ViewInqRenewalEntity v")
public class ViewInqRenewalEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CLI_NAME")
	private String cliName;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private Long idKey;
	
	@Column(name="TRX_CLASS_DESC")
	private String trxClassDesc;

	@Column(name="INS_NAME")
	private String insName;

	@Column(name="TC_RENEWABLE")
	private String tcRenewable;

	@Column(name="TC_RENEWABLE_STR")
	private String tcRenewableStr;

	@Column(name="TRX_CLIENT")
	private String trxClient;

	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;

	@Lob
	@Column(name="TRX_DESCRIPTION")
	private String trxDescription;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_END")
	private Date trxInsEnd;

	@Column(name="TRX_INS_ID")
	private String trxInsId;

	@Column(name="TRX_INS_REMARKS")
	private String trxInsRemarks;

	@Column(name="TRX_PREMIUM_SELL")
	private String trxPremiumSell;

	@Column(name="TRX_SUM_INSURED")
	private String trxSumInsured;

	@Column(name="TRX_TYPE")
	private String trxType;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	private String underwrite;
	
	@Transient
	private String trxInsEndStr;
	
	@Transient
	private String action;

	public ViewInqRenewalEntity() {
	}

	public String getCliName() {
		return this.cliName;
	}

	public void setCliName(String cliName) {
		this.cliName = cliName;
	}

	public Long getIdKey() {
		return this.idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getInsName() {
		return this.insName;
	}

	public void setInsName(String insName) {
		this.insName = insName;
	}

	public String getTcRenewable() {
		return this.tcRenewable;
	}

	public void setTcRenewable(String tcRenewable) {
		this.tcRenewable = tcRenewable;
	}

	public String getTcRenewableStr() {
		return this.tcRenewableStr;
	}

	public void setTcRenewableStr(String tcRenewableStr) {
		this.tcRenewableStr = tcRenewableStr;
	}

	public String getTrxClient() {
		return this.trxClient;
	}

	public void setTrxClient(String trxClient) {
		this.trxClient = trxClient;
	}

	public String getTrxCoverCode() {
		return this.trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public String getTrxCurrId() {
		return this.trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public String getTrxDescription() {
		return this.trxDescription;
	}

	public void setTrxDescription(String trxDescription) {
		this.trxDescription = trxDescription;
	}

	public Date getTrxInsEnd() {
		return this.trxInsEnd;
	}

	public void setTrxInsEnd(Date trxInsEnd) {
		this.trxInsEnd = trxInsEnd;
	}

	public String getTrxInsId() {
		return this.trxInsId;
	}

	public void setTrxInsId(String trxInsId) {
		this.trxInsId = trxInsId;
	}

	public String getTrxInsRemarks() {
		return this.trxInsRemarks;
	}

	public void setTrxInsRemarks(String trxInsRemarks) {
		this.trxInsRemarks = trxInsRemarks;
	}

	public String getTrxPremiumSell() {
		return this.trxPremiumSell;
	}

	public void setTrxPremiumSell(String trxPremiumSell) {
		this.trxPremiumSell = trxPremiumSell;
	}

	public String getTrxSumInsured() {
		return this.trxSumInsured;
	}

	public void setTrxSumInsured(String trxSumInsured) {
		this.trxSumInsured = trxSumInsured;
	}

	public String getTrxType() {
		return this.trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public String getTrxVoucherId() {
		return this.trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public String getTrxInsEndStr() {
		SimpleDateFormat formatDateId = new SimpleDateFormat("dd/MM/yyyy");
		String date = formatDateId.format(trxInsEnd);
		return date;
	}

	public void setTrxInsEndStr(String trxInsEndStr) {
		this.trxInsEndStr = trxInsEndStr;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTrxClassDesc() {
		return trxClassDesc;
	}

	public void setTrxClassDesc(String trxClassDesc) {
		this.trxClassDesc = trxClassDesc;
	}

	public String getUnderwrite() {
		return underwrite;
	}

	public void setUnderwrite(String underwrite) {
		this.underwrite = underwrite;
	}

}