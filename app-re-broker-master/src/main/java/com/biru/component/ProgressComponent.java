package com.biru.component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.biru.entity.ProgressEntity;

@Component
public class ProgressComponent {
	private Map<String, ProgressEntity> dataProgress = new ConcurrentHashMap<String, ProgressEntity>();
	
	@Autowired
	private ResourceLoader resourceLoader;
	
	@PostConstruct
	public void initDataProgress () throws IOException {
		Resource resource = resourceLoader.getResource("classpath:datasource.config");
		InputStream is = resource.getInputStream();
		BufferedReader bf = new BufferedReader(new InputStreamReader(is));
		
		String line = bf.readLine();
		while(line != null) {
			String[] conf = line.split(",");
		
			ProgressEntity progressEntity = new ProgressEntity();
			progressEntity.setFlag("0");
			progressEntity.setProgress("0");
			progressEntity.setTenant(conf[0]);
			
	        dataProgress.put(conf[0], progressEntity);
	        
			line = bf.readLine(); 
		}
	}

	public Map<String, ProgressEntity> getDataProgress() {
		return dataProgress;
	}

	public void setDataProgress(Map<String, ProgressEntity> dataProgress) {
		this.dataProgress = dataProgress;
	}
	
}
