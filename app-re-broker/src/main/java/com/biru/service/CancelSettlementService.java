package com.biru.service;

import java.text.ParseException;
import java.util.Map;

import com.biru.common.entity.DatatableSet;

public interface CancelSettlementService {
	public DatatableSet inquiry(Map<String, Object> param) throws ParseException;
	public Object save(Map<String, Object> param) throws ParseException;
}
