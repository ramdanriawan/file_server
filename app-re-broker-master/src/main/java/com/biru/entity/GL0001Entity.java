package com.biru.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name="GL0001")
@NamedQuery(name="GL0001Entity.findAll", query="SELECT m FROM GL0001Entity m")
public class GL0001Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name = "GL_BAL_YEAR")
	private String glBalYear;
	
	@Column(name = "COA_CODE")
	private String coaCode;
	
	@Column(name = "GL_BAL_CREDIT0")
	private BigDecimal glBalCredit0 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT0")
	private BigDecimal glBalDebit0 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT1")
	private BigDecimal glBalCredit1 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT1")
	private BigDecimal glBalDebit1 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT2")
	private BigDecimal glBalCredit2 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT2")
	private BigDecimal glBalDebit2 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT3")
	private BigDecimal glBalCredit3 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT3")
	private BigDecimal glBalDebit3 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT4")
	private BigDecimal glBalCredit4 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT4")
	private BigDecimal glBalDebit4 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT5")
	private BigDecimal glBalCredit5 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT5")
	private BigDecimal glBalDebit5 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT6")
	private BigDecimal glBalCredit6 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT6")
	private BigDecimal glBalDebit6 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT7")
	private BigDecimal glBalCredit7 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT7")
	private BigDecimal glBalDebit7 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT8")
	private BigDecimal glBalCredit8 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT8")
	private BigDecimal glBalDebit8 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT9")
	private BigDecimal glBalCredit9 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT9")
	private BigDecimal glBalDebit9 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT10")
	private BigDecimal glBalCredit10 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT10")
	private BigDecimal glBalDebit10 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT11")
	private BigDecimal glBalCredit11 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT11")
	private BigDecimal glBalDebit11 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT12")
	private BigDecimal glBalCredit12 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT12")
	private BigDecimal glBalDebit12 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CREDIT13")
	private BigDecimal glBalCredit13 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DEBIT13")
	private BigDecimal glBalDebit13 = BigDecimal.ZERO;

	@Column(name = "GL_BAL_CIDR0")
	private BigDecimal glBalCIdr0 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR0")
	private BigDecimal glBalDIdr0 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR1")
	private BigDecimal glBalCIdr1 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR1")
	private BigDecimal glBalDIdr1 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR2")
	private BigDecimal glBalCIdr2 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR2") 
	private BigDecimal glBalDIdr2 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR3")
	private BigDecimal glBalCIdr3 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR3")
	private BigDecimal glBalDIdr3 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR4")
	private BigDecimal glBalCIdr4 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR4")
	private BigDecimal glBalDIdr4 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR5")
	private BigDecimal glBalCIdr5 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR5")
	private BigDecimal glBalDIdr5 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR6")
	private BigDecimal glBalCIdr6 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR6")
	private BigDecimal glBalDIdr6 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR7")
	private BigDecimal glBalCIdr7 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR7")
	private BigDecimal glBalDIdr7 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR8")
	private BigDecimal glBalCIdr8 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR8")
	private BigDecimal glBalDIdr8 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR9")
	private BigDecimal glBalCIdr9 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR9")
	private BigDecimal glBalDIdr9 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR10")
	private BigDecimal glBalCIdr10 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR10")
	private BigDecimal glBalDIdr10 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR11")
	private BigDecimal glBalCIdr11 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR11")
	private BigDecimal glBalDIdr11 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR12")
	private BigDecimal glBalCIdr12 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR12")
	private BigDecimal glBalDIdr12 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_CIDR13")
	private BigDecimal glBalCIdr13 = BigDecimal.ZERO;
	
	@Column(name = "GL_BAL_DIDR13")
	private BigDecimal glBalDIdr13 = BigDecimal.ZERO;
	
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
	private String coaDescript;
	
	@Transient
	private Character coaNormal;
	
	public GL0001Entity() {
	}
	
	public GL0001Entity(String coaCode, 
			BigDecimal glBalCredit1, BigDecimal glBalDebit1, BigDecimal glBalCIdr1, BigDecimal glBalDIdr1,
			BigDecimal glBalCredit2, BigDecimal glBalDebit2, BigDecimal glBalCIdr2, BigDecimal glBalDIdr2,
			BigDecimal glBalCredit3, BigDecimal glBalDebit3, BigDecimal glBalCIdr3, BigDecimal glBalDIdr3,
			BigDecimal glBalCredit4, BigDecimal glBalDebit4, BigDecimal glBalCIdr4, BigDecimal glBalDIdr4,
			BigDecimal glBalCredit5, BigDecimal glBalDebit5, BigDecimal glBalCIdr5, BigDecimal glBalDIdr5,
			BigDecimal glBalCredit6, BigDecimal glBalDebit6, BigDecimal glBalCIdr6, BigDecimal glBalDIdr6,
			BigDecimal glBalCredit7, BigDecimal glBalDebit7, BigDecimal glBalCIdr7, BigDecimal glBalDIdr7,
			BigDecimal glBalCredit8, BigDecimal glBalDebit8, BigDecimal glBalCIdr8, BigDecimal glBalDIdr8,
			BigDecimal glBalCredit9, BigDecimal glBalDebit9, BigDecimal glBalCIdr9, BigDecimal glBalDIdr9,
			BigDecimal glBalCredit10, BigDecimal glBalDebit10, BigDecimal glBalCIdr10, BigDecimal glBalDIdr10,
			BigDecimal glBalCredit11, BigDecimal glBalDebit11, BigDecimal glBalCIdr11, BigDecimal glBalDIdr11,
			BigDecimal glBalCredit12, BigDecimal glBalDebit12, BigDecimal glBalCIdr12, BigDecimal glBalDIdr12) {
		super();
		this.coaCode = coaCode;
		this.glBalCredit1 = glBalCredit1;
		this.glBalDebit1 = glBalDebit1;
		this.glBalCIdr1 = glBalCIdr1;
		this.glBalDIdr1 = glBalDIdr1;
		this.glBalCredit2 = glBalCredit2;
		this.glBalDebit2 = glBalDebit2;
		this.glBalCIdr2 = glBalCIdr2;
		this.glBalDIdr2 = glBalDIdr2;
		this.glBalCredit3 = glBalCredit3;
		this.glBalDebit3 = glBalDebit3;
		this.glBalCIdr3 = glBalCIdr3;
		this.glBalDIdr3 = glBalDIdr3;
		this.glBalCredit4 = glBalCredit4;
		this.glBalDebit4 = glBalDebit4;
		this.glBalCIdr4 = glBalCIdr4;
		this.glBalDIdr4 = glBalDIdr4;
		this.glBalCredit5 = glBalCredit5;
		this.glBalDebit5 = glBalDebit5;
		this.glBalCIdr5 = glBalCIdr5;
		this.glBalDIdr5 = glBalDIdr5;
		this.glBalCredit6 = glBalCredit6;
		this.glBalDebit6 = glBalDebit6;
		this.glBalCIdr6 = glBalCIdr6;
		this.glBalDIdr6 = glBalDIdr6;
		this.glBalCredit7 = glBalCredit7;
		this.glBalDebit7 = glBalDebit7;
		this.glBalCIdr7 = glBalCIdr7;
		this.glBalDIdr7 = glBalDIdr7;
		this.glBalCredit8 = glBalCredit8;
		this.glBalDebit8 = glBalDebit8;
		this.glBalCIdr8 = glBalCIdr8;
		this.glBalDIdr8 = glBalDIdr8;
		this.glBalCredit9 = glBalCredit9;
		this.glBalDebit9 = glBalDebit9;
		this.glBalCIdr9 = glBalCIdr9;
		this.glBalDIdr9 = glBalDIdr9;
		this.glBalCredit10 = glBalCredit10;
		this.glBalDebit10 = glBalDebit10;
		this.glBalCIdr10 = glBalCIdr10;
		this.glBalDIdr10 = glBalDIdr10;
		this.glBalCredit11 = glBalCredit11;
		this.glBalDebit11 = glBalDebit11;
		this.glBalCIdr11 = glBalCIdr11;
		this.glBalDIdr11 = glBalDIdr11;
		this.glBalCredit12 = glBalCredit12;
		this.glBalDebit12 = glBalDebit12;
		this.glBalCIdr12 = glBalCIdr12;
		this.glBalDIdr12 = glBalDIdr12;
	}
	
	public GL0001Entity(String coaCode, Character coaNormal, 
			BigDecimal glBalCIdr1, BigDecimal glBalDIdr1,
			BigDecimal glBalCIdr2, BigDecimal glBalDIdr2,
			BigDecimal glBalCIdr3, BigDecimal glBalDIdr3,
			BigDecimal glBalCIdr4, BigDecimal glBalDIdr4,
			BigDecimal glBalCIdr5, BigDecimal glBalDIdr5,
			BigDecimal glBalCIdr6, BigDecimal glBalDIdr6,
			BigDecimal glBalCIdr7, BigDecimal glBalDIdr7,
			BigDecimal glBalCIdr8, BigDecimal glBalDIdr8,
			BigDecimal glBalCIdr9, BigDecimal glBalDIdr9,
			BigDecimal glBalCIdr10, BigDecimal glBalDIdr10,
			BigDecimal glBalCIdr11, BigDecimal glBalDIdr11,
			BigDecimal glBalCIdr12, BigDecimal glBalDIdr12) {
		super();
		this.coaCode = coaCode;
		this.coaNormal = coaNormal;
		this.glBalCIdr1 = glBalCIdr1;
		this.glBalDIdr1 = glBalDIdr1;
		this.glBalCIdr2 = glBalCIdr2;
		this.glBalDIdr2 = glBalDIdr2;
		this.glBalCIdr3 = glBalCIdr3;
		this.glBalDIdr3 = glBalDIdr3;
		this.glBalCIdr4 = glBalCIdr4;
		this.glBalDIdr4 = glBalDIdr4;
		this.glBalCIdr5 = glBalCIdr5;
		this.glBalDIdr5 = glBalDIdr5;
		this.glBalCIdr6 = glBalCIdr6;
		this.glBalDIdr6 = glBalDIdr6;
		this.glBalCIdr7 = glBalCIdr7;
		this.glBalDIdr7 = glBalDIdr7;
		this.glBalCIdr8 = glBalCIdr8;
		this.glBalDIdr8 = glBalDIdr8;
		this.glBalCIdr9 = glBalCIdr9;
		this.glBalDIdr9 = glBalDIdr9;
		this.glBalCIdr10 = glBalCIdr10;
		this.glBalDIdr10 = glBalDIdr10;
		this.glBalCIdr11 = glBalCIdr11;
		this.glBalDIdr11 = glBalDIdr11;
		this.glBalCIdr12 = glBalCIdr12;
		this.glBalDIdr12 = glBalDIdr12;
	}
	
	public GL0001Entity(String coaCode, BigDecimal glBalCredit13, 
			BigDecimal glBalDebit13, BigDecimal glBalCIdr13,
			BigDecimal glBalDIdr13) {
		super();
		this.coaCode = coaCode;
		this.glBalCredit13 = glBalCredit13;
		this.glBalDebit13 = glBalDebit13;
		this.glBalCIdr13 = glBalCIdr13;
		this.glBalDIdr13 = glBalDIdr13;
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getGlBalYear() {
		return glBalYear;
	}

	public void setGlBalYear(String glBalYear) {
		this.glBalYear = glBalYear;
	}

	public String getCoaCode() {
		return coaCode;
	}

	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	public BigDecimal getGlBalCredit0() {
		return glBalCredit0;
	}

	public void setGlBalCredit0(BigDecimal glBalCredit0) {
		this.glBalCredit0 = glBalCredit0;
	}

	public BigDecimal getGlBalDebit0() {
		return glBalDebit0;
	}

	public void setGlBalDebit0(BigDecimal glBalDebit0) {
		this.glBalDebit0 = glBalDebit0;
	}

	public BigDecimal getGlBalCredit1() {
		return glBalCredit1;
	}

	public void setGlBalCredit1(BigDecimal glBalCredit1) {
		this.glBalCredit1 = glBalCredit1;
	}

	public BigDecimal getGlBalDebit1() {
		return glBalDebit1;
	}

	public void setGlBalDebit1(BigDecimal glBalDebit1) {
		this.glBalDebit1 = glBalDebit1;
	}

	public BigDecimal getGlBalCredit2() {
		return glBalCredit2;
	}

	public void setGlBalCredit2(BigDecimal glBalCredit2) {
		this.glBalCredit2 = glBalCredit2;
	}

	public BigDecimal getGlBalDebit2() {
		return glBalDebit2;
	}

	public void setGlBalDebit2(BigDecimal glBalDebit2) {
		this.glBalDebit2 = glBalDebit2;
	}

	public BigDecimal getGlBalCredit3() {
		return glBalCredit3;
	}

	public void setGlBalCredit3(BigDecimal glBalCredit3) {
		this.glBalCredit3 = glBalCredit3;
	}

	public BigDecimal getGlBalDebit3() {
		return glBalDebit3;
	}

	public void setGlBalDebit3(BigDecimal glBalDebit3) {
		this.glBalDebit3 = glBalDebit3;
	}

	public BigDecimal getGlBalCredit4() {
		return glBalCredit4;
	}

	public void setGlBalCredit4(BigDecimal glBalCredit4) {
		this.glBalCredit4 = glBalCredit4;
	}

	public BigDecimal getGlBalDebit4() {
		return glBalDebit4;
	}

	public void setGlBalDebit4(BigDecimal glBalDebit4) {
		this.glBalDebit4 = glBalDebit4;
	}

	public BigDecimal getGlBalCredit5() {
		return glBalCredit5;
	}

	public void setGlBalCredit5(BigDecimal glBalCredit5) {
		this.glBalCredit5 = glBalCredit5;
	}

	public BigDecimal getGlBalDebit5() {
		return glBalDebit5;
	}

	public void setGlBalDebit5(BigDecimal glBalDebit5) {
		this.glBalDebit5 = glBalDebit5;
	}

	public BigDecimal getGlBalCredit6() {
		return glBalCredit6;
	}

	public void setGlBalCredit6(BigDecimal glBalCredit6) {
		this.glBalCredit6 = glBalCredit6;
	}

	public BigDecimal getGlBalDebit6() {
		return glBalDebit6;
	}

	public void setGlBalDebit6(BigDecimal glBalDebit6) {
		this.glBalDebit6 = glBalDebit6;
	}

	public BigDecimal getGlBalCredit7() {
		return glBalCredit7;
	}

	public void setGlBalCredit7(BigDecimal glBalCredit7) {
		this.glBalCredit7 = glBalCredit7;
	}

	public BigDecimal getGlBalDebit7() {
		return glBalDebit7;
	}

	public void setGlBalDebit7(BigDecimal glBalDebit7) {
		this.glBalDebit7 = glBalDebit7;
	}

	public BigDecimal getGlBalCredit8() {
		return glBalCredit8;
	}

	public void setGlBalCredit8(BigDecimal glBalCredit8) {
		this.glBalCredit8 = glBalCredit8;
	}

	public BigDecimal getGlBalDebit8() {
		return glBalDebit8;
	}

	public void setGlBalDebit8(BigDecimal glBalDebit8) {
		this.glBalDebit8 = glBalDebit8;
	}

	public BigDecimal getGlBalCredit9() {
		return glBalCredit9;
	}

	public void setGlBalCredit9(BigDecimal glBalCredit9) {
		this.glBalCredit9 = glBalCredit9;
	}

	public BigDecimal getGlBalDebit9() {
		return glBalDebit9;
	}

	public void setGlBalDebit9(BigDecimal glBalDebit9) {
		this.glBalDebit9 = glBalDebit9;
	}

	public BigDecimal getGlBalCredit10() {
		return glBalCredit10;
	}

	public void setGlBalCredit10(BigDecimal glBalCredit10) {
		this.glBalCredit10 = glBalCredit10;
	}

	public BigDecimal getGlBalDebit10() {
		return glBalDebit10;
	}

	public void setGlBalDebit10(BigDecimal glBalDebit10) {
		this.glBalDebit10 = glBalDebit10;
	}

	public BigDecimal getGlBalCredit11() {
		return glBalCredit11;
	}

	public void setGlBalCredit11(BigDecimal glBalCredit11) {
		this.glBalCredit11 = glBalCredit11;
	}

	public BigDecimal getGlBalDebit11() {
		return glBalDebit11;
	}

	public void setGlBalDebit11(BigDecimal glBalDebit11) {
		this.glBalDebit11 = glBalDebit11;
	}

	public BigDecimal getGlBalCredit12() {
		return glBalCredit12;
	}

	public void setGlBalCredit12(BigDecimal glBalCredit12) {
		this.glBalCredit12 = glBalCredit12;
	}

	public BigDecimal getGlBalDebit12() {
		return glBalDebit12;
	}

	public void setGlBalDebit12(BigDecimal glBalDebit12) {
		this.glBalDebit12 = glBalDebit12;
	}

	public BigDecimal getGlBalCredit13() {
		return glBalCredit13;
	}

	public void setGlBalCredit13(BigDecimal glBalCredit13) {
		this.glBalCredit13 = glBalCredit13;
	}

	public BigDecimal getGlBalDebit13() {
		return glBalDebit13;
	}

	public void setGlBalDebit13(BigDecimal glBalDebit13) {
		this.glBalDebit13 = glBalDebit13;
	}

	public BigDecimal getGlBalCIdr0() {
		return glBalCIdr0;
	}

	public void setGlBalCIdr0(BigDecimal glBalCIdr0) {
		this.glBalCIdr0 = glBalCIdr0;
	}

	public BigDecimal getGlBalDIdr0() {
		return glBalDIdr0;
	}

	public void setGlBalDIdr0(BigDecimal glBalDIdr0) {
		this.glBalDIdr0 = glBalDIdr0;
	}

	public BigDecimal getGlBalCIdr1() {
		return glBalCIdr1;
	}

	public void setGlBalCIdr1(BigDecimal glBalCIdr1) {
		this.glBalCIdr1 = glBalCIdr1;
	}

	public BigDecimal getGlBalDIdr1() {
		return glBalDIdr1;
	}

	public void setGlBalDIdr1(BigDecimal glBalDIdr1) {
		this.glBalDIdr1 = glBalDIdr1;
	}

	public BigDecimal getGlBalCIdr2() {
		return glBalCIdr2;
	}

	public void setGlBalCIdr2(BigDecimal glBalCIdr2) {
		this.glBalCIdr2 = glBalCIdr2;
	}

	public BigDecimal getGlBalDIdr2() {
		return glBalDIdr2;
	}

	public void setGlBalDIdr2(BigDecimal glBalDIdr2) {
		this.glBalDIdr2 = glBalDIdr2;
	}

	public BigDecimal getGlBalCIdr3() {
		return glBalCIdr3;
	}

	public void setGlBalCIdr3(BigDecimal glBalCIdr3) {
		this.glBalCIdr3 = glBalCIdr3;
	}

	public BigDecimal getGlBalDIdr3() {
		return glBalDIdr3;
	}

	public void setGlBalDIdr3(BigDecimal glBalDIdr3) {
		this.glBalDIdr3 = glBalDIdr3;
	}

	public BigDecimal getGlBalCIdr4() {
		return glBalCIdr4;
	}

	public void setGlBalCIdr4(BigDecimal glBalCIdr4) {
		this.glBalCIdr4 = glBalCIdr4;
	}

	public BigDecimal getGlBalDIdr4() {
		return glBalDIdr4;
	}

	public void setGlBalDIdr4(BigDecimal glBalDIdr4) {
		this.glBalDIdr4 = glBalDIdr4;
	}

	public BigDecimal getGlBalCIdr5() {
		return glBalCIdr5;
	}

	public void setGlBalCIdr5(BigDecimal glBalCIdr5) {
		this.glBalCIdr5 = glBalCIdr5;
	}

	public BigDecimal getGlBalDIdr5() {
		return glBalDIdr5;
	}

	public void setGlBalDIdr5(BigDecimal glBalDIdr5) {
		this.glBalDIdr5 = glBalDIdr5;
	}

	public BigDecimal getGlBalCIdr6() {
		return glBalCIdr6;
	}

	public void setGlBalCIdr6(BigDecimal glBalCIdr6) {
		this.glBalCIdr6 = glBalCIdr6;
	}

	public BigDecimal getGlBalDIdr6() {
		return glBalDIdr6;
	}

	public void setGlBalDIdr6(BigDecimal glBalDIdr6) {
		this.glBalDIdr6 = glBalDIdr6;
	}

	public BigDecimal getGlBalCIdr7() {
		return glBalCIdr7;
	}

	public void setGlBalCIdr7(BigDecimal glBalCIdr7) {
		this.glBalCIdr7 = glBalCIdr7;
	}

	public BigDecimal getGlBalDIdr7() {
		return glBalDIdr7;
	}

	public void setGlBalDIdr7(BigDecimal glBalDIdr7) {
		this.glBalDIdr7 = glBalDIdr7;
	}

	public BigDecimal getGlBalCIdr8() {
		return glBalCIdr8;
	}

	public void setGlBalCIdr8(BigDecimal glBalCIdr8) {
		this.glBalCIdr8 = glBalCIdr8;
	}

	public BigDecimal getGlBalDIdr8() {
		return glBalDIdr8;
	}

	public void setGlBalDIdr8(BigDecimal glBalDIdr8) {
		this.glBalDIdr8 = glBalDIdr8;
	}

	public BigDecimal getGlBalCIdr9() {
		return glBalCIdr9;
	}

	public void setGlBalCIdr9(BigDecimal glBalCIdr9) {
		this.glBalCIdr9 = glBalCIdr9;
	}

	public BigDecimal getGlBalDIdr9() {
		return glBalDIdr9;
	}

	public void setGlBalDIdr9(BigDecimal glBalDIdr9) {
		this.glBalDIdr9 = glBalDIdr9;
	}

	public BigDecimal getGlBalCIdr10() {
		return glBalCIdr10;
	}

	public void setGlBalCIdr10(BigDecimal glBalCIdr10) {
		this.glBalCIdr10 = glBalCIdr10;
	}

	public BigDecimal getGlBalDIdr10() {
		return glBalDIdr10;
	}

	public void setGlBalDIdr10(BigDecimal glBalDIdr10) {
		this.glBalDIdr10 = glBalDIdr10;
	}

	public BigDecimal getGlBalCIdr11() {
		return glBalCIdr11;
	}

	public void setGlBalCIdr11(BigDecimal glBalCIdr11) {
		this.glBalCIdr11 = glBalCIdr11;
	}

	public BigDecimal getGlBalDIdr11() {
		return glBalDIdr11;
	}

	public void setGlBalDIdr11(BigDecimal glBalDIdr11) {
		this.glBalDIdr11 = glBalDIdr11;
	}

	public BigDecimal getGlBalCIdr12() {
		return glBalCIdr12;
	}

	public void setGlBalCIdr12(BigDecimal glBalCIdr12) {
		this.glBalCIdr12 = glBalCIdr12;
	}

	public BigDecimal getGlBalDIdr12() {
		return glBalDIdr12;
	}

	public void setGlBalDIdr12(BigDecimal glBalDIdr12) {
		this.glBalDIdr12 = glBalDIdr12;
	}

	public BigDecimal getGlBalCIdr13() {
		return glBalCIdr13;
	}

	public void setGlBalCIdr13(BigDecimal glBalCIdr13) {
		this.glBalCIdr13 = glBalCIdr13;
	}

	public BigDecimal getGlBalDIdr13() {
		return glBalDIdr13;
	}

	public void setGlBalDIdr13(BigDecimal glBalDIdr13) {
		this.glBalDIdr13 = glBalDIdr13;
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

	public String getCoaDescript() {
		return coaDescript;
	}

	public void setCoaDescript(String coaDescript) {
		this.coaDescript = coaDescript;
	}
	
	public Character getCoaNormal() {
		return coaNormal;
	}

	public void setCoaNormal(Character coaNormal) {
		this.coaNormal = coaNormal;
	}

	@Override
	public String toString() {
		return "GL0001Entity [idKey=" + idKey + ", glBalYear=" + glBalYear + ", coaCode=" + coaCode + ", glBalCredit0="
				+ glBalCredit0 + ", glBalDebit0=" + glBalDebit0 + ", glBalCredit1=" + glBalCredit1 + ", glBalDebit1="
				+ glBalDebit1 + ", glBalCredit2=" + glBalCredit2 + ", glBalDebit2=" + glBalDebit2 + ", glBalCredit3="
				+ glBalCredit3 + ", glBalDebit3=" + glBalDebit3 + ", glBalCredit4=" + glBalCredit4 + ", glBalDebit4="
				+ glBalDebit4 + ", glBalCredit5=" + glBalCredit5 + ", glBalDebit5=" + glBalDebit5 + ", glBalCredit6="
				+ glBalCredit6 + ", glBalDebit6=" + glBalDebit6 + ", glBalCredit7=" + glBalCredit7 + ", glBalDebit7="
				+ glBalDebit7 + ", glBalCredit8=" + glBalCredit8 + ", glBalDebit8=" + glBalDebit8 + ", glBalCredit9="
				+ glBalCredit9 + ", glBalDebit9=" + glBalDebit9 + ", glBalCredit10=" + glBalCredit10 + ", glBalDebit10="
				+ glBalDebit10 + ", glBalCredit11=" + glBalCredit11 + ", glBalDebit11=" + glBalDebit11
				+ ", glBalCredit12=" + glBalCredit12 + ", glBalDebit12=" + glBalDebit12 + ", glBalCredit13="
				+ glBalCredit13 + ", glBalDebit13=" + glBalDebit13 + ", glBalCIdr0=" + glBalCIdr0 + ", glBalDIdr0="
				+ glBalDIdr0 + ", glBalCIdr1=" + glBalCIdr1 + ", glBalDIdr1=" + glBalDIdr1 + ", glBalCIdr2="
				+ glBalCIdr2 + ", glBalDIdr2=" + glBalDIdr2 + ", glBalCIdr3=" + glBalCIdr3 + ", glBalDIdr3="
				+ glBalDIdr3 + ", glBalCIdr4=" + glBalCIdr4 + ", glBalDIdr4=" + glBalDIdr4 + ", glBalCIdr5="
				+ glBalCIdr5 + ", glBalDIdr5=" + glBalDIdr5 + ", glBalCIdr6=" + glBalCIdr6 + ", glBalDIdr6="
				+ glBalDIdr6 + ", glBalCIdr7=" + glBalCIdr7 + ", glBalDIdr7=" + glBalDIdr7 + ", glBalCIdr8="
				+ glBalCIdr8 + ", glBalDIdr8=" + glBalDIdr8 + ", glBalCIdr9=" + glBalCIdr9 + ", glBalDIdr9="
				+ glBalDIdr9 + ", glBalCIdr10=" + glBalCIdr10 + ", glBalDIdr10=" + glBalDIdr10 + ", glBalCIdr11="
				+ glBalCIdr11 + ", glBalDIdr11=" + glBalDIdr11 + ", glBalCIdr12=" + glBalCIdr12 + ", glBalDIdr12="
				+ glBalDIdr12 + ", glBalCIdr13=" + glBalCIdr13 + ", glBalDIdr13=" + glBalDIdr13 + ", createBy="
				+ createBy + ", createOn=" + createOn + ", modifyBy=" + modifyBy + ", modifyOn=" + modifyOn
				+ ", coaDescript=" + coaDescript + "]";
	}
	
	
}
