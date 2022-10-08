package com.biru.service.impl;

import java.util.Calendar;
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
import com.biru.entity.MA0007Entity;
import com.biru.repository.MA0007Repo;
import com.biru.service.TcDataService;
@Service
public class TcDataServiceImpl implements TcDataService{
	
	@Autowired
	private MA0007Repo mA0007Repo;
	
	@Autowired
	private ResultComponent resultComponent;
	
	@Override
	public Object inquiry(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String group = Param.getStr(param, "group");
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0007Entity> pageMA0007Entity = mA0007Repo.findByGroupName(group, pageable);
		return new DatatableSet(pageMA0007Entity.getTotalElements(), pageMA0007Entity.getTotalElements(), pageMA0007Entity.getContent());
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
		Calendar c = Calendar.getInstance();
		MA0007Entity mA0007Entity = null ;
		if(Param.getStr(param, "idKey") == null) {
			mA0007Entity = new MA0007Entity();
			mA0007Entity.setCreateBy(Param.getStr(param, Param.CREATE_BY));
			mA0007Entity.setCreateOn(c.getTime());
			mA0007Entity.setModifyBy(Param.getStr(param, Param.MODIFY_BY));
			mA0007Entity.setModifyOn(c.getTime());
		}else {
			mA0007Entity = mA0007Repo.findById(Param.getStr(param, "idKey")).get();
			mA0007Entity.setModifyBy(Param.getStr(param, Param.MODIFY_BY));
			mA0007Entity.setModifyOn(c.getTime());
		}
		mA0007Entity.setDetailsDesc(Param.getStr(param, "description"));
		mA0007Entity.setDetailsName(Param.getStr(param, "details"));
		mA0007Entity.setDetailStatus(Param.getStr(param, "status"));
		mA0007Entity.setGroupName(Param.getStr(param, "group"));
		
		mA0007Repo.save(mA0007Entity);
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
	
	@Transactional
	private void deleteProcess(Map<String, Object> param) {
		String idKey = Param.getStr(param, "idKey");
		mA0007Repo.deleteById(idKey);
	}

}
