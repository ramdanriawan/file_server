package com.biru.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.CLOSING_PARAM;
import com.biru.ReBrokerConstants.DOCUMENT;
import com.biru.ReBrokerConstants.MESSAGE;
import com.biru.ReBrokerConstants.PARAM;
import com.biru.ReBrokerConstants.PROD_MAPKEY;
import com.biru.ReBrokerConstants.REST.PRODUCTION_CLASS;
import com.biru.ReBrokerConstants.REST.PRODUCTION_TRE_TYPE;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.ReportUtils;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0011Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0015AEntity;
import com.biru.entity.MA0018Entity;
import com.biru.entity.ResponseEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0003AEntity;
import com.biru.entity.TR0003Entity;
import com.biru.entity.TR0006DEntity;
import com.biru.entity.TR0006EEntity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0006FEntity;
import com.biru.entity.TR0006JEntity;
import com.biru.entity.TR0006KEntity;
import com.biru.entity.TR0012Entity;
import com.biru.helper.TreClosingParam;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0011Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0003ARepo;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0006DRepo;
import com.biru.repository.TR0006ERepo;
import com.biru.repository.TR0006FRepo;
import com.biru.repository.TR0006JRepo;
import com.biru.repository.TR0006KRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewInqProductionRepo;
import com.biru.service.CommonService;
import com.biru.service.ProductionTreatyService;
import com.biru.view.ViewInqProduction;

@Service
public class ProductionTreatySeviceImpl extends AbstractCommon implements ProductionTreatyService {
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private ViewInqProductionRepo viewInqProductionRepo;
	
	@Autowired
	private TR0006Repo tR0006Repo;
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private MA0011Repo mA0011Repo;
	
	@Autowired
	private MA0014Repo mA0014Repo;
	
	@Autowired
	private MA0015ARepo mA0015ARepo;
	
	@Autowired
	private MA0018Repo mA0018Repo;
	
	@Autowired
	private TR0001Repo tR0001Repo;
	
	@Autowired
	private TR0002Repo tR0002Repo;
	
	@Autowired
	private TR0003Repo tR0003Repo;
	
	@Autowired
	private TR0003ARepo tR0003ARepo;
	
	@Autowired
	private TR0012Repo tR0012Repo;
	
	@Autowired
	private TR0006DRepo tR0006DRepo;
	
	@Autowired
	private TR0006ERepo tR0006ERepo;
	
	@Autowired
	private TR0006FRepo tR0006FRepo;
	
	@Autowired
	private TR0006JRepo tR0006JRepo;
	
	@Autowired
	private TR0006KRepo tR0006KRepo;
	
	private Logger logger = LoggerFactory.getLogger(ProductionTreatySeviceImpl.class);
	
	private static final String APP_DATE 		= "appDate";
	private static final String CLIENT_CONFIRMATION_DATE = "clientConfirmationDate";
	private static final String COB				= "cob";
	private static final String CODE			= "code";
	private static final String CREATE_BY		= "createBy";
	private static final String CREATE_ON		= "createOn";
	private static final String DATE 			= "date";
	private static final String GROUP			= "group";
	private static final String LAYER		 	= "layer";
	private static final String PROCESS			= "process";
	private static final String QS				= "QS";
	private static final String REINSURANCE 	= "Reinsurance";
	private static final String REMARKS		 	= "remarks";
	private static final String RQ 				= "RQ";
	private static final String TRE 			= "TRE";
	private static final String TRX_VOUCHER_ID 	= "trxVoucherId";
	private static final String TYPE		 	= "type";
	private static final String VOUCHER_ID	 	= "voucherId";

	private static final String CLOSINGSLIP		= "closingslip";
	private static final String PLACING			= "placing";
	private static final String QUOTATION		= "quotation";
	
	private static final String CLIENT_TYPE 		= "0";
	private static final String REINSURANCE_TYPE 	= "1";

	private static final String CREDIT_NOTE 	= "CREDIT NOTE";
	
	private static final BigDecimal BD_100 = new BigDecimal("100");
	
