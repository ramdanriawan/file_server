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

@Entity
@Table(name="TR0015D")
@NamedQuery(name="TR0015DEntity.findAll", query="SELECT t FROM TR0015DEntity t")
public class TR0015DEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private Long idKey;
	
	@Column(name="TX_TYPE")
	private String txType;
	
	@Column(name="TX_VOUCHER_ID")
	private String txVoucherId;
	
	@Column(name="TX_INS_ID")
	private String txInsId;
	
	@Column(name="TX_INS_TYPE")
	private String txInsType;
	
	@Column(name="TX_INS_SHARE")
	private BigDecimal txInsShare;
	
	@Column(name="TX_INS_STATUS")
	private String txInsStatus;
	
	@Column(name="TX_INS_ATT")
	private String txInsAtt;
	
	@Column(name="TX_CURR_ID")
	private String txCurrId;
	
	@Column(name="TX_INS_AMOUNT")
	private BigDecimal txInsAmount = BigDecimal.ZERO;
	
	@Column(name="TX_INS_REFF_IN")
	private String txInsReffIn;
	
	@Column(name="TX_INS_REFF_OT")
	private String txInsReffOt;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TX_INS_PDATE")
	private Date txInsPdate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TX_INS_RDATE")
	private Date txInsRdate;
	
	@Column(name="TX_INS_RECEIVED_RC")
	private BigDecimal txInsReceivedRc = BigDecimal.ZERO;
	
	@Column(name="TX_INS_RECEIVED_PY")
	private BigDecimal txInsReceivedPy = BigDecimal.ZERO;
	
	@Transient
	private String txInsDesc;
	
	@Transient
	private String receive;
	
	@Transient
	private String action;
	
	public TR0015DEntity() {
	}

	public TR0015DEntity(Long idKey, String txVoucherId, String txInsId, String txInsDesc,
			String txCurrId, BigDecimal txInsAmount, BigDecimal txInsReceivedRc, 
			BigDecimal txInsReceivedPy, String txInsReffIn, String txInsReffOt, String txInsAtt) {
		this.idKey = idKey;
		this.txVoucherId = txVoucherId;
		this.txInsId = txInsId;
		this.txInsDesc = txInsDesc;
		this.txCurrId = txCurrId;
		this.txInsAmount = txInsAmount;
		this.txInsReceivedRc = txInsReceivedRc;
		this.txInsReceivedPy = txInsReceivedPy;
		this.txInsReffIn = txInsReffIn;
		this.txInsReffOt = txInsReffOt;
		this.txInsAtt = txInsAtt;
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
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

	public String getTxInsId() {
		return txInsId;
	}

	public void setTxInsId(String txInsId) {
		this.txInsId = txInsId;
	}

	public String getTxInsType() {
		return txInsType;
	}

	public void setTxInsType(String txInsType) {
		this.txInsType = txInsType;
	}

	public BigDecimal getTxInsShare() {
		return txInsShare;
	}

	public void setTxInsShare(BigDecimal txInsShare) {
		this.txInsShare = txInsShare;
	}

	public String getTxInsStatus() {
		return txInsStatus;
	}

	public void setTxInsStatus(String txInsStatus) {
		this.txInsStatus = txInsStatus;
	}

	public String getTxInsAtt() {
		return txInsAtt;
	}

	public void setTxInsAtt(String txInsAtt) {
		this.txInsAtt = txInsAtt;
	}

	public String getTxCurrId() {
		return txCurrId;
	}

	public void setTxCurrId(String txCurrId) {
		this.txCurrId = txCurrId;
	}

	public BigDecimal getTxInsAmount() {
		return txInsAmount;
	}

	public void setTxInsAmount(BigDecimal txInsAmount) {
		this.txInsAmount = txInsAmount;
	}

	public String getTxInsReffIn() {
		return txInsReffIn;
	}

	public void setTxInsReffIn(String txInsReffIn) {
		this.txInsReffIn = txInsReffIn;
	}

	public String getTxInsReffOt() {
		return txInsReffOt;
	}

	public void setTxInsReffOt(String txInsReffOt) {
		this.txInsReffOt = txInsReffOt;
	}

	public Date getTxInsPdate() {
		return txInsPdate;
	}

	public void setTxInsPdate(Date txInsPdate) {
		this.txInsPdate = txInsPdate;
	}

	public Date getTxInsRdate() {
		return txInsRdate;
	}

	public void setTxInsRdate(Date txInsRdate) {
		this.txInsRdate = txInsRdate;
	}

	public BigDecimal getTxInsReceivedRc() {
		return txInsReceivedRc;
	}

	public void setTxInsReceivedRc(BigDecimal txInsReceivedRc) {
		this.txInsReceivedRc = txInsReceivedRc;
	}

	public BigDecimal getTxInsReceivedPy() {
		return txInsReceivedPy;
	}

	public void setTxInsReceivedPy(BigDecimal txInsReceivedPy) {
		this.txInsReceivedPy = txInsReceivedPy;
	}

	public String getTxInsDesc() {
		return txInsDesc;
	}

	public void setTxInsDesc(String txInsDesc) {
		this.txInsDesc = txInsDesc;
	}

	public String getReceive() {
		return receive;
	}

	public void setReceive(String receive) {
		this.receive = receive;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
}
