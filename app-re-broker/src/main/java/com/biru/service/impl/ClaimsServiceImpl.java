package com.biru.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.component.ReportUtils;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0015AEntity;
import com.biru.entity.MA0018Entity;
import com.biru.entity.MA0019Entity;
import com.biru.entity.ResponseEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0003AEntity;
import com.biru.entity.TR0003Entity;
import com.biru.entity.TR0006BEntity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0006HEntity;
import com.biru.entity.TR0006JEntity;
import com.biru.entity.TR0006KEntity;
import com.biru.entity.TR0012Entity;
import com.biru.entity.TR0015AEntity;
import com.biru.entity.TR0015BEntity;
import com.biru.entity.TR0015CEntity;
import com.biru.entity.TR0015DEntity;
import com.biru.entity.TR0015Entity;
import com.biru.entity.ViewInqClaims;
import com.biru.entity.ViewInqClaimsCreate;
import com.biru.helper.InternalMemoHelper;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.MA0019Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0003ARepo;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0006BRepo;
import com.biru.repository.TR0006HRepo;
import com.biru.repository.TR0006JRepo;
import com.biru.repository.TR0006KRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.TR0015ARepo;
import com.biru.repository.TR0015BRepo;
import com.biru.repository.TR0015CRepo;
import com.biru.repository.TR0015DRepo;
import com.biru.repository.TR0015Repo;
import com.biru.repository.ViewInqClaimsCreateRepo;
import com.biru.repository.ViewInqClaimsRepo;
import com.biru.service.ClaimsService;
import com.biru.service.CommonService;

@Service
public class ClaimsServiceImpl extends AbstractCommon implements ClaimsService {
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private VoucherComponent voucherComponent;
		
	@Autowired
	private MA0004Repo ma0004Repo;
	
	@Autowired
	private MA0005Repo ma0005Repo;
	
	@Autowired
	private MA0015ARepo ma0015ARepo;
	
	@Autowired
	private MA0018Repo ma0018Repo;
	
	@Autowired
	private MA0019Repo ma0019Repo;
	
	@Autowired
	private TR0001Repo tr0001Repo;
	
	@Autowired
	private TR0002Repo tr0002Repo;
	
	@Autowired
	private TR0003Repo tr0003Repo;
	
	@Autowired
	private TR0003ARepo tr0003ARepo;
	
	@Autowired
	private TR0006Repo tr0006Repo;
	
	@Autowired
	private TR0006BRepo tr0006BRepo;
	
	@Autowired
	private TR0006HRepo tr0006HRepo;
	
	@Autowired
	private TR0006JRepo tr0006JRepo;
	
	@Autowired
	private TR0006KRepo tr0006KRepo;
	
	@Autowired
	private TR0012Repo tr0012Repo;
	
	@Autowired
	private TR0015Repo tr0015Repo;
	
	@Autowired
	private TR0015ARepo tr0015ARepo;
	
	@Autowired
	private TR0015BRepo tr0015BRepo;
	
	@Autowired
	private TR0015CRepo tr0015CRepo;
	
	@Autowired
	private TR0015DRepo tr0015DRepo;
	
	@Autowired
	private ViewInqClaimsRepo viewInqClaimsRepo;
	
	@Autowired
	private ViewInqClaimsCreateRepo viewInqClaimsCreateRepo;

	private static final String APP_DATE 		= "appDate";
	private static final String CLIENT 			= "client";
	private static final String COB 			= "cob";
	private static final String CREATE_BY		= "createBy";
	private static final String DATE 			= "date";
	private static final String RELATED_ID_KEY 	= "relatedIdKey";
	private static final String TX_DATA_STATUS 	= "txDataStatus";
	private static final String TX_TYPE		 	= "txType";
	private static final String TX_VOUCHER_ID 	= "txVoucherId";
	private static final String TRX_TRX_ID	 	= "trxTrxId";
	private static final String TRX_VOUCHER_ID 	= "trxVoucherId";
	private static final String SOURCE 	= "4";
	
	private static final String CS 				= "CS";
	private static final String TRE 			= "TRE";
	
	private static final Logger logger = LoggerFactory.getLogger(ClaimsServiceImpl.class);
	
	private Pageable getPageable(Map<String, Object> param) {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		
		return PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
	}

	@Override
	public Object inquiryDetail(Map<String, Object> param) {
		String txType = Param.getStr(param, TX_TYPE);
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		
		if(StringUtils.isBlank(txVoucherId) || StringUtils.isBlank(txType))
			return "No data found";
				
		Map<String, Object> data = new HashMap<String, Object>();
		
		TR0015Entity client = tr0015Repo.findClient(txType, txVoucherId);
		data.put("t1Data", client);

		TR0015Entity claim = tr0015Repo.findClaim(txType, txVoucherId);
		data.put("t2Data", claim);
		
		List<TR0015AEntity> value = tr0015ARepo.findValue(txType, txVoucherId);
		data.put("t4Data", value);
		
		List<TR0015BEntity> memo = tr0015BRepo.findByTxVoucherIdAndTrxTrxId(txVoucherId, txType);
		data.put("t5Data", memo);
		
		List<TR0015CEntity> document = tr0015CRepo.findByTrxVoucherIdAndTrxTrxId(txVoucherId, txType);
		for(TR0015CEntity doc : document) {
			List<MA0019Entity> types = ma0019Repo.findByClCode(Integer.valueOf(doc.getTrxDocId()));
			
			if(types.size()>0) {
				String status = "Mandatoy";
				if(types.get(0).getClCode() == Integer.valueOf(0))
					status = "Option";
				doc.setTrxDocIdDesc(types.get(0).getClDesc() + " - " + status);
			}
			 
			String link = "<a href=\"/gui-re-broker/marketing/claims-internal/download-file/" + doc.getTrxDocName() + "\" target=\"_blank\">" + doc.getTrxDocName() + "</a>";
			doc.setTrxDocNameLink(link);
		}
		data.put("t6Data", document);
		
		String typeOfTreaty = tr0006JRepo.getTypeOfTreaty("TRE", client.getTxOldType(), client.getTxOldVoucherId());
		
		data.put("source", typeOfTreaty);
		data.put("createBy", client.getCreateBy());
		data.put("createOn", client.getCreateOn().getTime());
		
		return data;
	}
	
	@Override
	public Object inquiry(Map<String, Object> param) {
		String txDataStatus = Param.getStrWithDef(param, TX_DATA_STATUS);
		String search = Param.getStrWithDef(param, "search");
		String year = Param.getStrWithDef(param, "year");
		
		Pageable pageable = getPageable(param);
		Page<ViewInqClaims> data = viewInqClaimsRepo.getDataClaims(txDataStatus, search, year, pageable);
		
		for(ViewInqClaims view : data.getContent()) {
			String action = "<button class=\"btn btn-secondary\" onclick=\"editClaims('"+view.getIdKey() + "','" + view.getTxType() + "','" + view.getTxVoucherId()+"')\">" 
					+ "<i class=\"fa fa-edit\"></i>" 
					+ "	</button>";
			if(!view.getTxDataStatus().equals("18"))
				action += " &nbsp;<button class=\"btn btn-danger\" onclick=\"removeClaims('" + view.getTxType() + "','" + view.getTxVoucherId()+"')\">" 
					+ "<i class=\"fa fa-trash\"></i>" 
					+ "</button>";
			
			if(view.getTxDataStatus().equals("12") || view.getTxDataStatus().equals("11")) //already delete
				action = "";
			
			view.setAction(action);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}
	
	@Override
	public Object inquiryCreate(Map<String, Object> param) {
		String client = Param.getStrWithDef(param, CLIENT);
		String cob = Param.getStrWithDef(param, COB);
		
		Pageable pageable = getPageable(param);
		Page<ViewInqClaimsCreate> result = viewInqClaimsCreateRepo
				.findByClientAndCob(client, cob, pageable);
		
		return new DatatableSet(result.getTotalElements(), result.getTotalElements(), result.getContent());
	}

	@Override
	public Object inquiryCreateDetail(Map<String, Object> param) {
		String trxTrxId = Param.getStrWithDef(param, TRX_TRX_ID);
		String trxVoucherId = Param.getStrWithDef(param, TRX_VOUCHER_ID);
		String search = Param.getStrWithDef(param, "search");
		
		Pageable pageable = getPageable(param);
		Page<TR0006HEntity> result = tr0006HRepo.getDataClaims(trxTrxId, trxVoucherId, search, pageable);
				
		return new DatatableSet(result.getTotalElements(), result.getTotalElements(), result.getContent());
	}

	@Override
	public Object save(Map<String, Object> param) throws ParseException {
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		
		String txVoucherId = Param.getStrWithDef(param, TX_VOUCHER_ID);
		
		Date appDate = common.getAppDate();
		if(StringUtils.isBlank(txVoucherId))	//create
			txVoucherId = voucherComponent.saveVoucherCounter(formatDate.format(appDate));
		
		Date now = Calendar.getInstance().getTime();
		Date createOn = now;
		
		Long createOnMillis = Param.getLong(param, "createOn");
		if(createOnMillis != null)
			createOn = new Date(createOnMillis);
		
		param.put(TX_VOUCHER_ID, txVoucherId);
		param.put(APP_DATE, appDate);
		param.put(DATE, now);

		saveClientInformationAndClaim(param);
		saveValue(param);
		saveMemo(param);
		saveDocument(param);
		
		String user = Param.getStrWithDef(param, Param.USER);
		String createBy = Param.getStrWithDef(param, CREATE_BY);
		if(StringUtils.isBlank(createBy))
			createBy = user;
		
		Map<String, Object> message = new HashMap<String, Object>();
		message.put("txVoucherId", txVoucherId);
		message.put("createOn", createOn.getTime());
		message.put("createBy", createBy);

		response.setResult(txVoucherId);
		response.setMessage(message);
		
		return response;
	}
	
	@SuppressWarnings("unchecked")
	private void saveClientInformationAndClaim(Map<String, Object> param) throws ParseException {
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);

		Date now = (Date) param.get(DATE);
		
		Map<String, Object> t1Data = (HashMap<String, Object>) param.get("t1Data");
		Map<String, Object> t2Data = (HashMap<String, Object>) param.get("t2Data");
		
		Long relatedIdKey = Param.getLong(t1Data, RELATED_ID_KEY);
		TR0015Entity tR0015Entity = tr0015Repo.findByTxTypeAndTxVoucherId(CS, txVoucherId);
		
		if(tR0015Entity == null)
			tR0015Entity = new TR0015Entity();
		tR0015Entity.setTxClientId(Param.getStr(t1Data, "txClientId"));
		tR0015Entity.setTxPolicyNo(Param.getStr(t1Data, "txPolicyNo"));
		tR0015Entity.setTxCoverId(Param.getStr(t1Data, "txCoverId"));
		tR0015Entity.setTxPartyName(Param.getStr(t1Data, "txPartyName"));
		tR0015Entity.setTxBrdxId(Param.getStr(t1Data, "txBrdxId"));
		tR0015Entity.setTxDateFrom(Param.getDate(t1Data, "txDateFrom", formatDateMon2));
		tR0015Entity.setTxDateTo(Param.getDate(t1Data, "txDateTo", formatDateMon2));
		tR0015Entity.setTxInterest(Param.getStr(t1Data, "txInterest"));
		tR0015Entity.setTxCurrId(Param.getStr(t1Data, "txCurrId"));
		tR0015Entity.setTxSumIns(Param.getBd(t1Data, "txTsiAmount"));
		tR0015Entity.setTxDataStatus(Param.getStr(t1Data, "txDataStatus"));
		tR0015Entity.setTxAttClient(Param.getStr(t1Data, "txAttClient"));
		tR0015Entity.setTxOldType(Param.getStr(param, "txOldType"));
		tR0015Entity.setTxOldVoucherId(Param.getStr(param, "txOldVoucherId"));
		tR0015Entity.setTxType(CS);
		tR0015Entity.setTxVoucherId(txVoucherId);
		tR0015Entity.setTxTrxSrc("3");
		tR0015Entity.setRelatedIdKey(relatedIdKey);
		tR0015Entity.setTxMethod("1");
		
		tR0015Entity.setTxLostDate(Param.getDate(t2Data, "txLostDate"));
		tR0015Entity.setTxAdviceDate(Param.getDate(t2Data, "txAdviceDate"));
		tR0015Entity.setTxUwReff(Param.getStr(t2Data, "txUwReff"));
		tR0015Entity.setTxMaxPaid(Param.getIntWithDef(t2Data, "txMaxPaid"));
		tR0015Entity.setTxCause(Param.getStr(t2Data, "txCause"));
		tR0015Entity.setTxDetails(Param.getStr(t2Data, "txDetails"));
		
		tR0015Entity.setTxOrAmt(Param.getBdWithDef(t1Data, "t4or"));
		tR0015Entity.setTxQsAmt(Param.getBdWithDef(t1Data, "t4qs"));
		tR0015Entity.setTxUrAmt(Param.getBdWithDef(t1Data, "t4ur"));
		tR0015Entity.setTxReinsNo(Param.getBdWithDef(t1Data, "t4reinsNo"));
		tR0015Entity.setTxReinsCl(Param.getBdWithDef(t1Data, "t4reinsPremiC"));
		tR0015Entity.setTxReinsUw(Param.getBdWithDef(t1Data, "t4reinsPremiR"));
		tR0015Entity.setTxClclAmt(Param.getBdWithDef(t1Data, "t4claimsC"));
		tR0015Entity.setTxUwclAmt(Param.getBdWithDef(t1Data, "t4claimsR"));
		
		String user = Param.getStrWithDef(param, Param.USER);
		String createBy = Param.getStrWithDef(param, CREATE_BY);
		Date createOn = now;
		
		if(StringUtils.isNotBlank(createBy)) {	
			createOn = new Date(Param.getLong(param, "createOn"));
			
			tR0015Entity.setModifyBy(user);
			tR0015Entity.setModifyOn(now);
		}else {
			createBy = user;
		}
		
		tR0015Entity.setCreateBy(createBy);
		tR0015Entity.setCreateOn(createOn);
		
		tr0015Repo.save(tR0015Entity);
		
//		TR0006HEntity tr6h = tr0006HRepo.findById(relatedIdKey.toString()).get();
//		tr6h.setTrxClaimStatus(tR0015Entity.getTxDataStatus());
//		
//		tr0006HRepo.save(tr6h);
	}
	
