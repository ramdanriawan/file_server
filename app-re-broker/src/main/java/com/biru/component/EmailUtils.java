package com.biru.component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.biru.entity.MA0010Entity;
import com.biru.entity.MA0014Entity;
import com.biru.repository.MA0010Repo;
import com.biru.repository.MA0014Repo;

@Component
public class EmailUtils {
	
	@Autowired
	private MA0010Repo ma0010Repo;
	
	@Autowired
	private MA0014Repo ma0014Repo;
	
	private JavaMailSenderImpl mailSender;
	
	private Map<Long, MA0014Entity> mapMailParams;
	
	private String userName;
	private String password;
	private String alias;
	
	private static final String MAILNOTIF = "MAILNOTIF";
	
	private static final Logger logger = LoggerFactory.getLogger(EmailUtils.class);
	
	public EmailUtils() {
		this.mailSender = new JavaMailSenderImpl();
	}
	
	public JavaMailSender getMailSender() {
		MA0010Entity company = ma0010Repo.getEmail();
		
        JavaMailSender mailSender = getMailSender(company.getCoPicEmail(), 
        		company.getCoPicEmailPass(), company.getCoPicEmailAlias());
        
        return mailSender;
    }

    public JavaMailSender getMailSender(String userName, String password, String alias) {        
        List<MA0014Entity> mailParams = ma0014Repo.findByPaParentCode(MAILNOTIF);  	
        
        Boolean isCreate = isCreateNewInstance(mailParams, userName, password, alias);
        
        if(isCreate) {
        	this.userName = userName;
        	this.password = password;
        	this.alias = alias;
        	initMailParams(mailParams);
        	
        	mailSender = new JavaMailSenderImpl();
        	
        	logger.info("Init new instance JavaMailSenderImpl.");
        	logger.info("New mail properties : {}.", this.mapMailParams.values());
        }
        
        Properties props = mailSender.getJavaMailProperties();
        mailSender.setUsername(userName);
        mailSender.setPassword(password);
        
        for(MA0014Entity param : mailParams) {
        	props.put(param.getPaChildDesc(), param.getPaChildValue());
        }
        props.put("mail.smtp.from", userName);
        
        return mailSender;
    }
    
    private void initMailParams(List<MA0014Entity> mailParams) {    	
    	this.mapMailParams = new HashMap<Long, MA0014Entity>();
    	
    	if(mailParams == null)
    		mailParams = ma0014Repo.findByPaParentCode(MAILNOTIF);
    	
    	for(MA0014Entity param : mailParams) {
    		this.mapMailParams.put(param.getIdKey(), param);
    	}
    }
    
    private Boolean isCreateNewInstance(List<MA0014Entity> mailParams, String userName, String password, String alias) {    	
    	if(this.mapMailParams == null)
    		return Boolean.TRUE;
    	
    	Boolean isCreate = Boolean.FALSE;
    	
    	isCreate = !(
    		(this.userName == null ? userName == null : this.userName.equals(userName)) &&
        	(this.password == null ? password == null : this.password.equals(password)) &&
        	(this.alias == null ? alias == null : this.alias.equals(alias))
        );
    	
    	for(MA0014Entity current : mailParams) {
    		MA0014Entity old = this.mapMailParams.get(current.getIdKey());
    		if(old == null)
    			isCreate = Boolean.TRUE;
    		else if(!current.equals(old))
    			isCreate = Boolean.TRUE;
    		
    		if(isCreate)
    			break;
    	}
    	
    	return isCreate;
    }
    
    public MimeMessageHelper getMimeMessageHelper(MimeMessage msg) throws Exception {
    	if(this.userName == null) {
    		MA0010Entity company = ma0010Repo.getEmail();
    		this.userName = company.getCoPicEmail();
    		this.password = company.getCoPicEmailPass();
    		this.alias = company.getCoPicEmailAlias();
    	}
    	
    	MimeMessageHelper helper = new MimeMessageHelper(msg, true);
    	helper.setFrom(new InternetAddress(this.userName, this.alias == null ? "Admin" : this.alias));
    	
    	return helper;
    }
	
}
