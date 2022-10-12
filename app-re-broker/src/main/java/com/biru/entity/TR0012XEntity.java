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
 * The persistent class for the TR0012 database table.
 * 
 */
@Entity
@Table(name="TR0012")
@NamedQuery(name="TR0012XEntity.findAll", query="SELECT t FROM TR0012XEntity t")
public class TR0012XEntity implements Serializable, Comparable<TR0012XEntity> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;

	@Column(name="TRX_TRX_CLASS")
	private String trxTrxClass;

	@Column(name="TRX_TYPE")
	private String trxType;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;

	@Column(name="TRX_SOURCE")
	private String trxSource = "0";

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DUE_DATE")
	private Date trxDueDate;

	@Column(name="TRX_METH_PAY")
	private String trxMethPay;

	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;

	@Column(name="TRX_COUNT_INV")
	private Integer trxCountInv;

	@Column(name="TRX_DATA_STATUS")
	private String trxDataStatus;

	@Column(name="TRX_OLD_TYPE")
	private String trxOldType;

	@Column(name="TRX_OLD_VOUCHER_ID")
	private String trxOldVoucherId;

	@Column(name="TRX_CLIENT")
	private String trxClient;

	@Column(name="TRX_DESCRIPTION")
	private String trxDescription;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;

	@Column(name="TRX_CURR_RATE")
	private BigDecimal trxCurrRate;

	@Column(name="TRX_ORG_AMOUNT")
	private BigDecimal trxOrgAmount;

	@Column(name="TRX_INT_AMOUNT")
	private BigDecimal trxIntAmount = BigDecimal.ZERO;

	@Column(name="TRX_INVC_AMOUNT")
	private BigDecimal trxInvcAmount = BigDecimal.ZERO;

	@Column(name="TRX_SET_AMOUNT")
	private BigDecimal trxSetAmount = BigDecimal.ZERO;

	@Column(name="TRX_COM_AMOUNT")
	private BigDecimal trxComAmount = BigDecimal.ZERO;

	@Column(name="TRX_PREMIUM")
	private BigDecimal trxPremium = BigDecimal.ZERO;

	@Column(name="TRX_DISC_AMOUNT")
	private BigDecimal trxDiscAmount = BigDecimal.ZERO;

	@Column(name="TRX_DEDUC_AMOUNT")
	private BigDecimal trxDeducAmount = BigDecimal.ZERO;

	@Column(name="TRX_BRKR_FEE")
	private BigDecimal trxBrkrFee = BigDecimal.ZERO;

	@Column(name="TRX_TAXIN_BF")
	private BigDecimal trxTaxinBf = BigDecimal.ZERO;

	@Column(name="TRX_TAXIN_CL")
	private BigDecimal trxTaxinCl = BigDecimal.ZERO;

	@Column(name="TRX_GROSS_AMOUNT")
	private BigDecimal trxGrossAmount = BigDecimal.ZERO;

	@Column(name="TRX_GROSS_BF")
	private BigDecimal trxGrossBf = BigDecimal.ZERO;

	@Column(name="TRX_COMO_AMOUNT")
	private BigDecimal trxComoAmount = BigDecimal.ZERO;

	@Column(name="TRX_OTHERS1_AMOUNT")
	private BigDecimal trxOthers1Amount = BigDecimal.ZERO;

	@Column(name="TRX_OTHERS2_AMOUNT")
	private BigDecimal trxOthers2Amount = BigDecimal.ZERO;

	@Column(name="TRX_OTHERS3_AMOUNT")
	private BigDecimal trxOthers3Amount = BigDecimal.ZERO;

	@Column(name="TRX_OTHERS4_AMOUNT")
	private BigDecimal trxOthers4Amount = BigDecimal.ZERO;

	@Column(name="TRX_NET_TOU")
	private BigDecimal trxNetTou = BigDecimal.ZERO;

	@Column(name="TRX_NET_TTL")
	private BigDecimal trxNetTtl = BigDecimal.ZERO;

	@Column(name="TRX_POL_AMOUNT")
	private BigDecimal trxPolAmount = BigDecimal.ZERO;

	@Column(name="TRX_SDUTY_AMOUNT")
	private BigDecimal trxSdutyAmount = BigDecimal.ZERO;

	@Column(name="TRX_OTHERS_AMOUNT")
	private BigDecimal trxOthersAmount = BigDecimal.ZERO;

	@Column(name="TRX_ADMIN_AMOUNT")
	private BigDecimal trxAdminAmount = BigDecimal.ZERO;

	@Column(name="TRX_BANK_AMOUNT")
	private BigDecimal trxBankAmount = BigDecimal.ZERO;

	@Column(name="TRX_INC_OTHERS")
	private BigDecimal trxIncOthers = BigDecimal.ZERO;

	@Column(name="TRX_TAXIN_OTH")
	private BigDecimal trxTaxinOth = BigDecimal.ZERO;

	@Column(name="TRX_WITH_AMOUNT")
	private BigDecimal trxWithAmount = BigDecimal.ZERO;

	@Column(name="TRX_REMARKS")
	private String trxRemarks;

	@Column(name="TRX_INS_OFFICER")
	private String trxInsOfficer = "";

	@Column(name="TRX_NX_TYPE")
	private String trxNxType;

	@Column(name="TRX_NX_VOUCHER_ID")
	private String trxNxVoucherId;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_SET_DATE")
	private Date trxSetDate;

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

	@Column(name="TRX_BRANCH")
	private String trxBranch;

	@Column(name="TRX_OFFICER")
	private String trxOfficer;

	@Transient
	private String cliName;

	@Transient
	private String action;

	@Transient
	private BigDecimal trxInvoiceAmount;

	@Transient
	private String trxDateStr;

	@Transient
	private String trxSetAmountStr;

	@Transient
	private String trxDueDateStr;

	@Transient
	private String trxInvcAmountStr;

	@Transient
	private String trxInvoiceAmountStr;

	@Transient
	private String trxInvcAmountNewStr = "0.00";

	@Transient
	private String trxDueDateNewStr = "";

	@Transient
	private String createOnStr;

	public TR0012XEntity(Long idKey, String trxDateStr, String trxType, String trxVoucherId, String cliName, String trxDescription,
                         String trxDataStatus, String trxCurrId, BigDecimal trxInvcAmount, BigDecimal trxSetAmount, BigDecimal trxInvoiceAmount,
                         String trxRemarks) {
		super();
		this.idKey = idKey;
		this.trxDateStr = trxDateStr;
		this.trxType = trxType;
		this.trxVoucherId = trxVoucherId;
		this.cliName = cliName;
		this.trxDescription = trxDescription;
		this.trxDataStatus = trxDataStatus;
		this.trxCurrId = trxCurrId;
		this.trxInvcAmount = trxInvcAmount;
		this.trxSetAmount = trxSetAmount;
		this.trxInvoiceAmount = trxInvoiceAmount;
		this.trxRemarks = trxRemarks;
	}

	public TR0012XEntity(Long idKey, String trxType, String trxVoucherId,
                         Integer trxCountInv, String trxClient, String cliName,
                         BigDecimal trxInvcAmount, Date trxDueDate) {
		super();
		this.idKey = idKey;
		this.trxType = trxType;
		this.trxVoucherId = trxVoucherId;
		this.trxCountInv = trxCountInv;
		this.trxClient = trxClient;
		this.cliName = cliName;
		this.trxInvcAmount = trxInvcAmount;
		this.trxDueDate = trxDueDate;
	}

	public TR0012XEntity(Long idKey, Date createOn, Date trxDate,
                         String trxVoucherId, String trxClient, String trxClienDesc,
                         String trxDescription, String trxCurrId, BigDecimal trxInvcAmount,
                         String trxDataStatus, String trxOldVoucherId, String trxSource) {
		super();
		this.idKey = idKey;
		this.createOn = createOn;
		this.trxDate = trxDate;
		this.trxVoucherId = trxVoucherId;
		this.trxClient = trxClient;
		this.cliName = trxClienDesc;
		this.trxDescription = trxDescription;
		this.trxCurrId = trxCurrId;
		this.trxInvcAmount = trxInvcAmount;
		this.trxDataStatus = trxDataStatus;
		this.trxOldVoucherId = trxOldVoucherId;
		this.trxSource = trxSource;
	}

	public TR0012XEntity() {
		super();
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getTrxTrxClass() {
		return trxTrxClass;
	}

	public void setTrxTrxClass(String trxTrxClass) {
		this.trxTrxClass = trxTrxClass;
	}

	public String getTrxType() {
		return trxType;
	}

	public void setTrxType(String trxType) {
		this.trxType = trxType;
	}

	public String getTrxVoucherId() {
		return trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public Date getTrxDueDate() {
		return trxDueDate;
	}

	public void setTrxDueDate(Date trxDueDate) {
		this.trxDueDate = trxDueDate;
	}

	public String getTrxMethPay() {
		return trxMethPay;
	}

	public void setTrxMethPay(String trxMethPay) {
		this.trxMethPay = trxMethPay;
	}

	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public Integer getTrxCountInv() {
		return trxCountInv;
	}

	public void setTrxCountInv(Integer trxCountInv) {
		this.trxCountInv = trxCountInv;
	}

	public String getTrxDataStatus() {
		return trxDataStatus;
	}

	public void setTrxDataStatus(String trxDataStatus) {
		this.trxDataStatus = trxDataStatus;
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

	public String getTrxClient() {
		return trxClient;
	}

	public void setTrxClient(String trxClient) {
		this.trxClient = trxClient;
	}

	public String getTrxDescription() {
		return trxDescription;
	}

	public void setTrxDescription(String trxDescription) {
		this.trxDescription = trxDescription;
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

	public BigDecimal getTrxOrgAmount() {
		return trxOrgAmount;
	}

	public void setTrxOrgAmount(BigDecimal trxOrgAmount) {
		this.trxOrgAmount = trxOrgAmount;
	}

	public BigDecimal getTrxIntAmount() {
		return trxIntAmount;
	}

	public void setTrxIntAmount(BigDecimal trxIntAmount) {
		this.trxIntAmount = trxIntAmount;
	}

	public BigDecimal getTrxInvcAmount() {
		return trxInvcAmount;
	}

	public void setTrxInvcAmount(BigDecimal trxInvcAmount) {
		this.trxInvcAmount = trxInvcAmount;
	}

	public BigDecimal getTrxSetAmount() {
		return trxSetAmount;
	}

	public void setTrxSetAmount(BigDecimal trxSetAmount) {
		this.trxSetAmount = trxSetAmount;
	}

	public BigDecimal getTrxComAmount() {
		return trxComAmount;
	}

	public void setTrxComAmount(BigDecimal trxComAmount) {
		this.trxComAmount = trxComAmount;
	}
	
	public BigDecimal getTrxPremium() {
		return trxPremium;
	}

	public void setTrxPremium(BigDecimal trxPremium) {
		this.trxPremium = trxPremium;
	}

	public BigDecimal getTrxBrkrFee() {
		return trxBrkrFee;
	}

	public void setTrxBrkrFee(BigDecimal trxBrkrFee) {
		this.trxBrkrFee = trxBrkrFee;
	}

	public BigDecimal getTrxDiscAmount() {
		return trxDiscAmount;
	}

	public void setTrxDiscAmount(BigDecimal trxDiscAmount) {
		this.trxDiscAmount = trxDiscAmount;
	}

	public BigDecimal getTrxDeducAmount() {
		return trxDeducAmount;
	}

	public void setTrxDeducAmount(BigDecimal trxDeducAmount) {
		this.trxDeducAmount = trxDeducAmount;
	}

	public BigDecimal getTrxTaxinBf() {
		return trxTaxinBf;
	}

	public void setTrxTaxinBf(BigDecimal trxTaxinBf) {
		this.trxTaxinBf = trxTaxinBf;
	}

	public BigDecimal getTrxTaxinCl() {
		return trxTaxinCl;
	}

	public void setTrxTaxinCl(BigDecimal trxTaxinCl) {
		this.trxTaxinCl = trxTaxinCl;
	}

	public BigDecimal getTrxGrossAmount() {
		return trxGrossAmount;
	}

	public void setTrxGrossAmount(BigDecimal trxGrossAmount) {
		this.trxGrossAmount = trxGrossAmount;
	}

	public BigDecimal getTrxGrossBf() {
		return trxGrossBf;
	}

	public BigDecimal getTrxComoAmount() {
		return trxComoAmount;
	}

	public void setTrxComoAmount(BigDecimal trxComoAmount) {
		this.trxComoAmount = trxComoAmount;
	}
	
	public BigDecimal getTrxOthers1Amount() {
		return trxOthers1Amount;
	}

	public void setTrxOthers1Amount(BigDecimal trxOthers1Amount) {
		this.trxOthers1Amount = trxOthers1Amount;
	}

	public BigDecimal getTrxOthers2Amount() {
		return trxOthers2Amount;
	}

	public void setTrxOthers2Amount(BigDecimal trxOthers2Amount) {
		this.trxOthers2Amount = trxOthers2Amount;
	}

	public BigDecimal getTrxOthers3Amount() {
		return trxOthers3Amount;
	}

	public void setTrxOthers3Amount(BigDecimal trxOthers3Amount) {
		this.trxOthers3Amount = trxOthers3Amount;
	}

	public BigDecimal getTrxOthers4Amount() {
		return trxOthers4Amount;
	}

	public void setTrxOthers4Amount(BigDecimal trxOthers4Amount) {
		this.trxOthers4Amount = trxOthers4Amount;
	}

	public void setTrxGrossBf(BigDecimal trxGrossBf) {
		this.trxGrossBf = trxGrossBf;
	}

	public BigDecimal getTrxNetTou() {
		return trxNetTou;
	}

	public void setTrxNetTou(BigDecimal trxNetTou) {
		this.trxNetTou = trxNetTou;
	}

	public BigDecimal getTrxNetTtl() {
		return trxNetTtl;
	}

	public void setTrxNetTtl(BigDecimal trxNetTtl) {
		this.trxNetTtl = trxNetTtl;
	}

	public BigDecimal getTrxPolAmount() {
		return trxPolAmount;
	}

	public void setTrxPolAmount(BigDecimal trxPolAmount) {
		this.trxPolAmount = trxPolAmount;
	}

	public BigDecimal getTrxSdutyAmount() {
		return trxSdutyAmount;
	}

	public void setTrxSdutyAmount(BigDecimal trxSdutyAmount) {
		this.trxSdutyAmount = trxSdutyAmount;
	}

	public BigDecimal getTrxOthersAmount() {
		return trxOthersAmount;
	}

	public void setTrxOthersAmount(BigDecimal trxOthersAmount) {
		this.trxOthersAmount = trxOthersAmount;
	}

	public BigDecimal getTrxIncOthers() {
		return trxIncOthers;
	}

	public void setTrxIncOthers(BigDecimal trxIncOthers) {
		this.trxIncOthers = trxIncOthers;
	}

	public BigDecimal getTrxTaxinOth() {
		return trxTaxinOth;
	}

	public void setTrxTaxinOth(BigDecimal trxTaxinOth) {
		this.trxTaxinOth = trxTaxinOth;
	}

	public BigDecimal getTrxWithAmount() {
		return trxWithAmount;
	}

	public void setTrxWithAmount(BigDecimal trxWithAmount) {
		this.trxWithAmount = trxWithAmount;
	}

	public String getTrxRemarks() {
		return trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}

	public BigDecimal getTrxAdminAmount() {
		return trxAdminAmount;
	}

	public void setTrxAdminAmount(BigDecimal trxAdminAmount) {
		this.trxAdminAmount = trxAdminAmount;
	}

	public BigDecimal getTrxBankAmount() {
		return trxBankAmount;
	}

	public void setTrxBankAmount(BigDecimal trxBankAmount) {
		this.trxBankAmount = trxBankAmount;
	}

	public String getTrxInsOfficer() {
		return trxInsOfficer;
	}

	public void setTrxInsOfficer(String trxInsOfficer) {
		this.trxInsOfficer = trxInsOfficer;
	}
	
	public String getTrxNxType() {
		return trxNxType;
	}

	public void setTrxNxType(String trxNxType) {
		this.trxNxType = trxNxType;
	}

	public String getTrxNxVoucherId() {
		return trxNxVoucherId;
	}

	public void setTrxNxVoucherId(String trxNxVoucherId) {
		this.trxNxVoucherId = trxNxVoucherId;
	}
	
	public Date getTrxSetDate() {
		return trxSetDate;
	}

	public void setTrxSetDate(Date trxSetDate) {
		this.trxSetDate = trxSetDate;
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
	
	public String getTrxBranch() {
		return trxBranch;
	}

	public void setTrxBranch(String trxBranch) {
		this.trxBranch = trxBranch;
	}

	public String getTrxOfficer() {
		return trxOfficer;
	}

	public void setTrxOfficer(String trxOfficer) {
		this.trxOfficer = trxOfficer;
	}

	public String getCliName() {
		return cliName;
	}

	public void setCliName(String cliName) {
		this.cliName = cliName;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public BigDecimal getTrxInvoiceAmount() {
		return trxInvoiceAmount;
	}

	public void setTrxInvoiceAmount(BigDecimal trxInvoiceAmount) {
		this.trxInvoiceAmount = trxInvoiceAmount;
	}
	
	public String getTrxDateStr() {
		if(trxDateStr != null)
			return trxDateStr;
		if(trxDate == null)
			return null;
		
		return new SimpleDateFormat("dd/MM/yyyy").format(getTrxDate());
	}

	public void setTrxDateStr(String trxDateStr) {
		this.trxDateStr = trxDateStr;
	}
	
	public String getTrxSetAmountStr() {
		if(trxSetAmount == null)
			return null;
		
		return NumberFormat.getCurrencyInstance().format(trxSetAmount).replace("$", "");
	}

	public void setTrxSetAmountStr(String trxSetAmountStr) {
		this.trxSetAmountStr = trxSetAmountStr;
	}
	
	public String getTrxInvcAmountStr() {
		if(trxInvcAmount == null)
			return null;
		
		return NumberFormat.getCurrencyInstance().format(getTrxInvcAmount()).replace("$", "");
	}

	public void setTrxInvcAmountStr(String trxInvcAmountStr) {
		this.trxInvcAmountStr = trxInvcAmountStr;
	}
	
	public String getTrxInvoiceAmountStr() {
		if(trxInvoiceAmount == null)
			return null;
		
		return NumberFormat.getCurrencyInstance().format(trxInvoiceAmount).replace("$", "");
	}

	public void setTrxInvoiceAmountStr(String trxInvoiceAmountStr) {
		this.trxInvoiceAmountStr = trxInvoiceAmountStr;
	}

	public void setTrxDueDateStr(String trxDueDateStr) {
		this.trxDueDateStr = trxDueDateStr;
	}
	
	public String getTrxDueDateStr() {
		if(trxDueDate == null)
			return null;
		
		return new SimpleDateFormat("dd/MM/yyyy").format(getTrxDueDate());
	}

	public String getTrxSource() {
		return trxSource;
	}

	public void setTrxSource(String trxSource) {
		this.trxSource = trxSource;
	}
	
	public String getTrxInvcAmountNewStr() {
		return trxInvcAmountNewStr;
	}

	public void setTrxInvcAmountNewStr(String trxInvcAmountNewStr) {
		this.trxInvcAmountNewStr = trxInvcAmountNewStr;
	}

	public String getTrxDueDateNewStr() {
		return trxDueDateNewStr;
	}

	public void setTrxDueDateNewStr(String trxDueDateNewStr) {
		this.trxDueDateNewStr = trxDueDateNewStr;
	}
	
	public String getCreateOnStr() {
		if(createOnStr != null)
			return createOnStr;
		if(createOn == null)
			return null;
			
		return new SimpleDateFormat("dd/MM/yyyy").format(getCreateOn());
	}

	public void setCreateOnStr(String createOnStr) {
		this.createOnStr = createOnStr;
	}

	@Override
	public int compareTo(TR0012XEntity tR0012Entity) {
		return (this.getTrxVoucherId().compareTo(tR0012Entity.getTrxVoucherId()) == -1 ? -1 : 
            (this.getTrxVoucherId() == tR0012Entity.getTrxVoucherId() ? 0 : 1));
	}

	@Override
	public String toString() {
		return "TR0012Entity [idKey=" + idKey + ", trxTrxClass=" + trxTrxClass + ", trxType=" + trxType
				+ ", trxVoucherId=" + trxVoucherId + ", trxDate=" + trxDate + ", trxDueDate=" + trxDueDate
				+ ", trxMethPay=" + trxMethPay + ", trxCoverCode=" + trxCoverCode + ", trxCountInv=" + trxCountInv
				+ ", trxDataStatus=" + trxDataStatus + ", trxOldType=" + trxOldType + ", trxOldVoucherId="
				+ trxOldVoucherId + ", trxClient=" + trxClient + ", trxDescription=" + trxDescription + ", trxCurrId="
				+ trxCurrId + ", trxCurrRate=" + trxCurrRate + ", trxOrgAmount=" + trxOrgAmount + ", trxIntAmount="
				+ trxIntAmount + ", trxInvcAmount=" + trxInvcAmount + ", trxSetAmount=" + trxSetAmount
				+ ", trxComAmount=" + trxComAmount + ", trxPremium=" + trxPremium + ", trxBrkrFee=" + trxBrkrFee
				+ ", trxDiscAmount=" + trxDiscAmount + ", trxDeducAmount=" + trxDeducAmount + ", trxTaxinBf="
				+ trxTaxinBf + ", trxTaxinCl=" + trxTaxinCl + ", trxGrossAmount=" + trxGrossAmount + ", trxGrossBf="
				+ trxGrossBf + ", trxComoAmount=" + trxComoAmount + ", trxOthers1Amount=" + trxOthers1Amount
				+ ", trxOthers2Amount=" + trxOthers2Amount + ", trxOthers3Amount=" + trxOthers3Amount
				+ ", trxOthers4Amount=" + trxOthers4Amount + ", trxNetTou=" + trxNetTou + ", trxNetTtl=" + trxNetTtl
				+ ", trxPolAmount=" + trxPolAmount + ", trxSdutyAmount=" + trxSdutyAmount + ", trxOthersAmount="
				+ trxOthersAmount + ", trxAdminAmount=" + trxAdminAmount + ", trxBankAmount=" + trxBankAmount
				+ ", trxIncOthers=" + trxIncOthers + ", trxTaxinOth=" + trxTaxinOth + ", trxWithAmount=" + trxWithAmount
				+ ", trxRemarks=" + trxRemarks + ", trxInsOfficer=" + trxInsOfficer + ", trxNxType=" + trxNxType
				+ ", trxNxVoucherId=" + trxNxVoucherId + ", trxSetDate=" + trxSetDate + ", createBy=" + createBy
				+ ", createOn=" + createOn + ", modifyBy=" + modifyBy + ", modifyOn=" + modifyOn + ", trxBranch=" + trxBranch + ", trxSource="
				+ trxSource + ", cliName=" + cliName + ", action=" + action + ", trxInvoiceAmount=" + trxInvoiceAmount
				+ ", trxDateStr=" + trxDateStr + ", trxSetAmountStr=" + trxSetAmountStr + ", trxDueDateStr="
				+ trxDueDateStr + ", trxInvcAmountStr=" + trxInvcAmountStr + ", trxInvoiceAmountStr="
				+ trxInvoiceAmountStr + ", trxInvcAmountNewStr=" + trxInvcAmountNewStr + ", trxDueDateNewStr="
				+ trxDueDateNewStr + "]";
	}
	
	
	
}