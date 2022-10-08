package com.biru.helper;

import java.io.Serializable;
import java.math.BigDecimal;

public class BalanceHelper implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private BigDecimal balance;
	
	private BigDecimal balanceIdr;
	
	private String coaClass;
	
	public BalanceHelper(BigDecimal balance) {
		super();
		this.balance = balance;
	}

	public BalanceHelper(BigDecimal balance, BigDecimal balanceIdr, String coaClass) {
		super();
		this.balance = balance;
		this.balanceIdr = balanceIdr;
		this.coaClass = coaClass;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalanceIdr() {
		return balanceIdr;
	}

	public void setBalanceIdr(BigDecimal balanceIdr) {
		this.balanceIdr = balanceIdr;
	}

	public String getCoaClass() {
		return coaClass;
	}

	public void setCoaClass(String coaClass) {
		this.coaClass = coaClass;
	}

}
