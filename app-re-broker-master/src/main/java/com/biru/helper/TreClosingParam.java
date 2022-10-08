package com.biru.helper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TreClosingParam implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String type;
	
	private String trxVoucherId;
	
	private String remarks;
	
	private String clientConfirmationDate;
	
	private String user;
	
	private List<Map<String, Object>> clientValue;
	
	private List<Map<String, Object>> reinsValue;
	
	private Boolean isAdjustment = Boolean.FALSE;
	
	public TreClosingParam() {
	}
	
	public TreClosingParam(String trxVoucherId, String remarks, String clientConfirmationDate, String user,
			List<Map<String, Object>> clientValue, List<Map<String, Object>> reinsValue) {
		super();
		this.trxVoucherId = trxVoucherId;
		this.remarks = remarks;
		this.clientConfirmationDate = clientConfirmationDate;
		this.user = user;
		this.clientValue = clientValue;
		this.reinsValue = reinsValue;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTrxVoucherId() {
		return trxVoucherId;
	}

	public void setTrxVoucherId(String trxVoucherId) {
		this.trxVoucherId = trxVoucherId;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getClientConfirmationDate() {
		return clientConfirmationDate;
	}

	public void setClientConfirmationDate(String clientConfirmationDate) {
		this.clientConfirmationDate = clientConfirmationDate;
	}
	
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<Map<String, Object>> getClientValue() {
		return clientValue;
	}

	public void setClientValue(List<Map<String, Object>> clientValue) {
		this.clientValue = clientValue;
	}

	public List<Map<String, Object>> getReinsValue() {
		return reinsValue;
	}

	public void setReinsValue(List<Map<String, Object>> reinsValue) {
		this.reinsValue = reinsValue;
	}

	public Boolean getIsAdjustment() {
		return isAdjustment;
	}

	public void setIsAdjustment(Boolean isAdjustment) {
		this.isAdjustment = isAdjustment;
	}

	@Override
	public String toString() {
		return "TreClosingParam [type=" + type + ", trxVoucherId=" + trxVoucherId + ", remarks=" + remarks
				+ ", clientConfirmationDate=" + clientConfirmationDate + ", user=" + user + ", clientValue="
				+ clientValue + ", reinsValue=" + reinsValue + ", isAdjustment=" + isAdjustment + "]";
	}
	
}
