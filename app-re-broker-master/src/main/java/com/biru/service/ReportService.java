package com.biru.service;

import java.util.Map;

public interface ReportService {

	public String createTrialBalanceExcel(Map<String, Object> p_Params) throws Exception;
	public String createTrialBalanceHtml(Map<String, Object> p_Params) throws Exception;
	public String createTrialBalancePdf(Map<String, Object> p_Params) throws Exception;
	
	public String createBalanceSheetExcel(Map<String, String> p_Params) throws Exception;
	public String createBalanceSheetHtml(Map<String, String> p_Params) throws Exception;
	public String createBalanceSheetPdf(Map<String, String> p_Params) throws Exception;
	
	public String createCashFlowExcel(Map<String, String> p_Params) throws Exception;
	public String createCashFlowHtml(Map<String, String> p_Params) throws Exception;
	public String createCashFlowPdf(Map<String, String> p_Params) throws Exception;
	
	public String createGeneralLedgerExcel(Map<String, Object> p_Params) throws Exception;
	public String createGeneralLedgerHtml(Map<String, Object> p_Params) throws Exception;
	public String createGeneralLedgerPdf(Map<String, Object> p_Params) throws Exception;
	
	public String createProfitAndLostExcel(Map<String, String> p_Params) throws Exception;
	public String createProfitAndLostHtml(Map<String, String> p_Params) throws Exception;
	public String createProfitAndLostPdf(Map<String, String> p_Params) throws Exception;
	
}
