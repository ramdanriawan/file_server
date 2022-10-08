package com.biru.service.impl;

import java.io.File;
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

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.CLOSING_PARAM;
import com.biru.ReBrokerConstants.CODE;
import com.biru.ReBrokerConstants.MESSAGE;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.component.EmailUtils;
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
import com.biru.entity.TR0006AEntity;
import com.biru.entity.TR0006BEntity;
import com.biru.entity.TR0006CEntity;
import com.biru.entity.TR0006DEntity;
import com.biru.entity.TR0006EEntity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0006FEntity;
import com.biru.entity.TR0006GEntity;
import com.biru.entity.TR0006IEntity;
import com.biru.entity.TR0012Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0011Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0003ARepo;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0006ARepo;
import com.biru.repository.TR0006BRepo;
import com.biru.repository.TR0006CRepo;
import com.biru.repository.TR0006DRepo;
import com.biru.repository.TR0006ERepo;
import com.biru.repository.TR0006FRepo;
import com.biru.repository.TR0006GRepo;
import com.biru.repository.TR0006IRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.service.CommonService;
import com.biru.service.ProductionDirectService;

@Service
public class ProductionDirectSeviceImpl extends AbstractCommon implements ProductionDirectService {
	
	@Autowired
	private CommonService common;

	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private MA0005Repo ma0005Repo;
	
	@Autowired
	private MA0011Repo ma0011Repo;
	
	@Autowired
	private MA0014Repo ma0014Repo;
	
	@Autowired
	private MA0015ARepo ma0015ARepo;
	
	@Autowired
	private MA0018Repo ma0018Repo;
	
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
	private TR0006IRepo tR0006IRepo;
	
	private static final String APP_DATE 		= "appDate";
	private static final String CREATE_BY		= "createBy";
	private static final String DATE 			= "date";
	private static final String FAC				= "FAC";
	private static final String RQ 				= "RQ";
	private static final String TRX_VOUCHER_ID 	= "trxVoucherId";
	
	private static final String PROCESS			= "process";
	private static final String PLACING			= "placing";
	private static final String CLOSING_SLIP	= "closingslip";
	private static final String QUOTATION		= "quotation";
	private static final String CLOSING			= "closing";
	private static final String REMARKS			= "remarks";
	private static final String CLIENT_CONFIRMATION_DATE = "clientConfirmationDate";

	private static final String DOC			 	= "doc";
	private static final String HTML		 	= "html";
	private static final String PDF			 	= "pdf";
	
	private static final String PREMIUM_VALUE			= "PREMIUM_VALUE";
	private static final String OTHERS1_VALUE			= "OTHERS1_VALUE";
	private static final String COST_POLICY_VALUE		= "COST_POLICY_VALUE";
	private static final String OTHERS2_VALUE			= "OTHERS2_VALUE";
	private static final String STAMP_DUTY_VALUE		= "STAMP_DUTY_VALUE";
	private static final String OTHERS3_VALUE			= "OTHERS3_VALUE";
	private static final String GROSS_VALUE				= "GROSS_VALUE";
	private static final String ADMIN_FEE_VALUE			= "ADMIN_FEE_VALUE";
	private static final String ADMIN_COST_VALUE		= "ADMIN_COST_VALUE";
	private static final String OTHERS4_VALUE			= "OTHERS4_VALUE";
	private static final String DISC_VALUE				= "DISC_VALUE";
	private static final String BANK_FEE_VALUE			= "BANK_FEE_VALUE";
	private static final String GROSS_TTL_VALUE			= "GROSS_TTL_VALUE";
	private static final String TAXIN_CL_VALUE			= "TAXIN_CL_VALUE";
	private static final String NET_TTL_VALUE			= "NET_TTL_VALUE";
	private static final String BRKR_FEE_VALUE			= "BRKR_FEE_VALUE";
	private static final String OTHERS_VALUE			= "OTHERS_VALUE";
	private static final String COMM_OUT_VALUE			= "COMM_OUT_VALUE";
	private static final String NET_INC_VALUE			= "NET_INC_VALUE";
	private static final String TAXIN_BF_VALUE			= "TAXIN_BF_VALUE";
	private static final String TAXIN_OTH_VALUE			= "TAXIN_OTH_VALUE";
	private static final String TAX_OUT_VALUE			= "TAX_OUT_VALUE";
	private static final String NET_TOU_VALUE			= "NET_TOU_VALUE";
	private static final String NET_PREMIUM_VALUE		= "NET_PREMIUM_VALUE";
	private static final String NET_OTHERS_VALUE		= "NET_OTHERS_VALUE";
	private static final String NET_CLIENT_VALUE		= "NET_CLIENT_VALUE";
	private static final String NET_OTHERSC_VALUE		= "NET_OTHERSC_VALUE";
	private static final String NET_BROKERAGE_VALUE		= "NET_BROKERAGE_VALUE";
	private static final BigDecimal BD_100		= new BigDecimal("100");
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@SuppressWarnings("unchecked")
	@Override
	public List<DropdownIdText> getCommOutName(Map<String, Object> param) {
		DatatableSet data = (DatatableSet) common.lookupClient(param);
		
		List<MA0005Entity> clientEntities = (List<MA0005Entity>) data.getRows();
		
		List<DropdownIdText> commOutNames = new ArrayList<DropdownIdText>();
		for(MA0005Entity client : clientEntities) {
			DropdownIdText name = new DropdownIdText();
			name.setId(client.getCliCode());
			name.setText(client.getCliName());
			
			commOutNames.add(name);
		}
		
		return commOutNames;
	}
	
	@Override
	public BigDecimal getWhtClient(Map<String, Object> param) {
		BigDecimal wht = BigDecimal.ZERO;
		
		MA0005Entity client = ma0005Repo.findByCliCode(Param.getStrWithDef(param, "cliCode"));
		
		if(client == null)
			return wht;

		String whtCode = CODE.C_TAXRATEH007;

		if(StringUtils.isNotBlank(client.getCliTaxId()))
			whtCode = CODE.C_TAXRATEH006;

		wht = new BigDecimal(ma0014Repo.findByPaChildCode(whtCode).getPaChildValue());

		return wht;
	}
	
	@Override
	public Object inquiryModify(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		
		if(StringUtils.isBlank(voucherId))
			return "No data found";
				
		Map<String, Object> data = new HashMap<String, Object>();
		
		List<TR0006Entity> client = tR0006Repo.getDataClient(RQ, voucherId);
		data.put("t1Data", client);
		
		List<TR0006AEntity> interest = tR0006ARepo.getDataInterestDirect(RQ, voucherId);
		data.put("t2Data", interest);
		
		List<TR0006GEntity> commOut = tR0006GRepo.getDataCommOutV2(RQ, voucherId);
		data.put("t3Data", commOut);
		
		List<TR0006BEntity> reinsurance = tR0006BRepo.getDataReinsurance(RQ, voucherId);
		for(TR0006BEntity reins : reinsurance) {	//set wht reins
			param.put("cliCode", reins.getTrxInsId());
			reins.setWht(getWhtClient(param));
		}
		data.put("t4Data", reinsurance);
		
		List<TR0006CEntity> value = tR0006CRepo.findByTrxIdAndTrxVoucherId(RQ, voucherId);
		data.put("t5Data", value);
		List<TR0006IEntity> bank = tR0006IRepo.findByTrxIdAndTrxVoucherId(RQ, voucherId);
		data.put("t5DataExt", bank);
		
		List<TR0006DEntity> tc = tR0006DRepo.getDataTc(RQ, voucherId);
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
		
		TR0006Entity tR0006Entity = new TR0006Entity();
		tR0006Entity.setTrxClass(FAC);
		tR0006Entity.setTrxTrxId(RQ);
		tR0006Entity.setTrxVoucherId(voucherId);
		tR0006Entity.setTrxClient(Param.getStr(t1Data, "client"));
		tR0006Entity.setTrxShare(Param.getBdWithDef(t1Data, "share"));
		tR0006Entity.setTrxCurrId(Param.getStr(t1Data, "curr"));	
		tR0006Entity.setTrxInsuredName(Param.getStr(t1Data, "insuredName"));
		tR0006Entity.setTrxSumInsured(Param.getBdWithDef(t1Data, "amount"));
		tR0006Entity.setTrxPayMthd(Param.getStr(t1Data, "paymentMethod"));
		tR0006Entity.setTrxOfficer(Param.getStr(t1Data, "officer"));
		tR0006Entity.setTrxDiretPy(Param.getStr(t1Data, "directPayment"));
		tR0006Entity.setTrxPpwCli(Param.getIntWithDef(t1Data, "ppw"));
		tR0006Entity.setTrxPeriodVld(Param.getIntWithDef(t1Data, "periodOfValidity"));
		tR0006Entity.setTrxRemarks(Param.getStr(t1Data, "remarks"));
		tR0006Entity.setTrxDataStatus(trxDataStatus);
		tR0006Entity.setTrxFeeClient(Param.getBdWithDef(t1Data, "feeRate"));
		tR0006Entity.setTrxCurrRate(Param.getBdWithDef(t1Data, "exchangeRate"));
		tR0006Entity.setTrxTsiAmount(Param.getBdWithDef(t1Data, "totalSumInsured"));
		
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
		
		param.put("productionCurr", Param.getStr(t1Data, "curr"));
		
		tR0006Repo.save(tR0006Entity);
		
	}
	
	@SuppressWarnings("unchecked")
	private void saveInterestInsured(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		tR0006ARepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);
		
		Map<String, Object> t2Data = (HashMap<String, Object>) param.get("t2Data");

