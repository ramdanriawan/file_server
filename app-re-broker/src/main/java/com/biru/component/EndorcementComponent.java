package com.biru.component;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biru.ReBrokerConstants.CODE;
import com.biru.common.param.Param;
import com.biru.entity.MA0014Entity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0006AEntity;
import com.biru.entity.TR0006BEntity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0012Entity;
import com.biru.repository.MA0014Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0006ARepo;
import com.biru.repository.TR0006BRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewInqTr0006Repo;
import com.biru.service.CommonService;
import com.biru.view.ViewInqTr0006Entity;

@Component
public class EndorcementComponent {
	
	@Autowired
	private TR0006Repo tR0006Repo;
	
	@Autowired
	private TR0012Repo tR0012Repo;
	
	@Autowired
	private TR0001Repo tR0001Repo;
	
	@Autowired
	private TR0002Repo tR0002Repo;
	
	@Autowired
	private MA0014Repo ma0014Repo;
	
	@Autowired
	private TR0006ARepo tR0006ARepo;
	
	@Autowired
	private TR0006BRepo tR0006BRepo;
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private ResultComponent resultComponent;
	
	@Autowired
	private ViewInqTr0006Repo viewInqTr0006Repo;
	
	@Autowired
	private VoucherComponent voucherComponent;

	private SimpleDateFormat sdfDateTime;
	private SimpleDateFormat formatDateDb;
	
	private static List<String> trxType = new ArrayList<String>();
	
	private static String RQ = "RQ";
	private static String STATUS_500 = "500";
	private static String MSG_DATA_PROD_NOT_FOUND = "Data production not found!";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public EndorcementComponent() {
		super();
		trxType.clear();
		Collections.addAll(trxType, "PU", "SE", "PO");
		
		this.formatDateDb = new SimpleDateFormat("MM-dd-yyyy");
		this.sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}

	@Transactional
	public Object cancelClosing(Map<String, Object> param) throws Exception {		
		String idKey = Param.getStr(param, "idKey");
		String user = Param.getStr(param, Param.USER);
		Long millisCreateOn = Param.getLong(param, "millisInqTr12CreateOn");

		String reason = null;
		List<ViewInqTr0006Entity> views = viewInqTr0006Repo.findByIdKey(idKey);
		if(views.size() == 0) {
			reason = MSG_DATA_PROD_NOT_FOUND;
			logger.info("Failed cancelClosing - with reason : {}", reason);
			return resultComponent.createResponse(STATUS_500, reason);
		}
		
		ViewInqTr0006Entity view = views.get(0);
		String trxVoucherId = view.getTrxVoucherId();	//trxVoucherId data production
		Boolean isProduction = view.getTrxVoucherId().equals(view.getTr6VoucherId()) ? Boolean.TRUE : Boolean.FALSE;
		
		List<TR0012Entity> listTR0012Entity = null;
		List<TR0012Entity> listCancel = null;
		if(isProduction) {	//Production
			listTR0012Entity = tR0012Repo.findSettleProd(RQ, trxVoucherId, trxType, BigDecimal.ZERO);
			listCancel = tR0012Repo.findCancelProd(RQ, trxVoucherId, trxType, BigDecimal.ZERO);
			
			//get all data production and endorsement
			views = viewInqTr0006Repo.findByTrxVoucherId(trxVoucherId);
		}else {				//Endorsement
			Calendar calCreateOn = Calendar.getInstance();
			calCreateOn.setTimeInMillis(millisCreateOn);
			
			logger.info("{} calCreateOn : '{}'", view.getTrxSource(), sdfDateTime.format(calCreateOn.getTime()));
			listTR0012Entity = tR0012Repo.findSettleEndors(RQ, trxVoucherId, trxType, BigDecimal.ZERO, calCreateOn.getTime());
			listCancel = tR0012Repo.findCancelEndors(RQ, trxVoucherId, trxType, BigDecimal.ZERO, calCreateOn.getTime());
		}		
		
		logger.info("{} trxVoucherId : '{}', TR0012 settle size : '{}', cancel size : '{}'", 
				view.getTrxSource(), trxVoucherId, listTR0012Entity.size(), listCancel.size());
		if(!listTR0012Entity.isEmpty()) {
			String message = "Failed Cancel Settlement, cause : ";
			for(TR0012Entity tr0012Entity : listTR0012Entity) {
				message = message.concat("\n")
						.concat(tr0012Entity.getTrxType())
						.concat("-")
						.concat(tr0012Entity.getTrxVoucherId());
			}
			
			return resultComponent.createResponse(STATUS_500, message);
		}else {
			Set<String> keySet = new HashSet<String>();
			Date now = Calendar.getInstance().getTime();
			for(TR0012Entity tr0012Entity : listCancel) {
				param.put("trxType", tr0012Entity.getTrxType());
				param.put("trxVoucherId", tr0012Entity.getTrxVoucherId());
				
				String key = tr0012Entity.getTrxType() + "_" + tr0012Entity.getTrxVoucherId();
				if(keySet.contains(key))
					continue;
				
				keySet.add(key);
				copyTr0001(param, now);
				copyTr0002(param);
			}
			updateTr0012(listCancel, user, now);
			updateTr006(views, user, now);
		}
		
		return resultComponent.createResponse(null);
	}
	
