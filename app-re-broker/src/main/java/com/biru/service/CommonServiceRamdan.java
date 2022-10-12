package com.biru.service;

import com.biru.common.entity.DropdownIdText;
import com.biru.view.ViewInqTr0006Entity;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CommonServiceRamdan {

	public Date getAppDate() throws ParseException;
	
	public String getAppDateStr() throws ParseException;
	
	public String getNextBusinessDayStr() throws ParseException;
	
	public List<DropdownIdText> getDropdownStatusSD();
	
	public List<DropdownIdText> getDropdownStatusClaims();
	
	public List<DropdownIdText> getMessageSave();
	
	public List<DropdownIdText> getYearCanBeProcess();
	
	public Map<String, String> getLastProMonthAndProYear();
	
	//==================== MA0004 (CHART OF ACCOUNT) ====================//
	
	public String getCurrCoa(String p_CoaCode);
	
	public List<DropdownIdText> getDropdownBank();
	
	//==================== MA0005 (CLIENT) ====================//
	
	public Object lookupClient(Map<String, Object> p_Params);
	
	public List<DropdownIdText> getDropdownClient(String p_CliType);
	
	//==================== MA0011 (PRODUCT) ====================//
	
	public List<DropdownIdText> getDropdownProduct();
	
	//==================== MA0012 (SALES/OFFICER) ====================//
	
	public List<DropdownIdText> getDropdownOfficer();
	
	public List<DropdownIdText> getDropdownOfficerTypeNot2();
	
	//==================== MA0014 (PARAMETER CHILD) ====================//
	
	public List<DropdownIdText> getDropdownParam(Map<String, Object> params);
	
	public List<DropdownIdText> getDropdownCurrency();
	
	public List<DropdownIdText> getDropdownDCNote();
	
	public List<DropdownIdText> getDropdownOffice();
	
	public List<DropdownIdText> getDropdownPaymentMethod();
	
	public List<DropdownIdText> getDropdownValue();
	
	public List<DropdownIdText> getDropdownFormat();
	
	public List<DropdownIdText> getDropdownClientType();
	
	public String taxRate(String p_ClientCode);
	
	public String getParameter(Map<String, String> p_Param);
	
	//==================== MA0015 (EXCHANGE RATE) ====================//
	
	public Object exchange(Map<String, Object> p_Params);
	
	//==================== MA0015A (EXCHANGE RATE NON EOM) ====================//
	
	public Object exchangeNonEom(Map<String, Object> p_Params);
	
	//==================== TR0004 (PROJECT LIST) ====================//
	
	public List<DropdownIdText> getDropdownProject();
	
	public List<DropdownIdText> getDropdownTypeOfCover();
	
	public List<DropdownIdText> getDropdownClasification();
	
	public List<DropdownIdText> getDropdownName();
	
	public List<DropdownIdText> dropdownTcGroup();
	
	public List<DropdownIdText> dropdownTcDetails(String groupName);
	
	public List<DropdownIdText> dropdownDocumentType(String tcCode);
	
	public Object getLogoCompany();
	
	public List<DropdownIdText> dropdownParticipantStatus();
	
	public List<DropdownIdText> dropdownParticipantCountry();
	
	public List<DropdownIdText> dropdownParticipantIndustry();
	
	public List<DropdownIdText> dropdownTbYear();
	
	public List<DropdownIdText> dropdownCurrTax();
  
	public List<DropdownIdText> dropdownMenu();
	
	public Object tr0006Inquiry(Map<String, Object> param);

	public Object tr0006InquiryVir(Map<String, Object> param);
	
	public Object tr0006Inquiry2(Map<String, Object> param);
	
	public BigDecimal getTaxRateh003();
	
	public BigDecimal getTaxRateh009();
	
	public BigDecimal getTaxRateh011();
	
	public BigDecimal getTaxRateh012();
	  
	public Boolean isAlreadyClosing(String trxId, String trxVoucherId);

	List<ViewInqTr0006Entity> tr0006GetVoucherId();
}
