package com.biru.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

import com.biru.ReBrokerConstants.CODE;
import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0015AEntity;
import com.biru.entity.MA0018Entity;
import com.biru.entity.ResponseEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0007Entity;
import com.biru.entity.TR0012Entity;
import com.biru.helper.SettlementHelper;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0007Repo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewInqSettlementRepo;
import com.biru.service.CommonService;
import com.biru.service.SettlementService;

@Service
public class SettlementServiceImpl extends AbstractCommon implements SettlementService {
	
	@Autowired
	private MA0004Repo ma0004Repo;
	
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
	private TR0007Repo tr0007Repo;
	
	@Autowired
	private TR0012Repo tr0012Repo;
	
	@Autowired
	private ViewInqSettlementRepo viewInqSettlementRepo;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	private Logger logger = LoggerFactory.getLogger(SettlementServiceImpl.class);

	private static final String ADMIN_COST_VALUE 	= "ADMIN_COST_VALUE";
	private static final String ADMIN_FEE_VALUE 	= "ADMIN_FEE_VALUE";
	private static final String BANK_FEE_VALUE 		= "BANK_FEE_VALUE";
	private static final String BRKR_FEE_VALUE 		= "BRKR_FEE_VALUE";
	private static final String COMM_OUT_VALUE 		= "COMM_OUT_VALUE";
	private static final String COST_POLICY_VALUE 	= "COST_POLICY_VALUE";
	private static final String CURR_RATE_VALUE 	= "CURR_RATE_VALUE";
	private static final String DEDUC_VALUE 		= "DEDUC_VALUE";
	private static final String DISC_VALUE 			= "DISC_VALUE";
	private static final String GROSS_BF_VALUE 		= "GROSS_BF_VALUE";
	private static final String GROSS_VALUE 		= "GROSS_VALUE";
	private static final String NET_INC_VALUE 		= "NET_INC_VALUE";
	private static final String NET_OS_VALUE		= "NET_OS_VALUE";
	private static final String NET_OS_NEW			= "NET_OS_NEW";
	private static final String NET_TOU_VALUE 		= "NET_TOU_VALUE";
	private static final String NET_TTL_VALUE 		= "NET_TTL_VALUE";
	private static final String OTHERS_VALUE 		= "OTHERS_VALUE";
	private static final String OTHERS1_VALUE 		= "OTHERS1_VALUE";
	private static final String OTHERS2_VALUE 		= "OTHERS2_VALUE";
	private static final String OTHERS3_VALUE 		= "OTHERS3_VALUE";
	private static final String OTHERS4_VALUE 		= "OTHERS4_VALUE";
	private static final String PREMIUM_VALUE 		= "PREMIUM_VALUE";
	private static final String RL_KURS_VALUE		= "RL_KURS_VALUE";
	private static final String STAMP_DUTY_VALUE 	= "STAMP_DUTY_VALUE";
	private static final String TAXIN_BF_VALUE 		= "TAXIN_BF_VALUE";
	private static final String TAXIN_CL_VALUE 		= "TAXIN_CL_VALUE";
	private static final String TAXIN_OTH_VALUE 	= "TAXIN_OTH_VALUE";
	private static final String TAX_OUT_VALUE 		= "TAX_OUT_VALUE";
	private static final String TI_AMOUNT 			= "TI_AMOUNT";

	private static final String AB_AMOUNT			= "AB_AMOUNT";
	private static final String DI_AMOUNT			= "DI_AMOUNT";
	private static final String DI_OLD_RATE			= "DI_OLD_RATE";
	private static final String PR_AMOUNT			= "PR_AMOUNT";
	private static final String PR_AMOUNT_BF		= "PR_AMOUNT_BF";
	private static final String PR_AMOUNT_OT		= "PR_AMOUNT_OT";

	@Override
	public DatatableSet inquiry(Map<String, Object> param) {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String type = Param.getStr(param, "type");
		String client = Param.getStrWithDef(param, "client");
		
		List<String> types;
		if(type.equals("RC"))	//Received (RC)
			types = Arrays.asList("SE","SO");
		else					//Payment (PY)
			types = Arrays.asList("PU","PO");
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		MA0014Entity paramDate = ma0014Repo.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);
		Page<SettlementHelper> data = viewInqSettlementRepo.findSettlement(paramDate.getPaChildValue(), types, client, pageable);
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity save(Map<String, Object> param) throws ParseException {
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		
		String type = Param.getStr(param, "type");				//RC 		||	PY
		String typeDesc = Param.getStr(param, "typeDesc");		//Received	|| 	Payment
		String client = Param.getStr(param, "client");
		String clientCode = Param.getStr(param, "clientCode");
		String currTrx = Param.getStr(param, "currTrx");		//IDR || USD || SGD || ...
		String currBank = Param.getStr(param, "currBank");
		BigDecimal exchangeBank = Param.getBdWithDef(param, "exchangeBank");
//		BigDecimal exchangeTrx = Param.getBdWithDef(param, "exchangeTrx");
		Date transactionDate = Param.getDate(param, "transactionDate");
		String bankCode = Param.getStr(param, "bankCode");
		BigDecimal bankFee = Param.getBdWithDef(param, "bankFee");
		BigDecimal pyrc = Param.getBdWithDef(param, "pyrc");
		BigDecimal wht = Param.getBdWithDef(param, "wht");
		BigDecimal total = Param.getBdWithDef(param, "total");
		BigDecimal different = Param.getBdWithDef(param, "different");
		String referenceId = Param.getStr(param, "referenceId");
		Boolean adjustment = Boolean.valueOf(Param.getStr(param, "adjustment"));
//		Boolean isWht = Boolean.valueOf(Param.getStr(param, "isWht"));
		BigDecimal outstandingOrg = Param.getBdWithDef(param, "outstandingOrg");
		
		ArrayList<Map<String, Object>> rows = (ArrayList<Map<String, Object>>) param.get("rows");
		if(rows.size()==0) {
			response.setStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());
			response.setMsgValidation("No data selected!");
			return response;
		}
		
