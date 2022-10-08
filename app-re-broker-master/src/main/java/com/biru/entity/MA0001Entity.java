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
@Table(name="MA0001")
@NamedQuery(name="MA0001Entity.findAll", query="SELECT m FROM MA0001Entity m")
public class MA0001Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEY")
	private Long idKey;

	@Temporal(TemporalType.DATE)
	@Column(name = "HOLI_DATE")
	private Date holiDate;
	
	@Column(name = "HOLI_DESC")
	private String holiDesc;
	
	@Column(name = "HOLI_DATA_STATUS")
	private String holiDataStatus;
	
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
	private String holiDataStatusDesc;
	
	@Transient
	private String holiDateFormat;
	
	public MA0001Entity() {
		super();
	}

	public MA0001Entity(Long idKey, String holiDateFormat, String holiDesc, 
			String holiDataStatus, String holiDataStatusDesc) {
		super();
		this.idKey = idKey;
		this.holiDateFormat = holiDateFormat;
		this.holiDesc = holiDesc;
		this.holiDataStatus = holiDataStatus;
		this.holiDataStatusDesc = holiDataStatusDesc;
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public Date getHoliDate() {
		return holiDate;
	}

	public void setHoliDate(Date holiDate) {
		this.holiDate = holiDate;
	}

	public String getHoliDesc() {
		return holiDesc;
	}

	public void setHoliDesc(String holiDesc) {
		this.holiDesc = holiDesc;
	}

	public String getHoliDataStatus() {
		return holiDataStatus;
	}

	public void setHoliDataStatus(String holiDataStatus) {
		this.holiDataStatus = holiDataStatus;
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

	public String getHoliDataStatusDesc() {
		return holiDataStatusDesc;
	}

	public void setHoliDataStatusDesc(String holiDataStatusDesc) {
		this.holiDataStatusDesc = holiDataStatusDesc;
	}

	public String getHoliDateFormat() {
		return holiDateFormat;
	}

	public void setHoliDateFormat(String holiDateFormat) {
		this.holiDateFormat = holiDateFormat;
	}
	
}
