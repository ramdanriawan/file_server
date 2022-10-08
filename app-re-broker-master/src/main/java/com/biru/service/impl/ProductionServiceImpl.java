package com.biru.service.impl;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.mail.internet.MimeMessage;
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
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.CLOSING_PARAM;
import com.biru.ReBrokerConstants.MESSAGE;
import com.biru.ReBrokerConstants.PARAM;
import com.biru.ReBrokerConstants.REST.EXTENSION;
import com.biru.ReBrokerConstants.REST.PRODUCTION_CLASS;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.EmailUtils;
import com.biru.component.ReportUtils;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0006Entity;
import com.biru.entity.MA0012Entity;
import com.biru.entity.MA0015AEntity;
import com.biru.entity.MA0018Entity;
import com.biru.entity.ResponseEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0003AEntity;
import com.biru.entity.TR0003Entity;
import com.biru.entity.TR0005Entity;
import com.biru.entity.TR0006AEntity;
import com.biru.entity.TR0006BEntity;
import com.biru.entity.TR0006CEntity;
import com.biru.entity.TR0006DEntity;
import com.biru.entity.TR0006EEntity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0006FEntity;
import com.biru.entity.TR0006GEntity;
import com.biru.entity.TR0012Entity;
import com.biru.entity.US0001Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0006Repo;
import com.biru.repository.MA0011Repo;
import com.biru.repository.MA0012Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0003ARepo;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0005Repo;
import com.biru.repository.TR0006ARepo;
import com.biru.repository.TR0006BRepo;
import com.biru.repository.TR0006CRepo;
import com.biru.repository.TR0006DRepo;
import com.biru.repository.TR0006ERepo;
import com.biru.repository.TR0006FRepo;
import com.biru.repository.TR0006GRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.US0001Repo;
import com.biru.repository.ViewInqProductionRepo;
import com.biru.service.CommonService;
import com.biru.service.DCNotesService;
import com.biru.service.ProductionService;
import com.biru.view.ViewInqProduction;

@Service
public class ProductionServiceImpl extends AbstractCommon implements ProductionService {

	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private MA0006Repo mA0006Repo;
	
	@Autowired
	private MA0011Repo mA0011Repo;
	
	@Autowired
	private MA0012Repo mA0012Repo;

	@Autowired
	private MA0014Repo mA0014Repo;
	
	@Autowired
	private MA0015ARepo mA0015ARepo;
	
	@Autowired
	private MA0018Repo mA0018Repo;

	@Autowired
	private TR0005Repo tR0005Repo;
	
	@Autowired
	private TR0006Repo tR0006Repo;

	@Autowired
	private TR0006ARepo tR0006ARepo;

	@Autowired
	private TR0006BRepo tR0006BRepo;

	@Autowired
	private TR0006CRepo tR0006CRepo;

	@Autowired
	private TR0006DRepo tR0006DRepo;

	@Autowired
	private TR0006ERepo tR0006ERepo;

	@Autowired
	private TR0006FRepo tR0006FRepo;
	
	@Autowired
	private TR0006GRepo tR0006GRepo;
	
	@Autowired
	private TR0001Repo tr0001Repo;
	
	@Autowired
	private TR0002Repo tr0002Repo;
	
	@Autowired
	private TR0003Repo tr0003Repo;
	
	@Autowired
	private TR0003ARepo tr0003ARepo;
	
	@Autowired
	private TR0012Repo tr0012Repo;
	
	@Autowired
	private ViewInqProductionRepo viewInqProductionRepo;
	
	@Autowired
	private US0001Repo uS0001Repo;

	@Autowired
	private CommonService common;
	
	@Autowired
	private DCNotesService dcNotesService;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private EmailUtils emailUtils;
	
	private static final String APP_DATE 		= "appDate";
	private static final String CREATE_BY		= "createBy";
	private static final String DATE 			= "date";
	private static final String FAC				= "FAC";
	private static final String RQ 				= "RQ";
	private static final String TRX_VOUCHER_ID 	= "trxVoucherId";

	private static final String DOC			 	= "doc";
	private static final String HTML		 	= "html";
	private static final String PDF			 	= "pdf";

	private static final String COVER			= "cover";
	private static final String DOCUMENT		= "document";
	private static final String PROCESS			= "process";
	private static final String PLACING			= "placing";
	private static final String CLOSING_SLIP	= "closingslip";
	private static final String EVIDENCE		= "evidence";
	private static final String EVIDENCE_OF_COVER	= "evidenceofcover";
	private static final String QUOTATION		= "quotation";
	private static final String REMARKS			= "remarks";
	private static final String CLIENT_CONFIRMATION_DATE = "clientConfirmationDate";
	
	private static final String GROSS_VALUE		= "GROSS_VALUE";
	private static final String GROSS_BF_VALUE 	= "GROSS_BF_VALUE";
	private static final String NET_TOU_VALUE	= "NET_TOU_VALUE";
	private static final String PREMIUM_VALUE 	= "PREMIUM_VALUE";
	private static final String DEDUC_VALUE 	= "DEDUC_VALUE";
	private static final String DISC_VALUE		= "DISC_VALUE";
	private static final String GROSS_TTL_VALUE = "GROSS_TTL_VALUE";
	private static final String BRKR_FEE_VALUE 	= "BRKR_FEE_VALUE";
	private static final String TAXIN_BF_VALUE	= "TAXIN_BF_VALUE";
	private static final String COMM_OUT_VALUE	= "COMM_OUT_VALUE";
	
	private static final BigDecimal BD_100		= new BigDecimal("100");
	
	private static final Logger logger = LoggerFactory.getLogger(ProductionServiceImpl.class);
	
	private SimpleDateFormat sdfDateTime;
	
