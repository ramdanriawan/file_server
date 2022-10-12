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

@Entity
@Table(name="MA0015")
@NamedQuery(name="MA0015Entity.findAll", query="SELECT m FROM MA0015Entity m")
public class MA0015Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEY")
	private Long idKey;
	
	@Column(name = "EX_MONTH")
	private Integer exMonth;
	
	@Column(name = "EX_YEAR")
	private Integer exYear;
	
	@Column(name = "EX_CURR_ID")
	private String exCurrId;
	
	@Column(name = "EX_RATE_VALUE")
	private BigDecimal exRateValue;
	
	@Column(name = "EX_RATE_EOM")
	private BigDecimal exRateEom;
	
	@Column(name = "EX_RATE_PRO")
	private BigDecimal exRatePro;
	
	@Column(name = "EX_RATE_TAX")
	private BigDecimal exRateTax;
	
	@Column(name = "CREATE_BY")
	private String createBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_ON")
	private Date createOn;
	
	@Column(name = "MODIFY_BY")
	private String modifyBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_ON")
	private Date modifyOn;
	
	@Transient
	private String exRateValueStr;
	
	@Transient
	private String exRateEomStr;
	
	@Transient
	private String exRateProStr;
	
	@Transient
	private String exRateTaxStr;
	
	@Transient
	private String monthStr;

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public Integer getExMonth() {
		return exMonth;
	}

	public void setExMonth(Integer exMonth) {
		this.exMonth = exMonth;
	}

	public Integer getExYear() {
		return exYear;
	}

	public void setExYear(Integer exYear) {
		this.exYear = exYear;
	}

	public String getExCurrId() {
		return exCurrId;
	}

	public void setExCurrId(String exCurrId) {
		this.exCurrId = exCurrId;
	}

	public BigDecimal getExRateValue() {
		return exRateValue;
	}

	public void setExRateValue(BigDecimal exRateValue) {
		this.exRateValue = exRateValue;
	}

	public BigDecimal getExRateEom() {
		return exRateEom;
	}

	public void setExRateEom(BigDecimal exRateEom) {
		this.exRateEom = exRateEom;
	}
	
	public BigDecimal getExRatePro() {
		return exRatePro;
	}

	public void setExRatePro(BigDecimal exRatePro) {
		this.exRatePro = exRatePro;
	}

	public BigDecimal getExRateTax() {
		return exRateTax;
	}

	public void setExRateTax(BigDecimal exRateTax) {
		this.exRateTax = exRateTax;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyOn() {
		return modifyOn;
	}

	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}

	public String getExRateValueStr() {
		return NumberFormat.getCurrencyInstance().format(exRateValue).replace("$", "");
	}

	public void setExRateValueStr(String exRateValueStr) {
		this.exRateValueStr = exRateValueStr;
	}

	public String getExRateEomStr() {
		return NumberFormat.getCurrencyInstance().format(exRateEom).replace("$", "");
	}

	public void setExRateEomStr(String exRateEomStr) {
		this.exRateEomStr = exRateEomStr;
	}

	public String getExRateProStr() {
		return NumberFormat.getCurrencyInstance().format(exRatePro).replace("$", "");
	}

	public void setExRateProStr(String exRateProStr) {
		this.exRateProStr = exRateProStr;
	}

	public String getExRateTaxStr() {
		return NumberFormat.getCurrencyInstance().format(exRateTax).replace("$", "");
	}

	public void setExRateTaxStr(String exRateTaxStr) {
		this.exRateTaxStr = exRateTaxStr;
	}
	
	public void setMonthStr(String monthStr) {
		this.monthStr = monthStr;
	}

	public String getMonthStr() {
		return getMonth();
	}
	
	public String getMonth() {
		if (this.exMonth == Calendar.JANUARY+1)
			return "January";
		if (this.exMonth == Calendar.FEBRUARY+1)
			return "February";
		if (this.exMonth == Calendar.MARCH+1)
			return "March";
		if (this.exMonth == Calendar.APRIL+1)
			return "April";
		if (this.exMonth == Calendar.MAY+1)
			return "May";
		if (this.exMonth == Calendar.JUNE+1)
			return "June";
		if (this.exMonth == Calendar.JULY+1)
			return "July";
		if (this.exMonth == Calendar.AUGUST+1)
			return "August";
		if (this.exMonth == Calendar.SEPTEMBER+1)
			return "September";
		if (this.exMonth == Calendar.OCTOBER+1)
			return "October";
		if (this.exMonth == Calendar.NOVEMBER+1)
			return "November";
		else		
			return "December";
	}

	
}
