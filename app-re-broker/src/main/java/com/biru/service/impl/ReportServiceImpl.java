package com.biru.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.biru.common.AbstractCommon;
import com.biru.common.param.Param;
import com.biru.component.ReportUtils;
import com.biru.entity.MA0010Entity;
import com.biru.entity.RPT001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.helper.BalanceHelper;
import com.biru.repository.GL0001Repo;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0010Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.RPT001Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.service.ReportService;

@Service
public class ReportServiceImpl extends AbstractCommon implements ReportService {
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private GL0001Repo gl0001Repo;
	
	@Autowired
	private MA0004Repo ma0004Repo;
	
	@Autowired
	private MA0010Repo ma0010Repo;
	
	@Autowired
	private MA0014Repo ma0014Repo;
	
	@Autowired
	private RPT001Repo rpt001Repo;
	
	@Autowired
	private TR0001Repo tr0001Repo;
	
	@Autowired
	private TR0002Repo tr0002Repo;
	
	@Value("${report.path.jasper}")
	String pathReport;
	
	private static final Character COA_NORMAL_C = 'C';
	private static final Character COA_NORMAL_D = 'D';
	
	private Logger logger = LoggerFactory.getLogger(ReportServiceImpl.class);
	
	//============================== TRIAL BALANCE ==============================//
	
	@Override
	public String createTrialBalanceExcel(Map<String, Object> p_Params) throws Exception {
		return createTrialBalance(p_Params, "excel");
	}
	
	@Override
	public String createTrialBalanceHtml(Map<String, Object> p_Params) throws Exception {
		return createTrialBalance(p_Params, "html");
	}

	@Override
	public String createTrialBalancePdf(Map<String, Object> p_Params) throws Exception {	
		return createTrialBalance(p_Params, "pdf");
	}
	
