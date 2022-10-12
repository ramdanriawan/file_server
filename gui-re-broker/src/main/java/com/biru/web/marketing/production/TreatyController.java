package com.biru.web.marketing.production;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.multipart.MultipartFile;

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
@RequestMapping(REST.MARKETING)
public class TreatyController {
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommonService common;
	
	@Value("${app.host}")
	private String host;
	
	@Value("${document.path}")
	private String path;
	
	@RequestMapping(value = REST.M_TPRODUCTION, method = RequestMethod.GET)
	public String treatyProduction(Model model) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
		String appDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
		
		model.addAttribute("mainMenu", "Marketing / Treaty Production");
		model.addAttribute("titlePage", "TREATY PRODUCTION");
		model.addAttribute("titlePageCreate", "TREATY PRODUCTION | CREATE");
		model.addAttribute("titlePageEdit", "TREATY PRODUCTION | EDIT");
		model.addAttribute("appDate", appDate);
		model.addAttribute("path", path + sessionComponent.getTenantId());
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.M_TPRODUCTION;
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="requestId") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="filterValue") String filterValue) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put(Param.FILTER_VALUE, filterValue);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_INQ + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_INQ_MODIFY, method = RequestMethod.POST)
	public @ResponseBody String inquiryModify(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_INQ_MODIFY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_DELETE, method = RequestMethod.POST)
	public @ResponseBody String delete(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_DELETE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_INQ_SEND, method = RequestMethod.POST)
	public @ResponseBody String inquirySend(@RequestBody Map<String, Object> param) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_INQ_SEND + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
	    return response.getBody();
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = REST.M_TPRODUCTION_PRINT, method = RequestMethod.GET)
	public void print(HttpServletResponse httpResponse,
			@RequestParam(value="trxVoucherId") String trxVoucherId,
			@RequestParam(value="group") String group,
			@RequestParam(value="cob") String cob,
			@RequestParam(value="layer") String layer,
			@RequestParam(value="code") String code,
			@RequestParam(value="process") String process) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("trxVoucherId", trxVoucherId);
		param.put("group", group);
		param.put("cob", cob);
		param.put("layer", layer);
		param.put("code", code);
		param.put("process", process);
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_RPT_PDF + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Map<String, Object> result = JsonParser.getParser().readValue(response.getBody(), Map.class);
		List<String> report = (List<String>) result.get("report");
		
		for(int i=0; i<report.size(); i++) {
			Path path = Paths.get(report.get(i));
			
			byte[] data = Files.readAllBytes(path);
		    httpResponse.setContentType("application/pdf");
		    httpResponse.setHeader("Content-disposition", "filename=" + "ProductionTreaty.pdf");
		    httpResponse.setContentLength(data.length);

		    httpResponse.getOutputStream().write(data);
		    httpResponse.getOutputStream().flush();
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = REST.M_TPRODUCTION_DOCUMENT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
	public @ResponseBody byte[] exportDoc(HttpServletResponse httpResponse,
			@RequestParam(value="trxVoucherId") String trxVoucherId,
			@RequestParam(value="group") String group,
			@RequestParam(value="cob") String cob,
			@RequestParam(value="layer") String layer,
			@RequestParam(value="code") String code,
			@RequestParam(value="process") String process,
			@RequestParam(value="file") String file) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("trxVoucherId", trxVoucherId);
		param.put("group", group);
		param.put("cob", cob);
		param.put("layer", layer);
		param.put("code", code);
		param.put("process", process);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_RPT_DOC + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Map<String, Object> result = JsonParser.getParser().readValue(response.getBody(), Map.class);
		List<String> report = (List<String>) result.get("report");
		
		byte[] data = null;
		file = file.replaceAll(",", "").replaceAll(";", "");
		for(int i=0; i<report.size(); i++) {
			Path path = Paths.get(report.get(i));
			
			data = Files.readAllBytes(path);
		    httpResponse.setHeader("Content-disposition", "filename=" + file + ".docx");
		    httpResponse.setContentLength(data.length);
		}
		
		return data;
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_CLOSING, method = RequestMethod.POST)
	public @ResponseBody String closing(@RequestBody Map<String, Object> param) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<Object> httpEntity = new HttpEntity<Object>(param, httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_CLOSING + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
	    return response.getBody();
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_CLOSING_PRINT, method = RequestMethod.GET)
	public void closingPrint(HttpServletResponse httpResponse,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="type") String type,
			@RequestParam(value="trxVoucherId") String trxVoucherId, 
			@RequestParam(value="code") String code,
			@RequestParam(value="group") String group,
			@RequestParam(value="layer") String layer) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucherId", voucherId);
		param.put("type", type);
		param.put("trxVoucherId", trxVoucherId);
		param.put("code", code);
		param.put("group", group);
		param.put("layer", layer);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_DNCN_CREATE_PDF + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setContentType("application/pdf");
	    httpResponse.setHeader("Content-disposition", "filename=" + "Production.pdf");
	    httpResponse.setContentLength(data.length);

	    httpResponse.getOutputStream().write(data);
	    httpResponse.getOutputStream().flush();
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_CLOSING_DOCUMENT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
	public @ResponseBody byte[] exportDocClosing(HttpServletResponse httpResponse,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="type") String type,
			@RequestParam(value="trxVoucherId") String trxVoucherId, 
			@RequestParam(value="code") String code,
			@RequestParam(value="group") String group,
			@RequestParam(value="layer") String layer,
			@RequestParam(value="fileName") String fileName) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucherId", voucherId);
		param.put("type", type);
		param.put("trxVoucherId", trxVoucherId);
		param.put("code", code);
		param.put("group", group);
		param.put("layer", layer);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_DNCN_CREATE_DOC + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setHeader("Content-disposition", "filename=" + fileName.replaceAll(",", "").replaceAll(";", "") + ".docx");
	    httpResponse.setContentLength(data.length);
	    
	    return data;
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_DROPDOWN_TYPE_OF_COVER, method = RequestMethod.GET)
	public @ResponseBody String getTypeOfCover() {
		return common.getTypeOfCover();
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_DROPDOWN_TC_GROUP, method = RequestMethod.GET)
	public @ResponseBody String dropdownTcGroup() {
		return common.dropdownTcGroup();
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_DROPDOWN_TC_DETAILS, method = RequestMethod.GET)
	public @ResponseBody String dropdownTcDetails(@RequestParam(name = "groupName")String groupName) {
		return common.dropdownTcDetails(groupName);
	}
	
	@RequestMapping(value = REST.M_TPRODUCTION_UPLOAD_FILE, method = RequestMethod.POST)
	public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile mf) {
		
		if(mf.isEmpty())
			return "No data to upload!";
		
		String pathFile = path.concat(sessionComponent.getTenantId()).concat(File.separator);
		createFolder(pathFile);
		
        String filename = pathFile.concat(mf.getOriginalFilename());
		File file = new File(filename);
		
		OutputStream os = null;
		try {
            byte[] bytes = mf.getBytes();
            
            os = new FileOutputStream(file);
            os.write(bytes);
            os.close();
        }catch(IOException e) {
            e.printStackTrace();
        }
		
		return "Done";	
	}
	
	private void createFolder(String path) {
		File directory = new File(path);
		
	    if(!directory.exists())
	    	directory.mkdir();
	}
	
