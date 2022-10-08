package com.biru.service;

import java.util.Map;

import com.biru.common.entity.DatatableSet;

public interface ProductService {
	public Object save(Map<String, Object> param);
	public DatatableSet inquiry(Map<String, Object> p_Param); 
	public Object getDropDown();
	public Object saveIns(Map<String, Object> param);
	public Object inquiryInsurance(Map<String, Object> param);
	public Object removeIns(Map<String, Object> param);
	public Object getDropDownPaChild();
	public Object saveProcess(Map<String, Object> param) throws Exception;
}
