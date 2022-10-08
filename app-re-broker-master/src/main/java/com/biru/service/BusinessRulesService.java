package com.biru.service;

import java.text.ParseException;
import java.util.Map;

import com.biru.common.entity.DatatableSet;

public interface BusinessRulesService {
	
	public Object save(Map<String, Object> p_Param);
	public DatatableSet inquiry(Map<String, Object> p_Param);
	public DatatableSet inquiryChild(Map<String, Object> p_Param);
	
}
