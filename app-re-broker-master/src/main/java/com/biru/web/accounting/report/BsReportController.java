package com.biru.web.accounting.report;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.BS_REPORT;
import com.biru.service.RptBsService;

@RestController
@RequestMapping(REST.ACCOUNTING_REPORT)
public class BsReportController {
	
	@Autowired
	private RptBsService rptBsService;
	
	@RequestMapping(value = BS_REPORT.PREVIEW, method = RequestMethod.POST)
	public Object preview(@RequestBody Map<String, Object> param) throws Exception {
		return rptBsService.preview(param);
	}
	
	@RequestMapping(value = BS_REPORT.PRINT, method = RequestMethod.POST)
	public Object print(@RequestBody Map<String, Object> param) throws Exception {
		return rptBsService.print(param);
	}
	
	@RequestMapping(value = BS_REPORT.EXPORT, method = RequestMethod.POST)
	public Object export(@RequestBody Map<String, Object> param) throws Exception {
		return rptBsService.export(param);
	}
}
