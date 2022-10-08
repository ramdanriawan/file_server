package com.biru.service;

import java.text.ParseException;
import java.util.Map;

import com.biru.common.entity.DatatableSet;
import com.biru.entity.ResponseEntity;

public interface SettlementService {
	
	public DatatableSet inquiry(Map<String, Object> param);
	public ResponseEntity save(Map<String, Object> param) throws ParseException;
	
}
