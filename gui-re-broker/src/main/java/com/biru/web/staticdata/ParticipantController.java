package com.biru.web.staticdata;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class ParticipantController {
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = REST.SD_PARTICIPANT, method = RequestMethod.GET)
	public String product(Model model) throws ParseException, JsonParseException, 
			JsonMappingException, IOException {
		model.addAttribute("mainMenu", "Static Data  /  Participant");
		model.addAttribute("titlePage", "PARTICIPANT");
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.SD_PARTICIPANT;
	}
	
	@RequestMapping(value = REST.SD_PARTICIPANT_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry() {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_PARTICIPANT_INQ + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.SD_PARTICIPANT_DROPDOWN_PARTICIPANT_STATUS, method = RequestMethod.GET)
	public @ResponseBody String dropdownParticipantStatus() {
		return common.dropdownParticipantStatus();
	}
	
	@RequestMapping(value = REST.SD_PARTICIPANT_DROPDOWN_PARTICIPANT_COUNTRY, method = RequestMethod.GET)
	public @ResponseBody String dropdownParticipantCountry() {
		return common.dropdownParticipantCountry();
	}
	
	@RequestMapping(value = REST.SD_PARTICIPANT_DROPDOWN_PARTICIPANT_INDUSTRY, method = RequestMethod.GET)
	public @ResponseBody String dropdownParticipantIndustry() {
		return common.dropdownParticipantIndustry();
	}
	
	@RequestMapping(value = REST.SD_PARTICIPANT_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		param.put(Param.CREATE_BY, sessionComponent.getUserLogin());
		param.put(Param.MODIFY_BY, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_PARTICIPANT_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
}
