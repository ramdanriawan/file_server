package com.biru.service;

import java.text.ParseException;
import java.util.Map;

import com.biru.helper.TreClosingParam;

public interface ProductionTreatyService {
	
	public Object save(Map<String, Object> param) throws ParseException;
	public Object delete(Map<String, Object> param);
	public Object inquiry(Map<String, Object> param) throws ParseException;
	public Object inquiryModify(Map<String, Object> param);
	public Object inquirySend(Map<String, Object> param);
	public Object createReportDoc(Map<String, Object> param) throws Exception;
	public Object createReportPdf(Map<String, Object> param) throws Exception;
	public Object doClosing(TreClosingParam treClosingParam) throws Exception;
	public Object closingDCNotePdf(Map<String, String> param) throws Exception;
	public Object closingDCNoteDoc(Map<String, String> param) throws Exception;
	
}
