package com.biru.component;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.biru.ReBrokerConstants.CHARACTER;
import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.config.DataSourceInit;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Component
public class ReportUtils extends AbstractCommon {
	
	@Value("${report.path.jasper}")
	private String pathJasper;
	
	@Value("${report.path.result}")
	private String pathResult;
	
	@Value("${tenant.id.default}")
	private String tenantIdDefault;
	
	@Autowired
	private DataSourceInit dataSourceInit;
	
	public String exportPdf(String p_FileName, Map<String, Object> p_Params) throws Exception {		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		
		String tenantId;
		if(attributes!=null)
			tenantId = attributes.getRequest().getParameter(PARAM.TENANT_ID);
        else
        	tenantId = tenantIdDefault;
		
		String path = pathResult.concat(tenantId).concat(File.separator);
		createFolder(path);
		
		DriverManagerDataSource dataSource = (DriverManagerDataSource) dataSourceInit.getMapSource().get(tenantId);
		
		JasperReport jasperReport = JasperCompileManager.compileReport(pathJasper + p_FileName);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, p_Params, dataSource.getConnection());
		
		String fileName = p_FileName.substring(0, p_FileName.lastIndexOf("."));
		fileName = fileName + CHARACTER.UNDERSCORE + formatTimestamp.format(Calendar.getInstance().getTime());
		fileName = path + fileName.concat(".pdf");
		JasperExportManager.exportReportToPdfFile(jasperPrint, fileName);		
		
		return fileName;
	}
	
	public String exportHtml(String p_FileName, Map<String, Object> p_Params) throws Exception {		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		
		String tenantId;
		if(attributes!=null)
			tenantId = attributes.getRequest().getParameter(PARAM.TENANT_ID);
        else
        	tenantId = tenantIdDefault;
		
		String path = pathResult.concat(tenantId).concat(File.separator);
		createFolder(path);
		
		DriverManagerDataSource dataSource = (DriverManagerDataSource) dataSourceInit.getMapSource().get(tenantId);
		
		JasperReport jasperReport = JasperCompileManager.compileReport(pathJasper + p_FileName);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, p_Params, dataSource.getConnection());
		
		String fileName = p_FileName.substring(0, p_FileName.lastIndexOf("."));
		fileName = fileName + CHARACTER.UNDERSCORE + formatTimestamp.format(Calendar.getInstance().getTime());
		fileName = path + fileName.concat(".html");
		JasperExportManager.exportReportToHtmlFile(jasperPrint, fileName);
		
		return fileName;
	}
	
	public void compileReport(String p_FileName) throws JRException {
		
		JasperCompileManager.compileReportToFile(pathJasper + p_FileName, pathJasper+p_FileName.substring(0, p_FileName.lastIndexOf("."))+".jasper");
	}
	
	public String exportExcel(String p_FileName, Map<String, Object> p_Params) throws Exception {		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		
		Boolean isReportProductionDi = p_Params.get("isReportProductionDi") != null ? 
				(Boolean) p_Params.get("isReportProductionDi") : false;
		
		String tenantId;
		if(attributes!=null)
			tenantId = attributes.getRequest().getParameter(PARAM.TENANT_ID);
        else
        	tenantId = tenantIdDefault;
		
		String path = pathResult.concat(tenantId).concat(File.separator);
		createFolder(path);
		
		DriverManagerDataSource dataSource = (DriverManagerDataSource) dataSourceInit.getMapSource().get(tenantId);
		
		JasperReport jasperReport = JasperCompileManager.compileReport(pathJasper + p_FileName);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, p_Params, dataSource.getConnection());
		
		String fileName = p_FileName.substring(0, p_FileName.lastIndexOf("."));
		fileName = fileName + CHARACTER.UNDERSCORE + formatTimestamp.format(Calendar.getInstance().getTime());
		fileName = path + fileName.concat(".xls");
		System.out.println(fileName);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));
		exporter.exportReport();

		return fileName;
	}
	
	public String exportDocx(String p_FileName, Map<String, Object> p_Params) throws Exception {		
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		
		String tenantId;
		if(attributes!=null)
			tenantId = attributes.getRequest().getParameter(PARAM.TENANT_ID);
        else
        	tenantId = tenantIdDefault;
		
		String path = pathResult.concat(tenantId).concat(File.separator);
		createFolder(path);
		
		DriverManagerDataSource dataSource = (DriverManagerDataSource) dataSourceInit.getMapSource().get(tenantId);
		
		JasperReport jasperReport = JasperCompileManager.compileReport(pathJasper + p_FileName);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, p_Params, dataSource.getConnection());
		
		String fileName = p_FileName.substring(0, p_FileName.lastIndexOf("."));
		fileName = fileName + CHARACTER.UNDERSCORE + formatTimestamp.format(Calendar.getInstance().getTime());
		fileName = path + fileName.concat(".docx");
		
		JRDocxExporter exporter = new JRDocxExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(fileName));
		exporter.exportReport();
		
		return fileName;
	}
	
	private void createFolder(String p_Path) {
		File directory = new File(p_Path);
		
	    if(!directory.exists())
	    	directory.mkdir();
	}
	
}
