package com.biru.web;
import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.fasterxml.jackson.core.JsonProcessingException;


@Controller
public class MainController {
	
	@Autowired
	private InMemoryUserDetailsManager  inMemoryUserDetailsManager;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;

	@RequestMapping(value = REST.HOME, method = RequestMethod.GET)
	public String home(Model model) {
		model.addAttribute("mainMenu", "Dashboard");

		return HTML.INDEX;
	}

	@RequestMapping(value = REST.MENU, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	@ResponseBody
	public Object menu(Principal principal) throws JsonProcessingException {
		System.out.println("cari menu");
		String userName = principal.getName();
		UserDetails u = inMemoryUserDetailsManager.loadUserByUsername(userName);
		String authority = "";
		for (GrantedAuthority grantedAuthority : u.getAuthorities()) {
			authority = grantedAuthority.getAuthority().replace("ROLE_", "");
		}
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object>data = new HashMap<String, Object>();
        data.put("groupId", authority);
		
		System.out.println(data);
				
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(data), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.MENU + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response =  restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

}
