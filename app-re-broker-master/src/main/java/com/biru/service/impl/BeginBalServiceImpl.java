package com.biru.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.ReBrokerConstants.PARAM.KEY;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.entity.GL0001Entity;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0015Entity;
import com.biru.helper.GL0001Helper;
import com.biru.repository.GL0001Repo;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0015Repo;
import com.biru.service.BeginBalService;

@Service
public class BeginBalServiceImpl implements BeginBalService {
	
	@Autowired
	private GL0001Repo gl0001Repo;

	@Autowired
	private MA0004Repo ma0004Repo;
	
	@Autowired
	private MA0015Repo ma0015Repo;
	
	@Override
	public List<DropdownIdText> getYear() {
		return gl0001Repo.findYear();
	}
	
	@Override
	public DatatableSet inquiryV1(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String glBalYear = Param.getStrWithDef(p_Param, "glBalYear");
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<GL0001Helper> data = gl0001Repo.findBeginBal(glBalYear +"%", pageable);
		
		GL0001Helper helper = summaryByYear(glBalYear);
		List<GL0001Helper> list = new ArrayList<GL0001Helper>(data.getContent());
		list.add(helper);
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), list);
	}
	
	@Override
	public List<GL0001Helper> inquiryV2(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String glBalYear = Param.getStrWithDef(p_Param, "glBalYear");
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<GL0001Helper> data = gl0001Repo.findBeginBal(glBalYear +"%", pageable);
		
		GL0001Helper helper = summaryByYear(glBalYear);
		List<GL0001Helper> list = new ArrayList<GL0001Helper>(data.getContent());
		list.add(helper);
		
		return list;
	}

	@Override
	public Boolean save(Map<String, Object> p_Param) {
		String user = Param.getStr(p_Param, Param.USER);
		String coaCode = Param.getStr(p_Param, "coaCode");
		String glBalYear = Param.getStr(p_Param, "glBalYear");
		BigDecimal glBalDebit0 = Param.getBdWithDef(p_Param, "glBalDebit0");
		BigDecimal glBalCredit0 = Param.getBdWithDef(p_Param, "glBalCredit0");
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		Boolean isValid = validationValue(glBalDebit0, glBalCredit0);
		
		if(!isValid)
			return Boolean.FALSE;
		
		GL0001Entity entity = gl0001Repo.findByCoaCodeAndGlBalYear(coaCode, glBalYear);
		if(entity==null) {
			entity = new GL0001Entity();
			entity.setCreateBy(user);
			entity.setCreateOn(now);
		}else {
			entity.setModifyBy(user);
			entity.setModifyOn(now);
		}
		
		entity.setCoaCode(coaCode);
		entity.setGlBalYear(glBalYear);
		entity.setGlBalDebit0(glBalDebit0);
		entity.setGlBalCredit0(glBalCredit0);
		
		MA0015Entity ma0015 = ma0015Repo.findByCoaCodeAndExMonthAndExYear(coaCode, 12, Integer.valueOf(glBalYear)-1);
		BigDecimal glBalDIdr0 = glBalDebit0.multiply(ma0015.getExRateValue());
		BigDecimal glBalCIdr0 = glBalCredit0.multiply(ma0015.getExRateValue());
		
		entity.setGlBalDIdr0(glBalDIdr0);
		entity.setGlBalCIdr0(glBalCIdr0);
		
		gl0001Repo.save(entity);
		
		return Boolean.TRUE;
	}
	
	private Boolean validationValue(BigDecimal p_Debit, BigDecimal p_Credit) {
		if(p_Debit.compareTo(BigDecimal.ZERO) == 0 && p_Credit.compareTo(BigDecimal.ZERO) == 0)
			return Boolean.FALSE;
		
		if(p_Debit.compareTo(BigDecimal.ZERO) > 0 && p_Credit.compareTo(BigDecimal.ZERO) > 0)
			return Boolean.FALSE;
		
		return Boolean.TRUE;
	}

	@Override
	public BigDecimal sumGlBalDebit0(String p_GlBalYear) {
		return gl0001Repo.sumGlBalDebit0(p_GlBalYear);
	}

	@Override
	public BigDecimal sumGlBalCredit0(String p_GlBalYear) {
		return gl0001Repo.sumGlBalCredit0(p_GlBalYear);
	}
	
	@Override
	public GL0001Helper summaryByYear(String p_GlBalYear) {
		if(StringUtils.isEmpty(p_GlBalYear))
			return gl0001Repo.summaryAll();
		else
			return gl0001Repo.summaryByYear(p_GlBalYear);
	}

	@Override
	public DatatableSet lookupCoa(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String filterKey = Param.getStr(p_Param, PARAM.FILTER_KEY);
		String filterValue = Param.getStr(p_Param, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<MA0004Entity> data = null;
		if(filterKey!=null) {
			if(KEY.COA_CODE.equals(filterKey))
				data = ma0004Repo.findByCoaHeaderAndCoaDataStatusAndCoaCodeContains('D', "11", filterValue, pageable);
			else
				data = ma0004Repo.findByCoaHeaderAndCoaDataStatusAndCoaDescriptContains('D', "11", filterValue, pageable);
		}else {
			data = ma0004Repo.findAll(pageable);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

}
