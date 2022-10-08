package com.biru.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.biru.common.param.Param;
import com.biru.entity.TR0012Entity;
import com.biru.repository.TR0012Repo;
import com.biru.service.HelperService;

@Service
public class HelperServiceImpl implements HelperService {
	
	@Value("${document.path}")
	private String path;

	@Autowired
	private TR0012Repo tr0012Repo;
	
	private static final String TRX_TRX_CLASS 	= "TRX_TRX_CLASS";
	private static final String TRX_TYPE		= "TRX_TYPE";
	private static final String TRX_VOUCHER_ID	= "TRX_VOUCHER_ID";
	private static final String TRX_DATE		= "TRX_DATE";
	private static final String TRX_DUE_DATE	= "TRX_DUE_DATE";
	private static final String TRX_CLIENT		= "TRX_CLIENT";
	private static final String TRX_DESCRIPTION	= "TRX_DESCRIPTION";
	private static final String TRX_COVER_CODE	= "TRX_COVER_CODE";
	private static final String TRX_CURR_ID		= "TRX_CURR_ID";
	private static final String TRX_CURR_RATE	= "TRX_CURR_RATE";
	private static final String TRX_ORG_AMOUNT	= "TRX_ORG_AMOUNT";
	private static final String TRX_INVC_AMOUNT	= "TRX_INVC_AMOUNT";
	private static final String TRX_BRKR_FEE	= "TRX_BRKR_FEE";
	private static final String TRX_TAXIN_BF	= "TRX_TAXIN_BF";
	private static final String NOW				= "NOW";
	
	private static final String S_ONE			= "1";
	private static final String MIGRATION		= "migration";
	
	private static final Logger logger = LoggerFactory.getLogger(HelperServiceImpl.class);
	
	@SuppressWarnings("resource")
	@Override
	public String uploadTR0012(Map<String, Object> param) throws IOException {
		long start = System.currentTimeMillis();
		
		String fileName = path.concat(Param.getStr(param, "fileName"));
		
		File myFile = new File(fileName);
		FileInputStream fis = new FileInputStream(myFile); 
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);

		String sheetName = Param.getStr(param, "sheetName");
		List<String> sheets = Arrays.asList(sheetName.split(","));
		
		for(String sheet : sheets) {
			XSSFSheet mySheet = myWorkBook.getSheet(sheet);
			
			if(mySheet != null)
				processSheet(mySheet);
			else
				logger.error("Sheet '{}' not found!", sheet);
		}
		
		logger.info("Upload TR0012 elapsed time : {}ms.", 
				System.currentTimeMillis() - start);
		
