package com.biru.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="MA0018")
@NamedQuery(name="MA0018Entity.findAll", query="SELECT m FROM MA0018Entity m")
public class MA0018Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name = "BR_CODE")
	private String brCode;
	
	@Column(name = "BR_CHILD_DC")
	private Character brChildDc;
	
	@Column(name = "BR_CHILD_COA")
	private String brChildCoa;
	
	@Column(name = "BR_CHILD_VALUE")
	private String brChildValue;
	
	@Transient
	private String brCoaDescript;
	
	@Transient
	private String brChildDcDesc;
	
	public MA0018Entity(Long idKey, String brCode, Character brChildDc, String brChildCoa, String brChildValue,
			String brCoaDescript) {
		super();
		this.idKey = idKey;
		this.brCode = brCode;
		this.brChildDc = brChildDc;
		this.brChildCoa = brChildCoa;
		this.brChildValue = brChildValue;
		this.brCoaDescript = brCoaDescript;
	}

	public MA0018Entity() {
		// TODO Auto-generated constructor stub
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getBrCode() {
		return brCode;
	}

	public void setBrCode(String brCode) {
		this.brCode = brCode;
	}

	public Character getBrChildDc() {
		return brChildDc;
	}

	public void setBrChildDc(Character brChildDc) {
		this.brChildDc = brChildDc;
	}

	public String getBrChildCoa() {
		return brChildCoa;
	}

	public void setBrChildCoa(String brChildCoa) {
		this.brChildCoa = brChildCoa;
	}

	public String getBrChildValue() {
		return brChildValue;
	}

	public void setBrChildValue(String brChildValue) {
		this.brChildValue = brChildValue;
	}

	public String getBrCoaDescript() {
		return brCoaDescript;
	}

	public void setBrCoaDescript(String brCoaDescript) {
		this.brCoaDescript = brCoaDescript;
	}

	public String getBrChildDcDesc() {
		if(brChildDc == null)
			return null;
		
		if(brChildDc.equals('0')){
			return "Debit";
		}else{
			return "Credit";
		}
	}

	public void setBrChildDcDesc(String brChildDcDesc) {
		this.brChildDcDesc = brChildDcDesc;
	}

	@Override
	public String toString() {
		return "MA0018Entity [idKey=" + idKey + ", brCode=" + brCode + ", brChildDc=" + brChildDc + ", brChildCoa="
				+ brChildCoa + ", brChildValue=" + brChildValue + ", brCoaDescript=" + brCoaDescript
				+ ", brChildDcDesc=" + brChildDcDesc + "]";
	}
	
}
