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

/**
 * The persistent class for the TR0003 database table.
 * 
 */
@Entity
@Table(name="TR0003")
@NamedQuery(name="TR0003Entity.findAll", query="SELECT t FROM TR0003Entity t")
public class TR0003Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name="TRX_TYPE")
	private String trxType;	

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_CLIENT")
	private String trxClient;
	
	@Column(name="TRX_REFERENCE")
	private String trxReference;
	
	@Column(name="TRX_DESCRIPTION")
	private String trxDescription;
	
	@Column(name="TRX_STATUS_DATA")
	private String trxStatusData;
	
	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;
	
	@Column(name="TRX_INS_INSURED")
	private String trxInsInsured;
	
	@Column(name="TRX_INS_OFFICER")
	private String trxInsOfficer;
		
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_START")
	private Date trxInsStart;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_END")
	private Date trxInsEnd;	

	@Column(name="TRX_ASSURED")
	private String trxAssured;

	@Column(name="TRX_AMT_DESC")
	private String trxAmtDesc;
	
	@Column(name="TRX_CURR_RATE")
	private BigDecimal trxCurrRate;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;
	
	@Column(name="TRX_AMOUNT_DUE")
	private BigDecimal trxAmountDue;	

	@Column(name="TRX_OLD_VOUCHER_ID")
	private String trxOldVoucherId;

	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public String getTrxVoucherId() {
		return trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public String getTrxClient() {
		return trxClient;
	}

	public void setTrxClient(String trxClient) {
		this.trxClient = trxClient;
	}

	public String getTrxReference() {
		return trxReference;
	}

	public void setTrxReference(String trxReference) {
		this.trxReference = trxReference;
	}

	public String getTrxDescription() {
		return trxDescription;
	}

	public void setTrxDescription(String trxDescription) {
		this.trxDescription = trxDescription;
	}

	public String getTrxStatusData() {
		return trxStatusData;
	}

	public void setTrxStatusData(String trxStatusData) {
		this.trxStatusData = trxStatusData;
	}

	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public String getTrxInsInsured() {
		return trxInsInsured;
	}

	public void setTrxInsInsured(String trxInsInsured) {
		this.trxInsInsured = trxInsInsured;
	}

	public String getTrxInsOfficer() {
		return trxInsOfficer;
	}

	public void setTrxInsOfficer(String trxInsOfficer) {
		this.trxInsOfficer = trxInsOfficer;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public Date getTrxInsStart() {
		return trxInsStart;
	}

	public void setTrxInsStart(Date trxInsStart) {
		this.trxInsStart = trxInsStart;
	}

	public Date getTrxInsEnd() {
		return trxInsEnd;
	}

	public void setTrxInsEnd(Date trxInsEnd) {
		this.trxInsEnd = trxInsEnd;
	}

	public String getTrxAssured() {
		return trxAssured;
	}

	public void setTrxAssured(String trxAssured) {
		this.trxAssured = trxAssured;
	}

	public String getTrxAmtDesc() {
		return trxAmtDesc;
	}

	public void setTrxAmtDesc(String trxAmtDesc) {
		this.trxAmtDesc = trxAmtDesc;
	}

	public BigDecimal getTrxCurrRate() {
		return trxCurrRate;
	}

	public void setTrxCurrRate(BigDecimal trxCurrRate) {
		this.trxCurrRate = trxCurrRate;
	}

	public String getTrxCurrId() {
		return trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public BigDecimal getTrxAmountDue() {
		return trxAmountDue;
	}

	public void setTrxAmountDue(BigDecimal trxAmountDue) {
		this.trxAmountDue = trxAmountDue;
	}
	
	public String getTrxOldVoucherId() {
		return trxOldVoucherId;
	}

	public void setTrxOldVoucherId(String trxOldVoucherId) {
		this.trxOldVoucherId = trxOldVoucherId;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}
	
}