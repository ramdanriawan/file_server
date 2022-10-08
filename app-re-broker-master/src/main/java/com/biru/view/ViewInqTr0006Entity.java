package com.biru.view;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * The persistent class for the view_inq_tr0006 database table.
 * 
 */
/**
 * @author piyaneo
 *
 */
@Entity
@Table(name="VIEW_INQ_TR0006")
@NamedQuery(name="ViewInqTr0006Entity.findAll", query="SELECT v FROM ViewInqTr0006Entity v")
public class ViewInqTr0006Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ROW_NUMBER")
	private String rowNumber;
	
	@Column(name="ID_KEY")
	private String idKey;
	
	@Column(name="CLI_NAME")
	private String cliName;
	
	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;

	@Column(name="TRX_CURR_ID")
	private String trxCurrId;

	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;

	@Column(name="TRX_INSURED_NAME")
	private String trxInsuredName;

	@Column(name="TRX_TSI_AMOUNT")
	private BigDecimal trxTsiAmount;

	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_REMARKS")
	private String trxRemarks;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_START")
	private Date trxInsStart;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_INS_END")
	private Date trxInsEnd;
	
	@Column(name="SA_NAME")
	private String saName;
	
	@Column(name="TRX_SOURCE")
	private String trxSource;
	
	@Column(name="TR6_VOUCHER_ID")
	private String tr6VoucherId;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="TR12_CREATE_ON")
	private Date tr12CreateOn;
	
	@Transient
	private String action;
	
	@Transient
	private String trxDateStr;
	
	@Transient
	private String trxTsiAmountStr;
	
	@Transient
	private String trxInsStartStr;
	
	@Transient
	private String trxInsEndStr;
	
	@Transient
	private String createOnStr;
	
	@Transient
	private String sumTrxPremiumStr;
	
	@Transient
	private String sumTrxOthers1AmtStr;
	
	@Transient
	private String sumTrxPolAmtStr;
	
	@Transient
	private String sumTrxOthers2AmtStr;
	
	@Transient
	private String sumTrxSdutyAmtStr;
	
	@Transient
	private String sumTrxOthers3AmtStr;
	
	@Transient
	private String sumTrxAdminStr;
	
	@Transient
	private String sumTrxComAmtStr;
	
	@Transient
	private String sumTrxBankAmtStr;
	
	@Transient
	private String sumTrxDiscAmtStr;
	
	@Transient
	private String sumTrxInvcAmtStr;
	
	@Transient
	private String sumTrxInvcAmtNetToPayStr;
	
	@Transient
	private String sumTrxBrkrFeeStr;
	
	@Transient
	private String sumOthersStr;
	
	@Transient
	private String sumTrxTaxinBfStr;
	
	@Transient
	private String sumTrxComoAmtStr;
	
	@Transient
	private String sumTrxWithAmtStr;
	
	@Transient
	private String sumTrxOthers4AmtStr;
	
	@Transient
	private String branch;
	
	public ViewInqTr0006Entity() {
	}

	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getTrxTrxId() {
		return trxTrxId;
	}

	public void setTrxTrxId(String trxTrxId) {
		this.trxTrxId = trxTrxId;
	}

	public void setTrxInsStart(Date trxInsStart) {
		this.trxInsStart = trxInsStart;
	}

	public void setTrxInsEndStr(String trxInsEndStr) {
		this.trxInsEndStr = trxInsEndStr;
	}

	public String getCliName() {
		return this.cliName;
	}

	public void setCliName(String cliName) {
		this.cliName = cliName;
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

	public Date getTrxDate() {
		return this.trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public String getTrxInsuredName() {
		return this.trxInsuredName;
	}

	public void setTrxInsuredName(String trxInsuredName) {
		this.trxInsuredName = trxInsuredName;
	}

	public BigDecimal getTrxTsiAmount() {
		return this.trxTsiAmount;
	}

	public void setTrxTsiAmount(BigDecimal trxTsiAmount) {
		this.trxTsiAmount = trxTsiAmount;
	}

	public String getTrxVoucherId() {
		return this.trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTrxDateStr() {
		return new SimpleDateFormat("dd/MM/yyyy").format(getTrxDate());
	}

	public void setTrxDateStr(String trxDateStr) {
		this.trxDateStr = trxDateStr;
	}

	public String getTrxTsiAmountStr() {
		return NumberFormat.getCurrencyInstance().format(trxTsiAmount).replace("$", "");
	}

	public void setTrxTsiAmountStr(String trxTsiAmountStr) {
		this.trxTsiAmountStr = trxTsiAmountStr;
	}

	public String getTrxRemarks() {
		return trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}

	public Date getTrxInsStart() {
		return trxInsStart;
	}

	public void setTrxReinsStart(Date trxInsStart) {
		this.trxInsStart = trxInsStart;
	}

	public Date getTrxInsEnd() {
		return trxInsEnd;
	}

	public void setTrxInsEnd(Date trxInsEnd) {
		this.trxInsEnd = trxInsEnd;
	}

	public String getTrxInsStartStr() {
		if(trxInsStart != null) {
			return new SimpleDateFormat("dd/MM/yyy").format(trxInsStart);
		}
		return null;
	}

	public void setTrxInsStartStr(String trxInsStartStr) {
		this.trxInsStartStr = trxInsStartStr;
	}

	public String getTrxInsEndStr() {
		if(trxInsEnd != null) {
			return new SimpleDateFormat("dd/MM/yyy").format(trxInsEnd);
		}
		return null;
	}

	public void setTrxReinsEndStr(String trxReinsEndStr) {
		this.trxInsEndStr = trxReinsEndStr;
	}

	public String getSaName() {
		return saName;
	}

	public void setSaName(String saName) {
		this.saName = saName;
	}

	public String getTrxSource() {
		return trxSource;
	}

	public void setTrxSource(String trxSource) {
		this.trxSource = trxSource;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCreateOnStr() {
		if(createOn != null) {
			return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(createOn);
		}
		return null;
	}

	public void setCreateOnStr(String createOnStr) {
		this.createOnStr = createOnStr;
	}

	public Date getTr12CreateOn() {
		return tr12CreateOn;
	}

	public void setTr12CreateOn(Date tr12CreateOn) {
		this.tr12CreateOn = tr12CreateOn;
	}

	public String getRowNumber() {
		return rowNumber;
	}

	public void setRowNumber(String rowNumber) {
		this.rowNumber = rowNumber;
	}

	public String getTr6VoucherId() {
		return tr6VoucherId;
	}

	public void setTr6VoucherId(String tr6VoucherId) {
		this.tr6VoucherId = tr6VoucherId;
	}

	public String getSumTrxPremium() {
		return sumTrxPremiumStr;
	}

	public void setSumTrxPremium(String sumTrxPremiumStr) {
		this.sumTrxPremiumStr = sumTrxPremiumStr;
	}

	public String getSumTrxPremiumStr() {
		return sumTrxPremiumStr;
	}

	public void setSumTrxPremiumStr(String sumTrxPremiumStr) {
		this.sumTrxPremiumStr = sumTrxPremiumStr;
	}

	public String getSumTrxOthers1AmtStr() {
		return sumTrxOthers1AmtStr;
	}

	public void setSumTrxOthers1AmtStr(String sumTrxOthers1AmtStr) {
		this.sumTrxOthers1AmtStr = sumTrxOthers1AmtStr;
	}

	public String getSumTrxPolAmtStr() {
		return sumTrxPolAmtStr;
	}

	public void setSumTrxPolAmtStr(String sumTrxPolAmtStr) {
		this.sumTrxPolAmtStr = sumTrxPolAmtStr;
	}

	public String getSumTrxOthers2AmtStr() {
		return sumTrxOthers2AmtStr;
	}

	public void setSumTrxOthers2AmtStr(String sumTrxOthers2AmtStr) {
		this.sumTrxOthers2AmtStr = sumTrxOthers2AmtStr;
	}

	public String getSumTrxSdutyAmtStr() {
		return sumTrxSdutyAmtStr;
	}

	public void setSumTrxSdutyAmtStr(String sumTrxSdutyAmtStr) {
		this.sumTrxSdutyAmtStr = sumTrxSdutyAmtStr;
	}

	public String getSumTrxOthers3AmtStr() {
		return sumTrxOthers3AmtStr;
	}

	public void setSumTrxOthers3AmtStr(String sumTrxOthers3AmtStr) {
		this.sumTrxOthers3AmtStr = sumTrxOthers3AmtStr;
	}

	public String getSumTrxAdminStr() {
		return sumTrxAdminStr;
	}

	public void setSumTrxAdminStr(String sumTrxAdminStr) {
		this.sumTrxAdminStr = sumTrxAdminStr;
	}

	public String getSumTrxComAmtStr() {
		return sumTrxComAmtStr;
	}

	public void setSumTrxComAmtStr(String sumTrxComAmtStr) {
		this.sumTrxComAmtStr = sumTrxComAmtStr;
	}

	public String getSumTrxBankAmtStr() {
		return sumTrxBankAmtStr;
	}

	public void setSumTrxBankAmtStr(String sumTrxBankAmtStr) {
		this.sumTrxBankAmtStr = sumTrxBankAmtStr;
	}

	public String getSumTrxDiscAmtStr() {
		return sumTrxDiscAmtStr;
	}

	public void setSumTrxDiscAmtStr(String sumTrxDiscAmtStr) {
		this.sumTrxDiscAmtStr = sumTrxDiscAmtStr;
	}

	public String getSumTrxInvcAmtStr() {
		return sumTrxInvcAmtStr;
	}

	public void setSumTrxInvcAmtStr(String sumTrxInvcAmtStr) {
		this.sumTrxInvcAmtStr = sumTrxInvcAmtStr;
	}
	
	public String getSumTrxInvcAmtNetToPayStr() {
		return sumTrxInvcAmtNetToPayStr;
	}

	public void setSumTrxInvcAmtNetToPayStr(String sumTrxInvcAmtNetToPayStr) {
		this.sumTrxInvcAmtNetToPayStr = sumTrxInvcAmtNetToPayStr;
	}

	public String getSumTrxBrkrFeeStr() {
		return sumTrxBrkrFeeStr;
	}

	public void setSumTrxBrkrFeeStr(String sumTrxBrkrFeeStr) {
		this.sumTrxBrkrFeeStr = sumTrxBrkrFeeStr;
	}

	public String getSumOthersStr() {
		return sumOthersStr;
	}

	public void setSumOthersStr(String sumOthersStr) {
		this.sumOthersStr = sumOthersStr;
	}

	public String getSumTrxTaxinBfStr() {
		return sumTrxTaxinBfStr;
	}

	public void setSumTrxTaxinBfStr(String sumTrxTaxinBfStr) {
		this.sumTrxTaxinBfStr = sumTrxTaxinBfStr;
	}

	public String getSumTrxComoAmtStr() {
		return sumTrxComoAmtStr;
	}

	public void setSumTrxComoAmtStr(String sumTrxComoAmtStr) {
		this.sumTrxComoAmtStr = sumTrxComoAmtStr;
	}

	public String getSumTrxWithAmtStr() {
		return sumTrxWithAmtStr;
	}

	public void setSumTrxWithAmtStr(String sumTrxWithAmtStr) {
		this.sumTrxWithAmtStr = sumTrxWithAmtStr;
	}

	public String getSumTrxOthers4AmtStr() {
		return sumTrxOthers4AmtStr;
	}

	public void setSumTrxOthers4AmtStr(String sumTrxOthers4AmtStr) {
		this.sumTrxOthers4AmtStr = sumTrxOthers4AmtStr;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}
	
}