		Date now = Calendar.getInstance().getTime();
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(transactionDate);
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		String brCode = "CB";
		
		if(currTrx.equals(currBank))
			brCode = "CS";
		else if(currTrx.equals("IDR"))
			brCode = "CT";
		
		if(type.equals("RC"))
			brCode += "RC";
		else
			brCode += "PY";
		
		if(brCode.startsWith("CT"))
			brCode += currBank;
		else
			brCode += currTrx;
		
		List<MA0018Entity> businessRule = ma0018Repo.findByBrCode(brCode);
		
		logger.info("Settlement with brCode : {}.", brCode);
		logger.info("business rule : {}.", businessRule);
		
		if(businessRule.size() == 0) {
			response.setStatus(HttpStatus.PRECONDITION_FAILED.getReasonPhrase());
			response.setMsgValidation("Journal Set not found!");
			return response;
		}
		
		Collections.sort(rows, new Comparator<Map<String, Object>>() { //priority
	        public int compare(Map<String, Object> map1, Map<String, Object> map2) {	        	
	            Date dueDate1 = null;
	            Date dueDate2 = null;
				try {
					dueDate1 = formatDateId.parse(Param.getStr(map1, "trxDueDate"));
					dueDate2 = formatDateId.parse(Param.getStr(map2, "trxDueDate"));
				}catch(ParseException pe) {
					pe.printStackTrace();
				}
	            
	            int dueDateComp = dueDate1.compareTo(dueDate2);

	            if(dueDateComp!=0)
	               return dueDateComp;

	            Long idKey1 = Param.getLong(map1, "idKey");
	            Long idKey2 = Param.getLong(map2, "idKey");
	            
	            return idKey1.compareTo(idKey2);
	        }
	    });
		
		ArrayList<Map<String, Object>> plus = new ArrayList<Map<String, Object>>();
		ArrayList<Map<String, Object>> negative = new ArrayList<Map<String, Object>>();
		
		for(Map<String, Object> data : rows) {
			BigDecimal settledValue = Param.getBdWithDef(data, "settledValue");
			if(settledValue.signum() >= 0)
				plus.add(data);
			else
				negative.add(data);
		}
		
		if(outstandingOrg.signum() > 0) {
			rows.clear();
			rows.addAll(negative);
			rows.addAll(plus);
		}
		if(outstandingOrg.signum() < 0) {
			rows.clear();
			rows.addAll(plus);
			rows.addAll(negative);
		}
		
		BigDecimal exchangeOld = BigDecimal.ZERO;
		List<TR0012Entity> tr12Update = new ArrayList<TR0012Entity>();
		List<TR0007Entity> tr7MappingSet = new ArrayList<TR0007Entity>();
		for(Map<String, Object> row : rows) {
			String trxType = Param.getStr(row, "trxType");
			String trxVoucherId = Param.getStr(row, "trxVoucherId");
			Integer trxCountInv = Param.getInt(row, "trxCountInv");
			
			TR0007Entity tr7 = new TR0007Entity();
			tr7.setTrxOldTrxId(trxType);
			tr7.setTrxOldVoucherId(trxVoucherId);
			tr7.setTrxOldCountInv(trxCountInv);
			
			BigDecimal settledValue = Param.getBdWithDef(row, "settledValue");
			
			Long idKey = Param.getLong(row, "idKey");
			Optional<TR0012Entity> update = tr0012Repo.findById(idKey);
			BigDecimal trxInvcAmount = update.get().getTrxInvcAmount() == null
					? BigDecimal.ZERO : update.get().getTrxInvcAmount();
			BigDecimal trxSetAmount = update.get().getTrxSetAmount() == null
					? BigDecimal.ZERO : update.get().getTrxSetAmount();
			
			row.put("settlement", settledValue);
			row.put("oldSettlement", update.get().getTrxSetAmount());
			trxSetAmount = trxSetAmount.add(settledValue);
			update.get().setTrxSetAmount(trxSetAmount);
			update.get().setModifyBy(param.get(Param.USER).toString());
			update.get().setModifyOn(now);
			
			BigDecimal out = Param.getBdWithDef(row, "outstanding");
			if(trxSetAmount.compareTo(trxInvcAmount) >= 0 && out.signum() > 0)
				update.get().setTrxDataStatus("13");
			else if(trxSetAmount.compareTo(trxInvcAmount) <= 0 && out.signum() < 0)
				update.get().setTrxDataStatus("13");
			
			tr12Update.add(update.get());
			
			tr7.setTrxSetAmount(settledValue);
			tr7MappingSet.add(tr7);
			
			exchangeOld = update.get().getTrxCurrRate();
		}
		
