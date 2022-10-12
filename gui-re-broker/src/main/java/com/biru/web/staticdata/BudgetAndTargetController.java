package com.biru.web.staticdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
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
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Controller
@RequestMapping(REST.STATIC_DATA)
public class BudgetAndTargetController {
		
		@Value("${app.host}")
		private String host;
	
		@Autowired
		private RestTemplate restTemplate;
		
		@Autowired
		private CommonService common;
		
		@Autowired
		private SessionComponent sessionComponent;
		
		private static final String FILE_NAME_BUDGET_AND_TARGET 	= "BudgetAndTarget.xls";
		private static final String SHEET_NAME_BUDGET_AND_TARGET 	= "Budget And Target";

		/* 
		 * page budget and target
		 * */
		@RequestMapping(value = REST.SD_BUDGET_AND_TARGET, method = RequestMethod.GET)
		public String holiday(Model model) throws ParseException, JsonParseException, 
				JsonMappingException, IOException {
			model.addAttribute("mainMenu", "Static Data  /  Budget And Target");
			model.addAttribute("titlePage", "BUDGET AND TARGET");
			model.addAttribute("titlePageCreate", "BUDGET AND TARGET | NEW");
			model.addAttribute("titlePageEdit", "BUDGET AND TARGET | EDIT");
			model.addAttribute("year", common.getYearOfAppDate());
			
			List<DropdownIdText> message = common.getMessageSave();
			for(DropdownIdText idText : message) {
				model.addAttribute("M_" + idText.getId(), idText.getText());
			}
			
			return HTML.SD_BUDGET_AND_TARGET;
		}
		
		/*
		 *  dropdown by tbYear
		 */
		@RequestMapping(value = REST.SD_BUDGET_AND_TARGET_DROPDOWN_TB_YEAR, method = RequestMethod.GET)
		public @ResponseBody Object tbYear() {
			return common.dropdownTbYear();
		}
		
		@RequestMapping(value = REST.SD_BUDGET_AND_TARGET_DROPDOWN_OFFICER, method = RequestMethod.GET)
		public @ResponseBody Object officer() {
			return common.getOfficerTypeNot2();
		}
		
		@RequestMapping(value = REST.SD_BUDGET_AND_TARGET_COA, method = RequestMethod.GET)
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
				
			String uri = sessionComponent.getHost() + URI.SD_BUDGET_AND_TARGET_COA + "?tenantId=" + sessionComponent.getTenantId();
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
			
			return response.getBody();
		}
		
		
		/*
		 * get inquiry data table set
		 */
		@RequestMapping(value = REST.SD_BUDGET_AND_TARGET_INQ, method = RequestMethod.GET)
		public @ResponseBody Object inquiry (
				@RequestParam(value="tbYear", defaultValue="") String tbYear,
				@RequestParam(value="sort", defaultValue="idKey") String sort,
				@RequestParam(value="order", defaultValue="asc") String order,
				@RequestParam(value="offset", defaultValue="0") Integer offset,
				@RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tbYear", tbYear);

			param.put(Param.SORT, sort);
			param.put(Param.ORDER, order);
			param.put(Param.OFFSET, offset);
			param.put(Param.LIMIT, limit);

			HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
				
			String uri = sessionComponent.getHost() + URI.SD_BUDGET_AND_TARGET_INQ + "?tenantId=" + sessionComponent.getTenantId();
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
			
			return response.getBody();
		}
		
		@RequestMapping(value = REST.SD_BUDGET_AND_TARGET_SAVE, method = RequestMethod.POST)
		public @ResponseBody String save(@RequestBody HashMap<String, Object> p_Param) throws JsonProcessingException {
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);

			p_Param.put(Param.USER, sessionComponent.getUserLogin());

			HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(p_Param), httpHeaders);

			String uri = sessionComponent.getHost() + URI.SD_BUDGET_AND_TARGET_SAVE + "?tenantId=" + sessionComponent.getTenantId();
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

			return response.getBody();	
		}
		
		
		/* 
		 * get export data excel budget and target
		 * */
		@RequestMapping(value = REST.SD_BUDGET_AND_TARGET_EXPORT, method = RequestMethod.GET, produces="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
		public @ResponseBody Object export(@RequestParam(value="tbYear") String tbYear) throws JsonProcessingException, FileNotFoundException {
			
			HttpHeaders httpHeaders = new HttpHeaders();
			httpHeaders.setContentType(MediaType.APPLICATION_JSON);
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("tbYear", tbYear);

			HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
				
			String uri = host + URI.SD_BUDGET_AND_TARGET_EXPORT + "?tenantId=" + sessionComponent.getTenantId();
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
			
			File file = new File(response.getBody());
		    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

		    return ResponseEntity.ok()
		    		.header("Content-Disposition", "attachment; filename=Budget-and-Targer"+tbYear+".xls")
		    		.contentLength(file.length())
		    		.body(resource);
		}

	}
