package com.biru.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0015AEntity;
import com.biru.entity.MA0018Entity;
import com.biru.entity.ResponseEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0012Entity;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0012Repo;
import com.biru.service.AdjustmentService;
import com.biru.service.CommonService;

@Service
public class AdjustmentServiceImpl extends AbstractCommon implements AdjustmentService {

	@Autowired
	private MA0004Repo ma0004Repo;
	
	@Autowired
	private MA0015ARepo ma0015ARepo;
	
	@Autowired
	private MA0018Repo ma0018Repo;
	
	@Autowired
	private TR0001Repo tr0001repo;
	
	@Autowired
	private TR0002Repo tr0002repo;
	
	@Autowired
	private TR0012Repo tr0012repo;
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	private Logger logger = LoggerFactory.getLogger(AdjustmentServiceImpl.class);
	
	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String filterValue = Param.getStrWithDef(p_Param, PARAM.FILTER_VALUE);
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<TR0012Entity> data = tr0012repo.findAdjustment(filterValue.toLowerCase(), pageable);
		List<TR0012Entity> listOfData = data.getContent();
		
		for(TR0012Entity tR0012Entity : listOfData) {
			String action = "<button class=\"btn btn-secondary\" onclick=\"t12Edit('"+tR0012Entity.getIdKey()+"')\">" 
					+ "<i class=\"fa fa-edit\"></i>" 
					+ "</button>";
			tR0012Entity.setAction(action);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	@Transactional
	public Object save(Map<String, Object> p_Param) throws ParseException {
		logger.info("Adjustment with param : {}.", p_Param);

		String type = Param.getStr(p_Param, "type").trim();
		String oldVoucherId = Param.getStr(p_Param, "oldVoucherId");
		TR0012Entity oldTR12 = tr0012repo.findByTrxVoucherIdAndTrxType(oldVoucherId, type);

		String curr = oldTR12.getTrxCurrId();
		String clientCode = oldTR12.getTrxClient();
		String remarks = Param.getStr(p_Param, "remarks");
		String position = Param.getStr(p_Param, "position");
		BigDecimal adjustment = Param.getBd(p_Param, "adjustment");
		
		if(position.equals("-"))
			adjustment = adjustment.negate();

		Date appDate = common.getAppDate();		
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(appDate);
		
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);

		Date now = Calendar.getInstance().getTime();
		
		String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(appDate));
		
		TR0001Entity tr1 = new TR0001Entity();
		tr1.setGlTrxClass("OP");
		tr1.setGlType(type);
		tr1.setGlVoucherId(voucherId);
		tr1.setGlTrxDate(appDate);
		tr1.setGlTrxDue(appDate);
		tr1.setGlTrxMonth(month.byteValue());
		tr1.setGlTrxYear(year.shortValue());
		tr1.setGlTrxOfficeId("0");
		tr1.setGlTrxProject("0000");
		tr1.setGlTrxClient(clientCode);
		
		String desc = "Adjustment " + type + " " + oldVoucherId;
		tr1.setGlTrxDesc(desc);
		
		MA0015AEntity exchangeCli = ma0015ARepo.findByExDateAndExCurrId(appDate, curr);
		
		tr1.setGlTrxValueOrg(adjustment.abs());
		tr1.setGlTrxValueIdr(adjustment.abs().multiply(exchangeCli.getExKmkRateBd()));	
		tr1.setGlTrxStatus("0");
		tr1.setGlDataStatus("11");
		tr1.setCreateOn(now);
		tr1.setCreateBy(Param.getStr(p_Param, Param.USER));
		tr1.setModifyOn(now);
		tr1.setModifyBy(Param.getStr(p_Param, Param.USER));
		
		String brCode;
		if(type.equals("PU") || type.equals("PO"))
			brCode = "ADJP";
		else
			brCode = "ADJS";
		
		brCode += curr;
		
		Map<String, BigDecimal> mapValue = new HashMap<String, BigDecimal>();
		mapValue.put("CHANGE_VALUE", adjustment);
		