	@Transactional
	private String createTrialBalance(Map<String, Object> p_Params, String p_Type) throws Exception {
		String idRequest = UUID.randomUUID().toString();
		String companyName = ma0010Repo.findById(1l).get().getCoName();
			
		Date asAt = Param.getDate(p_Params, "asAt");
		
		String partAsAt[] = Param.getStr(p_Params, "asAt").split("/");
		String year = partAsAt[2];
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_COMPANY", companyName);
		params.put("P_USER", p_Params.get(Param.USER));
		params.put("P_ASAT", partAsAt[0] + "-" + partAsAt[1] + "-" + partAsAt[2]);
		params.put("P_ID_REQUEST", idRequest);
		
		List<RPT001Entity> tbalances = rpt001Repo.getBeginingBalance(year);	
		List<TR0002Entity> summaryTrx = tr0002Repo.getMutationTB(new Short(year), asAt);	
		
		for(RPT001Entity tb : tbalances) {
			TR0002Entity trx = summaryTrx.stream()
					.filter(t -> t.getGlAccount().equals(tb.getCoaCode()))
					.findFirst().orElse(null);
			
			if(trx!=null) {
				tb.setMutDebitOrg(trx.getGlOrgDebit());
				tb.setMutCreditOrg(trx.getGlOrgCredit());
				tb.setMutDebitIdr(trx.getGlIdrDebit());
				tb.setMutCreditIdr(trx.getGlIdrCredit());
			}
			
			tb.setIdRequest(idRequest);
		}
		
		if(tbalances.size() > 0)
			rpt001Repo.saveAll(tbalances);
		
		logger.info("ROLL UP BEGINNING BALANCE");
		for(int level=5; level>1; level--) {	//ROLL UP BEGINNING BALANCE
			List<RPT001Entity> updateRpt = new ArrayList<RPT001Entity>();
			List<RPT001Entity> summaryRollUp = rpt001Repo.summaryRollUpBb(idRequest, level);
			
			for(RPT001Entity rollUp : summaryRollUp) {
				RPT001Entity rpt = rpt001Repo.findByIdRequestAndCoaCode(idRequest, rollUp.getCoaCode());
				logger.info("Roll Up [{}] - Coa Code : {}, bbDebitOrg : {}, bbCreditOrg : {}, bbDebitIdr : {}, bbCreditIdr : {}.",
						level, rollUp.getCoaCode(), rollUp.getMutDebitOrg(), rollUp.getMutCreditOrg(),
						rollUp.getMutDebitIdr(), rollUp.getMutCreditIdr());
				
				rpt.setBbDebitOrg(rollUp.getMutDebitOrg());
				rpt.setBbCreditOrg(rollUp.getMutCreditOrg());
				rpt.setBbDebitIdr(rollUp.getMutDebitIdr());
				rpt.setBbCreditIdr(rollUp.getMutCreditIdr());
				
				updateRpt.add(rpt);
			}
			
			if(updateRpt.size() > 0)
				rpt001Repo.saveAll(updateRpt);
		}
		
		logger.info("ROLL UP MUTATION");
		for(int level=5; level>1; level--) {	//ROLL UP MUTATION
			List<RPT001Entity> updateRpt = new ArrayList<RPT001Entity>();
			List<RPT001Entity> summaryRollUp = rpt001Repo.summaryRollUpMut(idRequest, level);
			
			for(RPT001Entity rollUp : summaryRollUp) {
				RPT001Entity rpt = rpt001Repo.findByIdRequestAndCoaCode(idRequest, rollUp.getCoaCode());
				logger.info("Roll Up [{}] - Coa Code : {}, mutDebitOrg : {}, mutCreditOrg : {}, mutDebitIdr : {}, mutCreditIdr : {}.",
						level, rollUp.getCoaCode(), rollUp.getMutDebitOrg(), rollUp.getMutCreditOrg(),
						rollUp.getMutDebitIdr(), rollUp.getMutCreditIdr());
				
				rpt.setMutDebitOrg(rollUp.getMutDebitOrg());
				rpt.setMutCreditOrg(rollUp.getMutCreditOrg());
				rpt.setMutDebitIdr(rollUp.getMutDebitIdr());
				rpt.setMutCreditIdr(rollUp.getMutCreditIdr());
				
				updateRpt.add(rpt);
			}
			
			if(updateRpt.size() > 0)
				rpt001Repo.saveAll(updateRpt);
		}
		
		tbalances = rpt001Repo.findByIdRequest(idRequest); 
		
		for(RPT001Entity tb : tbalances) {		//CALCULATE END BALANCE
			if(COA_NORMAL_C.equals(tb.getCoaNormal())) {
				tb.setEbCreditOrg(
						tb.getBbCreditOrg().subtract(tb.getBbDebitOrg())
							.add(
								tb.getMutCreditOrg().subtract(tb.getMutDebitOrg())
							)
				);
				tb.setEbCreditIdr(
						tb.getBbCreditIdr().subtract(tb.getBbDebitIdr())
							.add(
								tb.getMutCreditIdr().subtract(tb.getMutDebitIdr())
							)
				);
			}else if(COA_NORMAL_D.equals(tb.getCoaNormal())) {
				tb.setEbDebitOrg(
						tb.getBbDebitOrg().subtract(tb.getBbCreditOrg())
							.add(
								tb.getMutDebitOrg().subtract(tb.getMutCreditOrg())
							)
				);
				tb.setEbDebitIdr(
						tb.getBbDebitIdr().subtract(tb.getBbCreditIdr())
							.add(
								tb.getMutDebitIdr().subtract(tb.getMutCreditIdr())
							)
				);
			}
		}
		
		if(tbalances.size() > 0)
			rpt001Repo.saveAll(tbalances);
		
		rpt001Repo.deleteZeroValue(idRequest);
		
//		logger.info("ROLL UP END BALANCE");
//		for(int level=5; level>1; level--) {	//ROLL UP END BALANCE
//			List<RPT001Entity> updateRpt = new ArrayList<RPT001Entity>();
//			List<RPT001Entity> summaryRollUp = rpt001Repo.summaryRollUpEb(idRequest, level);
//			
//			for(RPT001Entity rollUp : summaryRollUp) {
//				RPT001Entity rpt = rpt001Repo.findByIdRequestAndCoaCode(idRequest, rollUp.getCoaCode());
//				logger.info("Roll Up [{}] - Coa Code : {}, ebDebitOrg : {}, ebCreditOrg : {}, ebDebitIdr : {}, ebCreditIdr : {}.",
//						level, rollUp.getCoaCode(), rollUp.getMutDebitOrg(), rollUp.getMutCreditOrg(),
//						rollUp.getMutDebitIdr(), rollUp.getMutCreditIdr());
//				
//				rpt.setEbDebitOrg(rollUp.getMutDebitOrg());
//				rpt.setEbCreditOrg(rollUp.getMutCreditOrg());
//				rpt.setEbDebitIdr(rollUp.getMutDebitIdr());
//				rpt.setEbCreditIdr(rollUp.getMutCreditIdr());
//				
//				updateRpt.add(rpt);
//			}
//			
//			if(updateRpt.size() > 0)
//				rpt001Repo.saveAll(updateRpt);
//		}
		
		String result;
		if(p_Type.equals("pdf"))
			result = reportUtils.exportPdf("TrialBalance.jrxml", params);
		else if(p_Type.equals("excel"))
			result = reportUtils.exportExcel("TrialBalanceExcel.jrxml", params);
		else
			result = reportUtils.exportHtml("TrialBalance.jrxml", params);
		
		rpt001Repo.deleteByIdRequest(idRequest);
		
		return result;
	}
	
