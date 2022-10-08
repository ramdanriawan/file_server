package com.biru.service.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.VoucherComponent;
import com.biru.entity.ResponseEntity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0007Entity;
import com.biru.entity.TR0012Entity;
import com.biru.entity.ViewInqCancelSet;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0007Repo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewInqCancelSetRepo;
import com.biru.service.CancelSettlementService;

@Service
public class CancelSettlementServiceImpl extends AbstractCommon implements CancelSettlementService {

	@Autowired
	private TR0012Repo tr0012Repo;
	
	@Autowired
	private TR0001Repo tr0001Repo;
	
	@Autowired
	private TR0002Repo tr0002Repo;
	
	@Autowired
	private TR0007Repo tr0007Repo;
	
	@Autowired
	private ViewInqCancelSetRepo viewInqCancelSetRepo;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private CommonServiceImpl commonServiceImpl;
	
	private Logger logger = LoggerFactory.getLogger(CancelSettlementServiceImpl.class);
	
	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) throws ParseException {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		
		if(sort.equals("trxDateStr"))
			sort = "trxDate";
		
		//String filterValue = Param.getStrWithDef(p_Param, PARAM.FILTER_VALUE);
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		
		String type = Param.getStr(p_Param, "type");
		Date settlementDate1 = formatDateId.parse(Param.getStr(p_Param, "transactionDateFrom"));
		Date settlementDate2 = formatDateId.parse(Param.getStr(p_Param, "transactionDateTo"));
		String client = Param.getStrWithDef(p_Param, "clientCode");
		
		Page<ViewInqCancelSet> data = viewInqCancelSetRepo.findCancelSettlement(settlementDate1, settlementDate2, type, client, pageable);
		List<ViewInqCancelSet> listOfData = data.getContent();
		
		for (ViewInqCancelSet cancelSet : listOfData) {
			/*String action = "<button class=\"btn btn-danger\" id=\"btnTrash\" >" 
					+ "<i class=\"fa fa-trash\"></i>" 
					+ "</button>";*/
			
			String action = "<button class=\"btn btn-danger\" onclick=\"t12Edit('"+cancelSet.getTrxVoucherId()+"', '"+cancelSet.getTrxType()+"')\">" 
					+ "<i class=\"fa fa-trash\"></i>" 
					+ "</button>";
			cancelSet.setAction(action);
		}
		
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	@Transactional
	public Object save(Map<String, Object> p_Param) throws ParseException {
		logger.info("Cancel Settlement with param : {}.", p_Param);
		
		Date appDate = commonServiceImpl.getAppDate();
		Date now = Calendar.getInstance().getTime();
		
		/*
		 * Mencari data di TR0012 untuk NO_AWAL = TRX_OLD_VOUCHER_ID ; TIPE_AWAL = TRX_OLD_TYPE
		 * where TRX_VOUCHER_ID = ‘Voucher yang dipilih’ 
		 * and TRX_OLD_TYPE=’’SE’,’PU’,’PO’’.
		 * */
		String p_VoucherId = Param.getStr(p_Param, "trxVoucherId");
		String p_Type = Param.getStr(p_Param, "trxType");
		TR0012Entity tr0012Entity = tr0012Repo.findByTrxVoucherIdAndTrxType(p_VoucherId, p_Type);
		
		List<TR0007Entity> mappingSetOld = tr0007Repo.findByTrxTrxIdAndTrxVoucherId(
				tr0012Entity.getTrxType(), tr0012Entity.getTrxVoucherId());
		
		for(TR0007Entity tr7 : mappingSetOld) {
			TR0012Entity tr12Old = tr0012Repo.findByTrxVoucherIdAndTrxTypeAndTrxCountInv(
					tr7.getTrxOldVoucherId(), tr7.getTrxOldTrxId(), tr7.getTrxOldCountInv());
			
			BigDecimal setAmt = tr12Old.getTrxSetAmount();
			setAmt = setAmt.subtract(tr7.getTrxSetAmount());
			
			tr12Old.setTrxDataStatus("11");
			tr12Old.setTrxSetAmount(setAmt);
			tr12Old.setModifyBy(Param.getStr(p_Param, Param.USER));
			tr12Old.setModifyOn(now);
			tr0012Repo.save(tr12Old);
		}
		
		/*
		 * Update TR0012 set TRX_DATA_STATUS=’13’ 
		 * where TRX_TYPE = TIPE yang dipilih (rc,py) and TRX_VOUCHER_ID = nomor yang dipilih.
		 * */
		tr0012Entity.setTrxDataStatus("13");
		tr0012Entity.setTrxSetDate(appDate);
		tr0012Repo.save(tr0012Entity);
		
		
		/*
		 * Mencari data TR0001 dimana GL_TYPE = TIPE_AWAL dan GL_VOUCHER_ID = NO_AWAL.
		 * */
		//TR0001Entity tr0001EntityOld = tr0001Repo.findByGlTypeAndGlVoucherId(p_TipeAwal, p_NoAwal);
		
		/*
		 * Mencari data TR0001 dimana GL_TYPE = TIPE_DIPILIH dan GL_VOUCHER_ID = VOUCHER_DIPILIH.
		 * */
		TR0001Entity tr0001EntityOld = tr0001Repo.findByGlTypeAndGlVoucherId(p_Type, p_VoucherId);
		
		/*
		 * Insert TR0001 dengan data hasil pencarian di atas, dengan diubah :
			i.	GL_VOUCHER_ID = No baru
			ii.	GL_TRX_DATE,GL_TRX_DUE = Tanggal proses
			iii.GL_TRX_DESC = ‘Cancel - ’ + GL_TRX_DESC yang lama
		 */
		Calendar calTrx = Calendar.getInstance();
		
		/*
		 * Mencari tanggal aplikasi
		 * */
		calTrx.setTime(appDate);
		Integer month = calTrx.get(Calendar.MONTH)+1;
		Integer year = calTrx.get(Calendar.YEAR);
			
		String voucherId = voucherComponent.saveVoucherCounter(formatDate.format(appDate));
		if(tr0001EntityOld != null){
			TR0001Entity tr0001EntityNew = new TR0001Entity();
			tr0001EntityNew.setGlTrxClass(tr0001EntityOld.getGlTrxClass());
			tr0001EntityNew.setGlType(tr0001EntityOld.getGlType());
			tr0001EntityNew.setGlVoucherId(voucherId);					//i.	GL_VOUCHER_ID = No baru
			tr0001EntityNew.setGlTrxDate(appDate);				//ii.	GL_TRX_DATE,GL_TRX_DUE = Tanggal proses
			tr0001EntityNew.setGlTrxDue(appDate);
			tr0001EntityNew.setGlTrxMonth(month.byteValue());
			tr0001EntityNew.setGlTrxYear(year.shortValue());
			tr0001EntityNew.setGlTrxOfficeId(tr0001EntityOld.getGlTrxOfficeId());
			tr0001EntityNew.setGlTrxProject(tr0001EntityOld.getGlTrxProject());
			tr0001EntityNew.setGlTrxClient(tr0001EntityOld.getGlTrxClient());
			
			String desc = "Cancel - " + tr0001EntityOld.getGlTrxDesc();	//iii.GL_TRX_DESC = ‘Cancel - ’ + GL_TRX_DESC yang lama
			tr0001EntityNew.setGlTrxDesc(desc);
			
			tr0001EntityNew.setGlReffId(tr0001EntityOld.getGlReffId());
			tr0001EntityNew.setGlTrxStatus("0");
			tr0001EntityNew.setGlDataStatus(tr0001EntityOld.getGlDataStatus());
			tr0001EntityNew.setCreateOn(now);
			tr0001EntityNew.setCreateBy(p_Param.get(Param.USER).toString());
			tr0001EntityNew.setModifyOn(now);
			tr0001EntityNew.setModifyBy(p_Param.get(Param.USER).toString());
			
			tr0001EntityNew.setGlTrxValueOrg(tr0001EntityOld.getGlTrxValueOrg());
			tr0001EntityNew.setGlTrxValueIdr(tr0001EntityOld.getGlTrxValueIdr());
			
			tr0001Repo.save(tr0001EntityNew);
		}
		
		/*
		 * Mencari data TR0002 dimana GL_TYPE = TIPE_AWAL dan GL_VOUCHER_ID = NO_AWAL.
		 * */
		//List<TR0002Entity> listOftr0002EntityOld = tr0002Repo.findByGlTypeAndGlVoucherId(p_TipeAwal, p_NoAwal);
		
		/*
		 * Mencari data TR0002 dimana GL_TYPE = TIPE_DIPILIH dan GL_VOUCHER_ID = NO_DIPILIH.
		 * */
		List<TR0002Entity> listOftr0002EntityOld = tr0002Repo.findByGlTypeAndGlVoucherId(p_Type, p_VoucherId);
		
		/*
		 * Insert data TR0002 dengan data dengan ketentuan :
			i.	GL_VOUCHER_ID = No baru pada proses  ‘d.i’ diatas.
			ii.	Posisi nilai diubah.  Contoh jika data aslinya nilai ada di ORG_DEBIT maka pada insert data baru nilainya dipindah ke ORG_CREDIT, dan sebaliknya
			iii.Posisi nilai diubah.  Contoh jika data aslinya nilai ada di IDR_DEBIT maka pada insert data baru nilainya dipindah ke IDR_CREDIT, dan sebaliknya.
		*/
		if(listOftr0002EntityOld.size() != 0) {
			for(int i=0; i<listOftr0002EntityOld.size() ; i++){
				TR0002Entity tr0002EntityNew = new TR0002Entity();
				tr0002EntityNew.setGlAccount(listOftr0002EntityOld.get(i).getGlAccount());
				tr0002EntityNew.setGlCurrId(listOftr0002EntityOld.get(i).getGlCurrId());
				tr0002EntityNew.setGlCurrRate(listOftr0002EntityOld.get(i).getGlCurrRate());
				tr0002EntityNew.setGlTrxClass(listOftr0002EntityOld.get(i).getGlTrxClass());
				tr0002EntityNew.setGlType(listOftr0002EntityOld.get(i).getGlType());
				tr0002EntityNew.setGlDescription(listOftr0002EntityOld.get(i).getGlDescription());
				tr0002EntityNew.setGlVoucherId(voucherId);			//i.	GL_VOUCHER_ID = No baru
				//ii.	Posisi nilai diubah.  Contoh jika data aslinya nilai ada di ORG_DEBIT maka pada insert data baru nilainya dipindah ke ORG_CREDIT, dan sebaliknya
				if(listOftr0002EntityOld.get(i).getGlOrgDebit() != null || listOftr0002EntityOld.get(i).getGlOrgDebit().equals(BigDecimal.ZERO))
					tr0002EntityNew.setGlOrgCredit(listOftr0002EntityOld.get(i).getGlOrgDebit());
				if(listOftr0002EntityOld.get(i).getGlOrgCredit() != null || listOftr0002EntityOld.get(i).getGlOrgCredit().equals(BigDecimal.ZERO))
					tr0002EntityNew.setGlOrgDebit(listOftr0002EntityOld.get(i).getGlOrgCredit());
				//iii.Posisi nilai diubah.  Contoh jika data aslinya nilai ada di IDR_DEBIT maka pada insert data baru nilainya dipindah ke IDR_CREDIT, dan sebaliknya.
				if(listOftr0002EntityOld.get(i).getGlIdrDebit() != null || listOftr0002EntityOld.get(i).getGlIdrDebit().equals(BigDecimal.ZERO))
					tr0002EntityNew.setGlIdrCredit(listOftr0002EntityOld.get(i).getGlIdrDebit());
				if(listOftr0002EntityOld.get(i).getGlIdrCredit() != null || listOftr0002EntityOld.get(i).getGlIdrCredit().equals(BigDecimal.ZERO))
					tr0002EntityNew.setGlIdrDebit(listOftr0002EntityOld.get(i).getGlIdrCredit());
				tr0002Repo.save(tr0002EntityNew);
			}
		}
		
		/*
		 * Insert TR0012 dimana :
			i.	TRX_TYPE = Type yang dipilih
			ii.	TRX_VOUCHER_ID = No baru pada proses  ‘d.i’ diatas.
			iii.TRX_DATE ,TRX_DUE= Tanggal proses (d.ii diatas).
			iv.	TRX_DESCRIPTION = sama dengan proses (d.iii) diatas.
			v.	TRX_DATA_STATUS = ‘13’
		*/
		if(tr0001EntityOld != null) {
			TR0012Entity tr0012EntityNew = new TR0012Entity();
			tr0012EntityNew.setCliName(tr0012Entity.getCliName());
			tr0012EntityNew.setCreateBy(Param.USER);
			tr0012EntityNew.setCreateOn(now);
			tr0012EntityNew.setModifyBy(Param.USER);
			tr0012EntityNew.setModifyOn(now);
			tr0012EntityNew.setTrxBrkrFee(tr0012Entity.getTrxBrkrFee());
			tr0012EntityNew.setTrxClient(tr0012Entity.getTrxClient());
			tr0012EntityNew.setTrxComAmount(tr0012Entity.getTrxComAmount());
			tr0012EntityNew.setTrxCountInv(tr0012Entity.getTrxCountInv());
			tr0012EntityNew.setTrxCoverCode(tr0012Entity.getTrxCoverCode());
			tr0012EntityNew.setTrxCurrId(tr0012Entity.getTrxCurrId());
			tr0012EntityNew.setTrxCurrRate(tr0012Entity.getTrxCurrRate());
			tr0012EntityNew.setTrxDataStatus("13");					//v.	TRX_DATA_STATUS = ‘13’
			tr0012EntityNew.setTrxDate(appDate);			//iii.TRX_DATE ,TRX_DUE= Tanggal proses (d.ii diatas).
			String desc = "Cancel - " + tr0001EntityOld.getGlTrxDesc();	
			tr0012EntityNew.setTrxDescription(desc);				//iv.	TRX_DESCRIPTION = sama dengan proses (d.iii) diatas.
			tr0012EntityNew.setTrxDiscAmount(tr0012Entity.getTrxDiscAmount());
			tr0012EntityNew.setTrxDueDate(appDate);		//iii.TRX_DATE ,TRX_DUE= Tanggal proses (d.ii diatas).
			tr0012EntityNew.setTrxInsOfficer(tr0012Entity.getTrxInsOfficer());
			tr0012EntityNew.setTrxIntAmount(tr0012Entity.getTrxIntAmount());
			tr0012EntityNew.setTrxInvcAmount(tr0012Entity.getTrxInvcAmount());
			tr0012EntityNew.setTrxMethPay(tr0012Entity.getTrxMethPay());
			tr0012EntityNew.setTrxOldType(tr0012Entity.getTrxType());//diisi dengan type data yg di-Cancel
			tr0012EntityNew.setTrxOldVoucherId(tr0012Entity.getTrxVoucherId());//diisi dengan type data yg di-Cancel
			tr0012EntityNew.setTrxOrgAmount(tr0012Entity.getTrxOrgAmount());
			tr0012EntityNew.setTrxRemarks(tr0012Entity.getTrxRemarks());
			tr0012EntityNew.setTrxSetAmount(tr0012Entity.getTrxSetAmount());
			tr0012EntityNew.setTrxTaxinBf(tr0012Entity.getTrxTaxinBf());
			tr0012EntityNew.setTrxTrxClass(tr0012Entity.getTrxTrxClass());
			tr0012EntityNew.setTrxType(p_Type);						//i.	TRX_TYPE = Type yang dipilih
			tr0012EntityNew.setTrxVoucherId(voucherId);				//ii.	TRX_VOUCHER_ID = No baru pada proses  ‘d.i’ diatas.
			
			tr0012Repo.save(tr0012EntityNew);
		}
		
		logger.info("New voucher Cancel Settlement : {}.", voucherId);
		
		ResponseEntity response = new ResponseEntity();
		response.setStatus(HttpStatus.OK.getReasonPhrase());
		response.setResult(voucherId);
		return response;
	}

}
