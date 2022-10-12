package com.biru.service;

import java.util.Map;

public interface ReportProductionService {
	public Object transactionDetail(Map<String, Object> param);
	public Object export(Map<String, Object> param) throws Exception;
	public Object printJournal(Map<String, Object> param) throws Exception;
	public Object exportExcel(Map<String, Object> param) throws Exception;
	public Object print(Map<String, Object> param) throws Exception;
	public Object tr6ExportToExcel(Map<String, Object> param) throws Exception;
}
