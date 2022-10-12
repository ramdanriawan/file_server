package com.biru.web.accounting;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.EOMEOY;
import com.biru.service.EomEoyService;

@RestController
@RequestMapping(REST.ACCOUNTING)
public class EomEoyController {
	
	@Autowired
	private EomEoyService eomEoyService;
	
	@RequestMapping(value = EOMEOY.CHECK_PROCESS, method = RequestMethod.POST)
	public Object checkProcess(@RequestBody Map<String, Object> p_Param) {
		return eomEoyService.isProcessStillRunning(p_Param);
	}
	
	@RequestMapping(value = EOMEOY.PROGRESS, method = RequestMethod.POST)
	public Object progress(@RequestBody Map<String, Object> p_Param) {
		return eomEoyService.getProgress(p_Param);
	}
	
	@RequestMapping(value = EOMEOY.RESET_PROGRESS, method = RequestMethod.POST)
	public Object resetProgress(@RequestBody Map<String, Object> p_Param) {
		eomEoyService.resetProgress(p_Param);
		return p_Param;
	}
	
	@RequestMapping(value = EOMEOY.PROCESS, method = RequestMethod.POST)
	public Object processEom(@RequestBody Map<String, Object> p_Param) {
		eomEoyService.initProcess(p_Param);
		return p_Param;
	}
	
}
