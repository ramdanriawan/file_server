///*
//package com.biru.web.marketing.production;
//
//import com.biru.GuiConstants.HTML;
//import com.biru.GuiConstants.REST;
//import com.biru.GuiConstants.URI;
//import com.biru.common.entity.DropdownIdText;
//import com.biru.common.param.Param;
//import com.biru.common.parser.JsonParser;
//import com.biru.component.SessionComponent;
//import com.biru.service.CommonService;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.http.*;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Controller
//@RequestMapping(REST.MARKETING)
//public class DirectBdsController {
//
//	@Autowired
//	private CommonService common;
//
//	@Autowired
//	private RestTemplate restTemplate;
//
//	@Autowired
//	private SessionComponent sessionComponent;
//
//	@Value("${app.host}")
//	private String host;
//
//	@Value("${document.path}")
//	private String path;
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS, method = RequestMethod.GET)
//	public String directProduction(Model model) throws Exception {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
//		String appDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
//
//		model.addAttribute("mainMenu", "Marketing / Direct Production");
//		model.addAttribute("titlePage", "DIRECT PRODUCTION");
//		model.addAttribute("titlePageCreate", "DIRECT PRODUCTION | CREATE");
//		model.addAttribute("titlePageEdit", "DIRECT PRODUCTION | EDIT");
//		model.addAttribute("appDate", appDate);
//		model.addAttribute("path", path + sessionComponent.getTenantId());
//		model.addAttribute("isValidUserLevel", validationUserLevel());
//
//		model.addAttribute("pPpn", common.getParameter("TAXRATEH", "TAXRATEH003"));
//		model.addAttribute("pPolicyCost", common.getParameter("TAXRATEH", "TAXRATEH004"));
//		model.addAttribute("pStampDuty", common.getParameter("TAXRATEH", "TAXRATEH005"));
//		model.addAttribute("pAdminFee", common.getParameter("TAXRATEH", "TAXRATEH008"));
//		model.addAttribute("taxRate009", common.getParameter("TAXRATEH", "TAXRATEH009"));
//		model.addAttribute("taxRate011", common.getParameter("TAXRATEH", "TAXRATEH011"));
//		model.addAttribute("taxRate012", common.getParameter("TAXRATEH", "TAXRATEH012"));
//		model.addAttribute("pSysdate7", common.getParameter("SYSDATE", "SYSDATE007"));
//
//		List<DropdownIdText> message = common.getMessageSave();
//		for(DropdownIdText idText : message) {
//			model.addAttribute("M_" + idText.getId(), idText.getText());
//		}
//
//		return HTML.M_DPRODUCTION_BDS;
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_INQ, method = RequestMethod.GET)
//	public @ResponseBody String inquiry(
//			@RequestParam(value="sort", defaultValue="requestId") String sort,
//			@RequestParam(value="order", defaultValue="asc") String order,
//			@RequestParam(value="offset", defaultValue="0") Integer offset,
//			@RequestParam(value="limit", defaultValue="5") Integer limit,
//			@RequestParam(value="filterValue") String filterValue) throws JsonProcessingException {
//
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put(Param.SORT, sort);
//		param.put(Param.ORDER, order);
//		param.put(Param.OFFSET, offset);
//		param.put(Param.LIMIT, limit);
//		param.put(Param.FILTER_VALUE, filterValue);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_INQ + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		return response.getBody();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_INQ_MODIFY, method = RequestMethod.POST)
//	public @ResponseBody String inquiryModify(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DPRODUCTION_INQ_MODIFY + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		return response.getBody();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_DELETE, method = RequestMethod.POST)
//	public @ResponseBody String delete(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_DELETE + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		return response.getBody();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_SAVE, method = RequestMethod.POST)
//	public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		param.put(Param.USER, sessionComponent.getUserLogin());
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DPRODUCTION_SAVE + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		return response.getBody();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_DROPDOWN_TYPE_OF_COVER, method = RequestMethod.GET)
//	public @ResponseBody String getTypeOfCover() {
//		return common.getTypeOfCover();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_MA_0011, method = RequestMethod.GET)
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
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_TERM_AND_CONDITION, method = RequestMethod.GET)
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
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_DROPDOWN_DOCUMENT_TYPE, method = RequestMethod.GET)
//	public @ResponseBody String dropdownDocumentType(@RequestParam(name = "tcCode")String tcCode) {
//		return common.dropdownDocumentType(tcCode);
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_DROPDOWN_NAME, method = RequestMethod.GET)
//	public @ResponseBody String getName() {
//		return common.getName();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_MA_0012, method = RequestMethod.GET)
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
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_DROPDOWN_TC_GROUP, method = RequestMethod.GET)
//	public @ResponseBody String dropdownTcGroup() {
//		return common.dropdownTcGroup();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_DROPDOWN_TC_DETAILS, method = RequestMethod.GET)
//	public @ResponseBody String dropdownTcDetails(@RequestParam(name = "groupName")String groupName) {
//		return common.dropdownTcDetails(groupName);
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_COMM_OUT_NAME, method = RequestMethod.GET)
//	public @ResponseBody String dropdownCommOutName(
//			@RequestParam(name = "cliType") String cliType,
//			@RequestParam(name = "cliDataStatus", defaultValue = "") String cliDataStatus) {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put(Param.SORT, "cliName");
//		param.put(Param.ORDER, "asc");
//		param.put(Param.OFFSET, 0);
//		param.put(Param.LIMIT, Integer.MAX_VALUE);
//		param.put("cliType", cliType);
//		param.put("cliDataStatus", cliDataStatus);
//
//		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(param, httpHeaders);
//
//		String uri = host + URI.M_DPRODUCTION_GET_COMM_OUT_NAME + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		return response.getBody();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_GET_CLIENT, method = RequestMethod.GET)
//	public @ResponseBody String getClient(@RequestParam(name = "cliCode") String cliCode) {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("cliCode", cliCode);
//
//		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(param, httpHeaders);
//
//		String uri = host + URI.SD_PARTICIPANT_GET_PARTICIPANT + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		return response.getBody();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_GET_WHT_CLIENT, method = RequestMethod.GET)
//	public @ResponseBody String getWhtClient(@RequestParam(name = "cliCode") String cliCode) {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("cliCode", cliCode);
//
//		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(param, httpHeaders);
//
//		String uri = host + URI.M_DPRODUCTION_GET_WHT_CLIENT + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		return response.getBody();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_UPLOAD_FILE, method = RequestMethod.POST)
//	public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile mf) {
//
//		if(mf.isEmpty())
//			return "No data to upload!";
//
//		String pathFile = path.concat(sessionComponent.getTenantId()).concat(File.separator);
//		createFolder(pathFile);
//
//        String filename = pathFile.concat(mf.getOriginalFilename());
//		File file = new File(filename);
//
//		OutputStream os = null;
//		try {
//            byte[] bytes = mf.getBytes();
//
//            os = new FileOutputStream(file);
//            os.write(bytes);
//            os.close();
//        }catch(IOException e) {
//            e.printStackTrace();
//        }
//
//		return "Done";
//	}
//
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_SEARCH, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//	public @ResponseBody String search(
//			@RequestParam(value="trxVoucherId") String trxVoucherId,
//			@RequestParam(value="process") String process) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("trxVoucherId", trxVoucherId);
//		param.put("process", process);
//		param.put(Param.USER, sessionComponent.getUserLogin());
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DPRODUCTION_RPT_HMTL + "?tenantId=" + sessionComponent.getTenantId();
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
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_INQ_SEND, method = RequestMethod.POST)
//	public @ResponseBody String inquirySend(@RequestBody Map<String, Object> param) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DPRODUCTION_INQ_SEND + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//	    return response.getBody();
//	}
//
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_PRINT, method = RequestMethod.GET)
//	public void print(HttpServletResponse httpResponse,
//			@RequestParam(value="trxVoucherId") String trxVoucherId,
//			@RequestParam(value="code") String code,
//			@RequestParam(value="process") String process,
//			@RequestParam(value="document") String document) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("trxVoucherId", trxVoucherId);
//		param.put("code", code);
//		param.put("process", process);
//		param.put("document", document);
//		param.put(Param.USER, sessionComponent.getUserLogin());
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DPRODUCTION_RPT_PDF + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		Map<String, Object> result = JsonParser.getParser().readValue(response.getBody(), Map.class);
//		List<String> report = (List<String>) result.get("report");
//
//		for(int i=0; i<report.size(); i++) {
//			Path path = Paths.get(report.get(i));
//
//			byte[] data = Files.readAllBytes(path);
//		    httpResponse.setContentType("application/pdf");
//		    httpResponse.setHeader("Content-disposition", "filename=" + "Production.pdf");
//		    httpResponse.setContentLength(data.length);
//
//		    httpResponse.getOutputStream().write(data);
//		    httpResponse.getOutputStream().flush();
//		}
//
//	}
//
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_DOCUMENT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
//	public @ResponseBody byte[] exportDoc(HttpServletResponse httpResponse,
//			@RequestParam(value="trxVoucherId") String trxVoucherId,
//			@RequestParam(value="code") String code,
//			@RequestParam(value="process") String process,
//			@RequestParam(value="document") String document,
//			@RequestParam(value="file") String file) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("trxVoucherId", trxVoucherId);
//		param.put("code", code);
//		param.put("process", process);
//		param.put("document", document);
//		param.put(Param.USER, sessionComponent.getUserLogin());
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DPRODUCTION_RPT_DOC + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		Map<String, Object> result = JsonParser.getParser().readValue(response.getBody(), Map.class);
//		List<String> report = (List<String>) result.get("report");
//
//		byte[] data = null;
//		file = file.replaceAll(",", "").replaceAll(";", "");
//		for(int i=0; i<report.size(); i++) {
//			Path path = Paths.get(report.get(i));
//
//			data = Files.readAllBytes(path);
//		    httpResponse.setHeader("Content-disposition", "filename=" + file + ".docx");
//		    httpResponse.setContentLength(data.length);
//		}
//
//		return data;
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_SEND_EMAIL, method = RequestMethod.POST)
//	public @ResponseBody String sendEmail(@RequestBody Map<String, Object> param) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		param.put(Param.USER, sessionComponent.getUserLogin());
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DPRODUCTION_SEND_EMAIL + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//	    return response.getBody();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_CLOSING, method = RequestMethod.POST)
//	public @ResponseBody String closing(@RequestBody Map<String, Object> param) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		param.put(Param.USER, sessionComponent.getUserLogin());
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DPRODUCTION_CLOSING + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//	    return response.getBody();
//	}
//
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_CLOSING_SEARCH, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
//	public @ResponseBody String closingSearch(
//			@RequestParam(value="trxVoucherId") String trxVoucherId) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("trxVoucherId", trxVoucherId);
//		param.put(Param.USER, sessionComponent.getUserLogin());
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DPRODUCTION_RPT_CLOSING_HTML + "?tenantId=" + sessionComponent.getTenantId();
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
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_IS_VALID_TRX_DATE, method = RequestMethod.POST)
//	public @ResponseBody String isValidTrxDate(@RequestBody Map<String, Object> param) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.MR_IS_VALID_TRX_DATE + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//	    return response.getBody();
//	}
//
//	@RequestMapping(value = REST.M_DPRODUCTION_BDS_VALIDATION_USER_LEVEL, method = RequestMethod.POST)
//	public @ResponseBody String validationUserLevel() throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put(Param.USER, sessionComponent.getUserLogin());
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.MR_VALIDATION_USER_LEVEL + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//	    return response.getBody();
//	}
//
//	@RequestMapping(value = REST.MR_PRODUCTION_VIEW_PDF, method = RequestMethod.GET)
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
//	@RequestMapping(value = REST.MR_PRODUCTION_CLOSING_PRINT, method = RequestMethod.GET)
//	public void closingPrint(HttpServletResponse httpResponse,
//			@RequestParam(value="voucherId") String voucherId,
//			@RequestParam(value="type") String type,
//			@RequestParam(value="trxVoucherId") String trxVoucherId) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("voucherId", voucherId);
//		param.put("type", type);
//		param.put("trxVoucherId", trxVoucherId);
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DC_NOTE_CREATE_PDF + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		Path path = Paths.get(response.getBody());
//		byte[] data = Files.readAllBytes(path);
//	    httpResponse.setContentType("application/pdf");
//	    httpResponse.setHeader("Content-disposition", "filename=" + "Production.pdf");
//	    httpResponse.setContentLength(data.length);
//
//	    httpResponse.getOutputStream().write(data);
//	    httpResponse.getOutputStream().flush();
//	}
//
//	@RequestMapping(value = REST.MR_PRODUCTION_CLOSING_DOCUMENT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
//	public void exportDoc(HttpServletResponse httpResponse,
//			@RequestParam(value="voucherId") String voucherId,
//			@RequestParam(value="cliName") String cliName,
//			@RequestParam(value="type") String type,
//			@RequestParam(value="trxVoucherId") String trxVoucherId) throws IOException {
//		HttpHeaders httpHeaders = new HttpHeaders();
//		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
//
//		Map<String, Object> param = new HashMap<String, Object>();
//		param.put("voucherId", voucherId);
//		param.put("type", type);
//		param.put("trxVoucherId", trxVoucherId);
//
//		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
//
//		String uri = sessionComponent.getHost() + URI.M_DC_NOTE_CREATE_DOC + "?tenantId=" + sessionComponent.getTenantId();
//		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
//
//		Path path = Paths.get(response.getBody());
//
//		String file = "Closing - " + cliName;
//		file = file.replaceAll(",", "").replaceAll(";", "");
//		byte[] data = Files.readAllBytes(path);
//	    httpResponse.setHeader("Content-disposition", "filename=" + file + ".docx");
//	    httpResponse.setContentLength(data.length);
//
//	    httpResponse.getOutputStream().write(data);
//	    httpResponse.getOutputStream().flush();
//	}
//
//	@RequestMapping(value = REST.MR_PRODUCTION_SEND_EMAIL_CLOSING, method = RequestMethod.POST)
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
//
//	private void createFolder(String p_Path) {
//		File directory = new File(p_Path);
//
//	    if(!directory.exists())
//	    	directory.mkdir();
//	}
//
//}
//
//*/
