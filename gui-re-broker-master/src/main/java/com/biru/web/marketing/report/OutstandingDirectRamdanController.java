package com.biru.web.marketing.report;

import com.biru.GuiConstantsRamdan;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.common.parser.JsonParser;
import com.biru.component.SessionComponent;
import com.biru.service.CommonService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(GuiConstantsRamdan.REST.MARKETING_REPORT)
public class OutstandingDirectRamdanController {

    @Autowired
    private CommonService common;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private SessionComponent sessionComponent;

    @RequestMapping(value = GuiConstantsRamdan.REST.OUTSTANDING_REPORT_GUI_URL, method = RequestMethod.GET)
    public String outstanding(Model model) throws Exception {
        model.addAttribute("mainMenu", "Finance Report  /  Outstanding");
        model.addAttribute("titlePage", "OUTSTANDING DIRECT");
        model.addAttribute("appDate", common.getAppDate().replaceAll("-", "/"));
        model.addAttribute("urlTypeOfCover", "outstanding-direct/get-dropdownTypeOfCover");
        model.addAttribute("urlClient", "outstanding-direct/client");
        model.addAttribute("urlSearch", "outstanding-direct/inquiry");

        List<DropdownIdText> message = common.getMessageSave();

        for (DropdownIdText idText : message) {
            model.addAttribute("M_" + idText.getId(), idText.getText());
        }

        return GuiConstantsRamdan.HTML.MARKETING_REPORT.OUTSTANDING_DIRECT;
    }

