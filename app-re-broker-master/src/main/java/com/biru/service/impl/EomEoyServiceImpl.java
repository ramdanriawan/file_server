package com.biru.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.biru.ReBrokerConstants.CODE;
import com.biru.common.AbstractCommon;
import com.biru.common.param.Param;
import com.biru.component.ProgressComponent;
import com.biru.component.VoucherComponent;
import com.biru.entity.GL0001Entity;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0015Entity;
import com.biru.entity.ProgressEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0005Entity;
import com.biru.helper.TrHelper;
import com.biru.repository.GL0001Repo;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0015Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0005Repo;
import com.biru.service.CommonService;
import com.biru.service.EomEoyService;
import com.biru.service.EoyService;

@Service
public class EomEoyServiceImpl extends AbstractCommon implements EomEoyService {
	
	@Autowired
	private GL0001Repo gl0001Repo;
	
	@Autowired
	private MA0004Repo ma0004Repo;
	
	@Autowired
	private MA0014Repo ma0014Repo;
	
	@Autowired
	private MA0015Repo ma0015Repo;
	
	@Autowired
	private TR0001Repo tr0001Repo;
	
	@Autowired
	private TR0002Repo tr0002Repo;

	@Autowired
	private TR0005Repo tr0005Repo;
	
	@Autowired
	private CommonService commonService;
	
	@Autowired
	private ProgressComponent progressComponent;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private EoyService eoyService;

	private static final String TENANT 			= "tenant";
	private static final String MONTH 			= "month";
	private static final String YEAR 			= "year";
	
	private Logger logger = LoggerFactory.getLogger(EomEoyServiceImpl.class);
	
	@Override
	public List<TrHelper> checkDataEom(Map<String, Object> p_Params) {
		long start = System.currentTimeMillis();
		logger.info("Start - Check data Eom.");
		
		String month = p_Params.get("month").toString();
		String year = p_Params.get("year").toString();
		
		List<TrHelper> datas = tr0001Repo.findDataEom(new Byte(month), new Short(year));
		
		logger.info("Finish - Check data Eom data : {}, elapsed time : {}ms.", 
				datas, System.currentTimeMillis()-start);
		
		return datas;
	}
	
	@Override
	@Transactional
	public void doProcess(Map<String, Object> p_Params) {
		long start = System.currentTimeMillis();
		logger.info("Start - Process End Of Month with param : {}.",
				p_Params);
		
		String appDate = null;
		String tenant = Param.getStr(p_Params, TENANT);
		try {
			appDate = updateAppDate(p_Params);
			
			updateBalanceEom(p_Params);
			updateDataEom(p_Params);
			ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
			pe.setProgress("40");
			progressComponent.getDataProgress().put(tenant, pe);
			
			calculateBalanceAllClass(p_Params);
			balancing(p_Params);
			pe = progressComponent.getDataProgress().get(tenant);
			pe.setProgress("60");
			progressComponent.getDataProgress().put(tenant, pe);
			
			calculateRollUpCoa(p_Params);
			pe = progressComponent.getDataProgress().get(tenant);
			pe.setProgress("80");
			progressComponent.getDataProgress().put(tenant, pe);
			
			insertTrEom(p_Params);
			finalizeEom(p_Params);
			pe = progressComponent.getDataProgress().get(tenant);
			pe.setProgress("100");
			progressComponent.getDataProgress().put(tenant, pe);
		}catch(Exception e) {
			ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
			pe.setIsFailed(Boolean.TRUE);
			pe.setReason(e.getMessage());
			progressComponent.getDataProgress().put(tenant, pe);
			
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}finally {
			if(appDate != null)
				revertAppDate(appDate);
		}
		
		logger.info("Finish - Process End Of Month with param : {}, elapsed time : {}ms.", 
				p_Params, System.currentTimeMillis()-start);
	}
	
	public void updateBalanceEom(Map<String, Object> p_Params) throws Exception {
		logger.info("do updateBalanceEom().");
		String month = Param.getStr(p_Params, MONTH);
		String year = Param.getStr(p_Params, YEAR);
		
		initBalanceEom(p_Params);
		
		List<TR0002Entity> summaryTr = getSummaryTrByAccount(month, year);
		List<GL0001Entity> balance = getBalance(month, year);
		
		boolean isError = Boolean.FALSE;
		for(TR0002Entity tr : summaryTr) {
			GL0001Entity gl = balance.stream()
					.filter(glx -> tr.getGlAccount().equals(glx.getCoaCode()))
					.findFirst().orElse(null);
			
			if(gl == null) {
				logger.warn("Account {} not found in GL0001.", tr.getGlAccount());
				isError = Boolean.TRUE;
			}
		}
		
		if(isError)
			throw new Exception("Coa Code Not Found in GL0001");
		
		for(GL0001Entity gl : balance) {
			TR0002Entity tr = summaryTr.stream()
					.filter(trx -> trx.getGlAccount().equals(gl.getCoaCode()))
					.findFirst().orElse(null);
			
			if(tr!=null) {
				gl.setGlBalCredit13(tr.getGlOrgCredit());
				gl.setGlBalDebit13(tr.getGlOrgDebit());
				gl.setGlBalCIdr13(tr.getGlIdrCredit());
				gl.setGlBalDIdr13(tr.getGlIdrDebit());
			}
		}
		
		if(balance.size()>0)
			gl0001Repo.saveAll(balance);
		
		logger.info("end updateBalanceEom(), size balance detail : {}, size trx detail : {}.", 
				balance.size(), summaryTr.size());
	}
	
