package com.biru.service;

import java.util.Map;

public interface EndorsementService {
	public Object cancelClosing(Map<String, Object> param);
	public Object save(Map<String, Object> param);
	public Object createClosingReport(Map<String, Object> param) throws Exception;
	public Object inquirySendTable(Map<String, Object> param) throws Exception;
}
