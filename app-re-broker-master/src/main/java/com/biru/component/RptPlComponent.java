package com.biru.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import com.biru.common.param.Param;
import com.biru.entity.GL0001Entity;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0010Entity;
import com.biru.entity.RptPlEntity;
import com.biru.repository.GL0001Repo;
import com.biru.repository.GL0002Repo;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0010Repo;
import com.biru.repository.RptPlRepo;

@Component
public class RptPlComponent {

	@Autowired
	private RptPlRepo rptPlRepo;

	@Autowired
	private GL0001Repo gL0001Repo;
	
	@Autowired
	private GL0002Repo gL0002Repo;

	@Autowired
	private MA0004Repo mA0004Repo;

	@Autowired
	private MA0010Repo mA0010Repo;

	@Autowired
	private DateComponent dateComponent;

	@Autowired
	private ReportUtils reportUtils;

	@Transactional
	public void setupReport(Map<String, Object>param) {
		int month = Param.getInt(param, "month");
		Integer year = Param.getInt(param, "year");

		rptPlRepo.resetRptpl();

		setBlock1(month, String.valueOf(year));
		setBlock2(String.valueOf(year));
		setBlock3(12, String.valueOf(year-1));
		setBlock4(12, String.valueOf(year-2));
		setBlock5(12, String.valueOf(year-3));
		setBlock6(12, String.valueOf(year-4));
		setXCXD();
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
			return reportUtils.exportHtml("RptPl.jrxml", param);
		}else if(type.equals("print")) {
			return reportUtils.exportPdf("RptPl.jrxml", param);
		}else {
			return reportUtils.exportExcel("RptPl.jrxml", param);
		}
	}

	private void setBlock1 (int month, String year) {
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "rowRpt");
		List<RptPlEntity> listRptPlEntities = rptPlRepo.findAll(pageable).getContent();

		for (RptPlEntity rptPlEntity : listRptPlEntities) {
			if(!rptPlEntity.getCoaGroup().equals("XC") && !rptPlEntity.getCoaGroup().equals("XD")) {
				if (rptPlEntity.getCoaCode().equals("000000") && rptPlEntity.getCoaGroup() != null) {
					if(rptPlEntity.getCoaGroup().startsWith("R")){
						rptPlEntity.setBlock1(rptPlRepo.sumBlock1(String.valueOf(rptPlEntity.getCoaGroup().charAt(1))));
					}else if(rptPlEntity.getCoaGroup().startsWith("X")){
						if(rptPlEntity.getCoaGroup().endsWith("A")) {
							BigDecimal rA = rptPlRepo.findByCoaGroup("RA").getBlock1();
							BigDecimal rB = rptPlRepo.findByCoaGroup("RB").getBlock1();
							rptPlEntity.setBlock1(rA.subtract(rB));
						}else {
							BigDecimal RC = rptPlRepo.findByCoaGroup("RC").getBlock1();
							BigDecimal XA = rptPlRepo.findByCoaGroup("XA").getBlock1();
							rptPlEntity.setBlock1(XA.subtract(RC));
						}
					}
				}else if(!rptPlEntity.getCoaCode().equals("000000")){
					GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptPlEntity.getCoaCode(), year);
					if(gL0001Entity != null) {
						MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptPlEntity.getCoaCode());

						Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
						BigDecimal glBalD = getGlBal.get("glBalD");
						BigDecimal glBalC = getGlBal.get("glBalC");
						BigDecimal result = BigDecimal.ZERO;

						if(mA0004Entity.getCoaNormal().equals('D')) {
							result = glBalD.subtract(glBalC);
						}else {
							result = glBalC.subtract(glBalD);
						}
						rptPlEntity.setBlock1(result);

					}
				}
				rptPlRepo.save(rptPlEntity);
			}
		}
	}

	private void setBlock2 (String year) {
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "rowRpt");
		List<RptPlEntity> listRptPlEntities = rptPlRepo.findAll(pageable).getContent();

		for (RptPlEntity rptPlEntity : listRptPlEntities) {
			if(!rptPlEntity.getCoaGroup().equals("XC") && !rptPlEntity.getCoaGroup().equals("XD")) {

			if (rptPlEntity.getCoaCode().equals("000000") && rptPlEntity.getCoaGroup() != null) {
				if(rptPlEntity.getCoaGroup().startsWith("R")){
					rptPlEntity.setBlock2(rptPlRepo.sumBlock2(String.valueOf(rptPlEntity.getCoaGroup().charAt(1))));
				}else if(rptPlEntity.getCoaGroup().startsWith("X")){
					if(rptPlEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptPlRepo.findByCoaGroup("RA").getBlock2();
						BigDecimal rB = rptPlRepo.findByCoaGroup("RB").getBlock2();
						rptPlEntity.setBlock2(rA.subtract(rB));
					}else {
						BigDecimal RC = rptPlRepo.findByCoaGroup("RC").getBlock2();
						BigDecimal XA = rptPlRepo.findByCoaGroup("XA").getBlock2();
						rptPlEntity.setBlock2(XA.subtract(RC));
					}
				}
			}else if(!rptPlEntity.getCoaCode().equals("000000")){
				BigDecimal tbAmount = gL0002Repo.sumTbAmountByCoaCodeAndTbYear(rptPlEntity.getCoaCode(), year);
				if(tbAmount != null) {
					rptPlEntity.setBlock2(tbAmount);
				}
			}
			rptPlRepo.save(rptPlEntity);
		}}
	}

	private void setBlock3 (int month, String year) {
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "rowRpt");
		List<RptPlEntity> listRptPlEntities = rptPlRepo.findAll(pageable).getContent();

		for (RptPlEntity rptPlEntity : listRptPlEntities) {
			if(!rptPlEntity.getCoaGroup().equals("XC") && !rptPlEntity.getCoaGroup().equals("XD")) {

			if (rptPlEntity.getCoaCode().equals("000000") && rptPlEntity.getCoaGroup() != null) {
				if(rptPlEntity.getCoaGroup().startsWith("R")){
					rptPlEntity.setBlock3(rptPlRepo.sumBlock3(String.valueOf(rptPlEntity.getCoaGroup().charAt(1))));
				}else if(rptPlEntity.getCoaGroup().startsWith("X")){
					if(rptPlEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptPlRepo.findByCoaGroup("RA").getBlock3();
						BigDecimal rB = rptPlRepo.findByCoaGroup("RB").getBlock3();
						rptPlEntity.setBlock3(rA.subtract(rB));
					}else {
						BigDecimal RC = rptPlRepo.findByCoaGroup("RC").getBlock3();
						BigDecimal XA = rptPlRepo.findByCoaGroup("XA").getBlock3();
						rptPlEntity.setBlock3(XA.subtract(RC));
					}
				}
			}else if(!rptPlEntity.getCoaCode().equals("000000")){
				GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptPlEntity.getCoaCode(), year);
				if(gL0001Entity != null) {
					MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptPlEntity.getCoaCode());

					Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
					BigDecimal glBalD = getGlBal.get("glBalD");
					BigDecimal glBalC = getGlBal.get("glBalC");
					BigDecimal result = BigDecimal.ZERO;

					if(mA0004Entity.getCoaNormal().equals('D')) {
						result = glBalD.subtract(glBalC);
					}else {
						result = glBalC.subtract(glBalD);
					}
					rptPlEntity.setBlock3(result);

				}
			}
			rptPlRepo.save(rptPlEntity);
		}}
	}

	private void setBlock4 (int month, String year) {
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "rowRpt");
		List<RptPlEntity> listRptPlEntities = rptPlRepo.findAll(pageable).getContent();

		for (RptPlEntity rptPlEntity : listRptPlEntities) {
			if(!rptPlEntity.getCoaGroup().equals("XC") && !rptPlEntity.getCoaGroup().equals("XD")) {

			if (rptPlEntity.getCoaCode().equals("000000") && rptPlEntity.getCoaGroup() != null) {
				if(rptPlEntity.getCoaGroup().startsWith("R")){
					rptPlEntity.setBlock4(rptPlRepo.sumBlock4(String.valueOf(rptPlEntity.getCoaGroup().charAt(1))));
				}else if(rptPlEntity.getCoaGroup().startsWith("X")){
					if(rptPlEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptPlRepo.findByCoaGroup("RA").getBlock4();
						BigDecimal rB = rptPlRepo.findByCoaGroup("RB").getBlock4();
						rptPlEntity.setBlock4(rA.subtract(rB));
					}else {
						BigDecimal RC = rptPlRepo.findByCoaGroup("RC").getBlock4();
						BigDecimal XA = rptPlRepo.findByCoaGroup("XA").getBlock4();
						rptPlEntity.setBlock4(XA.subtract(RC));
					}
				}
			}else if(!rptPlEntity.getCoaCode().equals("000000")){
				GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptPlEntity.getCoaCode(), year);
				if(gL0001Entity != null) {
					MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptPlEntity.getCoaCode());

					Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
					BigDecimal glBalD = getGlBal.get("glBalD");
					BigDecimal glBalC = getGlBal.get("glBalC");
					BigDecimal result = BigDecimal.ZERO;

					if(mA0004Entity.getCoaNormal().equals('D')) {
						result = glBalD.subtract(glBalC);
					}else {
						result = glBalC.subtract(glBalD);
					}
					rptPlEntity.setBlock4(result);

				}
			}
			rptPlRepo.save(rptPlEntity);
		}}
	}

	private void setBlock5 (int month, String year) {
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "rowRpt");
		List<RptPlEntity> listRptPlEntities = rptPlRepo.findAll(pageable).getContent();

		for (RptPlEntity rptPlEntity : listRptPlEntities) {
			if(!rptPlEntity.getCoaGroup().equals("XC") && !rptPlEntity.getCoaGroup().equals("XD")) {

			if (rptPlEntity.getCoaCode().equals("000000") && rptPlEntity.getCoaGroup() != null) {
				if(rptPlEntity.getCoaGroup().startsWith("R")){
					rptPlEntity.setBlock5(rptPlRepo.sumBlock5(String.valueOf(rptPlEntity.getCoaGroup().charAt(1))));
				}else if(rptPlEntity.getCoaGroup().startsWith("X")){
					if(rptPlEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptPlRepo.findByCoaGroup("RA").getBlock5();
						BigDecimal rB = rptPlRepo.findByCoaGroup("RB").getBlock5();
						rptPlEntity.setBlock5(rA.subtract(rB));
					}else {
						BigDecimal RC = rptPlRepo.findByCoaGroup("RC").getBlock5();
						BigDecimal XA = rptPlRepo.findByCoaGroup("XA").getBlock5();
						rptPlEntity.setBlock5(XA.subtract(RC));
					}
				}
			}else if(!rptPlEntity.getCoaCode().equals("000000")){
				GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptPlEntity.getCoaCode(), year);
				if(gL0001Entity != null) {
					MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptPlEntity.getCoaCode());

					Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
					BigDecimal glBalD = getGlBal.get("glBalD");
					BigDecimal glBalC = getGlBal.get("glBalC");
					BigDecimal result = BigDecimal.ZERO;

					if(mA0004Entity.getCoaNormal().equals('D')) {
						result = glBalD.subtract(glBalC);
					}else {
						result = glBalC.subtract(glBalD);
					}
					rptPlEntity.setBlock5(result);

				}
			}
			rptPlRepo.save(rptPlEntity);
		}}
	}
	
	private void setBlock6 (int month, String year) {
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "rowRpt");
		List<RptPlEntity> listRptPlEntities = rptPlRepo.findAll(pageable).getContent();

		for (RptPlEntity rptPlEntity : listRptPlEntities) {
			if(!rptPlEntity.getCoaGroup().equals("XC") && !rptPlEntity.getCoaGroup().equals("XD")) {

			if (rptPlEntity.getCoaCode().equals("000000") && rptPlEntity.getCoaGroup() != null) {
				if(rptPlEntity.getCoaGroup().startsWith("R")){
					rptPlEntity.setBlock6(rptPlRepo.sumBlock6(String.valueOf(rptPlEntity.getCoaGroup().charAt(1))));
				}else if(rptPlEntity.getCoaGroup().startsWith("X")){
					if(rptPlEntity.getCoaGroup().endsWith("A")) {
						BigDecimal rA = rptPlRepo.findByCoaGroup("RA").getBlock6();
						BigDecimal rB = rptPlRepo.findByCoaGroup("RB").getBlock6();
						rptPlEntity.setBlock6(rA.subtract(rB));
					}else {
						BigDecimal RC = rptPlRepo.findByCoaGroup("RC").getBlock6();
						BigDecimal XA = rptPlRepo.findByCoaGroup("XA").getBlock6();
						rptPlEntity.setBlock6(XA.subtract(RC));
					}
				}
			}else if(!rptPlEntity.getCoaCode().equals("000000")){
				GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(rptPlEntity.getCoaCode(), year);
				if(gL0001Entity != null) {
					MA0004Entity mA0004Entity = mA0004Repo.findByCoaCode(rptPlEntity.getCoaCode());

					Map<String, BigDecimal> getGlBal = getGlBal(gL0001Entity, month);
					BigDecimal glBalD = getGlBal.get("glBalD");
					BigDecimal glBalC = getGlBal.get("glBalC");
					BigDecimal result = BigDecimal.ZERO;

					if(mA0004Entity.getCoaNormal().equals('D')) {
						result = glBalD.subtract(glBalC);
					}else {
						result = glBalC.subtract(glBalD);
					}
					rptPlEntity.setBlock6(result);

				}
			}
			rptPlRepo.save(rptPlEntity);
		}}
	}
	
	private void setXCXD() {
		List<String>rowRpt = new ArrayList<String>();
		Collections.addAll(rowRpt, "XC", "XD");
		Pageable pageable = PageRequest.of(0, 10, Sort.Direction.ASC, "rowRpt");
		List<RptPlEntity> listRptPlEntities = rptPlRepo.findByCoaGroupIn(rowRpt, pageable).getContent();
		RptPlEntity RD = rptPlRepo.findByCoaGroup("RD");
		for (int i = 6; i > 0; i--) {
			setXCValue(listRptPlEntities.get(0), listRptPlEntities.get(1), i);
			setXDValue(listRptPlEntities.get(1), getRdValue(RD, i), i);
		}
	
		rptPlRepo.saveAll(listRptPlEntities);
	}
	
	private void setXCValue(RptPlEntity xc, RptPlEntity xd, int block) {
		if(block == 6) {
			xc.setBlock6(BigDecimal.ZERO);
		}else if(block == 5) {
			xc.setBlock5(xd.getBlock6());
		}else if(block == 4) {
			xc.setBlock4(xd.getBlock5());
		}else if(block == 3) {
			xc.setBlock3(xd.getBlock4());
		}else if(block == 2) {
			xc.setBlock2(BigDecimal.ZERO);
		}else if(block == 1) {
			xc.setBlock1(xd.getBlock3());
		}
		
	}
	
	private void setXDValue(RptPlEntity rptPlEntity, BigDecimal RD, int block) {
		BigDecimal XB = BigDecimal.ZERO;
		BigDecimal XC = BigDecimal.ZERO;
		if(block == 1) {
			XB = rptPlRepo.findByCoaGroup("XB").getBlock1();
			XC = rptPlRepo.findByCoaGroup("XC").getBlock1();
			rptPlEntity.setBlock1(XB.add(XC).add(RD));
		}else if(block == 2) {
			rptPlEntity.setBlock2(BigDecimal.ZERO);
		}else if(block == 3) {
			XB = rptPlRepo.findByCoaGroup("XB").getBlock3();
			XC = rptPlRepo.findByCoaGroup("XC").getBlock3();
			rptPlEntity.setBlock3(XB.add(XC).add(RD));
		}else if(block == 4) {
			XB = rptPlRepo.findByCoaGroup("XB").getBlock4();
			XC = rptPlRepo.findByCoaGroup("XC").getBlock4();
			rptPlEntity.setBlock4(XB.add(XC).add(RD));
		}else if(block == 5) {
			XB = rptPlRepo.findByCoaGroup("XB").getBlock5();
			XC = rptPlRepo.findByCoaGroup("XC").getBlock5();
			rptPlEntity.setBlock5(XB.add(XC).add(RD));
		}else if(block == 6) {
			XB = rptPlRepo.findByCoaGroup("XB").getBlock6();
			XC = rptPlRepo.findByCoaGroup("XC").getBlock6();
			rptPlEntity.setBlock6(XB.add(XC).add(RD));
		}
	}
	
	private BigDecimal getRdValue(RptPlEntity rptPlEntity, int block) {
		if(block == 6) {
			return rptPlEntity.getBlock6();
		}else if(block == 5) {
			return rptPlEntity.getBlock5();
		}else if(block == 4) {
			return rptPlEntity.getBlock4();
		}else if(block == 3) {
			return rptPlEntity.getBlock3();
		}else if(block == 2) {
			return rptPlEntity.getBlock2();
		}else if(block == 1) {
			return rptPlEntity.getBlock1();
		}
		
		return null;
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
}
