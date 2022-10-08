package com.biru.service;

import java.text.ParseException;
import java.util.Map;

public interface NewEndorsementService {
	public Object getRequestId() throws ParseException;
	public Object closing(Map<String, Object> param) throws ParseException;
}
