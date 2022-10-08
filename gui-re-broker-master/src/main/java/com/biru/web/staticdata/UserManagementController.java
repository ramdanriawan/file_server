package com.biru.web.staticdata;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
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
import com.fasterxml.jackson.databind.ObjectMapper;

@Controller
@RequestMapping(REST.STATIC_DATA)
public class UserManagementController {

	@Value("${app.host}")
	private String host;
	
	@Autowired
	private RestTemplate restTemplate;
	
	private String tenantId = "CPU01";
	
//	private String user = "dev";
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private InMemoryUserDetailsManager inMemoryUserDetailsManager;
	
	@Autowired
	private PasswordEncoder passwordEncoder; 
	
	/**
	 * GUI Page Controller
	 * */
	
	
	// GROUP MANAGEMENT 
	
	@RequestMapping(value = REST.SD_GROUPMANAGEMENT, method = RequestMethod.GET)
	public String groupManagement(Model model) throws JsonParseException, JsonMappingException, ParseException, IOException {
		model.addAttribute("mainMenu", "Static Data  /  Group Management");
		model.addAttribute("titlePage", "GROUP MANAGEMENT");
		model.addAttribute("titlePageCreate", "GROUP MANAGEMENT | NEW");
		model.addAttribute("titlePageEdit", "GROUP MANAGEMENT | EDIT");
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.SD_GROUPMANAGEMENT;
	}
	
	/**
	 * Get Inquiry Data Business Rules Controller
	 * */
	@RequestMapping(value = REST.SD_GROUPMANAGEMENT_INQ, method = RequestMethod.GET)
	public @ResponseBody String groupInquiry(
			@RequestParam(value="sort", defaultValue="groupId") String sort,	
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

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_GROUPMANAGEMENT_INQ + "?tenantId=" + tenantId;
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/**
	 * Get Inquiry Child Data Business Rules Controller
	 * */
	@RequestMapping(value = REST.SD_GROUPMANAGEMENT_INQ_CHILD, method = RequestMethod.GET)
	public @ResponseBody String groupInquiryChild(
			@RequestParam(value="groupId") String groupId,
			@RequestParam(value="sort", defaultValue="groupId") String sort,	
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
		param.put("groupId", groupId);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_GROUPMANAGEMENT_INQ_CHILD + "?tenantId=" + tenantId;
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/**
	 * Saving Data Business Rules Controller
	 * */
	@RequestMapping(value = REST.SD_GROUPMANAGEMENT_SAVE, method = RequestMethod.POST)
	public @ResponseBody String groupSave(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_GROUPMANAGEMENT_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.SD_GROUPMANAGEMENT_DROPDOWN, method = RequestMethod.GET)
	public @ResponseBody String groupDropDown() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_GROUPMANAGEMENT_DROPDOWN + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.SD_GROUPMANAGEMENT_DELETE, method = RequestMethod.POST)
	public @ResponseBody String groupDelete(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_GROUPMANAGEMENT_DELETE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	
	
	
	// USER MANAGEMENT
	
	@RequestMapping(value = REST.SD_USERMANAGEMENT, method = RequestMethod.GET)
	public String userManagement(Model model) throws JsonParseException, JsonMappingException, ParseException, IOException {
		model.addAttribute("mainMenu", "Static Data  /  User Management");
		model.addAttribute("titlePage", "USER MANAGEMENT");
		model.addAttribute("titlePageCreate", "USER MANAGEMENT | NEW");
		model.addAttribute("titlePageEdit", "USER MANAGEMENT | EDIT");
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.SD_USERMANAGEMENT;
	}
	
	/**
	 * Get Inquiry Data Business Rules Controller
	 * */
	@RequestMapping(value = REST.SD_USERMANAGEMENT_INQ, method = RequestMethod.GET)
	public @ResponseBody String userInquiry(
			@RequestParam(value="sort", defaultValue="groupId") String sort,	
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

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_USERMANAGEMENT_INQ + "?tenantId=" + tenantId;
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/**
	 * Get Inquiry Child Data Business Rules Controller
	 * */
	@RequestMapping(value = REST.SD_USERMANAGEMENT_INQ_CHILD, method = RequestMethod.GET)
	public @ResponseBody String userInquiryChild(
			@RequestParam(value="groupId") String groupId,
			@RequestParam(value="sort", defaultValue="groupId") String sort,	
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
		param.put("groupId", groupId);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = host + URI.SD_USERMANAGEMENT_INQ_CHILD + "?tenantId=" + tenantId;
		
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/**
	 * Saving Data Business Rules Controller
	 * @throws IOException 
	 * */
	@RequestMapping(value = REST.SD_USERMANAGEMENT_SAVE, method = RequestMethod.POST)
	public @ResponseBody String userSave(@RequestBody HashMap<String, Object> param) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_USERMANAGEMENT_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		ObjectMapper om = new ObjectMapper();
		Map<String, Object> map = om.readValue(response.getBody().toString(), LinkedHashMap.class);
		System.out.println(map);
		
		String status = String.valueOf(map.get("status"));
		String userStatus = String.valueOf(map.get("userStatus"));
		System.out.println("userStatus.equals(\"11\") = "+userStatus.equals("11"));
		System.out.println("status.toString().equals(\"success\") = "+status.toString().equals("success"));
		if(status.toString().equals("success") && userStatus.equals("11")) {
				Set<GrantedAuthority> sga = new HashSet<GrantedAuthority>();
    			sga.add(new SimpleGrantedAuthority("ROLE_"+map.get("groupId").toString()));
				UserDetails user = new User(map.get("username").toString().toString(), passwordEncoder.encode(map.get("password").toString()), sga);
				System.out.println(user);
				inMemoryUserDetailsManager.createUser(user);
				
		}else {
			inMemoryUserDetailsManager.deleteUser(param.get("userId").toString());
		}
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.SD_USERMANAGEMENT_DROPDOWN, method = RequestMethod.GET)
	public @ResponseBody String userDropDown() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_USERMANAGEMENT_DROPDOWN + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.SD_USERMANAGEMENT_USERLEVELDROPDOWN, method = RequestMethod.GET)
	public @ResponseBody String userLevelDropDown() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_USERMANAGEMENT_USERLEVELDROPDOWN + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.SD_USERMANAGEMENT_MPLDROPDOWN, method = RequestMethod.GET)
	public @ResponseBody String mplDropDown() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_USERMANAGEMENT_MPLDROPDOWN + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.SD_USERMANAGEMENT_STDATADROPDOWN, method = RequestMethod.GET)
	public @ResponseBody String stdataDropDown() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_USERMANAGEMENT_STDATADROPDOWN + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.SD_USERMANAGEMENT_SADROPDOWN, method = RequestMethod.GET)
	public @ResponseBody String saDropDown() throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.SD_USERMANAGEMENT_SADROPDOWN + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.SD_USERMANAGEMENT_DATE, method = RequestMethod.GET)
	public @ResponseBody String getDate() throws JsonProcessingException {
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String today = sdf.format(now);
		return today;
	}
	
}
