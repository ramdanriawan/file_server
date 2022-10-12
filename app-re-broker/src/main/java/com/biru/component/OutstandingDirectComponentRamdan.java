package com.biru.component;

import com.biru.common.param.Param;
import com.biru.entity.MA0010Entity;
import com.biru.entity.TR0012EntityRamdan;
import com.biru.repository.*;
import com.biru.specifications.TR0012Specifications;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.*;

@Component
public class OutstandingDirectComponentRamdan {

    @Autowired
    private TR0012Specifications tR0012Specifications;

    @Autowired
    private MA0005Repo mA0005Repo;

    @Autowired
    private OutstdRptRepo outstdRptRepo;

    @Autowired
    private TR0012Repo tR0012Repo;

    @Autowired
    private TR0003Repo tR0003Repo;

    @Autowired
    private ReportUtils reportUtils;

    @Autowired
    private MA0010Repo mA0010Repo;

    @Autowired
    private TR0007Repo tR0007Repo;

    @Autowired
    private MA0015Repo mA0015Repo;

    public Object createReport(Map<String, Object> param, String fileType, List<TR0012EntityRamdan> dataForPrint) throws Exception {
        Optional<MA0010Entity> company = mA0010Repo.findById(1L);
        String companyName = company.get().getCoName();
        String asAt = Param.getStr(param, "minDateParam");
        String type;

        if (Param.getStr(param, "typeParam").equals("RC")) {
            type = "Account Receivable";
        } else {
            type = "Account Payable";
        }
        System.out.println(dataForPrint.get(0).toString());
        //load file and compile it
        String path = "/app-dev/result";
        File file = new File("/app-dev/report/OutstandingV2-direct.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataForPrint);
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("companyName", companyName);
        parameters.put("asAt", asAt);
        parameters.put("type", type);
        parameters.put("userId", Param.getStr(param, "userId"));

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        String theFilePath = "";
        if (fileType.equals("xlsx")) {
            theFilePath = path + String.format("/OutstandingV2_%s", String.valueOf(Calendar.getInstance().getTime().getTime())) + ".xlsx";
            JRXlsxExporter exporter = new JRXlsxExporter();
            exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
            exporter.setParameter(JRXlsExporterParameter.OUTPUT_FILE_NAME, theFilePath);

            exporter.exportReport();
        }
        if (fileType.equals("pdf")) {
            theFilePath = path + String.format("/OutstandingV2_%s", String.valueOf(Calendar.getInstance().getTime().getTime())) + ".pdf";

            JasperExportManager.exportReportToPdfFile(jasperPrint, theFilePath);
        }

        return theFilePath;
    }
}
