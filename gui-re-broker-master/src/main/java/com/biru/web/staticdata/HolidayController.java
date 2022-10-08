package com.biru.web.staticdata;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.service.CommonService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(REST.STATIC_DATA)
public class HolidayController {
	
	@Value("${app.host}")
	private String host;

	private String tenantId = "CPU01";
	
	private String user = "dev";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommonService common;

	/* 
	 * page holiday
	 * */
	@RequestMapping(value = REST.SD_HOLIDAY, method = RequestMethod.GET)
	public String holiday(Model model) throws ParseException, JsonParseException, JsonMappingException, IOException {
		model.addAttribute("mainMenu", "Static Data  /  Holiday");
		model.addAttribute("titlePage", "HOLIDAY");
		model.addAttribute("titlePageCreate", "HOLIDAY | NEW");
		model.addAttribute("titlePageEdit", "HOLIDAY | EDIT");
		model.addAttribute("appDateNext", common.getAppDateNext());
		model.addAttribute("appDateNextDP", common.getAppDateNextDP());
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.SD_HOLIDAY;
	}
	
	/* 
	 * inquiry data holiday 
	 * */
	@RequestMapping(value = REST.SD_HOLIDAY_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="holiDate") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		if(sort.equals("holiDateFormat"))
			sort = "holiDate";
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.SD_HOLIDAY_INQ + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/* 
	 * validation is holiDate exist 
	 * */
	@RequestMapping(value = REST.SD_HOLIDAY_IS_EXSIST, method = RequestMethod.POST)
	public @ResponseBody String isExsist(@RequestBody String p_Date) {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(p_Date, httpHeaders);
			
		String uri = host + URI.SD_HOLIDAY_IS_EXSIST + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/* 
	 * save data holiday 
	 * */
	@RequestMapping(value = REST.SD_HOLIDAY_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody Map<String, Object> p_Param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		p_Param.put(Param.USER, user);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(p_Param), httpHeaders);
			
		String uri = host + URI.SD_HOLIDAY_SAVE + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
}