package com.biru.web.staticdata;

import java.io.IOException;
import java.math.BigDecimal;
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
public class ExchangeRateController {

	@Value("${app.host}")
	private String host;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String tenantId = "CPU01";
	
	private String user = "dev";
	
	@Autowired
	private CommonService common;
	
	/**
	 * GUI Page Controller
	 * */
	@RequestMapping(value = REST.SD_EXCHANGE_RATE, method = RequestMethod.GET)
	public String exchangeRate(Model model) throws JsonParseException, JsonMappingException, ParseException, IOException {
		model.addAttribute("mainMenu", "Static Data  /  Exchange Rate");
		model.addAttribute("titlePage", "EXCHANGE RATE");
		model.addAttribute("titlePageCreate", "EXCHANGE RATE | NEW");
		model.addAttribute("titlePageEdit", "EXCHANGE RATE | EDIT");
		model.addAttribute("titleKMK", "TAX RATE (KMK)");
		model.addAttribute("titlePageCreateKMK", "TAX RATE (KMK) | NEW");
		model.addAttribute("titlePageEditKMK", "TAX RATE (KMK) | EDIT");
		model.addAttribute("appDateNext", common.getAppDateNext());
		model.addAttribute("appDateNextDP", common.getAppDateNextDP());
		model.addAttribute("appDate", common.getAppDate());
		model.addAttribute("appDatetDP", common.getAppDateDP());
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.SD_EXCHANGE_RATE;
	}
	
	/**
	 * Dropdown Currency Controller
	 * */
	@RequestMapping(value = REST.SD_EXCHANGE_RATE_DROPDOWN_CURR, method = RequestMethod.GET)
	public @ResponseBody String dropdownCurr() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = host + URI.SD_ER_DROPDOWN_CURR + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/**
	 * Saving Data Exchange Rate Controller
	 * */
	@RequestMapping(value = REST.SD_EXCHANGE_RATE_SAVE, method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		param.put(Param.USER, user);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_ER_SAVE + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();

	}
	
	/**
	 * Get Inquiry Data Exchange Rate Controller
	 * */
	@RequestMapping(value = REST.SD_EXCHANGE_RATE_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="exYear") String sort,
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
		
		String uri = host + URI.SD_ER_INQ + "?tenantId=" + tenantId;
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.SD_EXCHANGE_DROPDOWN_CURR, method = RequestMethod.GET)
	public @ResponseBody String currDd() {
		return common.dropdownCurrTax();
	}
	
	// Get Inquiry Data Exchange KMK Rate Controller
	
	@RequestMapping(value = REST.SD_EXCHANGE_RATE_INQ_KMK, method = RequestMethod.GET)
	public @ResponseBody String inquirykmk(
			@RequestParam(value="sort", defaultValue="exDate") String sort,
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
		
		String uri = host + URI.SD_ER_INQ_KMK + "?tenantId=" + tenantId;
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	
	@RequestMapping(value = REST.SD_EXCHANGE_RATE_SAVE_KMK, method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object savekmk(@RequestBody Map<String, Object> param) throws JsonProcessingException {
		param.put(Param.USER, user);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_ER_SAVE_KMK + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();

	}
	
	@RequestMapping(value = REST.SD_EXCHANGE_RATE_REMOVE_KMK, method = RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object removekmk(@RequestBody Map<String, Object> param) throws JsonProcessingException {
		param.put(Param.USER, user);
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_ER_REMOVE_KMK + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();

	}
}
