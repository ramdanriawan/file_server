package com.biru.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.biru.component.ProgressComponent;
import com.biru.component.VoucherComponent;
import com.biru.entity.GL0001Entity;
import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.ProgressEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0005Entity;
import com.biru.repository.GL0001Repo;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0015Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0005Repo;
import com.biru.service.EoyService;

@Service
public class EoyServiceImpl implements EoyService{
	private Logger logger = LoggerFactory.getLogger(EoyServiceImpl.class);

	@Autowired
	private ProgressComponent progressComponent;

	@Autowired
	private TR0005Repo tR0005Repo;

	@Autowired
	private GL0001Repo gL0001Repo;

	@Autowired
	private MA0004Repo mA0004Repo;

	@Autowired
	private MA0014Repo mA0014Repo;

	@Autowired
	private MA0015Repo mA0015Repo;

	@Autowired
	private TR0001Repo tR0001Repo;

	@Autowired
	private TR0002Repo tR0002Repo;

	@Autowired
	private VoucherComponent voucherComponent;



	@Transactional
	@Override
	public void processEoy(Map<String, Object> param) throws Exception {

		logger.info("start processEoy, param : "+param);

		String tenant = param.get("tenant").toString();
		String process = param.get("process").toString();
		try {
			if (checkYearIsValid(param)) {
				if(process.equals("eoy")) {
					if(checkEom12(param)){
						ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
						pe.setProgress("30");
						progressComponent.getDataProgress().put(tenant, pe);

						copyCoaGL(param);
						pe = progressComponent.getDataProgress().get(tenant);
						pe.setProgress("45");
						progressComponent.getDataProgress().put(tenant, pe);

						updateGL0001COAEOY003(param);
						pe = progressComponent.getDataProgress().get(tenant);
						pe.setProgress("60");
						progressComponent.getDataProgress().put(tenant, pe);

						if(param.get("isTerminated") == null) {
							roleUp(param);
							pe = progressComponent.getDataProgress().get(tenant);
							pe.setProgress("75");
							progressComponent.getDataProgress().put(tenant, pe);
						
							insertTr0005(param);
							pe = progressComponent.getDataProgress().get(tenant);
							pe.setProgress("90");
							progressComponent.getDataProgress().put(tenant, pe);
							
							insertLastYearJournal(param);
							pe = progressComponent.getDataProgress().get(tenant);
							pe.setProgress("100");
							progressComponent.getDataProgress().put(tenant, pe);
						}

					}

				}else {
					deleteGL0001(param);
					ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
					pe.setProgress("50");
					progressComponent.getDataProgress().put(tenant, pe);

					updateTr0005(param);
					pe = progressComponent.getDataProgress().get(tenant);
					pe.setProgress("75");
					progressComponent.getDataProgress().put(tenant, pe);

					insertCancelLastYearJournal(param);
					pe = progressComponent.getDataProgress().get(tenant);
					pe.setProgress("100");
					progressComponent.getDataProgress().put(tenant, pe);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
			pe.setIsFailed(Boolean.TRUE);;
			pe.setReason(e.getMessage());
			progressComponent.getDataProgress().put(tenant, pe);
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
		}

		logger.info("finish processEoy, param : "+param);
	}

	@Override
	public Boolean checkYearIsValid(Map<String, Object> param) {
		logger.info("checkYearIsValid start, param : "+param);
		// TODO Auto-generated method stub
		String process = param.get("process").toString();
		String year = param.get("year").toString();
		String tenant = param.get("tenant").toString();

		ProgressEntity pe = progressComponent.getDataProgress().get(tenant);

		if(process.equals("eoy")) {
			TR0005Entity tR0005Entity = tR0005Repo.findByProIdAndProMonthAndProYear("0", new Short("12"), Integer.parseInt(year));
			if(tR0005Entity == null) {
				pe.setIsFailed(Boolean.TRUE);
				pe.setReason("Cannot run EOY, because EOM for December "+year+" not found");
				logger.info("checkYearIsValid finish, param : "+param+", result : false");
				return false;
			}else if(!tR0005Entity.getProStatus().equals("6")) {
				pe.setIsFailed(Boolean.TRUE);
				pe.setReason("Cannot run EOY, because EOM for December "+year+" has not been process.");
				logger.info("checkYearIsValid finish, param : "+param+", result : false");
				return false;
			}
		}else {
			List<TR0005Entity> listTR0005Entity = tR0005Repo.findByProIdAndProYearAndProStatus("0", Integer.parseInt(year)+1, "6");

			if(!listTR0005Entity.isEmpty()) {
				List<String> listMonths = new ArrayList<String>();
				for (TR0005Entity tr0005Entity : listTR0005Entity) {
					if(tr0005Entity.getProMonth() == Short.parseShort("1")) {
						listMonths.add("January");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("2")) {
						listMonths.add("February");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("3")) {
						listMonths.add("March");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("4")) {
						listMonths.add("April");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("5")) {
						listMonths.add("May");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("6")) {
						listMonths.add("June");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("7")) {
						listMonths.add("July");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("8")) {
						listMonths.add("August");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("9")) {
						listMonths.add("September");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("10")) {
						listMonths.add("October");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("11")) {
						listMonths.add("November");
					}else if(tr0005Entity.getProMonth() == Short.parseShort("12")) {
						listMonths.add("December");
					}
				}
				String months = listMonths.toString().replace("[", "").replace("]", "");

				pe.setIsFailed(Boolean.TRUE);
				pe.setReason("Cannot cancel EOY for year "+year+", because EOM for "+months+" "+(Integer.parseInt(year)+1)+" has been processed");

				logger.info("checkYearIsValid finish, param : "+param+", result : false");
				return false;
			}
		}
		logger.info("checkYearIsValid finish, param : "+param+" result : true");
		return true;
	}

	@Override
	public Boolean checkEom12(Map<String, Object> param) {
		// TODO Auto-generated method stub
		logger.info("checkEom12 start, param : "+param);

		String year = param.get("year").toString();
		String tenant = param.get("tenant").toString();
		TR0005Entity tR0005Entity = tR0005Repo.findByProMonthAndProYearAndProStatusAndProId(Short.parseShort("12"), Integer.parseInt(year), "6", "0");
		if(tR0005Entity == null) {
			ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
			pe.setIsFailed(Boolean.TRUE);
			pe.setReason("Cannot run EOY, because EOM for December "+year+" has not been processed");

			logger.info("checkYearIsValid finish, param : "+param+" result : false");
			return false;
		}

		logger.info("checkEom12 finish, param : "+param+" result : true");
		return true;
	}

	@Override
	public void copyCoaGL(Map<String, Object> param) {
		// TODO Auto-generated method stub
		logger.info("copyCoaGL start param : "+param);

		int offset = 0;
		int limit = 100;
		String year = param.get("year").toString();
		String user = param.get("user").toString();

		Calendar c = Calendar.getInstance();
		Date now = c.getTime();

		Long countData = gL0001Repo.countByGlBalYear(year);



		List<String> coaClassDescriptions = new ArrayList<String>();
		Collections.addAll(coaClassDescriptions, "Assets", "Liability", "Equity");

		List<MA0014Entity> listCoaClass = mA0014Repo.findByPaParentCodeAndPaChildDescIn("COACLASS", coaClassDescriptions);
		List<String> coaClass = new ArrayList<String>();
		for (MA0014Entity ma0014Entity : listCoaClass) {
			coaClass.add(ma0014Entity.getPaChildValue());
		}


		while(countData > (offset/limit)*limit) {
			System.out.println("countData = "+countData);
			System.out.println("(offset/limit)*limit = "+(offset/limit)*limit);
			System.out.println("countData >= (offset/limit)*limit = "+(countData >= (offset/limit)*limit));
			Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString("asc"), "coaCode");
			List<GL0001Entity> listGL0001Entity = gL0001Repo.findByGlBalYear(year, pageable).getContent();
			List<GL0001Entity> newlistGL0001Entity = new ArrayList<GL0001Entity>();

			List<String> listCoaCode = new ArrayList<String>();
			for (GL0001Entity gl0001Entity : listGL0001Entity) {
				listCoaCode.add(gl0001Entity.getCoaCode());
			}

			List<MA0004Entity> listMA0004Entity = mA0004Repo
					.findByCoaCodeInAndCoaClassInAndCoaHeaderEquals(listCoaCode,coaClass, 'D');
			Map<String, MA0004Entity> mapCoa = new HashMap<String, MA0004Entity>();

			for (MA0004Entity ma0004Entity : listMA0004Entity) {
				mapCoa.put(ma0004Entity.getCoaCode(), ma0004Entity);
			}

			for (GL0001Entity gl0001Entity : listGL0001Entity) {
				if(gl0001Entity.getCoaCode().equals("300007"))
					System.out.println("ketemu 300007");

				GL0001Entity newGL0001Entity = new GL0001Entity();
				newGL0001Entity.setCoaCode(gl0001Entity.getCoaCode());
				newGL0001Entity.setGlBalYear(String.valueOf(Integer.parseInt(year)+1));
				if(mapCoa.get(gl0001Entity.getCoaCode()) != null){
					newGL0001Entity.setGlBalDebit0(gl0001Entity.getGlBalDebit12());
					newGL0001Entity.setGlBalCredit0(gl0001Entity.getGlBalCredit12());
					newGL0001Entity.setGlBalDIdr0(gl0001Entity.getGlBalDIdr12());
					newGL0001Entity.setGlBalCIdr0(gl0001Entity.getGlBalCIdr12());
				}
				newGL0001Entity.setCreateBy(user);
				newGL0001Entity.setCreateOn(now);

				newlistGL0001Entity.add(newGL0001Entity);


				gl0001Entity.setGlBalDebit13(BigDecimal.ZERO);
				gl0001Entity.setGlBalCredit13(BigDecimal.ZERO);
				gl0001Entity.setGlBalCIdr13(BigDecimal.ZERO);
				gl0001Entity.setGlBalDIdr13(BigDecimal.ZERO);
			}

			gL0001Repo.saveAll(listGL0001Entity);
			gL0001Repo.saveAll(newlistGL0001Entity);
			offset = offset+limit;
		}
		logger.info("copyCoaGL finish param : "+param);
	}

	@Override
	public void updateGL0001COAEOY003(Map<String, Object> param) throws Exception {
		logger.info("updateGL0001COAEOY003 start, param : "+param);

		// TODO Auto-generated method stub
		String year = param.get("year").toString();
		String tenant = param.get("tenant").toString();
		
		String COAEOY003 = mA0014Repo.findByPaChildCode("COAEOY003").getPaChildValue();

		BigDecimal selisih = hitungSelisih(param);

		GL0001Entity newGlCOAEOY003 = gL0001Repo.findByCoaCodeAndGlBalYear(COAEOY003, String.valueOf(Integer.parseInt(year)+1));
		if(selisih.compareTo(BigDecimal.ZERO) < 0) {
			newGlCOAEOY003.setGlBalDIdr0(newGlCOAEOY003.getGlBalDIdr0().add(selisih.abs()));
			newGlCOAEOY003.setGlBalDebit0(newGlCOAEOY003.getGlBalDIdr0());
		}else if(selisih.compareTo(BigDecimal.ZERO) > 0) {
			newGlCOAEOY003.setGlBalCIdr0(newGlCOAEOY003.getGlBalCIdr0().add(selisih.abs()));
			newGlCOAEOY003.setGlBalCredit0(newGlCOAEOY003.getGlBalCIdr0());
		}

		gL0001Repo.save(newGlCOAEOY003);
		Boolean isDIdrCIdrBalance = gL0001Repo.comperDIdrCIdrEoy(String.valueOf(Integer.parseInt(year)+1));

		if(!isDIdrCIdrBalance) {
			ProgressEntity pe = progressComponent.getDataProgress().get(tenant);
			pe.setIsFailed(Boolean.TRUE);
			pe.setReason("Sum of DIDR0 and CIDR0 for year "+(Integer.parseInt(year)+1)+" is not balance. Please check the data first!");
			param.put("isTerminated", true);
			Exception e = new Exception(pe.getReason());
			logger.info("updateGL0001COAEOY003 finish, param : "+param+", exception : "+e.getMessage());
			throw e;
		}else {
			param.put("selisih", selisih);
		}

		logger.info("updateGL0001COAEOY003 finish, param : "+param);
	}

	@Override
	public void roleUp(Map<String, Object> param) {
		// TODO Auto-generated method stub
		logger.info("roleUp start, param : "+param);

		String year = param.get("year").toString();
		gL0001Repo.updateCoaH0ForEoy(String.valueOf(Integer.parseInt(year)+1));
		List<MA0004Entity> listMA0004EntityD = mA0004Repo.findByCoaHeaderAndCoaDataStatus('D', "11");
		for (MA0004Entity ma0004Entity : listMA0004EntityD) {
			GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(ma0004Entity.getCoaRoleUp(), String.valueOf(Integer.parseInt(year)+1));
			if(ma0004Entity.getCoaRoleUp() != null && gL0001Entity != null) {
				if(!ma0004Entity.getCoaRoleUp().equals("")) {
					GL0001Entity gL0001EntityRollUp = gL0001Repo.findByCoaCodeAndGlBalYear(ma0004Entity.getCoaRoleUp(), String.valueOf(Integer.parseInt(year)+1));
					gL0001EntityRollUp.setGlBalDebit0(gL0001EntityRollUp.getGlBalDebit0().add(gL0001Entity.getGlBalDebit0()));
					gL0001EntityRollUp.setGlBalCredit0(gL0001EntityRollUp.getGlBalCredit0().add(gL0001Entity.getGlBalCredit0()));
					gL0001EntityRollUp.setGlBalDIdr0(gL0001EntityRollUp.getGlBalDIdr0().add(gL0001Entity.getGlBalDIdr0()));
					gL0001EntityRollUp.setGlBalCIdr0(gL0001EntityRollUp.getGlBalCIdr0().add(gL0001Entity.getGlBalCIdr0()));
					gL0001Repo.save(gL0001EntityRollUp);
				}
			}
		}
		List<MA0004Entity> listMA0004EntityH = mA0004Repo.findByCoaHeaderAndCoaDataStatus('H', "11");
		for (MA0004Entity ma0004Entity : listMA0004EntityH) {
			GL0001Entity gL0001Entity = gL0001Repo.findByCoaCodeAndGlBalYear(ma0004Entity.getCoaRoleUp(), String.valueOf(Integer.parseInt(year)+1));
			if(ma0004Entity.getCoaRoleUp() != null && gL0001Entity != null) {
				if(!ma0004Entity.getCoaRoleUp().equals("")) {
					GL0001Entity gL0001EntityRollUp = gL0001Repo.findByCoaCodeAndGlBalYear(ma0004Entity.getCoaRoleUp(), String.valueOf(Integer.parseInt(year)+1));
					gL0001EntityRollUp.setGlBalDebit0(gL0001EntityRollUp.getGlBalDebit0().add(gL0001Entity.getGlBalDebit0()));
					gL0001EntityRollUp.setGlBalCredit0(gL0001EntityRollUp.getGlBalCredit0().add(gL0001Entity.getGlBalCredit0()));
					gL0001EntityRollUp.setGlBalDIdr0(gL0001EntityRollUp.getGlBalDIdr0().add(gL0001Entity.getGlBalDIdr0()));
					gL0001EntityRollUp.setGlBalCIdr0(gL0001EntityRollUp.getGlBalCIdr0().add(gL0001Entity.getGlBalCIdr0()));
					gL0001Repo.save(gL0001EntityRollUp);
				}
			}
		}

		logger.info("roleUp finish, param : "+param);
	}

	@Override
	public void insertTr0005(Map<String, Object> param) {
		// TODO Auto-generated method stub
		logger.info("insertTr0005 start, param : "+param);

		String year = param.get("year").toString();
		String user = param.get("user").toString();
		Calendar cal = Calendar.getInstance();

		TR0005Entity tR0005Entity = tR0005Repo.findByProIdAndProYear("1", Integer.parseInt(year));
		if(tR0005Entity == null) {
			tR0005Entity = new TR0005Entity();
			tR0005Entity.setProId("1");
			tR0005Entity.setProMonth(new Short("12"));
			tR0005Entity.setProYear(Integer.parseInt(year));
			tR0005Entity.setCreateBy(user);
			tR0005Entity.setCreateOn(cal.getTime());
		}
		tR0005Entity.setProStatus("6");
		tR0005Entity.setModifyBy(user);
		tR0005Entity.setModifyOn(cal.getTime());	


		tR0005Repo.save(tR0005Entity);
		logger.info("insertTr0005 finish, param : "+param);
	}

	@Override
	public void insertLastYearJournal(Map<String, Object> param) {
		// TODO Auto-generated method stub
		logger.info("insertLastYearJournal start, param : "+param);

		String year = param.get("year").toString();
		String user = param.get("user").toString();

		Calendar c = Calendar.getInstance();
		Date now = c.getTime();

		Calendar c2 = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 31);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.YEAR, Integer.parseInt(year));
		Date trxDate = c2.getTime();