	public ProductionServiceImpl() {
		this.sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	@Override
	public Object getName(Map<String, Object> param) {
		String saCode = Param.getStrWithDef(param, "saCode");
		
		return mA0012Repo.findBySaCode(saCode);
	}
	
	@Override
	public Object tcPremium(Map<String, Object> param) {
		String tcCode = param.get("tcCode").toString();
		return mA0011Repo.findByTcCode(tcCode);
	}

	@Override
	public Object taxRate() {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("taxRate", mA0014Repo.findByPaChildCodeAndPaChildStatus("TAXRATEH003", "11").getPaChildValue());
		return result;
	}

	@Override
	public Object termAndCondition(Map<String, Object> param) {
		List<MA0006Entity> listMA0006Entity = mA0006Repo.findByTcCode(param.get("tcCode").toString());
		for (MA0006Entity ma0006Entity : listMA0006Entity) {
			String action = "<button class=\"btn btn-danger\" onclick=\"t6Remove('"+ma0006Entity.getIdKey()+"')\">" 
					+ "<i class=\"fa fa-trash\"></i>" 
					+ "</button>";
			ma0006Entity.setAction(action);
		}
		return listMA0006Entity;
	}
	
	@Override
	public Object inquiry(Map<String, Object> param) throws ParseException {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String filterValue = Param.getStrWithDef(param, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Date appDate = common.getAppDate();
		Page<ViewInqProduction> data = viewInqProductionRepo.findProduction(appDate, PRODUCTION_CLASS.FAC, filterValue.toLowerCase(), pageable);
		
		
		for(ViewInqProduction view : data.getContent()) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			String createOnStr = sdf.format(view.getCreateOn());
			String action = "<button class=\"btn btn-secondary\" onclick=\"edit('"+view.getRequestId()+"','"+view.getTrxDataStatus()+"','"+createOnStr+"')\">" 
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
	public Object inquiryModify(Map<String, Object> param) throws ParseException {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String isEndorsement = Param.getStrWithDef(param, "isEndorsement");
		
		if(StringUtils.isBlank(voucherId))
			return "No data found";
				
		Map<String, Object> data = new HashMap<String, Object>();
		
		List<TR0006Entity> client = tR0006Repo.getDataClient(RQ, voucherId);
		data.put("t1Data", client);
		
		List<TR0006AEntity> interest = tR0006ARepo.getDataInterest(RQ, voucherId);
		data.put("t2Data", interest);
		
		List<TR0006GEntity> commOut = tR0006GRepo.getDataCommOut(RQ, voucherId);
		data.put("t3Data", commOut);
		
		List<TR0006BEntity> reinsurance = tR0006BRepo.getDataReinsurance(RQ, voucherId);
		data.put("t4Data", reinsurance);
		
		List<TR0006CEntity> value = tR0006CRepo.getDataValue(RQ, voucherId);
		data.put("t5Data", value);
		
		List<TR0006DEntity> tc = new ArrayList<TR0006DEntity>();
		if(isEndorsement.equals("true")) {
			tc = tR0006DRepo.getDataTcEndorsement(RQ, voucherId);
		}else {
			tc = tR0006DRepo.getDataTc(RQ, voucherId);
		}
		data.put("t6Data", tc);
		
		List<TR0006EEntity> checklist1 = tR0006ERepo.getDataChecklist1V2(RQ, voucherId);
		data.put("t7Data1", checklist1);
		
		List<TR0006FEntity> checklist2 = tR0006FRepo.getDataChecklist2(RQ, voucherId);
		data.put("t7Data2", checklist2);
		
		return data;
	}

	@Override
	@Transactional
	public Object save(Map<String, Object> param) throws ParseException {
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		
		String voucherId = Param.getStrWithDef(param, TRX_VOUCHER_ID);
		
		Date appDate = Param.getDate(param, "transactionDate");
		if(StringUtils.isBlank(voucherId))	//create
			voucherId = voucherComponent.saveVoucherCounter(formatDate.format(appDate));
		
		Date now = Calendar.getInstance().getTime();
		Date createOn = now;
		
		Long createOnMillis = Param.getLong(param, "createOn");
		if(createOnMillis != null)
			createOn = new Date(createOnMillis);
		
		param.put(TRX_VOUCHER_ID, voucherId);
		param.put(APP_DATE, appDate);
		param.put(DATE, now);

		saveClientInformation(param);

		saveInterestInsured(param);

		saveCommOut(param);
		
		saveReinsurance(param);
		
		saveValue(param);

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
		message.put("trxDataStatus", Param.getStr(param, "trxDataStatus"));

		response.setResult(voucherId);
		response.setMessage(message);
		
		return response;
	}
	
	@Override
	public Object delete(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		if(StringUtils.isBlank(voucherId))
			return "No data deleted";
		
		tR0006Repo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		tR0006ARepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		tR0006BRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		tR0006CRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		tR0006DRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		tR0006ERepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		tR0006FRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		tR0006GRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		
		return "Data successfully deleted";
	}

	@SuppressWarnings("unchecked")
	private void saveClientInformation(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		String trxDataStatus = "0";
		List<TR0006Entity> tr6Entities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		if(tr6Entities.size()>0)
			trxDataStatus = tr6Entities.get(0).getTrxDataStatus();
		param.put("trxDataStatus", trxDataStatus);
		
		tR0006Repo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);

		Date now = (Date) param.get(DATE);
		Date appDate = (Date) param.get(APP_DATE);
		
		Map<String, Object> t1Data = (HashMap<String, Object>) param.get("t1Data");
		List<HashMap<String, Object>> t1DataTable = (ArrayList<HashMap<String, Object>>) t1Data.get("t1DataTable");		

		List<TR0006Entity> clientInfo = new ArrayList<TR0006Entity>();
		for(HashMap<String, Object> data : t1DataTable) {
			TR0006Entity tR0006Entity = new TR0006Entity();
			tR0006Entity.setTrxClass(FAC);
			tR0006Entity.setTrxTrxId(RQ);
			tR0006Entity.setTrxVoucherId(voucherId);
			tR0006Entity.setTrxClient(Param.getStr(data, "client"));
			tR0006Entity.setTrxShare(Param.getBdWithDef(data, "share"));
			tR0006Entity.setTrxCurrId(Param.getStr(data, "curr"));	
			tR0006Entity.setTrxInsuredName(Param.getStr(data, "insuredName"));	
			tR0006Entity.setTrxPayMthd(Param.getStr(data, "paymentMethod"));
			tR0006Entity.setTrxOfficer(Param.getStr(t1Data, "officer"));
			tR0006Entity.setTrxDiretPy(Param.getStr(t1Data, "directPayment"));
			tR0006Entity.setTrxPpwCli(Param.getIntWithDef(t1Data, "ppw"));
			tR0006Entity.setTrxPeriodVld(Param.getIntWithDef(t1Data, "periodOfValidity"));
			tR0006Entity.setTrxRemarks(Param.getStr(t1Data, "remarks"));
			tR0006Entity.setTrxDataStatus(trxDataStatus);
			tR0006Entity.setTrxFeeClient(Param.getBdWithDef(data, "feeRate"));
			tR0006Entity.setTrxCurrRate(Param.getBdWithDef(data, "exchangeRate"));
			tR0006Entity.setTrxSumInsured(Param.getBdWithDef(data, "amount"));
			tR0006Entity.setTrxTsiAmount(Param.getBdWithDef(data, "totalSumInsured"));
			
			tR0006Entity.setTrxTreAdj("0");
			
			String user = Param.getStrWithDef(param, Param.USER);
			String createBy = Param.getStrWithDef(param, CREATE_BY);
			Date createOn = now;
			
			if(StringUtils.isNotBlank(createBy)) {	
				createOn = new Date(Param.getLong(param, "createOn"));
				
				tR0006Entity.setModifyBy(user);
				tR0006Entity.setModifyOn(now);
			}else {
				createBy = user;
			}
			
			tR0006Entity.setCreateBy(createBy);
			tR0006Entity.setCreateOn(createOn);
			tR0006Entity.setTrxDate(appDate);
			
			clientInfo.add(tR0006Entity);
			
			param.put("productionCurr", Param.getStr(data, "curr"));
		}
		
		if(clientInfo.size() > 0)
			tR0006Repo.saveAll(clientInfo);
	}

	@SuppressWarnings("unchecked")
	private void saveInterestInsured(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006ARepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		
		Map<String, Object> t2Data = (HashMap<String, Object>) param.get("t2Data");
		List<HashMap<String, Object>> t2DataTable = (ArrayList<HashMap<String, Object>>) t2Data.get("t2DataTable");

		Integer insSub = 1;
		Map<String, Object> mapInfoT2 = new HashMap<String, Object>();
		List<TR0006AEntity> interestInsured = new ArrayList<TR0006AEntity>();
		for (HashMap<String, Object> data : t2DataTable) {			
			TR0006AEntity tR0006AEntity = new TR0006AEntity();
			tR0006AEntity.setTrxClass(FAC);
			tR0006AEntity.setTrxTrxId(RQ);
			tR0006AEntity.setTrxVoucherId(voucherId);		
			tR0006AEntity.setTrxInsSub(insSub);
			tR0006AEntity.setTrxCoverCode(Param.getStr(t2Data,"typeOfCover"));
			tR0006AEntity.setTrxCoverClass(Param.getStr(t2Data, "classification"));
			tR0006AEntity.setTrxInsInsured(Param.getStr(data, "interestInsured"));
			tR0006AEntity.setTrxInsLocation(Param.getStr(data, "location"));
			tR0006AEntity.setTrxPremiumRate(Param.getBdWithDef(data, "premiumRate"));
			tR0006AEntity.setTrxWeightRate(Param.getBdWithDef(data, "weighted"));
			tR0006AEntity.setTrxCombined(Param.getStr(data, "combined"));
			tR0006AEntity.setTrxCurrId(Param.getStr(param, "productionCurr"));
			tR0006AEntity.setTrxCurrRate(Param.getBdWithDef(data, "exchangeRate"));
			tR0006AEntity.setTrxSumInsured(Param.getBdWithDef(data, "sumInsured"));
			tR0006AEntity.setTrxPremiumAmt(Param.getBdWithDef(data, "premiumAmount"));
			tR0006AEntity.setTrxInsStart(Param.getDate(data, "insuredPeriod"));
			tR0006AEntity.setTrxInsEnd(Param.getDate(data, "insuredPeriodTo"));
			tR0006AEntity.setTrxReinsStart(Param.getDate(data, "reinsuredPeriod"));
			tR0006AEntity.setTrxReinsEnd(Param.getDate(data, "reinsuredPeriodTo"));
			tR0006AEntity.setTrxRemarks(Param.getStr(data, "remarks"));
			tR0006AEntity.setTrxBfeeSell(Param.getBdWithDef(data, "bFee"));
			tR0006AEntity.setTrxDeducPct(Param.getBdWithDef(data, "deduction"));
			String trxTypeAd = Param.getStrWithDef(data,"endorsementType") == "" ? "A" : Param.getStrWithDef(data,"endorsementType");
			tR0006AEntity.setTrxTypeAd(trxTypeAd);

			interestInsured.add(tR0006AEntity);
			
			Map<String, Object> t2 = new HashMap<String, Object>();
			t2.put("insSub", insSub);
			t2.put("curr", Param.getStr(data, "curr"));
			t2.put("exchangeRate", Param.getBdWithDef(data, "exchangeRate"));
			mapInfoT2.put(Param.getStr(data, "id"), t2);
			
			insSub++;
		}
		param.put("mapInfoT2", mapInfoT2);
		
		if(interestInsured.size() > 0)
			tR0006ARepo.saveAll(interestInsured);
	}

	@SuppressWarnings("unchecked")
	private void saveCommOut(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006GRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		
		Map<String, Object> t3Data = (HashMap<String, Object>) param.get("t3Data");
		List<HashMap<String, Object>> t3DataTable = (ArrayList<HashMap<String, Object>>) t3Data.get("t3DataTable");

		List<TR0006GEntity> commOut = new ArrayList<TR0006GEntity>();
		for (HashMap<String, Object> data : t3DataTable) {
			TR0006GEntity tR0006GEntity = new TR0006GEntity();
			tR0006GEntity.setTrxClass(FAC);
			tR0006GEntity.setTrxTrxId(RQ);
			tR0006GEntity.setTrxVoucherId(voucherId);
			tR0006GEntity.setTrxSaCode(Param.getStr(data, "nameId"));
			tR0006GEntity.setTrxSaTaxId(Param.getStr(data, "taxId"));
			tR0006GEntity.setTrxCommPct(Param.getBdWithDef(data, "commission"));
			tR0006GEntity.setTrxCommAmt(Param.getBdWithDef(data, "commissionAmount"));

			commOut.add(tR0006GEntity);
		}
		
		if(commOut.size() > 0)
			tR0006GRepo.saveAll(commOut);
	}

	@SuppressWarnings("unchecked")
	private void saveReinsurance(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006BRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		
		Map<String, Object> t4Data = (HashMap<String, Object>) param.get("t4Data");
		List<HashMap<String, Object>> t4DataTable = (ArrayList<HashMap<String, Object>>) t4Data.get("t4DataTable");

		List<TR0006BEntity> reinsurance = new ArrayList<TR0006BEntity>();
		for (HashMap<String, Object> data : t4DataTable) {
			TR0006BEntity tR0006BEntity = new TR0006BEntity();
			tR0006BEntity.setTrxClass(FAC);
			tR0006BEntity.setTrxTrxId(RQ);
			tR0006BEntity.setTrxVoucherId(voucherId);
			tR0006BEntity.setTrxInsId(Param.getStr(data, "reinsuranceId"));
			tR0006BEntity.setTrxInsType(Param.getStr(data, "type"));
			tR0006BEntity.setTrxInsShare(Param.getBdWithDef(data, "share"));
			tR0006BEntity.setTrxCurrId(Param.getStr(param, "productionCurr"));
			tR0006BEntity.setTrxInsAmt(Param.getBdWithDef(data, "amount"));
			tR0006BEntity.setTrxPremAmt(Param.getBdWithDef(data, "premiumAmount"));
			tR0006BEntity.setTrxPremPortion(Param.getBdWithDef(data, "premiPortion"));
			tR0006BEntity.setTrxInsPaymethd(Param.getStr(data, "paymentId"));
			tR0006BEntity.setTrxInsRemarks(Param.getStr(data, "remarks"));
			tR0006BEntity.setTrxRicommAmt(Param.getBdWithDef(data, "bFeeAmount"));
			tR0006BEntity.setTrxInsBfee(Param.getBdWithDef(data, "bFee"));
			tR0006BEntity.setTrxInsPremium(Param.getBdWithDef(data, "premiumRate"));

			reinsurance.add(tR0006BEntity);
		}
		
		if(reinsurance.size() > 0)
			tR0006BRepo.saveAll(reinsurance);
	}

	@SuppressWarnings("unchecked")
	private void saveValue(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006CRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);

		Map<String, Object> t5Data = (HashMap<String, Object>) param.get("t5Data");
		List<HashMap<String, Object>> t5DataTable = (ArrayList<HashMap<String, Object>>) t5Data.get("t5DataTable");
	
		Map<String, Object> mapInfoT2 = (Map<String, Object>) param.get("mapInfoT2");
		List<TR0006CEntity> value = new ArrayList<TR0006CEntity>();
		for(HashMap<String, Object> data : t5DataTable) {
			Map<String, Object> t2 = (Map<String, Object>) mapInfoT2.get(Param.getStr(data, "id"));
			
			TR0006CEntity tR0006CEntity = new TR0006CEntity();
			tR0006CEntity.setTrxClass(FAC);
			tR0006CEntity.setTrxTrxId(RQ);
			tR0006CEntity.setTrxVoucherId(voucherId);
			tR0006CEntity.setTrxInsSub(Param.getInt(t2, "insSub"));
			tR0006CEntity.setTrxCurrId(Param.getStr(param, "productionCurr"));
			tR0006CEntity.setTrxCurrRate(Param.getBdWithDef(t2, "exchangeRate"));
			tR0006CEntity.setTrxSumInsured(Param.getBdWithDef(data, "sumInsured"));
			tR0006CEntity.setTrxPremiumBuy(Param.getBdWithDef(data, "premiumBuy"));
			tR0006CEntity.setTrxBfeeBuy(Param.getBdWithDef(data, "bfeeBuy"));
			tR0006CEntity.setTrxVatBuy(Param.getBdWithDef(data, "vatBuy"));
			tR0006CEntity.setTrxPremiumSell(Param.getBdWithDef(data, "premiumSell"));
			tR0006CEntity.setTrxBfeeSell(Param.getBdWithDef(data, "bfeeSell"));
			tR0006CEntity.setTrxVatSell(Param.getBdWithDef(data, "vatSell"));
			tR0006CEntity.setTrxDiscSell(Param.getBdWithDef(data, "discountSell"));
			
			tR0006CEntity.setTrxNetTou(Param.getBdWithDef(data, "totalRe"));
			tR0006CEntity.setTrxNetTtl(Param.getBdWithDef(data, "total"));

			value.add(tR0006CEntity);
		}
		
		if(value.size() > 0)
			tR0006CRepo.saveAll(value);
	}

	@SuppressWarnings("unchecked")
	private void saveTc(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		Date now = (Date) param.get(DATE);
		tR0006DRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);

		Map<String, Object> t6Data = (HashMap<String, Object>) param.get("t6Data");
		List<HashMap<String, Object>> t6DataTable = (ArrayList<HashMap<String, Object>>) t6Data.get("t6DataTable");

		List<TR0006DEntity> tc = new ArrayList<TR0006DEntity>();
		for(HashMap<String, Object> data : t6DataTable) {
			TR0006DEntity tR0006DEntity = new TR0006DEntity();
			tR0006DEntity.setTrxClass(FAC);
			tR0006DEntity.setTrxTrxId(RQ);
			tR0006DEntity.setTrxVoucherId(voucherId);
			tR0006DEntity.setTrxTcCode(Param.getStr(data, "tcGroup"));
			tR0006DEntity.setTrxTcData(Param.getStr(data, "tcDetails"));
			tR0006DEntity.setTrxInsDate(now);
			tc.add(tR0006DEntity);
		}
		if(Param.getStrWithDef(param, "isEndorsement").equals("true")) {
			String trxOldVoucherId = Param.getStr(param, "trxOldVoucherId");
			TR0006AEntity tR0006AEntity= tR0006ARepo.findByTrxVoucherId(trxOldVoucherId).get(0);
			String typeOfCover = tR0006AEntity.getTrxCoverCode();
			String mm = trxOldVoucherId.substring(2, 4);
			String yyyy = trxOldVoucherId.substring(4, 8);
			String no = trxOldVoucherId.substring(8, trxOldVoucherId.length());
			String tcData = typeOfCover + "/" + yyyy+ "/" + mm + "/" + no;
			
			TR0006DEntity tR0006DEntity = new TR0006DEntity();
			tR0006DEntity.setTrxClass(FAC);
			tR0006DEntity.setTrxTrxId(RQ);
			tR0006DEntity.setTrxVoucherId(voucherId);
			tR0006DEntity.setTrxTcCode("REFERENCE EOC");
			tR0006DEntity.setTrxTcData(tcData);
			tR0006DEntity.setTrxInsDate(now);

			tc.add(tR0006DEntity);
		}
		
		if(tc.size() > 0)
			tR0006DRepo.saveAll(tc);
	}

