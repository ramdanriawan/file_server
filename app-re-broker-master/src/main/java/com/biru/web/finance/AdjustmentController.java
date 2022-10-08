package com.biru.web.finance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.ADJUSTMENT;
import com.biru.ReBrokerConstants.REST.BUSINESS_RULES;
import com.biru.common.entity.DatatableSet;
import com.biru.service.AdjustmentService;

@RestController
@RequestMapping(REST.FINANCE)
public class AdjustmentController {
	
	@Autowired
	AdjustmentService adjService;

	@RequestMapping(value = ADJUSTMENT.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param){
		return adjService.inquiry(p_Param);
	}
	
	@RequestMapping(value = ADJUSTMENT.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> p_Param){
		try {
			return adjService.save(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
}
