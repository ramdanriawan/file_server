package com.biru.web.marketing;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.RENEWAL;
import com.biru.service.RenewalService;

@RestController
@RequestMapping(REST.MARKETING)
public class RenewalController {
	@Autowired
	private RenewalService renewalService;
	
	@RequestMapping(value = RENEWAL.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> param) throws Exception {
		return renewalService.inquiry(param);
	}
	
	@RequestMapping(value = RENEWAL.REMOVE, method = RequestMethod.POST)
	public Object remove(@RequestBody Map<String, Object> param) throws Exception {
		return renewalService.remove(param);
	}
	
	@RequestMapping(value = RENEWAL.EDIT, method = RequestMethod.POST)
	public Object edit(@RequestBody Map<String, Object> param) throws Exception {
		return renewalService.edit(param);
	}
	
	@RequestMapping(value = RENEWAL.SEND, method = RequestMethod.POST)
	public Object send(@RequestBody Map<String, Object> param) throws Exception {
		return renewalService.send(param);
	}
	
	@RequestMapping(value = RENEWAL.EXPORT, method = RequestMethod.POST)
	public Object export(@RequestBody Map<String, Object> param) throws Exception {
		return renewalService.export(param);
	}
	
	@RequestMapping(value = RENEWAL.PRINT, method = RequestMethod.POST)
	public Object print(@RequestBody Map<String, Object> param) throws Exception {
		return renewalService.print(param);
	}
}
