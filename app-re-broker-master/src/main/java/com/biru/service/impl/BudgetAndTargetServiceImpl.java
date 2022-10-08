package com.biru.service.impl;

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
import com.biru.component.BudgetAndTargetComponent;
import com.biru.component.ReportUtils;
import com.biru.component.ResultComponent;
import com.biru.entity.GL0002Entity;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0012Entity;
import com.biru.repository.GL0002Repo;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0012Repo;
import com.biru.service.BudgetAndTargetService;

@Service
public class BudgetAndTargetServiceImpl implements BudgetAndTargetService {
	
	@Autowired
	private GL0002Repo gl0002Repo;

	@Autowired
	private MA0004Repo ma0004Repo;
	
	@Autowired
	private MA0012Repo ma0012Repo;
	
	@Autowired
	private BudgetAndTargetComponent budgetAndTargetComponent;
	
	@Autowired
	private ResultComponent resultComponent;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private MA0004Repo mA0004Repo;

	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String tbYear = Param.getStrWithDef(p_Param, "tbYear");
		System.out.println(p_Param);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<GL0002Entity> data = null;
		if(tbYear.equals("")){
			data = gl0002Repo.findAll(pageable);
		}else {
			data = gl0002Repo.findByTbYear(tbYear, pageable);
		}
		for (GL0002Entity gl0002Entity : data.getContent()) {
			if(gl0002Entity.getSaCode() != null && !("").equals(gl0002Entity.getSaCode())) {
				MA0012Entity ma0012entity = ma0012Repo.findBySaCode(gl0002Entity.getSaCode());
				gl0002Entity.setSaName(ma0012entity.getSaName());
			}else {
				gl0002Entity.setSaName("");
			}
			MA0004Entity ma0004entity = ma0004Repo.findByCoaCode(gl0002Entity.getCoaCode());
			gl0002Entity.setDescription(ma0004entity.getCoaDescript());
		}
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object save(Map<String, Object> param) {
		// TODO Auto-generated method stub
		try {
			budgetAndTargetComponent.save(param);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return resultComponent.createResponse(e);
		}
	}

	@Override
	public Object exportExcel(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return reportUtils.exportExcel("BudgetAndTarget.jrxml", param);
	}
	
	@Override
	public Object coa(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String filterKey = Param.getStr(param, PARAM.FILTER_KEY);
		String filterValue = Param.getStr(param, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0004Entity> data = null;
		if (filterKey != null) {
			if(filterKey.equals(PARAM.KEY.COA_CODE)) {
				data = mA0004Repo.findByCoaHeaderAndCoaDataStatusAndCoaCodeContains('D', "11", filterValue, pageable);
			}else {
				data = mA0004Repo.findByCoaHeaderAndCoaDataStatusAndCoaDescriptContains('D', "11", filterValue, pageable);
			}
		}else {
			data = mA0004Repo.findByCoaHeaderAndCoaDataStatus('D', "11", pageable);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}
	
}

