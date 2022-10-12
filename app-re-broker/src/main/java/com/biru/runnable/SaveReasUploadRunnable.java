package com.biru.runnable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.biru.common.param.Param;
import com.biru.common.util.ConcurrentDateFormatAccess;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0018Entity;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0012Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0012Repo;

public class SaveReasUploadRunnable implements Runnable{
	
	private MA0005Repo mA0005Repo;
	private List<String> listOfSurce;
	private VoucherComponent voucherComponent;
	private ConcurrentDateFormatAccess formatDate = new ConcurrentDateFormatAccess("dd-MM-yyyy");
	private Date appDate;
	private Integer month;
	private Integer year;
	private Map<String, Object> param;
	private BigDecimal sumPremi;
	private int rowNum;
	private BigDecimal sumCashValue;
	private BigDecimal sumCashValueBrkr;
	private BigDecimal sumCashValueTaxIn;
	private Date now;
	private MA0018Repo ma0018Repo;
	private String currId;
	private BigDecimal dataRate;
	private BigDecimal sumBrokerFee;
	private Map<String, BigDecimal> mapValue;
	private BigDecimal ppnRate;
	private Date dueDate;
	private String p_TrxVoucherId;
	private TR0006Entity tr0006Entity;
	private TR0001Repo tr0001repo;
	private TR0002Repo tr0002repo;
	private TR0012Repo tr0012repo;
	private List<TR0001Entity> listOfTr0001Entity;
	private List<TR0002Entity> listOfTr0002Entity;
	private List<TR0012Entity> tr12Entities;
	private String coverCode;
	private String fileName;
	private BigDecimal taxRate009;
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		List<TR0001Entity> listOfTr0001Entity = new ArrayList<TR0001Entity>();
			TR0001Entity tr0001Entity = new TR0001Entity();
			String voucherIdReins = counter();
			tr0001Entity.setGlTrxClass("OP");
			tr0001Entity.setGlType("PU");
			tr0001Entity.setGlVoucherId(voucherIdReins);
			tr0001Entity.setGlTrxDate(appDate);
			tr0001Entity.setGlTrxDue(appDate);
			tr0001Entity.setGlTrxMonth(month.byteValue());
			tr0001Entity.setGlTrxYear(year.shortValue());
			tr0001Entity.setGlTrxOfficeId("0");
			tr0001Entity.setGlTrxProject("0000");
			tr0001Entity.setGlTrxClient(Param.getStr(param, "sourceTop"));
			tr0001Entity.setGlTrxClientDesc(param.get("p_Interest").toString()+" - "+fileName);
			tr0001Entity.setGlTrxValueOrg(sumPremi);
			tr0001Entity.setGlTrxValueIdr(sumCashValue);
			tr0001Entity.setGlTrxStatus("0");
			tr0001Entity.setGlDataStatus("11");
			tr0001Entity.setCreateOn(now);
			tr0001Entity.setCreateBy(param.get(Param.USER).toString());
			tr0001Entity.setModifyOn(now);
			tr0001Entity.setModifyBy(param.get(Param.USER).toString());
			
			tr0001Entity.setGlTrxDesc(param.get("p_Interest").toString());
			
			List<MA0018Entity> businessRule = ma0018Repo.findByBrCode("UVTRX".concat(currId));
			
			List<TR0002Entity> listOfTr0002Entity = new ArrayList<TR0002Entity>();
			
			BigDecimal sumTaxInBfValue = (sumBrokerFee.divide(taxRate009, 2, RoundingMode.HALF_UP)).multiply(ppnRate);
			BigDecimal sumGrossValue = sumPremi.add(sumBrokerFee.divide(taxRate009, 2, RoundingMode.HALF_UP)).add(sumTaxInBfValue);
			
