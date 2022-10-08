package com.biru.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.entity.ResponseEntity;
import com.biru.entity.TR0012Entity;
import com.biru.repository.TR0006ARepo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewInqDirectModifyRepo;
import com.biru.service.DirectModifyService;
import com.biru.specifications.ViewInqDirectModifySpesifications;
import com.biru.view.ViewInqDirectModify;

@Service
public class DirectModifyServiceImpl implements DirectModifyService {

	@Autowired
	private ViewInqDirectModifyRepo viewInqDirectModifyRepo;
	
	@Autowired
	private TR0006ARepo tr0006ARepo;
	
	@Autowired
	private TR0012Repo tr0012Repo;
	
	@Override
	public Object inquiry(Map<String, Object> params) {
		Integer limit = Param.getInt(params, PARAM.LIMIT);
		Integer offset = Param.getInt(params, PARAM.OFFSET);
		String order = Param.getStr(params, PARAM.ORDER);
		String sort = Param.getStr(params, PARAM.SORT);
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<ViewInqDirectModify> data = viewInqDirectModifyRepo.findAll(
				ViewInqDirectModifySpesifications.inquiry(params), pageable);
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object inquiryDetail(Map<String, Object> params) {
		String trxVoucherId = Param.getStr(params, "trxVoucherId");
		String trxTrxId = Param.getStr(params, "trxTrxId");
		
		List<TR0012Entity> tr12List = tr0012Repo.findDetailDirectModify(trxTrxId, trxVoucherId);
		
		return tr12List;
	}

	@Override
	@SuppressWarnings("unchecked")
	public Object save(Map<String, Object> params) {
		String trxVoucherId = Param.getStr(params, "trxVoucherId");
		String policyNo = Param.getStr(params, "policyNo");
		List<Map<String, Object>> trxModifyList = (List<Map<String, Object>>) params.get("trxModifyList");
		
		List<TR0012Entity> update = new ArrayList<TR0012Entity>();
		for(Map<String, Object> modify : trxModifyList) {
			TR0012Entity tr12 = tr0012Repo.findByIdKey(Param.getLong(modify, "idKey"));	
			tr12.setTrxInvcAmount(Param.getBd(modify, "trxInvcAmountNewStr"));
			tr12.setTrxOrgAmount(Param.getBd(modify, "trxInvcAmountNewStr"));
			tr12.setTrxDueDate(Param.getDate(modify, "trxDueDateNewStr"));
			
			update.add(tr12);
		}
		
		tr0012Repo.saveAll(update);
		tr0006ARepo.updateTrxRemarks(policyNo, "RQ", trxVoucherId);
		
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		
		return response;
	}

}
