package com.biru.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.component.ReportUtils;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0010Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0015Entity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0004Entity;
import com.biru.entity.TR0005Entity;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0010Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0015Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0004Repo;
import com.biru.repository.TR0005Repo;
import com.biru.service.CommonService;
import com.biru.service.EntryJournalService;

@Service
public class EntryJournalServiceImpl implements EntryJournalService{

	@Autowired
	private TR0004Repo tR0004Repo;
	
	@Autowired
	private MA0014Repo mA0014Repo;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private TR0005Repo tR0005Repo;
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private MA0004Repo mA0004Repo;
	
	@Autowired
	private TR0001Repo tR0001Repo;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private MA0015Repo mA0015Repo;
	
	@Autowired
	private TR0002Repo tR0002Repo;
	
	@Autowired
	private MA0010Repo mA0010Repo;
	
	@Autowired
	private ReportUtils reportUtils;

	private static final String RC = "RC";
	private static final String TYPE_CREDIT = "C";
	private static final String TYPE_DEBIT = "D";
	private static final String CURRENCY = "#,##0.00;(#,##0.00)";
	
	private static DecimalFormat decimalFormat;
	
	public EntryJournalServiceImpl() {
		decimalFormat = (DecimalFormat) DecimalFormat.getInstance();
		decimalFormat.applyPattern(CURRENCY);
	}
	
	@Override
	public Object inquiry(Map<String, Object> param) {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String filterKey = Param.getStr(param, PARAM.FILTER_KEY);
		String filterValue = Param.getStr(param, PARAM.FILTER_VALUE);
		
		if(sort.equals("glTrxDateStr")) {
			sort = "glTrxDate";
		}else if(sort.equals("glTrxValueIdrStr")) {
			sort = "glTrxValueIdr";
		}
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<TR0001Entity> data = null;
		if (filterKey != null) {
			if(filterKey.equals(PARAM.KEY.GL_TYPE)) {
				data = tR0001Repo.findByGlTrxStatusAndGlDataStatusAndGlTypeContains("0", "11", filterValue, pageable);
			}else {
				data = tR0001Repo.findByGlVoucherIdContains(filterValue, pageable);
			}
		}else {
			data = tR0001Repo.findByGlTrxStatusAndGlDataStatus("0", "11", pageable);
		}
		
		for (TR0001Entity tr0001Entity : data.getContent()) {
			String action = "<button class=\"btn btn-secondary\" onclick=\"edit('"+tr0001Entity.getGlVoucherId()+"')\">" 
					+ "<i class=\"fa fa-edit\"></i>" 
					+ "	</button> &nbsp;"
					+ "<button class=\"btn btn-danger\" onclick=\"remove('"+tr0001Entity.getGlVoucherId()+"')\">" 
					+ "<i class=\"fa fa-trash\"></i>" 
					+ "</button>";
			tr0001Entity.setAction(action);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());

	}
	
	@Override
	public Object getDropdownProject() {
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "proCode");
		List<TR0004Entity> listTR0004Entity = tR0004Repo.findAll(pageable).getContent();
		List<DropdownIdText> listDropdownIdText = new ArrayList<DropdownIdText>();
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
		Pageable pageable = PageRequest.of(0, 100, Sort.Direction.ASC, "paChildValue");
		List<MA0014Entity> listMA0014Entity = mA0014Repo.findByPaParentCodeAndPaChildStatus("OFFCODE", "11", pageable).getContent();
		List<DropdownIdText> listDropdownIdText = new ArrayList<DropdownIdText>();
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

	
	
