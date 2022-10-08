package com.biru.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.entity.MA0015AEntity;
import com.biru.entity.MA0015Entity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0015Repo;
import com.biru.service.ExchangeRateService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;


@Service
public class ExchangeRateServiceImpl implements ExchangeRateService {

	@Autowired
	private MA0015Repo ma0015repo;
	@Autowired
	private MA0015ARepo ma0015Arepo;
	
	@Override
	public DatatableSet inquiryTaxRate(Map<String, Object> p_Param) {

		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String filterValue = Param.getStr(p_Param, PARAM.FILTER_VALUE);
		if(sort.equals("monthStr")){
			sort = "exMonth";
		}else if (sort.equals("dateOnly")) {
			sort = "exDate";
		}
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0015AEntity> data = ma0015Arepo.findByFilter(filterValue, pageable);
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}
	
	@Override	
	public Boolean saveTaxRate(Map<String, Object> p_Param) throws ParseException {
		
		
		String user = Param.getStr(p_Param, Param.USER);
		String exCurrId = Param.getStr(p_Param, "exCurrId");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		BigDecimal exKmkRate = Param.getBdWithDef(p_Param, "exKmkRate");
		Long idKey = Param.getLong(p_Param, "exIdKey");
		String fromDate = Param.getStr(p_Param, "fromDate");
		String toDate = Param.getStr(p_Param, "toDate");
		Date from = sdf.parse(fromDate);
		Date to = sdf.parse(toDate);
		Date start = from;
		Calendar c = Calendar.getInstance();
		c.setTime(to);
		c.add(Calendar.DATE, 1);
		Date x = c.getTime();
		
		while (start.before(x)) {
			Date exDate = start;
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(start);
			MA0015AEntity entity = ma0015Arepo.findByIdKey(idKey);
			if (entity == null){
				entity = new MA0015AEntity();
			}
			Calendar cal = Calendar.getInstance();
			Date now = cal.getTime();
			entity.setExCurrId(exCurrId);
			entity.setExKmkRate(exKmkRate);
			entity.setExDate(exDate);
			entity.setCreateBy(user);
			entity.setCreateOn(now);
			entity.setModifyBy(user);
			entity.setModifyOn(now);
			/**
			 * Save to MA0015
			 * */
			ma0015Arepo.save(entity);
			
			calendar.add(Calendar.DATE, 1);
			start = calendar.getTime();
		}
		
		
		return Boolean.TRUE;
	}
	@Override
	public Boolean save(Map<String, Object> p_Param) throws ParseException {
		String user = Param.getStr(p_Param, Param.USER);
		String exCurrId = Param.getStr(p_Param, "exCurrId");
		Integer exMonth = Param.getInt(p_Param, "exMonth");
		Integer exYear = Param.getInt(p_Param, "exYear");
		BigDecimal exRateValue = Param.getBdWithDef(p_Param, "exRateValue");
		BigDecimal exRateEom = Param.getBdWithDef(p_Param, "exRateEom");
		BigDecimal exRatePro = Param.getBdWithDef(p_Param, "exRatePro");
		BigDecimal exRateTax = Param.getBdWithDef(p_Param, "exRateTax");
		Long idKey = Param.getLong(p_Param, "exIdKey");
		idKey = (idKey == null) ? -99l : idKey;
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		MA0015Entity entity = ma0015repo.findByIdKey(idKey);
		if (entity == null){
			entity = new MA0015Entity();
		}
		
		entity.setExCurrId(exCurrId);
		entity.setExMonth(exMonth);
		entity.setExYear(exYear);
		entity.setExRateValue(exRateValue);
		entity.setExRateEom(exRateEom);
		entity.setExRatePro(exRatePro);
		entity.setExRateTax(exRateTax);
		entity.setCreateBy(user);
		entity.setCreateOn(now);
		entity.setModifyBy(user);
		entity.setModifyOn(now);
		/**
		 * Save to MA0015
		 * */
		ma0015repo.save(entity);
		
		return Boolean.TRUE;
	}
	
	
	
	public static String objToJson(Object obj) {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = "";
		try {
			json = ow.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			System.out.println("ERROR");
		}
		return json;
	}

	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String filterValue = Param.getStr(p_Param, PARAM.FILTER_VALUE);
		if(sort.equals("monthStr")){
			sort = "exMonth";
		}
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<MA0015Entity> data = ma0015repo.inquiryER(filterValue.toLowerCase(), pageable);
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}
	
	@Override
	public Object removeTaxRate(Map<String, Object> p_Param) {
		Long idKey = Long.parseLong(p_Param.get("idKey").toString());
		MA0015AEntity entity = ma0015Arepo.findByIdKey(idKey);
		ma0015Arepo.delete(entity);
		return p_Param;
	}

}