		String oldType = null;
		String oldVoucherId = null;
		List<String> typeAndVoucher = new ArrayList<String>();
		for(Map<String, Object> row : rows) {
			oldType = Param.getStr(row, "trxType");
			oldVoucherId =  Param.getStr(row, "trxVoucherId");
			typeAndVoucher.add(oldType+" "+oldVoucherId);
		}
				
		String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		
		//NET_OS_NEW - rate saat settlement
		MA0015AEntity trxRate = ma0015ARepo.findByExDateAndExCurrId(commonService.getAppDate(), currTrx);
		TR0001Entity tr1 = new TR0001Entity();
		tr1.setGlTrxClass("OP");
		tr1.setGlType(type);
		tr1.setGlVoucherId(voucherId);
		tr1.setGlTrxDate(calTrx.getTime());
		tr1.setGlTrxDue(calTrx.getTime());
		tr1.setGlTrxMonth(month.byteValue());
		tr1.setGlTrxYear(year.shortValue());
		tr1.setGlTrxOfficeId("0");
		tr1.setGlTrxProject("0000");
		tr1.setGlTrxClient(clientCode);
		tr1.setGlReffId(referenceId);
		
		String desc = typeDesc + " to " + typeAndVoucher.toString().replace("[", "").replace("]", "") + ", " + client;
		tr1.setGlTrxDesc(desc);
		
		tr1.setGlTrxStatus("0");
		tr1.setGlDataStatus("11");
		tr1.setCreateOn(now);
		tr1.setCreateBy(param.get(Param.USER).toString());
		tr1.setModifyOn(now);
		tr1.setModifyBy(param.get(Param.USER).toString());
		
		Map<String, BigDecimal> mapTR12 = getMapTR12(rows);
		Map<String, BigDecimal> mapValue = new HashMap<String, BigDecimal>();		
		mapValue.put(DI_AMOUNT, BigDecimal.ZERO);
		if(adjustment) {
			mapValue.put(DI_AMOUNT, different);	
			mapValue.put(DI_OLD_RATE, different);
		}
		
		BigDecimal commOutValue = mapTR12.get(COMM_OUT_VALUE) == null
				? BigDecimal.ZERO : mapTR12.get(COMM_OUT_VALUE);
		BigDecimal bankFeeValue = mapTR12.get(BANK_FEE_VALUE) == null
				? BigDecimal.ZERO : mapTR12.get(BANK_FEE_VALUE);	
		
		BigDecimal PR_AMOUNT_VAL = pyrc.multiply(exchangeBank);
		BigDecimal PR_AMOUNT_BF_VAL = BigDecimal.ZERO;
		BigDecimal PR_AMOUNT_OT_VAL = BigDecimal.ZERO;
		if(commOutValue.signum() != 0) {
			PR_AMOUNT_OT_VAL = PR_AMOUNT_VAL;
			PR_AMOUNT_VAL = BigDecimal.ZERO;
		}else if(bankFeeValue.signum() != 0) {
			PR_AMOUNT_BF_VAL = PR_AMOUNT_VAL;
			PR_AMOUNT_VAL = BigDecimal.ZERO;
		}
		
		logger.info("Settlement value commOutValue : {}, bankFeeValue : {}", commOutValue, bankFeeValue);
		
		mapValue.put(PR_AMOUNT, PR_AMOUNT_VAL);
		mapValue.put(PR_AMOUNT_BF, PR_AMOUNT_BF_VAL);
		mapValue.put(PR_AMOUNT_OT, PR_AMOUNT_OT_VAL);
		mapValue.put(TAX_OUT_VALUE, wht.multiply(exchangeBank));
		mapValue.put(AB_AMOUNT, bankFee.multiply(exchangeBank));
		
//		if(isWht)
//			mapTR12.remove(TAX_OUT_VALUE);

