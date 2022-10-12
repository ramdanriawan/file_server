package com.biru.service;

import java.text.ParseException;
import java.util.Map;

import com.biru.common.entity.DatatableSet;
import com.biru.entity.MA0015Entity;

public interface ExchangeRateService {

	public Boolean save(Map<String, Object> p_Param) throws ParseException;
	public DatatableSet inquiry(Map<String, Object> p_Param);
	public Boolean saveTaxRate(Map<String, Object> p_Param) throws ParseException;
	public DatatableSet inquiryTaxRate(Map<String, Object> p_Param);
	public Object removeTaxRate(Map<String, Object> p_Param);
}