	private List<TR0002Entity> getSummaryTrByAccount(String p_Month, String p_Year) {
		return tr0002Repo.summaryByAccount(new Byte(p_Month), new Short(p_Year));
	}
	
	private List<GL0001Entity> getBalance(String p_Month, String p_Year) {
		return gl0001Repo.findBalanceEom(new Byte(p_Month), new Short(p_Year), p_Year);
	}
	
	private List<GL0001Entity> getBalanceAllClass(String p_Year) {
		return gl0001Repo.findBalanceAllClassEom(p_Year);
	}
	
	private void calculateBalanceAllClass(Map<String, Object> p_Params) {
		logger.info("do calculateBalanceAllClass().");
		String month = Param.getStr(p_Params, MONTH);
		String year = Param.getStr(p_Params, YEAR);
		
		List<GL0001Entity> balance = getBalanceAllClass(year);

		for(GL0001Entity gl : balance) {
			MA0015Entity ma0015 = ma0015Repo.findByCoaCodeAndExMonthAndExYear(
					gl.getCoaCode(), Integer.valueOf(month), Integer.valueOf(year));
			
			if("1".equals(month)) {
				gl.setGlBalDIdr1(gl.getGlBalDebit1().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr1(gl.getGlBalCredit1().multiply(ma0015.getExRateEom()));
			}else if("2".equals(month)) {
				gl.setGlBalDIdr2(gl.getGlBalDebit2().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr2(gl.getGlBalCredit2().multiply(ma0015.getExRateEom()));
			}else if("3".equals(month)) {
				gl.setGlBalDIdr3(gl.getGlBalDebit3().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr3(gl.getGlBalCredit3().multiply(ma0015.getExRateEom()));
			}else if("4".equals(month)) {
				gl.setGlBalDIdr4(gl.getGlBalDebit4().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr4(gl.getGlBalCredit4().multiply(ma0015.getExRateEom()));
			}else if("5".equals(month)) {
				gl.setGlBalDIdr5(gl.getGlBalDebit5().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr5(gl.getGlBalCredit5().multiply(ma0015.getExRateEom()));
			}else if("6".equals(month)) {
				gl.setGlBalDIdr6(gl.getGlBalDebit6().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr6(gl.getGlBalCredit6().multiply(ma0015.getExRateEom()));
			}else if("7".equals(month)) {
				gl.setGlBalDIdr7(gl.getGlBalDebit7().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr7(gl.getGlBalCredit7().multiply(ma0015.getExRateEom()));
			}else if("8".equals(month)) {
				gl.setGlBalDIdr8(gl.getGlBalDebit8().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr8(gl.getGlBalCredit8().multiply(ma0015.getExRateEom()));
			}else if("9".equals(month)) {
				gl.setGlBalDIdr9(gl.getGlBalDebit9().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr9(gl.getGlBalCredit9().multiply(ma0015.getExRateEom()));
			}else if("10".equals(month)) {
				gl.setGlBalDIdr10(gl.getGlBalDebit10().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr10(gl.getGlBalCredit10().multiply(ma0015.getExRateEom()));
			}else if("11".equals(month)) {
				gl.setGlBalDIdr11(gl.getGlBalDebit11().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr11(gl.getGlBalCredit11().multiply(ma0015.getExRateEom()));
			}else if("12".equals(month)) {
				gl.setGlBalDIdr12(gl.getGlBalDebit12().multiply(ma0015.getExRateEom()));
				gl.setGlBalCIdr12(gl.getGlBalCredit12().multiply(ma0015.getExRateEom()));
			}
		}
		
		if(balance.size()>0)
			gl0001Repo.saveAll(balance);
		
		logger.info("end calculateBalanceAllClass().");
	}
	
	private void balancing(Map<String, Object> p_Params) {
		logger.info("do balancing().");
		String month = Param.getStr(p_Params, MONTH);
		String year = Param.getStr(p_Params, YEAR);
		
		List<GL0001Entity> balDetail = gl0001Repo.getBalanceDetailByYear(year);

		BigDecimal different = BigDecimal.ZERO;
		for(GL0001Entity det : balDetail) {
			if("1".equals(month))
				different = different.add(det.getGlBalDIdr1().subtract(det.getGlBalCIdr1()));
			else if("2".equals(month))
				different = different.add(det.getGlBalDIdr2().subtract(det.getGlBalCIdr2()));
			else if("3".equals(month))
				different = different.add(det.getGlBalDIdr3().subtract(det.getGlBalCIdr3()));
			else if("4".equals(month))
				different = different.add(det.getGlBalDIdr4().subtract(det.getGlBalCIdr4()));
			else if("5".equals(month))
				different = different.add(det.getGlBalDIdr5().subtract(det.getGlBalCIdr5()));
			else if("6".equals(month))
				different = different.add(det.getGlBalDIdr6().subtract(det.getGlBalCIdr6()));
			else if("7".equals(month))
				different = different.add(det.getGlBalDIdr7().subtract(det.getGlBalCIdr7()));
			else if("8".equals(month))
				different = different.add(det.getGlBalDIdr8().subtract(det.getGlBalCIdr8()));
			else if("9".equals(month))
				different = different.add(det.getGlBalDIdr9().subtract(det.getGlBalCIdr9()));
			else if("10".equals(month))
				different = different.add(det.getGlBalDIdr10().subtract(det.getGlBalCIdr10()));
			else if("11".equals(month))
				different = different.add(det.getGlBalDIdr11().subtract(det.getGlBalCIdr11()));
			else if("12".equals(month))
				different = different.add(det.getGlBalDIdr12().subtract(det.getGlBalCIdr12()));
		}
		
		logger.info("different : {}.", different);
		
		MA0004Entity coaCodeEoy = ma0004Repo.findAccountEoy(CODE.COAEOY007);
		GL0001Entity glCoaEoy = gl0001Repo.findByCoaCodeAndGlBalYear(coaCodeEoy.getCoaCode(), year);
		
		logger.info("Coa Code EOM : {}.", coaCodeEoy.getCoaCode());
		
		BigDecimal debitCoaEoy = BigDecimal.ZERO;
		BigDecimal creditCoaEoy = BigDecimal.ZERO;
		BigDecimal dIdrCoaEoy = BigDecimal.ZERO;
		BigDecimal cIdrCoaEoy = BigDecimal.ZERO;
		
		if(different.compareTo(BigDecimal.ZERO) < 0) {
			debitCoaEoy = different.abs();
			dIdrCoaEoy = different.abs();
		}else {
			creditCoaEoy = different.abs();
			cIdrCoaEoy = different.abs();
		}
		
		logger.info("Different : {}.", different);
		
		if("1".equals(month)) {
			glCoaEoy.setGlBalDebit1(glCoaEoy.getGlBalDebit1().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit1(glCoaEoy.getGlBalCredit1().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr1(glCoaEoy.getGlBalDIdr1().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr1(glCoaEoy.getGlBalCIdr1().add(cIdrCoaEoy));
		}else if("2".equals(month)) {
			glCoaEoy.setGlBalDebit2(glCoaEoy.getGlBalDebit2().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit2(glCoaEoy.getGlBalCredit2().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr2(glCoaEoy.getGlBalDIdr2().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr2(glCoaEoy.getGlBalCIdr2().add(cIdrCoaEoy));
		}else if("3".equals(month)) {
			glCoaEoy.setGlBalDebit3(glCoaEoy.getGlBalDebit3().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit3(glCoaEoy.getGlBalCredit3().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr3(glCoaEoy.getGlBalDIdr3().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr3(glCoaEoy.getGlBalCIdr3().add(cIdrCoaEoy));
		}else if("4".equals(month)) {
			glCoaEoy.setGlBalDebit4(glCoaEoy.getGlBalDebit4().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit4(glCoaEoy.getGlBalCredit4().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr4(glCoaEoy.getGlBalDIdr4().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr4(glCoaEoy.getGlBalCIdr4().add(cIdrCoaEoy));
		}else if("5".equals(month)) {
			glCoaEoy.setGlBalDebit5(glCoaEoy.getGlBalDebit5().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit5(glCoaEoy.getGlBalCredit5().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr5(glCoaEoy.getGlBalDIdr5().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr5(glCoaEoy.getGlBalCIdr5().add(cIdrCoaEoy));
		}else if("6".equals(month)) {
			glCoaEoy.setGlBalDebit6(glCoaEoy.getGlBalDebit6().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit6(glCoaEoy.getGlBalCredit6().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr6(glCoaEoy.getGlBalDIdr6().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr6(glCoaEoy.getGlBalCIdr6().add(cIdrCoaEoy));
		}else if("7".equals(month)) {
			glCoaEoy.setGlBalDebit7(glCoaEoy.getGlBalDebit7().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit7(glCoaEoy.getGlBalCredit7().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr7(glCoaEoy.getGlBalDIdr7().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr7(glCoaEoy.getGlBalCIdr7().add(cIdrCoaEoy));
		}else if("8".equals(month)) {
			glCoaEoy.setGlBalDebit8(glCoaEoy.getGlBalDebit8().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit8(glCoaEoy.getGlBalCredit8().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr8(glCoaEoy.getGlBalDIdr8().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr8(glCoaEoy.getGlBalCIdr8().add(cIdrCoaEoy));
		}else if("9".equals(month)) {
			glCoaEoy.setGlBalDebit9(glCoaEoy.getGlBalDebit9().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit9(glCoaEoy.getGlBalCredit9().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr9(glCoaEoy.getGlBalDIdr9().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr9(glCoaEoy.getGlBalCIdr9().add(cIdrCoaEoy));
		}else if("10".equals(month)) {
			glCoaEoy.setGlBalDebit10(glCoaEoy.getGlBalDebit10().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit10(glCoaEoy.getGlBalCredit10().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr10(glCoaEoy.getGlBalDIdr10().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr10(glCoaEoy.getGlBalCIdr10().add(cIdrCoaEoy));
		}else if("11".equals(month)) {
			glCoaEoy.setGlBalDebit11(glCoaEoy.getGlBalDebit11().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit11(glCoaEoy.getGlBalCredit11().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr11(glCoaEoy.getGlBalDIdr11().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr11(glCoaEoy.getGlBalCIdr11().add(cIdrCoaEoy));
		}else if("12".equals(month)) {
			glCoaEoy.setGlBalDebit12(glCoaEoy.getGlBalDebit12().add(debitCoaEoy));
			glCoaEoy.setGlBalCredit12(glCoaEoy.getGlBalCredit12().add(creditCoaEoy));
			glCoaEoy.setGlBalDIdr12(glCoaEoy.getGlBalDIdr12().add(dIdrCoaEoy));
			glCoaEoy.setGlBalCIdr12(glCoaEoy.getGlBalCIdr12().add(cIdrCoaEoy));
		}
		
		gl0001Repo.save(glCoaEoy);
		
		logger.info("GL0001 : {}.", glCoaEoy);
		
		p_Params.put("different", different.toString());
		
		logger.info("end balancing().");
	}
	
	private void calculateRollUpCoa(Map<String, Object> p_Params) {
		logger.info("do calculateRollUpCoa().");
		String month = Param.getStr(p_Params, MONTH);
		String year = Param.getStr(p_Params, YEAR);
		
		for(int level=10; level>1; level--) {
			List<GL0001Entity> update = new ArrayList<GL0001Entity>();
			List<GL0001Entity> summaryRollUp = gl0001Repo.summaryRollUp(year, level);
			
			for(GL0001Entity glRoll : summaryRollUp) {
				GL0001Entity gl = gl0001Repo.findByCoaCodeAndGlBalYear(glRoll.getCoaCode(), year);
				if(gl == null)
					logger.error("Coa roll up not found : {}.", glRoll.getCoaCode());
				if("1".equals(month)) {
					gl.setGlBalCredit1(glRoll.getGlBalCredit1());
					gl.setGlBalDebit1(glRoll.getGlBalDebit1());
					gl.setGlBalCIdr1(glRoll.getGlBalCIdr1());
					gl.setGlBalDIdr1(glRoll.getGlBalDIdr1());
				}else if("2".equals(month)) {
					gl.setGlBalCredit2(glRoll.getGlBalCredit2());
					gl.setGlBalDebit2(glRoll.getGlBalDebit2());
					gl.setGlBalCIdr2(glRoll.getGlBalCIdr2());
					gl.setGlBalDIdr2(glRoll.getGlBalDIdr2());
				}else if("3".equals(month)) {
					gl.setGlBalCredit3(glRoll.getGlBalCredit3());
					gl.setGlBalDebit3(glRoll.getGlBalDebit3());
					gl.setGlBalCIdr3(glRoll.getGlBalCIdr3());
					gl.setGlBalDIdr3(glRoll.getGlBalDIdr3());
				}else if("4".equals(month)) {
					gl.setGlBalCredit4(glRoll.getGlBalCredit4());
					gl.setGlBalDebit4(glRoll.getGlBalDebit4());
					gl.setGlBalCIdr4(glRoll.getGlBalCIdr4());
					gl.setGlBalDIdr4(glRoll.getGlBalDIdr4());
				}else if("5".equals(month)) {
					gl.setGlBalCredit5(glRoll.getGlBalCredit5());
					gl.setGlBalDebit5(glRoll.getGlBalDebit5());
					gl.setGlBalCIdr5(glRoll.getGlBalCIdr5());
					gl.setGlBalDIdr5(glRoll.getGlBalDIdr5());
				}else if("6".equals(month)) {
					gl.setGlBalCredit6(glRoll.getGlBalCredit6());
					gl.setGlBalDebit6(glRoll.getGlBalDebit6());
					gl.setGlBalCIdr6(glRoll.getGlBalCIdr6());
					gl.setGlBalDIdr6(glRoll.getGlBalDIdr6());
				}else if("7".equals(month)) {
					gl.setGlBalCredit7(glRoll.getGlBalCredit7());
					gl.setGlBalDebit7(glRoll.getGlBalDebit7());
					gl.setGlBalCIdr7(glRoll.getGlBalCIdr7());
					gl.setGlBalDIdr7(glRoll.getGlBalDIdr7());
				}else if("8".equals(month)) {
					gl.setGlBalCredit8(glRoll.getGlBalCredit8());
					gl.setGlBalDebit8(glRoll.getGlBalDebit8());
					gl.setGlBalCIdr8(glRoll.getGlBalCIdr8());
					gl.setGlBalDIdr8(glRoll.getGlBalDIdr8());
				}else if("9".equals(month)) {
					gl.setGlBalCredit9(glRoll.getGlBalCredit9());
					gl.setGlBalDebit9(glRoll.getGlBalDebit9());
					gl.setGlBalCIdr9(glRoll.getGlBalCIdr9());
					gl.setGlBalDIdr9(glRoll.getGlBalDIdr9());
				}else if("10".equals(month)) {
					gl.setGlBalCredit10(glRoll.getGlBalCredit10());
					gl.setGlBalDebit10(glRoll.getGlBalDebit10());
					gl.setGlBalCIdr10(glRoll.getGlBalCIdr10());
					gl.setGlBalDIdr10(glRoll.getGlBalDIdr10());
				}else if("11".equals(month)) {
					gl.setGlBalCredit11(glRoll.getGlBalCredit11());
					gl.setGlBalDebit11(glRoll.getGlBalDebit11());
					gl.setGlBalCIdr11(glRoll.getGlBalCIdr11());
					gl.setGlBalDIdr11(glRoll.getGlBalDIdr11());
				}else if("12".equals(month)) {
					gl.setGlBalCredit12(glRoll.getGlBalCredit12());
					gl.setGlBalDebit12(glRoll.getGlBalDebit12());
					gl.setGlBalCIdr12(glRoll.getGlBalCIdr12());
					gl.setGlBalDIdr12(glRoll.getGlBalDIdr12());
				}
				
				update.add(gl);
			}
			
			if(update.size() > 0)
				gl0001Repo.saveAll(update);
		}
		
		logger.info("end calculateRollUpCoa().");
	}
	
	private void updateDataEom(Map<String, Object> p_Params) {
		logger.info("do updateDataEom().");
		String month = Param.getStr(p_Params, MONTH);
		String year = Param.getStr(p_Params, YEAR);
		String user = Param.getStr(p_Params, Param.USER);
		
		if("1".equals(month))
			gl0001Repo.updateEomJanuary(year, user);
		else if("2".equals(month))
			gl0001Repo.updateEomFebruary(year, user);
		else if("3".equals(month))
			gl0001Repo.updateEomMarch(year, user);
		else if("4".equals(month))
			gl0001Repo.updateEomApril(year, user);
		else if("5".equals(month))
			gl0001Repo.updateEomMay(year, user);
		else if("6".equals(month))
			gl0001Repo.updateEomJune(year, user);
		else if("7".equals(month))
			gl0001Repo.updateEomJuly(year, user);
		else if("8".equals(month))
			gl0001Repo.updateEomAugust(year, user);
		else if("9".equals(month))
			gl0001Repo.updateEomSeptember(year, user);
		else if("10".equals(month))
			gl0001Repo.updateEomOctober(year, user);
		else if("11".equals(month))
			gl0001Repo.updateEomNovember(year, user);
		else if("12".equals(month))
			gl0001Repo.updateEomDecember(year, user);
		
		logger.info("end updateDataEom().");
	}

	@Override
	@Transactional
	public void doCancelEom(Map<String, Object> p_Params) {
		logger.info("do doCancelEom().");
		String month = p_Params.get("month").toString();
		String year = p_Params.get("year").toString();
		String user = p_Params.get(Param.USER).toString();
		String tenant = p_Params.get("tenant").toString();
		
		initBalanceEom(p_Params);
		
		ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
		pe.setProgress("50");
		progressComponent.getDataProgress().put(tenant, pe);
		
		tr0001Repo.updateCancelEom(new Byte(month), new Short(year), user);
		pe = progressComponent.getDataProgress().get(tenant);
		pe.setProgress("75");
		progressComponent.getDataProgress().put(tenant, pe);
		
		reverseTrEom(p_Params);
		
		insertUpdateTR0005("0", month, year, "8", user);
		pe = progressComponent.getDataProgress().get(tenant);
		pe.setProgress("100");
		progressComponent.getDataProgress().put(tenant, pe);
		
		logger.info("end doCancelEom().");
	}

	@Override
	public void initProcess(Map<String, Object> p_Params) {
		String tenant = p_Params.get("tenant").toString();
		String user = p_Params.get("user").toString();
		String process = p_Params.get("process").toString();
		ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
		if(pe.getFlag().equals("0")) {
			pe.setFlag("1");
			pe.setProgress("20");
			pe.setProsesedBy(user);
			pe.setProcess(process);
			progressComponent.getDataProgress().put(tenant, pe);
			Map<String, Object> canBeprocessedObject = canBeprocessed(p_Params);
			if((Boolean) canBeprocessedObject.get("result")) {
				Thread t = new Thread(new Runnable() {
					@Override
					public void run() {
						if(process.equals("eom")) {
							List<TrHelper> listTrHelper = checkDataEom(p_Params);
							if(listTrHelper.isEmpty()) {
								doProcess(p_Params);
							}else {
								ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
								pe.setProgress("100");
								pe.setIsFailed(true);
								pe.setReason("Debit and credit for this data is not 0 "
										+ listTrHelper);
								pe.setErrorData(listTrHelper);
							}
						}else if(process.equals("ceom")) {
							try {
								doCancelEom(p_Params);
							} catch (Exception e) {
								e.printStackTrace();
								ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
								pe.setIsFailed(Boolean.TRUE);
								pe.setReason(e.getMessage());
								progressComponent.getDataProgress().put(tenant, pe);
							}
							
						}else {
							try {
								eoyService.processEoy(p_Params);
							} catch (Exception e) {
								e.printStackTrace();
								ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
								pe.setIsFailed(Boolean.TRUE);
								pe.setReason(e.getMessage());
								progressComponent.getDataProgress().put(tenant, pe);
							}
						}
							
					}
				});
				t.start();
			}else {
				pe = progressComponent.getDataProgress().get(tenant);
				pe.setProgress("100");
				pe.setIsFailed(Boolean.TRUE);
				pe.setReason(canBeprocessedObject.get("reason").toString());
				progressComponent.getDataProgress().put(tenant, pe);
			}
		}
	}

	@Override
	public ProgressEntity getProgress(Map<String, Object> p_Param) {
		String tenant = p_Param.get("tenant").toString();
		return progressComponent.getDataProgress().get(tenant);
	}

	@Override
	public void resetProgress(Map<String, Object> p_Param) {
		String tenant = p_Param.get("tenant").toString();
		ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
		pe.setFlag("0");
		pe.setProgress("0");
		pe.setIsFailed(false);
		pe.setProcess(null);
		pe.setProcessedBy(null);
		pe.setReason(null);
		pe.setErrorData(null);
		progressComponent.getDataProgress().put(tenant, pe);
	}

	@Override
	public Object isProcessStillRunning(Map<String, Object> p_Param) {
		String tenant = p_Param.get("tenant").toString();
		ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
		Map<String, Object> result = new HashMap<String, Object>();
				
		if(pe.getFlag().equals("1")) {
			result.put("isProcessStilRunning", true);
			result.put("message", "There Process "+pe.getProcess()+" still running");
		}else {
			result.put("isProcessStilRunning", false);
		}
		return result;
	}
	
	private Map<String, Object> canBeprocessed(Map<String, Object> p_Params) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("result", true);
		String process = p_Params.get("process").toString();
		if(process.equals("eom") || process.equals("ceom")) {
			Map<String, String> lastProMonthAndProYear = commonService.getLastProMonthAndProYear();
			String proYear = lastProMonthAndProYear.get("proYear").toString();
			String proMonth = lastProMonthAndProYear.get("proMonth").toString();
			String year = p_Params.get("year").toString();
			String month = p_Params.get("month").toString();
			
			
			Calendar calParamEom = Calendar.getInstance();
			calParamEom.set(Calendar.DAY_OF_MONTH, 1);
			calParamEom.set(Calendar.MONTH, Integer.parseInt(month)-1);
			calParamEom.set(Calendar.YEAR, Integer.parseInt(year));
			calParamEom.set(Calendar.HOUR_OF_DAY, 0);
			calParamEom.set(Calendar.MINUTE, 0);
			calParamEom.set(Calendar.SECOND, 0);
			calParamEom.set(Calendar.MILLISECOND, 0);
			
			Date dateParam = calParamEom.getTime();
			String dateStr = formatDateMMMMyyyy.format(dateParam);
			
			Calendar calProEom = Calendar.getInstance();
			calProEom.set(Calendar.DAY_OF_MONTH, 1);
			calProEom.set(Calendar.MONTH, Integer.parseInt(proMonth)-1);
			calProEom.set(Calendar.YEAR, Integer.parseInt(proYear));
			calProEom.set(Calendar.HOUR_OF_DAY, 0);
			calProEom.set(Calendar.MINUTE, 0);
			calProEom.set(Calendar.SECOND, 0);
			calProEom.set(Calendar.MILLISECOND, 0);
			
			Date dateProEom = calProEom.getTime();
			String proDateStr = formatDateMMMMyyyy.format(dateProEom);
			
			if(process.equals("eom")) {
				calProEom.add(Calendar.MONTH, 1);
				String currentEom = formatDateMMMMyyyy.format(calProEom.getTime());
				
				if(dateParam.equals(dateProEom) || dateParam.before(dateProEom)) {
					result.put("result", false);
					result.put("reason", "EOM cannot be processed because "
							+ "EOM for " + dateStr + " has been processed.");
				}else if(!calProEom.equals(calParamEom)) { //check is current eom is next month from last run
					result.put("result", false);
					result.put("reason", "EOM cannot be processed because "
							+ "EOM for " + currentEom + " unprocessed.");
				}else {
					List<String> exchangeNotExists = ma0015Repo.findExchangeEomNotExists(
							Integer.valueOf(month), year);
					
					if(exchangeNotExists.size() > 0) {
						result.put("result", false);
						result.put("reason", "EOM cannot be processed because "
							+ "Exchange Rate was not found for " + dateStr + ". "
							+ "(" + String.join(",", exchangeNotExists) +")");
					}	
				}
						
			}else if(process.equals("ceom")) {
				if(Integer.parseInt(year) + Integer.parseInt(month) != 
						Integer.parseInt(proYear) + Integer.parseInt(proMonth)) {
					result.put("result", false);
					result.put("reason", "You cannot cancel EOM for "
							+ dateStr + " because last EOM is "
							+ proDateStr + ".");
				}
			}
		}
		
		return result;
	}
	
	private void initBalanceEom(Map<String, Object> p_Params) {
		logger.info("initialize balance EOM...");
		String month = Param.getStr(p_Params, MONTH);
		String year = Param.getStr(p_Params, YEAR);
		String user = Param.getStr(p_Params, Param.USER);
		
		if("1".equals(month))
			gl0001Repo.initBalanceEomJanuary(year, user);
		else if("2".equals(month))
			gl0001Repo.initBalanceEomFebruary(year, user);
		else if("3".equals(month))
			gl0001Repo.initBalanceEomMarch(year, user);
		else if("4".equals(month))
			gl0001Repo.initBalanceEomApril(year, user);
		else if("5".equals(month))
			gl0001Repo.initBalanceEomMay(year, user);
		else if("6".equals(month))
			gl0001Repo.initBalanceEomJune(year, user);
		else if("7".equals(month))
			gl0001Repo.initBalanceEomJuly(year, user);
		else if("8".equals(month))
			gl0001Repo.initBalanceEomAugust(year, user);
		else if("9".equals(month))
			gl0001Repo.initBalanceEomSeptember(year, user);
		else if("10".equals(month))
			gl0001Repo.initBalanceEomOctober(year, user);
		else if("11".equals(month))
			gl0001Repo.initBalanceEomNovember(year, user);
		else if("12".equals(month))
			gl0001Repo.initBalanceEomDecember(year, user);
	}
	
	private void insertTrEom(Map<String, Object> p_Params) {
		logger.info("do insertTrEom()");
		String month = Param.getStr(p_Params, MONTH);
		String year = Param.getStr(p_Params, YEAR);
		String user = Param.getStr(p_Params, Param.USER);
		BigDecimal different = Param.getBdWithDef(p_Params, "different");
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, Integer.valueOf(month)-1);
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		String desc = "Unrealised P/L Forex " + month + "/" + year;
		String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(cal.getTime()));
		
		TR0001Entity tr1 = new TR0001Entity();
		tr1.setGlDataStatus("11");
		tr1.setGlTrxClass("OP");
		tr1.setGlTrxDate(cal.getTime());
		tr1.setGlTrxDesc(desc);
		tr1.setGlTrxDue(cal.getTime());
		tr1.setGlTrxMonth(new Byte(month));
		tr1.setGlTrxOfficeId("0");
		tr1.setGlTrxProject("0");
		tr1.setGlTrxStatus("1");
		tr1.setGlTrxValueIdr(different.abs());
		tr1.setGlTrxValueOrg(different.abs());
		tr1.setGlTrxYear(new Short(year));
		tr1.setGlType("CM");
		tr1.setGlVoucherId(voucherId);
		tr1.setCreateBy(user);
		tr1.setCreateOn(now);
		tr1.setModifyBy(user);
		tr1.setModifyOn(now);
		
		tr0001Repo.save(tr1);
		
		MA0004Entity coaCodeEoy = ma0004Repo.findAccountEoy(CODE.COAEOY007);
		MA0015Entity ma0015 = ma0015Repo.findByCoaCodeAndExMonthAndExYear(
				coaCodeEoy.getCoaCode(), Integer.valueOf(month), Integer.valueOf(year));
		
		TR0002Entity tr2 = new TR0002Entity();
		tr2.setGlAccount(coaCodeEoy.getCoaCode());
		tr2.setGlCurrId(coaCodeEoy.getCoaCurrId());
		tr2.setGlCurrRate(ma0015.getExRateEom());
		
		if(different.compareTo(BigDecimal.ZERO) < 0) {
			tr2.setGlOrgDebit(different.abs());
			tr2.setGlIdrDebit(different.abs());
			tr2.setGlIdrCredit(BigDecimal.ZERO);
			tr2.setGlOrgCredit(BigDecimal.ZERO);
		}else {
			tr2.setGlIdrCredit(different.abs());
			tr2.setGlOrgCredit(different.abs());
			tr2.setGlOrgDebit(BigDecimal.ZERO);
			tr2.setGlIdrDebit(BigDecimal.ZERO);
		}
		
		tr2.setGlTrxClass("OP");
		tr2.setGlType("CM");
		tr2.setGlVoucherId(voucherId);
		
		tr0002Repo.save(tr2);
		
		logger.info("end insertTrEom()");
	}
	
	private void finalizeEom(Map<String, Object> p_Params) {
		logger.info("do finalizeEom()");
		String month = Param.getStr(p_Params, MONTH);
		String year = Param.getStr(p_Params, YEAR);
		String user = Param.getStr(p_Params, Param.USER);
		
		tr0001Repo.updateSuccessEom(new Byte(month), new Short(year), user);
		insertUpdateTR0005("0", month, year, "6", user);
		
		logger.info("end finalizeEom()");
	}
	
	private String reverseTrEom(Map<String, Object> p_Params) {
		logger.info("do reverseTrEom()");
		String month = p_Params.get("month").toString();
		String year = p_Params.get("year").toString();
		String user = p_Params.get(Param.USER).toString();
		
		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();
		
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.MONTH, Integer.valueOf(month)-1);
		cal.set(Calendar.YEAR, Integer.valueOf(year));
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		String desc = "Unrealised P/L Forex " + month + "/" + year;
		
		String prevVoucherId = tr0001Repo.findVoucherId("OP", "CM", new Byte(month),
				new Short(year), desc);
		
		if(prevVoucherId == null) {
			logger.warn("previous voucherId doesn't exsist");
			return null;
		}
		
		String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(cal.getTime()));
		
		TR0001Entity prevTr1 = tr0001Repo.findTR0001("OP", "CM", new Byte(month),
				new Short(year), desc, prevVoucherId).get(0);
		
		TR0001Entity tr1 = new TR0001Entity();
		tr1.setGlDataStatus("11");
		tr1.setGlTrxClass("OP");
		tr1.setGlTrxDate(cal.getTime());
		tr1.setGlTrxDesc("CANCEL - " + desc);
		tr1.setGlTrxDue(cal.getTime());
		tr1.setGlTrxMonth(new Byte(month));
		tr1.setGlTrxOfficeId("0");
		tr1.setGlTrxProject("0");
		tr1.setGlTrxStatus("1");
		tr1.setGlTrxValueIdr(prevTr1.getGlTrxValueIdr());
		tr1.setGlTrxValueOrg(prevTr1.getGlTrxValueOrg());
		tr1.setGlTrxYear(new Short(year));
		tr1.setGlType("CM");
		tr1.setGlVoucherId(voucherId);
		tr1.setCreateBy(user);
		tr1.setCreateOn(now);
		tr1.setModifyBy(user);
		tr1.setModifyOn(now);
		
		tr0001Repo.save(tr1);
		
		List<TR0002Entity> prevTr2List = tr0002Repo.findTR0002("OP", "CM", prevVoucherId);
		
		List<TR0002Entity> reverse = new ArrayList<TR0002Entity>();
		for(TR0002Entity prevTr2 : prevTr2List) {
			TR0002Entity tr2 = new TR0002Entity();
			tr2.setGlAccount(prevTr2.getGlAccount());
			tr2.setGlCurrId(prevTr2.getGlCurrId());
			tr2.setGlCurrRate(prevTr2.getGlCurrRate());
			tr2.setGlOrgDebit(prevTr2.getGlOrgCredit());
			tr2.setGlOrgCredit(prevTr2.getGlOrgDebit());
			tr2.setGlIdrDebit(prevTr2.getGlIdrCredit());
			tr2.setGlIdrCredit(prevTr2.getGlIdrDebit());
			tr2.setGlTrxClass("OP");
			tr2.setGlType("CM");
			tr2.setGlVoucherId(voucherId);
			
			reverse.add(tr2);
		}
		
		if(reverse.size() > 0)
			tr0002Repo.saveAll(reverse);
		
		logger.info("end reverseTrEom()");
		
		return prevVoucherId;
	}
	
	public void insertUpdateTR0005(String p_ProId, String p_ProMonth,  String p_ProYear, 
			String p_ProStatus, String p_User) {
		TR0005Entity tr5 = tr0005Repo
				.findByProIdAndProMonthAndProYear(p_ProId, new Short(p_ProMonth), Integer.valueOf(p_ProYear));
		
		Date now = Calendar.getInstance().getTime();
		if(tr5 != null) {
			tr5.setProStatus(p_ProStatus);
		}else {
			tr5 = new TR0005Entity();
			tr5.setIdKey("0");
			tr5.setProId(p_ProId);
			tr5.setProMonth(new Short(p_ProMonth));
			tr5.setProStatus(p_ProStatus);
			tr5.setProYear(Integer.valueOf(p_ProYear));
			tr5.setCreateBy(p_User);
			tr5.setCreateOn(now);
		}
		
		tr5.setModifyBy(p_User);
		tr5.setModifyOn(now);
		
		tr0005Repo.save(tr5);
	}
	
	private String updateAppDate(Map<String, Object> p_Params) throws ParseException {
		String year = Param.getStr(p_Params, "year");
		String month = Param.getStr(p_Params, "month");
		
		Calendar calEom = Calendar.getInstance();
		calEom.set(Calendar.DAY_OF_MONTH, 1);
		calEom.set(Calendar.MONTH, Integer.parseInt(month)-1);
		calEom.set(Calendar.YEAR, Integer.parseInt(year));
		calEom.set(Calendar.HOUR_OF_DAY, 0);
		calEom.set(Calendar.MINUTE, 0);
		calEom.set(Calendar.SECOND, 0);
		calEom.set(Calendar.MILLISECOND, 0);
		
		//set last date eom
		calEom.set(Calendar.DAY_OF_MONTH, calEom.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		MA0014Entity param = ma0014Repo.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);
		String appDate = param.getPaChildValue();
		
		param.setPaChildValue(formatDateDb.format(calEom.getTime()));
		ma0014Repo.save(param);
		
		return appDate;
	}
	
	private void revertAppDate(String appDate) {
		MA0014Entity param = ma0014Repo.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);
		param.setPaChildValue(appDate);
		
		ma0014Repo.save(param);
	}
	
}
