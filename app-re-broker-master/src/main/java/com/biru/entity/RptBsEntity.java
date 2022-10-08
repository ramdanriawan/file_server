package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the rpt_bs database table.
 * 
 */
@Entity
@Table(name="RPT_BS")
@NamedQuery(name="RptBsEntity.findAll", query="SELECT r FROM RptBsEntity r")
public class RptBsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="BLOCK_1_L")
	private BigDecimal block1L;

	@Column(name="BLOCK_1_R")
	private BigDecimal block1R;

	@Column(name="BLOCK_2_L")
	private BigDecimal block2L;

	@Column(name="BLOCK_2_R")
	private BigDecimal block2R;

	@Column(name="BLOCK_3_L")
	private BigDecimal block3L;

	@Column(name="BLOCK_3_R")
	private BigDecimal block3R;

	@Column(name="BLOCK_4_L")
	private BigDecimal block4L;

	@Column(name="BLOCK_4_R")
	private BigDecimal block4R;

	@Column(name="BLOCK_5_L")
	private BigDecimal block5L;

	@Column(name="BLOCK_5_R")
	private BigDecimal block5R;

	@Column(name="COA_CODE")
	private String coaCode;

	@Lob
	@Column(name="COA_DESCRIPTION")
	private String coaDescription;

	@Column(name="COA_GROUP")
	private String coaGroup;

	private String position;

	@Column(name="ROW_HEAD")
	private String rowHead;

	@Id
	@Column(name="ROW_RPT")
	private Integer rowRpt;

	public RptBsEntity() {
	}

	public BigDecimal getBlock1L() {
		return this.block1L;
	}

	public void setBlock1L(BigDecimal block1L) {
		this.block1L = block1L;
	}

	public BigDecimal getBlock1R() {
		return this.block1R;
	}

	public void setBlock1R(BigDecimal block1R) {
		this.block1R = block1R;
	}

	public BigDecimal getBlock2L() {
		return this.block2L;
	}

	public void setBlock2L(BigDecimal block2L) {
		this.block2L = block2L;
	}

	public BigDecimal getBlock2R() {
		return this.block2R;
	}

	public void setBlock2R(BigDecimal block2R) {
		this.block2R = block2R;
	}

	public BigDecimal getBlock3L() {
		return this.block3L;
	}

	public void setBlock3L(BigDecimal block3L) {
		this.block3L = block3L;
	}

	public BigDecimal getBlock3R() {
		return this.block3R;
	}

	public void setBlock3R(BigDecimal block3R) {
		this.block3R = block3R;
	}

	public BigDecimal getBlock4L() {
		return this.block4L;
	}

	public void setBlock4L(BigDecimal block4L) {
		this.block4L = block4L;
	}

	public BigDecimal getBlock4R() {
		return this.block4R;
	}

	public void setBlock4R(BigDecimal block4R) {
		this.block4R = block4R;
	}

	public BigDecimal getBlock5L() {
		return this.block5L;
	}

	public void setBlock5L(BigDecimal block5L) {
		this.block5L = block5L;
	}

	public BigDecimal getBlock5R() {
		return this.block5R;
	}

	public void setBlock5R(BigDecimal block5R) {
		this.block5R = block5R;
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

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getRowHead() {
		return this.rowHead;
	}

	public void setRowHead(String rowHead) {
		this.rowHead = rowHead;
	}

	public int getRowRpt() {
		return this.rowRpt;
	}

	public void setRowRpt(Integer rowRpt) {
		this.rowRpt = rowRpt;
	}

}