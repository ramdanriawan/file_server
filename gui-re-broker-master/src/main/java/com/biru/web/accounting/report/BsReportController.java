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
public class BsReportController {
	
	@Value("${app.host}")
	private String host;
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	public SessionComponent sessionComponent;
	
	@RequestMapping(value = REST.ACCTR_BS_REPORT, method = RequestMethod.GET)
	public String trialBalance(Model model) throws ParseException {
		model.addAttribute("mainMenu", "Accounting Report  / BS Report");
		model.addAttribute("titlePage", "BS REPORT");
		
		String date[] = common.getAppDate().split(CHARACTER.DASH);
		model.addAttribute("year", date[2]);
		model.addAttribute("month", date[1]);
		
		return HTML.ACCTR_BS_REPORT;
	}
	
	/* 
	 * create balance sheet 
	 * */
	@RequestMapping(value = REST.ACCTR_BS_REPORT_PREVIEW, method = RequestMethod.GET, produces="text/html")
	public @ResponseBody String preview(
			@RequestParam(value="month") String month,
			@RequestParam(value="year") String year) throws IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("month", month);
		param.put("year", year);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.ACCTR_BS_REPORT_PREVIEW + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		String htmlContent = new String(Files.readAllBytes(path));
		Files.delete(path);
		
		htmlContent = htmlContent.replaceAll("font-size: 11px", "font-size: 13px");
		htmlContent = htmlContent.replaceAll("font-size: 9px", "font-size: 11px");
		
		return htmlContent;
	}
	
	/* 
	 * view balance sheet 
	 * */
	@RequestMapping(value = REST.ACCTR_BS_REPORT_PRINT, method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> print(
			@RequestParam(value="month") String month,
			@RequestParam(value="year") String year) throws IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("month", month);
		param.put("year", year);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.ACCTR_BS_REPORT_PRINT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "filename=BS_Report.pdf")
	    		.contentType(MediaType.APPLICATION_PDF)
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	/* 
	 * export to excel 
	 * */
	@RequestMapping(value = REST.ACCTR_BS_REPORT_EXPORT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public ResponseEntity<InputStreamResource> export(
			@RequestParam(value="month") String month,
			@RequestParam(value="year") String year) throws IOException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("month", month);
		param.put("year", year);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = host + URI.ACCTR_BS_REPORT_EXPORT+ "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=BS_Report.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
}
