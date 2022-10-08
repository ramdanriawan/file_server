package com.biru.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.CLOSING_PARAM;
import com.biru.common.param.Param;
import com.biru.component.NewEndoresementComponent;
import com.biru.component.ResultComponent;
import com.biru.component.VoucherComponent;
import com.biru.entity.TR0006AEntity;
import com.biru.entity.TR0006BEntity;
import com.biru.entity.TR0006CEntity;
import com.biru.entity.TR0006EEntity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0006GEntity;
import com.biru.repository.TR0006ARepo;
import com.biru.repository.TR0006BRepo;
import com.biru.repository.TR0006CRepo;
import com.biru.repository.TR0006ERepo;
import com.biru.repository.TR0006GRepo;
import com.biru.repository.TR0006Repo;
import com.biru.service.CommonService;
import com.biru.service.NewEndorsementService;

@Service
public class NewEndorsementServiceImpl implements NewEndorsementService {

	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private NewEndoresementComponent newEndoresementComponent;
	
	@Autowired
	private ResultComponent resultComponent;
	
	@Autowired
	private TR0006Repo tR0006Repo;

	@Autowired
	private TR0006ARepo tR0006ARepo;

	@Autowired
	private TR0006BRepo tR0006BRepo;

	@Autowired
	private TR0006CRepo tR0006CRepo;

	@Autowired
	private TR0006ERepo tR0006ERepo;

	@Autowired
	private TR0006GRepo tR0006GRepo;
	
	private static final String RQ 				= "RQ";
	private static final String TRX_VOUCHER_ID 	= "trxVoucherId";
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public Object getRequestId() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		HashMap<String, Object> result = new HashMap<String, Object>();
		result.put("requestId", voucherComponent.saveVoucherCounter(sdf.format(commonService.getAppDate())));
		
		return  result;
	}

	@Override
	public Object closing(Map<String, Object> param) {
		Long start = System.currentTimeMillis();
		String trxVoucherId = Param.getStr(param, TRX_VOUCHER_ID);		
		logger.info("Start - closing Endorsement with trxVoucherId : '{}'.", trxVoucherId);	
		
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
		
		try {
			Object result = newEndoresementComponent.closing(param);
			
			logger.info("End - closing Endorsement with trxVoucherId : '{}', elapsed time : {}ms.", trxVoucherId, System.currentTimeMillis()-start);
			return result;
		}catch(Exception e) {
			e.printStackTrace();
			return resultComponent.createResponse(e);
		}
	}

}