			for(MA0018Entity ma0018 : businessRule) {
				TR0002Entity tr0002Entity = new TR0002Entity();
				tr0002Entity.setGlTrxClass(tr0001Entity.getGlTrxClass());
				tr0002Entity.setGlType(tr0001Entity.getGlType());
				tr0002Entity.setGlVoucherId(tr0001Entity.getGlVoucherId());
				tr0002Entity.setGlAccount(ma0018.getBrChildCoa());
				
				tr0002Entity.setGlCurrId(currId);
				tr0002Entity.setGlCurrRate(dataRate);
				
				tr0002Entity.setGlOrgDebit(BigDecimal.ZERO);
				tr0002Entity.setGlIdrDebit(BigDecimal.ZERO);
				tr0002Entity.setGlOrgCredit(BigDecimal.ZERO);
				tr0002Entity.setGlIdrCredit(BigDecimal.ZERO);
				
				if(ma0018.getBrChildDc().equals('0')) {				
					if(("PREMIUM_VALUE").equals(ma0018.getBrChildValue())){
						tr0002Entity.setGlOrgDebit(sumPremi);
						tr0002Entity.setGlIdrDebit(sumCashValue);
					}else if(("BRKR_FEE_VALUE").equals(ma0018.getBrChildValue())){
						tr0002Entity.setGlOrgDebit(sumBrokerFee.divide(taxRate009, 2, RoundingMode.HALF_UP));
						tr0002Entity.setGlIdrDebit(sumCashValueBrkr);
					}else if(("TAXIN_BF_VALUE").equals(ma0018.getBrChildValue())){
						//TAXIN_VALUE
						tr0002Entity.setGlOrgDebit(sumTaxInBfValue);
						tr0002Entity.setGlIdrDebit(sumCashValueTaxIn);
					}else if(("GROSS_VALUE").equals(ma0018.getBrChildValue())){
						tr0002Entity.setGlOrgDebit(sumGrossValue);
						tr0002Entity.setGlIdrDebit(sumGrossValue.multiply(dataRate));
					}
				}else if(ma0018.getBrChildDc().equals('1')) {
					if(("PREMIUM_VALUE").equals(ma0018.getBrChildValue())){
						tr0002Entity.setGlOrgCredit(sumPremi);
						tr0002Entity.setGlIdrCredit(sumCashValue);
					}else if(("BRKR_FEE_VALUE").equals(ma0018.getBrChildValue())){
						tr0002Entity.setGlOrgCredit(sumBrokerFee.divide(taxRate009, 2, RoundingMode.HALF_UP));
						tr0002Entity.setGlIdrCredit(sumCashValueBrkr);
					}else if(("TAXIN_BF_VALUE").equals(ma0018.getBrChildValue())){
						//TAXIN_VALUE
						tr0002Entity.setGlOrgCredit(sumTaxInBfValue);
						tr0002Entity.setGlIdrCredit(sumCashValueTaxIn);
					}else if(("GROSS_VALUE").equals(ma0018.getBrChildValue())){
						tr0002Entity.setGlOrgCredit(sumGrossValue);
						tr0002Entity.setGlIdrCredit(sumGrossValue.multiply(dataRate));
					}
				}
				
				listOfTr0002Entity.add(tr0002Entity);
			}
			tr0001Entity.setGlTrxValueOrg(sumPremi);
			tr0001Entity.setGlTrxValueIdr(sumCashValue);
			
			listOfTr0001Entity.add(tr0001Entity);
				
