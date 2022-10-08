package com.biru.service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.biru.entity.TR0015DEntity;
import com.biru.helper.InternalMemoHelper;

public interface ClaimsService {

	public Object inquiry(Map<String, Object> param);
	public Object inquiryDetail(Map<String, Object> param);
	public Object inquiryCreate(Map<String, Object> param);
	public Object inquiryCreateDetail(Map<String, Object> param);
	public Object save(Map<String, Object> param) throws ParseException;
	public Object settlement(Map<String, Object> param) throws ParseException;
	public Object getUnderwriting(Map<String, Object> param);
	public Object delete(Map<String, Object> param);
	public Object dropdownYear();
	public String exportClaimslistExcel(Map<String, Object> param) throws Exception;
	public Object getValueTR6J(Map<String, Object> param);
	public String createDlaPlaPdf(Map<String, Object> param) throws Exception;
	public String createDlaPlaDoc(Map<String, Object> param) throws Exception;
	public List<String> DCNotes(Map<String, Object> param) throws Exception;
	public Object getListInsurance(Map<String, Object> param) throws Exception;
	public Object getListTR15A(Map<String, Object> param);
	public List<TR0015DEntity> getFinance(Map<String, Object> param);
	public Object processFinance(Map<String, Object> param);
	public List<InternalMemoHelper> getListInternalMemo(Map<String, Object> param);
	public String createInternalMemo(Map<String, Object> param) throws Exception;
	public String createInternalMemoExcel(Map<String, Object> param) throws Exception;
	
}
