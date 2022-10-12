package com.biru.web.marketing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.biru.GuiConstants.HTML;
import com.biru.GuiConstants.REST;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Controller
@RequestMapping(REST.MARKETING)
public class ClaimsInternalController {

	@Autowired
	private CommonService common;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SessionComponent sessionComponent;
	
	@RequestMapping(value = REST.M_CLM_INTERNAL, method = RequestMethod.GET)
	public String claimInternal(Model model) throws Exception {
		model.addAttribute("mainMenu", "Claim Internal  /  claim internal");
		model.addAttribute("titlePage", "Claim Internal");
		model.addAttribute("titlePageCreate", "Claim Internal | Create");
		model.addAttribute("appDate", common.getAppDate().replaceAll("-", "/"));
		model.addAttribute("minDate", common.getEomDate().replaceAll("-", "/"));
		model.addAttribute("urlClient", "claim/client");
		model.addAttribute("urlClient2", "claim/client2");
		model.addAttribute("urlSearch", "claim/inquiry");
		model.addAttribute("urlSearch2", "claim/inquiry2");
		model.addAttribute("urlTypeOfCover", "claim/get-dropdownTypeOfCover");
		model.addAttribute("urlStatus", "claim/get-dropdownStatus");
		model.addAttribute("urlTableDetail", "claim/inquiry-table-detail");
		
		List<DropdownIdText> message = common.getMessageSave();
		for(DropdownIdText idText : message) {
			model.addAttribute("M_" + idText.getId(), idText.getText());
		}
		
		return HTML.M_CLAIM_INTERNAL;
	}
	@RequestMapping(value = REST.M_CLAIM_DROPDOWN_TYPE_OF_COVER, method = RequestMethod.GET)
	public @ResponseBody String getTypeOfCover() {
		return common.getTypeOfCover();
	}
	@RequestMapping(value = REST.M_CLAIM_STATUS, method = RequestMethod.GET)
	public @ResponseBody String getStatus() {
		return common.getDropdownStatusClaims();
	}
	
	@RequestMapping(value = REST.M_CLAIM_INQUIRY, method = RequestMethod.GET)
	public @ResponseBody String inquiry(
			@RequestParam(value="typeOfCover") String typeOfCover,
			@RequestParam(value="client") String client,
			@RequestParam(value="fromDate") String fromDate,
			@RequestParam(value="to") String to,
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeOfCover", typeOfCover);
		param.put("client", client);
		param.put("fromDate", fromDate);
		param.put("to", to);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		System.out.println(param);
		return common.claimsInternalInquiry(param);
	}
	
	@RequestMapping(value = REST.M_CLAIM_INQUIRY2, method = RequestMethod.GET)
	public @ResponseBody String inquiry2(
			@RequestParam(value="typeOfCover") String typeOfCover,
			@RequestParam(value="client") String client,
			@RequestParam(value="sort", defaultValue="idKey") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("typeOfCover", typeOfCover);
		param.put("client", client);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		System.out.println(param);
		return common.claimsInternalInquiry2(param);
	}
	
	@RequestMapping(value = REST.M_CLAIM_INQUIRY_TABLE_DETAIL, method = RequestMethod.GET)
	public @ResponseBody String inquiryTableDetail(
			@RequestParam(value="trxTrxId") String trxTrxId,
			@RequestParam(value="trxVoucherId") String trxVoucherId,
			@RequestParam(value="sort", defaultValue="trxBrdxId") String sort,
			@RequestParam(value="order", defaultValue="asc") String order,
			@RequestParam(value="offset", defaultValue="0") Integer offset,
			@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("trxTrxId", trxTrxId);
		param.put("trxVoucherId", trxVoucherId);
		param.put(Param.SORT, sort);
		param.put(Param.ORDER, order);
		param.put(Param.OFFSET, offset);
		param.put(Param.LIMIT, limit);

		System.out.println(param);
		return common.claimsInternalInquiry3(param);
	}
	
	@RequestMapping(value = REST.M_CLAIM_CLIENT, method = RequestMethod.GET)
	public @ResponseBody String client(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="client", required=false) String cliType,
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

		return common.lookupClient(param);
	}
	
	@RequestMapping(value = REST.M_CLAIM_CLIENT2, method = RequestMethod.GET)
	public @ResponseBody String client2(
			@RequestParam(value="filterKey", required=false) String filterKey,
			@RequestParam(value="filterValue", required=false) String filterValue,
			@RequestParam(value="client", required=false) String cliType,
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

		return common.lookupClient(param);
	}
}
