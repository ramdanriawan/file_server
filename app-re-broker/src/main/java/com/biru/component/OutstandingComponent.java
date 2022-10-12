package com.biru.component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import com.biru.common.param.Param;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0010Entity;
import com.biru.entity.MA0015Entity;
import com.biru.entity.OutstdRptEntity;
import com.biru.entity.TR0003AEntity;
import com.biru.entity.TR0003Entity;
import com.biru.entity.TR0007Entity;
import com.biru.entity.TR0012Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0010Repo;
import com.biru.repository.MA0015Repo;
import com.biru.repository.OutstdRptRepo;
import com.biru.repository.TR0003ARepo;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0007Repo;
import com.biru.repository.TR0012Repo;
import com.biru.specifications.TR0012Specifications;

@Component
public class OutstandingComponent {
	
	@Autowired
	private TR0012Specifications tR0012Specifications;
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private OutstdRptRepo outstdRptRepo;
	
	@Autowired
	private TR0012Repo tR0012Repo;
	
	@Autowired
	private TR0003Repo tR0003Repo;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private MA0010Repo mA0010Repo;
	
	@Autowired
	private TR0007Repo tR0007Repo;
	
	@Autowired
	private MA0015Repo mA0015Repo;
	
	@Transactional
	public void setTableCC(Map<String, Object> param){
		System.out.println(param);
		String varC;
		String varD;
		if (Param.getStr(param, "typeParam").equals("RC")) {
			varC = "SE";
			varD = "RC";
		}else {
			varC = "PU";
			varD = "PY";
		}
		String clientParam = Param.getStr(param, "clientParam");
		String coverParam = Param.getStr(param, "coverParam");
		String minDateParam = Param.getStr(param, "minDateParam");
		
		Date asAt = null;
		String [] date = minDateParam.split("/");
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone("GMT+7:00"));
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		c.set(Calendar.MONTH, (Integer.parseInt(date[1])-1));
		c.set(Calendar.YEAR, Integer.parseInt(date[2]));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		asAt = c.getTime();
		System.out.println(asAt);
		
		String userId = Param.getStr(param, "userId");
		
		Pageable pageable = PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, "idKey");
		Specification<TR0012Entity> specificationTR0012Entity= 
