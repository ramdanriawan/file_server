package com.biru.web.marketing;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.DMODIFY;
import com.biru.service.DirectModifyService;

@RestController
@RequestMapping(REST.MARKETING)
public class DirectModifyController {

	@Autowired
	private DirectModifyService directModifyService;
	
	@RequestMapping(value = DMODIFY.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> params) {
		return directModifyService.inquiry(params);
	}
	
	@RequestMapping(value = DMODIFY.INQUIRY_DETAIL, method = RequestMethod.POST)
	public Object inquiryDetail(@RequestBody Map<String, Object> params) {
		return directModifyService.inquiryDetail(params);
	}
	
	@RequestMapping(value = DMODIFY.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> params) {
		return directModifyService.save(params);
	}
	
}
