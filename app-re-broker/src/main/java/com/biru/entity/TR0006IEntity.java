package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;

/**
 * The persistent class for the tr0006i database table.
 * 
 */
@Entity
@Table(name="TR0006I")
@NamedQuery(name="TR0006IEntity.findAll", query="SELECT t FROM TR0006IEntity t")
public class TR0006IEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEY")
	private String idKey;

	@Column(name="TRX_BANK_CODE")
	private String trxBankCode;

	@Column(name="TRX_CLASS")
	private String trxClass;

	@Column(name="TRX_COMM_AMT")
	private BigDecimal trxCommAmt;

	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Transient
	private String trxBankCodeDesc;
	
	public TR0006IEntity(String idKey, String trxBankCode, String trxClass, BigDecimal trxCommAmt, String trxTrxId,
			String trxVoucherId, String trxBankCodeDesc) {
		super();
		this.idKey = idKey;
		this.trxBankCode = trxBankCode;
		this.trxClass = trxClass;
		this.trxCommAmt = trxCommAmt;
		this.trxTrxId = trxTrxId;
		this.trxVoucherId = trxVoucherId;
		this.trxBankCodeDesc = trxBankCodeDesc;
	}

	public TR0006IEntity() {
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getTrxBankCode() {
		return this.trxBankCode;
	}

	public void setTrxBankCode(String trxBankCode) {
		this.trxBankCode = trxBankCode;
	}

	public String getTrxClass() {
		return this.trxClass;
	}

	public void setTrxClass(String trxClass) {
		this.trxClass = trxClass;
	}

	public BigDecimal getTrxCommAmt() {
		return this.trxCommAmt;
	}

	public void setTrxCommAmt(BigDecimal trxCommAmt) {
		this.trxCommAmt = trxCommAmt;
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

	public String getTrxBankCodeDesc() {
		return trxBankCodeDesc;
	}

	public void setTrxBankCodeDesc(String trxBankCodeDesc) {
		this.trxBankCodeDesc = trxBankCodeDesc;
	}
	
}