//	@RequestMapping(value = REST.M_TPRODUCTION_DROPDOWN_CLASIFICATION, method = RequestMethod.GET)
//	public @ResponseBody String getClasification() {
//		return common.getClasification();
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_DROPDOWN_CURR, method = RequestMethod.GET)
//	public @ResponseBody String getCurr() {
//		return common.getCurrency();
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_EXCHANGE_RATE, method = RequestMethod.GET)
//	public @ResponseBody Object exchange(@RequestParam(name = "date", defaultValue = "")String date,
//			@RequestParam(name = "curr", defaultValue = "")String curr) throws JsonProcessingException {
//		Map<String, Object> data = new HashMap<String, Object>();
//		data.put("result", common.getExchange(date, curr));
//		return data;
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_MA_0011, method = RequestMethod.GET)
//	public @ResponseBody Object tcPremium(@RequestParam(name = "tcCode", defaultValue = "")String tcCode) throws JsonProcessingException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("tcCode", tcCode);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//			
//		String uri = host + URI.MR_PRODUCTION_MA_0011 + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//		return response.getBody();
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_MA_0012, method = RequestMethod.GET)
//	public @ResponseBody Object getName(@RequestParam(name = "saCode", defaultValue = "") String saCode) throws JsonProcessingException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("saCode", saCode);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//			
//		String uri = host + URI.MR_PRODUCTION_MA_0012 + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//		return response.getBody();
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_DROPDOWN_NAME, method = RequestMethod.GET)
//	public @ResponseBody String getName() {
//		return common.getName();
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_TAX_RATE, method = RequestMethod.GET)
//	public @ResponseBody Object taxRate() throws JsonProcessingException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
//			
//		String uri = host + URI.MR_PRODUCTION_TAX_RATE + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//		return response.getBody();
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_TERM_AND_CONDITION, method = RequestMethod.GET)
//	public @ResponseBody String getTermAndCondition(@RequestParam(name = "tcCode", defaultValue = "") String tcCode) 
//			throws JsonProcessingException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("tcCode", tcCode);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//			
//		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_TERM_AND_CONDITION + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//		return response.getBody();	
//	}
//	
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_DROPDOWN_DOCUMENT_TYPE, method = RequestMethod.GET)
//	public @ResponseBody String dropdownDocumentType(@RequestParam(name = "tcCode")String tcCode) {
//		return common.dropdownDocumentType(tcCode);
//	}
//	
//	

