package com.biru.web.view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import com.biru.common.parser.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;

@Component("excelView")
public class ExcelView extends AbstractXlsView {
	
	private static final String BEGINNING_BALANCE = "beginningBalance";

	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, 
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		String file = (String) model.get("excelFile");
		
		if(!StringUtils.isEmpty(file)) {
			if(BEGINNING_BALANCE.equals(file))
				createBeginBal(model, workbook, response);
		}
		
	}
	
	private void createBeginBal(Map<String, Object> model, Workbook workbook, 
			HttpServletResponse response) throws Exception {
		String fileName = (String) model.get("excelFileName");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");

		String sheetName = (String) model.get("excelSheetName");
        Sheet sheet = workbook.createSheet(sheetName);

        CellStyle styleFontCenter = workbook.createCellStyle();
        CellStyle styleFontRight = workbook.createCellStyle();
        
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);
        
        styleFontCenter.setFont(fontBold);
        styleFontCenter.setAlignment(HorizontalAlignment.CENTER);
        styleFontRight.setAlignment(HorizontalAlignment.RIGHT);
        
        Row header = sheet.createRow(0);
        Cell cell0Head = header.createCell(0);
        Cell cell1Head = header.createCell(1);
        Cell cell2Head = header.createCell(2);
        Cell cell3Head = header.createCell(3);
        
        cell0Head.setCellValue("COA");
        cell1Head.setCellValue("Description");
        cell2Head.setCellValue("Debit");
        cell3Head.setCellValue("Credit");
        
        cell0Head.setCellStyle(styleFontCenter);
        cell1Head.setCellStyle(styleFontCenter);
        cell2Head.setCellStyle(styleFontCenter);
        cell3Head.setCellStyle(styleFontCenter);
        
        List<Map<String, String>> datas = JsonParser.getParser()
				.readValue((String) model.get("body"), new TypeReference<List<Map<String, String>>>(){});

        int row = 1;
        for (Map<String, String> data : datas) {
            Row courseRow = sheet.createRow(row);
            Cell cell0Val = courseRow.createCell(0);
            Cell cell1Val = courseRow.createCell(1);
            Cell cell2Val = courseRow.createCell(2);
            Cell cell3Val = courseRow.createCell(3);
            
            cell0Val.setCellValue(data.get("coaCode"));
            cell1Val.setCellValue(data.get("coaDescript"));
            cell2Val.setCellValue(data.get("glBalDebit0"));
            cell3Val.setCellValue(data.get("glBalCredit0"));
            
            cell2Val.setCellStyle(styleFontRight);
            cell3Val.setCellStyle(styleFontRight);
            
            row++;
        }
        
	}

}
