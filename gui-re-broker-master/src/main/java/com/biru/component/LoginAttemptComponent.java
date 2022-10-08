package com.biru.component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.URI;
import com.biru.common.parser.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;

@Component
public class LoginAttemptComponent {

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private InMemoryUserDetailsManager inMemoryUserDetailsManager;
    

    public void loginSucceeded(String key) {
        System.out.println(key+" loginSucceeded");
        send(key, URI.L_LOGIN_RESET_FAILED_LOGIN);
    }

    public void loginFailed(String key) {
    	System.out.println(key+" loginFailed");
    	send(key, URI.L_LOGIN_ADD_ERROR_COUNTER);
    }

    
    private void send(String username, String url) {
    	try {
    		HttpHeaders httpHeaders = new HttpHeaders();
    		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    		
    		Map<String, Object>data = new HashMap<String, Object>();
            data.put("username", username);
    		
    		System.out.println(data);
    				
    		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(data), httpHeaders);
    			
    		String uri = sessionComponent.getHost() + url + "?tenantId=" + sessionComponent.getTenantId();
    		restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
    		
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    @PostConstruct
    private void createUserLogin() {
    	try {
    		HttpHeaders httpHeaders = new HttpHeaders();
    		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
    		
    		Map<String, Object>data = new HashMap<String, Object>();
    		
    		System.out.println(data);
    				
    		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(data), httpHeaders);
    			
    		String uri = sessionComponent.getHost() + URI.L_LOGIN_INQUIRY_USERS + "?tenantId=" + sessionComponent.getTenantId();
    		ResponseEntity<ArrayList> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, ArrayList.class);
    		
    		List<Map<String, Object>> userDetailsList = (List<Map<String, Object>>) response.getBody();
    		for (Map<String, Object> map : userDetailsList) {
    			Set<GrantedAuthority> sga = new HashSet<GrantedAuthority>();
    			sga.add(new SimpleGrantedAuthority("ROLE_"+map.get("role").toString()));
				UserDetails user = new User(map.get("username").toString(), map.get("password").toString(), sga);
				System.out.println(user);
				inMemoryUserDetailsManager.createUser(user);
			}
    		
    	} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
    }
    
    public void syncronizeUser() throws JsonProcessingException {
    	HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object>data = new HashMap<String, Object>();
		
		System.out.println(data);
				
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(data), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.L_LOGIN_INQUIRY_USERS + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<ArrayList> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, ArrayList.class);
		
		List<Map<String, Object>> userDetailsList = (List<Map<String, Object>>) response.getBody();
		for (Map<String, Object> map : userDetailsList) {
			String username = map.get("username").toString();
			String password = map.get("password").toString();
			String role = map.get("role").toString();
			Set<GrantedAuthority> sga = new HashSet<GrantedAuthority>();
			sga.add(new SimpleGrantedAuthority("ROLE_"+role));
			UserDetails user = new User(username, password, sga);
			if(inMemoryUserDetailsManager.userExists(username)) {
				inMemoryUserDetailsManager.updateUser(user);
			}else {
				System.out.println(user);
				inMemoryUserDetailsManager.createUser(user);
			}
			
		}
    
    }
}
