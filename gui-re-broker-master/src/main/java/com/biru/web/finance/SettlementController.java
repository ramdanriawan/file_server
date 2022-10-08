package com.biru.web.finance;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(REST.FINANCE)
public class SettlementController {
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@RequestMapping(value = REST.FIN_SETTLEMENT, method = RequestMethod.GET)
	public String settlement(Model model) throws Exception {
		model.addAttribute("mainMenu", "Finance  /  Settlement");
		model.addAttribute("titlePage", "SETTLEMENT");
		model.addAttribute("appDate", common.getAppDate().replaceAll("-", "/"));
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.FNC_SETTLEMENT;
	}
	
	@RequestMapping(value = REST.FIN_SETTLEMENT_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="trxVoucherId") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="type") String type,
			@RequestParam(value="client") String client) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put("type", type);
		param.put("client", client);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.FIN_SETTLEMENT_INQ + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.FIN_SETTLEMENT_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.FIN_SETTLEMENT_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.FIN_SETTLEMENT_SEARCH, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String search(
			@RequestParam(value="voucher") String voucher,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="dueDate") String dueDate,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="client") String client,
			@RequestParam(value="description") String description) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("transactionDate", transactionDate);
		param.put("dueDate", dueDate);
		param.put("voucherId", voucherId);
		param.put("client", client);
		param.put("description", description);
		param.put("version", "V2");
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
	
	@RequestMapping(value = REST.FIN_SETTLEMENT_VIEW_PDF, method = RequestMethod.GET)
	public @ResponseBody Object viewPdf(
			@RequestParam(value="voucher") String voucher,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="dueDate") String dueDate,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="client") String client,
			@RequestParam(value="description") String description) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("transactionDate", transactionDate);
		param.put("dueDate", dueDate);
		param.put("voucherId", voucherId);
		param.put("client", client);
		param.put("description", description);
		param.put("version", "V2");
		param.put("printedBy", sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_PRINT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "filename=Settlement.pdf")
	    		.contentType(MediaType.APPLICATION_PDF)
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.FIN_SETTLEMENT_EXPORT_EXCEL, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public @ResponseBody Object exportExcel(
			@RequestParam(value="voucher") String voucher,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="dueDate") String dueDate,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="client") String client,
			@RequestParam(value="description") String description) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("transactionDate", transactionDate);
		param.put("dueDate", dueDate);
		param.put("voucherId", voucherId);
		param.put("client", client);
		param.put("description", description);
		param.put("version", "V2");
		param.put("printedBy", sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_EXPORT_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=Settlement.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
}
