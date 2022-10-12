package com.biru.web.finance.report;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.VAT_REPORT;
import com.biru.service.VatReportService;

import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(REST.FINANCE_REPORT)
public class VatReportController {
	
	@Autowired
	private VatReportService vatReportService;
	
	@RequestMapping(value = VAT_REPORT.INQUIRY, method = RequestMethod.POST)
	public Object inquiry(@RequestBody Map<String, Object> p_Param) {
		return vatReportService.inq(p_Param);
	}
	
	@RequestMapping(value = VAT_REPORT.PRINT, method = RequestMethod.POST)
	public Object print(@RequestBody Map<String, Object> p_Param) throws JRException, Exception {
		return vatReportService.print(p_Param);
	}
	
	@RequestMapping(value = VAT_REPORT.EXPORT, method = RequestMethod.POST)
	public Object export(@RequestBody Map<String, Object> p_Param) throws JRException, Exception {
		return vatReportService.export(p_Param);
	}
}
