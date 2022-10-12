package com.biru.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import com.biru.common.entity.DropdownIdText;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface CommonService {

	public String getDropdownStatus();
	
	public String getDropdownStatusClaims();
	
	public String getAppDate();
	
	public String getAppDateDP() throws ParseException;
	
	public String getAppDateNext() throws ParseException;
	
	public String getAppDateNextDP() throws ParseException;
	
	public String getSystemDate();
	
	public String getYearOfAppDate() throws ParseException;
	
	public List<DropdownIdText> getMessageSave() throws ParseException, JsonParseException, JsonMappingException, IOException;
	
	public List<DropdownIdText> getYearCanBeProcess() throws ParseException, JsonParseException, JsonMappingException, IOException;

	public String getLastProMonthAndProYear();
	
	public String getEomDate() throws JsonParseException, JsonMappingException, IOException;
	
	public String getExchange(String p_Date, String p_CurrId) throws JsonProcessingException;
	
	public String getExchangeNonEom(String p_Date, String p_CurrId, String p_CoaCode) throws JsonProcessingException;
	
	public String getExchangeCoa(String p_Date, String p_CoaCode) throws JsonProcessingException;
	
	public String getExchangeCoaNonEom(String p_Date, String p_CoaCode) throws JsonProcessingException;
	
	public String getCurrCoa(String p_CoaCode);
	
	public String getBank();
	
	public String getClient(String p_CliType);
	
	public String getProduct();

	public String getOfficer();
	
	public String getOfficerTypeNot2();
	
	public String getParam(String parentCode, String childValue);
	
	public String getCurrency();
	
	public String getDCNotes();
	
	public String getOffice();
	
	public String getPaymentMethod();
	
	public String getProject();
	
	public String getTypeOfCover();
	
	public String getClasification();
	
	public String getName();
	
	public String getTaxRate(String p_ClientCode);
	
	public String getValue();
	
	public String getParameter(String parentCode, String childCode) throws JsonProcessingException;
	
	public String dropdownTcGroup();
	
	public String dropdownTcDetails(String groupName);
	
	public String dropdownDocumentType(String tcCode);
	
	public String dropdownParticipantStatus();
	
	public String dropdownParticipantCountry();
	
	public String dropdownParticipantIndustry();
	
	public String dropdownTbYear();
	
	public String dropdownCurrTax();
	
	public String lookupClient(Map<String, Object> param) throws JsonProcessingException;
	
	public String tr0006Inquiry(Map<String, Object> param) throws JsonProcessingException;
	
	public String claimsInternalInquiry(Map<String, Object> param) throws JsonProcessingException;
	
	public String claimsInternalInquiry2(Map<String, Object> param) throws JsonProcessingException;
	
	public String claimsInternalInquiry3(Map<String, Object> param) throws JsonProcessingException;

	public String getFormat();
	
	public String getClientType();
	
	public Boolean isAlreadyClosing(String trxId, String trxVoucherId) throws JsonProcessingException;
	
}