package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
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
@Table(name="MA0015A")
@NamedQuery(name="MA0015AEntity.findAll", query="SELECT m FROM MA0015AEntity m")
public class MA0015AEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID_KEY")
	private Long idKey;
	
	@Column(name = "EX_DATE")
	private Date exDate;
	
	@Column(name = "EX_CURR_ID")
	private String exCurrId;
	
	@Column(name = "EX_KMK_RATE")
	private BigDecimal exKmkRate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_ON")
	private Date createOn;
	
	@Column(name = "CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_ON")
	private Date modifyOn;
	
	@Column(name = "MODIFY_BY")
	private String modifyBy;
	
	@Transient
	private String exKmkRateStr;
	
	@Transient
	private String dateOnly;
	
	@Transient
	private String action;
	
	public String getDateOnly(){
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String date = sdf.format(exDate);
		
		return date;
	}

	public String getExKmkRateStr() {
		return NumberFormat.getCurrencyInstance().format(exKmkRate).replace("$", "");
	}

	public MA0015AEntity() {
		super();
	}

	public MA0015AEntity(Long idKey, Date exDate, String exCurrId, BigDecimal exKmkRate) {
		super();
		this.idKey = idKey;
		this.exDate = exDate;
		this.exCurrId = exCurrId;
		this.exKmkRate = exKmkRate;
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public Date getExDate() {
		return exDate;
	}

	public void setExDate(Date exDate) {
		this.exDate = exDate;
	}

	public String getExCurrId() {
		return exCurrId;
	}

	public void setExCurrId(String exCurrId) {
		this.exCurrId = exCurrId;
	}

	public BigDecimal getExKmkRate() {
		if(exKmkRate == null)
			return BigDecimal.ZERO;
		
		return exKmkRate;
	}
	
	public BigDecimal getExKmkRateBd() {		
		return exKmkRate;
	}

	public void setExKmkRate(BigDecimal exKmkRate) {
		this.exKmkRate = exKmkRate;
	}

	public Date getCreateOn() {
		return createOn;
	}

	public void setCreateOn(Date createOn) {
		this.createOn = createOn;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getModifyOn() {
		return modifyOn;
	}

	public void setModifyOn(Date modifyOn) {
		this.modifyOn = modifyOn;
	}

	public String getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getAction() {
		return "<button id =\"btnDeleteKmk\" class=\"btn btn-secondary\" onclick=\"deleteTaxRate('"+getIdKey()+"')\">" 
				+ "<i class=\"fa fa-trash\"></i>" 
				+ "</button>";
	}
	
}
