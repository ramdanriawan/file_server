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


/**
 * The persistent class for the TR0005 database table.
 * 
 */
@Entity
@Table(name="TR0005")
@NamedQuery(name="TR0005Entity.findAll", query="SELECT t FROM TR0005Entity t")
public class TR0005Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idKey;

	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;

	@Column(name="MODIFY_BY")
	private String modifyBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFY_ON")
	private Date modifyOn; 

	@Column(name="PRO_ID")
	private String proId;

	@Column(name="PRO_MONTH")
	private short proMonth;

	@Column(name="PRO_STATUS")
	private String proStatus;

	@Column(name="PRO_YEAR")
	private int proYear;

	public TR0005Entity() {
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
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

	public String getProId() {
		return this.proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public short getProMonth() {
		return this.proMonth;
	}

	public void setProMonth(short proMonth) {
		this.proMonth = proMonth;
	}

	public String getProStatus() {
		return this.proStatus;
	}

	public void setProStatus(String proStatus) {
		this.proStatus = proStatus;
	}

	public int getProYear() {
		return this.proYear;
	}

	public void setProYear(int proYear) {
		this.proYear = proYear;
	}

}