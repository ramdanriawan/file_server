package com.biru.service;

import java.text.ParseException;
import java.util.Map;

import com.biru.common.entity.DatatableSet;
import com.biru.entity.MA0004Entity;

public interface CoaService {
	public DatatableSet inquiry(Map<String, Object> p_Param);
	public Object getDropdownClass();
	public Object getDropdownCurr();
	public DatatableSet roleUp(Map<String, Object> p_Param);
	public Object save(MA0004Entity mA0004Entity) throws ParseException;
	public Object save(Map<String, Object> p_Param) throws ParseException;
}
