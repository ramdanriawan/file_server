package com.biru.web.accounting;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@Controller
@RequestMapping(REST.ACCOUNTING)
public class EntryJournalController {
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommonService common;
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL, method = RequestMethod.GET)
	public String entryJournal(Model model) throws JsonParseException, JsonMappingException, ParseException, IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_TRANSACTION_MIN_DATE + "?tenantId=" + sessionComponent.getTenantId();
		String transactionMinDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
		
		uri = sessionComponent.getHost() + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
		String appDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
		
		model.addAttribute("mainMenu", "Accounting  /  Entry Journal");
		model.addAttribute("titlePage", "ENTRY JOURNAL");
		model.addAttribute("titlePageCreate", "ENTRY JOURNAL | CREATE");
		model.addAttribute("titlePageEdit", "ENTRY JOURNAL | EDIT");
		model.addAttribute("transactionMinDate", transactionMinDate);
		model.addAttribute("appDate", appDate);
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.ACCT_ENTY_JOURNAL;
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="sort", defaultValue="glTrxDate") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.FILTER_KEY, filterKey);
		param.put(Param.FILTER_VALUE, filterValue);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_INQ + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_DROPDOWN_PROJECT, method = RequestMethod.GET)
	public @ResponseBody String dropdownProject() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_DROPDOWN_PROJECT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_DROPDOWN_OFFICE, method = RequestMethod.GET)
	public @ResponseBody String dropdownOffice() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_DROPDOWN_OFFICE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_CLIENT, method = RequestMethod.GET)
	public @ResponseBody String client(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="sort", defaultValue="cliName") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.FILTER_KEY, filterKey);
		param.put(Param.FILTER_VALUE, filterValue);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_CLIENT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_COA, method = RequestMethod.GET)
	public @ResponseBody String coa(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="sort", defaultValue="coaCode") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.FILTER_KEY, filterKey);
		param.put(Param.FILTER_VALUE, filterValue);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_COA + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_VOUCHER_CODE, method = RequestMethod.GET)
	public @ResponseBody String voucherCode(
			@RequestParam(value="date") String date) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.DATE, date);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_VOUCHER_CODE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_EXCHANGE, method = RequestMethod.GET)
	public @ResponseBody String exchange(
			@RequestParam(value="date") String date,
			@RequestParam(value="curr") String curr) throws JsonProcessingException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.DATE, date);
		param.put(Param.CURR, curr);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_EXCHANGE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		param.put(Param.CREATE_BY, sessionComponent.getUserLogin());
		param.put(Param.CREATE_ON, now);
		param.put(Param.MODIFY_BY, sessionComponent.getUserLogin());
		param.put(Param.MODIFY_ON, now);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_EDIT, method = RequestMethod.POST)
	public @ResponseBody String edit(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_EDIT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_REMOVE, method = RequestMethod.POST)
	public @ResponseBody String remove(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_REMOVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_PRINT_JOURNAL, method = RequestMethod.GET, produces=MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String printJournal(
			@RequestParam(value="voucher") String voucher,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="dueDate") String dueDate,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="client") String client) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("transactionDate", transactionDate);
		param.put("dueDate", dueDate);
		param.put("voucherId", voucherId);
		param.put("client", client);
		param.put("printedBy", sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_PRINT_JOURNAL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		String htmlContent = new String(Files.readAllBytes(path));
		Files.delete(path);
		
		htmlContent = htmlContent.replaceAll("font-size: 10px", "font-size: 11px");
		htmlContent = htmlContent.replaceAll("font-size: 12px", "font-size: 13px");
		
		return htmlContent;
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_EXPORT_EXCEL, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public @ResponseBody Object exportExcel(@RequestParam(value="voucher") String voucher,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="dueDate") String dueDate,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="client") String client) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("transactionDate", transactionDate);
		param.put("dueDate", dueDate);
		param.put("voucherId", voucherId);
		param.put("client", client);
		param.put("printedBy", sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_EXPORT_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=Journal.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.ACCT_ENTRY_JOURNAL_PRINT, method = RequestMethod.GET)
	public @ResponseBody Object print(@RequestParam(value="voucher") String voucher,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="dueDate") String dueDate,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="client") String client) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("transactionDate", transactionDate);
		param.put("dueDate", dueDate);
		param.put("voucherId", voucherId);
		param.put("client", client);
		param.put("printedBy", sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_PRINT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "filename=Journal.pdf")
	    		.contentType(MediaType.APPLICATION_PDF)
	    		.contentLength(file.length())
	    		.body(resource);
	}
}
