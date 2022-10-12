package com.biru.entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the us0001 database table.
 * 
 */
@Entity
@Table(name = "US0001")
@NamedQuery(name = "US0001Entity.findAll", query = "SELECT u FROM US0001Entity u")
public class US0001Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_ON")
	private Date createOn;

	@Column(name = "GROUP_ID")
	private String groupId;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEY")
	private Long idKey;

	@Column(name = "MODIFY_BY")
	private String modifyBy;

	@Temporal(TemporalType.DATE)
	@Column(name = "MODIFY_ON")
	private Date modifyOn;

	@Column(name = "US_BRANCH")
	private String usBranch;

	@Column(name = "US_CODE")
	private String usCode;

	@Column(name = "US_EMAIL")
	private String usEmail;

	@Column(name = "US_ERR_COUNT")
	private Integer usErrCount;

	@Temporal(TemporalType.DATE)
	@Column(name = "US_EXPIRED_DATE")
	private Date usExpiredDate;

	@Column(name = "US_IS_ONLINE")
	private Integer usIsOnline;

	@Lob
	@Column(name = "US_KEY_FILE")
	private String usKeyFile;

	@Column(name = "US_LEVEL")
	private String usLevel;

	@Column(name = "US_NAME")
	private String usName;

	@Column(name = "US_PASS")
	private String usPass;

	@Column(name = "US_STATUS")
	private String usStatus;

	@Column(name = "US_UNIT")
	private String usUnit;

	@Transient
	private String action;

	@Transient
	private String status;

	@Transient
	private String unitStr;
	
	@Transient
	private String parseDateCreate;
	
	@Transient
	private String parseDateExpired;
	
	public String getParseDateExpired() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateStr;
		dateStr = sdf.format(this.usExpiredDate);
		this.parseDateExpired = dateStr;
		return this.parseDateExpired;
	}
	
	public String getParseDateCreate() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String dateStr;
		dateStr = sdf.format(this.createOn);
		this.parseDateCreate = dateStr;
		return this.parseDateCreate;
	}

	public String getUnitStr() {
		if (this.usUnit.equals("0")) {
			this.unitStr = "Marketing";
		} else if (this.usUnit.equals("1")) {
			this.unitStr = "Finance";
		} else if (this.usUnit.equals("2")) {
			this.unitStr = "Accounting";
		} else if (this.usUnit.equals("3")) {
			this.unitStr = "Claims";
		} else if (this.usUnit.equals("4")) {
			this.unitStr = "Direksi";
		} else {
			this.unitStr = "Unit";
		}
		return this.unitStr;
	}

	public void setUnitStr(String unitStr) {
		if (this.usUnit.equals("0")) {
			this.unitStr = "Marketing";
		} else if (this.usUnit.equals("1")) {
			this.unitStr = "Finance";
		} else if (this.usUnit.equals("2")) {
			this.unitStr = "Accounting";
		} else if (this.usUnit.equals("3")) {
			this.unitStr = "Claims";
		} else if (this.usUnit.equals("4")) {
			this.unitStr = "Direksi";
		} else {
			this.unitStr = "Unit";
		}
	}

	public String getStatus() {
		if (this.usStatus.equals("2")) {
			this.status = "Waiting for Approval (Quote)";
		} else if (this.usStatus.equals("11")) {
			this.status = "Active";
		} else {
			this.status = "Inactive";
		}
		return this.status;
	}

	public void setStatus(String status) {
		if (this.usStatus.equals("2")) {
			this.status = "Waiting for Approval (Quote)";
		} else if (this.usStatus.equals("11")) {
			this.status = "Active";
		} else {
			this.status = "Inactive";
		}
	}

	public US0001Entity() {
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getAction() {
		return "<button class=\"btn btn-secondary\" onclick=\"edit('" + getIdKey() + "')\">"
				+ "<i class=\"fa fa-edit\"></i>" + "</button>";
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

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public Long getIdKey() {
		return this.idKey;
	}

	public void setIdKey(Long idKey) {
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

	public String getUsBranch() {
		return this.usBranch;
	}

	public void setUsBranch(String usBranch) {
		this.usBranch = usBranch;
	}

	public String getUsCode() {
		return this.usCode;
	}

	public void setUsCode(String usCode) {
		this.usCode = usCode;
	}

	public String getUsEmail() {
		return this.usEmail;
	}

	public void setUsEmail(String usEmail) {
		this.usEmail = usEmail;
	}

	public Integer getUsErrCount() {
		return this.usErrCount;
	}

	public void setUsErrCount(Integer usErrCount) {
		this.usErrCount = usErrCount;
	}

	public Date getUsExpiredDate() {
		return this.usExpiredDate;
	}

	public void setUsExpiredDate(Date usExpiredDate) {
		this.usExpiredDate = usExpiredDate;
	}

	public Integer getUsIsOnline() {
		return this.usIsOnline;
	}

	public void setUsIsOnline(Integer usIsOnline) {
		this.usIsOnline = usIsOnline;
	}

	public String getUsKeyFile() {
		return this.usKeyFile;
	}

	public void setUsKeyFile(String usKeyFile) {
		this.usKeyFile = usKeyFile;
	}

	public String getUsLevel() {
		return this.usLevel;
	}

	public void setUsLevel(String usLevel) {
		this.usLevel = usLevel;
	}

	public String getUsName() {
		return this.usName;
	}

	public void setUsName(String usName) {
		this.usName = usName;
	}

	public String getUsPass() {
		return this.usPass;
	}

	public void setUsPass(String usPass) {
		this.usPass = usPass;
	}

	public String getUsStatus() {
		return this.usStatus;
	}

	public void setUsStatus(String usStatus) {
		this.usStatus = usStatus;
	}

	public String getUsUnit() {
		return this.usUnit;
	}

	public void setUsUnit(String usUnit) {
		this.usUnit = usUnit;
	}

}