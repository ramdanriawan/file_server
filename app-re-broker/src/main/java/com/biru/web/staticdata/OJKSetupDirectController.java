package com.biru.web.staticdata;

import java.util.List;
import java.util.Map;

import com.biru.common.entity.DropdownIdText;
import com.biru.common.entity.DropdownDash;
import com.biru.service.OJKSetupDirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.biru.ReBrokerConstants.REST;
import com.biru.ReBrokerConstants.REST.OJK_SETUP_DIRECT;
import com.biru.common.entity.DatatableSet;
import com.biru.service.OJKSetupDirectService;

@RestController
@RequestMapping(REST.STATIC_DATA)
public class OJKSetupDirectController {
    @Autowired
    private OJKSetupDirectService ojkSetupDirectService;

    @RequestMapping(value = REST.OJK_SETUP_DIRECT.SAVE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object save(@RequestBody Map<String, Object> param) {
        return ojkSetupDirectService.save(param);
    }

    @RequestMapping(value = REST.OJK_SETUP_DIRECT.DELETE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public Object delete(@RequestBody Map<String, Object> param) {
        return ojkSetupDirectService.delete(param);
    }

    @RequestMapping(value = REST.OJK_SETUP_DIRECT.INQUIRY, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public DatatableSet inquiry(@RequestBody Map<String, Object> param) {
        return ojkSetupDirectService.inquiry(param);
    }

    @RequestMapping(value = REST.OJK_SETUP_DIRECT.DROPDOWN_REPORT_CODE, method = RequestMethod.POST)
    public List<DropdownIdText> getReportCodes() {
        return ojkSetupDirectService.getReportCodes();
    }

    @RequestMapping(value = REST.OJK_SETUP_DIRECT.DROPDOWN_ROW, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DropdownDash> getRow(@RequestBody Map<String, Object> param) {
        return ojkSetupDirectService.getDropDownRow(param);
    }

    @RequestMapping(value = REST.OJK_SETUP_DIRECT.DROPDOWN_COA, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DropdownIdText> getCOA() {
        return ojkSetupDirectService.getDropDownCOA();
    }
}
