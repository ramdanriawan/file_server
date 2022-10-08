package com.biru.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0004Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0004Repo;
import com.biru.service.InquiryJournal;
import com.biru.specifications.TR0001Specifications;

@Service
public class InquiryJournalImpl implements InquiryJournal{

	@Autowired
	private TR0001Repo tR0001Repo;

	@Autowired
	private TR0004Repo tR0004Repo;

	@Autowired
	private MA0014Repo mA0014Repo;

	@Autowired
	private MA0005Repo mA0005Repo;

	@Autowired
	private TR0001Specifications tR0001Specifications;

	@Override
	public Object getDropdownProject() {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "proCode");
		List<TR0004Entity> listTR0004Entity = tR0004Repo.findAll(pageable).getContent();
		List<DropdownIdText> listDropdownIdText = new ArrayList<DropdownIdText>();
		DropdownIdText dropdownIdTextAll = new DropdownIdText("all", "All");
		listDropdownIdText.add(dropdownIdTextAll);
		for (TR0004Entity tr0004Entity : listTR0004Entity) {
			DropdownIdText dropdownIdText = new DropdownIdText();
			dropdownIdText.setId(tr0004Entity.getProCode());
			dropdownIdText.setText(tr0004Entity.getProDesc());
			listDropdownIdText.add(dropdownIdText);
		}
		Map<String, List<DropdownIdText>> results = new HashMap<>();
		results.put("results", listDropdownIdText);
		return results;
	}

	@Override
	public Object getDropdownOffice() {
		// TODO Auto-generated method stub
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "paChildValue");
		List<MA0014Entity> listMA0014Entity = mA0014Repo.findByPaParentCodeAndPaChildStatus("OFFCODE", "11", pageable).getContent();
		List<DropdownIdText> listDropdownIdText = new ArrayList<DropdownIdText>();
		DropdownIdText dropdownIdTextAll = new DropdownIdText("all", "All");
		listDropdownIdText.add(dropdownIdTextAll);
		for (MA0014Entity mA0014Entity : listMA0014Entity) {
			DropdownIdText dropdownIdText = new DropdownIdText();
			dropdownIdText.setId(mA0014Entity.getPaChildValue());
			dropdownIdText.setText(mA0014Entity.getPaChildDesc());
			listDropdownIdText.add(dropdownIdText);
		}
		Map<String, List<DropdownIdText>> results = new HashMap<>();
		results.put("results", listDropdownIdText);
		return results;
	}

	@Override
	public Object getJournal(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String activity = Param.getStr(param, "activity");
		String type = Param.getStr(param, "type");
		String project = Param.getStr(param, "project");
		String office = Param.getStr(param, "office");
		String transaction = Param.getStr(param, "transactionDate");
		Date transactionDate = null;
		if(!transaction.equals("")) {
			String [] date = transaction.split("/");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
			c.set(Calendar.MONTH, (Integer.parseInt(date[1])-1));
			c.set(Calendar.YEAR, Integer.parseInt(date[2]));
			c.set(Calendar.HOUR_OF_DAY, 0);
			c.set(Calendar.MINUTE, 0);
			c.set(Calendar.SECOND, 0);
			transactionDate = c.getTime();
		}

		String to = Param.getStr(param, "to");
		Date toDate = null;
		if(!to.equals("")) {
			String [] date = to.split("/");
			Calendar c = Calendar.getInstance();
			c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
			c.set(Calendar.MONTH, (Integer.parseInt(date[1])-1));
			c.set(Calendar.YEAR, Integer.parseInt(date[2]));
			c.set(Calendar.HOUR_OF_DAY, 23);
			c.set(Calendar.MINUTE, 59);
			c.set(Calendar.SECOND, 59);
			toDate = c.getTime();
		}

		if(sort.equals("glTrxDateStr")) {
			sort = "glTrxDate";
		}

		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Specification<TR0001Entity> specificationTR0001Entity= tR0001Specifications.glDataStatusEqual("11");
		if(!activity.equals("all")) {
			specificationTR0001Entity = specificationTR0001Entity
					.and(tR0001Specifications.glTrxClassEqual(activity));
		}
		if(!type.equals("all")) {
			specificationTR0001Entity = specificationTR0001Entity
					.and(tR0001Specifications.glTypeEqual(type));
		}
		if(!project.equals("all")) {
			specificationTR0001Entity = specificationTR0001Entity
					.and(tR0001Specifications.glTrxProjectEqual(project));
		}
		if(!office.equals("all")) {
			specificationTR0001Entity = specificationTR0001Entity
					.and(tR0001Specifications.glTrxOfficeIdEqual(office));
		}

		if(transactionDate != null && toDate!=null) {
			specificationTR0001Entity = specificationTR0001Entity
					.and(tR0001Specifications.glTrxDateGreaterThanOrEqualTo(transactionDate))
					.and(tR0001Specifications.glTrxDatelessThanOrEqualTo(toDate));
		}else if(transactionDate != null && toDate==null) {
			specificationTR0001Entity = specificationTR0001Entity
					.and(tR0001Specifications.glTrxDateGreaterThanOrEqualTo(transactionDate));
		}else if(transactionDate == null && toDate!=null) {
			specificationTR0001Entity = specificationTR0001Entity
					.and(tR0001Specifications.glTrxDatelessThanOrEqualTo(toDate));
		}

		Page<TR0001Entity>pageTR0001Entity = tR0001Repo.findAll(specificationTR0001Entity, pageable);

		for (TR0001Entity tr0001Entity : pageTR0001Entity.getContent()) {
			MA0005Entity mA0005Entity = mA0005Repo.findByCliCode(tr0001Entity.getGlTrxClient());
			if(mA0005Entity != null) {
				tr0001Entity.setGlTrxClientDesc(mA0005Entity.getCliName());
			}
		}

		return new DatatableSet(pageTR0001Entity.getTotalElements(), pageTR0001Entity.getTotalElements(), pageTR0001Entity.getContent());
	}



}
