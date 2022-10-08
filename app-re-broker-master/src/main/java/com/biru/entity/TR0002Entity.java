package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.text.NumberFormat;


/**
 * The persistent class for the TR0002 database table.
 * 
 */
@Entity
@Table(name="TR0002")
@NamedQuery(name="TR0002Entity.findAll", query="SELECT t FROM TR0002Entity t")
public class TR0002Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idKey;

	@Column(name="GL_ACCOUNT")
	private String glAccount;

	@Column(name="GL_CURR_ID")
	private String glCurrId;

	@Column(name="GL_CURR_RATE")
	private BigDecimal glCurrRate;

	@Column(name="GL_IDR_CREDIT")
	private BigDecimal glIdrCredit;

	@Column(name="GL_IDR_DEBIT")
	private BigDecimal glIdrDebit;

	@Column(name="GL_ORG_CREDIT")
	private BigDecimal glOrgCredit;

	@Column(name="GL_ORG_DEBIT")
	private BigDecimal glOrgDebit;

	@Column(name="GL_TRX_CLASS")
	private String glTrxClass;

	@Column(name="GL_TYPE")
	private String glType;

	@Column(name="GL_VOUCHER_ID")
	private String glVoucherId;
	
	@Column(name="GL_DESCRIPTION")
	private String glDescription;
	
	@Transient
	private String glCurrRateStr;

	@Transient
	private String glIdrCreditStr;

	@Transient
	private String glIdrDebitStr;

	@Transient
	private String glOrgCreditStr;

	@Transient
	private String glOrgDebitStr;

	@Transient
	private String glAccountDesc;
	
	public TR0002Entity() {
	}
	
	public TR0002Entity(String glAccount, BigDecimal glIdrCredit, BigDecimal glIdrDebit, 
			BigDecimal glOrgCredit, BigDecimal glOrgDebit) {
		super();
		this.glAccount = glAccount;
		this.glIdrCredit = glIdrCredit;
		this.glIdrDebit = glIdrDebit;
		this.glOrgCredit = glOrgCredit;
		this.glOrgDebit = glOrgDebit;
	}

	public TR0002Entity(BigDecimal glOrgCredit, BigDecimal glOrgDebit, BigDecimal glCurrRate, String glAccountDesc) {
		super();
		this.glOrgCredit = glOrgCredit;
		this.glOrgDebit = glOrgDebit;
		this.glCurrRate = glCurrRate;
		this.glAccountDesc = glAccountDesc;
	}
	
	public TR0002Entity(String glType, String glAccount, String glAccountDesc, 
			String glCurrId, BigDecimal glCurrRate,
			BigDecimal glOrgCredit, BigDecimal glOrgDebit,
			BigDecimal glIdrCredit, BigDecimal glIdrDebit,
			String glDescription) {
		super();
		this.glType = glType;
		this.glAccount = glAccount;
		this.glAccountDesc = glAccountDesc;
		this.glCurrId = glCurrId;
		this.glCurrRate = glCurrRate;
		this.glOrgCredit = glOrgCredit;
		this.glOrgDebit = glOrgDebit;
		this.glIdrCredit = glIdrCredit;
		this.glIdrDebit = glIdrDebit;
		this.glDescription = glDescription;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getGlAccount() {
		return this.glAccount;
	}

	public void setGlAccount(String glAccount) {
		this.glAccount = glAccount;
	}

	public String getGlCurrId() {
		return this.glCurrId;
	}

	public void setGlCurrId(String glCurrId) {
		this.glCurrId = glCurrId;
	}

	public BigDecimal getGlCurrRate() {
		return this.glCurrRate;
	}

	public void setGlCurrRate(BigDecimal glCurrRate) {
		this.glCurrRate = glCurrRate;
	}

	public BigDecimal getGlIdrCredit() {
		return this.glIdrCredit;
	}

	public void setGlIdrCredit(BigDecimal glIdrCredit) {
		this.glIdrCredit = glIdrCredit;
	}

	public BigDecimal getGlIdrDebit() {
		return this.glIdrDebit;
	}

	public void setGlIdrDebit(BigDecimal glIdrDebit) {
		this.glIdrDebit = glIdrDebit;
	}

	public BigDecimal getGlOrgCredit() {
		return this.glOrgCredit;
	}

	public void setGlOrgCredit(BigDecimal glOrgCredit) {
		this.glOrgCredit = glOrgCredit;
	}

	public BigDecimal getGlOrgDebit() {
		return this.glOrgDebit;
	}

	public void setGlOrgDebit(BigDecimal glOrgDebit) {
		this.glOrgDebit = glOrgDebit;
	}

	public String getGlTrxClass() {
		return this.glTrxClass;
	}

	public void setGlTrxClass(String glTrxClass) {
		this.glTrxClass = glTrxClass;
	}

	public String getGlType() {
		return this.glType;
	}

	public void setGlType(String glType) {
		this.glType = glType;
	}

	public String getGlVoucherId() {
		return this.glVoucherId;
	}

	public void setGlVoucherId(String glVoucherId) {
		this.glVoucherId = glVoucherId;
	}
	
	public String getGlDescription() {
		return glDescription;
	}

	public void setGlDescription(String glDescription) {
		this.glDescription = glDescription;
	}

	public String getGlCurrRateStr() {
		return NumberFormat.getCurrencyInstance().format(glCurrRate).replace("$", "");
	}

	public void setGlCurrRateStr(String glCurrRateStr) {
		this.glCurrRateStr = glCurrRateStr;
	}

	public String getGlIdrCreditStr() {
		return NumberFormat.getCurrencyInstance().format(glIdrCredit).replace("$", "");
	}

	public void setGlIdrCreditStr(String glIdrCreditStr) {
		this.glIdrCreditStr = glIdrCreditStr;
	}

	public String getGlIdrDebitStr() {
		return NumberFormat.getCurrencyInstance().format(glIdrDebit).replace("$", "");
	}

	public void setGlIdrDebitStr(String glIdrDebitStr) {
		this.glIdrDebitStr = glIdrDebitStr;
	}

	public String getGlOrgCreditStr() {
		return NumberFormat.getCurrencyInstance().format(glOrgCredit).replace("$", "");
	}

	public void setGlOrgCreditStr(String glOrgCreditStr) {
		this.glOrgCreditStr = glOrgCreditStr;
	}

	public String getGlOrgDebitStr() {
		return NumberFormat.getCurrencyInstance().format(glOrgDebit).replace("$", "");
	}

	public void setGlOrgDebitStr(String glOrgDebitStr) {
		this.glOrgDebitStr = glOrgDebitStr;
	}

	public String getGlAccountDesc() {
		return glAccountDesc;
	}

	public void setGlAccountDesc(String glAccountDesc) {
		this.glAccountDesc = glAccountDesc;
	}

	@Override
	public String toString() {
		return "TR0002Entity [idKey=" + idKey + ", glAccount=" + glAccount + ", glCurrId=" + glCurrId + ", glCurrRate="
				+ glCurrRate + ", glIdrCredit=" + glIdrCredit + ", glIdrDebit=" + glIdrDebit + ", glOrgCredit="
				+ glOrgCredit + ", glOrgDebit=" + glOrgDebit + ", glTrxClass=" + glTrxClass + ", glType=" + glType
				+ ", glVoucherId=" + glVoucherId + ", glDescription=" + glDescription + ", glCurrRateStr="
				+ glCurrRateStr + ", glIdrCreditStr=" + glIdrCreditStr + ", glIdrDebitStr=" + glIdrDebitStr
				+ ", glOrgCreditStr=" + glOrgCreditStr + ", glOrgDebitStr=" + glOrgDebitStr + ", glAccountDesc="
				+ glAccountDesc + "]";
	}
	
}