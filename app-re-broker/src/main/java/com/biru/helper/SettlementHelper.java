package com.biru.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class SettlementHelper {
	
	private Long idKey; 

	private String trxType;
	
	private String trxVoucherId;
	
	private String trxOldVoucherId;
	
	private String trxDate;
	
	private String trxDueDate;
	
	private Integer age;
	
	private Integer trxCountInv;
	
	private String trxClient;
	
	private String trxClientDesc;
	
	private String trxCoverCode;
	
	private String trxDescription;
	
	private String trxCurrId;
	
	private BigDecimal trxCurrRate;
	
	private BigDecimal trxOrgAmount;
	
	private BigDecimal trxSetAmount;
	
	private BigDecimal outstanding;
	
	private BigDecimal trxComAmount;
	
	private String trxRemarks;

	public SettlementHelper(Long idKey, String trxType, String trxVoucherId, String trxDate, String trxDueDate, Integer age,
			Integer trxCountInv, String trxClient, String trxClientDesc, String trxCoverCode, String trxDescription, 
			String trxCurrId, BigDecimal trxOrgAmount, BigDecimal trxSetAmount, BigDecimal outstanding, BigDecimal trxComAmount, String trxRemarks,
			BigDecimal trxCurrRate) {
		super();
		this.idKey = idKey;
		this.trxType = trxType;
		this.trxVoucherId = trxVoucherId;
		this.trxDate = trxDate;
		this.trxDueDate = trxDueDate;
		this.age = age;
		this.trxCountInv = trxCountInv;
		this.trxClient = trxClient;
		this.trxClientDesc = trxClientDesc;
		this.trxCoverCode = trxCoverCode;
		this.trxDescription = trxDescription;
		this.trxCurrId = trxCurrId;
		this.trxOrgAmount = trxOrgAmount;
		this.trxSetAmount = trxSetAmount;
		this.outstanding = outstanding;
		this.trxComAmount = trxComAmount;
		this.trxRemarks = trxRemarks;
		this.trxCurrRate = trxCurrRate;
	}
	
	public SettlementHelper(Long idKey, String trxType, String trxVoucherId, String trxDate, String trxDueDate, Integer age,
			Integer trxCountInv, String trxClient, String trxClientDesc, String trxCoverCode, String trxDescription, 
			String trxCurrId, BigDecimal trxOrgAmount, BigDecimal trxSetAmount, BigDecimal outstanding, BigDecimal trxComAmount, String trxRemarks,
			BigDecimal trxCurrRate, String trxOldVoucherId) {
		super();
		this.idKey = idKey;
		this.trxType = trxType;
		this.trxVoucherId = trxVoucherId;
		this.trxDate = trxDate;
		this.trxDueDate = trxDueDate;
		this.age = age;
		this.trxCountInv = trxCountInv;
		this.trxClient = trxClient;
		this.trxClientDesc = trxClientDesc;
		this.trxCoverCode = trxCoverCode;
		this.trxDescription = trxDescription;
		this.trxCurrId = trxCurrId;
		this.trxOrgAmount = trxOrgAmount;
		this.trxSetAmount = trxSetAmount;
		this.outstanding = outstanding;
		this.trxComAmount = trxComAmount;
		this.trxRemarks = trxRemarks;
		this.trxCurrRate = trxCurrRate;
		this.trxOldVoucherId = trxOldVoucherId;
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
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

	public String getTrxOldVoucherId() {
		return trxOldVoucherId;
	}

	public void setTrxOldVoucherId(String trxOldVoucherId) {
		this.trxOldVoucherId = trxOldVoucherId;
	}

	public String getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(String trxDate) {
		this.trxDate = trxDate;
	}

	public String getTrxDueDate() {
		return trxDueDate;
	}

	public void setTrxDueDate(String trxDueDate) {
		this.trxDueDate = trxDueDate;
	}

	public Integer getAge() {
		if(age == null)
			return null;
		
		if(age<=0)
			age = 0;
		
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getTrxCountInv() {
		return trxCountInv;
	}

	public void setTrxCountInv(Integer trxCountInv) {
		this.trxCountInv = trxCountInv;
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

	public String getTrxCoverCode() {
		return trxCoverCode;
	}

	public void setTrxCoverCode(String trxCoverCode) {
		this.trxCoverCode = trxCoverCode;
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

	public String getTrxOrgAmount() {
		return NumberFormat.getCurrencyInstance().format(trxOrgAmount).replace("$", "");
	}

	public void setTrxOrgAmount(BigDecimal trxOrgAmount) {
		this.trxOrgAmount = trxOrgAmount;
	}
	
	public BigDecimal getTrxSetAmount() {
		return trxSetAmount;
	}

	public void setTrxSetAmount(BigDecimal trxSetAmount) {
		this.trxSetAmount = trxSetAmount;
	}

	public String getOutstanding() {
		return NumberFormat.getCurrencyInstance().format(outstanding).replace("$", "");
	}

	public void setOutstanding(BigDecimal outstanding) {
		this.outstanding = outstanding;
	}

	public BigDecimal getTrxComAmount() {
		return trxComAmount;
	}

	public void setTrxComAmount(BigDecimal trxComAmount) {
		this.trxComAmount = trxComAmount;
	}

	public String getTrxRemarks() {
		return trxRemarks;
	}

	public void setTrxRemarks(String trxRemarks) {
		this.trxRemarks = trxRemarks;
	}
	
}