	@Override
	public Object inquiry(Map<String, Object> param) throws ParseException {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String filterValue = Param.getStrWithDef(param, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Date appDate = common.getAppDate();
		Page<ViewInqProduction> data = viewInqProductionRepo.findProduction(appDate, PRODUCTION_CLASS.TRE, filterValue.toLowerCase(), pageable);
		
		for(ViewInqProduction view : data.getContent()) {
			String action = "<button class=\"btn btn-secondary\" onclick=\"edit('"+view.getRequestId()+"')\">" 
					+ "<i class=\"fa fa-edit\"></i>" 
					+ "	</button> &nbsp;"
					+ "<button class=\"btn btn-danger\" onclick=\"remove('"+view.getRequestId()+"')\">" 
					+ "<i class=\"fa fa-trash\"></i>" 
					+ "</button>";
			
			view.setAction(action);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object inquiryModify(Map<String, Object> param) {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		if(StringUtils.isBlank(trxVoucherId))
			return "No data found";
				
		Map<String, Object> data = new HashMap<String, Object>();
		
		List<TR0006Entity> client = tR0006Repo.getDataClient(TRE, RQ, trxVoucherId);
		data.put("t1Data", client);
		List<TR0006JEntity> dataAdd = tR0006JRepo.getAdditionalTreaty(TRE, RQ, trxVoucherId);
		data.put("DataAdd", dataAdd);
		
		List<TR0006KEntity> reins = tR0006KRepo.getReinsuranceTreaty(TRE, RQ, trxVoucherId);
		data.put("t4Data", reins);
		
		List<TR0006DEntity> tc = tR0006DRepo.getDataTcTreaty(TRE, RQ, trxVoucherId);
		data.put("t6Data", tc);
		
		List<TR0006EEntity> checklist1 = tR0006ERepo.getDataChecklist1Treaty(TRE, RQ, trxVoucherId);
		data.put("t7Data1", checklist1);
		
		List<TR0006FEntity> checklist2 = tR0006FRepo.getDataChecklist2Treaty(TRE, RQ, trxVoucherId);
		data.put("t7Data2", checklist2);
		
		return data;
	}

	@Override
	public Object delete(Map<String, Object> param) {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		if(StringUtils.isBlank(trxVoucherId))
			return "No data deleted";
		
		tR0006Repo.deleteByTrxClassAndTrxIdAndTrxVoucherId(PRODUCTION_CLASS.TRE, RQ, trxVoucherId);
		tR0006JRepo.deleteByTrxClassAndTrxIdAndTrxVoucherId(PRODUCTION_CLASS.TRE, RQ, trxVoucherId);
		
		return "Data successfully deleted";
	}
	
	@Override
	@Transactional
	public Object save(Map<String, Object> param) throws ParseException {
		logger.info("Production Treaty save.");
		
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		Date appDate = common.getAppDate();
		if(StringUtils.isBlank(voucherId))	//create
			voucherId = voucherComponent.saveVoucherCounter(formatDate.format(appDate));
		
		Date now = Calendar.getInstance().getTime();
		Date createOn = now;
		
		Long createOnMillis = Param.getLong(param, CREATE_ON);
		if(createOnMillis != null)
			createOn = new Date(createOnMillis);
		
		param.put(TRX_VOUCHER_ID, voucherId);
		param.put(APP_DATE, appDate);
		param.put(DATE, now);

		saveClientInformation(param);
		saveInterestInsured(param);
		saveReinsurance(param);
		saveTc(param);
		saveChecklist(param);
		
		String user = Param.getStrWithDef(param, Param.USER);
		String createBy = Param.getStrWithDef(param, CREATE_BY);
		if(StringUtils.isBlank(createBy))
			createBy = user;
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("voucherId", voucherId);
		message.put("createOn", createOn.getTime());
		message.put("createBy", createBy);

		response.setResult(voucherId);
		response.setMessage(message);
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	private void saveClientInformation(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		String trxDataStatus = "0";
		List<TR0006Entity> tr6Entities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		if(tr6Entities.size()>0)
			trxDataStatus = tr6Entities.get(0).getTrxDataStatus();
		
		tR0006Repo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		
		Date now = (Date) param.get(DATE);
		
		Map<String, Object> t1Data = (HashMap<String, Object>) param.get("t1Data");
		
		//========== Save TR0006Entity ==========//
		TR0006Entity tR0006Entity = new TR0006Entity();
		tR0006Entity.setTrxTrxId(RQ);
		tR0006Entity.setTrxVoucherId(voucherId);
		tR0006Entity.setTrxClass(TRE);		
		tR0006Entity.setTrxClient(Param.getStr(t1Data, "client"));
		tR0006Entity.setTrxShare(Param.getBdWithDef(t1Data, "share"));
		tR0006Entity.setTrxOfficer(Param.getStr(t1Data, "officer"));
		tR0006Entity.setTrxPayMthd(Param.getStr(t1Data, "paymentMethod"));
		tR0006Entity.setTrxCurrId(Param.getStr(t1Data, "curr"));
		tR0006Entity.setTrxCurrRate(Param.getBdWithDef(t1Data, "exchangeRate"));
		tR0006Entity.setTrxRemarks(Param.getStr(t1Data, "remarks"));
		tR0006Entity.setTrxTreAdj("0");
		tR0006Entity.setTrxDataStatus(trxDataStatus);
		
		//default value
		tR0006Entity.setTrxFeeClient(BigDecimal.ZERO);
		tR0006Entity.setTrxSumInsured(BigDecimal.ZERO);
		tR0006Entity.setTrxTsiAmount(BigDecimal.ZERO);
		
		String user = Param.getStrWithDef(param, Param.USER);
		String createBy = Param.getStrWithDef(param, CREATE_BY);
		Date createOn = now;
		
		if(StringUtils.isNotBlank(createBy)) {	
			createOn = new Date(Param.getLong(param, CREATE_ON));
			
			tR0006Entity.setModifyBy(user);
			tR0006Entity.setModifyOn(now);
		}else {
			createBy = user;
		}
		
		tR0006Entity.setCreateBy(createBy);
		tR0006Entity.setCreateOn(createOn);
		
		tR0006Repo.save(tR0006Entity);
	}
	
	@SuppressWarnings("unchecked")
	private void saveInterestInsured(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006JRepo.deleteByTrxClassAndTrxIdAndTrxVoucherId(TRE, RQ, voucherId);
		
		Map<String, Object> t1Data = (HashMap<String, Object>) param.get("t1Data");
		Map<String, Object> t2Data = (HashMap<String, Object>) param.get("t2Data");
		List<HashMap<String, Object>> t2DataTable = (ArrayList<HashMap<String, Object>>) t2Data.get("t2DataTable");
		
		String type = Param.getStr(t1Data, TYPE);
		List<TR0006JEntity> interest = new ArrayList<TR0006JEntity>();
		for(HashMap<String, Object> data : t2DataTable) {
			TR0006JEntity tR0006JEntity = new TR0006JEntity();
			tR0006JEntity.setTrxTrxId(RQ);
			tR0006JEntity.setTrxVoucherId(voucherId);
			tR0006JEntity.setTrxClass(TRE);
			tR0006JEntity.setTrxNonPro(type);
			tR0006JEntity.setTrxInsStart(Param.getDate(t1Data, "insuredPeriod"));
			tR0006JEntity.setTrxInsEnd(Param.getDate(t1Data, "insuredPeriodTo"));
			tR0006JEntity.setTrxCurrId(Param.getStr(t1Data, "curr"));
			tR0006JEntity.setTrxExchange(Param.getBdWithDef(t1Data, "exchangeRate"));
			tR0006JEntity.setTrxUsdRate(Param.getBd(t1Data, "usdRate"));
			
			if(PRODUCTION_TRE_TYPE.NON_PROPORTIONAL.equals(type)) {
				tR0006JEntity.setTrxCobGroup(Param.getStr(data, "groupId"));
				tR0006JEntity.setTrxCoverCode(Param.getStr(data, "cobId"));
				tR0006JEntity.setTrxBasicCover(Param.getStr(data, "basicCover"));
				tR0006JEntity.setTrxLayer(Param.getStr(data, "layer"));
				tR0006JEntity.setTrxReInst(Param.getBd(data, "reinstatement1"));
				tR0006JEntity.setTrxReinsRate(Param.getBd(data, "reinstatement2"));
				tR0006JEntity.setTrxLimitAmt(Param.getBd(data, "limit"));
				tR0006JEntity.setTrxDeducAmt(Param.getBd(data, "deductible"));
				tR0006JEntity.setTrxXolRate(Param.getBd(data, "xolRate"));
				tR0006JEntity.setTrxDepositRate(Param.getBd(data, "deposit"));
				tR0006JEntity.setTrxRemarks(Param.getStr(t2Data, "remarks"));
				tR0006JEntity.setTrxOgnrpi(Param.getBd(data, "estOgnrpi"));
				tR0006JEntity.setTrxMaxAccpt(Param.getBd(data, "maxAcceptance"));
				tR0006JEntity.setTrxXolReas(Param.getBd(data, "xolReas"));
				tR0006JEntity.setTrxDepositReas(Param.getBd(data, "depositReas"));
			}else {
				tR0006JEntity.setTrxCobGroup(Param.getStr(data, "groupId"));
				tR0006JEntity.setTrxCoverCode(Param.getStr(data, "classOfBusinessId"));
				tR0006JEntity.setTrxLayer(Param.getStr(data, "quotaShare"));
				tR0006JEntity.setTrxReInst(Param.getBd(data, "multiple"));
				tR0006JEntity.setTrxBasicCover(Param.getStr(data, "basicCover"));
				tR0006JEntity.setTrxOwnAmt(Param.getBd(data, "ownRisk"));
				tR0006JEntity.setTrxLimitAmt(Param.getBd(data, "limit"));
				tR0006JEntity.setTrxRemarks(Param.getStr(t2Data, "remarks"));
			}
			
			interest.add(tR0006JEntity);
		}
		
		if(interest.size() == 0) {
			TR0006JEntity tR0006JEntity = new TR0006JEntity();
			tR0006JEntity.setTrxTrxId(RQ);
			tR0006JEntity.setTrxVoucherId(voucherId);
			tR0006JEntity.setTrxClass(TRE);
			tR0006JEntity.setTrxNonPro(type);
			tR0006JEntity.setTrxInsStart(Param.getDate(t1Data, "insuredPeriod"));
			tR0006JEntity.setTrxInsEnd(Param.getDate(t1Data, "insuredPeriodTo"));
			tR0006JEntity.setTrxCurrId(Param.getStr(t1Data, "curr"));
			tR0006JEntity.setTrxExchange(Param.getBdWithDef(t1Data, "exchangeRate"));
			tR0006JEntity.setTrxUsdRate(Param.getBd(t1Data, "usdRate"));
			
			interest.add(tR0006JEntity);
		}
		
		tR0006JRepo.saveAll(interest);
	}
	
	@SuppressWarnings("unchecked")
	private void saveReinsurance(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006KRepo.deleteByTrxClassAndTrxIdAndTrxVoucherId(TRE, RQ, voucherId);
		
		Map<String, Object> t4Data = (HashMap<String, Object>) param.get("t4Data");
		List<HashMap<String, Object>> t4DataTable = (ArrayList<HashMap<String, Object>>) t4Data.get("t4DataTable");
		
		List<TR0006KEntity> reinsurance = new ArrayList<TR0006KEntity>();
		for(HashMap<String, Object> data : t4DataTable) {
			TR0006KEntity tR0006KEntity = new TR0006KEntity();
			tR0006KEntity.setTrxClass(TRE);
			tR0006KEntity.setTrxTrxId(RQ);
			tR0006KEntity.setTrxVoucherId(voucherId);
			tR0006KEntity.setTrxCobGroup(Param.getStr(data, "groupId"));
			tR0006KEntity.setTrxCoverCode(Param.getStr(data, "cobId"));
			tR0006KEntity.setTrxLayer(Param.getStr(data, "layer"));
			tR0006KEntity.setTrxInsId(Param.getStr(data, "reinsuranceId"));
			tR0006KEntity.setTrxInsShare(Param.getBd(data, "share"));
			tR0006KEntity.setTrxPremium(Param.getBd(data, "premium"));
			tR0006KEntity.setTrxRiComm(Param.getBd(data, "riComm"));
			
			reinsurance.add(tR0006KEntity);
		}
		
		tR0006KRepo.saveAll(reinsurance);
	}
	
	@SuppressWarnings("unchecked")
	private void saveTc(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006DRepo.deleteByTrxClassAndTrxIdAndTrxVoucherId(TRE, RQ, voucherId);

		Map<String, Object> t6Data = (HashMap<String, Object>) param.get("t6Data");
		List<HashMap<String, Object>> t6DataTable = (ArrayList<HashMap<String, Object>>) t6Data.get("t6DataTable");

		List<TR0006DEntity> tc = new ArrayList<TR0006DEntity>();
		for(HashMap<String, Object> data : t6DataTable) {
			TR0006DEntity tR0006DEntity = new TR0006DEntity();
			tR0006DEntity.setTrxClass(TRE);
			tR0006DEntity.setTrxTrxId(RQ);
			tR0006DEntity.setTrxVoucherId(voucherId);
			tR0006DEntity.setTrxTcCode(Param.getStr(data, "tcGroup"));
			tR0006DEntity.setTrxTcData(Param.getStr(data, "tcDetails"));
			tR0006DEntity.setTrxNonPro(Param.getStr(data, "type"));
			tR0006DEntity.setTrxCobGroup(Param.getStr(data, "groupCobId"));
			tR0006DEntity.setTrxCoverCode(Param.getStr(data, "cobId"));

			tc.add(tR0006DEntity);
		}
		
		if(tc.size() > 0)
			tR0006DRepo.saveAll(tc);
	}
	
	@SuppressWarnings("unchecked")
	private void saveChecklist(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006ERepo.deleteByTrxClassAndTrxIdAndTrxVoucherId(TRE, RQ, voucherId);
		tR0006FRepo.deleteByTrxClassAndTrxIdAndTrxVoucherId(TRE, RQ, voucherId);

		Map<String, Object> t7Data = (HashMap<String, Object>) param.get("t7Data");
		List<HashMap<String, Object>> t7DataTable1 = (ArrayList<HashMap<String, Object>>) t7Data.get("t7DataTable1");
		List<HashMap<String, Object>> t7DataTable2 = (ArrayList<HashMap<String, Object>>) t7Data.get("t7DataTable2");
		
		List<TR0006EEntity> payment = new ArrayList<TR0006EEntity>();
		for (HashMap<String, Object> data : t7DataTable1) {
			TR0006EEntity tR0006EEntity = new TR0006EEntity();
			tR0006EEntity.setTrxClass(TRE);
			tR0006EEntity.setTrxTrxId(RQ);
			tR0006EEntity.setTrxVoucherId(voucherId);
			
			tR0006EEntity.setTrxPrClient("0");
			String type = Param.getStr(data, "type");
			if(REINSURANCE.equals(type))
				tR0006EEntity.setTrxPrClient("1");

			tR0006EEntity.setTrxPrDate(Param.getDate(data, "payDate"));
			tR0006EEntity.setTrxPrShare(Param.getBdWithDef(data, "portion"));
			tR0006EEntity.setTrxPrAmt(Param.getBdWithDef(data, "portionAmt"));

			payment.add(tR0006EEntity);
		}

		List<TR0006FEntity> document = new ArrayList<TR0006FEntity>();
		for (HashMap<String, Object> data : t7DataTable2) {
			TR0006FEntity tR0006FEntity = new TR0006FEntity();
			tR0006FEntity.setTrxClass(TRE);
			tR0006FEntity.setTrxTrxId(RQ);
			tR0006FEntity.setTrxVoucherId(voucherId);
			tR0006FEntity.setTrxDocId(Param.getStr(data, "documentId"));
			tR0006FEntity.setTrxDocName(Param.getStr(data, "file"));
			tR0006FEntity.setTrxDocFolder(Param.getStr(data, "path"));
			tR0006FEntity.setTrxPrClient(Param.getStr(data, "typeOfCover"));
			
			document.add(tR0006FEntity);
		}
		
		if(payment.size() > 0)
			tR0006ERepo.saveAll(payment);
		
		if(document.size() > 0)
			tR0006FRepo.saveAll(document);
	}

	@Override
	public Object inquirySend(Map<String, Object> param) {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String process = Param.getStr(param, PROCESS);
		String remarks = Param.getStr(param, REMARKS);	

		List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
		if(PLACING.equals(process)) {
			String typeOfTreaty = tR0006JRepo.getTypeOfTreaty(TRE, RQ, trxVoucherId);
			if(PRODUCTION_TRE_TYPE.PROPORTIONAL.equals(typeOfTreaty)) {
				TR0006Entity client = tR0006Repo.getDataClient(TRE, RQ, trxVoucherId).get(0);
				List<TR0006JEntity> interestList = tR0006JRepo.getPlacingProp(TRE, RQ, trxVoucherId);
				for(TR0006JEntity interest : interestList) {
					Map<String, Object> reportObj = new HashMap<String, Object>();
					reportObj.put("name", client.getClientDesc());
					reportObj.put("cob", interest.getTrxCoverCodeDesc());
					reportObj.put("layer", interest.getTrxLayer());
					reportObj.put("document", "EOC");
					
					String fileName = "EOC - " + interest.getTrxCoverCodeDesc() + " - " + interest.getTrxLayer() + " - " + client.getClientDesc();
					String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '-', '" + interest.getTrxCoverCode() + "', '" + interest.getTrxLayer() + "', '" + client.getTrxClient() + "', 'placing')\">" 
							+ "<i class=\"fa fa-print\"></i>" 
							+ "</button>&nbsp;";
					action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '-', '" + interest.getTrxCoverCode() + "', '" + interest.getTrxLayer() + "', '" + client.getTrxClient() + "', 'placing', '" + fileName + "')\">" 
							+ "<i class=\"fas fa-file-word\"></i>" 
							+ "</button>";
					
					reportObj.put("action", action);
					table.add(reportObj);
				}
				
				List<TR0006KEntity> reinsuranceList = tR0006KRepo.getReinsuranceTreaty(TRE, RQ, trxVoucherId);
				for(TR0006KEntity reinsurance : reinsuranceList) {
					Map<String, Object> reportObj = new HashMap<String, Object>();
					reportObj.put("name", reinsurance.getTrxInsIdDesc());
					reportObj.put("cob", reinsurance.getTrxCoverCodeDesc());
					reportObj.put("layer", reinsurance.getTrxLayer());
					reportObj.put("document", "Closing Slip");
					
					String fileName = "CS - " + reinsurance.getTrxCoverCodeDesc() + " - " + reinsurance.getTrxLayer() + " - " + reinsurance.getTrxInsIdDesc();
					String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '-', '" + reinsurance.getTrxCoverCode() + "', '" + reinsurance.getTrxLayer() + "', '" + reinsurance.getTrxInsId() + "', 'closingslip')\">" 
							+ "<i class=\"fa fa-print\"></i>" 
							+ "</button>&nbsp;";
					action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '-', '" + reinsurance.getTrxCoverCode() + "', '" + reinsurance.getTrxLayer() + "', '" + reinsurance.getTrxInsId() + "', 'closingslip', '" + fileName + "')\">" 
							+ "<i class=\"fas fa-file-word\"></i>" 
							+ "</button>";
					
					reportObj.put("action", action);
					table.add(reportObj);
				}
				
				tR0006Repo.updateTrxDataStatus("3", remarks, TRE, RQ, trxVoucherId);
			}else if(PRODUCTION_TRE_TYPE.NON_PROPORTIONAL.equals(typeOfTreaty)) {
				table.addAll(createEOC(trxVoucherId));
				
				tR0006Repo.updateTrxDataStatus("3", remarks, TRE, RQ, trxVoucherId);
			}
		}else if(QUOTATION.equals(process)) {
			TR0006Entity client = tR0006Repo.getDataClient(TRE, RQ, trxVoucherId).get(0);
			Map<String, Object> reportObj = new HashMap<String, Object>();
			reportObj.put("name", client.getClientDesc());
			reportObj.put("document", "Covering Letter");
			
			String fileName = "CL - " + client.getClientDesc();
			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '-', '-', '-', '-', 'quotation')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '-', '-', '-', '-', 'quotation', '" + fileName + "')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			reportObj.put("action", action);
			table.add(reportObj);
			