	private void copyTr0001(Map<String, Object> param, Date now) 
			throws ParseException {
		logger.info(">>>>> process copyTr0001 >>>>");
		String trxType = Param.getStr(param, "trxType");
		String trxVoucherId = Param.getStr(param, "trxVoucherId");
		
		MA0014Entity ma0014Entity = ma0014Repo
				.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);
		Date appDate = formatDateDb.parse(ma0014Entity.getPaChildValue());
		param.put("appDate", appDate);
		
		String arrayAppDate [] = ma0014Entity.getPaChildValue().split("-");
		String newVoucherId = voucherComponent.saveVoucherCounter(arrayAppDate[1]+"-"+arrayAppDate[0]+"-"+arrayAppDate[2]);
		param.put("newTrxVoucherId", newVoucherId);
		
		TR0001Entity tR0001Entity = tR0001Repo.findByGlTypeAndGlVoucherId(trxType, trxVoucherId);
		logger.info("old TR0001 >> {}", tR0001Entity);
		
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(common.getAppDate());
		
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		String user = Param.getStr(param, Param.USER);
		TR0001Entity newTR0001Entity = new TR0001Entity();
		newTR0001Entity.setCreateBy(user);
		newTR0001Entity.setCreateOn(now);
		newTR0001Entity.setGlDataStatus(tR0001Entity.getGlDataStatus());
		newTR0001Entity.setGlReffId(tR0001Entity.getGlReffId());
		newTR0001Entity.setGlTrxClass(tR0001Entity.getGlTrxClass());
		newTR0001Entity.setGlTrxClient(tR0001Entity.getGlTrxClient());
		newTR0001Entity.setGlTrxClientDesc(tR0001Entity.getGlTrxClientDesc());
		newTR0001Entity.setGlTrxDate(calTrx.getTime());
		newTR0001Entity.setGlTrxDateStr(tR0001Entity.getGlTrxDateStr());
		newTR0001Entity.setGlTrxDesc("CANCEL ".concat(tR0001Entity.getGlTrxDesc()));
		newTR0001Entity.setGlTrxDue(tR0001Entity.getGlTrxDue());
		newTR0001Entity.setGlTrxDueStr(tR0001Entity.getGlTrxDueStr());
		newTR0001Entity.setGlTrxInvoice(tR0001Entity.getGlTrxInvoice());
		newTR0001Entity.setGlTrxMonth(month.byteValue());
		newTR0001Entity.setGlTrxOfficeId(tR0001Entity.getGlTrxOfficeId());
		newTR0001Entity.setGlTrxProject(tR0001Entity.getGlTrxProject());
		newTR0001Entity.setGlTrxStatus("0");
		newTR0001Entity.setGlTrxStatusDesc(tR0001Entity.getGlTrxStatusDesc());
		newTR0001Entity.setGlTrxValueIdr(tR0001Entity.getGlTrxValueIdr());
		newTR0001Entity.setGlTrxValueIdrStr(tR0001Entity.getGlTrxValueIdrStr());
		newTR0001Entity.setGlTrxValueOrg(tR0001Entity.getGlTrxValueOrg());
		newTR0001Entity.setGlTrxValueIdrStr(tR0001Entity.getGlTrxValueIdrStr());
		newTR0001Entity.setGlTrxYear(year.shortValue());
		newTR0001Entity.setGlType(trxType);
		newTR0001Entity.setGlVoucherId(newVoucherId);
		newTR0001Entity.setModifyBy(user);
		newTR0001Entity.setModifyOn(now);
		newTR0001Entity.setPrRate(tR0001Entity.getPrRate());
		logger.info("new TR0001 >> {}", newTR0001Entity);
		
