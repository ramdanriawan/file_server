package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
 * The persistent class for the TR0001 database table.
 * 
 */
/**
 * @author LH38628X
 *
 */
@Entity
@Table(name="TR0001")
@NamedQuery(name="TR0001Entity.findAll", query="SELECT t FROM TR0001Entity t")
public class TR0001Entity implements Serializable {
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

	@Column(name="GL_DATA_STATUS")
	private String glDataStatus;

	@Column(name="GL_TRX_CLASS")
	private String glTrxClass;

	@Column(name="GL_TRX_CLIENT")
	private String glTrxClient;

	@Temporal(TemporalType.DATE)
	@Column(name="GL_TRX_DATE")
	private Date glTrxDate;

	@Column(name="GL_TRX_DESC")
	private String glTrxDesc;

	@Temporal(TemporalType.DATE)
	@Column(name="GL_TRX_DUE")
	private Date glTrxDue;

	@Column(name="GL_TRX_INVOICE")
	private String glTrxInvoice;
	
	@Column(name="GL_REFF_ID")
	private String glReffId;

	@Column(name="GL_TRX_MONTH")
	private byte glTrxMonth;

	@Column(name="GL_TRX_OFFICE_ID")
	private String glTrxOfficeId;

	@Column(name="GL_TRX_PROJECT")
	private String glTrxProject;

	@Column(name="GL_TRX_STATUS")
	private String glTrxStatus;

	@Column(name="GL_TRX_VALUE_IDR")
	private BigDecimal glTrxValueIdr;

	@Column(name="GL_TRX_VALUE_ORG")
	private BigDecimal glTrxValueOrg;

	@Column(name="GL_TRX_YEAR")
	private short glTrxYear;

	@Column(name="GL_TYPE")
	private String glType;

	@Column(name="GL_VOUCHER_ID")
	private String glVoucherId;

	@Column(name="MODIFY_BY")
	private String modifyBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFY_ON")
	private Date modifyOn;
	
	@Transient
	private String glTrxDateStr;
	
	@Transient
	private String glTrxDueStr;
	
	@Transient
	private String glTrxValueIdrStr;
	
	@Transient
	private String glTrxClientDesc;
	
	@Transient
	private String glTrxStatusDesc;
	
	@Transient
	private BigDecimal prRate;
	
	@Transient
	private String action;

	public TR0001Entity() {
	}
	
