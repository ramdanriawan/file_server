package com.biru.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import com.biru.common.entity.DatatableSet;

public interface UploadTreatyService {
	public DatatableSet inquiry(Map<String, Object> param) throws ParseException;
	public Object upload(Map<String, Object> param) throws ParseException, IOException;
	public Object readFile(Map<String, Object> param) throws IOException;
	public Object save(Map<String, Object> param) throws ParseException;
	public Object validateSource(Map<String, Object> param) throws IOException;
}
