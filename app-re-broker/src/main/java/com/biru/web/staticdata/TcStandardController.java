package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.TC_STANDARD;
import com.biru.common.entity.DatatableSet;
import com.biru.service.TcStandardService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class TcStandardController {
	
	@Autowired
	private TcStandardService tcStandardService;
	
	@RequestMapping(value = TC_STANDARD.INQUIRY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public DatatableSet inquiry(@RequestBody Map<String, Object> param) {
		return tcStandardService.inquiry(param);
	}
	
	@RequestMapping(value = TC_STANDARD.DESCRIPTION, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object description(@RequestBody Map<String, Object> param) {
		return tcStandardService.description(param);
	}
	
	@RequestMapping(value = TC_STANDARD.SAVE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object save(@RequestBody Map<String, Object> param) {
		return tcStandardService.save(param);
	}
	
	@RequestMapping(value = TC_STANDARD.DELETE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object delete(@RequestBody Map<String, Object> param) {
		return tcStandardService.delete(param);
	}
	
}
