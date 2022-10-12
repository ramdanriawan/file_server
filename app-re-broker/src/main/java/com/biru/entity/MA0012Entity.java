package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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

import org.apache.poi.hpsf.Decimal;

@Entity
@Table(name="MA0012")
@NamedQuery(name="MA0012Entity.findAll", query="SELECT m FROM MA0012Entity m")
public class MA0012Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name = "SA_CODE")
	private String saCode;
	
	@Column(name = "SA_OFFICE_ID")
	private String saOfficeId;
	
	@Column(name = "SA_NAME")
	private String saName;
	
	@Column(name = "SA_EMAIL")
	private String saEmail;
	
	@Column(name = "SA_PHONE")
	private String saPhone;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "SA_JOINT")
	private Date saJoint;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "SA_EXPIRED")
	private Date saExpired;
	
	@Column(name = "SA_LICENSE")
	private String saLicense;
	
	@Column(name = "SA_TAX_ID")
	private String saTaxId;
	
	@Column(name = "SA_TYPE")
	private String saType;
	
	@Column(name = "CLI_CODE")
	private String cliCode;
	
	@Column(name = "SA_DATA_STATUS")
	private String saDataStatus;
	
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
	
	@Column(name= "SA_COMM_PCT")
	private BigDecimal saCommision;
	
	@Transient
	private String action;
	
	@Transient
	private String saDataStatusDesc;
	
	@Transient
	private String saTypeDesc;
	
	@Transient
	private String saOfficeLabel;
	

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getSaCode() {
		return saCode;
	}

	public void setSaCode(String saCode) {
		this.saCode = saCode;
	}

	public String getSaOfficeId() {
		return saOfficeId;
	}

	public void setSaOfficeId(String saOfficeId) {
		this.saOfficeId = saOfficeId;
	}

	public String getSaName() {
		return saName;
	}

	public void setSaName(String saName) {
		this.saName = saName;
	}

	public String getSaEmail() {
		return saEmail;
	}

	public void setSaEmail(String saEmail) {
		this.saEmail = saEmail;
	}

	public String getSaPhone() {
		return saPhone;
	}

	public void setSaPhone(String saPhone) {
		this.saPhone = saPhone;
	}

	public Date getSaJoint() {
		return saJoint;
	}

	public void setSaJoint(Date saJoint) {
		this.saJoint = saJoint;
	}

	public Date getSaExpired() {
		return saExpired;
	}

	public void setSaExpired(Date saExpired) {
		this.saExpired = saExpired;
	}

	public String getSaLicense() {
		return saLicense;
	}

	public void setSaLicense(String saLicense) {
		this.saLicense = saLicense;
	}

	public String getSaTaxId() {
		return saTaxId;
	}

	public void setSaTaxId(String saTaxId) {
		this.saTaxId = saTaxId;
	}

	public String getSaType() {
		return saType;
	}

	public void setSaType(String saType) {
		this.saType = saType;
	}

	public String getCliCode() {
		return cliCode;
	}

	public void setCliCode(String cliCode) {
		this.cliCode = cliCode;
	}

	public String getSaDataStatus() {
		return saDataStatus;
	}

	public void setSaDataStatus(String saDataStatus) {
		this.saDataStatus = saDataStatus;
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
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
		
	public String getSaDataStatusDesc() {
		return saDataStatusDesc;
	}

	public void setSaDataStatusDesc(String saDataStatusDesc) {
		this.saDataStatusDesc = saDataStatusDesc;
	}

	public String getSaTypeDesc() {
		return saTypeDesc;
	}

	public void setSaTypeDesc(String saTypeDesc) {
		this.saTypeDesc = saTypeDesc;
	}

	public String getSaOfficeLabel() {
		return saOfficeLabel;
	}

	public void setSaOfficeLabel(String saOfficeLabel) {
		this.saOfficeLabel = saOfficeLabel;
	}

	public BigDecimal getSaCommision() {
		return saCommision;
	}

	public void setSaCommision(BigDecimal saCommision) {
		this.saCommision = saCommision;
	}

	public MA0012Entity() {
		
	}

	public MA0012Entity(Long idKey, String saCode, String saOfficeId, String saName, String saEmail, String saPhone,
			Date saJoint, Date saExpired, String saLicense, String saTaxId, String saType, String cliCode,
			String saDataStatus, String action) {
		super();
		this.idKey = idKey;
		this.saCode = saCode;
		this.saOfficeId = saOfficeId;
		this.saName = saName;
		this.saEmail = saEmail;
		this.saPhone = saPhone;
		this.saJoint = saJoint;
		this.saExpired = saExpired;
		this.saLicense = saLicense;
		this.saTaxId = saTaxId;
		this.saType = saType;
		this.cliCode = cliCode;
		this.saDataStatus = saDataStatus;
		this.action = action;
	}
	
}
