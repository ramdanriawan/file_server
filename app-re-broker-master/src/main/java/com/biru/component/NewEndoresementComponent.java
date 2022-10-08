package com.biru.component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biru.ReBrokerConstants.CLOSING_PARAM;
import com.biru.ReBrokerConstants.REST.EXTENSION;
import com.biru.common.AbstractCommon;
import com.biru.common.param.Param;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0012Entity;
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
import com.biru.entity.TR0006GEntity;
import com.biru.entity.TR0012Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0012Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0003ARepo;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0006BRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.service.CommonService;

@Component
public class NewEndoresementComponent extends AbstractCommon {
	
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
	
	private static final String RQ 				= "RQ";
	private static final String TRX_VOUCHER_ID 	= "trxVoucherId";
	private static final String TRX_OLD_VOUCHER_ID 	= "trxOldVoucherId";
	private static final String REMARKS			= "remarks";
	
	private static final BigDecimal BD_100		= new BigDecimal("100");
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private MA0015ARepo mA0015ARepo;
	
	@Autowired
	private MA0018Repo mA0018Repo;

	@Autowired
	private TR0006Repo tR0006Repo;

	@Autowired
	private TR0006BRepo tR0006BRepo;
	
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
	private VoucherComponent voucherComponent;
	
	@Autowired
	private MA0012Repo mA0012Repo;
	
	private Logger logger = LoggerFactory.getLogger(NewEndoresementComponent.class);
	
	@Transactional
	@SuppressWarnings("unchecked")
	public Object closing(Map<String, Object> param) throws ParseException {
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);	
		String trxOldVoucherId = Param.getStr(param, TRX_OLD_VOUCHER_ID);	
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
	
			Date appDate = common.getAppDate();
			
			Calendar calTrx = Calendar.getInstance();
			calTrx.setTime(appDate);
			Integer month = calTrx.get(Calendar.MONTH)+1;
			Integer year = calTrx.get(Calendar.YEAR);
			
			TR0006AEntity interest = interestI.stream()
					.filter(i -> i.getTrxInsSub() == values.getTrxInsSub())
					.findFirst().orElse(null);
			String trxTypeAdDesc = interestI.get(j-1).getTrxTypeAd().equals("D") ? "DED" : "ADD";
			
			String typeOfCover = interest.getTrxCoverCode();
			String remarksII = interest.getTrxRemarks();
			String insuredNameCI = tR0006Repo.findTrxInsuredNameByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			
			BigDecimal taxRate003 = common.getTaxRateh003();
			BigDecimal taxRate009 = common.getTaxRateh009();
			
			BigDecimal shareTotalCli = tR0006Repo.getSummaryTrxShareByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			if(shareTotalCli.compareTo(BD_100) > 0)
				shareTotalCli = BD_100;
			if(tClientInformation != null){
			for(TR0006Entity clientInformation : tClientInformation) {
				MA0005Entity client = mA0005Repo.findByCliCode(clientInformation.getTrxClient());
				String descCli = "RQ " + trxOldVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + remarksII;
				if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
					descCli = "Endorsement - DED - ".concat(descCli);
				}else {
					descCli = "Endorsement - ADD - ".concat(descCli);
				}
	//			BigDecimal shareRateCli = clientInformation.getTrxShare().divide(shareTotalCli, 2, RoundingMode.HALF_UP);
				BigDecimal shareRateCli = BigDecimal.ONE;
				MA0015AEntity exchangeRateCli = mA0015ARepo.findByExDateAndExCurrId(appDate, clientInformation.getTrxCurrId());
				
//				String brCodeCli = "CTRX" + clientInformation.getTrxCurrId();
//				String brCodeCli = interestI.get(j-1).getTrxTypeAd().equals("A") ? "CTRX" : "EVTRX";
				String brCodeCli = interestI.get(j-1).getTrxTypeAd().equals("A") ? "CTRX" : "ECTRX";
				brCodeCli = brCodeCli.concat(clientInformation.getTrxCurrId());
				
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
//					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
//						tr1.setGlType("PU");
//					}else {
						tr1.setGlType("SE");
//					}
					
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
					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
						tr1.setGlTrxDesc(descCli + " - (" + netTtlValueCliPrCli + ")");
					}else {
						tr1.setGlTrxDesc(descCli + " - " + netTtlValueCliPrCli);
					}
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
						if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
							org = org.negate();
						}
						
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
				tr3.setTrxOldVoucherId(trxOldVoucherId);
				tr3.setTrxStatusData("11");
				tr3.setCreateOn(now);
				tr3.setCreateBy(param.get(Param.USER).toString());
				if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
					tr3.setTrxDescription(descCli + " - (" + netTtlValueCli + ")");
					tr3.setTrxInsInsured(descCli + " - (" + netTtlValueCli +")"); 
					tr3.setTrxAmountDue(netTtlValueCli.negate()); 
				}
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
				journal.put("insSub", interest.getTrxInsSub());
				journal.put("isComm", Boolean.FALSE);
				String endorsementType = "Endorsement - ";
