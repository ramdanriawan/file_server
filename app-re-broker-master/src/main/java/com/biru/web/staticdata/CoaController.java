package com.biru.web.staticdata;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.COA;
import com.biru.common.entity.DatatableSet;
import com.biru.entity.MA0004Entity;
import com.biru.service.CoaService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class CoaController {
	
	@Autowired
	private CoaService coaService;
	
	@RequestMapping(value = COA.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) {
		return coaService.inquiry(p_Param);
	}
	
	@RequestMapping(value = COA.DROPDOWN_CLASS, method = RequestMethod.POST)
	public @ResponseBody Object getDropdownClass() {
		return coaService.getDropdownClass();
	}
	
	@RequestMapping(value = COA.DROPDOWN_CURR, method = RequestMethod.POST)
	public @ResponseBody Object getDropdownCurr() {
		return coaService.getDropdownCurr();
	}
	
	@RequestMapping(value = COA.ROLE_UP, method = RequestMethod.POST)
	public DatatableSet roleUp(@RequestBody Map<String, Object> p_Param) {
		return coaService.roleUp(p_Param);
	}
	
	@RequestMapping(value = COA.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> p_Param) throws ParseException {
		return coaService.save(p_Param);
	}
}
