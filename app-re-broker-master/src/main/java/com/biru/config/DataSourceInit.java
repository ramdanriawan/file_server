package com.biru.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class DataSourceInit {

	@Value("${tenant.id.default}")
	private String tenantIdDefault;
	
	@Value("${datasource.url.properties}")
	private String datasourceUrlProperties;
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	private HashMap<Object, Object> mapSource;
	
	@Bean
    public DataSource dataSource() throws IOException {
        CustomRoutingDataSource dataSource = new CustomRoutingDataSource(tenantIdDefault);
        dataSource.setTargetDataSources(getDataSourceHashMap());
        
        return dataSource;
    }
		
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<Object, Object> getDataSourceHashMap() throws IOException {
		mapSource = new HashMap();
		
		Resource resource = resourceLoader.getResource("classpath:datasource.config");
		InputStream is = resource.getInputStream();
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		
		String line = bf.readLine();
		while(line != null) {
			String[] conf = line.split(",");
		
			DriverManagerDataSource dataSource = new DriverManagerDataSource();
	        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
	        dataSource.setUrl(conf[1] + datasourceUrlProperties);
	        dataSource.setUsername(conf[2]);
	        dataSource.setPassword(conf[3]);
			
	        mapSource.put(conf[0], dataSource);
	        
			line = bf.readLine(); 
		}
        
        return mapSource;
    }

	public HashMap<Object, Object> getMapSource() {
		return mapSource;
	}
	
}
