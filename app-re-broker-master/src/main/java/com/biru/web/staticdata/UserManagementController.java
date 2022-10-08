package com.biru.web.staticdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.GROUPMANAGEMENT;
import com.biru.ReBrokerConstants.REST.USERMANAGEMENT;
import com.biru.common.entity.DatatableSet;
import com.biru.service.UserManagementService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class UserManagementController {

	@Autowired
	private UserManagementService userManagementService;
	
	
	//Group Management 
	
	
	
	@RequestMapping(value = GROUPMANAGEMENT.SAVE, method = RequestMethod.POST)
	public Object save(@RequestBody Map<String, Object> p_Param){
		try {
			return userManagementService.save(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	@RequestMapping(value = GROUPMANAGEMENT.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param){
		return userManagementService.inquiry(p_Param);
	}

	@RequestMapping(value = GROUPMANAGEMENT.INQUIRY_CHILD, method = RequestMethod.POST)
	public DatatableSet inquiryChild(@RequestBody Map<String, Object> p_Param){
		return userManagementService.inquiryChild(p_Param);
	}
	
	@RequestMapping(value = GROUPMANAGEMENT.DROPDOWN, method = RequestMethod.POST)
	public Object dropDown(){
		return userManagementService.getDropDown();
	}
	
	@RequestMapping(value = GROUPMANAGEMENT.DELETE, method = RequestMethod.POST)
	public Object delete(@RequestBody Map<String, Object> p_Param){
		return userManagementService.delete(p_Param);
	}
	
	
	
	
	
	// User Management
	
	
	
	@RequestMapping(value = USERMANAGEMENT.SAVE, method = RequestMethod.POST)
	public Object userSave(@RequestBody Map<String, Object> p_Param){
		try {
			return userManagementService.userSave(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}
	
	@RequestMapping(value = USERMANAGEMENT.INQUIRY, method = RequestMethod.POST)
	public DatatableSet userInquiry(@RequestBody Map<String, Object> p_Param){
		return userManagementService.userInquiry(p_Param);
	}

	@RequestMapping(value = USERMANAGEMENT.INQUIRY_CHILD, method = RequestMethod.POST)
	public DatatableSet userInquiryChild(@RequestBody Map<String, Object> p_Param){
		return userManagementService.inquiryChild(p_Param);
	}
	
	@RequestMapping(value = USERMANAGEMENT.DROPDOWN, method = RequestMethod.POST)
	public Object groupDropDown(){
		return userManagementService.getGroupDropDown();
	}
	
	@RequestMapping(value = USERMANAGEMENT.USERLEVEL_DROPDOWN, method = RequestMethod.POST)
	public Object userLevelDropDown(){
		return userManagementService.getUserLevelDropDown();
	}
	@RequestMapping(value = USERMANAGEMENT.MPL_DROPDOWN, method = RequestMethod.POST)
	public Object mplDropDown(){
		return userManagementService.getMplDropDown();
	}
	@RequestMapping(value = USERMANAGEMENT.STDATA_DROPDOWN, method = RequestMethod.POST)
	public Object stdataDropDown(){
		return userManagementService.getStdataDropDown();
	}
	@RequestMapping(value = USERMANAGEMENT.SA_DROPDOWN, method = RequestMethod.POST)
	public Object saDropDown(){
		return userManagementService.getSaDropDown();
	}
}

