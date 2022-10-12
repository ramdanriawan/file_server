package com.biru.web.marketing;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.CLAIMS;
import com.biru.service.ClaimInternalService;
import com.biru.service.ClaimsService;
import com.biru.service.CommonService;

@RestController
@RequestMapping(REST.MARKETING)
public class ClaimsController {

	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ClaimInternalService claimInternalService;
	
	@Autowired
	private ClaimsService claimsService;
	
	@RequestMapping(value = CLAIMS.INQ, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> p_Params) throws ParseException {
		return claimInternalService.claimsInquiry(p_Params);
	}
	
	@RequestMapping(value = CLAIMS.INQ2, method = RequestMethod.POST)
	public Object inquiry2(@RequestBody Map<String, Object> p_Params) throws ParseException {
		return commonService.tr0006Inquiry2(p_Params);
	}
	
	@RequestMapping(value = CLAIMS.INQ3, method = RequestMethod.POST)
	public Object inquiry3(@RequestBody Map<String, Object> p_Params) throws ParseException {
		return claimInternalService.claimsInquiryDetail(p_Params);
	}
	
	@RequestMapping(value = CLAIMS.INQ_DATA, method = RequestMethod.POST)
	public Object inquiryData(@RequestBody Map<String, Object> params) {
		return claimsService.inquiry(params);
	}
	
	@RequestMapping(value = CLAIMS.INQ_DETAIL, method = RequestMethod.POST)
	public Object inquiryDetail(@RequestBody Map<String, Object> params) {
		return claimsService.inquiryDetail(params);
	}
	
	@RequestMapping(value = CLAIMS.INQ_CREATE, method = RequestMethod.POST)
	public Object inquiryCreate(@RequestBody Map<String, Object> params) {
		return claimsService.inquiryCreate(params);
	}
	
	@RequestMapping(value = CLAIMS.INQ_CREATE_DETAIL, method = RequestMethod.POST)
	public Object inquiryCreateDetail(@RequestBody Map<String, Object> params) {
		return claimsService.inquiryCreateDetail(params);
	}
	
	@RequestMapping(value = CLAIMS.GET_UNDERWRITING, method = RequestMethod.POST)
	public Object getUnderwriting(@RequestBody Map<String, Object> params) {
		return claimsService.getUnderwriting(params);
	}
	
	@RequestMapping(value = CLAIMS.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> params) throws ParseException {
		return claimsService.save(params);
	}
	
	@RequestMapping(value = CLAIMS.SETTLEMENT, method = RequestMethod.POST)
	public Object settlement(@RequestBody Map<String, Object> params) throws ParseException {
		return claimsService.settlement(params);
	}
	
	@RequestMapping(value = CLAIMS.DELETE, method = RequestMethod.POST)
	public Object delete(@RequestBody Map<String, Object> params) {
		return claimsService.delete(params);
	}
	
	@RequestMapping(value = CLAIMS.DROPDOWN_YEAR, method = RequestMethod.POST)
	public Object dropdownYear() {
		return claimsService.dropdownYear();
	}
	
	@RequestMapping(value = CLAIMS.EXPORT_CLAIMSLIST_EXCEL, method = RequestMethod.POST)
	public Object exportClaimsListExcel(@RequestBody Map<String, Object> params) throws Exception {
		return claimsService.exportClaimslistExcel(params);
	}
	
	@RequestMapping(value = CLAIMS.GET_TR6J, method = RequestMethod.POST)
	public Object getTr6j(@RequestBody Map<String, Object> params) {
		return claimsService.getValueTR6J(params);
	}
	
	@RequestMapping(value = CLAIMS.PRINT_PLA_DLA, method = RequestMethod.POST)
	public Object printPlaDla(@RequestBody Map<String, Object> params) throws Exception {
		return claimsService.createDlaPlaPdf(params);
	}
	
	@RequestMapping(value = CLAIMS.PRINT_DOC_PLA_DLA, method = RequestMethod.POST)
	public Object printDocPlaDla(@RequestBody Map<String, Object> params) throws Exception {
		return claimsService.createDlaPlaDoc(params);
	}
	
	@RequestMapping(value = CLAIMS.CREATE_DC_NOTES, method = RequestMethod.POST)
	public Object createDCNotes(@RequestBody Map<String, Object> params) throws Exception {
		return claimsService.DCNotes(params);
	}
	
	@RequestMapping(value = CLAIMS.GET_LIST_INSURANCE, method = RequestMethod.POST)
	public Object getListInsurance(@RequestBody Map<String, Object> params) throws Exception {
		return claimsService.getListInsurance(params);
	}
	
	@RequestMapping(value = CLAIMS.GET_LIST_TR15A, method = RequestMethod.POST)
	public Object getListTR15A(@RequestBody Map<String, Object> params) {
		return claimsService.getListTR15A(params);
	}
	
	@RequestMapping(value = CLAIMS.GET_FINANCE, method = RequestMethod.POST)
	public Object getFinance(@RequestBody Map<String, Object> params) {
		return claimsService.getFinance(params);
	}
	
	@RequestMapping(value = CLAIMS.PROCESS_FINANCE, method = RequestMethod.POST)
	public Object processFinance(@RequestBody Map<String, Object> params) {
		return claimsService.processFinance(params);
	}
	
	@RequestMapping(value = CLAIMS.GET_LIST_INTERNALMEMO, method = RequestMethod.POST)
	public Object getListInternalMemo(@RequestBody Map<String, Object> params) {
		return claimsService.getListInternalMemo(params);
	}
	
	@RequestMapping(value = CLAIMS.CREATE_INTERNALMEMO, method = RequestMethod.POST)
	public Object createInternalMemo(@RequestBody Map<String, Object> params) throws Exception {
		return claimsService.createInternalMemo(params);
	}
	
	@RequestMapping(value = CLAIMS.CREATE_INTERNALMEMO_EXCEL, method = RequestMethod.POST)
	public Object createInternalMemoExcel(@RequestBody Map<String, Object> params) throws Exception {
		return claimsService.createInternalMemoExcel(params);
	}
	
}
