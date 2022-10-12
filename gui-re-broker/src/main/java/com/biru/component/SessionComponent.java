package com.biru.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SessionComponent {
	
	@Value("${app.host}")
	public String host;
	
	private String user = "admin";
	
	private String tenantId = "CPU01";
	
	public String getUserLogin() {
		 SecurityContext securityContext = SecurityContextHolder.getContext();
		    Authentication authentication = securityContext.getAuthentication();
		    if (authentication != null) {
		        Object principal = authentication.getPrincipal();
		        return principal instanceof UserDetails ? ((UserDetails) principal).getUsername() : user;
		    }
		    return user;
	}
	
	public String getTenantId() {
		return tenantId;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	
	
}
