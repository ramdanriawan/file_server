package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="VIEW_INQ_CLAIMS_CREATE")
@NamedQuery(name="ViewInqClaimsCreate.findAll", query="SELECT v FROM ViewInqClaimsCreate v")
public class ViewInqClaimsCreate implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="ID_KEY")
	private String idKey;
	
	@Column(name="TRX_CLASS")
	private String trxClass;
	
	@Column(name="TRX_TRX_ID")
	private String trxTrxId;
	
	@Column(name="TRX_VOUCHER_ID")
	private String trxVoucherId;
	
	@Column(name="TRX_REMARKS")
	private String trxRemarks;
	
	@Column(name="TRX_COVER_CODE")
	private String trxCoverCode;
	
	@Column(name="TRX_INS_START")
	private String trxInsStart;
	
	@Column(name="TRX_INS_END")
	private String trxInsEnd;
	
	@Column(name="PERIOD")
	private String period;
	
	@Column(name="TRX_CLIENT")
	private String trxClient;
	
	@Column(name="TRX_CLIENT_DESC")
	private String trxClientDesc;
	
	@Column(name="TRX_INSURED_NAME")
	private String trxInsuredName;
	
	@Column(name="TRX_CURR_ID")
	private String trxCurrId;
	
	@Column(name="TRX_TSI_AMOUNT")
	private BigDecimal trxTsiAmount;
	
	@Column(name="TRX_INS_INSURED")
	private String trxInsInsured;
	
	@Column(name="SOURCE")
	private String source;
	
	@Column(name="TRX_DEDUC_AMT")
	private BigDecimal trxDeducAmt;
	
	@Column(name="TRX_REINS_NO")
	private Integer trxReinsNo;
	
	@Column(name="TRX_LIMIT_AMT")
	private BigDecimal trxLimitAmt;
	
	@Column(name="A3")
	private BigDecimal a3;
	
	@Transient
	private String trxClassDesc;
	
	@Transient
	private String trxTsiAmountStr;
	
	public ViewInqClaimsCreate(){
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

	public String getTrxRemarks() {
		return trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}

	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
	}

	public String getTrxInsStart() {
		return trxInsStart;
	}

	public void setTrxInsStart(String trxInsStart) {
		this.trxInsStart = trxInsStart;
	}

	public String getTrxInsEnd() {
		return trxInsEnd;
	}

	public void setTrxInsEnd(String trxInsEnd) {
		this.trxInsEnd = trxInsEnd;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getTrxClient() {
		return trxClient;
	}

	public void setTrxClient(String trxClient) {
		this.trxClient = trxClient;
	}

	public String getTrxClientDesc() {
		return trxClientDesc;
	}

	public void setTrxClientDesc(String trxClientDesc) {
		this.trxClientDesc = trxClientDesc;
	}

	public String getTrxInsuredName() {
		return trxInsuredName;
	}

	public void setTrxInsuredName(String trxInsuredName) {
		this.trxInsuredName = trxInsuredName;
	}

	public String getTrxCurrId() {
		return trxCurrId;
	}

	public void setTrxCurrId(String trxCurrId) {
		this.trxCurrId = trxCurrId;
	}

	public BigDecimal getTrxTsiAmount() {
		return trxTsiAmount;
	}

	public void setTrxTsiAmount(BigDecimal trxTsiAmount) {
		this.trxTsiAmount = trxTsiAmount;
	}

	public String getTrxClassDesc() {
		if("FAC".equals(trxClass))
			return "Facultative";
		
		return "Treaty";
	}

	public void setTrxClassDesc(String trxClassDesc) {
		this.trxClassDesc = trxClassDesc;
	}

	public String getTrxTsiAmountStr() {
		if(trxTsiAmount != null)
			return NumberFormat.getCurrencyInstance().format(trxTsiAmount).replace("$", "");
		
		return trxTsiAmountStr;
	}

	public void setTrxTsiAmountStr(String trxTsiAmountStr) {
		this.trxTsiAmountStr = trxTsiAmountStr;
	}

	public String getTrxInsInsured() {
		return trxInsInsured;
	}

	public void setTrxInsInsured(String trxInsInsured) {
		this.trxInsInsured = trxInsInsured;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public BigDecimal getTrxDeducAmt() {
		return trxDeducAmt;
	}

	public void setTrxDeducAmt(BigDecimal trxDeducAmt) {
		this.trxDeducAmt = trxDeducAmt;
	}

	public Integer getTrxReinsNo() {
		return trxReinsNo;
	}

	public void setTrxReinsNo(Integer trxReinsNo) {
		this.trxReinsNo = trxReinsNo;
	}

	public BigDecimal getTrxLimitAmt() {
		return trxLimitAmt;
	}

	public void setTrxLimitAmt(BigDecimal trxLimitAmt) {
		this.trxLimitAmt = trxLimitAmt;
	}

	public BigDecimal getA3() {
		return a3;
	}

	public void setA3(BigDecimal a3) {
		this.a3 = a3;
	}
	
}
