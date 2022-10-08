package com.biru.web.marketing.report;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.ENTRY_JOURNAL;
import com.biru.ReBrokerConstants.REST.REPORT_PRODUCTION;
import com.biru.service.ProductionService;
import com.biru.service.ReportProductionService;

@RestController
@RequestMapping(REST.MARKETING_REPORT)
public class ReportProductionController {
	
	@Autowired
	private ReportProductionService reportProductionService;
	
	@RequestMapping(value = REPORT_PRODUCTION.TRANSACTION_DETAIL, method = RequestMethod.POST)
	public Object transactionDetail(@RequestBody Map<String, Object> param) {
		return reportProductionService.transactionDetail(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION.TRANSACTION_DETAIL_DI, method = RequestMethod.POST)
	public Object transactionDetailDi(@RequestBody Map<String, Object> param) {
		return reportProductionService.transactionDetail(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION.EXPORT, method = RequestMethod.POST)
	public Object export(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.export(param);
	}
	@RequestMapping(value = REPORT_PRODUCTION.PRINT_JOURNAL, method = RequestMethod.POST)
	public Object printJournal(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.printJournal(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION.EXPORT_EXCEL, method = RequestMethod.POST)
	public Object exportExcel(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.exportExcel(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION.PRINT, method = RequestMethod.POST)
	public Object print(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.print(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION.TR6_EXPORT_TO_EXCEL, method = RequestMethod.POST)
	public Object tr6ExportToExcel(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.tr6ExportToExcel(param);
	}
}

