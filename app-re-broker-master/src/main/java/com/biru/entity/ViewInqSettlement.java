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
 * The persistent class for the VIEW_INQ_SETTLEMENT database table.
 * 
 */
@Entity
@Table(name="VIEW_INQ_SETTLEMENT")
@NamedQuery(name="ViewInqSettlement.findAll", query="SELECT v FROM ViewInqSettlement v")
public class ViewInqSettlement implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;

	@Column(name="TRX_TYPE")
	private String trxType;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="DC_NOTES_VOUCHER_ID")
	private String dcNotesVoucherId;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DUE_DATE")
	private Date trxDueDate;

	@Column(name="TRX_COUNT_INV")
	private Integer trxCountInv;

	@Column(name="TRX_CLIENT")
	private String trxClient;
	
	@Column(name="CLI_NAME")
	private String cliName;

	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;

	@Column(name="TRX_DESCRIPTION")
	private String trxDescription;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;

	@Column(name="TRX_CURR_RATE")
	private BigDecimal trxCurrRate;

	@Column(name="TRX_ORG_AMOUNT")
	private BigDecimal trxOrgAmount;
	
	@Column(name="TRX_INVC_AMOUNT")
	private BigDecimal trxInvcAmount = BigDecimal.ZERO;
	
	@Column(name="TRX_SET_AMOUNT")
	private BigDecimal trxSetAmount = BigDecimal.ZERO;
	
	@Column(name="TRX_COM_AMOUNT")
	private BigDecimal trxComAmount = BigDecimal.ZERO;
	
	@Column(name="TRX_REMARKS")
	private String trxRemarks;
	
	public ViewInqSettlement() {
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

	public String getTrxVoucherId() {
		return trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public String getDcNotesVoucherId() {
		return dcNotesVoucherId;
	}

	public void setDcNotesVoucherId(String dcNotesVoucherId) {
		this.dcNotesVoucherId = dcNotesVoucherId;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public Date getTrxDueDate() {
		return trxDueDate;
	}

	public void setTrxDueDate(Date trxDueDate) {
		this.trxDueDate = trxDueDate;
	}

	public Integer getTrxCountInv() {
		return trxCountInv;
	}

	public void setTrxCountInv(Integer trxCountInv) {
		this.trxCountInv = trxCountInv;
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

	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
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

	public BigDecimal getTrxCurrRate() {
		return trxCurrRate;
	}

	public void setTrxCurrRate(BigDecimal trxCurrRate) {
		this.trxCurrRate = trxCurrRate;
	}

	public BigDecimal getTrxOrgAmount() {
		return trxOrgAmount;
	}

	public void setTrxOrgAmount(BigDecimal trxOrgAmount) {
		this.trxOrgAmount = trxOrgAmount;
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

	public BigDecimal getTrxComAmount() {
		return trxComAmount;
	}

	public void setTrxComAmount(BigDecimal trxComAmount) {
		this.trxComAmount = trxComAmount;
	}

	public String getTrxRemarks() {
		return trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}

	@Override
	public String toString() {
		return "ViewInqSettlement [idKey=" + idKey + ", trxType=" + trxType + ", trxVoucherId=" + trxVoucherId
				+ ", trxDate=" + trxDate + ", trxDueDate=" + trxDueDate + ", trxCountInv=" + trxCountInv
				+ ", trxClient=" + trxClient + ", cliName=" + cliName + ", trxCoverCode=" + trxCoverCode
				+ ", trxDescription=" + trxDescription + ", trxCurrId=" + trxCurrId + ", trxCurrRate=" + trxCurrRate
				+ ", trxOrgAmount=" + trxOrgAmount + ", trxInvcAmount=" + trxInvcAmount + ", trxSetAmount="
				+ trxSetAmount + ", trxComAmount=" + trxComAmount + ", trxRemarks=" + trxRemarks + "]";
	}
	
}