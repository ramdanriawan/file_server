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
 * The persistent class for the MA0005 database table.
 * 
 */
@Entity
@Table(name="MA0005")
@NamedQuery(name="MA0005Entity.findAll", query="SELECT m FROM MA0005Entity m")
public class MA0005Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idKey;

	@Column(name="CLI_ADDRESS")
	private String cliAddress;

	@Column(name="CLI_CODE")
	private String cliCode;

	@Column(name="CLI_COUNTRY_ID")
	private String cliCountryId;

	@Column(name="CLI_DATA_STATUS")
	private String cliDataStatus;

	@Column(name="CLI_EMAIL")
	private String cliEmail;

	@Column(name="CLI_FAX")
	private String cliFax;

	@Column(name="CLI_INDUSTRY")
	private String cliIndustry;

	@Temporal(TemporalType.DATE)
	@Column(name="CLI_JOINT_DATE")
	private Date cliJointDate;

	@Temporal(TemporalType.DATE)
	@Column(name="CLI_LAST_TRX")
	private Date cliLastTrx;

	@Column(name="CLI_LASTTRX_STS")
	private String cliLasttrxSts;

	@Column(name="CLI_NAME")
	private String cliName;

	@Column(name="CLI_OWN")
	private String cliOwn;

	@Column(name="CLI_PIC1")
	private String cliPic1;

	@Column(name="CLI_PIC1_EMAIL")
	private String cliPic1Email;

	@Column(name="CLI_PIC1_TELP")
	private String cliPic1Telp;

	@Column(name="CLI_PIC2")
	private String cliPic2;

	@Column(name="CLI_PIC2_EMAIL")
	private String cliPic2Email;

	@Column(name="CLI_PIC2_TELP")
	private String cliPic2Telp;

	@Column(name="CLI_POST_CODE")
	private String cliPostCode;

	@Column(name="CLI_REMARKS")
	private String cliRemarks;

	@Column(name="CLI_TAX_ID")
	private String cliTaxId;

	@Column(name="CLI_TAX_PKP")
	private String cliTaxPkp;

	@Column(name="CLI_TELEPON")
	private String cliTelepon;

	@Column(name="CLI_TYPE")
	private String cliType;

	@Column(name="CLI_WEB")
	private String cliWeb;

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
	
	@Transient
	private String cliDataStatusStr;
	
	@Transient
	private String cliTypeStr;
	
	@Transient
	private String cliTypeDesc;
	
	@Transient 
	private String action;
	

	public MA0005Entity() {
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getCliAddress() {
		return this.cliAddress;
	}

	public void setCliAddress(String cliAddress) {
		this.cliAddress = cliAddress;
	}

	public String getCliCode() {
		return this.cliCode;
	}

	public void setCliCode(String cliCode) {
		this.cliCode = cliCode;
	}

	public String getCliCountryId() {
		return this.cliCountryId;
	}

	public void setCliCountryId(String cliCountryId) {
		this.cliCountryId = cliCountryId;
	}

	public String getCliDataStatus() {
		return this.cliDataStatus;
	}

	public void setCliDataStatus(String cliDataStatus) {
		this.cliDataStatus = cliDataStatus;
	}

	public String getCliEmail() {
		return this.cliEmail;
	}

	public void setCliEmail(String cliEmail) {
		this.cliEmail = cliEmail;
	}

	public String getCliFax() {
		return this.cliFax;
	}

	public void setCliFax(String cliFax) {
		this.cliFax = cliFax;
	}

	public String getCliIndustry() {
		return this.cliIndustry;
	}

	public void setCliIndustry(String cliIndustry) {
		this.cliIndustry = cliIndustry;
	}

	public Date getCliJointDate() {
		return this.cliJointDate;
	}

	public void setCliJointDate(Date cliJointDate) {
		this.cliJointDate = cliJointDate;
	}

	public Date getCliLastTrx() {
		return this.cliLastTrx;
	}

	public void setCliLastTrx(Date cliLastTrx) {
		this.cliLastTrx = cliLastTrx;
	}

	public String getCliLasttrxSts() {
		return this.cliLasttrxSts;
	}

	public void setCliLasttrxSts(String cliLasttrxSts) {
		this.cliLasttrxSts = cliLasttrxSts;
	}

	public String getCliName() {
		return this.cliName;
	}

	public void setCliName(String cliName) {
		this.cliName = cliName;
	}

	public String getCliOwn() {
		return this.cliOwn;
	}

	public void setCliOwn(String cliOwn) {
		this.cliOwn = cliOwn;
	}

	public String getCliPic1() {
		return this.cliPic1;
	}

	public void setCliPic1(String cliPic1) {
		this.cliPic1 = cliPic1;
	}

	public String getCliPic1Email() {
		return this.cliPic1Email;
	}

	public void setCliPic1Email(String cliPic1Email) {
		this.cliPic1Email = cliPic1Email;
	}

	public String getCliPic1Telp() {
		return this.cliPic1Telp;
	}

	public void setCliPic1Telp(String cliPic1Telp) {
		this.cliPic1Telp = cliPic1Telp;
	}

	public String getCliPic2() {
		return this.cliPic2;
	}

	public void setCliPic2(String cliPic2) {
		this.cliPic2 = cliPic2;
	}

	public String getCliPic2Email() {
		return this.cliPic2Email;
	}

	public void setCliPic2Email(String cliPic2Email) {
		this.cliPic2Email = cliPic2Email;
	}

	public String getCliPic2Telp() {
		return this.cliPic2Telp;
	}

	public void setCliPic2Telp(String cliPic2Telp) {
		this.cliPic2Telp = cliPic2Telp;
	}

	public String getCliPostCode() {
		return this.cliPostCode;
	}

	public void setCliPostCode(String cliPostCode) {
		this.cliPostCode = cliPostCode;
	}

	public String getCliRemarks() {
		return this.cliRemarks;
	}

	public void setCliRemarks(String cliRemarks) {
		this.cliRemarks = cliRemarks;
	}

	public String getCliTaxId() {
		return this.cliTaxId;
	}

	public void setCliTaxId(String cliTaxId) {
		this.cliTaxId = cliTaxId;
	}

	public String getCliTaxPkp() {
		return this.cliTaxPkp;
	}

	public void setCliTaxPkp(String cliTaxPkp) {
		this.cliTaxPkp = cliTaxPkp;
	}

	public String getCliTelepon() {
		return this.cliTelepon;
	}

	public void setCliTelepon(String cliTelepon) {
		this.cliTelepon = cliTelepon;
	}

	public String getCliType() {
		return this.cliType;
	}

	public void setCliType(String cliType) {
		this.cliType = cliType;
	}

	public String getCliWeb() {
		return this.cliWeb;
	}

	public void setCliWeb(String cliWeb) {
		this.cliWeb = cliWeb;
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

	public String getCliDataStatusStr() {
		return cliDataStatusStr;
	}

	public void setCliDataStatusStr(String cliDataStatusStr) {
		this.cliDataStatusStr = cliDataStatusStr;
	}

	public String getAction() {
		return "<button class=\"btn btn-secondary\" onclick=\"edit('"+getIdKey()+"')\">" 
				+ "<i class=\"fa fa-edit\"></i>" 
				+ "</button>";
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCliTypeStr() {
		String cliTypeStr = null;
		switch (getCliType()) {
		case "0":
			cliTypeStr = "Reinsurance";
		break;
		case "1":
			cliTypeStr = "Life Insurance";
		break;
		case "2":
			cliTypeStr = "General Insurance";
		break;
		case "3":
			cliTypeStr = "Others";
		break;

		default:
			break;
		}

		return cliTypeStr;
	}

	public void setTypeStr(String cliTypeStr) {
		this.cliTypeStr = cliTypeStr;
	}

	public String getCliTypeDesc() {
		return cliTypeDesc;
	}

	public void setCliTypeDesc(String cliTypeDesc) {
		this.cliTypeDesc = cliTypeDesc;
	}
	
}