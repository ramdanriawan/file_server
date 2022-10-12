package com.biru.service.impl;

import java.util.List;
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
import com.biru.component.CancelUploadComponent;
import com.biru.component.ResultComponent;
import com.biru.entity.MA0014Entity;
import com.biru.entity.TR0012Entity;
import com.biru.entity.ViewDocumentDeleteEntity;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewDocumentDeleteRepo;
import com.biru.service.CancelUploadService;
@Service
public class CancelUploadServiceImpl implements CancelUploadService{
	
	@Autowired
	private ViewDocumentDeleteRepo viewDocumentDeleteRepo;
	
	@Autowired
	private CancelUploadComponent cancelUploadComponent;
	
	@Autowired
	private ResultComponent resultComponent;

	@Override
	public Object search(Map<String, Object> param) {
		// TODO Auto-generated method stub
		String client = Param.getStrWithDef(param, "client");
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		System.out.println(param);
		
		
		Page<ViewDocumentDeleteEntity> pageViewDocumentDeleteEntity = null;
		if(client.equals("")) {
			Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
			pageViewDocumentDeleteEntity = viewDocumentDeleteRepo.findAll(pageable);
		}else {
			Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
			pageViewDocumentDeleteEntity = viewDocumentDeleteRepo.findByTrxClientIdEquals(client, pageable);
		}
		
		if(!pageViewDocumentDeleteEntity.isEmpty()) {
			for (ViewDocumentDeleteEntity viewDocumentDeleteEntity : pageViewDocumentDeleteEntity) {
				if(viewDocumentDeleteEntity.getSettled().equals("Y")) {
					String action = "<button class=\"btn btn-danger\" onclick=\"cannotRemove()\">" 
							+ "<i class=\"fa fa-trash\"></i>" 
							+ "</button>";
					viewDocumentDeleteEntity.setAction(action);
				}else {
					String action = "<button class=\"btn btn-danger\" onclick=\"remove('"+viewDocumentDeleteEntity.getIdKey()+"')\">" 
							+ "<i class=\"fa fa-trash\"></i>" 
							+ "</button>";
					viewDocumentDeleteEntity.setAction(action);
				}
			}
		}
		
		return new DatatableSet(pageViewDocumentDeleteEntity.getTotalElements(), pageViewDocumentDeleteEntity.getTotalElements(), pageViewDocumentDeleteEntity.getContent());
	}

	@Override
	@Transactional
	public Object delete(Map<String, Object> param) {
		// TODO Auto-generated method stub
		try {
			cancelUploadComponent.delete(param);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return resultComponent.createResponse(e);
		}
	}
	
}
