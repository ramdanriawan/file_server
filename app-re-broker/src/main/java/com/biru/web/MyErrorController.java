package com.biru.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyErrorController implements ErrorController{

	@RequestMapping(value = "/error",  method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object handleError(HttpServletRequest request) {
		
		Map<String, Object> error = new HashMap<String, Object>();
		error.put("status", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		error.put("message", ((Throwable)request.getAttribute(RequestDispatcher.ERROR_EXCEPTION)).getMessage());
		return error;
    }
	
	@RequestMapping(value = "/error",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object handleErrorPost(HttpServletRequest request) {
		
		Map<String, Object> error = new HashMap<String, Object>();
		error.put("status", request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE));
		error.put("message", ((Throwable)request.getAttribute(RequestDispatcher.ERROR_EXCEPTION)).getMessage());
		return error;
    }
	
	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return "/error";
	}

}
