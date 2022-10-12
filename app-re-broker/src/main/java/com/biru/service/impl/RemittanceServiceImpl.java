package com.biru.service.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.CODE;
import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.ReportUtils;
import com.biru.entity.MA0014Entity;
import com.biru.helper.SettlementHelper;
import com.biru.repository.MA0014Repo;
import com.biru.repository.TR0012Repo;
import com.biru.service.RemittanceService;

@Service
public class RemittanceServiceImpl extends AbstractCommon implements RemittanceService {

	@Autowired
	private MA0014Repo ma0014Repo;

	@Autowired
	private TR0012Repo tr0012Repo;

	@Autowired
	private ReportUtils reportUtils;

	@Override
	public DatatableSet inquiry(Map<String, Object> param) {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String filterValue = Param.getStr(param, PARAM.FILTER_VALUE);
		String type = Param.getStr(param, "type");

		List<String> types;
		if (type.equals("RC"))
			types = Arrays.asList("SE", "SO");
		else
			types = Arrays.asList("PU", "PO");

		Pageable pageable = PageRequest.of(offset / limit, limit, Sort.Direction.fromString(order), sort);

		MA0014Entity paramDate = ma0014Repo.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);
		Page<SettlementHelper> data = tr0012Repo.findSettlement(paramDate.getPaChildValue(), types,
				filterValue.toLowerCase(), pageable);

		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object printJournal(Map<String, Object> param) throws Exception {
		return reportUtils.exportHtml("EntryJournal.jrxml", param);
	}

	@Override
	public Object exportExcel(Map<String, Object> param) throws Exception {
		List<String> voucherParam = Param.getListFromString(param, "voucherParam");
		String remarksParam = Param.getStr(param, "remarksParam");
		String clientParam = Param.getStr(param, "clientParam");
		
		Map<String, Object> paramRemittance = new HashMap<String, Object>();
		paramRemittance.put("P_VOUCHER_ID", voucherParam);
		paramRemittance.put("P_REMARKS", remarksParam);
		paramRemittance.put("P_CLIENT_CODE", clientParam);
		return reportUtils.exportExcel("Remittance.jrxml", paramRemittance);
	}

	@Override
	public Object print(Map<String, Object> param) throws Exception {
		List<String> voucherParam = Param.getListFromString(param, "voucherParam");
		String remarksParam = Param.getStr(param, "remarksParam");
		String clientParam = Param.getStr(param, "clientParam");
		
		Map<String, Object> paramRemittance = new HashMap<String, Object>();
		paramRemittance.put("P_VOUCHER_ID", voucherParam);
		paramRemittance.put("P_REMARKS", remarksParam);
		paramRemittance.put("P_CLIENT_CODE", clientParam);
		return reportUtils.exportPdf("Remittance.jrxml", paramRemittance);
	}
}
