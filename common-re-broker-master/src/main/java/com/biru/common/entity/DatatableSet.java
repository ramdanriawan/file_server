package com.biru.common.entity;

import java.io.Serializable;
import java.util.List;

public class DatatableSet implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long total;
	
	private Long totalNotFiltered;
	
	private List<?> rows;
	
	public DatatableSet() {
	}
	
	public DatatableSet(Long total, Long totalNotFiltered, List<?> rows) {
		super();
		this.total = total;
		this.totalNotFiltered = totalNotFiltered;
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Long getTotalNotFiltered() {
		return totalNotFiltered;
	}

	public void setTotalNotFiltered(Long totalNotFiltered) {
		this.totalNotFiltered = totalNotFiltered;
	}

	public List<?> getRows() {
		return rows;
	}

	public void setRows(List<?> rows) {
		this.rows = rows;
	}

	@Override
	public String toString() {
		return "DatatableSet [total=" + total + ", totalNotFiltered=" + totalNotFiltered + ", rows=" + rows + "]";
	}
	
}
