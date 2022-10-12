package com.biru.web.marketing;

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
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(REST.MARKETING)
public class CancelUploadController {
	@Autowired
	private CommonService common;

	@Autowired 
	private RestTemplate restTemplate;

	@Autowired 
	private SessionComponent sessionComponent;

	@RequestMapping(value = REST.M_CANCEL_UPLOAD, method = RequestMethod.GET)
	public String claims(Model model) throws Exception {
		model.addAttribute("mainMenu", "Marketing  /  Cancel Upload");
		model.addAttribute("titlePage", "CANCEL UPLOAD");

		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}

		return HTML.M_CANCEL_UPLOAD;
	}
	
	@RequestMapping(value = REST.M_CANCEL_UPLOAD_SEARCH, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="client", defaultValue="") String client,
			@RequestParam(value="sort", defaultValue="createOn") String sort,
			@RequestParam(value="order", defaultValue="desc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("client", client);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		System.out.println(param);
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.M_CANCEL_UPLOAD_SEARCH + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_CANCEL_UPLOAD_DELETE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object>param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CANCEL_UPLOAD_DELETE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
}
