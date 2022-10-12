package com.biru.service;

import java.util.Map;

public interface RptPlService {
	public Object print(Map<String, Object>param) throws Exception;
	public Object export(Map<String, Object>param) throws Exception;
	public Object preview(Map<String, Object>param) throws Exception;
}
