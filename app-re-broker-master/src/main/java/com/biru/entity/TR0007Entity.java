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
 * The persistent class for the TR0007 database table.
 * 
 */
@Entity
@Table(name="TR0007")
@NamedQuery(name="TR0007Entity.findAll", query="SELECT t FROM TR0007Entity t")
public class TR0007Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name="TRX_OLD_TRX_ID")
	private String trxOldTrxId;

	@Column(name="TRX_OLD_VOUCHER_ID")
	private String trxOldVoucherId;
	
	@Column(name="TRX_OLD_COUNT_INV")
	private Integer trxOldCountInv;

	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_SET_AMOUNT")
	private BigDecimal trxSetAmount = BigDecimal.ZERO;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_SET_DATE")
	private Date trxSetDate;

	public TR0007Entity() {
		super();
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getTrxOldTrxId() {
		return trxOldTrxId;
	}

	public void setTrxOldTrxId(String trxOldTrxId) {
		this.trxOldTrxId = trxOldTrxId;
	}

	public String getTrxOldVoucherId() {
		return trxOldVoucherId;
	}

	public void setTrxOldVoucherId(String trxOldVoucherId) {
		this.trxOldVoucherId = trxOldVoucherId;
	}

	public Integer getTrxOldCountInv() {
		return trxOldCountInv;
	}

	public void setTrxOldCountInv(Integer trxOldCountInv) {
		this.trxOldCountInv = trxOldCountInv;
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

	public BigDecimal getTrxSetAmount() {
		return trxSetAmount;
	}

	public void setTrxSetAmount(BigDecimal trxSetAmount) {
		this.trxSetAmount = trxSetAmount;
	}

	public Date getTrxSetDate() {
		return trxSetDate;
	}

	public void setTrxSetDate(Date trxSetDate) {
		this.trxSetDate = trxSetDate;
	}

	@Override
	public String toString() {
		return "TR0007Entity [idKey=" + idKey + ", trxOldTrxId=" + trxOldTrxId + ", trxOldVoucherId=" + trxOldVoucherId
				+ ", trxOldCountInv=" + trxOldCountInv + ", trxTrxId=" + trxTrxId + ", trxVoucherId=" + trxVoucherId
				+ ", trxSetAmount=" + trxSetAmount + ", trxSetDate=" + trxSetDate + "]";
	}
	
}