package com.biru.web.staticdata;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public class BeginBalController {
	
	@Value("${app.host}")
	private String host;

	private String tenantId = "CPU01";
	
	private String user = "dev";
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommonService common;
	
	private static final String FILE_NAME_BEGIN_BAL 	= "BeginBal.xls";
	private static final String SHEET_NAME_BEGIN_BAL 	= "Beginning Balance";

	/* 
	 * page beginning balance
	 * */
	@RequestMapping(value = REST.SD_BEGIN_BAL, method = RequestMethod.GET)
	public String holiday(Model model) throws ParseException, JsonParseException, 
			JsonMappingException, IOException {
		model.addAttribute("mainMenu", "Static Data  /  Beginning Balance");
		model.addAttribute("titlePage", "BEGINNING BALANCE");
		model.addAttribute("titlePageCreate", "BEGINNING BALANCE | NEW");
		model.addAttribute("titlePageEdit", "BEGINNING BALANCE | EDIT");
		model.addAttribute("year", common.getYearOfAppDate());
		model.addAttribute("columns", getColumnImport());
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.SD_BEGIN_BAL;
	}
	
	/* 
	 * inquiry data beginning balance 
	 * */
	@RequestMapping(value = REST.SD_BEGIN_BAL_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="coaCode") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="glBalYear") String glBalYear) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		if(sort.equals("coaDescript"))
			sort = "m_coaDescript";
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put("glBalYear", glBalYear);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.SD_BEGIN_BAL_INQ_V1 + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/* 
	 * get export data beginning balance 
	 * */
	@RequestMapping(value = REST.SD_BEGIN_BAL_EXPORT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public String export(Model model,
			@RequestParam(value="sort", defaultValue="coaCode") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="glBalYear") String glBalYear) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, Integer.MAX_VALUE);
		param.put("glBalYear", glBalYear);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.SD_BEGIN_BAL_INQ_V2 + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		model.addAttribute("excelFile", "beginningBalance");
		model.addAttribute("excelFileName", FILE_NAME_BEGIN_BAL);
		model.addAttribute("excelSheetName", SHEET_NAME_BEGIN_BAL);
		model.addAttribute("body", response.getBody());
		
		return "excelView";
	}
	
	/* 
	 * save data beginning balance 
	 * */
	@RequestMapping(value = REST.SD_BEGIN_BAL_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody Map<String, Object> p_Param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		p_Param.put(Param.USER, user);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(p_Param), httpHeaders);
			
		String uri = host + URI.SD_BEGIN_BAL_SAVE + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/* 
	 * get distinct year beginning balance
	 * */
	@RequestMapping(value = REST.SD_BEGIN_BAL_GET_YEAR, method = RequestMethod.GET)
	public @ResponseBody String getYear() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
			
		String uri = host + URI.SD_BEGIN_BAL_GET_YEAR + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/* 
	 * summary debit0 by year
	 * */
	@RequestMapping(value = REST.SD_BEGIN_BAL_SUM_DEBIT0, method = RequestMethod.GET)
	public @ResponseBody String sumDebit0(@RequestBody String p_GlBalYear) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(p_GlBalYear, httpHeaders);
			
		String uri = host + URI.SD_BEGIN_BAL_SUM_DEBIT0 + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/* 
	 * summary credit0 by year
	 * */
	@RequestMapping(value = REST.SD_BEGIN_BAL_SUM_CREDIT0, method = RequestMethod.GET)
	public @ResponseBody String sumCredit0(@RequestBody String p_GlBalYear) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(p_GlBalYear, httpHeaders);
			
		String uri = host + URI.SD_BEGIN_BAL_SUM_CREDIT0 + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/* 
	 * lookup data coa
	 * */
	@RequestMapping(value = REST.SD_BEGIN_BAL_LOOKUP_COA, method = RequestMethod.GET)
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
			
		String uri = host + URI.SD_BEGIN_BAL_LOOKUP_COA + "?tenantId=" + tenantId;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	public List<LinkedHashMap<String, String>> getColumnImport() throws JsonProcessingException {
		LinkedHashMap<String, String> column1 = new LinkedHashMap<String, String>();
		column1.put("headertext", "COA");
		column1.put("datatype", "string");
		column1.put("datafield", "coaCode");
		
		LinkedHashMap<String, String> column2 = new LinkedHashMap<String, String>();
		column2.put("headertext", "Description");
		column2.put("datatype", "string");
		column2.put("datafield", "coaDescript");
		
		LinkedHashMap<String, String> column3 = new LinkedHashMap<String, String>();
		column3.put("headertext", "Debit");
		column3.put("datatype", "string");
		column3.put("datafield", "glBalDebit0");
		
		LinkedHashMap<String, String> column4 = new LinkedHashMap<String, String>();
		column4.put("headertext", "Credit");
		column4.put("datatype", "string");
		column4.put("datafield", "glBalCredit0");
		
		List<LinkedHashMap<String, String>> columns = new ArrayList<LinkedHashMap<String, String>>();
		columns.add(column1);
		columns.add(column2);
		columns.add(column3);
		columns.add(column4);
		
		return columns;
	}
	
}