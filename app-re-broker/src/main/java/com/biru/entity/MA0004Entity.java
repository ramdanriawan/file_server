package com.biru.entity;

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

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name="MA0004")
@NamedQuery(name="MA0004Entity.findAll", query="SELECT m FROM MA0004Entity m")
public class MA0004Entity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;

	@Column(name = "COA_CODE")
	private String coaCode;
	
	@Column(name = "COA_LEVEL")
	private Integer coaLevel;
	
	@Column(name = "COA_DESCRIP")
	private String coaDescript;
	
	@Column(name = "COA_CURR_ID")	
	private String coaCurrId;
	
	@Column(name = "COA_HEADER")
	private Character coaHeader;
	
	@Column(name = "COA_NORMAL")
	private Character coaNormal;
	
	@Column(name = "COA_ROLL_UP")
	private String coaRoleUp;
	
	@Column(name = "COA_CLASS")
	private String coaClass;
	
	@Column(name = "COA_DATA_STATUS")
	private String coaDataStatus;
	
	@Column(name = "COA_PRINT_BS")
	private Character coaPrintBs;
	
	@Column(name = "COA_PRINT_PL")
	private Character coaPrintPl;
	
	@Column(name = "COA_PRINT_CF")
	private Character coaPrintCf;
	
	@Column(name = "COA_CETAK_AT")
	private Character coaCetakAt;
	
	@Column(name = "COA_BANK_BK")
	private Character coaBankBk = '0';
	
	@Column(name = "COA_REVALT")
	private Character coaRevalt = '0';
	
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
	
	@Column(name = "COA_PL_NEW")
	private String coaPlNew;
	
	@Column(name = "COA_BS_NEW")
	private String coaBsNew;
	
	@Transient
	private String coaDataStatusStr;
	
	@Transient
	private String coaClassStr;
	
	@Transient
	private Integer coaRowBs;
	
	@Transient
	private String rowHeadBs;
	
	@Transient
	private String positionBs;
	
	@Transient
	private String coaGroupBs;
	
	@Transient
	private String coaGroupPl;
	
	@Transient
	private Integer coaRowPl;

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getCoaCode() {
		return coaCode;
	}

	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	public Integer getCoaLevel() {
		return coaLevel;
	}

	public void setCoaLevel(Integer coaLevel) {
		this.coaLevel = coaLevel;
	}

	public String getCoaDescript() {
		return coaDescript;
	}

	public void setCoaDescript(String coaDescript) {
		this.coaDescript = coaDescript;
	}

	public String getCoaCurrId() {
		return coaCurrId;
	}

	public void setCoaCurrId(String coaCurrId) {
		this.coaCurrId = coaCurrId;
	}

	public Character getCoaHeader() {
		return coaHeader;
	}

	public void setCoaHeader(Character coaHeader) {
		this.coaHeader = coaHeader;
	}

	public Character getCoaNormal() {
		return coaNormal;
	}

	public void setCoaNormal(Character coaNormal) {
		this.coaNormal = coaNormal;
	}

	public String getCoaRoleUp() {
		return coaRoleUp;
	}

	public void setCoaRoleUp(String coaRoleUp) {
		this.coaRoleUp = coaRoleUp;
	}

	public String getCoaClass() {
		return coaClass;
	}

	public void setCoaClass(String coaClass) {
		this.coaClass = coaClass;
	}

	public String getCoaDataStatus() {
		return coaDataStatus;
	}

	public void setCoaDataStatus(String coaDataStatus) {
		this.coaDataStatus = coaDataStatus;
	}

	public Character getCoaPrintBs() {
		return coaPrintBs;
	}

	public void setCoaPrintBs(Character coaPrintBs) {
		this.coaPrintBs = coaPrintBs;
	}

	public Character getCoaPrintPl() {
		return coaPrintPl;
	}

	public void setCoaPrintPl(Character coaPrintPl) {
		this.coaPrintPl = coaPrintPl;
	}

	public Character getCoaPrintCf() {
		return coaPrintCf;
	}

	public void setCoaPrintCf(Character coaPrintCf) {
		this.coaPrintCf = coaPrintCf;
	}

	public Character getCoaCetakAt() {
		return coaCetakAt;
	}

	public void setCoaCetakAt(Character coaCetakAt) {
		this.coaCetakAt = coaCetakAt;
	}
	
	public Character getCoaBankBk() {
		return coaBankBk;
	}

	public void setCoaBankBk(Character coaBankBk) {
		this.coaBankBk = coaBankBk;
	}

	public Character getCoaRevalt() {
		return coaRevalt;
	}

	public void setCoaRevalt(Character coaRevalt) {
		this.coaRevalt = coaRevalt;
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

	public String getCoaDataStatusStr() {
		return coaDataStatusStr;
	}

	public void setCoaDataStatusStr(String coaDataStatusStr) {
		this.coaDataStatusStr = coaDataStatusStr;
	}
	
	public String getCoaPlNew() {
		return coaPlNew;
	}

	public void setCoaPlNew(String coaPlNew) {
		this.coaPlNew = coaPlNew;
	}

	public String getCoaBsNew() {
		return coaBsNew;
	}

	public void setCoaBsNew(String coaBsNew) {
		this.coaBsNew = coaBsNew;
	}

	public String getCoaClassStr() {
		return coaClassStr;
	}

	public void setCoaClassStr(String coaClassStr) {
		this.coaClassStr = coaClassStr;
	}

	public Integer getCoaRowBs() {
		return coaRowBs;
	}

	public void setCoaRowBs(Integer coaRowBs) {
		this.coaRowBs = coaRowBs;
	}

	public Integer getCoaRowPl() {
		return coaRowPl;
	}

	public void setCoaRowPl(Integer coaRowPl) {
		this.coaRowPl = coaRowPl;
	}

	public String getRowHeadBs() {
		return rowHeadBs;
	}

	public void setRowHeadBs(String rowHeadBs) {
		this.rowHeadBs = rowHeadBs;
	}

	public String getPositionBs() {
		return positionBs;
	}

	public void setPositionBs(String positionBs) {
		this.positionBs = positionBs;
	}

	public String getCoaGroupBs() {
		return coaGroupBs;
	}

	public void setCoaGroupBs(String coaGroupBs) {
		this.coaGroupBs = coaGroupBs;
	}

	public String getCoaGroupPl() {
		return coaGroupPl;
	}

	public void setCoaGroupPl(String coaGroupPl) {
		this.coaGroupPl = coaGroupPl;
	}

}