//				tR0012Specifications.trxOldVoucherIdIsNotNull() 
				tR0012Specifications.trxTypeEquals(varC)
				.and(tR0012Specifications.trxSourceIn(new ArrayList<String>(Arrays.asList("0", "1"))))
				.and(tR0012Specifications.trxSetAmountNotEqualsTrxOrgAmount())
				.and(tR0012Specifications.trxDataStatusIn(new ArrayList<String>(Arrays.asList("11", "13"))))
				.and(tR0012Specifications.trxDatelessThanOrEqualTo(asAt));
		if(!coverParam.equals("ALL")){
			specificationTR0012Entity = specificationTR0012Entity
					.and(tR0012Specifications.trxCoverCoderEquals(coverParam));
		}

		if(!clientParam.equals("")) {
			specificationTR0012Entity = specificationTR0012Entity
					.and(tR0012Specifications.trxClientEquals(clientParam));
		}
		
		Page<TR0012Entity>pageTR0012Entity = tR0012Repo.findAll(specificationTR0012Entity, pageable);
		List<OutstdRptEntity> listOutstdRptEntity = new ArrayList<OutstdRptEntity>();
		for (TR0012Entity tR0012Entity : pageTR0012Entity.getContent()) {
			OutstdRptEntity outstdRptEntity = new OutstdRptEntity();
			outstdRptEntity.setType(tR0012Entity.getTrxType());
			MA0005Entity ma0005Entity = mA0005Repo.findByCliCode(tR0012Entity.getTrxClient());
			outstdRptEntity.setName( ma0005Entity == null ? "" : ma0005Entity.getCliName());
			outstdRptEntity.setDescription(tR0012Entity.getTrxDescription().length() > 100 ? tR0012Entity.getTrxDescription().substring(0, 100) : tR0012Entity.getTrxDescription());
			outstdRptEntity.setVoucher(tR0012Entity.getTrxVoucherId());
			outstdRptEntity.setProdNo(tR0012Entity.getTrxOldVoucherId());
			outstdRptEntity.setTF((tR0012Entity.getTrxTrxClass() != null && tR0012Entity.getTrxTrxClass().equals("TRE")) ? "T" : "F");
			outstdRptEntity.setRisk(tR0012Entity.getTrxCoverCode());
			outstdRptEntity.setTrxDate(tR0012Entity.getTrxDate());
			outstdRptEntity.setDueDate(tR0012Entity.getTrxDueDate());
			outstdRptEntity.setOrg_ccy(tR0012Entity.getTrxCurrId());
			outstdRptEntity.setKmkRate(tR0012Entity.getTrxCurrRate());
			outstdRptEntity.setOrgOuts(tR0012Entity.getTrxOrgAmount());
			outstdRptEntity.setBegBalance(tR0012Entity.getTrxOrgAmount());
			outstdRptEntity.setBrokergare(tR0012Entity.getTrxBrkrFee());
			outstdRptEntity.setVat(tR0012Entity.getTrxTaxinBf());
			outstdRptEntity.setBrkvat(tR0012Entity.getTrxBrkrFee().add(tR0012Entity.getTrxTaxinBf()));
			outstdRptEntity.setUserid(userId);
			outstdRptEntity.setTrxSource(tR0012Entity.getTrxSource());
			outstdRptEntity.setInvno(tR0012Entity.getTrxCountInv());
			
			Pageable pageable2 = PageRequest.of(0, 1, Sort.Direction.DESC, "trxDate");
			List<TR0012Entity> lastDate = tR0012Repo.findByTrxOldTypeEqualsAndTrxOldVoucherIdEquals(tR0012Entity.getTrxType(), tR0012Entity.getTrxVoucherId(), pageable2).getContent();
			outstdRptEntity.setLastdate(!lastDate.isEmpty() ? lastDate.get(0).getTrxDate() : null);
			
			List<TR0003Entity> tr0003Entity = tR0003Repo.findByTrxOldVoucherIdAndCreateOnAndTrxClient(tR0012Entity.getTrxOldVoucherId(), tR0012Entity.getCreateOn(), tR0012Entity.getTrxClient());
			outstdRptEntity.setDcno(!tr0003Entity.isEmpty() ? tr0003Entity.get(0).getTrxVoucherId() : null);
			
			listOutstdRptEntity.add(outstdRptEntity);
		}
		
