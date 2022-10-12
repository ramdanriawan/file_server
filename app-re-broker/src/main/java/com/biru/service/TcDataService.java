package com.biru.service;

import java.util.Map;

public interface TcDataService {
	public Object inquiry(Map<String, Object> param);
	public Object save(Map<String, Object> param); 
	public Object delete(Map<String, Object> param); 
}
