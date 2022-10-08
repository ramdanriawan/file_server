package com.biru.web.finance.report;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.OUTSTANDING;
import com.biru.common.entity.DatatableSet;
import com.biru.service.OutstandingService;

@RestController
@RequestMapping(REST.FINANCE_REPORT)
public class OutstandingReportController {
	
	@Autowired
	private OutstandingService outstandingService;
	
	@RequestMapping(value = OUTSTANDING.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) throws Exception {
		
		return outstandingService.inquiry(p_Param);
	}
	
	@RequestMapping(value = OUTSTANDING.EXPORT_EXCEL, method = RequestMethod.POST)
	public Object exportExcel(@RequestBody Map<String, Object> param) throws Exception {
		return outstandingService.exportExcelV2(param);
	}
	
	@RequestMapping(value = OUTSTANDING.PRINT, method = RequestMethod.POST)
	public Object print(@RequestBody Map<String, Object> param) throws Exception {
		return outstandingService.printV2(param);
	}
	
}