		return "FINISH";
	}
	
	private void processSheet(XSSFSheet mySheet) {
		Date now = Calendar.getInstance().getTime();
		Iterator<Row> rowIterator = mySheet.iterator();
		List<TR0012Entity> tr12List = new ArrayList<TR0012Entity>();
		List<String> rowNumFailed = new ArrayList<String>();
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next(); 
			Boolean isNotEmpty = isNotEmpty(row);
			
			Integer rowNum = row.getRowNum();
			Integer colNum = null;
			if(isNotEmpty) {
				try {
					Map<String, Object> data = new HashMap<String, Object>();
					Iterator<Cell> cellIterator = row.cellIterator();
					
					while(cellIterator.hasNext()) {
						Cell cell = cellIterator.next(); 
						colNum = cell.getColumnIndex();
						
						if(colNum == 0)
							data.put(TRX_TRX_CLASS, getStringCell(cell));
						else if(colNum == 1)
							data.put(TRX_TYPE, getStringCell(cell));
						else if(colNum == 2)
							data.put(TRX_VOUCHER_ID, getStringCell(cell));
						else if(colNum == 3)
							data.put(TRX_CLIENT, getStringCell(cell));
						else if(colNum == 4)
							data.put(TRX_DESCRIPTION, getStringCell(cell));
						else if(colNum == 5)
							data.put(TRX_COVER_CODE, getStringCell(cell));
						else if(colNum == 6)
							data.put(TRX_DUE_DATE, cell.getDateCellValue());
						else if(colNum == 7)
							data.put(TRX_DATE, cell.getDateCellValue());
						else if(colNum == 8)
							data.put(TRX_CURR_ID, getStringCell(cell));						
						else if(colNum == 9)
							data.put(TRX_CURR_RATE, getNumericCell(cell));	
						else if(colNum == 10)
							data.put(TRX_ORG_AMOUNT, getNumericCell(cell));						
						else if(colNum == 11)
							data.put(TRX_INVC_AMOUNT, getNumericCell(cell));						
						else if(colNum == 12)
							data.put(TRX_BRKR_FEE, getNumericCell(cell));						
						else if(colNum == 13)
							data.put(TRX_TAXIN_BF, getNumericCell(cell));
					}
					data.put(NOW, now);
					tr12List.add(createTR0012(data));
				}catch(Exception e) {
					rowNumFailed.add(rowNum + "_" + colNum);
				}
			}
		}
		
		logger.warn("Failed process data for row : {}.", rowNumFailed);
		logger.info("Save upload TR0012 size data : {}.", tr12List.size());
		if(tr12List.size() > 0)
			tr0012Repo.saveAll(tr12List);
	}
	
	private Boolean isNotEmpty(Row row) {
		int rowNum = row.getRowNum();
		if(rowNum == 0)					//Header
			return Boolean.FALSE;
		
		Cell cellVoucherId = row.getCell(2);
		
		if(cellVoucherId == null)		//Empty
			return Boolean.FALSE;
		
		String trxVoucherId = getStringCell(cellVoucherId);
		
		return StringUtils.isNotBlank(trxVoucherId);
	}
	
	@SuppressWarnings("deprecation")
	private BigDecimal getNumericCell(Cell cell) {
		BigDecimal num = BigDecimal.ZERO;
		if(cell.getCellType().equals(CellType.NUMERIC)) {
			num = new BigDecimal(cell.getNumericCellValue());
		}else {
			cell.setCellType(CellType.STRING);
			String val = cell.getStringCellValue();
			if(StringUtils.isNotBlank(val))
				num = new BigDecimal(val.replace(",", ""));
		}
		
		return num;
	}
	
	@SuppressWarnings("deprecation")
	private String getStringCell(Cell cell) {
		cell.setCellType(CellType.STRING);
		
		return cell.getStringCellValue();
	}
	
	private TR0012Entity createTR0012(Map<String, Object> data) {
		TR0012Entity tr0012entity = new TR0012Entity();
		tr0012entity.setTrxTrxClass(Param.getStr(data, TRX_TRX_CLASS));
		tr0012entity.setTrxType(Param.getStr(data, TRX_TYPE));
		tr0012entity.setTrxVoucherId(Param.getStr(data, TRX_VOUCHER_ID));
		tr0012entity.setTrxSource(S_ONE);
		tr0012entity.setTrxCountInv(1);
		tr0012entity.setTrxDate((Date) data.get(TRX_DATE));
		tr0012entity.setTrxDueDate((Date) data.get(TRX_DUE_DATE));
		tr0012entity.setTrxClient(Param.getStr(data, TRX_CLIENT));
		tr0012entity.setTrxDescription(Param.getStr(data, TRX_DESCRIPTION));
		tr0012entity.setTrxCoverCode(Param.getStr(data, TRX_COVER_CODE));
		tr0012entity.setTrxCurrId(Param.getStr(data, TRX_CURR_ID));
		tr0012entity.setTrxMethPay("0");
		tr0012entity.setTrxDataStatus("11");
		tr0012entity.setTrxCurrRate(Param.getBdWithDef(data, TRX_CURR_RATE));
		tr0012entity.setTrxOrgAmount(Param.getBdWithDef(data, TRX_ORG_AMOUNT));
		tr0012entity.setTrxInvcAmount(Param.getBdWithDef(data, TRX_INVC_AMOUNT));
		tr0012entity.setTrxBrkrFee(Param.getBdWithDef(data, TRX_BRKR_FEE));
		tr0012entity.setTrxTaxinBf(Param.getBdWithDef(data, TRX_TAXIN_BF));		
		tr0012entity.setCreateBy(MIGRATION);
		tr0012entity.setCreateOn((Date) data.get(NOW));
		
		return tr0012entity;
	}

}
