package com.biru.service;

import java.text.ParseException;
import java.util.Map;

public interface DCNotesService {
	
	public Object inquiry(Map<String, Object> p_Params) throws ParseException;
	public String save(Map<String, Object> p_Params) throws ParseException;
	public String reverse(Map<String, Object> p_Params) throws Exception;
	
	public String createDCNotesExcel(Map<String, String> p_Params) throws Exception;
	public String createDCNotesHtml(Map<String, String> p_Params) throws Exception;
	public String createDCNotesPdf(Map<String, String> p_Params) throws Exception;
	public String createDCNotesDoc(Map<String, String> p_Params) throws Exception;
	
	public String createDCNotesHtmlUploadTreaty(Map<String, String> p_Params) throws Exception;
	public String createDCNotesPdfUploadTreaty(Map<String, String> p_Params) throws Exception;
	public String createDCNotesDocUploadTreaty(Map<String, String> p_Params) throws Exception;
}
