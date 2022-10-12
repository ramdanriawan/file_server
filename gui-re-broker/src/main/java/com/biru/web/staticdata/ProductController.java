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
public class ProductController {
	
	@Value("${app.host}")
	private String host;
	
	@Autowired
	private CommonService common;
	
	private String tenantId = "CPU01";
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = REST.SD_PRODUCT, method = RequestMethod.GET)
	public String product(Model model) throws ParseException, JsonParseException, 
			JsonMappingException, IOException {
		model.addAttribute("mainMenu", "Static Data  /  Product");
		model.addAttribute("titlePage", "PRODUCT");
		model.addAttribute("titlePageCreate", "PRODUCT | NEW");
		model.addAttribute("titlePageEdit", "PRODUCT | EDIT");
		model.addAttribute("titleLowerBox", "Supporting Document");
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.SD_PRODUCT;
	}
	
	@RequestMapping(value = REST.SD_PRODUCT_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="filterValue", defaultValue="", required=false) String filterValue,
			@RequestParam(value="sort", defaultValue="tcCode") String sort,	
			@RequestParam(value="order", defaultValue="asc") String order,	
			@RequestParam(value="offset", defaultValue="0") Integer offset,	
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put(Param.FILTER_VALUE, filterValue);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_PRODUCT_INQ + "?tenantId=" + sessionComponent.getTenantId();
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.SD_PRODUCT_DROPDOWN, method = RequestMethod.GET)
	public @ResponseBody String getDropDown() throws JsonProcessingException {
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = host + URI.SD_PRODUCT_DROPDOWN + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.SD_PRODUCT_DROPDOWN_PA_CHILD, method = RequestMethod.GET)
	public @ResponseBody String getDropDownPaChild() throws JsonProcessingException {
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = host + URI.SD_PRODUCT_DROPDOWN_PA_CHILD + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	
	@RequestMapping(value = REST.SD_PRODUCT_INQ_INS, method = RequestMethod.GET)
	public @ResponseBody String inquiryIns(@RequestParam(value="tcCode") String tcCode) throws JsonProcessingException {
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, "tcCode");
		param.put(Param.ORDER, "asc");
		param.put(Param.OFFSET, "0");
		param.put(Param.LIMIT, Integer.MAX_VALUE);
		param.put("tcCode", tcCode);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_PRODUCT_INQ_INS + "?tenantId=" + tenantId;
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.SD_PRODUCT_REMOVE_INS, method = RequestMethod.POST)
	public @ResponseBody String removeIns(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_PRODUCT_REMOVE_INS + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	
	@RequestMapping(value = REST.SD_PRODUCT_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
		System.out.println("MASUK CONTROLLER UI");
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		param.put(Param.CREATE_BY, sessionComponent.getUserLogin());
		param.put(Param.MODIFY_BY, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_PRODUCT_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.SD_PRODUCT_SAVE_INSURANCE, method = RequestMethod.POST)
	public @ResponseBody String saveInsurance(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		param.put(Param.CREATE_BY, sessionComponent.getUserLogin());
		param.put(Param.MODIFY_BY, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_PRODUCT_SAVE_INSURANCE  + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
}
