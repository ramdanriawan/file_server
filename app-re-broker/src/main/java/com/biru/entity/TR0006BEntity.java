package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the tr0006b database table.
 * 
 */
@Entity
@Table(name="TR0006B")
@NamedQuery(name="TR0006BEntity.findAll", query="SELECT t FROM TR0006BEntity t")
public class TR0006BEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private String idKey;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;

	@Column(name="TRX_INS_AMT")
	private BigDecimal trxInsAmt = BigDecimal.ZERO;

	@Column(name="TRX_PREM_AMT")
	private BigDecimal trxPremAmt = BigDecimal.ZERO;
	
	@Column(name="TRX_PREM_PORTION")
	private BigDecimal trxPremPortion = BigDecimal.ZERO;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_CONFIRMDATE")
	private Date trxInsConfirmdate;

	@Column(name="TRX_INS_CONFIRMID")
	private String trxInsConfirmid;

	@Column(name="TRX_INS_ID")
	private String trxInsId;

	@Column(name="TRX_INS_PAYMETHD")
	private String trxInsPaymethd;

	@Column(name="TRX_INS_REMARKS")
	private String trxInsRemarks;

	@Column(name="TRX_INS_SHARE")
	private BigDecimal trxInsShare;

	@Column(name="TRX_INS_TYPE")
	private String trxInsType;
	
	@Column(name="TRX_CLASS")
	private String trxClass;

	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_INS_BFEE")
	private BigDecimal trxInsBfee;
	
	@Column(name="TRX_INS_PREMIUM")
	private BigDecimal trxInsPremium;
	
	@Column(name="TRX_RICOMM_AMT")
	private BigDecimal trxRicommAmt = BigDecimal.ZERO;
	
	@Transient
	private String trxInsIdDesc;
	
	@Transient
	private String trxInsPaymethdDesc;
	
	@Transient
	private BigDecimal wht;

	public TR0006BEntity() {
	}
	
	public TR0006BEntity(String idKey, String trxCurrId, BigDecimal trxInsAmt, BigDecimal trxPremAmt, Date trxInsConfirmdate,
			String trxInsConfirmid, String trxInsId, String trxInsPaymethd, String trxInsRemarks,
			BigDecimal trxInsShare, String trxInsType, String trxTrxId, String trxVoucherId, BigDecimal trxInsBfee,
			BigDecimal trxInsPremium, BigDecimal trxRicommAmt, String trxInsIdDesc, String trxInsPaymethdDesc, BigDecimal trxPremPortion) {
		super();
		this.idKey = idKey;
		this.trxCurrId = trxCurrId;
		this.trxInsAmt = trxInsAmt;
		this.trxPremAmt = trxPremAmt;
		this.trxInsConfirmdate = trxInsConfirmdate;
		this.trxInsConfirmid = trxInsConfirmid;
		this.trxInsId = trxInsId;
		this.trxInsPaymethd = trxInsPaymethd;
		this.trxInsRemarks = trxInsRemarks;
		this.trxInsShare = trxInsShare;
		this.trxInsType = trxInsType;
		this.trxTrxId = trxTrxId;
		this.trxVoucherId = trxVoucherId;
		this.trxInsBfee = trxInsBfee;
		this.trxInsPremium = trxInsPremium;
		this.trxRicommAmt = trxRicommAmt;
		this.trxInsIdDesc = trxInsIdDesc;
		this.trxInsPaymethdDesc = trxInsPaymethdDesc;
		this.trxPremPortion = trxPremPortion;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getTrxCurrId() {
		return this.trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public BigDecimal getTrxInsAmt() {
		return this.trxInsAmt;
	}

	public void setTrxInsAmt(BigDecimal trxInsAmt) {
		this.trxInsAmt = trxInsAmt;
	}

	public BigDecimal getTrxPremAmt() {
		return trxPremAmt;
	}

	public void setTrxPremAmt(BigDecimal trxPremAmt) {
		this.trxPremAmt = trxPremAmt;
	}

	public BigDecimal getTrxPremPortion() {
		return trxPremPortion;
	}

	public void setTrxPremPortion(BigDecimal trxPremPortion) {
		this.trxPremPortion = trxPremPortion;
	}

	public Date getTrxInsConfirmdate() {
		return this.trxInsConfirmdate;
	}

	public void setTrxInsConfirmdate(Date trxInsConfirmdate) {
		this.trxInsConfirmdate = trxInsConfirmdate;
	}

	public String getTrxInsConfirmid() {
		return this.trxInsConfirmid;
	}

	public void setTrxInsConfirmid(String trxInsConfirmid) {
		this.trxInsConfirmid = trxInsConfirmid;
	}

	public String getTrxInsId() {
		return this.trxInsId;
	}

	public void setTrxInsId(String trxInsId) {
		this.trxInsId = trxInsId;
	}

	public String getTrxInsPaymethd() {
		return this.trxInsPaymethd;
	}

	public void setTrxInsPaymethd(String trxInsPaymethd) {
		this.trxInsPaymethd = trxInsPaymethd;
	}

	public String getTrxInsRemarks() {
		return this.trxInsRemarks;
	}

	public void setTrxInsRemarks(String trxInsRemarks) {
		this.trxInsRemarks = trxInsRemarks;
	}

	public BigDecimal getTrxInsShare() {
		return this.trxInsShare;
	}

	public void setTrxInsShare(BigDecimal trxInsShare) {
		this.trxInsShare = trxInsShare;
	}

	public String getTrxInsType() {
		return this.trxInsType;
	}

	public void setTrxInsType(String trxInsType) {
		this.trxInsType = trxInsType;
	}
	
	public String getTrxClass() {
		return trxClass;
	}

	public void setTrxClass(String trxClass) {
		this.trxClass = trxClass;
	}

	public String getTrxTrxId() {
		return this.trxTrxId;
	}

	public void setTrxTrxId(String trxTrxId) {
		this.trxTrxId = trxTrxId;
	}

	public String getTrxVoucherId() {
		return this.trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public BigDecimal getTrxInsBfee() {
		return trxInsBfee;
	}

	public void setTrxInsBfee(BigDecimal trxInsBfee) {
		this.trxInsBfee = trxInsBfee;
	}

	public BigDecimal getTrxInsPremium() {
		return trxInsPremium;
	}

	public void setTrxInsPremium(BigDecimal trxInsPremium) {
		this.trxInsPremium = trxInsPremium;
	}

	public String getTrxInsIdDesc() {
		return trxInsIdDesc;
	}

	public void setTrxInsIdDesc(String trxInsIdDesc) {
		this.trxInsIdDesc = trxInsIdDesc;
	}

	public String getTrxInsPaymethdDesc() {
		return trxInsPaymethdDesc;
	}

	public void setTrxInsPaymethdDesc(String trxInsPaymethdDesc) {
		this.trxInsPaymethdDesc = trxInsPaymethdDesc;
	}

	public BigDecimal getWht() {
		return wht;
	}

	public void setWht(BigDecimal wht) {
		this.wht = wht;
	}

	public BigDecimal getTrxRicommAmt() {
		return trxRicommAmt;
	}

	public void setTrxRicommAmt(BigDecimal trxRicommAmt) {
		this.trxRicommAmt = trxRicommAmt;
	}
	
}