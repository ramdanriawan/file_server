package com.biru.web.staticdata;

import java.text.ParseException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.HOLIDAY;
import com.biru.common.entity.DatatableSet;
import com.biru.service.HolidayService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class HolidayController {
	
	@Autowired
	private HolidayService holidayService;
	
	/* 
	 * Check date is exsist
	 * param dd-MM-yyyy
	 * */
	@RequestMapping(value = HOLIDAY.IS_EXSIST, method = RequestMethod.POST)
	public Boolean isDateExsist(@RequestBody String p_Date) throws ParseException {
		return holidayService.isExsist(p_Date);
	}
	
	/* 
	 * Inquiry holiday
	 * */
	@RequestMapping(value = HOLIDAY.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) {
		return holidayService.inquiry(p_Param);
	}
	
	/* 
	 * Create and update holiday
	 * */
	@RequestMapping(value = HOLIDAY.SAVE, method = RequestMethod.POST)
	public Boolean save(@RequestBody Map<String, Object> p_Param) throws ParseException {
		try {
			return holidayService.save(p_Param);
		}catch(Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}

}
