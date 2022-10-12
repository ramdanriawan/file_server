package com.biru.component;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.biru.common.param.Param;
import com.biru.entity.GL0001Entity;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0010Entity;
import com.biru.entity.RptBsEntity;
import com.biru.entity.RptPlEntity;
import com.biru.repository.GL0001Repo;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0010Repo;
import com.biru.repository.RptBsRepo;
import com.biru.repository.RptPlRepo;

@Component
public class RptBsComponent {

	@Autowired
	private RptBsRepo rptBsRepo;
	
	@Autowired
	private RptPlRepo rptPlRepo;

	@Autowired
	private GL0001Repo gL0001Repo;

	@Autowired
	private MA0004Repo mA0004Repo;
	
	@Autowired
	private MA0010Repo mA0010Repo;
	
	@Autowired
	private DateComponent dateComponent;
	
	@Autowired
	private ReportUtils reportUtils;
	
	private static final String XD = "XD";
	
	private static final Logger logger = LoggerFactory.getLogger(RptBsComponent.class);
	
	@Transactional
	public void setupReport(Map<String, Object>param) {
		int month = Param.getInt(param, "month");
		Integer year = Param.getInt(param, "year");
		
		rptBsRepo.resetRptBs();
		
		setBlock1(month, String.valueOf(year));
		setBlock2(12, String.valueOf(year-1));
		setBlock3(12, String.valueOf(year-2));
		setBlock4(12, String.valueOf(year-3));
		setBlock5(12, String.valueOf(year-4));
	}
	
	public Object createReport(String type, Map<String, Object>param) throws Exception {
		int month = Param.getInt(param, "month");
		Integer year = Param.getInt(param, "year");
		
		param.clear();
		
		Optional<MA0010Entity> company = mA0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		param.put("companyName", companyName);
		
		Calendar c = Calendar.getInstance();
		c.set(Calendar.MONTH, month-1);
		c.set(Calendar.YEAR, year);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		String dateStr = c.get(Calendar.DAY_OF_MONTH) + " " + dateComponent.getMonthName(month-1) + " " + year; 
		param.put("dateStr", dateStr);
		param.put("year", year);
		if(type.equals("preview")) {
			return reportUtils.exportHtml("RptBs.jrxml", param);
		}else if(type.equals("print")) {
			return reportUtils.exportPdf("RptBs.jrxml", param);
		}else {
			return reportUtils.exportExcel("RptBs.jrxml", param);
		}
	}

