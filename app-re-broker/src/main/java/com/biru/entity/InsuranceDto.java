package com.biru.entity;

import java.util.Date;

public class InsuranceDto{
	
	private Long idKey;
	
	private String insId;
	
	private String tcCode;
	
	private String createBy;

	private Date createOn;
	
	private String modifyBy;
	
	private Date modifyOn;
	
	private String action;
	
	private String cliName;
	
	public InsuranceDto(Long idKey, String insId, String tcCode, String createBy, Date createOn, String modifyBy,
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

	public InsuranceDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setCliName(String cliName) {
		this.cliName=cliName;
	}
	
	public String getCliName() {
		return cliName;
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
