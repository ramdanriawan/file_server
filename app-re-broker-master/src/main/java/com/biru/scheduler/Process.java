package com.biru.scheduler;

import java.text.ParseException;
import java.util.Calendar;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.biru.ReBrokerConstants.CODE;
import com.biru.common.AbstractCommon;
import com.biru.entity.MA0014Entity;
import com.biru.repository.MA0014Repo;
import com.biru.service.CommonService;

@Configuration
@EnableScheduling
public class Process extends AbstractCommon {
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private MA0014Repo ma0014Repo;

	private Logger logger = LoggerFactory.getLogger(Process.class);
	
	@Scheduled(cron = "${scheduler.cron.updatedate:0 0 1 * * ?}", zone = "Asia/Jakarta")
	public void doUpdateApplicationDate() throws ParseException {
		logger.info("Start - Scheduler update application date.");
		
		updateApplicationDate();
		
		logger.info("Finish - Scheduler update application date.");
	}
	
	private void updateApplicationDate() throws ParseException {
		Calendar calAppDate = Calendar.getInstance();
		calAppDate.setTime(common.getAppDate());
		calAppDate.set(Calendar.HOUR_OF_DAY, 0);
		calAppDate.set(Calendar.MINUTE, 0);
		calAppDate.set(Calendar.SECOND, 0);
		calAppDate.set(Calendar.MILLISECOND, 0);
		
		Calendar calServerDate = Calendar.getInstance();
		calServerDate.set(Calendar.HOUR_OF_DAY, 0);
		calServerDate.set(Calendar.MINUTE, 0);
		calServerDate.set(Calendar.SECOND, 0);
		calServerDate.set(Calendar.MILLISECOND, 0);
		
		Boolean isNotEqual = calAppDate.getTime().compareTo(calServerDate.getTime()) != 0;
		logger.info("calAppDate : {}, calServerDate : {}, isNotEqual : {}.", calAppDate.getTime(), calServerDate.getTime(), isNotEqual);
		if(isNotEqual) {
			String serverDateStr = formatDateDb.format(calServerDate.getTime());
			MA0014Entity paramAppDate = ma0014Repo.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);
			paramAppDate.setPaChildValue(serverDateStr);
			
			ma0014Repo.save(paramAppDate);
			logger.info("New parameter : {}.", paramAppDate);
		}
	}
	
	@PostConstruct
	public void onStartUp() throws ParseException {
		doUpdateApplicationDate();
	}
	
}