	@SuppressWarnings("deprecation")
	@Override
	public String getTransactionMinDate() throws ParseException {
		List<TR0005Entity> listTR0005Entity = tR0005Repo.findLastProMonthAndProYear();
		if(!listTR0005Entity.isEmpty()) {
			TR0005Entity tR0005Entity = listTR0005Entity.get(0);
			Date appDate = commonService.getAppDate();
			if(tR0005Entity.getProYear() >= (appDate.getYear()+1900) && 
					tR0005Entity.getProMonth() >= (appDate.getMonth()+1)) {
				return appDate.getDate() + "-" + (appDate.getMonth()+1) + "-" + (appDate.getYear()+1900);
			}else {
				Calendar c = Calendar.getInstance();
				c.set(Calendar.YEAR, tR0005Entity.getProYear());
				c.set(Calendar.MONTH, tR0005Entity.getProMonth()-1);
				c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
				c.setTimeInMillis(c.getTimeInMillis() + 1000*60*60*24);
				return c.get(Calendar.DAY_OF_MONTH) + "-" + (c.get(Calendar.MONTH)+1) + "-" + c.get(Calendar.YEAR);
			}
		}
		return null;
	}

	@Override
	public Object client(Map<String, Object> param) {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String filterKey = Param.getStr(param, PARAM.FILTER_KEY);
		String filterValue = Param.getStr(param, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0005Entity> data = null;
		if (filterKey != null) {
			if(filterKey.equals(PARAM.KEY.CLI_NAME)) {
				data = mA0005Repo.findBycliNameContainsAndCliDataStatusEquals(filterValue, "0", pageable);
			}else {
				data = mA0005Repo.findBycliCodeContainsAndCliDataStatusEquals(filterValue, "0", pageable);
			}
		}else { 
			data = mA0005Repo.findBycliDataStatus("0", pageable);
		}
		
		for (MA0005Entity ma0005Entity : data.getContent()) {
			ma0005Entity.setCliDataStatusStr("Active");
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object coa(Map<String, Object> param) {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String filterKey = Param.getStr(param, PARAM.FILTER_KEY);
		String filterValue = Param.getStr(param, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0004Entity> data = null;
		if (filterKey != null) {
			if(filterKey.equals(PARAM.KEY.COA_CODE)) {
				data = mA0004Repo.findByCoaHeaderAndCoaDataStatusAndCoaCodeContains('D', "11", filterValue, pageable);
			}else {
				data = mA0004Repo.findByCoaHeaderAndCoaDataStatusAndCoaDescriptContains('D', "11", filterValue, pageable);
			}
		}else {
			data = mA0004Repo.findByCoaHeaderAndCoaDataStatus('D', "11", pageable);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	@SuppressWarnings("deprecation")
	public Object voucherCode(Map<String, Object> param) {
		String date = param.get("date").toString().replace("/", "-");
		return voucherComponent.getVoucherId(date);
	}

	@Override
	public Object exchange(Map<String, Object> param) {
		String [] date = param.get("date").toString().split("/");
		String currId = param.get("curr").toString();
		
		MA0015Entity mA0015Entity = mA0015Repo.findByExMonthAndExYearAndExCurrId(Integer.valueOf(date[1]), Integer.valueOf(date[2]), currId);
		if(mA0015Entity == null) {
			return NumberFormat.getCurrencyInstance().format(BigDecimal.ZERO).replace("$", "");
		}
		return mA0015Entity.getExRateValueStr();
	}

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public Object save(Map<String, Object> param) {
		Calendar c = Calendar.getInstance();
		
		String activity = param.get("activity").toString();
		String type = param.get("type").toString();
		String voucher = param.get("voucher").toString();
		
		String [] transaction = param.get("transaction").toString().split("/");
		c.set(Calendar.YEAR, Integer.parseInt(transaction[2]));
		c.set(Calendar.MONTH, (Integer.parseInt(transaction[1])-1));
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(transaction[0]));
		Date transactionDate = c.getTime();
		
		String project = param.get("project").toString();
		String office = param.get("office").toString();
		String description = param.get("description").toString() == "" ? null : param.get("description").toString();
		String remark = param.get("remark").toString() == "" ? null : param.get("remark").toString();
		String client = param.get("client").toString()== "" ? null : param.get("client").toString();
		
		String [] dueDate = param.get("dueDate").toString().split("/");
		c.set(Calendar.YEAR, Integer.parseInt(dueDate[2]));
		c.set(Calendar.MONTH, (Integer.parseInt(dueDate[1])-1));
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dueDate[0]));
		Date dueDateDate = c.getTime();
		
		ArrayList<HashMap<String, Object>> rows= (ArrayList<HashMap<String, Object>>) param.get("rows");
		BigDecimal totalDebit = BigDecimal.ZERO;
		BigDecimal totalDebitIdr = BigDecimal.ZERO;
		for (HashMap<String, Object> rowsData : rows) {
			totalDebit = totalDebit.add(new BigDecimal(rowsData.get("debit").toString().replace(",", "")));
			totalDebitIdr = totalDebitIdr.add(new BigDecimal(rowsData.get("debitIdr").toString().replace(",", "")));
		}
		
		TR0001Entity tR0001Entity = tR0001Repo.findByGlVoucherId(voucher);
		List<TR0002Entity> listTR0002Entity = tR0002Repo.findByGlVoucherId(voucher);
		if(tR0001Entity == null) {
			tR0001Entity = new TR0001Entity();
//			tR0001Entity.setIdKey("0");
			tR0001Entity.setCreateOn(new Date((Long) param.get(Param.CREATE_ON)));
			tR0001Entity.setCreateBy(param.get(Param.CREATE_BY).toString());
			
			tR0001Entity.setModifyOn(new Date((Long) param.get(Param.MODIFY_ON)));
			tR0001Entity.setModifyBy(param.get(Param.MODIFY_BY).toString());
		}else {
			tR0001Entity.setModifyOn(new Date((Long) param.get(Param.MODIFY_ON)));
			tR0001Entity.setModifyBy(param.get(Param.MODIFY_BY).toString());
		}
		
		tR0001Entity.setGlTrxClass(activity);
		tR0001Entity.setGlType(type);
		tR0001Entity.setGlVoucherId(voucher);
		tR0001Entity.setGlTrxDate(transactionDate);
		tR0001Entity.setGlTrxMonth(Byte.parseByte(transaction[1]));
		tR0001Entity.setGlTrxYear(Short.parseShort(transaction[2]));
		tR0001Entity.setGlTrxOfficeId(office);
		tR0001Entity.setGlTrxProject(project);
		tR0001Entity.setGlTrxClient(client);
		tR0001Entity.setGlTrxDesc(description);
		tR0001Entity.setGlTrxInvoice(remark);
		tR0001Entity.setGlTrxValueOrg(totalDebit);
		tR0001Entity.setGlTrxValueIdr(totalDebitIdr);
		tR0001Entity.setGlTrxStatus("0");
		tR0001Entity.setGlDataStatus("11");
		
		tR0001Entity.setGlTrxDue(dueDateDate);
		
		tR0001Repo.save(tR0001Entity);
		
		if(!listTR0002Entity.isEmpty()) {
			tR0002Repo.deleteAll(listTR0002Entity);
		}
		listTR0002Entity.clear();
//		int id = 0;
		for (HashMap<String, Object> rowsData : rows) {
			TR0002Entity tR0002Entity = new TR0002Entity();
//			tR0002Entity.setIdKey(String.valueOf(id));
			tR0002Entity.setGlType(type);
			tR0002Entity.setGlVoucherId(voucher);
			tR0002Entity.setGlAccount(rowsData.get("coa").toString());
			tR0002Entity.setGlCurrId(rowsData.get("curr").toString());
			tR0002Entity.setGlOrgDebit(new BigDecimal(rowsData.get("debit").toString().replace(",", "")));
			tR0002Entity.setGlOrgCredit(new BigDecimal(rowsData.get("credit").toString().replace(",", "")));
			tR0002Entity.setGlIdrDebit(new BigDecimal(rowsData.get("debitIdr").toString().replace(",", "")));
			tR0002Entity.setGlIdrCredit(new BigDecimal(rowsData.get("creditIdr").toString().replace(",", "")));
			tR0002Entity.setGlTrxClass(activity);
			tR0002Entity.setGlCurrRate(new BigDecimal(rowsData.get("exchange").toString().replace(",", "")));
			listTR0002Entity.add(tR0002Entity);
//			id++;
		}
		tR0002Repo.saveAll(listTR0002Entity);
		
		voucherComponent.saveVoucherCounter(transaction[0]+"-"+transaction[1]+"-"+transaction[2]);
		
		return param;
	}

	@Override
	public Object edit(Map<String, Object> param) {
		String voucherId = param.get("voucherId").toString();
		TR0001Entity tR0001Entity = tR0001Repo.findByGlVoucherId(voucherId);
		String glTrxClientDesc = "";
		if(tR0001Entity.getGlTrxClient() != null) {
			MA0005Entity mA0005Entity=  mA0005Repo.findByCliCode(tR0001Entity.getGlTrxClient()) ;
			glTrxClientDesc = mA0005Entity == null ? "" : mA0005Entity.getCliName();
		}
		
		tR0001Entity.setGlTrxClientDesc(glTrxClientDesc);
		List<TR0002Entity> listTR0002Entity = tR0002Repo.findByGlVoucherId(voucherId);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("tR0001Entity", tR0001Entity);
		result.put("listTR0002Entity", listTR0002Entity);
		return result;
	}

	@Transactional
	@Override
	public Object remove(Map<String, Object> param) {
		String voucher = param.get("voucherId").toString();
		TR0001Entity tR0001Entity = tR0001Repo.findByGlVoucherId(voucher);
		tR0001Repo.delete(tR0001Entity);
		
		List<TR0002Entity> listTR0002Entity = tR0002Repo.findByGlVoucherId(voucher);
		tR0002Repo.deleteAll(listTR0002Entity);
		return param;
	}

	@Override
	public Object printJournal(Map<String, Object> param) throws Exception {
		Optional<MA0010Entity> company = mA0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		param.put("companyName", companyName);

		String version = Param.getStr(param, "version");
		if("V2".equals(version)) {
			String voucherId = Param.getStrWithDef(param, "voucher");
			TR0001Entity tr1 = tR0001Repo.findByGlVoucherId(voucherId);
			
			if(tr1!=null) {
				TR0002Entity bank = tR0002Repo.getBank(voucherId);
				String bankAccount = bank.getGlAccountDesc();	
				
				String type; //type of transaction			
				if(RC.equals(tr1.getGlType()))
					type = TYPE_CREDIT;
				else
					type = TYPE_DEBIT;
				
				BigDecimal totalOrg = BigDecimal.ZERO;
				if(type.equals(TYPE_CREDIT))
					totalOrg = bank.getGlOrgDebit().subtract(bank.getGlOrgCredit());
				else
					totalOrg = bank.getGlOrgCredit().subtract(bank.getGlOrgDebit());
				
				String currId = tR0002Repo.getBankCurrId(voucherId);
				param.put("P_VOUCHER_ID", voucherId);
				param.put("P_TOTAL_AMOUNT", currId + " " + decimalFormat.format(totalOrg).replace("$", ""));
				param.put("P_BANK_ACCOUNT", bankAccount);
				param.put("P_TYPE", type);
			}
			
			return reportUtils.exportHtml("EntryJournalV2.jrxml", param);
		}else {
			return reportUtils.exportHtml("EntryJournal.jrxml", param);
		}
	}

	@Override
	public Object exportExcel(Map<String, Object> param) throws Exception {
		Optional<MA0010Entity> company = mA0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		param.put("companyName", companyName);
		
		String version = Param.getStr(param, "version");
		if("V2".equals(version)) {
			String voucherId = Param.getStrWithDef(param, "voucher");
			TR0001Entity tr1 = tR0001Repo.findByGlVoucherId(voucherId);
			
			if(tr1!=null) {
				TR0002Entity bank = tR0002Repo.getBank(voucherId);
				String bankAccount = bank.getGlAccountDesc();	
				
				String type; //type of transaction			
				if(RC.equals(tr1.getGlType()))
					type = TYPE_CREDIT;
				else
					type = TYPE_DEBIT;
				
				BigDecimal totalOrg = BigDecimal.ZERO;
				if(type.equals(TYPE_CREDIT) && bank.getGlOrgDebit().compareTo(BigDecimal.ZERO) > 0) {
					totalOrg = bank.getGlOrgDebit();
				}else if(type.equals(TYPE_CREDIT) && bank.getGlOrgDebit().compareTo(BigDecimal.ZERO) <= 0) {
					totalOrg = bank.getGlOrgCredit().negate();
				}else if(type.equals(TYPE_DEBIT) && bank.getGlOrgCredit().compareTo(BigDecimal.ZERO) > 0) {
					totalOrg = bank.getGlOrgCredit();
				}else if(type.equals(TYPE_DEBIT) && bank.getGlOrgCredit().compareTo(BigDecimal.ZERO) <= 0) {
					totalOrg = bank.getGlOrgDebit().negate();
				}
				
				String currId = tR0002Repo.getBankCurrId(voucherId);
				param.put("P_VOUCHER_ID", voucherId);
				param.put("P_TOTAL_AMOUNT", currId + " " + decimalFormat.format(totalOrg).replace("$", ""));
				param.put("P_BANK_ACCOUNT", bankAccount);
				param.put("P_TYPE", type);
			}
			
			return reportUtils.exportExcel("EntryJournalV2.jrxml", param);
		}else {
			return reportUtils.exportExcel("EntryJournalExcel.jrxml", param);
		}
	}
	
	@Override
	public Object print(Map<String, Object> param) throws Exception {
		Optional<MA0010Entity> company = mA0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		param.put("companyName", companyName);
		
		String version = Param.getStr(param, "version");
		if("V2".equals(version)) {
			String voucherId = Param.getStrWithDef(param, "voucher");
			TR0001Entity tr1 = tR0001Repo.findByGlVoucherId(voucherId);
			
			if(tr1!=null) {
				TR0002Entity bank = tR0002Repo.getBank(voucherId);
				String bankAccount = bank.getGlAccountDesc();	
				
				String type; //type of transaction			
				if(RC.equals(tr1.getGlType()))
					type = TYPE_CREDIT;
				else
					type = TYPE_DEBIT;
				
				BigDecimal totalOrg = BigDecimal.ZERO;
				if(type.equals(TYPE_CREDIT) && bank.getGlOrgDebit().compareTo(BigDecimal.ZERO) > 0) {
					totalOrg = bank.getGlOrgDebit();
				}else if(type.equals(TYPE_CREDIT) && bank.getGlOrgDebit().compareTo(BigDecimal.ZERO) <= 0) {
					totalOrg = bank.getGlOrgCredit().negate();
				}else if(type.equals(TYPE_DEBIT) && bank.getGlOrgCredit().compareTo(BigDecimal.ZERO) > 0) {
					totalOrg = bank.getGlOrgCredit();
				}else if(type.equals(TYPE_DEBIT) && bank.getGlOrgCredit().compareTo(BigDecimal.ZERO) <= 0) {
					totalOrg = bank.getGlOrgDebit().negate();
				}
				
				String currId = tR0002Repo.getBankCurrId(voucherId);
				param.put("P_VOUCHER_ID", voucherId);
				param.put("P_TOTAL_AMOUNT", currId + " " + decimalFormat.format(totalOrg).replace("$", ""));
				param.put("P_BANK_ACCOUNT", bankAccount);
				param.put("P_TYPE", type);
			}
			
			return reportUtils.exportPdf("EntryJournalV2.jrxml", param);
		}else {
			return reportUtils.exportPdf("EntryJournalPdf.jrxml", param);
		}
	}

}
