package com.biru.entity;

import java.io.Serializable;
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

@Entity
@Table(name="MA0017")
@NamedQuery(name="MA0017Entity.findAll", query="SELECT m FROM MA0017Entity m")
public class MA0017Entity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEY")
	private Long idKey;
	
	@Column(name = "BR_CODE")
	private String brCode;
	
	@Column(name = "BR_PARENT_DESC")
	private String brParentDesc;
	
	@Column(name = "BR_CURR_ID")
	private String brCurrId;
	
	@Column(name = "BR_DATA_STATUS")
	private String brDataStatus;
	
	@Column(name = "CREATE_BY")
	private String createBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_ON")
	private Date createOn;
	
	@Column(name = "MODIFY_BY")
	private String modifyBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_ON")
	private Date modifyOn;
	
	@Transient
	private String brDataStatusDesc;
	
	public MA0017Entity(){
		super();
	}
	
	public MA0017Entity(Long idKey, String brCode, String brParentDesc, String brParentGroup, 
			String brDataStatus, String brDataStatusDesc) {
		super();
		this.idKey = idKey;
		this.brCode = brCode;
		this.brParentDesc = brParentDesc;
		this.brCurrId = brParentGroup;
		this.brDataStatus = brDataStatus;
		this.brDataStatusDesc = brDataStatusDesc;
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getBrCode() {
		return brCode;
	}

	public void setBrCode(String brCode) {
		this.brCode = brCode;
	}

	public String getBrParentDesc() {
		return brParentDesc;
	}

	public void setBrParentDesc(String brParentDesc) {
		this.brParentDesc = brParentDesc;
	}

	public String getBrCurrId() {
		return brCurrId;
	}

	public void setBrCurrId(String brCurrId) {
		this.brCurrId = brCurrId;
	}

	public String getBrDataStatus() {
		return brDataStatus;
	}

	public void setBrDataStatus(String brDataStatus) {
		this.brDataStatus = brDataStatus;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyOn() {
		return modifyOn;
	}

	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getBrDataStatusDesc() {
		return brDataStatusDesc;
	}

	public void setBrDataStatusDesc(String brDataStatusDesc) {
		this.brDataStatusDesc = brDataStatusDesc;
	}
		
}
