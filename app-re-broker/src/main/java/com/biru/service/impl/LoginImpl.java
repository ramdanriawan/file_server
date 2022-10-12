package com.biru.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.biru.component.EmailUtils;
import com.biru.component.EncryptionComponent;
import com.biru.entity.US0001Entity;
import com.biru.repository.MA0010Repo;
import com.biru.repository.US0001Repo;
import com.biru.repository.US0003Repo;
import com.biru.service.LoginService;
import com.biru.util.thread.GetUserThread;

@Service
public class LoginImpl implements LoginService{

	@Autowired
	private MA0010Repo mA0010Repo;
	
	@Autowired
	private US0001Repo uS0001Repo;
	
	@Autowired
	private EncryptionComponent encryptionComponent;
	
	@Autowired
	private EmailUtils emailUtils;

	@Autowired
	private US0003Repo uS0003Repo;
	
	public static List<Map<String, Object>> listUserDetails = new CopyOnWriteArrayList<Map<String, Object>>();
	
	@Override
	public Object loginPageItem() {
		// TODO Auto-generated method stub
		return mA0010Repo.findAll();
	}

	@Override
	public void countLoginError() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object isUserLocked(String username) {
		// TODO Auto-generated method stub
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("isLocked", false);
		result.put("isExp", false);
		
		US0001Entity uS0001Entity = uS0001Repo.findByUsCode(username);
		if(uS0001Entity != null){
			if(uS0001Entity.getUsExpiredDate().compareTo(Calendar.getInstance().getTime()) > 0) {
				result.put("isExp", true);
				result.put("message", "please contact the system administrator to renew this service");
			}
			if(uS0001Entity.getUsErrCount()>=3) {
				result.put("isLocked", true);
				result.put("message", "Too many login attempts, please contact the system administrator");
			}
		}
		return result;
	}

	@Override
	public void addFailedLogin(String username) {
		// TODO Auto-generated method stub
		US0001Entity uS0001Entity = uS0001Repo.findByUsCode(username);
		if(uS0001Entity != null){
			uS0001Entity.setUsErrCount(uS0001Entity.getUsErrCount()+1);
			uS0001Repo.save(uS0001Entity);
		}
		
	}

	@Override
	public void resetFailedLogin(String username) {
		// TODO Auto-generated method stub
		US0001Entity uS0001Entity = uS0001Repo.findByUsCode(username);
		if(uS0001Entity != null){
			uS0001Entity.setUsErrCount(0);
			uS0001Repo.save(uS0001Entity);
		}
	}

	@Override
	public void resetPassword(String username, String password) throws Exception {
		// TODO Auto-generated method stub
		String encryptedPassword = encryptionComponent.encrypt(password);
		US0001Entity uS0001Entity = uS0001Repo.findByUsCode(username);
		
		if(uS0001Entity != null){
			uS0001Entity.setUsErrCount(0);
			uS0001Entity.setUsPass(encryptionComponent.encrypt(encryptedPassword));
			uS0001Repo.save(uS0001Entity);
			
			JavaMailSender sender = emailUtils.getMailSender();
			MimeMessage msg = sender.createMimeMessage();
			
			MimeMessageHelper message = emailUtils.getMimeMessageHelper(msg);
			message.setTo(uS0001Entity.getUsEmail());
	        message.setSubject("Reset Password"); 
	        message.setText("Password untuk User "+username+" berhasil di-reset. Silahkan login kembali menggunakan password : "+password);
	        sender.send(msg);
	        
			
			
//	        MimeMessage msg = javaMailSender.createMimeMessage();
//	        MimeMessageHelper helper = new MimeMessageHelper(msg, true);
//			
//	        helper.setTo(uS0001Entity.getUsEmail());
//	        helper.setSubject("Reset Password");
//	        helper.setText("Password untuk User "+username+" berhasil di-reset. Silahkan login kembali menggunakan password : "+password, true);
//	
	        
//	        helper.addAttachment("Draft Placing Slip.pdf", file);
	
//	        javaMailSender.send(msg);
		}
	}

	@Override
	public Object inquiryUser() {
		// TODO Auto-generated method stub
		listUserDetails.clear();
		
		int index = 0;
		int chunk = 100;
		long count = uS0001Repo.countActiveUser();
		
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(10);
		threadPoolTaskExecutor.setMaxPoolSize(100);
		threadPoolTaskExecutor.setWaitForTasksToCompleteOnShutdown(true);
		threadPoolTaskExecutor.setAwaitTerminationSeconds(Integer.MAX_VALUE);
		threadPoolTaskExecutor.initialize();
		
		while (true) {
			if(index*chunk > count){
				break;
			}
			threadPoolTaskExecutor.execute(new GetUserThread(index, chunk, uS0001Repo));
			index++;
		}
		threadPoolTaskExecutor.shutdown();
		threadPoolTaskExecutor.destroy();
		
		
		return listUserDetails;
	}
	
}
