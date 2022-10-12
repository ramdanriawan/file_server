package com.biru.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.ReBrokerConstants.REST.PRODUCTION_TRE_TYPE;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.VoucherComponent;
import com.biru.entity.ResponseEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0006JEntity;
import com.biru.entity.TR0006KEntity;
import com.biru.entity.TR0012Entity;
import com.biru.helper.TreClosingParam;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0006JRepo;
import com.biru.repository.TR0006KRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewInqProductionRepo;
import com.biru.service.CommonService;
import com.biru.service.ProductionTreatyService;
import com.biru.service.TreatyAdjustmentService;
import com.biru.specifications.ViewInqProductionSpesifications;
import com.biru.view.ViewInqProduction;

@Service
public class TreatyAdjustmentSeviceImpl extends AbstractCommon implements TreatyAdjustmentService {
	
	@Autowired
	private TR0001Repo tr0001Repo;
	
	@Autowired
	private TR0002Repo tr0002Repo;
	
	@Autowired
	private TR0006Repo tr0006Repo;
	
	@Autowired
	private TR0006JRepo tr0006JRepo;
	
	@Autowired
	private TR0006KRepo tr0006KRepo;
	
	@Autowired
	private TR0012Repo tr0012Repo;
	
	@Autowired
	private ViewInqProductionRepo viewInqProductionRepo;
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private ProductionTreatyService productionTreatyService;
	
	@Autowired
	private VoucherComponent voucherComponent;

	//field
	private static final String ID_KEY			= "idKey";
	private static final String TRX_OGNRPI_ACT	= "trxOgnrpiAct";
	private static final String TRX_VOUCHER_ID	= "trxVoucherId";
	
	private static final String RQ 				= "RQ";
	private static final String TRE 			= "TRE";
	
	private static final BigDecimal BD_100		= new BigDecimal("100");
	
	private static final Logger logger = LoggerFactory.getLogger(TreatyAdjustmentSeviceImpl.class);
	