		List<MA0018Entity> businessRule = ma0018Repo.findByBrCode(brCode);
		List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
		for(MA0018Entity ma0018 : businessRule) {
			String code = ma0018.getBrChildValue().trim();		
			
			TR0002Entity t2 = new TR0002Entity();
			t2.setGlTrxClass(tr1.getGlTrxClass());
			t2.setGlType(tr1.getGlType());
			t2.setGlVoucherId(tr1.getGlVoucherId());
			t2.setGlAccount(ma0018.getBrChildCoa());
			
			MA0004Entity brAccount = ma0004Repo.findByCoaCode(ma0018.getBrChildCoa());
			MA0015AEntity brExchange = ma0015ARepo.findByExDateAndExCurrId(appDate, brAccount.getCoaCurrId());
			
			t2.setGlCurrId(brAccount.getCoaCurrId());
			t2.setGlDescription("");
			t2.setGlCurrRate(brExchange.getExKmkRateBd());
			
			BigDecimal org = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
			BigDecimal idr = org.multiply(brExchange.getExKmkRateBd());
			
			t2.setGlOrgDebit(BigDecimal.ZERO);
			t2.setGlIdrDebit(BigDecimal.ZERO);
			t2.setGlOrgCredit(BigDecimal.ZERO);
			t2.setGlIdrCredit(BigDecimal.ZERO);
			if(
				(ma0018.getBrChildDc().equals('0') && idr.compareTo(BigDecimal.ZERO) >= 0) ||
				(ma0018.getBrChildDc().equals('1') && idr.compareTo(BigDecimal.ZERO) < 0)
			) {				
				t2.setGlOrgDebit(org.abs());
				t2.setGlIdrDebit(idr.abs());
			}else if(
				(ma0018.getBrChildDc().equals('1') && idr.compareTo(BigDecimal.ZERO) >= 0) ||
				(ma0018.getBrChildDc().equals('0') && idr.compareTo(BigDecimal.ZERO) < 0)
			) {
				t2.setGlOrgCredit(org.abs());
				t2.setGlIdrCredit(idr.abs());
			}
				
			if(org.compareTo(BigDecimal.ZERO)!=0)
				tr2Entities.add(t2);
		}
		
		TR0012Entity tr12 = new TR0012Entity();
		tr12.setTrxTrxClass(tr1.getGlTrxClass());
		tr12.setTrxType(tr1.getGlType());
		tr12.setTrxVoucherId(tr1.getGlVoucherId());
		tr12.setTrxDate(tr1.getGlTrxDate());
		tr12.setTrxDueDate(tr1.getGlTrxDue());
		tr12.setTrxDataStatus("11");
		tr12.setTrxOldType(type);
		tr12.setTrxOldVoucherId(oldVoucherId);
		tr12.setTrxClient(clientCode);
		tr12.setTrxDescription(tr1.getGlTrxDesc());
		tr12.setTrxCurrId(curr);
		tr12.setTrxCurrRate(exchangeCli.getExKmkRateBd());
		tr12.setTrxOrgAmount(adjustment.abs());
		tr12.setTrxInvcAmount(adjustment.abs());
		tr12.setTrxSetAmount(adjustment.abs());
		tr12.setTrxCountInv(1);
		tr12.setCreateOn(now);
		tr12.setCreateBy(Param.getStr(p_Param, Param.USER));
		tr12.setModifyOn(now);
		tr12.setModifyBy(Param.getStr(p_Param, Param.USER));
		
		tr0001repo.save(tr1);
		tr0002repo.saveAll(tr2Entities);
		tr0012repo.save(tr12);
		
		BigDecimal orgAmount = oldTR12.getTrxOrgAmount();
		BigDecimal invcAmount = oldTR12.getTrxInvcAmount();
		BigDecimal setAmount = oldTR12.getTrxSetAmount();
		orgAmount = orgAmount.add(adjustment);
		invcAmount = invcAmount.add(adjustment);
		
		if(invcAmount.compareTo(setAmount)<=0)
			oldTR12.setTrxDataStatus("13");
		
		oldTR12.setTrxOrgAmount(orgAmount);
		oldTR12.setTrxInvcAmount(invcAmount);
		oldTR12.setTrxRemarks(remarks);
		
		tr0012repo.save(oldTR12);
		
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		response.setResult(tr1.getGlVoucherId());
		response.getMessage().put("description", tr1.getGlTrxDesc());
		
		return response;
	}

}
