package com.biru.entity;

import java.util.List;

import com.biru.helper.TrHelper;

public class ProgressEntity {
	public String process;
	public String tenant;
	public String progress;
	public String flag;
	public String processedBy;
	public Boolean isFailed = false;
	public String reason;
	public List<TrHelper> errorData;
	
	public ProgressEntity(String process, String tenant, String progress, String flag, 
			String processedBy, Boolean isFailed, String reason) {
		super();
		this.process = process;
		this.tenant = tenant;
		this.progress = progress;
		this.flag = flag;
		this.processedBy = processedBy;
		this.isFailed = isFailed;
		this.reason = reason;
	}
	
	public ProgressEntity() {
		super();
	}

	public String getProcess() {
		return process;
	}
	
	public void setProcess(String process) {
		this.process = process;
	}
	
	public String getProcessedBy() {
		return processedBy;
	}
	
	public void setProcessedBy(String processedBy) {
		this.processedBy = processedBy;
	}
	
	public String getTenant() {
		return tenant;
	}
	
	public void setTenant(String tenant) {
		this.tenant = tenant;
	}
	
	public String getProgress() {
		return progress;
	}
	
	public void setProgress(String progress) {
		this.progress = progress;
	}
	
	public String getFlag() {
		return flag;
	}
	
	public void setFlag(String flag) {
		this.flag = flag;
	}
	
	public String getProsesedBy() {
		return processedBy;
	}
	
	public void setProsesedBy(String processedBy) {
		this.processedBy = processedBy;
	}
	
	public Boolean getIsFailed() {
		return isFailed;
	}
	
	public void setIsFailed(Boolean isFailed) {
		this.isFailed = isFailed;
	}
	
	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}

	public List<TrHelper> getErrorData() {
		return errorData;
	}

	public void setErrorData(List<TrHelper> errorData) {
		this.errorData = errorData;
	}
	
}
