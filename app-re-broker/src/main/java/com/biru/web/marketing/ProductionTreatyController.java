package com.biru.web.marketing;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.TPRODUCTION;
import com.biru.helper.TreClosingParam;
import com.biru.service.ProductionTreatyService;

@RestController
@RequestMapping(REST.MARKETING)
public class ProductionTreatyController {

	@Autowired
	private ProductionTreatyService productionTreatyService;
	
	@RequestMapping(value = TPRODUCTION.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> param) throws ParseException {
		return productionTreatyService.save(param);
	}
	
	@RequestMapping(value = TPRODUCTION.DELETE, method = RequestMethod.POST)
	public Object delete(@RequestBody Map<String, Object> param) {
		return productionTreatyService.delete(param);
	}
	
	@RequestMapping(value = TPRODUCTION.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> param) throws ParseException {
		return productionTreatyService.inquiry(param);
	}
	
	@RequestMapping(value = TPRODUCTION.INQUIRY_MODIFY, method = RequestMethod.POST)
	public Object inquiryModify(@RequestBody Map<String, Object> param) {
		return productionTreatyService.inquiryModify(param);
	}
	
	@RequestMapping(value = TPRODUCTION.INQUIRY_SEND, method = RequestMethod.POST)
	public Object inquirySend(@RequestBody Map<String, Object> param) throws Exception {
		return productionTreatyService.inquirySend(param);
	}
	
	@RequestMapping(value = TPRODUCTION.CREATE_RPT_DOC, method = RequestMethod.POST)
	public Object createReportDoc(@RequestBody Map<String, Object> param) throws Exception {
		return productionTreatyService.createReportDoc(param);
	}
	
	@RequestMapping(value = TPRODUCTION.CREATE_RPT_PDF, method = RequestMethod.POST)
	public Object createReportPdf(@RequestBody Map<String, Object> param) throws Exception {
		return productionTreatyService.createReportPdf(param);
	}
	
	@RequestMapping(value = TPRODUCTION.CLOSING, method = RequestMethod.POST)
	public Object closing(@RequestBody TreClosingParam param) throws Exception {
		return productionTreatyService.doClosing(param);
	}
	
	@RequestMapping(value = TPRODUCTION.DC_NOTE_RPT_PDF, method = RequestMethod.POST)
	public Object closingDCNotePdf(@RequestBody Map<String, String> param) throws Exception {
		return productionTreatyService.closingDCNotePdf(param);
	}
	
	@RequestMapping(value = TPRODUCTION.DC_NOTE_RPT_DOC, method = RequestMethod.POST)
	public Object closingDCNoteDoc(@RequestBody Map<String, String> param) throws Exception {
		return productionTreatyService.closingDCNoteDoc(param);
	}
	
}
