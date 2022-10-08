package com.biru.web.accounting;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.ENTRY_JOURNAL;
import com.biru.service.EntryJournalService;

@RestController
@RequestMapping(REST.ACCOUNTING)
public class EntryJournalController {
	
	@Autowired
	private EntryJournalService entryJournalService;
	
	@RequestMapping(value = ENTRY_JOURNAL.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> p_Param) {
		return entryJournalService.inquiry(p_Param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.DROPDOWN_PROJECT, method = RequestMethod.POST)
	public Object dropdownProject() {
		return entryJournalService.getDropdownProject();
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.DROPDOWN_OFFICE, method = RequestMethod.POST)
	public Object dropdownOffice() {
		return entryJournalService.getDropdownOffice();
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.TRANSACTION_MIN_DATE, method = RequestMethod.POST)
	public Object getTransactionMinDate() throws ParseException {
		return entryJournalService.getTransactionMinDate();
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.CLIENT, method = RequestMethod.POST)
	public Object client(@RequestBody Map<String, Object> param) {
		return entryJournalService.client(param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.COA, method = RequestMethod.POST)
	public Object coa(@RequestBody Map<String, Object> param) {
		return entryJournalService.coa(param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.VOUCHRE_CODE, method = RequestMethod.POST)
	public Object voucherCode(@RequestBody Map<String, Object> param) {
		return entryJournalService.voucherCode(param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.EXCHANGE, method = RequestMethod.POST)
	public Object exchange(@RequestBody Map<String, Object> param) {
		return entryJournalService.exchange(param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> param) {
		return entryJournalService.save(param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.EDIT, method = RequestMethod.POST)
	public Object edit(@RequestBody Map<String, Object> param) {
		return entryJournalService.edit(param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.REMOVE, method = RequestMethod.POST)
	public Object remove(@RequestBody Map<String, Object> param) {
		return entryJournalService.remove(param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.PRINT_JOURNAL, method = RequestMethod.POST)
	public Object printJournal(@RequestBody Map<String, Object> param) throws Exception {
		return entryJournalService.printJournal(param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.EXPORT_EXCEL, method = RequestMethod.POST)
	public Object exportExcel(@RequestBody Map<String, Object> param) throws Exception {
		return entryJournalService.exportExcel(param);
	}
	
	@RequestMapping(value = ENTRY_JOURNAL.PRINT, method = RequestMethod.POST)
	public Object print(@RequestBody Map<String, Object> param) throws Exception {
		return entryJournalService.print(param);
	}
}
