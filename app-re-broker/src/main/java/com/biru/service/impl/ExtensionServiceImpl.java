package com.biru.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.CLOSING_PARAM;
import com.biru.ReBrokerConstants.PARAM;
import com.biru.ReBrokerConstants.REST.EXTENSION;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.VoucherComponent;
import com.biru.config.ThreadPoolConfig;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0018Entity;
import com.biru.entity.ResponseEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0003AEntity;
import com.biru.entity.TR0003Entity;
import com.biru.entity.TR0006AEntity;
import com.biru.entity.TR0006BEntity;
import com.biru.entity.TR0006CEntity;
import com.biru.entity.TR0006EEntity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0006HEntity;
import com.biru.entity.TR0012Entity;
import com.biru.entity.ViewInqExtensionEntity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0015Repo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0003ARepo;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0006ARepo;
import com.biru.repository.TR0006BRepo;
import com.biru.repository.TR0006CRepo;
import com.biru.repository.TR0006ERepo;
import com.biru.repository.TR0006HRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewInqExtensionRepo;
import com.biru.runnable.SaveClientUploadRunnable;
import com.biru.runnable.SaveReasUploadRunnable;
import com.biru.service.ExtensionService;
import com.biru.service.ProductionService;

@Service
public class ExtensionServiceImpl extends AbstractCommon implements ExtensionService{

	@Autowired
	private TR0006Repo tr0006repo;
	
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private TR0006BRepo tr0006Brepo;
	
	@Autowired
	private MA0018Repo ma0018Repo;
	
	@Autowired
	private TR0001Repo tr0001repo;
	
	@Autowired
	private TR0002Repo tr0002repo;
	
	@Autowired
	private TR0012Repo tr0012repo;
	
	@Autowired
	private TR0006HRepo tR0006HRepo;
	
	@Autowired
	private TR0006ERepo tr0006ERepo;
	
	@Autowired
	private ViewInqExtensionRepo viewInqExtensionRepo;
	
	@Autowired
	private MA0015Repo ma0015repo;
	
	@Autowired
	private MA0015ARepo ma0015Arepo;
	
	@Autowired
	private TR0006CRepo tR0006CRepo;
	
	@Autowired
	private TR0006ARepo tr0006ARepo;
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private TR0003Repo tr0003Repo;
	
	@Autowired
	private TR0003ARepo tr0003ARepo;
	
	@Autowired
	private MA0014Repo ma0014repo;
	
	@Autowired
	private ProductionService productionService;
	
	@Autowired
	private ThreadPoolConfig threadPoolConfig;
	
	@Value("${document.path}")
	private String path;
	
	private Logger log = LoggerFactory.getLogger(ExtensionServiceImpl.class);
	
