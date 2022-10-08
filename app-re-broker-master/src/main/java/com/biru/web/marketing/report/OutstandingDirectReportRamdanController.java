package com.biru.web.marketing.report;

import com.biru.ReBrokerConstantsRamdan;
import com.biru.ReBrokerConstantsRamdan.REST;
import com.biru.common.entity.DatatableSet;
import com.biru.service.OutstandingDirectServiceRamdan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(REST.MARKETING_REPORT)
public class OutstandingDirectReportRamdanController {

    @Autowired
    private OutstandingDirectServiceRamdan outstandingDirectServiceRamdan;

    @RequestMapping(value = ReBrokerConstantsRamdan.OUTSTANDING.INQUIRY, method = RequestMethod.POST)
    public DatatableSet inquiry(@RequestBody Map<String, Object> p_Param) throws Exception {

		return outstandingDirectServiceRamdan.inquiry(p_Param);
    }

    @RequestMapping(value = ReBrokerConstantsRamdan.OUTSTANDING.EXPORT_EXCEL, method = RequestMethod.POST)
    public Object exportExcel(@RequestBody Map<String, Object> param) throws Exception {
        return outstandingDirectServiceRamdan.exportExcelV2(param);
    }

    @RequestMapping(value = ReBrokerConstantsRamdan.OUTSTANDING.PRINT, method = RequestMethod.POST)
    public Object print(@RequestBody Map<String, Object> param) throws Exception {
        return outstandingDirectServiceRamdan.printV2(param);
    }

}