//				if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
//					journal.put("document", "Closing - CN");
//					journal.put("type", "CREDIT NOTE");
//					endorsementType = endorsementType.concat("DED");
//					
//					String fn = "Closing - CN - " + client.getCliName();
//					
//					action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', 'CREDIT NOTE', '"+endorsementType+"')\">" 
//							+ "<i class=\"fa fa-print\"></i>" 
//							+ "</button>&nbsp;";
//					action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', '" + fn + "', 'CREDIT NOTE', '"+endorsementType+"')\">" 
//							+ "<i class=\"fas fa-file-word\"></i>" 
//							+ "</button>";
//				}else {
					journal.put("document", "Closing - DN");
					journal.put("type", "DEBIT NOTE");
					endorsementType = endorsementType.concat(trxTypeAdDesc);
					String fn = "Closing - DN - " + client.getCliName();
					
					action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', 'DEBIT NOTE', '"+endorsementType+"')\">" 
							+ "<i class=\"fa fa-print\"></i>" 
							+ "</button>&nbsp;";
					action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', '" + fn + "', 'DEBIT NOTE', '"+endorsementType+"')\">" 
							+ "<i class=\"fas fa-file-word\"></i>" 
							+ "</button>";
					
//				}
				journal.put("endorsementType", endorsementType);
				
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
					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
						tr3a.setTrxDueAmount(netTtlValueCli.multiply(shareRatePrCli).negate());
					}else {
						tr3a.setTrxDueAmount(netTtlValueCli.multiply(shareRatePrCli));
					}
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
					
					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
						tr12.setTrxInvcAmount(netRcCli.multiply(shareRateCli).multiply(tr1.getPrRate()).negate());
						tr12.setTrxOrgAmount(netRcCli.multiply(shareRateCli).multiply(tr1.getPrRate()).negate());
					}else {
						tr12.setTrxInvcAmount(netRcCli.multiply(shareRateCli).multiply(tr1.getPrRate()));
						tr12.setTrxOrgAmount(netRcCli.multiply(shareRateCli).multiply(tr1.getPrRate()));
					}
					
					tr12.setTrxDiscAmount(discValue.multiply(shareRateCli).multiply(tr1.getPrRate()));
					tr12.setTrxDeducAmount(deducCli.multiply(shareRateCli).multiply(tr1.getPrRate()));
					tr12.setTrxNetTou(netTouValue);
					tr12.setTrxNetTtl(netTtlValue);
					
					tr12.setTrxInsOfficer(clientInformation.getTrxOfficer());
					tr12.setTrxOldType(RQ);
					tr12.setTrxOldVoucherId(trxOldVoucherId);
					tr12.setCreateOn(now);
					tr12.setCreateBy(param.get(Param.USER).toString());
					tr12.setModifyOn(now);
					tr12.setModifyBy(param.get(Param.USER).toString());
					
					if(param.get(EXTENSION.DESC) != null){
						tr12.setTrxDescription(extDesc);
					}
					
					tr12.setTrxSource("2");
					
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
				String descReins = "RQ " + trxOldVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + remarksII;
				if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
					descReins = "Endorsement - DED - ".concat(descReins);
				}else {
					descReins = "Endorsement - ADD - ".concat(descReins);
				}
				
	//			BigDecimal shareRateReins = reins.getTrxInsShare().divide(shareTotalReins, 10, RoundingMode.HALF_UP);
				BigDecimal reinsPortion = BigDecimal.ONE;
				BigDecimal shareRateReins = BigDecimal.ONE;
				if(reins.getTrxPremPortion() != null) {
					if(reins.getTrxPremPortion().signum() != 0)
						reinsPortion = reins.getTrxPremPortion().divide(BD_100, 10, RoundingMode.HALF_UP);
				}
				MA0015AEntity exchangeRateReins = mA0015ARepo.findByExDateAndExCurrId(appDate, reins.getTrxCurrId());
				
//				String brCodeReins = "VTRX" + reins.getTrxCurrId();
//				String brCodeReins = interestI.get(j-1).getTrxTypeAd().equals("A") ? "VTRX" : "ECTRX";
				String brCodeReins = interestI.get(j-1).getTrxTypeAd().equals("A") ? "VTRX" : "EVTRX";
				brCodeReins = brCodeReins.concat(reins.getTrxCurrId());
				
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
				
				logger.info("premi per reas : {}, deduc : {}, netRc : {}.", printCurr(premiCli), printCurr(deducCli), printCurr(netRc));
				
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
//					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
//						tr1.setGlType("SE");
//					}else {
						tr1.setGlType("PU");
