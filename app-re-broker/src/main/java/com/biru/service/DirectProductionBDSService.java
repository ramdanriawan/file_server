package com.biru.service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import com.biru.common.entity.DropdownIdText;

public interface DirectProductionBDSService {

    public List<DropdownIdText> getCommOutName(Map<String, Object> param);
    public BigDecimal getWhtClient(Map<String, Object> param);
    public Object save(Map<String, Object> param) throws ParseException;
    public Object inquiryModify(Map<String, Object> param);
    public Object createReportHtml(Map<String, Object> param) throws Exception;
    public Object createReportPdf(Map<String, Object> param) throws Exception;
    public Object createReportDoc(Map<String, Object> param) throws Exception;
    public Object sendEmail(Map<String, Object> param) throws Exception;
    public Object inquirySend(Map<String, Object> param);
    public Object doClosing(Map<String, Object> param) throws Exception;
    public Object closing(Map<String, Object> param) throws Exception;
    public Object createClosingHtml(Map<String, Object> param) throws Exception;

}
