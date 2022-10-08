package com.biru.service;

import java.text.ParseException;
import java.util.Map;

import com.biru.common.entity.DatatableSet;

public interface UserManagementService {

	public Object save(Map<String, Object> p_Param);
	public DatatableSet inquiry(Map<String, Object> p_Param);
	public DatatableSet inquiryChild(Map<String, Object> p_Param);
	public Object getDropDown();
	public Object delete(Map<String, Object> param);
	
	
	public DatatableSet userInquiry(Map<String, Object> p_Param);
	public Object userSave(Map<String, Object> p_Param) throws ParseException, Exception;
	public Object getGroupDropDown();
	public Object getUserLevelDropDown();
	public Object getMplDropDown();
	public Object getStdataDropDown();
	public Object getSaDropDown();
}
