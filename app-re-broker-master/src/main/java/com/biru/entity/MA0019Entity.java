package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The persistent class for the ma0019 database table.
 * 
 */
@Entity
@Table(name="MA0019")
@NamedQuery(name="MA0019Entity.findAll", query="SELECT m FROM MA0019Entity m")
public class MA0019Entity implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="CL_CODE")
	private Integer clCode;

	@Column(name="CL_DESC")
	private String clDesc;

	@Column(name="CL_STATUS")
	private String clStatus;

	@Id
	@Column(name="ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String idKey;

	@Column(name="TC_CODE")
	private String tcCode;

	public MA0019Entity() {
	}

	public int getClCode() {
		return this.clCode;
	}

	public void setClCode(int clCode) {
		this.clCode = clCode;
	}

	public String getClDesc() {
		return this.clDesc;
	}

	public void setClDesc(String clDesc) {
		this.clDesc = clDesc;
	}

	public String getClStatus() {
		return this.clStatus;
	}

	public void setClStatus(String clStatus) {
		this.clStatus = clStatus;
	}

	public String getIdKey() {
		return this.idKey;
	}

	public void setIdKey(String idKey) {
		this.idKey = idKey;
	}

	public String getTcCode() {
		return this.tcCode;
	}

	public void setTcCode(String tcCode) {
		this.tcCode = tcCode;
	}

}