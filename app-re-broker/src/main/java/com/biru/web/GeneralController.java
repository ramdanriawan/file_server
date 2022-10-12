package com.biru.web;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.GENERAL;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.component.EmailUtils;
import com.biru.service.CommonService;

@RestController
@RequestMapping(REST.GENERAL)
public class GeneralController {
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private EmailUtils emailUtils;
	
	private Logger logger = LoggerFactory.getLogger(GeneralController.class);
	
	/* 
	 * Application date 
	 * */
	@RequestMapping(value = GENERAL.APP_DATE, method = RequestMethod.POST)
	public String getAppDate() throws ParseException {
		return commonService.getAppDateStr();
	}
	
	/* 
	 * Application date + 1 (next business day) 
	 * */
	@RequestMapping(value = GENERAL.APP_DATE_NEXT, method = RequestMethod.POST)
	public String getAppDateNext() throws ParseException {
		return commonService.getNextBusinessDayStr();
	}
	
	/* 
	 * Status static data 
	 * Active & Inactive
	 * */
	@RequestMapping(value = GENERAL.DROPDOWN_STATUS, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownStatusSD() throws ParseException {
		return commonService.getDropdownStatusSD();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_STATUS_CLAIMS, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownStatusClaims() throws ParseException {
		return commonService.getDropdownStatusClaims();
	}
	
	/* 
	 * Message save from table MA0016
	 * 0001, 0005, 0006, 0008, 0036
	 * */
	@RequestMapping(value = GENERAL.MESSAGE_SAVE, method = RequestMethod.POST)
	public List<DropdownIdText> getMessageSave() throws ParseException {
		return commonService.getMessageSave();
	}

	@RequestMapping(value = GENERAL.YEAR, method = RequestMethod.POST)
	public List<DropdownIdText> getYear() {
		return commonService.getYearCanBeProcess();
	}
	
	@RequestMapping(value = GENERAL.LAST_PRO_MONTH_YEAR, method = RequestMethod.POST)
	public Object getLastYearMonth() {
		return commonService.getLastProMonthAndProYear();
	}
	
	//==================== MA0004 (CHART OF ACCOUNT) ====================//
	
	@RequestMapping(value = GENERAL.GET_CURR_COA, method = RequestMethod.POST)
	public String getCurrCoa(@RequestBody String p_CoaCode) {
		return commonService.getCurrCoa(p_CoaCode);
	}
	
	@RequestMapping(value = GENERAL.LOOKUP_CLIENT, method = RequestMethod.POST)
	public Object lookupClient(@RequestBody Map<String, Object> p_Params) {
		return commonService.lookupClient(p_Params);
	}
	
	//==================== MA0005 (CLIENT) ====================//
	
	@RequestMapping(value = GENERAL.DROPDOWN_BANK, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownBank() {
		return commonService.getDropdownBank();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_CLIENT, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownClient(@RequestParam("cliType") String p_CliType) {
		return commonService.getDropdownClient(p_CliType);
	}
	
	//==================== MA0010 (COMPANY) ====================//
	
	@RequestMapping(value = GENERAL.LOGO_COMPANY, method = RequestMethod.POST)
	public Object getLogoCompany(HttpServletResponse response) throws IOException {
		return commonService.getLogoCompany();
	}
	
	//==================== MA0011 (PRODUCT) ====================//
	
	@RequestMapping(value = GENERAL.DROPDOWN_PRODUCT, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownProduct() {
		return commonService.getDropdownProduct();
	}
	
	//==================== MA0012 (SALES/OFFICER) ====================//
	
	@RequestMapping(value = GENERAL.DROPDOWN_OFFICER, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownOfficer() {
		return commonService.getDropdownOfficer();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_OFFICER_NOT_2, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownOfficerTypeNot2() {
		return commonService.getDropdownOfficerTypeNot2();
	}
	
	//==================== MA0014 (PARAMETER CHILD) ====================//
	
	@RequestMapping(value = GENERAL.DROPDOWN_PARAM, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownParam(@RequestBody Map<String, Object> params) {
		return commonService.getDropdownParam(params);
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_CURRENCY, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownCurrency() {
		return commonService.getDropdownCurrency();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_DC_NOTE, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownDCNote() {
		return commonService.getDropdownDCNote();
	}

	@RequestMapping(value = GENERAL.DROPDOWN_OFFICE, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownOffice() {
		return commonService.getDropdownOffice();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_PAY_MTHD, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownPaymentMethod() {
		return commonService.getDropdownPaymentMethod();
	}
	
	@RequestMapping(value = GENERAL.FIND_TAX_RATE, method = RequestMethod.POST)
	public String findTaxRate(@RequestBody String p_ClientCode) {
		return commonService.taxRate(p_ClientCode);
	}

	@RequestMapping(value = GENERAL.DROPDOWN_VALUE, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownValue() {
		return commonService.getDropdownValue();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_FORMAT, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownFormat() {
		return commonService.getDropdownFormat();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_CLIENT_TYPE, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownClientType() {
		return commonService.getDropdownClientType();
	}
	
	@RequestMapping(value = GENERAL.GET_PARAMETER, method = RequestMethod.POST)
	public String getParameter(@RequestBody Map<String, String> p_Param) {
		return commonService.getParameter(p_Param);
	}
	
	//==================== MA0015 (EXCHANGE RATE) ====================//
	
	@RequestMapping(value = GENERAL.FIND_EXCHANGE, method = RequestMethod.POST)
	public Object findExchange(@RequestBody Map<String, Object> p_Params) {
		return commonService.exchange(p_Params);
	}
	
	//==================== MA0015 (EXCHANGE RATE NON EOM) ====================//
	
	@RequestMapping(value = GENERAL.FIND_EXCHANGE_NON_EOM, method = RequestMethod.POST)
	public Object findExchangeNonEom(@RequestBody Map<String, Object> p_Params) {
		return commonService.exchangeNonEom(p_Params);
	}
	
	//==================== TR0004 (PROJECT LIST) ====================//
	
	@RequestMapping(value = GENERAL.DROPDOWN_PROJECT, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownProject() {
		return commonService.getDropdownProject();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_TYPE_OF_COVER, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownTypeOfCover() {
		return commonService.getDropdownTypeOfCover();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_CLASIFICATION, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownClasification() {
		return commonService.getDropdownClasification();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_NAME, method = RequestMethod.POST)
	public List<DropdownIdText> getDropdownName() {
		return commonService.getDropdownName();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_TC_GROUP, method = RequestMethod.POST)
	public List<DropdownIdText> dropdownTcGroup() {
		return commonService.dropdownTcGroup();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_TC_DETAILS, method = RequestMethod.POST)
	public List<DropdownIdText> dropdownTcDetails(@RequestBody String groupName) {
		return commonService.dropdownTcDetails(groupName);
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_DOCUMENT_TYPE, method = RequestMethod.POST)
	public List<DropdownIdText> dropdownDocumentType(@RequestBody(required = false) String tcCode) {
		return commonService.dropdownDocumentType(tcCode);
	}

	@RequestMapping(value = GENERAL.DROPDOWN_PARTICIPANT_STATUS, method = RequestMethod.POST)
	public List<DropdownIdText> dropdownParticipantStatus() {
		return commonService.dropdownParticipantStatus();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_PARTICIPANT_COUNTRY, method = RequestMethod.POST)
	public List<DropdownIdText> dropdownParticipantCountry() {
		return commonService.dropdownParticipantCountry();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_PARTICIPANT_INDUSTRY, method = RequestMethod.POST)
	public List<DropdownIdText> dropdownParticipantIndustry() {
		return commonService.dropdownParticipantIndustry();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_TB_YEAR, method = RequestMethod.POST)
	public List<DropdownIdText> dropdownTbYear() {
		return commonService.dropdownTbYear();
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_CURR_TAX, method = RequestMethod.POST)
	public List<DropdownIdText> dropdownCurrTax() {
		return commonService.dropdownCurrTax();
	}
	
	@RequestMapping(value = GENERAL.TR0006_INQUIRY, method = RequestMethod.POST)
	public Object tr0006Inquiry(@RequestBody Map<String, Object> param) {
		return commonService.tr0006Inquiry(param);
	}
	
	@RequestMapping(value = GENERAL.DROPDOWN_MENU, method = RequestMethod.GET)
	public List<DropdownIdText> dropdownMenu() {
		return commonService.dropdownMenu();
	}
	
	@RequestMapping(value = GENERAL.IS_ALREADY_CLOSING, method = RequestMethod.POST)
	public Boolean isAlreadyClosing(@RequestBody Map<String, Object> param) {
		return commonService.isAlreadyClosing(
				Param.getStrWithDef(param, "trxId"), Param.getStrWithDef(param, "trxVoucherId"));
	}
	
	@RequestMapping(value = "/test-email", method = RequestMethod.GET)
	public Object testEmail(@RequestParam(name = "email", required = false, defaultValue = "adm.rebroker@gmail.com") String email) 
			throws Exception {
		logger.info("Test email with param : {}.", email);
		JavaMailSender sender = emailUtils.getMailSender();
		
		MimeMessage msg = sender.createMimeMessage();
        MimeMessageHelper helper = emailUtils.getMimeMessageHelper(msg);
		
        helper.setTo(email);
        helper.setSubject("Testing Email");
        helper.setText("Testing", true);

        sender.send(msg);
		
		return "FINISH";
	}
	
}
