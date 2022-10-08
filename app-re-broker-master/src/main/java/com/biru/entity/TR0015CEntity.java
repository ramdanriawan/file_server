package com.biru.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="TR0015C")
@NamedQuery(name="TR0015CEntity.findAll", query="SELECT t FROM TR0015CEntity t")
public class TR0015CEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private Long idKey;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;
	
	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_DOC_ID")
	private String trxDocId;
	
	@Column(name="TRX_DOC_NAME")
	private String trxDocName;
	
	@Column(name="TRX_DOC_FOLDER")
	private String trxDocFolder;
	
	@Transient
	private String trxDocIdDesc;
	
	@Transient
	private String trxDocNameLink;
	
	public TR0015CEntity(String trxDocName, String trxTrxId, String trxDocId, String trxVoucherId, 
			String trxDocFolder, String trxDocIdDesc) {
		super();
		this.trxDocName = trxDocName;
		this.trxTrxId = trxTrxId;
		this.trxDocId = trxDocId;
		this.trxVoucherId = trxVoucherId;
		this.trxDocFolder = trxDocFolder;
		this.trxDocIdDesc = trxDocIdDesc;
	}

	public TR0015CEntity() {
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

	public String getTrxVoucherId() {
		return trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public String getTrxDocId() {
		return trxDocId;
	}

	public void setTrxDocId(String trxDocId) {
		this.trxDocId = trxDocId;
	}

	public String getTrxDocName() {
		return trxDocName;
	}

	public void setTrxDocName(String trxDocName) {
		this.trxDocName = trxDocName;
	}

	public String getTrxDocFolder() {
		return trxDocFolder;
	}

	public void setTrxDocFolder(String trxDocFolder) {
		this.trxDocFolder = trxDocFolder;
	}

	public String getTrxDocIdDesc() {
		return trxDocIdDesc;
	}

	public void setTrxDocIdDesc(String trxDocIdDesc) {
		this.trxDocIdDesc = trxDocIdDesc;
	}

	public String getTrxDocNameLink() {
		return trxDocNameLink;
	}

	public void setTrxDocNameLink(String trxDocNameLink) {
		this.trxDocNameLink = trxDocNameLink;
	}
		
}
