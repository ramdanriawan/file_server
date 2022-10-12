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

import org.springframework.beans.factory.annotation.Autowired;

import com.biru.repository.MA0005Repo;

@Entity
@Table(name="MA0021")
@NamedQuery(name="MA0021Entity.findAll", query="SELECT m FROM MA0021Entity m")
public class MA0021Entity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name = "INS_ID")
	private String insId;
	
	@Column(name = "TC_CODE")
	private String tcCode;
	
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
	private String action;
	
	public MA0021Entity(Long idKey, String insId, String tcCode, String createBy, Date createOn, String modifyBy,
			Date modifyOn) {
		super();
		this.idKey = idKey;
		this.insId = insId;
		this.tcCode = tcCode;
		this.createBy = createBy;
		this.createOn = createOn;
		this.modifyBy = modifyBy;
		this.modifyOn = modifyOn;
	}

	public MA0021Entity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public String getAction() {
		return "<button id =\"btnDeleteKmk\" class=\"btn btn-secondary\" onclick=\"deleteIns('"+getIdKey()+"')\">" 
				+ "<i class=\"fa fa-trash\"></i>" 
				+ "</button>";
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getTcCode() {
		return tcCode;
	}

	public void setTcCode(String tcCode) {
		this.tcCode = tcCode;
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