	public TR0001Entity(String idKey, Date glTrxDate, String glVoucherId, String glReffId, String glTrxDesc, String glTrxStatus) {
		this.idKey = idKey;
		this.glTrxDate = glTrxDate;
		this.glVoucherId = glVoucherId;
		this.glReffId = glReffId;
		this.glTrxDesc = glTrxDesc;
		this.glTrxStatus = glTrxStatus;
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

	public String getGlDataStatus() {
		return this.glDataStatus;
	}

	public void setGlDataStatus(String glDataStatus) {
		this.glDataStatus = glDataStatus;
	}

	public String getGlTrxClass() {
		return this.glTrxClass;
	}

	public void setGlTrxClass(String glTrxClass) {
		this.glTrxClass = glTrxClass;
	}

	public String getGlTrxClient() {
		return this.glTrxClient;
	}

	public void setGlTrxClient(String glTrxClient) {
		this.glTrxClient = glTrxClient;
	}

	public Date getGlTrxDate() {
		return this.glTrxDate;
	}

	public void setGlTrxDate(Date glTrxDate) {
		this.glTrxDate = glTrxDate;
	}

	public String getGlTrxDesc() {
		return this.glTrxDesc;
	}

	public void setGlTrxDesc(String glTrxDesc) {
		this.glTrxDesc = glTrxDesc;
	}

	public Date getGlTrxDue() {
		return this.glTrxDue;
	}

	public void setGlTrxDue(Date glTrxDue) {
		this.glTrxDue = glTrxDue;
	}

	public String getGlTrxInvoice() {
		return this.glTrxInvoice;
	}

	public void setGlTrxInvoice(String glTrxInvoice) {
		this.glTrxInvoice = glTrxInvoice;
	}

	public String getGlReffId() {
		return glReffId;
	}


	public void setGlReffId(String glReffId) {
		this.glReffId = glReffId;
	}


	public byte getGlTrxMonth() {
		return this.glTrxMonth;
	}

	public void setGlTrxMonth(byte glTrxMonth) {
		this.glTrxMonth = glTrxMonth;
	}

	public String getGlTrxOfficeId() {
		return this.glTrxOfficeId;
	}

	public void setGlTrxOfficeId(String glTrxOfficeId) {
		this.glTrxOfficeId = glTrxOfficeId;
	}

	public String getGlTrxProject() {
		return this.glTrxProject;
	}

	public void setGlTrxProject(String glTrxProject) {
		this.glTrxProject = glTrxProject;
	}

	public String getGlTrxStatus() {
		return this.glTrxStatus;
	}

	public void setGlTrxStatus(String glTrxStatus) {
		this.glTrxStatus = glTrxStatus;
	}

	public BigDecimal getGlTrxValueIdr() {
		return this.glTrxValueIdr;
	}

	public void setGlTrxValueIdr(BigDecimal glTrxValueIdr) {
		this.glTrxValueIdr = glTrxValueIdr;
	}

	public BigDecimal getGlTrxValueOrg() {
		return this.glTrxValueOrg;
	}

	public void setGlTrxValueOrg(BigDecimal glTrxValueOrg) {
		this.glTrxValueOrg = glTrxValueOrg;
	}

	public short getGlTrxYear() {
		return this.glTrxYear;
	}

	public void setGlTrxYear(short glTrxYear) {
		this.glTrxYear = glTrxYear;
	}

	public String getGlType() {
		return this.glType;
	}

	public void setGlType(String glType) {
		this.glType = glType;
	}

	public String getGlVoucherId() {
		return this.glVoucherId;
	}

	public void setGlVoucherId(String glVoucherId) {
		this.glVoucherId = glVoucherId;
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
	
	public BigDecimal getPrRate() {
		return prRate;
	}

	public void setPrRate(BigDecimal prRate) {
		this.prRate = prRate;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public String getGlTrxValueIdrStr() {
		if(glTrxValueIdr != null)
			return NumberFormat.getCurrencyInstance().format(glTrxValueIdr).replace("$", "");
		else 
			return glTrxValueIdrStr;
	}

	public void setGlTrxValueIdrStr(String glTrxValueIdrStr) {
		this.glTrxValueIdrStr = glTrxValueIdrStr;
	}

	public String getGlTrxDateStr() {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
		return formatDate.format(glTrxDate);
	}

	public void setGlTrxDateStr(String glTrxDateStr) {
		this.glTrxDateStr = glTrxDateStr;
	}

	public String getGlTrxDueStr() {
		SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
		return formatDate.format(glTrxDue == null ? glTrxDate: glTrxDue);
	}

	public void setGlTrxDueStr(String glTrxDueStr) {
		this.glTrxDueStr = glTrxDueStr;
	}

	public String getGlTrxClientDesc() {
		return glTrxClientDesc;
	}

	public void setGlTrxClientDesc(String glTrxClientDesc) {
		this.glTrxClientDesc = glTrxClientDesc;
	}

	public String getGlTrxStatusDesc() {
		if(glTrxStatus!=null)
			return glTrxStatus.equals("0") ? "Unposted" : "Posted";
		else
			return glTrxStatusDesc;
	}

	public void setGlTrxStatusDesc(String glTrxStatusDesc) {
		this.glTrxStatusDesc = glTrxStatusDesc;
	}

	@Override
	public String toString() {
		return "TR0001Entity [idKey=" + idKey + ", createBy=" + createBy + ", createOn=" + createOn + ", glDataStatus="
				+ glDataStatus + ", glTrxClass=" + glTrxClass + ", glTrxClient=" + glTrxClient + ", glTrxDate="
				+ glTrxDate + ", glTrxDesc=" + glTrxDesc + ", glTrxDue=" + glTrxDue + ", glTrxInvoice=" + glTrxInvoice
				+ ", glReffId=" + glReffId + ", glTrxMonth=" + glTrxMonth + ", glTrxOfficeId=" + glTrxOfficeId
				+ ", glTrxProject=" + glTrxProject + ", glTrxStatus=" + glTrxStatus + ", glTrxValueIdr=" + glTrxValueIdr
				+ ", glTrxValueOrg=" + glTrxValueOrg + ", glTrxYear=" + glTrxYear + ", glType=" + glType
				+ ", glVoucherId=" + glVoucherId + ", modifyBy=" + modifyBy + ", modifyOn=" + modifyOn
				+ ", glTrxDateStr=" + glTrxDateStr + ", glTrxDueStr=" + glTrxDueStr + ", glTrxValueIdrStr="
				+ glTrxValueIdrStr + ", glTrxClientDesc=" + glTrxClientDesc + ", glTrxStatusDesc=" + glTrxStatusDesc
				+ ", prRate=" + prRate + ", action=" + action + "]";
	}

	
}