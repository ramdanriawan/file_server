package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.math.BigInteger;


/**
 * The persistent class for the report_production database table.
 * 
 */
@Entity
@Table(name="REPORT_PRODUCTION")
@NamedQuery(name="ReportProductionEntity.findAll", query="SELECT r FROM ReportProductionEntity r")
public class ReportProductionEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_KEY")
	private String idKey;
	
	@Column(name="ROW_NUMBER")
	private Integer rowNumber;
	
	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_OLD_VOUCHER_ID")
	private String trxOldVoucherId;
	
	@Column(name="TRX_OLD_TYPE")
	private String trxOldType;
	
	@Column(name="CLI_NAME_SE")
	private String cliNameSe;
	
	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;
	
	@Column(name="TC_OJK_GROUP")
	private String tcOjkGroup;
	
	@Column(name="TRX_TRX_CLASS")
	private String trxTrxClass;
	
	@Column(name="TRX_SOURCE")
	private String trxSource;
	
	@Lob
	@Column(name="TRX_INSURED_NAME")
	private String trxInsuredName;
	
	@Column(name="TRX_DESCRIPTION")
	private String trxDescription;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_START")
	private Date trxInsStart;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_END")
	private Date trxInsEnd;
	
	@Column(name="TRX_COUNT_INV_SE")
	private int trxCountInvSe;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DUE_DATE_SE")
	private Date trxDueDateSe;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;
	
	@Column(name="TRX_SHARE")
	private BigDecimal trxShare = BigDecimal.ZERO;
	
	@Column(name="TRX_TSI_AMOUNT")
	private BigDecimal trxTsiAmount = BigDecimal.ZERO;
	
	@Column(name="TRX_ORG_AMOUNT_SE")
	private BigDecimal trxOrgAmountSe= BigDecimal.ZERO;
	
	@Column(name="TRX_BRKR_FEE")
	private BigDecimal trxBrkrFee = BigDecimal.ZERO;
	
	@Column(name="TRX_TAXIN_BF")
	private BigDecimal trxTaxinBf = BigDecimal.ZERO;
	
	@Column(name="TRX_ORG_AMOUNT_PU")
	private BigDecimal trxOrgAmountPu = BigDecimal.ZERO;
	
	@Column(name="TRX_CURR_RATE")
	private BigDecimal trxCurrRate = BigDecimal.ZERO;

	@Column(name="PREMIUM_IDR")
	private BigDecimal premiumIdr = BigDecimal.ZERO;

	@Column(name="RI_COMM_IDR")
	private BigDecimal riCommIdr = BigDecimal.ZERO;
	
	@Column(name="VAT_IDR")
	private BigDecimal vatIdr = BigDecimal.ZERO;
	
	@Column(name="UNDERWRITE_IDR")
	private BigDecimal underwriteIdr = BigDecimal.ZERO;
	
	@Column(name="CLI_NAME_PU")
	private String cliNamePu;
	
	private String di;

	@Column(name="TRX_INS_SHARE")
	private BigDecimal trxInsShare = BigDecimal.ZERO;
	
	@Column(name="TRX_COUNT_INV_PU")
	private int trxCountInvPu;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DUE_DATE_PU")
	private Date trxDueDatePu;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CLOSING_DATE")
	private Date closingDate;
	
	@Column(name="DC_NOTE_1")
	private String dcNote1;
	
	@Column(name="DC_NOTE_2")
	private String dcNote2;
	
	@Column(name="TRX_ADMIN_AMOUNT")
	private BigDecimal trxAdminAmount = BigDecimal.ZERO;
	
	@Column(name="TRX_OTHERS")
	private BigDecimal trxOthers = BigDecimal.ZERO;
	
	@Column(name="ADMIN_IDR")
	private BigDecimal adminIdr = BigDecimal.ZERO;
	
	@Column(name="OTHERS_IDR")
	private BigDecimal othersIdr = BigDecimal.ZERO;
	
	@Column(name="POLICY_NO")
	private String policyNo;
	
	public ReportProductionEntity() {
	}

	public Integer getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(Integer rowNumber) {
		this.rowNumber = rowNumber;
	}



	public String getCliNamePu() {
		return this.cliNamePu;
	}

	public void setCliNamePu(String cliNamePu) {
		this.cliNamePu = cliNamePu;
	}

	public String getCliNameSe() {
		return this.cliNameSe;
	}

	public void setCliNameSe(String cliNameSe) {
		this.cliNameSe = cliNameSe;
	}

	public Date getClosingDate() {
		return this.closingDate;
	}

	public void setClosingDate(Date closingDate) {
		this.closingDate = closingDate;
	}

	public String getDi() {
		return this.di;
	}

	public void setDi(String di) {
		this.di = di;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public BigDecimal getPremiumIdr() {
		return this.premiumIdr;
	}

	public void setPremiumIdr(BigDecimal premiumIdr) {
		this.premiumIdr = premiumIdr;
	}

	public BigDecimal getRiCommIdr() {
		return this.riCommIdr;
	}

	public void setRiCommIdr(BigDecimal riCommIdr) {
		this.riCommIdr = riCommIdr;
	}

	public String getTcOjkGroup() {
		return this.tcOjkGroup;
	}

	public void setTcOjkGroup(String tcOjkGroup) {
		this.tcOjkGroup = tcOjkGroup;
	}

	public BigDecimal getTrxBrkrFee() {
		return this.trxBrkrFee;
	}

	public void setTrxBrkrFee(BigDecimal trxBrkrFee) {
		this.trxBrkrFee = trxBrkrFee;
	}


	public int getTrxCountInvPu() {
		return this.trxCountInvPu;
	}

	public void setTrxCountInvPu(int trxCountInvPu) {
		this.trxCountInvPu = trxCountInvPu;
	}

	public int getTrxCountInvSe() {
		return this.trxCountInvSe;
	}

	public void setTrxCountInvSe(int trxCountInvSe) {
		this.trxCountInvSe = trxCountInvSe;
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

	public Date getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public String getTrxDescription() {
		return this.trxDescription;
	}

	public void setTrxDescription(String trxDescription) {
		this.trxDescription = trxDescription;
	}

	public Date getTrxDueDatePu() {
		return this.trxDueDatePu;
	}

	public void setTrxDueDatePu(Date trxDueDatePu) {
		this.trxDueDatePu = trxDueDatePu;
	}

	public Date getTrxDueDateSe() {
		return this.trxDueDateSe;
	}

	public void setTrxDueDateSe(Date trxDueDateSe) {
		this.trxDueDateSe = trxDueDateSe;
	}

	public Date getTrxInsEnd() {
		return this.trxInsEnd;
	}

	public void setTrxInsEnd(Date trxInsEnd) {
		this.trxInsEnd = trxInsEnd;
	}

	public BigDecimal getTrxInsShare() {
		return this.trxInsShare;
	}

	public void setTrxInsShare(BigDecimal trxInsShare) {
		this.trxInsShare = trxInsShare;
	}

	public Date getTrxInsStart() {
		return this.trxInsStart;
	}

	public void setTrxInsStart(Date trxInsStart) {
		this.trxInsStart = trxInsStart;
	}

	public String getTrxInsuredName() {
		return this.trxInsuredName;
	}

	public void setTrxInsuredName(String trxInsuredName) {
		this.trxInsuredName = trxInsuredName;
	}

	public String getTrxOldType() {
		return this.trxOldType;
	}

	public void setTrxOldType(String trxOldType) {
		this.trxOldType = trxOldType;
	}

	public String getTrxOldVoucherId() {
		return this.trxOldVoucherId;
	}

	public void setTrxOldVoucherId(String trxOldVoucherId) {
		this.trxOldVoucherId = trxOldVoucherId;
	}

	public BigDecimal getTrxOrgAmountPu() {
		return this.trxOrgAmountPu;
	}

	public void setTrxOrgAmountPu(BigDecimal trxOrgAmountPu) {
		this.trxOrgAmountPu = trxOrgAmountPu;
	}

	public BigDecimal getTrxOrgAmountSe() {
		return this.trxOrgAmountSe;
	}

	public void setTrxOrgAmountSe(BigDecimal trxOrgAmountSe) {
		this.trxOrgAmountSe = trxOrgAmountSe;
	}

	public BigDecimal getTrxShare() {
		return this.trxShare;
	}

	public void setTrxShare(BigDecimal trxShare) {
		this.trxShare = trxShare;
	}

	public String getTrxSource() {
		return this.trxSource;
	}

	public void setTrxSource(String trxSource) {
		this.trxSource = trxSource;
	}

	public BigDecimal getTrxTaxinBf() {
		return this.trxTaxinBf;
	}

	public void setTrxTaxinBf(BigDecimal trxTaxinBf) {
		this.trxTaxinBf = trxTaxinBf;
	}

	public String getTrxTrxClass() {
		return this.trxTrxClass;
	}

	public void setTrxTrxClass(String trxTrxClass) {
		this.trxTrxClass = trxTrxClass;
	}

	public BigDecimal getTrxTsiAmount() {
		return this.trxTsiAmount;
	}

	public void setTrxTsiAmount(BigDecimal trxTsiAmount) {
		this.trxTsiAmount = trxTsiAmount;
	}

	public BigDecimal getUnderwriteIdr() {
		return this.underwriteIdr;
	}

	public void setUnderwriteIdr(BigDecimal underwriteIdr) {
		this.underwriteIdr = underwriteIdr;
	}

	public BigDecimal getVatIdr() {
		return this.vatIdr;
	}

	public void setVatIdr(BigDecimal vatIdr) {
		this.vatIdr = vatIdr;
	}
	
	public String getTrxVoucherId() {
		return trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public String getDcNote1() {
		return dcNote1;
	}

	public void setDcNote1(String dcNote1) {
		this.dcNote1 = dcNote1;
	}

	public String getDcNote2() {
		return dcNote2;
	}

	public void setDcNote2(String dcNote2) {
		this.dcNote2 = dcNote2;
	}
	
	public BigDecimal getTrxAdminAmount() {
		return trxAdminAmount;
	}

	public void setTrxAdminAmount(BigDecimal trxAdminAmount) {
		this.trxAdminAmount = trxAdminAmount;
	}

	public BigDecimal getTrxOthers() {
		return trxOthers;
	}

	public void setTrxOthers(BigDecimal trxOthers) {
		this.trxOthers = trxOthers;
	}

	public BigDecimal getAdminIdr() {
		return adminIdr;
	}

	public void setAdminIdr(BigDecimal adminIdr) {
		this.adminIdr = adminIdr;
	}

	public BigDecimal getOthersIdr() {
		return othersIdr;
	}

	public void setOthersIdr(BigDecimal othersIdr) {
		this.othersIdr = othersIdr;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public ReportProductionEntity(String cliNamePu, 
			String cliNameSe, 
			Date closingDate, 
			String di,
			String idKey,  
			BigDecimal premiumIdr, 
			BigDecimal riCommIdr, 
			String tcOjkGroup,
			BigDecimal trxBrkrFee,  
			int trxCountInvPu, 
			int trxCountInvSe, 
			String trxCoverCode,
			String trxCurrId, 
			BigDecimal trxCurrRate, 
			Date trxDate, 
			String trxDescription, 
			Date trxDueDatePu,
			Date trxDueDateSe, 
			Date trxInsEnd, 
			BigDecimal trxInsShare, 
			Date trxInsStart, 
			String trxInsuredName,
			String trxOldType, 
			String trxOldVoucherId, 
			BigDecimal trxOrgAmountPu, 
			BigDecimal trxOrgAmountSe,
			BigDecimal trxShare, 
			String trxSource, 
			BigDecimal trxTaxinBf, 
			String trxTrxClass, 
			BigDecimal trxTsiAmount,
			BigDecimal underwriteIdr, 
			BigDecimal vatIdr,
			String trxVoucherId,
			String dcNote1,
			String dcNote2,
			BigDecimal trxAdminAmount,
			BigDecimal trxOthers,
			BigDecimal adminIdr,
			BigDecimal othersIdr,
			String policyNo) {
		super();
		this.cliNamePu = cliNamePu;
		this.cliNameSe = cliNameSe;
		this.closingDate = closingDate;
		this.di = di;
		this.idKey = idKey;
		this.premiumIdr = premiumIdr;
		this.riCommIdr = riCommIdr;
		this.tcOjkGroup = tcOjkGroup;
		this.trxBrkrFee = trxBrkrFee;
		this.trxCountInvPu = trxCountInvPu;
		this.trxCountInvSe = trxCountInvSe;
		this.trxCoverCode = trxCoverCode;
		this.trxCurrId = trxCurrId;
		this.trxCurrRate = trxCurrRate;
		this.trxDate = trxDate;
		this.trxDescription = trxDescription;
		this.trxDueDatePu = trxDueDatePu;
		this.trxDueDateSe = trxDueDateSe;
		this.trxInsEnd = trxInsEnd;
		this.trxInsShare = trxInsShare;
		this.trxInsStart = trxInsStart;
		this.trxInsuredName = trxInsuredName;
		this.trxOldType = trxOldType;
		this.trxOldVoucherId = trxOldVoucherId;
		this.trxOrgAmountPu = trxOrgAmountPu;
		this.trxOrgAmountSe = trxOrgAmountSe;
		this.trxShare = trxShare;
		this.trxSource = trxSource;
		this.trxTaxinBf = trxTaxinBf;
		this.trxTrxClass = trxTrxClass;
		this.trxTsiAmount = trxTsiAmount;
		this.underwriteIdr = underwriteIdr;
		this.vatIdr = vatIdr;
		this.trxVoucherId = trxVoucherId;
		this.dcNote1 = dcNote1;
		this.dcNote2 = dcNote1;
		this.trxAdminAmount = trxAdminAmount;
		this.trxOthers = trxOthers;
		this.adminIdr = adminIdr;
		this.othersIdr = othersIdr;
		this.policyNo = policyNo;
	}

	public ReportProductionEntity (ReportProductionEntity rpe) {
		this (rpe.getCliNamePu(),
		rpe.getCliNameSe(),
		rpe.getClosingDate(),
		rpe.getDi(),
		rpe.getIdKey(),
		rpe.getPremiumIdr(),
		rpe.getRiCommIdr(),
		rpe.getTcOjkGroup(),
		rpe.getTrxBrkrFee(),
		rpe.getTrxCountInvPu(),
		rpe.getTrxCountInvSe(),
		rpe.getTrxCoverCode(),
		rpe.getTrxCurrId(),
		rpe.getTrxCurrRate(),
		rpe.getTrxDate(),
		rpe.getTrxDescription(),
		rpe.getTrxDueDatePu(),
		rpe.getTrxDueDateSe(),
		rpe.getTrxInsEnd(),
		rpe.getTrxInsShare(),
		rpe.getTrxInsStart(),
		rpe.getTrxInsuredName(),
		rpe.getTrxOldType(),
		rpe.getTrxOldVoucherId(),
		rpe.getTrxOrgAmountPu(),
		rpe.getTrxOrgAmountSe(),
		rpe.getTrxShare(),
		rpe.getTrxSource(),
		rpe.getTrxTaxinBf(),
		rpe.getTrxTrxClass(),
		rpe.getTrxTsiAmount(),
		rpe.getUnderwriteIdr(),
		rpe.getVatIdr(),
		rpe.getTrxVoucherId(),
		rpe.getDcNote1(),
		rpe.getDcNote2(),
		rpe.getTrxAdminAmount(),
		rpe.getTrxOthers(),
		rpe.getAdminIdr(),
		rpe.getOthersIdr(),
		rpe.getPolicyNo());
	}
//	@Override
//	public Object clone() {
//	    try {
//	        return (ReportProductionEntity) super.clone();
//	    } catch (CloneNotSupportedException e) {
//	    	return new ReportProductionEntity();
//	    }
//	}
}