	@SuppressWarnings("unchecked")
	private void saveChecklist(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006ERepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		tR0006FRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);

		Map<String, Object> t7Data = (HashMap<String, Object>) param.get("t7Data");
		List<HashMap<String, Object>> t7DataTable1 = (ArrayList<HashMap<String, Object>>) t7Data.get("t7DataTable1");
		List<HashMap<String, Object>> t7DataTable2 = (ArrayList<HashMap<String, Object>>) t7Data.get("t7DataTable2");
		
		List<TR0006EEntity> payment = new ArrayList<TR0006EEntity>();
		for (HashMap<String, Object> data : t7DataTable1) {
			TR0006EEntity tR0006EEntity = new TR0006EEntity();
			tR0006EEntity.setTrxClass(FAC);
			tR0006EEntity.setTrxTrxId(RQ);
			tR0006EEntity.setTrxVoucherId(voucherId);
			
			tR0006EEntity.setTrxPrClient(Param.getStr(data, "typeId"));

			tR0006EEntity.setTrxPrDate(Param.getDate(data, "payDate"));
			tR0006EEntity.setTrxPrShare(Param.getBdWithDef(data, "portion"));
			tR0006EEntity.setTrxPrAmt(Param.getBdWithDef(data, "portionAmt"));

			payment.add(tR0006EEntity);
		}

