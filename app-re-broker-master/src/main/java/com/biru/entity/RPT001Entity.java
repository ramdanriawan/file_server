package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/* Helper for Report Trial Balance */

@Entity
@Table(name="RPT001")
@NamedQuery(name="RPT001Entity.findAll", query="SELECT r FROM RPT001Entity r")
public class RPT001Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name = "ID_REQUEST")
	private String idRequest;
	
	@Column(name = "COA_CODE")
	private String coaCode;
	
	@Column(name = "COA_DESCRIPTION")
	private String coaDescription;
	
	@Column(name = "COA_HEADER")
	private Character coaHeader;
	
	@Column(name = "COA_NORMAL")
	private Character coaNormal;
	
	@Column(name = "COA_ROLL_UP")
	private String coaRollUp;
	
	@Column(name = "COA_LEVEL")
	private Integer coaLevel;
	
	@Column(name = "BB_DEBIT_ORG")
	private BigDecimal bbDebitOrg = BigDecimal.ZERO;
	
	@Column(name = "BB_CREDIT_ORG")
	private BigDecimal bbCreditOrg = BigDecimal.ZERO;
	
	@Column(name = "BB_DEBIT_IDR")
	private BigDecimal bbDebitIdr = BigDecimal.ZERO;
	
	@Column(name = "BB_CREDIT_IDR")
	private BigDecimal bbCreditIdr = BigDecimal.ZERO;
	
	@Column(name = "MUT_DEBIT_ORG")
	private BigDecimal mutDebitOrg = BigDecimal.ZERO;
	
	@Column(name = "MUT_CREDIT_ORG")
	private BigDecimal mutCreditOrg = BigDecimal.ZERO;
	
	@Column(name = "MUT_DEBIT_IDR")
	private BigDecimal mutDebitIdr = BigDecimal.ZERO;
	
	@Column(name = "MUT_CREDIT_IDR")
	private BigDecimal mutCreditIdr = BigDecimal.ZERO;
	
	@Column(name = "EB_DEBIT_ORG")
	private BigDecimal ebDebitOrg = BigDecimal.ZERO;
	
	@Column(name = "EB_CREDIT_ORG")
	private BigDecimal ebCreditOrg = BigDecimal.ZERO;
	
	@Column(name = "EB_DEBIT_IDR")
	private BigDecimal ebDebitIdr = BigDecimal.ZERO;
	
	@Column(name = "EB_CREDIT_IDR")
	private BigDecimal ebCreditIdr = BigDecimal.ZERO;
	
	public RPT001Entity() {		
	}
	
	public RPT001Entity(String coaCode, String coaDescription, 
			Character coaHeader, Character coaNormal, 
			String coaRollUp, Integer coaLevel, 
			BigDecimal bbDebitOrg, BigDecimal bbCreditOrg, 
			BigDecimal bbDebitIdr, BigDecimal bbCreditIdr) {
		super();
		this.coaCode = coaCode;
		this.coaDescription = coaDescription;
		this.coaHeader = coaHeader;
		this.coaNormal = coaNormal;
		this.coaRollUp = coaRollUp;
		this.coaLevel = coaLevel;
		this.bbDebitOrg = bbDebitOrg;
		this.bbCreditOrg = bbCreditOrg;
		this.bbDebitIdr = bbDebitIdr;
		this.bbCreditIdr = bbCreditIdr;
	}

	public RPT001Entity(String coaCode, BigDecimal mutDebitOrg, 
			BigDecimal mutCreditOrg, BigDecimal mutDebitIdr,
			BigDecimal mutCreditIdr) {
		super();
		this.coaCode = coaCode;
		this.mutDebitOrg = mutDebitOrg;
		this.mutCreditOrg = mutCreditOrg;
		this.mutDebitIdr = mutDebitIdr;
		this.mutCreditIdr = mutCreditIdr;
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getIdRequest() {
		return idRequest;
	}

	public void setIdRequest(String idRequest) {
		this.idRequest = idRequest;
	}

	public String getCoaCode() {
		return coaCode;
	}

	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	public String getCoaDescription() {
		return coaDescription;
	}

	public void setCoaDescription(String coaDescription) {
		this.coaDescription = coaDescription;
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

	public String getCoaRollUp() {
		return coaRollUp;
	}

	public void setCoaRollUp(String coaRollUp) {
		this.coaRollUp = coaRollUp;
	}

	public Integer getCoaLevel() {
		return coaLevel;
	}

	public void setCoaLevel(Integer coaLevel) {
		this.coaLevel = coaLevel;
	}

	public BigDecimal getBbDebitOrg() {
		return bbDebitOrg;
	}

	public void setBbDebitOrg(BigDecimal bbDebitOrg) {
		this.bbDebitOrg = bbDebitOrg;
	}

	public BigDecimal getBbCreditOrg() {
		return bbCreditOrg;
	}

	public void setBbCreditOrg(BigDecimal bbCreditOrg) {
		this.bbCreditOrg = bbCreditOrg;
	}

	public BigDecimal getBbDebitIdr() {
		return bbDebitIdr;
	}

	public void setBbDebitIdr(BigDecimal bbDebitIdr) {
		this.bbDebitIdr = bbDebitIdr;
	}

	public BigDecimal getBbCreditIdr() {
		return bbCreditIdr;
	}

	public void setBbCreditIdr(BigDecimal bbCreditIdr) {
		this.bbCreditIdr = bbCreditIdr;
	}

	public BigDecimal getMutDebitOrg() {
		return mutDebitOrg;
	}

	public void setMutDebitOrg(BigDecimal mutDebitOrg) {
		this.mutDebitOrg = mutDebitOrg;
	}

	public BigDecimal getMutCreditOrg() {
		return mutCreditOrg;
	}

	public void setMutCreditOrg(BigDecimal mutCreditOrg) {
		this.mutCreditOrg = mutCreditOrg;
	}

	public BigDecimal getMutDebitIdr() {
		return mutDebitIdr;
	}

	public void setMutDebitIdr(BigDecimal mutDebitIdr) {
		this.mutDebitIdr = mutDebitIdr;
	}

	public BigDecimal getMutCreditIdr() {
		return mutCreditIdr;
	}

	public void setMutCreditIdr(BigDecimal mutCreditIdr) {
		this.mutCreditIdr = mutCreditIdr;
	}

	public BigDecimal getEbDebitOrg() {
		return ebDebitOrg;
	}

	public void setEbDebitOrg(BigDecimal ebDebitOrg) {
		this.ebDebitOrg = ebDebitOrg;
	}

	public BigDecimal getEbCreditOrg() {
		return ebCreditOrg;
	}

	public void setEbCreditOrg(BigDecimal ebCreditOrg) {
		this.ebCreditOrg = ebCreditOrg;
	}

	public BigDecimal getEbDebitIdr() {
		return ebDebitIdr;
	}

	public void setEbDebitIdr(BigDecimal ebDebitIdr) {
		this.ebDebitIdr = ebDebitIdr;
	}

	public BigDecimal getEbCreditIdr() {
		return ebCreditIdr;
	}

	public void setEbCreditIdr(BigDecimal ebCreditIdr) {
		this.ebCreditIdr = ebCreditIdr;
	}
	
}
