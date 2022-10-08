package com.biru.service;

import java.util.Map;

public interface RenewalService {
	public Object inquiry(Map<String, Object> param) throws Exception;
	public Object remove(Map<String, Object> param) throws Exception;
	public Object edit(Map<String, Object> param) throws Exception;
	public Object send(Map<String, Object> param) throws Exception;
	
	public Object export(Map<String, Object> param) throws Exception;
	public Object print(Map<String, Object> param) throws Exception;
}
