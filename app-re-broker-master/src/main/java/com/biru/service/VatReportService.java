package com.biru.service;

import java.util.Map;

import net.sf.jasperreports.engine.JRException;

public interface VatReportService {
	public Object inq(Map<String, Object>param); 
	public Object print(Map<String, Object>param) throws JRException, Exception; 
	public Object export(Map<String, Object>param) throws JRException, Exception; 
	
}
