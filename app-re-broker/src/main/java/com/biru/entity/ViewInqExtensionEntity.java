package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the VIEW_INQ_EXTENSION database table.
 * 
 */
@Entity
@Table(name="VIEW_INQ_EXTENSION")
@NamedQuery(name="ViewInqExtensionEntity.findAll", query="SELECT v FROM ViewInqExtensionEntity v")
public class ViewInqExtensionEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID_KEY")
	private String idKey;
	
	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_CLIENT")
	private String trxClient;
	
	@Column(name="CLI_NAME")
	private String cliName;
	
	@Column(name="TRX_CURR_ID")
	private String trxCurrId;
	
	@Column(name="TRX_TSI_AMOUNT")
	private BigDecimal trxTsiAmount;
	
	@Column(name="CREATE_BY")
	private String createBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;
	
	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;
	
	@Column(name="TC_DESC")
	private String tcDesc;
	
	@Column(name="TRX_DESCRIPTION")
	private String trxDescription;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TRX_INS_START")
	private Date trxInsStart;
	
	@Column(name="TRX_INS_START_STR")
	private String trxInsStartStr;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TRX_INS_END")
	private Date trxInsEnd;
	
	@Column(name="TRX_INS_END_STR")
	private String trxInsEndStr;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;
	
	@Column(name="TRX_REINS")
	private String trxReins;
	
	@Transient
	private String trxDate;
	
	@Transient
	private String trxTsiAmountStr;
	
	@Transient
	private BigDecimal exRateValue;
	
	@Transient
	private String exRateValueStr;
	
	public ViewInqExtensionEntity(){
	}
	
	public ViewInqExtensionEntity(String idKey, String trxVoucherId, String trxClient, String cliName, String trxCurrId,
			BigDecimal trxTsiAmount, String createBy, Date createOn, String trxCoverCode, String tcDesc, String trxDescription,
			Date trxInsStart, String trxInsStartStr, Date trxInsEnd, String trxInsEndStr, String trxTrxId, String trxDate,
			String trxTsiAmountStr, BigDecimal exRateValue, String exRateValueStr, String trxReins) {
		super();
		this.idKey = idKey;
		this.trxVoucherId = trxVoucherId;
		this.trxClient = trxClient;
		this.cliName = cliName;
		this.trxCurrId = trxCurrId;
		this.trxTsiAmount = trxTsiAmount;
		this.createBy = createBy;
		this.createOn = createOn;
		this.trxCoverCode = trxCoverCode;
		this.tcDesc = tcDesc;
		this.trxDescription = trxDescription;
		this.trxInsStart = trxInsStart;
		this.trxInsStartStr = trxInsStartStr;
		this.trxInsEnd = trxInsEnd;
		this.trxInsEndStr = trxInsEndStr;
		this.trxDate = trxDate;
		this.trxTrxId = trxTrxId;
		this.trxTsiAmountStr = trxTsiAmountStr;
		this.exRateValue = exRateValue;
		this.exRateValueStr = exRateValueStr;
		this.trxReins = trxReins;
	}


	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
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

	public String getCliName() {
		return cliName;
	}

	public void setCliName(String cliName) {
		this.cliName = cliName;
	}

	public String getTrxCurrId() {
		return trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public BigDecimal getTrxTsiAmount() {
		return trxTsiAmount;
	}

	public void setTrxTsiAmount(BigDecimal trxTsiAmount) {
		this.trxTsiAmount = trxTsiAmount;
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

	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public String getTcDesc() {
		return tcDesc;
	}

	public void setTcDesc(String tcDesc) {
		this.tcDesc = tcDesc;
	}

	public String getTrxDescription() {
		return trxDescription;
	}

	public void setTrxDescription(String trxDescription) {
		this.trxDescription = trxDescription;
	}

	public Date getTrxInsStart() {
		return trxInsStart;
	}

	public void setTrxInsStart(Date trxInsStart) {
		this.trxInsStart = trxInsStart;
	}

	public String getTrxInsStartStr() {
		return trxInsStartStr;
	}

	public void setTrxInsStartStr(String trxInsStartStr) {
		this.trxInsStartStr = trxInsStartStr;
	}

	public Date getTrxInsEnd() {
		return trxInsEnd;
	}

	public void setTrxInsEnd(Date trxInsEnd) {
		this.trxInsEnd = trxInsEnd;
	}

	public String getTrxInsEndStr() {
		return trxInsEndStr;
	}

	public void setTrxInsEndStr(String trxInsEndStr) {
		this.trxInsEndStr = trxInsEndStr;
	}
	
	public String getTrxTrxId() {
		return trxTrxId;
	}

	public void setTrxTrxId(String trxTrxId) {
		this.trxTrxId = trxTrxId;
	}

	public String getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}
	
	public String getTrxTsiAmountStr(){
		if(trxTsiAmount == null){
			return null;
		}
		return NumberFormat.getCurrencyInstance().format(trxTsiAmount).replace("$", "");
	}

	public void setTrxTsiAmountStr(String trxTsiAmountStr) {
		this.trxTsiAmountStr = trxTsiAmountStr;
	}

	public BigDecimal getExRateValue() {
		return exRateValue;
	}

	public void setExRateValue(BigDecimal exRateValue) {
		this.exRateValue = exRateValue;
	}

	public String getExRateValueStr() {
		return exRateValueStr;
	}

	public void setExRateValueStr(String exRateValueStr) {
		this.exRateValueStr = exRateValueStr;
	}

	public String getTrxReins() {
		return trxReins;
	}

	public void setTrxReins(String trxReins) {
		this.trxReins = trxReins;
	}
	
}
