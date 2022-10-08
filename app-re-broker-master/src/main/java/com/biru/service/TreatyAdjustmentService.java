package com.biru.service;

import java.text.ParseException;
import java.util.Map;

public interface TreatyAdjustmentService {
	
	public Object inquiry(Map<String, Object> param);
	public Object inquiryModify(Map<String, Object> param);
	public Object save(Map<String, Object> param);
	public Object process(Map<String, Object> param) throws Exception;
	public Object cancel(Map<String, Object> param) throws ParseException;
	
}
