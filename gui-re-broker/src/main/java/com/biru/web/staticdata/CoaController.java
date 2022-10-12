package com.biru.web.staticdata;
import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
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
public class CoaController {
	
	@Value("${app.host}")
	private String host;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String user = "dev";
	
	private String tenantId = "CPU01";

	@Autowired
	private CommonService common;
	
	@RequestMapping(value = REST.SD_COA, method = RequestMethod.GET)
	public String holiday(Model model) throws JsonParseException, JsonMappingException, ParseException, IOException {
		model.addAttribute("mainMenu", "Static Data  /  Chart of Account");
		model.addAttribute("titlePage", "CHAR OF ACCOUNT");
		model.addAttribute("titlePageCreate", "CHAR OF ACCOUNT | NEW");
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.SD_COA;
	}
	
	@RequestMapping(value = REST.SD_COA_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="sort", defaultValue="coaCode") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.FILTER_KEY, filterKey);
		param.put(Param.FILTER_VALUE, filterValue);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.SD_COA_INQ + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.SD_COA_DROPDOWN_CLASS, method = RequestMethod.GET)
	public @ResponseBody String dropdownClass() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = host + URI.SD_COA_DROPDOWN_CLASS + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();

	}
	
	@RequestMapping(value = REST.SD_COA_DROPDOWN_CURR, method = RequestMethod.GET)
	public @ResponseBody String dropdownCurr() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = host + URI.SD_COA_DROPDOWN_CURR + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();

	}
	
	@RequestMapping(value = REST.SD_COA_ROLE_UP, method = RequestMethod.GET)
	public @ResponseBody String roleUp(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="sort", defaultValue="coaCode") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.FILTER_KEY, filterKey);
		param.put(Param.FILTER_VALUE, filterValue);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.SD_COA_ROLE_UP + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	
	@RequestMapping(value = REST.SD_COA_SAVE, method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		param.put(Param.CREATE_BY, user);
		param.put(Param.CREATE_ON, now);
		param.put(Param.MODIFY_BY, user);
		param.put(Param.MODIFY_ON, now);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_COA_SAVE + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();

	}
}
