package com.biru.web.accounting.report;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.PL_ACCOUNT;
import com.biru.service.RptPlService;

@RestController
@RequestMapping(REST.ACCOUNTING_REPORT)
public class PLAccountController {
	
	@Autowired
	private RptPlService rptPlService;
	
//	@RequestMapping(value = "/test", method = RequestMethod.POST)
//	public Object preview(@RequestBody Map<String, Object> param) throws Exception {
//		return rptPlService.preview(param);
//	}
	
	@RequestMapping(value = PL_ACCOUNT.PREVIEW, method = RequestMethod.POST)
	public Object preview(@RequestBody Map<String, Object> param) throws Exception {
		return rptPlService.preview(param);
	}
	
	@RequestMapping(value = PL_ACCOUNT.PRINT, method = RequestMethod.POST)
	public Object print(@RequestBody Map<String, Object> param) throws Exception {
		return rptPlService.print(param);
	}
	
	@RequestMapping(value = PL_ACCOUNT.EXPORT, method = RequestMethod.POST)
	public Object export(@RequestBody Map<String, Object> param) throws Exception {
		return rptPlService.export(param);
	}
}
