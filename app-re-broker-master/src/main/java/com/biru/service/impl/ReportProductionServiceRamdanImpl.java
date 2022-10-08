package com.biru.service.impl;

import com.biru.common.param.Param;
import com.biru.component.DateComponent;
import com.biru.component.ReportUtils;
import com.biru.entity.*;
import com.biru.repository.*;
import com.biru.service.ReportProductionService;
import com.biru.service.ReportProductionServiceRamdan;
import com.biru.specifications.TR0012Specifications;
import com.biru.view.ViewInqTr0006Entity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ReportProductionServiceRamdanImpl implements ReportProductionServiceRamdan {

	@Autowired
	private TR0012Repo tR0012Repo;

	@Autowired
	private MA0005Repo mA0005Repo;

	@Autowired
	private ReportUtils reportUtils;

	@Autowired
	private EntryJournalServiceImpl entryJournalServiceImpl;

	@Autowired
	private MA0010Repo mA0010Repo;

	@Autowired
	private MA0011Repo mA0011Repo;

	@Autowired
	private TR0006Repo trR0006Repo;

	@Autowired
	private TR0012Specifications tR0012Specifications;

	@Autowired
	private TR0006ARepo tR0006ARepo;

	@Autowired
	private TR0003ARepo tr0003ARepo ;

	@Autowired
	private TR0006BRepo tR0006BRepo;

	@Autowired
	private ReportProducitionRepo reportProducitionRepo;
	
	@Autowired
	private DateComponent dateComponent;

	@Override
	public Object transactionDetail(Map<String, Object> param) {
		// TODO Auto-generated method stub
		String voucherId = Param.getStr(param, "voucherId");
		
		Boolean isReportProductionDi = param.get("isReportProductionDi") != null ? 
				(Boolean) param.get("isReportProductionDi") : false;

		List<TR0012Entity>result = new ArrayList<TR0012Entity>();

		int level = 0;
		Set<String> setTrxType = new HashSet<String>();
		Collections.addAll(setTrxType, "PU","SE","PO");

		List<String> listTrxOldVoucherId = new ArrayList<String>();
		while (true) {
			List<TR0012Entity> listTR0012Entity = new ArrayList<TR0012Entity>();
			if(level < 1) {
				listTR0012Entity = tR0012Repo.findByTrxTypeInAndTrxOldVoucherIdEquals(setTrxType, voucherId);
				level++;
			}else {
				listTR0012Entity = tR0012Repo.findByTrxOldTypeInAndTrxOldVoucherIdIn(setTrxType, listTrxOldVoucherId);
			}

			if(!listTR0012Entity.isEmpty()) {
				result.addAll(listTR0012Entity);
				setTrxType.clear();
				listTrxOldVoucherId.clear();
				for (TR0012Entity tr0012Entity : listTR0012Entity) {
					setTrxType.add(tr0012Entity.getTrxType());
					listTrxOldVoucherId.add(tr0012Entity.getTrxVoucherId());
				}
			}else {
				break;
			}

		}

		for (TR0012Entity tr0012Entity : result) {
			String cliName = mA0005Repo.findByCliCode(tr0012Entity.getTrxClient()).getCliName();
			tr0012Entity.setCliName(cliName);
		}
		
		if(isReportProductionDi){
			for (TR0012Entity tr0012Entity : result) {
				if(("12").equals(tr0012Entity.getTrxDataStatus())){
					tr0012Entity.setTrxDataStatus("Cancelled");
				}else{
					tr0012Entity.setTrxDataStatus("Active");
				}
			}
		}
		
		Collections.sort(result);

		System.out.println(result);
		return result;
	}


	@Override
	public Object transactionDetailDiVir(Map<String, Object> param) {
		// TODO Auto-generated method stub
		String voucherId = Param.getStr(param, "voucherId");

		Boolean isReportProductionDi = param.get("isReportProductionDi") != null ?
				(Boolean) param.get("isReportProductionDi") : false;

		List<TR0012Entity>result = new ArrayList<TR0012Entity>();

		int level = 0;
		Set<String> setTrxType = new HashSet<String>();
		Collections.addAll(setTrxType, "PU","SE","PO");

		List<String> listTrxOldVoucherId = new ArrayList<String>();
		while (true) {
			List<TR0012Entity> listTR0012Entity = new ArrayList<TR0012Entity>();
			if(level < 1) {
				listTR0012Entity = tR0012Repo.findByTrxTypeInAndTrxOldVoucherIdEquals(setTrxType, voucherId);
				level++;
			}else {
				listTR0012Entity = tR0012Repo.findByTrxOldTypeInAndTrxOldVoucherIdIn(setTrxType, listTrxOldVoucherId);
			}

			if(!listTR0012Entity.isEmpty()) {
				result.addAll(listTR0012Entity);
				setTrxType.clear();
				listTrxOldVoucherId.clear();
				for (TR0012Entity tr0012Entity : listTR0012Entity) {
					setTrxType.add(tr0012Entity.getTrxType());
					listTrxOldVoucherId.add(tr0012Entity.getTrxVoucherId());
				}
			}else {
				break;
			}

		}

		for (TR0012Entity tr0012Entity : result) {
			String cliName = mA0005Repo.findByCliCode(tr0012Entity.getTrxClient()).getCliName();
			tr0012Entity.setCliName(cliName);
		}

		if(isReportProductionDi){
			for (TR0012Entity tr0012Entity : result) {
				if(("12").equals(tr0012Entity.getTrxDataStatus())){
					tr0012Entity.setTrxDataStatus("Cancelled");
				}else{
					tr0012Entity.setTrxDataStatus("Active");
				}
			}
		}

		Collections.sort(result);

		System.out.println(result);
		return result;
	}

	@Override
	public Object export(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		return reportUtils.exportExcel("ReportProductionTransactionDetails.jrxml", param);
	}

	@Override
	public Object printJournal(Map<String, Object> param) throws Exception {
		return entryJournalServiceImpl.printJournal(param);
	}

	@Override
	public Object exportExcel(Map<String, Object> param) throws Exception {
		return entryJournalServiceImpl.exportExcel(param);
	}

	@Override
	public Object print(Map<String, Object> param) throws Exception {
		return entryJournalServiceImpl.print(param);
	}

	@Override
	public synchronized Object tr6ExportToExcel(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		reportProducitionRepo.deleteAll();
		List<String> listTrxDataStatus = new ArrayList<String>();
		Collections.addAll(listTrxDataStatus, "11", "13");
		
		List<String> listTrxSource = new ArrayList<String>();
		Collections.addAll(listTrxSource, "0", "1", "2");
		
		String typeOfCover = Param.getStr(param, "typeOfCover");
		String client = Param.getStrWithDef(param, "client");
		String trxDate = Param.getStrWithDef(param, "transactionDate");
		String [] date = trxDate.split("/");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		c.set(Calendar.MONTH, (Integer.parseInt(date[1])-1));
		c.set(Calendar.YEAR, Integer.parseInt(date[2]));
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		Date trxDateDate = c.getTime();
		System.out.println("trxDateDate = "+trxDateDate);

		String to = Param.getStrWithDef(param, "toTransactionDate");
		Date toDate = null;
		date = to.split("/");
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		c.set(Calendar.MONTH, (Integer.parseInt(date[1])-1));
		c.set(Calendar.YEAR, Integer.parseInt(date[2]));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		toDate = c.getTime();
		System.out.println(client + " - "+ typeOfCover);

		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, "idKey");
		Specification<TR0012Entity> specificationTR0012Entity= tR0012Specifications.trxTypeEquals("SE")
				.and(tR0012Specifications.trxDateBetween(trxDateDate, toDate))
				.and(tR0012Specifications.trxDataStatusIn(listTrxDataStatus))
				.and(tR0012Specifications.trxSourceIn(listTrxSource));
		if(!typeOfCover.equals("ALL")) {
			specificationTR0012Entity = specificationTR0012Entity
					.and(tR0012Specifications.trxCoverCoderEquals(typeOfCover));
		}
		if(!client.equals("")) {
			specificationTR0012Entity = specificationTR0012Entity
					.and(tR0012Specifications.trxClientEquals(client));
		}

		Page<TR0012Entity>pageTR0012Entity = tR0012Repo.findAll(specificationTR0012Entity, pageable);
		Map<String, TR0012Entity> sortedData = new TreeMap<String, TR0012Entity>();
		
		for (TR0012Entity tR0012Entity : pageTR0012Entity.getContent()) {
			if(!(tR0012Entity.getTrxDataStatus().equals("13") && tR0012Entity.getTrxSetAmount().compareTo(BigDecimal.ZERO) == 0)) {
				sortedData.put(tR0012Entity.getIdKey().toString(), tR0012Entity);
				
				Specification<TR0012Entity> specificationTR0012EntityPu = tR0012Specifications.trxTypeEquals("PU")
						.and(tR0012Specifications.trxDateEquals(tR0012Entity.getTrxDate()))
						.and(tR0012Specifications.trxDataStatusIn(listTrxDataStatus))
						.and(tR0012Specifications.trxCoverCoderEquals(tR0012Entity.getTrxCoverCode()))
						.and(tR0012Specifications.trxTrxClassEquals(tR0012Entity.getTrxTrxClass()))
						.and(tR0012Specifications.trxOldVoucherIdEquals(tR0012Entity.getTrxOldVoucherId()))
						.and(tR0012Specifications.trxSourceIn(listTrxSource));	
				Page<TR0012Entity>pageTR0012EntityPu = tR0012Repo.findAll(specificationTR0012EntityPu, pageable);
				for (TR0012Entity tR0012EntityPu : pageTR0012EntityPu.getContent()) {
					if(!(tR0012EntityPu.getTrxDataStatus().equals("13") && tR0012EntityPu.getTrxSetAmount().compareTo(BigDecimal.ZERO) == 0)) 
						sortedData.put(tR0012EntityPu.getIdKey().toString(), tR0012EntityPu);
				}
			}
		}
		
		System.out.println(sortedData.keySet());
		List<ReportProductionEntity> listReportProductionEntity = new ArrayList<ReportProductionEntity>();
		boolean isNewSe = Boolean.TRUE;
		for (TR0012Entity tR0012Entity : sortedData.values()) {
			System.out.println("loop for key " + tR0012Entity.getIdKey());
			if(tR0012Entity.getTrxType().equals("SE")) {
				TR0006Entity tR0006Entity = trR0006Repo.findByTrxVoucherId(tR0012Entity.getTrxOldVoucherId());
				ReportProductionEntity reportProductionEntity = new ReportProductionEntity();
				reportProductionEntity.setIdKey(UUID.randomUUID().toString());
				reportProductionEntity.setTrxVoucherId(tR0012Entity.getTrxVoucherId());
				reportProductionEntity.setTrxOldType(tR0012Entity.getTrxOldType());
				reportProductionEntity.setTrxOldVoucherId(tR0012Entity.getTrxOldVoucherId());
				MA0005Entity mA0005Entity = mA0005Repo.findByCliCode(tR0012Entity.getTrxClient());
				reportProductionEntity.setCliNameSe(mA0005Entity != null ? mA0005Entity.getCliName() : null);
				reportProductionEntity.setTrxCoverCode(tR0012Entity.getTrxCoverCode());
				MA0011Entity mA0011Entity = mA0011Repo.findByTcCode(tR0012Entity.getTrxCoverCode());
				reportProductionEntity.setTcOjkGroup(mA0011Entity != null ? mA0011Entity.getTcOjkGroup() : "");
				reportProductionEntity.setTrxTrxClass(tR0012Entity.getTrxTrxClass().equals("TRE") ? "TREATY" : "FACULTATIVE");
				reportProductionEntity.setTrxSource(tR0012Entity.getTrxSource().equals("0") || tR0012Entity.getTrxSource().equals("1") ? "Production" :
					tR0012Entity.getTrxSource().equals("2") && tR0012Entity.getTrxDescription().contains("Endorsement - ADD") ? "Endorsement - ADD"  : 
						tR0012Entity.getTrxSource().equals("2") && tR0012Entity.getTrxDescription().contains("Endorsement - DED") ? "Endorsement - DED" : "");
				reportProductionEntity.setTrxInsuredName(tR0006Entity != null ? tR0006Entity.getTrxInsuredName() : null);
				reportProductionEntity.setTrxDescription(tR0012Entity.getTrxDescription().length() > 150 ? tR0012Entity.getTrxDescription().substring(0, 150) : tR0012Entity.getTrxDescription());
				List<TR0006AEntity> tR0006AEntity = tR0006ARepo.findByTrxVoucherId(tR0012Entity.getTrxOldVoucherId());
				reportProductionEntity.setTrxInsStart(tR0006AEntity.isEmpty() ? null : tR0006AEntity.get(0).getTrxInsStart());
				reportProductionEntity.setTrxInsEnd(tR0006AEntity.isEmpty() ? null : tR0006AEntity.get(0).getTrxInsEnd());
				reportProductionEntity.setTrxCountInvSe(tR0012Entity.getTrxCountInv());
				reportProductionEntity.setTrxDate(tR0012Entity.getTrxDate());
				reportProductionEntity.setTrxDueDateSe(tR0012Entity.getTrxDueDate());
				reportProductionEntity.setTrxCurrId(tR0012Entity.getTrxCurrId());
				reportProductionEntity.setTrxTsiAmount(tR0006Entity != null ? tR0006Entity.getTrxTsiAmount(): BigDecimal.ZERO);
				reportProductionEntity.setTrxShare(tR0006Entity != null ? tR0006Entity.getTrxShare() : BigDecimal.ZERO);
				reportProductionEntity.setTrxOrgAmountSe(tR0012Entity.getTrxOrgAmount());
				reportProductionEntity.setTrxCurrRate(tR0012Entity.getTrxCurrRate());
				reportProductionEntity.setPremiumIdr(tR0012Entity.getTrxOrgAmount().multiply(tR0012Entity.getTrxCurrRate()));
				reportProductionEntity.setClosingDate(tR0012Entity.getCreateOn());
				
				List<TR0003AEntity> tr3a = tr0003ARepo.findByTrxDueAmountAndTrxDueDateAndTrxNoRow(reportProductionEntity.getTrxOrgAmountSe(), reportProductionEntity.getTrxDueDateSe(), reportProductionEntity.getTrxCountInvSe());
				reportProductionEntity.setDcNote1(tr3a.isEmpty() ? "-" : tr3a.get(0).getTrxVoucherId());
				
				listReportProductionEntity.add(reportProductionEntity);
				isNewSe = Boolean.TRUE;
			}else {
				ReportProductionEntity reportProductionEntityPu = null;
				if(isNewSe) {
					reportProductionEntityPu = listReportProductionEntity.get(listReportProductionEntity.size()-1);
				}else {
					reportProductionEntityPu = new ReportProductionEntity(listReportProductionEntity.get(listReportProductionEntity.size()-1));
					reportProductionEntityPu.setIdKey(UUID.randomUUID().toString()); 
				}
				reportProductionEntityPu.setTrxBrkrFee(tR0012Entity.getTrxBrkrFee());
				reportProductionEntityPu.setTrxTaxinBf(tR0012Entity.getTrxTaxinBf());
				reportProductionEntityPu.setTrxOrgAmountPu(tR0012Entity.getTrxOrgAmount());
				reportProductionEntityPu.setTrxCurrRate(tR0012Entity.getTrxCurrRate());
				if(isNewSe) {
					reportProductionEntityPu.setPremiumIdr(reportProductionEntityPu.getTrxOrgAmountSe().multiply(reportProductionEntityPu.getTrxCurrRate()));
				}else {
					reportProductionEntityPu.setPremiumIdr(BigDecimal.ZERO);
				}
				reportProductionEntityPu.setRiCommIdr(tR0012Entity.getTrxBrkrFee().multiply(tR0012Entity.getTrxCurrRate()));
				reportProductionEntityPu.setVatIdr(tR0012Entity.getTrxTaxinBf().multiply(tR0012Entity.getTrxCurrRate()));
				reportProductionEntityPu.setUnderwriteIdr(tR0012Entity.getTrxOrgAmount().multiply(tR0012Entity.getTrxCurrRate()));
				MA0005Entity mA0005Entity_2 = mA0005Repo.findByCliCode(tR0012Entity.getTrxClient());
				reportProductionEntityPu.setCliNamePu(mA0005Entity_2 != null ? mA0005Entity_2.getCliName() : null);
				reportProductionEntityPu.setDi(mA0005Entity_2 != null ? mA0005Repo.findByCliCode(tR0012Entity.getTrxClient()).getCliIndustry().equals("999") ? "I": "D" : null);
				List<TR0006BEntity> tR0006BEntity = tR0006BRepo.findByTrxVoucherIdAndTrxInsId(tR0012Entity.getTrxOldVoucherId(), tR0012Entity.getTrxClient()); 
				reportProductionEntityPu.setTrxInsShare(!tR0006BEntity.isEmpty() ? tR0006BEntity.get(0).getTrxInsShare() : BigDecimal.ZERO);
				reportProductionEntityPu.setTrxCountInvPu(tR0012Entity.getTrxCountInv());
				reportProductionEntityPu.setTrxDueDatePu(tR0012Entity.getTrxDueDate());
				reportProductionEntityPu.setClosingDate(tR0012Entity.getCreateOn());

				if(!isNewSe) {
					listReportProductionEntity.add(reportProductionEntityPu);
				}
				
				List<TR0003AEntity> tr3a = tr0003ARepo.findByTrxDueAmountAndTrxDueDateAndTrxNoRow(reportProductionEntityPu.getTrxOrgAmountPu(), reportProductionEntityPu.getTrxDueDatePu(), reportProductionEntityPu.getTrxCountInvPu());
				reportProductionEntityPu.setDcNote2(tr3a.isEmpty() ? "-" : tr3a.get(0).getTrxVoucherId());
				
				isNewSe = Boolean.FALSE;
			}
		}
		
		int index = 1;
		for (ReportProductionEntity reportProductionEntity : listReportProductionEntity) {
			reportProductionEntity.setRowNumber(index);
			index++;
		}

		reportProducitionRepo.saveAll(listReportProductionEntity);

		Optional<MA0010Entity> company = mA0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		param.put("typeOfCover", typeOfCover);
		if(client.equals("")) {
			param.put("client", "ALL");
		}else {
			param.put("client", mA0005Repo.findByCliCode(client).getCliName());
		}
		param.put("companyName", companyName);
		param.put("transactionDate", new SimpleDateFormat("dd/MM/yyyy").parse(param.get("transactionDate").toString()));
		param.put("toTransactionDate", new SimpleDateFormat("dd/MM/yyyy").parse(param.get("toTransactionDate").toString()));
		
		Boolean isReportProductionDi = param.get("isReportProductionDi") != null ? 
				(Boolean) param.get("isReportProductionDi") : false;
				
		if(isReportProductionDi){
			return reportUtils.exportExcel("ReportProduction.jrxml", param);
		}else{
			return reportUtils.exportExcel("ReportProduction.jrxml", param);
		}
	}
	
	public ViewInqTr0006Entity generateNewViewTr6Entity(ViewInqTr0006Entity param){
		BigDecimal sumTrxPremium = BigDecimal.ZERO;
		BigDecimal sumTrxOthers1Amt = BigDecimal.ZERO;
		BigDecimal sumTrxPolAmt = BigDecimal.ZERO;
		BigDecimal sumTrxOthers2Amt = BigDecimal.ZERO;
		BigDecimal sumTrxSdutyAmt = BigDecimal.ZERO;
		BigDecimal sumTrxOthers3Amt = BigDecimal.ZERO;
		BigDecimal sumTrxAdmin = BigDecimal.ZERO;
		BigDecimal sumTrxComAmt = BigDecimal.ZERO;
		BigDecimal sumTrxBankAmt = BigDecimal.ZERO;
		BigDecimal sumTrxDiscAmt = BigDecimal.ZERO;
		BigDecimal sumTrxInvcAmt = BigDecimal.ZERO;
		BigDecimal sumTrxBrkrFee = BigDecimal.ZERO;
		BigDecimal sumOthers = BigDecimal.ZERO;
		BigDecimal sumTrxComoAmt = BigDecimal.ZERO;
		BigDecimal sumTrxTaxinBf = BigDecimal.ZERO;
		BigDecimal sumTrxWithAmt = BigDecimal.ZERO;
		BigDecimal sumTrxOthers4Amt = BigDecimal.ZERO;
		BigDecimal sumTrxInvcAmtNetToPay = BigDecimal.ZERO;
		String branch = "";
		
		ViewInqTr0006Entity view = param;
		Set<String> setTrxType = new HashSet<String>();
		Collections.addAll(setTrxType, "PU","SE","PO");
		
		List<TR0012Entity> listOfTr0012Entity = tR0012Repo.findByTrxTypeInAndTrxOldVoucherIdEquals(setTrxType, view.getTr6VoucherId());
		
		if(!listOfTr0012Entity.isEmpty()) {
			for (TR0012Entity tr0012Entity : listOfTr0012Entity) {
				if(("PU").equals(tr0012Entity.getTrxType()) && !("12").equals(tr0012Entity.getTrxDataStatus())){
					sumTrxPremium = sumTrxPremium.add(tr0012Entity.getTrxPremium());
					sumTrxPolAmt = sumTrxPolAmt.add(tr0012Entity.getTrxPolAmount());
					sumTrxSdutyAmt = sumTrxSdutyAmt.add(tr0012Entity.getTrxSdutyAmount());
					sumTrxBankAmt = sumTrxBankAmt.add(tr0012Entity.getTrxBankAmount());
					sumTrxBrkrFee = sumTrxBrkrFee.add(tr0012Entity.getTrxBrkrFee());
					sumTrxComoAmt = sumTrxComoAmt.add(tr0012Entity.getTrxComoAmount());
					sumTrxInvcAmtNetToPay = sumTrxInvcAmtNetToPay.add(tr0012Entity.getTrxInvcAmount());
					sumTrxTaxinBf = sumTrxTaxinBf.add(tr0012Entity.getTrxTaxinBf());
					sumTrxWithAmt = sumTrxWithAmt.add(tr0012Entity.getTrxWithAmount());
				}else if(("SE").equals(tr0012Entity.getTrxType()) && !("12").equals(tr0012Entity.getTrxDataStatus())){
					sumTrxOthers1Amt = sumTrxOthers1Amt.add(tr0012Entity.getTrxOthers1Amount());
					sumTrxOthers2Amt = sumTrxOthers2Amt.add(tr0012Entity.getTrxOthers2Amount());
					sumTrxOthers3Amt = sumTrxOthers3Amt.add(tr0012Entity.getTrxOthers3Amount());
					sumTrxOthers4Amt = sumTrxOthers4Amt.add(tr0012Entity.getTrxOthers4Amount());
					sumTrxAdmin = sumTrxAdmin.add(tr0012Entity.getTrxAdminAmount());
					sumTrxComAmt = sumTrxComAmt.add(tr0012Entity.getTrxComAmount());
					sumTrxDiscAmt = sumTrxDiscAmt.add(tr0012Entity.getTrxDiscAmount());
					sumTrxInvcAmt = sumTrxInvcAmt.add(tr0012Entity.getTrxInvcAmount());
				}
				branch = tr0012Entity.getTrxBranch();
			}
			view.setSumTrxPremium(NumberFormat.getCurrencyInstance().format(sumTrxPremium).replace("$", ""));
			view.setSumTrxPolAmtStr(NumberFormat.getCurrencyInstance().format(sumTrxPolAmt).replace("$", ""));
			view.setSumTrxSdutyAmtStr(NumberFormat.getCurrencyInstance().format(sumTrxSdutyAmt).replace("$", ""));
			view.setSumTrxBankAmtStr(NumberFormat.getCurrencyInstance().format(sumTrxBankAmt).replace("$", ""));
			view.setSumTrxBrkrFeeStr(NumberFormat.getCurrencyInstance().format(sumTrxBrkrFee).replace("$", ""));
			view.setSumTrxComoAmtStr(NumberFormat.getCurrencyInstance().format(sumTrxComoAmt).replace("$", ""));
			view.setSumTrxInvcAmtStr(NumberFormat.getCurrencyInstance().format(sumTrxInvcAmt).replace("$", ""));
			view.setSumTrxInvcAmtNetToPayStr(NumberFormat.getCurrencyInstance().format(sumTrxInvcAmtNetToPay).replace("$", ""));
			view.setSumTrxWithAmtStr(NumberFormat.getCurrencyInstance().format(sumTrxWithAmt).replace("$", ""));
			view.setSumTrxOthers1AmtStr(NumberFormat.getCurrencyInstance().format(sumTrxOthers1Amt).replace("$", ""));
			view.setSumTrxOthers2AmtStr(NumberFormat.getCurrencyInstance().format(sumTrxOthers2Amt).replace("$", ""));
			view.setSumTrxOthers3AmtStr(NumberFormat.getCurrencyInstance().format(sumTrxOthers3Amt).replace("$", ""));
			view.setSumTrxOthers4AmtStr(NumberFormat.getCurrencyInstance().format(sumTrxOthers4Amt).replace("$", ""));
			view.setSumTrxAdminStr(NumberFormat.getCurrencyInstance().format(sumTrxAdmin).replace("$", ""));
			view.setSumTrxComAmtStr(NumberFormat.getCurrencyInstance().format(sumTrxComAmt).replace("$", ""));
			view.setSumTrxDiscAmtStr(NumberFormat.getCurrencyInstance().format(sumTrxDiscAmt).replace("$", ""));
			view.setSumTrxTaxinBfStr(NumberFormat.getCurrencyInstance().format(sumTrxTaxinBf).replace("$", ""));
			sumOthers = sumTrxOthers1Amt.add(sumTrxOthers2Amt).add(sumTrxOthers3Amt).add(sumTrxOthers4Amt);
			view.setSumOthersStr(NumberFormat.getCurrencyInstance().format(sumOthers).replace("$", ""));
			view.setBranch(branch);
		}
		
		return view;
	}

}
