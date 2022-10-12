package com.biru.web.marketing.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
@RequestMapping(REST.MARKETING_REPORT)
public class ReportProduction {

	@Autowired
	private CommonService common;

	@Autowired 
	private RestTemplate restTemplate;

	@Autowired 
	private SessionComponent sessionComponent;
	
	@RequestMapping(value = REST.MR_REPORT_PRODUCTION, method = RequestMethod.GET)
	public String endorsement(Model model) throws Exception {
		model.addAttribute("mainMenu", "Marketing / Report Production");
		model.addAttribute("titlePage", "REPORT PRODUCTION");
		model.addAttribute("appDate", common.getAppDate().replaceAll("-", "/"));
		model.addAttribute("urlClient", "report-production/client");
		model.addAttribute("urlSearch", "report-production/inquiry");
		model.addAttribute("urlTypeOfCover", "report-production/get-dropdownTypeOfCover");

		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}

		return HTML.MR_REPORT_PRODUCTION;
	}
	
	@RequestMapping(value = REST.MR_REPORT_PRODUCTION_DROPDOWN_TYPE_OF_COVER, method = RequestMethod.GET)
	public @ResponseBody String getTypeOfCover() {
		return common.getTypeOfCover();
	}

	@RequestMapping(value = REST.MR_REPORT_PRODUCTION_CLIENT, method = RequestMethod.GET)
	public @ResponseBody String client(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="client", required=false) String cliType,
			@RequestParam(value="cliDataStatus", required=false) String cliDataStatus,
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
		param.put("cliType", cliType);
		param.put("cliDataStatus", cliDataStatus);

		return common.lookupClient(param);
	}

	@RequestMapping(value = REST.MR_REPORT_PRODUCTION_INQUIRY, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="typeOfCover") String typeOfCover,
			@RequestParam(value="client") String client,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="to") String to,
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeOfCover", typeOfCover);
		param.put("client", client);
		param.put("transactionDate", transactionDate);
		param.put("to", to);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		System.out.println(param);
		return common.tr0006Inquiry(param);
	}
	
	@RequestMapping(value = REST.MR_REPORT_PRODUCTION_TRANSACTION_DETAIL, method = RequestMethod.GET)
	public @ResponseBody String transactionDetail(
			@RequestParam(value="voucherId") String voucherId) throws JsonProcessingException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucherId", voucherId);


		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.MR_REPORT_PRODUCTION_TRANSACTION_DETAIL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		System.out.println(response.getBody());
		return response.getBody();
	}
	
	
	@RequestMapping(value = REST.MR_REPORT_PRODUCTION_EXPORT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public @ResponseBody Object export(@RequestParam(value="listIdKey") String listIdKey,
			@RequestParam(value="tradeDate") String tradeDate,
			@RequestParam(value="transactionId") String transactionId,
			@RequestParam(value="typeOfCover") String typeOfCover,
			@RequestParam(value="curr") String curr,
			@RequestParam(value="tsi") String tsi,
			@RequestParam(value="remarks") String remarks,
			@RequestParam(value="period") String period,
			@RequestParam(value="officer") String officer) throws JsonProcessingException, FileNotFoundException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object>param = new HashMap<String, Object>();
		param.put("listIdKey", listIdKey);
		param.put("transactionId", transactionId);
		param.put("tradeDate", tradeDate);
		param.put("typeOfCover", typeOfCover);
		param.put("curr", curr);
		param.put("tsi", tsi);
		param.put("remarks", remarks);
		param.put("period", period);
		param.put("officer", officer);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.MR_REPORT_PRODUCTION_EXPORT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=RPT Poduction Detail 1.0.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.MR_REPORT_PRODUCTION_PRINT_JOURNAL, method = RequestMethod.GET, produces=MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String printJournal(
			@RequestParam(value="voucher") String voucher,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="dueDate") String dueDate,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="client") String client,
			@RequestParam(value="description") String description) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("transactionDate", transactionDate);
		param.put("dueDate", dueDate);
		param.put("voucherId", voucherId);
		param.put("client", client);
		param.put("description", description);
		param.put("printedBy", sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.MR_REPORT_PRODUCTION_PRINT_JOURNAL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		String htmlContent = new String(Files.readAllBytes(path));
		Files.delete(path);
		
		htmlContent = htmlContent.replaceAll("font-size: 10px", "font-size: 11px");
		htmlContent = htmlContent.replaceAll("font-size: 12px", "font-size: 13px");
		
		return htmlContent;
	}
	
	@RequestMapping(value = REST.MR_REPORT_PRODUCTION_EXPORT_JOURNAL, method = RequestMethod.GET)
	public @ResponseBody Object exportExcel(@RequestParam(value="voucher") String voucher,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="dueDate") String dueDate,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="client") String client,
			@RequestParam(value="description") String description) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("transactionDate", transactionDate);
		param.put("dueDate", dueDate);
		param.put("voucherId", voucherId);
		param.put("client", client);
		param.put("description", description);
		param.put("printedBy", sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.MR_REPORT_PRODUCTION_EXPORT_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=Journal.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.MR_REPORT_PRODUCTION_PRINT, method = RequestMethod.GET)
	public @ResponseBody Object print(@RequestParam(value="voucher") String voucher,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="dueDate") String dueDate,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="client") String client,
			@RequestParam(value="description") String description) throws IOException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("transactionDate", transactionDate);
		param.put("dueDate", dueDate);
		param.put("voucherId", voucherId);
		param.put("client", client);
		param.put("description", description);
		param.put("printedBy", sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.MR_REPORT_PRODUCTION_PRINT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "filename=Journal.pdf")
	    		.contentType(MediaType.APPLICATION_PDF)
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.MR_REPORT_PRODUCTION_TR6_EXPORT_TO_EXCEL, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public @ResponseBody Object tr6ExportToExcel(
			@RequestParam(value="typeOfCover") String typeOfCover,
			@RequestParam(value="client") String client,
			@RequestParam(value="transactionDate") String transactionDate,
			@RequestParam(value="toTransactionDate") String toTransactionDate) throws JsonProcessingException, FileNotFoundException  {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeOfCover", typeOfCover);
		param.put("client", client);
		param.put("transactionDate", transactionDate);
		param.put("toTransactionDate", toTransactionDate);
		System.out.println(param);
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.MR_REPORT_PRODUCTION_TR6_EXPORT_TO_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=Report_Production.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
}
