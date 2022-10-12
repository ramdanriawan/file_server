package com.biru.web.accounting.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.CHARACTER;
import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;

@Controller
@RequestMapping(REST.ACCOUNTING_REPORT)
public class CashFlowController {
	
	@Value("${app.host}")
	private String host;
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	public SessionComponent sessionComponent;
	
	@RequestMapping(value = REST.ACCTR_CASH_FLOW, method = RequestMethod.GET)
	public String trialBalance(Model model) throws ParseException {
		model.addAttribute("mainMenu", "Accounting Report  /  Cash Flow");
		model.addAttribute("titlePage", "CASH FLOW");
		
		String date[] = common.getAppDate().split(CHARACTER.DASH);
		model.addAttribute("year", date[2]);
		
		return HTML.ACCTR_CASH_FLOW;
	}
	
	/* 
	 * create cash flow 
	 * */
	@RequestMapping(value = REST.ACCTR_CF_SEARCH, method = RequestMethod.GET, produces="text/html")
	public @ResponseBody String search(
			@RequestParam(value="year") String year) throws IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("year", year);
		param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.ACCTR_CF_CREATE_HTML + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		String htmlContent = new String(Files.readAllBytes(path));
		Files.delete(path);
		
		htmlContent = htmlContent.replaceAll("font-size: 11px", "font-size: 13px");
		htmlContent = htmlContent.replaceAll("font-size: 9px", "font-size: 11px");
		
		return htmlContent;
	}
	
	/* 
	 * view cash flow
	 * */
	@RequestMapping(value = REST.ACCTR_CF_VIEW_PDF, method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> viewPdf(
			@RequestParam(value="year") String year) throws IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("year", year);
		param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.ACCTR_CF_CREATE_PDF + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "filename=CashFlow.pdf")
	    		.contentType(MediaType.APPLICATION_PDF)
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	/* 
	 * export to excel 
	 * */
	@RequestMapping(value = REST.ACCTR_CF_EXPORT_EXCEL, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<InputStreamResource> exportExcel(
			@RequestParam(value="year") String year) throws IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("year", year);
		param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.ACCTR_CF_CREATE_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=CashFlow.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
}
