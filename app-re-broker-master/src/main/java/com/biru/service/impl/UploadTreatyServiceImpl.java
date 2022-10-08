package com.biru.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.util.SystemOutLogger;
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
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.CLOSING_PARAM;
import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0015AEntity;
import com.biru.entity.MA0018Entity;
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
import com.biru.entity.TR0006JEntity;
import com.biru.entity.TR0006KEntity;
import com.biru.entity.TR0012Entity;
import com.biru.entity.ViewInqUploadTreatyEntity;
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
import com.biru.repository.TR0006JRepo;
import com.biru.repository.TR0006KRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewInqUploadTreatyRepo;
import com.biru.service.ProductionService;
import com.biru.service.UploadTreatyService;

@Service
public class UploadTreatyServiceImpl extends AbstractCommon implements UploadTreatyService{

	@Autowired
	private CommonServiceImpl commonServiceImpl;
	
	@Autowired
	private MA0015Repo ma0015repo;
	
	@Autowired
	private ViewInqUploadTreatyRepo viewInqUploadTreatyRepo;
	
	private Logger log = LoggerFactory.getLogger(ExtensionServiceImpl.class);
	
	@Value("${document.path}")
	private String path;
	
	@Autowired
	private ProductionService productionService;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private TR0006Repo tr0006repo;
	
	@Autowired
	private TR0006BRepo tr0006Brepo;
	
	@Autowired
	private TR0006HRepo tR0006HRepo;
	
	@Autowired
	private TR0006ERepo tr0006ERepo;
	
	@Autowired
	private TR0006ARepo tr0006ARepo;
	
	@Autowired
	private TR0006CRepo tR0006CRepo;
	
	@Autowired
	private TR0006JRepo tR0006JRepo;
	
	@Autowired
	private TR0006KRepo tr0006KRepo;
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private MA0018Repo ma0018Repo;
	
	@Autowired
	private TR0001Repo tr0001repo;
	
	@Autowired
	private TR0002Repo tr0002repo;
	
	@Autowired
	private TR0003Repo tr0003Repo;
	
	@Autowired
	private TR0003ARepo tr0003ARepo;
	
	@Autowired
	private TR0012Repo tr0012repo;
	
	@Autowired
	private MA0015ARepo ma0015Arepo;
	
	@Autowired
	private MA0014Repo ma0014repo;
	
