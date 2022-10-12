package com.biru.web.marketing;

import java.io.File;
import java.io.IOException;
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
public class UploadTreatyController {
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private CommonService common;
	
	@Value("${document.path}")
	private String path;
	
	/**
	 * Page of Extension Controller
	 * */
	@RequestMapping(value = REST.M_UPLOAD_TREATY, method = RequestMethod.GET)
	public String UploadTreaty(Model model) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		String uri = sessionComponent.getHost() + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
		String appDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
		
		model.addAttribute("mainMenu", "Marketing  /  Upload Treaty");
		model.addAttribute("titlePage", "UPLOAD TREATY | CREATE");
		model.addAttribute("appDate", appDate);
		
		model.addAttribute("titlePageEdit", "UPLOAD TREATY | EDIT");
		model.addAttribute("path", path + sessionComponent.getTenantId());
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.M_UPLOAD_TREATY;
	}
	
	/**
	 * Front Inquiry of Extension Data Controller
	 * */
	@RequestMapping(value = REST.M_UPLOAD_TREATY_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="trxVoucherId") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="type") String type,
			@RequestParam(value="clientCode") String clientCode) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put("typeOfCover", type);
		param.put("clientCode", clientCode);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_UPLOAD_TREATY_INQ + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/**
	 * Saving Data Upload Extension Controller
	 * */
	@RequestMapping(value = REST.M_UPLOAD_TREATY_SAVE_UPLOAD, method = RequestMethod.POST)
	public @ResponseBody String saveUpload(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		String fileName = sessionComponent.getTenantId().concat(File.separator).concat("extension").concat(File.separator)
				.concat(Param.getStr(param, "file"));
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		param.put("fileName", fileName);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_UPLOAD_TREATY_UPLOAD + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_UPLOAD_TREATY_CLOSING_DOCUMENT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
	public @ResponseBody byte[] exportDoc(HttpServletResponse httpResponse,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="cliName") String cliName,
			@RequestParam(value="type") String type,
			@RequestParam(value="trxVoucherId") String trxVoucherId,
			@RequestParam(value="p_Description2Add", required=false) String p_Description2Add,
			@RequestParam(value="fileName") String fileName,
			@RequestParam(value="insSub") String insSub,
			@RequestParam(value="interestDesc") String interestDesc,
			@RequestParam(value="modul" , defaultValue = "") String modul) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucherId", voucherId);
		param.put("type", type);
		param.put("trxVoucherId", trxVoucherId);
		param.put("p_Description2Add", p_Description2Add);
		param.put("insSub", insSub);
		param.put("interestDesc", interestDesc);
		param.put("modul", modul);
		param.put("cliName", cliName);
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_DC_NOTE_CREATE_DOC_U_TREATY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		
		String file = "Extension - " + cliName; 
		
		if("treaty".equals(modul)){
			file = "Treaty - " + cliName; 
		}
		
		file = file.replaceAll(",", "").replaceAll(";", "");
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setHeader("Content-disposition", "filename=" + file + ".docx");
	    httpResponse.setContentLength(data.length);
	    
	    return data;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = REST.M_UPLOAD_TREATY_CLOSING_SEARCH, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String closingSearch(
			@RequestParam(value="ids") String ids,
			@RequestParam(value="type") String type,
			@RequestParam(value="trxVoucherId") String trxVoucherId,
			@RequestParam(value="p_Description2Add", required=false) String p_Description2Add,
			@RequestParam(value="insSub") String insSub,
			@RequestParam(value="interestDesc") String interestDesc,
			@RequestParam(value="cliName") String cliName,
			@RequestParam(value="endorsementType" , defaultValue = "") String endorsementType) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		System.out.println(">>>>>>> CLIENT NAME = "+cliName);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("type", type);
		param.put("trxVoucherId", trxVoucherId);
		param.put("p_Description2Add", p_Description2Add);
		param.put("insSub", insSub);
		param.put("interestDesc", interestDesc);
		param.put("endorsementType", endorsementType);
		param.put("uploadTreaty", "y");
		param.put("cliName", cliName);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.MR_PRODUCTION_RPT_CLOSING_HTML + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		if(response.getBody() == null)
			return "";
		
		Map<String, Object> result = JsonParser.getParser().readValue(response.getBody(), Map.class);
		List<String> report = (List<String>) result.get("report");
		
		Path path;
		String content;
		String htmlContent = "";
		for(int i=0; i<report.size(); i++) {			
			path = Paths.get(report.get(i));
			content = new String(Files.readAllBytes(path));
			htmlContent += content;
			
			Files.delete(path);
		}
		
		htmlContent = htmlContent.replaceAll("font-size:", "x-size:");
		htmlContent = htmlContent.replaceAll("SansSerif", "Arial");
		
		return htmlContent;
	}
	
	@RequestMapping(value = REST.M_UPLOAD_TREATY_CLOSING_PRINT, method = RequestMethod.GET)
	public void closingPrint(HttpServletResponse httpResponse,
			@RequestParam(value="voucherId") String voucherId,
			@RequestParam(value="cliName") String cliName,
			@RequestParam(value="type") String type,
			@RequestParam(value="trxVoucherId") String trxVoucherId,
			@RequestParam(value="p_Description2Add", required=false) String p_Description2Add,
			@RequestParam(value="insSub") String insSub,
			@RequestParam(value="interestDesc") String interestDesc) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucherId", voucherId);
		param.put("type", type);
		param.put("trxVoucherId", trxVoucherId);
		param.put("p_Description2Add", p_Description2Add);
		param.put("insSub", insSub);
		param.put("interestDesc", interestDesc);
		param.put("cliName", cliName);
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_DC_NOTE_CREATE_PDF_U_TREATY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setContentType("application/pdf");
	    httpResponse.setHeader("Content-disposition", "filename=" + "Production.pdf");
	    httpResponse.setContentLength(data.length);

	    httpResponse.getOutputStream().write(data);
	    httpResponse.getOutputStream().flush();
	}
	
	/**
	 * Validate Source Code
	 * */
	/*@RequestMapping(method = RequestMethod.POST, value = REST.M_UPLOAD_TREATY_ISSOURCE_CODE_EXIST, produces = "application/json")
	public @ResponseBody ResponseEntity<String> isFileNameExist(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		String fileName = sessionComponent.getTenantId().concat(File.separator).concat("extension").concat(File.separator)
				.concat(Param.getStr(param, "file"));
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		param.put("fileName", fileName);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_UPLOAD_TREATY_ISSOURCE_CODE_EXIST + "?tenantId=" + sessionComponent.getTenantId();
		
		return new ResponseEntity<String> (restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody(), HttpStatus.OK);
	}*/
}
