package com.biru.web.marketing.report;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.PRODUCTION;
import com.biru.service.ProductionService;

@RestController
@RequestMapping(REST.MARKETING_REPORT)
public class ProductionController {
	
	@Autowired
	private ProductionService productionService;
	
	@RequestMapping(value = PRODUCTION.MA_0011, method = RequestMethod.POST)
	public Object tcPremium(@RequestBody Map<String, Object> param) {
		return productionService.tcPremium(param);
	}
	
	@RequestMapping(value = PRODUCTION.MA_0012, method = RequestMethod.POST)
	public Object getName(@RequestBody Map<String, Object> param) {
		return productionService.getName(param);
	}
	
	@RequestMapping(value = PRODUCTION.TAX_RATE, method = RequestMethod.POST)
	public Object taxRate() {
		return productionService.taxRate();
	}
	
	@RequestMapping(value = PRODUCTION.TERM_AND_CONDITION, method = RequestMethod.POST)
	public Object termAndCondition(@RequestBody Map<String, Object> param) {
		return productionService.termAndCondition(param);
	}
	
	@RequestMapping(value = PRODUCTION.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> param) throws ParseException {
		return productionService.save(param);
	}
	
	@RequestMapping(value = PRODUCTION.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> param) throws ParseException {
		return productionService.inquiry(param);
	}
	
	@RequestMapping(value = PRODUCTION.INQUIRY_MODIFY, method = RequestMethod.POST)
	public Object inquiryModify(@RequestBody Map<String, Object> param) throws ParseException {
		return productionService.inquiryModify(param);
	}
	
	@RequestMapping(value = PRODUCTION.DELETE, method = RequestMethod.POST)
	public Object delete(@RequestBody Map<String, Object> param) {
		return productionService.delete(param);
	}
	
	@RequestMapping(value = PRODUCTION.CREATE_RPT_DOC, method = RequestMethod.POST)
	public Object createReportDoc(@RequestBody Map<String, Object> param) throws Exception {
		return productionService.createReportDoc(param);
	}
	
	@RequestMapping(value = PRODUCTION.CREATE_RPT_HTML, method = RequestMethod.POST)
	public Object createReportHtml(@RequestBody Map<String, Object> param) throws Exception {
		return productionService.createReportHtml(param);
	}
	
	@RequestMapping(value = PRODUCTION.CREATE_RPT_PDF, method = RequestMethod.POST)
	public Object createReportPdf(@RequestBody Map<String, Object> param) throws Exception {
		return productionService.createReportPdf(param);
	}

	@RequestMapping(value = PRODUCTION.INQUIRY_SEND, method = RequestMethod.POST)
	public Object inquirySend(@RequestBody Map<String, Object> param) throws Exception {
		return productionService.inquirySend(param);
	}
	
	@RequestMapping(value = PRODUCTION.SEND_EMAIL, method = RequestMethod.POST)
	public Object sendEmail(@RequestBody Map<String, Object> param) throws Exception {
		return productionService.sendEmail(param);
	}
	
	@RequestMapping(value = PRODUCTION.CLOSING, method = RequestMethod.POST)
	public Object closing(@RequestBody Map<String, Object> param) throws Exception {
		return productionService.doClosing(param);
	}
	
	@RequestMapping(value = PRODUCTION.CREATE_CLOSING_HTML, method = RequestMethod.POST)
	public Object createClosingHtml(@RequestBody Map<String, Object> param) throws Exception {
		return productionService.createClosingHtml(param);
	}
	
	@RequestMapping(value = PRODUCTION.SEND_EMAIL_CLOSING, method = RequestMethod.POST)
	public Object sendEmailClosing(@RequestBody Map<String, Object> param) throws Exception {
		return productionService.sendEmailClosing(param);
	}
	
	@RequestMapping(value = PRODUCTION.IS_VALID_TRX_DATE, method = RequestMethod.POST)
	public Object isValidTrxDate(@RequestBody Map<String, Object> param) {
		return productionService.isValidTrxDate(param);
	}
	
	@RequestMapping(value = PRODUCTION.VALIDATION_USER_LEVEL, method = RequestMethod.POST)
	public Object valdiationUserLevel(@RequestBody Map<String, Object> param) {
		return productionService.validationUserLevel(param);
	}
	
}

