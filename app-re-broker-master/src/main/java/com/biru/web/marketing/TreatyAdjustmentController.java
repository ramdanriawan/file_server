package com.biru.web.marketing;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.TADJUSTMENT;
import com.biru.service.TreatyAdjustmentService;

@RestController
@RequestMapping(REST.MARKETING)
public class TreatyAdjustmentController {

	@Autowired
	private TreatyAdjustmentService treatyAdjustmentService;
	
	@RequestMapping(value = TADJUSTMENT.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> param) {
		return treatyAdjustmentService.inquiry(param);
	}
	
	@RequestMapping(value = TADJUSTMENT.INQUIRY_MODIFY, method = RequestMethod.POST)
	public Object inquiryModify(@RequestBody Map<String, Object> param) {
		return treatyAdjustmentService.inquiryModify(param);
	}
	
	@RequestMapping(value = TADJUSTMENT.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> param) {
		return treatyAdjustmentService.save(param);
	}
	
	@RequestMapping(value = TADJUSTMENT.PROCESS, method = RequestMethod.POST)
	public Object process(@RequestBody Map<String, Object> param) throws Exception {
		return treatyAdjustmentService.process(param);
	}
	
	@RequestMapping(value = TADJUSTMENT.CANCEL, method = RequestMethod.POST)
	public Object cancel(@RequestBody Map<String, Object> param) throws ParseException {
		return treatyAdjustmentService.cancel(param);
	}
	
}