//	

//	

//	
//	@RequestMapping(value = REST.M_TPRODUCTION_DELETE_FILE, method = RequestMethod.POST)
//	public @ResponseBody String deleteFile(@RequestBody String filenameDelete) {
//				
//		String pathFile = path.concat(sessionComponent.getTenantId()).concat(File.separator);
//		
//        String filename = pathFile.concat(filenameDelete);
//		File file = new File(filename);
//		file.delete();
//		
//		return "Done";	
//	}
//	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = REST.M_TPRODUCTION_SEARCH, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//	public @ResponseBody String search(
//			@RequestParam(value="trxVoucherId") String trxVoucherId,
//			@RequestParam(value="process") String process) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("trxVoucherId", trxVoucherId);
//		param.put("process", process);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//			
//		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_RPT_HMTL + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//		if(response.getBody() == null)
//			return "";
//		
//		Map<String, Object> result = JsonParser.getParser().readValue(response.getBody(), Map.class);
//		List<String> report = (List<String>) result.get("report");
//		
//		Path path;
//		String content;
//		String htmlContent = "";
//		for(int i=0; i<report.size(); i++) {			
//			path = Paths.get(report.get(i));
//			content = new String(Files.readAllBytes(path));
//			htmlContent += content;
//			
//			Files.delete(path);
//		}
//		
//		htmlContent = htmlContent.replaceAll("font-size:", "x-size:");
//		htmlContent = htmlContent.replaceAll("SansSerif", "Arial");
//		htmlContent = htmlContent.replaceAll("COMPANY_LOGO", "<center><img src='http://localhost:8080/gui-re-broker/static-data/logo-company' width='120' height='100'></center>");
//		
//		return htmlContent;
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_VIEW_PDF, method = RequestMethod.GET)
//	public @ResponseBody String viewPdf(
//			@RequestParam(value="trxVoucherId") String trxVoucherId,
//			@RequestParam(value="process") String process) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("trxVoucherId", trxVoucherId);
//		param.put("process", process);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//			
//		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_RPT_PDF + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//		return response.getBody();
//	}
//	
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_INQ_SEND, method = RequestMethod.POST)
//	public @ResponseBody String inquirySend(@RequestBody Map<String, Object> param) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//			
//		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_INQ_SEND + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//	    return response.getBody();
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_SEND_EMAIL, method = RequestMethod.POST)
//	public @ResponseBody String sendEmail(@RequestBody Map<String, Object> param) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//			
//		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_SEND_EMAIL + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//	    return response.getBody();
//	}
//	

//	

//	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = REST.M_TPRODUCTION_CLOSING_SEARCH, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//	public @ResponseBody String closingSearch(
//			@RequestParam(value="ids") String ids,
//			@RequestParam(value="type") String type,
//			@RequestParam(value="trxVoucherId") String trxVoucherId,
//			@RequestParam(value="insSub") String insSub) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//		
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("ids", ids);
//		param.put("type", type);
//		param.put("trxVoucherId", trxVoucherId);
//		param.put("insSub", insSub);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//			
//		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_RPT_CLOSING_HTML + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//		if(response.getBody() == null)
//			return "";
//		
//		Map<String, Object> result = JsonParser.getParser().readValue(response.getBody(), Map.class);
//		List<String> report = (List<String>) result.get("report");
//		
//		Path path;
//		String content;
//		String htmlContent = "";
//		for(int i=0; i<report.size(); i++) {			
//			path = Paths.get(report.get(i));
//			content = new String(Files.readAllBytes(path));
//			htmlContent += content;
//			
//			Files.delete(path);
//		}
//		
//		htmlContent = htmlContent.replaceAll("font-size:", "x-size:");
//		htmlContent = htmlContent.replaceAll("SansSerif", "Arial");
//		
//		return htmlContent;
//	}
//	
//	@RequestMapping(value = REST.M_TPRODUCTION_SEND_EMAIL_CLOSING, method = RequestMethod.POST)
//	public @ResponseBody String sendEmailClosing(@RequestBody Map<String, Object> param) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//			
//		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_SEND_EMAIL_CLOSING + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//		
//	    return response.getBody();
//	}

	
}

