package com.biru.service;

import java.text.ParseException;
import java.util.Map;

import com.biru.common.entity.DatatableSet;

public interface HolidayService {

	public Boolean isExsist(String p_Date) throws ParseException;
	
	public DatatableSet inquiry(Map<String, Object> p_Param);
	
	public Boolean save(Map<String, Object> p_Param) throws ParseException;
	
}