		Integer insSub = 1;
		TR0006AEntity tR0006AEntity = new TR0006AEntity();
		tR0006AEntity.setTrxClass(FAC);
		tR0006AEntity.setTrxTrxId(RQ);
		tR0006AEntity.setTrxVoucherId(voucherId);		
		tR0006AEntity.setTrxInsSub(insSub);
		tR0006AEntity.setTrxCoverCode(Param.getStr(t2Data,"typeOfCover"));
		tR0006AEntity.setTrxInsInsured(Param.getStr(t2Data, "interestInsured"));
		tR0006AEntity.setTrxInsLocation(Param.getStr(t2Data, "location"));
		tR0006AEntity.setTrxPremiumRate(Param.getBdWithDef(t2Data, "premiumRate"));
		tR0006AEntity.setTrxWeightRate(Param.getBdWithDef(t2Data, "weighted"));
		tR0006AEntity.setTrxCombined(Param.getStr(t2Data, "inclTax"));
		tR0006AEntity.setTrxCurrId(Param.getStr(t2Data, "curr"));
		tR0006AEntity.setTrxCurrRate(Param.getBdWithDef(t2Data, "exchangeRate"));
		tR0006AEntity.setTrxSumInsured(Param.getBdWithDef(t2Data, "sumInsured"));
		tR0006AEntity.setTrxInsStart(Param.getDate(t2Data, "insuredPeriod"));
		tR0006AEntity.setTrxInsEnd(Param.getDate(t2Data, "insuredPeriodTo"));
		tR0006AEntity.setTrxInsDur(Param.getInt(t2Data, "duration"));
		tR0006AEntity.setTrxPpw(Param.getIntWithDef(t2Data, "ppw"));
		tR0006AEntity.setTrxQuoValid(Param.getIntWithDef(t2Data, "validity"));
		tR0006AEntity.setTrxRemarks(Param.getStr(t2Data, "remarks"));
		tR0006AEntity.setTrxBfeeSell(Param.getBdWithDef(t2Data, "bFee"));
		tR0006AEntity.setTrxDeducPct(Param.getBdWithDef(t2Data, "deduction"));
		
		Map<String, Object> t2 = new HashMap<String, Object>();
		t2.put("insSub", insSub);
		t2.put("curr", Param.getStr(t2Data, "curr"));
		t2.put("exchangeRate", Param.getBdWithDef(t2Data, "exchangeRate"));

		Map<String, Object> mapInfoT2 = new HashMap<String, Object>();
		mapInfoT2.put("1", t2);
		
		param.put("mapInfoT2", mapInfoT2);
		
		tR0006ARepo.save(tR0006AEntity);
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
			//tR0006BEntity.setTrxCurrId(Param.getStr(data, "curr"));
			tR0006BEntity.setTrxCurrId(Param.getStr(param, "productionCurr"));
			tR0006BEntity.setTrxInsAmt(Param.getBdWithDef(data, "amount"));
			tR0006BEntity.setTrxInsPaymethd(Param.getStr(data, "paymentId"));
			tR0006BEntity.setTrxInsRemarks(Param.getStr(data, "remarks"));
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
		tR0006IRepo.deleteByTrxIdAndTrxVoucherId(RQ, voucherId);

		Map<String, Object> t5Data = (HashMap<String, Object>) param.get("t5Data");
		Map<String, Object> mapInfoT2 = (Map<String, Object>) param.get("mapInfoT2");

		BigDecimal bankFee = Param.getBdWithDef(t5Data, "bankFee");
		String bank = Param.getStrWithDef(t5Data, "bank");
		
		Map<String, Object> t2 = (Map<String, Object>) mapInfoT2.get("1");
		TR0006CEntity tR0006CEntity = new TR0006CEntity();
		tR0006CEntity.setTrxClass(FAC);
		tR0006CEntity.setTrxTrxId(RQ);
		tR0006CEntity.setTrxVoucherId(voucherId);
		tR0006CEntity.setTrxInsSub(Param.getInt(t2, "insSub"));
		tR0006CEntity.setTrxCurrId(Param.getStr(t2, "curr"));
		tR0006CEntity.setTrxCurrRate(Param.getBdWithDef(t2, "exchangeRate"));
		tR0006CEntity.setTrxSumInsured(Param.getBdWithDef(t5Data, "sumInsured"));
		tR0006CEntity.setTrxPremiumBuy(Param.getBdWithDef(t5Data, "premium"));
		tR0006CEntity.setTrxPremiumSell(Param.getBdWithDef(t5Data, "premium"));
		tR0006CEntity.setTrxPolAmount(Param.getBdWithDef(t5Data, "policyCost"));
		tR0006CEntity.setTrxSdutyAmount(Param.getBdWithDef(t5Data, "stampDuty"));
		tR0006CEntity.setTrxOthers1Amount(Param.getBdWithDef(t5Data, "others1"));
		tR0006CEntity.setTrxOthers2Amount(Param.getBdWithDef(t5Data, "others2"));
		tR0006CEntity.setTrxOthers3Amount(Param.getBdWithDef(t5Data, "others3"));
		tR0006CEntity.setTrxAdminAmount(Param.getBdWithDef(t5Data, "adminFee"));
		tR0006CEntity.setTrxComAmount(Param.getBdWithDef(t5Data, "adminCost"));
		tR0006CEntity.setTrxOthers4Amount(Param.getBdWithDef(t5Data, "others4"));
		tR0006CEntity.setTrxBankAmount(bankFee);
		tR0006CEntity.setTrxGrossAmount(Param.getBdWithDef(t5Data, "grossTotal"));
		tR0006CEntity.setTrxDiscSell(Param.getBdWithDef(t5Data, "discount"));
		tR0006CEntity.setTrxTaxinCl(Param.getBdWithDef(t5Data, "taxPpn"));
		tR0006CEntity.setTrxNetTtl(Param.getBdWithDef(t5Data, "netTotal"));
		tR0006CEntity.setTrxBrkrFee(Param.getBdWithDef(t5Data, "brokerageFee"));
		tR0006CEntity.setTrxOthersAmount(Param.getBdWithDef(t5Data, "others"));
		tR0006CEntity.setTrxComoAmount(Param.getBdWithDef(t5Data, "commisionOut"));
		tR0006CEntity.setTrxIncAmount(Param.getBdWithDef(t5Data, "netIncome"));
		tR0006CEntity.setTrxTaxinBf(Param.getBdWithDef(t5Data, "taxinBfee"));
		tR0006CEntity.setTrxTaxinOth(Param.getBdWithDef(t5Data, "taxinOthers"));
		tR0006CEntity.setTrxWithAmount(Param.getBdWithDef(t5Data, "witholdingTax"));
		tR0006CEntity.setTrxNetTou(Param.getBdWithDef(t5Data, "netPayToUwriter"));
		
		tR0006CEntity.setIsModifyTrxBrkrFee(Param.getBoolean(t5Data, "isModifyBrokerageFee"));
		tR0006CEntity.setIsModifyTrxTaxinBf(Param.getBoolean(t5Data, "isModifyTaxinBfee"));
		tR0006CEntity.setIsModifyTrxTaxinOth(Param.getBoolean(t5Data, "isModifyTaxinOthers"));
		
		if(bankFee.signum() > 0 && StringUtils.isNotBlank(bank)) {
			TR0006IEntity t6i = new TR0006IEntity();
			t6i.setTrxBankCode(Param.getStrWithDef(t5Data, "bank"));
			t6i.setTrxClass("FAC");
			t6i.setTrxCommAmt(bankFee);
			t6i.setTrxTrxId(RQ);
			t6i.setTrxVoucherId(voucherId);
			
			tR0006IRepo.save(t6i);
		}
		
