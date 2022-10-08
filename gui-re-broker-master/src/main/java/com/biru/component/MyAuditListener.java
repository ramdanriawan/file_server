package com.biru.component;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AbstractAuthenticationEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class MyAuditListener implements ApplicationListener<AbstractAuthenticationEvent>{

	@Autowired
	private LoginAttemptComponent loginAttemptComponent;
	
	@Override
	public void onApplicationEvent(AbstractAuthenticationEvent appEvent) {
		if (appEvent instanceof AuthenticationSuccessEvent){
			AuthenticationSuccessEvent event = (AuthenticationSuccessEvent)appEvent;
			loginAttemptComponent.loginSucceeded(((UserDetails)event.getAuthentication().getPrincipal()).getUsername());
		}

		if (appEvent instanceof AuthenticationFailureBadCredentialsEvent){
			AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) appEvent;
			
			loginAttemptComponent.loginFailed(event.getAuthentication().getPrincipal().toString());
		}
	}

} 