//		listOutstdRptEntity = (List<OutstdRptEntity>) outstdRptRepo.saveAll(listOutstdRptEntity);
		System.out.println("Pada Step 2 :\n"
				+ "=========================================================="
				+ "\n"+listOutstdRptEntity);
		
		Map<String, Object> endorsementBagBalance = new HashMap<String, Object>();
		Map<String, Object> endorsementPayment = new HashMap<String, Object>();
		
		for (OutstdRptEntity outstdRptEntity : listOutstdRptEntity) {
			if(outstdRptEntity.getProdNo() != null) {
				BigDecimal begBalance = outstdRptEntity.getBegBalance();
				List<TR0012Entity> listTR0012Entity = tR0012Repo.findByTrxOldTypeEqualsAndTrxOldVoucherIdEqualsAndTrxTypeIn(outstdRptEntity.getType(), outstdRptEntity.getVoucher(), new ArrayList<String>(Arrays.asList(varD)));
				for (TR0012Entity tR0012Entity : listTR0012Entity) {
					if(tR0012Entity.getTrxDate() != null) {
						Calendar cTrxDate = Calendar.getInstance();
						cTrxDate.setTime(tR0012Entity.getTrxDate());
						if(cTrxDate.get(Calendar.YEAR) < c.get(Calendar.YEAR)) {
							List<TR0012Entity> listTR0012EntityCancel = tR0012Repo.findByTrxOldTypeEqualsAndTrxOldVoucherIdEquals(tR0012Entity.getTrxType(), tR0012Entity.getTrxVoucherId());
							if(listTR0012EntityCancel.isEmpty()) {
								List<TR0007Entity> tR7 = tR0007Repo.findByTrxVoucherIdEqualsAndTrxOldVoucherIdEquals(tR0012Entity.getTrxVoucherId(), tR0012Entity.getTrxOldVoucherId());
								if(!tR7.isEmpty()) {
									begBalance = begBalance.subtract(tR7.get(0).getTrxSetAmount());
								}else {
									begBalance = begBalance.subtract(tR0012Entity.getTrxOrgAmount());
								}
								
							}
						}
					}
				}
				
				specificationTR0012Entity= tR0012Specifications.trxTypeEquals(varC)
						.and(tR0012Specifications.trxSourceIn(new ArrayList<String>(Arrays.asList("2"))))
						.and(tR0012Specifications.trxSetAmountNotEqualsTrxOrgAmount())
						.and(tR0012Specifications.trxDataStatusIn(new ArrayList<String>(Arrays.asList("11", "13"))))
						.and(tR0012Specifications.trxOldVoucherIdEquals(outstdRptEntity.getProdNo()));
				Page<TR0012Entity>pageTR0012EntityEndorsement = tR0012Repo.findAll(specificationTR0012Entity, pageable);
				
				if(endorsementBagBalance.get(outstdRptEntity.getProdNo()) == null) {
					BigDecimal endorsement = BigDecimal.ZERO;
					for (TR0012Entity TR0012EntityEndorsement : pageTR0012EntityEndorsement.getContent()) {
						if(TR0012EntityEndorsement.getTrxDate() != null) {
							Calendar cTrxDate = Calendar.getInstance();
							cTrxDate.setTime(TR0012EntityEndorsement.getTrxDate());
							if(cTrxDate.get(Calendar.YEAR) < c.get(Calendar.YEAR)) {
								endorsement = endorsement.add(TR0012EntityEndorsement.getTrxOrgAmount());
							}
						}
					}
					begBalance = begBalance.add(endorsement);
					endorsementBagBalance.put(outstdRptEntity.getProdNo(), endorsement);
				}
				
				outstdRptEntity.setBegBalance(begBalance);
				
				
				BigDecimal payment = outstdRptEntity.getPayment();
				for (TR0012Entity tR0012Entity : listTR0012Entity) {
					if(tR0012Entity.getTrxDate() != null) {
						Calendar cTrxDate = Calendar.getInstance();
						cTrxDate.setTime(tR0012Entity.getTrxDate());
						if(cTrxDate.get(Calendar.YEAR) == c.get(Calendar.YEAR)
								&& cTrxDate.get(Calendar.MONTH) <= c.get(Calendar.MONTH)
								&& cTrxDate.get(Calendar.DAY_OF_MONTH) <= c.get(Calendar.DAY_OF_MONTH)) {
							List<TR0012Entity> listTR0012EntityCancel = tR0012Repo.findByTrxOldTypeEqualsAndTrxOldVoucherIdEquals(tR0012Entity.getTrxType(), tR0012Entity.getTrxVoucherId());
							if(listTR0012EntityCancel.isEmpty()) {
								List<TR0007Entity> tR7 = tR0007Repo.findByTrxVoucherIdEqualsAndTrxOldVoucherIdEquals(tR0012Entity.getTrxVoucherId(), tR0012Entity.getTrxOldVoucherId());
								if(!tR7.isEmpty()) {
									payment = payment.add(tR7.get(0).getTrxSetAmount());
								}else {
									payment = payment.add(tR0012Entity.getTrxOrgAmount());
								}
							}
						}
					}
				}
				outstdRptEntity.setPayment(payment);
				
				if(endorsementPayment.get(outstdRptEntity.getProdNo()) == null) {
					BigDecimal endorsement = BigDecimal.ZERO;
					for (TR0012Entity TR0012EntityEndorsement : pageTR0012EntityEndorsement.getContent()) {
						if(TR0012EntityEndorsement.getTrxDate() != null) {
							Calendar cTrxDate = Calendar.getInstance();
							cTrxDate.setTime(TR0012EntityEndorsement.getTrxDate());
							if(cTrxDate.get(Calendar.YEAR) == c.get(Calendar.YEAR)
									&& cTrxDate.get(Calendar.MONTH) <= c.get(Calendar.MONTH)
									&& cTrxDate.get(Calendar.DAY_OF_MONTH) <= c.get(Calendar.DAY_OF_MONTH)) {
								endorsement = endorsement.add(TR0012EntityEndorsement.getTrxOrgAmount());
							}
						}
					}
					endorsementPayment.put(outstdRptEntity.getProdNo(), endorsement);
					outstdRptEntity.setEndorsement(endorsement);
				}
				
				
				outstdRptEntity.setOutstanding(outstdRptEntity.getBegBalance().subtract(outstdRptEntity.getPayment()).add(outstdRptEntity.getEndorsement()));
				outstdRptEntity.setOutIdr(outstdRptEntity.getKmkRate().multiply(outstdRptEntity.getOutstanding()));
				outstdRptEntity.setBegBalIdr(outstdRptEntity.getKmkRate().multiply(outstdRptEntity.getBegBalance()));
				outstdRptEntity.setPaymentIdr(outstdRptEntity.getKmkRate().multiply(outstdRptEntity.getPayment()));
				outstdRptEntity.setEndorsementIdr(outstdRptEntity.getKmkRate().multiply(outstdRptEntity.getEndorsement()));
				outstdRptEntity.setIdrOuts(outstdRptEntity.getKmkRate().multiply(outstdRptEntity.getOrgOuts()));
				
				List<TR0012Entity> cedant = tR0012Repo.findByTrxTypeInAndTrxOldVoucherIdEquals(new HashSet<String>(Arrays.asList("SE")), outstdRptEntity.getProdNo());
				if(!cedant.isEmpty())
					outstdRptEntity.setCedantName(mA0005Repo.findByCliCode(cedant.get(0).getTrxClient()).getCliName());
				
				outstdRptEntity.setStatus(outstdRptEntity.getOrgOuts().compareTo(outstdRptEntity.getOutstanding()) == 0 ? "O/S" : "PARTIAL");
			
				MA0015Entity ma15 = mA0015Repo.findByExCurrIdAndExMonthAndExYear(outstdRptEntity.getOrg_ccy()
						, (c.get(Calendar.MONTH) + 1)
						, c.get(Calendar.YEAR));
				if(ma15 == null) {
					Pageable page = PageRequest.of(0, 1, Sort.Direction.DESC, "idKey");
					List<MA0015Entity> listMa15= mA0015Repo.findLastByExCurrId(outstdRptEntity.getOrg_ccy(), page).getContent();
					if(!listMa15.isEmpty()) {
						ma15 = listMa15.get(0);
					}
				}
				
				BigDecimal exchangeRate = ma15 != null ?  ma15.getExRateEom() : outstdRptEntity.getKmkRate();
				outstdRptEntity.setExchangeRate(exchangeRate);
				outstdRptEntity.setOutstanding2(outstdRptEntity.getOutstanding().multiply(exchangeRate));
				outstdRptEntity.setBrokerage(outstdRptEntity.getBrokergare().multiply(exchangeRate));
				outstdRptEntity.setVat2(outstdRptEntity.getVat().multiply(exchangeRate));
			}
			
		}
		
		List<OutstdRptEntity> listRemove = new ArrayList<OutstdRptEntity>();
		for (OutstdRptEntity outstdRptEntity : listOutstdRptEntity) {
			if (outstdRptEntity.getOutstanding().equals(BigDecimal.ZERO)) {
				listRemove.add(outstdRptEntity);
	        }
		}
		System.out.println("list listOutstdRptEntity = "+listOutstdRptEntity.size());
		System.out.println("list listOutstdRptEntity yang mau di remove= "+listRemove.size());
	    listOutstdRptEntity.removeAll(listRemove);
		
		outstdRptRepo.deletAllByUserId(userId);
		outstdRptRepo.saveAll(listOutstdRptEntity);
	}
	
	public Object createReport(Map<String, Object> param, String fileType) throws Exception {
		Optional<MA0010Entity> company = mA0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		String asAt = Param.getStr(param, "minDateParam");
		String type;
		if (Param.getStr(param, "typeParam").equals("RC")) {
			type = "Account Receivable";
		}else {
			type = "Account Payable";
		}
		
		
		Map<String, Object> paramOutstanding = new HashMap<String, Object>();
		paramOutstanding.put("companyName", companyName);
		paramOutstanding.put("asAt", asAt);
		paramOutstanding.put("type", type);
		paramOutstanding.put("userId", Param.getStr(param, "userId"));
		
		if(fileType.equals("xlsx")) {
			return reportUtils.exportExcel("OutstandingV2.jrxml", paramOutstanding);
		}
		
		return reportUtils.exportPdf("OutstandingV2.jrxml", paramOutstanding);
	}
}
