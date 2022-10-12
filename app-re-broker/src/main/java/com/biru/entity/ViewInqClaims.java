package com.biru.entity;

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

@Entity
@Table(name="VIEW_INQ_CLAIMS")
@NamedQuery(name="ViewInqClaims.findAll", query="SELECT v FROM ViewInqClaims v")
public class ViewInqClaims implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID_KEY")
	private Long idKey;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;
	
	@Column(name="TX_TYPE")
	private String txType;
	
	@Column(name="TX_VOUCHER_ID")
	private String txVoucherId;
	
	@Column(name="TX_COVER_ID")
	private String txCoverId;
	
	@Column(name="TX_CLIENT_ID")
	private String txClientId;
	
	@Column(name="TX_CLIENT_CODE")
	private String txClientCode;
	
	@Column(name="TX_INS_ID")
	private String txInsId;
	
	@Column(name="TX_INS_CODE")
	private String txInsCode;
	
	@Column(name="TX_POLICY_NO")
	private String txPolicyNo;
	
	@Column(name="TX_INTEREST")
	private String txInterest;

	@Column(name="TX_PARTY_NAME")
	private String txPartyName;
	
	@Column(name="TX_CURR_ID")
	private String txCurrId;
	
	@Column(name="TX_SUM_INS")
	private BigDecimal txSumIns;
	
	@Column(name="TX_DATA_STATUS")
	private String txDataStatus;
	
	@Column(name="TX_DATA_STATUS_VAL")
	private String txDataStatusVal;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TX_ADVICE_DATE")
	private Date txAdviceDate;

	@Transient
	private String createOnStr;
	
	@Transient
	private String txSumInsStr;
	
	@Transient
	private String action;
	
	@Transient
	private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	
	public ViewInqClaims(){
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getTxVoucherId() {
		return txVoucherId;
	}

	public void setTxVoucherId(String txVoucherId) {
		this.txVoucherId = txVoucherId;
	}

	public String getTxCoverId() {
		return txCoverId;
	}

	public void setTxCoverId(String txCoverId) {
		this.txCoverId = txCoverId;
	}

	public String getTxClientId() {
		return txClientId;
	}

	public void setTxClientId(String txClientId) {
		this.txClientId = txClientId;
	}

	public String getTxClientCode() {
		return txClientCode;
	}

	public void setTxClientCode(String txClientCode) {
		this.txClientCode = txClientCode;
	}

	public String getTxInsId() {
		return txInsId;
	}

	public void setTxInsId(String txInsId) {
		this.txInsId = txInsId;
	}

	public String getTxInsCode() {
		return txInsCode;
	}

	public void setTxInsCode(String txInsCode) {
		this.txInsCode = txInsCode;
	}

	public String getTxPolicyNo() {
		return txPolicyNo;
	}

	public void setTxPolicyNo(String txPolicyNo) {
		this.txPolicyNo = txPolicyNo;
	}

	public String getTxInterest() {
		return txInterest;
	}

	public void setTxInterest(String txInterest) {
		this.txInterest = txInterest;
	}

	public String getTxPartyName() {
		return txPartyName;
	}

	public void setTxPartyName(String txPartyName) {
		this.txPartyName = txPartyName;
	}

	public String getTxCurrId() {
		return txCurrId;
	}

	public void setTxCurrId(String txCurrId) {
		this.txCurrId = txCurrId;
	}

	public BigDecimal getTxSumIns() {
		return txSumIns;
	}

	public void setTxSumIns(BigDecimal txSumIns) {
		this.txSumIns = txSumIns;
	}

	public String getTxDataStatus() {
		return txDataStatus;
	}

	public void setTxDataStatus(String txDataStatus) {
		this.txDataStatus = txDataStatus;
	}

	public Date getTxAdviceDate() {
		return txAdviceDate;
	}

	public void setTxAdviceDate(Date txAdviceDate) {
		this.txAdviceDate = txAdviceDate;
	}

	public String getTxDataStatusVal() {
		return txDataStatusVal;
	}

	public void setTxDataStatusVal(String txDataStatusVal) {
		this.txDataStatusVal = txDataStatusVal;
	}

	public String getCreateOnStr() {
		if(createOn != null)
			return sdf.format(createOn);
		
		return createOnStr;
	}

	public void setCreateOnStr(String createOnStr) {
		this.createOnStr = createOnStr;
	}

	public String getTxSumInsStr() {
		if(txSumIns != null)
			return NumberFormat.getCurrencyInstance().format(txSumIns).replace("$", "");
		
		return txSumInsStr;
	}

	public void setTxSumInsStr(String txSumInsStr) {
		this.txSumInsStr = txSumInsStr;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
