package com.biru.web.marketing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;

@Controller
@RequestMapping(REST.MARKETING)
public class TreatyAdjustmentController {
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@RequestMapping(value = REST.M_TADJUSTMENT, method = RequestMethod.GET)
	public String treatyAdjustment(Model model) throws Exception {	
		model.addAttribute("mainMenu", "Marketing / Treaty Adjustment");
		model.addAttribute("titlePage", "TREATY ADJUSTMENT");
		model.addAttribute("titlePageCreate", "TREATY ADJUSTMENT | CREATE");
		model.addAttribute("titlePageEdit", "TREATY ADJUSTMENT | EDIT");
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.M_TADJUSTMENT;
	}
	
	@RequestMapping(value = REST.M_TADJUSTMENT_INQ, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="sort", defaultValue="requestId") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit,
			@RequestParam(value="client") String client,
			@RequestParam(value="treInsStart") String treInsStart,
			@RequestParam(value="treInsEnd") String treInsEnd) throws Exception {
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);
		param.put("client", client);
		param.put("treInsStart", treInsStart);
		param.put("treInsEnd", treInsEnd);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TADJUSTMENT_INQ + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_TADJUSTMENT_INQ_MODIFY, method = RequestMethod.POST)
	public @ResponseBody String inquiryModify(@RequestBody Map<String, Object> param) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TADJUSTMENT_INQ_MODIFY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_TADJUSTMENT_SAVE, method = RequestMethod.POST)
	public @ResponseBody String save(@RequestBody Map<String, Object> param) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TADJUSTMENT_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.M_TADJUSTMENT_PROCESS, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> process(@RequestBody Map<String, Object> param) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TADJUSTMENT_PROCESS + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
	}
	
	@RequestMapping(value = REST.M_TADJUSTMENT_CANCEL, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<String> cancel(@RequestBody Map<String, Object> param) throws Exception {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		param.put(Param.USER, sessionComponent.getUserLogin());

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TADJUSTMENT_CANCEL+ "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return new ResponseEntity<String>(response.getBody(), HttpStatus.OK);
	}
	
	@RequestMapping(value = REST.M_TADJUSTMENT_PRINT, method = RequestMethod.GET)
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
		param.put("addDesc", "Adjustment ");
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_DNCN_CREATE_PDF + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setContentType("application/pdf");
	    httpResponse.setHeader("Content-disposition", "filename=" + "Adjustment.pdf");
	    httpResponse.setContentLength(data.length);

	    httpResponse.getOutputStream().write(data);
	    httpResponse.getOutputStream().flush();
	}
	
	@RequestMapping(value = REST.M_TADJUSTMENT_DOCUMENT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.wordprocessingml.document")
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
		param.put("addDesc", "Adjustment ");

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.M_TPRODUCTION_DNCN_CREATE_DOC + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		Path path = Paths.get(response.getBody());
		
		byte[] data = Files.readAllBytes(path);
	    httpResponse.setHeader("Content-disposition", "filename=" + fileName.replaceAll(",", "").replaceAll(";", "") + ".docx");
	    httpResponse.setContentLength(data.length);
	    
	    return data;
	}
	
}