		String voucherId = voucherComponent.getVoucherId("31-12-"+Integer.parseInt(year));

		TR0001Entity tR0001Entity = new TR0001Entity();
		tR0001Entity.setCreateBy(user);
		tR0001Entity.setCreateOn(now);
		tR0001Entity.setGlDataStatus("11");
		tR0001Entity.setGlTrxClass("OP");
		tR0001Entity.setGlTrxDate(trxDate);
		tR0001Entity.setGlTrxDue(trxDate);
		tR0001Entity.setGlTrxMonth(new Byte("12"));
		tR0001Entity.setGlTrxOfficeId("0");
		tR0001Entity.setGlTrxProject("0000");
		tR0001Entity.setGlTrxStatus("0");
		tR0001Entity.setGlTrxValueIdr(((BigDecimal) param.get("selisih")).abs());
		tR0001Entity.setGlTrxValueOrg(((BigDecimal) param.get("selisih")).abs());
		tR0001Entity.setGlTrxYear(new Short(year));
		tR0001Entity.setGlType("CY");
		tR0001Entity.setGlVoucherId(voucherId);
		tR0001Entity.setModifyBy(user);
		tR0001Entity.setModifyOn(now);
		tR0001Entity.setGlTrxDesc("RE "+year);

		tR0001Repo.save(tR0001Entity);

