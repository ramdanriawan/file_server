package com.biru.helper;

import java.math.BigDecimal;
import java.text.NumberFormat;

public class TrHelper {

	private String glVoucherId;
	
	private BigDecimal balance;
	
	private BigDecimal balanceCredit;

	private BigDecimal balanceDebit;
	
	private BigDecimal balanceCreditIdr;

	private BigDecimal balanceDebitIdr;
	
	private String balanceStr;
	
	private String balanceCreditStr;

	private String balanceDebitStr;

	public TrHelper(String glVoucherId, BigDecimal balance, BigDecimal balanceCredit, BigDecimal balanceDebit) {
		super();
		this.glVoucherId = glVoucherId;
		this.balance = balance;
		this.balanceCredit = balanceCredit;
		this.balanceDebit = balanceDebit;
	}

	public String getGlVoucherId() {
		return glVoucherId;
	}

	public void setGlVoucherId(String glVoucherId) {
		this.glVoucherId = glVoucherId;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getBalanceCredit() {
		return balanceCredit;
	}

	public void setBalanceCredit(BigDecimal balanceCredit) {
		this.balanceCredit = balanceCredit;
	}

	public BigDecimal getBalanceDebit() {
		return balanceDebit;
	}

	public void setBalanceDebit(BigDecimal balanceDebit) {
		this.balanceDebit = balanceDebit;
	}

	public BigDecimal getBalanceCreditIdr() {
		return balanceCreditIdr;
	}

	public void setBalanceCreditIdr(BigDecimal balanceCreditIdr) {
		this.balanceCreditIdr = balanceCreditIdr;
	}

	public BigDecimal getBalanceDebitIdr() {
		return balanceDebitIdr;
	}

	public void setBalanceDebitIdr(BigDecimal balanceDebitIdr) {
		this.balanceDebitIdr = balanceDebitIdr;
	}
	
	public String getBalanceStr() {
		if(balance != null)
			return NumberFormat.getCurrencyInstance().format(balance).replace("$", "");
		
		return balanceStr;
	}

	public void setBalanceStr(String balanceStr) {
		this.balanceStr = balanceStr;
	}

	public String getBalanceCreditStr() {
		if(balanceCredit != null)
			return NumberFormat.getCurrencyInstance().format(balanceCredit).replace("$", "");
		
		return balanceCreditStr;
	}

	public void setBalanceCreditStr(String balanceCreditStr) {
		this.balanceCreditStr = balanceCreditStr;
	}

	public String getBalanceDebitStr() {
		if(balanceDebit != null)
			return NumberFormat.getCurrencyInstance().format(balanceDebit).replace("$", "");
		
		return balanceDebitStr;
	}

	public void setBalanceDebitStr(String balanceDebitStr) {
		this.balanceDebitStr = balanceDebitStr;
	}

	@Override
	public String toString() {
		return "{voucherId:" + glVoucherId + ", balance:" + balance + ", balanceCredit:" + balanceCredit
				+ ", balanceDebit:" + balanceDebit + ", balanceCreditIdr:" + balanceCreditIdr + ", balanceDebitIdr:"
				+ balanceDebitIdr + "}";
	}

}
