package com.biru.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biru.component.RptBsComponent;
import com.biru.service.RptBsService;

@Service
public class RptBsServiceImpl implements RptBsService{

	@Autowired
	private RptBsComponent rptBsComponent;
	
	@Override
	public synchronized Object print(Map<String, Object>param) throws Exception {
		// TODO Auto-generated method stub
		rptBsComponent.setupReport(param);
		return rptBsComponent.createReport("print", param);
	}

	@Override
	public synchronized Object export(Map<String, Object>param) throws Exception {
		// TODO Auto-generated method stub
		rptBsComponent.setupReport(param);
		return rptBsComponent.createReport("export", param);
	}

	@Override
	public synchronized Object preview(Map<String, Object>param) throws Exception {
		// TODO Auto-generated method stub
		rptBsComponent.setupReport(param);
		return rptBsComponent.createReport("preview", param);
	}

}
