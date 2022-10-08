package com.biru.web.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biru.ReBrokerConstants.REST;
import com.biru.service.LoginService;

@Controller
@RequestMapping(value = REST.LOGIN)
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping(value = REST.LOGIN_PAGE.LOGIN_PAGE_ITEM, method = RequestMethod.POST)
	public @ResponseBody Object loginPageItem(@RequestBody Map<String, Object> param)  {
		return loginService.loginPageItem();
	}
	
	@RequestMapping(value = REST.LOGIN_PAGE.IS_USER_LOCKED, method = RequestMethod.POST)
	public @ResponseBody Object isUserLocked(@RequestBody Map<String, Object> param)  {
		return loginService.isUserLocked(param.get("username").toString());
	}
	
	@RequestMapping(value = REST.LOGIN_PAGE.ADD_ERROR_COUNTER, method = RequestMethod.POST)
	public @ResponseBody Object addFailedLogin(@RequestBody Map<String, Object> param)  {
		loginService.addFailedLogin(param.get("username").toString());
		return param;
	}
	
	@RequestMapping(value = REST.LOGIN_PAGE.RESET_FAILED_LOGIN, method = RequestMethod.POST)
	public @ResponseBody Object resetFailedLogin(@RequestBody Map<String, Object> param)  {
		loginService.resetFailedLogin(param.get("username").toString());
		return param;
	}
	
	@RequestMapping(value = REST.LOGIN_PAGE.RESET_PASSWORD, method = RequestMethod.POST)
	public @ResponseBody Object resetPassword(@RequestBody Map<String, Object> param) throws Exception  {
		loginService.resetPassword(param.get("username").toString(), param.get("password").toString());
		return param;
	}
	
	@RequestMapping(value = REST.LOGIN_PAGE.INQUIRY_USERS, method = RequestMethod.POST)
	public @ResponseBody Object inquiryUser(@RequestBody Map<String, Object> param)  {
		
		return loginService.inquiryUser();
	}
}