		int count = 1;
		List<TR0012Entity> tr12Entities = new ArrayList<TR0012Entity>();
		for(TR0001Entity tr1 : listOfTr0001Entity) {
			TR0012Entity tr0012entity = new TR0012Entity();
			tr0012entity.setTrxTrxClass("FAC");
			tr0012entity.setTrxType("PU");
			tr0012entity.setTrxVoucherId(tr1.getGlVoucherId());
			tr0012entity.setTrxSource("1");
			tr0012entity.setTrxCountInv(1);
			tr0012entity.setTrxDate(appDate);
			tr0012entity.setTrxDueDate(dueDate);
			tr0012entity.setTrxMethPay("0");
			tr0012entity.setTrxCoverCode(coverCode);
			tr0012entity.setTrxDataStatus("11");
			tr0012entity.setTrxOldType("RQ");
			tr0012entity.setTrxOldVoucherId(p_TrxVoucherId);
			tr0012entity.setTrxClient(listOfSurce.get(0).toString());
			tr0012entity.setTrxDescription(param.get("p_Interest").toString()+" - "+fileName);
			tr0012entity.setTrxCurrId(currId);
			tr0012entity.setTrxCurrRate(dataRate);
			tr0012entity.setTrxOrgAmount(sumPremi);
			tr0012entity.setTrxInvcAmount(sumPremi);
			tr0012entity.setTrxBrkrFee(sumBrokerFee.divide(taxRate009, 2, RoundingMode.HALF_UP));
			tr0012entity.setTrxTaxinBf((sumBrokerFee.divide(taxRate009, 2, RoundingMode.HALF_UP)).multiply(ppnRate));
			tr0012entity.setTrxInsOfficer(tr0006Entity.getTrxOfficer());
			tr0012entity.setCreateBy(param.get(Param.USER).toString());
			tr0012entity.setCreateOn(now);
			
			tr12Entities.add(tr0012entity);
			count++;
		}
		
