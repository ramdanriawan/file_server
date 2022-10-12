package com.biru.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0015AEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.service.BankBookService;

@Service
public class BankBookServiceImpl extends AbstractCommon implements BankBookService {
	
	@Autowired
	private MA0005Repo ma0005Repo;
	
	@Autowired
	private MA0015ARepo ma0015ARepo;
	
	@Autowired
	private TR0001Repo tr0001Repo;
	
	@Autowired
	private TR0002Repo tr0002Repo;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Override
	public Object inquiry(Map<String, Object> param) throws ParseException {
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String filterKey = Param.getStrWithDef(param, PARAM.FILTER_KEY);
		String filterValue = Param.getStrWithDef(param, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<TR0001Entity> data = null;
		if(filterKey.equals("trx"))
			data = tr0001Repo.findBankBookByTrxDate(filterValue, pageable);
		else
			data = tr0001Repo.findBankBookByEntryDate(filterValue, pageable);
		
		
		for(TR0001Entity tr1 : data.getContent()) {
			String action = "<button class=\"btn btn-secondary\" onclick=\"edit('"+tr1.getGlVoucherId()+"')\">" 
					+ "<i class=\"fa fa-edit\"></i>" 
					+ "	</button> &nbsp;"
					+ "<button class=\"btn btn-danger\" onclick=\"remove('"+tr1.getGlVoucherId()+"')\">" 
					+ "<i class=\"fa fa-trash\"></i>" 
					+ "</button>";
			
			tr1.setAction(action);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}
	
	@Override
	public Object inquiryModify(Map<String, Object> p_Params) {
		String glVoucherId = Param.getStr(p_Params, "glVoucherId");
		
		if(StringUtils.isBlank(glVoucherId))
			return "No data found";
				
		Map<String, Object> data = new HashMap<String, Object>();
		
		TR0001Entity tr1 = tr0001Repo.findByGlVoucherId(glVoucherId);		
		if(tr1==null)
			return "No data found";
		data.put("tr1Data", tr1);
		
		List<TR0002Entity> tr2Entities = tr0002Repo.findDetailBankBook(glVoucherId);
		data.put("tr2Data", tr2Entities);
		
		//rekal with new rate
		for(TR0002Entity tr2 : tr2Entities) {
			BigDecimal orgDebit = tr2.getGlOrgDebit();
			BigDecimal orgCredit = tr2.getGlOrgCredit();
			
			MA0015AEntity ma0015a = ma0015ARepo.findByCoaCodeAndExDate(tr2.getGlAccount(), tr1.getGlTrxDate());
			BigDecimal rate = ma0015a == null ? BigDecimal.ZERO : ma0015a.getExKmkRate();
			
			tr2.setGlCurrRate(rate);
			tr2.setGlIdrDebit(orgDebit.multiply(rate));
			tr2.setGlIdrCredit(orgCredit.multiply(rate));
		}
		
		TR0002Entity bank = tr0002Repo.findBank(glVoucherId);
		if(bank!=null) {
			data.put("exchangeBank", bank.getGlCurrRate());
			data.put("bank", bank.getGlAccount());
		}else {
			data.put("exchangeBank", "0.00");
			data.put("bank", "");
		}
		
		data.put("client", tr1.getGlTrxClient());
		MA0005Entity client = ma0005Repo.findByCliCode(tr1.getGlTrxClient());
		
		if(client!=null)
			data.put("clientDesc", client.getCliName());
		
		return data;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public String save(Map<String, Object> param) throws ParseException {		
		String activity = param.get("activity").toString();
		String type = param.get("type").toString();
		
		String transaction = param.get("transaction").toString();
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(formatDateId.parse(transaction));
		
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		String project = param.get("project").toString();
		String office = param.get("office").toString();
		String referenceId = Param.getStr(param, "referenceId");
		
		String description = param.get("description").toString() == "" ? null : param.get("description").toString();
		String remark = param.get("remark").toString() == "" ? null : param.get("remark").toString();
		String client = param.get("client").toString()== "" ? null : param.get("client").toString();
		
		String bank = param.get("bank").toString();
		MA0015AEntity ma0015Bank = ma0015ARepo.findByCoaCodeAndExDate(bank, calTrx.getTime());
		BigDecimal exchangeBank = Param.getBd(param, "exchangeBank");
		
		ArrayList<HashMap<String, Object>> rows = (ArrayList<HashMap<String, Object>>) param.get("rows");
		BigDecimal totalAmount = BigDecimal.ZERO;
		BigDecimal totalAmountIdr = BigDecimal.ZERO;
		for(HashMap<String, Object> rowsData : rows) {
			BigDecimal amount = new BigDecimal(rowsData.get("amount").toString().replace(",", ""));
			BigDecimal amountIdr = new BigDecimal(rowsData.get("amountIdr").toString().replace(",", ""));
			
			totalAmount = totalAmount.add(amount);
			totalAmountIdr = totalAmountIdr.add(amountIdr);
		}
		
		TR0001Entity tr1 = null;
		boolean isModify = Boolean.FALSE;
		
		String voucherId = Param.getStrWithDef(param, "glVoucherId");
		tr1 = tr0001Repo.findByGlVoucherId(voucherId);
		Date now = Calendar.getInstance().getTime();
		
		if(StringUtils.isBlank(voucherId))
			voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		
		if(tr1==null) {
			tr1 = new TR0001Entity();
			tr1.setGlTrxStatus("0");
			tr1.setGlDataStatus("11");
			tr1.setCreateOn(now);
			tr1.setCreateBy(param.get(Param.USER).toString());
		}else {
			isModify = Boolean.TRUE;
		}
		tr1.setGlTrxClass(activity);
		tr1.setGlType(type);
		tr1.setGlVoucherId(voucherId);
		
		tr1.setGlTrxDate(calTrx.getTime());
		tr1.setGlTrxMonth(month.byteValue());
		tr1.setGlTrxYear(year.shortValue());
		
		tr1.setGlTrxOfficeId(office);
		tr1.setGlTrxProject(project);
		tr1.setGlTrxClient(client);
		tr1.setGlTrxDesc(description);
		tr1.setGlTrxInvoice(remark);
		tr1.setGlReffId(referenceId);
		tr1.setGlTrxValueOrg(totalAmount);
		tr1.setGlTrxValueIdr(totalAmountIdr);
		tr1.setGlTrxDue(tr1.getGlTrxDate());
		tr1.setModifyOn(now);
		tr1.setModifyBy(param.get(Param.USER).toString());
		
		if(isModify)
			tr0002Repo.deleteByGlVoucherId(voucherId);
		
		List<TR0002Entity> t2Entities = new ArrayList<TR0002Entity>();
		for(HashMap<String, Object> rowsData : rows) {
			TR0002Entity t2 = new TR0002Entity();
			t2.setGlType(type);
			t2.setGlVoucherId(voucherId);
			t2.setGlAccount(rowsData.get("coa").toString());
			t2.setGlCurrId(rowsData.get("curr").toString());
			
			t2.setGlOrgDebit(BigDecimal.ZERO);
			t2.setGlIdrDebit(BigDecimal.ZERO);
			t2.setGlOrgCredit(BigDecimal.ZERO);
			t2.setGlIdrCredit(BigDecimal.ZERO);
			
			BigDecimal org = new BigDecimal(rowsData.get("amount").toString().replace(",", ""));
			BigDecimal idr = new BigDecimal(rowsData.get("amountIdr").toString().replace(",", ""));
			
			if((type.equals("PY") && idr.signum() >= 0) || (type.equals("RC") && idr.signum() == -1)) {
				t2.setGlOrgDebit(org.abs());
				t2.setGlIdrDebit(idr.abs());
			}else {
				t2.setGlOrgCredit(org.abs());
				t2.setGlIdrCredit(idr.abs());
			}
			
			t2.setGlTrxClass(activity);
			t2.setGlCurrRate(new BigDecimal(rowsData.get("exchange").toString().replace(",", "")));
			t2.setGlDescription(Param.getStr(rowsData, "description"));
			
			t2Entities.add(t2);
		}
		
		//BANK
		TR0002Entity t2Bank = new TR0002Entity();
		t2Bank.setGlType(type);
		t2Bank.setGlVoucherId(voucherId);
		t2Bank.setGlAccount(bank);
		t2Bank.setGlCurrId(ma0015Bank.getExCurrId());
		
		t2Bank.setGlOrgDebit(BigDecimal.ZERO);
		t2Bank.setGlIdrDebit(BigDecimal.ZERO);
		t2Bank.setGlOrgCredit(BigDecimal.ZERO);
		t2Bank.setGlIdrCredit(BigDecimal.ZERO);
		
		BigDecimal amountOrg = totalAmountIdr.divide(exchangeBank, 2, RoundingMode.HALF_UP);
		if((type.equals("RC") && totalAmountIdr.signum() >= 0) || (type.equals("PY") && totalAmountIdr.signum() == -1)) {
			t2Bank.setGlOrgDebit(amountOrg.abs());
			t2Bank.setGlIdrDebit(totalAmountIdr.abs());
		}else {
			t2Bank.setGlOrgCredit(amountOrg.abs());
			t2Bank.setGlIdrCredit(totalAmountIdr.abs());
		}
		
		t2Bank.setGlTrxClass(activity);		
		t2Bank.setGlCurrRate(exchangeBank);
		
		t2Entities.add(t2Bank);
		
		tr0001Repo.save(tr1);
		tr0002Repo.saveAll(t2Entities);
		
		return voucherId;
	}

	@Override
	@Transactional
	public Object delete(Map<String, Object> p_Params) {
		String glVoucherId = Param.getStr(p_Params, "glVoucherId");
		
		int deletedTr1 = tr0001Repo.deleteByGlVoucherId(glVoucherId);
		tr0002Repo.deleteByGlVoucherId(glVoucherId);
		
		if(deletedTr1 > 0)
			return "Data successfully deleted";
		else
			return "No data deleted";
	}

}
