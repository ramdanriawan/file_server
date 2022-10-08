package com.biru.web.marketing;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(REST.MARKETING)
public class ClaimsController {

	@Autowired
	private CommonService common;

	@Autowired 
	private RestTemplate restTemplate;

	@Autowired 
	private SessionComponent sessionComponent;
	
	@Value("${document.path}")
	private String path;

	@RequestMapping(value = REST.M_CLAIMS, method = RequestMethod.GET)
	public String claims(Model model) throws Exception {
		model.addAttribute("mainMenu", "Marketing  /  Claims");
		model.addAttribute("titlePage", "CLAIMS");
		model.addAttribute("titlePageCreate", "CLAIMS | CREATE");
		model.addAttribute("titlePageEdit", "CLAIMS | EDIT");
		model.addAttribute("path", path + sessionComponent.getTenantId());
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
		String appDate = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody().replaceAll("-", "/");
		
		model.addAttribute("appDate", appDate);

		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}

		return HTML.M_CLAIMS;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = REST.M_CLAIMS_DROPDOWN_TYPE_OF_COVER, method = RequestMethod.GET)
	public @ResponseBody Object getTypeOfCover() throws JsonParseException, JsonMappingException, IOException {
		String typeOfCover = common.getTypeOfCover();
		List<Map<String, Object>> listToc = JsonParser.getParser()
				.readValue(typeOfCover, List.class);
		
		List<Map<String, Object>> newListToc = new ArrayList<Map<String,Object>>();
		for(Map<String, Object> toc : listToc) {
			Map<String, Object> newToc = new HashMap<String, Object>();
			newToc.put("id", toc.get("id"));
			newToc.put("text", toc.get("id"));
			
			newListToc.add(newToc);
		}
		
		return newListToc;
	}
	
	@RequestMapping(value = REST.M_CLAIMS_DROPDOWN_TYPE_OF_COVER_VAL, method = RequestMethod.GET)
	public @ResponseBody String getTypeOfCoverVal() {
		return common.getTypeOfCover();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_DROPDOWN_DOCUMENT_TYPE, method = RequestMethod.GET)
	public @ResponseBody String dropdownDocumentType(@RequestParam(name = "tcCode", defaultValue = "") String tcCode) {
		return common.dropdownDocumentType(tcCode);
	}
	
	@RequestMapping(value = REST.M_CLAIMS_INQ_DATA, method = RequestMethod.GET)
	public @ResponseBody String inquiryData(
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="txDataStatus") String txDataStatus,
			@RequestParam(value="search") String search,
			@RequestParam(value="year") String year) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, updateSort(sort));
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put("txDataStatus", txDataStatus);
		param.put("search", search);
		param.put("year", year);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_INQ_DATA + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_INQ_DETAIL, method = RequestMethod.POST)
	public @ResponseBody String inquiryDetail(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_INQ_DETAIL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_CLAIMS_INQ_CREATE, method = RequestMethod.GET)
	public @ResponseBody String inquiryCreate(
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="client") String client,
			@RequestParam(value="cob") String cob) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, updateSort(sort));
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put("client", client);
		param.put("cob", cob);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_INQ_CREATE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_INQ_CREATE_DETAIL, method = RequestMethod.GET)
	public @ResponseBody String inquiryCreateDetail(
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="trxTrxId") String trxTrxId,
			@RequestParam(value="trxVoucherId") String trxVoucherId,
			@RequestParam(value="search") String search) throws JsonProcessingException {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, updateSort(sort));
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put("trxTrxId", trxTrxId);
		param.put("trxVoucherId", trxVoucherId);
		param.put("search", search);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_INQ_CREATE_DETAIL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_GET_UNDERWRITING, method = RequestMethod.POST)
	public @ResponseBody String getUnderwriting(@RequestBody Map<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_GET_UNDERWRITING + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_CLAIMS_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_CLAIMS_SETTLEMENT, method = RequestMethod.POST)
	public @ResponseBody String settlement(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_SETTLEMENT+ "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();	
	}
	
	@RequestMapping(value = REST.M_CLAIMS_DELETE, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> delete(@RequestBody HashMap<String, Object> param) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_DELETE+ "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);	
	}
	
	private String updateSort(String sort) {
		if(sort.equals("trxClassDesc"))
			sort = "trxClass";
		else if(sort.equals("trxTsiAmountStr"))
			sort = "trxTsiAmount"; 
		
		return sort;
	}
	
	@RequestMapping(value = REST.M_CLAIMS_UPLOAD_FILE, method = RequestMethod.POST)
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
	
	@RequestMapping(value = REST.M_CLAIMS_DOWNLOAD_FILE + "/{fileName}", method = RequestMethod.GET)
	public ResponseEntity<Resource> downloadFile(@PathVariable("fileName") String fileName, HttpServletResponse response) throws IOException {
    	String file = path.concat(sessionComponent.getTenantId()).concat(File.separator) + fileName;
    	
    	File f = new File(file);
    	
    	Path path = Paths.get(f.getAbsolutePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData(f.getName(), f.getName());
        
        return ResponseEntity.ok()
        		.headers(headers)
                .contentLength(f.length())
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
	}
	
	private void createFolder(String p_Path) {
		File directory = new File(p_Path);
		
	    if(!directory.exists())
	    	directory.mkdir();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_DROPDOWN_YEAR, method = RequestMethod.GET)
	public @ResponseBody String dropdownYear() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = sessionComponent.getHost() + URI.M_CLAIMS_DROPDOWN_YEAR+ "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_EXPORT_CLAIMSLIST_EXCEL, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public @ResponseBody Object exportXls(@RequestParam(value="year") String year,
			@RequestParam(value="status") String status) throws JsonProcessingException, FileNotFoundException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("year", year);
		param.put("status", status);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_EXPORT_CLAIMSLIST_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=Claim List.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.M_CLAIMS_GET_TR6J, method = RequestMethod.POST)
	public @ResponseBody Object getTr6j(@RequestBody Map<String, Object> params) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(params), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_GET_TR6J + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_PRINT_DLA_PLA, method = RequestMethod.GET)
	public void printDlaPla(HttpServletResponse httpResponse,
			@RequestParam(value="type") String type,
			@RequestParam(value="txVoucherId") String txVoucherId,
			@RequestParam(value="file") String file,
			@RequestParam(value="idKey") String idKey,
			@RequestParam(value="insId") String insId) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		param.put("txVoucherId", txVoucherId);
		param.put("file", file);
		param.put("idKey", idKey);
		param.put("insId", insId);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_PRINT_DLA_PLA + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setContentType("application/pdf");
	    httpResponse.setHeader("Content-disposition", "filename=" + file + ".pdf");
	    httpResponse.setContentLength(data.length);

	    httpResponse.getOutputStream().write(data);
	    httpResponse.getOutputStream().flush();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_PRINT_DOC_DLA_PLA, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
	public @ResponseBody byte[] printDocDlaPla(
			HttpServletResponse httpResponse,
			@RequestParam(value="type") String type,
			@RequestParam(value="txVoucherId") String txVoucherId,
			@RequestParam(value="file") String file,
			@RequestParam(value="idKey") String idKey,
			@RequestParam(value="insId") String insId) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("type", type);
		param.put("txVoucherId", txVoucherId);
		param.put("file", file);
		param.put("idKey", idKey);
		param.put("insId", insId);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_PRINT_DOC_DLA_PLA + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
			
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setHeader("Content-disposition", "filename=" + file + ".docx");
	    httpResponse.setContentLength(data.length);
		
		return data;
	}
	
	@RequestMapping(value = REST.M_CLAIMS_CREATE_DC_NOTES, method = RequestMethod.POST)
	public @ResponseBody String createDCNotes(@RequestBody Map<String, Object> params) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		params.put(Param.USER, sessionComponent.getUserLogin());
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(params), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_CREATE_DC_NOTES + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_GET_LIST_INSURANCE, method = RequestMethod.POST)
	public @ResponseBody String getListInsurance(@RequestBody Map<String, Object> params) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		params.put(Param.USER, sessionComponent.getUserLogin());
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(params), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_GET_LIST_INSURANCE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_GET_LIST_VALUE, method = RequestMethod.POST)
	public @ResponseBody String getListValue(@RequestBody Map<String, Object> params) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		params.put(Param.USER, sessionComponent.getUserLogin());
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(params), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_GET_LIST_VALUE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_GET_FINANCE, method = RequestMethod.POST)
	public @ResponseBody String getFinance(@RequestBody Map<String, Object> params) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		params.put(Param.USER, sessionComponent.getUserLogin());
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(params), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_GET_FINANCE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_PROCESS_FINANCE, method = RequestMethod.POST)
	public @ResponseBody String processFinance(@RequestBody Map<String, Object> params) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		params.put(Param.USER, sessionComponent.getUserLogin());
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(params), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_PROCESS_FINANCE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_FINANCE_SEARCH, method = RequestMethod.GET, produces = MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String financeSearch(
			@RequestParam(value="voucher") String voucher) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("version", "V2");

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_PRINT_JOURNAL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		String htmlContent = new String(Files.readAllBytes(path));
		Files.delete(path);
		
		return htmlContent;
	}
	
	@RequestMapping(value = REST.M_CLAIMS_FINANCE_VIEW_PDF, method = RequestMethod.GET)
	public @ResponseBody Object viewPdf(
			@RequestParam(value="voucher") String voucher) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("version", "V2");

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_PRINT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "filename=Finance.pdf")
	    		.contentType(MediaType.APPLICATION_PDF)
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.M_CLAIMS_FINANCE_EXPORT_EXCEL, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public @ResponseBody Object exportExcel(
			@RequestParam(value="voucher") String voucher) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("voucher", voucher);
		param.put("version", "V2");

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_EXPORT_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=Finance.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
	@RequestMapping(value = REST.M_CLAIMS_GET_LIST_INTERNALMEMO, method = RequestMethod.POST)
	public @ResponseBody String getListInternalMemo(@RequestBody Map<String, Object> params) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(params), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_GET_LIST_INTERNALMEMO + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_CLAIMS_CREATE_INTERNALMEMO, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
	public @ResponseBody byte[] createInternalMemo(
			HttpServletResponse httpResponse,
			@RequestParam(value="ccy") String ccy,
			@RequestParam(value="cedant") String cedant,
			@RequestParam(value="cedantName") String cedantName,
			@RequestParam(value="startDate") String startDate,
			@RequestParam(value="endDate") String endDate) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("ccy", ccy);
		param.put("cedant", cedant);
		param.put("cedantName", cedantName);
		param.put("startDate", startDate);
		param.put("endDate", endDate);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_CREATE_INTERNALMEMO + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
			
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setHeader("Content-disposition", "filename=InternalMemo - " + cedantName + ".docx");
	    httpResponse.setContentLength(data.length);
		
		return data;
	}
	
	@RequestMapping(value = REST.M_CLAIMS_CREATE_INTERNALMEMO_EXCEL, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
	public @ResponseBody Object createInternalMemoExcel(
			@RequestParam(value="startDate") String startDate,
			@RequestParam(value="endDate") String endDate) throws JsonProcessingException, FileNotFoundException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startDate", startDate);
		param.put("endDate", endDate);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_CLAIMS_CREATE_INTERNALMEMO_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		File file = new File(response.getBody());
	    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

	    return ResponseEntity.ok()
	    		.header("Content-Disposition", "attachment; filename=InternalMemo.xls")
	    		.contentLength(file.length())
	    		.body(resource);
	}
	
}

