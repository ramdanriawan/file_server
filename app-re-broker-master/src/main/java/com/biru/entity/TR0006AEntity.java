package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.concurrent.TimeUnit;

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
 * The persistent class for the TR0006A database table.
 * 
 */
@Entity
@Table(name="TR0006A")
@NamedQuery(name="TR0006AEntity.findAll", query="SELECT t FROM TR0006AEntity t")
public class TR0006AEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private String idKey;

	@Column(name="TRX_COMBINED")
	private String trxCombined;

	@Column(name="TRX_COVER_CLASS")
	private String trxCoverClass;

	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;

	@Column(name="TRX_CURR_RATE")
	private BigDecimal trxCurrRate;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_END")
	private Date trxInsEnd;

	@Column(name="TRX_INS_INSURED")
	private String trxInsInsured;

	@Column(name="TRX_INS_LOCATION")
	private String trxInsLocation;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_START")
	private Date trxInsStart;
	
	@Column(name="TRX_INS_DUR")
	private Integer trxInsDur;
	
	@Column(name="TRX_PPW")
	private Integer trxPpw;
	
	@Column(name="TRX_QUO_VALID")
	private Integer trxQuoValid;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="TRX_INS_SUB")
	private Integer trxInsSub;

	@Column(name="TRX_PREMIUM_RATE")
	private BigDecimal trxPremiumRate;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_REINS_END")
	private Date trxReinsEnd;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_REINS_START")
	private Date trxReinsStart;

	@Column(name="TRX_REMARKS")
	private String trxRemarks;

	@Column(name="TRX_SUM_INSURED")
	private BigDecimal trxSumInsured;
	
	@Column(name="TRX_PREMIUM_AMT")
	private BigDecimal trxPremiumAmt = BigDecimal.ZERO;
	
	@Column(name="TRX_CLASS")
	private String trxClass;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;

	@Column(name="TRX_WEIGHT_RATE")
	private BigDecimal trxWeightRate;
	
	@Column(name="TRX_BFEE_SELL")
	private BigDecimal trxBfeeSell;
	
	@Column(name="TRX_DEDUC_PCT")
	private BigDecimal trxDeducPct;
	
	@Column(name="TRX_TYPE_AD")
	private String trxTypeAd = "A";
	
	@Transient
	private String trxInsStartStr;
	
	@Transient
	private String trxInsEndStr;
	
	@Transient
	private String trxReinsEndStr;

	@Transient
	private String trxReinsStartStr;

	public TR0006AEntity() {
	}
	
	public TR0006AEntity(Date trxInsStart, Date trxInsEnd, Date trxReinsStart, Date trxReinsEnd) {
		this.trxInsStart = trxInsStart;
		this.trxInsEnd = trxInsEnd;
		this.trxReinsStart = trxReinsStart;
		this.trxReinsEnd = trxReinsEnd;
	}

	public TR0006AEntity(String trxCombined, String trxCoverClass, String trxCoverCode, String trxCurrId,
			BigDecimal trxCurrRate, String trxInsInsured, String trxInsLocation, int trxInsSub, 
			BigDecimal trxPremiumRate, String trxRemarks, BigDecimal trxSumInsured, BigDecimal trxPremiumAmt, String trxTrxId, 
			String trxVoucherId, BigDecimal trxWeightRate, String trxInsStartStr, String trxInsEndStr, 
			String trxReinsEndStr, String trxReinsStartStr, BigDecimal trxBfeeSell, BigDecimal trxDeducPct) {
		this.trxCombined = trxCombined;
		this.trxCoverClass = trxCoverClass;
		this.trxCoverCode = trxCoverCode;
		this.trxCurrId = trxCurrId;
		this.trxCurrRate = trxCurrRate;
		this.trxInsInsured = trxInsInsured;
		this.trxInsLocation = trxInsLocation;
		this.trxInsSub = trxInsSub;
		this.trxPremiumRate = trxPremiumRate;
		this.trxRemarks = trxRemarks;
		this.trxSumInsured = trxSumInsured;
		this.trxPremiumAmt = trxPremiumAmt;
		this.trxTrxId = trxTrxId;
		this.trxVoucherId = trxVoucherId;
		this.trxWeightRate = trxWeightRate;
		this.trxInsStartStr = trxInsStartStr;
		this.trxInsEndStr = trxInsEndStr;
		this.trxReinsEndStr = trxReinsEndStr;
		this.trxReinsStartStr = trxReinsStartStr;
		this.trxBfeeSell = trxBfeeSell;
		this.trxDeducPct = trxDeducPct;
	}
	
	public TR0006AEntity(String trxCombined, String trxCoverClass, String trxCoverCode, String trxCurrId,
			BigDecimal trxCurrRate, String trxInsInsured, String trxInsLocation, int trxInsSub, 
			BigDecimal trxPremiumRate, String trxRemarks, BigDecimal trxSumInsured, String trxTrxId, 
			String trxVoucherId, BigDecimal trxWeightRate, String trxInsStartStr, String trxInsEndStr, Integer trxInsDur,
			BigDecimal trxBfeeSell, BigDecimal trxDeducPct, Integer trxPpw, Integer trxQuoValid) {
		this.trxCombined = trxCombined;
		this.trxCoverClass = trxCoverClass;
		this.trxCoverCode = trxCoverCode;
		this.trxCurrId = trxCurrId;
		this.trxCurrRate = trxCurrRate;
		this.trxInsInsured = trxInsInsured;
		this.trxInsLocation = trxInsLocation;
		this.trxInsSub = trxInsSub;
		this.trxPremiumRate = trxPremiumRate;
		this.trxRemarks = trxRemarks;
		this.trxSumInsured = trxSumInsured;
		this.trxTrxId = trxTrxId;
		this.trxVoucherId = trxVoucherId;
		this.trxWeightRate = trxWeightRate;
		this.trxInsStartStr = trxInsStartStr;
		this.trxInsEndStr = trxInsEndStr;
		this.trxInsDur = trxInsDur;
		this.trxBfeeSell = trxBfeeSell;
		this.trxDeducPct = trxDeducPct;
		this.trxPpw = trxPpw;
		this.trxQuoValid = trxQuoValid;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getTrxCombined() {
		return this.trxCombined;
	}

	public void setTrxCombined(String trxCombined) {
		this.trxCombined = trxCombined;
	}

	public String getTrxCoverClass() {
		return this.trxCoverClass;
	}

	public void setTrxCoverClass(String trxCoverClass) {
		this.trxCoverClass = trxCoverClass;
	}

	public String getTrxCoverCode() {
		return this.trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public String getTrxCurrId() {
		return this.trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public BigDecimal getTrxCurrRate() {
		return this.trxCurrRate;
	}

	public void setTrxCurrRate(BigDecimal trxCurrRate) {
		this.trxCurrRate = trxCurrRate;
	}

	public Date getTrxInsEnd() {
		return this.trxInsEnd;
	}

	public void setTrxInsEnd(Date trxInsEnd) {
		this.trxInsEnd = trxInsEnd;
	}

	public String getTrxInsInsured() {
		return this.trxInsInsured;
	}

	public void setTrxInsInsured(String trxInsInsured) {
		this.trxInsInsured = trxInsInsured;
	}

	public String getTrxInsLocation() {
		return this.trxInsLocation;
	}

	public void setTrxInsLocation(String trxInsLocation) {
		this.trxInsLocation = trxInsLocation;
	}

	public Date getTrxInsStart() {
		return this.trxInsStart;
	}

	public void setTrxInsStart(Date trxInsStart) {
		this.trxInsStart = trxInsStart;
	}
	
	public Integer getTrxInsDur() {
		return trxInsDur;
	}

	public void setTrxInsDur(Integer trxInsDur) {
		this.trxInsDur = trxInsDur;
	}
	
	public Integer getTrxPpw() {
		return trxPpw;
	}

	public void setTrxPpw(Integer trxPpw) {
		this.trxPpw = trxPpw;
	}

	public Integer getTrxQuoValid() {
		return trxQuoValid;
	}

	public void setTrxQuoValid(Integer trxQuoValid) {
		this.trxQuoValid = trxQuoValid;
	}

	public int getTrxInsSub() {
		return this.trxInsSub;
	}

	public void setTrxInsSub(Integer trxInsSub) {
		this.trxInsSub = trxInsSub;
	}

	public BigDecimal getTrxPremiumRate() {
		return this.trxPremiumRate;
	}

	public void setTrxPremiumRate(BigDecimal trxPremiumRate) {
		this.trxPremiumRate = trxPremiumRate;
	}

	public Date getTrxReinsEnd() {
		return this.trxReinsEnd;
	}

	public void setTrxReinsEnd(Date trxReinsEnd) {
		this.trxReinsEnd = trxReinsEnd;
	}

	public Date getTrxReinsStart() {
		return this.trxReinsStart;
	}

	public void setTrxReinsStart(Date trxReinsStart) {
		this.trxReinsStart = trxReinsStart;
	}

	public String getTrxRemarks() {
		return this.trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}

	public BigDecimal getTrxSumInsured() {
		return this.trxSumInsured;
	}

	public BigDecimal getTrxPremiumAmt() {
		return trxPremiumAmt;
	}

	public void setTrxPremiumAmt(BigDecimal trxPremiumAmt) {
		this.trxPremiumAmt = trxPremiumAmt;
	}

	public void setTrxSumInsured(BigDecimal trxSumInsured) {
		this.trxSumInsured = trxSumInsured;
	}
	
	public String getTrxClass() {
		return trxClass;
	}

	public void setTrxClass(String trxClass) {
		this.trxClass = trxClass;
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

	public BigDecimal getTrxWeightRate() {
		return this.trxWeightRate;
	}

	public void setTrxWeightRate(BigDecimal trxWeightRate) {
		this.trxWeightRate = trxWeightRate;
	}

	public BigDecimal getTrxBfeeSell() {
		return trxBfeeSell;
	}

	public void setTrxBfeeSell(BigDecimal trxBfeeSell) {
		this.trxBfeeSell = trxBfeeSell;
	}

	public BigDecimal getTrxDeducPct() {
		return trxDeducPct;
	}

	public void setTrxDeducPct(BigDecimal trxDeducPct) {
		this.trxDeducPct = trxDeducPct;
	}

	public String getTrxInsStartStr() {
		return trxInsStartStr;
	}

	public void setTrxInsStartStr(String trxInsStartStr) {
		this.trxInsStartStr = trxInsStartStr;
	}

	public String getTrxInsEndStr() {
		return trxInsEndStr;
	}

	public void setTrxInsEndStr(String trxInsEndStr) {
		this.trxInsEndStr = trxInsEndStr;
	}

	public String getTrxReinsEndStr() {
		return trxReinsEndStr;
	}

	public void setTrxReinsEndStr(String trxReinsEndStr) {
		this.trxReinsEndStr = trxReinsEndStr;
	}

	public String getTrxReinsStartStr() {
		return trxReinsStartStr;
	}

	public void setTrxReinsStartStr(String trxReinsStartStr) {
		this.trxReinsStartStr = trxReinsStartStr;
	}
	
	public BigDecimal getDiffDaysInsured() {
		if(getTrxInsStart() != null && getTrxInsEnd() != null) {
			long diffInMillies = Math.abs(getTrxInsStart().getTime() - getTrxInsEnd().getTime());
		    long days = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
			
			return new BigDecimal(days)
							.divide(new BigDecimal("365"), 16, RoundingMode.HALF_UP);
		}
		
		return BigDecimal.ONE;
	}

	public String getTrxTypeAd() {
		return trxTypeAd;
	}

	public void setTrxTypeAd(String trxTypeAd) {
		this.trxTypeAd = trxTypeAd;
	}

}