	@Override
	public Object inquiry(Map<String, Object> param) {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		param.put("trxClass", TRE);
		param.put("trxNonPro", "N");
		param.put("trxDataStatus", "11");
		Specification<ViewInqProduction> spec = ViewInqProductionSpesifications.inquiryAdjustment(param);
		Page<ViewInqProduction> data = viewInqProductionRepo.findAll(spec, pageable);
		
		for(ViewInqProduction view : data.getContent()) {
			String action = "<button class=\"btn btn-secondary\" onclick=\"edit('"+view.getRequestId()+"','"+ view.getTreAdjStatus() +"')\">" 
					+ "<i class=\"fa fa-edit\"></i>" 
					+ "	</button> &nbsp;";
			if("Adjusted".equals(view.getTreAdjStatus()))
				action += "<button class=\"btn btn-danger\" onclick=\"cancel('"+view.getRequestId()+"')\">" 
						+ "<i class=\"fa fa-trash\"></i>" 
						+ "</button>";
					
			view.setAction(action);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object inquiryModify(Map<String, Object> param) {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		List<TR0006JEntity> interest = tr0006JRepo.getDetailAdjustment(TRE, RQ, trxVoucherId);
		
		List<TR0006JEntity> details = new ArrayList<TR0006JEntity>();
		for(TR0006JEntity ii : interest) {
			String action = "<button class=\"btn btn-secondary\" onclick=\"update('"+ii.getIdKey()+"')\">" 
					+ "<i class=\"fa fa-edit\"></i>" 
					+ "	</button>";
			
			ii.setAction(action);
			
			if(ii.getTrxOgnrpi().compareTo(BigDecimal.ZERO) > 0)
				details.add(ii);
		}
		
		Collections.sort(details, new Comparator<TR0006JEntity> () {

			@Override
			public int compare(TR0006JEntity o1, TR0006JEntity o2) {
				int sort;
				sort = o1.getTrxCobGroupDesc().compareTo(o2.getTrxCobGroupDesc());
				if(sort == 0)
					sort = o1.getTrxLayer().compareTo(o2.getTrxLayer());
				if(sort == 0)
					sort = o1.getTrxCoverCodeDesc().compareTo(o2.getTrxCoverCodeDesc());
				
				return sort;
			}
		    
		});
		
		return details;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object save(Map<String, Object> param) {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		response.setResult(trxVoucherId);
		
		List<Map<String, Object>> interest = (ArrayList<Map<String, Object>>) param.get("interest");
		List<TR0006JEntity> tr6jUpdate = new ArrayList<TR0006JEntity>();
		for(Map<String, Object> ii : interest) {
			String idKey = Param.getStr(ii, ID_KEY);
			BigDecimal trxOgnrpiAct = Param.getBdWithDef(ii, TRX_OGNRPI_ACT);
			
			TR0006JEntity tr6j = tr0006JRepo.findById(idKey).get();
			tr6j.setTrxOgnrpiAct(trxOgnrpiAct);

			tr6jUpdate.add(tr6j);
		}
		
		if(tr6jUpdate.size() > 0)
			tr0006JRepo.saveAll(tr6jUpdate);
		
		return response;
	}

	@Override
	@Transactional
	public Object process(Map<String, Object> param) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		List<TR0006JEntity> interest = tr0006JRepo.getAdditionalTreaty(TRE, RQ, trxVoucherId);
		Map<String, Map<String, Object>> mapValueInterest = getValueInterest(interest);
		getDeltaAdjustment(mapValueInterest);
		
		List<TR0006KEntity> reins = tr0006KRepo.getReinsuranceTreaty(TRE, RQ, trxVoucherId);
		Map<String, Map<String, Object>> mapValueReins = getValueReins(reins, mapValueInterest);
		
		TreClosingParam treClosingParam = new TreClosingParam();
		treClosingParam.setTrxVoucherId(trxVoucherId);
		treClosingParam.setType(PRODUCTION_TRE_TYPE.NON_PROPORTIONAL);
		treClosingParam.setClientValue(new ArrayList<Map<String,Object>>(mapValueInterest.values()));
		treClosingParam.setReinsValue(new ArrayList<Map<String,Object>>(mapValueReins.values()));
		treClosingParam.setUser(Param.getStr(param, Param.USER));
		treClosingParam.setIsAdjustment(Boolean.TRUE);
		
		Object result = productionTreatyService.doClosing(treClosingParam);
		
		//update status adjustment
		tr0006Repo.updateTrxTreAdj("1", TRE, RQ, trxVoucherId);
		
		return result;
	}
	
	private Map<String, Map<String, Object>> getValueInterest(List<TR0006JEntity> interest) {
		Map<String, Map<String, Object>> mapValueInterest = new HashMap<String, Map<String,Object>>();
		
		//summary by group & layer
		for(TR0006JEntity ii : interest) {
			String compositeId = ii.getTrxCobGroup() + ii.getTrxLayer();
			
			Map<String, Object> valueInterest = mapValueInterest.get(compositeId);
			if(valueInterest == null) {
				valueInterest = new HashMap<String, Object>();
				valueInterest.put("id", compositeId);
				valueInterest.put("groupId", ii.getTrxCobGroup());
				valueInterest.put("group", ii.getTrxCobGroupDesc());
				valueInterest.put("layer", ii.getTrxLayer());
				
				mapValueInterest.put(compositeId, valueInterest);
			}
			
			BigDecimal xol = ii.getTrxXolRate();
			BigDecimal sumXol = Param.getBdWithDef(valueInterest, "xol");
			valueInterest.put("xol", sumXol.add(xol));
			
			BigDecimal estOgnrpi = ii.getTrxOgnrpi();
			BigDecimal sumEstOgnrpi = Param.getBdWithDef(valueInterest, "estOgnrpi");
			valueInterest.put("estOgnrpi", sumEstOgnrpi.add(estOgnrpi));
			
			BigDecimal estOgnrpiAct = ii.getTrxOgnrpiAct();
			BigDecimal sumEstOgnrpiAct = Param.getBdWithDef(valueInterest, "estOgnrpiAct");
			valueInterest.put("estOgnrpiAct", sumEstOgnrpiAct.add(estOgnrpiAct));
			
			BigDecimal depositPct = ii.getTrxDepositRate();
			BigDecimal sumDepositPct = Param.getBdWithDef(valueInterest, "depositPct");
			valueInterest.put("depositPct", sumDepositPct.add(depositPct));	
			
			BigDecimal limit = ii.getTrxLimitAmt();
			BigDecimal sumLimit = Param.getBdWithDef(valueInterest, "limit");
			valueInterest.put("limit", sumLimit.add(limit));	
		}
		
		List<Map<String, Object>> listValueInterest = new ArrayList<Map<String,Object>>(mapValueInterest.values());
		
		//calculate value
		for(Map<String, Object> valueInterest : listValueInterest) {
			String groupId = Param.getStr(valueInterest, "groupId");
			String layer = Param.getStr(valueInterest, "layer");
			
			BigDecimal xolRate = getRate(Param.getBd(valueInterest, "xol"));
			BigDecimal estOgnrpi = Param.getBd(valueInterest, "estOgnrpi");
			BigDecimal estOgnrpiAct = Param.getBd(valueInterest, "estOgnrpiAct");
			BigDecimal depositRate = getRate(Param.getBd(valueInterest, "depositPct"));
			BigDecimal limit = Param.getBd(valueInterest, "limit");
			
			BigDecimal sumEstOgnrpi = layer.equals("Subs Layer") ? 
					estOgnrpi : getSumEstOgnrpi(listValueInterest, groupId, false);
			BigDecimal sumEstOgnrpiAct = layer.equals("Subs Layer") ? 
					estOgnrpiAct : getSumEstOgnrpi(listValueInterest, groupId, true);
			
			BigDecimal premium = sumEstOgnrpi.multiply(xolRate);			
			BigDecimal premiumAct = sumEstOgnrpiAct.multiply(xolRate);
			BigDecimal deposit = premium.multiply(depositRate);
			BigDecimal depositAct = premiumAct.multiply(depositRate);
			BigDecimal rol = premium.divide(limit, 4, RoundingMode.HALF_UP).multiply(BD_100);
			BigDecimal rolAct = premium.divide(limit, 4, RoundingMode.HALF_UP).multiply(BD_100);
			
			valueInterest.put("premium", premium);
			valueInterest.put("premiumAct", premiumAct);
			valueInterest.put("deposit", deposit);
			valueInterest.put("depositAct", depositAct);
			valueInterest.put("rol", rol);
			valueInterest.put("rolAct", rolAct);
		}
		
		return mapValueInterest;
	}
	
	private Map<String, Map<String, Object>> getDeltaAdjustment(
			Map<String, Map<String, Object>> mapValueInterest) {
		
		List<String> interestRemove = new ArrayList<String>();
		for(Map<String, Object> valueInterest : mapValueInterest.values()) {			
			BigDecimal premiumAct = Param.getBd(valueInterest, "premiumAct").setScale(4);
			BigDecimal deposit = Param.getBd(valueInterest, "deposit").setScale(4);
			
			if(premiumAct.compareTo(deposit) <= 0)
				interestRemove.add(valueInterest.get("id").toString());
			else
				valueInterest.put("deposit", premiumAct.subtract(deposit));
			
		}
		
		for(String id : interestRemove) {
			mapValueInterest.remove(id);
		}
		
		return mapValueInterest;
	}
	
	private Map<String, Map<String, Object>> getValueReins(List<TR0006KEntity> reins,
			Map<String, Map<String, Object>> mapValueInterest) {		
		Map<String, Map<String, Object>> mapValueReins = new HashMap<String, Map<String,Object>>();
		
		for(TR0006KEntity r : reins) {
			String compositeId = r.getTrxCobGroup() + r.getTrxLayer();
			
			Map<String, Object> valueInterest = mapValueInterest.get(compositeId);
			
			String id = UUID.randomUUID().toString();
			Map<String, Object> valueReins = new HashMap<String, Object>();			
			valueReins.put("id", id);
			valueReins.put("compositeId", compositeId);
			valueReins.put("reinsuranceId", r.getTrxInsId());
			valueReins.put("groupId", r.getTrxCobGroup());
			valueReins.put("group", r.getTrxCobGroupDesc());
			valueReins.put("layer", r.getTrxLayer());
			
			BigDecimal deposit = BigDecimal.ZERO;
			BigDecimal depositAct = BigDecimal.ZERO;
			if(valueInterest != null) {
				deposit = Param.getBdWithDef(valueInterest, "deposit");
				depositAct = Param.getBdWithDef(valueInterest, "depositAct");
			}
				
			BigDecimal shareRate = getRate(r.getTrxInsShare());
			BigDecimal riCommRate = getRate(r.getTrxRiComm());
			BigDecimal premiumAmt = deposit.multiply(shareRate);
			BigDecimal premiumAmtAct = depositAct.multiply(shareRate);

			BigDecimal riCommAmt = premiumAmt.multiply(riCommRate);
			BigDecimal riCommAmtAct = premiumAmtAct.multiply(riCommRate);
			BigDecimal payToUWAmt = premiumAmt.subtract(riCommAmt);
			BigDecimal payToUWAmtAct = premiumAmtAct.subtract(riCommAmt);
			
			valueReins.put("premium", premiumAmt);
			valueReins.put("premiumAct", premiumAmtAct);
			valueReins.put("riCommAmount", riCommAmt);
			valueReins.put("riCommAmountAct", riCommAmtAct);
			valueReins.put("payToUW", payToUWAmt);
			valueReins.put("payToUWAct", payToUWAmtAct);
			
			if(valueInterest != null)
				mapValueReins.put(id, valueReins);
		}
		
		return mapValueReins;
	}
	
	private BigDecimal getRate(BigDecimal value) {
		return value.divide(BD_100, 6, RoundingMode.HALF_UP);
	}
	
	private BigDecimal getSumEstOgnrpi(List<Map<String, Object>> listValueInterest, String groupId, Boolean isAct) {
		BigDecimal sumEstOgnrpi = BigDecimal.ZERO;
		BigDecimal sumEstOgnrpiAct = BigDecimal.ZERO;
		
		for(Map<String, Object> valueInterest : listValueInterest) {
			String groupIdValue = Param.getStr(valueInterest, "groupId");
			
			if(groupIdValue.equals(groupId)) {
				sumEstOgnrpi = sumEstOgnrpi.add(Param.getBd(valueInterest, "estOgnrpi"));
				sumEstOgnrpiAct = sumEstOgnrpiAct.add(Param.getBd(valueInterest, "estOgnrpiAct"));
			}
		}
		
		if(isAct)
			return sumEstOgnrpiAct;
		else
			return sumEstOgnrpi;
	}

	@Override
	@Transactional
	public Object cancel(Map<String, Object> param) throws ParseException {
		String trxVoucherId = Param.getStrWithDef(param, TRX_VOUCHER_ID);
		String user = param.get(Param.USER).toString();
		logger.info("Cancel Adjustment for request id : {}.", trxVoucherId);

		Date now = Calendar.getInstance().getTime();
		tr0006Repo.cancelAdjustment(user, now, TRE, RQ, trxVoucherId);
		tr0012Repo.cancelAdjustment(user, now, RQ, trxVoucherId);
		
		List<TR0012Entity> adjustmentList = tr0012Repo.getDataAdjustment(RQ, trxVoucherId);
		
		Date appDate = common.getAppDate();
		
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(appDate);
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		//reverse
		List<TR0001Entity> reverseT1 = new ArrayList<TR0001Entity>();
		List<TR0002Entity> reverseT2 = new ArrayList<TR0002Entity>();
		for(TR0012Entity adjustment : adjustmentList) {
			TR0001Entity t1 = tr0001Repo.findByGlVoucherId(adjustment.getTrxVoucherId());
			TR0001Entity t1Rvs = new TR0001Entity();
			t1Rvs.setGlTrxClass(t1.getGlTrxClass());
			t1Rvs.setGlType(t1.getGlType());
			
			String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(appDate));
			t1Rvs.setGlVoucherId(voucherId);
			
			t1Rvs.setGlTrxDate(appDate);
			t1Rvs.setGlTrxDue(appDate);
			t1Rvs.setGlTrxMonth(month.byteValue());
			t1Rvs.setGlTrxYear(year.shortValue());
			
			t1Rvs.setGlTrxOfficeId(t1.getGlTrxOfficeId());
			t1Rvs.setGlTrxProject(t1.getGlTrxProject());
			t1Rvs.setGlTrxClient(t1.getGlTrxClient());
			t1Rvs.setGlTrxValueOrg(t1.getGlTrxValueOrg());
			t1Rvs.setGlTrxValueIdr(t1.getGlTrxValueIdr());
			
			t1Rvs.setGlTrxDesc("Cancel - " + t1.getGlTrxDesc());
			t1Rvs.setGlTrxStatus(t1.getGlTrxStatus());
			t1Rvs.setGlDataStatus(t1.getGlDataStatus());
			t1Rvs.setCreateOn(now);
			t1Rvs.setCreateBy(user);
			t1Rvs.setModifyOn(now);
			t1Rvs.setModifyBy(user);
			
			reverseT1.add(t1Rvs);
			
			List<TR0002Entity> tr2List = tr0002Repo.findByGlTypeAndGlVoucherId(t1.getGlType(), t1.getGlVoucherId());
			for(TR0002Entity tr2 : tr2List) {
				TR0002Entity tr2Rvs = new TR0002Entity();
				tr2Rvs.setGlTrxClass(t1Rvs.getGlTrxClass());
				tr2Rvs.setGlType(t1Rvs.getGlType());
				tr2Rvs.setGlVoucherId(t1Rvs.getGlVoucherId());
				tr2Rvs.setGlAccount(tr2.getGlAccount());
				
				tr2Rvs.setGlCurrId(tr2.getGlCurrId());
				tr2Rvs.setGlCurrRate(tr2.getGlCurrRate());
				
				tr2Rvs.setGlOrgDebit(tr2.getGlOrgCredit());
				tr2Rvs.setGlIdrDebit(tr2.getGlIdrCredit());
				tr2Rvs.setGlOrgCredit(tr2.getGlOrgDebit());
				tr2Rvs.setGlIdrCredit(tr2.getGlIdrDebit());
				
				reverseT2.add(tr2Rvs);
			}
		}
		
		tr0001Repo.saveAll(reverseT1);
		tr0002Repo.saveAll(reverseT2);
		
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		response.setResult(trxVoucherId);
		return response;
	}
	
}
