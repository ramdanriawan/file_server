package com.biru.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.ENDORSEMENT_TYPE;
import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.ReportUtils;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0010Entity;
import com.biru.entity.MA0018Entity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0003AEntity;
import com.biru.entity.TR0003Entity;
import com.biru.entity.TR0006AEntity;
import com.biru.entity.TR0006HEntity;
import com.biru.entity.TR0012Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0010Repo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0003ARepo;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0006ARepo;
import com.biru.repository.TR0006HRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.service.CommonService;
import com.biru.service.DCNotesService;

@Service
public class DNotesServiceImpl extends AbstractCommon implements DCNotesService {
	
	@Autowired
	private MA0010Repo ma0010Repo;
	
	@Autowired
	private MA0018Repo ma0018Repo;

	@Autowired
	private TR0001Repo tr0001Repo;
	
	@Autowired
	private TR0002Repo tr0002Repo;
	
	@Autowired
	private TR0003Repo tr0003Repo;
	
	@Autowired
	private TR0003ARepo tr0003ARepo;
	
	@Autowired
	private TR0006Repo tR0006Repo;
	
	@Autowired
	private TR0006ARepo tR0006ARepo;
	
	@Autowired
	private TR0006HRepo tR0006HRepo;
	
	@Autowired
	private TR0012Repo tr0012Repo;
	
	@Autowired
	private MA0005Repo ma0005Repo;
	
	@Autowired
	private CommonService common;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Value("${report.path.jasper}")
	private String pathReport;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String SOURCE 	= "4";
	private static final String RQ 		= "RQ";
	private static final String STATUS_DELETE 	= "13";
	private static final String TRX_VOUCHER_ID 	= "trxVoucherId";
	
	private static final BigDecimal BD_100		= new BigDecimal("100");
	private static final List<String> SOURCES 	= Arrays.asList("1", "4");

