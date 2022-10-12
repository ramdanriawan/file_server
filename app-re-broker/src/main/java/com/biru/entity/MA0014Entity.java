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
@Table(name="MA0014")
@NamedQuery(name="MA0014Entity.findAll", query="SELECT m FROM MA0014Entity m")
public class MA0014Entity implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_KEY")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idKey;
	
	@Column(name = "PA_PARENT_CODE")
	private String paParentCode;
	
	@Column(name = "PA_CHILD_CODE")
	private String paChildCode;
	
	@Column(name = "PA_CHILD_DESC")
	private String paChildDesc;
	
	@Column(name = "PA_CHILD_VALUE")
	private String paChildValue;
	
	@Column(name = "PA_CHILD_STATUS")
	private String paChildStatus;
	
	@Transient 
	private String action;
	
	@Transient 
	private String childStatusActive;
	
	public String getChildStatusActive() {
		if (paChildStatus.equals("11")) {
			return "Active";
		}else {
			return "Inactive";
		}
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public String getAction() {
		return "<button class=\"btn btn-secondary\" onclick=\"editChildPa('"+getIdKey()+"')\">" 
				+ "<i class=\"fa fa-edit\"></i>" 
				+ "</button>";
	}

	public Long getIdKey() {
		return idKey;
	}

	public void setIdKey(Long idKey) {
		this.idKey = idKey;
	}

	public String getPaParentCode() {
		return paParentCode;
	}

	public void setPaParentCode(String paParentCode) {
		this.paParentCode = paParentCode;
	}

	public String getPaChildCode() {
		return paChildCode;
	}

	public void setPaChildCode(String paChildCode) {
		this.paChildCode = paChildCode;
	}

	public String getPaChildDesc() {
		return paChildDesc;
	}

	public void setPaChildDesc(String paChildDesc) {
		this.paChildDesc = paChildDesc;
	}

	public String getPaChildValue() {
		return paChildValue;
	}

	public void setPaChildValue(String paChildValue) {
		this.paChildValue = paChildValue;
	}

	public String getPaChildStatus() {
		return paChildStatus;
	}

	public void setPaChildStatus(String paChildStatus) {
		this.paChildStatus = paChildStatus;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((action == null) ? 0 : action.hashCode());
		result = prime * result + ((childStatusActive == null) ? 0 : childStatusActive.hashCode());
		result = prime * result + ((idKey == null) ? 0 : idKey.hashCode());
		result = prime * result + ((paChildCode == null) ? 0 : paChildCode.hashCode());
		result = prime * result + ((paChildDesc == null) ? 0 : paChildDesc.hashCode());
		result = prime * result + ((paChildStatus == null) ? 0 : paChildStatus.hashCode());
		result = prime * result + ((paChildValue == null) ? 0 : paChildValue.hashCode());
		result = prime * result + ((paParentCode == null) ? 0 : paParentCode.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MA0014Entity other = (MA0014Entity) obj;
		if (action == null) {
			if (other.action != null)
				return false;
		} else if (!action.equals(other.action))
			return false;
		if (childStatusActive == null) {
			if (other.childStatusActive != null)
				return false;
		} else if (!childStatusActive.equals(other.childStatusActive))
			return false;
		if (idKey == null) {
			if (other.idKey != null)
				return false;
		} else if (!idKey.equals(other.idKey))
			return false;
		if (paChildCode == null) {
			if (other.paChildCode != null)
				return false;
		} else if (!paChildCode.equals(other.paChildCode))
			return false;
		if (paChildDesc == null) {
			if (other.paChildDesc != null)
				return false;
		} else if (!paChildDesc.equals(other.paChildDesc))
			return false;
		if (paChildStatus == null) {
			if (other.paChildStatus != null)
				return false;
		} else if (!paChildStatus.equals(other.paChildStatus))
			return false;
		if (paChildValue == null) {
			if (other.paChildValue != null)
				return false;
		} else if (!paChildValue.equals(other.paChildValue))
			return false;
		if (paParentCode == null) {
			if (other.paParentCode != null)
				return false;
		} else if (!paParentCode.equals(other.paParentCode))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "MA0014Entity [idKey=" + idKey + ", paParentCode=" + paParentCode + ", paChildCode=" + paChildCode
				+ ", paChildDesc=" + paChildDesc + ", paChildValue=" + paChildValue + ", paChildStatus=" + paChildStatus
				+ ", action=" + action + ", childStatusActive=" + childStatusActive + "]";
	}
	
}
