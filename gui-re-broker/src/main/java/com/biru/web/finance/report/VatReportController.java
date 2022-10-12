package com.biru.web.finance.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(REST.FINANCE_REPORT)
public class VatReportController {
	@Autowired
	private CommonService common;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@RequestMapping(value = REST.FIN_VAT_REPORT, method = RequestMethod.GET)
	public String remittance(Model model) throws Exception {
		model.addAttribute("mainMenu", "Finance Report  /  VAT Report");
		model.addAttribute("titlePage", "VAT REPORT");
		model.addAttribute("appDate", common.getAppDate().replaceAll("-", "/"));
		
		return HTML.FNCR_VAT_REPORT;
	}
	
	@RequestMapping(value = REST.FIN_VAT_REPORT_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="transactionDate", defaultValue="") String transactionDate,
			@RequestParam(value="to", defaultValue="") String to) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put("transactionDate", transactionDate);
		param.put("to", to);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.FIN_VAT_REPORT_INQ + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.FIN_VAT_REPORT_PRINT, method = RequestMethod.GET)
	public @ResponseBody Object print(@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="to") String to) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("transactionDate", transactionDate);
		param.put("to", to);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.FIN_VAT_REPORT_PRINT+ "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
	    
	    File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "filename=VatReport.pdf")
	    		.contentType(MediaType.APPLICATION_PDF)
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.FIN_VAT_REPORT_EXPORT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public @ResponseBody Object export(@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="to") String to) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("transactionDate", transactionDate);
		param.put("to", to);
		

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.FIN_VAT_REPORT_EXPORT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
	    
	    File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=VatReport.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
}
