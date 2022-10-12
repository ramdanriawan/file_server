package com.biru.service;

import java.util.Map;

public interface EoyService {
	
	public void processEoy(Map<String, Object> param) throws Exception;
	public Boolean checkYearIsValid(Map<String, Object> param);
	
	public Boolean checkEom12(Map<String, Object> param);
	public void copyCoaGL(Map<String, Object> param);
	public void updateGL0001COAEOY003(Map<String, Object> param) throws Exception;
	public void roleUp(Map<String, Object> param);
	public void insertTr0005(Map<String, Object> param);
	public void insertLastYearJournal(Map<String, Object> param);
	
	public void deleteGL0001(Map<String, Object> param);
	public void updateTr0005(Map<String, Object> param);
	public void insertCancelLastYearJournal(Map<String, Object> param);
}
