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

@Entity
@Table(name="TR0015A")
@NamedQuery(name="TR0015AEntity.findAll", query="SELECT t FROM TR0015AEntity t")
public class TR0015AEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private Long idKey;
	
	@Column(name="TX_TYPE")
	private String txType;
	
	@Column(name="TX_VOUCHER_ID")
	private String txVoucherId;
	
	@Column(name="TX_COB_GROUP")
	private String txCobGroup;
	
	@Column(name="TX_COVER_ID")
	private String txCoverId;
	
	@Column(name="TX_LAYER")
	private String txLayer;
	
	@Column(name="TX_CLAIM_TYPE")
	private String txClaimType;
	
	@Column(name="TX_CLAIM_REMARKS")
	private String txClaimRemarks;
	
	@Column(name="TX_CURR_ID")
	private String txCurrId;
	
	@Column(name="TX_CLAIM_PLA")
	private BigDecimal txClaimPla = BigDecimal.ZERO;
	
	@Column(name="TX_CLAIM_DLA")
	private BigDecimal txClaimDla = BigDecimal.ZERO;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TX_PAID_DATE")
	private Date txPaidDate;
	
	@Column(name="TX_DATA_STATUS")
	private String txDataStatus;
	
	@Column(name="TX_REINS_NO")
	private String txReinsNo;
	
	@Column(name="TX_MEMO")
	private String txMemo;
	
	@Column(name="TX_ROW_NO")
	private Integer txRowNo;
	
	@Transient
	private String txCobGroupDesc;
	
	@Transient
	private String txCoverIdDesc;
	
	@Transient
	private String txClaimTypeDesc;
	
	@Transient
	private String txDataStatusDesc;
	
	@Transient
	private String txPaidDateFmt;
	
	@Transient
	private String txClaimPlaStr;
	
	@Transient
	private String txClaimDlaStr;
	
	public TR0015AEntity() {
		super();
	}

	public TR0015AEntity(Long idKey, String txType, String txVoucherId, String txCobGroup, String txCoverId,
			String txClaimType, String txClaimRemarks, String txCurrId, BigDecimal txClaimPla, BigDecimal txClaimDla,
			Date txPaidDate, String txDataStatus, String txReinsNo, String txCobGroupDesc, String txCoverIdDesc,
			String txClaimTypeDesc, String txDataStatusDesc, String txPaidDateFmt, String txLayer, String txMemo,
			Integer txRowNo) {
		super();
		this.idKey = idKey;
		this.txType = txType;
		this.txVoucherId = txVoucherId;
		this.txCobGroup = txCobGroup;
		this.txCoverId = txCoverId;
		this.txClaimType = txClaimType;
		this.txClaimRemarks = txClaimRemarks;
		this.txCurrId = txCurrId;
		this.txClaimPla = txClaimPla;
		this.txClaimDla = txClaimDla;
		this.txPaidDate = txPaidDate;
		this.txDataStatus = txDataStatus;
		this.txReinsNo = txReinsNo;
		this.txCobGroupDesc = txCobGroupDesc;
		this.txCoverIdDesc = txCoverIdDesc;
		this.txClaimTypeDesc = txClaimTypeDesc;
		this.txDataStatusDesc = txDataStatusDesc;
		this.txPaidDateFmt = txPaidDateFmt;
		this.txLayer = txLayer;
		this.txMemo = txMemo;
		this.txRowNo = txRowNo;
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

	public String getTxCobGroup() {
		return txCobGroup;
	}

	public void setTxCobGroup(String txCobGroup) {
		this.txCobGroup = txCobGroup;
	}

	public String getTxCoverId() {
		return txCoverId;
	}

	public void setTxCoverId(String txCoverId) {
		this.txCoverId = txCoverId;
	}
	
	public String getTxLayer() {
		return txLayer;
	}

	public void setTxLayer(String txLayer) {
		this.txLayer = txLayer;
	}

	public String getTxClaimType() {
		return txClaimType;
	}

	public void setTxClaimType(String txClaimType) {
		this.txClaimType = txClaimType;
	}

	public String getTxClaimRemarks() {
		return txClaimRemarks;
	}

	public void setTxClaimRemarks(String txClaimRemarks) {
		this.txClaimRemarks = txClaimRemarks;
	}

	public String getTxCurrId() {
		return txCurrId;
	}

	public void setTxCurrId(String txCurrId) {
		this.txCurrId = txCurrId;
	}

	public BigDecimal getTxClaimPla() {
		return txClaimPla;
	}

	public void setTxClaimPla(BigDecimal txClaimPla) {
		this.txClaimPla = txClaimPla;
	}

	public BigDecimal getTxClaimDla() {
		return txClaimDla;
	}

	public void setTxClaimDla(BigDecimal txClaimDla) {
		this.txClaimDla = txClaimDla;
	}

	public Date getTxPaidDate() {
		return txPaidDate;
	}

	public void setTxPaidDate(Date txPaidDate) {
		this.txPaidDate = txPaidDate;
	}

	public String getTxDataStatus() {
		return txDataStatus;
	}

	public void setTxDataStatus(String txDataStatus) {
		this.txDataStatus = txDataStatus;
	}

	public String getTxReinsNo() {
		return txReinsNo;
	}

	public void setTxReinsNo(String txReinsNo) {
		this.txReinsNo = txReinsNo;
	}
	
	public String getTxMemo() {
		return txMemo;
	}

	public void setTxMemo(String txMemo) {
		this.txMemo = txMemo;
	}

	public Integer getTxRowNo() {
		return txRowNo;
	}

	public void setTxRowNo(Integer txRowNo) {
		this.txRowNo = txRowNo;
	}

	public String getTxCobGroupDesc() {
		return txCobGroupDesc;
	}

	public void setTxCobGroupDesc(String txCobGroupDesc) {
		this.txCobGroupDesc = txCobGroupDesc;
	}

	public String getTxCoverIdDesc() {
		return txCoverIdDesc;
	}

	public void setTxCoverIdDesc(String txCoverIdDesc) {
		this.txCoverIdDesc = txCoverIdDesc;
	}

	public String getTxClaimTypeDesc() {
		return txClaimTypeDesc;
	}

	public void setTxClaimTypeDesc(String txClaimTypeDesc) {
		this.txClaimTypeDesc = txClaimTypeDesc;
	}

	public String getTxDataStatusDesc() {
		return txDataStatusDesc;
	}

	public void setTxDataStatusDesc(String txDataStatusDesc) {
		this.txDataStatusDesc = txDataStatusDesc;
	}

	public String getTxPaidDateFmt() {
		return txPaidDateFmt;
	}

	public void setTxPaidDateFmt(String txPaidDateFmt) {
		this.txPaidDateFmt = txPaidDateFmt;
	}
	
	public String getTxClaimPlaStr() {
		if(this.txClaimPla == null)
			return null;
			
		return NumberFormat.getCurrencyInstance().format(this.txClaimPla).replace("$", "");
	}

	public void setTxClaimPlaStr(String txClaimPlaStr) {
		this.txClaimPlaStr = txClaimPlaStr;
	}

	public String getTxClaimDlaStr() {
		if(this.txClaimDla == null)
			return null;
			
		return NumberFormat.getCurrencyInstance().format(this.txClaimDla).replace("$", "");
	}

	public void setTxClaimDlaStr(String txClaimDlaStr) {
		this.txClaimDlaStr = txClaimDlaStr;
	}

	@Override
	public String toString() {
		return "TR0015AEntity [idKey=" + idKey + ", txType=" + txType + ", txVoucherId=" + txVoucherId + ", txCobGroup="
				+ txCobGroup + ", txCoverId=" + txCoverId + ", txLayer=" + txLayer + ", txClaimType=" + txClaimType
				+ ", txClaimRemarks=" + txClaimRemarks + ", txCurrId=" + txCurrId + ", txClaimPla=" + txClaimPla
				+ ", txClaimDla=" + txClaimDla + ", txPaidDate=" + txPaidDate + ", txDataStatus=" + txDataStatus
				+ ", txReinsNo=" + txReinsNo + ", txMemo=" + txMemo + ", txRowNo=" + txRowNo + ", txCobGroupDesc="
				+ txCobGroupDesc + ", txCoverIdDesc=" + txCoverIdDesc + ", txClaimTypeDesc=" + txClaimTypeDesc
				+ ", txDataStatusDesc=" + txDataStatusDesc + ", txPaidDateFmt=" + txPaidDateFmt + ", txClaimPlaStr="
				+ txClaimPlaStr + ", txClaimDlaStr=" + txClaimDlaStr + "]";
	}
	
}
