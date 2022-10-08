package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.AGENT_OFFICER;
import com.biru.common.entity.DatatableSet;
import com.biru.service.AgentOfficerService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class AgentOfficerController {
	
	@Autowired
	public AgentOfficerService agentService;
	
	@RequestMapping(value = AGENT_OFFICER.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> p_Param){
		try {
			return agentService.save(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	@RequestMapping(value = AGENT_OFFICER.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param){
		return agentService.inquiry(p_Param);
	}
	
}
