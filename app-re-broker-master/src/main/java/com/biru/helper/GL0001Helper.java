package com.biru.helper;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;

public class GL0001Helper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String coaCode;
	
	private String coaDescript;
	
	private String glBalYear;
	
	private BigDecimal glBalCredit0;
	
	private BigDecimal glBalDebit0;
	
	public GL0001Helper() {
	}
	
	public GL0001Helper(String coaCode, String coaDescript, String glBalYear,
			BigDecimal glBalDebit0, BigDecimal glBalCredit0) {
		super();
		this.coaCode = coaCode;
		this.coaDescript = coaDescript;
		this.glBalYear = glBalYear;
		this.glBalDebit0 = glBalDebit0;
		this.glBalCredit0 = glBalCredit0;
	}

	public String getCoaCode() {
		return coaCode;
	}

	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	public String getCoaDescript() {
		return coaDescript;
	}

	public void setCoaDescript(String coaDescript) {
		this.coaDescript = coaDescript;
	}

	public String getGlBalYear() {
		return glBalYear;
	}

	public void setGlBalYear(String glBalYear) {
		this.glBalYear = glBalYear;
	}

	public String getGlBalCredit0() {
		return NumberFormat.getCurrencyInstance().format(glBalCredit0).replace("$", "");
	}

	public void setGlBalCredit0(BigDecimal glBalCredit0) {
		this.glBalCredit0 = glBalCredit0;
	}

	public String getGlBalDebit0() {
		return NumberFormat.getCurrencyInstance().format(glBalDebit0).replace("$", "");
	}

	public void setGlBalDebit0(BigDecimal glBalDebit0) {
		this.glBalDebit0 = glBalDebit0;
	}

}
