package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.BUSINESS_RULES;
import com.biru.common.entity.DatatableSet;
import com.biru.service.BusinessRulesService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class BusinessRulesController {

	@Autowired
	private BusinessRulesService brService;
	
	@RequestMapping(value = BUSINESS_RULES.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> p_Param){
		try {
			return brService.save(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	@RequestMapping(value = BUSINESS_RULES.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param){
		return brService.inquiry(p_Param);
	}

	@RequestMapping(value = BUSINESS_RULES.INQUIRY_CHILD, method = RequestMethod.POST)
	public DatatableSet inquiryChild(@RequestBody Map<String, Object> p_Param){
		return brService.inquiryChild(p_Param);
	}
}
