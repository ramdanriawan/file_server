package com.biru.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biru.component.RptPlComponent;
import com.biru.service.RptPlService;

@Service
public class RptPlServiceImpl implements RptPlService{

	@Autowired
	private RptPlComponent rptPlComponent;
	
	@Override
	public synchronized Object print(Map<String, Object>param) throws Exception {
		// TODO Auto-generated method stub
		rptPlComponent.setupReport(param);
		return rptPlComponent.createReport("print", param);
	}

	@Override
	public synchronized Object export(Map<String, Object>param) throws Exception {
		// TODO Auto-generated method stub
		rptPlComponent.setupReport(param);
		return rptPlComponent.createReport("export", param);
	}

	@Override
	public synchronized Object preview(Map<String, Object>param) throws Exception {
		// TODO Auto-generated method stub
		rptPlComponent.setupReport(param);
		return rptPlComponent.createReport("preview", param);
	}

}