		setListOfTr0001Entity(listOfTr0001Entity);
		setListOfTr0002Entity(listOfTr0002Entity);
		setTr12Entities(tr12Entities);
	}
	
	private String counter(){
		synchronized (appDate) {
			return voucherComponent.saveVoucherCounter(formatDate.format(appDate));
		}
	}

	public MA0005Repo getmA0005Repo() {
		return mA0005Repo;
	}

	public void setmA0005Repo(MA0005Repo mA0005Repo) {
		this.mA0005Repo = mA0005Repo;
	}

	public List<String> getListOfSurce() {
		return listOfSurce;
	}

	public void setListOfSurce(List<String> listOfSurce) {
		this.listOfSurce = listOfSurce;
	}

	public VoucherComponent getVoucherComponent() {
		return voucherComponent;
	}

	public void setVoucherComponent(VoucherComponent voucherComponent) {
		this.voucherComponent = voucherComponent;
	}

	public ConcurrentDateFormatAccess getFormatDate() {
		return formatDate;
	}

	public void setFormatDate(ConcurrentDateFormatAccess formatDate) {
		this.formatDate = formatDate;
	}

	public Date getAppDate() {
		return appDate;
	}

	public void setAppDate(Date appDate) {
		this.appDate = appDate;
	}

	public Integer getMonth() {
		return month;
	}

	public void setMonth(Integer month) {
		this.month = month;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	public Date getNow() {
		return now;
	}

	public void setNow(Date now) {
		this.now = now;
	}

	public MA0018Repo getMa0018Repo() {
		return ma0018Repo;
	}

	public void setMa0018Repo(MA0018Repo ma0018Repo) {
		this.ma0018Repo = ma0018Repo;
	}

	public String getCurrId() {
		return currId;
	}

	public void setCurrId(String currId) {
		this.currId = currId;
	}

	public BigDecimal getDataRate() {
		return dataRate;
	}

	public void setDataRate(BigDecimal dataRate) {
		this.dataRate = dataRate;
	}

	public Map<String, BigDecimal> getMapValue() {
		return mapValue;
	}

	public void setMapValue(Map<String, BigDecimal> mapValue) {
		this.mapValue = mapValue;
	}

	public BigDecimal getPpnRate() {
		return ppnRate;
	}

	public void setPpnRate(BigDecimal ppnRate) {
		this.ppnRate = ppnRate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public String getP_TrxVoucherId() {
		return p_TrxVoucherId;
	}

	public void setP_TrxVoucherId(String p_TrxVoucherId) {
		this.p_TrxVoucherId = p_TrxVoucherId;
	}

	public TR0006Entity getTr0006Entity() {
		return tr0006Entity;
	}

	public void setTr0006Entity(TR0006Entity tr0006Entity) {
		this.tr0006Entity = tr0006Entity;
	}

	public TR0001Repo getTr0001repo() {
		return tr0001repo;
	}

	public void setTr0001repo(TR0001Repo tr0001repo) {
		this.tr0001repo = tr0001repo;
	}

	public TR0002Repo getTr0002repo() {
		return tr0002repo;
	}

	public void setTr0002repo(TR0002Repo tr0002repo) {
		this.tr0002repo = tr0002repo;
	}

	public TR0012Repo getTr0012repo() {
		return tr0012repo;
	}

	public void setTr0012repo(TR0012Repo tr0012repo) {
		this.tr0012repo = tr0012repo;
	}

	public List<TR0001Entity> getListOfTr0001Entity() {
		return listOfTr0001Entity;
	}

	public void setListOfTr0001Entity(List<TR0001Entity> listOfTr0001Entity) {
		this.listOfTr0001Entity = listOfTr0001Entity;
	}

	public List<TR0002Entity> getListOfTr0002Entity() {
		return listOfTr0002Entity;
	}

	public void setListOfTr0002Entity(List<TR0002Entity> listOfTr0002Entity) {
		this.listOfTr0002Entity = listOfTr0002Entity;
	}

	public List<TR0012Entity> getTr12Entities() {
		return tr12Entities;
	}

	public void setTr12Entities(List<TR0012Entity> tr12Entities) {
		this.tr12Entities = tr12Entities;
	}

	public BigDecimal getSumPremi() {
		return sumPremi;
	}

	public void setSumPremi(BigDecimal sumPremi) {
		this.sumPremi = sumPremi;
	}

	public BigDecimal getSumCashValue() {
		return sumCashValue;
	}

	public void setSumCashValue(BigDecimal sumCashValue) {
		this.sumCashValue = sumCashValue;
	}

	public BigDecimal getSumCashValueBrkr() {
		return sumCashValueBrkr;
	}

	public void setSumCashValueBrkr(BigDecimal sumCashValueBrkr) {
		this.sumCashValueBrkr = sumCashValueBrkr;
	}

	public BigDecimal getSumCashValueTaxIn() {
		return sumCashValueTaxIn;
	}

	public void setSumCashValueTaxIn(BigDecimal sumCashValueTaxIn) {
		this.sumCashValueTaxIn = sumCashValueTaxIn;
	}

	public BigDecimal getSumBrokerFee() {
		return sumBrokerFee;
	}

	public void setSumBrokerFee(BigDecimal sumBrokerFee) {
		this.sumBrokerFee = sumBrokerFee;
	}
	
	public String getCoverCode() {
		return coverCode;
	}

	public void setCoverCode(String coverCode) {
		this.coverCode = coverCode;
	}
	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public BigDecimal getTaxRate009() {
		return taxRate009;
	}

	public void setTaxRate009(BigDecimal taxRate009) {
		this.taxRate009 = taxRate009;
	}

	@Override
	public String toString() {
		return "SaveReasUploadRunnable [mA0005Repo=" + mA0005Repo + ", listOfSurce=" + listOfSurce
				+ ", voucherComponent=" + voucherComponent + ", formatDate=" + formatDate + ", appDate=" + appDate
				+ ", month=" + month + ", year=" + year + ", param=" + param + ", sumPremi=" + sumPremi + ", rowNum="
				+ rowNum + ", sumCashValue=" + sumCashValue + ", sumCashValueBrkr=" + sumCashValueBrkr
				+ ", sumCashValueTaxIn=" + sumCashValueTaxIn + ", now=" + now + ", ma0018Repo=" + ma0018Repo
				+ ", currId=" + currId + ", dataRate=" + dataRate + ", sumBrokerFee=" + sumBrokerFee + ", mapValue="
				+ mapValue + ", ppnRate=" + ppnRate + ", dueDate=" + dueDate + ", p_TrxVoucherId=" + p_TrxVoucherId
				+ ", tr0006Entity=" + tr0006Entity + ", tr0001repo=" + tr0001repo + ", tr0002repo=" + tr0002repo
				+ ", tr0012repo=" + tr0012repo + ", listOfTr0001Entity=" + listOfTr0001Entity + ", listOfTr0002Entity="
				+ listOfTr0002Entity + ", tr12Entities=" + tr12Entities + ", coverCode=" + coverCode + ", fileName="
				+ fileName + ", taxRate009=" + taxRate009 + "]";
	}
	
}