			tR0006Repo.updateTrxDataStatus("5", remarks, TRE, RQ, trxVoucherId);
		}
		
		return table;
	}
	
	@Override
	public Object createReportDoc(Map<String, Object> param) throws Exception {
		String process = Param.getStr(param, PROCESS);
		
		if(PLACING.equals(process))
			return createPlacing(param, DOCUMENT.DOC);
		else if(QUOTATION.equals(process))
			return createQuotation(param, DOCUMENT.DOC);
		
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String typeOfTreaty = tR0006JRepo.getTypeOfTreaty(TRE, RQ, trxVoucherId);
		if(CLOSINGSLIP.equals(process) && PRODUCTION_TRE_TYPE.PROPORTIONAL.equals(typeOfTreaty))
			return createClosingProp(param, DOCUMENT.DOC);
		else if(CLOSINGSLIP.equals(process) && PRODUCTION_TRE_TYPE.NON_PROPORTIONAL.equals(typeOfTreaty))
			return createClosingNoProp(param, DOCUMENT.DOC);
			
		return null;
	}
	
	@Override
	public Object createReportPdf(Map<String, Object> param) throws Exception {
		String process = Param.getStr(param, PROCESS);
		
		if(PLACING.equals(process))
			return createPlacing(param, DOCUMENT.PDF);
		else if(QUOTATION.equals(process))
			return createQuotation(param, DOCUMENT.PDF);
		
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String typeOfTreaty = tR0006JRepo.getTypeOfTreaty(TRE, RQ, trxVoucherId);
		if(CLOSINGSLIP.equals(process) && PRODUCTION_TRE_TYPE.PROPORTIONAL.equals(typeOfTreaty))
			return createClosingProp(param, DOCUMENT.PDF);
		else if(CLOSINGSLIP.equals(process) && PRODUCTION_TRE_TYPE.NON_PROPORTIONAL.equals(typeOfTreaty))
			return createClosingNoProp(param, DOCUMENT.PDF);
			
		return null;
	}
	
	private Object createPlacing(Map<String, Object> param, String type) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String group = Param.getStr(param, GROUP);
		String cob = Param.getStr(param, COB);
		String layer = Param.getStr(param, LAYER);
		
		Map<String, Object> result = new HashMap<String, Object>();
		String appDate1 = mA0014Repo.getAppDateReportProduction1();
		
		String temp[] = appDate1.split(" ");
		String appDate = temp[0].substring(0, temp[0].length()-2)
						+ "<sup>" 
						+ temp[0].substring(temp[0].length()-2, temp[0].length())
						+ "</sup>"
						+ " " + temp[1] + " " + temp[2];

		String typeOfTreaty = tR0006JRepo.getTypeOfTreaty(TRE, RQ, trxVoucherId);
		Boolean isProp = PRODUCTION_TRE_TYPE.PROPORTIONAL.equals(typeOfTreaty);
		
		TR0006Entity client = tR0006Repo.findByTrxClassAndTrxTrxIdAndTrxVoucherId(TRE, RQ, trxVoucherId).get(0);
		TR0006JEntity ii = tR0006JRepo.getPeriod(TRE, RQ, trxVoucherId).get(0);
		List<TR0006KEntity> reins = tR0006KRepo.getReinsuranceTreaty(TRE, RQ, trxVoucherId, cob, layer);
		if(reins.size() == 0)
			reins = tR0006KRepo.getReinsuranceTreaty(TRE, RQ, trxVoucherId);
		
		String mm = trxVoucherId.substring(2, 4);
		String yyyy = trxVoucherId.substring(4, 8);
		String no = trxVoucherId.substring(8, trxVoucherId.length());
		String ref = (isProp ? "TR" : "T-MV") + "/" + yyyy+ "/" + mm + "/" + no;
		
		MA0011Entity ma11 = mA0011Repo.findByTcCode(cob);
		String cover = ma11 == null ? "" : ma11.getTcDesc();
		
		String tot = cover + " " 
				+ (QS.equals(layer) ? "QUOTA SHARE" : "SURPLUS")  + " "
				+ "REINSURANCE TREATY";
		
		Map<String, Object> paramPlacing = new HashMap<String, Object>();
		paramPlacing.put("P_TRX_CLASS", TRE);
		paramPlacing.put("P_TRX_ID", RQ);
		paramPlacing.put("P_VOUCHER_ID", trxVoucherId);
		paramPlacing.put("P_TRX_CLIENT", client.getTrxClient());
		paramPlacing.put("P_TRX_INS_ID", reins.get(0).getTrxInsId());
		paramPlacing.put("P_DATE", appDate);
		paramPlacing.put("TRE_TOT", tot.toUpperCase());
		paramPlacing.put("TRE_REF", ref);
		paramPlacing.put("TRE_GROUP", group);
		paramPlacing.put("TRE_YEAR", yyyy);
		paramPlacing.put("TRE_PERIOD", (ii.getTrxInsStartStr() == null ? "-" : ii.getTrxInsStartStr()) + " to " + (ii.getTrxInsEndStr() == null ? "-" : ii.getTrxInsEndStr()));
		
		if(!isProp) {
			List<String> coverList = tR0006JRepo.getListCoverByGroup(TRE, RQ, trxVoucherId, group);
			
			paramPlacing.put("TRE_COB", String.join(" & ", coverList));
		}
		
		String reportName = null;
		List<String> report = new ArrayList<String>();
		
		String jrxml;
		if(isProp)
			jrxml = "Tre_EOC_Prop.jrxml";
		else
			jrxml = "Tre_EOC_NoProp.jrxml";
		
		if(type.equals(DOCUMENT.PDF)) {
			reportName = reportUtils.exportPdf(jrxml, paramPlacing);
		}else if(type.equals(DOCUMENT.DOC)) {
			reportName = reportUtils.exportDocx(jrxml, paramPlacing);
		}
			
		if(reportName != null)
			report.add(reportName);
		
		result.put("report", report);
		
		return result;
	}
	
	private Object createQuotation(Map<String, Object> param, String type) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String code = Param.getStr(param, CODE);
		if("-".equals(code))
			code = "";
		
		Map<String, Object> result = new HashMap<String, Object>();

		TR0006Entity client = tR0006Repo.findByTrxClassAndTrxTrxIdAndTrxVoucherId(TRE, RQ, trxVoucherId).get(0);
		TR0006JEntity ii = tR0006JRepo.getPeriod(TRE, RQ, trxVoucherId).get(0);
		String yyyy = trxVoucherId.substring(4, 8);
		
		String typeOfTreatyDesc = null;
		String typeOfTreaty = tR0006JRepo.getTypeOfTreaty(TRE, RQ, trxVoucherId);
		if(PRODUCTION_TRE_TYPE.PROPORTIONAL.equals(typeOfTreaty))
			typeOfTreatyDesc = "Proportional";
		else
			typeOfTreatyDesc = "Non Proportional";
		String ref = typeOfTreatyDesc + " Reinsurance Treaty";

		String period = "From " + 
				(ii.getTrxInsStartStr() == null ? "-" : ii.getTrxInsStartStr()) 
				+ " up to " 
				+ (ii.getTrxInsEndStr() == null ? "-" : ii.getTrxInsEndStr());
		
		String sysdate = mA0014Repo.getAppDateReportProduction1();
		
		String temp[] = sysdate.split(" ");
		String dateNow = temp[0].substring(0, temp[0].length()-2)
						+ "<sup>" 
						+ temp[0].substring(temp[0].length()-2, temp[0].length())
						+ "</sup>"
						+ " " + temp[1] + " " + temp[2];
		
		Map<String, Object> paramQuo = new HashMap<String, Object>();
		paramQuo.put("P_TRX_CLASS", TRE);
		paramQuo.put("P_TRX_ID", RQ);
		paramQuo.put("P_VOUCHER_ID", trxVoucherId);
		paramQuo.put("P_TRX_CLIENT", client.getTrxClient());
		paramQuo.put("P_DATE", dateNow);
		paramQuo.put("TRE_YEAR", yyyy);
		paramQuo.put("TRE_REF", ref);
		paramQuo.put("TRE_PERIOD", period);
		paramQuo.put("TRE_CODE", code);
		
		String reportName = null;
		String jrxml = "Tre_CL_NoProp.jrxml";
		List<String> report = new ArrayList<String>();
		if(type.equals(DOCUMENT.PDF))
			reportName = reportUtils.exportPdf(jrxml, paramQuo);
		else if(type.equals(DOCUMENT.DOC))
			reportName = reportUtils.exportDocx(jrxml, paramQuo);
			
		if(reportName != null)
			report.add(reportName);
		
		result.put("report", report);
		
		return result;
	}
	
	private Object createClosingProp(Map<String, Object> param, String type) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String cob = Param.getStr(param, COB);
		String layer = Param.getStr(param, LAYER);
		String code = Param.getStr(param, CODE);
		
		Map<String, Object> result = new HashMap<String, Object>();
		String appDate1 = mA0014Repo.getAppDateReportProduction1();
		
		String temp[] = appDate1.split(" ");
		String appDate = temp[0].substring(0, temp[0].length()-2)
						+ "<sup>" 
						+ temp[0].substring(temp[0].length()-2, temp[0].length())
						+ "</sup>"
						+ " " + temp[1] + " " + temp[2];
		
		TR0006Entity client = tR0006Repo.findByTrxClassAndTrxTrxIdAndTrxVoucherId(TRE, RQ, trxVoucherId).get(0);
		TR0006JEntity ii = tR0006JRepo.getPeriod(TRE, RQ, trxVoucherId).get(0);
		TR0006KEntity reins = tR0006KRepo.getReinsuranceTreaty(TRE, RQ, trxVoucherId, cob, layer).get(0);
		
		String mm = trxVoucherId.substring(2, 4);
		String yyyy = trxVoucherId.substring(4, 8);
		String no = trxVoucherId.substring(8, trxVoucherId.length());
		String ref = "TR" + "/" + yyyy+ "/" + mm + "/" + no;
		
		String cover = mA0011Repo.findByTcCode(cob).getTcDesc();
		
		String tot = cover + " " 
				+ (QS.equals(layer) ? "QUOTA SHARE" : "SURPLUS")  + " "
				+ "REINSURANCE TREATY";
		
		Map<String, Object> paramPlacing = new HashMap<String, Object>();
		paramPlacing.put("P_TRX_CLASS", TRE);
		paramPlacing.put("P_TRX_ID", RQ);
		paramPlacing.put("P_VOUCHER_ID", trxVoucherId);
		paramPlacing.put("P_TRX_CLIENT", client.getTrxClient());
		paramPlacing.put("P_TRX_INS_ID", code);
		paramPlacing.put("P_DATE", appDate);
		paramPlacing.put("TRE_TOT", tot.toUpperCase());
		paramPlacing.put("TRE_REF", ref);
		paramPlacing.put("TRE_BFEE", reins.getTrxRiComm());
		paramPlacing.put("TRE_YEAR", yyyy);
		paramPlacing.put("TRE_PERIOD", (ii.getTrxInsStartStr() == null ? "-" : ii.getTrxInsStartStr()) + " to " + (ii.getTrxInsEndStr() == null ? "-" : ii.getTrxInsEndStr()));
		
		String reportName = null;
		List<String> report = new ArrayList<String>();
		
		String jrxml = "Tre_CS_Prop.jrxml";
		if(type.equals(DOCUMENT.PDF))
			reportName = reportUtils.exportPdf(jrxml, paramPlacing);
		else if(type.equals(DOCUMENT.DOC))
			reportName = reportUtils.exportDocx(jrxml, paramPlacing);
			
		if(reportName != null)
			report.add(reportName);
		
		result.put("report", report);
		
		return result;
	}
	
	private Object createClosingNoProp(Map<String, Object> param, String type) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String group = Param.getStr(param, GROUP);
		String cob = Param.getStr(param, COB);
		String layer = Param.getStr(param, LAYER);
		String code = Param.getStr(param, CODE);
		
		Map<String, Object> result = new HashMap<String, Object>();
		String appDate1 = mA0014Repo.getAppDateReportProduction1();
		
		String temp[] = appDate1.split(" ");
		String appDate = temp[0].substring(0, temp[0].length()-2)
						+ "<sup>" 
						+ temp[0].substring(temp[0].length()-2, temp[0].length())
						+ "</sup>"
						+ " " + temp[1] + " " + temp[2];
		
		TR0006Entity client = tR0006Repo.findByTrxClassAndTrxTrxIdAndTrxVoucherId(TRE, RQ, trxVoucherId).get(0);
		TR0006JEntity ii = tR0006JRepo.getPeriod(TRE, RQ, trxVoucherId).get(0);
		List<TR0006KEntity> reins = tR0006KRepo.getReinsuranceTreaty(TRE, RQ, trxVoucherId, cob, layer);
		if(reins.size() == 0)
			reins = tR0006KRepo.getReinsuranceTreaty(TRE, RQ, trxVoucherId);
		
		String mm = trxVoucherId.substring(2, 4);
		String yyyy = trxVoucherId.substring(4, 8);
		String no = trxVoucherId.substring(8, trxVoucherId.length());
		String ref = "T-MV" + "/" + yyyy+ "/" + mm + "/" + no;
		
		MA0011Entity ma11 = mA0011Repo.findByTcCode(cob);
		String cover = ma11 == null ? "" : ma11.getTcDesc();
		
		String tot = cover + " " 
				+ (QS.equals(layer) ? "QUOTA SHARE" : "SURPLUS")  + " "
				+ "REINSURANCE TREATY";
		
		Map<String, Object> paramClosing = new HashMap<String, Object>();
		paramClosing.put("P_TRX_CLASS", TRE);
		paramClosing.put("P_TRX_ID", RQ);
		paramClosing.put("P_VOUCHER_ID", trxVoucherId);
		paramClosing.put("P_TRX_CLIENT", client.getTrxClient());
		paramClosing.put("P_TRX_INS_ID", code);
		paramClosing.put("P_DATE", appDate);
		paramClosing.put("TRE_TOT", tot.toUpperCase());
		paramClosing.put("TRE_REF", ref);
		paramClosing.put("TRE_GROUP", group);
		paramClosing.put("TRE_YEAR", yyyy);
		paramClosing.put("TRE_PERIOD", (ii.getTrxInsStartStr() == null ? "-" : ii.getTrxInsStartStr()) + " to " + (ii.getTrxInsEndStr() == null ? "-" : ii.getTrxInsEndStr()));
		
		List<TR0006KEntity> reinsList = tR0006KRepo.findReinsByGroupAndTrxInsId(TRE, RQ, trxVoucherId, group, code);
		paramClosing.put("TRE_INS_SHARE", reinsList.get(0).getTrxInsShare());
		paramClosing.put("TRE_INS_BFEE", reinsList.get(0).getTrxRiComm());
		
		List<String> coverList = tR0006JRepo.getListCoverByGroup(TRE, RQ, trxVoucherId, group);
		paramClosing.put("TRE_COB", String.join(" & ", coverList));
		
		String reportName = null;
		List<String> report = new ArrayList<String>();
		
		String jrxml = "Tre_CS_NoProp.jrxml";
		
		if(type.equals(DOCUMENT.PDF))
			reportName = reportUtils.exportPdf(jrxml, paramClosing);
		else if(type.equals(DOCUMENT.DOC))
			reportName = reportUtils.exportDocx(jrxml, paramClosing);
			
		if(reportName != null)
			report.add(reportName);
		
		result.put("report", report);
		
		return result;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	public Object doClosing(TreClosingParam treClosingParam) throws Exception {
		Long start = System.currentTimeMillis();
		String type = treClosingParam.getType();
		String remarks = treClosingParam.getRemarks();
		String trxVoucherId = treClosingParam.getTrxVoucherId();
		Boolean isAdjustment = treClosingParam.getIsAdjustment();
		
		//update status only
		if(PRODUCTION_TRE_TYPE.PROPORTIONAL.equals(type)) {
			tR0006Repo.updateTrxDataStatus("11", remarks, TRE, RQ, trxVoucherId);
			return new ArrayList<Object>();
		}
		
		logger.info("Start - closing treaty with trxVoucherId : '{}'.", trxVoucherId);	
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> clientValueList = treClosingParam.getClientValue();
		List<Map<String, Object>> reinsValueList = treClosingParam.getReinsValue();
		
		param.put(TRX_VOUCHER_ID, trxVoucherId);
		param.put(REMARKS, treClosingParam.getRemarks());
		param.put(CLIENT_CONFIRMATION_DATE, treClosingParam.getClientConfirmationDate());
		param.put(Param.USER, treClosingParam.getUser());
		
		param.put(CLOSING_PARAM.TRE_CLIENT_VALUE, clientValueList);
		param.put(CLOSING_PARAM.TRE_REINS_VALUE, reinsValueList);
		
		List<TR0006EEntity> tChecklistCli = tR0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(TRE, RQ, trxVoucherId, CLIENT_TYPE);
		param.put(CLOSING_PARAM.PAYMENT_METHOD_CLI_LIST, tChecklistCli);
		
		List<TR0006EEntity> tChecklistReins = tR0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(TRE, RQ, trxVoucherId, REINSURANCE_TYPE);
		param.put(CLOSING_PARAM.PAYMENT_METHOD_REINS_LIST, tChecklistReins);
		
		param.put(CLOSING_PARAM.IS_ADJUSTMENT, isAdjustment);
		
		Object result = null;
		synchronized (param) {
			result = closing(param);
		}
		
		logger.info("End - closing treaty with trxVoucherId : '{}', elapsed time : {}ms.", 
				trxVoucherId, System.currentTimeMillis()-start);
		return result;
	}
	
	@Transactional(rollbackOn = Exception.class)
	@SuppressWarnings("unchecked")
	private Object closing(Map<String, Object> param) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);	
		
		Boolean isAdjustment = Param.getBoolean(param, CLOSING_PARAM.IS_ADJUSTMENT);
		String descAdjustment = isAdjustment ? "Adjustment " : "";

		List<Map<String, Object>> tableAll = new ArrayList<Map<String, Object>>();
		Map<String, List<Map<String, Object>>> mapTable = new LinkedHashMap<String, List<Map<String,Object>>>();

		Date appDate = common.getAppDate();
		Calendar calNow = Calendar.getInstance();
		
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(appDate);
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		List<Map<String, Object>> clientValueList = (List<Map<String, Object>>) param.get(CLOSING_PARAM.TRE_CLIENT_VALUE);
		List<TR0006EEntity> tChecklistCli = (List<TR0006EEntity>) param.get(CLOSING_PARAM.PAYMENT_METHOD_CLI_LIST);
		
		//only one client for treaty
		int j = 1;
		TR0006Entity tClient = tR0006Repo.findByTrxClassAndTrxTrxIdAndTrxVoucherId(TRE, RQ, trxVoucherId).get(0);
		TR0006JEntity tInterest = tR0006JRepo.getAdditionalTreaty(TRE, RQ, trxVoucherId).get(0);
		for(Map<String, Object> clientValue : clientValueList) {		
			Date now = calNow.getTime();	
			MA0005Entity client = mA0005Repo.findByCliCode(tClient.getTrxClient());
			
			String groupId = Param.getStr(clientValue, "groupId");
			String group = Param.getStr(clientValue, "group");
			String layer = Param.getStr(clientValue, "layer");
			String descCli = descAdjustment + "Treaty Non Prop – " + year 
					+ " - " + group 
					+ " - " + layer
					+ " - " + trxVoucherId;
			
			String brCodeCli = "CTRX" + tClient.getTrxCurrId();
			List<MA0018Entity> businessRuleCli = mA0018Repo.findByBrCode(brCodeCli);
			
			BigDecimal exchangeRateCli = tClient.getTrxCurrRate();
			BigDecimal portionRateCli = getRate(tClient.getTrxShare());
						
			BigDecimal premiumValue = Param.getBd(clientValue, "deposit").multiply(portionRateCli);
			premiumValue = premiumValue.setScale(2, RoundingMode.HALF_UP);
			
			Map<String, BigDecimal> mapValue = new HashMap<String, BigDecimal>();
			mapValue.put(PROD_MAPKEY.PREMIUM_VALUE, premiumValue);
			
			List<TR0001Entity> tr1Entities = new ArrayList<TR0001Entity>();
			List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
			for(TR0006EEntity payMthdCli : tChecklistCli) {
				String voucherIdCli = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				
				TR0001Entity tr1 = new TR0001Entity();
				tr1.setGlTrxClass("OP");
				tr1.setGlType("SE");
				tr1.setGlVoucherId(voucherIdCli);
				
				tr1.setGlTrxDate(calTrx.getTime());
				tr1.setGlTrxDue(payMthdCli.getTrxPrDate());
				tr1.setGlTrxMonth(month.byteValue());
				tr1.setGlTrxYear(year.shortValue());
				
				tr1.setGlTrxOfficeId("0");
				tr1.setGlTrxProject("0000");
				tr1.setGlTrxClient(tClient.getTrxClient());
				
				BigDecimal shareRatePrCli = getRate(payMthdCli.getTrxPrShare());
				tr1.setPrRate(shareRatePrCli);
				
				BigDecimal premiumValuePr = premiumValue.multiply(shareRatePrCli);
				premiumValuePr = premiumValuePr.setScale(2, RoundingMode.HALF_UP);
				
				tr1.setGlTrxDesc(descCli + " - " + premiumValuePr);
				tr1.setGlTrxStatus("0");
				tr1.setGlDataStatus("11");
				tr1.setCreateOn(now);
				tr1.setCreateBy(param.get(Param.USER).toString());
				tr1.setModifyOn(now);
				tr1.setModifyBy(param.get(Param.USER).toString());
				
				tr1Entities.add(tr1);
				
				BigDecimal valueOrg = BigDecimal.ZERO;
				BigDecimal valueIdr = BigDecimal.ZERO;
				for(MA0018Entity ma0018 : businessRuleCli) {
					TR0002Entity t2 = new TR0002Entity();
					t2.setGlTrxClass(tr1.getGlTrxClass());
					t2.setGlType(tr1.getGlType());
					t2.setGlVoucherId(tr1.getGlVoucherId());
					t2.setGlAccount(ma0018.getBrChildCoa());
					
					MA0015AEntity exchange = mA0015ARepo.findByCoaCodeAndExDate(ma0018.getBrChildCoa(), appDate);
					t2.setGlCurrId(exchange.getExCurrId());
					t2.setGlCurrRate(exchange.getExKmkRateBd());
					
					String code = ma0018.getBrChildValue().trim();
					BigDecimal value = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
					BigDecimal org = value.multiply(shareRatePrCli);
					BigDecimal idr = org.multiply(exchange.getExKmkRateBd());
					
					t2.setGlOrgDebit(BigDecimal.ZERO);
					t2.setGlIdrDebit(BigDecimal.ZERO);
					t2.setGlOrgCredit(BigDecimal.ZERO);
					t2.setGlIdrCredit(BigDecimal.ZERO);
					if(ma0018.getBrChildDc().equals('0')) {
						t2.setGlOrgDebit(org);
						t2.setGlIdrDebit(idr);
					}else if(ma0018.getBrChildDc().equals('1')) {
						t2.setGlOrgCredit(org);
						t2.setGlIdrCredit(idr);
					}
					
					valueOrg = valueOrg.add(t2.getGlOrgDebit());
					valueIdr = valueIdr.add(t2.getGlIdrDebit());
					
					tr2Entities.add(t2);
				}
				
				tr1.setGlTrxValueOrg(valueOrg);
				tr1.setGlTrxValueIdr(valueIdr);
			}
			
			TR0006KEntity tReins = tR0006KRepo.findReinsByGroupAndLayer(TRE, RQ, trxVoucherId, groupId, layer).get(0);
			
			String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
			TR0003Entity tr3 = new TR0003Entity();
			tr3.setTrxType(RQ);
			tr3.setTrxVoucherId(voucherId);
			tr3.setTrxDescription(descCli + " - " + premiumValue);
			tr3.setTrxClient(tClient.getTrxClient());
			tr3.setTrxDate(calTrx.getTime());
			tr3.setTrxAssured(tReins.getTrxInsId());
			tr3.setTrxCoverCode(null);
			tr3.setTrxInsOfficer(tClient.getTrxOfficer());
			tr3.setTrxInsInsured(descCli + " - " + premiumValue); 
			tr3.setTrxInsStart(tInterest.getTrxInsStart());
			tr3.setTrxInsEnd(tInterest.getTrxInsEnd());
			tr3.setTrxCurrId(tClient.getTrxCurrId());
			tr3.setTrxCurrRate(exchangeRateCli);
			tr3.setTrxAmountDue(premiumValue); 
			tr3.setTrxOldVoucherId(trxVoucherId);
			tr3.setTrxStatusData("11");
			tr3.setCreateOn(now);
			tr3.setCreateBy(param.get(Param.USER).toString());
			
			List<Map<String, Object>> table = mapTable.get(tClient.getTrxClient());
			if(table == null)
				table = new ArrayList<Map<String,Object>>();
			
			Map<String, Object> cover = new HashMap<String, Object>();
			cover.put("name", client.getCliName());
			cover.put("document", "Covering Letter");
			
			String fileName = "CL - " + client.getCliName();
			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '-', '-', '-', '-', 'quotation')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '-', '-', '-', '-', 'quotation', '" + fileName + "')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			cover.put("action", action);
			
			if(j == 1 && !isAdjustment)
				table.add(cover);
			
			Map<String, Object> journal = new HashMap<String, Object>();
			journal.put("voucherId", tr3.getTrxVoucherId());
			journal.put("group", group);
			journal.put("layer", layer);
			journal.put("name", client.getCliName());
			journal.put("document", "Closing - DN");
			journal.put("type", "DEBIT NOTE");
			
			String fn = descAdjustment + "DN - CEDANT - " + group + " - " + layer;
			
			action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + group + "', '" + layer + "', '" + client.getCliCode() + "', '" + client.getCliName() + "', 'DEBIT NOTE')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + group + "', '" + layer + "', '" + client.getCliCode() + "', '" + client.getCliName() + "', '" + fn + "', 'DEBIT NOTE')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			journal.put("action", action);
			table.add(journal);
			
			mapTable.put(tClient.getTrxClient(), table);
			
			int noRow = 1;
			List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
			for(TR0006EEntity payMthdCli : tChecklistCli) {
				TR0003AEntity tr3a = new TR0003AEntity();
				tr3a.setTrxType(tr3.getTrxType());
				tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
				tr3a.setTrxNoRow(noRow);
				
				tr3a.setTrxDueDate(payMthdCli.getTrxPrDate());
				tr3a.setTrxRemarks("Instalment "+noRow);
				
				BigDecimal shareRatePrCli = getRate(payMthdCli.getTrxPrShare());
				tr3a.setTrxDueAmount(premiumValue.multiply(shareRatePrCli));
				
				tr3a.setTrxTrxClass("OP");
				
				tr3aEntities.add(tr3a);
				
				noRow++;
			}
			
			int count = 1;
			List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
			for(TR0001Entity tr1 : tr1Entities) {
				TR0012Entity tr12 = new TR0012Entity();
				tr12.setTrxTrxClass(tr1.getGlTrxClass());
				tr12.setTrxType(tr1.getGlType());
				tr12.setTrxVoucherId(tr1.getGlVoucherId());
				tr12.setTrxDate(tr1.getGlTrxDate());
				tr12.setTrxDueDate(tr1.getGlTrxDue());
				
				Long diffInMillies = Math.abs(tr1.getGlTrxDue().getTime() - tr1.getGlTrxDate().getTime());
				Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				tr12.setTrxMethPay(diff.toString());
				
				tr12.setTrxCoverCode(null);
				tr12.setTrxCountInv(count);
				tr12.setTrxDataStatus("11");
				tr12.setTrxClient(tClient.getTrxClient());
				tr12.setTrxDescription(tr1.getGlTrxDesc());
				tr12.setTrxCurrId(tClient.getTrxCurrId());
				tr12.setTrxCurrRate(exchangeRateCli);
				
				tr12.setTrxInvcAmount(premiumValue.multiply(tr1.getPrRate()));
				tr12.setTrxOrgAmount(premiumValue.multiply(tr1.getPrRate()));
				tr12.setTrxDiscAmount(BigDecimal.ZERO);
				tr12.setTrxDeducAmount(BigDecimal.ZERO);
				tr12.setTrxNetTou(BigDecimal.ZERO);
				tr12.setTrxNetTtl(BigDecimal.ZERO);
				
				tr12.setTrxInsOfficer(tClient.getTrxOfficer());
				tr12.setTrxOldType(RQ);
				tr12.setTrxOldVoucherId(trxVoucherId);
				tr12.setCreateOn(now);
				tr12.setCreateBy(param.get(Param.USER).toString());
				tr12.setModifyOn(now);
				tr12.setModifyBy(param.get(Param.USER).toString());
				
				tr12Entities.add(tr12);
			
				count++;
			}
			
			j++;
			
			tR0001Repo.saveAll(tr1Entities);
			tR0002Repo.saveAll(tr2Entities);
			tR0003Repo.save(tr3);
			tR0003ARepo.saveAll(tr3aEntities);
			tR0012Repo.saveAll(tr12Entities);
		}
		
		calNow.add(Calendar.MILLISECOND, 10);

		Map<String, String> mapCS = new HashMap<String, String>();
		List<Map<String, Object>> reinsValueList = (List<Map<String, Object>>) param.get(CLOSING_PARAM.TRE_REINS_VALUE);
		List<TR0006EEntity> tChecklistReins = (List<TR0006EEntity>) param.get(CLOSING_PARAM.PAYMENT_METHOD_REINS_LIST);
		for(Map<String, Object> reinsValue :reinsValueList) {	
			Date now = calNow.getTime();	
			MA0005Entity client = mA0005Repo.findByCliCode(Param.getStr(reinsValue, "reinsuranceId"));
			
			String group = Param.getStr(reinsValue, "group");
			String groupId = Param.getStr(reinsValue, "groupId");
			String layer = Param.getStr(reinsValue, "layer");
			String descReins = descAdjustment + "Treaty Non Prop – " + year 
					+ " - " + group 
					+ " - " + layer
					+ " - " + trxVoucherId;
			
			String brCodeReins = "VTRX" + tClient.getTrxCurrId();
			List<MA0018Entity> businessRuleReins = mA0018Repo.findByBrCode(brCodeReins);

			BigDecimal exchangeRateReins = tClient.getTrxCurrRate();
//			BigDecimal portionRateReins = getRate(Param.getBd(reinsValue, "share"));
			
			BigDecimal premiumValue = Param.getBd(reinsValue, "payToUW");
			premiumValue = premiumValue.setScale(2, RoundingMode.HALF_UP);
			
			BigDecimal brkrFee = Param.getBd(reinsValue, "riCommAmount")
					.multiply(new BigDecimal("11"))
					.divide(new BigDecimal("10"));
			
			MA0014Entity m14Ppn = mA0014Repo.findByPaChildCode("TAXRATEH003");
			BigDecimal ppnRate = getRate(new BigDecimal(m14Ppn.getPaChildValue()));
			BigDecimal taxinBf = brkrFee.multiply(ppnRate);
			
			Map<String, BigDecimal> mapValue = new HashMap<String, BigDecimal>();
			mapValue.put(PROD_MAPKEY.PREMIUM_VALUE, premiumValue);
			mapValue.put(PROD_MAPKEY.BRKR_FEE_VALUE, brkrFee);
			mapValue.put(PROD_MAPKEY.TAXIN_BF_VALUE, taxinBf);
			
			List<TR0001Entity> tr1Entities = new ArrayList<TR0001Entity>();
			List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
			for(TR0006EEntity payMthdReins : tChecklistReins) {
				String voucherIdReins = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				
				TR0001Entity tr1 = new TR0001Entity();
				tr1.setGlTrxClass("OP");
				tr1.setGlType("PU");
				tr1.setGlVoucherId(voucherIdReins);
				
				tr1.setGlTrxDate(calTrx.getTime());
				tr1.setGlTrxDue(payMthdReins.getTrxPrDate());
				tr1.setGlTrxMonth(month.byteValue());
				tr1.setGlTrxYear(year.shortValue());
				
				tr1.setGlTrxOfficeId("0");
				tr1.setGlTrxProject("0000");
				tr1.setGlTrxClient(client.getCliCode());
				
				BigDecimal shareRatePrReins = getRate(payMthdReins.getTrxPrShare());
				tr1.setPrRate(shareRatePrReins);
				
				tr1.setGlTrxDesc(descReins + " - " + premiumValue);
				tr1.setGlTrxStatus("0");
				tr1.setGlDataStatus("11");
				tr1.setCreateOn(now);
				tr1.setCreateBy(param.get(Param.USER).toString());
				tr1.setModifyOn(now);
				tr1.setModifyBy(param.get(Param.USER).toString());
				
				tr1Entities.add(tr1);
				
				BigDecimal valueOrg = BigDecimal.ZERO;
				BigDecimal valueIdr = BigDecimal.ZERO;
				for(MA0018Entity ma0018 : businessRuleReins) {
					TR0002Entity t2 = new TR0002Entity();
					t2.setGlTrxClass(tr1.getGlTrxClass());
					t2.setGlType(tr1.getGlType());
					t2.setGlVoucherId(tr1.getGlVoucherId());
					t2.setGlAccount(ma0018.getBrChildCoa());
					
					MA0015AEntity exchange = mA0015ARepo.findByCoaCodeAndExDate(ma0018.getBrChildCoa(), appDate);
					t2.setGlCurrId(exchange.getExCurrId());
					t2.setGlCurrRate(exchange.getExKmkRateBd());
					
					String code = ma0018.getBrChildValue().trim();
					BigDecimal value = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
					BigDecimal org = value.multiply(shareRatePrReins);
					BigDecimal idr = org.multiply(exchange.getExKmkRateBd());
					
					t2.setGlOrgDebit(BigDecimal.ZERO);
					t2.setGlIdrDebit(BigDecimal.ZERO);
					t2.setGlOrgCredit(BigDecimal.ZERO);
					t2.setGlIdrCredit(BigDecimal.ZERO);
					if(ma0018.getBrChildDc().equals('0')) {
						t2.setGlOrgDebit(org);
						t2.setGlIdrDebit(idr);
					}else if(ma0018.getBrChildDc().equals('1')) {
						t2.setGlOrgCredit(org);
						t2.setGlIdrCredit(idr);
					}
					
					valueOrg = valueOrg.add(t2.getGlOrgDebit());
					valueIdr = valueIdr.add(t2.getGlIdrDebit());
					
					tr2Entities.add(t2);
				}
				
				tr1.setGlTrxValueOrg(valueOrg);
				tr1.setGlTrxValueIdr(valueIdr);
			}
			
			String clientAssured = tClient.getTrxClient();
			String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
			TR0003Entity tr3 = new TR0003Entity();
			tr3.setTrxType(RQ);
			tr3.setTrxVoucherId(voucherId);
			tr3.setTrxDescription(descReins + " - " + premiumValue);
			tr3.setTrxClient(client.getCliCode());
			tr3.setTrxDate(calTrx.getTime());
			tr3.setTrxAssured(clientAssured);
			tr3.setTrxCoverCode(null);
			tr3.setTrxInsOfficer(tClient.getTrxOfficer());
			tr3.setTrxInsInsured(descReins + " - " + premiumValue); 
			tr3.setTrxInsStart(tInterest.getTrxInsStart());
			tr3.setTrxInsEnd(tInterest.getTrxInsEnd());
			tr3.setTrxCurrId(tClient.getTrxCurrId());
			tr3.setTrxCurrRate(exchangeRateReins);
			tr3.setTrxAmountDue(premiumValue); 
			tr3.setTrxOldVoucherId(trxVoucherId); 
			tr3.setTrxStatusData("11");
			tr3.setCreateOn(now);
			tr3.setCreateBy(param.get(Param.USER).toString());
		
			List<Map<String, Object>> table = mapTable.get(client.getCliCode());
			if(table == null)
				table = new ArrayList<Map<String,Object>>();
			
			Map<String, Object> cover = new HashMap<String, Object>();
			cover.put("name", client.getCliName());
			cover.put("document", "Covering Letter");
			
			String fileName = "CL - " + client.getCliName();
			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '-', '-', '-', '" + client.getCliCode() + "', 'quotation')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '-', '-', '-', '" + client.getCliCode() + "', 'quotation', '" + fileName + "')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			cover.put("action", action);
			
			if(table.size() == 0 && !isAdjustment)
				table.add(cover);
			
			Map<String, Object> closingslip = new HashMap<String, Object>();
			closingslip.put("name", client.getCliName());
			closingslip.put("group", group);
			closingslip.put("document", "Closing Slip");
			
			fileName = "CS - " + group + " - " + client.getCliName();
			action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + groupId + "', '-', '-', '" + client.getCliCode() + "', 'closingslip')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + groupId + "', '-', '-', '" + client.getCliCode() + "', 'closingslip', '" + fileName + "')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			closingslip.put("action", action);
			
			String keyCS = client.getCliCode().concat(group);
			if(mapCS.get(keyCS) == null && !isAdjustment) {
				table.add(closingslip);
				mapCS.put(keyCS, keyCS);
			}
			
			Map<String, Object> journal = new HashMap<String, Object>();
			journal.put("voucherId", tr3.getTrxVoucherId());
			journal.put("group", group);
			journal.put("layer", layer);
			journal.put("name", client.getCliName());
			journal.put("document", "Closing - CN");
			journal.put("type", "CREDIT NOTE");
			
			String fn = descAdjustment + "CN - " + client.getCliName() +" - " + group + " - " + layer;
			
			action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + group + "', '" + layer + "', '" + client.getCliCode() + "', '" + client.getCliName() + "', 'CREDIT NOTE')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + group + "', '" + layer + "', '" + client.getCliCode() + "', '" + client.getCliName() + "', '" + fn + "', 'CREDIT NOTE')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			journal.put("action", action);
			table.add(journal);
			
			mapTable.put(client.getCliCode(), table);
			
			int noRow = 1;
			List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
			for(TR0006EEntity payMthdReins : tChecklistReins) {
				TR0003AEntity tr3a = new TR0003AEntity();
				tr3a.setTrxType(tr3.getTrxType());
				tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
				tr3a.setTrxNoRow(noRow);
				
				tr3a.setTrxDueDate(payMthdReins.getTrxPrDate());
				tr3a.setTrxRemarks("Instalment "+noRow);
				
				BigDecimal shareRatePrReins = payMthdReins.getTrxPrShare().divide(BD_100);
				tr3a.setTrxDueAmount(premiumValue.multiply(shareRatePrReins));
				
				tr3a.setTrxTrxClass("OP");
				
				tr3aEntities.add(tr3a);
				
				noRow++;
			}
			
			int count = 1;
			List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
			for(TR0001Entity tr1 : tr1Entities) {
				TR0012Entity tr12 = new TR0012Entity();
				tr12.setTrxTrxClass(tr1.getGlTrxClass());
				tr12.setTrxType(tr1.getGlType());
				tr12.setTrxVoucherId(tr1.getGlVoucherId());
				tr12.setTrxDate(tr1.getGlTrxDate());
				tr12.setTrxDueDate(tr1.getGlTrxDue());
				
				Long diffInMillies = Math.abs(tr1.getGlTrxDue().getTime() - tr1.getGlTrxDate().getTime());
				Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
				tr12.setTrxMethPay(diff.toString());
				
				tr12.setTrxCoverCode(null);
				tr12.setTrxCountInv(count);
				tr12.setTrxDataStatus("11");
				tr12.setTrxClient(client.getCliCode());
				tr12.setTrxDescription(tr1.getGlTrxDesc());
				tr12.setTrxCurrId(tClient.getTrxCurrId());
				tr12.setTrxCurrRate(exchangeRateReins);
				
				tr12.setTrxInvcAmount(premiumValue.multiply(tr1.getPrRate()));
				tr12.setTrxOrgAmount(premiumValue.multiply(tr1.getPrRate()));
				tr12.setTrxBrkrFee(brkrFee.multiply(tr1.getPrRate()));
				tr12.setTrxTaxinBf(taxinBf.multiply(tr1.getPrRate()));
				tr12.setTrxNetTou(BigDecimal.ZERO);
				tr12.setTrxNetTtl(BigDecimal.ZERO);
				
				tr12.setTrxInsOfficer(tClient.getTrxOfficer());
				tr12.setTrxOldType(RQ);
				tr12.setTrxOldVoucherId(trxVoucherId);
				tr12.setCreateOn(now);
				tr12.setCreateBy(param.get(Param.USER).toString());
				tr12.setModifyOn(now);
				tr12.setModifyBy(param.get(Param.USER).toString());
				
				tr12Entities.add(tr12);
			
				count++;
			}
			
			tR0001Repo.saveAll(tr1Entities);
			tR0002Repo.saveAll(tr2Entities);
			tR0003Repo.save(tr3);
			tR0003ARepo.saveAll(tr3aEntities);
			tR0012Repo.saveAll(tr12Entities);
		}
		
		String remarks = Param.getStr(param, REMARKS);
		Date closingDate = Param.getDate(param, CLIENT_CONFIRMATION_DATE);
		
		for(String key : mapTable.keySet()) {
			tableAll.addAll(mapTable.get(key));
		}
		
		if(!isAdjustment) {
			//handle multiple submit
			if(common.isAlreadyClosing(RQ, trxVoucherId))
				throw new Exception(MESSAGE.ALREADY_CLOSING + trxVoucherId);
			
			tR0006Repo.updateTrxDataStatus("11", remarks, closingDate, TRE, RQ, trxVoucherId);
			tableAll.addAll(0, createEOC(trxVoucherId));	
		}
		
		return tableAll;
	}
	
	private List<Map<String, Object>> createEOC(String trxVoucherId) {
		List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
		
		TR0006Entity client = tR0006Repo.getDataClient(TRE, RQ, trxVoucherId).get(0);
		List<TR0006JEntity> interestList = tR0006JRepo.getPlacingNoProp(TRE, RQ, trxVoucherId);
		for(TR0006JEntity interest : interestList) {
			Map<String, Object> eoc = new HashMap<String, Object>();
			eoc.put("name", client.getClientDesc());
			eoc.put("group", interest.getTrxCobGroupDesc());
			eoc.put("document", "EOC");
			
			String fileName = "EOC - " + interest.getTrxCobGroupDesc() + " - " + client.getClientDesc();
			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + interest.getTrxCobGroup() + "', '-', '-', '" + client.getTrxClient() + "', 'placing')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + interest.getTrxCobGroup() + "', '-', '-', '" + client.getTrxClient() + "', 'placing', '" + fileName + "')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			eoc.put("action", action);
			table.add(eoc);
		}
		
		return table;
	}
	
	private BigDecimal getRate(BigDecimal value) {
		return value.divide(BD_100, 4, RoundingMode.HALF_UP);
	}

	@Override
	public Object closingDCNotePdf(Map<String, String> param) throws Exception {
		return createDCNotes(param, "pdf");
	}

	@Override
	public Object closingDCNoteDoc(Map<String, String> param) throws Exception {
		return createDCNotes(param, "doc");
	}
	
	private String createDCNotes(Map<String, String> param, String docType) throws Exception {
		String voucherId = param.get(VOUCHER_ID);			//VoucherId DCNotes
		String trxVoucherId = param.get(TRX_VOUCHER_ID);	//VoucherId Production
		String type = param.get(TYPE);
		String group = param.get("group");
		String layer = param.get("layer");
		String code = param.get("code");
		String addDesc = param.get("addDesc");
		
		String mm = trxVoucherId.substring(2, 4);
		String yyyy = trxVoucherId.substring(4, 8);
		String no = trxVoucherId.substring(8, trxVoucherId.length());
		String ref = "RQ - " + trxVoucherId + " - " 
					+ group + "/" + yyyy + "/" + mm + "/" + no;
		
		MA0014Entity m14 = mA0014Repo.findByParentCodeAndChildDesc("COBGROUP", group);
		List<String> cover = tR0006JRepo.getListCoverByGroup(TRE, RQ, trxVoucherId, m14.getPaChildValue());
		
		BigDecimal share = BigDecimal.ZERO;
		BigDecimal bfee = BigDecimal.ZERO;
		if(type.equals(CREDIT_NOTE)) {
			TR0006KEntity reins = tR0006KRepo.findReinsByGroupAndLayerAndTrxInsId(TRE, RQ, trxVoucherId, m14.getPaChildValue(), layer, code).get(0);
			share = reins.getTrxInsShare();
			bfee = reins.getTrxRiComm();
		}else {
			TR0006Entity client = tR0006Repo.findByTrxClassAndTrxTrxIdAndTrxVoucherId(TRE, RQ, trxVoucherId).get(0);
			share = client.getTrxShare();
		}
				
		Map<String, Object> reportParam = new HashMap<String, Object>();
		reportParam.put("P_VOUCHER_ID", voucherId);
		reportParam.put("P_TYPE", type);
		reportParam.put("P_REF", ref);
		reportParam.put("P_GROUP", group);
		reportParam.put("P_COVER", String.join(", ", cover));
		reportParam.put("P_LAYER", layer);
		reportParam.put("P_SHARE", share);
		reportParam.put("P_BFEE", bfee);
		reportParam.put("P_ADD_DESC", addDesc == null ? "" : addDesc);
		
		if(docType.equals("pdf"))
			return reportUtils.exportPdf("Tre_DCNotes_NoProp.jrxml", reportParam);
		else if(docType.equals("excel"))
			return reportUtils.exportExcel("Tre_DCNotes_NoProp.jrxml", reportParam);
		else if(docType.equals("doc"))
			return reportUtils.exportDocx("Tre_DCNotes_NoProp.jrxml", reportParam);
		else
			return reportUtils.exportHtml("Tre_DCNotes_NoProp.jrxml", reportParam);
	}
	
}
