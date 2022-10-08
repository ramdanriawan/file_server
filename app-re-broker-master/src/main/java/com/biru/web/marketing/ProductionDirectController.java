package com.biru.web.marketing;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.DPRODUCTION;
import com.biru.service.ProductionDirectService;

@RestController
@RequestMapping(REST.MARKETING)
public class ProductionDirectController {

	@Autowired
	private ProductionDirectService productionDirectService;
		
	@RequestMapping(value = DPRODUCTION.GET_COMM_OUT_NAME, method = RequestMethod.POST)
	public Object getCommOutName(@RequestBody Map<String, Object> param) {
		return productionDirectService.getCommOutName(param);
	}
	
	@RequestMapping(value = DPRODUCTION.GET_WHT_CLIENT, method = RequestMethod.POST)
	public Object getWhtClient(@RequestBody Map<String, Object> param) {
		return productionDirectService.getWhtClient(param);
	}
	
	@RequestMapping(value = DPRODUCTION.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> param) throws ParseException {
		return productionDirectService.save(param);
	}
	
	@RequestMapping(value = DPRODUCTION.INQUIRY_MODIFY, method = RequestMethod.POST)
	public Object inquiryModify(@RequestBody Map<String, Object> param) throws ParseException {
		return productionDirectService.inquiryModify(param);
	}
	
	@RequestMapping(value = DPRODUCTION.CREATE_RPT_HTML, method = RequestMethod.POST)
	public Object createReportHtml(@RequestBody Map<String, Object> param) throws Exception {
		return productionDirectService.createReportHtml(param);
	}
	
	@RequestMapping(value = DPRODUCTION.CREATE_RPT_PDF, method = RequestMethod.POST)
	public Object createReportPdf(@RequestBody Map<String, Object> param) throws Exception {
		return productionDirectService.createReportPdf(param);
	}
	
	@RequestMapping(value = DPRODUCTION.CREATE_RPT_DOC, method = RequestMethod.POST)
	public Object createReportDoc(@RequestBody Map<String, Object> param) throws Exception {
		return productionDirectService.createReportDoc(param);
	}
	
	@RequestMapping(value = DPRODUCTION.INQUIRY_SEND, method = RequestMethod.POST)
	public Object inquirySend(@RequestBody Map<String, Object> param) throws Exception {
		return productionDirectService.inquirySend(param);
	}
	
	@RequestMapping(value = DPRODUCTION.SEND_EMAIL, method = RequestMethod.POST)
	public Object sendEmail(@RequestBody Map<String, Object> param) throws Exception {
		return productionDirectService.sendEmail(param);
	}
	
	@RequestMapping(value = DPRODUCTION.CLOSING, method = RequestMethod.POST)
	public Object closing(@RequestBody Map<String, Object> param) throws Exception {
		return productionDirectService.doClosing(param);
	}
	
	@RequestMapping(value = DPRODUCTION.CREATE_CLOSING_HTML, method = RequestMethod.POST)
	public Object createClosingHtml(@RequestBody Map<String, Object> param) throws Exception {
		return productionDirectService.createClosingHtml(param);
	}
	
}
