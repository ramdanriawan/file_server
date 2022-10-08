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
import javax.persistence.Transient;

@Entity
@Table(name="TR0015B")
@NamedQuery(name="TR0015BEntity.findAll", query="SELECT t FROM TR0015BEntity t")
public class TR0015BEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private Long idKey;
	
	@Column(name="TX_TYPE")
	private String trxTrxId;
	
	@Column(name="TX_VOUCHER_ID")
	private String txVoucherId;
	
	@Column(name="TX_DATE_ACT")
	private Date txDateAct;
	
	@Column(name="TX_INS_MEMO")
	private String txInsMemo;
	
	@Column(name="TX_ACT_TAKE")
	private String txActTake;
	
	@Transient
	private String txDateActFmts;
	
	@Transient
	private SimpleDateFormat sdfUI = new SimpleDateFormat("dd/MM/yyyy");
	
	public TR0015BEntity(String txActTake, Date txDateAct, String txInsMemo, String txVoucherId, String trxTrxId) {
		super();
		this.txActTake = txActTake;
		this.txDateAct = txDateAct;
		this.txInsMemo = txInsMemo;
		this.txVoucherId = txVoucherId;
		this.trxTrxId = trxTrxId;
	}

	public TR0015BEntity() {
		super();
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getTrxTrxId() {
		return trxTrxId;
	}

	public void setTrxTrxId(String trxTrxId) {
		this.trxTrxId = trxTrxId;
	}

	public String getTxVoucherId() {
		return txVoucherId;
	}

	public void setTxVoucherId(String txVoucherId) {
		this.txVoucherId = txVoucherId;
	}

	public Date getTxDateAct() {
		return txDateAct;
	}

	public void setTxDateAct(Date txDateAct) {
		this.txDateAct = txDateAct;
	}

	public String getTxInsMemo() {
		return txInsMemo;
	}

	public void setTxInsMemo(String txInsMemo) {
		this.txInsMemo = txInsMemo;
	}

	public String getTxActTake() {
		return txActTake;
	}

	public void setTxActTake(String txActTake) {
		this.txActTake = txActTake;
	}

	public String getTxDateActFmts() {
		if(txDateAct != null)
			return sdfUI.format(txDateAct);
		
		return txDateActFmts;
	}

	public void setTxDateActFmts(String txDateActFmts) {
		this.txDateActFmts = txDateActFmts;
	}
	
}