	@SuppressWarnings("unchecked")
	private void saveValue(Map<String, Object> param) {
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		
		Map<String, Object> t1Data = (HashMap<String, Object>) param.get("t1Data");
		Map<String, Object> t4Data = (HashMap<String, Object>) param.get("t4Data");
		List<HashMap<String, Object>> t4DataTable = (ArrayList<HashMap<String, Object>>) t4Data.get("t4DataTable");
		
		List<Long> listIdUpdate = new ArrayList<Long>(); listIdUpdate.add(-99l);
		List<TR0015AEntity> datas = new ArrayList<TR0015AEntity>();
		for(HashMap<String, Object> data : t4DataTable) {
			Optional<TR0015AEntity> tR0015A = tr0015ARepo.findById(Param.getLongWithDef(data, "idKey"));
			
			TR0015AEntity tR0015AEntity = new TR0015AEntity();
			if(tR0015A.isPresent()) {
				tR0015AEntity = tR0015A.get();
				listIdUpdate.add(tR0015AEntity.getIdKey());
			}
				
			tR0015AEntity.setTxType(CS);
			tR0015AEntity.setTxVoucherId(txVoucherId);
			tR0015AEntity.setTxCobGroup(Param.getStr(data, "groupId"));
			tR0015AEntity.setTxCoverId(Param.getStr(data, "cobId"));
			tR0015AEntity.setTxLayer(Param.getStr(data, "layer"));
			tR0015AEntity.setTxClaimType(Param.getStr(data, "typeId"));
			tR0015AEntity.setTxClaimDla(Param.getBd(data, "dla"));
			tR0015AEntity.setTxClaimPla(Param.getBd(data, "pla"));
			tR0015AEntity.setTxPaidDate(Param.getDate(data, "date"));
			tR0015AEntity.setTxDataStatus(Param.getStr(data, "statusId"));
			tR0015AEntity.setTxClaimRemarks(Param.getStr(data, "remarks"));
			tR0015AEntity.setTxMemo(Param.getStr(t4Data, "t4remarks2"));
			tR0015AEntity.setTxRowNo(Param.getInt(data, "no"));
			tR0015AEntity.setTxCurrId(Param.getStr(t1Data, "txCurrId"));
			
			datas.add(tR0015AEntity);
		}
		
		tr0015ARepo.deleteByTxTypeAndTxVoucherIdAndIdKeyNotIn(CS, txVoucherId, listIdUpdate);
		if(datas.size()>0)
			tr0015ARepo.saveAll(datas);
		
	}
	
	@SuppressWarnings("unchecked")
	private void saveMemo(Map<String, Object> param) {
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		
		Map<String, Object> t5Data = (HashMap<String, Object>) param.get("t5Data");
		List<HashMap<String, Object>> t5DataTable = (ArrayList<HashMap<String, Object>>) t5Data.get("t5DataTable");
		
		tr0015BRepo.deleteByTxVoucherIdAndTrxTrxId(txVoucherId, CS);
		
		List<TR0015BEntity> datas = new ArrayList<TR0015BEntity>();
		for(HashMap<String, Object> data : t5DataTable) {
			TR0015BEntity tR0015BEntity = new TR0015BEntity();
			
			tR0015BEntity.setTrxTrxId(CS);
			tR0015BEntity.setTxActTake(Param.getStr(data, "txActTake"));
			tR0015BEntity.setTxDateAct(Param.getDate(data, "txDateAct"));
			tR0015BEntity.setTxInsMemo(Param.getStr(data, "txInsMem"));
			tR0015BEntity.setTxVoucherId(txVoucherId);
			
			datas.add(tR0015BEntity);
		}
		
		Boolean isCancel = Param.getBoolean(t5Data, "isCancel");
		Boolean isReject = Param.getBoolean(t5Data, "isReject");
		Boolean isPaid = Param.getBoolean(t5Data, "isPaid");
				
		String status = "16";
		if(isCancel)
			status = "8";
		else if(isReject)
			status = "18";
		else if(isPaid)
			status = "17";
		
		tr0015Repo.updateStatus(txVoucherId, status);
		tr0015BRepo.saveAll(datas);
	}
	
	@SuppressWarnings("unchecked")
	private void saveDocument(Map<String, Object> param) {
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		
		Map<String, Object> t6Data = (HashMap<String, Object>) param.get("t6Data");
		List<HashMap<String, Object>> t6DataTable = (ArrayList<HashMap<String, Object>>) t6Data.get("t6DataTable");
		
		tr0015CRepo.deleteByTxVoucherIdAndTrxTrxId(txVoucherId, CS);
		
		List<TR0015CEntity> datas = new ArrayList<TR0015CEntity>();
		for(HashMap<String, Object> data : t6DataTable) {
			TR0015CEntity tR0015CEntity = new TR0015CEntity();
			
			tR0015CEntity.setTrxTrxId(CS);
			tR0015CEntity.setTrxDocFolder(Param.getStr(data, "txDocFolder"));
			tR0015CEntity.setTrxDocId(Param.getStr(data, "txDocId"));
			tR0015CEntity.setTrxDocName(Param.getStr(data, "txDocName"));
			tR0015CEntity.setTrxVoucherId(txVoucherId);
			
			datas.add(tR0015CEntity);
		}
		
		if(datas.size() > 0)
			tr0015CRepo.saveAll(datas);
	}

