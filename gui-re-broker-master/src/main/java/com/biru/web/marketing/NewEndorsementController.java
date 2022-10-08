package com.biru.web.marketing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
public class NewEndorsementController {

	@Autowired
	private CommonService common;

	@Autowired 
	private RestTemplate restTemplate;

	@Autowired 
	private SessionComponent sessionComponent;


	@RequestMapping(value = REST.M_NEW_ENDORSEMENT, method = RequestMethod.GET)
	public String endorsement(Model model) throws Exception {
		model.addAttribute("mainMenu", "Marketing  /  Endorsement");
		model.addAttribute("titlePage", "ENDORSEMENT");
		model.addAttribute("titlePageEdit", "ENDORSEMENT | EDIT");
		model.addAttribute("appDate", common.getAppDate().replaceAll("-", "/"));
		model.addAttribute("urlClient", "new-endorsement/client");
		model.addAttribute("urlSearch", "new-endorsement/inquiry");
		model.addAttribute("urlTypeOfCover", "endorsement/get-dropdownTypeOfCover");
		model.addAttribute("isValidUserLevel", validationUserLevel());
		model.addAttribute("isEndorsement", true);
		
		model.addAttribute("pPpn", common.getParameter("TAXRATEH", "TAXRATEH003"));
		model.addAttribute("taxRate009", common.getParameter("TAXRATEH", "TAXRATEH009"));

		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}

		return HTML.M_NEW_ENDORSEMENT;
	}

	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_DROPDOWN_TYPE_OF_COVER, method = RequestMethod.GET)
	public @ResponseBody String getTypeOfCover() {
		return common.getTypeOfCover();
	}

	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_CLIENT, method = RequestMethod.GET)
	public @ResponseBody String client(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="client", required=false) String cliType,
			@RequestParam(value="cliDataStatus", required=false) String cliDataStatus,
			@RequestParam(value="sort", defaultValue="cliName") String sort,
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
		param.put("cliType", cliType);
		param.put("cliDataStatus", cliDataStatus);

		return common.lookupClient(param);
	}

	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_INQUIRY, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="typeOfCover") String typeOfCover,
			@RequestParam(value="client") String client,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="to") String to,
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeOfCover", typeOfCover);
		param.put("client", client);
		param.put("transactionDate", transactionDate);
		param.put("to", to);
		param.put("isEndorsement", true);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		System.out.println(param);
		return common.tr0006Inquiry(param);
	}

	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_REMOVE, method = RequestMethod.POST)
	public @ResponseBody String remove(@RequestBody HashMap<String, Object> p_Param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		p_Param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(p_Param), httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_ENDORSEMENT_REMOVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();	
	}
	
	
	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object> p_Param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		p_Param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(p_Param), httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_ENDORSEMENT_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();	
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_CREATE_CLOSING_REPORT, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String closingSearch(@RequestParam(value="trxVoucherId") String trxVoucherId) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("trxVoucherId", trxVoucherId);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_ENDORSEMENT_CREATE_CLOSING_REPORT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		 System.out.println(response.getBody());
		if(response.getBody() == null)
			return "";
		
		Map<String, Object> result = JsonParser.getParser().readValue(response.getBody(), Map.class);
		List<String> report = (List<String>) result.get("report");
		
		Path path;
		String content;
		String htmlContent = "";
		for(int i=0; i<report.size(); i++) {			
			path = Paths.get(report.get(i));
			content = new String(Files.readAllBytes(path));
			htmlContent += content;
			
			Files.delete(path);
		}
		
		htmlContent = htmlContent.replaceAll("font-size:", "x-size:");
		htmlContent = htmlContent.replaceAll("SansSerif", "Arial");
		
		return htmlContent;
	}
	
	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_CLOSING, method = RequestMethod.POST)
	public @ResponseBody String closing(@RequestBody Map<String, Object> param) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_NEW_ENDORSEMENT_CLOSING + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
	    return response.getBody();
	}
	
	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_INQ_SEND_TABLE, method = RequestMethod.POST)
	public @ResponseBody String inquirySendTable(@RequestBody HashMap<String, Object> p_Param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(p_Param), httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_ENDORSEMENT_INQUIRY_SEND_TABLE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_GET_REQUSET_ID, method = RequestMethod.GET)
	public @ResponseBody String getRequestId() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(new HashMap<String, Object>()), httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_NEW_ENDORSEMENT_GET_REQUEST_ID + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_NEW_ENDORSEMENT_VALIDATION_USER_LEVEL, method = RequestMethod.POST)
	public @ResponseBody String validationUserLevel() throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.USER, sessionComponent.getUserLogin());
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.MR_VALIDATION_USER_LEVEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
	    return response.getBody();
	}
	
}

