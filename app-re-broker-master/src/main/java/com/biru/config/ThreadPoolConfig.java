package com.biru.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class ThreadPoolConfig {
	
	//@Value("${executor.pool.size}")
	//private String poolSize;

	public ThreadPoolTaskExecutor executorGeneral(String prefix){
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(Integer.valueOf(100));
		executor.setMaxPoolSize(Integer.valueOf(100));
		executor.setThreadNamePrefix(prefix);
		executor.setAwaitTerminationSeconds(3600);
		executor.setWaitForTasksToCompleteOnShutdown(true);
		executor.initialize();
		return executor;
	}

}