	@Override
	public Object getUnderwriting(Map<String, Object> param) {
		String trxTrxId = Param.getStr(param, TRX_TRX_ID);
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		List<TR0006Entity> tr6 = tr0006Repo.findByTrxTrxIdAndTrxVoucherId(trxTrxId, trxVoucherId);
		
		List<TR0006BEntity> uw1Fac = tr0006BRepo.getDataReinsurance(trxTrxId, trxVoucherId);
		List<TR0006KEntity> uw1Tre = tr0006KRepo.getReinsuranceClaims(trxTrxId, trxVoucherId);
		
		List<TR0012Entity> uw2 = tr0012Repo.findUnderwriting(trxTrxId, trxVoucherId);
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("uw1", uw1Fac);
		if(tr6.get(0).getTrxClass().equals("TRE"))
			result.put("uw1", uw1Tre);
		
		result.put("uw2", uw2);
		
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object settlement(Map<String, Object> param) throws ParseException {
		long start = System.currentTimeMillis();
		logger.info("Start - settlement() for claims with param : {}.", param);
		
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		Date now = Calendar.getInstance().getTime();

		TR0015Entity tr15 = tr0015Repo.findByTxTypeAndTxVoucherId(CS, txVoucherId);
		String oldVoucherId = tr15.getTxOldVoucherId();
		String oldType = tr15.getTxOldType();
		TR0006Entity tr0006 = tr0006Repo.findByTrxTrxIdAndTrxVoucherId(oldType, oldVoucherId).get(0);
		
		List<Map<String, Object>> t4Settlement = (ArrayList<Map<String, Object>>) param.get("t4Settlement");
		
		// proses A - update status 17 data settlement
		List<TR0015AEntity> tr15ASettlement = new ArrayList<TR0015AEntity>();
		for(Map<String, Object> data : t4Settlement) {
			Long idKey = Param.getLong(data, "idKey");
			TR0015AEntity tr15a = tr0015ARepo.findById(idKey).get();
			tr15a.setTxDataStatus("17");
			
			tr15ASettlement.add(tr15a);
		}
		
		if(tr15ASettlement.size() > 0)
			tr0015ARepo.saveAll(tr15ASettlement);
		tr15ASettlement.sort((o1, o2) -> o1.getTxCobGroup().compareTo(o2.getTxCobGroup())); // sort asc by group
		
		// step 1 - get limit berdasarkan group & layer
		List<TR0006JEntity> tr6JEntities = tr0006JRepo.findByTrxVoucherId(oldVoucherId);
		
		Map<String, Object> mapLimit = new HashMap<String, Object>();
		for(TR0006JEntity tr6j : tr6JEntities) {
			String idLimit = tr6j.getTrxCobGroup() + tr6j.getTrxLayer();
			if(tr6j.getTrxLimitAmt().signum() > 0)
				mapLimit.put(idLimit, tr6j.getTrxLimitAmt());
		}
		
		// step 2 - create dla per layer
		Map<Integer, String> mapLayer = getLayer();
		Map<String, Object> mapDla = new HashMap<String, Object>();
		for(TR0015AEntity tr15a : tr15ASettlement) {
			if(tr15a.getTxClaimType().equals("0")) {						//type 'Claims Amount'	
				String layer = mapLayer.get(1);								//1st Layer
				BigDecimal dlaValue = tr0006JRepo.getDlaValue(TRE, oldType, oldVoucherId, 	//dla value for 1st Layer
						tr15a.getTxCobGroup(), tr15a.getTxCoverId(), layer);
				
				//get limit for 1st Layer
				String idLimit = tr15a.getTxCobGroup() + layer;
				BigDecimal limit = Param.getBdWithDef(mapLimit, idLimit);
				
				//set dla1
				BigDecimal dla = limit.subtract(dlaValue);
				String idDla = tr15a.getTxCobGroup() + tr15a.getTxCoverId() + layer;
				mapDla.put(idDla, dla);
				
				for(int i=2; i<=5; i++) { 									//get dla every layer
					layer = mapLayer.get(i);								//layer 2 to 5
					idLimit = tr15a.getTxCobGroup() + layer;
					limit = Param.getBdWithDef(mapLimit, idLimit);			//limit 2 to 5
					
					BigDecimal tempDla = tr15a.getTxClaimDla();	//DLA ENTRY - DLA n-1
					for(int j=1;j<i;j++) {
						String subLayer = mapLayer.get(j);	
						String subIdDla = tr15a.getTxCobGroup() + tr15a.getTxCoverId() + subLayer;
						BigDecimal subDla = Param.getBdWithDef(mapDla, subIdDla);
						tempDla = tempDla.subtract(subDla);
					}
					
					if(tempDla.compareTo(limit) >= 0)
						dla = limit;
					else
						dla = tempDla;
									
					idDla = tr15a.getTxCobGroup() + tr15a.getTxCoverId() + layer;
					mapDla.put(idDla, dla);
				}
			}
		}
		
		logger.info("mapLayer : {}.", mapLayer);
		logger.info("mapLimit : {}.", mapLimit);
		logger.info("mapDla : {}.", mapDla);
		
		//step 3 - update dla = old dla + new dla
		for(TR0015AEntity tr15a : tr15ASettlement) {
			if(tr15a.getTxClaimType().equals("0")) {
				for(int i=1; i<=5; i++) {
					String layer = mapLayer.get(i);
					String idDla = tr15a.getTxCobGroup() + tr15a.getTxCoverId() + layer;
					BigDecimal dla = Param.getBdWithDef(mapDla, idDla);
					
					int sizeUpdate = tr0006JRepo.updateDla(dla, TRE, oldType, oldVoucherId, 
							tr15a.getTxCobGroup(), tr15a.getTxCoverId(), layer);
					if(sizeUpdate > 0)
						logger.info("Update dla for {}-{}-{} : {}.", tr15a.getTxCobGroup(), 
								tr15a.getTxCoverId(), layer, dla);
				}
			}
		}
		
		//step 4 - create dc notes
		List<TR0006JEntity> tr6JUpdate = new ArrayList<TR0006JEntity>();
		for(TR0015AEntity tr15a : tr15ASettlement) {
			if(tr15a.getTxClaimType().equals("0")) {
				for(int i=1; i<=5; i++) {
					String layer = mapLayer.get(i);
					BigDecimal sumTrxReInst = tr0006JRepo.sumTrxReInst(TRE, oldType, oldVoucherId, 
							tr15a.getTxCobGroup(), layer);
					
					List<TR0006JEntity> tr6jEntities = tr0006JRepo.getDataUpdatedDla(TRE, oldType, oldVoucherId, 
							tr15a.getTxCobGroup(), tr15a.getTxCoverId(), layer);
					
					if(tr6jEntities.size() > 0) {
						TR0006JEntity tr6j = tr6jEntities.get(0);
						
						BigDecimal trxReinsNo = tr6j.getTrxReinsNo();
						if(tr6j.getTrxDlaValue().compareTo(tr6j.getTrxLimitAmt()) > 0) {
							trxReinsNo = trxReinsNo.add(BigDecimal.ONE);
							
							if(trxReinsNo.compareTo(sumTrxReInst) > 0) {
								//create dc notes
								Map<String, Object> paramDCNotes = new HashMap<String, Object>();
								paramDCNotes.put(TRX_VOUCHER_ID, oldVoucherId);
								
//								createDCNotes(paramDCNotes);
								
								tr6j.setTrxDlaValue(BigDecimal.ZERO);
								tr6j.setTrxReinsNo(BigDecimal.ZERO);
							}
							
							tr6JUpdate.add(tr6j);
						}
					}
				}
			}
		}
		
		if(tr6JUpdate.size() > 0)
			tr0006JRepo.saveAll(tr6JUpdate);
		
		// step 5
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(formatDateId.parse(Param.getStr(param, "date")));
		
		MA0015AEntity ma0015Bank = ma0015ARepo.findByCoaCodeAndExDate(Param.getStr(param, "bank"), calTrx.getTime());
		
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		String voucherIdBankBookReas = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
	
		String reffIn = Param.getStr(param, "reffIdIn");
		String typeReins = "RC";
		TR0001Entity tr1Reas = new TR0001Entity();
		tr1Reas.setGlTrxStatus("0");
		tr1Reas.setGlDataStatus("11");
		tr1Reas.setCreateOn(now);
		tr1Reas.setCreateBy(param.get(Param.USER).toString());
			
		tr1Reas.setGlTrxClass("OP");
		tr1Reas.setGlType(typeReins);
		tr1Reas.setGlVoucherId(voucherIdBankBookReas);
		
		tr1Reas.setGlTrxDate(calTrx.getTime());
		tr1Reas.setGlTrxMonth(month.byteValue());
		tr1Reas.setGlTrxYear(year.shortValue());
		
		tr1Reas.setGlTrxOfficeId("0");
		tr1Reas.setGlTrxProject("00000");
		
		//get one group entry
		String txCobGroup = tr15ASettlement.get(0).getTxCobGroup();
		TR0006KEntity tr006k = tr0006KRepo.findReinsByGroup("TRE", tr0006.getTrxTrxId(), 
				tr0006.getTrxVoucherId(), txCobGroup).get(0);
		MA0005Entity m5Reas = ma0005Repo.findByCliCode(tr006k.getTrxInsId());
		
		tr1Reas.setGlTrxClient(m5Reas.getCliCode());
		tr1Reas.setGlTrxDesc("Claim - " + tr0006.getTrxRemarks());
		tr1Reas.setGlTrxInvoice(m5Reas.getCliName());
		tr1Reas.setGlReffId(reffIn);
		tr1Reas.setGlTrxDue(tr1Reas.getGlTrxDate());
		tr1Reas.setModifyOn(now);
		tr1Reas.setModifyBy(param.get(Param.USER).toString());
		
		List<TR0002Entity> t2Entities = new ArrayList<TR0002Entity>();
		for(TR0015AEntity tr15a : tr15ASettlement) {
			TR0002Entity t2 = new TR0002Entity();
			t2.setGlType(typeReins);
			t2.setGlVoucherId(voucherIdBankBookReas);
			
			String brCode = "CVTRX" + tr15a.getTxCurrId();
			MA0018Entity ma18 = ma0018Repo.findByBrCode(brCode).get(0);
			MA0015AEntity exchange = ma0015ARepo.findByCoaCodeAndExDate(ma18.getBrChildCoa(), calTrx.getTime());
			
			t2.setGlAccount(ma18.getBrChildCoa());
			t2.setGlCurrId(exchange.getExCurrId());
			
			t2.setGlOrgDebit(BigDecimal.ZERO);
			t2.setGlIdrDebit(BigDecimal.ZERO);
			t2.setGlOrgCredit(BigDecimal.ZERO);
			t2.setGlIdrCredit(BigDecimal.ZERO);
			
			BigDecimal org = tr15a.getTxClaimDla();
			BigDecimal idr = org.multiply(exchange.getExKmkRate());
			
			if((typeReins.equals("PY") && idr.signum() >= 0) || (typeReins.equals("RC") && idr.signum() == -1)) {
				t2.setGlOrgDebit(org.abs());
				t2.setGlIdrDebit(idr.abs());
			}else {
				t2.setGlOrgCredit(org.abs());
				t2.setGlIdrCredit(idr.abs());
			}
			
			t2.setGlTrxClass("OP");
			t2.setGlCurrRate(exchange.getExKmkRate());
			t2.setGlDescription(tr15a.getTxClaimType());
			
			t2Entities.add(t2);
		}
		
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalAmountIdr = BigDecimal.ZERO;
		for(TR0002Entity t2 : t2Entities) {
			totalAmount = totalAmount.add(t2.getGlOrgCredit());
			totalAmountIdr = totalAmountIdr.add(t2.getGlIdrCredit());
		}
		tr1Reas.setGlTrxValueOrg(totalAmount);
		tr1Reas.setGlTrxValueIdr(totalAmountIdr);
		
		//BANK
		TR0002Entity t2Bank1 = new TR0002Entity();
		t2Bank1.setGlType(typeReins);
		t2Bank1.setGlVoucherId(voucherIdBankBookReas);
		t2Bank1.setGlAccount(Param.getStr(param, "bank"));
		t2Bank1.setGlCurrId(ma0015Bank.getExCurrId());
		
		t2Bank1.setGlOrgDebit(BigDecimal.ZERO);
		t2Bank1.setGlIdrDebit(BigDecimal.ZERO);
		t2Bank1.setGlOrgCredit(BigDecimal.ZERO);
		t2Bank1.setGlIdrCredit(BigDecimal.ZERO);
		
		BigDecimal amountOrg = totalAmountIdr.divide(ma0015Bank.getExKmkRateBd(), 2, RoundingMode.HALF_UP);
		if((typeReins.equals("RC") && totalAmountIdr.signum() >= 0) || (typeReins.equals("PY") && totalAmountIdr.signum() == -1)) {
			t2Bank1.setGlOrgDebit(amountOrg.abs());
			t2Bank1.setGlIdrDebit(totalAmountIdr.abs());
		}else {
			t2Bank1.setGlOrgCredit(amountOrg.abs());
			t2Bank1.setGlIdrCredit(totalAmountIdr.abs());
		}
		
		t2Bank1.setGlTrxClass("OP");		
		t2Bank1.setGlCurrRate(ma0015Bank.getExKmkRateBd());
		
		t2Entities.add(t2Bank1);
		
		// step 6
		List<TR0015AEntity> listValue = tr0015ARepo.findByTxTypeAndTxVoucherIdAndTxDataStatusNot(
				CS, txVoucherId, "17");
		String statusClaims = "17";
		if(listValue.size() == 0)
			statusClaims = "20";
		
		tr0015Repo.updateStatus(txVoucherId, statusClaims);
		
		logger.info("Finish - settlement(), elapsed time : {}.", System.currentTimeMillis() - start);
		
		return null;
	}
	
	private Map<Integer, String> getLayer() {
		Map<Integer, String> mapLayer = new LinkedHashMap<Integer, String>();
		mapLayer.put(1, "1st Layer");
		mapLayer.put(2, "2nd Layer");
		mapLayer.put(3, "3rd Layer");
		mapLayer.put(4, "4th Layer");
		mapLayer.put(5, "5th Layer");
		
		return mapLayer;
	}

	@Override
	public Object delete(Map<String, Object> param) {
		logger.info("Start - delete() for claims with param : {}.", param);
		
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		String txType = Param.getStr(param, TX_TYPE);
		String user = Param.getStr(param, Param.USER);
		
		TR0015Entity tr15 = tr0015Repo.findByTxTypeAndTxVoucherId(txType, txVoucherId);
		String status = tr15.getTxDataStatus();
		
		tr0006HRepo.updateTrxDataStatusByTrxVoucherIdAndTrxTrxId(tr15.getTxOldVoucherId(), tr15.getTxOldType(), "11");
		
		if(status.equals("17"))
			tr15.setTxDataStatus("11"); // + reverse DC Notes
		else
			tr15.setTxDataStatus("12");
		
		tr15.setModifyBy(user);
		tr15.setModifyOn(Calendar.getInstance().getTime());
		tr0015Repo.save(tr15);
		
		logger.info("Finish - delete() for claims with param : {}.", param);
			
		return "Data successfully deleted";
	}
	
//	@SuppressWarnings("unchecked")
//	public Object createDCNotes(Map<String, Object> param) {
//		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);	
//
//		List<Map<String, Object>> tableAll = new ArrayList<Map<String, Object>>();
//		Map<String, List<Map<String, Object>>> mapTable = new LinkedHashMap<String, List<Map<String,Object>>>();
//
//		Date appDate = common.getAppDate();
//		Date now = Calendar.getInstance().getTime();
//		
//		Calendar calTrx = Calendar.getInstance();
//		calTrx.setTime(appDate);
//		Integer month = calTrx.get(Calendar.MONTH)+1;
//		Integer year = calTrx.get(Calendar.YEAR);
//		
//		List<Map<String, Object>> clientValueList = (List<Map<String, Object>>) param.get(CLOSING_PARAM.TRE_CLIENT_VALUE);
//		List<TR0006EEntity> tChecklistCli = (List<TR0006EEntity>) param.get(CLOSING_PARAM.PAYMENT_METHOD_CLI_LIST);
//		
//		//only one client for treaty
//		int j = 1;
//		TR0006Entity tClient = tR0006Repo.findByTrxClassAndTrxTrxIdAndTrxVoucherId(TRE, RQ, trxVoucherId).get(0);
//		TR0006JEntity tInterest = tR0006JRepo.getAdditionalTreaty(TRE, RQ, trxVoucherId).get(0);
//		for(Map<String, Object> clientValue : clientValueList) {			
//			MA0005Entity client = mA0005Repo.findByCliCode(tClient.getTrxClient());
//			
//			String groupId = Param.getStr(clientValue, "groupId");
//			String group = Param.getStr(clientValue, "group");
//			String layer = Param.getStr(clientValue, "layer");
//			String descCli = descAdjustment + "Treaty Non Prop – " + year 
//					+ " - " + group 
//					+ " - " + layer
//					+ " - " + trxVoucherId;
//			
//			String brCodeCli = "CTRX" + tClient.getTrxCurrId();
//			List<MA0018Entity> businessRuleCli = mA0018Repo.findByBrCode(brCodeCli);
//			
//			BigDecimal exchangeRateCli = tClient.getTrxCurrRate();
//			BigDecimal portionRateCli = getRate(tClient.getTrxShare());
//						
//			BigDecimal premiumValue = Param.getBd(clientValue, "deposit").multiply(portionRateCli);
//			premiumValue = premiumValue.setScale(2, RoundingMode.HALF_UP);
//			
//			Map<String, BigDecimal> mapValue = new HashMap<String, BigDecimal>();
//			mapValue.put(PROD_MAPKEY.PREMIUM_VALUE, premiumValue);
//			
//			List<TR0001Entity> tr1Entities = new ArrayList<TR0001Entity>();
//			List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
//			for(TR0006EEntity payMthdCli : tChecklistCli) {
//				String voucherIdCli = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
//				
//				TR0001Entity tr1 = new TR0001Entity();
//				tr1.setGlTrxClass("OP");
//				tr1.setGlType("SE");
//				tr1.setGlVoucherId(voucherIdCli);
//				
//				tr1.setGlTrxDate(calTrx.getTime());
//				tr1.setGlTrxDue(payMthdCli.getTrxPrDate());
//				tr1.setGlTrxMonth(month.byteValue());
//				tr1.setGlTrxYear(year.shortValue());
//				
//				tr1.setGlTrxOfficeId("0");
//				tr1.setGlTrxProject("0000");
//				tr1.setGlTrxClient(tClient.getTrxClient());
//				
//				BigDecimal shareRatePrCli = getRate(payMthdCli.getTrxPrShare());
//				tr1.setPrRate(shareRatePrCli);
//				
//				BigDecimal premiumValuePr = premiumValue.multiply(shareRatePrCli);
//				premiumValuePr = premiumValuePr.setScale(2, RoundingMode.HALF_UP);
//				
//				tr1.setGlTrxDesc(descCli + " - " + premiumValuePr);
//				tr1.setGlTrxStatus("0");
//				tr1.setGlDataStatus("11");
//				tr1.setCreateOn(now);
//				tr1.setCreateBy(param.get(Param.USER).toString());
//				tr1.setModifyOn(now);
//				tr1.setModifyBy(param.get(Param.USER).toString());
//				
//				tr1Entities.add(tr1);
//				
//				BigDecimal valueOrg = BigDecimal.ZERO;
//				BigDecimal valueIdr = BigDecimal.ZERO;
//				for(MA0018Entity ma0018 : businessRuleCli) {
//					TR0002Entity t2 = new TR0002Entity();
//					t2.setGlTrxClass(tr1.getGlTrxClass());
//					t2.setGlType(tr1.getGlType());
//					t2.setGlVoucherId(tr1.getGlVoucherId());
//					t2.setGlAccount(ma0018.getBrChildCoa());
//					
//					MA0015AEntity exchange = mA0015ARepo.findByCoaCodeAndExDate(ma0018.getBrChildCoa(), appDate);
//					t2.setGlCurrId(exchange.getExCurrId());
//					t2.setGlCurrRate(exchange.getExKmkRateBd());
//					
//					String code = ma0018.getBrChildValue().trim();
//					BigDecimal value = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
//					BigDecimal org = value.multiply(shareRatePrCli);
//					BigDecimal idr = org.multiply(exchange.getExKmkRateBd());
//					
//					t2.setGlOrgDebit(BigDecimal.ZERO);
//					t2.setGlIdrDebit(BigDecimal.ZERO);
//					t2.setGlOrgCredit(BigDecimal.ZERO);
//					t2.setGlIdrCredit(BigDecimal.ZERO);
//					if(ma0018.getBrChildDc().equals('0')) {
//						t2.setGlOrgDebit(org);
//						t2.setGlIdrDebit(idr);
//					}else if(ma0018.getBrChildDc().equals('1')) {
//						t2.setGlOrgCredit(org);
//						t2.setGlIdrCredit(idr);
//					}
//					
//					valueOrg = valueOrg.add(t2.getGlOrgDebit());
//					valueIdr = valueIdr.add(t2.getGlIdrDebit());
//					
//					tr2Entities.add(t2);
//				}
//				
//				tr1.setGlTrxValueOrg(valueOrg);
//				tr1.setGlTrxValueIdr(valueIdr);
//			}
//			
//			TR0006KEntity tReins = tR0006KRepo.findReinsByGroupAndLayer(TRE, RQ, trxVoucherId, groupId, layer).get(0);
//			
//			String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
//			TR0003Entity tr3 = new TR0003Entity();
//			tr3.setTrxType(RQ);
//			tr3.setTrxVoucherId(voucherId);
//			tr3.setTrxDescription(descCli + " - " + premiumValue);
//			tr3.setTrxClient(tClient.getTrxClient());
//			tr3.setTrxDate(calTrx.getTime());
//			tr3.setTrxAssured(tReins.getTrxInsId());
//			tr3.setTrxCoverCode(null);
//			tr3.setTrxInsOfficer(tClient.getTrxOfficer());
//			tr3.setTrxInsInsured(descCli + " - " + premiumValue); 
//			tr3.setTrxInsStart(tInterest.getTrxInsStart());
//			tr3.setTrxInsEnd(tInterest.getTrxInsEnd());
//			tr3.setTrxCurrId(tClient.getTrxCurrId());
//			tr3.setTrxCurrRate(exchangeRateCli);
//			tr3.setTrxAmountDue(premiumValue); 
//			tr3.setTrxOldVoucherId(trxVoucherId);
//			tr3.setTrxStatusData("11");
//			tr3.setCreateOn(now);
//			tr3.setCreateBy(param.get(Param.USER).toString());
//			
//			List<Map<String, Object>> table = mapTable.get(tClient.getTrxClient());
//			if(table == null)
//				table = new ArrayList<Map<String,Object>>();
//			
//			Map<String, Object> cover = new HashMap<String, Object>();
//			cover.put("name", client.getCliName());
//			cover.put("document", "Covering Letter");
//			
//			String fileName = "CL - " + client.getCliName();
//			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '-', '-', '-', '-', 'quotation')\">" 
//					+ "<i class=\"fa fa-print\"></i>" 
//					+ "</button>&nbsp;";
//			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '-', '-', '-', '-', 'quotation', '" + fileName + "')\">" 
//					+ "<i class=\"fas fa-file-word\"></i>" 
//					+ "</button>";
//			
//			cover.put("action", action);
//			
//			if(j == 1 && !isAdjustment)
//				table.add(cover);
//			
//			Map<String, Object> journal = new HashMap<String, Object>();
//			journal.put("voucherId", tr3.getTrxVoucherId());
//			journal.put("group", group);
//			journal.put("layer", layer);
//			journal.put("name", client.getCliName());
//			journal.put("document", "Closing - DN");
//			journal.put("type", "DEBIT NOTE");
//			
//			String fn = descAdjustment + "DN - CEDANT - " + group + " - " + layer;
//			
//			action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + group + "', '" + layer + "', '" + client.getCliCode() + "', '" + client.getCliName() + "', 'DEBIT NOTE')\">" 
//					+ "<i class=\"fa fa-print\"></i>" 
//					+ "</button>&nbsp;";
//			action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + group + "', '" + layer + "', '" + client.getCliCode() + "', '" + client.getCliName() + "', '" + fn + "', 'DEBIT NOTE')\">" 
//					+ "<i class=\"fas fa-file-word\"></i>" 
//					+ "</button>";
//			
//			journal.put("action", action);
//			table.add(journal);
//			
//			mapTable.put(tClient.getTrxClient(), table);
//			
//			int noRow = 1;
//			List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
//			for(TR0006EEntity payMthdCli : tChecklistCli) {
//				TR0003AEntity tr3a = new TR0003AEntity();
//				tr3a.setTrxType(tr3.getTrxType());
//				tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
//				tr3a.setTrxNoRow(noRow);
//				
//				tr3a.setTrxDueDate(payMthdCli.getTrxPrDate());
//				tr3a.setTrxRemarks("Instalment "+noRow);
//				
//				BigDecimal shareRatePrCli = getRate(payMthdCli.getTrxPrShare());
//				tr3a.setTrxDueAmount(premiumValue.multiply(shareRatePrCli));
//				
//				tr3a.setTrxTrxClass("OP");
//				
//				tr3aEntities.add(tr3a);
//				
//				noRow++;
//			}
//			
//			int count = 1;
//			List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
//			for(TR0001Entity tr1 : tr1Entities) {
//				TR0012Entity tr12 = new TR0012Entity();
//				tr12.setTrxTrxClass(tr1.getGlTrxClass());
//				tr12.setTrxType(tr1.getGlType());
//				tr12.setTrxVoucherId(tr1.getGlVoucherId());
//				tr12.setTrxDate(tr1.getGlTrxDate());
//				tr12.setTrxDueDate(tr1.getGlTrxDue());
//				
//				Long diffInMillies = Math.abs(tr1.getGlTrxDue().getTime() - tr1.getGlTrxDate().getTime());
//				Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//				tr12.setTrxMethPay(diff.toString());
//				
//				tr12.setTrxCoverCode(null);
//				tr12.setTrxCountInv(count);
//				tr12.setTrxDataStatus("11");
//				tr12.setTrxClient(tClient.getTrxClient());
//				tr12.setTrxDescription(tr1.getGlTrxDesc());
//				tr12.setTrxCurrId(tClient.getTrxCurrId());
//				tr12.setTrxCurrRate(exchangeRateCli);
//				
//				tr12.setTrxInvcAmount(premiumValue.multiply(tr1.getPrRate()));
//				tr12.setTrxOrgAmount(premiumValue.multiply(tr1.getPrRate()));
//				tr12.setTrxDiscAmount(BigDecimal.ZERO);
//				tr12.setTrxDeducAmount(BigDecimal.ZERO);
//				tr12.setTrxNetTou(BigDecimal.ZERO);
//				tr12.setTrxNetTtl(BigDecimal.ZERO);
//				
//				tr12.setTrxInsOfficer(tClient.getTrxOfficer());
//				tr12.setTrxOldType(RQ);
//				tr12.setTrxOldVoucherId(trxVoucherId);
//				tr12.setCreateOn(now);
//				tr12.setCreateBy(param.get(Param.USER).toString());
//				tr12.setModifyOn(now);
//				tr12.setModifyBy(param.get(Param.USER).toString());
//				
//				tr12Entities.add(tr12);
//			
//				count++;
//			}
//			
//			j++;
//			
//			tR0001Repo.saveAll(tr1Entities);
//			tR0002Repo.saveAll(tr2Entities);
//			tR0003Repo.save(tr3);
//			tR0003ARepo.saveAll(tr3aEntities);
//			tR0012Repo.saveAll(tr12Entities);
//		}
//
//		Map<String, String> mapCS = new HashMap<String, String>();
//		List<Map<String, Object>> reinsValueList = (List<Map<String, Object>>) param.get(CLOSING_PARAM.TRE_REINS_VALUE);
//		List<TR0006EEntity> tChecklistReins = (List<TR0006EEntity>) param.get(CLOSING_PARAM.PAYMENT_METHOD_REINS_LIST);
//		for(Map<String, Object> reinsValue :reinsValueList) {
//			MA0005Entity client = mA0005Repo.findByCliCode(Param.getStr(reinsValue, "reinsuranceId"));
//			
//			String group = Param.getStr(reinsValue, "group");
//			String groupId = Param.getStr(reinsValue, "groupId");
//			String layer = Param.getStr(reinsValue, "layer");
//			String descReins = descAdjustment + "Treaty Non Prop – " + year 
//					+ " - " + group 
//					+ " - " + layer
//					+ " - " + trxVoucherId;
//			
//			String brCodeReins = "VTRX" + tClient.getTrxCurrId();
//			List<MA0018Entity> businessRuleReins = mA0018Repo.findByBrCode(brCodeReins);
//
//			BigDecimal exchangeRateReins = tClient.getTrxCurrRate();
////			BigDecimal portionRateReins = getRate(Param.getBd(reinsValue, "share"));
//			
//			BigDecimal premiumValue = Param.getBd(reinsValue, "payToUW");
//			premiumValue = premiumValue.setScale(2, RoundingMode.HALF_UP);
//			
//			BigDecimal brkrFee = Param.getBd(reinsValue, "riCommAmount")
//					.multiply(new BigDecimal("11"))
//					.divide(new BigDecimal("10"));
//			
//			MA0014Entity m14Ppn = mA0014Repo.findByPaChildCode("TAXRATEH003");
//			BigDecimal ppnRate = getRate(new BigDecimal(m14Ppn.getPaChildValue()));
//			BigDecimal taxinBf = brkrFee.multiply(ppnRate);
//			
//			Map<String, BigDecimal> mapValue = new HashMap<String, BigDecimal>();
//			mapValue.put(PROD_MAPKEY.PREMIUM_VALUE, premiumValue);
//			mapValue.put(PROD_MAPKEY.BRKR_FEE_VALUE, brkrFee);
//			mapValue.put(PROD_MAPKEY.TAXIN_BF_VALUE, taxinBf);
//			
//			List<TR0001Entity> tr1Entities = new ArrayList<TR0001Entity>();
//			List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
//			for(TR0006EEntity payMthdReins : tChecklistReins) {
//				String voucherIdReins = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
//				
//				TR0001Entity tr1 = new TR0001Entity();
//				tr1.setGlTrxClass("OP");
//				tr1.setGlType("PU");
//				tr1.setGlVoucherId(voucherIdReins);
//				
//				tr1.setGlTrxDate(calTrx.getTime());
//				tr1.setGlTrxDue(payMthdReins.getTrxPrDate());
//				tr1.setGlTrxMonth(month.byteValue());
//				tr1.setGlTrxYear(year.shortValue());
//				
//				tr1.setGlTrxOfficeId("0");
//				tr1.setGlTrxProject("0000");
//				tr1.setGlTrxClient(client.getCliCode());
//				
//				BigDecimal shareRatePrReins = getRate(payMthdReins.getTrxPrShare());
//				tr1.setPrRate(shareRatePrReins);
//				
//				tr1.setGlTrxDesc(descReins + " - " + premiumValue);
//				tr1.setGlTrxStatus("0");
//				tr1.setGlDataStatus("11");
//				tr1.setCreateOn(now);
//				tr1.setCreateBy(param.get(Param.USER).toString());
//				tr1.setModifyOn(now);
//				tr1.setModifyBy(param.get(Param.USER).toString());
//				
//				tr1Entities.add(tr1);
//				
//				BigDecimal valueOrg = BigDecimal.ZERO;
//				BigDecimal valueIdr = BigDecimal.ZERO;
//				for(MA0018Entity ma0018 : businessRuleReins) {
//					TR0002Entity t2 = new TR0002Entity();
//					t2.setGlTrxClass(tr1.getGlTrxClass());
//					t2.setGlType(tr1.getGlType());
//					t2.setGlVoucherId(tr1.getGlVoucherId());
//					t2.setGlAccount(ma0018.getBrChildCoa());
//					
//					MA0015AEntity exchange = mA0015ARepo.findByCoaCodeAndExDate(ma0018.getBrChildCoa(), appDate);
//					t2.setGlCurrId(exchange.getExCurrId());
//					t2.setGlCurrRate(exchange.getExKmkRateBd());
//					
//					String code = ma0018.getBrChildValue().trim();
//					BigDecimal value = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
//					BigDecimal org = value.multiply(shareRatePrReins);
//					BigDecimal idr = org.multiply(exchange.getExKmkRateBd());
//					
//					t2.setGlOrgDebit(BigDecimal.ZERO);
//					t2.setGlIdrDebit(BigDecimal.ZERO);
//					t2.setGlOrgCredit(BigDecimal.ZERO);
//					t2.setGlIdrCredit(BigDecimal.ZERO);
//					if(ma0018.getBrChildDc().equals('0')) {
//						t2.setGlOrgDebit(org);
//						t2.setGlIdrDebit(idr);
//					}else if(ma0018.getBrChildDc().equals('1')) {
//						t2.setGlOrgCredit(org);
//						t2.setGlIdrCredit(idr);
//					}
//					
//					valueOrg = valueOrg.add(t2.getGlOrgDebit());
//					valueIdr = valueIdr.add(t2.getGlIdrDebit());
//					
//					tr2Entities.add(t2);
//				}
//				
//				tr1.setGlTrxValueOrg(valueOrg);
//				tr1.setGlTrxValueIdr(valueIdr);
//			}
//			
//			String clientAssured = tClient.getTrxClient();
//			String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
//			TR0003Entity tr3 = new TR0003Entity();
//			tr3.setTrxType(RQ);
//			tr3.setTrxVoucherId(voucherId);
//			tr3.setTrxDescription(descReins + " - " + premiumValue);
//			tr3.setTrxClient(client.getCliCode());
//			tr3.setTrxDate(calTrx.getTime());
//			tr3.setTrxAssured(clientAssured);
//			tr3.setTrxCoverCode(null);
//			tr3.setTrxInsOfficer(tClient.getTrxOfficer());
//			tr3.setTrxInsInsured(descReins + " - " + premiumValue); 
//			tr3.setTrxInsStart(tInterest.getTrxInsStart());
//			tr3.setTrxInsEnd(tInterest.getTrxInsEnd());
//			tr3.setTrxCurrId(tClient.getTrxCurrId());
//			tr3.setTrxCurrRate(exchangeRateReins);
//			tr3.setTrxAmountDue(premiumValue); 
//			tr3.setTrxOldVoucherId(trxVoucherId); 
//			tr3.setTrxStatusData("11");
//			tr3.setCreateOn(now);
//			tr3.setCreateBy(param.get(Param.USER).toString());
//		
//			List<Map<String, Object>> table = mapTable.get(client.getCliCode());
//			if(table == null)
//				table = new ArrayList<Map<String,Object>>();
//			
//			Map<String, Object> cover = new HashMap<String, Object>();
//			cover.put("name", client.getCliName());
//			cover.put("document", "Covering Letter");
//			
//			String fileName = "CL - " + client.getCliName();
//			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '-', '-', '-', '" + client.getCliCode() + "', 'quotation')\">" 
//					+ "<i class=\"fa fa-print\"></i>" 
//					+ "</button>&nbsp;";
//			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '-', '-', '-', '" + client.getCliCode() + "', 'quotation', '" + fileName + "')\">" 
//					+ "<i class=\"fas fa-file-word\"></i>" 
//					+ "</button>";
//			
//			cover.put("action", action);
//			
//			if(table.size() == 0 && !isAdjustment)
//				table.add(cover);
//			
//			Map<String, Object> closingslip = new HashMap<String, Object>();
//			closingslip.put("name", client.getCliName());
//			closingslip.put("group", group);
//			closingslip.put("document", "Closing Slip");
//			
//			fileName = "CS - " + group + " - " + client.getCliName();
//			action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + groupId + "', '-', '-', '" + client.getCliCode() + "', 'closingslip')\">" 
//					+ "<i class=\"fa fa-print\"></i>" 
//					+ "</button>&nbsp;";
//			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + groupId + "', '-', '-', '" + client.getCliCode() + "', 'closingslip', '" + fileName + "')\">" 
//					+ "<i class=\"fas fa-file-word\"></i>" 
//					+ "</button>";
//			
//			closingslip.put("action", action);
//			
//			String keyCS = client.getCliCode().concat(group);
//			if(mapCS.get(keyCS) == null && !isAdjustment) {
//				table.add(closingslip);
//				mapCS.put(keyCS, keyCS);
//			}
//			
//			Map<String, Object> journal = new HashMap<String, Object>();
//			journal.put("voucherId", tr3.getTrxVoucherId());
//			journal.put("group", group);
//			journal.put("layer", layer);
//			journal.put("name", client.getCliName());
//			journal.put("document", "Closing - CN");
//			journal.put("type", "CREDIT NOTE");
//			
//			String fn = descAdjustment + "CN - " + client.getCliName() +" - " + group + " - " + layer;
//			
//			action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + group + "', '" + layer + "', '" + client.getCliCode() + "', '" + client.getCliName() + "', 'CREDIT NOTE')\">" 
//					+ "<i class=\"fa fa-print\"></i>" 
//					+ "</button>&nbsp;";
//			action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + group + "', '" + layer + "', '" + client.getCliCode() + "', '" + client.getCliName() + "', '" + fn + "', 'CREDIT NOTE')\">" 
//					+ "<i class=\"fas fa-file-word\"></i>" 
//					+ "</button>";
//			
//			journal.put("action", action);
//			table.add(journal);
//			
//			mapTable.put(client.getCliCode(), table);
//			
//			int noRow = 1;
//			List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
//			for(TR0006EEntity payMthdReins : tChecklistReins) {
//				TR0003AEntity tr3a = new TR0003AEntity();
//				tr3a.setTrxType(tr3.getTrxType());
//				tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
//				tr3a.setTrxNoRow(noRow);
//				
//				tr3a.setTrxDueDate(payMthdReins.getTrxPrDate());
//				tr3a.setTrxRemarks("Instalment "+noRow);
//				
//				BigDecimal shareRatePrReins = payMthdReins.getTrxPrShare().divide(BD_100);
//				tr3a.setTrxDueAmount(premiumValue.multiply(shareRatePrReins));
//				
//				tr3a.setTrxTrxClass("OP");
//				
//				tr3aEntities.add(tr3a);
//				
//				noRow++;
//			}
//			
//			int count = 1;
//			List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
//			for(TR0001Entity tr1 : tr1Entities) {
//				TR0012Entity tr12 = new TR0012Entity();
//				tr12.setTrxTrxClass(tr1.getGlTrxClass());
//				tr12.setTrxType(tr1.getGlType());
//				tr12.setTrxVoucherId(tr1.getGlVoucherId());
//				tr12.setTrxDate(tr1.getGlTrxDate());
//				tr12.setTrxDueDate(tr1.getGlTrxDue());
//				
//				Long diffInMillies = Math.abs(tr1.getGlTrxDue().getTime() - tr1.getGlTrxDate().getTime());
//				Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
//				tr12.setTrxMethPay(diff.toString());
//				
//				tr12.setTrxCoverCode(null);
//				tr12.setTrxCountInv(count);
//				tr12.setTrxDataStatus("11");
//				tr12.setTrxClient(client.getCliCode());
//				tr12.setTrxDescription(tr1.getGlTrxDesc());
//				tr12.setTrxCurrId(tClient.getTrxCurrId());
//				tr12.setTrxCurrRate(exchangeRateReins);
//				
//				tr12.setTrxInvcAmount(premiumValue.multiply(tr1.getPrRate()));
//				tr12.setTrxOrgAmount(premiumValue.multiply(tr1.getPrRate()));
//				tr12.setTrxBrkrFee(brkrFee.multiply(tr1.getPrRate()));
//				tr12.setTrxTaxinBf(taxinBf.multiply(tr1.getPrRate()));
//				tr12.setTrxNetTou(BigDecimal.ZERO);
//				tr12.setTrxNetTtl(BigDecimal.ZERO);
//				
//				tr12.setTrxInsOfficer(tClient.getTrxOfficer());
//				tr12.setTrxOldType(RQ);
//				tr12.setTrxOldVoucherId(trxVoucherId);
//				tr12.setCreateOn(now);
//				tr12.setCreateBy(param.get(Param.USER).toString());
//				tr12.setModifyOn(now);
//				tr12.setModifyBy(param.get(Param.USER).toString());
//				
//				tr12Entities.add(tr12);
//			
//				count++;
//			}
//			
//			tR0001Repo.saveAll(tr1Entities);
//			tR0002Repo.saveAll(tr2Entities);
//			tR0003Repo.save(tr3);
//			tR0003ARepo.saveAll(tr3aEntities);
//			tR0012Repo.saveAll(tr12Entities);
//		}
//		
//		String remarks = Param.getStr(param, REMARKS);
//		Date closingDate = Param.getDate(param, CLIENT_CONFIRMATION_DATE);
//		
//		for(String key : mapTable.keySet()) {
//			tableAll.addAll(mapTable.get(key));
//		}
//		
//		if(!isAdjustment) {
//			tR0006Repo.updateTrxDataStatus("11", remarks, closingDate, TRE, RQ, trxVoucherId);
//			tableAll.addAll(0, createEOC(trxVoucherId));	
//		}
//		
//		return tableAll;
//	}

	@Transactional
	@SuppressWarnings("unchecked")
	public List<String> DCNotes(Map<String, Object> param) throws ParseException {	
		String txVoucherId = Param.getStr(param, "txVoucherId");
		String idKey = Param.getStrWithDef(param, "idKey");
		
		List<Long> idKeyClaims = new ArrayList<Long>();
		for(String id : Arrays.asList(idKey.split(","))) idKeyClaims.add(Long.valueOf(id));
		List<TR0015AEntity> tr15a = (List<TR0015AEntity>) tr0015ARepo.findAllById(idKeyClaims);
		
		String group = "";
		String layer = "";
		
		for(TR0015AEntity t : tr15a) {
			if(t.getTxClaimType().equals("0")) {
				group = t.getTxCobGroup();
				layer = t.getTxLayer();
			}
		}
		
		group = StringUtils.isNotBlank(group) ? group : tr15a.get(0).getTxCobGroup();
		layer = StringUtils.isNotBlank(layer) ? layer : tr15a.get(0).getTxLayer();
		
//		String journal = "DNTRXIDR";
		String journal = "DNCLAIM";
		String type = "Normal";

		if(idKeyClaims.size() == tr15a.size())
			type = "ALL";
		
		Map<String, Object> paramDetail = new HashMap<String, Object>();
		paramDetail.put(TX_TYPE, "CS");
		paramDetail.put(TX_VOUCHER_ID, txVoucherId);
		Map<String, Object> detail = (Map<String, Object>) inquiryDetail(paramDetail);
		
		TR0015Entity client = (TR0015Entity) detail.get("t1Data");
		
		TR0006BEntity tr15b = tr0006BRepo.findLeadByTrxIdAndTrxVoucherId(client.getTxOldType(), client.getTxOldVoucherId());
		List<TR0006KEntity> tr15kEntities = tr0006KRepo.findLeadReinsByGroupAndLayer(client.getTxOldVoucherId(), group, layer);
		
		String clientIssued = tr15b != null ? tr15b.getTrxInsId() : tr15kEntities.get(0).getTrxInsId();
		
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(common.getAppDate());
		String clientAssured = client.getTxClientId();
		String typeOfCover = client.getTxCoverId();
		
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);

		BigDecimal amountDue = BigDecimal.ZERO;
		for(TR0015AEntity t : tr15a) {
			BigDecimal plaDla = BigDecimal.ZERO;
			if(t.getTxClaimType().equals("1")) {	//deduction
				plaDla = t.getTxClaimDla().signum() != 0 ? t.getTxClaimDla() : t.getTxClaimPla();
				plaDla = plaDla.negate();
			}else {
				plaDla = t.getTxClaimDla().signum() != 0 ? t.getTxClaimDla() : t.getTxClaimPla();
			}
			
			amountDue = amountDue.add(plaDla);
		}
		
		String descriptionInterest = "Debit Note - Claim - " + typeOfCover + " - " + client.getTxBrdxId() + " - ";
		descriptionInterest += client.getTxPartyName() + " - " + type + " - " + amountDue;
				
		String currency = client.getTxCurrId();
	
		List<MA0018Entity> businessRule = ma0018Repo.findByBrCode(journal);
		Date now = Calendar.getInstance().getTime();
		
		List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
		String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		
		TR0001Entity tr1Reins = new TR0001Entity();
		tr1Reins.setGlTrxClass("OP");
		if(journal.startsWith("D"))
			tr1Reins.setGlType("SE");
		else
			tr1Reins.setGlType("PU");
		tr1Reins.setGlVoucherId(voucherId);
		
		tr1Reins.setGlTrxDate(calTrx.getTime());
		tr1Reins.setGlTrxDue(calTrx.getTime());
		tr1Reins.setGlTrxMonth(month.byteValue());
		tr1Reins.setGlTrxYear(year.shortValue());
		
		tr1Reins.setGlTrxOfficeId("0");
		tr1Reins.setGlTrxProject("0000");
		tr1Reins.setGlTrxClient(clientIssued);
		
		MA0015AEntity exchange = ma0015ARepo.findByExDateAndExCurrId(calTrx.getTime(), currency);
		tr1Reins.setGlTrxDesc(descriptionInterest);
		BigDecimal valueOrg = amountDue;
		BigDecimal valueIdr = valueOrg.multiply(exchange.getExKmkRate());
		tr1Reins.setGlTrxValueOrg(valueOrg);
		tr1Reins.setGlTrxValueIdr(valueIdr);
		tr1Reins.setGlTrxStatus("0");
		tr1Reins.setGlDataStatus("11");
		tr1Reins.setCreateOn(now);
		tr1Reins.setCreateBy(param.get(Param.USER).toString());
		tr1Reins.setModifyOn(now);
		tr1Reins.setModifyBy(param.get(Param.USER).toString());
		
		for(MA0018Entity ma0018 : businessRule) {
			TR0002Entity t2Reins = new TR0002Entity();
			t2Reins.setGlTrxClass(tr1Reins.getGlTrxClass());
			t2Reins.setGlType(tr1Reins.getGlType());
			t2Reins.setGlVoucherId(tr1Reins.getGlVoucherId());
			t2Reins.setGlAccount(ma0018.getBrChildCoa());
			t2Reins.setGlCurrId(currency);
			t2Reins.setGlCurrRate(exchange.getExKmkRate());
			
			t2Reins.setGlOrgDebit(BigDecimal.ZERO);
			t2Reins.setGlIdrDebit(BigDecimal.ZERO);
			t2Reins.setGlOrgCredit(BigDecimal.ZERO);
			t2Reins.setGlIdrCredit(BigDecimal.ZERO);
			if(ma0018.getBrChildDc().equals('0')) {
				t2Reins.setGlOrgDebit(valueOrg);
				t2Reins.setGlIdrDebit(valueIdr);
			}else if(ma0018.getBrChildDc().equals('1')) {
				t2Reins.setGlOrgCredit(valueOrg);
				t2Reins.setGlIdrCredit(valueIdr);
			}
			
			tr2Entities.add(t2Reins);
		}
		
		String voucherIdTr3Reins = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		TR0003Entity tr3Reins = new TR0003Entity();
		tr3Reins.setTrxType("RQ");
		tr3Reins.setTrxVoucherId(voucherIdTr3Reins);
		tr3Reins.setTrxDescription(descriptionInterest);
		tr3Reins.setTrxClient(clientIssued);
		tr3Reins.setTrxDate(calTrx.getTime());
		tr3Reins.setTrxAssured(clientAssured);
		tr3Reins.setTrxCoverCode(typeOfCover);
		tr3Reins.setTrxInsInsured(descriptionInterest);
		tr3Reins.setTrxInsStart(client.getTxDateFrom());
		tr3Reins.setTrxInsEnd(client.getTxDateTo());
		tr3Reins.setTrxAmtDesc(descriptionInterest);
		tr3Reins.setTrxCurrId(currency);
		tr3Reins.setTrxCurrRate(exchange.getExKmkRate());
		tr3Reins.setTrxAmountDue(amountDue);
		tr3Reins.setTrxStatusData("11");
		tr3Reins.setCreateOn(now);
		tr3Reins.setCreateBy(param.get(Param.USER).toString());
		
		int noRow = 1;
		TR0003AEntity tr3aReins = new TR0003AEntity();
		tr3aReins.setTrxType(tr3Reins.getTrxType());
		tr3aReins.setTrxVoucherId(tr3Reins.getTrxVoucherId());
		tr3aReins.setTrxNoRow(noRow);
		tr3aReins.setTrxRemarks("-");
		
		tr3aReins.setTrxDueDate(calTrx.getTime());
		
		tr3aReins.setTrxDueAmount(amountDue);
		
		tr3aReins.setTrxTrxClass("OP");
		
		TR0012Entity tr12Reins = new TR0012Entity();
		tr12Reins.setTrxTrxClass(tr1Reins.getGlTrxClass());
		tr12Reins.setTrxType(tr1Reins.getGlType());
		tr12Reins.setTrxVoucherId(tr1Reins.getGlVoucherId());
		tr12Reins.setTrxDate(tr1Reins.getGlTrxDate());
		tr12Reins.setTrxDueDate(tr1Reins.getGlTrxDue());
		
		Long diffInMillies = Math.abs(tr1Reins.getGlTrxDue().getTime() - tr1Reins.getGlTrxDate().getTime());
		Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		tr12Reins.setTrxMethPay(diff.toString());
		
		tr12Reins.setTrxCoverCode(typeOfCover);
		tr12Reins.setTrxCountInv(1);
		tr12Reins.setTrxDataStatus("11");
		tr12Reins.setTrxClient(clientIssued);
		tr12Reins.setTrxDescription(tr1Reins.getGlTrxDesc());
		tr12Reins.setTrxCurrId(currency);
		tr12Reins.setTrxCurrRate(exchange.getExKmkRate());
		tr12Reins.setTrxOrgAmount(amountDue);
		tr12Reins.setTrxIntAmount(BigDecimal.ZERO);
		tr12Reins.setTrxInvcAmount(tr1Reins.getGlTrxValueOrg());
		tr12Reins.setTrxSetAmount(BigDecimal.ZERO);
		tr12Reins.setTrxOldType(tr3Reins.getTrxType());
		tr12Reins.setTrxOldVoucherId(tr3Reins.getTrxVoucherId());
		tr12Reins.setCreateOn(now);
		tr12Reins.setCreateBy(param.get(Param.USER).toString());
		tr12Reins.setModifyOn(now);
		tr12Reins.setModifyBy(param.get(Param.USER).toString());
		tr12Reins.setTrxSource(SOURCE);
		
		//CREDIT NOTE
//		journal = "CNTRXIDR";
		journal = "CNCLAIM";
		
		clientIssued = client.getTxClientId();
		clientAssured = tr15b != null ? tr15b.getTrxInsId() : tr15kEntities.get(0).getTrxInsId();
		
		descriptionInterest = "Credit Note - Claim - " + typeOfCover + " - " + client.getTxBrdxId() + " - ";
		descriptionInterest += client.getTxPartyName() + " - " + type + " - " + amountDue;
		
		businessRule = ma0018Repo.findByBrCode(journal);
		
		tr2Entities = new ArrayList<TR0002Entity>();
		voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		
		TR0001Entity tr1Cli = new TR0001Entity();
		tr1Cli.setGlTrxClass("OP");
		if(journal.startsWith("D"))
			tr1Cli.setGlType("SE");
		else
			tr1Cli.setGlType("PU");
		tr1Cli.setGlVoucherId(voucherId);
		
		tr1Cli.setGlTrxDate(calTrx.getTime());
		tr1Cli.setGlTrxDue(calTrx.getTime());
		tr1Cli.setGlTrxMonth(month.byteValue());
		tr1Cli.setGlTrxYear(year.shortValue());
		
		tr1Cli.setGlTrxOfficeId("0");
		tr1Cli.setGlTrxProject("0000");
		tr1Cli.setGlTrxClient(clientIssued);
		
		tr1Cli.setGlTrxDesc(descriptionInterest);
		valueOrg = amountDue;
		valueIdr = valueOrg.multiply(exchange.getExKmkRate());
		tr1Cli.setGlTrxValueOrg(valueOrg);
		tr1Cli.setGlTrxValueIdr(valueIdr);
		tr1Cli.setGlTrxStatus("0");
		tr1Cli.setGlDataStatus("11");
		tr1Cli.setCreateOn(now);
		tr1Cli.setCreateBy(param.get(Param.USER).toString());
		tr1Cli.setModifyOn(now);
		tr1Cli.setModifyBy(param.get(Param.USER).toString());
		
		for(MA0018Entity ma0018 : businessRule) {
			TR0002Entity t2Cli = new TR0002Entity();
			t2Cli.setGlTrxClass(tr1Cli.getGlTrxClass());
			t2Cli.setGlType(tr1Cli.getGlType());
			t2Cli.setGlVoucherId(tr1Cli.getGlVoucherId());
			t2Cli.setGlAccount(ma0018.getBrChildCoa());
			t2Cli.setGlCurrId(currency);
			t2Cli.setGlCurrRate(exchange.getExKmkRate());
			
			t2Cli.setGlOrgDebit(BigDecimal.ZERO);
			t2Cli.setGlIdrDebit(BigDecimal.ZERO);
			t2Cli.setGlOrgCredit(BigDecimal.ZERO);
			t2Cli.setGlIdrCredit(BigDecimal.ZERO);
			if(ma0018.getBrChildDc().equals('0')) {
				t2Cli.setGlOrgDebit(valueOrg);
				t2Cli.setGlIdrDebit(valueIdr);
			}else if(ma0018.getBrChildDc().equals('1')) {
				t2Cli.setGlOrgCredit(valueOrg);
				t2Cli.setGlIdrCredit(valueIdr);
			}
			
			tr2Entities.add(t2Cli);
		}
		
		String voucherIdTr3Cli = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		TR0003Entity tr3Cli = new TR0003Entity();
		tr3Cli.setTrxType("RQ");
		tr3Cli.setTrxVoucherId(voucherIdTr3Cli);
		tr3Cli.setTrxDescription(descriptionInterest);
		tr3Cli.setTrxClient(clientIssued);
		tr3Cli.setTrxDate(calTrx.getTime());
		tr3Cli.setTrxAssured(clientAssured);
		tr3Cli.setTrxCoverCode(typeOfCover);
		tr3Cli.setTrxInsInsured(descriptionInterest);
		tr3Cli.setTrxInsStart(client.getTxDateFrom());
		tr3Cli.setTrxInsEnd(client.getTxDateTo());
		tr3Cli.setTrxAmtDesc(descriptionInterest);
		tr3Cli.setTrxCurrId(currency);
		tr3Cli.setTrxCurrRate(exchange.getExKmkRate());
		tr3Cli.setTrxAmountDue(amountDue);
		tr3Cli.setTrxStatusData("11");
		tr3Cli.setCreateOn(now);
		tr3Cli.setCreateBy(param.get(Param.USER).toString());
		
		noRow = 1;
		TR0003AEntity tr3aCli = new TR0003AEntity();
		tr3aCli.setTrxType(tr3Cli.getTrxType());
		tr3aCli.setTrxVoucherId(tr3Cli.getTrxVoucherId());
		tr3aCli.setTrxNoRow(noRow);
		tr3aCli.setTrxRemarks("-");
		
		tr3aCli.setTrxDueDate(calTrx.getTime());
		
		tr3aCli.setTrxDueAmount(amountDue);
		
		tr3aCli.setTrxTrxClass("OP");
		
		TR0012Entity tr12Cli = new TR0012Entity();
		tr12Cli.setTrxTrxClass(tr1Cli.getGlTrxClass());
		tr12Cli.setTrxType(tr1Cli.getGlType());
		tr12Cli.setTrxVoucherId(tr1Cli.getGlVoucherId());
		tr12Cli.setTrxDate(tr1Cli.getGlTrxDate());
		tr12Cli.setTrxDueDate(tr1Cli.getGlTrxDue());
		
		diffInMillies = Math.abs(tr1Cli.getGlTrxDue().getTime() - tr1Cli.getGlTrxDate().getTime());
		diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
		tr12Cli.setTrxMethPay(diff.toString());
		
		tr12Cli.setTrxCoverCode(typeOfCover);
		tr12Cli.setTrxCountInv(1);
		tr12Cli.setTrxDataStatus("11");
		tr12Cli.setTrxClient(clientIssued);
		tr12Cli.setTrxDescription(tr1Cli.getGlTrxDesc());
		tr12Cli.setTrxCurrId(currency);
		tr12Cli.setTrxCurrRate(exchange.getExKmkRate());
		tr12Cli.setTrxOrgAmount(amountDue);
		tr12Cli.setTrxIntAmount(BigDecimal.ZERO);
		tr12Cli.setTrxInvcAmount(tr1Cli.getGlTrxValueOrg());
		tr12Cli.setTrxSetAmount(BigDecimal.ZERO);
		tr12Cli.setTrxOldType(tr3Cli.getTrxType());
		tr12Cli.setTrxOldVoucherId(tr3Cli.getTrxVoucherId());
		tr12Cli.setCreateOn(now);
		tr12Cli.setCreateBy(param.get(Param.USER).toString());
		tr12Cli.setModifyOn(now);
		tr12Cli.setModifyBy(param.get(Param.USER).toString());
		tr12Cli.setTrxSource(SOURCE);
		
		tr0001Repo.save(tr1Reins);
		tr0002Repo.saveAll(tr2Entities);
		tr0003Repo.save(tr3Reins);
		tr0003ARepo.save(tr3aReins);
		tr0012Repo.save(tr12Reins);
		tr0001Repo.save(tr1Cli);
		tr0002Repo.saveAll(tr2Entities);
		tr0003Repo.save(tr3Cli);
		tr0003ARepo.save(tr3aCli);
		tr0012Repo.save(tr12Cli);
		
		List<String> vhouchers = new ArrayList<String>();
		vhouchers.add(voucherIdTr3Reins);
		vhouchers.add(voucherIdTr3Cli);
		
		return vhouchers;
	}

