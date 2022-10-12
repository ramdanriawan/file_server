package com.biru.component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biru.ReBrokerConstants.CODE;
import com.biru.common.AbstractCommon;
import com.biru.entity.MA0014Entity;
import com.biru.repository.MA0001Repo;
import com.biru.repository.MA0014Repo;

@Component
public class BusinessDay extends AbstractCommon {

	@Autowired
	private MA0001Repo ma0001Repo;
	
	@Autowired
	private MA0014Repo ma0014Repo;
	
	public String getNext() throws ParseException {
		MA0014Entity param = ma0014Repo.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);
		Date date = formatDateDb.parse(param.getPaChildValue());
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, 1);
		
		while(!isBusinessDay(cal.getTime())) {
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}
		
		return formatDate.format(cal.getTime());
	}
	
	public Boolean isBusinessDay(Date p_Date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(p_Date);
		
		List<MA0014Entity> params = ma0014Repo.getHolidayParam();
		
		for(MA0014Entity param : params) {
			if(cal.get(Calendar.DAY_OF_WEEK)==Integer.valueOf(param.getPaChildValue()))
				return Boolean.FALSE;
		}
		
		return ma0001Repo.isActiveExsist(formatDate.format(p_Date)) == 0;
	}
	
}
