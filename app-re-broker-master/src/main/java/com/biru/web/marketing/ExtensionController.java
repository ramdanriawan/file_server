package com.biru.web.marketing;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.EXTENSION;
import com.biru.common.entity.DatatableSet;
import com.biru.entity.ResponseEntity;
import com.biru.service.ExtensionService;

@RestController
@RequestMapping(REST.MARKETING)
public class ExtensionController {
	
	@Autowired
	private ExtensionService extensionService;
	
	@RequestMapping(value = EXTENSION.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) throws ParseException{
		return extensionService.inquiry(p_Param);
	}

	@RequestMapping(value = EXTENSION.ENTRY, method = RequestMethod.POST)
	public Object entry(@RequestBody Map<String, Object> p_Param){
		try {
			return extensionService.entry(p_Param);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity response = new ResponseEntity();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			response.setError(e.getMessage());
			return response;
		}
	}
	
	@RequestMapping(value = EXTENSION.UPLOAD, method = RequestMethod.POST)
	public Object upload(@RequestBody Map<String, Object> p_Param)throws Exception{
		return extensionService.upload(p_Param);
		/*try {
			return extensionService.upload(p_Param);
		} catch (Exception e) {
			e.printStackTrace();
			ResponseEntity response = new ResponseEntity();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			response.setError(e.getMessage());
			return response;
		}*/
	}
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public Object testUpload(@RequestBody Map<String, Object> p_Param) throws IOException, InvalidFormatException{
		extensionService.readFile(p_Param);
		return p_Param;
	}
	
	@RequestMapping(value = EXTENSION.ISFILENAME_EXIST, method = RequestMethod.POST)
	public Object isFileNameExist(@RequestBody Map<String, Object> p_Param)throws Exception{
		return extensionService.isFileNameExist(p_Param);
		
	}
}
