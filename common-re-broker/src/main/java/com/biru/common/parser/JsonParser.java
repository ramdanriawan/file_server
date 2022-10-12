package com.biru.common.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class JsonParser {
	
	private static ObjectMapper objectMapper = null;

	protected JsonParser() {
	}

	public static ObjectMapper getParser() {
		if(objectMapper == null) {
			objectMapper = new ObjectMapper();
			setFeature(objectMapper);
		}
		
		return objectMapper;
	}

	private static void setFeature(ObjectMapper objectMapper) {
		objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
}
