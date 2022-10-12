package com.biru.service;

import java.util.Map;

import com.biru.common.entity.DatatableSet;

public interface RemittanceService {
	
	public DatatableSet inquiry(Map<String, Object> param);
	public Object printJournal(Map<String, Object> param) throws Exception;
	public Object exportExcel(Map<String, Object> param) throws Exception;
	public Object print(Map<String, Object> param) throws Exception;
	
}
