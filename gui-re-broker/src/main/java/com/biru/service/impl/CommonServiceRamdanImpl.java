package com.biru.service.impl;

import com.biru.GuiConstants.KEY;
import com.biru.GuiConstantsRamdan.URI;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.biru.service.CommonServiceRamdan;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CommonServiceRamdanImpl implements CommonServiceRamdan {

	@Value("${app.host}")
	private String host;

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private SessionComponent sessionComponent;

	private SimpleDateFormat formatYear;
	private SimpleDateFormat formatDate;
	private SimpleDateFormat formatDatePicker;

	public CommonServiceRamdanImpl() {
		this.formatYear = new SimpleDateFormat("yyyy");
		this.formatDate = new SimpleDateFormat("dd-MM-yyyy");
		this.formatDatePicker = new SimpleDateFormat("yyyy-MM-dd");
	}

	/*
	 * get status 11 = Active 12 = Inactive
	 */
	@Override
	public String getDropdownStatus() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_STATUS + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getDropdownStatusClaims() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_STATUS_CLAIMS + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	/*
	 * get application date format dd-MM-yyyy
	 */
	@Override
	public String getAppDate() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_APPLICATION_DATE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getAppDateDP() throws ParseException {
		String dateStr = getAppDate();
		Date date = formatDate.parse(dateStr);

		return formatDatePicker.format(date);
	}

	/*
	 * get application date + 1 (next business day) format dd-MM-yyyy
	 */
	@Override
	public String getAppDateNext() throws ParseException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_APPLICATION_DATE_NEXT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getAppDateNextDP() throws ParseException {
		String dateStr = getAppDateNext();
		Date date = formatDate.parse(dateStr);

		return formatDatePicker.format(date);
	}

	@Override
	public String getSystemDate() {
		Calendar cal = Calendar.getInstance();

		return formatDate.format(cal.getTime());
	}

	/*
	 * get year of application date format yyyy
	 */
	@Override
	public String getYearOfAppDate() throws ParseException {
		String dateStr = getAppDate();
		Date date = formatDate.parse(dateStr);

		return formatYear.format(date);
	}

	@Override
	public List<DropdownIdText> getMessageSave()
			throws ParseException, JsonParseException, JsonMappingException, IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_MESSAGE_SAVE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		ObjectMapper mapper = JsonParser.getParser();
		List<DropdownIdText> message = mapper.readValue(response.getBody(),
				mapper.getTypeFactory().constructCollectionType(List.class, DropdownIdText.class));

		return message;
	}

	@Override
	public List<DropdownIdText> getVoucherId()
			throws ParseException, JsonParseException, JsonMappingException, IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_VOUCHER_ID + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		ObjectMapper mapper = JsonParser.getParser();

		System.out.println(response.getBody());

		List<DropdownIdText> message = mapper.readValue(response.getBody(),
				mapper.getTypeFactory().constructCollectionType(List.class, DropdownIdText.class));

		return message;
	}

	@Override
	public List<DropdownIdText> getYearCanBeProcess()
			throws ParseException, JsonParseException, JsonMappingException, IOException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_YEAR + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		ObjectMapper mapper = JsonParser.getParser();
		List<DropdownIdText> message = mapper.readValue(response.getBody(),
				mapper.getTypeFactory().constructCollectionType(List.class, DropdownIdText.class));

		return message;
	}

	@Override
	public String getLastProMonthAndProYear() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_LAST_PRO_MONTH_YEAR + "?tenantId=" + sessionComponent.getTenantId();
		return restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody();
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getEomDate() throws JsonParseException, JsonMappingException, IOException {
		Calendar cal = Calendar.getInstance();
		ObjectMapper mapper = JsonParser.getParser();

		Map<String, String> lastEom = mapper.readValue(getLastProMonthAndProYear(), Map.class);

		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, Integer.valueOf(lastEom.get("proMonth")) - 1);
		cal.set(Calendar.YEAR, Integer.valueOf(lastEom.get("proYear")));

		cal.add(Calendar.MONTH, 1);

		return formatDate.format(cal.getTime());
	}

	@Override
	public String getExchange(String p_Date, String p_CurrId) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.DATE, p_Date);
		param.put(Param.CURR, p_CurrId);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_FIND_EXCHANGE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getExchangeNonEom(String p_Date, String p_CurrId, String p_CoaCode) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.DATE, p_Date);
		param.put(Param.CURR, p_CurrId);
		param.put("coaCode", p_CoaCode);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_FIND_EXCHANGE_NON_EOM + "?tenantId="
				+ sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getExchangeCoa(String p_Date, String p_CoaCode) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.DATE, p_Date);
		param.put("coaCode", p_CoaCode);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_FIND_EXCHANGE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getExchangeCoaNonEom(String p_Date, String p_CoaCode) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put(Param.DATE, p_Date);
		param.put("coaCode", p_CoaCode);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_FIND_EXCHANGE_NON_EOM + "?tenantId="
				+ sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	// ==================== MA0004 (CHART OF ACCOUNT) ====================//

	@Override
	public String getCurrCoa(String p_CoaCode) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(p_CoaCode, httpHeaders);

		String uri = host + URI.G_CURR_COA + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getBank() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_BANK + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	// ==================== MA0005 (CLIENT) ====================//

	@Override
	public String getClient(String p_CliType) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_CLIENT + "?tenantId=" + sessionComponent.getTenantId();
		uri = uri + "&cliType=" + p_CliType;
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	// ==================== MA0011 (PRODUCT) ====================//

	@Override
	public String getProduct() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_PRODUCT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	// ==================== MA0012 (SALES/OFFICER) ====================//

	@Override
	public String getOfficer() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_OFFICER + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getOfficerTypeNot2() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_OFFICER_NOT_2 + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	// ==================== MA0014 (PARAMETER CHILD) ====================//
	
	@Override
	public String getParam(String parentCode, String childValue) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("parentCode", parentCode);
		param.put("childValue", childValue);
		
		HttpEntity<Map<String, Object>> httpEntity = new HttpEntity<Map<String, Object>>(param, httpHeaders);

		String uri = host + URI.G_DROPDOWN_PARAM + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getCurrency() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_CURRENCY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getDCNotes() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_DC_NOTE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getPaymentMethod() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_PAY_MTHD + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getOffice() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_OFFICE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getValue() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_VALUE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getFormat() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_FORMAT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}
	
	@Override
	public String getClientType() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_CLIENT_TYPE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	// ==================== TR0004 (PROJECT LIST) ====================//

	@Override
	public String getProject() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_PROJECT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String getTypeOfCover() {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_TYPE_OF_COVER + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String getClasification() {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_CLASIFICATION + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_NAME + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String getTaxRate(String p_ClientCode) {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(p_ClientCode, httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_FIND_TAX_RATE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String dropdownTcGroup() {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(httpHeaders);

		String uri = host + URI.G_DROPDOWN_TC_GROUP + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String dropdownTcDetails(String groupName) {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(groupName, httpHeaders);

		String uri = host + URI.G_DROPDOWN_TC_DETAILS + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String dropdownDocumentType(String groupName) {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(groupName, httpHeaders);

		String uri = host + URI.G_DROPDOWN_DOCUMENT_TYPE + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String dropdownParticipantStatus() {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);

		String uri = host + URI.G_DROPDOWN_PARTICIPANT_STATUS + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String dropdownParticipantCountry() {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);

		String uri = host + URI.G_DROPDOWN_PARTICIPANT_COUNTRY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String dropdownParticipantIndustry() {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);

		String uri = host + URI.G_DROPDOWN_PARTICIPANT_INDUSTRY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String dropdownTbYear() {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);

		String uri = host + URI.G_DROPDOWN_TB_YEAR + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}

	@Override
	public String dropdownCurrTax() {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);

		String uri = host + URI.G_DROPDOWN_CURR_TAX + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
		return response.getBody();
	}
	
	@Override
	public String lookupClient(Map<String, Object> param) throws JsonProcessingException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_LOOKUP_CLIENT + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String tr0006Inquiry(Map<String, Object> param) throws JsonProcessingException {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_TR0006_INQUIRY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String tr0006InquiryVir(Map<String, Object> param) throws JsonProcessingException {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_TR0006_INQUIRY_VIR + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}

	@Override
	public String claimsInternalInquiry(Map<String, Object> param) throws JsonProcessingException {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_CLAIMS_INQUIRY + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}
	
	@Override
	public String claimsInternalInquiry2(Map<String, Object> param) throws JsonProcessingException {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_CLAIMS_INQUIRY2 + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}
	
	@Override
	public String claimsInternalInquiry3(Map<String, Object> param) throws JsonProcessingException {
		// TODO Auto-generated method stub
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param),
				httpHeaders);

		String uri = sessionComponent.getHost() + URI.G_CLAIMS_INQUIRY3 + "?tenantId=" + sessionComponent.getTenantId();
		ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

		return response.getBody();
	}
	
	@Override
	public String getParameter(String parentCode, String childCode) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put(KEY.PARENT_CODE, parentCode);
		param.put(KEY.CHILD_CODE, childCode);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.G_GET_PARAMETER + "?tenantId=" + sessionComponent.getTenantId();
		String response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class).getBody();
		
		return response;
	}

	@Override
	public Boolean isAlreadyClosing(String trxId, String trxVoucherId) throws JsonProcessingException {
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("trxId", trxId);
		param.put("trxVoucherId", trxVoucherId);

		HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
			
		String uri = sessionComponent.getHost() + URI.G_IS_ALREADY_CLOSING + "?tenantId=" + sessionComponent.getTenantId();
		Boolean response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, Boolean.class).getBody();
		
		return response;
	}
	
}
