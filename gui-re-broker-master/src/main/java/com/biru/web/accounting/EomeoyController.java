package com.biru.web.accounting;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(REST.ACCOUNTING)
public class EomeoyController {
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommonService common;
	
	@RequestMapping(value = REST.ACCT_EOM_EOY, method = RequestMethod.GET)
	public String eomeoyIndex(Model model) throws JsonParseException, JsonMappingException, ParseException, IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
		String appDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
		
		List<DropdownIdText> year = common.getYearCanBeProcess(); 
		String lastProMonthAndProYear = common.getLastProMonthAndProYear();
		
		
		model.addAttribute("mainMenu", "Accounting  /  End of Month - End of Year");
		model.addAttribute("titlePage", "EOM - EOY");
		model.addAttribute("appDate", appDate);
		model.addAttribute("year", year);
		model.addAttribute("lastProMonthAndProYear", lastProMonthAndProYear);
		
		
		return HTML.ACCT_EOMEOY;
	}
	
	@RequestMapping(value = REST.ACCT_EOM_EOY_PROCESS, method = RequestMethod.GET)
	public @ResponseBody Object process(
			@RequestParam(value="process") String process,
			@RequestParam(value="month", defaultValue="0") String month,
			@RequestParam(value="year") String year
			) throws JsonParseException, JsonMappingException, ParseException, IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tenant", sessionComponent.getTenantId());
		param.put("month", month);
		param.put("year", year);
		param.put("process", process);
		param.put("user", sessionComponent.getUserLogin());
		
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCT_EOM_EOY_PROCESS+ "?tenantId=" + sessionComponent.getTenantId();
		return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
	}
	
	@RequestMapping(value = REST.ACCT_EOM_EOY_PROGRESS, method = RequestMethod.GET)
	public @ResponseBody String progress() throws JsonParseException, JsonMappingException, ParseException, IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tenant", sessionComponent.getTenantId());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCT_EOM_EOY_PROGRESS+ "?tenantId=" + sessionComponent.getTenantId();
		return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody();
	}	
	
	@RequestMapping(value = REST.ACCT_EOM_EOY_RESET_PROGRESS, method = RequestMethod.GET)
	public @ResponseBody String resetProgress() throws JsonParseException, JsonMappingException, ParseException, IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tenant", sessionComponent.getTenantId());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCT_EOM_EOY_RESET_PROGRESS+ "?tenantId=" + sessionComponent.getTenantId();
		return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody();
	}
	
	@RequestMapping(value = REST.ACCT_EOM_EOY_CHECK_PROCESS, method = RequestMethod.GET)
	public @ResponseBody String checkProcess() throws JsonParseException, JsonMappingException, ParseException, IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tenant", sessionComponent.getTenantId());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCT_EOM_EOY_CHECK_PROCESS+ "?tenantId=" + sessionComponent.getTenantId();
		return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody();
	}
}