	@Override
	public Object dropdownYear() {
		return tr0015Repo.dropdownYear();
	}

	@Override
	public String exportClaimslistExcel(Map<String, Object> param) throws Exception {		
		String year = Param.getStr(param, "year");
		String status = Param.getStr(param, "status");
		
		Map<String, Object> paramStatus = new HashMap<String, Object>();
		paramStatus.put("parentCode", "STDATA");
		paramStatus.put("childValue", status);
		
		List<DropdownIdText> statusValIdText = common.getDropdownParam(paramStatus);
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_YEAR", year);
		params.put("P_STATUS", status);
		params.put("P_STATUS_VAL", statusValIdText.size() > 0 ? statusValIdText.get(0).getText() : "All");
		
		logger.info("Generate Claims List with param : {}.", param);
		
		return reportUtils.exportExcel("ClaimsListExcel.jrxml", params);
	}

	@Override
	public Object getValueTR6J(Map<String, Object> param) {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String groupId = Param.getStr(param, "groupId");
		String layer = Param.getStr(param, "layer");
		
		TR0006JEntity tr6j = tr0006JRepo.getTR6JClaims(trxVoucherId, groupId, layer);
		
		return tr6j;
	}

	@Override
	public String createDlaPlaPdf(Map<String, Object> param) throws Exception {
		return createDlaPla(param, "pdf");
	}