		List<TR0006FEntity> document = new ArrayList<TR0006FEntity>();
		for (HashMap<String, Object> data : t7DataTable2) {
			TR0006FEntity tR0006FEntity = new TR0006FEntity();
			tR0006FEntity.setTrxClass(FAC);
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
	@SuppressWarnings("unchecked")
	public Object createReportHtml(Map<String, Object> param) throws Exception {
		String process = Param.getStr(param, PROCESS);
		
		if(PLACING.equals(process) || CLOSING_SLIP.equals(process)) {
			Map<String, Object> resultPlacing = (Map<String, Object>) createPlacing(param, HTML);
			
			List<String> report = new ArrayList<String>();
			List<String> reportPlacing = (List<String>) resultPlacing.get("report");
			report.addAll(reportPlacing);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("report", report);
			
			return result;
		}else if(QUOTATION.equals(process)) {
			Map<String, Object> resultEvidence = (Map<String, Object>) createEvidence(param, HTML);
			Map<String, Object> resultQuotation = (Map<String, Object>) createQuotation(param, HTML);
			
			List<String> report = new ArrayList<String>();
			List<String> reportEvidence = (List<String>) resultEvidence.get("report");
			List<String> reportQuotation = (List<String>) resultQuotation.get("report");
			report.addAll(reportEvidence);
			report.addAll(reportQuotation);
			
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("report", report);
			return result;
		}
			
		return null;
	}

	@Override
	public Object createReportPdf(Map<String, Object> param) throws Exception {
		String process = Param.getStr(param, PROCESS);
		
		if(PLACING.equals(process) || CLOSING_SLIP.equals(process))
			return createPlacing(param, PDF);
		else if(EVIDENCE.equals(process) || EVIDENCE_OF_COVER.equals(process))
			return createEvidence(param, PDF);
		else if(QUOTATION.equals(process))
			return createQuotation(param, PDF);
			
		return null;
	}

	@Override
	public Object createReportDoc(Map<String, Object> param) throws Exception {
		String process = Param.getStr(param, PROCESS);
		
		if(PLACING.equals(process) || CLOSING_SLIP.equals(process))
			return createPlacing(param, DOC);
		else if(EVIDENCE.equals(process) || EVIDENCE_OF_COVER.equals(process))
			return createEvidence(param, DOC);
		else if(QUOTATION.equals(process))
			return createQuotation(param, DOC);
			
		return null;
	}
	
	private Object createPlacing(Map<String, Object> param, String type) throws Exception {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String document = Param.getStr(param, DOCUMENT);
		
		Map<String, Object> result = new HashMap<String, Object>();
		String appDate1 = mA0014Repo.getAppDateReportProduction1();
		
		String temp[] = appDate1.split(" ");
		String appDate = temp[0].substring(0, temp[0].length()-2)
						+ "<sup>" 
						+ temp[0].substring(temp[0].length()-2, temp[0].length())
						+ "</sup>"
						+ " " + temp[1] + " " + temp[2];
		
		List<TR0006AEntity> interestEntities = tR0006ARepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		String typeOfCover = interestEntities.get(0).getTrxCoverCode();
		String dd = voucherId.substring(0, 2);
		String mm = voucherId.substring(2, 4);
		String yyyy = voucherId.substring(4, 8);
		String no = voucherId.substring(8, voucherId.length());
		String slipNo = typeOfCover + "/" + yyyy+ "/" + mm + "/" + dd + no;
		
		String code = Param.getStr(param, "code");
		List<TR0006BEntity> reinsEntities;
		if(StringUtils.isNotBlank(code)) 
			reinsEntities = tR0006BRepo.findByTrxTrxIdAndTrxVoucherIdAndTrxInsId(RQ, voucherId, code);
		else
			reinsEntities = tR0006BRepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		Map<String, Object> paramPlacing = new HashMap<String, Object>();
		paramPlacing.put("P_DATE", appDate);
		paramPlacing.put("P_SLIP_NO", slipNo);
		paramPlacing.put("P_VOUCHER_ID", voucherId);
		paramPlacing.put("P_DATE2", appDate);
		String process = Param.getStr(param, PROCESS);
		if(CLOSING_SLIP.equals(process))
			paramPlacing.put("P_TITLE", "Closing Slip");
		
		String status = tR0006Repo.findTrxDataStatusByTrxIdAndTrxVoucherId(RQ, voucherId);
		List<String> report = new ArrayList<String>();
		for(TR0006BEntity reins : reinsEntities) {
			MA0005Entity ma0005 = mA0005Repo.findByCliCode(reins.getTrxInsId());
			String name = ma0005.getCliName();
			
			paramPlacing.put("P_CLIENT_NAME", name);
			paramPlacing.put("P_CLIENT", reins.getTrxInsId());
			paramPlacing.put("P_REINSURENCE", reins.getTrxInsId());
			paramPlacing.put("P_REINS_CODE", reins.getTrxInsId());
			paramPlacing.put("P_REINS_ID", "11".equals(status) ? reins.getTrxInsId() : reins.getTrxInsId());
			
			String reportName = null;
			String coverName = null;
			if(type.equals(PDF)) {
				if(COVER.equals(document)) {
					coverName = reportUtils.exportPdf("Cover.jrxml", paramPlacing);
				}else if(PLACING.equals(document) || CLOSING_SLIP.equals(document)) {
					reportName = reportUtils.exportPdf("PlacingSlip.jrxml", paramPlacing);
				}else {
					reportName = reportUtils.exportPdf("PlacingSlip.jrxml", paramPlacing);
				}
			}else if(type.equals(DOC)) {
				if(COVER.equals(document)) {
					coverName = reportUtils.exportDocx("Cover.jrxml", paramPlacing);
				}else if(PLACING.equals(document) || CLOSING_SLIP.equals(document)) {
					reportName = reportUtils.exportDocx("PlacingSlip.jrxml", paramPlacing);
				}else {
					reportName = reportUtils.exportDocx("PlacingSlip.jrxml", paramPlacing);
				}
			}else {
				if(COVER.equals(document)) {
					coverName = reportUtils.exportHtml("CoverHtml.jrxml", paramPlacing);
				}else if(PLACING.equals(document) || CLOSING_SLIP.equals(document)) {
					reportName = reportUtils.exportHtml("PlacingSlipHtml.jrxml", paramPlacing);
				}else {
					reportName = reportUtils.exportHtml("PlacingSlipHtml.jrxml", paramPlacing);
				}
			}
			
			if(coverName != null)
				report.add(coverName);
			if(reportName != null)
				report.add(reportName);
		}
		result.put("report", report);
		
		return result;
	}
	
	private Object createEvidence(Map<String, Object> param, String type) throws Exception {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String document = Param.getStr(param, DOCUMENT);
		
		Map<String, Object> result = new HashMap<String, Object>();
		String appDate1 = mA0014Repo.getAppDateReportProduction1();
		
		String temp[] = appDate1.split(" ");
		String appDate = temp[0].substring(0, temp[0].length()-2)
						+ "<sup>" 
						+ temp[0].substring(temp[0].length()-2, temp[0].length())
						+ "</sup>"
						+ " " + temp[1] + " " + temp[2];
		
		List<TR0006AEntity> interestEntities = tR0006ARepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		String typeOfCover = interestEntities.get(0).getTrxCoverCode();
		String dd = voucherId.substring(0, 2);
		String mm = voucherId.substring(2, 4);
		String yyyy = voucherId.substring(4, 8);
		String no = voucherId.substring(8, voucherId.length());
		String slipNo = typeOfCover + "/" + yyyy+ "/" + mm + "/" + dd + no;
		
		String code = Param.getStr(param, "code");
		List<TR0006Entity> clientEntities;
		if(StringUtils.isNotBlank(code))
			clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherIdAndTrxClient(RQ, voucherId, code);
		else
			clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		Map<String, Object> paramPlacing = new HashMap<String, Object>();
		paramPlacing.put("P_DATE", appDate);
		paramPlacing.put("P_SLIP_NO", slipNo);
		paramPlacing.put("P_VOUCHER_ID", voucherId);
		paramPlacing.put("P_DATE2", appDate);
		
		String process = Param.getStr(param, PROCESS);
		if(EVIDENCE_OF_COVER.equals(process))
			paramPlacing.put("P_TITLE", "Evidence Of Cover");
		
		List<String> report = new ArrayList<String>();
		for(TR0006Entity client : clientEntities) {
			MA0005Entity ma0005 = mA0005Repo.findByCliCode(client.getTrxClient());
			String name = ma0005.getCliName();
			
			paramPlacing.put("P_CLIENT_NAME", name);
			paramPlacing.put("P_CLIENT", client.getTrxClient());
			
			String reportName = null;
			String coverName = null;
			if(type.equals(PDF)) {
				if(COVER.equals(document)) {
					coverName = reportUtils.exportPdf("Cover.jrxml", paramPlacing);
				}else if(EVIDENCE.equals(document) || EVIDENCE_OF_COVER.equals(process)) {
					reportName = reportUtils.exportPdf("Evidence.jrxml", paramPlacing);
				}else {
					reportName = reportUtils.exportPdf("Evidence.jrxml", paramPlacing);
				}
			}else if(type.equals(DOC)) {
				if(COVER.equals(document)) {
					coverName = reportUtils.exportDocx("Cover.jrxml", paramPlacing);
				}else if(EVIDENCE.equals(document) || EVIDENCE_OF_COVER.equals(process)) {
					reportName = reportUtils.exportDocx("Evidence.jrxml", paramPlacing);
				}else {
					reportName = reportUtils.exportDocx("Evidence.jrxml", paramPlacing);
				}
			}else {
				if(COVER.equals(document)) {
					coverName = reportUtils.exportHtml("CoverHtml.jrxml", paramPlacing);
				}else if(EVIDENCE.equals(document) || EVIDENCE_OF_COVER.equals(process)) {
					reportName = reportUtils.exportHtml("EvidenceHtml.jrxml", paramPlacing);
				}else {
					reportName = reportUtils.exportHtml("EvidenceHtml.jrxml", paramPlacing);
				}
			}
			
			if(coverName != null)
				report.add(coverName);
			if(reportName != null)
				report.add(reportName);
		}
		result.put("report", report);
		
		return result;
	}
	
	private Object createQuotation(Map<String, Object> param, String type) throws Exception {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		Map<String, Object> result = new HashMap<String, Object>();
		String appDate1 = mA0014Repo.getAppDateReportProduction1();
		
		String temp[] = appDate1.split(" ");
		String appDate = temp[0].substring(0, temp[0].length()-2)
						+ "<sup>" 
						+ temp[0].substring(temp[0].length()-2, temp[0].length())
						+ "</sup>"
						+ " " + temp[1] + " " + temp[2];
		
		List<TR0006AEntity> interestEntities = tR0006ARepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		String typeOfCover = interestEntities.get(0).getTrxCoverCode();
		String dd = voucherId.substring(0, 2);
		String mm = voucherId.substring(2, 4);
		String yyyy = voucherId.substring(4, 8);
		String no = voucherId.substring(8, voucherId.length());
		String slipNo = typeOfCover + "/" + yyyy+ "/" + mm + "/" + dd + no;
		
		String code = Param.getStr(param, "code");
		List<TR0006Entity> clientEntities;
		if(StringUtils.isNotBlank(code))
			clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherIdAndTrxClient(RQ, voucherId, code);
		else
			clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		Map<String, Object> paramQuotation = new HashMap<String, Object>();
		paramQuotation.put("P_DATE", appDate);
		paramQuotation.put("P_SLIP_NO", slipNo);
		paramQuotation.put("P_VOUCHER_ID", voucherId);
		paramQuotation.put("P_DATE2", appDate);
		
		List<String> report = new ArrayList<String>();
		for(TR0006Entity client : clientEntities) {			
			paramQuotation.put("P_CLIENT", client.getTrxClient());
			
			String reportName = null;
			if(type.equals(PDF))
				reportName = reportUtils.exportPdf("Quotation.jrxml", paramQuotation);
			else if(type.equals(DOC))
				reportName = reportUtils.exportDocx("Quotation.jrxml", paramQuotation);
			else 
				reportName = reportUtils.exportHtml("QuotationHtml.jrxml", paramQuotation);
			
			if(reportName != null)
				report.add(reportName);
		}
		result.put("report", report);
		
		return result;
	}

	@Override
	public Object inquirySend(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String process = Param.getStr(param, PROCESS);		

		List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
		if(PLACING.equals(process)) {
			String action;
		
			List<TR0006BEntity> reinsEntities = tR0006BRepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
			for(TR0006BEntity reins : reinsEntities) {
				MA0005Entity ma0005 = mA0005Repo.findByCliCode(reins.getTrxInsId());
				
				Map<String, Object> reportObj = new HashMap<String, Object>();
				reportObj.put("name", ma0005.getCliName());
				reportObj.put("document", "Draft Placing Slip");
				
				action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + voucherId + "', '" + reins.getTrxInsId() + "', 'placing', 'placing')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + voucherId + "', '" + reins.getTrxInsId() + "', 'placing', 'placing', 'Draft Placing Slip - " + ma0005.getCliName() + "')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				reportObj.put("action", action);
				table.add(reportObj);
			}
		}else if(QUOTATION.equals(process)) {
			String action;
			
			List<TR0006Entity> clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
			for(TR0006Entity client : clientEntities) {
				MA0005Entity ma0005 = mA0005Repo.findByCliCode(client.getTrxClient());
				
				Map<String, Object> reportObj = new HashMap<String, Object>();
				reportObj.put("name", ma0005.getCliName());
				reportObj.put("document", "Quotation");
				
				action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + voucherId + "', '" + client.getTrxClient() + "', 'evidence', 'evidence')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + voucherId + "', '" + client.getTrxClient() + "', 'evidence', 'evidence', 'Quotation - " + ma0005.getCliName() + "')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				reportObj.put("action", action);
				table.add(reportObj);
			}
			
			for(TR0006Entity client : clientEntities) {
				MA0005Entity ma0005 = mA0005Repo.findByCliCode(client.getTrxClient());
				
				Map<String, Object> reportObj = new HashMap<String, Object>();
				reportObj.put("name", ma0005.getCliName());
				reportObj.put("document", "Summary Quotation");
				
				action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + voucherId + "', '" + client.getTrxClient() + "', 'quotation', 'quotation')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + voucherId + "', '" + client.getTrxClient() + "', 'quotation', 'quotation', 'Summary Quotation - " + ma0005.getCliName() + "')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				reportObj.put("action", action);
				table.add(reportObj);
			}
		}
		
		return table;
	}

	@Override
	public Object sendEmail(Map<String, Object> param) throws Exception {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String process = Param.getStr(param, PROCESS);
		String remarks = Param.getStr(param, REMARKS);
		
		String trxDataStatus = null;
		Map<String, Object> result = new HashMap<String, Object>();
		String appDate1 = mA0014Repo.getAppDateReportProduction1();
		
		String temp[] = appDate1.split(" ");
		String appDate = temp[0].substring(0, temp[0].length()-2)
						+ "<sup>" 
						+ temp[0].substring(temp[0].length()-2, temp[0].length())
						+ "</sup>"
						+ " " + temp[1] + " " + temp[2];
		
		List<TR0006AEntity> interestEntities = tR0006ARepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		String typeOfCover = interestEntities.get(0).getTrxCoverCode();
		String dd = voucherId.substring(0, 2);
		String mm = voucherId.substring(2, 4);
		String yyyy = voucherId.substring(4, 8);
		String no = voucherId.substring(8, voucherId.length());
		String slipNo = typeOfCover + "/" + yyyy+ "/" + mm + "/" + dd + no;
		
		Map<String, Object> paramPlacing = new HashMap<String, Object>();
		paramPlacing.put("P_DATE", appDate);
		paramPlacing.put("P_SLIP_NO", slipNo);
		paramPlacing.put("P_VOUCHER_ID", voucherId);
		paramPlacing.put("P_DATE2", appDate);
		
		JavaMailSender sender = emailUtils.getMailSender();
		if(PLACING.equals(process)) {			
			List<TR0006BEntity> reinsEntities = tR0006BRepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
			for(TR0006BEntity reins : reinsEntities) {
				MA0005Entity ma0005 = mA0005Repo.findByCliCode(reins.getTrxInsId());
				String name = ma0005.getCliName();
				
				paramPlacing.put("P_CLIENT_NAME", name);
				paramPlacing.put("P_CLIENT", reins.getTrxInsId());
				paramPlacing.put("P_REINSURENCE", reins.getTrxInsId());
				
				String report = reportUtils.exportPdf("PlacingSlip.jrxml", paramPlacing);
				
				MimeMessage msg = sender.createMimeMessage();
		        MimeMessageHelper helper = emailUtils.getMimeMessageHelper(msg);
				
		        helper.setTo(ma0005.getCliEmail());
		        helper.setSubject("Placing Slip No : " + voucherId);
		        helper.setText("", true);
		
		        File file;
		        file = new File(report);
		        helper.addAttachment("Draft Placing Slip.pdf", file);
		
		        sender.send(msg);
			}
			
			trxDataStatus = "3";
			tR0006Repo.updateTrxDataStatus(trxDataStatus, remarks, RQ, voucherId);
		}else if(QUOTATION.equals(process)) {
			List<TR0006Entity> clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
			
			for(TR0006Entity client : clientEntities) {
				MA0005Entity ma0005 = mA0005Repo.findByCliCode(client.getTrxClient());
				String name = ma0005.getCliName();
				
				paramPlacing.put("P_CLIENT_NAME", name);
				paramPlacing.put("P_CLIENT", client.getTrxClient());
				
				String reportEvidence = reportUtils.exportPdf("Evidence.jrxml", paramPlacing);
				String reportQuo = reportUtils.exportPdf("Quotation.jrxml", paramPlacing);
				String reportQuoHtml = reportUtils.exportHtml("QuotationHtml.jrxml", paramPlacing);
				
				MimeMessage msg = sender.createMimeMessage();
		        MimeMessageHelper helper = emailUtils.getMimeMessageHelper(msg);
				
		        helper.setTo(ma0005.getCliEmail());
		        helper.setSubject("Quotation No : " + voucherId);
		        
		        Path path = Paths.get(reportQuoHtml);
				String content = new String(Files.readAllBytes(path));
				content = content.replaceAll("font-size:", "x-size:")
						.replaceAll("SansSerif", "Arial")
						.replaceAll("COMPANY_LOGO", "");
		        helper.setText(content, true);
		
		        File fileEvidence = new File(reportEvidence);
		        helper.addAttachment("Quotation.pdf", fileEvidence);
		        
		        File fileQuotation = new File(reportQuo);
		        helper.addAttachment("Summary Quotation.pdf", fileQuotation);
		
		        sender.send(msg);
			}
			
			trxDataStatus = "5";
			tR0006Repo.updateTrxDataStatus(trxDataStatus, remarks, RQ, voucherId);
		}
		
		result.put("trxDataStatus", trxDataStatus);
		return result;
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Object doClosing(Map<String, Object> param) throws Exception {
		Long start = System.currentTimeMillis();
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);	
		logger.info("Start - closing with trxVoucherId : '{}'.", trxVoucherId);	
		
		List<TR0006AEntity> interestInsured = tR0006ARepo.findByTrxTrxIdAndTrxVoucherId(RQ, trxVoucherId);
		param.put(CLOSING_PARAM.INTEREST_INSURED_LIST, interestInsured);
		
		List<TR0006Entity> tClientInformation = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, trxVoucherId);
		param.put(CLOSING_PARAM.CLIENT_LIST, tClientInformation);
		
		List<TR0006BEntity> tReinsurance = tR0006BRepo.findByTrxTrxIdAndTrxVoucherId(RQ, trxVoucherId);
		param.put(CLOSING_PARAM.REINSURANCE_LIST, tReinsurance);
		
		List<TR0006GEntity> tCommOut = tR0006GRepo.findByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
		param.put(CLOSING_PARAM.COMMOUT_LIST, tCommOut);
		
		List<TR0006EEntity> tChecklistCli = tR0006ERepo.findByChecklistClient(RQ, trxVoucherId);
		param.put(CLOSING_PARAM.PAYMENT_METHOD_CLI_LIST, tChecklistCli);
		
		List<TR0006EEntity> tChecklistReins = tR0006ERepo.findByChecklistReins(RQ, trxVoucherId);
		param.put(CLOSING_PARAM.PAYMENT_METHOD_REINS_LIST, tChecklistReins);
		
		List<TR0006CEntity> tValue = tR0006CRepo.findByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
		param.put(CLOSING_PARAM.VALUES_LIST, tValue);
		
		param.put(CLOSING_PARAM.IS_CLOSING, Boolean.TRUE);
		
		Object result = null;
		synchronized (param) {
			result = closing(param);
		}
		
		logger.info("End - closing with trxVoucherId : '{}', elapsed time : {}ms.", trxVoucherId, System.currentTimeMillis()-start);
		return result;
	}

