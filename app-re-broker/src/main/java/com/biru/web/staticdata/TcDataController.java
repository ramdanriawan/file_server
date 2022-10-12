package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.TC_DATA;
import com.biru.service.TcDataService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class TcDataController {
	
	@Autowired
	private TcDataService tcDataService;
	
	@RequestMapping(value = TC_DATA.INQUIRY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object inquiry(@RequestBody Map<String, Object> param) {
		return tcDataService.inquiry(param);
	}
	@RequestMapping(value = TC_DATA.SAVE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object save(@RequestBody Map<String, Object> param) {
		return tcDataService.save(param);
	}
	@RequestMapping(value = TC_DATA.DELETE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object delete(@RequestBody Map<String, Object> param) {
		return tcDataService.delete(param);
	}
	
}
