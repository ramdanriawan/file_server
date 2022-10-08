package com.biru.web.marketing;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.NEW_ENDORSEMENT;
import com.biru.service.NewEndorsementService;

@RestController
@RequestMapping(REST.MARKETING)
public class NewEndorsementController {
	@Autowired
	private NewEndorsementService newEndorsementService;
	
	
	@RequestMapping(value = NEW_ENDORSEMENT.GET_REQUEST_ID, method = RequestMethod.POST)
	public Object getRequsetId() throws Exception {
		return newEndorsementService.getRequestId();
	}
	
	@RequestMapping(value = NEW_ENDORSEMENT.CLOSING, method = RequestMethod.POST)
	public Object closing(@RequestBody Map<String, Object> param) throws Exception {
		return newEndorsementService.closing(param);
	}
}
