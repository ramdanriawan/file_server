package com.biru.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.biru.entity.MA0013Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0017Entity;
import com.biru.entity.MA0018Entity;
import com.biru.repository.MA0013Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0017Repo;
import com.biru.repository.MA0018Repo;
import com.biru.service.BusinessRulesService;
import com.biru.service.ParameterService;

@Service
public class ParameterServiceImpl implements ParameterService {
	
	@Autowired
	private MA0013Repo ma0013repo;
	
	@Autowired
	private MA0014Repo ma0014repo;
	
	@Override
	public Object save(Map<String, Object> p_Param) {
		String paChildCode = Param.getStr(p_Param, "paChildCode");
		String paChildDesc = Param.getStr(p_Param, "paChildDesc");
		String paChildValue = Param.getStr(p_Param, "paChildValue");
		String paChildStatus = Param.getStr(p_Param, "paChildStatus");
		String paParentCode = Param.getStr(p_Param, "paParentCode");
		
		
		Long idKey = Param.getLong(p_Param, "paIdKey");
		idKey = (idKey == null) ? -99l : idKey;
		
		MA0014Entity ma0014Entity = ma0014repo.findByPaChildCode(paChildCode);
		
		
		if (ma0014Entity == null) {
			ma0014Entity = new MA0014Entity();
		}
		ma0014Entity.setPaParentCode(paParentCode);
		ma0014Entity.setPaChildCode(paChildCode);
		ma0014Entity.setPaChildDesc(paChildDesc);
		ma0014Entity.setPaChildValue(paChildValue);
		ma0014Entity.setPaChildStatus(paChildStatus);
		
		
		ma0014repo.save(ma0014Entity);
		
		HashMap<String, Object> returnValue = new HashMap<String, Object>();
		
		returnValue.put("paChildCode", ma0014Entity.getPaChildCode());
		returnValue.put("paChildDesc", ma0014Entity.getPaChildDesc());
		returnValue.put("paChildValue", ma0014Entity.getPaChildValue());
		returnValue.put("paChildStatus", ma0014Entity.getPaChildStatus());
		
		return returnValue;
	}

	
	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String filterKey = Param.getStr(p_Param, PARAM.FILTER_KEY);
		String filterValue = Param.getStr(p_Param, PARAM.FILTER_VALUE);
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0013Entity> data = null;
		if (filterKey != null) {
			if(filterKey.equals(PARAM.KEY.PA_CODE)) {
				data = ma0013repo.findByPaParentCodeContains(filterValue, pageable);
			}else {
				data = ma0013repo.findByPaParentDescContains(filterValue, pageable);
			}
		}else {
			data = ma0013repo.findByFilter("", pageable);
		}
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public DatatableSet inquiryChild(Map<String, Object> p_Param) {
		System.out.println(p_Param);
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String paParentCode = Param.getStr(p_Param, "paParentCode");
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<MA0014Entity> data = ma0014repo.findByPaParentCode(paParentCode, pageable);
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}
}
