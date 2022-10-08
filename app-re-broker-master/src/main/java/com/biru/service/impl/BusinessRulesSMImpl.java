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
import com.biru.entity.MA0017Entity;
import com.biru.entity.MA0018Entity;
import com.biru.repository.MA0017Repo;
import com.biru.repository.MA0018Repo;
import com.biru.service.BusinessRulesService;

@Service
public class BusinessRulesSMImpl implements BusinessRulesService{

	@Autowired
	private MA0017Repo ma0017repo;
	
	@Autowired
	private MA0018Repo ma0018repo;
	
	@Override
	public Object save(Map<String, Object> p_Param) {
		String user = Param.getStr(p_Param, Param.USER);
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		String brCode = Param.getStr(p_Param, "brCode");
		String brParentDesc = Param.getStr(p_Param, "brParentDesc");
		String brCurrId = Param.getStr(p_Param, "brParentGroup");
		String brDataStatus = Param.getStr(p_Param, "brDataStatus");
		
		Long idKey = Param.getLong(p_Param, "brIdKey");
		idKey = (idKey == null) ? -99l : idKey;
		
		MA0017Entity ma0017Entity = ma0017repo.findByIdKey(idKey);
		List<MA0018Entity> listOfMA0018Entity = new ArrayList<MA0018Entity>();
		
		if (ma0017Entity == null){
			ma0017Entity = new MA0017Entity();
		} else {
			ma0018repo.deleteByBrCode(ma0017Entity.getBrCode());
		}
		
		ArrayList<HashMap<String, Object>> rows = (ArrayList<HashMap<String, Object>>) p_Param.get("rows");
		
		for(HashMap<String, Object> rowsData : rows) {
			String brCodeChild = rowsData.get("brChildCoa").toString();
			String brPosition = rowsData.get("brChildDc").toString();
			String brValue = rowsData.get("brChildValue").toString();
			String brCoaDescript = rowsData.get("brCoaDescript").toString();
			//Character brChildDC = null;
			
			MA0018Entity ma0018Entity = new MA0018Entity();
			
			ma0018Entity.setBrChildCoa(brCodeChild);
			ma0018Entity.setBrChildDc(brPosition.charAt(0));
			ma0018Entity.setBrChildValue(brValue);
			ma0018Entity.setBrCode(brCode);
			
			listOfMA0018Entity.add(ma0018Entity);
		}
		
		ma0017Entity.setBrCode(brCode);
		ma0017Entity.setBrDataStatus(brDataStatus);
		ma0017Entity.setBrParentDesc(brParentDesc);
		ma0017Entity.setBrCurrId(brCurrId);
		ma0017Entity.setCreateBy(user);
		ma0017Entity.setCreateOn(now);
		ma0017Entity.setModifyBy(user);
		ma0017Entity.setModifyOn(now);
		
		ma0017repo.save(ma0017Entity);
		ma0018repo.saveAll(listOfMA0018Entity);
		
		HashMap<String, Object> returnValue = new HashMap<String, Object>();
		
		returnValue.put("brCode", ma0017Entity.getBrCode());
		returnValue.put("brDataStatus", ma0017Entity.getBrDataStatus());
		returnValue.put("brParentDesc", ma0017Entity.getBrParentDesc());
		returnValue.put("brParentGroup", ma0017Entity.getBrCurrId());
		returnValue.put("ma0018asChild", listOfMA0018Entity);
		
		return returnValue;
	}

	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String filterValue = Param.getStr(p_Param, PARAM.FILTER_VALUE);
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<MA0017Entity> data = ma0017repo.findBusinessRules(filterValue.toLowerCase(), pageable);
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public DatatableSet inquiryChild(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String brCode = Param.getStr(p_Param, "brCode");
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<MA0018Entity> data = ma0018repo.findByBrCode(brCode, pageable);
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

}
