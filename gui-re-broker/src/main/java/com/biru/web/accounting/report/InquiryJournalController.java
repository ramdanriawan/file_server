package com.biru.web.accounting.report;

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
import org.springframework.http.ResponseEntity;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(REST.ACCOUNTING_REPORT)
public class InquiryJournalController {
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommonService common;
	
	@RequestMapping(value = REST.ACCTR_INQUIRY_JOURNAL, method = RequestMethod.GET)
	public String entryJournal(Model model) throws JsonParseException, JsonMappingException, ParseException, IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
		String appDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
		
		model.addAttribute("mainMenu", "Accounting  Journal /  Inquiry Journal");
		model.addAttribute("titlePage", "INQUIRY JOURNAL");
		model.addAttribute("appDate", appDate);
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.ACCTR_INQUIRY_JOURNAL;
	}
	
	@RequestMapping(value = REST.ACCTR_INQUIRY_JOURNAL_DROPDOWN_PROJECT, method = RequestMethod.GET)
	public @ResponseBody String dropdownProject() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCTR_INQUIRY_JOURNAL_DROPDOWN_PROJECT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.ACCTR_INQUIRY_JOURNAL_DROPDOWN_OFFICE, method = RequestMethod.GET)
	public @ResponseBody String dropdownOffice() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCTR_INQUIRY_JOURNAL_DROPDOWN_OFFICE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.ACCTR_INQUIRY_JOURNAL_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="activity", defaultValue="all") String activity,
			@RequestParam(value="type", defaultValue="all") String type,
			@RequestParam(value="project", defaultValue="all") String project,
			@RequestParam(value="office", defaultValue="all") String office,
			@RequestParam(value="transactionDate", defaultValue="") String transactionDate,
			@RequestParam(value="to", defaultValue="") String to,
			@RequestParam(value="sort", defaultValue="glTrxDate") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("activity", activity);
		param.put("type", type);
		param.put("project", project);
		param.put("office", office);
		param.put("transactionDate", transactionDate);
		param.put("to", to);
		param.put("sort", sort);
		param.put("order", order);
		param.put("offset", offset);
		param.put("limit", limit);
		
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCTR_INQUIRY_JOURNAL_INQ + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
}
