package com.biru.web.staticdata;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.REST;
import com.biru.GuiConstants.URI;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(REST.STATIC_DATA)
public class StaticDataController {
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SessionComponent sessionComponent;

	/*
	 * get status
	 * 11 = Active
	 * 12 = Inactive
	 */
	@RequestMapping(value = REST.DROPDOWN_STATUS, method = RequestMethod.GET)
	public @ResponseBody String getDropdownStatus() {
		return common.getDropdownStatus();
	}

	/* 
	 * get application date 
	 * */
	@RequestMapping(value = REST.APPLICATION_DATE, method = RequestMethod.GET)
	public @ResponseBody String getAppDate() {
		return common.getAppDate();
	}
	
	/* 
	 * get next business day
	 * format yyyy-MM-dd
	 * */
	@RequestMapping(value = REST.APPLICATION_DATE_NEXT, method = RequestMethod.GET)
	public @ResponseBody String getAppDateNext() throws ParseException {
		return common.getAppDateNext();			
	}
	
	//==================== MA0004 (CHART OF ACCOUNT) ====================//
	
	@RequestMapping(value = REST.CURR_COA, method = RequestMethod.GET)
	public @ResponseBody String getCurrCoa(@RequestParam("coaCode") String p_CoaCode) {
		return common.getCurrCoa(p_CoaCode);			
	}
	
	@RequestMapping(value = REST.DROPDOWN_BANK, method = RequestMethod.GET)
	public @ResponseBody String getBank() {
		return common.getBank();			
	}
	
	//==================== MA0005 (CLIENT) ====================//
	
	@RequestMapping(value = REST.DROPDOWN_CLIENT, method = RequestMethod.GET)
	public @ResponseBody String getClient(@RequestParam("cliType") String p_CliType) {
		return common.getClient(p_CliType);			
	}
	
	//==================== MA0011 (PRODUCT) ====================//
	
	@RequestMapping(value = REST.DROPDOWN_PRODUCT, method = RequestMethod.GET)
	public @ResponseBody String getDropdownProduct() {
		return common.getProduct();			
	}
	
	//==================== MA0012 (SALES/OFFICER) ====================//
	
	@RequestMapping(value = REST.DROPDOWN_OFFICER, method = RequestMethod.GET)
	public @ResponseBody String getDropdownOfficer() {
		return common.getOfficer();			
	}
	
	@RequestMapping(value = REST.DROPDOWN_OFFICER_NOT_2, method = RequestMethod.GET)
	public @ResponseBody String getDropdownOfficerTypeNot2() {
		return common.getOfficerTypeNot2();		
	}
	
	//==================== MA0014 (PARAMETER CHILD) ====================//
	
	@RequestMapping(value = REST.DROPDOWN_PARAM, method = RequestMethod.GET)
	public @ResponseBody String getDropdownParam(
			@RequestParam("parentCode") String parentCode,
			@RequestParam(value = "childValue", required = false) String childValue) {
		return common.getParam(parentCode, childValue);			
	}
	
	@RequestMapping(value = REST.DROPDOWN_CURRENCY, method = RequestMethod.GET)
	public @ResponseBody String getDropdownCurrency() {
		return common.getCurrency();			
	}
	
	@RequestMapping(value = REST.DROPDOWN_DC_NOTE, method = RequestMethod.GET)
	public @ResponseBody String getDropdownDCNote() {
		return common.getDCNotes();			
	}
	
	@RequestMapping(value = REST.DROPDOWN_OFFICE, method = RequestMethod.GET)
	public @ResponseBody String getDropdownOffice() {
		return common.getOffice();		
	}
	
	@RequestMapping(value = REST.DROPDOWN_PAY_MTHD, method = RequestMethod.GET)
	public @ResponseBody String getDropdownPaymentMethod() {
		return common.getPaymentMethod();		
	}
	
	@RequestMapping(value = REST.DROPDOWN_VALUE, method = RequestMethod.GET)
	public @ResponseBody String getDropdownValue() {
		return common.getValue();
	}
	
	@RequestMapping(value = REST.DROPDOWN_FORMAT, method = RequestMethod.GET)
	public @ResponseBody String getDropdownFormat() {
		return common.getFormat();
	}
	
	@RequestMapping(value = REST.DROPDOWN_CLIENT_TYPE, method = RequestMethod.GET)
	public @ResponseBody String getDropdownClientType() {
		return common.getClientType();
	}
	
	//==================== TR0004 (PROJECT LIST) ====================//
	
	@RequestMapping(value = REST.DROPDOWN_PROJECT, method = RequestMethod.GET)
	public @ResponseBody String getDropdownProject() {
		return common.getProject();			
	}
	
	@RequestMapping(value = REST.CLIENT, method = RequestMethod.GET)
	public @ResponseBody String client(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="cliType", required=false) String cliType,
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

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.G_LOOKUP_CLIENT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.COA, method = RequestMethod.GET)
	public @ResponseBody String coa(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="sort", defaultValue="coaCode") String sort,
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

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.ACCT_ENTRY_JOURNAL_COA + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		
		return response.getBody();
	}
	
	@RequestMapping(value = REST.EXCHANGE, method = RequestMethod.GET)
	public @ResponseBody String exchange(
			@RequestParam(value="date") String p_Date,
			@RequestParam(value="curr") String p_CurrId) throws JsonProcessingException {
			
		return common.getExchange(p_Date, p_CurrId);	
	}
	
	@RequestMapping(value = REST.EXCHANGE_NON_EOM, method = RequestMethod.GET)
	public @ResponseBody String exchangeNonEom(
			@RequestParam(value="date") String p_Date,
			@RequestParam(value="curr", required = false) String p_CurrId,
			@RequestParam(value="coaCode", required = false) String p_CoaCode) throws JsonProcessingException {
			
		return common.getExchangeNonEom(p_Date, p_CurrId, p_CoaCode);	
	}
	
	@RequestMapping(value = REST.EXCHANGE_COA, method = RequestMethod.GET)
	public @ResponseBody String exchangeCoa(
			@RequestParam(value="date") String p_Date,
			@RequestParam(value="coaCode") String p_CoaCode) throws JsonProcessingException {
			
		return common.getExchangeCoa(p_Date, p_CoaCode);	
	}
	
	@RequestMapping(value = REST.EXCHANGE_COA_NON_EOM, method = RequestMethod.GET)
	public @ResponseBody String exchangeCoaNonEom(
			@RequestParam(value="date") String p_Date,
			@RequestParam(value="coaCode") String p_CoaCode) throws JsonProcessingException {
			
		return common.getExchangeCoaNonEom(p_Date, p_CoaCode);	
	}
	
	@RequestMapping(value = REST.TAX_RATE, method = RequestMethod.GET)
	public @ResponseBody String taxRate(@RequestParam(value="clientCode") String p_ClientCode) throws JsonProcessingException{
			
		return common.getTaxRate(p_ClientCode);	
	}
	
	@RequestMapping(value = REST.LOGO_COMPANY, method = RequestMethod.GET)
	public @ResponseBody void getLogoCompany(HttpServletResponse response) throws IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);
		
		String uri = sessionComponent.getHost() + URI.G_LOGO_COMPANY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<byte[]> result = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, byte[].class);
		
		response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
	    response.getOutputStream().write(result.getBody());
	    response.getOutputStream().close();
	}
	
}