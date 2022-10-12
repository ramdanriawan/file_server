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
@Table(name="MA0013")
@NamedQuery(name="MA0013Entity.findAll", query="SELECT m FROM MA0013Entity m")
public class MA0013Entity implements Serializable{

	

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name = "PA_PARENT_CODE")
	private String paParentCode;
	
	@Column(name = "PA_PARENT_DESC")
	private String paParentDesc;
	
	@Column(name = "PA_PARENT_MAX")
	private int paParentMax;
	
	@Column(name = "PA_PARENT_STATUS")
	private String paParentStatus;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_ON")
	private Date createOn;
	
	@Column(name = "CREATE_BY")
	private String createBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_ON")
	private Date modifyOn;
	
	@Column(name = "MODIFY_BY")
	private String modifyBy;
	
	@Transient 
	private String action;

	public MA0013Entity() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return "<button class=\"btn btn-secondary\" onclick=\"editPa('"+getIdKey()+"')\">" 
				+ "<i class=\"fa fa-edit\"></i>" 
				+ "</button>";
	}
	

	public MA0013Entity(Long idKey, String paParentCode, String paParentDesc, int paParentMax, String paParentStatus,
			Date createOn, String createBy, Date modifyOn, String modifyBy) {
		super();
		this.idKey = idKey;
		this.paParentCode = paParentCode;
		this.paParentDesc = paParentDesc;
		this.paParentMax = paParentMax;
		this.paParentStatus = paParentStatus;
		this.createOn = createOn;
		this.createBy = createBy;
		this.modifyOn = modifyOn;
		this.modifyBy = modifyBy;
	}
	
	


	public MA0013Entity(Long idKey, String paParentCode, String paParentDesc, String paParentStatus) {
		super();
		this.idKey = idKey;
		this.paParentCode = paParentCode;
		this.paParentDesc = paParentDesc;
		this.paParentStatus = paParentStatus;
	}


	public Long getIdKey() {
		return idKey;
	}


	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}


	public String getPaParentCode() {
		return paParentCode;
	}


	public void setPaParentCode(String paParentCode) {
		this.paParentCode = paParentCode;
	}


	public String getPaParentDesc() {
		return paParentDesc;
	}


	public void setPaParentDesc(String paParentDesc) {
		this.paParentDesc = paParentDesc;
	}


	public int getPaParentMax() {
		return paParentMax;
	}


	public void setPaParentMax(int paParentMax) {
		this.paParentMax = paParentMax;
	}


	public String getPaParentStatus() {
		if (this.paParentStatus.equals("11")){
			return "Active";
		}else {
			return "Inactive";
		}
	}


	public void setPaParentStatus(String paParentStatus) {
		if ( paParentStatus.equals("Active")) {
			this.paParentStatus="11";
		}else {
			this.paParentStatus="12";
		}
	}


	public Date getCreateOn() {
		return createOn;
	}


	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}


	public String getCreateBy() {
		return createBy;
	}


	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}


	public Date getModifyOn() {
		return modifyOn;
	}


	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}


	public String getModifyBy() {
		return modifyBy;
	}


	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
