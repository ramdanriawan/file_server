package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.math.BigDecimal;

/**
 * The persistent class for the TR0006G database table.
 * 
 */
@Entity
@Table(name = "TR0006G")
@NamedQuery(name="TR0006GEntity.findAll", query="SELECT t FROM TR0006GEntity t")
public class TR0006GEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private String idKey;
	
	@Column(name="TRX_CLASS")
	private String trxClass;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;
	
	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_SA_CODE")
	private String trxSaCode;
	
	@Column(name="TRX_SA_TAX_ID")
	private String trxSaTaxId;
	
	@Column(name="TRX_COMM_PCT")
	private BigDecimal trxCommPct;
	
	@Column(name="TRX_COMM_AMT")
	private BigDecimal trxCommAmt;
	
	@Transient
	private String trxSaCodeDesc;

	public TR0006GEntity() {
	}

	public TR0006GEntity(String idKey, String trxTrxId, String trxVoucherId, String trxSaCode, String trxSaTaxId,
			BigDecimal trxCommPct, BigDecimal trxCommAmt, String trxSaCodeDesc) {
		super();
		this.idKey = idKey;
		this.trxTrxId = trxTrxId;
		this.trxVoucherId = trxVoucherId;
		this.trxSaCode = trxSaCode;
		this.trxSaTaxId = trxSaTaxId;
		this.trxCommPct = trxCommPct;
		this.trxCommAmt = trxCommAmt;
		this.trxSaCodeDesc = trxSaCodeDesc;
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

	public String getTrxSaCode() {
		return trxSaCode;
	}

	public void setTrxSaCode(String trxSaCode) {
		this.trxSaCode = trxSaCode;
	}

	public String getTrxSaTaxId() {
		return trxSaTaxId;
	}

	public void setTrxSaTaxId(String trxSaTaxId) {
		this.trxSaTaxId = trxSaTaxId;
	}

	public BigDecimal getTrxCommPct() {
		return trxCommPct;
	}

	public void setTrxCommPct(BigDecimal trxCommPct) {
		this.trxCommPct = trxCommPct;
	}

	public BigDecimal getTrxCommAmt() {
		return trxCommAmt;
	}

	public void setTrxCommAmt(BigDecimal trxCommAmt) {
		this.trxCommAmt = trxCommAmt;
	}

	public String getTrxSaCodeDesc() {
		return trxSaCodeDesc;
	}

	public void setTrxSaCodeDesc(String trxSaCodeDesc) {
		this.trxSaCodeDesc = trxSaCodeDesc;
	}
	
}