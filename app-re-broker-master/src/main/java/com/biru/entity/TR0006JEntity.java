package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Date;

import javax.persistence.*;

/**
 * The persistent class for the TR0006J database table.
 * 
 */
@Entity
@Table(name="TR0006J")
@NamedQuery(name="TR0006JEntity.findAll", query="SELECT t FROM TR0006JEntity t")
public class TR0006JEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEY")
	private String idKey;

	@Column(name = "TRX_CLASS")
	private String trxClass;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;
	
	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_NON_PRO")
	private String trxNonPro;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_START")
	private Date trxInsStart;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_END")
	private Date trxInsEnd;
	
	@Column(name="TRX_COB_GROUP")
	private String trxCobGroup = "";
	
	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;
	
	@Column(name="TRX_COVER_REMA")
	private String trxCoverRema;
	
	@Column(name="TRX_LAYER")
	private String trxLayer;
	
	@Column(name="TRX_BASIS_COVER")
	private String trxBasicCover;
	
	@Column(name="TRX_CURR_ID")
	private String trxCurrId;
	
	@Column(name="TRX_EXCHANGE")
	private BigDecimal trxExchange = BigDecimal.ZERO;
	
	@Column(name="TRX_RE_INST")
	private BigDecimal trxReInst = BigDecimal.ZERO;
	
	@Column(name="TRX_REINS_RATE")
	private BigDecimal trxReinsRate = BigDecimal.ZERO;
	
	@Column(name="TRX_LIMIT_AMT")
	private BigDecimal trxLimitAmt = BigDecimal.ZERO;
	
	@Column(name="TRX_OWN_AMT")
	private BigDecimal trxOwnAmt = BigDecimal.ZERO;
	
	@Column(name="TRX_DEDUC_AMT")
	private BigDecimal trxDeducAmt = BigDecimal.ZERO;
	
	@Column(name="TRX_PREMIUM_AMT")
	private BigDecimal trxPremiumAmt = BigDecimal.ZERO;
	
	@Column(name="TRX_XOL_RATE")
	private BigDecimal trxXolRate = BigDecimal.ZERO;
	
	@Column(name="TRX_XOL_REAS")
	private BigDecimal trxXolReas = BigDecimal.ZERO;
	
	@Column(name="TRX_DEPOSIT_RATE")
	private BigDecimal trxDepositRate = BigDecimal.ZERO;
	
	@Column(name="TRX_DEPOSIT_REAS")
	private BigDecimal trxDepositReas = BigDecimal.ZERO;
	
	@Column(name="TRX_OGNRPI")
	private BigDecimal trxOgnrpi = BigDecimal.ZERO;
	
	@Column(name="TRX_OGNRPI_ACT")
	private BigDecimal trxOgnrpiAct = BigDecimal.ZERO;
	
	@Column(name="TRX_MAX_ACCPT")
	private BigDecimal trxMaxAccpt = BigDecimal.ZERO;
	
	@Column(name="TRX_REMARKS")
	private String trxRemarks;
	
	@Column(name="TRX_USD_RATE")
	private BigDecimal trxUsdRate = BigDecimal.ZERO;
	
	@Column(name="TRX_PLA_VALUE")
	private BigDecimal trxPlaValue = BigDecimal.ZERO;
	
	@Column(name="TRX_DLA_VALUE")
	private BigDecimal trxDlaValue = BigDecimal.ZERO;
	
	@Column(name="TRX_REINS_NO")
	private BigDecimal trxReinsNo = BigDecimal.ZERO;
	
	@Transient
	private String trxInsStartStr;
	
	@Transient
	private String trxInsEndStr;
	
	@Transient
	private String trxCobGroupDesc;
	
	@Transient
	private String trxCoverCodeDesc;
	
	@Transient
	private String trxOgnrpiFmt;
	
	@Transient
	private String trxOgnrpiActFmt;
	
	@Transient
	private String action;
	
	public TR0006JEntity() {
	}
	
	public TR0006JEntity(String trxNonPro, String trxInsStartStr, String trxInsEndStr) {
		super();		
		this.trxNonPro = trxNonPro;
		this.trxInsStartStr = trxInsStartStr;
		this.trxInsEndStr = trxInsEndStr;
	}
	
	public TR0006JEntity(String trxCobGroup, String trxCobGroupDesc) {
		this.trxCobGroup = trxCobGroup;
		this.trxCobGroupDesc = trxCobGroupDesc;
	}
	
	public TR0006JEntity(String trxCobGroup, String trxCoverCode, String trxLayer, 
			String trxCobGroupDesc, String trxCoverCodeDesc) {
		super();		
		this.trxCobGroup = trxCobGroup;
		this.trxCoverCode = trxCoverCode;
		this.trxLayer = trxLayer;
		this.trxCobGroupDesc = trxCobGroupDesc;
		this.trxCoverCodeDesc = trxCoverCodeDesc;
	}
	
	public TR0006JEntity(BigDecimal trxReinsNo, BigDecimal trxDeducAmt, 
			BigDecimal trxLimitAmt, BigDecimal trxOgnrpi, 
			BigDecimal trxXolRate, BigDecimal trxDepositRate) {
		super();
		this.trxReinsNo = trxReinsNo;
		this.trxDeducAmt = trxDeducAmt;
		this.trxLimitAmt = trxLimitAmt;
		this.trxOgnrpi = trxOgnrpi;
		this.trxXolRate = trxXolRate;
		this.trxDepositRate =trxDepositRate;
	}
	
	public TR0006JEntity(String trxNonPro, Date trxInsStart, Date trxInsEnd, String trxInsStartStr, String trxInsEndStr, String trxCoverCode, 
			String trxLayer, String trxBasicCover, String trxCurrId, BigDecimal trxExchange, BigDecimal trxUsdRate,
			String trxCobGroup, String trxRemarks, BigDecimal trxReInst, BigDecimal trxReinsRate, 
			BigDecimal trxLimitAmt, BigDecimal trxDeducAmt, BigDecimal trxXolRate, 
			BigDecimal trxDepositRate, BigDecimal trxOgnrpi, BigDecimal trxOgnrpiAct, 
			BigDecimal trxMaxAccpt, BigDecimal trxOwnAmt,
			String trxCobGroupDesc, String trxCoverCodeDesc, 
			BigDecimal trxXolReas, BigDecimal trxDepositReas) {
		super();		
		this.trxNonPro = trxNonPro;
		this.trxInsStart = trxInsStart;
		this.trxInsEnd = trxInsEnd;
		this.trxInsStartStr = trxInsStartStr;
		this.trxInsEndStr = trxInsEndStr;
		this.trxCoverCode = trxCoverCode;
		this.trxLayer = trxLayer;
		this.trxBasicCover = trxBasicCover;
		this.trxCurrId = trxCurrId;
		this.trxExchange = trxExchange;
		this.trxUsdRate = trxUsdRate;
		this.trxCobGroup = trxCobGroup;
		this.trxRemarks = trxRemarks;
		this.trxReInst = trxReInst;
		this.trxReinsRate = trxReinsRate;
		this.trxLimitAmt = trxLimitAmt;
		this.trxDeducAmt = trxDeducAmt;
		this.trxXolRate = trxXolRate;
		this.trxDepositRate = trxDepositRate;
		this.trxOgnrpi = trxOgnrpi;
		this.trxOgnrpiAct = trxOgnrpiAct;
		this.trxMaxAccpt = trxMaxAccpt;
		this.trxOwnAmt = trxOwnAmt;
		this.trxCobGroupDesc = trxCobGroupDesc;
		this.trxCoverCodeDesc = trxCoverCodeDesc;
		this.trxXolReas = trxXolReas;
		this.trxDepositReas = trxDepositReas;
	}
	
	public TR0006JEntity(String idKey, String trxCobGroup, 
			String trxCoverCode, String trxLayer, 
			BigDecimal trxOgnrpi, BigDecimal trxOgnrpiAct,
			String trxCobGroupDesc, String trxCoverCodeDesc) {
		super();		
		this.idKey = idKey;
		this.trxCobGroup = trxCobGroup;
		this.trxCoverCode = trxCoverCode;
		this.trxLayer = trxLayer;
		this.trxOgnrpi = trxOgnrpi;
		this.trxOgnrpiAct = trxOgnrpiAct;
		this.trxCobGroupDesc = trxCobGroupDesc;
		this.trxCoverCodeDesc = trxCoverCodeDesc;
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

	public String getTrxNonPro() {
		return trxNonPro;
	}

	public void setTrxNonPro(String trxNonPro) {
		this.trxNonPro = trxNonPro;
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

	public String getTrxCobGroup() {
		return trxCobGroup;
	}

	public void setTrxCobGroup(String trxCobGroup) {
		this.trxCobGroup = trxCobGroup;
	}

	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public String getTrxCoverRema() {
		return trxCoverRema;
	}

	public void setTrxCoverRema(String trxCoverRema) {
		this.trxCoverRema = trxCoverRema;
	}

	public String getTrxLayer() {
		return trxLayer;
	}

	public void setTrxLayer(String trxLayer) {
		this.trxLayer = trxLayer;
	}

	public String getTrxBasicCover() {
		return trxBasicCover;
	}

	public void setTrxBasicCover(String trxBasicCover) {
		this.trxBasicCover = trxBasicCover;
	}

	public String getTrxCurrId() {
		return trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public BigDecimal getTrxExchange() {
		return trxExchange;
	}

	public void setTrxExchange(BigDecimal trxExchange) {
		this.trxExchange = trxExchange;
	}

	public BigDecimal getTrxReInst() {
		return trxReInst;
	}

	public void setTrxReInst(BigDecimal trxReInst) {
		this.trxReInst = trxReInst;
	}

	public BigDecimal getTrxReinsRate() {
		return trxReinsRate;
	}

	public void setTrxReinsRate(BigDecimal trxReinsRate) {
		this.trxReinsRate = trxReinsRate;
	}

	public BigDecimal getTrxLimitAmt() {
		return trxLimitAmt;
	}

	public void setTrxLimitAmt(BigDecimal trxLimitAmt) {
		this.trxLimitAmt = trxLimitAmt;
	}

	public BigDecimal getTrxOwnAmt() {
		return trxOwnAmt;
	}

	public void setTrxOwnAmt(BigDecimal trxOwnAmt) {
		this.trxOwnAmt = trxOwnAmt;
	}

	public BigDecimal getTrxDeducAmt() {
		return trxDeducAmt;
	}

	public void setTrxDeducAmt(BigDecimal trxDeducAmt) {
		this.trxDeducAmt = trxDeducAmt;
	}

	public BigDecimal getTrxPremiumAmt() {
		return trxPremiumAmt;
	}

	public void setTrxPremiumAmt(BigDecimal trxPremiumAmt) {
		this.trxPremiumAmt = trxPremiumAmt;
	}

	public BigDecimal getTrxXolRate() {
		return trxXolRate;
	}

	public void setTrxXolRate(BigDecimal trxXolRate) {
		this.trxXolRate = trxXolRate;
	}

	public BigDecimal getTrxDepositRate() {
		return trxDepositRate;
	}

	public void setTrxDepositRate(BigDecimal trxDepositRate) {
		this.trxDepositRate = trxDepositRate;
	}

	public BigDecimal getTrxOgnrpi() {
		return trxOgnrpi;
	}

	public void setTrxOgnrpi(BigDecimal trxOgnrpi) {
		this.trxOgnrpi = trxOgnrpi;
	}

	public BigDecimal getTrxOgnrpiAct() {
		return trxOgnrpiAct;
	}

	public void setTrxOgnrpiAct(BigDecimal trxOgnrpiAct) {
		this.trxOgnrpiAct = trxOgnrpiAct;
	}

	public BigDecimal getTrxMaxAccpt() {
		return trxMaxAccpt;
	}

	public void setTrxMaxAccpt(BigDecimal trxMaxAccpt) {
		this.trxMaxAccpt = trxMaxAccpt;
	}

	public String getTrxRemarks() {
		return trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}

	public BigDecimal getTrxUsdRate() {
		return trxUsdRate;
	}

	public void setTrxUsdRate(BigDecimal trxUsdRate) {
		this.trxUsdRate = trxUsdRate;
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

	public String getTrxCobGroupDesc() {
		return trxCobGroupDesc;
	}

	public void setTrxCobGroupDesc(String trxCobGroupDesc) {
		this.trxCobGroupDesc = trxCobGroupDesc;
	}

	public String getTrxCoverCodeDesc() {
		return trxCoverCodeDesc;
	}

	public void setTrxCoverCodeDesc(String trxCoverCodeDesc) {
		this.trxCoverCodeDesc = trxCoverCodeDesc;
	}
	
	public String getTrxOgnrpiFmt() {
		if(trxOgnrpi != null)
			return NumberFormat.getCurrencyInstance().format(trxOgnrpi).replace("$", "");
		
		return trxOgnrpiFmt;
	}

	public void setTrxOgnrpiFmt(String trxOgnrpiFmt) {
		this.trxOgnrpiFmt = trxOgnrpiFmt;
	}

	public String getTrxOgnrpiActFmt() {
		if(trxOgnrpiAct != null)
			return NumberFormat.getCurrencyInstance().format(trxOgnrpiAct).replace("$", "");
		
		return trxOgnrpiActFmt;
	}

	public void setTrxOgnrpiActFmt(String trxOgnrpiActFmt) {
		this.trxOgnrpiActFmt = trxOgnrpiActFmt;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public BigDecimal getTrxPlaValue() {
		return trxPlaValue;
	}

	public void setTrxPlaValue(BigDecimal trxPlaValue) {
		this.trxPlaValue = trxPlaValue;
	}

	public BigDecimal getTrxDlaValue() {
		return trxDlaValue;
	}

	public void setTrxDlaValue(BigDecimal trxDlaValue) {
		this.trxDlaValue = trxDlaValue;
	}

	public BigDecimal getTrxReinsNo() {
		return trxReinsNo;
	}

	public void setTrxReinsNo(BigDecimal trxReinsNo) {
		this.trxReinsNo = trxReinsNo;
	}

	public BigDecimal getTrxXolReas() {
		return trxXolReas;
	}

	public void setTrxXolReas(BigDecimal trxXolReas) {
		this.trxXolReas = trxXolReas;
	}

	public BigDecimal getTrxDepositReas() {
		return trxDepositReas;
	}

	public void setTrxDepositReas(BigDecimal trxDepositReas) {
		this.trxDepositReas = trxDepositReas;
	}
		
}