		String currId = mA0004Repo.findByCoaCode(mA0014Repo.findByPaChildCode("COAEOY006").getPaChildValue()).getCoaCurrId();
		BigDecimal currRate = mA0015Repo.findByExMonthAndExYearAndExCurrId(12, Integer.parseInt(year), currId).getExRateValue();

		TR0002Entity tR0002Entity = new TR0002Entity();
		tR0002Entity.setGlAccount(mA0014Repo.findByPaChildCode("COAEOY003").getPaChildValue());
		tR0002Entity.setGlCurrId(currId);
		tR0002Entity.setGlCurrRate(currRate);

		if(((BigDecimal)param.get("selisih")).compareTo(BigDecimal.ZERO) < 0) {
			tR0002Entity.setGlIdrDebit(((BigDecimal)param.get("selisih")).abs());
			tR0002Entity.setGlOrgDebit(((BigDecimal)param.get("selisih")).abs());
			tR0002Entity.setGlIdrCredit(BigDecimal.ZERO);
			tR0002Entity.setGlOrgCredit(BigDecimal.ZERO);
		}else if(((BigDecimal)param.get("selisih")).compareTo(BigDecimal.ZERO) > 0) {
			tR0002Entity.setGlIdrDebit(BigDecimal.ZERO);
			tR0002Entity.setGlOrgDebit(BigDecimal.ZERO);
			tR0002Entity.setGlIdrCredit(((BigDecimal)param.get("selisih")).abs());
			tR0002Entity.setGlOrgCredit(((BigDecimal)param.get("selisih")).abs());
		}


