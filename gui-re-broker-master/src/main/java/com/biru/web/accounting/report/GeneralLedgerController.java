package com.biru.web.accounting.report;

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

import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;

@Controller
@RequestMapping(REST.ACCOUNTING_REPORT)
public class GeneralLedgerController {
	
	@Value("${app.host}")
	private String host;
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	public SessionComponent sessionComponent;
	
	@RequestMapping(value = REST.ACCTR_GENERAL_LEDGER, method = RequestMethod.GET)
	public String generalLedger(Model model) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
		String appDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
		
		model.addAttribute("mainMenu", "Accounting Report  /  General Ledger");
		model.addAttribute("titlePage", "GENERAL LEDGER");
		model.addAttribute("appDate", appDate);
		model.addAttribute("appDatetDP", common.getAppDateDP());
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.ACCTR_GENERAL_LEDGER;
	}
	
	/* 
	 * create general ledger 
	 * */
	@RequestMapping(value = REST.ACCTR_GL_SEARCH, method = RequestMethod.GET, produces="text/html")
	public @ResponseBody String search(
			@RequestParam(value="fromDate") String fromDate,
			@RequestParam(value="toDate") String toDate,
			@RequestParam(value="fromCoa") String fromCoa,
			@RequestParam(value="toCoa") String toCoa,
			@RequestParam(value="offCode") String offCode) throws IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fromDate", fromDate);
		param.put("toDate", toDate);
		param.put("fromCoa", fromCoa);
		param.put("toCoa", toCoa);
		param.put("offCode", offCode);
		param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.ACCTR_GL_CREATE_HTML + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		String htmlContent = new String(Files.readAllBytes(path));
		Files.delete(path);
		
		htmlContent = htmlContent.replaceAll("font-size: 11px", "font-size: 13px");
		htmlContent = htmlContent.replaceAll("font-size: 9px", "font-size: 11px");
		
		return htmlContent;
	}
	
	/* 
	 * view general ledger 
	 * */
	@RequestMapping(value = REST.ACCTR_GL_VIEW_PDF, method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> viewPdf(
			@RequestParam(value="fromDate") String fromDate,
			@RequestParam(value="toDate") String toDate,
			@RequestParam(value="fromCoa") String fromCoa,
			@RequestParam(value="toCoa") String toCoa,
			@RequestParam(value="offCode") String offCode) throws IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fromDate", fromDate);
		param.put("toDate", toDate);
		param.put("fromCoa", fromCoa);
		param.put("toCoa", toCoa);
		param.put("offCode", offCode);
		param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.ACCTR_GL_CREATE_PDF + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "filename=GeneralLedger.pdf")
	    		.contentType(MediaType.APPLICATION_PDF)
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	/* 
	 * export to excel 
	 * */
	@RequestMapping(value = REST.ACCTR_GL_EXPORT_EXCEL, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<InputStreamResource> exportExcel(
			@RequestParam(value="fromDate") String fromDate,
			@RequestParam(value="toDate") String toDate,
			@RequestParam(value="fromCoa") String fromCoa,
			@RequestParam(value="toCoa") String toCoa,
			@RequestParam(value="offCode") String offCode) throws IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("fromDate", fromDate);
		param.put("toDate", toDate);
		param.put("fromCoa", fromCoa);
		param.put("toCoa", toCoa);
		param.put("offCode", offCode);
		param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.ACCTR_GL_CREATE_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=GeneralLedger.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
}
