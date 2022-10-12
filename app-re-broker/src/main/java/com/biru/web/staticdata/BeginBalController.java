package com.biru.web.staticdata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.BEGIN_BAL;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.helper.GL0001Helper;
import com.biru.service.BeginBalService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class BeginBalController {
	
	@Autowired
	private BeginBalService beginBalService;
	
	/* 
	 * Get distinct year from GL0001
	 * */
	@RequestMapping(value = BEGIN_BAL.GET_YEAR, method = RequestMethod.POST)
	public List<DropdownIdText> getYear() throws ParseException {
		return beginBalService.getYear();
	}
	
	/* 
	 * Inquiry beginning balance
	 * return DatatableSet
	 * */
	@RequestMapping(value = BEGIN_BAL.INQUIRY_V1, method = RequestMethod.POST)
	public DatatableSet inquiryV1(@RequestBody Map<String, Object> p_Param) {
		return beginBalService.inquiryV1(p_Param);
	}
	
	/* 
	 * Inquiry beginning balance
	 * return List<GL0001Helper>
	 * */
	@RequestMapping(value = BEGIN_BAL.INQUIRY_V2, method = RequestMethod.POST)
	public List<GL0001Helper> inquiryV2(@RequestBody Map<String, Object> p_Param) {
		return beginBalService.inquiryV2(p_Param);
	}
	
	/* 
	 * Create and update beginning balance
	 * */
	@RequestMapping(value = BEGIN_BAL.SAVE, method = RequestMethod.POST)
	public Boolean save(@RequestBody Map<String, Object> p_Param) throws ParseException {
		try {
			return beginBalService.save(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	/* 
	 * Summary balance debit0
	 * */
	@RequestMapping(value = BEGIN_BAL.SUM_BAL_DEBIT0, method = RequestMethod.POST)
	public BigDecimal summaryBalDebit0(@RequestBody String p_GlBalYear) {
		return beginBalService.sumGlBalDebit0(p_GlBalYear);
	}
	
	/* 
	 * Summary balance credit0
	 * */
	@RequestMapping(value = BEGIN_BAL.SUM_BAL_CREDIT0, method = RequestMethod.POST)
	public BigDecimal summaryBalCredit0(@RequestBody String p_GlBalYear) {
		return beginBalService.sumGlBalCredit0(p_GlBalYear);
	}
	
	/* 
	 * Lookup data chart of account
	 * */
	@RequestMapping(value = BEGIN_BAL.LOOKUP_COA, method = RequestMethod.POST)
	public DatatableSet lookupCoa(@RequestBody Map<String, Object> p_Param) {
		return beginBalService.lookupCoa(p_Param);
	}

}
