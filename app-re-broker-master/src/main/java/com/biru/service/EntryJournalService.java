package com.biru.service;

import java.text.ParseException;
import java.util.Map;

public interface EntryJournalService {
	
	public Object inquiry(Map<String, Object> param);
	public Object getDropdownProject();
	public Object getDropdownOffice();
	
	public String getTransactionMinDate() throws ParseException;
	public Object client(Map<String, Object> param);
	public Object coa(Map<String, Object> param);
	public Object exchange(Map<String, Object> param);
	
	public Object voucherCode(Map<String, Object> param) ;
	public Object save(Map<String, Object> param) ;
	
	public Object edit(Map<String, Object> param);
	
	public Object remove(Map<String, Object> param);
	
	public Object printJournal(Map<String, Object> param) throws Exception;
	
	public Object exportExcel(Map<String, Object> param) throws Exception;
	public Object print(Map<String, Object> param) throws Exception;
}
