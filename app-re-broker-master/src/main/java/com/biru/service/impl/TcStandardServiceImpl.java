package com.biru.service.impl;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.ResultComponent;
import com.biru.entity.MA0006Entity;
import com.biru.entity.MA0014Entity;
import com.biru.repository.MA0006Repo;
import com.biru.repository.MA0007Repo;
import com.biru.repository.MA0014Repo;
import com.biru.service.TcStandardService;

@Service
public class TcStandardServiceImpl implements TcStandardService{

	@Autowired
	private MA0006Repo mA0006Repo;
	
	@Autowired
	private MA0014Repo mA0014Repo;
	
	@Autowired
	private MA0007Repo mA0007Repo;
	
	@Autowired
	private ResultComponent resultComponent;
	
	@Override
	public DatatableSet inquiry(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String tcCode = Param.getStr(param, "tcCode");
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0006Entity> pageMA0006Entity = mA0006Repo.findByTcCode(tcCode, pageable);
		
		for (MA0006Entity ma0006Entity : pageMA0006Entity.getContent()) {
			String action = "<button class=\"btn btn-danger\" onclick=\"remove('"+ma0006Entity.getIdKey()+"')\">" 
					+ "<i class=\"fa fa-trash\"></i>" 
					+ "</button>";
			ma0006Entity.setAction(action);
		}
		
		return new DatatableSet(pageMA0006Entity.getTotalElements(), pageMA0006Entity.getTotalElements(), pageMA0006Entity.getContent());
	}
	

	@Override
	public Object save(Map<String, Object> param) {
		// TODO Auto-generated method stub
		try {
			saveProcess(param);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			// TODO: handle exception
			return resultComponent.createResponse(e);
		}
	}
	
	@Transactional
	private void saveProcess(Map<String, Object> param) {
		MA0014Entity mA0014Entity = mA0014Repo.findByPaParentCodeAndPaChildStatusAndPaChildValue("TCGROUP", "11", Param.getStr(param, "group"));
		Integer tcNo = Integer.parseInt(mA0014Entity.getPaChildCode().replace("TCGROUP", ""));
		Calendar now = Calendar.getInstance();
		MA0006Entity mA0006Entity = new MA0006Entity();
		mA0006Entity.setCreateBy(Param.getStr(param, Param.CREATE_BY));
		mA0006Entity.setCreateOn(now.getTime());
		mA0006Entity.setModifyBy(Param.getStr(param, Param.MODIFY_BY));
		mA0006Entity.setModifyOn(now.getTime());
		mA0006Entity.setTcCode(Param.getStr(param, "tcCode"));
		mA0006Entity.setTcDetails(Param.getStr(param, "details"));
		mA0006Entity.setTcGroup(Param.getStr(param, "group"));
		mA0006Entity.setTcNo(tcNo);
		
		mA0006Repo.save(mA0006Entity);
	}

	@Override
	public Object delete(Map<String, Object> param) {
		// TODO Auto-generated method stub
		try {
			deleteProcess(param);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			// TODO: handle exception
			return resultComponent.createResponse(e);
		}
		
	}

	private void deleteProcess(Map<String, Object> param) {
		mA0006Repo.deleteById(Param.getStr(param, "idKey"));
	}
	
	@Override
	public Object description(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("description", mA0007Repo.findByGroupNameAndDetailsName(Param.getStr(param, "group"), Param.getStr(param, "details")).getDetailsDesc());
		return data;
	}

}
