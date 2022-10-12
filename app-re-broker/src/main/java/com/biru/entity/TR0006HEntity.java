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

import org.apache.commons.lang3.StringUtils;

/**
 * The persistent class for the TR0006G database table.
 * 
 */
@Entity
@Table(name = "TR0006H")
@NamedQuery(name = "TR0006HEntity.findAll", query = "SELECT t FROM TR0006HEntity t")
public class TR0006HEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEY")
	private String idKey;
	
	@Column(name = "TRX_CLASS")
	private String trxClass;

	@Column(name = "TRX_TRX_ID")
	private String trxTrxId;

	@Column(name = "TRX_VOUCHER_ID")
	private String trxVoucherId;

	@Column(name = "TRX_OLD_TYPE")
	private String trxOldType;

	@Column(name = "TRX_OLD_VOUCHER_ID")
	private String trxOldVoucherId;

	@Column(name = "TRX_CURR_ID")
	private String trxCurrId;

	@Column(name = "TRX_CURR_RATE")
	private BigDecimal trxCurrRate;

	@Column(name = "TRX_TSI_AMOUNT")
	private BigDecimal trxTsiAmount;

	@Column(name = "TRX_SUM_INSURED")
	private BigDecimal trxSumInsured;

	@Column(name = "TRX_NEW_PORTION")
	private BigDecimal trxNewPortion;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRX_DATE")
	private Date trxDate;

	@Transient
	private String trxNewStartF;

	@Transient
	private String trxNewEndF;
	
	@Transient
	private String period;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRX_NEW_START")
	private Date trxNewStart;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRX_NEW_END")
	private Date trxNewEnd;

	@Column(name = "TRX_INTEREST")
	private String trxInterest;

	@Column(name = "TRX_PREMIUM_CLIENT")
	private BigDecimal trxPremiumClient;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRX_DUE_CLIENT")
	private Date trxDueClient;

	@Column(name = "TRX_PREMIUM_RE")
	private BigDecimal trxPremiumRe;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRX_DUE_RE")
	private Date trxDueRe;

	@Column(name = "TRX_BRDX_ID")
	private String trxBrdxId;

	@Column(name = "TRX_PARTY_NAME")
	private String trxPartyName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRX_BIRTH_DATE")
	private Date trxBirthDate;

	@Column(name = "TRX_PARTICIPANT_ID")
	private String trxParticipantId;

	@Column(name = "TRX_DURATION")
	private BigDecimal trxDuration;

	@Column(name = "TRX_CREDIT_LIMIT")
	private BigDecimal trxCreditLimit;

	@Column(name = "TRX_GP_RATE")
	private BigDecimal trxGpRate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRX_SUBS_DATE")
	private Date trxSubsDate;

	@Column(name = "TRX_BENEFIT")
	private String trxBenefit;

	@Column(name = "TRX_DESC_TYPE")
	private String trxDescType;

	@Column(name = "TRX_DESC_YOB")
	private String trxDescYob;

	@Column(name = "TRX_DESC_GRT")
	private String trxDescGrt;

	@Column(name = "TRX_DESC_CLASS")
	private String trxDescClass;

	@Column(name = "TRX_DESC_FLAG")
	private String trxDescFlag;

	@Column(name = "TRX_DESC_CONST")
	private String trxDescConst;

	@Column(name = "TRX_FILE_NAME")
	private String trxFileName;

	@Column(name = "TRX_DATA_STATUS")
	private String trxDataStatus;

	@Column(name = "TRX_GP100")
	private BigDecimal trxGp100;
	
	@Column(name = "TRX_YEAR")
	private Integer trxYear;
	
	@Column(name = "TRX_COMM")
	private BigDecimal trxComm;
	
	@Column(name = "TRX_CLAIM")
	private BigDecimal trxClaim;
	
	@Column(name = "TRX_RE_SHARE")
	private BigDecimal trxReShare;
	
	@Column(name = "TRX_BROKR_FEE")
	private BigDecimal trxBrokrFee;
	
	@Column(name = "TRX_CLIENT_ID")
	private String trxClientId;
	
	@Column(name = "TRX_BALANCE")
	private BigDecimal trxBalance;
	
	@Column(name = "TRX_GROSS")
	private BigDecimal trxGross;
	
	@Column(name = "CREATE_BY")
	private String trxCreatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_ON")
	private Date trxCreatedOn;
	
	@Column(name = "TRX_CLAIM_STATUS")
	private String trxClaimStatus;
	
	@Transient
	private String trxTsiAmountStr;
	
	@Transient
	private String trxClaimStatusVal;
	
	@Transient
	private String fileName;

	public void setTrxNewStartF(String trxNewStartF) {
		this.trxNewStartF = trxNewStartF;
	}
	
	public String getTrxNewStartF() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if(trxNewStart != null)
			return sdf.format(trxNewStart);
		else
			return trxNewStartF;
	}

	public void setTrxNewEndF(String trxNewEndF) {
		this.trxNewEndF = trxNewEndF;
	}

	public String getTrxNewEndF() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

		if(trxNewEnd != null)
			return sdf.format(trxNewEnd);
		else
			return trxNewEndF;
	}
	
	public String getPeriod() {
		String start = (trxNewStartF == null) ? "" : trxNewStartF;
		String end = (trxNewEndF == null) ? "" : trxNewEndF;
		
		return start + " - " + end;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public TR0006HEntity() {
		super();
	}
	
	public TR0006HEntity(String idKey, String trxBrdxId, String trxPartyName, 
			String trxNewStartF, String trxNewEndF, String trxCurrId, BigDecimal trxTsiAmount,
			String trxBenefit, String trxClaimStatus, String trxClaimStatusVal, String trxInterest,
			String trxFileName) {
		this.idKey = idKey;
		this.trxBrdxId = trxBrdxId;
		this.trxPartyName = trxPartyName;
		this.trxNewStartF = trxNewStartF;
		this.trxNewEndF = trxNewEndF;
		this.trxCurrId = trxCurrId;
		this.trxTsiAmount = trxTsiAmount;
		this.trxBenefit = trxBenefit;
		this.trxClaimStatus = trxClaimStatus;
		this.trxClaimStatusVal = trxClaimStatusVal;
		this.trxInterest = trxInterest;
		this.trxFileName = trxFileName;
	}

	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getTrxClass() {
		return trxClass;
	}

	public void setTrxClass(String trxClass) {
		this.trxClass = trxClass;
	}

	public String getTrxTrxId() {
		return trxTrxId;
	}

	public void setTrxTrxId(String trxTrxId) {
		this.trxTrxId = trxTrxId;
	}

	public String getTrxVoucherId() {
		return trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public String getTrxOldType() {
		return trxOldType;
	}

	public void setTrxOldType(String trxOldType) {
		this.trxOldType = trxOldType;
	}

	public String getTrxOldVoucherId() {
		return trxOldVoucherId;
	}

	public void setTrxOldVoucherId(String trxOldVoucherId) {
		this.trxOldVoucherId = trxOldVoucherId;
	}

	public String getTrxCurrId() {
		return trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public BigDecimal getTrxCurrRate() {
		return trxCurrRate;
	}

	public void setTrxCurrRate(BigDecimal trxCurrRate) {
		this.trxCurrRate = trxCurrRate;
	}

	public BigDecimal getTrxTsiAmount() {
		return trxTsiAmount;
	}

	public void setTrxTsiAmount(BigDecimal trxTsiAmount) {
		this.trxTsiAmount = trxTsiAmount;
	}

	public BigDecimal getTrxSumInsured() {
		return trxSumInsured;
	}

	public void setTrxSumInsured(BigDecimal trxSumInsured) {
		this.trxSumInsured = trxSumInsured;
	}

	public BigDecimal getTrxNewPortion() {
		return trxNewPortion;
	}

	public void setTrxNewPortion(BigDecimal trxNewPortion) {
		this.trxNewPortion = trxNewPortion;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public Date getTrxNewStart() {
		return trxNewStart;
	}

	public void setTrxNewStart(Date trxNewStart) {
		this.trxNewStart = trxNewStart;
	}

	public Date getTrxNewEnd() {
		return trxNewEnd;
	}

	public void setTrxNewEnd(Date trxNewEnd) {
		this.trxNewEnd = trxNewEnd;
	}

	public String getTrxInterest() {
		return trxInterest;
	}

	public void setTrxInterest(String trxInterest) {
		this.trxInterest = trxInterest;
	}

	public BigDecimal getTrxPremiumClient() {
		return trxPremiumClient;
	}

	public void setTrxPremiumClient(BigDecimal trxPremiumClient) {
		this.trxPremiumClient = trxPremiumClient;
	}

	public Date getTrxDueClient() {
		return trxDueClient;
	}

	public void setTrxDueClient(Date trxDueClient) {
		this.trxDueClient = trxDueClient;
	}

	public BigDecimal getTrxPremiumRe() {
		return trxPremiumRe;
	}

	public void setTrxPremiumRe(BigDecimal trxPremiumRe) {
		this.trxPremiumRe = trxPremiumRe;
	}

	public Date getTrxDueRe() {
		return trxDueRe;
	}

	public void setTrxDueRe(Date trxDueRe) {
		this.trxDueRe = trxDueRe;
	}

	public String getTrxBrdxId() {
		return trxBrdxId;
	}

	public void setTrxBrdxId(String trxBrdxId) {
		this.trxBrdxId = trxBrdxId;
	}

	public String getTrxPartyName() {
		return trxPartyName;
	}

	public void setTrxPartyName(String trxPartyName) {
		this.trxPartyName = trxPartyName;
	}

	public Date getTrxBirthDate() {
		return trxBirthDate;
	}

	public void setTrxBirthDate(Date trxBirthDate) {
		this.trxBirthDate = trxBirthDate;
	}

	public String getTrxParticipantId() {
		return trxParticipantId;
	}

	public void setTrxParticipantId(String trxParticipantId) {
		this.trxParticipantId = trxParticipantId;
	}

	public BigDecimal getTrxDuration() {
		return trxDuration;
	}

	public void setTrxDuration(BigDecimal trxDuration) {
		this.trxDuration = trxDuration;
	}

	public BigDecimal getTrxCreditLimit() {
		return trxCreditLimit;
	}

	public void setTrxCreditLimit(BigDecimal trxCreditLimit) {
		this.trxCreditLimit = trxCreditLimit;
	}

	public BigDecimal getTrxGpRate() {
		return trxGpRate;
	}

	public void setTrxGpRate(BigDecimal trxGpRate) {
		this.trxGpRate = trxGpRate;
	}

	public Date getTrxSubsDate() {
		return trxSubsDate;
	}

	public void setTrxSubsDate(Date trxSubsDate) {
		this.trxSubsDate = trxSubsDate;
	}

	public String getTrxBenefit() {
		return trxBenefit;
	}

	public void setTrxBenefit(String trxBenefit) {
		this.trxBenefit = trxBenefit;
	}

	public String getTrxDescType() {
		return trxDescType;
	}

	public void setTrxDescType(String trxDescType) {
		this.trxDescType = trxDescType;
	}

	public String getTrxDescYob() {
		return trxDescYob;
	}

	public void setTrxDescYob(String trxDescYob) {
		this.trxDescYob = trxDescYob;
	}

	public String getTrxDescGrt() {
		return trxDescGrt;
	}

	public void setTrxDescGrt(String trxDescGrt) {
		this.trxDescGrt = trxDescGrt;
	}

	public String getTrxDescClass() {
		return trxDescClass;
	}

	public void setTrxDescClass(String trxDescClass) {
		this.trxDescClass = trxDescClass;
	}

	public String getTrxDescFlag() {
		return trxDescFlag;
	}

	public void setTrxDescFlag(String trxDescFlag) {
		this.trxDescFlag = trxDescFlag;
	}

	public String getTrxDescConst() {
		return trxDescConst;
	}

	public void setTrxDescConst(String trxDescConst) {
		this.trxDescConst = trxDescConst;
	}

	public String getTrxFileName() {
		return trxFileName;
	}

	public void setTrxFileName(String trxFileName) {
		this.trxFileName = trxFileName;
	}

	public String getTrxDataStatus() {
		return trxDataStatus;
	}

	public void setTrxDataStatus(String trxDataStatus) {
		this.trxDataStatus = trxDataStatus;
	}

	public BigDecimal getTrxGp100() {
		return trxGp100;
	}

	public void setTrxGp100(BigDecimal trxGp100) {
		this.trxGp100 = trxGp100;
	}
	
	public Integer getTrxYear() {
		return trxYear;
	}

	public void setTrxYear(Integer trxYear) {
		this.trxYear = trxYear;
	}

	public BigDecimal getTrxComm() {
		return trxComm;
	}

	public void setTrxComm(BigDecimal trxComm) {
		this.trxComm = trxComm;
	}

	public BigDecimal getTrxClaim() {
		return trxClaim;
	}

	public void setTrxClaim(BigDecimal trxClaim) {
		this.trxClaim = trxClaim;
	}

	public BigDecimal getTrxReShare() {
		return trxReShare;
	}

	public void setTrxReShare(BigDecimal trxReShare) {
		this.trxReShare = trxReShare;
	}

	public BigDecimal getTrxBrokrFee() {
		return trxBrokrFee;
	}

	public void setTrxBrokrFee(BigDecimal trxBrokrFee) {
		this.trxBrokrFee = trxBrokrFee;
	}

	public String getTrxClientId() {
		return trxClientId;
	}

	public void setTrxClientId(String trxClientId) {
		this.trxClientId = trxClientId;
	}

	public BigDecimal getTrxBalance() {
		return trxBalance;
	}

	public void setTrxBalance(BigDecimal trxBalance) {
		this.trxBalance = trxBalance;
	}

	public BigDecimal getTrxGross() {
		return trxGross;
	}

	public void setTrxGross(BigDecimal trxGross) {
		this.trxGross = trxGross;
	}

	public String getTrxCreatedBy() {
		return trxCreatedBy;
	}

	public void setTrxCreatedBy(String trxCreatedBy) {
		this.trxCreatedBy = trxCreatedBy;
	}

	public Date getTrxCreatedOn() {
		return trxCreatedOn;
	}

	public String getTrxClaimStatus() {
		return trxClaimStatus;
	}

	public void setTrxClaimStatus(String trxClaimStatus) {
		this.trxClaimStatus = trxClaimStatus;
	}

	public void setTrxCreatedOn(Date trxCreatedOn) {
		this.trxCreatedOn = trxCreatedOn;
	}
	
	public String getTrxTsiAmountStr() {
		if(trxTsiAmount != null)
			return NumberFormat.getCurrencyInstance().format(trxTsiAmount).replace("$", "");
		
		return trxTsiAmountStr;
	}

	public void setTrxTsiAmountStr(String trxTsiAmountStr) {
		this.trxTsiAmountStr = trxTsiAmountStr;
	}
	
	public String getTrxClaimStatusVal() {
		return trxClaimStatusVal;
	}

	public void setTrxClaimStatusVal(String trxClaimStatusVal) {
		this.trxClaimStatusVal = trxClaimStatusVal;
	}

	public String getFileName() {
		if(StringUtils.isBlank(trxFileName))
			return "";
		
		int start = trxFileName.lastIndexOf("/");
		if(start+1 <= trxFileName.length())
			start += 1;
		
		return trxFileName.substring(start, trxFileName.length());
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "TR0006HEntity [idKey=" + idKey + ", trxTrxId=" + trxTrxId + ", trxVoucherId=" + trxVoucherId
				+ ", trxOldTrxId=" + trxOldType + ", trxOldVoucherId=" + trxOldVoucherId + ", trxCurrId=" + trxCurrId
				+ ", trxCurrRate=" + trxCurrRate + ", trxTsiAmount=" + trxTsiAmount + ", trxSumInsured=" + trxSumInsured
				+ ", trxNewPortion=" + trxNewPortion + ", trxDate=" + trxDate + ", trxNewStart=" + trxNewStart
				+ ", trxNewEnd=" + trxNewEnd + ", trxInterest=" + trxInterest + ", trxPremiumClient=" + trxPremiumClient
				+ ", trxDueClient=" + trxDueClient + ", trxPremiumRe=" + trxPremiumRe + ", trxDueRe=" + trxDueRe
				+ ", trxBrdxId=" + trxBrdxId + ", trxPartyName=" + trxPartyName + ", trxBirthDate=" + trxBirthDate
				+ ", trxParticipantId=" + trxParticipantId + ", trxDuration=" + trxDuration + ", trxCreditLimit="
				+ trxCreditLimit + ", trxGpRate=" + trxGpRate + ", trxSubsDate=" + trxSubsDate + ", trxBenefit="
				+ trxBenefit + ", trxDescType=" + trxDescType + ", trxDescYob=" + trxDescYob + ", trxDescGrt="
				+ trxDescGrt + ", trxDescClass=" + trxDescClass + ", trxDescConst=" + trxDescConst + ", trxFileName="
				+ trxFileName + ", trxGp100=" + trxGp100 + ", trxCreatedBy=" + trxCreatedBy + ", trxCreatedOn="
				+ trxCreatedOn + "]";
	}

}
