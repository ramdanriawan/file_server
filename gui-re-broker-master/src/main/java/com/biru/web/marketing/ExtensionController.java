package com.biru.web.marketing;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
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
public class ExtensionController {

	@Autowired
	private CommonService common;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Value("${document.path}")
	private String path;
	
	private Logger log = LoggerFactory.getLogger(ExtensionController.class);
	/**
	 * Page of Extension Controller
	 * */
	@RequestMapping(value = REST.M_EXTENSION, method = RequestMethod.GET)
	public String Extension(Model model) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		String uri = sessionComponent.getHost() + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
		String appDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
		
		model.addAttribute("mainMenu", "Marketing  /  Extension");
		model.addAttribute("titlePage", "EXTENSION | CREATE");
		model.addAttribute("appDate", appDate);
		
		model.addAttribute("titlePageEdit", "EXTENSION | EDIT");
		model.addAttribute("path", path + sessionComponent.getTenantId());
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.M_EXTENSION;
	}
	
	/**
	 * Front Inquiry of Extension Data Controller
	 * */
	@RequestMapping(value = REST.M_EXTENSION_INQ, method = RequestMethod.GET)
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
			
		String uri = sessionComponent.getHost() + URI.M_EXTENSION_INQ + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	/**
	 * Saving Data Extension Controller
	 * */
	@RequestMapping(value = REST.M_EXTENSION_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_EXTENSION_ENTRY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	/**
	 * Upload File Data Extension To Server Controller
	 * */
	@RequestMapping(value = REST.M_EXTENSION_UPLOAD_FILE, method = RequestMethod.POST)
	public @ResponseBody String uploadFile(@RequestParam("file") MultipartFile mf) {
		
		if(mf.isEmpty())
			return "No data to upload!";
		
		String pathFile = path.concat(sessionComponent.getTenantId()).concat(File.separator).concat("extension").concat(File.separator);
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
		
		return mf.getOriginalFilename();	
	}
	
	/**
	 * Saving Data Upload Extension Controller
	 * */
	@RequestMapping(value = REST.M_EXTENSION_SAVE_UPLOAD, method = RequestMethod.POST)
	public @ResponseBody String saveUpload(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		String fileName = sessionComponent.getTenantId().concat(File.separator).concat("extension").concat(File.separator)
				.concat(Param.getStr(param, "file"));
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		param.put("fileName", fileName);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_EXTENSION_UPLOAD + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	private void createFolder(String p_Path) {
		File directory = new File(p_Path);
		
	    if(!directory.exists())
	    	directory.mkdirs();
	}
	
	@RequestMapping(value = REST.M_EXTENSION_CLOSING_PRINT, method = RequestMethod.GET)
	public void closingPrint(HttpServletResponse httpResponse,
			@RequestParam(value="voucherId") String voucherId,
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
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_DC_NOTE_CREATE_PDF + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setContentType("application/pdf");
	    httpResponse.setHeader("Content-disposition", "filename=" + "Production.pdf");
	    httpResponse.setContentLength(data.length);

	    httpResponse.getOutputStream().write(data);
	    httpResponse.getOutputStream().flush();
	}
	
	@RequestMapping(value = REST.M_EXTENSION_CLOSING_DOCUMENT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
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

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_DC_NOTE_CREATE_DOC + "?tenantId=" + sessionComponent.getTenantId();
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
	@RequestMapping(value = REST.M_EXTENSION_CLOSING_SEARCH, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String closingSearch(
			@RequestParam(value="ids") String ids,
			@RequestParam(value="type") String type,
			@RequestParam(value="trxVoucherId") String trxVoucherId,
			@RequestParam(value="p_Description2Add", required=false) String p_Description2Add,
			@RequestParam(value="insSub") String insSub,
			@RequestParam(value="interestDesc") String interestDesc,
			@RequestParam(value="endorsementType" , defaultValue = "") String endorsementType) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ids", ids);
		param.put("type", type);
		param.put("trxVoucherId", trxVoucherId);
		param.put("p_Description2Add", p_Description2Add);
		param.put("insSub", insSub);
		param.put("interestDesc", interestDesc);
		param.put("endorsementType", endorsementType);

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
	
	@RequestMapping(method = RequestMethod.POST, value = REST.M_EXTENSION_ISFILENAME_EXIST, produces = "application/json")
	public @ResponseBody ResponseEntity<String> isFileNameExist(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		
		Map<String, Object> param2 = new HashMap<String, Object>();
		String[] bits = Param.getStr(param, "fileName").split("\\\\");
		String fileNameOnly = bits[bits.length-1];
		log.info("name "+fileNameOnly);
		param.put("fileName", fileNameOnly);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.M_ISFILENAME_EXIST + "?tenantId=" + sessionComponent.getTenantId();
		log.info("Hasil: {}",restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody());
		return new ResponseEntity<String> (restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody(), HttpStatus.OK);
	}
}
