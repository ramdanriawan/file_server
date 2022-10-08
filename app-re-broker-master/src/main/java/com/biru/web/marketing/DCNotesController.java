package com.biru.web.marketing;

import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.DEBIT_CREDIT_NOTES;
import com.biru.entity.ResponseEntity;
import com.biru.service.DCNotesService;

@RestController
@RequestMapping(REST.MARKETING)
public class DCNotesController {
	
	@Autowired
	private DCNotesService dcNotesService;
	
	private Logger logger = LoggerFactory.getLogger(DCNotesController.class);
	
	@RequestMapping(value = DEBIT_CREDIT_NOTES.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> p_Params) throws ParseException {
		return dcNotesService.inquiry(p_Params);
	}

	@RequestMapping(value = DEBIT_CREDIT_NOTES.DELETE, method = RequestMethod.POST)
	public Object reverse(@RequestBody Map<String, Object> p_Params) throws Exception {
		return dcNotesService.reverse(p_Params);
	}
	
	@RequestMapping(value = DEBIT_CREDIT_NOTES.SAVE, method = RequestMethod.POST)
	public ResponseEntity save(@RequestBody Map<String, Object> p_Params) throws ParseException {
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		
		String result = null;
		try {
			result = dcNotesService.save(p_Params);
			response.setResult(result);
		}catch(Exception e) {
			logger.error("Process debit/credit note failed! param : {}", p_Params);
			
			e.printStackTrace();
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
			response.setError(e.getMessage());
		}
		
		return response;
	}
	
	@RequestMapping(value = DEBIT_CREDIT_NOTES.CREATE_EXCEL, method = RequestMethod.POST)
	public String createDCNotesExcel(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return dcNotesService.createDCNotesExcel(p_Params);
	}
	
	@RequestMapping(value = DEBIT_CREDIT_NOTES.CREATE_HTML, method = RequestMethod.POST)
	public String createDCNotesHtml(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return dcNotesService.createDCNotesHtml(p_Params);
	}
	
	@RequestMapping(value = DEBIT_CREDIT_NOTES.CREATE_PDF, method = RequestMethod.POST)
	public String createDCNotesPdf(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return dcNotesService.createDCNotesPdf(p_Params);
	}
	
	@RequestMapping(value = DEBIT_CREDIT_NOTES.CREATE_DOC, method = RequestMethod.POST)
	public String createDCNotesDoc(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return dcNotesService.createDCNotesDoc(p_Params);
	}
	
	@RequestMapping(value = DEBIT_CREDIT_NOTES.CREATE_DOC_UPLOAD_TREATY, method = RequestMethod.POST)
	public String createDCNotesDocUploadTreaty(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return dcNotesService.createDCNotesDocUploadTreaty(p_Params);
	}
	
	@RequestMapping(value = DEBIT_CREDIT_NOTES.CREATE_PDF_UPLOAD_TREATY, method = RequestMethod.POST)
	public String createDCNotesPdfUploadTreaty(@RequestBody Map<String, String> p_Params) throws Exception {
		
		return dcNotesService.createDCNotesPdfUploadTreaty(p_Params);
	}
}
