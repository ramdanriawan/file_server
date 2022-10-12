package com.biru.component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.biru.service.CommonService;

@ControllerAdvice
public class GlobalControllerAdvice {
	
	@Autowired
	private CommonService common;
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@ModelAttribute
	public void myMethod(Model model) throws Exception {
		String appDate = null;
		try {
			appDate = common.getAppDate();  
		}catch(Exception e) {
			logger.error("Failed get date, cause : {}", e.getLocalizedMessage());
		}
		
	    model.addAttribute("appDateGlobal", appDate);
 	}
}