	@Override
	public String createDlaPlaDoc(Map<String, Object> param) throws Exception {
		return createDlaPla(param, "doc");
	}
	
	@SuppressWarnings("unchecked")
	private String createDlaPla(Map<String, Object> param, String fileType) throws Exception {		
		String txVoucherId = Param.getStr(param, "txVoucherId");
		String type = Param.getStr(param, "type");
		String idKey = Param.getStrWithDef(param, "idKey");
		
		String title = "PRELIMINARY LOSS ADVICE";
		if(type.equals("dla"))
			title = "DEFINITE LOSS ADVICE";
		
		Map<String, Object> paramDetail = new HashMap<String, Object>();
		paramDetail.put(TX_TYPE, "CS");
		paramDetail.put(TX_VOUCHER_ID, txVoucherId);
		Map<String, Object> detail = (Map<String, Object>) inquiryDetail(paramDetail);
		
		TR0015Entity client = (TR0015Entity) detail.get("t1Data");
		
		String yyyy = txVoucherId.substring(4, 8);
		String no = txVoucherId.substring(8, txVoucherId.length());
		String ref = yyyy + "/" + client.getTxCoverId() + "/" + no;
		
		List<DropdownIdText> dToc = common.getDropdownTypeOfCover();
		DropdownIdText toc = dToc.stream()
				.filter(t -> t.getId().equals(client.getTxCoverId()))
				.findFirst().orElse(null);
		
		List<Long> idKeyClaims = new ArrayList<Long>();
		for(String s : Arrays.asList(idKey.split(","))) idKeyClaims.add(Long.valueOf(s));
		List<TR0015AEntity> tr15a = (List<TR0015AEntity>) tr0015ARepo.findAllById(idKeyClaims);
		
		String group = "";
		String layer = "";
		
		for(TR0015AEntity t : tr15a) {
			if(t.getTxClaimType().equals("0")) {
				group = t.getTxCobGroup();
				layer = t.getTxLayer();
			}
		}
		
		group = StringUtils.isNotBlank(group) ? group : tr15a.get(0).getTxCobGroup();
		layer = StringUtils.isNotBlank(layer) ? layer : tr15a.get(0).getTxLayer();
		
		String typeOfTreaty = tr0006JRepo.getTypeOfTreaty("TRE", client.getTxOldType(), client.getTxOldVoucherId());
		
		Boolean isNonProp = Boolean.FALSE;
		if("N".equals(typeOfTreaty))
			isNonProp = Boolean.TRUE;
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_TX_VOUCHER_ID", txVoucherId);
		params.put("P_TITLE", title);
		params.put("P_REF_NO", ref);
		params.put("P_COB", toc == null ? "" : toc.getText());
		params.put("P_GROUP", group);
		params.put("P_LAYER", layer);
		params.put("P_ID_KEY_CLAIMS", Arrays.asList(idKey.split(",")));
		params.put("P_IS_NONPROP", isNonProp);
		
		String insId = Param.getStr(param, "insId");
		
		List<TR0006BEntity> reins1 = tr0006BRepo.findByTrxTrxIdAndTrxVoucherIdAndTrxInsId(
				client.getTxOldType(), client.getTxOldVoucherId(), insId);
		
		if(reins1.size() > 0) {
			params.put("P_INS_ID", reins1.get(0).getTrxInsId());
			params.put("P_INS_SHARE", reins1.get(0).getTrxInsShare());
			if(type.equals("dla"))
				saveTR0015D(params, reins1.get(0).getTrxInsType(), detail, tr15a);
			
			if(fileType.equals("doc"))
				return reportUtils.exportDocx("DlaPla_Claims.jrxml", params);
			else
				return reportUtils.exportPdf("DlaPla_Claims.jrxml", params);
		}
		
		List<TR0006KEntity> reins2 = tr0006KRepo.findReinsByGroupAndLayerAndTrxInsId("TRE", client.getTxOldType(), 
				client.getTxOldVoucherId(), group, layer, insId);
		
		if(reins2.size() > 0) {
			params.put("P_INS_ID", reins2.get(0).getTrxInsId());
			params.put("P_INS_SHARE", reins2.get(0).getTrxInsShare());
			if(type.equals("dla"))
				saveTR0015D(params, "", detail, tr15a);
			
			if(fileType.equals("doc"))
				return reportUtils.exportDocx("DlaPla_Claims.jrxml", params);
			else
				return reportUtils.exportPdf("DlaPla_Claims.jrxml", params);
		}
		
		return "";
	}

