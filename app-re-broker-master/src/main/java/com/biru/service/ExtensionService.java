package com.biru.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.biru.common.entity.DatatableSet;

public interface ExtensionService {
	public DatatableSet inquiry(Map<String, Object> param) throws ParseException;
	//public Object save(Map<String, Object> param) throws ParseException;
	public Object saveUpload(Map<String, Object> param) throws ParseException;
	public Object upload(Map<String, Object> param) throws ParseException, IOException;
	public Object entry(Map<String, Object> param) throws Exception;
	public Object readFile(Map<String, Object> param) throws IOException, InvalidFormatException;
	public Object isFileNameExist(Map<String, Object> param);
}
