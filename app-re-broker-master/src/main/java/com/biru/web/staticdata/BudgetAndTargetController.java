package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.BUDGET_AND_TARGET;
import com.biru.common.entity.DatatableSet;
import com.biru.service.BudgetAndTargetService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class BudgetAndTargetController {
	
	@Autowired
	private BudgetAndTargetService budgetAndTargetService;
	
	
	/* 
	 * Inquiry beginning balance
	 * return DatatableSet
	 * */
	@RequestMapping(value = BUDGET_AND_TARGET.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) {
		return budgetAndTargetService.inquiry(p_Param);
	}
	
	@RequestMapping(value = BUDGET_AND_TARGET.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> p_Param){
		return budgetAndTargetService.save(p_Param);
	}
	
	@RequestMapping(value = BUDGET_AND_TARGET.EXPORT_EXCEL, method = RequestMethod.POST)
	public Object createBudgetAndTargetExcel(@RequestBody Map<String, Object> param) throws Exception {
		
		return budgetAndTargetService.exportExcel(param);
	}

	@RequestMapping(value = BUDGET_AND_TARGET.COA, method = RequestMethod.POST)
	public Object coa(@RequestBody Map<String, Object> p_Param) {
		return budgetAndTargetService.coa(p_Param);
	}

}
