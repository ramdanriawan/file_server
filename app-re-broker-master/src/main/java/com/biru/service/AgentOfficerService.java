package com.biru.service;

import java.text.ParseException;
import java.util.Map;


import com.biru.common.entity.DatatableSet;


public interface AgentOfficerService {
	public DatatableSet inquiry(Map<String, Object> p_Param);
	public Object save(Map<String, Object> p_Param) throws ParseException;
}
