package com.biru.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.poi.hpsf.Decimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.entity.GL0001Entity;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.RptBsEntity;
import com.biru.entity.RptPlEntity;
import com.biru.repository.GL0001Repo;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.RptBsRepo;
import com.biru.repository.RptPlRepo;
import com.biru.service.CoaService;

@Service
public class CoaServiceImpl implements CoaService {

	@Autowired
	private MA0004Repo mA0004Repo;
	
	@Autowired
	private MA0014Repo mA0014Repo;
	
	@Autowired
	private GL0001Repo gL0001Repo;
	
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	
	@Autowired
	private RptBsRepo rptBsRepo;
	
	@Autowired
	private RptPlRepo rptPlRepo;
	
	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {
		// TODO Auto-generated method stub
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String filterKey = Param.getStr(p_Param, PARAM.FILTER_KEY);
		String filterValue = Param.getStr(p_Param, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0004Entity> data = null;
		
		RptBsEntity rptBsEntity = new RptBsEntity();
		RptPlEntity rptPlEntity = new RptPlEntity();
		
		if (filterKey != null) {
			if(filterKey.equals(PARAM.KEY.COA_CODE)) {
				data = mA0004Repo.findByCoaCodeContains(filterValue, pageable);
			}else {
				data = mA0004Repo.findByCoaDescriptContains(filterValue, pageable);
			}
		}else {
			data = mA0004Repo.findAll(pageable);
		}
		for (MA0004Entity ma0004Entity : data.getContent()) {
			if(ma0004Entity.getCoaDataStatus().equals("11")) {
				ma0004Entity.setCoaDataStatusStr("Active");
			}else {
				ma0004Entity.setCoaDataStatusStr("Inactive");
			}
			MA0014Entity mA0014Entity = mA0014Repo.findByPaParentCodeAndPaChildStatusAndPaChildValue("COACLASS", "11", ma0004Entity.getCoaClass()); 
			ma0004Entity.setCoaClassStr(mA0014Entity.getPaChildDesc());
			
			rptBsEntity = rptBsRepo.findByCoaCode(ma0004Entity.getCoaCode());
			if(rptBsEntity != null){
				ma0004Entity.setCoaRowBs(rptBsEntity.getRowRpt());
				ma0004Entity.setRowHeadBs(rptBsEntity.getRowHead());
				ma0004Entity.setCoaGroupBs(rptBsEntity.getCoaGroup());
				ma0004Entity.setPositionBs(rptBsEntity.getPosition());
			}
			
			rptPlEntity = rptPlRepo.findByCoaCode(ma0004Entity.getCoaCode());
			if(rptPlEntity != null){
				ma0004Entity.setCoaRowPl(rptPlEntity.getRowRpt());
				ma0004Entity.setCoaGroupPl(rptPlEntity.getCoaGroup());
			}
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object getDropdownClass() {
		// TODO Auto-generated method stub
		Map<String, List<DropdownIdText>>results = new HashMap<>();
		results.put("results", mA0014Repo.getCoaClass());
		results.get("results").add(0, new DropdownIdText("", ""));
		return results;
	}

	@Override
	public Object getDropdownCurr() {
		// TODO Auto-generated method stub
		Map<String, List<DropdownIdText>> results = new HashMap<>();
		results.put("results", mA0014Repo.getCurrency());
		results.get("results").add(0, new DropdownIdText("", ""));
		return results;
	}

	@Override
	public DatatableSet roleUp(Map<String, Object> p_Param) {
		// TODO Auto-generated method stub
		
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String filterKey = Param.getStr(p_Param, PARAM.FILTER_KEY);
		String filterValue = Param.getStr(p_Param, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0004Entity> data = null;
		if (filterKey != null) {
			if(filterKey.equals(PARAM.KEY.COA_CODE)) {
				data = mA0004Repo.findByCoaHeaderAndCoaDataStatusAndCoaCodeContains('H', "11", filterValue, pageable);
			}else {
				data = mA0004Repo.findByCoaHeaderAndCoaDataStatusAndCoaDescriptContains('H', "11", filterValue, pageable);
			}
		}else { 
			data = mA0004Repo.findByCoaHeaderAndCoaDataStatus('H', "11", pageable);
		}
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	/*
	 * UNUSED
	 * */
	@Override
	@Transactional
	public Object save(MA0004Entity mA0004Entity) throws ParseException {
			
		// TODO Auto-generated method stub
		MA0004Entity mA0004EntityExist = mA0004Repo.findByCoaCode(mA0004Entity.getCoaCode());
		if(mA0004EntityExist != null) {
			mA0004EntityExist.setCoaCetakAt(mA0004Entity.getCoaCetakAt());
			mA0004EntityExist.setCoaClass(mA0004Entity.getCoaClass());
			mA0004EntityExist.setCoaCode(mA0004Entity.getCoaCode());
			mA0004EntityExist.setCoaCurrId(mA0004Entity.getCoaCurrId());
			mA0004EntityExist.setCoaDataStatus(mA0004Entity.getCoaDataStatus());
			mA0004EntityExist.setCoaDescript(mA0004Entity.getCoaDescript());
			mA0004EntityExist.setCoaHeader(mA0004Entity.getCoaHeader());
			mA0004EntityExist.setCoaLevel(mA0004Entity.getCoaLevel());
			mA0004EntityExist.setCoaNormal(mA0004Entity.getCoaNormal());
			mA0004EntityExist.setCoaPrintBs(mA0004Entity.getCoaPrintBs());
			mA0004EntityExist.setCoaPrintCf(mA0004Entity.getCoaPrintCf());
			mA0004EntityExist.setCoaPrintPl(mA0004Entity.getCoaPrintPl());
			mA0004EntityExist.setCoaRoleUp(mA0004Entity.getCoaRoleUp());
			mA0004EntityExist.setModifyBy(mA0004Entity.getModifyBy());
			mA0004EntityExist.setModifyOn(mA0004Entity.getModifyOn());
			mA0004Repo.save(mA0004EntityExist);
		}else {
//			mA0004Entity.setIdKey(0l);
			mA0004Repo.save(mA0004Entity);
		}
		
		String year = commonServiceImpl.getAppDateStr().split("-")[2];
		
		GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(mA0004Entity.getCoaCode(), year);
		if(gL0001Entity == null) {
			gL0001Entity = new GL0001Entity();
			gL0001Entity.setIdKey(0l);
			gL0001Entity.setCoaCode(mA0004Entity.getCoaCode());
			gL0001Entity.setGlBalYear(year);
			gL0001Entity.setCreateBy(mA0004Entity.getCreateBy());
			gL0001Entity.setCreateOn(mA0004Entity.getCreateOn());
			gL0001Repo.save(gL0001Entity);
		}
		
		return mA0004Entity;
	}

	@Override
	public Object save(Map<String, Object> p_Param) throws ParseException {
		MA0004Entity mA0004EntityExist = mA0004Repo.findByCoaCode(Param.getStr(p_Param, "coaCode"));
		MA0004Entity mA0004Entity = new MA0004Entity();
		RptBsEntity rptBsEntityExist = rptBsRepo.findByCoaCode(Param.getStr(p_Param, "coaCode")); 
		RptBsEntity rptBsEntity = new RptBsEntity();
		RptPlEntity rptPlEntityExist = rptPlRepo.findByCoaCode(Param.getStr(p_Param, "coaCode"));
		RptPlEntity rptPlEntity = new RptPlEntity();
		
		if(rptBsEntityExist != null){
			rptBsEntity = rptBsEntityExist;
		}
		
		if(rptPlEntityExist != null){
			rptPlEntity = rptPlEntityExist;
		}
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		String user = Param.getStr(p_Param, Param.USER);
		System.out.println("PARAM INPUT "+p_Param.toString());
		
		if(mA0004EntityExist != null) {
			mA0004EntityExist.setCoaCetakAt(Param.getStr(p_Param, "coaCetakAt").charAt(0));
			mA0004EntityExist.setCoaClass(Param.getStr(p_Param, "coaClass"));
			mA0004EntityExist.setCoaCode(p_Param.get("coaCode").toString());
			mA0004EntityExist.setCoaCurrId(p_Param.get("coaCurrId").toString());
			mA0004EntityExist.setCoaDataStatus(p_Param.get("coaDataStatus").toString());
			mA0004EntityExist.setCoaDescript(p_Param.get("coaDescript").toString());
			mA0004EntityExist.setCoaHeader(p_Param.get("coaHeader").toString().charAt(0));
			mA0004EntityExist.setCoaLevel((Integer)p_Param.get("coaLevel"));
			mA0004EntityExist.setCoaNormal(p_Param.get("coaNormal").toString().charAt(0));
			mA0004EntityExist.setCoaPrintBs(p_Param.get("coaPrintBs").toString().charAt(0));
			mA0004EntityExist.setCoaPrintCf(p_Param.get("coaPrintCf").toString().charAt(0));
			mA0004EntityExist.setCoaPrintPl(p_Param.get("coaPrintPl").toString().charAt(0));
			mA0004EntityExist.setCoaRoleUp(p_Param.get("coaRoleUp").toString());
			
			
			mA0004EntityExist.setCoaRevalt(p_Param.get("coaReval").toString().charAt(0));
			mA0004EntityExist.setCoaBankBk(p_Param.get("coaBankList").toString().charAt(0));
			mA0004EntityExist.setCoaPlNew(p_Param.get("coaProfitLoss").toString());
			mA0004EntityExist.setCoaBsNew(p_Param.get("coaBlSheet").toString());
			
			if(("1").equals(p_Param.get("coaBlSheet").toString())){
				rptBsEntity.setRowRpt(Param.getInt(p_Param, "coaRowBS"));
				rptBsEntity.setRowHead(p_Param.get("coaHeaderBS").toString());
				rptBsEntity.setCoaCode(p_Param.get("coaCode").toString());
				rptBsEntity.setCoaDescription(p_Param.get("coaDescript").toString());
				rptBsEntity.setCoaGroup(p_Param.get("coaGroupBS").toString());
				rptBsEntity.setPosition(p_Param.get("coaPosition").toString());
				if(rptBsEntity.getBlock1L() != null){
					rptBsEntity.setBlock1L(rptBsEntity.getBlock1L());
				}else{
					rptBsEntity.setBlock1L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock1R() != null){
					rptBsEntity.setBlock1R(rptBsEntity.getBlock1R());
				}else{
					rptBsEntity.setBlock1R(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock2L() != null){
					rptBsEntity.setBlock2L(rptBsEntity.getBlock2L());
				}else{
					rptBsEntity.setBlock2L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock2R() != null){
					rptBsEntity.setBlock2R(rptBsEntity.getBlock2R());
				}else{
					rptBsEntity.setBlock2R(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock3L() != null){
					rptBsEntity.setBlock3L(rptBsEntity.getBlock3L());
				}else{
					rptBsEntity.setBlock3L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock3R() != null){
					rptBsEntity.setBlock3R(rptBsEntity.getBlock3R());
				}else{
					rptBsEntity.setBlock3R(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock4L() != null){
					rptBsEntity.setBlock4L(rptBsEntity.getBlock4L());
				}else{
					rptBsEntity.setBlock4L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock4R() != null){
					rptBsEntity.setBlock4R(rptBsEntity.getBlock4R());
				}else{
					rptBsEntity.setBlock4R(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock5L() != null){
					rptBsEntity.setBlock5L(rptBsEntity.getBlock5L());
				}else{
					rptBsEntity.setBlock5L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock5R() != null){
					rptBsEntity.setBlock5R(rptBsEntity.getBlock5R());
				}else{
					rptBsEntity.setBlock5R(BigDecimal.ZERO);
				}
				rptBsRepo.save(rptBsEntity);
				System.out.println("SAVING TO TABLE RPTBS MOD DONE");
			}
			
			if(("1").equals(p_Param.get("coaProfitLoss").toString())){
				rptPlEntity.setIdKey(rptPlEntity.getIdKey());
				rptPlEntity.setRowRpt(Param.getInt(p_Param, "coaRowPL"));
				rptPlEntity.setCoaCode(p_Param.get("coaCode").toString());
				rptPlEntity.setCoaDescription(p_Param.get("coaDescript").toString());
				rptPlEntity.setCoaGroup(p_Param.get("coaGroupPL").toString());
				if(rptPlEntity.getBlock1() != null){
					rptPlEntity.setBlock1(rptPlEntity.getBlock1());
				}else{
					rptPlEntity.setBlock1(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock2() != null){
					rptPlEntity.setBlock2(rptPlEntity.getBlock2());
				}else{
					rptPlEntity.setBlock2(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock3() != null){
					rptPlEntity.setBlock3(rptPlEntity.getBlock3());
				}else{
					rptPlEntity.setBlock3(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock4() != null){
					rptPlEntity.setBlock4(rptPlEntity.getBlock4());
				}else{
					rptPlEntity.setBlock4(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock5() != null){
					rptPlEntity.setBlock5(rptPlEntity.getBlock5());
				}else{
					rptPlEntity.setBlock5(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock6() != null){
					rptPlEntity.setBlock6(rptPlEntity.getBlock6());
				}else{
					rptPlEntity.setBlock6(BigDecimal.ZERO);
				}
				rptPlRepo.save(rptPlEntity);
				System.out.println("SAVING TO TABLE RPTPL MOD DONE");
			}
			
			mA0004EntityExist.setModifyBy(p_Param.get("modifyBy").toString());
			mA0004EntityExist.setModifyOn(now);
			mA0004Repo.save(mA0004EntityExist);
			System.out.println("SAVING TO TABLE MA04 MOD DONE");
			mA0004Entity = mA0004EntityExist;
		}else {
//			mA0004Entity.setIdKey(0l);
			mA0004Entity.setCoaCetakAt(p_Param.get("coaCetakAt").toString().charAt(0));
			mA0004Entity.setCoaClass(p_Param.get("coaClass").toString());
			mA0004Entity.setCoaCode(p_Param.get("coaCode").toString());
			mA0004Entity.setCoaCurrId(p_Param.get("coaCurrId").toString());
			mA0004Entity.setCoaDataStatus(p_Param.get("coaDataStatus").toString());
			mA0004Entity.setCoaDescript(p_Param.get("coaDescript").toString());
			mA0004Entity.setCoaHeader(p_Param.get("coaHeader").toString().charAt(0));
			mA0004Entity.setCoaLevel((Integer)p_Param.get("coaLevel"));
			mA0004Entity.setCoaNormal(p_Param.get("coaNormal").toString().charAt(0));
			mA0004Entity.setCoaPrintBs(p_Param.get("coaPrintBs").toString().charAt(0));
			mA0004Entity.setCoaPrintCf(p_Param.get("coaPrintCf").toString().charAt(0));
			mA0004Entity.setCoaPrintPl(p_Param.get("coaPrintPl").toString().charAt(0));
			mA0004Entity.setCoaRoleUp(p_Param.get("coaRoleUp").toString());
			mA0004Entity.setCreateBy(p_Param.get("createBy").toString());
			mA0004Entity.setCreateOn(now);
			mA0004Entity.setModifyBy(p_Param.get("modifyBy").toString());
			mA0004Entity.setModifyOn(now);
			mA0004Entity.setCoaRevalt(p_Param.get("coaReval").toString().charAt(0));
			mA0004Entity.setCoaBankBk(p_Param.get("coaBankList").toString().charAt(0));
			mA0004Entity.setCoaPlNew(p_Param.get("coaProfitLoss").toString());
			mA0004Entity.setCoaBsNew(p_Param.get("coaBlSheet").toString());
			
			if(("1").equals(p_Param.get("coaBlSheet").toString())){
				rptBsEntity.setRowRpt(Integer.valueOf(p_Param.get("coaRowBS").toString()));
				rptBsEntity.setRowHead(p_Param.get("coaHeaderBS").toString());
				rptBsEntity.setCoaCode(p_Param.get("coaCode").toString());
				rptBsEntity.setCoaDescription(p_Param.get("coaDescript").toString());
				rptBsEntity.setCoaGroup(p_Param.get("coaGroupBS").toString());
				rptBsEntity.setPosition(p_Param.get("coaPosition").toString());
				if(rptBsEntity.getBlock1L() != null){
					rptBsEntity.setBlock1L(rptBsEntity.getBlock1L());
				}else{
					rptBsEntity.setBlock1L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock1R() != null){
					rptBsEntity.setBlock1R(rptBsEntity.getBlock1R());
				}else{
					rptBsEntity.setBlock1R(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock2L() != null){
					rptBsEntity.setBlock2L(rptBsEntity.getBlock2L());
				}else{
					rptBsEntity.setBlock2L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock2R() != null){
					rptBsEntity.setBlock2R(rptBsEntity.getBlock2R());
				}else{
					rptBsEntity.setBlock2R(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock3L() != null){
					rptBsEntity.setBlock3L(rptBsEntity.getBlock3L());
				}else{
					rptBsEntity.setBlock3L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock3R() != null){
					rptBsEntity.setBlock3R(rptBsEntity.getBlock3R());
				}else{
					rptBsEntity.setBlock3R(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock4L() != null){
					rptBsEntity.setBlock4L(rptBsEntity.getBlock4L());
				}else{
					rptBsEntity.setBlock4L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock4R() != null){
					rptBsEntity.setBlock4R(rptBsEntity.getBlock4R());
				}else{
					rptBsEntity.setBlock4R(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock5L() != null){
					rptBsEntity.setBlock5L(rptBsEntity.getBlock5L());
				}else{
					rptBsEntity.setBlock5L(BigDecimal.ZERO);
				}
				if(rptBsEntity.getBlock5R() != null){
					rptBsEntity.setBlock5R(rptBsEntity.getBlock5R());
				}else{
					rptBsEntity.setBlock5R(BigDecimal.ZERO);
				}
				rptBsRepo.save(rptBsEntity);
				System.out.println("SAVING TO TABLE RPTBS CRE DONE");
			}
			
			if(("1").equals(p_Param.get("coaProfitLoss").toString())){
				rptPlEntity.setRowRpt(Integer.valueOf(p_Param.get("coaRowPL").toString()));
				rptPlEntity.setCoaCode(p_Param.get("coaCode").toString());
				rptPlEntity.setCoaDescription(p_Param.get("coaDescript").toString());
				rptPlEntity.setCoaGroup(p_Param.get("coaGroupPL").toString());
				if(rptPlEntity.getBlock1() != null){
					rptPlEntity.setBlock1(rptPlEntity.getBlock1());
				}else{
					rptPlEntity.setBlock1(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock2() != null){
					rptPlEntity.setBlock2(rptPlEntity.getBlock2());
				}else{
					rptPlEntity.setBlock2(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock3() != null){
					rptPlEntity.setBlock3(rptPlEntity.getBlock3());
				}else{
					rptPlEntity.setBlock3(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock4() != null){
					rptPlEntity.setBlock4(rptPlEntity.getBlock4());
				}else{
					rptPlEntity.setBlock4(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock5() != null){
					rptPlEntity.setBlock5(rptPlEntity.getBlock5());
				}else{
					rptPlEntity.setBlock5(BigDecimal.ZERO);
				}
				if(rptPlEntity.getBlock6() != null){
					rptPlEntity.setBlock6(rptPlEntity.getBlock6());
				}else{
					rptPlEntity.setBlock6(BigDecimal.ZERO);
				}
				rptPlRepo.save(rptPlEntity);
				System.out.println("SAVING TO TABLE RPTPL CRE DONE");
			}
			mA0004Repo.save(mA0004Entity);
			System.out.println("SAVING TO TABLE MA04 CRE DONE");
		}
		
		String year = commonServiceImpl.getAppDateStr().split("-")[2];
		System.out.println("YEAR + "+year);
		GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(Param.getStr(p_Param, "coaCode"), year);
		if(gL0001Entity == null) {
			System.out.println("MASUK SINI");
			gL0001Entity = new GL0001Entity();
			//gL0001Entity.setIdKey(0l);
			gL0001Entity.setCoaCode(p_Param.get("coaCode").toString());
			gL0001Entity.setGlBalYear(year);
			gL0001Entity.setCreateBy(p_Param.get("createBy").toString());
			gL0001Entity.setCreateOn(now);
			gL0001Repo.save(gL0001Entity);
			System.out.println("SAVING TO TABLE GL01 CRE DONE");
		}
		
		return mA0004Entity;
	}

}
