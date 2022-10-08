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

@Entity
@Table(name="MA0011")
@NamedQuery(name="MA0011Entity.findAll", query="SELECT m FROM MA0011Entity m")
public class MA0011Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name = "TC_CODE")
	private String tcCode;
	
	@Column(name = "TC_OJK_GROUP")
	private String tcOjkGroup;
	
	@Column(name = "TC_CLASS")
	private Integer tcClass;
	
	@Column(name = "TC_FULLDESC")
	private String tcFullDesc;
	
	@Column(name = "TC_DESC")
	private String tcDesc;
	
	@Column(name = "TC_PPW")
	private Integer tcPpw;
	
	@Column(name = "TC_PAY_MAX")
	private Integer tcPayMax;
	
	@Column(name = "TC_PAY_METHOD")
	private Character tcPayMethod;
	
	@Column(name = "TC_QUO_VALID")
	private Integer tcQuoValid;
	
	@Column(name = "TC_POLICY_INS")
	private Integer tcPolicyIns;
	
	@Column(name = "TC_DATA_STATUS")
	private String tcDataStatus;
	
	@Column(name = "TC_PREMIUM")
	private BigDecimal tcPremium;
	
	@Column(name = "TC_BROFEE_AMT")
	private BigDecimal tcBrofeeAmt;
	
	@Column(name = "TC_BROFEE_PCT")
	private BigDecimal tcBrofeePct;
	
	@Column(name = "TC_RENEWABLE")
	private Character tcRenewable;
	
	@Column(name = "TC_CHANNEL")
	private Character tcChannel;
	
	@Column(name = "TC_SALES_TAX")
	private Character tcSalesTax;
	
	@Column(name = "TC_EXT_MAX")
	private Integer tcExtMax;
	
	@Column(name = "TC_PATH_FILE")
	private String tcPathFile;
	
	@Column(name = "TC_FILE_NAME")
	private String tcFileName;
	
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
	private String tcDataStatusStr;
	
	

	public MA0011Entity(Long idKey, String tcCode, String tcOjkGroup, String tcDesc) {
		super();
		this.idKey = idKey;
		this.tcCode = tcCode;
		this.tcOjkGroup = tcOjkGroup;
		this.tcDesc = tcDesc;
	}

	public MA0011Entity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getTcCode() {
		return tcCode;
	}

	public void setTcCode(String tcCode) {
		this.tcCode = tcCode;
	}

	
	public String getTcFullDesc() {
		return tcFullDesc;
	}

	public void setTcFullDesc(String tcFullDesc) {
		this.tcFullDesc = tcFullDesc;
	}


	public String getTcDesc() {
		return tcDesc;
	}

	public void setTcDesc(String tcDesc) {
		this.tcDesc = tcDesc;
	}

	public Integer getTcPpw() {
		return tcPpw;
	}

	public void setTcPpw(Integer tcPpw) {
		this.tcPpw = tcPpw;
	}

	public Integer getTcPayMax() {
		return tcPayMax;
	}

	public void setTcPayMax(Integer tcPayMax) {
		this.tcPayMax = tcPayMax;
	}

	public Character getTcPayMethod() {
		return tcPayMethod;
	}

	public void setTcPayMethod(Character tcPayMethod) {
		this.tcPayMethod = tcPayMethod;
	}

	public Integer getTcQuoValid() {
		return tcQuoValid;
	}

	public void setTcQuoValid(Integer tcQuoValid) {
		this.tcQuoValid = tcQuoValid;
	}

	public Integer getTcPolicyIns() {
		return tcPolicyIns;
	}

	public void setTcPolicyIns(Integer tcPolicyIns) {
		this.tcPolicyIns = tcPolicyIns;
	}

	public String getTcDataStatus() {
		return tcDataStatus;
	}

	public void setTcDataStatus(String tcDataStatus) {
		this.tcDataStatus = tcDataStatus;
	}

	public BigDecimal getTcPremium() {
		return tcPremium;
	}

	public void setTcPremium(BigDecimal tcPremium) {
		this.tcPremium = tcPremium;
	}

	public BigDecimal getTcBrofeeAmt() {
		return tcBrofeeAmt;
	}

	public void setTcBrofeeAmt(BigDecimal tcBrofeeAmt) {
		this.tcBrofeeAmt = tcBrofeeAmt;
	}

	public BigDecimal getTcBrofeePct() {
		return tcBrofeePct;
	}

	public void setTcBrofeePct(BigDecimal tcBrofeePct) {
		this.tcBrofeePct = tcBrofeePct;
	}

	public Character getTcRenewable() {
		return tcRenewable;
	}

	public void setTcRenewable(Character tcRenewable) {
		this.tcRenewable = tcRenewable;
	}

	public Character getTcChannel() {
		return tcChannel;
	}

	public void setTcChannel(Character tcChannel) {
		this.tcChannel = tcChannel;
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

	public String getTcOjkGroup() {
		return tcOjkGroup;
	}

	public void setTcOjkGroup(String tcOjkGroup) {
		this.tcOjkGroup = tcOjkGroup;
	}

	public Integer getTcClass() {
		return tcClass;
	}

	public void setTcClass(Integer tcClass) {
		this.tcClass = tcClass;
	}

	public Integer getTcExtMax() {
		return tcExtMax;
	}

	public void setTcExtMax(Integer tcExtMax) {
		this.tcExtMax = tcExtMax;
	}
	
	public Character getTcSalesTax() {
		return tcSalesTax;
	}

	public void setTcSalesTax(Character tcSalesTax) {
		this.tcSalesTax = tcSalesTax;
	}

	public String getTcPathFile() {
		return tcPathFile;
	}

	public void setTcPathFile(String tcPathFile) {
		this.tcPathFile = tcPathFile;
	}

	public String getTcFileName() {
		return tcFileName;
	}

	public void setTcFileName(String tcFileName) {
		this.tcFileName = tcFileName;
	}

	public String getTcDataStatusStr() {
		if(getTcDataStatus().equals("11")) {
			return "Active";
		}else {
			return "Inactive";
		}
	}

	public void setTcDataStatusStr(String tcDataStatusStr) {
		this.tcDataStatusStr = tcDataStatusStr;
	}
	
	
}