	@Override
	@Transactional
	public Object inquiry(Map<String, Object> p_Params) throws ParseException {
		Integer limit = Param.getInt(p_Params, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Params, PARAM.OFFSET);
		String order = Param.getStr(p_Params, PARAM.ORDER);
		String sort = Param.getStr(p_Params, PARAM.SORT);
		String filterKey = Param.getStrWithDef(p_Params, PARAM.FILTER_KEY);
		String filterValue = Param.getStrWithDef(p_Params, PARAM.FILTER_VALUE);
		
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		Page<TR0012Entity> data = null;
		if(filterKey.equals("trx"))
			data = tr0012Repo.findDCNotesByTrxDate(filterValue, SOURCES, pageable);
		else
			data = tr0012Repo.findDCNotesByEntryDate(filterValue, SOURCES, pageable);
		
		for(TR0012Entity tr12 : data.getContent()) {
			BigDecimal num = new BigDecimal(tr12.getTrxVoucherId()).add(BigDecimal.ONE);
			String reprintVoucherId = String.valueOf(num.longValue());
			
			if(reprintVoucherId.length() < tr12.getTrxVoucherId().length()) {
				int length = tr12.getTrxVoucherId().length() - reprintVoucherId.length();
				reprintVoucherId = tr12.getTrxVoucherId().substring(0, length) + reprintVoucherId;
			}
			
			String action = "<button class=\"btn btn-secondary\" onclick=\"reprint('"+ tr12.getTrxVoucherId() +"', '"+ reprintVoucherId +"')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "	</button> &nbsp;";
			
			if(!"13".equals(tr12.getTrxDataStatus()) && tr12.getTrxSource().equals(SOURCE))
				action += "<button class=\"btn btn-danger\" onclick=\"remove('"+tr12.getTrxVoucherId()+"')\">" 
						+ "<i class=\"fa fa-trash\"></i>" 
						+ "</button>";
			
			tr12.setAction(action);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public String reverse(Map<String, Object> p_Params) throws Exception {
		String trxVoucherId = Param.getStr(p_Params, "trxVoucherId");
		String user = Param.getStr(p_Params, Param.USER);
		
		Date now = Calendar.getInstance().getTime();
		//step a
		tr0012Repo.updateTrxDataStatus(trxVoucherId, STATUS_DELETE, now);
		
		//step b & c
		TR0001Entity tr1Old = tr0001Repo.findByGlVoucherId(trxVoucherId);
		TR0001Entity tr1New = (TR0001Entity) copyEntity(tr1Old);
		
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(common.getAppDate());
		
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		String newVoucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		String desc = "Cancel " + tr1New.getGlTrxDesc();
		
		tr1New.setIdKey(null);
		tr1New.setGlVoucherId(newVoucherId);
		tr1New.setGlTrxDesc(desc);
		tr1New.setCreateBy(user);
		tr1New.setCreateOn(now);
		tr1New.setModifyBy(user);
		tr1New.setModifyOn(now);
		
		//update
		tr1New.setGlTrxDate(calTrx.getTime());
		tr1New.setGlTrxMonth(month.byteValue());
		tr1New.setGlTrxYear(year.shortValue());
		tr1New.setGlTrxStatus("0");
		
		List<TR0002Entity> tr2OldEntities = tr0002Repo.findByGlVoucherId(trxVoucherId);
		List<TR0002Entity> tr2NewEntities = new ArrayList<TR0002Entity>();
		
		for(TR0002Entity tr2Old : tr2OldEntities) {
			TR0002Entity tr2New = (TR0002Entity) copyEntity(tr2Old);
			tr2New.setIdKey(null);
			tr2New.setGlVoucherId(tr1New.getGlVoucherId());
			tr2New.setGlOrgDebit(tr2Old.getGlOrgCredit());
			tr2New.setGlIdrDebit(tr2Old.getGlIdrCredit());
			tr2New.setGlOrgCredit(tr2Old.getGlOrgDebit());
			tr2New.setGlIdrCredit(tr2Old.getGlIdrDebit());
			
			tr2NewEntities.add(tr2New);
		}
		
		TR0012Entity tr12Old = tr0012Repo.findByTrxVoucherId(trxVoucherId);
		TR0003Entity tr3Old = tr0003Repo.findByTrxVoucherId(tr12Old.getTrxOldVoucherId()).get(0);
		TR0003Entity tr3New = (TR0003Entity) copyEntity(tr3Old);
		
		String newVoucherIdTr3 = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		tr3New.setTrxVoucherId(newVoucherIdTr3);
		
		//update
		tr3New.setTrxDate(calTrx.getTime());
		
		tr3New.setTrxDescription(desc);
		tr3New.setTrxStatusData(STATUS_DELETE);
		tr3New.setCreateOn(now);
		tr3New.setCreateBy(user);
		
		int lastIndex = tr1New.getGlTrxDesc().lastIndexOf(", ");
		String remarks = tr1New.getGlTrxDesc().substring(
				(lastIndex+1 <= tr1New.getGlTrxDesc().length() ? lastIndex+1 : lastIndex), tr1New.getGlTrxDesc().length());
		TR0003AEntity tr3ANew = new TR0003AEntity();
		tr3ANew.setTrxType(tr3New.getTrxType());
		tr3ANew.setTrxVoucherId(tr3New.getTrxVoucherId());
		tr3ANew.setTrxNoRow(1);
		tr3ANew.setTrxRemarks(remarks.trim());
		tr3ANew.setTrxDueDate(tr1New.getGlTrxDue());
		tr3ANew.setTrxDueAmount(tr1New.getGlTrxValueOrg());
		tr3ANew.setTrxTrxClass("OP");
		
		TR0012Entity tr12New = (TR0012Entity) copyEntity(tr12Old);
		tr12New.setIdKey(null);
		tr12New.setTrxVoucherId(tr1New.getGlVoucherId());
		tr12New.setTrxDescription(tr1New.getGlTrxDesc());
		tr12New.setTrxDataStatus(STATUS_DELETE);
		tr12New.setTrxOldVoucherId(tr3ANew.getTrxVoucherId());
		tr12New.setCreateBy(user);
		tr12New.setCreateOn(now);
		tr12New.setModifyBy(user);
		tr12New.setModifyOn(now);
		
		tr0001Repo.save(tr1New);
		if(tr2NewEntities.size() > 0)
			tr0002Repo.saveAll(tr2NewEntities);
		tr0003Repo.save(tr3New);
		tr0003ARepo.save(tr3ANew);
		tr0012Repo.save(tr12New);
		
		return newVoucherId;
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@Override
	public String save(Map<String, Object> param) throws ParseException {		
		String journal = param.get("journal").toString();
		String type = param.get("type").toString();
		String clientIssued = param.get("clientIssued").toString();
		String clientIssuedDesc = param.get("clientIssuedDesc").toString();
		String reference = param.get("reference").toString();
		
		String transaction = param.get("transaction").toString();
		Calendar calTrx = Calendar.getInstance();
		calTrx.setTime(formatDateId.parse(transaction));
		String clientAssured = param.get("clientAssured").toString();
		String typeOfCover = param.get("typeOfCover").toString();
		String officer = param.get("officer").toString();
		
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
		
		String descriptionInterest = param.get("descriptionInterest").toString();
		String periodFrom = param.get("periodFrom").toString();
		Calendar calPeriodFrom = Calendar.getInstance();
		calPeriodFrom.setTime(formatDateId.parse(periodFrom));
		String periodTo = param.get("periodTo").toString();
		Calendar calPeriodTo = Calendar.getInstance();
		calPeriodTo.setTime(formatDateId.parse(periodTo));
		String descriptionAmount = param.get("descriptionAmount").toString();
		String currency = param.get("currency").toString();
		BigDecimal bdExchange = Param.getBd(param, "exchange");
		String amountDue = param.get("amountDue").toString();
		BigDecimal bdAmountDue = Param.getBd(param, "amountDue");
		
		ArrayList<HashMap<String, Object>> rows = (ArrayList<HashMap<String, Object>>) param.get("rows");
		if(rows.size()==0)
			return null;		
		
		List<MA0018Entity> businessRule = ma0018Repo.findByBrCode(journal);
		
		String desc = "";
		if(type.equals("Reversed"))
			desc += "Cancel ";
		
		if(journal.startsWith("D"))
			desc += "Debit Note, ";
		else
			desc += "Credit Note, ";
		
		desc += clientIssuedDesc + ", ";
		
		if(Strings.isNotBlank(typeOfCover))
			desc += typeOfCover + ", ";
		
		desc += currency + ", " + amountDue;
		
		Date now = Calendar.getInstance().getTime();
		List<TR0001Entity> tr1Entities = new ArrayList<TR0001Entity>();
		List<TR0002Entity> tr2Entities = new ArrayList<TR0002Entity>();
		for(HashMap<String, Object> row : rows) {
			String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
			
			TR0001Entity tr1 = new TR0001Entity();
			tr1.setGlTrxClass("OP");
			if(journal.startsWith("D"))
				tr1.setGlType("SE");
			else
				tr1.setGlType("PU");
			tr1.setGlVoucherId(voucherId);
			
			tr1.setGlTrxDate(calTrx.getTime());
			String dueDate = row.get("dueDate").toString();
			tr1.setGlTrxDue(formatDateId.parse(dueDate));
			tr1.setGlTrxMonth(month.byteValue());
			tr1.setGlTrxYear(year.shortValue());
			
			tr1.setGlTrxOfficeId("0");
			tr1.setGlTrxProject("0000");
			tr1.setGlTrxClient(clientIssued);
			
			tr1.setGlTrxDesc(desc + ", " + row.get("remarks").toString());
			tr1.setGlTrxInvoice(reference);
			BigDecimal valueOrg = Param.getBd(row, "amount");
			BigDecimal valueIdr = valueOrg.multiply(bdExchange);
			tr1.setGlTrxValueOrg(valueOrg);
			tr1.setGlTrxValueIdr(valueIdr);
			tr1.setGlTrxStatus("0");
			tr1.setGlDataStatus("11");
			tr1.setCreateOn(now);
			tr1.setCreateBy(param.get(Param.USER).toString());
			tr1.setModifyOn(now);
			tr1.setModifyBy(param.get(Param.USER).toString());
			
			tr1Entities.add(tr1);
			
			for(MA0018Entity ma0018 : businessRule) {
				TR0002Entity t2 = new TR0002Entity();
				t2.setGlTrxClass(tr1.getGlTrxClass());
				t2.setGlType(tr1.getGlType());
				t2.setGlVoucherId(tr1.getGlVoucherId());
				t2.setGlAccount(ma0018.getBrChildCoa());
				t2.setGlCurrId(currency);
				t2.setGlCurrRate(bdExchange);
				
				t2.setGlOrgDebit(BigDecimal.ZERO);
				t2.setGlIdrDebit(BigDecimal.ZERO);
				t2.setGlOrgCredit(BigDecimal.ZERO);
				t2.setGlIdrCredit(BigDecimal.ZERO);
				if(
					(type.equals("Normal") && ma0018.getBrChildDc().equals('0'))
						|| (type.equals("Reversed") && ma0018.getBrChildDc().equals('1'))
				) {
					t2.setGlOrgDebit(valueOrg);
					t2.setGlIdrDebit(valueIdr);
				}else if(
					(type.equals("Normal") && ma0018.getBrChildDc().equals('1'))
						|| (type.equals("Reversed") && ma0018.getBrChildDc().equals('0'))
				) {
					t2.setGlOrgCredit(valueOrg);
					t2.setGlIdrCredit(valueIdr);
				}
				
				tr2Entities.add(t2);
			}
		}
		
		String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(calTrx.getTime()));
		TR0003Entity tr3 = new TR0003Entity();
		tr3.setTrxType("RQ");
		tr3.setTrxVoucherId(voucherId);
		tr3.setTrxDescription(desc);
		tr3.setTrxClient(clientIssued);
		tr3.setTrxReference(reference);
		tr3.setTrxDate(calTrx.getTime());
		tr3.setTrxAssured(clientAssured);
		tr3.setTrxCoverCode(typeOfCover);
		tr3.setTrxInsOfficer(officer);
		tr3.setTrxInsInsured(descriptionInterest);
		tr3.setTrxInsStart(calPeriodFrom.getTime());
		tr3.setTrxInsEnd(calPeriodTo.getTime());
		tr3.setTrxAmtDesc(descriptionAmount);
		tr3.setTrxCurrId(currency);
		tr3.setTrxCurrRate(bdExchange);
		tr3.setTrxAmountDue(bdAmountDue);
		tr3.setTrxStatusData("11");
		tr3.setCreateOn(now);
		tr3.setCreateBy(param.get(Param.USER).toString());
		
		int noRow = 1;
		List<TR0003AEntity> tr3aEntities = new ArrayList<TR0003AEntity>();
		for(HashMap<String, Object> row : rows) {
			TR0003AEntity tr3a = new TR0003AEntity();
			tr3a.setTrxType(tr3.getTrxType());
			tr3a.setTrxVoucherId(tr3.getTrxVoucherId());
			tr3a.setTrxNoRow(noRow);
			tr3a.setTrxRemarks(row.get("remarks").toString());
			
			String dueDate = row.get("dueDate").toString();
			tr3a.setTrxDueDate(formatDateId.parse(dueDate));
			
			tr3a.setTrxDueAmount(Param.getBd(row, "amount"));
			
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
			tr12.setTrxClient(clientIssued);
			tr12.setTrxDescription(tr1.getGlTrxDesc());
			tr12.setTrxCurrId(currency);
			tr12.setTrxCurrRate(bdExchange);
			tr12.setTrxOrgAmount(bdAmountDue);
			tr12.setTrxIntAmount(BigDecimal.ZERO);
			tr12.setTrxInvcAmount(tr1.getGlTrxValueOrg());
			tr12.setTrxSetAmount(BigDecimal.ZERO);
			tr12.setTrxInsOfficer(officer);
			tr12.setTrxOldType(tr3.getTrxType());
			tr12.setTrxOldVoucherId(tr3.getTrxVoucherId());
			tr12.setCreateOn(now);
			tr12.setCreateBy(param.get(Param.USER).toString());
			tr12.setModifyOn(now);
			tr12.setModifyBy(param.get(Param.USER).toString());
			tr12.setTrxSource(SOURCE);
			
			tr12Entities.add(tr12);
		
			count++;
		}
		
		tr0001Repo.saveAll(tr1Entities);
		tr0002Repo.saveAll(tr2Entities);
		tr0003Repo.save(tr3);
		tr0003ARepo.saveAll(tr3aEntities);
		tr0012Repo.saveAll(tr12Entities);
		
		return voucherId;
	}

	@Override
	public String createDCNotesExcel(Map<String, String> p_Params) throws Exception {
		return createDCNotes(p_Params, "excel");
	}

	@Override
	public String createDCNotesHtml(Map<String, String> p_Params) throws Exception {
		return createDCNotes(p_Params, "html");
	}

	@Override
	public String createDCNotesPdf(Map<String, String> p_Params) throws Exception {
		return createDCNotes(p_Params, "pdf");
	}
	
	@Override
	public String createDCNotesDoc(Map<String, String> p_Params) throws Exception {
		return createDCNotes(p_Params, "doc");
	}
	
	@Override
	public String createDCNotesHtmlUploadTreaty(Map<String, String> p_Params) throws Exception {
		String p_TrxVoucherId = p_Params.get("trxVoucherId");
		String p_Cliname = p_Params.get("cliName");
		String p_CliCode = ma0005Repo.findCliCodeByCliName(p_Cliname);
		System.out.println(">>>>>>> HTML V : "+ p_TrxVoucherId);
		System.out.println(">>>>>>> HTML CLINAME : "+p_Cliname );
		System.out.println(">>>>>>> HTML CLICODE : "+ p_CliCode);
		TR0003Entity tr3 = tr0003Repo.findByTrxOldVoucherId(p_TrxVoucherId);
		TR0006HEntity tr6h = tR0006HRepo.findByTrxOldVoucherIdAndCliCode(p_TrxVoucherId, p_CliCode);
		
		String insuredNameTreaty = tr3.getTrxInsInsured();
		String descriptionTreaty = tr3.getTrxDescription();
		BigDecimal share = (tr6h.getTrxReShare().multiply(BD_100)).setScale(2, RoundingMode.HALF_UP);
		
		p_Params.put("insuredNameTreaty", insuredNameTreaty);
		p_Params.put("descriptionTreaty", descriptionTreaty);
		p_Params.put("shareTreaty", share.toString());
		return createDCNotes(p_Params, "html");
	}
	
	@Override
	public String createDCNotesPdfUploadTreaty(Map<String, String> p_Params) throws Exception {
		String p_TrxVoucherId = p_Params.get("trxVoucherId");
		String p_Cliname = p_Params.get("cliName");
		String p_CliCode = ma0005Repo.findCliCodeByCliName(p_Cliname);
		System.out.println(">>>>>>> PDF V : "+ p_TrxVoucherId);
		System.out.println(">>>>>>> PDF CLINAME : "+p_Cliname );
		System.out.println(">>>>>>> PDF CLICODE : "+ p_CliCode);
		
		TR0003Entity tr3 = tr0003Repo.findByTrxOldVoucherId(p_TrxVoucherId);
		TR0006HEntity tr6h = tR0006HRepo.findByTrxOldVoucherIdAndCliCode(p_TrxVoucherId, p_CliCode);
		
		String insuredNameTreaty = tr3.getTrxInsInsured();
		String descriptionTreaty = tr3.getTrxDescription();
		BigDecimal share = (tr6h.getTrxReShare().multiply(BD_100)).setScale(2, RoundingMode.HALF_UP);
		
		p_Params.put("insuredNameTreaty", insuredNameTreaty);
		p_Params.put("descriptionTreaty", descriptionTreaty);
		p_Params.put("shareTreaty", share.toString());
		return createDCNotes(p_Params, "pdf");
	}
	
	@Override
	public String createDCNotesDocUploadTreaty(Map<String, String> p_Params) throws Exception {
		String p_TrxVoucherId = p_Params.get("trxVoucherId");
		String p_Cliname = p_Params.get("cliName");
		String p_CliCode = ma0005Repo.findCliCodeByCliName(p_Cliname);
		System.out.println(">>>>>>> DOC V : "+ p_TrxVoucherId);
		System.out.println(">>>>>>> DOC CLINAME : "+p_Cliname );
		System.out.println(">>>>>>> DOC CLICODE : "+ p_CliCode);
		
		TR0003Entity tr3 = tr0003Repo.findByTrxOldVoucherId(p_TrxVoucherId);
		TR0006HEntity tr6h = tR0006HRepo.findByTrxOldVoucherIdAndCliCode(p_TrxVoucherId, p_CliCode);
		
		String insuredNameTreaty = tr3.getTrxInsInsured();
		String descriptionTreaty = tr3.getTrxDescription();
		BigDecimal share = (tr6h.getTrxReShare().multiply(BD_100)).setScale(2, RoundingMode.HALF_UP);
		
		p_Params.put("insuredNameTreaty", insuredNameTreaty);
		p_Params.put("descriptionTreaty", descriptionTreaty);
		p_Params.put("shareTreaty", share.toString());
		return createDCNotes(p_Params, "doc");
	}
	
	private String createDCNotes(Map<String, String> p_Params, String p_Type) throws Exception {
		logger.info("createDCNotes param : {}.", p_Params);
		
		Optional<MA0010Entity> company = ma0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		String address = company.get().getCoAddress();
		String chairman = company.get().getCoBodPresident();
		
		String voucherId = p_Params.get("voucherId");
		String type = p_Params.get("type");
		
		String forDesc1Treaty = "";
		
		String interestDesc = p_Params.get("interestDesc");
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("P_COMPANY", companyName);
		params.put("P_VOUCHER_ID", voucherId);
		params.put("P_ADDRESS", address);
		params.put("P_CHAIRMAN", chairman);
		params.put("P_TYPE", type);
		params.put("SUBREPORT_DIR", pathReport + "DCNotes" + File.separator);
		
		String trxVoucherId = p_Params.get(TRX_VOUCHER_ID);
		if(StringUtils.isNotBlank(trxVoucherId)) {
			String insSub = p_Params.get("insSub");
			
			TR0006AEntity interest = null;
			if(insSub==null || insSub=="")
				interest = tR0006ARepo
							.findByTrxTrxIdAndTrxVoucherId(RQ, trxVoucherId)
							.get(0);
			else
				interest = tR0006ARepo
							.findByTrxTrxIdAndTrxVoucherIdAndTrxInsSub(RQ, trxVoucherId, Integer.valueOf(insSub))
							.get(0);
			
			String typeOfCover = interest.getTrxCoverCode();
			String dd = trxVoucherId.substring(0, 2);
			String mm = trxVoucherId.substring(2, 4);
			String yyyy = trxVoucherId.substring(4, 8);
			String no = trxVoucherId.substring(8, trxVoucherId.length());
			String slipNo = typeOfCover + "/" + yyyy+ "/" + mm + "/" + dd + no;
			
			String interestInsured = interest.getTrxInsInsured()
					.replaceAll("font-size:", "foff-size:")
					.replaceAll("font-family", "foff-family")
					.replaceAll("<font face=", "<foff face=")
					.replaceAll("</font>", "</foff>");
			
			String insuredNameCI = tR0006Repo.findTrxInsuredNameByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			if(insuredNameCI != null) {
				insuredNameCI = insuredNameCI
						.replaceAll("font-size:", "foff-size:")
						.replaceAll("font-family", "foff-family")
						.replaceAll("<font face=", "<foff face=")
						.replaceAll("</font>", "</foff>");
			}else {
				interest = tR0006ARepo.findOneIIIByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
				insuredNameCI = interest.getTrxInsInsured();
				typeOfCover = interest.getTrxCoverCode();
				slipNo = typeOfCover + "/" + yyyy+ "/" + mm + "/" + dd + no;
				
				interestInsured = interest.getTrxInsInsured()
						.replaceAll("font-size:", "foff-size:")
						.replaceAll("font-family", "foff-family")
						.replaceAll("<font face=", "<foff face=")
						.replaceAll("</font>", "</foff>");
				
				forDesc1Treaty = interest.getTrxInsLocation();
			}
			
			if(StringUtils.isNotBlank(p_Params.get("insuredNameTreaty"))) {
				//module Upload Treaty
				insuredNameCI = p_Params.get("insuredNameTreaty");
			}
			
			BigDecimal totalSumInsured = tR0006Repo.findTotalSumInsuredByTrxIdAndTrxVoucherId(RQ, trxVoucherId);
			BigDecimal sumInsured = interest.getTrxSumInsured();
			
			BigDecimal pctInsured = BigDecimal.ZERO;
			if(totalSumInsured.signum()>0)
				pctInsured = sumInsured.divide(totalSumInsured, 2, RoundingMode.HALF_UP).multiply(BD_100);
			
			String description1 = slipNo + " - " + insuredNameCI + " - " + interest.getTrxRemarks() + " - " + interestInsured + " - ";
			
			String endorsementType = p_Params.get("endorsementType") == null ? "" : p_Params.get("endorsementType");
			endorsementType = getDescriptionEndorsement(interest, endorsementType.toString());
			if(endorsementType.equals("Endorsement - DED"))
				description1 = description1 + interest.getTrxCurrId() + " - (" 
							+ NumberFormat.getCurrencyInstance().format(sumInsured).replace("$", "") +")";
			else
				description1 = description1 + interest.getTrxCurrId() + " - " 
						+ NumberFormat.getCurrencyInstance().format(sumInsured).replace("$", "");
			
			String description2 = "Order : " + NumberFormat.getCurrencyInstance().format(pctInsured).replace("$", "") + " of 100%";
			
			String p_Description2Add = p_Params.get("p_Description2Add");
			
			if(StringUtils.isNotBlank(interestDesc)) {
				//module Extention
				String p_TrxVoucherId = p_Params.get("trxVoucherId");
				TR0003Entity tr3 = tr0003Repo.findByTrxOldVoucherId(p_TrxVoucherId);
				TR0006HEntity tr6h = tR0006HRepo.findByTrxOldVoucherId(p_TrxVoucherId);
				
				String insuredNameExtention = tr3.getTrxInsInsured();
				BigDecimal share = (tr6h.getTrxReShare().multiply(BD_100)).setScale(2, RoundingMode.HALF_UP);
				//share.setScale(2, RoundingMode.HALF_UP);
				
				insuredNameCI = insuredNameExtention;
				description1 = interestDesc;
				p_Description2Add = share.toString();
			}
			
			if(insuredNameCI == null) {
				description1 = slipNo + " - " + forDesc1Treaty;
				p_Description2Add = "";
				params.put("P_SHARE", BigDecimal.ZERO);
			}
			
			if(StringUtils.isNotBlank(endorsementType))
				description1 = endorsementType.concat(" - ").concat(description1);
			
			if(StringUtils.isNotBlank(p_Params.get("descriptionTreaty"))){
				//module Upload Treaty
				description1 = p_Params.get("descriptionTreaty");
				p_Description2Add = p_Params.get("shareTreaty");
			}
			
			params.put("P_RQ_NUMBER", slipNo);
			params.put("P_INSURED_NAME", insuredNameCI);
			params.put("P_DESCRIPTION1", description1);
			params.put("P_DESCRIPTION2", description2);
			
			//String p_Description2Add = p_Params.get("p_Description2Add");
			params.put("P_DESCRIPTION2_ADD", p_Description2Add != null ? p_Description2Add : "");
		}
		
		if(p_Type.equals("pdf"))
			return reportUtils.exportPdf("DCNotes.jrxml", params);
		else if(p_Type.equals("excel"))
			return reportUtils.exportExcel("DCNotes.jrxml", params);
		else if(p_Type.equals("doc"))
			return reportUtils.exportDocx("DCNotes.jrxml", params);
		else
			return reportUtils.exportHtml("DCNotes.jrxml", params);
	}

	private Object copyEntity(Object original) throws Exception {
	    ByteArrayOutputStream bos = new ByteArrayOutputStream();
	    ObjectOutputStream out = new ObjectOutputStream(bos);
	    out.writeObject(original);

	    ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
	    ObjectInputStream in = new ObjectInputStream(bis);
	    return in.readObject();
	}
	
	//method to define type of endorsement DED/ADD
	//handle Modify Facultative
	private String getDescriptionEndorsement(TR0006AEntity interest, String text) {
		String textEndorsement = "Endorsement - ";
		if(textEndorsement.equals(text)) {
			if(ENDORSEMENT_TYPE.ADD.getKey().equals(interest.getTrxTypeAd()))
				text += ENDORSEMENT_TYPE.ADD.getValue();
			else
				text += ENDORSEMENT_TYPE.DED.getValue();
		}
		
		return text;
	}
	
}
