package com.biru.service.impl;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.OutstandingDirectComponentRamdan;
import com.biru.component.ReportUtils;
import com.biru.entity.TR0012EntityRamdan;
import com.biru.repository.TR0012RepoRamdan;
import com.biru.service.OutstandingDirectServiceRamdan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class OutstandingDirectServiceRamdanImpl extends AbstractCommon implements OutstandingDirectServiceRamdan {

    @Autowired
    private TR0012RepoRamdan tr0012RepoRamdan;

    @Autowired
    private ReportUtils reportUtils;

    @Autowired
    private OutstandingDirectComponentRamdan outstandingDirectComponentRamdan;
    private List<TR0012EntityRamdan> dataForPrint;

    @Override
    public DatatableSet inquiry(Map<String, Object> param) throws ParseException {
        List<String> types;

        String typeOfCover = Param.getStr(param, "typeOfCover");
        String client = Param.getStrWithDef(param, "client");
        String typeOfTransaction = Param.getStrWithDef(param, "typeOfTransaction");
        String transactionDate = Param.getStr(param, "transactionDate");
//        String to = Param.getStr(param, "to");
        String officer = Param.getStr(param, "officer");
        String branch = Param.getStr(param, "branch");

        String sort = Param.getStr(param, PARAM.SORT);
        String order = Param.getStr(param, PARAM.ORDER);
        Integer offset = Param.getInt(param, PARAM.OFFSET);
        Integer limit = Param.getInt(param, PARAM.LIMIT);
//        Integer limit = Param.getInt(param, PARAM.LIMIT);

        if (typeOfCover.toLowerCase().equals("all")) {
            typeOfCover = "";
        }

        if (officer.toLowerCase().equals("all")) {
            officer = "";
        }

        if (branch.toLowerCase().equals("all")) {
            branch = "";
        }

        if (client.isEmpty()) {
            client = "";
        }

        if (typeOfTransaction.toLowerCase().equals("ap")) {
            typeOfTransaction = "PU";
        }

        if (typeOfTransaction.toLowerCase().equals("ar")) {
            typeOfTransaction = "SE";
        }

        Pageable pageable = PageRequest.of(offset / limit, limit, Sort.Direction.fromString(order), sort);

        List<TR0012EntityRamdan> data = tr0012RepoRamdan.findOutstandingDirect(
                typeOfCover,
                client,
                typeOfTransaction,
                transactionDate,
//                to,
                officer,
                branch,

                pageable
        );

        dataForPrint = tr0012RepoRamdan.findOutstandingDirectFull(
                typeOfCover,
                client,
                typeOfTransaction,
                transactionDate,
//                to,
                officer,
                branch
        );

        return new DatatableSet(Long.parseLong(String.valueOf(dataForPrint.size())), Long.parseLong(String.valueOf(dataForPrint.size())), data);
    }

    @Override
    public Object exportExcelV2(Map<String, Object> param) throws Exception {
        if (dataForPrint == null) {
            return "Tidak ada data yang di cari / silakan cari ulang";
        }

        return outstandingDirectComponentRamdan.createReport(param, "xlsx", dataForPrint);
    }

    @Override
    public Object printV2(Map<String, Object> param) throws Exception {
        if (dataForPrint == null) {
            return "Tidak ada data yang di cari / silakan cari ulang";
        }

        return outstandingDirectComponentRamdan.createReport(param, "pdf", dataForPrint);
    }

    @Override
    public Object exportExcel(Map<String, Object> param) throws Exception {
        return null;
    }

    @Override
    public Object print(Map<String, Object> param) throws Exception {
        return null;
    }

}
