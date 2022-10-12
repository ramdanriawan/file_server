package com.biru.web.staticdata;

import com.biru.GuiConstants;
import com.biru.GuiConstants.REST;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.service.CommonService;
import com.biru.component.SessionComponent;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(REST.STATIC_DATA)
public class OJKReportSetupDirectController {
    @Value("${app.host}")
    private String host;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private CommonService common;

    @Autowired
    private SessionComponent sessionComponent;

    @RequestMapping(value = REST.SD_OJK_REPORT_SETUP_DIRECT, method = RequestMethod.GET)
    public String ojkReportSetupDirect(Model model) throws ParseException, IOException {
        model.addAttribute("mainMenu", "Static Data / Setup OJK Report");
        model.addAttribute("titlePage", "SETUP OJK REPORT");

        List<DropdownIdText> message = common.getMessageSave();
        for(DropdownIdText idText : message) {
            model.addAttribute("M_" + idText.getId(), idText.getText());
        }
        return GuiConstants.HTML.SD_OJK_REPORT_SETUP_DIRECT;
    }

    @RequestMapping(value = REST.SD_OJK_REPORT_SETUP_DIRECT_INQUIRY, method = RequestMethod.GET)
    public @ResponseBody String inquiry(
            @RequestParam(value="reportcode") String reportCode,
            @RequestParam(value="sort", defaultValue="idKey") String sort,
            @RequestParam(value="order", defaultValue="asc") String order,
            @RequestParam(value="offset", defaultValue="0") Integer offset,
            @RequestParam(value="limit", defaultValue="5") Integer limit) throws JsonProcessingException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("reportcode", reportCode);
        param.put(Param.SORT, sort);
        param.put(Param.ORDER, order);
        param.put(Param.OFFSET, offset);
        param.put(Param.LIMIT, limit);

        HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);

        String uri = sessionComponent.getHost() + GuiConstants.URI.SD_OJK_REPORT_SETUP_INQUIRY + "?tenantId=" +
                        sessionComponent.getTenantId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        return response.getBody();
    }

    @RequestMapping(value = REST.SD_OJK_REPORT_SETUP_DIRECT_SAVE, method = RequestMethod.POST)
    public @ResponseBody String save(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        param.put(Param.CREATE_BY, sessionComponent.getUserLogin());
        param.put(Param.MODIFY_BY, sessionComponent.getUserLogin());

        HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);

        String uri = sessionComponent.getHost() + GuiConstants.URI.SD_OJK_REPORT_SETUP_SAVE + "?tenantId=" + sessionComponent.getTenantId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        return response.getBody();
    }

    @RequestMapping(value = REST.SD_OJK_REPORT_SETUP_DIRECT_DELETE, method = RequestMethod.POST)
    public @ResponseBody String delete(@RequestBody HashMap<String, Object> param) throws JsonProcessingException{
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);

        String uri = sessionComponent.getHost() + GuiConstants.URI.SD_OJK_REPORT_SETUP_DELETE + "?tenantId=" + sessionComponent.getTenantId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        return response.getBody();
    }

    @RequestMapping(value = REST.SD_OJK_REPORT_SETUP_DIRECT_DROPDOWN_REPORT_CODE, method = RequestMethod.GET)
    public @ResponseBody String getReportCodes() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);

        String uri = host + GuiConstants.URI.SD_OJK_REPORT_SETUP_GET_REPORT_CODE + "?tenantId=" + sessionComponent.getTenantId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        return response.getBody();
    }

    @RequestMapping(value = REST.SD_OJK_REPORT_SETUP_DIRECT_DROPDOWN_ROW, method = RequestMethod.GET)
    public @ResponseBody String getRow(@RequestParam(name = "reportcode")String reportCode) throws JsonProcessingException  {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("reportCode", reportCode);

        HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);

        String uri = host + GuiConstants.URI.SD_OJK_REPORT_SETUP_GET_ROW + "?tenantId=" + sessionComponent.getTenantId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        return response.getBody();

    }

    @RequestMapping(value = REST.SD_OJK_REPORT_SETUP_DIRECT_DROPDOWN_COA, method = RequestMethod.GET)
    public @ResponseBody String getCoa() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<String>(null, httpHeaders);

        String uri = host + GuiConstants.URI.SD_OJK_REPORT_SETUP_GET_COA + "?tenantId=" + sessionComponent.getTenantId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);
        return response.getBody();
    }

}
