package com.biru.service;

import java.util.Map;

import com.biru.common.entity.DatatableSet;


public interface OutstandingService {
	
	public DatatableSet inquiry(Map<String, Object> param) throws Exception;
	public Object exportExcel(Map<String, Object> param) throws Exception;
	public Object print(Map<String, Object> param) throws Exception;
	public Object exportExcelV2(Map<String, Object> param) throws Exception;
	public Object printV2(Map<String, Object> param) throws Exception;
	
}