		tR0006CRepo.save(tR0006CEntity);
	}

	@SuppressWarnings("unchecked")
	private void saveTc(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
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
	public Object inquirySend(Map<String, Object> param) {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String process = Param.getStr(param, PROCESS);		

		String action;
		List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
		if(PLACING.equals(process)) {
			List<TR0006BEntity> reinsEntities = tR0006BRepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
			for(TR0006BEntity reins : reinsEntities) {
				MA0005Entity ma0005 = ma0005Repo.findByCliCode(reins.getTrxInsId());
				
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
			List<TR0006Entity> clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
			for(TR0006Entity client : clientEntities) {
				MA0005Entity ma0005 = ma0005Repo.findByCliCode(client.getTrxClient());
				
				Map<String, Object> reportObj = new HashMap<String, Object>();
				reportObj.put("name", ma0005.getCliName());
				reportObj.put("document", "Quotation");
				
				action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + voucherId + "', '" + client.getTrxClient() + "', 'quotation', 'quotation')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + voucherId + "', '" + client.getTrxClient() + "', 'quotation', 'quotation', 'Quotation - " + ma0005.getCliName() + "')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				reportObj.put("action", action);
				table.add(reportObj);
			}
		}
		
		return table;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public Object createReportHtml(Map<String, Object> param) throws Exception {
		String process = Param.getStr(param, PROCESS);
		
		Map<String, Object> result = new HashMap<String, Object>();
		if(PLACING.equals(process) || CLOSING_SLIP.equals(process)) {
			Map<String, Object> resultPlacing = (Map<String, Object>) createPlacing(param, HTML);
			List<String> reportPlacing = (List<String>) resultPlacing.get("report");
			result.put("report", reportPlacing);
			
			return result;
		}else if(QUOTATION.equals(process)) {
			Map<String, Object> resultQuotation = (Map<String, Object>) createQuotation(param, HTML);
			List<String> reportQuotation = (List<String>) resultQuotation.get("report");
			result.put("report", reportQuotation);
			
			return result;
		}else if(CLOSING.equals(process)) {
			Map<String, Object> resultInvoice = (Map<String, Object>) createInvoice(param, HTML);
			List<String> reportInvoice = (List<String>) resultInvoice.get("report");
			result.put("report", reportInvoice);
			
			return result;
		}
			
		return null;
	}
	
	private Object createPlacing(Map<String, Object> param, String type) throws Exception {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String user = Param.getStr(param, Param.USER);
		String trxInsId = Param.getStr(param, "code");
				
		String appDate = ma0014Repo.getAppDateReportProductionDirect();
		
		Map<String, Object> paramPlacing = new HashMap<String, Object>();
		paramPlacing.put("P_DATE", appDate);
		paramPlacing.put("P_VOUCHER_ID", voucherId);
		paramPlacing.put("P_TRX_ID", RQ);
		paramPlacing.put("P_USER", user);
		
		List<TR0006BEntity> reinsEntities;
		if(StringUtils.isNotBlank(trxInsId)) 
			reinsEntities = tR0006BRepo.findByTrxTrxIdAndTrxVoucherIdAndTrxInsId(RQ, voucherId, trxInsId);
		else
			reinsEntities = tR0006BRepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		MA0014Entity reportParam = ma0014Repo.findByPaChildCode("OUTFILE001");
		
		List<String> report = new ArrayList<String>();
		for(TR0006BEntity reins : reinsEntities) {
			paramPlacing.put("P_TRX_CLIENT", reins.getTrxInsId());
			
			String reportName = null;
			if(type.equals(PDF))
				reportName = reportUtils.exportPdf(reportParam.getPaChildValue() + ".jrxml", paramPlacing);
			else if(type.equals(DOC))
				reportName = reportUtils.exportDocx(reportParam.getPaChildValue() + ".jrxml", paramPlacing);
			else
				reportName = reportUtils.exportHtml(reportParam.getPaChildValue() + ".jrxml", paramPlacing);
			
			if(reportName != null)
				report.add(reportName);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("report", report);
		
		return result;
	}
	
	private Object createQuotation(Map<String, Object> param, String type) throws Exception {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String user = Param.getStr(param, Param.USER);
		String trxClient = Param.getStr(param, "code");
				
		String appDate = ma0014Repo.getAppDateReportProductionDirect();
		
		Map<String, Object> paramPlacing = new HashMap<String, Object>();
		paramPlacing.put("P_DATE", appDate);
		paramPlacing.put("P_VOUCHER_ID", voucherId);
		paramPlacing.put("P_TRX_ID", RQ);
		paramPlacing.put("P_USER", user);
		
		List<TR0006Entity> clientEntities;
		if(StringUtils.isNotBlank(trxClient)) 
			clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherIdAndTrxClient(RQ, voucherId, trxClient);
		else
			clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		MA0014Entity reportParam = ma0014Repo.findByPaChildCode("OUTFILE003");
		
		List<String> report = new ArrayList<String>();
		for(TR0006Entity client : clientEntities) {
			paramPlacing.put("P_TRX_CLIENT", client.getTrxClient());
			
			String reportName = null;
			if(type.equals(PDF))
				reportName = reportUtils.exportPdf(reportParam.getPaChildValue() + ".jrxml", paramPlacing);
			else if(type.equals(DOC))
				reportName = reportUtils.exportDocx(reportParam.getPaChildValue() + ".jrxml", paramPlacing);
			else
				reportName = reportUtils.exportHtml(reportParam.getPaChildValue() + ".jrxml", paramPlacing);
			
			if(reportName != null)
				report.add(reportName);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("report", report);
		
		return result;
	}
	
	private Object createInvoice(Map<String, Object> param, String type) throws Exception {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String user = Param.getStr(param, Param.USER);
				
		String appDate = ma0014Repo.getAppDateReportProductionDirect();
		
		Map<String, Object> paramPlacing = new HashMap<String, Object>();
		paramPlacing.put("P_DATE", appDate);
		paramPlacing.put("P_VOUCHER_ID", voucherId);
		paramPlacing.put("P_TRX_ID", RQ);
		paramPlacing.put("P_USER", user);
		
		List<TR0006Entity> clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
		MA0014Entity reportParam = ma0014Repo.findByPaChildCode("OUTFILE004");
		
		List<String> report = new ArrayList<String>();
		for(TR0006Entity cli : clientEntities) {
			paramPlacing.put("P_TRX_CLIENT", cli.getTrxClient());
			
			String reportName = null;
			if(type.equals(PDF))
				reportName = reportUtils.exportPdf(reportParam.getPaChildValue() + ".jrxml", paramPlacing);
			else if(type.equals(DOC))
				reportName = reportUtils.exportDocx(reportParam.getPaChildValue() + ".jrxml", paramPlacing);
			else
				reportName = reportUtils.exportHtml(reportParam.getPaChildValue() + ".jrxml", paramPlacing);
			
			if(reportName != null)
				report.add(reportName);
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("report", report);
		
		return result;
	}
	
	@Override
	public Object createReportPdf(Map<String, Object> param) throws Exception {
		String process = Param.getStr(param, PROCESS);
		
		if(PLACING.equals(process) || CLOSING_SLIP.equals(process))
			return createPlacing(param, PDF);
		else if(QUOTATION.equals(process))
			return createQuotation(param, PDF);
		else if(CLOSING.equals(process))
			return createInvoice(param, PDF);
			
		return null;
	}

	@Override
	public Object createReportDoc(Map<String, Object> param) throws Exception {
		String process = Param.getStr(param, PROCESS);
		
		if(PLACING.equals(process) || CLOSING_SLIP.equals(process))
			return createPlacing(param, DOC);
		else if(QUOTATION.equals(process))
			return createQuotation(param, DOC);
		else if(CLOSING.equals(process))
			return createInvoice(param, DOC);
			
		return null;
	}
	
	@Override
	public Object sendEmail(Map<String, Object> param) throws Exception {
		String voucherId = Param.getStr(param, TRX_VOUCHER_ID);
		String process = Param.getStr(param, PROCESS);
		String user = Param.getStr(param, Param.USER);
		String remarks = Param.getStr(param, REMARKS);
		
		String appDate = ma0014Repo.getAppDateReportProductionDirect();
		
		Map<String, Object> paramReport = new HashMap<String, Object>();
		paramReport.put("P_DATE", appDate);
		paramReport.put("P_VOUCHER_ID", voucherId);
		paramReport.put("P_TRX_ID", RQ);
		paramReport.put("P_USER", user);
		
		String trxDataStatus = null;
		JavaMailSender sender = emailUtils.getMailSender();
		if(PLACING.equals(process)) {			
			MA0014Entity reportParam = ma0014Repo.findByPaChildCode("OUTFILE001");
			List<TR0006BEntity> reinsEntities = tR0006BRepo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
		
			for(TR0006BEntity reins : reinsEntities) {
				MA0005Entity ma0005 = ma0005Repo.findByCliCode(reins.getTrxInsId());
				
				paramReport.put("P_TRX_CLIENT", reins.getTrxInsId());
				
				String report = reportUtils.exportPdf(reportParam.getPaChildValue() + ".jrxml", paramReport);
				
				MimeMessage msg = sender.createMimeMessage();
		        MimeMessageHelper helper = emailUtils.getMimeMessageHelper(msg);
		        
		        helper.setTo(ma0005.getCliEmail());
		        helper.setSubject("Placing Slip No : " + voucherId);
		        helper.setText("", true);
		        
		        MA0014Entity ccPlacingParam = ma0014Repo.findByPaChildCode("EMAILRC001");
		        helper.setCc(ccPlacingParam.getPaChildValue());
		
		        File file;
		        file = new File(report);
		        helper.addAttachment("Draft Placing Slip.pdf", file);
		
		        sender.send(msg);
			}
			
			trxDataStatus = "3";
			tR0006Repo.updateTrxDataStatus(trxDataStatus, remarks, RQ, voucherId);
		}else if(QUOTATION.equals(process)) {
			MA0014Entity reportParam = ma0014Repo.findByPaChildCode("OUTFILE003");
			List<TR0006Entity> clientEntities = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, voucherId);
			
			for(TR0006Entity client : clientEntities) {
				MA0005Entity ma0005 = ma0005Repo.findByCliCode(client.getTrxClient());
				
				paramReport.put("P_TRX_CLIENT", client.getTrxClient());
				
				String reportQuo = reportUtils.exportPdf(reportParam.getPaChildValue() + ".jrxml", paramReport);
				
				MimeMessage msg = sender.createMimeMessage();
		        MimeMessageHelper helper = emailUtils.getMimeMessageHelper(msg);
				
		        helper.setTo(ma0005.getCliEmail());
		        helper.setSubject("Quotation No : " + voucherId);
		        helper.setText("", true);
		        
		        MA0014Entity ccQuotationParam = ma0014Repo.findByPaChildCode("EMAILRC002");
		        helper.setCc(ccQuotationParam.getPaChildValue());
		
		        File fileQuo = new File(reportQuo);
		        helper.addAttachment("Quotation.pdf", fileQuo);
		
		        sender.send(msg);
			}
			
			trxDataStatus = "5";
			tR0006Repo.updateTrxDataStatus(trxDataStatus, remarks, RQ, voucherId);
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("trxDataStatus", trxDataStatus);
		return result;
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	public Object doClosing(Map<String, Object> param) throws Exception {
		Long start = System.currentTimeMillis();
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);
		logger.info("Start - closing direct with trxVoucherId : '{}'.", trxVoucherId);		
		
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
		
		List<TR0006IEntity> tValueBank = tR0006IRepo.findByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
		param.put(CLOSING_PARAM.VALUES_BANK_LIST, tValueBank);
		
		Object result = null;
		synchronized (param) {
			result = closing(param);
		}
		
		logger.info("End - closing direct with trxVoucherId : '{}', elapsed time : {}ms.", 
				trxVoucherId, System.currentTimeMillis()-start);
		return result;
	}
	
	@Override
	@Transactional(rollbackOn = Exception.class)
	@SuppressWarnings("unchecked")
	public Object closing(Map<String, Object> param) throws Exception {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);	

		List<Map<String, Object>> tableAll = new ArrayList<Map<String, Object>>();
		Map<String, List<Map<String, Object>>> mapTable = new LinkedHashMap<String, List<Map<String,Object>>>();
		
		Calendar calNow = Calendar.getInstance();
		
		List<TR0006AEntity> interestI = (List<TR0006AEntity>) param.get(CLOSING_PARAM.INTEREST_INSURED_LIST);
		List<TR0006CEntity> tValues = (List<TR0006CEntity>) param.get(CLOSING_PARAM.VALUES_LIST);
		for(TR0006CEntity values : tValues) {
			Date now = calNow.getTime();
			
			BigDecimal premiumValue = values.getTrxPremiumBuy();
			BigDecimal others1Value = values.getTrxOthers1Amount();
			BigDecimal costPolicyValue = values.getTrxPolAmount();
			BigDecimal others2Value = values.getTrxOthers2Amount();
			BigDecimal stampDutyValue = values.getTrxSdutyAmount();
			BigDecimal others3Value = values.getTrxOthers3Amount();
			BigDecimal grossValue = premiumValue.add(costPolicyValue).add(stampDutyValue)
					.add(others1Value).add(others2Value).add(others3Value);
			BigDecimal adminFeeValue = values.getTrxAdminAmount();
			BigDecimal adminCostValue = values.getTrxComAmount();
			BigDecimal others4Value = values.getTrxOthers4Amount();
			BigDecimal discValue = values.getTrxDiscSell();
			BigDecimal bankFeeValue = values.getTrxBankAmount();
			BigDecimal grossTtlValue = values.getTrxGrossAmount();
			BigDecimal taxinClValue = values.getTrxTaxinCl();
			BigDecimal netTtlValue = values.getTrxNetTtl();
			BigDecimal brkrFeeValue = values.getTrxBrkrFee();
			BigDecimal othersValue = values.getTrxOthersAmount();
			BigDecimal commOutValue = values.getTrxComoAmount();
			BigDecimal netIncValue = values.getTrxIncAmount();
			BigDecimal taxinBfValue = values.getTrxTaxinBf();
			BigDecimal taxinOthValue = values.getTrxTaxinOth();
			BigDecimal taxOutValue = values.getTrxWithAmount();
			BigDecimal netTouValue = values.getTrxNetTou();
			BigDecimal netBrokerageValue = brkrFeeValue.add(taxinBfValue).subtract(taxOutValue).subtract(discValue)	;		
			BigDecimal netPremiumValue = premiumValue.subtract(brkrFeeValue).subtract(taxinBfValue);
			BigDecimal netOthersCValue = others1Value.add(others2Value).add(others3Value)
					.add(others4Value).add(adminCostValue).add(adminFeeValue);
			BigDecimal netOthersValue = others1Value.add(others2Value).add(others3Value)
					.add(others4Value).add(adminCostValue).add(adminFeeValue).subtract(discValue);
		//if ( taxinBfValue.compareTo(taxinBfValue)<0); {taxinBfValue= BigDecimal.ZERO ;}
			BigDecimal netClientValue = premiumValue.add(costPolicyValue).add(stampDutyValue)
					.subtract(brkrFeeValue).add(taxOutValue).subtract(taxinBfValue);
			Boolean isModifyTrxTaxinBf = values.getIsModifyTrxTaxinBf();
			Boolean isModifyTrxTaxinOth = values.getIsModifyTrxTaxinOth();
			
			logger.info("closing isModifyTrxTaxinBf : {}, isModifyTrxTaxinOth : {}.", isModifyTrxTaxinBf, isModifyTrxTaxinOth);
			
			//initialize map value			
			Map<String, BigDecimal> mapValue = new HashMap<String, BigDecimal>();
			mapValue.put(PREMIUM_VALUE, premiumValue);
			mapValue.put(OTHERS1_VALUE, others1Value);
			mapValue.put(COST_POLICY_VALUE, costPolicyValue);
			mapValue.put(OTHERS2_VALUE, others2Value);
			mapValue.put(STAMP_DUTY_VALUE, stampDutyValue);
			mapValue.put(OTHERS3_VALUE, others3Value);
			mapValue.put(GROSS_VALUE, grossValue);
			mapValue.put(ADMIN_FEE_VALUE, adminFeeValue);
			mapValue.put(ADMIN_COST_VALUE, adminCostValue);
			mapValue.put(OTHERS4_VALUE, others4Value);
			mapValue.put(DISC_VALUE, discValue);
			mapValue.put(BANK_FEE_VALUE, bankFeeValue);
			mapValue.put(GROSS_TTL_VALUE, grossTtlValue);
			mapValue.put(TAXIN_CL_VALUE, taxinClValue);
			mapValue.put(NET_TTL_VALUE, netTtlValue);
			mapValue.put(BRKR_FEE_VALUE, brkrFeeValue);
			mapValue.put(OTHERS_VALUE, othersValue);
			mapValue.put(COMM_OUT_VALUE, commOutValue);
			mapValue.put(NET_INC_VALUE, netIncValue);
			mapValue.put(TAXIN_BF_VALUE, taxinBfValue);
			mapValue.put(TAXIN_OTH_VALUE, taxinOthValue);
			mapValue.put(TAX_OUT_VALUE, taxOutValue);
			mapValue.put(NET_TOU_VALUE, netTouValue);
			mapValue.put(NET_PREMIUM_VALUE, netPremiumValue);
			mapValue.put(NET_OTHERS_VALUE, netOthersValue);	
			mapValue.put(NET_CLIENT_VALUE, netClientValue);	
			mapValue.put(NET_OTHERSC_VALUE, netOthersCValue);	
			mapValue.put(NET_BROKERAGE_VALUE, netBrokerageValue);	
			
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
			
			MA0011Entity toc = ma0011Repo.findByTcCode(typeOfCover);
			MA0014Entity m14Ppn = ma0014Repo.findByPaChildCode("TAXRATEH003");
			BigDecimal ppn = new BigDecimal(m14Ppn.getPaChildValue());
			BigDecimal ppnRate = ppn.divide(BD_100, 4, RoundingMode.HALF_UP);
			
			BigDecimal shareTotalCli = tR0006Repo.getSummaryTrxShareByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			if(shareTotalCli.compareTo(BD_100) > 0)
				shareTotalCli = BD_100;
			for(TR0006Entity clientInformation : tClientInformation) {
				MA0005Entity client = ma0005Repo.findByCliCode(clientInformation.getTrxClient());
				String descCli = "RQ " + trxVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + remarksII;

				BigDecimal shareCli = clientInformation.getTrxShare();
				BigDecimal portion = shareCli.divide(shareTotalCli, 10, RoundingMode.HALF_UP);
				
				String brCodeCli = "DCTRX" + clientInformation.getTrxCurrId();
				List<MA0018Entity> businessRuleCli = ma0018Repo.findByBrCode(brCodeCli);
				
				List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
				String voucherIdCli = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				
				TR0001Entity tr1 = new TR0001Entity();
				tr1.setGlTrxClass("OP");
				tr1.setGlType("SE");
				tr1.setGlVoucherId(voucherIdCli);
				
				tr1.setGlTrxDate(calTrx.getTime());
				tr1.setGlTrxDue(calTrx.getTime());
				tr1.setGlTrxMonth(month.byteValue());
				tr1.setGlTrxYear(year.shortValue());
				
				tr1.setGlTrxOfficeId("0");
				tr1.setGlTrxProject("0000");
				tr1.setGlTrxClient(clientInformation.getTrxClient());
				
				//transient
				tr1.setPrRate(portion);
				
				tr1.setGlTrxDesc(descCli + " - " + netTtlValue.multiply(portion) + " - " + client.getCliName());
				tr1.setGlTrxStatus("0");
				tr1.setGlDataStatus("11");
				tr1.setCreateOn(now);
				tr1.setCreateBy(param.get(Param.USER).toString());
				tr1.setModifyOn(now);
				tr1.setModifyBy(param.get(Param.USER).toString());
				
				BigDecimal valueOrg = BigDecimal.ZERO;
				BigDecimal valueIdr = BigDecimal.ZERO;
				for(MA0018Entity ma0018 : businessRuleCli) {
					TR0002Entity t2 = new TR0002Entity();
					t2.setGlTrxClass(tr1.getGlTrxClass());
					t2.setGlType(tr1.getGlType());
					t2.setGlVoucherId(tr1.getGlVoucherId());
					t2.setGlAccount(ma0018.getBrChildCoa());
					
					MA0015AEntity exchange = ma0015ARepo.findByCoaCodeAndExDate(ma0018.getBrChildCoa(), appDate);
					t2.setGlCurrId(exchange.getExCurrId());
					t2.setGlCurrRate(exchange.getExKmkRateBd());
					
					String code = ma0018.getBrChildValue().trim();
					BigDecimal value = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
					BigDecimal org = value.multiply(portion);
					BigDecimal idr = org.multiply(exchange.getExKmkRateBd());
					
					t2.setGlOrgDebit(BigDecimal.ZERO);
					t2.setGlIdrDebit(BigDecimal.ZERO);
					t2.setGlOrgCredit(BigDecimal.ZERO);
					t2.setGlIdrCredit(BigDecimal.ZERO);
					//------
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
					
					valueOrg = valueOrg.add(t2.getGlOrgDebit());
					valueIdr = valueIdr.add(t2.getGlIdrDebit());
						
						if(org.compareTo(BigDecimal.ZERO)!=0)
							tr2Entities.add(t2);
					//-----
					/*
					if(ma0018.getBrChildDc().equals('0')) {
						t2.setGlOrgDebit(org);
						t2.setGlIdrDebit(idr);
					}else if(ma0018.getBrChildDc().equals('1')) {
						t2.setGlOrgCredit(org);
						t2.setGlIdrCredit(idr);
					}
					
					valueOrg = valueOrg.add(t2.getGlOrgDebit());
					valueIdr = valueIdr.add(t2.getGlIdrDebit());
					
					if(valueOrg.compareTo(BigDecimal.ZERO)!= 0) {	
		
					tr2Entities.add(t2);
					}
					*/
				}
				
				tr1.setGlTrxValueOrg(valueOrg);
				tr1.setGlTrxValueIdr(valueIdr);				
				
				//get lead reins
				TR0006BEntity tr0006b = tR0006BRepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(RQ, trxVoucherId, "0");
			
				String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				TR0003Entity tr3 = new TR0003Entity();
				tr3.setTrxType(RQ);
				tr3.setTrxVoucherId(voucherId);
				tr3.setTrxDescription(descCli + " - " + netTtlValue.multiply(portion));
				tr3.setTrxClient(clientInformation.getTrxClient());
				tr3.setTrxDate(calTrx.getTime());
				tr3.setTrxAssured(tr0006b.getTrxInsId());
				tr3.setTrxCoverCode(typeOfCover);
				tr3.setTrxInsOfficer(clientInformation.getTrxOfficer());
				tr3.setTrxInsInsured(descCli + " - " + netTtlValue.multiply(portion)); 
				tr3.setTrxInsStart(interest.getTrxInsStart());
				tr3.setTrxInsEnd(interest.getTrxInsEnd());
				tr3.setTrxCurrId(clientInformation.getTrxCurrId());
				tr3.setTrxCurrRate(clientInformation.getTrxCurrRate());
				tr3.setTrxAmountDue(netTtlValue.multiply(portion)); 
				tr3.setTrxOldVoucherId(trxVoucherId);
				tr3.setTrxStatusData("11");
				tr3.setCreateOn(now);
				tr3.setCreateBy(param.get(Param.USER).toString());
				
				List<Map<String, Object>> table = mapTable.get(clientInformation.getTrxClient());
				if(table == null)
					table = new ArrayList<Map<String,Object>>();
				
				Map<String, Object> invoiceObj = new HashMap<String, Object>();
				invoiceObj.put("name", client.getCliName());
				invoiceObj.put("document", "Invoice");
				
				String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'closing', 'invoice')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'closing', 'invoice', 'Invoice Of " + client.getCliName() + "')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				invoiceObj.put("action", action);
				
				table.add(invoiceObj);
				
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
				
				List<TR0006EEntity> newChecklistClient = new ArrayList<TR0006EEntity>();
				for(TR0006EEntity t6e : tChecklistCli) {
					if(t6e.getTrxPrClient().equals(clientInformation.getTrxClient()))
						newChecklistClient.add(t6e);	//update production
				}
				
				int installment = 1;				
				List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
				for(TR0006EEntity payMthdCli : newChecklistClient) {
					TR0003AEntity tr3a = new TR0003AEntity();
					tr3a.setTrxType(tr3.getTrxType());
					tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
					tr3a.setTrxNoRow(installment);
					
					tr3a.setTrxDueDate(payMthdCli.getTrxPrDate());
					tr3a.setTrxRemarks("Instalment "+installment);
					
					BigDecimal premiumPart = premiumValue.multiply(portion)
							.multiply(payMthdCli.getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP));
					BigDecimal discPart = discValue.multiply(portion)
							.multiply(payMthdCli.getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP));
					
					BigDecimal taxinClPart = BigDecimal.ZERO;
					BigDecimal totalPart = premiumPart.subtract(discPart); 
							
					if(installment == 1)
						totalPart = totalPart.add(costPolicyValue).add(stampDutyValue).add(adminFeeValue)
								.add(others1Value).add(others2Value).add(others3Value).add(others4Value)
								.add(adminCostValue).add(bankFeeValue);
					
					if(toc.getTcSalesTax().equals('Y') || toc.getTcSalesTax().equals('y'))
						taxinClPart = totalPart.multiply(ppn.divide(BD_100, 4, RoundingMode.HALF_UP));
					
					BigDecimal netTtlPart = totalPart.add(taxinClPart);
					tr3a.setTrxDueAmount(netTtlPart);
					
					tr3a.setTrxTrxClass("OP");
					
					tr3aEntities.add(tr3a);
					
					installment++;
				}
				
				List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
				for(installment=1; installment<=newChecklistClient.size(); installment++) {
					TR0012Entity tr12 = new TR0012Entity();
					tr12.setTrxTrxClass(tr1.getGlTrxClass());
					tr12.setTrxType(tr1.getGlType());
					tr12.setTrxVoucherId(tr1.getGlVoucherId());
					tr12.setTrxDate(tr1.getGlTrxDate());
					tr12.setTrxDueDate(newChecklistClient.get(installment-1).getTrxPrDate());
					
					Long diffInMillies = Math.abs(tr12.getTrxDueDate().getTime() - tr12.getTrxDate().getTime());
					Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
					tr12.setTrxMethPay(diff.toString());
					
					tr12.setTrxCoverCode(typeOfCover);
					tr12.setTrxCountInv(installment);
					tr12.setTrxDataStatus("11");
					tr12.setTrxClient(clientInformation.getTrxClient());
					tr12.setTrxDescription(tr1.getGlTrxDesc());
					tr12.setTrxCurrId(clientInformation.getTrxCurrId());
					tr12.setTrxCurrRate(clientInformation.getTrxCurrRate());
					
					BigDecimal premiumPart = premiumValue.multiply(portion)
							.multiply(newChecklistClient.get(installment-1).getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP));
					BigDecimal discPart = discValue.multiply(portion)
							.multiply(newChecklistClient.get(installment-1).getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP));
					
					BigDecimal taxinClPart = BigDecimal.ZERO;
					BigDecimal totalPart = premiumPart.subtract(discPart); 
					
					BigDecimal brkrFeePart = brkrFeeValue.multiply(portion)
							.multiply(newChecklistClient.get(installment-1).getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP));
							
					BigDecimal taxinBfPart = isModifyTrxTaxinBf ? 
							taxinBfValue.multiply(portion)
										.multiply(newChecklistClient.get(installment-1).getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP))
							: brkrFeePart.multiply(ppnRate);
					
					if(installment == 1)
						totalPart = totalPart.add(costPolicyValue).add(stampDutyValue).add(adminFeeValue)
								.add(others1Value).add(others2Value).add(others3Value).add(others4Value)
								.add(adminCostValue).add(bankFeeValue);
					
					if(toc.getTcSalesTax().equals('Y') || toc.getTcSalesTax().equals('y'))
						taxinClPart = totalPart.multiply(ppn.divide(BD_100, 4, RoundingMode.HALF_UP));
					
					BigDecimal netTtlPart = totalPart.add(taxinClPart);
					
					tr12.setTrxPremium(premiumPart);
					tr12.setTrxDiscAmount(discPart);
					tr12.setTrxTaxinCl(taxinClPart);
					tr12.setTrxNetTtl(netTtlPart);
					tr12.setTrxBrkrFee(brkrFeePart);
					tr12.setTrxTaxinBf(taxinBfPart);
					tr12.setTrxWithAmount(taxOutValue);					
					
					if(installment == 1) {
						tr12.setTrxPolAmount(costPolicyValue);
						tr12.setTrxSdutyAmount(stampDutyValue);
						tr12.setTrxAdminAmount(adminFeeValue);
						tr12.setTrxOthers1Amount(others1Value);
						tr12.setTrxOthers2Amount(others2Value);
						tr12.setTrxOthers3Amount(others3Value);
						tr12.setTrxOthers4Amount(others4Value);
						tr12.setTrxComAmount(adminCostValue);
						tr12.setTrxBankAmount(bankFeeValue);
									
						}
					
					tr12.setTrxOrgAmount(netTtlPart);
					tr12.setTrxInvcAmount(netTtlPart);
					
					tr12.setTrxInsOfficer(clientInformation.getTrxOfficer());
					tr12.setTrxOldType(RQ);
					tr12.setTrxOldVoucherId(trxVoucherId);
					tr12.setCreateOn(now);
					tr12.setCreateBy(param.get(Param.USER).toString());
					tr12.setModifyOn(now);
					tr12.setModifyBy(param.get(Param.USER).toString());
					
					tr12Entities.add(tr12);
				}
				
				tr0001Repo.save(tr1);
				tr0002Repo.saveAll(tr2Entities);
				tr0003Repo.save(tr3);
				tr0003ARepo.saveAll(tr3aEntities);
				tr0012Repo.saveAll(tr12Entities);
			}
			
			TR0006BEntity reinsLead = tR0006BRepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(RQ, trxVoucherId, "0");
			BigDecimal shareTotalReins = tR0006BRepo.getSummaryTrxInsShareByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			if(shareTotalReins.compareTo(BD_100) > 0)
				shareTotalReins = BD_100;
			List<TR0006BEntity> tReinsurance = (List<TR0006BEntity>) param.get(CLOSING_PARAM.REINSURANCE_LIST);
			BigDecimal totalBrkrFeeReins = getTotalBrkrFeeReins(tReinsurance, interest.getTrxSumInsured());
			for(TR0006BEntity reins :tReinsurance) {				
				MA0005Entity client = ma0005Repo.findByCliCode(reins.getTrxInsId());
				String descReins = "RQ " + trxVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + remarksII;
				
				BigDecimal shareReins = reins.getTrxInsShare();
				BigDecimal portion = shareReins.divide(shareTotalReins, 10, RoundingMode.HALF_UP);
				
				Boolean isLead = reins.getTrxInsType().equals("0");
				//========================= Map Value =========================//				
				BigDecimal premiumVal = premiumValue.multiply(portion);
				BigDecimal others1Val = isLead ? values.getTrxOthers1Amount() : BigDecimal.ZERO;
				BigDecimal costPolicyVal = isLead ? values.getTrxPolAmount() : BigDecimal.ZERO;
				BigDecimal others2Val = isLead ? values.getTrxOthers2Amount() : BigDecimal.ZERO;
				BigDecimal stampDutyVal = isLead ? values.getTrxSdutyAmount() : BigDecimal.ZERO;
				BigDecimal others3Val = isLead ? values.getTrxOthers3Amount() : BigDecimal.ZERO;
				BigDecimal grossVal = premiumVal.add(costPolicyVal).add(stampDutyVal)
						.add(others1Val).add(others2Val).add(others3Val);
				BigDecimal adminFeeVal = isLead ? values.getTrxAdminAmount() : BigDecimal.ZERO;
				BigDecimal adminCostVal = isLead ? values.getTrxComAmount() : BigDecimal.ZERO;
				BigDecimal others4Val = isLead ? values.getTrxOthers4Amount() : BigDecimal.ZERO;
				BigDecimal discVal = values.getTrxDiscSell();
				BigDecimal bankFeeVal = isLead ? values.getTrxBankAmount() : BigDecimal.ZERO;
				BigDecimal grossTtlVal = grossVal.add(adminFeeVal).add(adminCostVal).add(others4Val);
				
				BigDecimal total = grossTtlVal.subtract(discVal).add(bankFeeVal);
				
				BigDecimal taxPPn = BigDecimal.ZERO;
				if(toc.getTcSalesTax().equals('Y') || toc.getTcSalesTax().equals('y'))
					taxPPn = total.multiply(ppnRate);
				
				BigDecimal taxinClVal = taxPPn;
				BigDecimal netTtlVal = total.add(taxPPn);

				BigDecimal taxRateh11 = common.getTaxRateh011();
				BigDecimal taxRateh12 = common.getTaxRateh012();
				
				BigDecimal brkrFeeReins = calculateBrkrFee(reins, interest.getTrxSumInsured());
				BigDecimal portionBrkrFee = getPortion(brkrFeeReins, totalBrkrFeeReins, portion);
				BigDecimal brkrFeeVal = brkrFeeValue.multiply(portionBrkrFee);
				
				BigDecimal othersVal = others1Val.add(others2Val).add(others3Val)
						.add(adminFeeVal).add(adminCostVal).add(others4Val);
				othersVal = othersVal.divide(taxRateh12, 4, RoundingMode.HALF_UP);
				othersVal = isLead ? othersVal : BigDecimal.ZERO;
				
				BigDecimal commOutVal = isLead ? commOutValue.multiply(portion) : BigDecimal.ZERO;
				BigDecimal netIncVal = brkrFeeVal.add(othersVal).subtract(commOutVal);
				BigDecimal taxinBfVal = brkrFeeVal.multiply(ppnRate);
				taxinBfVal = isModifyTrxTaxinBf ? taxinBfValue.multiply(portionBrkrFee) : taxinBfVal;	//modify input UI
				BigDecimal taxinOthVal = othersVal.multiply(taxRateh11);
				taxinOthVal = (isLead && isModifyTrxTaxinOth) ? taxinOthValue : taxinOthVal;			//modify input UI
				
				Map<String, Object> paramWht = new HashMap<String, Object>();
				paramWht.put("cliCode", reinsLead.getTrxInsId());
				BigDecimal whtRate = getWhtClient(paramWht).divide(BD_100, 4, RoundingMode.HALF_UP);
				BigDecimal taxOutVal = brkrFeeVal.multiply(whtRate);
				
				BigDecimal netTouVal = premiumVal.add(costPolicyVal).add(stampDutyVal)
						.subtract(brkrFeeVal).subtract(taxinBfVal).add(taxOutVal);
				netTouVal = netTouVal.setScale(2, RoundingMode.HALF_UP);
				BigDecimal netPremiumVal = premiumVal.subtract(brkrFeeVal).subtract(taxinBfVal);
				//= premiumVal.add(costPolicyVal).add(stampDutyVal)
				
				//initialize map value			
				Map<String, BigDecimal> mapValueReins = new HashMap<String, BigDecimal>();
				mapValueReins.put(PREMIUM_VALUE, premiumVal);
				mapValueReins.put(OTHERS1_VALUE, others1Val);
				mapValueReins.put(COST_POLICY_VALUE, costPolicyVal);
				mapValueReins.put(OTHERS2_VALUE, others2Val);
				mapValueReins.put(STAMP_DUTY_VALUE, stampDutyVal);
				mapValueReins.put(OTHERS3_VALUE, others3Val);
				mapValueReins.put(GROSS_VALUE, grossVal);
				mapValueReins.put(ADMIN_FEE_VALUE, adminFeeVal);
				mapValueReins.put(ADMIN_COST_VALUE, adminCostVal);
				mapValueReins.put(OTHERS4_VALUE, others4Val);
				mapValueReins.put(DISC_VALUE, discVal);
				mapValueReins.put(BANK_FEE_VALUE, bankFeeVal);
				mapValueReins.put(GROSS_TTL_VALUE, grossTtlVal);
				mapValueReins.put(TAXIN_CL_VALUE, taxinClVal);
				mapValueReins.put(NET_TTL_VALUE, netTtlVal);
				mapValueReins.put(BRKR_FEE_VALUE, brkrFeeVal);
				mapValueReins.put(OTHERS_VALUE, othersVal);
				mapValueReins.put(COMM_OUT_VALUE, commOutVal);
				mapValueReins.put(NET_INC_VALUE, netIncVal);
				mapValueReins.put(TAXIN_BF_VALUE, taxinBfVal);
				mapValueReins.put(TAXIN_OTH_VALUE, taxinOthVal);
				mapValueReins.put(TAX_OUT_VALUE, taxOutVal);
				mapValueReins.put(NET_TOU_VALUE, netTouVal);
				mapValueReins.put(NET_PREMIUM_VALUE, netPremiumVal);
				
				//========================= Map Value =========================//
				
				String brCodeReins = "DVTRX" + reins.getTrxCurrId();
				List<MA0018Entity> businessRuleReins = ma0018Repo.findByBrCode(brCodeReins);
				
				List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
				String voucherIdReins = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				
				TR0001Entity tr1 = new TR0001Entity();
				tr1.setGlTrxClass("OP");
				tr1.setGlType("PU");
				tr1.setGlVoucherId(voucherIdReins);
				
				tr1.setGlTrxDate(calTrx.getTime());
				tr1.setGlTrxDue(calTrx.getTime());
				tr1.setGlTrxMonth(month.byteValue());
				tr1.setGlTrxYear(year.shortValue());
				
				tr1.setGlTrxOfficeId("0");
				tr1.setGlTrxProject("0000");
				tr1.setGlTrxClient(reins.getTrxInsId());
				
				//transient
//				tr1.setPrRate(portion);
				
				tr1.setGlTrxDesc(descReins + " - " + netTouVal + " - " + client.getCliName());
				tr1.setGlTrxStatus("0");
				tr1.setGlDataStatus("11");
				tr1.setCreateOn(now);
				tr1.setCreateBy(param.get(Param.USER).toString());
				tr1.setModifyOn(now);
				tr1.setModifyBy(param.get(Param.USER).toString());
				
				BigDecimal valueOrg = BigDecimal.ZERO;
				BigDecimal valueIdr = BigDecimal.ZERO;
				for(MA0018Entity ma0018 : businessRuleReins) {
					TR0002Entity t2 = new TR0002Entity();
					t2.setGlTrxClass(tr1.getGlTrxClass());
					t2.setGlType(tr1.getGlType());
					t2.setGlVoucherId(tr1.getGlVoucherId());
					t2.setGlAccount(ma0018.getBrChildCoa());
					
					MA0015AEntity exchange = ma0015ARepo.findByCoaCodeAndExDate(ma0018.getBrChildCoa(), appDate);
					t2.setGlCurrId(exchange.getExCurrId());
					t2.setGlCurrRate(exchange.getExKmkRateBd());
					
					String code = ma0018.getBrChildValue().trim();
					BigDecimal value = mapValueReins.get(code) == null ? BigDecimal.ZERO : mapValueReins.get(code);
					BigDecimal org = value;
					BigDecimal idr = org.multiply(exchange.getExKmkRateBd());
					
					t2.setGlOrgDebit(BigDecimal.ZERO);
					t2.setGlIdrDebit(BigDecimal.ZERO);
					t2.setGlOrgCredit(BigDecimal.ZERO);
					t2.setGlIdrCredit(BigDecimal.ZERO);
					//------
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
					
					valueOrg = valueOrg.add(t2.getGlOrgDebit());
					valueIdr = valueIdr.add(t2.getGlIdrDebit());
						
						if(org.compareTo(BigDecimal.ZERO)!=0)
							tr2Entities.add(t2);
					//-----
					
					
					/*if(ma0018.getBrChildDc().equals('0')) {
						t2.setGlOrgDebit(org);
						t2.setGlIdrDebit(idr);
					}else if(ma0018.getBrChildDc().equals('1')) {
						t2.setGlOrgCredit(org);
						t2.setGlIdrCredit(idr);
					}
					
					valueOrg = valueOrg.add(t2.getGlOrgDebit());
					valueIdr = valueIdr.add(t2.getGlIdrDebit());
					
					tr2Entities.add(t2);
					*/
				}
				
				tr1.setGlTrxValueOrg(valueOrg);
				tr1.setGlTrxValueIdr(valueIdr);
				
				TR0006Entity tr0006 = tR0006Repo.findOneClientByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
				
				String clientAssured = tr0006.getTrxClient();
				String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				TR0003Entity tr3 = new TR0003Entity();
				tr3.setTrxType(RQ);
				tr3.setTrxVoucherId(voucherId);
				tr3.setTrxClient(reins.getTrxInsId());
				tr3.setTrxDate(calTrx.getTime());
				tr3.setTrxAssured(clientAssured);
				tr3.setTrxCoverCode(typeOfCover);
				tr3.setTrxInsOfficer(tr0006.getTrxOfficer());
				tr3.setTrxInsStart(interest.getTrxInsStart());
				tr3.setTrxInsEnd(interest.getTrxInsEnd());
				tr3.setTrxCurrId(reins.getTrxCurrId());
				tr3.setTrxCurrRate(tClientInformation.get(0).getTrxCurrRate());
				tr3.setTrxDescription(descReins + " - " + netTouVal);
				tr3.setTrxInsInsured(descReins + " - " + netTouVal); 
				tr3.setTrxAmountDue(netTouVal); 
				tr3.setTrxOldVoucherId(trxVoucherId); 
				tr3.setTrxStatusData("11");
				tr3.setCreateOn(now);
				tr3.setCreateBy(param.get(Param.USER).toString());
				
				List<Map<String, Object>> table = mapTable.get(reins.getTrxInsId());
				if(table == null)
					table = new ArrayList<Map<String,Object>>();
				
				Map<String, Object> journal = new HashMap<String, Object>();
				journal.put("voucherId", tr3.getTrxVoucherId());
				journal.put("name", client.getCliName());
				journal.put("document", "Closing - CN");
				journal.put("insSub", interest.getTrxInsSub());
				journal.put("type", "CREDIT NOTE");
				journal.put("isComm", Boolean.FALSE);
				
				String fn = "Closing - CN - " + client.getCliName();
				
				String action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', '" + fn + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				journal.put("action", action);
				table.add(journal);
				
				mapTable.put(reins.getTrxInsId(), table);
				
				List<TR0006EEntity> newChecklistReins = new ArrayList<TR0006EEntity>();
				for(TR0006EEntity t6e : tChecklistReins) {
					if(t6e.getTrxPrClient().equals(reins.getTrxInsId()))
						newChecklistReins.add(t6e);	//update production
				}
				
				int installment = 1;				
				List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
				for(TR0006EEntity payMthdReins : newChecklistReins) {
					TR0003AEntity tr3a = new TR0003AEntity();
					tr3a.setTrxType(tr3.getTrxType());
					tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
					tr3a.setTrxNoRow(installment);
					
					tr3a.setTrxDueDate(payMthdReins.getTrxPrDate());
					tr3a.setTrxRemarks("Instalment "+installment);
					
					BigDecimal premiumPart = premiumValue.multiply(portion)
							.multiply(payMthdReins.getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP));
					
					BigDecimal brkrFeePart = brkrFeeValue.multiply(portionBrkrFee)
							.multiply(payMthdReins.getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP));
							
					BigDecimal taxinBfPart = isModifyTrxTaxinBf ? 
							taxinBfValue.multiply(portionBrkrFee)
										.multiply(payMthdReins.getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP)) 
							: brkrFeePart.multiply(ppnRate);
					BigDecimal taxOutPart = brkrFeePart.multiply(whtRate);
					
					BigDecimal netTouPart = premiumPart.subtract(brkrFeePart).subtract(taxinBfPart).add(taxOutPart);
					if(installment == 1 && isLead)
						netTouPart = netTouPart.add(costPolicyValue).add(stampDutyValue);
					
					tr3a.setTrxDueAmount(netTouPart);
					
					tr3a.setTrxTrxClass("OP");
					
					tr3aEntities.add(tr3a);
					
					installment++;
				}
				
				List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
				for(installment = 1; installment<=newChecklistReins.size(); installment++) {
					TR0012Entity tr12 = new TR0012Entity();
					tr12.setTrxTrxClass(tr1.getGlTrxClass());
					tr12.setTrxType(tr1.getGlType());
					tr12.setTrxVoucherId(tr1.getGlVoucherId());
					tr12.setTrxDate(tr1.getGlTrxDate());
					tr12.setTrxDueDate(newChecklistReins.get(installment-1).getTrxPrDate());
					
					Long diffInMillies = Math.abs(tr12.getTrxDueDate().getTime() - tr12.getTrxDate().getTime());
					Long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
					tr12.setTrxMethPay(diff.toString());
					
					tr12.setTrxCoverCode(typeOfCover);
					tr12.setTrxCountInv(installment);
					tr12.setTrxDataStatus("11");
					tr12.setTrxClient(reins.getTrxInsId());
					tr12.setTrxDescription(tr1.getGlTrxDesc());
					tr12.setTrxCurrId(reins.getTrxCurrId());
					tr12.setTrxCurrRate(tClientInformation.get(0).getTrxCurrRate());
					
					BigDecimal premiumPart = premiumValue.multiply(portion)
							.multiply(newChecklistReins.get(installment-1).getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP));
					
					BigDecimal brkrFeePart = brkrFeeValue.multiply(portionBrkrFee)
							.multiply(newChecklistReins.get(installment-1).getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP));
							
					BigDecimal taxinBfPart = isModifyTrxTaxinBf ? 
							taxinBfValue.multiply(portionBrkrFee)
										.multiply(newChecklistReins.get(installment-1).getTrxPrShare().divide(BD_100, 4, RoundingMode.HALF_UP)) 
							: brkrFeePart.multiply(ppnRate);					
					BigDecimal taxOutPart = brkrFeePart.multiply(whtRate);
					
					BigDecimal netTouPart = premiumPart.subtract(brkrFeePart).subtract(taxinBfPart).add(taxOutPart);
					if(installment == 1 && isLead)
						netTouPart = netTouPart.add(costPolicyValue).add(stampDutyValue);
					
					tr12.setTrxPremium(premiumPart);
					tr12.setTrxBrkrFee(brkrFeePart);
					tr12.setTrxTaxinBf(taxinBfPart);
					tr12.setTrxWithAmount(taxOutPart);
					
					if(installment == 1 && isLead) {
						tr12.setTrxPolAmount(costPolicyValue);
						tr12.setTrxSdutyAmount(stampDutyValue);
					}

					tr12.setTrxNetTou(netTouPart);
					tr12.setTrxOrgAmount(netTouPart);
					tr12.setTrxInvcAmount(netTouPart);
					
					tr12.setTrxInsOfficer(tr0006.getTrxOfficer());
					tr12.setTrxOldType(RQ);
					tr12.setTrxOldVoucherId(trxVoucherId);
					tr12.setCreateOn(now);
					tr12.setCreateBy(param.get(Param.USER).toString());
					tr12.setModifyOn(now);
					tr12.setModifyBy(param.get(Param.USER).toString());
					
					tr12Entities.add(tr12);
				}
				
				tr0001Repo.save(tr1);
				tr0002Repo.saveAll(tr2Entities);
				tr0003Repo.save(tr3);
				tr0003ARepo.saveAll(tr3aEntities);
				tr0012Repo.saveAll(tr12Entities);
			}
			
			List<TR0006GEntity> tCommOut = (List<TR0006GEntity>) param.get(CLOSING_PARAM.COMMOUT_LIST);
			if(tCommOut.size()>0) {
				TR0006GEntity commission = tCommOut.get(0);
				MA0005Entity client = ma0005Repo.findByCliCode(commission.getTrxSaCode());
				TR0006BEntity tr0006b = tR0006BRepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(RQ, trxVoucherId, "0");
				
				String descComm = "RQ " + trxVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + remarksII;
					
				MA0015AEntity exchangeRate = ma0015ARepo.findByExDateAndExCurrId(appDate, tr0006b.getTrxCurrId());
					
				String brCode = "DOTRX" + tr0006b.getTrxCurrId();
				List<MA0018Entity> businessRule = ma0018Repo.findByBrCode(brCode);
					
				List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
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
				tr1.setGlTrxClient(commission.getTrxSaCode());
				
				tr1.setGlTrxDesc(descComm + " - " + commOutValue + " - " + client.getCliName());
				tr1.setGlTrxStatus("0");
				tr1.setGlDataStatus("11");
				tr1.setCreateOn(now);
				tr1.setCreateBy(param.get(Param.USER).toString());
				tr1.setModifyOn(now);
				tr1.setModifyBy(param.get(Param.USER).toString());
				
				BigDecimal valueOrg = BigDecimal.ZERO;
				BigDecimal valueIdr = BigDecimal.ZERO;
				for(MA0018Entity ma0018 : businessRule) {
					TR0002Entity t2 = new TR0002Entity();
					t2.setGlTrxClass(tr1.getGlTrxClass());
					t2.setGlType(tr1.getGlType());
					t2.setGlVoucherId(tr1.getGlVoucherId());
					t2.setGlAccount(ma0018.getBrChildCoa());
					
					MA0015AEntity exchange = ma0015ARepo.findByCoaCodeAndExDate(ma0018.getBrChildCoa(), appDate);
					t2.setGlCurrId(exchange.getExCurrId());
					t2.setGlCurrRate(exchange.getExKmkRateBd());
					
					String code = ma0018.getBrChildValue().trim();
					BigDecimal value = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
					BigDecimal org = value;
					BigDecimal idr = org.multiply(exchange.getExKmkRateBd());
					
					t2.setGlOrgDebit(BigDecimal.ZERO);
					t2.setGlIdrDebit(BigDecimal.ZERO);
					t2.setGlOrgCredit(BigDecimal.ZERO);
					t2.setGlIdrCredit(BigDecimal.ZERO);
					//------
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
					
					valueOrg = valueOrg.add(t2.getGlOrgDebit());
					valueIdr = valueIdr.add(t2.getGlIdrDebit());
						
						if(org.compareTo(BigDecimal.ZERO)!=0)
							tr2Entities.add(t2);
					//-----
					
					/*
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
					*/
				}
				
				tr1.setGlTrxValueOrg(valueOrg);
				tr1.setGlTrxValueIdr(valueIdr);
					
				TR0006Entity tr0006 = tR0006Repo.findOneClientByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
				
				voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
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
				
				List<Map<String, Object>> table = mapTable.get(client.getCliCode());
				if(table == null)
					table = new ArrayList<Map<String,Object>>();
				
				Map<String, Object> journal = new HashMap<String, Object>();
				journal.put("voucherId", tr3.getTrxVoucherId());
				journal.put("name", client.getCliName());
				journal.put("document", "Closing - CN");
				journal.put("insSub", interest.getTrxInsSub());
				journal.put("type", "CREDIT NOTE");
				journal.put("isComm", Boolean.FALSE);
				
				String fn = "Closing - CN - " + client.getCliName();
				
				String action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', '" + fn + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				journal.put("action", action);
				table.add(journal);
				
				mapTable.put(client.getCliCode(), table);
				
				TR0003AEntity tr3a = new TR0003AEntity();
				tr3a.setTrxType(tr3.getTrxType());
				tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
				tr3a.setTrxNoRow(1);
				tr3a.setTrxDueDate(calTrx.getTime());
				tr3a.setTrxRemarks("Instalment "+1);
				tr3a.setTrxDueAmount(commOutValue);
				tr3a.setTrxTrxClass("OP");
				
				int count = 1;
				List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
				for(TR0006GEntity com : tCommOut) {
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
					tr12.setTrxClient(com.getTrxSaCode());
					tr12.setTrxDescription(tr1.getGlTrxDesc());
					tr12.setTrxCurrId(tr0006b.getTrxCurrId());
					tr12.setTrxCurrRate(exchangeRate.getExKmkRateBd());

					tr12.setTrxComAmount(BigDecimal.ZERO);
					tr12.setTrxPremium(BigDecimal.ZERO);
					tr12.setTrxDiscAmount(BigDecimal.ZERO);
					tr12.setTrxDeducAmount(BigDecimal.ZERO);
					tr12.setTrxBrkrFee(BigDecimal.ZERO);
					tr12.setTrxTaxinBf(BigDecimal.ZERO);
					tr12.setTrxTaxinCl(BigDecimal.ZERO);
					tr12.setTrxGrossAmount(BigDecimal.ZERO);
					tr12.setTrxGrossBf(BigDecimal.ZERO);
					
					BigDecimal comAmt = com.getTrxCommAmt() == null ? BigDecimal.ZERO : com.getTrxCommAmt();
					BigDecimal comPct = com.getTrxCommPct() == null ? BigDecimal.ZERO : com.getTrxCommPct();
					BigDecimal comPctRate = comPct.divide(BD_100, 4, RoundingMode.HALF_UP);
					
					BigDecimal comoAmt = comPct.compareTo(BigDecimal.ZERO) > 0 ?
							premiumValue.multiply(comPctRate) : comAmt;
					tr12.setTrxComoAmount(comoAmt);
					
					tr12.setTrxOthers1Amount(BigDecimal.ZERO);
					tr12.setTrxOthers2Amount(BigDecimal.ZERO);
					tr12.setTrxOthers3Amount(BigDecimal.ZERO);
					tr12.setTrxOthers4Amount(BigDecimal.ZERO);
					tr12.setTrxNetTou(BigDecimal.ZERO);
					tr12.setTrxNetTtl(BigDecimal.ZERO);
					tr12.setTrxPolAmount(BigDecimal.ZERO);
					tr12.setTrxSdutyAmount(BigDecimal.ZERO);
					tr12.setTrxOthersAmount(BigDecimal.ZERO);
					tr12.setTrxAdminAmount(BigDecimal.ZERO);
					tr12.setTrxBankAmount(BigDecimal.ZERO);
					tr12.setTrxIncOthers(BigDecimal.ZERO);
					tr12.setTrxTaxinOth(BigDecimal.ZERO);
					tr12.setTrxWithAmount(BigDecimal.ZERO);
					
					tr12.setTrxInsOfficer(tr0006.getTrxOfficer());
					tr12.setTrxOldType(RQ);
					tr12.setTrxOldVoucherId(trxVoucherId);
					tr12.setCreateOn(now);
					tr12.setCreateBy(param.get(Param.USER).toString());
					tr12.setModifyOn(now);
					tr12.setModifyBy(param.get(Param.USER).toString());
					
					tr12.setTrxOrgAmount(comoAmt);
					tr12.setTrxInvcAmount(comoAmt);
					
					tr12Entities.add(tr12);
				
					count++;
				}
				
				tr0001Repo.save(tr1);
				tr0002Repo.saveAll(tr2Entities);
				tr0003Repo.save(tr3);
				tr0003ARepo.save(tr3a);
				tr0012Repo.saveAll(tr12Entities);
			}
			
			List<TR0006IEntity> tValuesBank = (List<TR0006IEntity>) param.get(CLOSING_PARAM.VALUES_BANK_LIST);
			if(tValuesBank.size()>0) {
				TR0006IEntity bank = tValuesBank.get(0);
				MA0005Entity client = ma0005Repo.findByCliCode(bank.getTrxBankCode());
				TR0006BEntity tr0006b = tR0006BRepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(RQ, trxVoucherId, "0");
				
				String descBank = "RQ " + trxVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + remarksII;
					
				MA0015AEntity exchangeRate = ma0015ARepo.findByExDateAndExCurrId(appDate, interest.getTrxCurrId());
					
				String brCode = "DBTRX" + interest.getTrxCurrId();
				List<MA0018Entity> businessRule = ma0018Repo.findByBrCode(brCode);
					
				List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
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
				tr1.setGlTrxClient(bank.getTrxBankCode());
				
				tr1.setGlTrxDesc(descBank + " - " + bank.getTrxCommAmt() + " - " + client.getCliName());
				tr1.setGlTrxStatus("0");
				tr1.setGlDataStatus("11");
				tr1.setCreateOn(now);
				tr1.setCreateBy(param.get(Param.USER).toString());
				tr1.setModifyOn(now);
				tr1.setModifyBy(param.get(Param.USER).toString());
				
				BigDecimal valueOrg = BigDecimal.ZERO;
				BigDecimal valueIdr = BigDecimal.ZERO;
				for(MA0018Entity ma0018 : businessRule) {
					TR0002Entity t2 = new TR0002Entity();
					t2.setGlTrxClass(tr1.getGlTrxClass());
					t2.setGlType(tr1.getGlType());
					t2.setGlVoucherId(tr1.getGlVoucherId());
					t2.setGlAccount(ma0018.getBrChildCoa());
					
					MA0015AEntity exchange = ma0015ARepo.findByCoaCodeAndExDate(ma0018.getBrChildCoa(), appDate);
					t2.setGlCurrId(exchange.getExCurrId());
					t2.setGlCurrRate(exchange.getExKmkRateBd());
					
					String code = ma0018.getBrChildValue().trim();
					BigDecimal value = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
					BigDecimal org = value;
					BigDecimal idr = org.multiply(exchange.getExKmkRateBd());
					
					t2.setGlOrgDebit(BigDecimal.ZERO);
					t2.setGlIdrDebit(BigDecimal.ZERO);
					t2.setGlOrgCredit(BigDecimal.ZERO);
					t2.setGlIdrCredit(BigDecimal.ZERO);
					//------
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
					
					valueOrg = valueOrg.add(t2.getGlOrgDebit());
					valueIdr = valueIdr.add(t2.getGlIdrDebit());
						
						if(org.compareTo(BigDecimal.ZERO)!=0)
							tr2Entities.add(t2);
					//-----
					/*
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
					*/
				}
				
				tr1.setGlTrxValueOrg(valueOrg);
				tr1.setGlTrxValueIdr(valueIdr);
					
				TR0006Entity tr0006 = tR0006Repo.findOneClientByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
				
				voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
				TR0003Entity tr3 = new TR0003Entity();
				tr3.setTrxType(RQ);
				tr3.setTrxVoucherId(voucherId);
				tr3.setTrxDescription(descBank + " - " + bank.getTrxCommAmt());
				tr3.setTrxClient(bank.getTrxBankCode());
				tr3.setTrxDate(calTrx.getTime());
				tr3.setTrxAssured(tr0006b.getTrxInsId());
				tr3.setTrxCoverCode(typeOfCover);
				tr3.setTrxInsOfficer(tr0006.getTrxOfficer());
				tr3.setTrxInsInsured(descBank + " - " + bank.getTrxCommAmt()); 
				tr3.setTrxInsStart(interest.getTrxInsStart());
				tr3.setTrxInsEnd(interest.getTrxInsEnd());
				tr3.setTrxCurrId(interest.getTrxCurrId());
				tr3.setTrxCurrRate(exchangeRate.getExKmkRateBd());
				tr3.setTrxAmountDue(bank.getTrxCommAmt()); 
				tr3.setTrxOldVoucherId(trxVoucherId); 
				tr3.setTrxStatusData("11");
				tr3.setCreateOn(now);
				tr3.setCreateBy(param.get(Param.USER).toString());
				
				List<Map<String, Object>> table = mapTable.get(client.getCliCode());
				if(table == null)
					table = new ArrayList<Map<String,Object>>();
				
				Map<String, Object> journal = new HashMap<String, Object>();
				journal.put("voucherId", tr3.getTrxVoucherId());
				journal.put("name", client.getCliName());
				journal.put("document", "Closing - CN");
				journal.put("insSub", interest.getTrxInsSub());
				journal.put("type", "CREDIT NOTE");
				journal.put("isComm", Boolean.FALSE);
				
				String fn = "Closing - CN - " + client.getCliName();
				
				String action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fa fa-print\"></i>" 
						+ "</button>&nbsp;";
				action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', '" + fn + "', 'CREDIT NOTE')\">" 
						+ "<i class=\"fas fa-file-word\"></i>" 
						+ "</button>";
				
				journal.put("action", action);
				table.add(journal);
				
				mapTable.put(client.getCliCode(), table);
				
				TR0003AEntity tr3a = new TR0003AEntity();
				tr3a.setTrxType(tr3.getTrxType());
				tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
				tr3a.setTrxNoRow(1);
				tr3a.setTrxDueDate(calTrx.getTime());
				tr3a.setTrxRemarks("Instalment "+1);
				tr3a.setTrxDueAmount(bank.getTrxCommAmt());
				tr3a.setTrxTrxClass("OP");
				
				int count = 1;
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
				tr12.setTrxClient(bank.getTrxBankCode());
				tr12.setTrxDescription(tr1.getGlTrxDesc());
				tr12.setTrxCurrId(interest.getTrxCurrId());
				tr12.setTrxCurrRate(exchangeRate.getExKmkRateBd());

				tr12.setTrxComAmount(BigDecimal.ZERO);
				tr12.setTrxPremium(BigDecimal.ZERO);
				tr12.setTrxDiscAmount(BigDecimal.ZERO);
				tr12.setTrxDeducAmount(BigDecimal.ZERO);
				tr12.setTrxBrkrFee(BigDecimal.ZERO);
				tr12.setTrxTaxinBf(BigDecimal.ZERO);
				tr12.setTrxTaxinCl(BigDecimal.ZERO);
				tr12.setTrxGrossAmount(BigDecimal.ZERO);
				tr12.setTrxGrossBf(BigDecimal.ZERO);
				tr12.setTrxComoAmount(BigDecimal.ZERO);
				tr12.setTrxOthers1Amount(BigDecimal.ZERO);
				tr12.setTrxOthers2Amount(BigDecimal.ZERO);
				tr12.setTrxOthers3Amount(BigDecimal.ZERO);
				tr12.setTrxOthers4Amount(BigDecimal.ZERO);
				tr12.setTrxNetTou(BigDecimal.ZERO);
				tr12.setTrxNetTtl(BigDecimal.ZERO);
				tr12.setTrxPolAmount(BigDecimal.ZERO);
				tr12.setTrxSdutyAmount(BigDecimal.ZERO);
				tr12.setTrxOthersAmount(BigDecimal.ZERO);
				tr12.setTrxAdminAmount(BigDecimal.ZERO);
				tr12.setTrxBankAmount(bankFeeValue);
				tr12.setTrxIncOthers(BigDecimal.ZERO);
				tr12.setTrxTaxinOth(BigDecimal.ZERO);
				tr12.setTrxWithAmount(BigDecimal.ZERO);
				
				tr12.setTrxInsOfficer(tr0006.getTrxOfficer());
				tr12.setTrxOldType(RQ);
				tr12.setTrxOldVoucherId(trxVoucherId);
				tr12.setCreateOn(now);
				tr12.setCreateBy(param.get(Param.USER).toString());
				tr12.setModifyOn(now);
				tr12.setModifyBy(param.get(Param.USER).toString());
				
				tr12.setTrxOrgAmount(bank.getTrxCommAmt());
				tr12.setTrxInvcAmount(bank.getTrxCommAmt());
				
				tr0001Repo.save(tr1);
				tr0002Repo.saveAll(tr2Entities);
				tr0003Repo.save(tr3);
				tr0003ARepo.save(tr3a);
				tr0012Repo.save(tr12);
			}
			
			calNow.add(Calendar.SECOND, 1);
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
		param.put("process", CLOSING);
		
		return createReportHtml(param);
	}
	
	//get total brkrfee reins
	private BigDecimal getTotalBrkrFeeReins(List<TR0006BEntity> reinsList, BigDecimal sumInsured) {
		BigDecimal total = BigDecimal.ZERO;
		
		for(TR0006BEntity reins : reinsList) {
			BigDecimal bfee = calculateBrkrFee(reins, sumInsured);
			total = total.add(bfee);
		}
		
		return total;
	}
	
	//calculate brkrfee reins
	private BigDecimal calculateBrkrFee(TR0006BEntity reins, BigDecimal sumInsured) {
		BigDecimal shareRate = reins.getTrxInsShare().divide(BD_100, 4, RoundingMode.HALF_UP);
		BigDecimal premiRate = reins.getTrxInsPremium().divide(BD_100, 4, RoundingMode.HALF_UP);
		BigDecimal bfeeRate = reins.getTrxInsBfee().divide(BD_100, 4, RoundingMode.HALF_UP);
		
		BigDecimal premi = sumInsured.multiply(shareRate).multiply(premiRate);
		BigDecimal bfee = premi.multiply(bfeeRate);
		
		return bfee;
	}
	
	private BigDecimal getPortion(BigDecimal part, BigDecimal total, BigDecimal defultPortion) {
		BigDecimal portion = defultPortion;
		
		if(total != null && total.compareTo(BigDecimal.ZERO) != 0)
			portion = part.divide(total, 6, RoundingMode.HALF_UP);
		
		return portion;
	}
	
}