    @RequestMapping(value = GuiConstantsRamdan.REST.OUTSTANDING_REPORT_SERVER_URL, method = RequestMethod.GET)
    public @ResponseBody
    String inquiry(
            @RequestParam(value = "typeOfCover") String typeOfCover,
            @RequestParam(value = "client") String client,
            @RequestParam(value = "typeOfTransaction") String typeOfTransaction,
            @RequestParam(value = "transactionDate") String transactionDate,
            @RequestParam(value = "to") String to,
            @RequestParam(value = "officer") String officer,
            @RequestParam(value = "branch") String branch,

            @RequestParam(value = "sort", defaultValue = "ID_KEY") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") Integer limit
    ) throws JsonProcessingException, ParseException {

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        if (!transactionDate.isEmpty()) {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(transactionDate.replace("/", "-"));

            transactionDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        } else if (transactionDate.isEmpty()) {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(common.getAppDate().replace("/", "-"));

            transactionDate = new SimpleDateFormat("yyyy-MM-dd").format(date);
        }

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("typeOfCover", typeOfCover);
        param.put("client", client.equals("undefined") ? "" : client);
        param.put("typeOfTransaction", typeOfTransaction);
        param.put("transactionDate", transactionDate);
//        param.put("to", to);
        param.put("officer", officer);
        param.put("branch", branch);

        if (sort.equals("trxDate")) {
            sort = "TRX_DATE";
        }

        if (sort.equals("trxDueDate")) {
            sort = "TRX_DUE_DATE";
        }

        if (sort.equals("age")) {
            sort = "AGE";
        }

        if (sort.equals("trxType")) {
            sort = "TRX_TYPE";
        }

        if (sort.equals("trxVoucherId")) {
            sort = "TRX_VOUCHER_ID";
        }

        if (sort.equals("trxCountInv")) {
            sort = "TRX_COUNT_INV";
        }

        if (sort.equals("trxCoverCode")) {
            sort = "TRX_COVER_CODE";
        }

        if (sort.equals("trxOldType")) {
            sort = "TRX_OLD_TYPE";
        }

        if (sort.equals("trxOldVoucherId")) {
            sort = "TRX_OLD_VOUCHER_ID";
        }

        if (sort.equals("trxClientDesc")) {
            sort = "TRX_CLIENT_DESC";
        }

        if (sort.equals("trxDescription")) {
            sort = "TRX_DESCRIPTION";
        }

        if (sort.equals("trxCurrId")) {
            sort = "TRX_CURR_ID";
        }

        if (sort.equals("outstanding")) {
            sort = "TRX_OUTSTANDING";
        }

        if (sort.equals("trxClientDesc")) {
            sort = "TRX_CLIENT_DESC";
        }


        param.put(Param.SORT, sort);
        param.put(Param.ORDER, order);
        param.put(Param.OFFSET, offset);
        param.put(Param.LIMIT, limit);

        HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);
        System.out.println("hello bandung");
        String uri = sessionComponent.getHost() + GuiConstantsRamdan.REST.OUTSTANDING_REPORT_API_URL + "?tenantId=" + sessionComponent.getTenantId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        return response.getBody();
    }

    @RequestMapping(value = GuiConstantsRamdan.REST.OUTSTANDING_REPORT_DROPDOWN_TYPE_OF_COVER, method = RequestMethod.GET)
    public @ResponseBody
    String getTypeOfCover() {

        return common.getTypeOfCover();
    }

    @RequestMapping(value = GuiConstantsRamdan.REST.FIN_OUTSTANDING_CLIENT, method = RequestMethod.GET)
    public @ResponseBody
    String client(
            @RequestParam(value = "filterKey", required = false) String filterKey,
            @RequestParam(value = "filterValue", required = false) String filterValue,
            @RequestParam(value = "client", required = false) String cliType,
            @RequestParam(value = "cliDataStatus", required = false) String cliDataStatus,
            @RequestParam(value = "sort", defaultValue = "cliName") String sort,
            @RequestParam(value = "order", defaultValue = "asc") String order,
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") Integer limit) throws JsonProcessingException {

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


    @RequestMapping(value = GuiConstantsRamdan.REST.FIN_OUTSTANDING_VIEW_PDF, method = RequestMethod.GET)
    public @ResponseBody
    Object viewPdf(
            @RequestParam(value = "clientParam") String clientParam,
            @RequestParam(value = "typeParam") String typeParam,
            @RequestParam(value = "coverParam") String coverParam,
            @RequestParam(value = "maxDateParam") String maxDateParam,
            @RequestParam(value = "minDateParam") String minDateParam) throws JsonProcessingException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("clientParam", clientParam);
        param.put("typeParam", typeParam);
        param.put("coverParam", coverParam);
        param.put("maxDateParam", maxDateParam);
        param.put("minDateParam", minDateParam);
        param.put("version", "V2");
        param.put("userId", sessionComponent.getUserLogin());

        HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);

        String uri = sessionComponent.getHost() + GuiConstantsRamdan.REST.OUTSTANDING_REPORT_API_URL_PRINT + "?tenantId=" + sessionComponent.getTenantId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        File file;
        InputStreamResource resource;

        try {
            file = new File(response.getBody());
            resource = new InputStreamResource(new FileInputStream(file));
        } catch (Exception e) {
            return response.getBody();
        }

        return ResponseEntity.ok()
                .header("Content-Disposition", "filename=Outstanding.pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .contentLength(file.length())
                .body(resource);
    }

    @RequestMapping(value = GuiConstantsRamdan.REST.FIN_OUTSTANDING_EXPORT_EXCEL, method = RequestMethod.GET, produces = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
    public @ResponseBody
    Object exportExcel(
            @RequestParam(value = "clientParam") String clientParam,
            @RequestParam(value = "typeParam") String typeParam,
            @RequestParam(value = "coverParam") String coverParam,
            @RequestParam(value = "maxDateParam") String maxDateParam,
            @RequestParam(value = "minDateParam") String minDateParam) throws IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> param = new HashMap<String, Object>();
        param.put("clientParam", clientParam);
        param.put("typeParam", typeParam);
        param.put("coverParam", coverParam);
        param.put("maxDateParam", maxDateParam);
        param.put("minDateParam", minDateParam);
        param.put("version", "V2");
        param.put("userId", sessionComponent.getUserLogin());

        HttpEntity<String> httpEntity = new HttpEntity<String>(JsonParser.getParser().writeValueAsString(param), httpHeaders);

        String uri = sessionComponent.getHost() + GuiConstantsRamdan.REST.OUTSTANDING_REPORT_API_URL_EXCEL + "?tenantId=" + sessionComponent.getTenantId();
        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, httpEntity, String.class);

        File file = new File(response.getBody());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=Outstanding.xls")
                .contentLength(file.length())
                .body(resource);
    }

}