		tR0002Entity.setGlTrxClass("OP");
		tR0002Entity.setGlType("CY");
		tR0002Entity.setGlVoucherId(voucherId);

		tR0002Repo.save(tR0002Entity);

		voucherComponent.saveVoucherCounter("31-12-"+Integer.parseInt(year));

		logger.info("insertLastYearJournal finish, param : "+param);
	}

	@Override
	public void deleteGL0001(Map<String, Object> param) {
		// TODO Auto-generated method stub
		logger.info("deleteGL0001 start, param : "+param);
		String year = param.get("year").toString();

		gL0001Repo.deleteGl001Eoy(String.valueOf(Integer.parseInt(year)+1));

		logger.info("deleteGL0001 finish, param : "+param);
	}

	@Override
	public void updateTr0005(Map<String, Object> param) {
		// TODO Auto-generated method stub
		logger.info("updateTr0005 start, param : "+param);
		String year = param.get("year").toString();
		String user = param.get("user").toString();

		Calendar c = Calendar.getInstance();
		Date now = c.getTime();
		TR0005Entity tR0005Entity = tR0005Repo.findByProIdAndProYear("1", Integer.parseInt(year));
		if(tR0005Entity != null) {
			tR0005Entity.setProStatus("8");
			tR0005Entity.setModifyOn(now);
			tR0005Entity.setModifyBy(user);
			tR0005Repo.save(tR0005Entity);
		}
		logger.info("updateTr0005 finish, param : "+param);
	}

	@Override
	public void insertCancelLastYearJournal(Map<String, Object> param) {
		// TODO Auto-generated method stub
		logger.info("insertCancelLastYearJournal start, param : "+param);

		String year = param.get("year").toString();
		String user = param.get("user").toString();
		BigDecimal selisih = hitungSelisih(param);


		Calendar c = Calendar.getInstance();
		Date now = c.getTime();

		Calendar c2 = Calendar.getInstance();
		c.set(Calendar.DAY_OF_MONTH, 31);
		c.set(Calendar.MONTH, 11);
		c.set(Calendar.YEAR, Integer.parseInt(year));
		Date trxDate = c2.getTime();

		String voucherId = voucherComponent.getVoucherId("31-12-"+year);
		
		TR0001Entity tR0001Entity = new TR0001Entity();
		tR0001Entity.setCreateBy(param.get("user").toString());
		tR0001Entity.setCreateOn(now);
		tR0001Entity.setGlDataStatus("11");
		tR0001Entity.setGlTrxClass("OP");
		tR0001Entity.setGlTrxDate(trxDate);
		tR0001Entity.setGlTrxDue(trxDate);
		tR0001Entity.setGlTrxMonth(new Byte("12"));
		tR0001Entity.setGlTrxOfficeId("0");
		tR0001Entity.setGlTrxProject("0000");
		tR0001Entity.setGlTrxStatus("0");
		tR0001Entity.setGlTrxValueIdr(selisih.abs());
		tR0001Entity.setGlTrxValueOrg(selisih.abs());
		tR0001Entity.setGlTrxYear(new Short(year));
		tR0001Entity.setGlType("CY");
		tR0001Entity.setGlVoucherId(voucherId);
		tR0001Entity.setModifyBy(user);
		tR0001Entity.setModifyOn(now);
		tR0001Entity.setGlTrxDesc("CANCEL RE "+year);

		tR0001Repo.save(tR0001Entity);

		String currId = mA0004Repo.findByCoaCode(mA0014Repo.findByPaChildCode("COAEOY006").getPaChildValue()).getCoaCurrId();
		BigDecimal currRate = mA0015Repo.findByExMonthAndExYearAndExCurrId(12, Integer.parseInt(year), currId).getExRateValue();

		TR0002Entity tR0002Entity = new TR0002Entity();
		tR0002Entity.setGlAccount(mA0014Repo.findByPaChildCode("COAEOY003").getPaChildValue());
		tR0002Entity.setGlCurrId(currId);
		tR0002Entity.setGlCurrRate(currRate);
		if(selisih.compareTo(BigDecimal.ZERO) < 0) {
			tR0002Entity.setGlIdrDebit(BigDecimal.ZERO);
			tR0002Entity.setGlOrgDebit(BigDecimal.ZERO);
			tR0002Entity.setGlIdrCredit(selisih.abs());
			tR0002Entity.setGlOrgCredit(selisih.abs());
		}else if(selisih.compareTo(BigDecimal.ZERO) > 0) {
			tR0002Entity.setGlIdrDebit(selisih);
			tR0002Entity.setGlOrgDebit(selisih);
			tR0002Entity.setGlIdrCredit(BigDecimal.ZERO);
			tR0002Entity.setGlOrgCredit(BigDecimal.ZERO);
		}
		tR0002Entity.setGlTrxClass("OP");
		tR0002Entity.setGlType("CY");
		tR0002Entity.setGlVoucherId(voucherId);

		tR0002Repo.save(tR0002Entity);

		voucherComponent.saveVoucherCounter("31-12-"+year);
		logger.info("insertCancelLastYearJournal finish, param : "+param);
	}

	private BigDecimal hitungSelisih (Map<String, Object> param) {
		String year = param.get("year").toString();
		String COAEOY001 = mA0014Repo.findByPaChildCode("COAEOY001").getPaChildValue();
		String COAEOY002 = mA0014Repo.findByPaChildCode("COAEOY002").getPaChildValue();

		GL0001Entity glCOAEOY001 = gL0001Repo.findByCoaCodeAndGlBalYear(COAEOY001, year);
		GL0001Entity glCOAEOY002 = gL0001Repo.findByCoaCodeAndGlBalYear(COAEOY002, year);

		BigDecimal sumDIdr = glCOAEOY001.getGlBalDIdr12().add(glCOAEOY002.getGlBalDIdr12()); 
		BigDecimal sumCIdr = glCOAEOY001.getGlBalCIdr12().add(glCOAEOY002.getGlBalCIdr12()); 

		return sumCIdr.subtract(sumDIdr);

	}
}
