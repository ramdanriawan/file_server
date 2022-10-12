package com.biru.web.marketing;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
public class RenewalController {

	@Autowired
	private CommonService common;

	@Autowired 
	private RestTemplate restTemplate;

	@Autowired 
	private SessionComponent sessionComponent;


	@RequestMapping(value = REST.M_RENEWAL, method = RequestMethod.GET)
	public String renewal(Model model) throws Exception {
		model.addAttribute("mainMenu", "Marketing  /  Renewal");
		model.addAttribute("titlePage", "RENEWAL");
		model.addAttribute("titlePageEdit", "RENEWAL | EDIT");
		model.addAttribute("appDate", common.getAppDate().replaceAll("-", "/"));
		model.addAttribute("urlSearch", "renewal/inquiry");
		model.addAttribute("urlTypeOfCover", "renewal/get-dropdownTypeOfCover");

		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}

		return HTML.M_RENEWAL;
	}

	@RequestMapping(value = REST.M_RENEWAL_DROPDOWN_TYPE_OF_COVER, method = RequestMethod.GET)
	public @ResponseBody String getTypeOfCover() {
		return common.getTypeOfCover();
	}

	@RequestMapping(value = REST.M_RENEWAL_CLIENT, method = RequestMethod.GET)
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

	@RequestMapping(value = REST.M_RENEWAL_INQUIRY, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="typeOfCover") String typeOfCover,
			@RequestParam(value="client") String client,
			@RequestParam(value="expiryDate") String expireDate,
			@RequestParam(value="status") String status,
			@RequestParam(value="to") String to,
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeOfCover", typeOfCover);
		param.put("client", client);
		param.put("expiryDate", expireDate);
		param.put("to", to);
		param.put("status", status);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_RENEWAL_INQUIRY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_RENEWAL_REMOVE, method = RequestMethod.POST)
	public @ResponseBody String remove(
			@RequestBody HashMap<String, Object> param) throws JsonProcessingException {


		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_RENEWAL_REMOVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_RENEWAL_EDIT, method = RequestMethod.POST)
	public @ResponseBody String edit(
			@RequestBody HashMap<String, Object> param) throws JsonProcessingException {

		param.put("user", sessionComponent.getUserLogin());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_RENEWAL_EDIT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	
	@RequestMapping(value = REST.M_RENEWAL_SEND, method = RequestMethod.POST)
	public @ResponseBody String send(
			@RequestBody HashMap<String, Object> param) throws JsonProcessingException {

		param.put("user", sessionComponent.getUserLogin());
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_RENEWAL_SEND + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_RENEWAL_EXPORT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public @ResponseBody Object export(@RequestParam(value="typeOfCover") String typeOfCover,
			@RequestParam(value="client") String client,
			@RequestParam(value="expiryDate") String expireDate,
			@RequestParam(value="status") String status,
			@RequestParam(value="to") String to) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeOfCover", typeOfCover);
		param.put("client", client);
		param.put("expiryDate", expireDate);
		param.put("to", to);
		param.put("status", status);
		
		System.out.println(param);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_RENEWAL_EXPORT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
	    
	    File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=Renewal.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.M_RENEWAL_PRINT, method = RequestMethod.GET)
	public @ResponseBody Object print(@RequestParam(value="typeOfCover") String typeOfCover,
			@RequestParam(value="client") String client,
			@RequestParam(value="expiryDate") String expireDate,
			@RequestParam(value="status") String status,
			@RequestParam(value="to") String to) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeOfCover", typeOfCover);
		param.put("client", client);
		param.put("expiryDate", expireDate);
		param.put("to", to);
		param.put("status", status);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_RENEWAL_PRINT+ "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
	    
	    File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "filename=Renewa.pdf")
	    		.contentType(MediaType.APPLICATION_PDF)
	    		.contentLength(file.length())
	    		.body(resource);
	}
}