	private static final String CLIENT_TYPE 		= "0";
	private static final String REINSURANCE_TYPE 	= "1";
	private static final BigDecimal BD_100		= new BigDecimal("100");

	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) throws ParseException{
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String p_CoverCode = Param.getStr(p_Param, "typeOfCover");
		String p_Client = Param.getStr(p_Param, "clientCode");
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		/*
		 * Mencari tanggal aplikasi
		 * */
		Calendar calTrx = Calendar.getInstance();
		Date appDate=commonServiceImpl.getAppDate();
		calTrx.setTime(appDate);
		Integer p_month = calTrx.get(Calendar.MONTH)+1;
		Integer p_year = calTrx.get(Calendar.YEAR);
		
		
		Page<ViewInqExtensionEntity> data = viewInqExtensionRepo.getDataForExtension(p_CoverCode, p_Client, pageable);
		List<ViewInqExtensionEntity> listOfData = data.getContent();
		
		BigDecimal exRateValue = new BigDecimal(0);
		String exRateValueStr = "";
		
		for (int i=0; i<listOfData.size(); i++){
			ViewInqExtensionEntity viewInqExtensionEntity = listOfData.get(i);
			String voucherId = viewInqExtensionEntity.getTrxVoucherId();
			String fullDate = voucherId.substring(0, 9);
			String date = fullDate.substring(0, 2).concat("/").concat(fullDate.substring(2, 4)).concat("/").concat(fullDate.substring(4, 8));
			viewInqExtensionEntity.setTrxDate(date);
			
			//exRateValue = ma0015repo.findByExCurrIdAndExMonthAndExYear(viewInqExtensionEntity.getTrxCurrId(), p_month, p_year).getExRateValue();
			exRateValue = ma0015Arepo.findByExDateAndExCurrId(appDate, viewInqExtensionEntity.getTrxCurrId()).getExKmkRate();
			exRateValueStr = NumberFormat.getCurrencyInstance().format(exRateValue).replace("$", "");
			viewInqExtensionEntity.setExRateValue(exRateValue);
			viewInqExtensionEntity.setExRateValueStr(exRateValueStr);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object upload(Map<String, Object> paramFromUI) throws ParseException, IOException {
		Long beginUpload = System.currentTimeMillis();
		String trxId = Param.getStr(paramFromUI, "p_TrxId");
		String voucherId = Param.getStr(paramFromUI, "p_TrxVoucherId");
		log.info("Param Extension: {}", paramFromUI);
		Long beforeReadFile = System.currentTimeMillis();
		List<Map<String, Object>> objectFromFile = readFile(paramFromUI);
		beforeReadFile = System.currentTimeMillis()-beforeReadFile;
		
		BigDecimal premiumClient = BigDecimal.ZERO;
		String format = Param.getStr(paramFromUI, "p_Format");
		Boolean isReas = true;
		Boolean isBordoClient = false;
		
		if(objectFromFile.get(0).get("type").toString().contains("C")){
			isReas = false;
		}
		
		/*if (source is tru){
			return respom;
		}*/
		paramFromUI.put("p_TrxId", trxId);
		paramFromUI.put("trxVoucherId", voucherId);
				
		Date appDate=commonServiceImpl.getAppDate();
		String cliMeth = tr0006repo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId).get(0).getTrxPayMthd();
		String reMeth = tr0006Brepo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId).get(0).getTrxInsPaymethd();
		Date clientDate = formatDateId.parse(Param.getStr(paramFromUI, "p_ClientDate"));
		Calendar calTrx = Calendar.getInstance();
		
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		/*
		 * Replacing values from Input From, with Object from File
		 * */
		BigDecimal tsi = BigDecimal.ZERO;
		List<TR0006CEntity> tValuesAll = new ArrayList<TR0006CEntity>();
		List<String> listOfSource = new ArrayList<String>();
		List<BigDecimal> listOfPremi = new ArrayList<BigDecimal>();
		List<BigDecimal> listOfBrokerFee = new ArrayList<BigDecimal>();
		List<TR0006HEntity> listOfTR6HEntity = new ArrayList<TR0006HEntity>();
		
		List<TR0006EEntity> tChecklistCli = tr0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(trxId, voucherId, CLIENT_TYPE);
		List<TR0006EEntity> tChecklistReins = tr0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(trxId, voucherId, REINSURANCE_TYPE);
		Long beginTr6h = System.currentTimeMillis();
		for(int i=0; i<objectFromFile.size(); i++){
			Map<String, Object> param = new HashMap<String, Object>(objectFromFile.get(i));
			param.put("p_CurrId", Param.getStr(objectFromFile.get(i), "currFromFile"));
			param.put("p_StartDate", Param.getStr(objectFromFile.get(i), "startDateFromFile"));
			param.put("p_EndDate", Param.getStr(objectFromFile.get(i), "endDateFromFile"));
			param.put("p_TsiAmount", Param.getBdWithDef(objectFromFile.get(i), "tsiAmtFromFile"));
			param.put("p_NewPortion", "100");
			param.put("isUpload", Boolean.TRUE);
			param.put("p_BrokerFeeOnly", Param.getBdWithDef(objectFromFile.get(i), "brokerFeeOnly"));
			param.put("p_RiCommOnly", Param.getBdWithDef(objectFromFile.get(i), "riCommOnly"));
			param.put("p_Share", Param.getBdWithDef(objectFromFile.get(i), "share"));
			param.put("trxVoucherId", voucherId);
			
			Date startDate = formatDateId.parse(Param.getStr(param, "p_StartDate"));
			Date endDate = formatDateId.parse(Param.getStr(param, "p_EndDate"));
			
			premiumClient = premiumClient.add(Param.getBdWithDef(objectFromFile.get(i), "sumPremiNett"));
			paramFromUI.put("brokerFee", Param.getBdWithDef(objectFromFile.get(i), "brokerFee")); //sumRiComm
			if("13".equals(format)){
				//BORDO
				/*
				 * Pada Client, sumRiComm = Discount
				 * Pada Reas, sumRiComm = Brokerage Fee 
				 * */
				paramFromUI.put("discount", Param.getBdWithDef(objectFromFile.get(i), "brokerFee"));
				if(isReas == false){
					isBordoClient = true;	
				}
				
				BigDecimal grossPremi = Param.getBdWithDef(objectFromFile.get(0), "grossPremi").setScale(2, RoundingMode.HALF_UP);
				BigDecimal rate = (Param.getBdWithDef(objectFromFile.get(0), "rate").setScale(6, RoundingMode.HALF_UP));
				BigDecimal tsiForReShare = Param.getBdWithDef(objectFromFile.get(0), "tsiAmtFromFile");
				
				BigDecimal share = BigDecimal.ZERO;
				if(grossPremi != null || rate != null || tsiForReShare != null){
					share = (grossPremi.divide(rate, 2, RoundingMode.HALF_UP)).divide(tsiForReShare, 2, RoundingMode.HALF_UP);
				}
				
				param.put("p_Share", share);
			}
			paramFromUI.put("isBordoClient", isBordoClient);
			
			param.put("premiumClientUpload", premiumClient);
			
			param.put("listOftChecklistCli", tChecklistCli);
			param.put("listOftChecklistReins", tChecklistReins);
			
			tsi = tsi.add(Param.getBdWithDef(objectFromFile.get(i), "tsiAmtFromFile"));
			
			List<TR0006CEntity> tValues = new ArrayList<TR0006CEntity>();
			
			paramFromUI.put("p_TrxId", trxId);
			
			param.put("appDate", appDate);
			param.put("reference", paramFromUI.get("p_Reference").toString()); //dari ui
			param.put("currId", paramFromUI.get("p_CurrId").toString().replace(".", "")); //dari ui
			param.put("exchangeRate", Param.getBdWithDef(paramFromUI, "p_ExchangeRate")); //dari ui
			param.put("CLI_METH", cliMeth);
			param.put("RE_METH", reMeth);
			param.put("clientDate", clientDate); //dari ui
			param.put("createOn", calTrx.getTime());
			param.put("isReas", isReas);
			
			TR0006HEntity tr0006hEntity = insertTR0006H(param);
			listOfTR6HEntity.add(tr0006hEntity);
			
			tValuesAll.addAll(tValues);
			
			listOfSource.add(Param.getStr(objectFromFile.get(i), "source"));
			listOfPremi.add(Param.getBd(objectFromFile.get(i), "premi"));
			listOfBrokerFee.add(Param.getBd(objectFromFile.get(i), "brokerFeeEach"));
			paramFromUI.put("fileName", Param.getStr(param, "fileName"));
		}
		tR0006HRepo.saveAll(listOfTR6HEntity);
		log.info("INSERT TR6H elapse time : {}", System.currentTimeMillis()-beginTr6h);
		BigDecimal premiBuy = BigDecimal.ZERO;
		BigDecimal premiSell = BigDecimal.ZERO;
		BigDecimal premiBfeebuy = BigDecimal.ZERO;
		BigDecimal premiVatBuy = BigDecimal.ZERO;
		BigDecimal premiBfeeSell = BigDecimal.ZERO;
		BigDecimal premiDiscSell = BigDecimal.ZERO;
		for(TR0006CEntity values : tValuesAll) {
			premiBuy = premiBuy.add(values.getTrxPremiumBuy());
			premiSell = premiSell.add(values.getTrxPremiumSell());
			premiBfeebuy = premiBfeebuy.add(values.getTrxBfeeBuy());
			premiVatBuy = premiVatBuy.add(values.getTrxVatBuy());
			premiBfeeSell = premiBfeeSell.add(values.getTrxBfeeSell());
			premiDiscSell = premiDiscSell.add(values.getTrxDiscSell());
		}
		TR0006CEntity sumValues = new TR0006CEntity();
		sumValues.setTrxPremiumBuy(premiBuy);
		sumValues.setTrxPremiumSell(premiSell);
		sumValues.setTrxBfeeBuy(premiBfeebuy);
		sumValues.setTrxVatBuy(premiVatBuy);
		sumValues.setTrxBfeeSell(premiBfeeSell);
		sumValues.setTrxDiscSell(premiDiscSell);
		
		List<TR0006AEntity> listInterestInsured = tr0006ARepo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId);
		TR0006AEntity interest = listInterestInsured.get(0);
		interest.setTrxSumInsured(tsi);
		List<Object> newListInterest = new ArrayList<Object>();
		newListInterest.add(interest);
		
		sumValues.setTrxInsSub(interest.getTrxInsSub());
		
		List<TR0006Entity> tClientInformation = tr0006repo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId);
		List<TR0006BEntity> tReinsurance = tr0006Brepo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId);
		paramFromUI.put("sumPremi", objectFromFile.get(objectFromFile.size()-1).get("sumPremiNett"));
		paramFromUI.put("typeOfFile", objectFromFile.get(0).get("type"));
		paramFromUI.put("sumBrokerFee", objectFromFile.get(objectFromFile.size()-1).get("brokerFee"));
		paramFromUI.put("numberOfRow", objectFromFile.size());
		paramFromUI.put("currency", objectFromFile.get(0).get("currFromFile"));
		paramFromUI.put("listOfSource", listOfSource);
		paramFromUI.put("listOfPremi", listOfPremi);
		paramFromUI.put("listOfBrokerFee", listOfBrokerFee);
		paramFromUI.put("sourceTop", Param.getStr(objectFromFile.get(0), "source"));
		
		if(isReas){
			//upload Reas
			paramFromUI.put(CLOSING_PARAM.REINSURANCE_LIST, tReinsurance);
			paramFromUI.put(CLOSING_PARAM.PAYMENT_METHOD_REINS_LIST, tChecklistReins);
		}else{
			//upload Client
			paramFromUI.put(CLOSING_PARAM.CLIENT_LIST, tClientInformation);
			paramFromUI.put(CLOSING_PARAM.PAYMENT_METHOD_CLI_LIST, tChecklistCli);
		}
		
		response.addAll((List<Map<String, Object>>) saveUpload(paramFromUI));
		log.info("READ FILE elapse time : {}", beforeReadFile);
		log.info("BEGIN UPLOAD elapse time : {}", System.currentTimeMillis()-beginUpload);
		return response;
	}

	@Override
	public Object entry(Map<String, Object> param) throws Exception {
		String trxId = Param.getStr(param, "p_TrxId");
		String voucherId = Param.getStr(param, "p_TrxVoucherId");
		log.info("Param Extension: {}", param);
		List<TR0006CEntity> listOfTR0006CEntity = tR0006CRepo.findByTrxIdAndTrxVoucherId(
				trxId, voucherId);
		List<TR0006EEntity> tChecklistCli = new ArrayList<TR0006EEntity>();
		List<TR0006EEntity> tChecklistReins = new ArrayList<TR0006EEntity>();
		
		TR0006EEntity tr0006EEntity = new TR0006EEntity();
		tr0006EEntity.setTrxPrShare(BD_100);
		
		tChecklistCli.add(tr0006EEntity);
		tChecklistReins.add(tr0006EEntity);
		
		param.put("listOfTR0006CEntity", listOfTR0006CEntity);
		param.put("listOftChecklistCli", tChecklistCli);
		param.put("listOftChecklistReins", tChecklistReins);
		param.put("isUpload", Boolean.FALSE);
		//Object response = save(param);
		
		List<TR0006Entity> tClientInformation = tr0006repo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId);
		List<TR0006BEntity> tReinsurance = tr0006Brepo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId);
		BigDecimal premiumValueClient = Param.getBdWithDef(param, "p_PremiumValue");
		BigDecimal reinsuranceAmount = Param.getBdWithDef(param, "p_ReinsuranceAmt");
		param.put(CLOSING_PARAM.CLIENT_LIST, tClientInformation);
		param.put(CLOSING_PARAM.REINSURANCE_LIST, tReinsurance);
		param.put(CLOSING_PARAM.COMMOUT_LIST, new ArrayList<TR0006BEntity>());
		param.put(CLOSING_PARAM.PAYMENT_METHOD_CLI_LIST, tChecklistCli);
		param.put(CLOSING_PARAM.PAYMENT_METHOD_REINS_LIST, tChecklistReins);
		param.put(CLOSING_PARAM.VALUES_LIST, calculateValues(voucherId, trxId, null, reinsuranceAmount, premiumValueClient, null, null, param));
		param.put(EXTENSION.DESC, param.get("p_Interest").toString());
		
		Object response = productionService.closing(param);
		
		
		insertTR0006H(param);
		
		return response;
	}
	
	private TR0006HEntity insertTR0006H(Map<String, Object> param) throws ParseException{
		Boolean isUpload = Param.getBoolean(param, "isUpload");
		Boolean isReas = Param.getBoolean(param, "isReas");
		/*
		 * Insert to TR0006H 
		 * */
		TR0006HEntity tr0006hEntity = new TR0006HEntity();
		tr0006hEntity.setTrxTrxId(Param.getStr(param, "p_TrxId"));
		tr0006hEntity.setTrxVoucherId(voucherComponent.saveVoucherCounter(formatDate.format((Date) param.get("appDate"))));
		tr0006hEntity.setTrxOldType(Param.getStr(param, "p_TrxId"));
		tr0006hEntity.setTrxOldVoucherId(Param.getStr(param, "reference"));
		tr0006hEntity.setTrxCurrId(Param.getStr(param, "currId"));
		tr0006hEntity.setTrxCurrRate(Param.getBdWithDef(param, "exchangeRate"));
		tr0006hEntity.setTrxTsiAmount(Param.getBdWithDef(param, "p_TsiAmount")); //
		tr0006hEntity.setTrxSumInsured(BigDecimal.ZERO);
		tr0006hEntity.setTrxNewPortion(Param.getBdWithDef(param, "p_NewPortion")); //
		tr0006hEntity.setTrxDate((Date) param.get("appDate")); //
		tr0006hEntity.setTrxNewStart((Date) param.get("newStart")); //
		tr0006hEntity.setTrxNewEnd((Date) param.get("newEnd")); //
		tr0006hEntity.setTrxInterest(param.get("p_Interest").toString()); //
		tr0006hEntity.setTrxPremiumClient(Param.getBdWithDef(param, "premiumValueClient")); //
		tr0006hEntity.setTrxDueClient((Date) param.get("clientDate")); //
		tr0006hEntity.setTrxPremiumRe(Param.getBdWithDef(param, "p_ReinsuranceAmt")); //
		tr0006hEntity.setTrxDueRe(formatDateId.parse(Param.getStr(param, "p_ReDate"))); //\
		tr0006hEntity.setTrxDataStatus("11");
		if(isReas){
			tr0006hEntity.setTrxDataStatus("12");
		}
		tr0006hEntity.setTrxDataStatus("11");
		tr0006hEntity.setTrxCreatedBy(param.get(Param.USER).toString());
		tr0006hEntity.setTrxCreatedOn((Date) param.get("createOn"));
		tr0006hEntity.setTrxClass("FAC");
		
		if(isUpload){
			//Reset some values
			tr0006hEntity.setTrxTsiAmount(Param.getBdWithDef(param, "p_TsiAmount"));
			tr0006hEntity.setTrxNewPortion(Param.getBdWithDef(param, "p_NewPortion"));
			tr0006hEntity.setTrxDate((Date) param.get("appDate")); 
			tr0006hEntity.setTrxNewStart((Date) param.get("newStart")); 
			tr0006hEntity.setTrxNewEnd((Date) param.get("newEnd")); 
			
			tr0006hEntity.setTrxPremiumRe(Param.getBdWithDef(param, "NET_TOU_VALUE")); 
			
			Date trxDate = (Date) param.get("appDate");
			Calendar c = Calendar.getInstance();
	        c.setTime(trxDate);
	        
			String cliMeth = param.get("CLI_METH").toString();
			if (cliMeth == "90"){
				Integer cliMethInt = Integer.valueOf(cliMeth);
				c.add(Calendar.DATE, cliMethInt);
			} else {
				Integer cliMethInt = 4;
				c.add(Calendar.MONTH, cliMethInt);
			}
			
			String reMeth = param.get("RE_METH").toString();
			if (cliMeth == "90"){
				Integer reMethInt = Integer.valueOf(cliMeth);
				c.add(Calendar.DATE, reMethInt);
			} else {
				Integer reMethInt = 4;
				c.add(Calendar.MONTH, reMethInt);
			}

			tr0006hEntity.setTrxDueClient(c.getTime()); 
			tr0006hEntity.setTrxDueRe(c.getTime()); 
			
			String format = Param.getStr(param, "p_Format");
			if("16".equals(format)){
				//16 = AJK
				tr0006hEntity.setTrxBrdxId(Param.getStr(param, "borderouxFromFile"));
				tr0006hEntity.setTrxPartyName(Param.getStr(param, "namaPesertaFromFile")); 
				tr0006hEntity.setTrxBirthDate(formatDateMon2.parse(Param.getStr(param, "tglLahirFromFile")));
				tr0006hEntity.setTrxParticipantId(Param.getStr(param, "noPesertaFromFile"));
				tr0006hEntity.setTrxDuration(Param.getBdWithDef(param, "masaArusansiFromFile"));
				tr0006hEntity.setTrxGpRate(Param.getBdWithDef(param, "rateFromFile"));
				tr0006hEntity.setTrxGp100(Param.getBdWithDef(param, "gp100FromFile"));
				tr0006hEntity.setTrxSubsDate(formatDateId.parse(Param.getStr(param, "tglPendaftaranFromFile")));
				tr0006hEntity.setTrxBenefit(Param.getStr(param, "benefitFromFile"));
				tr0006hEntity.setTrxPremiumClient(Param.getBdWithDef(param, "gp100FromFile")); 
				tr0006hEntity.setTrxInterest(param.get("p_Interest").toString()
						.concat(" - ")
						.concat(Param.getStr(param, "borderouxFromFile"))
						.concat(" - ")
						.concat(Param.getStr(param, "noPesertaFromFile"))
						.concat(" - ")
						.concat(Param.getStr(param, "namaPesertaFromFile"))
						.concat(" - ")
						.concat(Param.getBdWithDef(param, "gp100FromFile").toString()));
				tr0006hEntity.setTrxClientId(Param.getStr(param, "source"));
				
				tr0006hEntity.setTrxNewStart(formatDateId.parse(Param.getStr(param, "startDateFromFile")));
				tr0006hEntity.setTrxNewEnd(formatDateId.parse(Param.getStr(param, "endDateFromFile")));
				tr0006hEntity.setTrxCreditLimit(Param.getBdWithDef(param, "p_TsiAmount"));
				tr0006hEntity.setTrxBrokrFee(Param.getBdWithDef(param, "p_BrokerFeeOnly"));
				tr0006hEntity.setTrxComm(Param.getBdWithDef(param, "p_BrokerFeeOnly"));
				BigDecimal trxShare = (Param.getBdWithDef(param, "p_TsiAmount")).divide(Param.getBdWithDef(param, "p_Share"), 2, RoundingMode.HALF_UP);
				tr0006hEntity.setTrxReShare(trxShare);
			}else{
				//13 = MH
				tr0006hEntity.setTrxPartyName(Param.getStr(param, "vesselFromFile"));
				tr0006hEntity.setTrxDescType(Param.getStr(param, "typeFromFile"));
				tr0006hEntity.setTrxDescYob(Param.getBdWithDef(param, "yobFromFile").toString());
				tr0006hEntity.setTrxDescGrt(Param.getBdWithDef(param, "grtFromFile").toString());
				tr0006hEntity.setTrxDescClass(Param.getStr(param, "classFromFile"));
				tr0006hEntity.setTrxDescFlag(Param.getStr(param, "flagFromFile"));
				tr0006hEntity.setTrxDescConst(Param.getStr(param, "constructionFromFile"));
				BigDecimal trxPreCliForMH = (Param.getBdWithDef(param, "CLI_PRERATE")).multiply(Param.getBdWithDef(param, "tsiAmtFromFile")); //???
				tr0006hEntity.setTrxPremiumClient(trxPreCliForMH);
				tr0006hEntity.setTrxInterest(param.get("p_Interest").toString()
						.concat(" - ")
						.concat(Param.getStr(param, "vesselFromFile"))
						.concat(" - ")
						.concat(Param.getStr(param, "currId"))
						.concat(" - ")
						.concat(Param.getBdWithDef(param, "p_TsiAmount").toString()));
				tr0006hEntity.setTrxClientId(Param.getStr(param, "source"));
				
				tr0006hEntity.setTrxNewStart(formatDateId.parse(Param.getStr(param, "startDateFromFile")));
				tr0006hEntity.setTrxNewEnd(formatDateId.parse(Param.getStr(param, "endDateFromFile")));
				
				BigDecimal trxShare = (Param.getBdWithDef(param, "p_Share"));
				tr0006hEntity.setTrxReShare(trxShare);
				
			}
			String[] bits = Param.getStr(param, "fileName").split("\\\\");
			String fileNameOnly = bits[bits.length-1];
			
			tr0006hEntity.setTrxFileName(fileNameOnly);
			tr0006hEntity.setTrxClaimStatus("0");
		}
		
		return tr0006hEntity;
		
	}

	@Override
	public List<Map<String, Object>> readFile(Map<String, Object> paramFromUI) throws IOException {
		Long begin = System.currentTimeMillis();
		String fileName = path.concat(Param.getStr(paramFromUI, "fileName"));
		String format = Param.getStr(paramFromUI, "p_Format");
		File myFile = new File(fileName);
		FileInputStream fis = new FileInputStream(myFile); 
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
		
		DataFormatter df = new DataFormatter();
		
		BigDecimal sumPremiNett = BigDecimal.ZERO;
		BigDecimal sumRiComm = BigDecimal.ZERO;
		BigDecimal sumBrokerageFee = BigDecimal.ZERO;
		BigDecimal brokerFee = BigDecimal.ZERO;
		
		BigDecimal riComm = BigDecimal.ZERO;
		BigDecimal brokerageFee = BigDecimal.ZERO;
		BigDecimal brokerFeeEach = BigDecimal.ZERO;
		
		Iterator<Row> rowIterator = mySheet.iterator();
		List<Map<String, Object>> listOfContents = new ArrayList<Map<String, Object>>();
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next(); 
			
			Boolean isNotEmpty = isNotEmpty(row, "16".equals(format));
			
			if(isNotEmpty) {
				Map<String, Object> param = new HashMap<String, Object>(paramFromUI);
				Iterator<Cell> cellIterator = row.cellIterator();
				
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next(); 
					int colNum = cell.getColumnIndex();
								
					if("16".equals(format)) { 	//AJK
						if(colNum==10) {
							param.put("tsiAmtFromFile", getNumericCell(cell));
						}else if(colNum==9) {
							param.put("endDateFromFile", formatDateId.format(cell.getDateCellValue()));
						}else if(colNum==8) {
							param.put("startDateFromFile", formatDateId.format(cell.getDateCellValue()));
						}else if(colNum==1) {
							param.put("borderouxFromFile", cell.getStringCellValue());
						}else if(colNum==3) {
							param.put("namaPesertaFromFile", cell.getStringCellValue());
						}else if(colNum==4) {
							param.put("tglLahirFromFile", cell.getStringCellValue());
						}else if(colNum==6) {
							param.put("noPesertaFromFile", cell.getStringCellValue());
						}else if(colNum==7) {
							param.put("masaArusansiFromFile", getNumericCell(cell));
						}else if(colNum==11) {
							param.put("share", getNumericCell(cell));
						}else if(colNum==12){
							param.put("rateFromFile", getNumericCell(cell));
						}else if(colNum==14){
							riComm = getNumericCell(cell);
							param.put("riCommOnly", riComm);
							//RI Comm
							sumRiComm = sumRiComm.add(getNumericCell(cell));
						}else if(colNum==15){
							brokerageFee = getNumericCell(cell);
							//Brokerage Fee
							sumBrokerageFee = sumBrokerageFee.add(getNumericCell(cell));
							param.put("brokerFeeOnly", brokerageFee);
						}
						else if(colNum==19){
							param.put("tglPendaftaranFromFile", formatDateId.format(cell.getDateCellValue()));
						}else if(colNum==20){
							param.put("benefitFromFile", cell.getStringCellValue());
						}else if(colNum==16){
							param.put("premi", getNumericCell(cell));
							//Premi Nett
							sumPremiNett = sumPremiNett.add(getNumericCell(cell));
						}else if(colNum==21){
							param.put("type", cell.getStringCellValue());
						}else if(colNum==22){
							param.put("source", df.formatCellValue(cell));
						}
						//AJK file do not contain CurrId. CurrId use field from UI instead.
						param.put("currFromFile", Param.getStr(param, "p_CurrId"));
						brokerFeeEach = brokerageFee.add(riComm);
						param.put("brokerFeeEach", brokerFeeEach);
					} else {
						//13 = BOR/MH
						if(colNum==12) {
							param.put("tsiAmtFromFile", getNumericCell(cell));
						}else if(colNum==11) {
							//log.info("11 : {}",cell.getStringCellValue());
							param.put("currFromFile", cell.getStringCellValue());
						}else if(colNum==10) {
							//log.info("10: {}",formatDateId.format(cell.getDateCellValue()));
							param.put("endDateFromFile", formatDateId.format(cell.getDateCellValue()));
						}else if(colNum==9) {
							//log.info("9: {}",formatDateId.format(cell.getDateCellValue()));
							param.put("startDateFromFile", formatDateId.format(cell.getDateCellValue()));
						}else if(colNum==2) {
							param.put("vesselFromFile", cell.getStringCellValue());
						}else if(colNum==3) {
							param.put("typeFromFile", cell.getStringCellValue());
						}else if(colNum==4) {
							param.put("yobFromFile", getNumericCell(cell));
						}else if(colNum==5) {
							param.put("grtFromFile", getNumericCell(cell));
						}else if(colNum==6) {
							param.put("classFromFile", cell.getStringCellValue());
						}else if(colNum==7) {
							param.put("flagFromFile", cell.getStringCellValue());
						}else if(colNum==8) {
							param.put("constructionFromFile", cell.getStringCellValue());
						}else if(colNum==13) {
							param.put("rate", getNumericCell(cell));
						}else if(colNum==14) {
							param.put("grossPremi", getNumericCell(cell));
						}else if(colNum==15){
							param.put("brokerFeeEach", getNumericCell(cell));
							//RI Comm
							sumRiComm = sumRiComm.add(getNumericCell(cell));
						}else if(colNum==16){
							param.put("premi", getNumericCell(cell));
							//Premi Nett
							sumPremiNett = sumPremiNett.add(getNumericCell(cell));
						}else if(colNum==17){
							param.put("type", cell.getStringCellValue());
						}else if(colNum==18){
							param.put("source", df.formatCellValue(cell));
						}
					}
				}
				param.put("sumPremiNett", sumPremiNett);
				brokerFee = sumRiComm.add(sumBrokerageFee);
				param.put("brokerFee", brokerFee);
				listOfContents.add(param);
				
			}
		}
		log.info("READ FILE elapseTime : {}", System.currentTimeMillis()-begin);
		return listOfContents;
	}
	
	private List<TR0006CEntity> calculateValues(String voucherId, String trxId, BigDecimal tsiAmount, BigDecimal premiumBuy, BigDecimal premiumSell, Date startDate, Date endDate, Map<String, Object> params){
		BigDecimal periodVal = BigDecimal.ONE;
		TR0006AEntity interest = tr0006ARepo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId).get(0);
		Boolean isBordoClient = (Boolean) params.get("isBordoClient");
		
		if(premiumBuy == null)
			premiumBuy = BigDecimal.ZERO;
		
		if(premiumSell == null)
			premiumSell = BigDecimal.ZERO;
		
		if(startDate != null && endDate != null){
			//modul upload
			interest.setTrxInsStart(startDate);
			interest.setTrxInsEnd(endDate);
		}
		
		BigDecimal premiumRe = BigDecimal.ZERO;				
		BigDecimal sumShareRe = BigDecimal.ZERO;
		BigDecimal sumPremiRe = BigDecimal.ZERO;
		BigDecimal sumRiCommRe =BigDecimal.ZERO;
		
		List<TR0006BEntity> tReinsurance = tr0006Brepo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId);
		
		for(int i=0; i<tReinsurance.size(); i++) {
			sumShareRe = sumShareRe.add(tReinsurance.get(i).getTrxInsShare());
			sumPremiRe = sumPremiRe.add(tReinsurance.get(i).getTrxInsPremium());
			sumRiCommRe = sumRiCommRe.add(tReinsurance.get(i).getTrxInsBfee());
		}
			
		if(tReinsurance.size() >0) {
			sumPremiRe = sumPremiRe.divide(new BigDecimal(tReinsurance.size()));
			sumRiCommRe = sumRiCommRe.divide(new BigDecimal(tReinsurance.size()));
		}
				
		Boolean flagSummary = false;
		
		if(sumShareRe.compareTo(new BigDecimal(100))>0) {
			sumShareRe = new BigDecimal(100);
			flagSummary = true;	
		}
			
		BigDecimal period = interest.getDiffDaysInsured();
		
		BigDecimal tsi = tsiAmount;
		if (tsiAmount == null){
			//modul entry
			tsi = BigDecimal.ONE;
		}
		
		BigDecimal shareCli = BigDecimal.ZERO;
		List<TR0006Entity> ciTable = tr0006repo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId);
		if(ciTable.size()>0) {
			//modul entry
			shareCli = ciTable.get(0).getTrxShare();
		}
			
		// Premium (re) = sumInsured (ii) * premiumRate (%) (re) * weighted (%) (ii);
		BigDecimal weight = interest.getTrxWeightRate();
		for(int i=0; i<tReinsurance.size(); i++) {
			BigDecimal premiumReSub = BigDecimal.ZERO;
			BigDecimal share = tReinsurance.get(i).getTrxInsShare();
			BigDecimal premi = tReinsurance.get(i).getTrxInsPremium();
			premiumReSub = tsi.multiply(premi.divide(new BigDecimal(100))).multiply(weight.divide(new BigDecimal(100))).multiply(share.divide(new BigDecimal(100))).multiply(periodVal);
			
			premiumRe = premiumRe.add(premiumReSub);
		}
			
		if(flagSummary)
			premiumRe = premiumBuy;
			//premiumRe = tsi.multiply(sumPremiRe.divide(new BigDecimal(100))).multiply(weight.divide(new BigDecimal(100))).multiply(sumShareRe.divide(new BigDecimal(100))).multiply(periodVal);
		
		if(tsiAmount == null){
			//modul entry
			premiumRe = premiumBuy;
		}
		
		//bFee = Premium (re) * RI Comm (%);
		BigDecimal bFeeBuy = premiumRe.multiply(sumRiCommRe.divide(new BigDecimal(100)));
		
		//Total (re) = Premium (re) - bFee;
		BigDecimal totalRe = premiumRe.subtract(bFeeBuy);
		
		//Premium (cli) = sumInsured (ii) * premiumRate (%) (ii) * weighted (%) (ii);
		BigDecimal premiumCli = BigDecimal.ZERO;
		BigDecimal premi = interest.getTrxPremiumRate();
		//premiumCli = tsi.multiply(shareCli.divide(new BigDecimal(100))).multiply(weight.divide(new BigDecimal(100))).multiply(premi.divide(new BigDecimal(100))).multiply(periodVal);
		premiumCli = premiumSell;
		
		if(tsiAmount == null){
			premiumCli = premiumSell;
		}
		
		BigDecimal deductionII = interest.getTrxDeducPct();
		//bFee = Premium (cli) * Deduction (%);
		BigDecimal bFeeSell = premiumCli.multiply(deductionII.divide(new BigDecimal(100)));
		//vat = 0;
		BigDecimal vatSell = BigDecimal.ZERO;
		
		BigDecimal discount = BigDecimal.ZERO;
		if(isBordoClient){
			discount = (BigDecimal) params.get("discount");
		}
		
		//Total (cli) = Premium - bFee - discount;
		BigDecimal totalCli = premiumCli.subtract(bFeeSell).subtract(discount);
		
		BigDecimal taxRate003 = commonServiceImpl.getTaxRateh003();
		BigDecimal taxRate009 = commonServiceImpl.getTaxRateh009();
		//Net Income = Total (cli) - Total (re) / taxRate009;
		BigDecimal netIncome = (totalCli.subtract(totalRe)).divide(taxRate009);
		//vat = Premium (re) * RI Comm (%) * ppn;
		BigDecimal vatBuy = netIncome.multiply(taxRate003);
		
		TR0006CEntity values = new TR0006CEntity();
		if(premiumBuy == null){
			//Client
			values.setTrxPremiumBuy(premiumRe);
		} else {
			values.setTrxPremiumBuy(premiumBuy);
		}
		if(premiumSell == null){
			//Reas
			values.setTrxPremiumSell(premiumCli);
		} else {
			values.setTrxPremiumSell(premiumSell);
		}
		values.setTrxBfeeBuy(bFeeBuy);
		values.setTrxVatBuy(vatBuy);
		values.setTrxBfeeSell(bFeeSell);
		values.setTrxDiscSell(discount);
		
		BigDecimal netTou = values.getTrxPremiumBuy().subtract(values.getTrxBfeeBuy());
		BigDecimal netTtl = values.getTrxPremiumSell().subtract(values.getTrxBfeeSell()).subtract(values.getTrxDiscSell());
		values.setTrxNetTou(netTou);
		values.setTrxNetTtl(netTtl);
		
		List<TR0006CEntity> listValues = new ArrayList<TR0006CEntity>();
		listValues.add(values);
		
		params.put("newStart", startDate); //dari upload
		params.put("newEnd", endDate); //dari upload
		params.put("premiumValueClient", values.getTrxPremiumSell());
		params.put("premiumValueRe", values.getTrxPremiumBuy());
		params.put("reDate", endDate); //dari upload
		params.put("NET_TOU_VALUE", values.getTrxPremiumBuy().subtract(bFeeBuy));
		params.put("CLI_PRERATE", interest.getTrxPremiumRate());
		return listValues;
	}

	@Override
	public Object isFileNameExist(Map<String, Object> param) {
		String fileName = Param.getStr(param, "fileName");
		Map<String, Object> result = new HashMap<String, Object>();
		List<TR0006HEntity> listWithFileName = new ArrayList<TR0006HEntity>();
		listWithFileName = tR0006HRepo.findByTrxFileNameEndsWith(fileName);
		
		if(listWithFileName.size()>0){
			result.put("isFileNameExist", true);
		}else{
			result.put("isFileNameExist", false);
		}
		
		
		return result;
	}
	
	/*public Object isSourceCodeExist(Map<String, Object> param) throws IOException {
		List<Map<String, Object>> objectFromFile = readFile(param);
		String aaa = Param.getStr(objectFromFile.get(0), "source");
		
		MA0015Entity ma15entity = ma0015repo.findb
		return null;
	}*/

	/*
	 * yang digunakan 
	 * */
	@Override
	public Object saveUpload(Map<String, Object> param) throws ParseException {
		String p_TrxId = param.get("p_TrxId").toString(); //dari UI
		String p_TrxVoucherId = param.get("p_TrxVoucherId").toString(); //dari UI
		String coverCode = param.get("p_CoverCode").toString(); //dari UI
		BigDecimal premiumValueClient = Param.getBdWithDef(param, "sumPremi");
		String currId = Param.getStr(param, "p_CurrId");
		String typeOfFile = Param.getStr(param, "typeOfFile").trim();
		Boolean isReas = false;
		
		String[] bits = Param.getStr(param, "fileName").split("\\\\");
		String fileNameOnly = bits[bits.length-1];
		
		if(typeOfFile.contains("R")){
			isReas = true;
		}
		
		BigDecimal brkrFeeValue = Param.getBdWithDef(param, "sumBrokerFee");
		
		/*
		 * Mencari TAX RATE
		 * */
		MA0014Entity ma0014Entity = ma0014repo.findByPaChildCode("TAXRATEH003");
		BigDecimal ppnRate = (new BigDecimal(ma0014Entity.getPaChildValue()).divide(BD_100));
		
		BigDecimal taxinBfValue = brkrFeeValue.multiply(ppnRate);
		
		/*
		 * Mencari tanggal aplikasi
		 * */
		Calendar calTrx = Calendar.getInstance();
		Date appDate=commonServiceImpl.getAppDate();
		calTrx.setTime(appDate);
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		Date now = Calendar.getInstance().getTime();
		
		MA0014Entity ma14s7 = ma0014repo.findByPaChildCode("SYSDATE007");
		MA0014Entity ma14s3 = ma0014repo.findByPaChildCode("SYSDATE003");
		
		Integer valueDay = 0;
		if(isReas){
			if (ma14s7 != null)
				valueDay = Integer.valueOf(ma14s7.getPaChildValue());
		} else {
			if (ma14s3 != null)
				valueDay = Integer.valueOf(ma14s3.getPaChildValue());
		}
		
		Date dueDate = formatDateId.parse(Param.getStr(param, "p_DueDate")); //dari UI
		
		Map<String, BigDecimal> mapValue = new HashMap<String, BigDecimal>();
		mapValue.put("PREMIUM_VALUE", premiumValueClient);
		mapValue.put("BRKR_FEE_VALUE", brkrFeeValue);
		mapValue.put("TAXIN_BF_VALUE", taxinBfValue);
		
		
		
		List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
		
		/* Find Client(s) 
		 * */
		Integer numOfRow = (Integer) param.get("numberOfRow");
		List<String> listOfSurce = (List<String>) param.get("listOfSource");
		List<BigDecimal> listOfPremi = (List<BigDecimal>) param.get("listOfPremi");
		List<BigDecimal> listOfBrokerFee = (List<BigDecimal>) param.get("listOfBrokerFee");
		
		/*
		 * Mencari Curr Rate
		 * */
		BigDecimal dataRate = ma0015Arepo.findByExDateAndExCurrId(appDate, currId).getExKmkRate();
		BigDecimal cashValue = BigDecimal.ZERO;
		BigDecimal cashValueBrkr = BigDecimal.ZERO;
		BigDecimal cashValueTaxIn = BigDecimal.ZERO;
		List<BigDecimal> listOfCashValue = new ArrayList<BigDecimal>();
		List<BigDecimal> listOfCashValueBrkr = new ArrayList<BigDecimal>();
		List<BigDecimal> listOfCashValueTaxIn = new ArrayList<BigDecimal>();
		
		BigDecimal taxRate009 = commonServiceImpl.getTaxRateh009();
		for (int i = 0; i < listOfPremi.size(); i++) {
			cashValue = listOfPremi.get(i).multiply(dataRate);
			cashValueBrkr = (listOfBrokerFee.get(i).divide(taxRate009, 2, RoundingMode.HALF_UP)).multiply(dataRate);
			cashValueTaxIn = (listOfBrokerFee.get(i).divide(taxRate009, 2, RoundingMode.HALF_UP)).multiply(ppnRate).multiply(dataRate);
			listOfCashValue.add(cashValue);
			listOfCashValueBrkr.add(cashValueBrkr);
			listOfCashValueTaxIn.add(cashValueTaxIn);
		}
		
		/*
		 * Ada request untuk membuat pencatatan data di TR01, TR02, dan TR12 menjadi 1 baris saja
		 * dan semua Premi dan Broker Fee disum.
		 * Sehingga list di atas, dibongkar lagi sbb
		 */
		BigDecimal sumPremi = BigDecimal.ZERO;
		BigDecimal sumBrokerFee = BigDecimal.ZERO;
		BigDecimal sumCashValue = BigDecimal.ZERO;
		BigDecimal sumCashValueBrkr = BigDecimal.ZERO;
		BigDecimal sumCashValueTaxIn = BigDecimal.ZERO;
		for (int i = 0; i < listOfPremi.size(); i++) {
			sumPremi = sumPremi.add(listOfPremi.get(i));
			sumCashValue = sumCashValue.add(listOfCashValue.get(i));
			sumCashValueBrkr = sumCashValueBrkr.add(listOfCashValueBrkr.get(i));
			sumCashValueTaxIn = sumCashValueTaxIn.add(listOfCashValueTaxIn.get(i));
			sumBrokerFee = sumBrokerFee.add(listOfBrokerFee.get(i));
		}
		
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		
		if(!isReas){
			List<TR0006EEntity> tChecklistCli = tr0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(p_TrxId, p_TrxVoucherId, CLIENT_TYPE);
			TR0006Entity tr0006Entity = tr0006repo.findOneClientByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
			ThreadPoolTaskExecutor executorClient = threadPoolConfig.executorGeneral("Upload_Ext_CLIENT-");
			List<SaveClientUploadRunnable> listClient = new ArrayList<SaveClientUploadRunnable>();
			//for (int rowNum = 0 ; rowNum<numOfRow; rowNum++){
				SaveClientUploadRunnable clientUploadRunnable = new SaveClientUploadRunnable();
				clientUploadRunnable.setAppDate(appDate);
				clientUploadRunnable.setCurrId(currId);
				clientUploadRunnable.setDataRate(dataRate);
				clientUploadRunnable.setDueDate(dueDate);
				clientUploadRunnable.setFormatDate(formatDate);
				/*clientUploadRunnable.setListOfBrokerFee(listOfBrokerFee);
				clientUploadRunnable.setListOfCashValue(listOfCashValue);
				clientUploadRunnable.setListOfCashValueBrkr(listOfCashValueBrkr);
				clientUploadRunnable.setListOfCashValueTaxIn(listOfCashValueTaxIn);
				clientUploadRunnable.setListOfPremi(listOfPremi);*/
				clientUploadRunnable.setSumBrokerFee(sumBrokerFee);
				clientUploadRunnable.setSumCashValue(sumCashValue);
				clientUploadRunnable.setSumCashValueBrkr(sumCashValueBrkr);
				clientUploadRunnable.setSumCashValueTaxIn(sumCashValueTaxIn);
				clientUploadRunnable.setSumPremi(sumPremi);
				clientUploadRunnable.setListOfSurce(listOfSurce);
				clientUploadRunnable.setmA0005Repo(mA0005Repo);
				clientUploadRunnable.setMa0018Repo(ma0018Repo);
				clientUploadRunnable.setMapValue(mapValue);
				clientUploadRunnable.setMonth(month);
				clientUploadRunnable.setNow(now);
				clientUploadRunnable.setP_TrxVoucherId(p_TrxVoucherId);
				clientUploadRunnable.setParam(param);
				clientUploadRunnable.setPpnRate(ppnRate);
				clientUploadRunnable.setRowNum(0);
				clientUploadRunnable.setTr0001repo(tr0001repo);
				clientUploadRunnable.setTr0002repo(tr0002repo);
				clientUploadRunnable.setTr0012repo(tr0012repo);
				clientUploadRunnable.setTr0006Entity(tr0006Entity);
				clientUploadRunnable.setVoucherComponent(voucherComponent);
				clientUploadRunnable.setYear(year);
				clientUploadRunnable.setCoverCode(coverCode);
				clientUploadRunnable.setFileName(fileNameOnly);
				clientUploadRunnable.setTaxRate009(taxRate009);
				
				listClient.add(clientUploadRunnable);
				executorClient.execute(clientUploadRunnable);
			//}
			executorClient.shutdown();
			executorClient.destroy();
			
			List<TR0001Entity> listOfTr0001Entity = new ArrayList<TR0001Entity>();
			List<TR0002Entity> listOfTr0002Entity = new ArrayList<TR0002Entity>();
			List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
			
			for(SaveClientUploadRunnable saveClientUpload : listClient){
				listOfTr0001Entity.addAll(saveClientUpload.getListOfTr0001Entity());
				listOfTr0002Entity.addAll(saveClientUpload.getListOfTr0002Entity());
				tr12Entities.addAll(saveClientUpload.getTr12Entities());
			}
			
			tr0001repo.saveAll(listOfTr0001Entity);
			tr0002repo.saveAll(listOfTr0002Entity);
			tr0012repo.saveAll(tr12Entities);
			
			String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
			String voucherIdT3 = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
			TR0006AEntity tr0006a = tr0006ARepo.findPeriodByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
			TR0006BEntity tr0006b = tr0006Brepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(p_TrxId, p_TrxVoucherId, "0");
			TR0003Entity tr3 = new TR0003Entity();
			
			tr3.setTrxType(p_TrxId);
			tr3.setTrxVoucherId(voucherIdT3);
			tr3.setTrxDescription(param.get("p_Interest").toString());
			tr3.setTrxClient(Param.getStr(param, "sourceTop"));
			tr3.setTrxDate(appDate);
			tr3.setTrxAssured(tr0006Entity.getTrxClient());
			tr3.setTrxCoverCode(coverCode);
			tr3.setTrxInsOfficer(tr0006Entity.getTrxOfficer());
			//tr3.setTrxInsInsured(param.get("p_Interest").toString()); 
			tr3.setTrxInsInsured(param.get("p_Reinsurance").toString());
			tr3.setTrxInsStart(tr0006a.getTrxInsStart());
			tr3.setTrxInsEnd(tr0006a.getTrxInsEnd());
			tr3.setTrxCurrId(Param.getStr(param, "currency"));
			tr3.setTrxCurrRate(dataRate);
			tr3.setTrxAmountDue(mapValue.get("PREMIUM_VALUE")); 
			//tr3.setTrxAmountDue(listOfBrokerFee.get(rowNum));
			tr3.setTrxStatusData("11");
			tr3.setCreateOn(now);
			tr3.setCreateBy(param.get(Param.USER).toString());
			tr3.setTrxOldVoucherId(p_TrxVoucherId);
			
			MA0005Entity ma0005EntityForReport = mA0005Repo.findByCliCode(listOfSurce.get(0).toString());
			Map<String, Object> journal = new HashMap<String, Object>();
			journal.put("voucherId", tr3.getTrxVoucherId());
			journal.put("name", ma0005EntityForReport.getCliName());
			journal.put("document", "Closing Client - DN");
			journal.put("type", "DEBIT NOTE");
			journal.put("insSub", 1);
			journal.put("isComm", Boolean.FALSE);
			String description2Add = "";
			String fileName = "Extension - DN - " + ma0005EntityForReport.getCliName();
			journal.put("p_Description2Add", description2Add);
			
			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '1', '" + ma0005EntityForReport.getCliName() + "', 'DEBIT NOTE')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '1', '" + ma0005EntityForReport.getCliName() + "', '" + fileName + "', 'DEBIT NOTE" + "')\">"
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			journal.put("action", action);
			table.add(journal);
			
			int noRow = 1;
			List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
				TR0003AEntity tr3a = new TR0003AEntity();
				tr3a.setTrxType(tr3.getTrxType());
				tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
				tr3a.setTrxNoRow(noRow);
				
				tr3a.setTrxDueDate(dueDate);
				tr3a.setTrxRemarks("-");
				
				tr3a.setTrxDueAmount(mapValue.get("PREMIUM_VALUE"));
				tr3a.setTrxDueDate(dueDate);
				tr3a.setTrxTrxClass("OP");
				
				tr3aEntities.add(tr3a);
				
				noRow++;
			tr0003Repo.save(tr3);
			tr0003ARepo.saveAll(tr3aEntities);
		}
		
		else if(isReas){
			Long startSAVE = System.currentTimeMillis();
			List<TR0006EEntity> tChecklistReins = tr0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(p_TrxId, p_TrxVoucherId, REINSURANCE_TYPE);
			TR0006Entity tr0006Entity = tr0006repo.findOneClientByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
			ThreadPoolTaskExecutor executorReas = threadPoolConfig.executorGeneral("Upload_Ext_REAS-");
			List<SaveReasUploadRunnable> listReas = new ArrayList<SaveReasUploadRunnable>();
				SaveReasUploadRunnable reasUploadRunnable = new SaveReasUploadRunnable();
				reasUploadRunnable.setAppDate(appDate);
				reasUploadRunnable.setCurrId(currId);
				reasUploadRunnable.setDataRate(dataRate);
				reasUploadRunnable.setDueDate(dueDate);
				reasUploadRunnable.setFormatDate(formatDate);
				/*reasUploadRunnable.setListOfBrokerFee(listOfBrokerFee);
				reasUploadRunnable.setListOfCashValue(listOfCashValue);
				reasUploadRunnable.setListOfCashValueBrkr(listOfCashValueBrkr);
				reasUploadRunnable.setListOfCashValueTaxIn(listOfCashValueTaxIn);
				reasUploadRunnable.setListOfPremi(listOfPremi);*/
				reasUploadRunnable.setSumBrokerFee(sumBrokerFee);
				reasUploadRunnable.setSumCashValue(sumCashValue);
				reasUploadRunnable.setSumCashValueBrkr(sumCashValueBrkr);
				reasUploadRunnable.setSumCashValueTaxIn(sumCashValueTaxIn);
				reasUploadRunnable.setSumPremi(sumPremi);
				reasUploadRunnable.setListOfSurce(listOfSurce);
				reasUploadRunnable.setmA0005Repo(mA0005Repo);
				reasUploadRunnable.setMa0018Repo(ma0018Repo);
				reasUploadRunnable.setMapValue(mapValue);
				reasUploadRunnable.setMonth(month);
				reasUploadRunnable.setNow(now);
				reasUploadRunnable.setP_TrxVoucherId(p_TrxVoucherId);
				reasUploadRunnable.setParam(param);
				reasUploadRunnable.setPpnRate(ppnRate);
				reasUploadRunnable.setRowNum(0);
				reasUploadRunnable.setTr0001repo(tr0001repo);
				reasUploadRunnable.setTr0002repo(tr0002repo);
				reasUploadRunnable.setTr0012repo(tr0012repo);
				reasUploadRunnable.setTr0006Entity(tr0006Entity);
				reasUploadRunnable.setVoucherComponent(voucherComponent);
				reasUploadRunnable.setYear(year);
				reasUploadRunnable.setCoverCode(coverCode);
				reasUploadRunnable.setFileName(fileNameOnly);
				reasUploadRunnable.setTaxRate009(taxRate009);
				
				listReas.add(reasUploadRunnable);
				executorReas.execute(reasUploadRunnable);
			executorReas.shutdown();
			executorReas.destroy();
			List<TR0001Entity> listOfTr0001Entity = new ArrayList<TR0001Entity>();
			List<TR0002Entity> listOfTr0002Entity = new ArrayList<TR0002Entity>();
			List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
			
			for(SaveReasUploadRunnable saveReasUpload : listReas){
				listOfTr0001Entity.addAll(saveReasUpload.getListOfTr0001Entity());
				listOfTr0002Entity.addAll(saveReasUpload.getListOfTr0002Entity());
				tr12Entities.addAll(saveReasUpload.getTr12Entities());
			}
			tr0001repo.saveAll(listOfTr0001Entity);
			tr0002repo.saveAll(listOfTr0002Entity);
			tr0012repo.saveAll(tr12Entities);
			//di sini set TR3 dan TR3A karena DC Notes hanya dicetak 1x
			String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
			String voucherIdT3 = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
			TR0006Entity tr0006 = tr0006repo.findOneClientByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
			String clientAssured = tr0006.getTrxClient();
			TR0006AEntity tr0006a = tr0006ARepo.findPeriodByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
			TR0006BEntity tr0006b = tr0006Brepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(p_TrxId, p_TrxVoucherId, "0");
			TR0003Entity tr3 = new TR0003Entity();
			tr3.setTrxType(p_TrxId);
			tr3.setTrxVoucherId(voucherIdT3);
			tr3.setTrxDescription(param.get("p_Interest").toString());
			tr3.setTrxClient(Param.getStr(param, "sourceTop"));
			tr3.setTrxDate(appDate);
			tr3.setTrxAssured(clientAssured);
			tr3.setTrxCoverCode(coverCode);
			tr3.setTrxInsOfficer(tr0006.getTrxOfficer());
			//tr3.setTrxInsInsured(param.get("p_Interest").toString()); 
			tr3.setTrxInsInsured(param.get("p_Client").toString());
			tr3.setTrxInsStart(tr0006a.getTrxInsStart());
			tr3.setTrxInsEnd(tr0006a.getTrxInsEnd());
			tr3.setTrxCurrId(Param.getStr(param, "currency"));
			tr3.setTrxCurrRate(dataRate);
			tr3.setTrxAmountDue(mapValue.get("PREMIUM_VALUE")); 
			//tr3.setTrxAmountDue(listOfBrokerFee.get(rowNum));
			tr3.setTrxStatusData("11");
			tr3.setCreateOn(now);
			tr3.setCreateBy(param.get(Param.USER).toString());
			tr3.setTrxOldVoucherId(p_TrxVoucherId);
			
			Map<String, Object> journal = new HashMap<String, Object>();
			MA0005Entity ma0005EntityForReport = mA0005Repo.findByCliCode(listOfSurce.get(0).toString());
			journal.put("voucherId", tr3.getTrxVoucherId());
			journal.put("name", ma0005EntityForReport.getCliName());
			journal.put("document", "Closing Reinsurance - CN");
			journal.put("type", "CREDIT NOTE");
			journal.put("insSub", 1);
			journal.put("isComm", Boolean.FALSE);
			String description2Add = "";
			String fileName = "Extension - CN - " + ma0005EntityForReport.getCliName();
			journal.put("p_Description2Add", description2Add);
			
			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '1', '" + ma0005EntityForReport.getCliName() + "', 'CREDIT NOTE')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '1', '" + ma0005EntityForReport.getCliName() + "', '" + fileName + "', 'CREDIT NOTE" + "')\">"
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			journal.put("action", action);
			table.add(journal);
			
			int noRow = 1;
			List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
				TR0003AEntity tr3a = new TR0003AEntity();
				tr3a.setTrxType(tr3.getTrxType());
				tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
				tr3a.setTrxNoRow(noRow);
				
				tr3a.setTrxRemarks("-");
				
				tr3a.setTrxDueAmount((mapValue.get("PREMIUM_VALUE"))); 
				tr3a.setTrxDueDate(dueDate);
				tr3a.setTrxTrxClass("OP");
				
				tr3aEntities.add(tr3a);
				
				noRow++;
			tr0003Repo.save(tr3);
			tr0003ARepo.saveAll(tr3aEntities);
			log.info("SAVE DATA elapseTime : {}", System.currentTimeMillis()-startSAVE);
		}
		
		return table;
	}
	
	private Boolean isNotEmpty(Row row, Boolean isAjk) {
		int rowNum = row.getRowNum();
		if(rowNum <2)	//Header
			return Boolean.FALSE;
		
		Cell cellTsi;
		if(isAjk)
			cellTsi = row.getCell(10);
		else
			cellTsi = row.getCell(12);
		
		if(cellTsi == null)		//Empty
			return Boolean.FALSE;
		
		BigDecimal tsi;
		if(cellTsi.getCellType().equals(CellType.NUMERIC))
			tsi = new BigDecimal(cellTsi.getNumericCellValue());
		else 
			tsi = new BigDecimal(cellTsi.getStringCellValue());
		
		return tsi.signum() != 0;
	}
	
	@SuppressWarnings("deprecation")
	private BigDecimal getNumericCell(Cell cell) {
		BigDecimal num = BigDecimal.ZERO;
		if(cell.getCellType().equals(CellType.NUMERIC)) {
			num = new BigDecimal(cell.getNumericCellValue());
		}else {
			cell.setCellType(CellType.STRING);
			String val = cell.getStringCellValue();
			if(StringUtils.isNotBlank(val))
				num = new BigDecimal(val.replace(",", ""));
		}
		
		return num;
	}
	
}