	//============================== BALANCE SHEET ==============================//
	
	@Override
	public String createBalanceSheetExcel(Map<String, String> p_Params) throws Exception {
		return createBalanceSheet(p_Params, "excel");
	}
	
	@Override
	public String createBalanceSheetHtml(Map<String, String> p_Params) throws Exception {
		return createBalanceSheet(p_Params, "html");
	}

	@Override
	public String createBalanceSheetPdf(Map<String, String> p_Params) throws Exception {	
		return createBalanceSheet(p_Params, "pdf");
	}
	
	private String createBalanceSheet(Map<String, String> p_Params, String p_Type) throws Exception {
		Optional<MA0010Entity> company = ma0010Repo.findById(1l);
		String companyName = company.get().getCoName();
				
		String month = p_Params.get("month");
		String year = p_Params.get("year");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, Integer.valueOf(month)-1);
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_COMPANY", companyName);
		params.put("P_USER", p_Params.get(Param.USER));
		params.put("P_ASAT", month + "/" + cal.getActualMaximum(Calendar.DAY_OF_MONTH) + "/" + year);
		params.put("P_MM", month);
		params.put("P_YY", year);
		
		String coaCode = ma0014Repo.findCoaCodeEoy();
		List<BalanceHelper> list = gl0001Repo.findByYearAndClassAndRoleUpNull(month, year);
		
		BigDecimal valIncome = BigDecimal.ZERO;
		BigDecimal valExpanse = BigDecimal.ZERO;
		BigDecimal valIncomeIdr = BigDecimal.ZERO;
		BigDecimal valExpanseIdr = BigDecimal.ZERO;
		
		for(BalanceHelper balance : list) {
			if(balance.getCoaClass().equals("3")) {
				valIncome = balance.getBalance();
				valIncomeIdr = balance.getBalanceIdr();
			}else if(balance.getCoaClass().equals("4")) {
				valExpanse = balance.getBalance();
				valExpanseIdr = balance.getBalanceIdr();
			}
		}
		
		BigDecimal value = valIncome.subtract(valExpanse);
		BigDecimal valueIdr = valIncomeIdr.subtract(valExpanseIdr);		
		
		params.put("P_COA_CODE", coaCode);
		params.put("P_VALUE", value);
		params.put("P_VALUE_IDR", valueIdr);
		
		logger.info("Generate Balance Sheet with param report : {}.", params);
		
