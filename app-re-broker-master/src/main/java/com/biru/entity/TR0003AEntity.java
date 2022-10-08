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
 * The persistent class for the TR0003A database table.
 * 
 */
@Entity
@Table(name="TR0003A")
@NamedQuery(name="TR0003AEntity.findAll", query="SELECT t FROM TR0003AEntity t")
public class TR0003AEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name="TRX_TRX_CLASS")
	private String trxTrxClass;
	
	@Column(name="TRX_TYPE")
	private String trxType;	

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_NO_ROW")
	private Integer trxNoRow;
	
	@Column(name="TRX_REMARKS")
	private String trxRemarks;
			
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DUE_DATE")
	private Date trxDueDate;
	
	@Column(name="TRX_DUE_AMOUNT")
	private BigDecimal trxDueAmount;

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getTrxTrxClass() {
		return trxTrxClass;
	}

	public void setTrxTrxClass(String trxTrxClass) {
		this.trxTrxClass = trxTrxClass;
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

	public Integer getTrxNoRow() {
		return trxNoRow;
	}

	public void setTrxNoRow(Integer trxNoRow) {
		this.trxNoRow = trxNoRow;
	}

	public String getTrxRemarks() {
		return trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}

	public Date getTrxDueDate() {
		return trxDueDate;
	}

	public void setTrxDueDate(Date trxDueDate) {
		this.trxDueDate = trxDueDate;
	}

	public BigDecimal getTrxDueAmount() {
		return trxDueAmount;
	}

	public void setTrxDueAmount(BigDecimal trxDueAmount) {
		this.trxDueAmount = trxDueAmount;
	}

}