package com.biru.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
 * The persistent class for the VIEW_INQ_DIRECT_MODIFY database table.
 * 
 */
@Entity
@Table(name="VIEW_INQ_DIRECT_MODIFY")
@NamedQuery(name="ViewInqDirectModify.findAll", query="SELECT v FROM ViewInqDirectModify v")
public class ViewInqDirectModify implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	private String idKey;
	
	@Column(name="TRX_CLASS")
	private String trxClass;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;
	
	@Column(name="TRX_CLIENT")
	private String trxClient;
	
	@Column(name="CLIENT_NAME")
	private String clientName;
	
	@Column(name="TRX_INSURED_NAME")
	private String trxInsuredName;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_START")
	private Date trxInsStart;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_END")
	private Date trxInsEnd;
	
	@Column(name="TRX_CURR_ID")
	private String trxCurrId;

	@Column(name="TRX_TSI_AMOUNT")
	private BigDecimal trxTsiAmount;
	
	@Column(name="TRX_REMARKS")
	private String trxRemarks;
	
	@Column(name="TRX_DATA_STATUS")
	private String trxDataStatus;
	
	@Column(name="STATUS")
	private String status;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_CLOSING_DATE")
	private Date trxClosingDate;
	
	@Transient
	private String trxTsiAmountStr;
	
	@Transient
	private String trxInsStartFmt;
	
	@Transient
	private String trxInsEndFmt;
	
	@Transient
	private String trxClosingDateFmt;
	
	@Transient
	private SimpleDateFormat dateUI = new SimpleDateFormat("dd/MM/yyyy");

	public ViewInqDirectModify() {
	}

	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getTrxClass() {
		return trxClass;
	}

	public void setTrxClass(String trxClass) {
		this.trxClass = trxClass;
	}

	public String getTrxTrxId() {
		return trxTrxId;
	}

	public void setTrxTrxId(String trxTrxId) {
		this.trxTrxId = trxTrxId;
	}

	public String getTrxVoucherId() {
		return trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public String getTrxClient() {
		return trxClient;
	}

	public void setTrxClient(String trxClient) {
		this.trxClient = trxClient;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getTrxInsuredName() {
		return trxInsuredName;
	}

	public void setTrxInsuredName(String trxInsuredName) {
		this.trxInsuredName = trxInsuredName;
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

	public String getTrxRemarks() {
		return trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}

	public String getTrxDataStatus() {
		return trxDataStatus;
	}

	public void setTrxDataStatus(String trxDataStatus) {
		this.trxDataStatus = trxDataStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getTrxClosingDate() {
		return trxClosingDate;
	}

	public void setTrxClosingDate(Date trxClosingDate) {
		this.trxClosingDate = trxClosingDate;
	}
	
	public String getTrxTsiAmountStr(){
		if(trxTsiAmount == null)
			return null;

		return NumberFormat.getCurrencyInstance().format(trxTsiAmount).replace("$", "");
	}

	public void setTrxTsiAmountStr(String trxTsiAmountStr) {
		this.trxTsiAmountStr = trxTsiAmountStr;
	}

	public String getTrxInsStartFmt() {
		if(trxInsStart != null)
			return dateUI.format(trxInsStart);
		
		return trxInsStartFmt;
	}

	public void setTrxInsStartFmt(String trxInsStartFmt) {
		this.trxInsStartFmt = trxInsStartFmt;
	}

	public String getTrxInsEndFmt() {
		if(trxInsEnd != null)
			return dateUI.format(trxInsEnd);
		
		return trxInsEndFmt;
	}

	public void setTrxInsEndFmt(String trxInsEndFmt) {
		this.trxInsEndFmt = trxInsEndFmt;
	}

	public String getTrxClosingDateFmt() {
		if(trxClosingDate != null)
			return dateUI.format(trxClosingDate);
		
		return trxClosingDateFmt;
	}

	public void setTrxClosingDateFmt(String trxClosingDateFmt) {
		this.trxClosingDateFmt = trxClosingDateFmt;
	}
	
}