	@Override
	@Transactional(rollbackOn = Exception.class)
	@SuppressWarnings("unchecked")
	public Object closing(Map<String, Object> param) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);	
		Boolean isClosing =  Param.getBoolean(param, CLOSING_PARAM.IS_CLOSING);
		String extDesc = Param.getStr(param, EXTENSION.DESC);
		logger.info("extDesc : {}", extDesc);
		
		Boolean isExtension = false;
		if(extDesc != null)
			isExtension = true;

		List<Map<String, Object>> tableAll = new ArrayList<Map<String, Object>>();
		Map<String, List<Map<String, Object>>> mapTable = new LinkedHashMap<String, List<Map<String,Object>>>();
		
		Calendar calNow = Calendar.getInstance();
		
		int j = 1;
		List<TR0006AEntity> interestI = (List<TR0006AEntity>) param.get(CLOSING_PARAM.INTEREST_INSURED_LIST);
		List<TR0006CEntity> tValues = (List<TR0006CEntity>) param.get(CLOSING_PARAM.VALUES_LIST);
		for(TR0006CEntity values : tValues) {
			Date now = calNow.getTime();
			logger.info("now : {}", sdfDateTime.format(now));
			
			//initialize map value
			BigDecimal grossValue = BigDecimal.ZERO;
			BigDecimal grossBfValue = BigDecimal.ZERO;
			BigDecimal netTouValue = BigDecimal.ZERO;
			BigDecimal premiumValue = BigDecimal.ZERO;
			BigDecimal deducValue = BigDecimal.ZERO;
			BigDecimal discValue = BigDecimal.ZERO;
			BigDecimal netTtlValue = BigDecimal.ZERO;
			BigDecimal brkrFeeValue = BigDecimal.ZERO;
			BigDecimal taxinBfValue = BigDecimal.ZERO;
			BigDecimal commOutValue = BigDecimal.ZERO;
			
			grossValue = grossValue.add(values.getTrxPremiumBuy());
			grossBfValue = grossBfValue.add(values.getTrxBfeeBuy());
			premiumValue = premiumValue.add(values.getTrxPremiumSell());
			deducValue = deducValue.add(values.getTrxBfeeSell());
			discValue = discValue.add(values.getTrxDiscSell());
			taxinBfValue = taxinBfValue.add(values.getTrxVatBuy());
			
			netTouValue = netTouValue.add(values.getTrxNetTou());
			netTtlValue = netTtlValue.add(values.getTrxNetTtl());
			brkrFeeValue = netTtlValue.subtract(netTouValue).subtract(taxinBfValue);
			
			List<TR0006GEntity> tCommOut = (List<TR0006GEntity>) param.get(CLOSING_PARAM.COMMOUT_LIST);
			for(TR0006GEntity commOut : tCommOut) {
				BigDecimal commPct = commOut.getTrxCommPct().divide(BD_100);
				BigDecimal valueCommOut = netTtlValue.multiply(commPct);
				
				if(valueCommOut.compareTo(BigDecimal.ZERO) == 0)
					valueCommOut = commOut.getTrxCommAmt();
				
				commOutValue = commOutValue.add(valueCommOut);
			}
			
			Map<String, BigDecimal> mapValue = new HashMap<String, BigDecimal>();
			mapValue.put(GROSS_VALUE, grossValue);
			mapValue.put(GROSS_BF_VALUE, grossBfValue);
			mapValue.put(NET_TOU_VALUE, netTouValue);
			mapValue.put(PREMIUM_VALUE, premiumValue);
			mapValue.put(DEDUC_VALUE, deducValue);
			mapValue.put(DISC_VALUE, discValue);
			mapValue.put(GROSS_TTL_VALUE, netTtlValue);
			mapValue.put(BRKR_FEE_VALUE, brkrFeeValue);
			mapValue.put(TAXIN_BF_VALUE, taxinBfValue);
			mapValue.put(COMM_OUT_VALUE, commOutValue);
			
			List<TR0006Entity> tClientInformation = (List<TR0006Entity>) param.get(CLOSING_PARAM.CLIENT_LIST);
			List<TR0006EEntity> tChecklistCli = (List<TR0006EEntity>) param.get(CLOSING_PARAM.PAYMENT_METHOD_CLI_LIST);
			List<TR0006EEntity> tChecklistReins = (List<TR0006EEntity>) param.get(CLOSING_PARAM.PAYMENT_METHOD_REINS_LIST);
	
			Date appDate = Param.getDate(param, "transactionDate");
			
			Calendar calTrx = Calendar.getInstance();
			calTrx.setTime(appDate);
			Integer month = calTrx.get(Calendar.MONTH)+1;
			Integer year = calTrx.get(Calendar.YEAR);
			
			TR0006AEntity interest = interestI.stream()
					.filter(i -> i.getTrxInsSub() == values.getTrxInsSub())
					.findFirst().orElse(null);
			
			String typeOfCover = interest.getTrxCoverCode();
			String remarksII = interest.getTrxRemarks();
			String insuredNameCI = tR0006Repo.findTrxInsuredNameByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			
			BigDecimal period = interest.getDiffDaysInsured();
			logger.info("period interest : {}", period);
			
			BigDecimal taxRate003 = common.getTaxRateh003();
			BigDecimal taxRate009 = common.getTaxRateh009();
			
			BigDecimal shareTotalCli = tR0006Repo.getSummaryTrxShareByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			if(shareTotalCli.compareTo(BD_100) > 0)
				shareTotalCli = BD_100;
			if(tClientInformation != null){
			for(TR0006Entity clientInformation : tClientInformation) {
				MA0005Entity client = mA0005Repo.findByCliCode(clientInformation.getTrxClient());
				String descCli = "RQ " + trxVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + interest.getTrxInsInsured() + ", " + remarksII;
				
	//			BigDecimal shareRateCli = clientInformation.getTrxShare().divide(shareTotalCli, 2, RoundingMode.HALF_UP);
				BigDecimal shareRateCli = BigDecimal.ONE;
				MA0015AEntity exchangeRateCli = mA0015ARepo.findByExDateAndExCurrId(appDate, clientInformation.getTrxCurrId());
				
				String brCodeCli = "CTRX" + clientInformation.getTrxCurrId();
				List<MA0018Entity> businessRuleCli = mA0018Repo.findByBrCode(brCodeCli);
				
				BigDecimal portion = clientInformation.getTrxShare();
				
				portion = portion.divide(BD_100);
				
//				BigDecimal premiCli = BigDecimal.ZERO;
//				
//				premiCli = interest.getTrxSumInsured()
//									.multiply(portion)
//									.multiply(premiRateCli)
//									.multiply(weigth)
//									.multiply(period);
//				
//				BigDecimal deducCli = premiCli.multiply(deducRateCli);
//				BigDecimal netRcCli = premiCli.subtract(deducCli).subtract(discValue);

				BigDecimal premiCli = premiumValue;
				BigDecimal deducCli = deducValue;
				BigDecimal netRcCli = netTtlValue;
				
				if(isExtension){
					//Premi dari Upload Extension v2
					premiCli = Param.getBdWithDef(param, "premiumValueClient");
					netRcCli = premiCli;
				}
				
				logger.info("***** PREMI CLIENT : {}", printCurr(premiCli));
				
				mapValue.put(DEDUC_VALUE, deducCli);
				mapValue.put(PREMIUM_VALUE, netRcCli);
				
				List<TR0006EEntity> newChecklistClient = new ArrayList<TR0006EEntity>();
				for(TR0006EEntity t6e : tChecklistCli) {
					if(t6e.getTrxPrClient().equals(clientInformation.getTrxClient()))
						newChecklistClient.add(t6e);	//update production
				}
				
				if(newChecklistClient.size() == 0)
					newChecklistClient = tChecklistCli;
				
				List<TR0001Entity> tr1Entities = new ArrayList<TR0001Entity>();
				List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
				for(TR0006EEntity payMthdCli : newChecklistClient) {
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
					tr1.setGlTrxClient(clientInformation.getTrxClient());
					
					BigDecimal shareRatePrCli = payMthdCli.getTrxPrShare().divide(BD_100);
					BigDecimal netTtlValueCliPrCli = payMthdCli.getTrxPrAmt();
					if(netTtlValueCliPrCli.compareTo(BigDecimal.ZERO) == 0)
						netTtlValueCliPrCli = netRcCli.multiply(shareRateCli).multiply(shareRatePrCli);
					netTtlValueCliPrCli = netTtlValueCliPrCli.setScale(2, RoundingMode.HALF_UP);
					
					BigDecimal prRate = BigDecimal.ZERO;
					if(payMthdCli.getTrxPrAmt().compareTo(BigDecimal.ZERO) > 0)
						prRate = payMthdCli.getTrxPrAmt().divide(netRcCli.multiply(shareRateCli), 2, RoundingMode.HALF_UP);
					else
						prRate = shareRatePrCli;
					tr1.setPrRate(prRate);
					
					tr1.setGlTrxDesc(descCli + " - " + netTtlValueCliPrCli);
					tr1.setGlTrxStatus("0");
					tr1.setGlDataStatus("11");
					tr1.setCreateOn(now);
					tr1.setCreateBy(param.get(Param.USER).toString());
					tr1.setModifyOn(now);
					tr1.setModifyBy(param.get(Param.USER).toString());
					if(param.get(EXTENSION.DESC) != null){
						tr1.setGlTrxClientDesc(extDesc);
						tr1.setGlTrxDesc(extDesc);
					}
					
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
						BigDecimal org = value.multiply(shareRateCli).multiply(shareRatePrCli);
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
				
				TR0006BEntity tr0006b = tR0006BRepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(RQ, trxVoucherId, "0");
				
				//get lead reins
				BigDecimal netTtlValueCli = netRcCli.multiply(shareRateCli).setScale(10, RoundingMode.HALF_UP);
				String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				TR0003Entity tr3 = new TR0003Entity();
				tr3.setTrxType(RQ);
				tr3.setTrxVoucherId(voucherId);
				tr3.setTrxDescription(descCli + " - " + netTtlValueCli);
				tr3.setTrxClient(clientInformation.getTrxClient());
				tr3.setTrxDate(calTrx.getTime());
				tr3.setTrxAssured(tr0006b.getTrxInsId());
				tr3.setTrxCoverCode(typeOfCover);
				tr3.setTrxInsOfficer(clientInformation.getTrxOfficer());
				tr3.setTrxInsInsured(descCli + " - " + netTtlValueCli); 
				tr3.setTrxInsStart(interest.getTrxInsStart());
				tr3.setTrxInsEnd(interest.getTrxInsEnd());
				tr3.setTrxCurrId(clientInformation.getTrxCurrId());
				tr3.setTrxCurrRate(exchangeRateCli.getExKmkRateBd());
				logger.info("***** NET TOTAL VAL CLI : {}", printCurr(netTtlValueCli));
				tr3.setTrxAmountDue(netTtlValueCli); 
				tr3.setTrxOldVoucherId(trxVoucherId);
				tr3.setTrxStatusData("11");
				tr3.setCreateOn(now);
				tr3.setCreateBy(param.get(Param.USER).toString());
				if(param.get(EXTENSION.DESC) != null){
					tr3.setTrxDescription(extDesc);
					tr3.setTrxInsInsured(extDesc);
				}
				
				List<Map<String, Object>> table = mapTable.get(clientInformation.getTrxClient());
				if(table == null)
					table = new ArrayList<Map<String,Object>>();
				
				Map<String, Object> coverObj = new HashMap<String, Object>();
				coverObj.put("name", client.getCliName());
				coverObj.put("document", "Cover - Client");
				
				String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'evidence', 'cover')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'evidence', 'cover', 'Cover - " + client.getCliName() + "')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				coverObj.put("action", action);
				
				if(j == 1) {
					if(isClosing)
						table.add(coverObj);
				}
				
				Map<String, Object> evidenceObj = new HashMap<String, Object>();
				evidenceObj.put("name", client.getCliName());
				evidenceObj.put("document", "Evidence of Cover");
				
				action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'evidenceofcover', 'evidenceofcover')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'evidenceofcover', 'evidenceofcover', 'Evidence of Cover " + client.getCliName() + "')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				evidenceObj.put("action", action);
				
				if(j == 1) {
					if(isClosing)
						table.add(evidenceObj);
				}
				
				Map<String, Object> journal = new HashMap<String, Object>();
				journal.put("voucherId", tr3.getTrxVoucherId());
				journal.put("name", client.getCliName());
				journal.put("document", "Closing - DN");
				journal.put("insSub", interest.getTrxInsSub());
				journal.put("type", "DEBIT NOTE");
				journal.put("isComm", Boolean.FALSE);
				
				String fn = "Closing - DN - " + client.getCliName();
				
				action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', 'DEBIT NOTE')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', '" + fn + "', 'DEBIT NOTE')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				journal.put("action", action);
				table.add(journal);
				
				mapTable.put(clientInformation.getTrxClient(), table);
				
				int noRow = 1;
				List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
				for(TR0006EEntity payMthdCli : newChecklistClient) {
					TR0003AEntity tr3a = new TR0003AEntity();
					tr3a.setTrxType(tr3.getTrxType());
					tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
					tr3a.setTrxNoRow(noRow);
					
					tr3a.setTrxDueDate(payMthdCli.getTrxPrDate());
					tr3a.setTrxRemarks("Instalment "+noRow);
					
					BigDecimal shareRatePrCli = payMthdCli.getTrxPrShare().divide(BD_100);
					tr3a.setTrxDueAmount(netTtlValueCli.multiply(shareRatePrCli));
					
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
					
					tr12.setTrxCoverCode(typeOfCover);
					tr12.setTrxCountInv(count);
					tr12.setTrxDataStatus("11");
					tr12.setTrxClient(clientInformation.getTrxClient());
					tr12.setTrxDescription(tr1.getGlTrxDesc());
					tr12.setTrxCurrId(clientInformation.getTrxCurrId());
					tr12.setTrxCurrRate(exchangeRateCli.getExKmkRateBd());
					
					tr12.setTrxInvcAmount(netRcCli.multiply(shareRateCli).multiply(tr1.getPrRate()));
					tr12.setTrxOrgAmount(netRcCli.multiply(shareRateCli).multiply(tr1.getPrRate()));
					tr12.setTrxDiscAmount(discValue.multiply(shareRateCli).multiply(tr1.getPrRate()));
					tr12.setTrxDeducAmount(deducCli.multiply(shareRateCli).multiply(tr1.getPrRate()));
					tr12.setTrxNetTou(netTouValue);
					tr12.setTrxNetTtl(netTtlValue);
					
					tr12.setTrxInsOfficer(clientInformation.getTrxOfficer());
					tr12.setTrxOldType(RQ);
					tr12.setTrxOldVoucherId(trxVoucherId);
					tr12.setCreateOn(now);
					tr12.setCreateBy(param.get(Param.USER).toString());
					tr12.setModifyOn(now);
					tr12.setModifyBy(param.get(Param.USER).toString());
					
					if(param.get(EXTENSION.DESC) != null){
						tr12.setTrxDescription(extDesc);
					}
					
					tr12Entities.add(tr12);
				
					count++;
				}
				
				tr0001Repo.saveAll(tr1Entities);
				tr0002Repo.saveAll(tr2Entities);
				tr0003Repo.save(tr3);
				tr0003ARepo.saveAll(tr3aEntities);
				tr0012Repo.saveAll(tr12Entities);
			}
			}
			
			BigDecimal shareTotalReins = tR0006BRepo.getSummaryTrxInsShareByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			if(shareTotalReins.compareTo(BD_100) > 0)
				shareTotalReins = BD_100;
			List<TR0006BEntity> tReinsurance = (List<TR0006BEntity>) param.get(CLOSING_PARAM.REINSURANCE_LIST);
			if(tReinsurance != null){
			for(TR0006BEntity reins :tReinsurance) {
				TR0006Entity tr0006 = tR0006Repo.findOneClientByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
				MA0005Entity client = mA0005Repo.findByCliCode(reins.getTrxInsId());
				String descReins = "RQ " + trxVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + interest.getTrxInsInsured() + ", " + remarksII;
				
				BigDecimal reinsPortion = BigDecimal.ONE;
				BigDecimal shareRateReins = BigDecimal.ONE;
				if(reins.getTrxPremPortion() != null) {
					if(reins.getTrxPremPortion().signum() != 0)
						reinsPortion = reins.getTrxPremPortion().divide(BD_100, 10, RoundingMode.HALF_UP);
				}
				MA0015AEntity exchangeRateReins = mA0015ARepo.findByExDateAndExCurrId(appDate, reins.getTrxCurrId());
				
				String brCodeReins = "VTRX" + reins.getTrxCurrId();
				List<MA0018Entity> businessRuleReins = mA0018Repo.findByBrCode(brCodeReins);

				BigDecimal feeRateReins = reins.getTrxInsBfee().divide(BD_100);
				
				BigDecimal premiReins = grossValue;		//premi total reins		
				premiReins = premiReins.multiply(reinsPortion)
						.setScale(2, RoundingMode.HALF_UP);
				BigDecimal brokerFee = premiReins.multiply(feeRateReins);
				if(reins.getTrxRicommAmt().signum() != 0)
					brokerFee = reins.getTrxRicommAmt();
				
//				BigDecimal brokerFee = premiReins.multiply(feeRateReins).divide(taxRate009, 2, RoundingMode.HALF_UP);
//				
//				System.out.println("************* PREMI REAS"+premiReins);
//				System.out.println("************* BROKER FEE"+brokerFee);
//				
//				BigDecimal vat = brokerFee.multiply(taxRate003);
//				BigDecimal netPy = premiReins.subtract(brokerFee).subtract(vat);
				
				logger.info("***** PREMI REAS : {}", printCurr(premiReins));
				logger.info("***** BROKER FEE : {}", printCurr(brokerFee));

				BigDecimal netPy = premiReins.subtract(brokerFee);
				
				if(isExtension){
					//Premi dari Upload Extension v2
					premiReins = Param.getBdWithDef(param, "premiumValueRe");
					brokerFee = Param.getBdWithDef(param, "brokerFee");
					netPy = premiReins;
				}
				
				logger.info("***** NETT PY : {}", printCurr(netPy));
				
				//rate per reins
				BigDecimal trxInsShare = reins.getTrxInsShare();			
				if(reins.getTrxInsAmt().signum() != 0) {
					trxInsShare = reins.getTrxInsAmt()
							.divide(tr0006.getTrxTsiAmount(), 10, RoundingMode.HALF_UP).multiply(BD_100);
					logger.info("trxInsShare : {}", trxInsShare);
				}
				
				BigDecimal premiCli = premiumValue.multiply(trxInsShare)
						.divide(tr0006.getTrxShare(), 10, RoundingMode.HALF_UP);
				BigDecimal deducCli = premiCli.multiply(interest.getTrxDeducPct().divide(BD_100, 10, RoundingMode.HALF_UP));
				if(interest.getTrxPremiumAmt().signum()!=0)
					deducCli = BigDecimal.ZERO;
				
				BigDecimal netRc = premiCli.subtract(deducCli);
				
				logger.info("premi per reas : {}, deduc : {}, netRc : {}, netPy : {}.", printCurr(premiCli), printCurr(deducCli), printCurr(netRc), printCurr(netPy));
				
				BigDecimal income = netRc.subtract(netPy);
				BigDecimal brkrFee = income.divide(taxRate009, 2, RoundingMode.HALF_UP);
				BigDecimal taxinBf = brkrFee.multiply(taxRate003);
				
				if(isExtension){
					//angka premiCli di-0-kan
					brkrFee = BigDecimal.ZERO;
					taxinBf = BigDecimal.ZERO;
					
					brkrFee = brokerFee.divide(taxRate009, 2, RoundingMode.HALF_UP);
					taxinBf = brkrFee.multiply(taxRate003);
				}
				
				mapValue.put(PREMIUM_VALUE, netPy);
				mapValue.put(BRKR_FEE_VALUE, brkrFee);
				mapValue.put(TAXIN_BF_VALUE, taxinBf);
				
				List<TR0006EEntity> newChecklistReins = new ArrayList<TR0006EEntity>();
				for(TR0006EEntity t6e : tChecklistReins) {
					if(t6e.getTrxPrClient().equals(reins.getTrxInsId()))
						newChecklistReins.add(t6e);	//update production
				}
				
				if(newChecklistReins.size() == 0)
					newChecklistReins = tChecklistReins;
				
				List<TR0001Entity> tr1Entities = new ArrayList<TR0001Entity>();
				List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
				for(TR0006EEntity payMthdReins : newChecklistReins) {
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
					tr1.setGlTrxClient(reins.getTrxInsId());
					
					BigDecimal shareRatePrReins = payMthdReins.getTrxPrShare().divide(BD_100);
					BigDecimal netTtlValueCliPrReins = payMthdReins.getTrxPrAmt();
					if(netTtlValueCliPrReins.compareTo(BigDecimal.ZERO) == 0)
						netTtlValueCliPrReins = netPy.multiply(shareRateReins).multiply(shareRatePrReins);
					netTtlValueCliPrReins = netTtlValueCliPrReins.setScale(2, RoundingMode.HALF_UP);
					
					BigDecimal prRate = BigDecimal.ZERO;
					if(payMthdReins.getTrxPrAmt().compareTo(BigDecimal.ZERO) > 0)
						prRate = payMthdReins.getTrxPrAmt().divide(netPy.multiply(shareRateReins), 2, RoundingMode.HALF_UP);
					else
						prRate = shareRatePrReins;
					tr1.setPrRate(prRate);
					
					tr1.setGlTrxDesc(descReins + " - " + netTtlValueCliPrReins);
					tr1.setGlTrxStatus("0");
					tr1.setGlDataStatus("11");
					tr1.setCreateOn(now);
					tr1.setCreateBy(param.get(Param.USER).toString());
					tr1.setModifyOn(now);
					tr1.setModifyBy(param.get(Param.USER).toString());
					
					if(param.get(EXTENSION.DESC) != null){
						tr1.setGlTrxClientDesc(extDesc);
						tr1.setGlTrxDesc(extDesc);
					}
					
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
						BigDecimal org = value.multiply(shareRateReins).multiply(shareRatePrReins);
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
				
				logger.info("***** SHARE RATE REINS : {}", shareRateReins);
				BigDecimal netTouValueReins = netPy.multiply(shareRateReins).setScale(2, RoundingMode.HALF_UP);
				String clientAssured = tr0006.getTrxClient();
				String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				TR0003Entity tr3 = new TR0003Entity();
				tr3.setTrxType(RQ);
				tr3.setTrxVoucherId(voucherId);
				tr3.setTrxDescription(descReins + " - " + netTouValueReins);
				tr3.setTrxClient(reins.getTrxInsId());
				tr3.setTrxDate(calTrx.getTime());
				tr3.setTrxAssured(clientAssured);
				tr3.setTrxCoverCode(typeOfCover);
				tr3.setTrxInsOfficer(tr0006.getTrxOfficer());
				logger.info("***** NET TOU VAL RE : {}", printCurr(netTouValueReins));
				tr3.setTrxInsInsured(descReins + " - " + netTouValueReins); 
				tr3.setTrxInsStart(interest.getTrxInsStart());
				tr3.setTrxInsEnd(interest.getTrxInsEnd());
				tr3.setTrxCurrId(reins.getTrxCurrId());
				tr3.setTrxCurrRate(exchangeRateReins.getExKmkRateBd());
				tr3.setTrxAmountDue(netTouValueReins); 
				tr3.setTrxOldVoucherId(trxVoucherId); 
				tr3.setTrxStatusData("11");
				tr3.setCreateOn(now);
				tr3.setCreateBy(param.get(Param.USER).toString());
				if(param.get(EXTENSION.DESC) != null){
					tr3.setTrxDescription(extDesc);
					tr3.setTrxInsInsured(extDesc);
				}
			
				List<Map<String, Object>> table = mapTable.get(reins.getTrxInsId());
				if(table == null)
					table = new ArrayList<Map<String,Object>>();
				
				Map<String, Object> coverObj = new HashMap<String, Object>();
				coverObj.put("name", client.getCliName());
				coverObj.put("document", "Cover - Reinsurance");
				
				String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + reins.getTrxInsId() + "', 'placing', 'cover')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + reins.getTrxInsId() + "', 'placing', 'cover', 'Cover - " + client.getCliName() + "')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				coverObj.put("action", action);
				
				if(j == 1) {
					if(isClosing)
						table.add(coverObj);
				}
				
				Map<String, Object> placingObj = new HashMap<String, Object>();
				placingObj.put("name", client.getCliName());
				placingObj.put("document", "Closing Slip");
				
				action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + reins.getTrxInsId() + "', 'closingslip', 'closingslip')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + reins.getTrxInsId() + "', 'closingslip', 'closingslip', 'Closing Slip - " + client.getCliName() + "')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				placingObj.put("action", action);
				
				if(j == 1) {
					if(isClosing)
						table.add(placingObj);
				}
				
				Map<String, Object> journal = new HashMap<String, Object>();
				journal.put("voucherId", tr3.getTrxVoucherId());
				journal.put("name", client.getCliName());
				journal.put("document", "Closing - CN");
				journal.put("insSub", interest.getTrxInsSub());
				journal.put("type", "CREDIT NOTE");
				journal.put("isComm", Boolean.FALSE);
				
				String fn = "Closing - CN - " + client.getCliName();
				
				action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', '" + fn + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				journal.put("action", action);
				table.add(journal);
				
				mapTable.put(reins.getTrxInsId(), table);
				
				int noRow = 1;
				List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
				for(TR0006EEntity payMthdReins : newChecklistReins) {
					TR0003AEntity tr3a = new TR0003AEntity();
					tr3a.setTrxType(tr3.getTrxType());
					tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
					tr3a.setTrxNoRow(noRow);
					
					tr3a.setTrxDueDate(payMthdReins.getTrxPrDate());
					tr3a.setTrxRemarks("Instalment "+noRow);
					
					BigDecimal shareRatePrReins = payMthdReins.getTrxPrShare().divide(BD_100);
					tr3a.setTrxDueAmount(netTouValueReins.multiply(shareRatePrReins));
					
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
					
					tr12.setTrxCoverCode(typeOfCover);
					tr12.setTrxCountInv(count);
					tr12.setTrxDataStatus("11");
					tr12.setTrxClient(reins.getTrxInsId());
					tr12.setTrxDescription(tr1.getGlTrxDesc());
					tr12.setTrxCurrId(reins.getTrxCurrId());
					tr12.setTrxCurrRate(exchangeRateReins.getExKmkRateBd());
					
					tr12.setTrxInvcAmount(netPy.multiply(shareRateReins).multiply(tr1.getPrRate()));
					tr12.setTrxOrgAmount(netPy.multiply(shareRateReins).multiply(tr1.getPrRate()));
					tr12.setTrxBrkrFee(brkrFee.multiply(shareRateReins).multiply(tr1.getPrRate()));
					tr12.setTrxTaxinBf(taxinBf.multiply(shareRateReins).multiply(tr1.getPrRate()));
					if(isExtension){
						//reset values
						tr12.setTrxBrkrFee(mapValue.get(BRKR_FEE_VALUE));
						tr12.setTrxTaxinBf(mapValue.get(TAXIN_BF_VALUE));
					}
					tr12.setTrxNetTou(netTouValue);
					tr12.setTrxNetTtl(netTtlValue);
					
					tr12.setTrxInsOfficer(tr0006.getTrxOfficer());
					tr12.setTrxOldType(RQ);
					tr12.setTrxOldVoucherId(trxVoucherId);
					tr12.setCreateOn(now);
					tr12.setCreateBy(param.get(Param.USER).toString());
					tr12.setModifyOn(now);
					tr12.setModifyBy(param.get(Param.USER).toString());
					
					tr12Entities.add(tr12);
					
					if(param.get(EXTENSION.DESC) != null){
						tr12.setTrxDescription(extDesc);
					}
				
					count++;
				}
				
				tr0001Repo.saveAll(tr1Entities);
				tr0002Repo.saveAll(tr2Entities);
				tr0003Repo.save(tr3);
				tr0003ARepo.saveAll(tr3aEntities);
				tr0012Repo.saveAll(tr12Entities);
			}
			}
			
			if(tCommOut.size()>0) {
				TR0006BEntity tr0006b = tR0006BRepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(RQ, trxVoucherId, "0");
				TR0006GEntity commission = tCommOut.get(0);
				
				MA0012Entity agent = mA0012Repo.findBySaCode(commission.getTrxSaCode());
				String descComm = "RQ " + trxVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + remarksII;
					
				MA0015AEntity exchangeRate = mA0015ARepo.findByExDateAndExCurrId(appDate, tr0006b.getTrxCurrId());
					
				String brCode = "UTRX" + tr0006b.getTrxCurrId();
				List<MA0018Entity> businessRule = mA0018Repo.findByBrCode(brCode);
					
				List<TR0001Entity> tr1Entities = new ArrayList<TR0001Entity>();
				List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
				for(TR0006GEntity comm : tCommOut) {
					String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
					
					TR0001Entity tr1 = new TR0001Entity();
					tr1.setGlTrxClass("OP");
					tr1.setGlType("PU");
					tr1.setGlVoucherId(voucherId);
					
					tr1.setGlTrxDate(calTrx.getTime());
					tr1.setGlTrxDue(calTrx.getTime());
					tr1.setGlTrxMonth(month.byteValue());
					tr1.setGlTrxYear(year.shortValue());
					
					tr1.setGlTrxOfficeId("0");
					tr1.setGlTrxProject("0000");
					tr1.setGlTrxClient(comm.getTrxSaCode());
					
					commOutValue = commOutValue.setScale(2, RoundingMode.HALF_UP);
					
					tr1.setGlTrxDesc(descComm + " - " + commOutValue);
					tr1.setGlTrxStatus("0");
					tr1.setGlDataStatus("11");
					tr1.setCreateOn(now);
					tr1.setCreateBy(param.get(Param.USER).toString());
					tr1.setModifyOn(now);
					tr1.setModifyBy(param.get(Param.USER).toString());
					
					tr1Entities.add(tr1);
					
					BigDecimal valueOrg = BigDecimal.ZERO;
					BigDecimal valueIdr = BigDecimal.ZERO;
					for(MA0018Entity ma0018 : businessRule) {
						TR0002Entity t2 = new TR0002Entity();
						t2.setGlTrxClass(tr1.getGlTrxClass());
						t2.setGlType(tr1.getGlType());
						t2.setGlVoucherId(tr1.getGlVoucherId());
						t2.setGlAccount(ma0018.getBrChildCoa());
						
						MA0015AEntity exchange = mA0015ARepo.findByCoaCodeAndExDate(ma0018.getBrChildCoa(), appDate);
						t2.setGlCurrId(exchange.getExCurrId());
						t2.setGlCurrRate(exchange.getExKmkRateBd());
						
						BigDecimal commPct = comm.getTrxCommPct().divide(BD_100);
						BigDecimal valueCommOut = netTtlValue.multiply(commPct);
						
						if(valueCommOut.compareTo(BigDecimal.ZERO) == 0)
							valueCommOut = comm.getTrxCommAmt();
						
						BigDecimal rate = valueCommOut.divide(commOutValue, 2, RoundingMode.HALF_UP);
						
						String code = ma0018.getBrChildValue().trim();
						BigDecimal value = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
						BigDecimal org = value.multiply(rate);
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
					
				TR0006Entity tr0006 = tR0006Repo.findOneClientByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
				
				String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				TR0003Entity tr3 = new TR0003Entity();
				tr3.setTrxType(RQ);
				tr3.setTrxVoucherId(voucherId);
				tr3.setTrxDescription(descComm + " - " + commOutValue);
				tr3.setTrxClient(commission.getTrxSaCode());
				tr3.setTrxDate(calTrx.getTime());
				tr3.setTrxAssured(tr0006b.getTrxInsId());
				tr3.setTrxCoverCode(typeOfCover);
				tr3.setTrxInsOfficer(tr0006.getTrxOfficer());
				tr3.setTrxInsInsured(descComm + " - " + commOutValue); 
				tr3.setTrxInsStart(interest.getTrxInsStart());
				tr3.setTrxInsEnd(interest.getTrxInsEnd());
				tr3.setTrxCurrId(tr0006b.getTrxCurrId());
				tr3.setTrxCurrRate(exchangeRate.getExKmkRateBd());
				tr3.setTrxAmountDue(commOutValue); 
				tr3.setTrxOldVoucherId(trxVoucherId); 
				tr3.setTrxStatusData("11");
				tr3.setCreateOn(now);
				tr3.setCreateBy(param.get(Param.USER).toString());
				
				List<Map<String, Object>> table = mapTable.get(commission.getTrxSaCode());
				if(table == null)
					table = new ArrayList<Map<String,Object>>();
				
				Map<String, Object> journal = new HashMap<String, Object>();
				journal.put("voucherId", tr3.getTrxVoucherId());
				journal.put("name", agent.getSaName());
				journal.put("document", "Closing - CN");
				journal.put("insSub", interest.getTrxInsSub());
				journal.put("type", "CREDIT NOTE");
				journal.put("isComm", Boolean.TRUE);
				
				String fn = "Closing - CN - " + agent.getSaName();
				
				String action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + agent.getSaName() + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + agent.getSaName() + "', '" + fn + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				journal.put("action", action);
				table.add(journal);
				
				mapTable.put(commission.getTrxSaCode(), table);
				
				int noRow = 1;
				List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
				for(TR0006GEntity comm : tCommOut) {
					TR0003AEntity tr3a = new TR0003AEntity();
					tr3a.setTrxType(tr3.getTrxType());
					tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
					tr3a.setTrxNoRow(noRow);
					
					tr3a.setTrxDueDate(calTrx.getTime());
					tr3a.setTrxRemarks("Instalment "+noRow);
					
					BigDecimal commPct = comm.getTrxCommPct().divide(BD_100);
					BigDecimal valueCommOut = netTtlValue.multiply(commPct);
					
					if(valueCommOut.compareTo(BigDecimal.ZERO) == 0)
						valueCommOut = comm.getTrxCommAmt();
					
					tr3a.setTrxDueAmount(valueCommOut);
					
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
					
					tr12.setTrxCoverCode(typeOfCover);
					tr12.setTrxCountInv(count);
					tr12.setTrxDataStatus("11");
					tr12.setTrxClient(commission.getTrxSaCode());
					tr12.setTrxDescription(tr1.getGlTrxDesc());
					tr12.setTrxCurrId(tr0006b.getTrxCurrId());
					tr12.setTrxCurrRate(exchangeRate.getExKmkRateBd());
					tr12.setTrxIntAmount(BigDecimal.ZERO);
					tr12.setTrxInvcAmount(tr1.getGlTrxValueOrg());
					tr12.setTrxSetAmount(BigDecimal.ZERO);
					tr12.setTrxInsOfficer(tr0006.getTrxOfficer());
					tr12.setTrxOldType(RQ);
					tr12.setTrxOldVoucherId(trxVoucherId);
					tr12.setCreateOn(now);
					tr12.setCreateBy(param.get(Param.USER).toString());
					tr12.setModifyOn(now);
					tr12.setModifyBy(param.get(Param.USER).toString());
					
					tr12.setTrxOrgAmount(tr12.getTrxInvcAmount().subtract(tr12.getTrxSetAmount()));
					
					tr12Entities.add(tr12);
				
					count++;
				}
				
				tr0001Repo.saveAll(tr1Entities);
				tr0002Repo.saveAll(tr2Entities);
				tr0003Repo.save(tr3);
				tr0003ARepo.saveAll(tr3aEntities);
				tr0012Repo.saveAll(tr12Entities);
			}
			
			calNow.add(Calendar.SECOND, 1);
			j++;
		}
		
		//handle multiple submit
		if(common.isAlreadyClosing(RQ, trxVoucherId))
			throw new Exception(MESSAGE.ALREADY_CLOSING + trxVoucherId);
		
		String remarks = Param.getStr(param, REMARKS);
		Date closingDate = Param.getDate(param, CLIENT_CONFIRMATION_DATE);
		tR0006Repo.updateTrxDataStatus("11", remarks, closingDate, RQ, trxVoucherId);
		
		for(String key : mapTable.keySet()) {
			tableAll.addAll(mapTable.get(key));
		}
		
		return tableAll;
	}

	@Override
	public Object createClosingHtml(Map<String, Object> param) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String ids = Param.getStr(param, "ids");
		String type = Param.getStr(param, "type");
		String insSub = Param.getStr(param, "insSub");
		String descAdds = Param.getStrWithDef(param, "p_Description2Add");
		String interestDesc = Param.getStr(param, "interestDesc");
		String endorsementTypes = Param.getStrWithDef(param, "endorsementType");
		
		String id[] = ids.split(",");
		String ty[] = type.split(",");
		String sub[] = insSub.split(",");
		String descAdd[] = descAdds.split(",");
		
		String endorsementType[] = null;
		if(!endorsementTypes.equals(""))
			endorsementType = endorsementTypes.split(",");
		
		if((param.get("uploadTreaty") != null)) {
			//from module of Upload Treaty
			String cliNames = Param.getStr(param, "cliName");
		}
			
		logger.info("Param createClosingHtml : {}.", param);
		List<String> report = new ArrayList<String>();
		for(int i=0;i<id.length;i++) {
			Map<String, String> paramReport = new HashMap<String, String>();
			paramReport.put("voucherId", id[i]);
			paramReport.put("type", ty[i]);
			paramReport.put("trxVoucherId", trxVoucherId);
			paramReport.put("insSub", sub[i]);
			paramReport.put("p_Description2Add", descAdd.length > i ? descAdd[i] : "");
			
			if(interestDesc != null)
				paramReport.put("interestDesc", interestDesc);
			if(endorsementType != null)
				paramReport.put("endorsementType", endorsementType[i]);
			
			if((param.get("uploadTreaty") != null)) {
				//from module of Upload Treaty
				String cliNames = Param.getStr(param, "cliName");
				String cliName[] = cliNames.split(",");
				paramReport.put("cliName", cliName[i]);
				report.add(dcNotesService.createDCNotesHtmlUploadTreaty(paramReport));
			}else {
				//general module
				report.add(dcNotesService.createDCNotesHtml(paramReport));
			}
		}
		
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("report", report);
		
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object sendEmailClosing(Map<String, Object> param) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		List<String> ids = (List<String>) param.get("ids");
		List<String> type = (List<String>) param.get("type");
		List<Integer> insSub = (List<Integer>) param.get("insSub");
		List<Boolean> isComm = (List<Boolean>) param.get("isComm");
		List<String> endorsementType = param.get("endorsementType") == null ? new ArrayList<String>() : (List<String>) param.get("endorsementType");
		
		String appDate1 = mA0014Repo.getAppDateReportProduction1();
		String temp[] = appDate1.split(" ");
		String appDate = temp[0].substring(0, temp[0].length()-2)
						+ "<sup>" 
						+ temp[0].substring(temp[0].length()-2, temp[0].length())
						+ "</sup>"
						+ " " + temp[1] + " " + temp[2];
		
		Map<String, Object> result = new HashMap<String, Object>();
		for(int i=0;i<ids.size();i++) {
			Map<String, String> paramReport = new HashMap<String, String>();
			paramReport.put("voucherId", ids.get(i));
			paramReport.put("type", type.get(i));
			paramReport.put("trxVoucherId", trxVoucherId);
			paramReport.put("insSub", insSub.get(i).toString());
			if(!endorsementType.isEmpty())
				paramReport.put("endorsementType", endorsementType.get(i).toString());
			
			String report = dcNotesService.createDCNotesPdf(paramReport);
			
			TR0003Entity tr3 = tr0003Repo.findByTrxVoucherIdAndTrxType(ids.get(i), RQ);
			
			Map<String, Object> paramCover = new HashMap<String, Object>();
			paramCover.put("P_DATE", appDate);
			paramCover.put("P_VOUCHER_ID", trxVoucherId);
			
			List<TR0006AEntity> interestEntities = tR0006ARepo.findByTrxTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			String typeOfCover = interestEntities.get(0).getTrxCoverCode();
			String mm = trxVoucherId.substring(2, 4);
			String yyyy = trxVoucherId.substring(4, 8);
			String no = trxVoucherId.substring(8, trxVoucherId.length());
			String slipNo = typeOfCover + "/" + yyyy+ "/" + mm + "/" + no;
			paramCover.put("P_SLIP_NO", slipNo);
			
			String email = "";
			if(isComm.get(i)) {
				MA0012Entity agent = mA0012Repo.findBySaCode(tr3.getTrxClient());
				email = agent.getSaEmail();
			}else {
				MA0005Entity client = mA0005Repo.findByCliCode(tr3.getTrxClient());
				email = client.getCliEmail();

				paramCover.put("P_CLIENT_NAME", client.getCliName());
				
				if(type.get(i).equals("CREDIT NOTE"))
					paramCover.put("P_REINS_CODE", client.getCliCode());
			}
			
			JavaMailSender sender = emailUtils.getMailSender();
			if(StringUtils.isNotBlank(email)) {
				MimeMessage msg = sender.createMimeMessage();
		        MimeMessageHelper helper = emailUtils.getMimeMessageHelper(msg);
		        
		        helper.setTo(email);
		        helper.setSubject("Closing : " + trxVoucherId);
		        helper.setText("", true);
		        
		        if(paramCover.get("P_CLIENT_NAME") != null) {
		        	String cover = reportUtils.exportPdf("Cover.jrxml", paramCover);
					String coverEmail = reportUtils.exportHtml("CoverHtml.jrxml", paramCover);
					
					Path path = Paths.get(coverEmail);
					String content = new String(Files.readAllBytes(path));
					content = content.replaceAll("font-size:", "x-size:")
							.replaceAll("SansSerif", "Arial")
							.replaceAll("COMPANY_LOGO", "");
			        helper.setText(content, true);
			
			        File file;
			        file = new File(cover);
			        helper.addAttachment("Cover.pdf", file);
		        }
		
		        File file;
		        file = new File(report);
		        helper.addAttachment("Closing - " + trxVoucherId + ".pdf", file);
		
		        sender.send(msg);
			}else {
	        	logger.error("No recipient for sending email closing with id : {}", ids.get(i));
			}
		}
		
		return result;
	}

	@Override
	public Boolean isValidTrxDate(Map<String, Object> param) {
		logger.info("isValidTrxDate() with param : {}.", param);
		Date trxDate = Param.getDate(param, "trxDate");
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(trxDate);
		
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		TR0005Entity tr5 = tR0005Repo
				.findByProMonthAndProYearAndProStatusAndProId(month.shortValue(), year, "6", "0");
		
		if(tr5 != null) //already EOM
			return Boolean.FALSE;
		
		return Boolean.TRUE;
	}

	@Override
	public Boolean validationUserLevel(Map<String, Object> param) {
		logger.info("do validationUserLevel with param : {}.", param);
		String user = Param.getStr(param, Param.USER);
		
		US0001Entity us01 = uS0001Repo.findByUsCode(user);
		
		Boolean isValid = Boolean.FALSE;
		
		if(us01 != null) {
			if(us01.getUsLevel().equals("2")) {
				isValid = Boolean.TRUE;
			}
		}

		logger.info("do validationUserLevel with param : {}, isValid : {}.", param, isValid);
		return isValid;
	}
	
	public static String printCurr(BigDecimal value) {
	    return NumberFormat.getCurrencyInstance().format(value).replace("$", "");
	}
	
}
