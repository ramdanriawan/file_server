package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the TR0006F database table.
 * 
 */
@Entity
@Table(name = "TR0006F")
@NamedQuery(name="TR0006FEntity.findAll", query="SELECT t FROM TR0006FEntity t")
public class TR0006FEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private String idKey;

	@Column(name="TRX_DOC_FOLDER")
	private String trxDocFolder;

	@Column(name="TRX_DOC_ID")
	private String trxDocId;

	@Column(name="TRX_DOC_NAME")
	private String trxDocName;

	@Column(name="TRX_PR_CLIENT")
	private String trxPrClient;
	
	@Column(name="TRX_CLASS")
	private String trxClass;

	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;

	@Transient
	private String docDesc;
	
	@Transient
	private String docStatus;
	
	public TR0006FEntity() {
	}
	
	public TR0006FEntity(String trxDocName, String trxDocFolder, String trxPrClient,
			String trxDocId, String docDesc, String docStatus) {
		super();
		this.trxDocName = trxDocName;
		this.trxDocFolder = trxDocFolder;
		this.trxPrClient = trxPrClient;
		this.trxDocId = trxDocId;
		this.docDesc = docDesc;
		this.docStatus = docStatus;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getTrxDocFolder() {
		return this.trxDocFolder;
	}

	public void setTrxDocFolder(String trxDocFolder) {
		this.trxDocFolder = trxDocFolder;
	}

	public String getTrxDocId() {
		return this.trxDocId;
	}

	public void setTrxDocId(String trxDocId) {
		this.trxDocId = trxDocId;
	}

	public String getTrxDocName() {
		return this.trxDocName;
	}

	public void setTrxDocName(String trxDocName) {
		this.trxDocName = trxDocName;
	}

	public String getTrxPrClient() {
		return this.trxPrClient;
	}

	public void setTrxPrClient(String trxPrClient) {
		this.trxPrClient = trxPrClient;
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

	public String getDocDesc() {
		return docDesc;
	}

	public void setDocDesc(String docDesc) {
		this.docDesc = docDesc;
	}

	public String getDocStatus() {
		if("0".equals(docStatus))
			return "Option";
		if("1".equals(docStatus))
			return "Mandatory";
		
		return docStatus;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

}