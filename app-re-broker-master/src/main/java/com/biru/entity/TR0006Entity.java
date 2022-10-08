package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
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
 * The persistent class for the tr0006 database table.
 * 
 */
@Entity
@Table(name="TR0006")
@NamedQuery(name="TR006Entity.findAll", query="SELECT t FROM TR0006Entity t")
public class TR0006Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private String idKey;

	@Column(name="MODIFY_BY")
	private String modifyBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFY_ON")
	private Date modifyOn;
	
	@Column(name="TRX_CLASS")
	private String trxClass;

	@Column(name="TRX_CLIENT")
	private String trxClient;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_CLOSING_DATE")
	private Date trxClosingDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_CONFIRM_DATE")
	private Date trxConfirmDate;

	@Column(name="TRX_CONFIRM_ID")
	private String trxConfirmId;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;
	
	@Column(name="TRX_INSURED_NAME")
	private String trxInsuredName;

	@Column(name="TRX_CURR_RATE")
	private BigDecimal trxCurrRate;

	@Column(name="TRX_DATA_STATUS")
	private String trxDataStatus;

	@Column(name="TRX_DIRET_PY")
	private String trxDiretPy;

	@Column(name="TRX_OFFICER")
	private String trxOfficer;

	@Column(name="TRX_PAY_MTHD")
	private String trxPayMthd;

	@Column(name="TRX_PERIOD_VLD")
	private int trxPeriodVld;

	@Column(name="TRX_PPW_CLI")
	private int trxPpwCli;

	@Column(name="TRX_REMARKS")
	private String trxRemarks;

	@Column(name="TRX_SHARE")
	private BigDecimal trxShare;

	@Column(name="TRX_SUM_INSURED")
	private BigDecimal trxSumInsured;

	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_FEE_CLIENT")
	private BigDecimal trxFeeClient;
	
	@Column(name="TRX_TSI_AMOUNT")
	private BigDecimal trxTsiAmount;
	
	@Column(name="TRX_TRE_ADJ")
	private String trxTreAdj;
	
	@Transient
	private String clientDesc;
	
	@Transient
	private Long createOnMillis;
	
	@Transient
	private String trxCoverCode;
	
	@Transient
	private String trxRemarks6a;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date trxInsStart;
	
	@Transient
	@Temporal(TemporalType.DATE)
	private Date trxInsEnd;
	
	@Transient
	private BigDecimal exRateValue;
	
	@Transient
	private String clientDesc2;
	
	@Transient
	private String clientDesc3;
	
	@Transient
	private String trxTrxTrxId6a;
	
	@Transient
	private String trxTsiAmountStr;
	
	@Transient
	private String exRateValueStr;
	
	@Transient
	private String trxDateStr;

	public TR0006Entity() {
	}
	
	public TR0006Entity(String trxClient, String clientDesc) {
		super();
		this.trxClient = trxClient;
		this.clientDesc = clientDesc;
	}

	public TR0006Entity(String createBy, Date createOn, String trxClient, 
			String trxCurrId, String trxInsuredName, BigDecimal trxCurrRate, 
			String trxDiretPy, String trxOfficer, String trxPayMthd, int trxPeriodVld, 
			int trxPpwCli, String trxRemarks, BigDecimal trxShare, BigDecimal trxSumInsured, 
			String trxTrxId, String trxVoucherId, BigDecimal trxFeeClient, 
			BigDecimal trxTsiAmount, String clientDesc, String trxDateStr) {
		super();
		this.createBy = createBy;
		this.createOn = createOn;
		this.trxClient = trxClient;
		this.trxCurrId = trxCurrId;
		this.trxInsuredName = trxInsuredName;
		this.trxCurrRate = trxCurrRate;
		this.trxDiretPy = trxDiretPy;
		this.trxOfficer = trxOfficer;
		this.trxPayMthd = trxPayMthd;
		this.trxPeriodVld = trxPeriodVld;
		this.trxPpwCli = trxPpwCli;
		this.trxRemarks = trxRemarks;
		this.trxShare = trxShare;
		this.trxSumInsured = trxSumInsured;
		this.trxTrxId = trxTrxId;
		this.trxVoucherId = trxVoucherId;
		this.trxFeeClient = trxFeeClient;
		this.trxTsiAmount = trxTsiAmount;
		this.clientDesc = clientDesc;
		this.trxDateStr = trxDateStr;
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

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
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
	
	public String getTrxClass() {
		return trxClass;
	}

	public void setTrxClass(String trxClass) {
		this.trxClass = trxClass;
	}

	public String getTrxClient() {
		return this.trxClient;
	}

	public void setTrxClient(String trxClient) {
		this.trxClient = trxClient;
	}

	public Date getTrxClosingDate() {
		return this.trxClosingDate;
	}

	public void setTrxClosingDate(Date trxClosingDate) {
		this.trxClosingDate = trxClosingDate;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public Date getTrxConfirmDate() {
		return this.trxConfirmDate;
	}

	public void setTrxConfirmDate(Date trxConfirmDate) {
		this.trxConfirmDate = trxConfirmDate;
	}

	public String getTrxConfirmId() {
		return this.trxConfirmId;
	}

	public void setTrxConfirmId(String trxConfirmId) {
		this.trxConfirmId = trxConfirmId;
	}

	public String getTrxCurrId() {
		return this.trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public String getTrxInsuredName() {
		return trxInsuredName;
	}

	public void setTrxInsuredName(String trxInsuredName) {
		this.trxInsuredName = trxInsuredName;
	}

	public BigDecimal getTrxCurrRate() {
		return this.trxCurrRate;
	}

	public void setTrxCurrRate(BigDecimal trxCurrRate) {
		this.trxCurrRate = trxCurrRate;
	}

	public String getTrxDataStatus() {
		return this.trxDataStatus;
	}

	public void setTrxDataStatus(String trxDataStatus) {
		this.trxDataStatus = trxDataStatus;
	}

	public String getTrxDiretPy() {
		return this.trxDiretPy;
	}

	public void setTrxDiretPy(String trxDiretPy) {
		this.trxDiretPy = trxDiretPy;
	}

	public String getTrxOfficer() {
		return this.trxOfficer;
	}

	public void setTrxOfficer(String trxOfficer) {
		this.trxOfficer = trxOfficer;
	}

	public String getTrxPayMthd() {
		return this.trxPayMthd;
	}

	public void setTrxPayMthd(String trxPayMthd) {
		this.trxPayMthd = trxPayMthd;
	}

	public int getTrxPeriodVld() {
		return this.trxPeriodVld;
	}

	public void setTrxPeriodVld(int trxPeriodVld) {
		this.trxPeriodVld = trxPeriodVld;
	}

	public int getTrxPpwCli() {
		return this.trxPpwCli;
	}

	public void setTrxPpwCli(int trxPpwCli) {
		this.trxPpwCli = trxPpwCli;
	}

	public String getTrxRemarks() {
		return this.trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}

	public BigDecimal getTrxShare() {
		return this.trxShare;
	}

	public void setTrxShare(BigDecimal trxShare) {
		this.trxShare = trxShare;
	}

	public BigDecimal getTrxSumInsured() {
		return this.trxSumInsured;
	}

	public void setTrxSumInsured(BigDecimal trxSumInsured) {
		this.trxSumInsured = trxSumInsured;
	}

	public String getTrxTrxId() {
		return this.trxTrxId;
	}

	public void setTrxTrxId(String trxTrxId) {
		this.trxTrxId = trxTrxId;
	}

	public String getTrxVoucherId() {
		return this.trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public BigDecimal getTrxFeeClient() {
		return trxFeeClient;
	}

	public void setTrxFeeClient(BigDecimal trxFeeClient) {
		this.trxFeeClient = trxFeeClient;
	}

	public BigDecimal getTrxTsiAmount() {
		return trxTsiAmount;
	}

	public void setTrxTsiAmount(BigDecimal trxTsiAmount) {
		this.trxTsiAmount = trxTsiAmount;
	}
	
	public String getTrxTreAdj() {
		return trxTreAdj;
	}

	public void setTrxTreAdj(String trxTreAdj) {
		this.trxTreAdj = trxTreAdj;
	}

	public String getClientDesc() {
		return clientDesc;
	}

	public void setClientDesc(String clientDesc) {
		this.clientDesc = clientDesc;
	}
	
	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public String getTrxRemarks6a() {
		return trxRemarks6a;
	}

	public void setTrxRemarks6a(String trxRemarks6a) {
		this.trxRemarks6a = trxRemarks6a;
	}

	public Date getTrxInsStart() {
		return trxInsStart;
	}

	public void setTrxInsStart(Date trxInsStart) {
		this.trxInsStart = trxInsStart;
	}

	public Date getTrxInsEnd() {
		return trxInsEnd;
	}

	public void setTrxInsEnd(Date trxInsEnd) {
		this.trxInsEnd = trxInsEnd;
	}

	public BigDecimal getExRateValue() {
		return exRateValue;
	}

	public void setExRateValue(BigDecimal exRateValue) {
		this.exRateValue = exRateValue;
	}

	public String getClientDesc2() {
		return clientDesc2;
	}

	public void setClientDesc2(String clientDesc2) {
		this.clientDesc2 = clientDesc2;
	}

	public String getClientDesc3() {
		return clientDesc3;
	}

	public void setClientDesc3(String clientDesc3) {
		this.clientDesc3 = clientDesc3;
	}

	public String getTrxTrxTrxId6a() {
		return trxTrxTrxId6a;
	}

	public void setTrxTrxTrxId6a(String trxTrxTrxId6a) {
		this.trxTrxTrxId6a = trxTrxTrxId6a;
	}

	public String getTrxTsiAmountStr(){
		if(trxTsiAmount == null){
			return null;
		}
		return NumberFormat.getCurrencyInstance().format(trxTsiAmount).replace("$", "");
	}

	public void setTrxTsiAmountStr(String trxTsiAmountStr) {
		this.trxTsiAmountStr = trxTsiAmountStr;
	}
	
	public String getExRateValueStr() {
		if(exRateValue == null){
			return null;
		}
		return NumberFormat.getCurrencyInstance().format(exRateValue).replace("$", "");
	}

	public void setExRateValueStr(String exRateValueStr) {
		this.exRateValueStr = exRateValueStr;
	}

	public void setCreateOnMillis(Long createOnMillis) {
		this.createOnMillis = createOnMillis;
	}

	public Long getCreateOnMillis() {
		if(createOn != null)
			return createOn.getTime();
		else
			return null;
	}
	
	public String getTrxDateStr() {
		return trxDateStr;
	}

	public void setTrxDateStr(String trxDateStr) {
		this.trxDateStr = trxDateStr;
	}

	public TR0006Entity(String idKey, String trxVoucherId, String trxClient, String clientDesc,
			String trxCurrId, BigDecimal trxTsiAmount, String createBy, Date createOn,
			String trxCoverCode, String trxRemarks6a, Date trxInsStart, Date trxInsEnd){
		super();
		this.idKey = idKey;
		this.trxVoucherId = trxVoucherId;
		this.trxClient = trxClient;
		this.clientDesc = clientDesc;
		this.trxCurrId = trxCurrId;
		this.trxTsiAmount = trxTsiAmount;
		this.createBy = createBy;
		this.createOn = createOn;
		this.trxCoverCode = trxCoverCode;
		this.trxRemarks6a = trxRemarks6a;
		this.trxInsStart = trxInsStart;
		this.trxInsEnd = trxInsEnd;
	}

	@Override
	public String toString() {
		return "TR0006Entity [createBy=" + createBy + ", createOn=" + createOn + ", idKey=" + idKey + ", modifyBy="
				+ modifyBy + ", modifyOn=" + modifyOn + ", trxClass=" + trxClass + ", trxClient=" + trxClient
				+ ", trxClosingDate=" + trxClosingDate + ", trxDate=" + trxDate + ", trxConfirmDate=" + trxConfirmDate
				+ ", trxConfirmId=" + trxConfirmId + ", trxCurrId=" + trxCurrId + ", trxInsuredName=" + trxInsuredName
				+ ", trxCurrRate=" + trxCurrRate + ", trxDataStatus=" + trxDataStatus + ", trxDiretPy=" + trxDiretPy
				+ ", trxOfficer=" + trxOfficer + ", trxPayMthd=" + trxPayMthd + ", trxPeriodVld=" + trxPeriodVld
				+ ", trxPpwCli=" + trxPpwCli + ", trxRemarks=" + trxRemarks + ", trxShare=" + trxShare
				+ ", trxSumInsured=" + trxSumInsured + ", trxTrxId=" + trxTrxId + ", trxVoucherId=" + trxVoucherId
				+ ", trxFeeClient=" + trxFeeClient + ", trxTsiAmount=" + trxTsiAmount + ", trxTreAdj=" + trxTreAdj
				+ ", clientDesc=" + clientDesc + ", createOnMillis=" + createOnMillis + ", trxCoverCode=" + trxCoverCode
				+ ", trxRemarks6a=" + trxRemarks6a + ", trxInsStart=" + trxInsStart + ", trxInsEnd=" + trxInsEnd
				+ ", exRateValue=" + exRateValue + ", clientDesc2=" + clientDesc2 + ", clientDesc3=" + clientDesc3
				+ ", trxTrxTrxId6a=" + trxTrxTrxId6a + ", trxTsiAmountStr=" + trxTsiAmountStr + ", exRateValueStr="
				+ exRateValueStr + ", trxDateStr=" + trxDateStr + "]";
	}
	
}