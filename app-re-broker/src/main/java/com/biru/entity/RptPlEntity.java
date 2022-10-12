package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the rpt_pl database table.
 * 
 */
@Entity
@Table(name="RPT_PL")
@NamedQuery(name="RptPlEntity.findAll", query="SELECT r FROM RptPlEntity r")
public class RptPlEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="BLOCK_1")
	private BigDecimal block1;

	@Column(name="BLOCK_2")
	private BigDecimal block2;

	@Column(name="BLOCK_3")
	private BigDecimal block3;

	@Column(name="BLOCK_4")
	private BigDecimal block4;

	@Column(name="BLOCK_5")
	private BigDecimal block5;

	@Column(name="BLOCK_6")
	private BigDecimal block6;

	@Column(name="COA_CODE")
	private String coaCode;

	@Column(name="COA_DESCRIPTION")
	private String coaDescription;

	@Column(name="COA_GROUP")
	private String coaGroup;

	@Id
	@Column(name="ID_KEY")
	private int idKey;

	@Column(name="ROW_RPT")
	private int rowRpt;

	public RptPlEntity() {
	}

	public BigDecimal getBlock1() {
		return this.block1;
	}

	public void setBlock1(BigDecimal block1) {
		this.block1 = block1;
	}

	public BigDecimal getBlock2() {
		return this.block2;
	}

	public void setBlock2(BigDecimal block2) {
		this.block2 = block2;
	}

	public BigDecimal getBlock3() {
		return this.block3;
	}

	public void setBlock3(BigDecimal block3) {
		this.block3 = block3;
	}

	public BigDecimal getBlock4() {
		return this.block4;
	}

	public void setBlock4(BigDecimal block4) {
		this.block4 = block4;
	}

	public BigDecimal getBlock5() {
		return this.block5;
	}

	public void setBlock5(BigDecimal block5) {
		this.block5 = block5;
	}

	public BigDecimal getBlock6() {
		return this.block6;
	}

	public void setBlock6(BigDecimal block6) {
		this.block6 = block6;
	}

	public String getCoaCode() {
		return this.coaCode;
	}

	public void setCoaCode(String coaCode) {
		this.coaCode = coaCode;
	}

	public String getCoaDescription() {
		return this.coaDescription;
	}

	public void setCoaDescription(String coaDescription) {
		this.coaDescription = coaDescription;
	}

	public String getCoaGroup() {
		return this.coaGroup;
	}

	public void setCoaGroup(String coaGroup) {
		this.coaGroup = coaGroup;
	}

	public int getIdKey() {
		return this.idKey;
	}

	public void setIdKey(int idKey) {
		this.idKey = idKey;
	}

	public int getRowRpt() {
		return this.rowRpt;
	}

	public void setRowRpt(int rowRpt) {
		this.rowRpt = rowRpt;
	}

}