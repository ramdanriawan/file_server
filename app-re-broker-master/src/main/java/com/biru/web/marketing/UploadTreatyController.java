package com.biru.web.marketing;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.EXTENSION;
import com.biru.ReBrokerConstants.REST.UPLOAD_TREATY;
import com.biru.common.entity.DatatableSet;
import com.biru.service.UploadTreatyService;

@RestController
@RequestMapping(REST.MARKETING)
public class UploadTreatyController {
	
	@Autowired
	private UploadTreatyService uploadTreatyService;
	
	@RequestMapping(value = UPLOAD_TREATY.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) throws ParseException{
		return uploadTreatyService.inquiry(p_Param);
	}

	@RequestMapping(value = UPLOAD_TREATY.UPLOAD, method = RequestMethod.POST)
	public Object upload(@RequestBody Map<String, Object> p_Param)throws Exception{
		return uploadTreatyService.upload(p_Param);
	}
	
	@RequestMapping(value = UPLOAD_TREATY.VALIDATE_SOURCE_CODE, method = RequestMethod.POST)
	public Object validateSourceCode(@RequestBody Map<String, Object> p_Param)throws Exception{
		return uploadTreatyService.validateSource(p_Param);
	}
}