		if(p_Type.equals("pdf"))
			return reportUtils.exportPdf("BalanceSheet.jrxml", params);
		else if(p_Type.equals("excel"))
			return reportUtils.exportExcel("BalanceSheetExcel.jrxml", params);
		else
			return reportUtils.exportHtml("BalanceSheet.jrxml", params);
	}
	
	//============================== CASH FLOW ==============================//

	@Override
	public String createCashFlowExcel(Map<String, String> p_Params) throws Exception {
		return createCashFlow(p_Params, "excel");
	}

	@Override
	public String createCashFlowHtml(Map<String, String> p_Params) throws Exception {
		return createCashFlow(p_Params, "html");
	}

	@Override
	public String createCashFlowPdf(Map<String, String> p_Params) throws Exception {
		return createCashFlow(p_Params, "pdf");
	}
	
	private String createCashFlow(Map<String, String> p_Params, String p_Type) throws Exception {
		Optional<MA0010Entity> company = ma0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		
		String year = p_Params.get("year");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_COMPANY", companyName);
		params.put("P_USER", p_Params.get(Param.USER));
		params.put("P_ASAT", (Calendar.DECEMBER+1) + "/" + cal.getActualMaximum(Calendar.DAY_OF_MONTH) + "/" + year);
		params.put("P_YY", year);
		
		BigDecimal rcOp = tr0001Repo.sumRcByClassesAndYear(Arrays.asList("OP"), year);
		BigDecimal rcIn = tr0001Repo.sumRcByClassesAndYear(Arrays.asList("IN"), year);
		BigDecimal rcFi = tr0001Repo.sumRcByClassesAndYear(Arrays.asList("FI"), year);
		BigDecimal rcLastYear = tr0001Repo.sumRcByClassesAndYear(Arrays.asList("OP", "IN", "FI"), String.valueOf(cal.get(Calendar.YEAR)-1));
		BigDecimal pyLastYear = tr0001Repo.sumPyByClassesAndYear(Arrays.asList("OP", "IN", "FI"), String.valueOf(cal.get(Calendar.YEAR)-1));
		
		rcOp = rcOp == null ? BigDecimal.ZERO : rcOp;
		rcIn = rcIn == null ? BigDecimal.ZERO : rcIn;
		rcFi = rcFi == null ? BigDecimal.ZERO : rcFi;
		rcLastYear = rcLastYear == null ? BigDecimal.ZERO : rcLastYear;
		pyLastYear = pyLastYear == null ? BigDecimal.ZERO : pyLastYear;
		
		params.put("P_VALUE_OP", rcOp);
		params.put("P_VALUE_IN", rcIn);
		params.put("P_VALUE_FI", rcFi);		
		params.put("P_NET_LAST_YEAR", rcLastYear.subtract(pyLastYear));
		
		if(p_Type.equals("pdf"))
			return reportUtils.exportPdf("CashFlow.jrxml", params);
		else if(p_Type.equals("excel"))
			return reportUtils.exportExcel("CashFlowExcel.jrxml", params);
		else
			return reportUtils.exportHtml("CashFlow.jrxml", params);
	}
	
	//============================== GENERAL LEDGER ==============================//

	@Override
	public String createGeneralLedgerExcel(Map<String, Object> p_Params) throws Exception {
		return createGeneralLedger(p_Params, "excel");
	}

	@Override
	public String createGeneralLedgerHtml(Map<String, Object> p_Params) throws Exception {
		return createGeneralLedger(p_Params, "html");
	}

	@Override
	public String createGeneralLedgerPdf(Map<String, Object> p_Params) throws Exception {
		return createGeneralLedger(p_Params, "pdf");
	}
	
	private String createGeneralLedger(Map<String, Object> p_Params, String p_Type) throws Exception {
		Optional<MA0010Entity> company = ma0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		
		Calendar prevCal = Calendar.getInstance();
		Calendar fromStartCal = Calendar.getInstance();
		Calendar fromCal = Calendar.getInstance();
		Calendar toCal = Calendar.getInstance();
		
		fromCal.setTime(Param.getDate(p_Params, "fromDate"));
		toCal.setTime(Param.getDate(p_Params, "toDate"));
		
		fromStartCal.setTime(fromCal.getTime());
		fromStartCal.set(Calendar.DAY_OF_MONTH, 1);
		
		prevCal.setTime(fromCal.getTime());
		prevCal.add(Calendar.MONTH, -1);
		
		Integer monthPrev = prevCal.get(Calendar.MONTH)+1;
		Integer yearPrev = prevCal.get(Calendar.YEAR);
		
		if(monthPrev==12)
			yearPrev = yearPrev+1;
		
		String fromCoa = Param.getStr(p_Params, "fromCoa");
		String toCoa = Param.getStr(p_Params, "toCoa");
		
		if(StringUtils.isBlank(fromCoa))
			fromCoa = ma0004Repo.findMinCoaCode();
		if(StringUtils.isBlank(toCoa))
			toCoa = ma0004Repo.findMaxCoaCode();
		
		String offCode = Param.getStrWithDef(p_Params, "offCode");
		
		List<String> offCodes = new ArrayList<String>();
		if("All".equals(offCode))
			offCodes = ma0014Repo.getOfficeCode();
		else
			offCodes.add(offCode);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_COMPANY", companyName);
		params.put("P_USER", p_Params.get(Param.USER));
		params.put("P_MM_PREV", (monthPrev < 10) ? "0" + monthPrev.toString() : monthPrev.toString());
		params.put("P_YY_PREV", yearPrev.toString());
		params.put("P_FROM_START", formatDateSql.format(fromStartCal.getTime()));
		params.put("P_FROM", formatDateSql.format(fromCal.getTime()));
		params.put("P_TO", formatDateSql.format(toCal.getTime()));
		params.put("P_COA_FROM", fromCoa);
		params.put("P_COA_TO", toCoa);
		params.put("P_OFF_CODE", offCodes);
		params.put("SUBREPORT_DIR", pathReport + "GeneralLedger" + File.separator);
		
		if(p_Type.equals("pdf"))
			return reportUtils.exportPdf("GeneralLedger.jrxml", params);
		else if(p_Type.equals("excel"))
			return reportUtils.exportExcel("GeneralLedgerExcel.jrxml", params);
		else
			return reportUtils.exportHtml("GeneralLedger.jrxml", params);
	}
	
	//============================== PROFIT AND LOST ==============================//

	@Override
	public String createProfitAndLostExcel(Map<String, String> p_Params) throws Exception {
		return createProfitAndLost(p_Params, "excel");
	}

	@Override
	public String createProfitAndLostHtml(Map<String, String> p_Params) throws Exception {
		return createProfitAndLost(p_Params, "html");
	}

	@Override
	public String createProfitAndLostPdf(Map<String, String> p_Params) throws Exception {
		return createProfitAndLost(p_Params, "pdf");
	}
	
	private String createProfitAndLost(Map<String, String> p_Params, String p_Type) throws Exception {
		Optional<MA0010Entity> company = ma0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		
		String month = p_Params.get("month");
		String year = p_Params.get("year");
		
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, Integer.valueOf(month)-1);
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		
		Boolean isAsAt = new Boolean(p_Params.get("isAsAt"));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_COMPANY", companyName);
		params.put("P_USER", p_Params.get(Param.USER));
		params.put("P_MM", month);
		params.put("P_YY", year);
		params.put("P_PERIOD", formatDateMMMyyyy.format(cal.getTime()));
		params.put("P_IS_AS_AT", isAsAt);
		
		Boolean isActualBudget = new Boolean(p_Params.get("isActualBudget"));
		
		String report = "ProfitAndLost.jrxml";
		String reportExcel = "ProfitAndLostExcel.jrxml";
		
		if(isActualBudget) {
			report = "ProfitAndLostBAT.jrxml";
			reportExcel = "ProfitAndLostExcelBAT.jrxml";
			
			if(isAsAt)
				params.put("P_MM2", "01");
			else
				params.put("P_MM2", month);
		}
		
		if(p_Type.equals("pdf"))
			return reportUtils.exportPdf(report, params);
		else if(p_Type.equals("excel"))
			return reportUtils.exportExcel(reportExcel, params);
		else
			return reportUtils.exportHtml(report, params);
	}
	
}