		List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
		for(MA0018Entity ma0018 : businessRule) {
			String code = ma0018.getBrChildValue().trim();		
			
			if(mapTR12.keySet().contains(code)) {
				for(Map<String, Object> row : rows) {
					TR0002Entity t2 = new TR0002Entity();
					t2.setGlTrxClass(tr1.getGlTrxClass());
					t2.setGlType(tr1.getGlType());
					t2.setGlVoucherId(tr1.getGlVoucherId());
					t2.setGlAccount(ma0018.getBrChildCoa());
					
					BigDecimal settlement = Param.getBdWithDef(row, "settlement");
					BigDecimal oldSettlement = Param.getBdWithDef(row, "oldSettlement");
					
					BigDecimal setAmount = Param.getBdWithDef(row, "trxSetAmount");
					BigDecimal trxCurrRate = Param.getBdWithDef(row, "trxCurrRate");
					String trxDescription = Param.getStr(row, "trxDescription");
					
					t2.setGlCurrId(currTrx);
					t2.setGlCurrRate(trxCurrRate);
					t2.setGlDescription(trxDescription);
					
					BigDecimal org = BigDecimal.ZERO;
					
					Long idKey = Param.getLong(row, "idKey");
					Optional<TR0012Entity> oldTr12 = tr0012Repo.findById(idKey);

					BigDecimal commOutT12 = mapTR12.get(COMM_OUT_VALUE) == null
							? BigDecimal.ZERO : mapTR12.get(COMM_OUT_VALUE);
					BigDecimal bankFeeT12 = mapTR12.get(BANK_FEE_VALUE) == null
							? BigDecimal.ZERO : mapTR12.get(BANK_FEE_VALUE);
					
					if(code.equals(NET_OS_VALUE) || code.equals(NET_OS_NEW)) {
						if(bankFeeT12.signum() != 0 && type.equals("PY"))
							org = BigDecimal.ZERO;
						else if(commOutT12.signum() != 0 && type.equals("PY"))
							org = BigDecimal.ZERO;
						else if(commOutT12.signum() != 0 && bankFeeT12.signum() != 0)
							org = BigDecimal.ZERO;
						else
							org = settlement;
					}else if(setAmount.compareTo(BigDecimal.ZERO) != 0)
						org = BigDecimal.ZERO;						
					else if(code.equals(TI_AMOUNT))
						org = oldTr12.get().getTrxIntAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxIntAmount();
					else if(code.equals(PREMIUM_VALUE))
						org	= oldTr12.get().getTrxInvcAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxInvcAmount();
					else if(code.equals(ADMIN_COST_VALUE))
						org = oldTr12.get().getTrxComAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxComAmount();
					else if(code.equals(DISC_VALUE)) 
						org = oldTr12.get().getTrxDiscAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxDiscAmount();
					else if(code.equals(DEDUC_VALUE))
						org = oldTr12.get().getTrxDeducAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxDeducAmount();					
					else if(code.equals(BRKR_FEE_VALUE))
						org = oldTr12.get().getTrxBrkrFee() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxBrkrFee();
					else if(code.equals(TAXIN_BF_VALUE))
						org = oldTr12.get().getTrxTaxinBf() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxTaxinBf();					
					else if(code.equals(TAXIN_CL_VALUE))
						org = oldTr12.get().getTrxTaxinCl() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxTaxinCl();					
					else if(code.equals(GROSS_VALUE))
						org = oldTr12.get().getTrxGrossAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxGrossAmount();					
					else if(code.equals(GROSS_BF_VALUE))
						org = oldTr12.get().getTrxGrossBf() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxGrossBf();					
					else if(code.equals(CURR_RATE_VALUE))
						org = oldTr12.get().getTrxCurrRate() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxCurrRate();
					else if(code.equals(COMM_OUT_VALUE)) {
						org = oldTr12.get().getTrxComoAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxComoAmount();
					
						if(bankFeeT12.signum() != 0 && type.equals("PY"))
							org = BigDecimal.ZERO;
						
						if(org.signum() != 0)
							org = org.subtract(wht);
					}else if(code.equals(OTHERS1_VALUE))
						org = oldTr12.get().getTrxOthers1Amount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxOthers1Amount();
					else if(code.equals(OTHERS2_VALUE))
						org = oldTr12.get().getTrxOthers2Amount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxOthers2Amount();
					else if(code.equals(OTHERS3_VALUE))
						org = oldTr12.get().getTrxOthers3Amount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxOthers3Amount();					
					else if(code.equals(OTHERS4_VALUE))
						org = oldTr12.get().getTrxOthers4Amount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxOthers4Amount();
					else if(code.equals(NET_TOU_VALUE))
						org = oldTr12.get().getTrxNetTou() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxNetTou();					
					else if(code.equals(NET_TTL_VALUE))
						org = oldTr12.get().getTrxNetTtl() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxNetTtl();
					else if(code.equals(COST_POLICY_VALUE))
						org = oldTr12.get().getTrxPolAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxPolAmount();
					else if(code.equals(STAMP_DUTY_VALUE))
						org = oldTr12.get().getTrxSdutyAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxSdutyAmount();
					else if(code.equals(OTHERS_VALUE))
						org = oldTr12.get().getTrxOthersAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxOthersAmount();					
					else if(code.equals(ADMIN_FEE_VALUE))
						org = oldTr12.get().getTrxAdminAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxAdminAmount();
					else if(code.equals(BANK_FEE_VALUE)) {
						org = oldTr12.get().getTrxBankAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxBankAmount();
						
						if(commOutT12.signum() != 0 && type.equals("PY"))
							org = BigDecimal.ZERO;
						
						if(org.signum() != 0)
							org = org.subtract(wht);
					}else if(code.equals(NET_INC_VALUE))
						org = oldTr12.get().getTrxIncOthers() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxIncOthers();
					else if(code.equals(TAXIN_OTH_VALUE))
						org = oldTr12.get().getTrxTaxinOth() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxTaxinOth();
					else if(code.equals(TAX_OUT_VALUE))
						org = oldTr12.get().getTrxWithAmount() == null 
							? BigDecimal.ZERO : oldTr12.get().getTrxWithAmount();
					 
					BigDecimal t12Value = org;
					if((code.equals(BRKR_FEE_VALUE) || code.equals(TAXIN_BF_VALUE)) 
							&& adjustment)
						org = t12Value;
					else if((code.equals(BRKR_FEE_VALUE) || code.equals(TAXIN_BF_VALUE)) 
							&& (outstandingOrg.signum() > 0 && different.signum() <= 0))
						org = t12Value;
					else if((code.equals(BRKR_FEE_VALUE) || code.equals(TAXIN_BF_VALUE)) 
							&& (outstandingOrg.signum() < 0 && different.signum() >= 0))
						org = t12Value;
					else if((code.equals(BRKR_FEE_VALUE) || code.equals(TAXIN_BF_VALUE))
							&& (outstandingOrg.signum() > 0 && different.signum() > 0 && !adjustment))
						org = BigDecimal.ZERO;
					else if((code.equals(BRKR_FEE_VALUE) || code.equals(TAXIN_BF_VALUE))
							&& (outstandingOrg.signum() < 0 && different.signum() < 0 && !adjustment))
						org = BigDecimal.ZERO;
					else if(oldSettlement.signum() != 0 && !(code.equals(NET_OS_VALUE) || code.equals(NET_OS_NEW) || code.equals(PREMIUM_VALUE)))
						org = BigDecimal.ZERO;
					
					BigDecimal idr = org.multiply(trxCurrRate);
					if(code.equals(NET_OS_NEW))
						idr = org.multiply(trxRate.getExKmkRate());
					
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
			}else {
				TR0002Entity t2 = new TR0002Entity();
				t2.setGlTrxClass(tr1.getGlTrxClass());
				t2.setGlType(tr1.getGlType());
				t2.setGlVoucherId(tr1.getGlVoucherId());
				t2.setGlAccount(ma0018.getBrChildCoa());
				
				MA0004Entity account = ma0004Repo.findByCoaCode(ma0018.getBrChildCoa());
				
				Map<String, Object> p = new HashMap<String, Object>();
				p.put("coaCode", account.getCoaCode());
				p.put("date", formatDateId.format(calTrx.getTime()));
				BigDecimal exchangeCoa = new BigDecimal(((String) commonService.exchangeNonEom(p)).replaceAll(",", ""));
				
				t2.setGlCurrId(account.getCoaCurrId());
				t2.setGlDescription("");
				
				BigDecimal org = BigDecimal.ZERO;
				BigDecimal idr = BigDecimal.ZERO;
				if(code.equals(DI_AMOUNT)) {					
					org = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
					idr = org.multiply(exchangeCoa);
				}else if(code.equals(DI_OLD_RATE)) {					
					org = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
					idr = org.multiply(exchangeOld);
				}else { 
					idr = mapValue.get(code) == null ? BigDecimal.ZERO : mapValue.get(code);
					org = idr.divide(exchangeCoa, 10, RoundingMode.HALF_UP);
				}
				
				t2.setGlCurrRate(exchangeCoa);
				
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
		}
		
		//BANK
		TR0002Entity tr2Bank = new TR0002Entity();
		tr2Bank.setGlTrxClass(tr1.getGlTrxClass());
		tr2Bank.setGlType(tr1.getGlType());
		tr2Bank.setGlVoucherId(tr1.getGlVoucherId());
		tr2Bank.setGlAccount(bankCode);
		tr2Bank.setGlCurrId(currBank);
		tr2Bank.setGlCurrRate(exchangeBank);
		tr2Bank.setGlDescription("");
		tr2Bank.setGlOrgDebit(BigDecimal.ZERO);
		tr2Bank.setGlIdrDebit(BigDecimal.ZERO);
		tr2Bank.setGlOrgCredit(BigDecimal.ZERO);
		tr2Bank.setGlIdrCredit(BigDecimal.ZERO);
		
		BigDecimal amountOrg = total;
		BigDecimal amountIdr = total.multiply(exchangeBank);
		if(type.equals("RC")) {
			tr2Bank.setGlOrgDebit(amountOrg);
			tr2Bank.setGlIdrDebit(amountIdr);
		}else {
			tr2Bank.setGlOrgCredit(amountOrg);
			tr2Bank.setGlIdrCredit(amountIdr);
		}
		
		BigDecimal cIdr = BigDecimal.ZERO;
		BigDecimal dIdr = BigDecimal.ZERO;
		
		for(TR0002Entity t2 : tr2Entities) {
			cIdr = cIdr.add(t2.getGlIdrCredit());
			dIdr = dIdr.add(t2.getGlIdrDebit());
		}
		cIdr = cIdr.add(tr2Bank.getGlIdrCredit());
		dIdr = dIdr.add(tr2Bank.getGlIdrDebit());
		
		BigDecimal release = BigDecimal.ZERO;
		release = dIdr.subtract(cIdr);
		
		if(release.compareTo(BigDecimal.ZERO) != 0) {	//RELEASE KURS
			for(MA0018Entity ma0018 : businessRule) {
				String code = ma0018.getBrChildValue().trim();
				
				if(code.equals(RL_KURS_VALUE)) {
					TR0002Entity t2 = new TR0002Entity();
					t2.setGlTrxClass(tr1.getGlTrxClass());
					t2.setGlType(tr1.getGlType());
					t2.setGlVoucherId(tr1.getGlVoucherId());
					t2.setGlAccount(ma0018.getBrChildCoa());
					
					MA0004Entity account = ma0004Repo.findByCoaCode(ma0018.getBrChildCoa());
					
					Map<String, Object> p = new HashMap<String, Object>();
					p.put("coaCode", account.getCoaCode());
					p.put("date", formatDateId.format(calTrx.getTime()));
					BigDecimal exchangeCoa = new BigDecimal(((String) commonService.exchangeNonEom(p)).replaceAll(",", ""));
					
					t2.setGlCurrId(account.getCoaCurrId());
					t2.setGlCurrRate(exchangeCoa);
					t2.setGlDescription("");
					
					BigDecimal org = release;
					if(brCode.startsWith("CT"))
						org = different;
					else
						org = release;
						
					BigDecimal idr = org.multiply(exchangeCoa);
					
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
					
					tr2Entities.add(t2);
				}
			}
		}
		
		tr2Entities.add(tr2Bank);
		
		BigDecimal valueOrg = BigDecimal.ZERO;
		BigDecimal valueIdr = BigDecimal.ZERO;
		for(TR0002Entity tr2 : tr2Entities) {
			valueOrg = valueOrg.add(tr2.getGlOrgCredit());
			valueIdr = valueIdr.add(tr2.getGlIdrCredit());
		}
		
		tr1.setGlTrxValueOrg(valueOrg);
		tr1.setGlTrxValueIdr(valueIdr);		

		TR0012Entity tr12 = new TR0012Entity();
		tr12.setTrxTrxClass(tr1.getGlTrxClass());
		tr12.setTrxType(tr1.getGlType());
		tr12.setTrxVoucherId(tr1.getGlVoucherId());
		tr12.setTrxDate(tr1.getGlTrxDate());
		tr12.setTrxDueDate(tr1.getGlTrxDue());
		tr12.setTrxDataStatus("11");
		tr12.setTrxOldType(oldType);
		tr12.setTrxOldVoucherId(oldVoucherId);
		tr12.setTrxClient(clientCode);
		tr12.setTrxDescription(tr1.getGlTrxDesc());
		tr12.setTrxCurrId(currBank);
		tr12.setTrxCurrRate(exchangeBank);
		tr12.setTrxOrgAmount(pyrc);
		tr12.setTrxSetAmount(pyrc);
		tr12.setTrxCountInv(1);
		tr12.setCreateOn(now);
		tr12.setCreateBy(param.get(Param.USER).toString());
		tr12.setModifyOn(now);
		tr12.setModifyBy(param.get(Param.USER).toString());
		
		for(TR0012Entity t : tr12Update) {
			t.setTrxNxType(tr12.getTrxType());
			t.setTrxNxVoucherId(tr12.getTrxVoucherId());
			t.setTrxSetDate(calTrx.getTime());
		}
		
		for(TR0007Entity t : tr7MappingSet) {
			t.setTrxTrxId(tr12.getTrxType());
			t.setTrxVoucherId(tr12.getTrxVoucherId());
			t.setTrxSetDate(calTrx.getTime());
		}
		
		tr0001Repo.save(tr1);
		tr0002Repo.saveAll(tr2Entities);
		tr0007Repo.saveAll(tr7MappingSet);
		tr0012Repo.saveAll(tr12Update);
		tr0012Repo.save(tr12);
		
		response.setResult(tr1.getGlVoucherId());
		response.getMessage().put("description", tr1.getGlTrxDesc());
		
		return response;
	}
	
	private Map<String, BigDecimal> getMapTR12(List<Map<String, Object>> rows) {
		Map<String, BigDecimal> mapTR12 = new HashMap<String, BigDecimal>();	
		mapTR12.put("NET_OS_VALUE", BigDecimal.ZERO);	
		mapTR12.put("NET_OS_NEW", BigDecimal.ZERO);
		mapTR12.put("TI_AMOUNT", BigDecimal.ZERO);
		mapTR12.put("PREMIUM_VALUE", BigDecimal.ZERO);
		mapTR12.put("ADMIN_COST_VALUE", BigDecimal.ZERO);
		mapTR12.put("DISC_VALUE", BigDecimal.ZERO);
		mapTR12.put("DEDUC_VALUE", BigDecimal.ZERO);
		mapTR12.put("BRKR_FEE_VALUE", BigDecimal.ZERO);
		mapTR12.put("TAXIN_BF_VALUE", BigDecimal.ZERO);
		mapTR12.put("TAXIN_CL_VALUE", BigDecimal.ZERO);
		mapTR12.put("GROSS_VALUE", BigDecimal.ZERO);
		mapTR12.put("GROSS_BF_VALUE", BigDecimal.ZERO);
		mapTR12.put("CURR_RATE_VALUE", BigDecimal.ZERO);
		mapTR12.put("COMM_OUT_VALUE", BigDecimal.ZERO);
		mapTR12.put("OTHERS1_VALUE", BigDecimal.ZERO);
		mapTR12.put("OTHERS2_VALUE", BigDecimal.ZERO);
		mapTR12.put("OTHERS3_VALUE", BigDecimal.ZERO);
		mapTR12.put("OTHERS4_VALUE", BigDecimal.ZERO);
		mapTR12.put("NET_TOU_VALUE", BigDecimal.ZERO);
		mapTR12.put("NET_TTL_VALUE", BigDecimal.ZERO);
		mapTR12.put("COST_POLICY_VALUE", BigDecimal.ZERO);
		mapTR12.put("STAMP_DUTY_VALUE", BigDecimal.ZERO);
		mapTR12.put("OTHERS_VALUE", BigDecimal.ZERO);
		mapTR12.put("ADMIN_FEE_VALUE", BigDecimal.ZERO);
		mapTR12.put("BANK_FEE_VALUE", BigDecimal.ZERO);
		mapTR12.put("NET_INC_VALUE", BigDecimal.ZERO);
		mapTR12.put("TAXIN_OTH_VALUE", BigDecimal.ZERO);
//		mapTR12.put("TAX_OUT_VALUE", BigDecimal.ZERO);
		
		for(Map<String, Object> row : rows) {
			Long idKey = Param.getLong(row, "idKey");
			Optional<TR0012Entity> oldTr12 = tr0012Repo.findById(idKey);
			
			BigDecimal invcAmt = oldTr12.get().getTrxInvcAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxInvcAmount();
			BigDecimal setAmt = oldTr12.get().getTrxSetAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxSetAmount();
			
			BigDecimal NET_OS_VALUE = invcAmt.subtract(setAmt);
			mapTR12.put("NET_OS_VALUE", mapTR12.get("NET_OS_VALUE").add(NET_OS_VALUE));
			
			BigDecimal TI_AMOUNT = oldTr12.get().getTrxIntAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxIntAmount();
			mapTR12.put("TI_AMOUNT", mapTR12.get("TI_AMOUNT").add(TI_AMOUNT));
			
			BigDecimal PREMIUM_VALUE = oldTr12.get().getTrxInvcAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxInvcAmount();
			mapTR12.put("PREMIUM_VALUE", mapTR12.get("PREMIUM_VALUE").add(PREMIUM_VALUE));
			
			BigDecimal ADMIN_COST_VALUE = oldTr12.get().getTrxComAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxComAmount();
			mapTR12.put("ADMIN_COST_VALUE", mapTR12.get("ADMIN_COST_VALUE").add(ADMIN_COST_VALUE));
			
			BigDecimal DISC_VALUE = oldTr12.get().getTrxDiscAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxDiscAmount();
			mapTR12.put("DISC_VALUE", mapTR12.get("DISC_VALUE").add(DISC_VALUE));
			
			BigDecimal DEDUC_VALUE = oldTr12.get().getTrxDeducAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxDeducAmount();
			mapTR12.put("DEDUC_VALUE", mapTR12.get("DEDUC_VALUE").add(DEDUC_VALUE));
			
			BigDecimal BRKR_FEE_VALUE = oldTr12.get().getTrxBrkrFee() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxBrkrFee();
			mapTR12.put("BRKR_FEE_VALUE", mapTR12.get("BRKR_FEE_VALUE").add(BRKR_FEE_VALUE));
			
			BigDecimal TAXIN_BF_VALUE = oldTr12.get().getTrxTaxinBf() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxTaxinBf();
			mapTR12.put("TAXIN_BF_VALUE", mapTR12.get("TAXIN_BF_VALUE").add(TAXIN_BF_VALUE));
			
			BigDecimal TAXIN_CL_VALUE = oldTr12.get().getTrxTaxinCl() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxTaxinCl();
			mapTR12.put("TAXIN_CL_VALUE", mapTR12.get("TAXIN_CL_VALUE").add(TAXIN_CL_VALUE));
			
			BigDecimal GROSS_VALUE = oldTr12.get().getTrxGrossAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxGrossAmount();
			mapTR12.put("GROSS_VALUE", mapTR12.get("GROSS_VALUE").add(GROSS_VALUE));
			
			BigDecimal GROSS_BF_VALUE = oldTr12.get().getTrxGrossBf() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxGrossBf();
			mapTR12.put("GROSS_BF_VALUE", mapTR12.get("GROSS_BF_VALUE").add(GROSS_BF_VALUE));
			
			BigDecimal CURR_RATE_VALUE = oldTr12.get().getTrxCurrRate() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxCurrRate();
			mapTR12.put("CURR_RATE_VALUE", mapTR12.get("CURR_RATE_VALUE").add(CURR_RATE_VALUE));
			
			BigDecimal COMM_OUT_VALUE = oldTr12.get().getTrxComoAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxComoAmount();
			mapTR12.put("COMM_OUT_VALUE", mapTR12.get("COMM_OUT_VALUE").add(COMM_OUT_VALUE));
			
			BigDecimal OTHERS1_VALUE = oldTr12.get().getTrxOthers1Amount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxOthers1Amount();
			mapTR12.put("OTHERS1_VALUE", mapTR12.get("OTHERS1_VALUE").add(OTHERS1_VALUE));
			
			BigDecimal OTHERS2_VALUE = oldTr12.get().getTrxOthers2Amount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxOthers2Amount();
			mapTR12.put("OTHERS2_VALUE", mapTR12.get("OTHERS2_VALUE").add(OTHERS2_VALUE));
			
			BigDecimal OTHERS3_VALUE = oldTr12.get().getTrxOthers3Amount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxOthers3Amount();
			mapTR12.put("OTHERS3_VALUE", mapTR12.get("OTHERS3_VALUE").add(OTHERS3_VALUE));
			
			BigDecimal OTHERS4_VALUE = oldTr12.get().getTrxOthers4Amount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxOthers4Amount();
			mapTR12.put("OTHERS4_VALUE", mapTR12.get("OTHERS4_VALUE").add(OTHERS4_VALUE));
			
			BigDecimal NET_TOU_VALUE = oldTr12.get().getTrxNetTou() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxNetTou();
			mapTR12.put("NET_TOU_VALUE", mapTR12.get("NET_TOU_VALUE").add(NET_TOU_VALUE));
			
			BigDecimal NET_TTL_VALUE = oldTr12.get().getTrxNetTtl() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxNetTtl();
			mapTR12.put("NET_TTL_VALUE", mapTR12.get("NET_TTL_VALUE").add(NET_TTL_VALUE));
			
			BigDecimal COST_POLICY_VALUE = oldTr12.get().getTrxPolAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxPolAmount();
			mapTR12.put("COST_POLICY_VALUE", mapTR12.get("COST_POLICY_VALUE").add(COST_POLICY_VALUE));
			
			BigDecimal STAMP_DUTY_VALUE = oldTr12.get().getTrxSdutyAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxSdutyAmount();
			mapTR12.put("STAMP_DUTY_VALUE", mapTR12.get("STAMP_DUTY_VALUE").add(STAMP_DUTY_VALUE));
			
			BigDecimal OTHERS_VALUE = oldTr12.get().getTrxOthersAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxOthersAmount();
			mapTR12.put("OTHERS_VALUE", mapTR12.get("OTHERS_VALUE").add(OTHERS_VALUE));
			
			BigDecimal ADMIN_FEE_VALUE = oldTr12.get().getTrxAdminAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxAdminAmount();
			mapTR12.put("ADMIN_FEE_VALUE", mapTR12.get("ADMIN_FEE_VALUE").add(ADMIN_FEE_VALUE));
			
			BigDecimal BANK_FEE_VALUE = oldTr12.get().getTrxBankAmount() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxBankAmount();
			mapTR12.put("BANK_FEE_VALUE", mapTR12.get("BANK_FEE_VALUE").add(BANK_FEE_VALUE));
			
			BigDecimal NET_INC_VALUE = oldTr12.get().getTrxIncOthers() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxIncOthers();
			mapTR12.put("NET_INC_VALUE", mapTR12.get("NET_INC_VALUE").add(NET_INC_VALUE));
			
			BigDecimal TAXIN_OTH_VALUE = oldTr12.get().getTrxTaxinOth() == null 
					? BigDecimal.ZERO : oldTr12.get().getTrxTaxinOth();
			mapTR12.put("TAXIN_OTH_VALUE", mapTR12.get("TAXIN_OTH_VALUE").add(TAXIN_OTH_VALUE));
			
//			BigDecimal TAX_OUT_VALUE = oldTr12.get().getTrxWithAmount() == null 
//					? BigDecimal.ZERO : oldTr12.get().getTrxWithAmount();
//			mapTR12.put("TAX_OUT_VALUE", mapTR12.get("TAX_OUT_VALUE").add(TAX_OUT_VALUE));
		}
		
		return mapTR12;
	}

}