		tR0001Repo.save(newTR0001Entity);
		logger.info(">>>>> process copyTr0001 SUCCESS >>>>");
	}
	private void copyTr0002(Map<String, Object> param) {
		/* COPY TR0002, cari data dengan GL_TYPE = A dan GL_VOUCHER_ID=B, dengan
		 * mengubah posisi nilai GL_ORG_DEBIT ke GL_ORG_CREDIT (dan sebaliknya),
		 * mengubah posisi nilai GL_IDR_DEBIT ke GL_IDR_DEBIT (dan sebaliknya).
		 * GL_voucher_id = GL_VOUCHER_ID data baru di TR0001. (proses ini minimal 2)
		 */
		
		logger.info(">>>>> process copyTr0002 >>>>");
		String trxType = Param.getStr(param, "trxType");
		String trxVoucherId = Param.getStr(param, "trxVoucherId");
		String newTrxVoucherId = Param.getStr(param, "newTrxVoucherId");
		
		List<TR0002Entity> listTR0002Entity = tR0002Repo.findByGlTypeAndGlVoucherId(trxType, trxVoucherId);
		for(TR0002Entity tr0002Entity : listTR0002Entity) {
			logger.info("old TR0002 >> {}", tr0002Entity);
			
			TR0002Entity newTR0002Entity = new TR0002Entity();
			newTR0002Entity.setGlAccount(tr0002Entity.getGlAccount());
			newTR0002Entity.setGlCurrId(tr0002Entity.getGlCurrId());
			newTR0002Entity.setGlCurrRate(tr0002Entity.getGlCurrRate());
			newTR0002Entity.setGlIdrCredit(tr0002Entity.getGlIdrDebit());
			newTR0002Entity.setGlIdrDebit(tr0002Entity.getGlIdrCredit());
			newTR0002Entity.setGlOrgCredit(tr0002Entity.getGlOrgDebit());
			newTR0002Entity.setGlOrgDebit(tr0002Entity.getGlOrgCredit());
			newTR0002Entity.setGlTrxClass(tr0002Entity.getGlTrxClass());
			newTR0002Entity.setGlType(trxType);
			newTR0002Entity.setGlVoucherId(newTrxVoucherId);
			
			tR0002Repo.save(newTR0002Entity);
			logger.info("new TR0002 >> {} >> SAVED", newTR0002Entity);
		}
		
		logger.info(">>>>> process copyTr0002 SUCCESS >>>>");
	}
	
	private void updateTr0012(List<TR0012Entity> tr12Entities, String user, Date now) {
		for(TR0012Entity tr12 : tr12Entities) {
			tr12.setTrxDataStatus("12");
			tr12.setModifyBy(user);
			tr12.setModifyOn(now);
		}
		
		tR0012Repo.saveAll(tr12Entities);
	}
	
	private void updateTr006(List<ViewInqTr0006Entity> views, String user, Date now) {
		for(ViewInqTr0006Entity v : views) {
			logger.info("updateTr006 trxVoucherId : {}", v.getTr6VoucherId());
			tR0006Repo.updateTrxDataStatus0(RQ, v.getTr6VoucherId(), user, now);
		}
	}
	
	@Transactional
	public void save(Map<String, Object> param) throws ParseException {
		String voucherId = Param.getStr(param, "trxVoucherId");
		String t1Remark = Param.getStr(param, "t1Remark");
		String t2Remark = Param.getStr(param, "t2Remark");
		String t4Remark = Param.getStr(param, "t4Remark");
		String modifyBy = Param.getStr(param, "modifyBy");

		MA0014Entity ma0014Entity = ma0014Repo.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);
		SimpleDateFormat formatDateDb = new SimpleDateFormat("MM-dd-yyyy");
		Date appDate = formatDateDb.parse(ma0014Entity.getPaChildValue());
		
//		Date insuredPeriod = Param.getDate(param, "insuredPeriod");
//		Date insuredPeriodTo = Param.getDate(param, "insuredPeriod");
//		Date reinsuredPeriod = Param.getDate(param, "insuredPeriod");
//		Date reinsuredPeriodTo = Param.getDate(param, "insuredPeriod");
		
		List<TR0006Entity> listTR0006Entity = tR0006Repo.findByTrxTrxIdAndTrxVoucherId("RQ", voucherId);
		for (TR0006Entity tr0006Entity : listTR0006Entity) {
			tr0006Entity.setTrxRemarks(t1Remark);
			tr0006Entity.setModifyBy(modifyBy);
			tr0006Entity.setModifyOn(appDate);
		}
		tR0006Repo.saveAll(listTR0006Entity);
		
//		saveInterestInsured(param);
		List<TR0006AEntity>listTR0006AEntity =tR0006ARepo.findByTrxTrxIdAndTrxVoucherId("RQ", voucherId);
		for (TR0006AEntity tr0006aEntity : listTR0006AEntity) {
//			if(insuredPeriod != null) {
//				tr0006aEntity.setTrxInsStart(insuredPeriod);
//			}
//			if(insuredPeriodTo != null) {
//				tr0006aEntity.setTrxInsEnd(insuredPeriodTo);
//			}
//			if(reinsuredPeriod != null) {
//				tr0006aEntity.setTrxReinsStart(reinsuredPeriod);
//			}
//			if(reinsuredPeriodTo != null) {
//				tr0006aEntity.setTrxReinsEnd(reinsuredPeriodTo);
//			}
			tr0006aEntity.setTrxRemarks(t2Remark);
		}
		tR0006ARepo.saveAll(listTR0006AEntity);
		
//		saveReinsurance(param);
		List<TR0006BEntity>listTR0006BEntity  = tR0006BRepo.findByTrxTrxIdAndTrxVoucherId("RQ", voucherId);
		for (TR0006BEntity tr0006bEntity : listTR0006BEntity) {
			tr0006bEntity.setTrxInsRemarks(t4Remark);
		}
		tR0006BRepo.saveAll(listTR0006BEntity);
		

		
		
	}
}
