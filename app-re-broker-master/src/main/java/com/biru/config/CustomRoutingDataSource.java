package com.biru.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.biru.ReBrokerConstants.PARAM;

public class CustomRoutingDataSource extends AbstractRoutingDataSource {
	
	private String tenantIdDefault;
	
	public CustomRoutingDataSource(String tenantIdDefault) {
		this.tenantIdDefault = tenantIdDefault;
	}
	
    @Override
    protected Object determineCurrentLookupKey() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        
        if(attributes!=null)
            return attributes.getRequest().getParameter(PARAM.TENANT_ID);
        else
            return tenantIdDefault;
    }
    
}