	public void setBlock1 (int month, String year) {
		logger.info("Rpt Bs setBlock1");
		List<RptBsEntity> listRptBsEntities = (List<RptBsEntity>) rptBsRepo.findAll(new Sort(Sort.Direction.ASC, "rowRpt"));

		for (RptBsEntity rptBsEntity : listRptBsEntities) {
			if (rptBsEntity.getCoaCode().equals("000000") && rptBsEntity.getCoaGroup() != null) {
				if(rptBsEntity.getCoaGroup().startsWith("R")){
					rptBsEntity.setBlock1R(rptBsRepo.sumBlock1(String.valueOf(rptBsEntity.getCoaGroup().charAt(1))));
				}else if(rptBsEntity.getCoaGroup().startsWith("X")){
					if(rptBsEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptBsRepo.findByCoaGroup("RA").getBlock1R();
						BigDecimal rB = rptBsRepo.findByCoaGroup("RB").getBlock1R();
						BigDecimal rC = rptBsRepo.findByCoaGroup("RC").getBlock1R();
						BigDecimal rD = rptBsRepo.findByCoaGroup("RD").getBlock1R();
						rptBsEntity.setBlock1R(rA.add(rB).subtract(rC).subtract(rD));
					}else {
						rptBsEntity.setBlock1R(
								getValPlReport(rptBsEntity.getCoaCode(), XD, 1));
					}
				}
			}else if(!rptBsEntity.getCoaCode().equals("000000")){
				GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptBsEntity.getCoaCode(), year);
				if(gL0001Entity != null) {
					MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptBsEntity.getCoaCode());
					
					Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
					BigDecimal glBalD = getGlBal.get("glBalD");
					BigDecimal glBalC = getGlBal.get("glBalC");
					BigDecimal result = BigDecimal.ZERO;

					if(mA0004Entity.getCoaNormal().equals('D')) {
						result = glBalD.subtract(glBalC);
					}else {
						result = glBalC.subtract(glBalD);
					}
					
					if(rptBsEntity.getPosition().equals("L")) {
						rptBsEntity.setBlock1L(result);
					}else {
						rptBsEntity.setBlock1R(result);
					}
				}
				
			}
			rptBsRepo.save(rptBsEntity);
		}
	}
	
	public void setBlock2 (int month, String year) {
		logger.info("Rpt Bs setBlock2");
		List<RptBsEntity> listRptBsEntities = (List<RptBsEntity>) rptBsRepo.findAll(new Sort(Sort.Direction.ASC, "rowRpt"));

		for (RptBsEntity rptBsEntity : listRptBsEntities) {
			if (rptBsEntity.getCoaCode().equals("000000") && rptBsEntity.getCoaGroup() != null) {
				if(rptBsEntity.getCoaGroup().startsWith("R")){
					rptBsEntity.setBlock2R(rptBsRepo.sumBlock2(String.valueOf(rptBsEntity.getCoaGroup().charAt(1))));
				}else if(rptBsEntity.getCoaGroup().startsWith("X")){
					if(rptBsEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptBsRepo.findByCoaGroup("RA").getBlock2R();
						BigDecimal rB = rptBsRepo.findByCoaGroup("RB").getBlock2R();
						BigDecimal rC = rptBsRepo.findByCoaGroup("RC").getBlock2R();
						BigDecimal rD = rptBsRepo.findByCoaGroup("RD").getBlock2R();
						rptBsEntity.setBlock2R(rA.add(rB).subtract(rC).subtract(rD));
					}else {
						rptBsEntity.setBlock2R(
								getValPlReport(rptBsEntity.getCoaCode(), XD, 3));
					}
				}
			}else if(!rptBsEntity.getCoaCode().equals("000000")){
				GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptBsEntity.getCoaCode(), year);
				if(gL0001Entity != null) {
					MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptBsEntity.getCoaCode());
					
					Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
					BigDecimal glBalD = getGlBal.get("glBalD");
					BigDecimal glBalC = getGlBal.get("glBalC");
					BigDecimal result = BigDecimal.ZERO;

					if(mA0004Entity.getCoaNormal().equals('D')) {
						result = glBalD.subtract(glBalC);
					}else {
						result = glBalC.subtract(glBalD);
					}
					
					if(rptBsEntity.getPosition().equals("L")) {
						rptBsEntity.setBlock2L(result);
					}else {
						rptBsEntity.setBlock2R(result);
					}
				}
			}
			rptBsRepo.save(rptBsEntity);
		}
	}
	
	public void setBlock3 (int month, String year) {
		logger.info("Rpt Bs setBlock3");
		List<RptBsEntity> listRptBsEntities = (List<RptBsEntity>) rptBsRepo.findAll(new Sort(Sort.Direction.ASC, "rowRpt"));

		for (RptBsEntity rptBsEntity : listRptBsEntities) {
			if (rptBsEntity.getCoaCode().equals("000000") && rptBsEntity.getCoaGroup() != null) {
				if(rptBsEntity.getCoaGroup().startsWith("R")){
					rptBsEntity.setBlock3R(rptBsRepo.sumBlock3(String.valueOf(rptBsEntity.getCoaGroup().charAt(1))));
				}else if(rptBsEntity.getCoaGroup().startsWith("X")){
					if(rptBsEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptBsRepo.findByCoaGroup("RA").getBlock3R();
						BigDecimal rB = rptBsRepo.findByCoaGroup("RB").getBlock3R();
						BigDecimal rC = rptBsRepo.findByCoaGroup("RC").getBlock3R();
						BigDecimal rD = rptBsRepo.findByCoaGroup("RD").getBlock3R();
						rptBsEntity.setBlock3R(rA.add(rB).subtract(rC).subtract(rD));
					}else {
						rptBsEntity.setBlock3R(
								getValPlReport(rptBsEntity.getCoaCode(), XD, 4));
					}
				}
			}else if(!rptBsEntity.getCoaCode().equals("000000")){
				GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptBsEntity.getCoaCode(), year);
				if(gL0001Entity != null) {
					MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptBsEntity.getCoaCode());
					
					Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
					BigDecimal glBalD = getGlBal.get("glBalD");
					BigDecimal glBalC = getGlBal.get("glBalC");
					BigDecimal result = BigDecimal.ZERO;

					if(mA0004Entity.getCoaNormal().equals('D')) {
						result = glBalD.subtract(glBalC);
					}else {
						result = glBalC.subtract(glBalD);
					}
					
					if(rptBsEntity.getPosition().equals("L")) {
						rptBsEntity.setBlock3L(result);
					}else {
						rptBsEntity.setBlock3R(result);
					}
				}
				
			}
			rptBsRepo.save(rptBsEntity);
		}
	}
	
	public void setBlock4 (int month, String year) {
		logger.info("Rpt Bs setBlock4");
		List<RptBsEntity> listRptBsEntities = (List<RptBsEntity>) rptBsRepo.findAll(new Sort(Sort.Direction.ASC, "rowRpt"));

		for (RptBsEntity rptBsEntity : listRptBsEntities) {
			if (rptBsEntity.getCoaCode().equals("000000") && rptBsEntity.getCoaGroup() != null) {
				if(rptBsEntity.getCoaGroup().startsWith("R")){
					rptBsEntity.setBlock4R(rptBsRepo.sumBlock4(String.valueOf(rptBsEntity.getCoaGroup().charAt(1))));
				}else if(rptBsEntity.getCoaGroup().startsWith("X")){
					if(rptBsEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptBsRepo.findByCoaGroup("RA").getBlock4R();
						BigDecimal rB = rptBsRepo.findByCoaGroup("RB").getBlock4R();
						BigDecimal rC = rptBsRepo.findByCoaGroup("RC").getBlock4R();
						BigDecimal rD = rptBsRepo.findByCoaGroup("RD").getBlock4R();
						rptBsEntity.setBlock4R(rA.add(rB).subtract(rC).subtract(rD));
					}else {
						rptBsEntity.setBlock4R(
								getValPlReport(rptBsEntity.getCoaCode(), XD, 5));
					}
				}
			}else if(!rptBsEntity.getCoaCode().equals("000000")){
				GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptBsEntity.getCoaCode(), year);
				if(gL0001Entity != null) {
					MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptBsEntity.getCoaCode());
					
					Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
					BigDecimal glBalD = getGlBal.get("glBalD");
					BigDecimal glBalC = getGlBal.get("glBalC");
					BigDecimal result = BigDecimal.ZERO;

					if(mA0004Entity.getCoaNormal().equals('D')) {
						result = glBalD.subtract(glBalC);
					}else {
						result = glBalC.subtract(glBalD);
					}
					
					if(rptBsEntity.getPosition().equals("L")) {
						rptBsEntity.setBlock4L(result);
					}else {
						rptBsEntity.setBlock4R(result);
					}
				}
				
			}
			rptBsRepo.save(rptBsEntity);
		}
	}
	
	public void setBlock5 (int month, String year) {
		logger.info("Rpt Bs setBlock5");
		List<RptBsEntity> listRptBsEntities = (List<RptBsEntity>) rptBsRepo.findAll(new Sort(Sort.Direction.ASC, "rowRpt"));

		for (RptBsEntity rptBsEntity : listRptBsEntities) {
			if (rptBsEntity.getCoaCode().equals("000000") && rptBsEntity.getCoaGroup() != null) {
				if(rptBsEntity.getCoaGroup().startsWith("R")){
					rptBsEntity.setBlock5R(rptBsRepo.sumBlock5(String.valueOf(rptBsEntity.getCoaGroup().charAt(1))));
				}else if(rptBsEntity.getCoaGroup().startsWith("X")){
					if(rptBsEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptBsRepo.findByCoaGroup("RA").getBlock5R();
						BigDecimal rB = rptBsRepo.findByCoaGroup("RB").getBlock5R();
						BigDecimal rC = rptBsRepo.findByCoaGroup("RC").getBlock5R();
						BigDecimal rD = rptBsRepo.findByCoaGroup("RD").getBlock5R();
						rptBsEntity.setBlock5R(rA.add(rB).subtract(rC).subtract(rD));
					}else {
						rptBsEntity.setBlock5R(
								getValPlReport(rptBsEntity.getCoaCode(), XD, 6));
					}
				}
			}else if(!rptBsEntity.getCoaCode().equals("000000")){
				GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptBsEntity.getCoaCode(), year);
				if(gL0001Entity != null) {
					MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptBsEntity.getCoaCode());
					
					Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
					BigDecimal glBalD = getGlBal.get("glBalD");
					BigDecimal glBalC = getGlBal.get("glBalC");
					BigDecimal result = BigDecimal.ZERO;

					if(mA0004Entity.getCoaNormal().equals('D')) {
						result = glBalD.subtract(glBalC);
					}else {
						result = glBalC.subtract(glBalD);
					}
					
					if(rptBsEntity.getPosition().equals("L")) {
						rptBsEntity.setBlock5L(result);
					}else {
						rptBsEntity.setBlock5R(result);
					}
				}
				
			}
			rptBsRepo.save(rptBsEntity);
		}
	}

	private Map<String, BigDecimal> getGlBal(GL0001Entity gL0001Entity, int month){
		BigDecimal glBalD = BigDecimal.ZERO;
		BigDecimal glBalC = BigDecimal.ZERO;
		if(month == 1) {
			glBalC = gL0001Entity.getGlBalCIdr1();
			glBalD = gL0001Entity.getGlBalDIdr1();
		}else if(month == 2) {
			glBalC = gL0001Entity.getGlBalCIdr2();
			glBalD = gL0001Entity.getGlBalDIdr2();
		}else if(month == 3) {
			glBalC = gL0001Entity.getGlBalCIdr3();
			glBalD = gL0001Entity.getGlBalDIdr3();
		}else if(month == 4) {
			glBalC = gL0001Entity.getGlBalCIdr4();
			glBalD = gL0001Entity.getGlBalDIdr4();
		}else if(month == 5) {
			glBalC = gL0001Entity.getGlBalCIdr5();
			glBalD = gL0001Entity.getGlBalDIdr5();
		}else if(month == 6) {
			glBalC = gL0001Entity.getGlBalCIdr6();
			glBalD = gL0001Entity.getGlBalDIdr6();
		}else if(month == 7) {
			glBalC = gL0001Entity.getGlBalCIdr7();
			glBalD = gL0001Entity.getGlBalDIdr7();
		}else if(month == 8) {
			glBalC = gL0001Entity.getGlBalCIdr8();
			glBalD = gL0001Entity.getGlBalDIdr8();
		}else if(month == 9) {
			glBalC = gL0001Entity.getGlBalCIdr9();
			glBalD = gL0001Entity.getGlBalDIdr9();
		}else if(month == 10) {
			glBalC = gL0001Entity.getGlBalCIdr10();
			glBalD = gL0001Entity.getGlBalDIdr10();
		}else if(month == 11) {
			glBalC = gL0001Entity.getGlBalCIdr11();
			glBalD = gL0001Entity.getGlBalDIdr11();
		}else if(month == 12) {
			glBalC = gL0001Entity.getGlBalCIdr12();
			glBalD = gL0001Entity.getGlBalDIdr12();
		}

		Map<String , BigDecimal> result = new HashMap<String, BigDecimal>();
		result.put("glBalD", glBalD);
		result.put("glBalC", glBalC);

		return result;
	}
	
	private BigDecimal getValPlReport(String coaCode, String coaGroup, Integer block) {
		BigDecimal value = BigDecimal.ZERO;
		
		List<RptPlEntity> rptPlEntities = rptPlRepo.findByCoaCodeAndCoaGroup(coaCode, coaGroup);
		if(rptPlEntities.size() > 0) {
			RptPlEntity pl = rptPlEntities.get(0);
			if(block == 1) 
				value = pl.getBlock1();
			else if(block == 2) 
				value = pl.getBlock2();
			else if(block == 3) 
				value = pl.getBlock3();
			else if(block == 4) 
				value = pl.getBlock4();
			else if(block == 5) 
				value = pl.getBlock5();
			else if(block == 6) 
				value = pl.getBlock6();
		}else {
			logger.error("Report RPT_BS failed get RptPlEntity for coaCode : {}, groupCode : {}!", coaCode, coaGroup);
		}
		
		return value;
	}
	
}
