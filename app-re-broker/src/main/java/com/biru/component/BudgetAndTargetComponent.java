package com.biru.component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.biru.ReBrokerConstants.CODE;
import com.biru.common.param.Param;
import com.biru.entity.GL0002Entity;
import com.biru.entity.MA0014Entity;
import com.biru.repository.GL0002Repo;
import com.biru.repository.MA0014Repo;

@Component
public class BudgetAndTargetComponent {
	@Autowired
	private GL0002Repo gL0002Repo;
	
	@Autowired
	private MA0014Repo ma0014Repo;
	
	@Transactional
	public void save(Map<String, Object> param) throws ParseException {
		System.out.println(param);
		String year = Param.getStrWithDef(param, "year");
		String coa = Param.getStrWithDef(param, "coa");
		String officer = Param.getStrWithDef(param, "officer");
		String month = Param.getStrWithDef(param, "month");
		String amount = Param.getStrWithDef(param, "amount");
		Boolean forEachMonth = (Boolean) param.get("forEachMonth");
		
		MA0014Entity ma0014Entity = ma0014Repo.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);
		SimpleDateFormat formatDateDb = new SimpleDateFormat("MM-dd-yyyy");
		Date appDate = formatDateDb.parse(ma0014Entity.getPaChildValue());
		
		
		if(forEachMonth) {
			gL0002Repo.deleteByTbYearAndCoaCodeAndSaCode(year, coa, officer);
			List<GL0002Entity>listGL0002Entity = new ArrayList<GL0002Entity>();
			for (int i = 0; i < 12; i++) {
				GL0002Entity gL0002Entity = new GL0002Entity();
				gL0002Entity.setCoaCode(coa);
				gL0002Entity.setCreateBy(Param.USER);
				gL0002Entity.setCreateOn(appDate);
				gL0002Entity.setModifyBy(Param.USER);
				gL0002Entity.setModifyOn(appDate);
				gL0002Entity.setSaCode(officer);
				gL0002Entity.setTbAmount(new BigDecimal(amount));
				gL0002Entity.setTbMonth((i+1)+"");
				gL0002Entity.setTbYear(year);
				gL0002Entity.setIdKey(UUID.randomUUID().toString());
				listGL0002Entity.add(gL0002Entity);
			}
			gL0002Repo.saveAll(listGL0002Entity);
		}else {
			gL0002Repo.deleteByTbYearAndTbMonthAndCoaCodeAndSaCode(year, month, coa, officer);
			GL0002Entity gL0002Entity = new GL0002Entity();
			gL0002Entity.setCoaCode(coa);
			gL0002Entity.setCreateBy(Param.USER);
			gL0002Entity.setCreateOn(appDate);
			gL0002Entity.setModifyBy(Param.USER);
			gL0002Entity.setModifyOn(appDate);
			gL0002Entity.setSaCode(officer);
			gL0002Entity.setTbAmount(new BigDecimal(amount));
			gL0002Entity.setTbMonth(month);
			gL0002Entity.setTbYear(year);
			gL0002Entity.setIdKey(UUID.randomUUID().toString());
			gL0002Repo.save(gL0002Entity);
		}
	}
}
