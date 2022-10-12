package com.biru.view;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * The persistent class for the VIEW_INQ_PRODUCTION database table.
 * 
 */
@Entity
@Table(name="VIEW_INQ_PRODUCTION")
@NamedQuery(name="ViewInqProduction.findAll", query="SELECT v FROM ViewInqProduction v")
public class ViewInqProduction implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_KEY")
	private String idKey;

	@Column(name="REQUEST_ID")
	private String requestId;
	
	@Column(name="TRX_CLASS")
	private String trxClass;

	@Column(name="CLIENT")
	private String client;
	
	@Column(name="TYPE_OF_COVER")
	private String typeOfCover;
	
	@Column(name="CURR")
	private String curr;
	
	@Column(name="AMOUNT")
	private BigDecimal amount;
	
	@Column(name="TRX_DATA_STATUS")
	private String trxDataStatus;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="CREATE_BY")
	private String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="CREATE_ON")
	private Date createOn;
	
	@Column(name="TRX_NON_PRO")
	private String trxNonPro;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRE_INS_START")
	private Date treInsStart;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRE_INS_END")
	private Date treInsEnd;
	
	@Column(name="TRE_ADJ_AMOUNT")
	private BigDecimal treAdjAmount;
	
	@Column(name="TRE_ADJ_STATUS")
	private String treAdjStatus;
	
	@Temporal(TemporalType.DATE)
	@Column(name="TRX_DATE")
	private Date trxDate;
	
	@Transient
	private String trxNonProDesc;
	
	@Transient
	private Integer aging;
	
	@Transient
	private String period;
	
	@Transient
	private String action;
	
	@Transient
	private String treAdjAmountFmt;
	
	@Transient
	private SimpleDateFormat dateUI = new SimpleDateFormat("dd/MM/yyyy");

	public ViewInqProduction() {
	}

	public ViewInqProduction(String idKey, String requestId, String client, String typeOfCover, String curr,
			BigDecimal amount, String trxDataStatus, String status, String createBy, Date createOn, 
			String trxNonPro, Integer aging) {
		super();
		this.idKey = idKey;
		this.requestId = requestId;
		this.client = client;
		this.typeOfCover = typeOfCover;
		this.curr = curr;
		this.amount = amount;
		this.trxDataStatus = trxDataStatus;
		this.status = status;
		this.createBy = createBy;
		this.createOn = createOn;
		this.trxNonPro = trxNonPro;
		this.aging = aging;
	}

	public String getIdKey() {
		return idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}
	
	public String getTrxClass() {
		return trxClass;
	}

	public void setTrxClass(String trxClass) {
		this.trxClass = trxClass;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getTypeOfCover() {
		return typeOfCover;
	}

	public void setTypeOfCover(String typeOfCover) {
		this.typeOfCover = typeOfCover;
	}

	public String getCurr() {
		return curr;
	}

	public void setCurr(String curr) {
		this.curr = curr;
	}

	public String getAmount() {
		return NumberFormat.getCurrencyInstance().format(amount).replace("$", "");
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getTrxDataStatus() {
		return trxDataStatus;
	}

	public void setTrxDataStatus(String trxDataStatus) {
		this.trxDataStatus = trxDataStatus;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
	
	public String getTrxNonPro() {
		return trxNonPro;
	}

	public void setTrxNonPro(String trxNonPro) {
		this.trxNonPro = trxNonPro;
	}
	
	public Date getTreInsStart() {
		return treInsStart;
	}

	public void setTreInsStart(Date treInsStart) {
		this.treInsStart = treInsStart;
	}

	public Date getTreInsEnd() {
		return treInsEnd;
	}

	public void setTreInsEnd(Date treInsEnd) {
		this.treInsEnd = treInsEnd;
	}

	public BigDecimal getTreAdjAmount() {
		return treAdjAmount;
	}

	public void setTreAdjAmount(BigDecimal treAdjAmount) {
		this.treAdjAmount = treAdjAmount;
	}

	public String getTreAdjStatus() {
		return treAdjStatus;
	}

	public void setTreAdjStatus(String treAdjStatus) {
		this.treAdjStatus = treAdjStatus;
	}

	public Date getTrxDate() {
		return trxDate;
	}

	public void setTrxDate(Date trxDate) {
		this.trxDate = trxDate;
	}

	public String getTrxNonProDesc() {
		if("N".equals(this.trxNonPro))
			return "Non Prop";
		else if("P".equals(this.trxNonPro))
			return "Prop";
		
		return trxNonProDesc;
	}

	public void setTrxNonProDesc(String trxNonProDesc) {
		this.trxNonProDesc = trxNonProDesc;
	}

	public Integer getAging() {
		if(aging == null)
			return null;
		else if(aging < 0)
			return 0;
		
		return Math.abs(aging);
	}

	public void setAging(Integer aging) {
		this.aging = aging;
	}
	
	public String getPeriod() {
		String periodStart = treInsStart == null ? "" : dateUI.format(treInsStart);			
		String periodEnd = treInsEnd == null ? "" : dateUI.format(treInsEnd);		
		
		return periodStart + " - " + periodEnd;
	}

	public void setPeriod(String period) {
		this.period = period;
	}
	
	public String getTreAdjAmountFmt() {
		if(treAdjAmount != null)
			return NumberFormat.getCurrencyInstance().format(treAdjAmount).replace("$", "");
		
		return treAdjAmountFmt;
	}

	public void setTreAdjAmountFmt(String treAdjAmountFmt) {
		this.treAdjAmountFmt = treAdjAmountFmt;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

}