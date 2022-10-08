package com.biru.helper;

public class InternalMemoHelper {
	
	private String ccy;
	private String cedant;
	private String cedantName;
	
	public InternalMemoHelper() {
	}
	
	public InternalMemoHelper(String ccy, String cedant, String cedantName) {
		this.ccy = ccy;
		this.cedant = cedant;
		this.cedantName = cedantName;
	}

	public String getCcy() {
		return ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public String getCedant() {
		return cedant;
	}

	public void setCedant(String cedant) {
		this.cedant = cedant;
	}

	public String getCedantName() {
		return cedantName;
	}

	public void setCedantName(String cedantName) {
		this.cedantName = cedantName;
	}
	
}
