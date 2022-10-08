package com.biru.web;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.service.HelperService;

@RestController
@RequestMapping("/helper")
public class HelperController {
	
	@Autowired
	private HelperService helperService;
	
	@RequestMapping(value = "/upload-tr0012", method = RequestMethod.POST)
	public Object uploadtr0012(@RequestBody Map<String, Object> param) throws IOException {
		helperService.uploadTR0012(param);
		
		return "FINISH";
	}
	
}
