package com.biru.entity;

import java.io.Serializable;
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
 * The persistent class for the tr0006d database table.
 * 
 */
@Entity
@Table(name = "TR0006D")
@NamedQuery(name="TR0006DEntity.findAll", query="SELECT t FROM TR0006DEntity t")
public class TR0006DEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private String idKey;
	
	@Column(name="TRX_CLASS")
	private String trxClass;

	@Column(name="TRX_TC_CODE")
	private String trxTcCode;

	@Column(name="TRX_TC_DATA")
	private String trxTcData;

	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_NON_PRO")
	private String trxNonPro;
	
	@Column(name="TRX_COB_GROUP")
	private String trxCobGroup;
	
	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;
	
	@Column(name="TRX_INS_DATE")
	@Temporal(value=TemporalType.TIMESTAMP)
	private Date trxInsDate;
	
	@Transient
	private String trxCobGroupDesc;
	
	@Transient
	private String trxCoverCodeDesc;

	public TR0006DEntity() {
	}
	
	public TR0006DEntity(String idKey, String trxClass, String trxTcCode, String trxTcData, String trxTrxId,
			String trxVoucherId, String trxNonPro, String trxCobGroup, String trxCoverCode, String trxCobGroupDesc, String trxCoverCodeDesc) {
		super();
		this.idKey = idKey;
		this.trxClass = trxClass;
		this.trxTcCode = trxTcCode;
		this.trxTcData = trxTcData;
		this.trxTrxId = trxTrxId;
		this.trxVoucherId = trxVoucherId;
		this.trxNonPro = trxNonPro;
		this.trxCobGroup = trxCobGroup;
		this.trxCoverCode = trxCoverCode;
		this.trxCobGroupDesc = trxCobGroupDesc;
		this.trxCoverCodeDesc = trxCoverCodeDesc;
	}

	public String getIdKey() {
		return this.idKey;
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

	public String getTrxTcCode() {
		return this.trxTcCode;
	}

	public void setTrxTcCode(String trxTcCode) {
		this.trxTcCode = trxTcCode;
	}

	public String getTrxTcData() {
		return this.trxTcData;
	}

	public void setTrxTcData(String trxTcData) {
		this.trxTcData = trxTcData;
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

	public String getTrxNonPro() {
		return trxNonPro;
	}

	public void setTrxNonPro(String trxNonPro) {
		this.trxNonPro = trxNonPro;
	}

	public String getTrxCobGroup() {
		return trxCobGroup;
	}

	public void setTrxCobGroup(String trxCobGroup) {
		this.trxCobGroup = trxCobGroup;
	}

	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public String getTrxCobGroupDesc() {
		return trxCobGroupDesc;
	}

	public void setTrxCobGroupDesc(String trxCobGroupDesc) {
		this.trxCobGroupDesc = trxCobGroupDesc;
	}

	public String getTrxCoverCodeDesc() {
		return trxCoverCodeDesc;
	}

	public void setTrxCoverCodeDesc(String trxCoverCodeDesc) {
		this.trxCoverCodeDesc = trxCoverCodeDesc;
	}

	public Date getTrxInsDate() {
		return trxInsDate;
	}

	public void setTrxInsDate(Date trxInsDate) {
		this.trxInsDate = trxInsDate;
	}
}