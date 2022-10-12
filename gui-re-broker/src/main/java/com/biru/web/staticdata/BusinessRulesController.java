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
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(REST.STATIC_DATA)
public class BusinessRulesController {

	@Value("${app.host}")
	private String host;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String tenantId = "CPU01";
	
	private String user = "dev";
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	/**
	 * GUI Page Controller
	 * */
	@RequestMapping(value = REST.SD_BUSINESS_RULES, method = RequestMethod.GET)
	public String businessRules(Model model) throws JsonParseException, JsonMappingException, ParseException, IOException {
		model.addAttribute("mainMenu", "Static Data  /  Business Rules");
		model.addAttribute("titlePage", "BUSINESS RULES");
		model.addAttribute("titlePageCreate", "BUSINESS RULES | NEW");
		model.addAttribute("titlePageEdit", "BUSINESS RULES | EDIT");
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.SD_BUSINESS_RULES;
	}
	
	/**
	 * Get Inquiry Data Business Rules Controller
	 * */
	@RequestMapping(value = REST.SD_BUSINESS_RULES_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="brCode") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="filterValue") String filterValue) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put(Param.FILTER_VALUE, filterValue);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_BR_INQ + "?tenantId=" + tenantId;
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/**
	 * Get Inquiry Child Data Business Rules Controller
	 * */
	@RequestMapping(value = REST.SD_BUSINESS_RULES_INQ_CHILD, method = RequestMethod.GET)
	public @ResponseBody String inquiryChild(
			@RequestParam(value="brCode") String brCode) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, "brChildCoa");
		param.put(Param.ORDER, "asc");
		param.put(Param.OFFSET, "0");
		param.put(Param.LIMIT, Integer.MAX_VALUE);
		param.put("brCode", brCode);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_BR_INQ_CHILD + "?tenantId=" + tenantId;
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/**
	 * Saving Data Business Rules Controller
	 * */
	@RequestMapping(value = REST.SD_SD_BUSINESS_RULES_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_BR_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
}
