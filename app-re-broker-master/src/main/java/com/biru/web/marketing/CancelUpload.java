package com.biru.web.marketing;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.CANCEL_UPLOAD;
import com.biru.service.CancelUploadService;

@RestController
@RequestMapping(REST.MARKETING)
public class CancelUpload {
	
	@Autowired
	private CancelUploadService cancelUploadService;
	
	@RequestMapping(value = CANCEL_UPLOAD.SEARCH, method = RequestMethod.POST)
	public Object search(@RequestBody Map<String, Object> param){
		return cancelUploadService.search(param);
	}
	
	@RequestMapping(value = CANCEL_UPLOAD.DELETE, method = RequestMethod.POST)
	public Object delete(@RequestBody Map<String, Object> param){
		return cancelUploadService.delete(param);
	}

}
