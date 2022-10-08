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

@Entity
@Table(name="TR0015")
@NamedQuery(name="TR0015Entity.findAll", query="SELECT t FROM TR0015Entity t")
public class TR0015Entity implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="ID_KEY")
	private Long idKey;
	
	@Column(name="TX_TYPE")
	private String txType;
	
	@Column(name="TX_VOUCHER_ID")
	private String txVoucherId;
	
	@Column(name="TX_TRX_SRC")
	private String txTrxSrc;
	
	@Column(name="TX_OLD_TYPE")
	private String txOldType;
	
	@Column(name="TX_OLD_VOUCHER_ID")
	private String txOldVoucherId;
	
	@Column(name="TX_CLIENT_ID")
	private String txClientId;
	
	@Column(name="TX_COVER_ID")
	private String txCoverId;
	
	@Column(name="TX_POLICY_NO")
	private String txPolicyNo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TX_DATE_FROM")
	private Date txDateFrom;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TX_DATE_TO")
	private Date txDateTo;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TX_INCEPTION")
	private Date txInception;
	
	@Column(name="TX_INTEREST")
	private String txInterest;
	
	@Column(name="TX_CURR_ID")
	private String txCurrId;
	
	@Column(name="TX_SUM_INS")
	private BigDecimal txSumIns;
	
	@Column(name="TX_TRX_STATUS")
	private String txTrxStatus;
	
	@Column(name="TX_PPW")
	private int txPpw;
	
	@Column(name="TX_CLIENT_BANK")
	private String txClientBank = "";
	
	@Column(name="TX_METHOD")
	private String txMethod;
	
	@Column(name="TX_ATT_CLIENT")
	private String txAttClient;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TX_LOST_DATE")
	private Date txLostDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TX_ADVICE_DATE")
	private Date txAdviceDate;
	
	@Column(name="TX_UW_REFF")
	private String txUwReff;
	
	@Column(name="TX_CAUSE")
	private String txCause;
	
	@Column(name="TX_DETAILS")
	private String txDetails;
	
	@Column(name="TX_MAX_PAID")
	private int txMaxPaid;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;
	
	@Column(name="CREATE_BY")
	private String createBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFY_ON")
	private Date modifyOn;
	
	@Column(name="MODIFY_BY")
	private String modifyBy;
	
	@Column(name="TX_DATA_STATUS")
	private String txDataStatus;
	
	@Column(name="TX_CLAIM_AMT")
	private BigDecimal txClaimAmt = BigDecimal.ZERO;
	
	@Column(name="TX_DEDUC_AMT")
	private BigDecimal txDeducAmt = BigDecimal.ZERO;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TX_DATE_EST")
	private Date txDateEst;
	
	@Column(name="TX_PAY_MTD")
	private String txPayMtd;
	
	@Column(name="TX_APPR_AMT")
	private BigDecimal txApprAmt = BigDecimal.ZERO;
	
	@Column(name="TX_CLAIM_FEE")
	private BigDecimal txClaimFee = BigDecimal.ZERO;
	
	@Column(name="TX_ADJUST_FEE")
	private BigDecimal txAdjustFee = BigDecimal.ZERO;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TX_DATE_SET")
	private Date txDateSet;
	
	@Column(name="TX_PARTY_NAME")
	private String txPartyName;
	
	@Column(name="TX_BRDX_ID")
	private String txBrdxId;
	
	@Column(name="RELATED_ID_KEY")
	private Long relatedIdKey;
	
	@Column(name="TX_OR_AMT")
	private BigDecimal txOrAmt = BigDecimal.ZERO;
	
	@Column(name="TX_QS_AMT")
	private BigDecimal txQsAmt = BigDecimal.ZERO;
	
	@Column(name="TX_REINS_NO")
	private BigDecimal txReinsNo = BigDecimal.ZERO;
	
	@Column(name="TX_REINS_CL")
	private BigDecimal txReinsCl = BigDecimal.ZERO;
	
	@Column(name="TX_REINS_UW")
	private BigDecimal txReinsUw = BigDecimal.ZERO;
	
	@Column(name="TX_DC_REAL")
	private BigDecimal txDcReal = BigDecimal.ZERO;
	
	@Column(name="TX_UR_AMT")
	private BigDecimal txUrAmt = BigDecimal.ZERO;
	
	@Column(name="TX_UWCL_AMT")
	private BigDecimal txUwclAmt = BigDecimal.ZERO;
	
	@Column(name="TX_CLCL_AMT")
	private BigDecimal txClclAmt = BigDecimal.ZERO;
	
	@Column(name="TX_UWPR_AMT")
	private BigDecimal txUwprAmt = BigDecimal.ZERO;
	
	@Transient 
	private String action;
	
	@Transient 
	private String txSumInsStr;
	
	@Transient 
	private String period1Start;
	
	@Transient 
	private String period1End;
	
	@Transient 
	private String period2Start;
	
	@Transient 
	private String period2End;
	
	@Transient 
	private String txClientCode;
	
	@Transient 
	private String txDataStatusVal;
	
	@Transient 
	private String txLostDateStr;
	
	@Transient 
	private String txAdviceDateStr;

	public String getAction() {
		return "<button class=\"btn btn-secondary\" onclick=\"editPa('"+getIdKey()+"')\">" 
				+ "<i class=\"fa fa-trash\"></i>" 
				+ "</button>";
	}

	public TR0015Entity() {
		super();
	}

	public TR0015Entity(String txPolicyNo, String txTrxSrc, Date txDateFrom, BigDecimal txClaimAmt, String txClientBank,
			String txCoverId, Date txDateSet, BigDecimal txSumIns, int txMaxPaid, Date txLostDate, Date txDateEst,
			String txUwReff, String txVoucherId, Date modifyOn, String txDataStatus, String txClientId, String modifyBy,
			String txMethod, String txType, BigDecimal txDeducAmt, BigDecimal txClaimFee, String txCurrId,
			String txDetails, int txPpw, BigDecimal txApprAmt, BigDecimal txAdjustFee, Date txAdviceDate,
			String txCause, Date createOn, Date txInception, String txPayMtd, String txOldType, String txTrxStatus,
			String action, String txAttClient, String txOldVoucherId, Date txDateTo, String createBy,
			String txInterest) {
		super();
		this.txPolicyNo = txPolicyNo;
		this.txTrxSrc = txTrxSrc;
		this.txDateFrom = txDateFrom;
		this.txClaimAmt = txClaimAmt;
		this.txClientBank = txClientBank;
		this.txCoverId = txCoverId;
		this.txDateSet = txDateSet;
		this.txSumIns = txSumIns;
		this.txMaxPaid = txMaxPaid;
		this.txLostDate = txLostDate;
		this.txDateEst = txDateEst;
		this.txUwReff = txUwReff;
		this.txVoucherId = txVoucherId;
		this.modifyOn = modifyOn;
		this.txDataStatus = txDataStatus;
		this.txClientId = txClientId;
		this.modifyBy = modifyBy;
		this.txMethod = txMethod;
		this.txType = txType;
		this.txDeducAmt = txDeducAmt;
		this.txClaimFee = txClaimFee;
		this.txCurrId = txCurrId;
		this.txDetails = txDetails;
		this.txPpw = txPpw;
		this.txApprAmt = txApprAmt;
		this.txAdjustFee = txAdjustFee;
		this.txAdviceDate = txAdviceDate;
		this.txCause = txCause;
		this.createOn = createOn;
		this.txInception = txInception;
		this.txPayMtd = txPayMtd;
		this.txOldType = txOldType;
		this.txTrxStatus = txTrxStatus;
		this.action = action;
		this.txAttClient = txAttClient;
		this.txOldVoucherId = txOldVoucherId;
		this.txDateTo = txDateTo;
		this.createBy = createBy;
		this.txInterest = txInterest;
	}
	
	public TR0015Entity(String txClientId, String txClientCode, String txPolicyNo,
			String txCoverId, String period1Start, String period1End, 
			String period2Start, String period2End, String txPartyName, String txBrdxId,
			String txInterest, String txCurrId, BigDecimal txSumIns, String txDataStatus, 
			String txDataStatusVal, String txAttClient, String txType, String txVoucherId,
			String txOldType, String txOldVoucherId, Long relatedIdKey, String createBy, Date createOn,
			BigDecimal txOrAmt, BigDecimal txQsAmt, BigDecimal txReinsNo, BigDecimal txReinsCl,
			BigDecimal txReinsUw, BigDecimal txDcReal, BigDecimal txUrAmt, BigDecimal txUwclAmt, 
			BigDecimal txClclAmt, BigDecimal txUwprAmt) {
		this.txClientId = txClientId;
		this.txClientCode = txClientCode;
		this.txPolicyNo = txPolicyNo;
		this.txCoverId = txCoverId;
		this.period1Start = period1Start;
		this.period1End = period1End;
		this.period2Start = period2Start;
		this.period2End = period2End;
		this.txPartyName = txPartyName;
		this.txBrdxId = txBrdxId;
		this.txInterest = txInterest;
		this.txCurrId = txCurrId;
		this.txSumIns = txSumIns;
		this.txDataStatus = txDataStatus;
		this.txDataStatusVal = txDataStatusVal;
		this.txAttClient = txAttClient;
		this.txType = txType;
		this.txVoucherId = txVoucherId;
		this.txOldType = txOldType;
		this.txOldVoucherId = txOldVoucherId;
		this.relatedIdKey = relatedIdKey;
		this.createBy = createBy;
		this.createOn = createOn;
		this.txOrAmt = txOrAmt;
		this.txQsAmt = txQsAmt;
		this.txReinsNo = txReinsNo;
		this.txReinsCl = txReinsCl;
		this.txReinsUw = txReinsUw;
		this.txDcReal = txDcReal;
		this.txUrAmt = txUrAmt;
		this.txUwclAmt = txUwclAmt; 
		this.txClclAmt = txClclAmt;
		this.txUwprAmt = txUwprAmt;
	}
	
	public TR0015Entity(String txType, String txVoucherId,
			String txLostDateStr, String txAdviceDateStr, String txUwReff,
			Integer txMaxPaid, String txCause, String txDetails) {
		this.txType = txType;
		this.txVoucherId = txVoucherId;
		this.txLostDateStr = txLostDateStr;
		this.txAdviceDateStr = txAdviceDateStr;
		this.txUwReff = txUwReff;
		this.txMaxPaid = txMaxPaid;
		this.txCause = txCause;
		this.txDetails= txDetails;
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getTxType() {
		return txType;
	}

	public void setTrxTrxId(String txType) {
		this.txType = txType;
	}

	public String getTxVoucherId() {
		return txVoucherId;
	}

	public void setTxVoucherId(String txVoucherId) {
		this.txVoucherId = txVoucherId;
	}

	public String getTxTrxSrc() {
		return txTrxSrc;
	}

	public void setTxTrxSrc(String txTrxSrc) {
		this.txTrxSrc = txTrxSrc;
	}

	public String getTxOldType() {
		return txOldType;
	}

	public void setTxOldType(String txOldType) {
		this.txOldType = txOldType;
	}

	public String getTxOldVoucherId() {
		return txOldVoucherId;
	}

	public void setTxOldVoucherId(String txOldVoucherId) {
		this.txOldVoucherId = txOldVoucherId;
	}

	public String getTxClientId() {
		return txClientId;
	}

	public void setTxClientId(String txClientId) {
		this.txClientId = txClientId;
	}

	public String getTxCoverId() {
		return txCoverId;
	}

	public void setTxCoverId(String txCoverId) {
		this.txCoverId = txCoverId;
	}

	public String getTxPolicyNo() {
		return txPolicyNo;
	}

	public void setTxPolicyNo(String txPolicyNo) {
		this.txPolicyNo = txPolicyNo;
	}

	public Date getTxDateFrom() {
		return txDateFrom;
	}

	public void setTxDateFrom(Date txDateFrom) {
		this.txDateFrom = txDateFrom;
	}

	public Date getTxDateTo() {
		return txDateTo;
	}

	public void setTxDateTo(Date txDateTo) {
		this.txDateTo = txDateTo;
	}

	public Date getTxInception() {
		return txInception;
	}

	public void setTxInception(Date txInception) {
		this.txInception = txInception;
	}

	public String getTxInterest() {
		return txInterest;
	}

	public void setTxInterest(String txInterest) {
		this.txInterest = txInterest;
	}

	public String getTxCurrId() {
		return txCurrId;
	}

	public void setTxCurrId(String txCurrId) {
		this.txCurrId = txCurrId;
	}

	public BigDecimal getTxSumIns() {
		return txSumIns;
	}

	public void setTxSumIns(BigDecimal txSumIns) {
		this.txSumIns = txSumIns;
	}

	public String getTxTrxStatus() {
		return txTrxStatus;
	}

	public void setTxTrxStatus(String txTrxStatus) {
		this.txTrxStatus = txTrxStatus;
	}

	public int getTxPpw() {
		return txPpw;
	}

	public void setTxPpw(int txPpw) {
		this.txPpw = txPpw;
	}

	public String getTxClientBank() {
		return txClientBank;
	}

	public void setTxClientBank(String txClientBank) {
		this.txClientBank = txClientBank;
	}

	public String getTxMethod() {
		return txMethod;
	}

	public void setTxMethod(String txMethod) {
		this.txMethod = txMethod;
	}

	public String getTxAttClient() {
		return txAttClient;
	}

	public void setTxAttClient(String txAttClient) {
		this.txAttClient = txAttClient;
	}

	public Date getTxLostDate() {
		return txLostDate;
	}

	public void setTxLostDate(Date txLostDate) {
		this.txLostDate = txLostDate;
	}

	public Date getTxAdviceDate() {
		return txAdviceDate;
	}

	public void setTxAdviceDate(Date txAdviceDate) {
		this.txAdviceDate = txAdviceDate;
	}

	public String getTxUwReff() {
		return txUwReff;
	}

	public void setTxUwReff(String txUwReff) {
		this.txUwReff = txUwReff;
	}

	public String getTxCause() {
		return txCause;
	}

	public void setTxCause(String txCause) {
		this.txCause = txCause;
	}

	public String getTxDetails() {
		return txDetails;
	}

	public void setTxDetails(String txDetails) {
		this.txDetails = txDetails;
	}

	public int getTxMaxPaid() {
		return txMaxPaid;
	}

	public void setTxMaxPaid(int txMaxPaid) {
		this.txMaxPaid = txMaxPaid;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getModifyOn() {
		return modifyOn;
	}

	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getTxDataStatus() {
		return txDataStatus;
	}

	public void setTxDataStatus(String txDataStatus) {
		this.txDataStatus = txDataStatus;
	}

	public BigDecimal getTxClaimAmt() {
		return txClaimAmt;
	}

	public void setTxClaimAmt(BigDecimal txClaimAmt) {
		this.txClaimAmt = txClaimAmt;
	}

	public BigDecimal getTxDeducAmt() {
		return txDeducAmt;
	}

	public void setTxDeducAmt(BigDecimal txDeducAmt) {
		this.txDeducAmt = txDeducAmt;
	}

	public Date getTxDateEst() {
		return txDateEst;
	}

	public void setTxDateEst(Date txDateEst) {
		this.txDateEst = txDateEst;
	}

	public String getTxPayMtd() {
		return txPayMtd;
	}

	public void setTxPayMtd(String txPayMtd) {
		this.txPayMtd = txPayMtd;
	}

	public BigDecimal getTxApprAmt() {
		return txApprAmt;
	}

	public void setTxApprAmt(BigDecimal txApprAmt) {
		this.txApprAmt = txApprAmt;
	}

	public BigDecimal getTxClaimFee() {
		return txClaimFee;
	}

	public void setTxClaimFee(BigDecimal txClaimFee) {
		this.txClaimFee = txClaimFee;
	}

	public BigDecimal getTxAdjustFee() {
		return txAdjustFee;
	}

	public void setTxAdjustFee(BigDecimal txAdjustFee) {
		this.txAdjustFee = txAdjustFee;
	}

	public Date getTxDateSet() {
		return txDateSet;
	}

	public void setTxDateSet(Date txDateSet) {
		this.txDateSet = txDateSet;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getTxPartyName() {
		return txPartyName;
	}
	
	public void setTxPartyName(String txPartyName) {
		this.txPartyName = txPartyName;
	}

	public String getTxBrdxId() {
		return txBrdxId;
	}

	public void setTxBrdxId(String txBrdxId) {
		this.txBrdxId = txBrdxId;
	}

	public Long getRelatedIdKey() {
		return relatedIdKey;
	}

	public void setRelatedIdKey(Long relatedIdKey) {
		this.relatedIdKey = relatedIdKey;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getTxClientCode() {
		return txClientCode;
	}

	public void setTxClientCode(String txClientCode) {
		this.txClientCode = txClientCode;
	}
	
	public String getTxSumInsStr() {
		if(txSumIns == null)
			return null;
		
		return NumberFormat.getCurrencyInstance().format(txSumIns).replace("$", "");
	}

	public void setTxSumInsStr(String txSumInsStr) {
		this.txSumInsStr = txSumInsStr;
	}

	public String getPeriod1Start() {
		return period1Start;
	}

	public void setPeriod1Start(String period1Start) {
		this.period1Start = period1Start;
	}

	public String getPeriod1End() {
		return period1End;
	}

	public void setPeriod1End(String period1End) {
		this.period1End = period1End;
	}

	public String getPeriod2Start() {
		return period2Start;
	}

	public void setPeriod2Start(String period2Start) {
		this.period2Start = period2Start;
	}

	public String getPeriod2End() {
		return period2End;
	}

	public void setPeriod2End(String period2End) {
		this.period2End = period2End;
	}

	public String getTxDataStatusVal() {
		return txDataStatusVal;
	}

	public void setTxDataStatusVal(String txDataStatusVal) {
		this.txDataStatusVal = txDataStatusVal;
	}

	public String getTxLostDateStr() {
		return txLostDateStr;
	}

	public void setTxLostDateStr(String txLostDateStr) {
		this.txLostDateStr = txLostDateStr;
	}

	public String getTxAdviceDateStr() {
		return txAdviceDateStr;
	}

	public void setTxAdviceDateStr(String txAdviceDateStr) {
		this.txAdviceDateStr = txAdviceDateStr;
	}

	public BigDecimal getTxOrAmt() {
		return txOrAmt;
	}

	public void setTxOrAmt(BigDecimal txOrAmt) {
		this.txOrAmt = txOrAmt;
	}

	public BigDecimal getTxQsAmt() {
		return txQsAmt;
	}

	public void setTxQsAmt(BigDecimal txQsAmt) {
		this.txQsAmt = txQsAmt;
	}

	public BigDecimal getTxReinsNo() {
		return txReinsNo;
	}

	public void setTxReinsNo(BigDecimal txReinsNo) {
		this.txReinsNo = txReinsNo;
	}

	public BigDecimal getTxReinsCl() {
		return txReinsCl;
	}

	public void setTxReinsCl(BigDecimal txReinsCl) {
		this.txReinsCl = txReinsCl;
	}

	public BigDecimal getTxReinsUw() {
		return txReinsUw;
	}

	public void setTxReinsUw(BigDecimal txReinsUw) {
		this.txReinsUw = txReinsUw;
	}

	public BigDecimal getTxDcReal() {
		return txDcReal;
	}

	public void setTxDcReal(BigDecimal txDcReal) {
		this.txDcReal = txDcReal;
	}

	public BigDecimal getTxUrAmt() {
		return txUrAmt;
	}

	public void setTxUrAmt(BigDecimal txUrAmt) {
		this.txUrAmt = txUrAmt;
	}

	public BigDecimal getTxUwclAmt() {
		return txUwclAmt;
	}

	public void setTxUwclAmt(BigDecimal txUwclAmt) {
		this.txUwclAmt = txUwclAmt;
	}

	public BigDecimal getTxClclAmt() {
		return txClclAmt;
	}

	public void setTxClclAmt(BigDecimal txClclAmt) {
		this.txClclAmt = txClclAmt;
	}

	public BigDecimal getTxUwprAmt() {
		return txUwprAmt;
	}

	public void setTxUwprAmt(BigDecimal txUwprAmt) {
		this.txUwprAmt = txUwprAmt;
	}
	
}
