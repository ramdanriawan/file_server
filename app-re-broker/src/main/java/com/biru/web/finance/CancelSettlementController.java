package com.biru.web.finance;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.CANCEL_SETTLEMENT;
import com.biru.common.entity.DatatableSet;
import com.biru.service.CancelSettlementService;

@RestController
@RequestMapping(REST.FINANCE)
public class CancelSettlementController {
	
	@Autowired
	private CancelSettlementService cancelSettlementService;
	
	@RequestMapping(value = CANCEL_SETTLEMENT.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) throws ParseException{
		return cancelSettlementService.inquiry(p_Param);
	}
	
	@RequestMapping(value = CANCEL_SETTLEMENT.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> p_Param){
		try {
			return cancelSettlementService.save(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
}
