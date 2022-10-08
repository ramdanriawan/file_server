package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.*;

/**
 * The persistent class for the TR0006K database table.
 * 
 */
@Entity
@Table(name="TR0006K")
@NamedQuery(name="TR0006KEntity.findAll", query="SELECT t FROM TR0006KEntity t")
public class TR0006KEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEY")
	private String idKey;

	@Column(name = "TRX_CLASS")
	private String trxClass;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;
	
	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_COB_GROUP")
	private String trxCobGroup;
	
	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;
	
	@Column(name="TRX_LAYER")
	private String trxLayer;
	
	@Column(name="TRX_INS_ID")
	private String trxInsId;
	
	@Column(name="TRX_INS_SHARE")
	private BigDecimal trxInsShare = BigDecimal.ZERO;
	
	@Column(name="TRX_PREMIUM")
	private BigDecimal trxPremium = BigDecimal.ZERO;
	
	@Column(name="TRX_RI_COMM")
	private BigDecimal trxRiComm = BigDecimal.ZERO;
	
	@Transient
	private String trxInsIdDesc;
	
	@Transient
	private String trxCobGroupDesc;
	
	@Transient
	private String trxCoverCodeDesc;
	
	public TR0006KEntity() {
	}
	
	public TR0006KEntity(String trxLayer, String trxInsId, String trxInsIdDesc) {
		super();
		this.trxLayer = trxLayer;
		this.trxInsId = trxInsId;
		this.trxInsIdDesc = trxInsIdDesc;
	}
	
	public TR0006KEntity(String idKey, String trxClass, String trxTrxId, String trxVoucherId, 
			String trxCobGroup, String trxCoverCode, String trxLayer, String trxInsId, 
			BigDecimal trxInsShare, BigDecimal trxPremium, BigDecimal trxRiComm, 
			String trxInsIdDesc, String trxCobGroupDesc, String trxCoverCodeDesc) {
		super();
		this.idKey = idKey;
		this.trxClass = trxClass;
		this.trxTrxId = trxTrxId;
		this.trxVoucherId = trxVoucherId;
		this.trxCobGroup = trxCobGroup;
		this.trxCoverCode = trxCoverCode;
		this.trxLayer = trxLayer;
		this.trxInsId = trxInsId;
		this.trxInsShare = trxInsShare;
		this.trxPremium = trxPremium;
		this.trxRiComm = trxRiComm;
		this.trxInsIdDesc = trxInsIdDesc;
		this.trxCobGroupDesc = trxCobGroupDesc;
		this.trxCoverCodeDesc = trxCoverCodeDesc;
	}
	
	public TR0006KEntity(String idKey, String trxClass, 
			String trxTrxId, String trxVoucherId, 
			String trxInsId, BigDecimal trxInsShare,
			String trxInsIdDesc) {
		super();
		this.idKey = idKey;
		this.trxClass = trxClass;
		this.trxTrxId = trxTrxId;
		this.trxVoucherId = trxVoucherId;
		this.trxInsId = trxInsId;
		this.trxInsShare = trxInsShare;
		this.trxInsIdDesc = trxInsIdDesc;
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

	public String getTrxLayer() {
		return trxLayer;
	}

	public void setTrxLayer(String trxLayer) {
		this.trxLayer = trxLayer;
	}

	public String getTrxInsId() {
		return trxInsId;
	}

	public void setTrxInsId(String trxInsId) {
		this.trxInsId = trxInsId;
	}

	public BigDecimal getTrxInsShare() {
		return trxInsShare;
	}

	public void setTrxInsShare(BigDecimal trxInsShare) {
		this.trxInsShare = trxInsShare;
	}

	public BigDecimal getTrxPremium() {
		return trxPremium;
	}

	public void setTrxPremium(BigDecimal trxPremium) {
		this.trxPremium = trxPremium;
	}

	public BigDecimal getTrxRiComm() {
		return trxRiComm;
	}

	public void setTrxRiComm(BigDecimal trxRiComm) {
		this.trxRiComm = trxRiComm;
	}

	public String getTrxInsIdDesc() {
		return trxInsIdDesc;
	}

	public void setTrxInsIdDesc(String trxInsIdDesc) {
		this.trxInsIdDesc = trxInsIdDesc;
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
	
}