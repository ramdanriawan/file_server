package com.biru.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.helper.GL0001Helper;
public interface BeginBalService {
	
	public List<DropdownIdText> getYear();
	
	public DatatableSet inquiryV1(Map<String, Object> p_Param);
	
	public List<GL0001Helper> inquiryV2(Map<String, Object> p_Param);
	
	public Boolean save(Map<String, Object> p_Param);
	
	public BigDecimal sumGlBalDebit0(String p_GlBalYear);
	
	public BigDecimal sumGlBalCredit0(String p_GlBalYear);
	
	public GL0001Helper summaryByYear(String p_GlBalYear);
	
	public DatatableSet lookupCoa(Map<String, Object> p_Param);

}
