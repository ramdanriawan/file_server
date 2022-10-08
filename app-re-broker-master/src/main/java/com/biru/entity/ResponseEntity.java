package com.biru.entity;

import java.util.HashMap;
import java.util.Map;

public class ResponseEntity {
	
	String error;
	
	String msgValidation;
	
	String result;
	
	String status;
	
	Map<String, Object> message;

	public ResponseEntity() {
		this.message = new HashMap<String, Object>();
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMsgValidation() {
		return msgValidation;
	}

	public void setMsgValidation(String msgValidation) {
		this.msgValidation = msgValidation;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, Object> getMessage() {
		return message;
	}

	public void setMessage(Map<String, Object> message) {
		this.message = message;
	}
	
}