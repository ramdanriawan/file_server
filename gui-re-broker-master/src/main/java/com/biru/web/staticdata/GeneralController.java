package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biru.GuiConstants.REST;
import com.biru.service.CommonService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(REST.GENERAL)
public class GeneralController {

	@Autowired
	private CommonService common;
	
	@RequestMapping(value = REST.GET_PARAMETER, method = RequestMethod.GET)
	public @ResponseBody String getParameter(
			@RequestParam(value="parentCode") String parentCode,
			@RequestParam(value="childCode") String childCode) throws JsonProcessingException {
		
		return common.getParameter(parentCode, childCode);
	}
	
	@RequestMapping(value = REST.IS_ALREADY_CLOSING, method = RequestMethod.POST)
	public @ResponseBody Boolean isAlreadyClosing(@RequestBody Map<String, String> param) throws JsonProcessingException {
		
		return common.isAlreadyClosing(param.get("trxId"), param.get("trxVoucherId"));
	}
	
}
