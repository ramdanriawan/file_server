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


/**
 * The persistent class for the us0003 database table.
 * 
 */
@Entity
@Table(name="US0003")
@NamedQuery(name="US0003Entity.findAll", query="SELECT u FROM US0003Entity u")
public class US0003Entity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private Long idKey;
	
	@Column(name="GROUP_ID")
	private String groupId;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Column(name="UI_ID")
	private String uiId;
	
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
	
	@Transient 
	private String action;

	public US0003Entity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public US0003Entity(String groupId, String groupName, String uiId, String createBy, Date createOn, String modifyBy,
			Date modifyOn) {
		super();
		this.groupId = groupId;
		this.groupName = groupName;
		this.uiId = uiId;
		this.createBy = createBy;
		this.createOn = createOn;
		this.modifyBy = modifyBy;
		this.modifyOn = modifyOn;
	}
	
	public String getAction() {
		return "<button class=\"btn btn-secondary\" onclick=\"edit('"+getIdKey()+"')\">" 
				+ "<i class=\"fa fa-edit\"></i>" 
				+ "</button>";
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