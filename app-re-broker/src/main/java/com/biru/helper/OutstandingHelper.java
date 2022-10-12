package com.biru.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class OutstandingHelper {
	
	private Long idKey; 
	
	private String trxDate;
	
	private String trxDueDate;
	
	private String age;

	private String trxType;
	
	private String trxVoucherId;
	
	private Integer trxCountInv;
	
	private String trxCoverCode;
	
	private String trxOldType;
	
	private String trxOldVoucherId;
	
	private String trxClient;
	
	private String trxClientDesc;
	
	private String trxDescription;
	
	private String trxCurrId;
	
	private BigDecimal outstanding;

	public OutstandingHelper(Long idKey, String trxDate, String trxDueDate, Integer age, String trxType, String trxVoucherId,
			Integer trxCountInv, String trxCoverCode, String trxOldType, String trxOldVoucherId, String trxClient, 
			String trxClientDesc, String trxDescription, String trxCurrId, BigDecimal outstanding) {
		super();
		this.idKey = idKey;
		this.trxDate = trxDate;
		this.trxDueDate = trxDueDate;
		this.age = age.toString();
		this.trxType = trxType;
		this.trxVoucherId = trxVoucherId;
		this.trxCountInv = trxCountInv;
		this.trxCoverCode = trxCoverCode;
		this.trxOldType = trxOldType;
		this.trxOldVoucherId = trxOldVoucherId;
		this.trxClient = trxClient;
		this.trxClientDesc = trxClientDesc;
		this.trxDescription = trxDescription;
		this.trxCurrId = trxCurrId;
		this.outstanding = outstanding;
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

	public String getAge() {
		if(age == null)
			return null;

		if(Integer.parseInt(age)<0)
			age = "("+age.substring(1)+")";
		
		return age;
	}

	public void setAge(Integer age) {
		this.age = age.toString();
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
	
	public String getOutstanding() {
		return NumberFormat.getCurrencyInstance().format(outstanding).replace("$", "");
	}

	public void setOutstanding(BigDecimal outstanding) {
		this.outstanding = outstanding;
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

}
