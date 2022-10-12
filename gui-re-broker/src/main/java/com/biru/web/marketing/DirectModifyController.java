package com.biru.web.marketing;

import java.io.IOException;
import java.util.ArrayList;
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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(REST.MARKETING)
public class DirectModifyController {

	@Autowired
	private CommonService common;

	@Autowired 
	private RestTemplate restTemplate;

	@Autowired 
	private SessionComponent sessionComponent;

	@RequestMapping(value = REST.M_DMODIFY, method = RequestMethod.GET)
	public String directModify(Model model) throws Exception {
		model.addAttribute("mainMenu", "Marketing  /  Direct Modify");
		model.addAttribute("titlePage", "DIRECT MODIFY");
		model.addAttribute("titlePageEdit", "DIRECT MODIFY | EDIT");

		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}

		return HTML.M_DMODIFY;
	}
	
	@RequestMapping(value = REST.M_DMODIFY_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="trxClient") String trxClient,
			@RequestParam(value="trxCoverCode") String trxCoverCode) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, updateSort(sort));
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put("trxClient", trxClient);
		param.put("trxCoverCode", trxCoverCode);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_DMODIFY_INQUIRY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_DMODIFY_INQ_DETAIL, method = RequestMethod.POST)
	public @ResponseBody String inquiryDetail(@RequestBody Map<String, Object> params) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(params), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_DMODIFY_INQUIRY_DETAIL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_DMODIFY_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody Map<String, Object> params) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(params), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_DMODIFY_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = REST.M_DMODIFY_DROPDOWN_TYPE_OF_COVER, method = RequestMethod.GET)
	public @ResponseBody Object getTypeOfCover() throws JsonParseException, JsonMappingException, IOException {
		String typeOfCover = common.getTypeOfCover();
		List<Map<String, Object>> listToc = JsonParser.getParser()
				.readValue(typeOfCover, List.class);
		
		List<Map<String, Object>> newListToc = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> toc : listToc) {
			Map<String, Object> newToc = new HashMap<String, Object>();
			newToc.put("id", toc.get("id"));
			newToc.put("text", toc.get("id"));
			
			newListToc.add(newToc);
		}
		
		return newListToc;
	}

	private String updateSort(String key) {
		if(key.endsWith("Fmt"))
			return key.replace("Fmt", "");
		
		return key;
	}
	
}