	@Override
	public Object getListInsurance(Map<String, Object> param) throws Exception {
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		String group = Param.getStr(param, "group");
		String layer = Param.getStr(param, "layer");
		
		TR0015Entity tr15 = tr0015Repo.findByTxTypeAndTxVoucherId("CS", txVoucherId);
		
		List<TR0006BEntity> fac = tr0006BRepo.getDataReinsurance(tr15.getTxOldType(), tr15.getTxOldVoucherId());	
		if(fac.size() > 0)
			return fac;
		
		List<TR0006KEntity> tre = tr0006KRepo.findReinsByGroupAndLayer("TRE",
				tr15.getTxOldType(), tr15.getTxOldVoucherId(), group, layer);
		
		return tre;
	}

	@Override
	public Object getListTR15A(Map<String, Object> param) {
		String txType = Param.getStr(param, TX_TYPE);
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		
		List<TR0015AEntity> value = tr0015ARepo.findValue(txType, txVoucherId);
		
		return value;
	}
	
	private void saveTR0015D(Map<String, Object> params, String insType,
			Map<String, Object> detail, List<TR0015AEntity> tr15a) {
		String txVoucherId = Param.getStr(params, "P_TX_VOUCHER_ID");
		String txInsId = Param.getStr(params, "P_INS_ID");
		BigDecimal txInsShare = Param.getBd(params, "P_INS_SHARE");
		
		TR0015DEntity tr15d = tr0015DRepo
				.findByTxVoucherIdAndTxInsIdAndTxInsShare(txVoucherId, txInsId, txInsShare);
		TR0015Entity clientTab = (TR0015Entity) detail.get("t1Data");
		TR0015Entity claimTab = (TR0015Entity) detail.get("t2Data");
		
		if(tr15d == null)
			tr15d = new TR0015DEntity();

		tr15d.setTxType("CS");
		tr15d.setTxVoucherId(txVoucherId);
		tr15d.setTxInsId(txInsId);
		tr15d.setTxInsShare(txInsShare);
		tr15d.setTxInsStatus("0");
		tr15d.setTxCurrId(clientTab.getTxCurrId());
		
		String att = "Claims No : " + txVoucherId + ", Obligee : " + clientTab.getTxPartyName();
		att += ", U/W Reff : " + claimTab.getTxUwReff() + ", DOL : ";
		att += (claimTab.getTxLostDate() == null) ? "" : formatDateddMMMMyyyy.format(claimTab.getTxLostDate());
		att += ", Reinstate: " + clientTab.getTxReinsNo();
		
		tr15d.setTxInsAtt(att);
		
		BigDecimal claim = getClaimDueToUs(tr15a);
		BigDecimal rate = getRate(txInsShare);
		tr15d.setTxInsAmount(claim.multiply(rate));
		
		tr0015DRepo.save(tr15d);
	}
	
