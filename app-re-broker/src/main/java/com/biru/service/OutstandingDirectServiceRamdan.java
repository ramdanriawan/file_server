package com.biru.service;

import com.biru.common.entity.DatatableSet;

import java.util.Map;


public interface OutstandingDirectServiceRamdan {
	
	public DatatableSet inquiry(Map<String, Object> param) throws Exception;
	public Object exportExcel(Map<String, Object> param) throws Exception;
	public Object print(Map<String, Object> param) throws Exception;
	public Object exportExcelV2(Map<String, Object> param) throws Exception;
	public Object printV2(Map<String, Object> param) throws Exception;
	
}