//					}
					
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
					
					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
						tr1.setGlTrxDesc(descReins + " - (" + netTtlValueCliPrReins+")");
					}else {
						tr1.setGlTrxDesc(descReins + " - " + netTtlValueCliPrReins);
					}
					
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
						if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
							org = org.negate();
						}
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
				tr3.setTrxOldVoucherId(trxOldVoucherId); 
				tr3.setTrxStatusData("11");
				tr3.setCreateOn(now);
				tr3.setCreateBy(param.get(Param.USER).toString());
				if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
					tr3.setTrxDescription(descReins + " - (" + netTouValueReins+")");
					tr3.setTrxInsInsured(descReins + " - (" + netTouValueReins+")"); 
					tr3.setTrxAmountDue(netTouValueReins.negate()); 
				}
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
				journal.put("insSub", interest.getTrxInsSub());
				String endorsementType = "Endorsement - ";
				journal.put("isComm", Boolean.FALSE);
//				if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
//					journal.put("document", "Closing - DN");
//					journal.put("type", "DEBIT NOTE");
//					endorsementType = endorsementType.concat("DED");
//					String fn = "Closing - DN - " + client.getCliName();
//					
//					action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', 'DEBIT NOTE', '"+endorsementType+"')\">" 
//							+ "<i class=\"fa fa-print\"></i>" 
//							+ "</button>&nbsp;";
//					action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', '" + fn + "', 'DEBIT NOTE', '"+endorsementType+"')\">" 
//							+ "<i class=\"fas fa-file-word\"></i>" 
//							+ "</button>";
//				}else {
					journal.put("document", "Closing - CN");
					journal.put("type", "CREDIT NOTE");
					endorsementType = endorsementType.concat(trxTypeAdDesc);
					String fn = "Closing - CN - " + client.getCliName();
		
					action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', 'CREDIT NOTE', '"+endorsementType+"')\">" 
							+ "<i class=\"fa fa-print\"></i>" 
							+ "</button>&nbsp;";
					action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + client.getCliName() + "', '" + fn + "', 'CREDIT NOTE', '"+endorsementType+"')\">" 
							+ "<i class=\"fas fa-file-word\"></i>" 
							+ "</button>";
//				}
				
				journal.put("endorsementType", endorsementType);
				
				
				
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
					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
						tr3a.setTrxDueAmount(netTouValueReins.multiply(shareRatePrReins.negate()));
					}else {
						tr3a.setTrxDueAmount(netTouValueReins.multiply(shareRatePrReins));
					}
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
					
					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
						tr12.setTrxInvcAmount(netPy.multiply(shareRateReins).multiply(tr1.getPrRate()).negate());
						tr12.setTrxOrgAmount(netPy.multiply(shareRateReins).multiply(tr1.getPrRate()).negate());
						tr12.setTrxBrkrFee(brkrFee.multiply(shareRateReins).multiply(tr1.getPrRate()).negate());
						tr12.setTrxTaxinBf(taxinBf.multiply(shareRateReins).multiply(tr1.getPrRate()).negate());
					}else {
						tr12.setTrxInvcAmount(netPy.multiply(shareRateReins).multiply(tr1.getPrRate()));
						tr12.setTrxOrgAmount(netPy.multiply(shareRateReins).multiply(tr1.getPrRate()));
						tr12.setTrxBrkrFee(brkrFee.multiply(shareRateReins).multiply(tr1.getPrRate()));
						tr12.setTrxTaxinBf(taxinBf.multiply(shareRateReins).multiply(tr1.getPrRate()));
					}
					
					if(isExtension){
						//reset values
						tr12.setTrxBrkrFee(mapValue.get(BRKR_FEE_VALUE));
						tr12.setTrxTaxinBf(mapValue.get(TAXIN_BF_VALUE));
					}
					tr12.setTrxNetTou(netTouValue);
					tr12.setTrxNetTtl(netTtlValue);
					
					tr12.setTrxInsOfficer(tr0006.getTrxOfficer());
					tr12.setTrxOldType(RQ);
					tr12.setTrxOldVoucherId(trxOldVoucherId);
					tr12.setCreateOn(now);
					tr12.setCreateBy(param.get(Param.USER).toString());
					tr12.setModifyOn(now);
					tr12.setModifyBy(param.get(Param.USER).toString());
					
					tr12Entities.add(tr12);
					
					if(param.get(EXTENSION.DESC) != null){
						tr12.setTrxDescription(extDesc);
					}
					
					tr12.setTrxSource("2");
				
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
				String descComm = "RQ " + trxOldVoucherId + ", " + typeOfCover + ", " + insuredNameCI + ", " + remarksII;
				if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
					descComm = "Endorsement - DED - ".concat(descComm);
				}else {
					descComm = "Endorsement - ADD - ".concat(descComm);
				}
					
				MA0015AEntity exchangeRate = mA0015ARepo.findByExDateAndExCurrId(appDate, tr0006b.getTrxCurrId());
					
				String brCode = "EUTRX" + tr0006b.getTrxCurrId();
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
					
					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
						tr1.setGlTrxDesc(descComm + " - (" + commOutValue +")");
					}else {
						tr1.setGlTrxDesc(descComm + " - "+ commOutValue);
					}
					
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
						if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
							org = org.negate();
						}
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
				tr3.setTrxOldVoucherId(trxOldVoucherId); 
				tr3.setTrxStatusData("11");
				tr3.setCreateOn(now);
				tr3.setCreateBy(param.get(Param.USER).toString());
				if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
					tr3.setTrxDescription(descComm + " - (" + commOutValue+")");
					tr3.setTrxInsInsured(descComm + " - (" + commOutValue+")"); 
					tr3.setTrxAmountDue(commOutValue.negate());
				}
				
				
				List<Map<String, Object>> table = mapTable.get(commission.getTrxSaCode());
				if(table == null)
					table = new ArrayList<Map<String,Object>>();
				
				Map<String, Object> journal = new HashMap<String, Object>();
				journal.put("voucherId", tr3.getTrxVoucherId());
				journal.put("name", agent.getSaName());
				journal.put("insSub", interest.getTrxInsSub());
				journal.put("isComm", Boolean.TRUE);
				String endorsementType = "Endorsement - ";
