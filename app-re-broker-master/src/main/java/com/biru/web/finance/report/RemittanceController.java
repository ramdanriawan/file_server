package com.biru.web.finance.report;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.REMITTANCE;
import com.biru.common.entity.DatatableSet;
import com.biru.service.RemittanceService;

@RestController
@RequestMapping(REST.FINANCE_REPORT)
public class RemittanceController {
	
	@Autowired
	private RemittanceService remittanceService;
	
	@RequestMapping(value = REMITTANCE.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) {
		
		return remittanceService.inquiry(p_Param);
	}

	@RequestMapping(value = REMITTANCE.PRINT_JOURNAL, method = RequestMethod.POST)
	public Object printJournal(@RequestBody Map<String, Object> param) throws Exception {
		return remittanceService.printJournal(param);
	}
	
	@RequestMapping(value = REMITTANCE.EXPORT_EXCEL, method = RequestMethod.POST)
	public Object exportExcel(@RequestBody Map<String, Object> param) throws Exception {
		return remittanceService.exportExcel(param);
	}
	
	@RequestMapping(value = REMITTANCE.PRINT, method = RequestMethod.POST)
	public Object print(@RequestBody Map<String, Object> param) throws Exception {
		return remittanceService.print(param);
	}
}