	private static final String CLIENT_TYPE 		= "0";
	private static final String REINSURANCE_TYPE 	= "1";
	private static final BigDecimal BD_100		= new BigDecimal("100");
	
	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) throws ParseException {
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
		
		
		Page<ViewInqUploadTreatyEntity> data = viewInqUploadTreatyRepo.getDataForUploadTreaty(p_Client, pageable);
		List<ViewInqUploadTreatyEntity> listOfData = data.getContent();
		
		BigDecimal exRateValue = new BigDecimal(0);
		String exRateValueStr = "";
		List<TR0006KEntity> tr6k = new ArrayList<TR0006KEntity>();
		MA0005Entity ma5 = new MA0005Entity();
		
		for (int i=0; i<listOfData.size(); i++){
			ViewInqUploadTreatyEntity viewInqUploadTreatyEntity = listOfData.get(i);
			String voucherId = viewInqUploadTreatyEntity.getTrxVoucherId();
			String fullDate = voucherId.substring(0, 9);
			String date = fullDate.substring(0, 2).concat("/").concat(fullDate.substring(2, 4)).concat("/").concat(fullDate.substring(4, 8));
			viewInqUploadTreatyEntity.setTrxDate(date);
			
			tr6k = tr0006KRepo.findLeadReinsForTreaty(voucherId);
			ma5 = mA0005Repo.findByCliCode(tr6k.get(0).getTrxInsId());
			
			//exRateValue = ma0015repo.findByExCurrIdAndExMonthAndExYear(viewInqUploadTreatyEntity.getTrxCurrId(), p_month, p_year).getExRateValue();
			exRateValue = ma0015Arepo.findByExDateAndExCurrId(appDate, viewInqUploadTreatyEntity.getTrxCurrId()).getExKmkRate();
			exRateValueStr = NumberFormat.getCurrencyInstance().format(exRateValue).replace("$", "");
			viewInqUploadTreatyEntity.setExRateValue(exRateValue);
			viewInqUploadTreatyEntity.setExRateValueStr(exRateValueStr);
			viewInqUploadTreatyEntity.setLeadReins(ma5.getCliName());
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}


	@Override
	public Object upload(Map<String, Object> paramFromUI) throws ParseException, IOException {
		String trxId = Param.getStr(paramFromUI, "p_TrxId");
		String voucherId = Param.getStr(paramFromUI, "p_TrxVoucherId");
		log.info("Param Upload Treaty: {}", paramFromUI);
		Map<String, Object> objectFromFile = readFile(paramFromUI);
		List<Map<String, Object>> listOfClientData = (List<Map<String, Object>>) objectFromFile.get("listOfDataClient");
		List<Map<String, Object>> listOfReasData = (List<Map<String, Object>>) objectFromFile.get("listOfDataReas");
		BigDecimal premiumClient = BigDecimal.ZERO;
		Boolean isReas = true;
		
		/*
		 * List of Reas data dipisah dan di-sum berdasarkan masing2 Source Code yang sama
		 * */
		List<Map<String, Object>> sortedListOfReasData = new ArrayList<>();
		HashMap<String, Object> initMap = new HashMap<>();
		BigDecimal sumPremiNettRecalculated = BigDecimal.ZERO;
		BigDecimal sumBrokerFeeRecalculated = BigDecimal.ZERO;
		initMap.put("type", listOfReasData.get(0).get("type"));
		initMap.put("sumPremiNett", listOfReasData.get(0).get("premi"));
		initMap.put("sumBrokerFee", listOfReasData.get(0).get("brokerFeeEach"));
		initMap.put("brokerFee", listOfReasData.get(0).get("brokerFeeEach"));
		initMap.put("source", listOfReasData.get(0).get("source"));
		initMap.put("currency", listOfReasData.get(0).get("currency"));
		initMap.put("source", listOfReasData.get(0).get("source"));
		initMap.put("classOfBusiness", listOfReasData.get(0).get("classOfBusiness"));
		initMap.put("listOfSourceReas", listOfReasData.get(0).get("source"));
		initMap.put("listOfBrokerFeeReas", listOfReasData.get(0).get("brokerFeeEach"));
		initMap.put("listOfPremiReas", listOfReasData.get(0).get("premi"));
		initMap.put("dueDateReas", listOfReasData.get(0).get("dueDate"));
		sortedListOfReasData.add(initMap);
		sumPremiNettRecalculated = (BigDecimal) listOfReasData.get(0).get("premi");
		sumBrokerFeeRecalculated = (BigDecimal) listOfReasData.get(0).get("brokerFeeEach");
		int counter = 1;
		int index = 0;
		for (int i = 1; i < listOfReasData.size(); i++) {
			if(listOfReasData.get(i).get("source").equals(sortedListOfReasData.get(index).get("source"))){
				/*
				 * Tidak membuat object baru, hanya update nilai Sum Premi nya
				 * dari List yang sudah ada
				 * */
				sumPremiNettRecalculated = sumPremiNettRecalculated.add((BigDecimal) listOfReasData.get(i).get("premi"));
				sumBrokerFeeRecalculated = sumBrokerFeeRecalculated.add((BigDecimal) listOfReasData.get(i).get("brokerFeeEach"));
				HashMap<String, Object> updatedMap = new HashMap<>();
				updatedMap.put("type", listOfReasData.get(i).get("type"));
				updatedMap.put("sumPremiNett", sumPremiNettRecalculated);
				updatedMap.put("sumBrokerFee", sumBrokerFeeRecalculated);
				updatedMap.put("brokerFee", listOfReasData.get(i).get("brokerFeeEach"));
				updatedMap.put("source", listOfReasData.get(i).get("source"));
				updatedMap.put("currency", listOfReasData.get(i).get("currency"));
				updatedMap.put("source", listOfReasData.get(i).get("source"));
				updatedMap.put("classOfBusiness", listOfReasData.get(i).get("classOfBusiness"));
				updatedMap.put("listOfSourceReas", listOfReasData.get(i).get("source"));
				updatedMap.put("listOfBrokerFeeReas", listOfReasData.get(i).get("brokerFeeEach"));
				updatedMap.put("listOfPremiReas", listOfReasData.get(i).get("premi"));
				updatedMap.put("dueDateReas", listOfReasData.get(i).get("dueDate"));
				sortedListOfReasData.set(i-counter, updatedMap);
				counter++;
			}else{
				HashMap<String, Object> newMap = new HashMap<>();
				newMap.put("type", listOfReasData.get(i).get("type"));
				newMap.put("sumPremiNett", listOfReasData.get(i).get("premi"));
				newMap.put("sumBrokerFee", listOfReasData.get(i).get("brokerFeeEach"));
				newMap.put("brokerFee", listOfReasData.get(i).get("brokerFeeEach"));
				newMap.put("source", listOfReasData.get(i).get("source"));
				newMap.put("currency", listOfReasData.get(i).get("currency"));
				newMap.put("source", listOfReasData.get(i).get("source"));
				newMap.put("classOfBusiness", listOfReasData.get(i).get("classOfBusiness"));
				newMap.put("listOfSourceReas", listOfReasData.get(i).get("source"));
				newMap.put("listOfBrokerFeeReas", listOfReasData.get(i).get("brokerFeeEach"));
				newMap.put("listOfPremiReas", listOfReasData.get(i).get("premi"));
				newMap.put("dueDateReas", listOfReasData.get(i).get("dueDate"));
				sortedListOfReasData.add(newMap);
				sumPremiNettRecalculated = (BigDecimal) listOfReasData.get(i).get("premi");
				sumBrokerFeeRecalculated = (BigDecimal) listOfReasData.get(i).get("brokerFeeEach");
				index++;
			}
		}
		System.out.println("LIST nya : "+sortedListOfReasData);
		
		paramFromUI.put("p_TrxId", trxId);
		paramFromUI.put("trxVoucherId", voucherId);
		
		List<TR0006JEntity> tr6j = tR0006JRepo.findByTrxVoucherId(voucherId);
		Date startDate = tr6j.get(0).getTrxInsStart();
		Date endDate = tr6j.get(0).getTrxInsEnd();
				
		Date appDate=commonServiceImpl.getAppDate();
		String cliMeth = tr0006repo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId).get(0).getTrxPayMthd();
		String reMeth = "0";
		Calendar calTrx = Calendar.getInstance();
		
		List<Map<String, Object>> response = new ArrayList<Map<String, Object>>();
		/*
		 * Replacing values from Input From, with Object from File
		 * */
		BigDecimal tsi = BigDecimal.ZERO;
		List<TR0006CEntity> tValuesAll = new ArrayList<TR0006CEntity>();
		
		List<String> listOfSourceClient = new ArrayList<String>();
		List<BigDecimal> listOfPremiClient = new ArrayList<BigDecimal>();
		List<BigDecimal> listOfBrokerFeeClient = new ArrayList<BigDecimal>();
		
		List<String> listOfSourceReas = new ArrayList<String>();
		List<BigDecimal> listOfPremiReas = new ArrayList<BigDecimal>();
		List<BigDecimal> listOfBrokerFeeReas = new ArrayList<BigDecimal>();
		
		for(int i=0; i<listOfClientData.size(); i++){
			/*
			 * Processing Client Data to TR6H and TR6A
			 * */
			Map<String, Object> param = new HashMap<String, Object>(listOfClientData.get(i));
			param.put("p_CurrId", Param.getStr(listOfClientData.get(i), "currency"));
			param.put("p_StartDate", startDate);
			param.put("p_EndDate", endDate);
			param.put("p_TsiAmount", Param.getBdWithDef(listOfClientData.get(i), "tsiAmount"));
			param.put("p_NewPortion", "100");
			param.put("isUpload", Boolean.TRUE);
			
			param.put("trxVoucherId", voucherId);
						
			premiumClient = premiumClient.add(Param.getBdWithDef(listOfClientData.get(i), "sumPremiNett"));
			paramFromUI.put("brokerFee", Param.getBdWithDef(listOfClientData.get(i), "brokerFee")); //sumRiComm
			paramFromUI.put("dueDateClient", Param.getStr(listOfClientData.get(i), "dueDate"));
			
			param.put("premiumClientUpload", premiumClient);
						
			tsi = tsi.add(Param.getBdWithDef(listOfClientData.get(i), "tsiAmtFromFile"));
			
			List<TR0006CEntity> tValues = new ArrayList<TR0006CEntity>();
			
			paramFromUI.put("p_TrxId", trxId);
			
			/*
			 * Mencari Curr Rate
			 * */
			BigDecimal dataRate = ma0015Arepo.findByExDateAndExCurrId(appDate, Param.getStr(listOfClientData.get(i), "currency")).getExKmkRate();
			
			param.put("appDate", appDate);
			param.put("currRate", dataRate);
			param.put("reference", paramFromUI.get("p_Reference").toString()); //dari ui
			param.put("currId", paramFromUI.get("p_CurrId").toString().replace(".", "")); //dari ui
			param.put("exchangeRate", Param.getBdWithDef(paramFromUI, "p_ExchangeRate")); //dari ui
			param.put("CLI_METH", cliMeth);
			param.put("RE_METH", reMeth);
			param.put("createOn", calTrx.getTime());
			param.put("descEntry", Param.getStr(paramFromUI, "p_Interest"));
			
			insertTR0006H(param);
			insertTR0006A(param);
			
			tValuesAll.addAll(tValues);
			
			listOfSourceClient.add(Param.getStr(listOfClientData.get(i), "source"));
			listOfPremiClient.add(Param.getBd(listOfClientData.get(i), "premi"));
			listOfBrokerFeeClient.add(Param.getBd(listOfClientData.get(i), "brokerFeeEach"));
		}
		
		for(int i=0; i<listOfReasData.size(); i++){
			/*
			 * Processing Reas Data to TR6H and TR6A
			 * */
			Map<String, Object> param = new HashMap<String, Object>(listOfReasData.get(i));
			param.put("p_CurrId", Param.getStr(listOfReasData.get(i), "currency"));
			param.put("p_StartDate", startDate);
			param.put("p_EndDate", endDate);
			param.put("p_TsiAmount", Param.getBdWithDef(listOfReasData.get(i), "tsiAmount"));
			param.put("p_NewPortion", "100");
			param.put("isUpload", Boolean.TRUE);
			
			param.put("trxVoucherId", voucherId);
						
			premiumClient = premiumClient.add(Param.getBdWithDef(listOfReasData.get(i), "sumPremiNett"));
			paramFromUI.put("brokerFee", Param.getBdWithDef(listOfReasData.get(i), "brokerFee")); //sumRiComm
			
			
			param.put("premiumClientUpload", premiumClient);
						
			tsi = tsi.add(Param.getBdWithDef(listOfReasData.get(i), "tsiAmtFromFile"));
			
			List<TR0006CEntity> tValues = new ArrayList<TR0006CEntity>();
			
			paramFromUI.put("p_TrxId", trxId);
			
			/*
			 * Mencari Curr Rate
			 * */
			BigDecimal dataRate = ma0015Arepo.findByExDateAndExCurrId(appDate, Param.getStr(listOfReasData.get(i), "currency")).getExKmkRate();
			
			param.put("appDate", appDate);
			param.put("currRate", dataRate);
			param.put("reference", paramFromUI.get("p_Reference").toString()); //dari ui
			param.put("currId", paramFromUI.get("p_CurrId").toString().replace(".", "")); //dari ui
			param.put("exchangeRate", Param.getBdWithDef(paramFromUI, "p_ExchangeRate")); //dari ui
			param.put("CLI_METH", cliMeth);
			param.put("RE_METH", reMeth);
			param.put("createOn", calTrx.getTime());
			param.put("descEntry", Param.getStr(paramFromUI, "p_Interest"));
			param.put("isReas", isReas);
			
			insertTR0006H(param);
			insertTR0006A(param);
			
			tValuesAll.addAll(tValues);
			
			listOfSourceReas.add(Param.getStr(listOfReasData.get(i), "source"));
			listOfPremiReas.add(Param.getBd(listOfReasData.get(i), "premi"));
			listOfBrokerFeeReas.add(Param.getBd(listOfReasData.get(i), "brokerFeeEach"));
			paramFromUI.put("fileName", Param.getStr(param, "fileName"));
		}
		
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
		
		sumValues.setTrxInsSub(0);
		
		List<TR0006Entity> tClientInformation = tr0006repo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId);
		List<TR0006BEntity> tReinsurance = tr0006Brepo.findByTrxTrxIdAndTrxVoucherId(trxId, voucherId);
		List<TR0006EEntity> tChecklistCli = tr0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(trxId, voucherId, CLIENT_TYPE);
		List<TR0006EEntity> tChecklistReins = tr0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(trxId, voucherId, REINSURANCE_TYPE);
		
		paramFromUI.put(CLOSING_PARAM.VALUES_LIST, Arrays.asList(sumValues));
		paramFromUI.put(CLOSING_PARAM.INTEREST_INSURED_LIST, new ArrayList<TR0006AEntity>());
		paramFromUI.put(CLOSING_PARAM.COMMOUT_LIST, new ArrayList<TR0006BEntity>());
		paramFromUI.put(CLOSING_PARAM.IS_CLOSING, false);
		paramFromUI.put("treatyModul", "Y");
		
		paramFromUI.put("numberOfRow", objectFromFile.size());
		
		paramFromUI.put("typeOfFileClient", listOfClientData.get(0).get("type"));
		paramFromUI.put("sumPremiClient", listOfClientData.get(listOfClientData.size()-1).get("sumPremiNett"));
		paramFromUI.put("sumBrokerFeeClient", listOfClientData.get(listOfClientData.size()-1).get("brokerFee"));
		paramFromUI.put("sourceClient", listOfClientData.get(0).get("source"));
		paramFromUI.put("currencyClient", listOfClientData.get(0).get("currency"));
		paramFromUI.put("sourceTopClient", Param.getStr(listOfClientData.get(0), "source"));
		paramFromUI.put("classOfBusinessClient", Param.getStr(listOfClientData.get(0), "classOfBusiness"));
		paramFromUI.put("listOfSourceClient", listOfSourceClient);
		paramFromUI.put("listOfPremiClient", listOfPremiClient);
		paramFromUI.put("listOfBrokerFeeClient", listOfBrokerFeeClient);
		paramFromUI.put("listOfClientData", listOfClientData);
		
		paramFromUI.put("typeOfFileReas", listOfReasData.get(0).get("type"));
		paramFromUI.put("sumPremiReas", listOfReasData.get(listOfReasData.size()-1).get("sumPremiNett"));
		paramFromUI.put("sumBrokerFeeReas", listOfReasData.get(listOfReasData.size()-1).get("brokerFee"));
		paramFromUI.put("sourceReas", listOfReasData.get(0).get("source"));
		paramFromUI.put("currencyReas", listOfReasData.get(0).get("currency"));
		paramFromUI.put("sourceTopReas", Param.getStr(listOfReasData.get(0), "source"));
		paramFromUI.put("classOfBusinessReas", Param.getStr(listOfReasData.get(0), "classOfBusiness"));
		paramFromUI.put("listOfSourceReas", listOfSourceReas);
		paramFromUI.put("listOfPremiReas", listOfPremiReas);
		paramFromUI.put("listOfBrokerFeeReas", listOfBrokerFeeReas);
		//paramFromUI.put("listOfReasData", listOfReasData);
		paramFromUI.put("listOfReasData", sortedListOfReasData);
		
		if(isReas){
			//upload Reas
			paramFromUI.put(CLOSING_PARAM.REINSURANCE_LIST, tReinsurance);
			paramFromUI.put(CLOSING_PARAM.PAYMENT_METHOD_REINS_LIST, tChecklistReins);
		}else{
			//upload Client
			paramFromUI.put(CLOSING_PARAM.CLIENT_LIST, tClientInformation);
			paramFromUI.put(CLOSING_PARAM.PAYMENT_METHOD_CLI_LIST, tChecklistCli);
		}
		
		response.addAll((List<Map<String, Object>>) save(paramFromUI));
		return response;
	}

	@Override
	public Map<String, Object> readFile(Map<String, Object> paramFromUI) throws IOException {
		String fileName = path.concat(Param.getStr(paramFromUI, "fileName"));
		File myFile = new File(fileName);
		FileInputStream fis = new FileInputStream(myFile); 
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		XSSFSheet mySheet = myWorkBook.getSheetAt(0); 
		
		BigDecimal sumPremiNettClient = BigDecimal.ZERO;
		BigDecimal sumRiCommClient = BigDecimal.ZERO;
		//BigDecimal sumBrokerageFee = BigDecimal.ZERO;
		BigDecimal brokerFeeClient = BigDecimal.ZERO;
		
		BigDecimal sumPremiNettReas = BigDecimal.ZERO;
		BigDecimal sumRiCommReas = BigDecimal.ZERO;
		//BigDecimal sumBrokerageFee = BigDecimal.ZERO;
		BigDecimal brokerFeeReas = BigDecimal.ZERO;
		
		Boolean isReas = false;
		
		Iterator<Row> rowIterator = mySheet.iterator();
		int i=0;
		List<Map<String, Object>> listOfContents = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listOfContentsClient = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> listOfContentsReas = new ArrayList<Map<String, Object>>();
		Map<String, Object> mapOfListContents = new HashMap<>();
		
		while(rowIterator.hasNext()) {
			Row row = rowIterator.next(); 
			
			if(i>=2) {
				Map<String, Object> param = new HashMap<String, Object>(paramFromUI);
				Iterator<Cell> cellIterator = row.cellIterator(); 
				int j=0;
				while (cellIterator.hasNext()) {
					
					Cell cell = cellIterator.next();
					
					if(j==0) {
						param.put("classOfBusiness", cell.getStringCellValue());
					}else if(j==1) {
						param.put("classOfTreaty", cell.getStringCellValue());
					}else if(j==2) {
						param.put("treatyYear", BigDecimal.valueOf(cell.getNumericCellValue()));
					}else if(j==3) {
						param.put("currency", cell.getStringCellValue());
					}else if(j==4) {
						param.put("tsiAmount", BigDecimal.valueOf(cell.getNumericCellValue())); //tsiAmount from treatyPremium
					}else if(j==5) {
						param.put("commission", BigDecimal.valueOf(cell.getNumericCellValue()));
					}else if(j==6) {
						param.put("claim", BigDecimal.valueOf(cell.getNumericCellValue()));
					}else if(j==7){
						param.put("netBalance", BigDecimal.valueOf(cell.getNumericCellValue()));
					}else if(j==8){
						param.put("share", BigDecimal.valueOf(cell.getNumericCellValue()));	
					}else if(j==11){
						param.put("netPremium", BigDecimal.valueOf(cell.getNumericCellValue()));
						param.put("brokerFeeEach", BigDecimal.valueOf(cell.getNumericCellValue()));
					}
					else if(j==12){
						param.put("premi", BigDecimal.valueOf(cell.getNumericCellValue()));
					}else if(j==13){
						param.put("type", cell.getStringCellValue());	
					}else if(j==14){
						param.put("source", String.valueOf((int) cell.getNumericCellValue()));
					}else if(j==15){
						param.put("noPolis", cell.getStringCellValue());
					}else if(j==16){
						param.put("dueDate", formatDateId.format(cell.getDateCellValue()));
					}
				j++;
				}
				/*param.put("sumPremiNett", sumPremiNett);
				System.out.println("************* SUM PREMI NETT "+sumPremiNett);
				brokerFee = sumRiComm;
				System.out.println("************* SUM BROK FEE "+brokerFee);
				param.put("brokerFee", brokerFee);*/
				//listOfContents.add(param);
				
				if(param.get("type").equals("C")){
					/* 
					 * Read Data Client
					 * */
					//RI Comm
					System.out.println("CREATING LIST CLIENT >>>>>>>>>>>>>>>>>>");
					sumRiCommClient = sumRiCommClient.add((BigDecimal) param.get("brokerFeeEach"));
					//Premi Nett
					sumPremiNettClient = sumPremiNettClient.add((BigDecimal) param.get("premi"));
					param.put("sumPremiNett", sumPremiNettClient);
					System.out.println("************* SUM PREMI NETT CLIENT "+sumPremiNettClient);
					brokerFeeClient = sumRiCommClient;
					System.out.println("************* SUM BROK FEE CLIENT "+brokerFeeClient);
					param.put("brokerFee", brokerFeeClient);
					param.put("isReas", isReas);
					listOfContentsClient.add(param);
				}else{
					/* 
					 * Read Data Reas
					 * */
					//RI Comm
					System.out.println("CREATING LIST REAS >>>>>>>>>>>>>>>>>>");
					sumRiCommReas = sumRiCommReas.add((BigDecimal) param.get("brokerFeeEach"));
					//Premi Nett
					sumPremiNettReas = sumPremiNettReas.add((BigDecimal) param.get("premi"));
					param.put("sumPremiNett", sumPremiNettReas);
					System.out.println("************* SUM PREMI NETT CLIENT "+sumPremiNettReas);
					brokerFeeReas = sumRiCommReas;
					System.out.println("************* SUM BROK FEE CLIENT "+brokerFeeReas);
					param.put("brokerFee", brokerFeeReas);
					isReas = true;
					param.put("isReas", isReas);
					listOfContentsReas.add(param);
				}
			}
		i++;
		}
		mapOfListContents.put("listOfDataClient", listOfContentsClient);
		mapOfListContents.put("listOfDataReas", listOfContentsReas);
		
		return mapOfListContents;
	}
	
	private void insertTR0006H(Map<String, Object> param) throws ParseException{
		Boolean isUpload = Param.getBoolean(param, "isUpload");
		Boolean isReas = Param.getBoolean(param, "isReas");
		/*
		 * Insert to TR0006H 
		 * */
		TR0006HEntity tr0006hEntity = new TR0006HEntity();
		
		//Set Upload Treaty
		tr0006hEntity.setTrxBrdxId(Param.getStr(param, "classOfBusiness"));
		tr0006hEntity.setTrxPartyName(Param.getStr(param, "classOfTreaty"));
		tr0006hEntity.setTrxYear(Param.getBdWithDef(param, "treatyYear").intValue());
		tr0006hEntity.setTrxCurrId(Param.getStr(param, "currency"));
		tr0006hEntity.setTrxTsiAmount(Param.getBdWithDef(param, "tsiAmount"));
		tr0006hEntity.setTrxComm(Param.getBdWithDef(param, "commission"));
		tr0006hEntity.setTrxClaim(Param.getBdWithDef(param, "claim"));
		tr0006hEntity.setTrxBalance(Param.getBdWithDef(param, "netBalance"));
		tr0006hEntity.setTrxReShare(Param.getBdWithDef(param, "share"));
		tr0006hEntity.setTrxGross(Param.getBdWithDef(param, "netPremium"));
		tr0006hEntity.setTrxBrokrFee(Param.getBdWithDef(param, "sumRiComm"));
		tr0006hEntity.setTrxPremiumClient(Param.getBdWithDef(param, "sumPremiNett"));
		tr0006hEntity.setTrxClientId(Param.getStr(param, "source"));
		tr0006hEntity.setTrxClass("TRE");
		tr0006hEntity.setTrxTrxId("RQ");
		tr0006hEntity.setTrxVoucherId(voucherComponent.saveVoucherCounter(formatDate.format((Date) param.get("appDate"))));
		tr0006hEntity.setTrxOldType("RQ");
		tr0006hEntity.setTrxCurrRate(Param.getBdWithDef(param, "currRate"));
		tr0006hEntity.setTrxOldVoucherId(Param.getStr(param, "reference"));
		tr0006hEntity.setTrxSumInsured(BigDecimal.ZERO);
		tr0006hEntity.setTrxNewPortion(Param.getBdWithDef(param, "p_NewPortion"));
		tr0006hEntity.setTrxDate((Date) param.get("appDate"));
		tr0006hEntity.setTrxInterest(param.get("descEntry").toString()); //
		tr0006hEntity.setTrxDueClient((Date) param.get("clientDate")); //
		tr0006hEntity.setTrxPremiumRe(Param.getBdWithDef(param, "p_ReinsuranceAmt")); //
		tr0006hEntity.setTrxDataStatus("11");
		if(isReas){
			tr0006hEntity.setTrxDataStatus("12");
		}
		tr0006hEntity.setTrxParticipantId(Param.getStr(param, "noPolis"));
		tr0006hEntity.setTrxCreatedBy(param.get(Param.USER).toString());
		tr0006hEntity.setTrxCreatedOn((Date) param.get("createOn"));
		
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
		
		String[] bits = Param.getStr(param, "fileName").split("\\\\");
		String fileNameOnly = bits[bits.length-1];
		tr0006hEntity.setTrxFileName(fileNameOnly);
		tr0006hEntity.setTrxClaimStatus("0");
		tR0006HRepo.save(tr0006hEntity);
		
		System.out.println(">>>>> Komponen TR06H : "+tr0006hEntity.getTrxVoucherId());
		
	}
	
	private void insertTR0006A(Map<String, Object> param){
		/*
		 * Insert to TR0006A 
		 * */
		TR0006AEntity tr0006aEntity = new TR0006AEntity();
		tr0006aEntity.setTrxClass("TRE");
		tr0006aEntity.setTrxTrxId("RQ");
		tr0006aEntity.setTrxVoucherId(Param.getStr(param, "trxVoucherId"));
		tr0006aEntity.setTrxInsSub(0);
		tr0006aEntity.setTrxCoverCode(Param.getStr(param, "classOfBusiness"));
		tr0006aEntity.setTrxCoverClass("TRE");
		tr0006aEntity.setTrxInsInsured(param.get("descEntry").toString());
		tr0006aEntity.setTrxInsLocation(param.get("descEntry").toString());
		tr0006aEntity.setTrxPremiumRate(BigDecimal.ZERO);
		tr0006aEntity.setTrxWeightRate(BigDecimal.ZERO);
		tr0006aEntity.setTrxCombined("0");
		tr0006aEntity.setTrxCurrId(Param.getStr(param, "p_CurrId"));
		tr0006aEntity.setTrxCurrRate(Param.getBdWithDef(param, "currRate"));
		tr0006aEntity.setTrxSumInsured(Param.getBdWithDef(param, "sumPremiNett"));
		tr0006aEntity.setTrxInsStart((Date) param.get("appDate"));
		tr0006aEntity.setTrxInsEnd((Date) param.get("appDate"));
		tr0006aEntity.setTrxRemarks(param.get("descEntry").toString());
		
		tr0006ARepo.save(tr0006aEntity);
		System.out.println(">>>>> Komponen TR06A : "+tr0006aEntity.getTrxVoucherId());
	}


	@Override
	public Object save(Map<String, Object> param) throws ParseException {
		String p_TrxId = param.get("p_TrxId").toString(); //dari UI
		String p_TrxVoucherId = param.get("p_TrxVoucherId").toString(); //dari UI
		String currId = Param.getStr(param, "p_CurrId");
		
		String[] bits = Param.getStr(param, "fileName").split("\\\\");
		String fileNameOnly = bits[bits.length-1];
		
		BigDecimal taxRate009 = commonServiceImpl.getTaxRateh009();
		BigDecimal sumPremiumValueClient = Param.getBdWithDef(param, "sumPremiClient");
		BigDecimal sumBrkrFeeValueClient = Param.getBdWithDef(param, "sumBrokerFeeClient").divide(taxRate009, 2, RoundingMode.HALF_UP);

		/*
		 * Mencari TAX RATE
		 * */
		MA0014Entity ma0014Entity = ma0014repo.findByPaChildCode("TAXRATEH003");
		BigDecimal ppnRate = new BigDecimal(ma0014Entity.getPaChildValue()).divide(BD_100);
		
		BigDecimal sumTaxinBfValueClient = sumBrkrFeeValueClient.multiply(ppnRate);
		BigDecimal sumGrossValueClient = sumBrkrFeeValueClient.add(sumTaxinBfValueClient).add(sumPremiumValueClient);
				
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
		
		Map<String, BigDecimal> mapValueClient = new HashMap<String, BigDecimal>();
		mapValueClient.put("PREMIUM_VALUE", sumPremiumValueClient);
		mapValueClient.put("BRKR_FEE_VALUE", sumBrkrFeeValueClient);
		mapValueClient.put("TAXIN_BF_VALUE", sumTaxinBfValueClient);
		mapValueClient.put("GROSS_VALUE", sumGrossValueClient);
		
		
		
		List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
		
		/* Find Client(s) 
		 * */
		List<String> listOfSourceClient = (List<String>) param.get("listOfSourceClient");
		List<BigDecimal> listOfPremiClient = (List<BigDecimal>) param.get("listOfPremiClient");
		
		/*
		 * Mencari Curr Rate
		 * */
		BigDecimal dataRate = ma0015Arepo.findByExDateAndExCurrId(appDate, currId).getExKmkRate();
		//BigDecimal cashValue = premiumValueClient.multiply(BigDecimal.valueOf(dataRate));
		BigDecimal cashValue = BigDecimal.ZERO;
		List<BigDecimal> listOfCashValueClient = new ArrayList<BigDecimal>();
		//List<BigDecimal> listOfCashValueReas = new ArrayList<BigDecimal>();
		
		for (int i = 0; i < listOfPremiClient.size(); i++) {
			cashValue = listOfPremiClient.get(i).multiply(dataRate);
			listOfCashValueClient.add(cashValue);
		}
		
			System.out.println("BEGIN CLIENT PROCESS");
			List<TR0006EEntity> tChecklistCli = tr0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(p_TrxId, p_TrxVoucherId, CLIENT_TYPE);
			TR0006Entity tr0006EntityClient = tr0006repo.findOneClientByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
			List<Map<String, Object>> listOfClientData = (List<Map<String, Object>>) param.get("listOfClientData");
			Date dueDateClient = formatDateId.parse(Param.getStr(param, "dueDateClient"));
			//for (int rowNum = 0 ; rowNum<numOfRow; rowNum++){
				List<TR0001Entity> listOfTr0001EntityClient = new ArrayList<TR0001Entity>();
				MA0005Entity clientC = mA0005Repo.findByCliCode(listOfSourceClient.get(0).toString());
				//for (TR0006EEntity payMthdCli : tChecklistCli){
					TR0001Entity tr0001EntityClientData = new TR0001Entity();
					String voucherIdClient = voucherComponent.saveVoucherCounter(formatDate.format(appDate));
					tr0001EntityClientData.setGlTrxClass("TRE");
					tr0001EntityClientData.setGlType("SE");
					tr0001EntityClientData.setGlVoucherId(voucherIdClient);
					tr0001EntityClientData.setGlTrxDate(appDate);
					tr0001EntityClientData.setGlTrxDue(dueDateClient);
					tr0001EntityClientData.setGlTrxMonth(month.byteValue());
					tr0001EntityClientData.setGlTrxYear(year.shortValue());
					tr0001EntityClientData.setGlTrxOfficeId("0");
					tr0001EntityClientData.setGlTrxProject("0000");
					tr0001EntityClientData.setGlTrxClient(Param.getStr(param, "sourceTopClient"));
					tr0001EntityClientData.setGlTrxClientDesc(param.get("p_Interest").toString()+" - "+fileNameOnly); //dari UI
					tr0001EntityClientData.setGlTrxValueOrg(sumPremiumValueClient);
					tr0001EntityClientData.setGlTrxValueIdr(sumPremiumValueClient.multiply(dataRate));
					tr0001EntityClientData.setGlTrxStatus("0");
					tr0001EntityClientData.setGlDataStatus("11");
					tr0001EntityClientData.setCreateOn(now);
					tr0001EntityClientData.setCreateBy(param.get(Param.USER).toString());
					tr0001EntityClientData.setModifyOn(now);
					tr0001EntityClientData.setModifyBy(param.get(Param.USER).toString());
										
					tr0001EntityClientData.setGlTrxDesc(param.get("p_Interest").toString());
					
					List<MA0018Entity> businessRuleClient = ma0018Repo.findByBrCode("UCTRX".concat(Param.getStr(param, "currencyClient")));
					
					List<TR0002Entity> listOfTr0002EntityClient = new ArrayList<TR0002Entity>();
					for(MA0018Entity ma0018 : businessRuleClient) {
						TR0002Entity tr0002EntityClientData = new TR0002Entity();
						tr0002EntityClientData.setGlTrxClass(tr0001EntityClientData.getGlTrxClass());
						tr0002EntityClientData.setGlType(tr0001EntityClientData.getGlType());
						tr0002EntityClientData.setGlVoucherId(tr0001EntityClientData.getGlVoucherId());
						tr0002EntityClientData.setGlAccount(ma0018.getBrChildCoa());
						
						tr0002EntityClientData.setGlCurrId(currId);
						tr0002EntityClientData.setGlCurrRate(dataRate);
						
						String code = ma0018.getBrChildValue().trim();
						BigDecimal org = mapValueClient.get(code) == null ? BigDecimal.ZERO : mapValueClient.get(code);
						BigDecimal idr = org.multiply(dataRate);
						
						tr0002EntityClientData.setGlOrgDebit(BigDecimal.ZERO);
						tr0002EntityClientData.setGlIdrDebit(BigDecimal.ZERO);
						tr0002EntityClientData.setGlOrgCredit(BigDecimal.ZERO);
						tr0002EntityClientData.setGlIdrCredit(BigDecimal.ZERO);
						
						if(ma0018.getBrChildDc().equals('0')) {				
							if(("PREMIUM_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityClientData.setGlOrgDebit(mapValueClient.get("PREMIUM_VALUE"));
								tr0002EntityClientData.setGlIdrDebit(mapValueClient.get("PREMIUM_VALUE").multiply(dataRate));
							}else if(("BRKR_FEE_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityClientData.setGlOrgDebit(mapValueClient.get("BRKR_FEE_VALUE"));
								tr0002EntityClientData.setGlIdrDebit(mapValueClient.get("BRKR_FEE_VALUE").multiply(dataRate));
							}else if(("TAXIN_BF_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityClientData.setGlOrgDebit(mapValueClient.get("TAXIN_BF_VALUE"));
								tr0002EntityClientData.setGlIdrDebit(mapValueClient.get("TAXIN_BF_VALUE").multiply(dataRate));
							}else if(("GROSS_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityClientData.setGlOrgDebit(mapValueClient.get("GROSS_VALUE"));
								tr0002EntityClientData.setGlIdrDebit(mapValueClient.get("GROSS_VALUE").multiply(dataRate));
							}
						}else if(ma0018.getBrChildDc().equals('1')) {
							if(("PREMIUM_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityClientData.setGlOrgCredit(mapValueClient.get("PREMIUM_VALUE"));
								tr0002EntityClientData.setGlIdrCredit(mapValueClient.get("PREMIUM_VALUE").multiply(dataRate));
							}else if(("BRKR_FEE_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityClientData.setGlOrgCredit(mapValueClient.get("BRKR_FEE_VALUE"));
								tr0002EntityClientData.setGlIdrCredit(mapValueClient.get("BRKR_FEE_VALUE").multiply(dataRate));
							}else if(("TAXIN_BF_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityClientData.setGlOrgCredit(mapValueClient.get("TAXIN_BF_VALUE"));
								tr0002EntityClientData.setGlIdrCredit(mapValueClient.get("TAXIN_BF_VALUE").multiply(dataRate));
							}else if(("GROSS_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityClientData.setGlOrgCredit(mapValueClient.get("GROSS_VALUE"));
								tr0002EntityClientData.setGlIdrCredit(mapValueClient.get("GROSS_VALUE").multiply(dataRate));
							}
						}
						listOfTr0002EntityClient.add(tr0002EntityClientData);
					}
					listOfTr0001EntityClient.add(tr0001EntityClientData); 
					tr0001repo.saveAll(listOfTr0001EntityClient);
					tr0002repo.saveAll(listOfTr0002EntityClient);
					
				//from here
				
				int countC = 1;
				List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
				for(TR0001Entity tr1 : listOfTr0001EntityClient) {
					TR0012Entity tr0012entityClient = new TR0012Entity();
					tr0012entityClient.setTrxTrxClass("TRE");
					tr0012entityClient.setTrxType("SE");
					tr0012entityClient.setTrxVoucherId(tr1.getGlVoucherId());
					tr0012entityClient.setTrxSource("1");
					tr0012entityClient.setTrxCountInv(1);
					tr0012entityClient.setTrxDate(appDate);
					tr0012entityClient.setTrxDueDate(dueDateClient);
					tr0012entityClient.setTrxMethPay("0");
					tr0012entityClient.setTrxCoverCode(Param.getStr(param, "classOfBusinessClient"));
					tr0012entityClient.setTrxDataStatus("11");
					tr0012entityClient.setTrxOldType("RQ");
					tr0012entityClient.setTrxOldVoucherId(p_TrxVoucherId);
					tr0012entityClient.setTrxClient(listOfSourceClient.get(0).toString());
					tr0012entityClient.setTrxDescription(param.get("p_Interest").toString()+" - "+fileNameOnly);
					tr0012entityClient.setTrxCurrId(Param.getStr(param, "currencyClient"));
					tr0012entityClient.setTrxCurrRate(dataRate);
					tr0012entityClient.setTrxOrgAmount(mapValueClient.get("PREMIUM_VALUE"));
					tr0012entityClient.setTrxInvcAmount(mapValueClient.get("PREMIUM_VALUE"));
					tr0012entityClient.setTrxBrkrFee(mapValueClient.get("BRKR_FEE_VALUE"));
					tr0012entityClient.setTrxTaxinBf(mapValueClient.get("TAXIN_BF_VALUE"));
					tr0012entityClient.setTrxInsOfficer(tr0006EntityClient.getTrxOfficer());
					tr0012entityClient.setCreateBy(param.get(Param.USER).toString());
					tr0012entityClient.setCreateOn(now);
					
					tr12Entities.add(tr0012entityClient);
					countC++;
				}
				
				tr0012repo.saveAll(tr12Entities);
				
			
			String voucherIdTr3 = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
			TR0006AEntity tr0006a = tr0006ARepo.findPeriodByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
			TR0006BEntity tr0006b = tr0006Brepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(p_TrxId, p_TrxVoucherId, "0");
			TR0003Entity tr3 = new TR0003Entity();
			
			tr3.setTrxType(p_TrxId);
			tr3.setTrxVoucherId(voucherIdTr3);
			tr3.setTrxDescription(param.get("p_Interest").toString());
			tr3.setTrxClient(Param.getStr(param, "sourceTopClient"));
			tr3.setTrxDate(appDate);
			tr3.setTrxAssured(tr0006EntityClient.getTrxClient());
			tr3.setTrxCoverCode(Param.getStr(param, "classOfBusinessClient"));
			tr3.setTrxInsOfficer(tr0006EntityClient.getTrxOfficer());
			tr3.setTrxInsInsured(param.get("p_Reins").toString());
			tr3.setTrxInsStart(tr0006a.getTrxInsStart());
			tr3.setTrxInsEnd(tr0006a.getTrxInsEnd());
			tr3.setTrxCurrId(Param.getStr(param, "currencyClient"));
			tr3.setTrxCurrRate(dataRate);
			tr3.setTrxAmountDue(mapValueClient.get("PREMIUM_VALUE")); 
			tr3.setTrxStatusData("11");
			tr3.setCreateOn(now);
			tr3.setCreateBy(param.get(Param.USER).toString());
			tr3.setTrxOldVoucherId(p_TrxVoucherId);
			
			MA0005Entity ma0005EntityForReport = mA0005Repo.findByCliCode(listOfSourceClient.get(0).toString());
			Map<String, Object> journal = new HashMap<String, Object>();
			journal.put("voucherId", tr3.getTrxVoucherId());
			journal.put("name", ma0005EntityForReport.getCliName());
			journal.put("document", "Closing Client - DN");
			journal.put("type", "DEBIT NOTE");
			journal.put("isComm", Boolean.FALSE);
			journal.put("insSub", 0);
			String description2Add = "";
			String fileName = "Treaty - DN - " + ma0005EntityForReport.getCliName();
			journal.put("p_Description2Add", description2Add);
			
			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '', '" + ma0005EntityForReport.getCliName() + "', 'DEBIT NOTE')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '', '" + ma0005EntityForReport.getCliName() + "', '" + fileName + "', 'DEBIT NOTE" + "')\">"
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
				
				tr3a.setTrxDueDate(dueDateClient);
				tr3a.setTrxRemarks("-");
				
				tr3a.setTrxDueAmount(mapValueClient.get("PREMIUM_VALUE"));
				tr3a.setTrxDueDate(dueDateClient);
				tr3a.setTrxTrxClass("TRE");
				
				tr3aEntities.add(tr3a);
				
				noRow++;
			tr0003Repo.save(tr3);
			tr0003ARepo.saveAll(tr3aEntities);
		
			System.out.println("BEGIN REAS PROCESS");
			/*
			 * Reas akan disum dan disimpan berdasarkan Source Code nya
			 * */
			List<Map<String, Object>> listOfReasData = (List<Map<String, Object>>) param.get("listOfReasData");
			List<TR0006EEntity> tChecklistReins = tr0006ERepo.findByTrxIdAndTrxVoucherIdAndTrxPrClient(p_TrxId, p_TrxVoucherId, REINSURANCE_TYPE);
			TR0006Entity tr0006EntityReas = tr0006repo.findOneClientByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
				List<TR0001Entity> listOfTr0001EntityReas = new ArrayList<TR0001Entity>();
				List<Map<String, BigDecimal>> listOfmapValueReas = new ArrayList<>();
				for (int i=0; i<listOfReasData.size(); i++){
					Date dueDateReas = formatDateId.parse(listOfReasData.get(i).get("dueDateReas").toString());
					TR0001Entity tr0001EntityReas = new TR0001Entity();
					String voucherIdReins = voucherComponent.saveVoucherCounter(formatDate.format(appDate));
					tr0001EntityReas.setGlTrxClass("TRE");
					tr0001EntityReas.setGlType("PU");
					tr0001EntityReas.setGlVoucherId(voucherIdReins);
					tr0001EntityReas.setGlTrxDate(appDate);
					tr0001EntityReas.setGlTrxDue(dueDateReas);
					tr0001EntityReas.setGlTrxMonth(month.byteValue());
					tr0001EntityReas.setGlTrxYear(year.shortValue());
					tr0001EntityReas.setGlTrxOfficeId("0");
					tr0001EntityReas.setGlTrxProject("0000");
					tr0001EntityReas.setGlTrxClient(listOfReasData.get(i).get("source").toString());
					tr0001EntityReas.setGlTrxClientDesc(param.get("p_Interest").toString()+" - "+fileNameOnly);
					tr0001EntityReas.setGlTrxValueOrg((BigDecimal) listOfReasData.get(i).get("sumPremiNett"));
					tr0001EntityReas.setGlTrxValueIdr(((BigDecimal) listOfReasData.get(i).get("sumPremiNett")).multiply(dataRate));
					tr0001EntityReas.setGlTrxStatus("0");
					tr0001EntityReas.setGlDataStatus("11");
					tr0001EntityReas.setCreateOn(now);
					tr0001EntityReas.setCreateBy(param.get(Param.USER).toString());
					tr0001EntityReas.setModifyOn(now);
					tr0001EntityReas.setModifyBy(param.get(Param.USER).toString());
					
					tr0001EntityReas.setGlTrxDesc(param.get("p_Interest").toString());
					
					BigDecimal sumPremiumValueReas = (BigDecimal) listOfReasData.get(i).get("sumPremiNett");
					BigDecimal sumBrkrFeeValueReas = ((BigDecimal) listOfReasData.get(i).get("sumBrokerFee")).divide(taxRate009, 2, RoundingMode.HALF_UP);
					BigDecimal sumTaxinBfValueReas = sumBrkrFeeValueReas.multiply(ppnRate);
					BigDecimal sumGrossValueReas = sumBrkrFeeValueReas.add(sumTaxinBfValueReas).add(sumPremiumValueReas);

					Map<String, BigDecimal> mapValueReas = new HashMap<String, BigDecimal>();
					mapValueReas.put("PREMIUM_VALUE", sumPremiumValueReas);
					mapValueReas.put("BRKR_FEE_VALUE", sumBrkrFeeValueReas);
					mapValueReas.put("TAXIN_BF_VALUE", sumTaxinBfValueReas);
					mapValueReas.put("GROSS_VALUE", sumGrossValueReas);
					listOfmapValueReas.add(mapValueReas);
					
					List<MA0018Entity> businessRuleReas = ma0018Repo.findByBrCode("UVTRX".concat(Param.getStr(param, "currencyReas")));
					
					List<TR0002Entity> listOfTr0002EntityReas = new ArrayList<TR0002Entity>();
					for(MA0018Entity ma0018 : businessRuleReas) {
						TR0002Entity tr0002EntityReas = new TR0002Entity();
						tr0002EntityReas.setGlTrxClass(tr0001EntityReas.getGlTrxClass());
						tr0002EntityReas.setGlType(tr0001EntityReas.getGlType());
						tr0002EntityReas.setGlVoucherId(tr0001EntityReas.getGlVoucherId());
						tr0002EntityReas.setGlAccount(ma0018.getBrChildCoa());
						
						tr0002EntityReas.setGlCurrId(currId);
						tr0002EntityReas.setGlCurrRate(dataRate);
						
						String code = ma0018.getBrChildValue().trim();
						BigDecimal org = mapValueReas.get(code) == null ? BigDecimal.ZERO : mapValueReas.get(code);
						BigDecimal idr = org.multiply(dataRate);
						
						tr0002EntityReas.setGlOrgDebit(BigDecimal.ZERO);
						tr0002EntityReas.setGlIdrDebit(BigDecimal.ZERO);
						tr0002EntityReas.setGlOrgCredit(BigDecimal.ZERO);
						tr0002EntityReas.setGlIdrCredit(BigDecimal.ZERO);
						
						if(ma0018.getBrChildDc().equals('0')) {				
							if(("PREMIUM_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityReas.setGlOrgDebit(mapValueReas.get("PREMIUM_VALUE"));
								tr0002EntityReas.setGlIdrDebit(mapValueReas.get("PREMIUM_VALUE").multiply(dataRate));
							}else if(("BRKR_FEE_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityReas.setGlOrgDebit(mapValueReas.get("BRKR_FEE_VALUE"));
								tr0002EntityReas.setGlIdrDebit(mapValueReas.get("BRKR_FEE_VALUE").multiply(dataRate));
							}else if(("TAXIN_BF_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityReas.setGlOrgDebit(mapValueReas.get("TAXIN_BF_VALUE"));
								tr0002EntityReas.setGlIdrDebit(mapValueReas.get("TAXIN_BF_VALUE").multiply(dataRate));
							}else if(("GROSS_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityReas.setGlOrgDebit(mapValueReas.get("GROSS_VALUE"));
								tr0002EntityReas.setGlIdrDebit(mapValueReas.get("GROSS_VALUE").multiply(dataRate));
							}
						}else if(ma0018.getBrChildDc().equals('1')) {
							if(("PREMIUM_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityReas.setGlOrgCredit(mapValueReas.get("PREMIUM_VALUE"));
								tr0002EntityReas.setGlIdrCredit(mapValueReas.get("PREMIUM_VALUE").multiply(dataRate));
							}else if(("BRKR_FEE_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityReas.setGlOrgCredit(mapValueReas.get("BRKR_FEE_VALUE"));
								tr0002EntityReas.setGlIdrCredit(mapValueReas.get("BRKR_FEE_VALUE").multiply(dataRate));
							}else if(("TAXIN_BF_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityReas.setGlOrgCredit(mapValueReas.get("TAXIN_BF_VALUE"));
								tr0002EntityReas.setGlIdrCredit(mapValueReas.get("TAXIN_BF_VALUE").multiply(dataRate));
							}else if(("GROSS_VALUE").equals(ma0018.getBrChildValue())){
								tr0002EntityReas.setGlOrgCredit(mapValueReas.get("GROSS_VALUE"));
								tr0002EntityReas.setGlIdrCredit(mapValueReas.get("GROSS_VALUE").multiply(dataRate));
							}
						}
						
						listOfTr0002EntityReas.add(tr0002EntityReas);
						System.out.println(">>>>> Komponen TR02 for REAS : "+tr0002EntityReas.getGlVoucherId());
					}
					listOfTr0001EntityReas.add(tr0001EntityReas);
					
					tr0001repo.saveAll(listOfTr0001EntityReas);
					
					tr0002repo.saveAll(listOfTr0002EntityReas);
					
					
					
					String voucherIdTr3Reas = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
					TR0006Entity tr0006 = tr0006repo.findOneClientByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
					String clientAssured = tr0006.getTrxClient();
					TR0006AEntity tr0006aReas = tr0006ARepo.findPeriodByTrxIdAndTrxVoucherId(p_TrxId, p_TrxVoucherId);
					TR0006BEntity tr0006bReas = tr0006Brepo.findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(p_TrxId, p_TrxVoucherId, "0");
					TR0003Entity tr3Reas = new TR0003Entity();
					tr3Reas.setTrxType(p_TrxId);
					tr3Reas.setTrxVoucherId(voucherIdTr3Reas);
					tr3Reas.setTrxDescription(param.get("p_Interest").toString());
					tr3Reas.setTrxClient(listOfReasData.get(i).get("source").toString());
					tr3Reas.setTrxDate(appDate);
					tr3Reas.setTrxAssured(clientAssured);
					tr3Reas.setTrxCoverCode(Param.getStr(param, "classOfBusinessReas"));
					tr3Reas.setTrxInsOfficer(tr0006.getTrxOfficer());
					tr3Reas.setTrxInsInsured(param.get("p_Cedant").toString());
					tr3Reas.setTrxInsStart(tr0006aReas.getTrxInsStart());
					tr3Reas.setTrxInsEnd(tr0006aReas.getTrxInsEnd());
					tr3Reas.setTrxCurrId(Param.getStr(param, "currencyReas"));
					tr3Reas.setTrxCurrRate(dataRate);
					tr3Reas.setTrxAmountDue(mapValueReas.get("PREMIUM_VALUE")); 
					tr3Reas.setTrxStatusData("11");
					tr3Reas.setCreateOn(now);
					tr3Reas.setCreateBy(param.get(Param.USER).toString());
					tr3Reas.setTrxOldVoucherId(p_TrxVoucherId);
					
					Map<String, Object> journalReas = new HashMap<String, Object>();
					MA0005Entity ma0005EntityForReportReas = mA0005Repo.findByCliCode(listOfReasData.get(i).get("source").toString());
					journalReas.put("voucherId", tr3Reas.getTrxVoucherId());
					journalReas.put("name", ma0005EntityForReportReas.getCliName());
					journalReas.put("document", "Closing Reinsurance - CN");
					journalReas.put("type", "CREDIT NOTE");
					journalReas.put("isComm", Boolean.FALSE);
					journalReas.put("insSub", 0);
					String description2AddReas = "";
					String fileNameReas = "Treaty - CN - " + ma0005EntityForReportReas.getCliName();
					journalReas.put("p_Description2Add", description2AddReas);
					
					String actionReas = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3Reas.getTrxVoucherId() + "', '', '" + ma0005EntityForReportReas.getCliName() + "', 'CREDIT NOTE')\">" 
							+ "<i class=\"fa fa-print\"></i>" 
							+ "</button>&nbsp;";
					actionReas += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3Reas.getTrxVoucherId() + "', '', '" + ma0005EntityForReportReas.getCliName() + "', '" + fileNameReas + "', 'CREDIT NOTE" + "')\">"
							+ "<i class=\"fas fa-file-word\"></i>" 
							+ "</button>";
					
					journalReas.put("action", actionReas);
					table.add(journalReas);
					
					int noRowReas = 1;
					List<TR0003AEntity> tr3aEntitiesReas = new ArrayList<TR0003AEntity>();
						TR0003AEntity tr3aReas = new TR0003AEntity();
						tr3aReas.setTrxType(tr3Reas.getTrxType());
						tr3aReas.setTrxVoucherId(tr3Reas.getTrxVoucherId());
						tr3aReas.setTrxNoRow(noRowReas);
						
						tr3aReas.setTrxRemarks("-");
						
						tr3aReas.setTrxDueAmount((mapValueReas.get("PREMIUM_VALUE"))); 
						tr3aReas.setTrxDueDate(dueDateReas);
						tr3aReas.setTrxTrxClass("TRE");
						
						tr3aEntitiesReas.add(tr3aReas);
						
						noRowReas++;
					tr0003Repo.save(tr3Reas);
					tr0003ARepo.saveAll(tr3aEntitiesReas);
					
				}
				
				int countR = 1;
				List<TR0012Entity> tr12EntitiesReas = new ArrayList<TR0012Entity>();
				int index = 0;
				for(TR0001Entity tr1 : listOfTr0001EntityReas) {
					TR0012Entity tr0012entityReas = new TR0012Entity();
					Date dueDateReas = (tr1.getGlTrxDue());
					tr0012entityReas.setTrxTrxClass("TRE");
					tr0012entityReas.setTrxType("PU");
					tr0012entityReas.setTrxVoucherId(tr1.getGlVoucherId());
					tr0012entityReas.setTrxSource("1");
					tr0012entityReas.setTrxCountInv(1);
					tr0012entityReas.setTrxDate(appDate);
					tr0012entityReas.setTrxDueDate(dueDateReas);
					tr0012entityReas.setTrxMethPay("0");
					tr0012entityReas.setTrxCoverCode(Param.getStr(param, "classOfBusinessReas"));
					tr0012entityReas.setTrxDataStatus("11");
					tr0012entityReas.setTrxOldType("RQ");
					tr0012entityReas.setTrxOldVoucherId(p_TrxVoucherId);
					tr0012entityReas.setTrxClient(tr1.getGlTrxClient());
					tr0012entityReas.setTrxDescription(param.get("p_Interest").toString()+" - "+fileNameOnly);
					tr0012entityReas.setTrxCurrId(Param.getStr(param, "currencyReas"));
					tr0012entityReas.setTrxCurrRate(dataRate);
					tr0012entityReas.setTrxOrgAmount(listOfmapValueReas.get(index).get("PREMIUM_VALUE"));
					tr0012entityReas.setTrxInvcAmount(listOfmapValueReas.get(index).get("PREMIUM_VALUE"));
					tr0012entityReas.setTrxBrkrFee(listOfmapValueReas.get(index).get("BRKR_FEE_VALUE"));
					tr0012entityReas.setTrxTaxinBf(listOfmapValueReas.get(index).get("TAXIN_BF_VALUE"));
					tr0012entityReas.setTrxInsOfficer(tr0006EntityReas.getTrxOfficer());
					tr0012entityReas.setCreateBy(param.get(Param.USER).toString());
					tr0012entityReas.setCreateOn(now);
					
					tr12EntitiesReas.add(tr0012entityReas);
					countR++;
					index++;
				}
				tr0012repo.saveAll(tr12EntitiesReas);
				
		return table;
	}


	@Override
	public Object validateSource(Map<String, Object> param) throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

}
