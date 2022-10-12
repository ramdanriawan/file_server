package com.biru.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class InitBean {
	
	@Value(value = "${resttemplate.total.con}")
	private Integer totalCon;
	
	@Value(value = "${resttemplate.per-route.con}")
	private Integer perRouteCon;

	@Bean
	public RestTemplate restTemplate() {
		HttpClient httpClient = HttpClientBuilder.create()
		        .setMaxConnTotal(totalCon)
		        .setMaxConnPerRoute(perRouteCon)
		        .build();
		HttpComponentsClientHttpRequestFactory fc = new HttpComponentsClientHttpRequestFactory(httpClient);
		RestTemplate template = new RestTemplate(fc);
		
		return template;
	}
	
}
