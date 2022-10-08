package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.PARAMETER;
import com.biru.common.entity.DatatableSet;
import com.biru.service.ParameterService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class ParameterController {

	@Autowired
	private ParameterService parameterService;
	
	@RequestMapping(value = PARAMETER.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> p_Param){
		try {
			return parameterService.save(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	@RequestMapping(value = PARAMETER.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param){
		return parameterService.inquiry(p_Param);
	}

	@RequestMapping(value = PARAMETER.INQUIRY_CHILD, method = RequestMethod.POST)
	public DatatableSet inquiryChild(@RequestBody Map<String, Object> p_Param){
		return parameterService.inquiryChild(p_Param);
	}
}
