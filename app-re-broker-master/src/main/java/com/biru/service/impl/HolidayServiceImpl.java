package com.biru.service.impl;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.entity.MA0001Entity;
import com.biru.repository.MA0001Repo;
import com.biru.service.HolidayService;

@Service
public class HolidayServiceImpl extends AbstractCommon implements HolidayService {

	@Autowired
	private MA0001Repo ma0001Repo;
	
	@Override
	public Boolean isExsist(String p_Date) throws ParseException {
		return ma0001Repo.findByDate(p_Date) != null;
	}

	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<MA0001Entity> data = ma0001Repo.findHoliday(pageable);
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Boolean save(Map<String, Object> p_Param) throws ParseException {		
		String user = Param.getStr(p_Param, Param.USER);
		String date = Param.getStr(p_Param, "holiDate");
		String desc = Param.getStr(p_Param, "holiDesc");
		String status = Param.getStr(p_Param, "holiDataStatus");
		Boolean isCreate = Param.getBoolean(p_Param, "isCreate");
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		MA0001Entity entity = ma0001Repo.findByDate(date);
		if(entity==null) {
			entity = new MA0001Entity();
			entity.setCreateBy(user);
			entity.setCreateOn(now);
		}else {
			if(isCreate)				//cannot create already exsist
				return Boolean.FALSE;
			entity.setModifyBy(user);
			entity.setModifyOn(now);
		}
		
		entity.setHoliDate(formatDate.parse(date));
		entity.setHoliDesc(desc);
		entity.setHoliDataStatus(status);	
		
		ma0001Repo.save(entity);
		
		return Boolean.TRUE;
	}

}
