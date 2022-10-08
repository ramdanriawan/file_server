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
 * The persistent class for the ma0006 database table.
 * 
 */
@Entity
@Table(name="MA0006")
@NamedQuery(name="MA0006Entity.findAll", query="SELECT m FROM MA0006Entity m")
public class MA0006Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;
	
	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idKey;

	@Column(name="MODIFY_BY")
	private String modifyBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFY_ON")
	private Date modifyOn;

	@Column(name="TC_CODE")
	private String tcCode;

	@Column(name="TC_DETAILS")
	private String tcDetails;

	@Column(name="TC_GROUP")
	private String tcGroup;

	@Column(name="TC_NO")
	private int tcNo;
	
	@Transient
	private String action;

	public MA0006Entity() {
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

	public String getTcCode() {
		return this.tcCode;
	}

	public void setTcCode(String tcCode) {
		this.tcCode = tcCode;
	}

	public String getTcDetails() {
		return this.tcDetails;
	}

	public void setTcDetails(String tcDetails) {
		this.tcDetails = tcDetails;
	}

	public String getTcGroup() {
		return this.tcGroup;
	}

	public void setTcGroup(String tcGroup) {
		this.tcGroup = tcGroup;
	}

	public int getTcNo() {
		return this.tcNo;
	}

	public void setTcNo(int tcNo) {
		this.tcNo = tcNo;
	}

	public String getAction() {
		
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
}