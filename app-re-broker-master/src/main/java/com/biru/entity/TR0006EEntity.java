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


/**
 * The persistent class for the tr0006e database table.
 * 
 */
@Entity
@Table(name = "TR0006E")
@NamedQuery(name="TR0006EEntity.findAll", query="SELECT t FROM TR0006EEntity t")
public class TR0006EEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private String idKey;

	@Column(name="TRX_PR_AMT")
	private BigDecimal trxPrAmt;

	@Column(name="TRX_PR_CLIENT")
	private String trxPrClient;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_PR_DATE")
	private Date trxPrDate;

	@Column(name="TRX_PR_SHARE")
	private BigDecimal trxPrShare;

	@Column(name="TRX_CLASS")
	private String trxClass;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Transient
	private String trxPrDateStr;
	
	@Transient
	private String trxPrClientDesc;

	public TR0006EEntity() {
	}
	
	public TR0006EEntity(BigDecimal trxPrAmt, BigDecimal trxPrShare, String trxPrDateStr, String trxPrClient) {
		super();
		this.trxPrAmt = trxPrAmt;
		this.trxPrShare = trxPrShare;
		this.trxPrDateStr = trxPrDateStr;
		this.trxPrClient = trxPrClient;
	}
	
	public TR0006EEntity(BigDecimal trxPrAmt, BigDecimal trxPrShare, String trxPrDateStr, 
			String trxPrClient, String trxPrClientDesc) {
		super();
		this.trxPrAmt = trxPrAmt;
		this.trxPrShare = trxPrShare;
		this.trxPrDateStr = trxPrDateStr;
		this.trxPrClient = trxPrClient;
		this.trxPrClientDesc = trxPrClientDesc;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public BigDecimal getTrxPrAmt() {
		return this.trxPrAmt;
	}

	public void setTrxPrAmt(BigDecimal trxPrAmt) {
		this.trxPrAmt = trxPrAmt;
	}

	public String getTrxPrClient() {
		return this.trxPrClient;
	}

	public void setTrxPrClient(String trxPrClient) {
		this.trxPrClient = trxPrClient;
	}

	public Date getTrxPrDate() {
		return this.trxPrDate;
	}

	public void setTrxPrDate(Date trxPrDate) {
		this.trxPrDate = trxPrDate;
	}

	public BigDecimal getTrxPrShare() {
		return this.trxPrShare;
	}

	public void setTrxPrShare(BigDecimal trxPrShare) {
		this.trxPrShare = trxPrShare;
	}
	
	public String getTrxClass() {
		return trxClass;
	}

	public void setTrxClass(String trxClass) {
		this.trxClass = trxClass;
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

	public String getTrxPrDateStr() {
		return trxPrDateStr;
	}

	public void setTrxPrDateStr(String trxPrDateStr) {
		this.trxPrDateStr = trxPrDateStr;
	}
	
	public String getTrxPrClientDesc() {
		if("0".equals(trxPrClient))
			return "Client";
		if("1".equals(trxPrClient))
			return "Reinsurance";
		
		return trxPrClientDesc;
	}

}