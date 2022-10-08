package com.biru.web.finance;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.BANK_BOOK;
import com.biru.entity.ResponseEntity;
import com.biru.service.BankBookService;

@RestController
@RequestMapping(REST.FINANCE)
public class BankBookController {
	
	@Autowired
	private BankBookService bankBookService;
	
	@RequestMapping(value = BANK_BOOK.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> p_Params) throws ParseException {
		return bankBookService.inquiry(p_Params);
	}

	@RequestMapping(value = BANK_BOOK.INQUIRY_MODIFY, method = RequestMethod.POST)
	public Object inquiryModify(@RequestBody Map<String, Object> p_Params) throws ParseException {
		return bankBookService.inquiryModify(p_Params);
	}
	
	@RequestMapping(value = BANK_BOOK.SAVE, method = RequestMethod.POST)
	public ResponseEntity save(@RequestBody Map<String, Object> p_Params) throws ParseException {
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		
		String result = null;
		try {
			result = bankBookService.save(p_Params);
			response.setResult(result);
		}catch(Exception e) {
			e.printStackTrace();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			response.setError(e.getMessage());
		}
		
		return response;
	}
	
	@RequestMapping(value = BANK_BOOK.DELETE, method = RequestMethod.POST)
	public Object delete(@RequestBody Map<String, Object> p_Params) {
		return bankBookService.delete(p_Params);
	}
	
}
