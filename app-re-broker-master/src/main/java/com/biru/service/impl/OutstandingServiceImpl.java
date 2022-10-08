package com.biru.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.OutstandingComponent;
import com.biru.component.ReportUtils;
import com.biru.entity.MA0005Entity;
import com.biru.entity.OutstdRptEntity;
import com.biru.entity.TR0012Entity;
import com.biru.helper.OutstandingHelper;
import com.biru.repository.MA0005Repo;
import com.biru.repository.OutstdRptRepo;
import com.biru.repository.TR0012Repo;
import com.biru.service.OutstandingService;
import com.biru.specifications.TR0012Specifications;

@Service
public class OutstandingServiceImpl extends AbstractCommon implements OutstandingService {

	@Autowired
	private TR0012Repo tR0012Repo;

	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private OutstandingComponent outstandingComponent;

	@Override
	public DatatableSet inquiry(Map<String, Object> param) throws ParseException {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String typeOfCover = Param.getStr(param, "typeOfCover");
		Date transactionDate = formatDateId.parse(Param.getStr(param, "transactionDate"));
		// Date toTransactionDate = formatDateId.parse(Param.getStr(param, "to"));
		String client = Param.getStrWithDef(param, "client");
		String type = Param.getStr(param, "typeOfTransaction");

		if(typeOfCover.equals("ALL")){
			typeOfCover = "";
		}
		if(client.equals("ALL")){
			client = "";
		}
		List<String> types;
		if (type.equals("all"))
			types = Arrays.asList("SE", "SO", "PU", "PO");
		else if (type.equals("RC"))
			types = Arrays.asList("SE", "SO");
		else
			types = Arrays.asList("PU", "PO");

		Pageable pageable = PageRequest.of(offset / limit, limit, Sort.Direction.fromString(order), sort);

		Page<OutstandingHelper> data = tR0012Repo.findOutstanding(typeOfCover, client, types, transactionDate, pageable);

		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object exportExcel(Map<String, Object> param) throws Exception {
		List<String> types;
		if (Param.getStr(param, "typeParam").equals("RC"))
			types = Arrays.asList("SE", "SO");
		else
			types = Arrays.asList("PU", "PO");
		List<String> typeParam = types;
		String clientParam = Param.getStr(param, "clientParam");
		String coverParam = Param.getStr(param, "coverParam");
		String minDateParam = Param.getStr(param, "minDateParam");
		String maxDateParam = Param.getStr(param, "maxDateParam");
		
		if(coverParam.equals("ALL")){
			coverParam = "";
		}

		if(clientParam.equals("ALL")){
			clientParam = "";
		}
		Map<String, Object> paramOutstanding = new HashMap<String, Object>();
		paramOutstanding.put("P_CLIENT", clientParam);
		paramOutstanding.put("P_TYPE", typeParam);
		paramOutstanding.put("P_COVER", coverParam);
		paramOutstanding.put("P_MIN_DATE", minDateParam);
		paramOutstanding.put("P_MAX_DATE", maxDateParam);
		return reportUtils.exportExcel("Outstanding.jrxml", paramOutstanding);
	}

	@Override
	public Object print(Map<String, Object> param) throws Exception {
		List<String> types;
		if (Param.getStr(param, "typeParam").equals("RC"))
			types = Arrays.asList("SE", "SO");
		else
			types = Arrays.asList("PU", "PO");
		List<String> typeParam = types;
		String clientParam = Param.getStr(param, "clientParam");
		String coverParam = Param.getStr(param, "coverParam");
		String minDateParam = Param.getStr(param, "minDateParam");
		String maxDateParam = Param.getStr(param, "maxDateParam");
		
		if(coverParam.equals("ALL")){
			coverParam = "";
		}

		if(clientParam.equals("ALL")){
			clientParam = "";
		}
		Map<String, Object> paramOutstanding = new HashMap<String, Object>();
		paramOutstanding.put("P_CLIENT", clientParam);
		paramOutstanding.put("P_TYPE", typeParam);
		paramOutstanding.put("P_COVER", coverParam);
		paramOutstanding.put("P_MIN_DATE", minDateParam);
		paramOutstanding.put("P_MAX_DATE", maxDateParam);
		return reportUtils.exportPdf("Outstanding.jrxml", paramOutstanding);
	}

	@Override
	public Object exportExcelV2(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		outstandingComponent.setTableCC(param);		
		return outstandingComponent.createReport(param, "xlsx");
	}

	@Override
	public Object printV2(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		outstandingComponent.setTableCC(param);
		return outstandingComponent.createReport(param, "pdf");
	}
}
