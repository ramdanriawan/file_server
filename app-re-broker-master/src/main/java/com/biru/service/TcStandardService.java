package com.biru.service;

import java.util.Map;

import com.biru.common.entity.DatatableSet;

public interface TcStandardService {
	public DatatableSet inquiry(Map<String, Object> param); 
	public Object description(Map<String, Object> param); 
	public Object save(Map<String, Object> param); 
	public Object delete(Map<String, Object> param);
}
