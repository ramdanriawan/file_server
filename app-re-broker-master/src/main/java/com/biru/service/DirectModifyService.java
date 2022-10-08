package com.biru.service;

import java.util.Map;

public interface DirectModifyService {

	public Object inquiry(Map<String, Object> params);
	public Object inquiryDetail(Map<String, Object> params);
	public Object save(Map<String, Object> params);
	
}