//				if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
//					journal.put("document", "Closing - DN");
//					journal.put("type", "DEBIT NOTE");
//					endorsementType = endorsementType.concat("DED");
//					String fn = "Closing - DN - " + agent.getSaName();
//					
//					String action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + agent.getSaName() + "', 'DEBIT NOTE', '"+endorsementType+"')\">" 
//							+ "<i class=\"fa fa-print\"></i>" 
//							+ "</button>&nbsp;";
//					action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + agent.getSaName() + "', '" + fn + "', 'DEBIT NOTE', '"+endorsementType+"')\">" 
//							+ "<i class=\"fas fa-file-word\"></i>" 
//							+ "</button>";
//					journal.put("action", action);
//				}else {
					journal.put("document", "Closing - CN");
					journal.put("type", "CEBIT NOTE");
					endorsementType = endorsementType.concat(trxTypeAdDesc);
					String fn = "Closing - CN - " + agent.getSaName();
					
					String action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + agent.getSaName() + "', 'CREDIT NOTE', '"+endorsementType+"')\">" 
							+ "<i class=\"fa fa-print\"></i>" 
							+ "</button>&nbsp;";
					action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + interest.getTrxInsSub() + "', '" + agent.getSaName() + "', '" + fn + "', 'CREDIT NOTE', '"+endorsementType+"')\">" 
							+ "<i class=\"fas fa-file-word\"></i>" 
							+ "</button>";
					journal.put("action", action);
//				}
					
				journal.put("endorsementType", endorsementType);
				
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
					if(interestI.get(j-1).getTrxTypeAd().equals("D")) {
						tr3a.setTrxDueAmount(valueCommOut.negate());
					}else {
						tr3a.setTrxDueAmount(valueCommOut);
					}
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
					if(interestI.get(j-1).getTrxTypeAd().equals("D")){
						tr12.setTrxInvcAmount(tr1.getGlTrxValueOrg().negate());
						tr12.setTrxOrgAmount(tr12.getTrxInvcAmount().subtract(tr12.getTrxSetAmount().negate()));
					}else {
						tr12.setTrxInvcAmount(tr1.getGlTrxValueOrg());
						tr12.setTrxOrgAmount(tr12.getTrxInvcAmount().subtract(tr12.getTrxSetAmount()));
					}
					
					tr12.setTrxSetAmount(BigDecimal.ZERO);
					tr12.setTrxInsOfficer(tr0006.getTrxOfficer());
					tr12.setTrxOldType(RQ);
					tr12.setTrxOldVoucherId(trxOldVoucherId);
					tr12.setCreateOn(now);
					tr12.setCreateBy(param.get(Param.USER).toString());
					tr12.setModifyOn(now);
					tr12.setModifyBy(param.get(Param.USER).toString());
					
					
					
					tr12.setTrxSource("2");
					
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
		
		String remarks = Param.getStr(param, REMARKS);
		tR0006Repo.updateTrxDataStatus("11", remarks, RQ, trxVoucherId);
		
		for(String key : mapTable.keySet()) {
			tableAll.addAll(mapTable.get(key));
		}
		
		return tableAll;
	}
	
	public static String printCurr(BigDecimal value) {
	    return NumberFormat.getCurrencyInstance().format(value).replace("$", "");
	}
	
}
