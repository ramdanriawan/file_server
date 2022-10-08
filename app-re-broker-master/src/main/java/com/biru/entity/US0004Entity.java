package com.biru.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the us0003 database table.
 * 
 */
@Entity
@Table(name="US0004")
@NamedQuery(name="US0004Entity.findAll", query="SELECT u FROM US0004Entity u")
public class US0004Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	private Long idKey;
	
	@Column(name="UI_ID")
	private String uiId;
	
	@Column(name="UI_NAME")
	private String uiName;
	
	@Column(name="UI_URL")
	private String uiUrl;
	
	@Column(name="UI_TYPE")
	private String uiType;
	
	@Column(name="UI_PARENT")
	private String uiParent;
	
	@Column(name="UI_ICON")
	private String uiIcon;
	
	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.DATE)
	@Column(name="CREATE_ON")
	private Date createOn;

	@Column(name="MODIFY_BY")
	private String modifyBy;

	@Temporal(TemporalType.DATE)
	@Column(name="MODIFY_ON")
	private Date modifyOn;
	
	

	public US0004Entity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public US0004Entity(String uiId, String uiName, String uiUrl, String uiType, String createBy, Date createOn,
			String modifyBy, Date modifyOn) {
		super();
		this.uiId = uiId;
		this.uiName = uiName;
		this.uiUrl = uiUrl;
		this.uiType = uiType;
		this.createBy = createBy;
		this.createOn = createOn;
		this.modifyBy = modifyBy;
		this.modifyOn = modifyOn;
	}

	public US0004Entity(String uiName, String uiUrl) {
		super();
		this.uiName = uiName;
		this.uiUrl = uiUrl;
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getUiId() {
		return uiId;
	}

	public void setUiId(String uiId) {
		this.uiId = uiId;
	}
	
	

	public String getUiName() {
		return uiName;
	}

	public void setUiName(String uiName) {
		this.uiName = uiName;
	}

	public String getUiUrl() {
		return uiUrl;
	}

	public void setUiUrl(String uiUrl) {
		this.uiUrl = uiUrl;
	}

	public String getUiType() {
		return uiType;
	}

	public void setUiType(String uiType) {
		this.uiType = uiType;
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

	public String getUiParent() {
		return uiParent;
	}

	public void setUiParent(String uiParent) {
		this.uiParent = uiParent;
	}

	public String getUiIcon() {
		return uiIcon;
	}

	public void setUiIcon(String uiIcon) {
		this.uiIcon = uiIcon;
	}	
}