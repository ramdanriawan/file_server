package com.biru.web.marketing;

import java.text.ParseException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.biru.ReBrokerConstants.REST;
import com.biru.service.DirectProductionBDSService;

@RestController
@RequestMapping(REST.MARKETING)
public class DirectProductionBDSController {
    @Autowired
    private DirectProductionBDSService directProductionBDSService;

    @RequestMapping(value = REST.DPRODUCTION_BDS.GET_COMM_OUT_NAME, method = RequestMethod.POST)
    public Object getCommOutName(@RequestBody Map<String, Object> param) {
        return directProductionBDSService.getCommOutName(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.GET_WHT_CLIENT, method = RequestMethod.POST)
    public Object getWhtClient(@RequestBody Map<String, Object> param) {
        return directProductionBDSService.getWhtClient(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.SAVE, method = RequestMethod.POST)
    public Object save(@RequestBody Map<String, Object> param) throws ParseException {
        return directProductionBDSService.save(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.INQUIRY_MODIFY, method = RequestMethod.POST)
    public Object inquiryModify(@RequestBody Map<String, Object> param) throws ParseException {
        return directProductionBDSService.inquiryModify(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.CREATE_RPT_HTML, method = RequestMethod.POST)
    public Object createReportHtml(@RequestBody Map<String, Object> param) throws Exception {
        return directProductionBDSService.createReportHtml(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.CREATE_RPT_PDF, method = RequestMethod.POST)
    public Object createReportPdf(@RequestBody Map<String, Object> param) throws Exception {
        return directProductionBDSService.createReportPdf(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.CREATE_RPT_DOC, method = RequestMethod.POST)
    public Object createReportDoc(@RequestBody Map<String, Object> param) throws Exception {
        return directProductionBDSService.createReportDoc(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.INQUIRY_SEND, method = RequestMethod.POST)
    public Object inquirySend(@RequestBody Map<String, Object> param) throws Exception {
        return directProductionBDSService.inquirySend(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.SEND_EMAIL, method = RequestMethod.POST)
    public Object sendEmail(@RequestBody Map<String, Object> param) throws Exception {
        return directProductionBDSService.sendEmail(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.CLOSING, method = RequestMethod.POST)
    public Object closing(@RequestBody Map<String, Object> param) throws Exception {
        return directProductionBDSService.doClosing(param);
    }

    @RequestMapping(value = REST.DPRODUCTION_BDS.CREATE_CLOSING_HTML, method = RequestMethod.POST)
    public Object createClosingHtml(@RequestBody Map<String, Object> param) throws Exception {
        return directProductionBDSService.createClosingHtml(param);
    }

}
