package com.biru.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
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
 * The persistent class for the TR0019 database table.
 * 
 */
@Entity
@Table(name="TR0019")
@NamedQuery(name="TR0019Entity.findAll", query="SELECT t FROM TR0019Entity t")
public class TR0019Entity implements Serializable, Comparable<TR0019Entity> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;

	@Column(name="CO_CODE")
	private String coCode;

	@Column(name="TRX_TYPE")
	private String trxType;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_OLD_VOUCHER_ID")
	private String trxOldVoucherId;

	@Temporal(TemporalType.DATE)
	@Column(name="RNW_DATE")
	private Date rnwDate;
	
	@Column(name="RNW_STATUS")
	private String rnwStatus;
	
	@Transient
	private String rnwDateStr;

	public TR0019Entity(Long idKey, String coCode, String trxType, String trxVoucherId, String trxOldVoucherId, String rnwDateStr, String rnwStatus) {
		super();
		this.idKey = idKey;
		this.coCode = coCode;
		this.trxType = trxType;
		this.trxVoucherId = trxVoucherId;
		this.trxOldVoucherId = trxOldVoucherId;
		this.rnwDateStr = rnwDateStr;
		this.rnwStatus = rnwStatus;
	}
	
	public TR0019Entity() {
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

	public Date getRnwDate() {
		return rnwDate;
	}

	public void setRnwDate(Date rnwDate) {
		this.rnwDate = rnwDate;
	}

	public String getTrxOldVoucherId() {
		return trxOldVoucherId;
	}

	public void setTrxOldVoucherId(String trxOldVoucherId) {
		this.trxOldVoucherId = trxOldVoucherId;
	}

	public String getRnwDateStr() {
		if(rnwDateStr != null)
			return rnwDateStr;
		if(rnwDate == null)
			return null;
		
		return new SimpleDateFormat("dd/MM/yyyy").format(getRnwDate());
	}

	public void setRnwDateStr(String rnwDateStr) {
		this.rnwDateStr = rnwDateStr;
	}
	
	@Override
	public int compareTo(TR0019Entity tR0019Entity) {
		return (this.getTrxVoucherId().compareTo(tR0019Entity.getTrxVoucherId()) == -1 ? -1 : 
            (this.getTrxVoucherId() == tR0019Entity.getTrxVoucherId() ? 0 : 1));
	}

	@Override
	public String toString() {
		return "TR0019Entity [idKey=" + idKey + ", coCode=" + coCode + ", trxType=" + trxType
				+ ", trxVoucherId=" + trxVoucherId + ", rnwDate=" + rnwDate + ", rnwDateStr=" + rnwDateStr
				+ ", rnwStatus=" + rnwStatus + ", trxOldVoucherId=" + trxOldVoucherId + "]";
	}

	public String getCoCode() {
		return coCode;
	}

	public void setCoCode(String coCode) {
		this.coCode = coCode;
	}

	public String getRnwStatus() {
		return rnwStatus;
	}

	public void setRnwStatus(String rnwStatus) {
		this.rnwStatus = rnwStatus;
	}
	
	
	
}