package com.biru.web.accounting;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.ACCOUNTING_REPORT;
import com.biru.service.ReportService;

@RestController
@RequestMapping(REST.ACCOUNTING_REPORT)
public class ReportController {
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping(value = ACCOUNTING_REPORT.TB_CREATE_EXCEL, method = RequestMethod.POST)
	public String createTrialBalanceExcel(@RequestBody Map<String, Object> p_Params) throws Exception {
		
		return reportService.createTrialBalanceExcel(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.TB_CREATE_HTML, method = RequestMethod.POST)
	public String createTrialBalanceHtml(@RequestBody Map<String, Object> p_Params) throws Exception {
		
		return reportService.createTrialBalanceHtml(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.TB_CREATE_PDF, method = RequestMethod.POST)
	public String createTrialBalancePdf(@RequestBody Map<String, Object> p_Params) throws Exception {
		
		return reportService.createTrialBalancePdf(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.BS_CREATE_EXCEL, method = RequestMethod.POST)
	public String createBalanceSheetExcel(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return reportService.createBalanceSheetExcel(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.BS_CREATE_HTML, method = RequestMethod.POST)
	public String createBalanceSheetHtml(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return reportService.createBalanceSheetHtml(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.BS_CREATE_PDF, method = RequestMethod.POST)
	public String createBalanceSheetPdf(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return reportService.createBalanceSheetPdf(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.CF_CREATE_EXCEL, method = RequestMethod.POST)
	public String createCashFlowExcel(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return reportService.createCashFlowExcel(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.CF_CREATE_HTML, method = RequestMethod.POST)
	public String createCashFlowHtml(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return reportService.createCashFlowHtml(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.CF_CREATE_PDF, method = RequestMethod.POST)
	public String createCashFlowPdf(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return reportService.createCashFlowPdf(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.GL_CREATE_EXCEL, method = RequestMethod.POST)
	public String createGeneralLedgerExcel(@RequestBody Map<String, Object> p_Params) throws Exception {
		
		return reportService.createGeneralLedgerExcel(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.GL_CREATE_HTML, method = RequestMethod.POST)
	public String createGeneralLedgerHtml(@RequestBody Map<String, Object> p_Params) throws Exception {
		
		return reportService.createGeneralLedgerHtml(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.GL_CREATE_PDF, method = RequestMethod.POST)
	public String createGeneralLedgerPdf(@RequestBody Map<String, Object> p_Params) throws Exception {
		
		return reportService.createGeneralLedgerPdf(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.PL_CREATE_EXCEL, method = RequestMethod.POST)
	public String createProfitAndLostExcel(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return reportService.createProfitAndLostExcel(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.PL_CREATE_HTML, method = RequestMethod.POST)
	public String createProfitAndLostHtml(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return reportService.createProfitAndLostHtml(p_Params);
	}
	
	@RequestMapping(value = ACCOUNTING_REPORT.PL_CREATE_PDF, method = RequestMethod.POST)
	public String createProfitAndLostPdf(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return reportService.createProfitAndLostPdf(p_Params);
	}
	
}