	private BigDecimal getClaimDueToUs(List<TR0015AEntity> tr15a) {	
		BigDecimal claim = BigDecimal.ZERO;
		
		for(TR0015AEntity t : tr15a) {
			BigDecimal value = t.getTxClaimDla();
			if(t.getTxClaimType().equals("1"))
				value = value.negate();
				
			claim = claim.add(value);
		}
		
		return claim;
	}
	
	private BigDecimal getRate(BigDecimal val) {
		return val.divide(new BigDecimal("100"), 4);
	}

	@Override
	public List<TR0015DEntity> getFinance(Map<String, Object> param) {
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		
		List<TR0015DEntity> tr15d = tr0015DRepo.findByTxVoucherIdAndTxType(txVoucherId, "CS");
		
		return tr15d;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object processFinance(Map<String, Object> param) {
		logger.info("do processFinance() with param : {}.", param);
		
		String txVoucherId = Param.getStr(param, TX_VOUCHER_ID);
		String type = Param.getStr(param, "type");
		String bank = Param.getStr(param, "bank");
		String reffId = Param.getStr(param, "reffId");
		Date entryDate = Param.getDate(param, "entryDate");
		BigDecimal exchangeRate = Param.getBd(param, "exchangeRate");
		
		String currBank = Param.getStr(param, "currBank"); 
		Boolean isAdjustment = Param.getBoolean(param, "isAdjustment");
		BigDecimal diff = Param.getBd(param, "diff");
		BigDecimal pyrc = Param.getBd(param, "pyrc"); //IDR
				
		List<Map<String, Object>> t7Table = (ArrayList<Map<String, Object>>) param.get("t7Table");
		
		Map<String, Object> paramDetail = new HashMap<String, Object>();
		paramDetail.put(TX_TYPE, "CS");
		paramDetail.put(TX_VOUCHER_ID, txVoucherId);
		Map<String, Object> detail = (Map<String, Object>) inquiryDetail(paramDetail);
		
		TR0015Entity tabClient = (TR0015Entity) detail.get("t1Data");
		
		String currId = null;
		String client = tabClient.getTxClientId();
		String description = null;
		for(Map<String, Object> t7 : t7Table) {
			description = Param.getStr(t7, "txInsAtt");
			currId = Param.getStr(t7, "txCurrId");
			
			if(type.equals("rc"))
				client = Param.getStr(t7, "txInsId");
		}

		Date now = Calendar.getInstance().getTime();
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(entryDate);
		
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		BigDecimal diffIdr = BigDecimal.ZERO;
		
		MA0015AEntity ma0015Trx = ma0015ARepo.findByExDateAndExCurrId(calTrx.getTime(), currId);	
		if(!currId.equals("IDR") && isAdjustment)
			diffIdr = diff.multiply(ma0015Trx.getExKmkRate());
		else if(currId.equals("IDR") && isAdjustment)
			diffIdr = diff;
		
		String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		TR0001Entity tr1 = new TR0001Entity();
		tr1.setGlTrxClass("OP");
		tr1.setGlType(type.toUpperCase());
		tr1.setGlVoucherId(voucherId);
		tr1.setGlTrxDate(entryDate);
		tr1.setGlTrxDue(entryDate);
		tr1.setGlTrxMonth(month.byteValue());
		tr1.setGlTrxYear(year.shortValue());
		tr1.setGlTrxProject("0000");
		tr1.setGlTrxOfficeId("0");
		tr1.setGlTrxClient(client);
		tr1.setGlTrxDesc(description);
		tr1.setGlReffId(reffId);
		tr1.setGlTrxValueOrg(pyrc.add(diffIdr));
		tr1.setGlTrxValueIdr(pyrc.add(diffIdr));
		tr1.setGlTrxStatus("0");
		tr1.setGlDataStatus("11");
		tr1.setCreateBy(Param.getStr(param, Param.USER));
		tr1.setCreateOn(now);
		tr1.setModifyBy(Param.getStr(param, Param.USER));
		tr1.setModifyOn(now);
		
		String journal = null;
		if(type.equals("rc")) {
			journal = "RC";	
			
			//Claim IDR, RC Non IDR (Bank Non IDR) -> RCCLTUSD
			if(currId.equals("IDR") && !currId.equals(currBank))
				journal += "CLT" + currBank;
			//Claim IDR, RC IDR (Bank IDR) -> RCCLSIDR
			else if(currId.equals(currBank))
				journal += "CLS" + currBank;
			//Claim USD (Non IDR), RC IDR (Bank IDR) -> RCCLBUSD
			else if(!currId.equals("IDR") && currBank.equals("IDR"))
				journal += "CLB" + currId;
		}else {
			journal = "PY";	
			
			//Claim IDR, PY Non IDR (Bank Non IDR) -> PYCLTUSD
			if(currId.equals("IDR") && !currId.equals(currBank))
				journal += "CLT" + currBank;
			//Claim IDR, PY IDR (Bank IDR) -> PYCLSIDR
			else if(currId.equals(currBank))
				journal += "CLS" + currBank;
			//Claim USD (Non IDR), PY IDR (Bank IDR) -> PYCLBUSD 
			else if(!currId.equals("IDR") && currBank.equals("IDR"))
				journal += "CLB" + currId;
		}
		
		List<MA0018Entity> businessRule = ma0018Repo.findByBrCode(journal);

		logger.info("Finance process with journal : {}, businessRule : {}.", journal, businessRule);

		Map<String, Object> mapValue = new HashMap<String, Object>();
		mapValue.put("NET_OS_VALUE", pyrc);
		mapValue.put("DI_AMOUNT", diffIdr);
			
		MA0005Entity cli = ma0005Repo.findByCliCode(client);
		List<TR0002Entity> tr2List = new ArrayList<TR0002Entity>();
		for (MA0018Entity ma0018 : businessRule) {
			TR0002Entity tr2 = new TR0002Entity();
			tr2.setGlTrxClass(tr1.getGlTrxClass());
			tr2.setGlType(tr1.getGlType());
			tr2.setGlVoucherId(tr1.getGlVoucherId());

			MA0004Entity cliBr = ma0004Repo.findByCoaCode(ma0018.getBrChildCoa());
			tr2.setGlAccount(ma0018.getBrChildCoa());
			tr2.setGlCurrId(cliBr.getCoaCurrId());
			
			BigDecimal exchangeBr = new BigDecimal("1");
			if(!cliBr.getCoaCurrId().equals("IDR"))
				exchangeBr = ma0015Trx.getExKmkRate();
			tr2.setGlCurrRate(exchangeBr);
				
			tr2.setGlDescription(type.toUpperCase() + " Claim : " + cli.getCliName());
			tr2.setGlOrgDebit(BigDecimal.ZERO);
			tr2.setGlIdrDebit(BigDecimal.ZERO);
			tr2.setGlOrgCredit(BigDecimal.ZERO);
			tr2.setGlIdrCredit(BigDecimal.ZERO);
			
			BigDecimal value = Param.getBdWithDef(mapValue, ma0018.getBrChildValue());
			if(ma0018.getBrChildDc().equals('0')) {
				tr2.setGlOrgDebit(value.divide(exchangeBr, 2, RoundingMode.HALF_UP));
				tr2.setGlIdrDebit(value);
			}else if(ma0018.getBrChildDc().equals('1')) {
				tr2.setGlOrgCredit(value.divide(exchangeBr, 2, RoundingMode.HALF_UP));
				tr2.setGlIdrCredit(value);
			}
			
			tr2List.add(tr2);
		}
		
		BigDecimal amountCIdr = BigDecimal.ZERO;
		BigDecimal amountDIdr = BigDecimal.ZERO;
		for(TR0002Entity t : tr2List) {
			amountDIdr = amountDIdr.add(t.getGlIdrDebit());
			amountCIdr = amountCIdr.add(t.getGlIdrCredit());
		}
		Boolean isDebit = amountDIdr.compareTo(amountCIdr) >= 0;
		BigDecimal amountIdr = isDebit ? amountDIdr.subtract(amountCIdr) : amountCIdr.subtract(amountDIdr);

		//BANK
		TR0002Entity t2Bank = new TR0002Entity();
		t2Bank.setGlTrxClass(tr1.getGlTrxClass());
		t2Bank.setGlType(tr1.getGlType());
		t2Bank.setGlVoucherId(tr1.getGlVoucherId());
		t2Bank.setGlAccount(bank);
		t2Bank.setGlCurrId(currBank);
		t2Bank.setGlCurrRate(exchangeRate);
		t2Bank.setGlOrgDebit(BigDecimal.ZERO);
		t2Bank.setGlIdrDebit(BigDecimal.ZERO);
		t2Bank.setGlOrgCredit(BigDecimal.ZERO);
		t2Bank.setGlIdrCredit(BigDecimal.ZERO);
			
		BigDecimal amountOrg = amountIdr.divide(exchangeRate, 2, RoundingMode.HALF_UP);
		if(isDebit) {
			t2Bank.setGlOrgCredit(amountOrg.abs());
			t2Bank.setGlIdrCredit(amountIdr.abs());
		}else {
			t2Bank.setGlOrgDebit(amountOrg.abs());
			t2Bank.setGlIdrDebit(amountIdr.abs());
		}
		tr2List.add(t2Bank);
		
		processUpdateTR0015D(t7Table, type, reffId, entryDate, journal, 
				(isAdjustment ? diff : BigDecimal.ZERO), exchangeRate, ma0015Trx.getExKmkRate());		
//		{type=py, txVoucherId=2504202100040, entryDate=25/04/2021, bank=111207, reffId=reff otttt, exchangeRate=1.00, pyrc=6,000.00, t7Table=[{idKey=1, txType=null, txVoucherId=2504202100040, txInsId=00216, txInsType=null, txInsShare=null, txInsStatus=null, txInsAtt=null, txCurrId=IDR, txInsAmount=161400, txInsReffIn=reff in, txInsReffOt=null, txInsPdate=null, txInsRdate=null, txInsReceived=400, txInsDesc=ANNISA DIYAN FITRI, receive=<input type="text" id="receive_t7_1" class="form-control" style="text-align: right;" onChange=t7receiveChange() required disabled ><input type="hidden" id="hreceive_t7_1">, action=<div class="input-group"><div class="custom-control custom-checkbox"><input type="checkbox" class="custom-control-input" name="t7_1" id="t7_1" onChange=t7checkboxChange(this.id)><label class="custom-control-label" for="t7_1"></label></div></div>, txInsAmountFmt=161,400.00, txInsReceivedFmt=161,000.00, receiveValue=6,000.00}], user=admin}

		tr0001Repo.save(tr1);
		if(tr2List.size() > 0)
			tr0002Repo.saveAll(tr2List);
		
		param.put("financeVoucher", voucherId);
		
		return param;
	}
	
	private void processUpdateTR0015D(List<Map<String, Object>> list, 
			String type, String reff, Date entryDate, 
			String br, BigDecimal diff, BigDecimal exchangeRateBank, BigDecimal exchangeRateTrx) {
		List<TR0015DEntity> update = new ArrayList<TR0015DEntity>();
		for(Map<String, Object> data : list) {
			Long idKey = Param.getLong(data, "idKey");
			TR0015DEntity tr15d = tr0015DRepo.findById(idKey).get();

			BigDecimal receive = Param.getBd(data, "receiveValue");
			BigDecimal pyrcDif = BigDecimal.ZERO;
			if(br.startsWith("RCCLT") || br.startsWith("PYCLT")) {
				pyrcDif = receive.multiply(exchangeRateBank);
				pyrcDif = pyrcDif.add(diff);
			}else if(br.startsWith("RCCLS") || br.startsWith("PYCLS")) {
				pyrcDif = receive;
				pyrcDif = pyrcDif.add(diff);
			}else if(br.startsWith("RCCLB") || br.startsWith("PYCLB")) {
				pyrcDif = receive.divide(exchangeRateTrx, 8, RoundingMode.HALF_UP);
				pyrcDif = pyrcDif.add(diff);
			}
			
			if(type.equals("rc")) { 	//Received
				tr15d.setTxInsReffIn(reff);
				tr15d.setTxInsRdate(entryDate);
				
				BigDecimal totalReceive = tr15d.getTxInsReceivedRc();
				tr15d.setTxInsReceivedRc(totalReceive.add(pyrcDif));
			}else {						//Payment
				tr15d.setTxInsReffOt(reff);
				tr15d.setTxInsPdate(entryDate);
				
				BigDecimal totalPayment = tr15d.getTxInsReceivedPy();
				tr15d.setTxInsReceivedPy(totalPayment.add(pyrcDif));
			}
			
		}
		
		tr0015DRepo.saveAll(update);
	}

	@Override
	public List<InternalMemoHelper> getListInternalMemo(Map<String, Object> param) {
		Date startDate = Param.getDate(param, "startDate");
		Date endDate = Param.getDate(param, "endDate");
		
		List<InternalMemoHelper> list = tr0015DRepo.getListInternalMemo(startDate, endDate);
		
		return list;
	}

	@Override
	public String createInternalMemo(Map<String, Object> param) throws Exception {
		String ccy = Param.getStr(param, "ccy");
		String cedant = Param.getStr(param, "cedant");
		Date startDate = Param.getDate(param, "startDate");
		Date endDate = Param.getDate(param, "endDate");
		
		Date appDate = common.getAppDate();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_CCY", ccy);
		params.put("P_CEDANT", cedant);
		params.put("P_START_DATE", startDate);
		params.put("P_END_DATE", endDate);
		params.put("P_APP_DATE", appDate);
		
		return reportUtils.exportDocx("ClaimsInternalMemo.jrxml", params);
	}

	@Override
	public String createInternalMemoExcel(Map<String, Object> param) throws Exception {
		Date startDate = Param.getDate(param, "startDate");
		Date endDate = Param.getDate(param, "endDate");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_START_DATE", startDate);
		params.put("P_END_DATE", endDate);
		
		return reportUtils.exportExcel("ClaimsInternalMemoExcel.jrxml", params);
	}

}
