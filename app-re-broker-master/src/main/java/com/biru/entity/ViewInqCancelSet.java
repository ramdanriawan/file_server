package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
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
 * The persistent class for the VIEW_INQ_CANCEL_SET database table.
 * 
 */
@Entity
@Table(name="VIEW_INQ_CANCEL_SET")
@NamedQuery(name="ViewInqCancelSet.findAll", query="SELECT t FROM ViewInqCancelSet t")
public class ViewInqCancelSet implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;

	@Column(name="TRX_TYPE")
	private String trxType;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;
	
	@Column(name="TRX_DATE_STR")
	private String trxDateStr;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_CLIENT")
	private String trxClient;

	@Column(name="TRX_CLIENT_DESC")
	private String trxClientDesc;

	@Column(name="TRX_DESCRIPTION")
	private String trxDescription;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;
	
	@Column(name="TRX_INVC_AMOUNT")
	private BigDecimal trxInvcAmount = BigDecimal.ZERO;
	
	@Column(name="TRX_SET_AMOUNT")
	private BigDecimal trxSetAmount = BigDecimal.ZERO;
	
	@Column(name="TRX_INVOICE_AMOUNT")
	private BigDecimal trxInvoiceAmount = BigDecimal.ZERO;
	
	@Column(name="TRX_REMARKS")
	private String trxRemarks;
	
	@Transient
	private String action;
	
	@Transient
	private String trxSetAmountStr;
	
	
	public ViewInqCancelSet() {
		super();
	}

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

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public String getTrxDateStr() {
		return trxDateStr;
	}

	public void setTrxDateStr(String trxDateStr) {
		this.trxDateStr = trxDateStr;
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

	public String getTrxClientDesc() {
		return trxClientDesc;
	}

	public void setTrxClientDesc(String trxClientDesc) {
		this.trxClientDesc = trxClientDesc;
	}

	public String getTrxDescription() {
		return trxDescription;
	}

	public void setTrxDescription(String trxDescription) {
		this.trxDescription = trxDescription;
	}

	public String getTrxCurrId() {
		return trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public BigDecimal getTrxInvcAmount() {
		return trxInvcAmount;
	}

	public void setTrxInvcAmount(BigDecimal trxInvcAmount) {
		this.trxInvcAmount = trxInvcAmount;
	}

	public BigDecimal getTrxSetAmount() {
		return trxSetAmount;
	}

	public void setTrxSetAmount(BigDecimal trxSetAmount) {
		this.trxSetAmount = trxSetAmount;
	}

	public BigDecimal getTrxInvoiceAmount() {
		return trxInvoiceAmount;
	}

	public void setTrxInvoiceAmount(BigDecimal trxInvoiceAmount) {
		this.trxInvoiceAmount = trxInvoiceAmount;
	}

	public String getTrxRemarks() {
		return trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}
	
	public String getTrxSetAmountStr() {
		if(trxSetAmount == null)
			return null;
			
		return NumberFormat.getCurrencyInstance().format(trxSetAmount).replace("$", "");
	}

	public void setTrxSetAmountStr(String trxSetAmountStr) {
		this.trxSetAmountStr = trxSetAmountStr;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "ViewInqCancelSet [idKey=" + idKey + ", trxType=" + trxType + ", trxDate=" + trxDate + ", trxDateStr="
				+ trxDateStr + ", trxVoucherId=" + trxVoucherId + ", trxClient=" + trxClient + ", trxClientDesc="
				+ trxClientDesc + ", trxDescription=" + trxDescription + ", trxCurrId=" + trxCurrId + ", trxInvcAmount="
				+ trxInvcAmount + ", trxSetAmount=" + trxSetAmount + ", trxInvoiceAmount=" + trxInvoiceAmount
				+ ", trxRemarks=" + trxRemarks + ", action=" + action + "]";
	}
	
}