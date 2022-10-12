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
 * The persistent class for the ma0007 database table.
 * 
 */
@Entity
@Table(name="MA0007")
@NamedQuery(name="MA0007Entity.findAll", query="SELECT m FROM MA0007Entity m")
public class MA0007Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;

	@Column(name="DETAIL_STATUS")
	private String detailStatus;

	@Column(name="DETAILS_DESC")
	private String detailsDesc;

	@Column(name="DETAILS_NAME")
	private String detailsName;

	@Column(name="GROUP_NAME")
	private String groupName;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idKey;

	@Column(name="MODIFY_BY")
	private String modifyBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFY_ON")
	private Date modifyOn;
	
	@Transient
	private String detailStatusStr;
	
	@Transient
	private String action;

	public MA0007Entity() {
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getDetailStatus() {
		return this.detailStatus;
	}

	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
	}

	public String getDetailsDesc() {
		return this.detailsDesc;
	}

	public void setDetailsDesc(String detailsDesc) {
		this.detailsDesc = detailsDesc;
	}

	public String getDetailsName() {
		return this.detailsName;
	}

	public void setDetailsName(String detailsName) {
		this.detailsName = detailsName;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyOn() {
		return this.modifyOn;
	}

	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}

	public String getDetailStatusStr() {
		if(getDetailStatus().equals("11")) {
			setDetailStatusStr("Active");
		}else {
			setDetailStatusStr("Inactive");
		}
		return detailStatusStr;
	}

	public void setDetailStatusStr(String detailStatusStr) {
		this.detailStatusStr = detailStatusStr;
	}

	public String getAction() {
		setAction("<button class=\"btn btn-secondary\" onclick=\"edit('"+getIdKey()+"')\">" 
					+ "<i class=\"fa fa-edit\"></i>" 
					+ "	</button> &nbsp;"
					+ "<button class=\"btn btn-danger\" onclick=\"remove('"+getIdKey()+"')\">" 
					+ "<i class=\"fa fa-trash\"></i>" 
					+ "</button>");
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	
}