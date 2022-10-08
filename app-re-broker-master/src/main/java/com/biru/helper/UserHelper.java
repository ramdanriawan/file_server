package com.biru.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

public class UserHelper implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long idKey;
	private String groupId;
	private String groupName;
	private String uiId;
	private String uiName;
	private String createBy;
	private Date createOn;
	private String modifyBy;
	private Date modifyOn;	
	
	public UserHelper(Long idKey, String groupId, String groupName, String uiId, String uiName, String createBy, Date createOn,
			String modifyBy, Date modifyOn) {
		super();
		this.idKey = idKey;
		this.groupId = groupId;
		this.groupName = groupName;
		this.uiId = uiId;
		this.uiName = uiName;
		this.createBy = createBy;
		this.createOn = createOn;
		this.modifyBy = modifyBy;
		this.modifyOn = modifyOn;
	}
	
	public String getAction() {
		return "<button class=\"btn btn-danger\" onclick=\"remove('"+getIdKey()+"')\">" 
				+ "<i class=\"fa fa-trash\"></i>" 
				+ "</button>";
	}
	
	public UserHelper() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getIdKey() {
		return idKey;
	}
	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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

}
