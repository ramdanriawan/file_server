package com.biru.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.component.ResultComponent;
//import com.biru.entity.InsuranceDto;
import com.biru.entity.MA0011Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0018Entity;
import com.biru.entity.MA0019Entity;
import com.biru.entity.MA0021Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0011Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0019Repo;
import com.biru.repository.MA0021Repo;
import com.biru.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

	@Autowired
	private MA0011Repo mA0011Repo;
	
	@Autowired
	private MA0014Repo mA0014Repo;
	
	@Autowired
	private MA0005Repo ma0005Repo;
	
	@Autowired
	private MA0021Repo mA0021Repo;
	
	@Autowired
	private MA0019Repo mA0019Repo;
	
	@Autowired
	private ResultComponent resultComponent;
	
	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {

		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String filterValue = Param.getStr(p_Param, PARAM.FILTER_VALUE);
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		System.out.println(pageable);
		Page<MA0011Entity> data = null;
		if (filterValue==null || filterValue=="") {
			data = mA0011Repo.findByFilter("", pageable);
		}else {
			data = mA0011Repo.findByFilter(filterValue, pageable);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}
	
	@Override
	public Object inquiryInsurance(Map<String, Object> p_Param) {

		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String tcCode = Param.getStr(p_Param, "tcCode");
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		List<MA0019Entity> list19 = mA0019Repo.findByTcCode(tcCode);
		
		int start = offset;
		int end = (start + pageable.getPageSize()) > list19.size() ? list19.size() : (start + pageable.getPageSize());
		Page<MA0019Entity> finalData = new PageImpl<MA0019Entity>(list19.subList(start, end), pageable, list19.size());
		return new DatatableSet(finalData.getTotalElements(), finalData.getTotalElements(), finalData.getContent());
	}
	
	@Override 
	public Object removeIns(Map<String, Object> param) {
		MA0021Entity ma0021 = mA0021Repo.findByIdKey(Param.getLong(param, "idKey"));
		mA0021Repo.delete(ma0021);
		return null;
	}

	@Override
	public Object getDropDown() {
		
		Map<String, List<DropdownIdText>>results = new HashMap<>();
		results.put("results", ma0005Repo.getDropdownForProduct());
		results.get("results").add(0, new DropdownIdText("", ""));
		return results;
	}
	@Override
	public Object save(Map<String, Object> param) {
		try {
			saveProcess(param);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			return resultComponent.createResponse(e);
		}
	}
	
	@Override
	public Object saveIns(Map<String, Object> param) {
		try {
			saveInsProcess(param);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			 return resultComponent.createResponse(e);
		}
	}
	
	@Transactional
	private void saveInsProcess(Map<String, Object> param) throws Exception {
			Calendar c = Calendar.getInstance();
			Long idKey = Param.getLong(param, "idKey");
			MA0021Entity ma0021Entity = null;
			if(idKey == null) {
				ma0021Entity = mA0021Repo.findByInsIdAndTcCode(Param.getStr(param, "insId"), Param.getStr(param, "tcCode"));
				if(ma0021Entity != null) {
					Exception e = new Exception("Data already exist");
					throw e;
				}
				ma0021Entity = new MA0021Entity();
				ma0021Entity.setCreateBy(Param.getStr(param, "createBy"));
				ma0021Entity.setCreateOn(c.getTime());
			}else {
				ma0021Entity = mA0021Repo.findById(idKey).get();
			}
			ma0021Entity.setModifyBy(Param.getStr(param, "modifyBy"));
			ma0021Entity.setModifyOn(c.getTime());
			ma0021Entity.setInsId(Param.getStr(param, "insId"));
			ma0021Entity.setTcCode(Param.getStr(param, "tcCode"));
			
			mA0021Repo.save(ma0021Entity);
		
	}
	
	@Override
	public Object saveProcess(Map<String, Object> param) throws Exception {
			Calendar c = Calendar.getInstance();
			Long idKey = Param.getLong(param, "idKey");
			MA0011Entity mA0011Entity = null;
			List<MA0019Entity> oldListOfMA0019Entity = null;
			Boolean modifyingData = false;
			if(idKey == null) {
				mA0011Entity = mA0011Repo.findByTcCode(Param.getStr(param, "tcCode"));
				if(mA0011Entity != null) {
					Exception e = new Exception("Code already exist");
					throw e;
				}
				mA0011Entity = new MA0011Entity();
				mA0011Entity.setCreateBy(Param.getStr(param, "createBy"));
				mA0011Entity.setCreateOn(c.getTime());
			}else {
				//when modifying existing data
				mA0011Entity = mA0011Repo.findById(idKey).get();
				oldListOfMA0019Entity = mA0019Repo.findByTcCode(Param.getStr(param, "tcCode"));
				modifyingData = true;
			}
			
			mA0011Entity.setModifyBy(Param.getStr(param, "modifyBy"));
			mA0011Entity.setModifyOn(c.getTime());
			mA0011Entity.setTcBrofeePct(Param.getBd(param, "tcBrofeePct"));
			mA0011Entity.setTcChannel(Param.getStr(param, "tcChannel").charAt(0));
			mA0011Entity.setTcCode(Param.getStr(param, "tcCode"));
			mA0011Entity.setTcDataStatus(Param.getStr(param, "tcDataStatus"));
			mA0011Entity.setTcDesc(Param.getStr(param, "tcDesc"));
			mA0011Entity.setTcFullDesc(Param.getStr(param, "tcFullDesc"));
			mA0011Entity.setTcExtMax(Param.getInt(param, "tcExtMax"));
			mA0011Entity.setTcOjkGroup(Param.getStr(param, "tcOjkGroup"));
			mA0011Entity.setTcPpw(Param.getInt(param, "tcPpw"));
			mA0011Entity.setTcPremium(Param.getBd(param, "tcPremium"));
			mA0011Entity.setTcQuoValid(Param.getInt(param, "tcQuoValid"));
			mA0011Entity.setTcRenewable(Param.getStr(param, "tcRenewable").charAt(0));
			
			mA0011Repo.save(mA0011Entity);
			System.out.println("SAVING TABLE MA11 DONE");
			
			ArrayList<HashMap<String, Object>> rows = (ArrayList<HashMap<String, Object>>) param.get("rows");
			List<MA0019Entity> listOfMA0019Entity = new ArrayList<MA0019Entity>();
			
			for(HashMap<String, Object> rowsData : rows) {
				MA0019Entity ma0019Entity = new MA0019Entity();
				MA0014Entity ma14 = mA0014Repo.findChildCodeByChildDesc(rowsData.get("document").toString());
				
				ma0019Entity.setTcCode(Param.getStr(param, "tcCode"));
				ma0019Entity.setClCode(Integer.valueOf(ma14.getPaChildValue()));
				ma0019Entity.setClStatus(rowsData.get("statusVal").toString());
				ma0019Entity.setClDesc(rowsData.get("document").toString());
				
				listOfMA0019Entity.add(ma0019Entity);
			}
			
			if(modifyingData)
				mA0019Repo.deleteAll(oldListOfMA0019Entity);
			
			mA0019Repo.saveAll(listOfMA0019Entity);
			System.out.println("SAVING TABLE MA19 DONE");
			return mA0011Entity;
		
	}

	@Override
	public Object getDropDownPaChild() {
		Map<String, List<DropdownIdText>>results = new HashMap<>();
		results.put("results", mA0014Repo.getDropDownPaChild());
		results.get("results").add(0, new DropdownIdText("", ""));
		return results;
	}

}
