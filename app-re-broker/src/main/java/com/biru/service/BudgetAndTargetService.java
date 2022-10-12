package com.biru.service;

import java.util.Map;

import com.biru.common.entity.DatatableSet;

public interface BudgetAndTargetService {
	
	public DatatableSet inquiry(Map<String, Object> p_Param);
	public Object save(Map<String, Object> param);
	public Object exportExcel(Map<String, Object> param) throws Exception;
	public Object coa(Map<String, Object> param);

}
