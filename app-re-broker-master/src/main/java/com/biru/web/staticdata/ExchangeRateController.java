package com.biru.web.staticdata;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.EXCHANGE_RATE;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.entity.MA0015AEntity;
import com.biru.service.ExchangeRateService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class ExchangeRateController {

	@Autowired
	private ExchangeRateService erService;

	@RequestMapping(value = EXCHANGE_RATE.SAVE, method = RequestMethod.POST)
	public Boolean save(@RequestBody Map<String, Object> p_Param) throws ParseException {
		try {
			return erService.save(p_Param);
		} catch (Exception e) {
			e.printStackTrace();
			return Boolean.FALSE;
		}
	}

	@RequestMapping(value = EXCHANGE_RATE.INQUIRY, method = RequestMethod.POST)
	public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) {
		return erService.inquiry(p_Param);
	}

	@RequestMapping(value = EXCHANGE_RATE.SAVE_KMK, method = RequestMethod.POST)
	public Boolean saveKmk(@RequestBody Map<String, Object> p_Param) throws ParseException {
		
		return erService.saveTaxRate(p_Param);
	}

	@RequestMapping(value = EXCHANGE_RATE.INQUIRY_KMK, method = RequestMethod.POST)
	public DatatableSet inquiryKmk(@RequestBody Map<String, Object> p_Param) {
		return erService.inquiryTaxRate(p_Param);
	}

	@RequestMapping(value = EXCHANGE_RATE.REMOVE_KMK, method = RequestMethod.POST)
	public Object deleteKmk(@RequestBody Map<String, Object> p_Param) {
		return erService.removeTaxRate(p_Param);
	}
}
