package com.biru.web.marketing.report;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstantsRamdan.REST.REPORT_PRODUCTION_DI_VIR;
import com.biru.service.ReportProductionService;
import com.biru.service.ReportProductionServiceRamdan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(REST.MARKETING_REPORT)
public class ReportProductionDiVirRamdanController {
	
	@Autowired
	private ReportProductionServiceRamdan reportProductionService;
	
	@RequestMapping(value = REPORT_PRODUCTION_DI_VIR.TRANSACTION_DETAIL_DI_VIR, method = RequestMethod.POST)
	public Object transactionDetailDiVir(@RequestBody Map<String, Object> param) {
		return reportProductionService.transactionDetail(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION_DI_VIR.TRANSACTION_DETAIL_DI_DI_VIR, method = RequestMethod.POST)
	public Object transactionDetailDiDiVir(@RequestBody Map<String, Object> param) {

		return reportProductionService.transactionDetailDiVir(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION_DI_VIR.EXPORT_DI_VIR, method = RequestMethod.POST)
	public Object exportDiVir(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.export(param);
	}
	@RequestMapping(value = REPORT_PRODUCTION_DI_VIR.PRINT_JOURNAL_DI_VIR, method = RequestMethod.POST)
	public Object printJournalDiVir(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.printJournal(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION_DI_VIR.EXPORT_EXCEL_DI_VIR, method = RequestMethod.POST)
	public Object exportExcelDiVir(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.exportExcel(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION_DI_VIR.PRINT_DI_VIR, method = RequestMethod.POST)
	public Object printDiVir(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.print(param);
	}
	
	@RequestMapping(value = REPORT_PRODUCTION_DI_VIR.TR6_EXPORT_TO_EXCEL_DI_VIR, method = RequestMethod.POST)
	public Object tr6ExportToExcelDiVir(@RequestBody Map<String, Object> param) throws Exception {
		return reportProductionService.tr6ExportToExcel(param);
	}
}

