package com.biru.component;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

@Component
public class ResultComponent {
	
	public static String MESSAGE = "message";
	public static String STATUS = "status";
	
	public Object createResponse(Exception e) {
		Map<String, Object> error = new HashMap<String, Object>();
		if(e == null) {
			error.put(STATUS, "200");
			error.put(MESSAGE, "");
		}else {
			error.put(STATUS, "500");
			error.put(MESSAGE, e.getMessage());
		}
		
		return error;
	}
	
	public Object createResponse(String statusCode, String message) {
		Map<String, Object> response = new HashMap<String, Object>();
		response.put(STATUS, statusCode);
		response.put(MESSAGE, message);
		
		return response;
	}
	
}
