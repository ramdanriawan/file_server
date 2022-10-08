package com.biru.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.entity.TR0006HEntity;
import com.biru.repository.TR0006HRepo;
import com.biru.service.ClaimInternalService;

@Service
public class ClaimInternalServiceImpl implements ClaimInternalService {

	@Autowired
	private TR0006HRepo tr6hRepo;
	
	@Override
	public Object claimsInquiry(Map<String, Object>param) {
		return null;
	}
	
	@Override
	public Object claimsInquiryDetail(Map<String, Object>param) {
		System.out.println(param);
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String trxTrxId = Param.getStr(param, "trxTrxId");
		String trxVoucherId = Param.getStr(param, "trxVoucherId");
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<TR0006HEntity> result = null;
		result = tr6hRepo.getDataClaim(trxTrxId, trxVoucherId, pageable);

		return new DatatableSet(result.getTotalElements(), result.getTotalElements(), result.getContent());
	}
	
}
