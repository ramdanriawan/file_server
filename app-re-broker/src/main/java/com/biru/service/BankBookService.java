package com.biru.service;

import java.text.ParseException;
import java.util.Map;

public interface BankBookService {
	
	public Object inquiry(Map<String, Object> p_Params) throws ParseException;
	public Object inquiryModify(Map<String, Object> p_Params) throws ParseException;
	public String save(Map<String, Object> p_Params) throws ParseException;
	public Object delete(Map<String, Object> p_Params);
	
}
