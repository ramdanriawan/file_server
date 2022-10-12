package com.biru.web.finance;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.SETTLEMENT;
import com.biru.common.entity.DatatableSet;
import com.biru.entity.ResponseEntity;
import com.biru.service.SettlementService;

@RestController
@RequestMapping(REST.FINANCE)
public class SettlementController {
	
	@Autowired
	private SettlementService settlementService;
	
	@RequestMapping(value = SETTLEMENT.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) {
		
		return settlementService.inquiry(p_Param);
	}
	
	@RequestMapping(value = SETTLEMENT.SAVE, method = RequestMethod.POST)
	public ResponseEntity save(@RequestBody Map<String, Object> p_Param) {
		try {
			return settlementService.save(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			
			ResponseEntity response = new ResponseEntity();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			response.setError(e.getMessage());
			return response;
		}
	}
	
}
