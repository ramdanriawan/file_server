package com.biru.service;

import java.text.ParseException;
import java.util.Map;

public interface ProductionService {
	
	public Object getName(Map<String, Object> param);
	public Object tcPremium(Map<String, Object> param);
	public Object taxRate();
	public Object termAndCondition(Map<String, Object> param);
	public Object inquiry(Map<String, Object> param) throws ParseException;
	public Object inquiryModify(Map<String, Object> param) throws ParseException;
	public Object save(Map<String, Object> param) throws ParseException;
	public Object delete(Map<String, Object> param);
	public Object createReportHtml(Map<String, Object> param) throws Exception;
	public Object createReportPdf(Map<String, Object> param) throws Exception;
	public Object createReportDoc(Map<String, Object> param) throws Exception;
	public Object inquirySend(Map<String, Object> param);
	public Object sendEmail(Map<String, Object> param) throws Exception;
	public Object doClosing(Map<String, Object> param) throws Exception;
	public Object closing(Map<String, Object> param) throws Exception;
	public Object createClosingHtml(Map<String, Object> param) throws Exception;
	public Object sendEmailClosing(Map<String, Object> param) throws Exception;
	public Boolean isValidTrxDate(Map<String, Object> param);
	public Boolean validationUserLevel(Map<String, Object> param);
	
}