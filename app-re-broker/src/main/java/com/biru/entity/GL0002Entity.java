package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the gl0002 database table.
 * 
 */
@Entity
@Table(name="GL0002")
@NamedQuery(name="GL0002Entity.findAll", query="SELECT g FROM GL0002Entity g")
public class GL0002Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idKey;

	@Column(name="COA_CODE")
	private String coaCode;
	
	@Column(name="SA_CODE")
	private String saCode;

	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;

	@Column(name="MODIFY_BY")
	private String modifyBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="MODIFY_ON")
	private Date modifyOn;

	@Column(name="TB_AMOUNT")
	private BigDecimal tbAmount;

	@Column(name="TB_MONTH")
	private String tbMonth;

	@Column(name="TB_YEAR")
	private String tbYear;
	
	@Transient
	private String saName;

	@Transient
	private String description;
	
	@Transient
	private String tbAmountStr;
	
	@Transient
	private String tbMonthStr;
	
	public GL0002Entity() {
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getCoaCode() {
		return this.coaCode;
	}

	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return this.createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyOn() {
		return this.modifyOn;
	}

	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}

	public BigDecimal getTbAmount() {
		return this.tbAmount;
	}

	public void setTbAmount(BigDecimal tbAmount) {
		this.tbAmount = tbAmount;
	}

	public String getTbMonth() {
		return this.tbMonth;
	}

	public void setTbMonth(String tbMonth) {
		this.tbMonth = tbMonth;
	}

	public String getTbYear() {
		return this.tbYear;
	}

	public void setTbYear(String tbYear) {
		this.tbYear = tbYear;
	}
	
	public String getSaCode() {
		return saCode;
	}

	public void setSaCode(String saCode) {
		this.saCode = saCode;
	}

	public String getSaName() {
		return saName;
	}

	public void setSaName(String saName) {
		this.saName = saName;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTbAmountStr() {
		return NumberFormat.getCurrencyInstance().format(tbAmount).replace("$", "");
	}

	public void setTbAmountStr(String tbAmountStr) {
		this.tbAmountStr = tbAmountStr;
	}

	public String getTbMonthStr() {
		switch (Integer.valueOf(getTbMonth())-1) {
		case Calendar.JANUARY:
			setTbMonthStr("January"); 
			break;
		case Calendar.FEBRUARY:
			setTbMonthStr("February"); 
			break;
		case Calendar.MARCH:
			setTbMonthStr("March"); 
			break;
		case Calendar.APRIL:
			setTbMonthStr("April"); 
			break;
		case Calendar.MAY:
			setTbMonthStr("May"); 
			break;
		case Calendar.JUNE:
			setTbMonthStr("June"); 
			break;
		case Calendar.JULY:
			setTbMonthStr("July"); 
			break;
		case Calendar.AUGUST:
			setTbMonthStr("August"); 
			break;
		case Calendar.SEPTEMBER:
			setTbMonthStr("September"); 
			break;
		case Calendar.OCTOBER:
			setTbMonthStr("October"); 
			break;
		case Calendar.NOVEMBER:
			setTbMonthStr("November"); 
			break;
		case Calendar.DECEMBER:
			setTbMonthStr("December"); 
			break;
		default:
			break;
		}
		return tbMonthStr;
	}

	void setTbMonthStr(String tbMonthStr) {
		this.tbMonthStr = tbMonthStr;
	}
	
}