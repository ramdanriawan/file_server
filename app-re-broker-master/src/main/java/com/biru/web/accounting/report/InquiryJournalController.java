package com.biru.web.accounting.report;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.INQUIRY_JOURNAL;
import com.biru.service.InquiryJournal;

@RestController
@RequestMapping(REST.ACCOUNTING_REPORT)
public class InquiryJournalController {
	
	@Autowired
	private InquiryJournal inquiryJournal;
	
	@RequestMapping(value = INQUIRY_JOURNAL.DROPDOWN_PROJECT, method = RequestMethod.POST)
	public Object dropdownProject() {
		return inquiryJournal.getDropdownProject();
	}
	
	@RequestMapping(value = INQUIRY_JOURNAL.DROPDOWN_OFFICE, method = RequestMethod.POST)
	public Object dropdownOffice() {
		return inquiryJournal.getDropdownOffice();
	}
	
	@RequestMapping(value = INQUIRY_JOURNAL.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> p_Param) {
		return inquiryJournal.getJournal(p_Param);
	}
}
