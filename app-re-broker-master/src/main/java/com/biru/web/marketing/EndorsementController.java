package com.biru.web.marketing;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.ENDORSEMENT;
import com.biru.service.EndorsementService;

@RestController
@RequestMapping(REST.MARKETING)
public class EndorsementController {
	
	@Autowired
	private EndorsementService endorsementService;
	
	@RequestMapping(value = ENDORSEMENT.REMOVE, method = RequestMethod.POST)
	public Object remove(@RequestBody Map<String, Object> param) throws Exception {
		return endorsementService.cancelClosing(param);
	}
	
	@RequestMapping(value = ENDORSEMENT.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> param) throws Exception {
		System.out.println("edit");
		System.out.println(param);
		return endorsementService.save(param);
	}
	
	@RequestMapping(value = ENDORSEMENT.CREATE_CLOSING_REPORT, method = RequestMethod.POST)
	public Object createClosingReport(@RequestBody Map<String, Object> param) throws Exception {
		return endorsementService.createClosingReport(param);
	}
	
	@RequestMapping(value = ENDORSEMENT.INQ_SEND_TABLE, method = RequestMethod.POST)
	public Object inquirySendTable(@RequestBody Map<String, Object> param) throws Exception {
		return endorsementService.inquirySendTable(param);
	}
}
