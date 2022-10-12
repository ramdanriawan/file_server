package com.biru.service.impl;

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

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.BIGDECIMAL;
import com.biru.ReBrokerConstants.CHILDCODE;
import com.biru.ReBrokerConstants.CODE;
import com.biru.ReBrokerConstants.PARAM;
import com.biru.ReBrokerConstants.PARAM.KEY;
import com.biru.ReBrokerConstants.PARENTCODE;
import com.biru.common.AbstractCommon;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.component.BusinessDay;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0015AEntity;
import com.biru.entity.MA0015Entity;
import com.biru.entity.MA0019Entity;
import com.biru.entity.TR0005Entity;
import com.biru.entity.TR0006Entity;
import com.biru.repository.GL0001Repo;
import com.biru.repository.GL0002Repo;
import com.biru.repository.MA0004Repo;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0007Repo;
import com.biru.repository.MA0010Repo;
import com.biru.repository.MA0011Repo;
import com.biru.repository.MA0012Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0015ARepo;
import com.biru.repository.MA0015Repo;
import com.biru.repository.MA0016Repo;
import com.biru.repository.MA0019Repo;
import com.biru.repository.TR0004Repo;
import com.biru.repository.TR0005Repo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.ViewInqTr0006Repo;
import com.biru.service.CommonService;
import com.biru.specifications.MA0005Spesifications;
import com.biru.view.ViewInqTr0006Entity;

@Service
public class CommonServiceImpl extends AbstractCommon implements CommonService {

	@Autowired
	private BusinessDay businessDay;

	@Autowired
	private MA0004Repo ma0004Repo;

	@Autowired
	private MA0005Repo ma0005Repo;

	@Autowired
	private MA0010Repo ma0010Repo;

	@Autowired
	private MA0011Repo ma0011Repo;

	@Autowired
	private MA0012Repo ma0012Repo;

	@Autowired
	private MA0014Repo ma0014Repo;

	@Autowired
	private MA0015Repo ma0015Repo;

	@Autowired
	private MA0015ARepo ma0015ARepo;

	@Autowired
	private MA0016Repo ma0016Repo;

	@Autowired
	private GL0001Repo gl0001Repo;

	@Autowired
	private TR0004Repo tR0004Repo;

	@Autowired
	private TR0005Repo tR0005Repo;
	
	@Autowired
	private TR0006Repo tR0006Repo;

	@Autowired
	private MA0007Repo mA0007Repo;

	@Autowired
	private MA0019Repo mA0019Repo;

	@Autowired
	private GL0002Repo gl0002Repo;

	@Autowired
	private ViewInqTr0006Repo viewInqTr0006Repo;
	
	@Autowired
	private ReportProductionServiceImpl reportProdService;
	
	private static final String PARENT_CODE = "parentCode";
	private static final String CHILD_CODE 	= "childCode";
	
	private Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);

	@Override
	public Date getAppDate() throws ParseException {
		return formatDate.parse(getAppDateStr());
	}

	@Override
	public String getAppDateStr() throws ParseException {
		MA0014Entity param = ma0014Repo.findByParentCodeAndChildCode(CODE.P_SYSDATE, CODE.C_SYSDATE001);

		Date appDate = formatDateDb.parse(param.getPaChildValue());

		return formatDate.format(appDate);
	}

	@Override
	public List<DropdownIdText> getDropdownStatusSD() {
		return ma0014Repo.getStatusParamSD();
	}
	
	@Override
	public List<DropdownIdText> getDropdownStatusClaims(){
		return ma0014Repo.getStatusClaims();
	}

	@Override
	public String getNextBusinessDayStr() throws ParseException {
		return businessDay.getNext();
	}

	@Override
	public List<DropdownIdText> getMessageSave() {
		return ma0016Repo.getMessageSave();
	}

	@Override
	public List<DropdownIdText> getYearCanBeProcess() {
		return gl0001Repo.findYear();
	}

	@Override
	public Map<String, String> getLastProMonthAndProYear() {
		TR0005Entity tR0005Entity = tR0005Repo.findLastProMonthAndProYear().get(0);
		Map<String, String> result = new HashMap<String, String>();
		result.put("proYear", String.valueOf(tR0005Entity.getProYear()));
		result.put("proMonth", String.valueOf(tR0005Entity.getProMonth()));

		return result;
	}

	@Override
	public String getCurrCoa(String p_CoaCode) {
		return ma0004Repo.findCoaCurrIdByCoaCode(p_CoaCode);
	}

	@Override
	public List<DropdownIdText> getDropdownBank() {

		return ma0004Repo.getBank();
	}

	@Override
	public Object lookupClient(Map<String, Object> p_Params) {
		Integer limit = Param.getInt(p_Params, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Params, PARAM.OFFSET);
		String order = Param.getStr(p_Params, PARAM.ORDER);
		String sort = Param.getStr(p_Params, PARAM.SORT);
		String filterKey = Param.getStrWithDef(p_Params, PARAM.FILTER_KEY);
		String filterValue = Param.getStrWithDef(p_Params, PARAM.FILTER_VALUE);

		String cliName = null;
		String cliCode = null;
		String cliType = Param.getStrWithDef(p_Params, "cliType");		
		String cliDataStatus = Param.getStrWithDef(p_Params, "cliDataStatus");

		if(StringUtils.isBlank(cliDataStatus))
			cliDataStatus = "0";

		if(KEY.CLI_NAME.equals(filterKey))
			cliName = filterValue;
		else if(KEY.CLI_CODE.equals(filterKey))
			cliCode = filterValue;

		Specification<MA0005Entity> spec = MA0005Spesifications.lookupClient(cliName, cliCode, cliType, cliDataStatus);
		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<MA0005Entity> data = ma0005Repo.findAll(spec, pageable);

		List<MA0014Entity> paramTypes = ma0014Repo.findByPaParentCode("TYPECO");
		for(MA0005Entity ma0005Entity : data.getContent()) {
			MA0014Entity type = paramTypes.stream()
					.filter(param -> param.getPaChildValue().equals(ma0005Entity.getCliType()))
					.findFirst().orElse(null);
			if(type != null)
				ma0005Entity.setCliTypeDesc(type.getPaChildDesc());
			
			ma0005Entity.setCliDataStatusStr("Active");
		}

		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public List<DropdownIdText> getDropdownClient(String p_CliType) {

		return ma0005Repo.getDropdownClient(p_CliType);
	}

	@Override
	public List<DropdownIdText> getDropdownProduct() {

		return ma0011Repo.getDropdownProduct();
	}

	@Override
	public List<DropdownIdText> getDropdownOfficer() {

		return ma0012Repo.getDropdownOfficer();
	}

	@Override
	public List<DropdownIdText> getDropdownOfficerTypeNot2() {

		return ma0012Repo.getDropdownOfficerTypeNot2();
	}
	
	@Override
	public List<DropdownIdText> getDropdownParam(Map<String, Object> params) {
		String parentCode = Param.getStr(params, "parentCode");
		
		String childCode = Param.getStrWithDef(params, "childValue");
		String[] arrayChildCode = childCode.split(",");
		
		if(StringUtils.isNotBlank(childCode))
			return ma0014Repo.getParam(parentCode, Arrays.asList(arrayChildCode));
		else
			return ma0014Repo.getParam(parentCode);
	}

	@Override
	public List<DropdownIdText> getDropdownCurrency() {

		return ma0014Repo.getCurrency();
	}

	@Override
	public List<DropdownIdText> getDropdownDCNote() {

		return ma0014Repo.getDCNotes();
	}

	@Override
	public List<DropdownIdText> getDropdownOffice() {
		List<DropdownIdText> datas = ma0014Repo.getOffice();

		return datas;
	}

	@Override
	public List<DropdownIdText> getDropdownProject() {

		return tR0004Repo.getProject();
	}

	@Override
	public List<DropdownIdText> getDropdownPaymentMethod() {

		return ma0014Repo.getPaymentMethod();
	}

	@Override
	public String taxRate(String p_ClientCode) {
		MA0005Entity client = ma0005Repo.findByCliCode(p_ClientCode);

		String taxCode = CODE.C_TAXRATEH002;

		if(StringUtils.isNotBlank(client.getCliTaxId()))
			taxCode = CODE.C_TAXRATEH001;

		String taxRate = ma0014Repo.findByPaChildCode(taxCode).getPaChildValue();

		return taxRate;
	}

	@Override
	public List<DropdownIdText> getDropdownTypeOfCover() {
		return ma0011Repo.getDropdownTypeOfCover();
	}

	@Override
	public List<DropdownIdText> getDropdownClasification() {
		return ma0014Repo.getClasification();
	}

	@Override
	public List<DropdownIdText> getDropdownValue() {
		return ma0014Repo.getValue();
	}

	@Override
	public List<DropdownIdText> getDropdownName() {
		return ma0012Repo.getDropdownOfficerType2();
	}

	@Override
	public Object exchange(Map<String, Object> p_Params) {
		String[] date = p_Params.get("date").toString().split("/");
		String curr = p_Params.get("curr") != null ? p_Params.get("curr").toString() : null;
		String coaCode = p_Params.get("coaCode") != null ? p_Params.get("coaCode").toString() : null;

		MA0015Entity exchange = null;
		if(coaCode!=null)
			exchange = ma0015Repo.findByCoaCodeAndExMonthAndExYear(coaCode, Integer.valueOf(date[1]), Integer.valueOf(date[2]));
		else
			exchange = ma0015Repo.findByExMonthAndExYearAndExCurrId(Integer.valueOf(date[1]), Integer.valueOf(date[2]), curr);

		if(exchange == null)
			return NumberFormat.getCurrencyInstance().format(BigDecimal.ZERO).replace("$", "");

		return exchange.getExRateValueStr();
	}

	@Override
	public Object exchangeNonEom(Map<String, Object> p_Params) {
		Date date = Param.getDate(p_Params, "date");
		String curr = Param.getStr(p_Params, "curr");
		String coaCode = Param.getStr(p_Params, "coaCode");

		MA0015AEntity exchangeNonEom = null;
		if(coaCode!=null)
			exchangeNonEom = ma0015ARepo.findByCoaCodeAndExDate(coaCode, date);
		else
			exchangeNonEom = ma0015ARepo.findByExDateAndExCurrId(date, curr);

		if(exchangeNonEom == null)
			return NumberFormat.getCurrencyInstance().format(BigDecimal.ZERO).replace("$", "");

		return exchangeNonEom.getExKmkRateStr();
	}

	@Override
	public List<DropdownIdText> dropdownTcGroup() {
		return ma0014Repo.findTcGroup();
	}

	@Override
	public List<DropdownIdText> dropdownTcDetails(String groupName) {
		String[] arrayGroupName = groupName.split("\\|");
		List<DropdownIdText> tcDetails = new ArrayList<DropdownIdText>();
		tcDetails.add(new DropdownIdText("", ""));
		tcDetails.addAll(mA0007Repo.findTcDetails(arrayGroupName[0]));
		if(arrayGroupName.length > 1) {
			tcDetails.remove(0);
		}
		return tcDetails;
	}

	@Override
	public List<DropdownIdText> dropdownDocumentType(String tcCode) {
		List<MA0019Entity> listMA0019Entity;
		if(StringUtils.isNotBlank(tcCode))
			listMA0019Entity = mA0019Repo.findByTcCode(tcCode);
		else
			listMA0019Entity = mA0019Repo.findAll(PageRequest.of(0, Integer.MAX_VALUE, Sort.Direction.ASC, "clDesc")).getContent();
		
		List<DropdownIdText> listDropdownIdText = new ArrayList<DropdownIdText>();
		for (MA0019Entity mA0019Entity : listMA0019Entity) {
			String mandatoryOption = "Mandatory";
			if(mA0019Entity.getClStatus().equals("0"))
				mandatoryOption = "Option";
			
			DropdownIdText dit = new DropdownIdText(String.valueOf(mA0019Entity.getClCode()), mA0019Entity.getClDesc().concat(" - ").concat(mandatoryOption));
			listDropdownIdText.add(dit);
		}
		
		return listDropdownIdText;
	}

	@Override
	public Object getLogoCompany() {
		return ma0010Repo.findAll().iterator().next().getCoLogo();
	}

	@Override
	public List<DropdownIdText> dropdownParticipantStatus() {
		List<MA0014Entity> listMA0014Entity =  ma0014Repo.findByPaParentCodeAndPaChildStatus("STCLIENT", "11");
		List<DropdownIdText> listDropdownIdText = new ArrayList<DropdownIdText>();
		for (MA0014Entity ma0014Entity : listMA0014Entity) {
			DropdownIdText dropdownIdText = new DropdownIdText(ma0014Entity.getPaChildValue(), ma0014Entity.getPaChildDesc());
			listDropdownIdText.add(dropdownIdText);
		}
		return listDropdownIdText;
	}

	@Override
	public List<DropdownIdText> dropdownParticipantCountry() {
		List<MA0014Entity> listMA0014Entity =  ma0014Repo.findByPaParentCodeAndPaChildStatus("COUCODE", "11");
		List<DropdownIdText> listDropdownIdText = new ArrayList<DropdownIdText>();
		for (MA0014Entity ma0014Entity : listMA0014Entity) {
			DropdownIdText dropdownIdText = new DropdownIdText(ma0014Entity.getPaChildValue(), ma0014Entity.getPaChildDesc());
			listDropdownIdText.add(dropdownIdText);
		}
		return listDropdownIdText;
	}

	@Override
	public List<DropdownIdText> dropdownParticipantIndustry() {
		List<MA0014Entity> listMA0014Entity =  ma0014Repo.findByPaParentCodeAndPaChildStatus("CLINDUST", "11");
		List<DropdownIdText> listDropdownIdText = new ArrayList<DropdownIdText>();
		for (MA0014Entity ma0014Entity : listMA0014Entity) {
			DropdownIdText dropdownIdText = new DropdownIdText(ma0014Entity.getPaChildValue(), ma0014Entity.getPaChildDesc());
			listDropdownIdText.add(dropdownIdText);
		}
		return listDropdownIdText;
	}

	@Override
	public List<DropdownIdText> dropdownTbYear() {
		return gl0002Repo.findYear();
	}
	
	public List<DropdownIdText> dropdownCurrTax(){
		return ma0014Repo.getCurrency();
	}

	@Override
	public Object tr0006Inquiry(Map<String, Object> param) {
		logger.info("Param tr0006Inquiry : {}.", param);
		
		Boolean isEndorsement = param.get("isEndorsement") != null ? 
				(Boolean) param.get("isEndorsement") : false;
		Boolean isModifyFacultative = param.get("isModifyFacultative") != null ? 
				(Boolean) param.get("isModifyFacultative") : false;
		Boolean isReportProductionDi = param.get("isReportProductionDi") != null ? 
				(Boolean) param.get("isReportProductionDi") : false;
				
		
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String typeOfCover = Param.getStr(param, "typeOfCover");
		String client = "%"+Param.getStrWithDef(param, "client")+"%";
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

		String to = Param.getStrWithDef(param, "to");
		Date toDate = null;
		date = to.split("/");
		c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date[0]));
		c.set(Calendar.MONTH, (Integer.parseInt(date[1])-1));
		c.set(Calendar.YEAR, Integer.parseInt(date[2]));
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		toDate = c.getTime();
		
		String insuredName = "%"+Param.getStrWithDef(param, "insuredName")+"%";

		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<ViewInqTr0006Entity> pageViewInqTr0006Entity = null;
		logger.info("trxDateDate = {}", trxDateDate);
		logger.info("toDate = {}", toDate);
		logger.info("client = {}", client);
		logger.info("typeOfCover = {}", typeOfCover);
		logger.info("insuredName = {}", insuredName);
		logger.info("isEndorsement = {}, isModifyFacultative = {}, isReportProductionDi = {}", isEndorsement, isModifyFacultative, isReportProductionDi);
		
		if(!typeOfCover.equals("ALL")) {
			if(isEndorsement)
				pageViewInqTr0006Entity = viewInqTr0006Repo.findByTrxDateAndCientAndCoverCodeEndorsement(trxDateDate, toDate, client, typeOfCover, insuredName, pageable);
			else
				pageViewInqTr0006Entity = viewInqTr0006Repo.findByTrxDateAndCientAndCoverCode(trxDateDate, toDate, client, typeOfCover, insuredName, pageable);
		}else {
			if(isEndorsement)
				pageViewInqTr0006Entity = viewInqTr0006Repo.findByTrxDateAndCientEndorsement(trxDateDate, toDate, client, insuredName, pageable);
			else
				pageViewInqTr0006Entity = viewInqTr0006Repo.findByTrxDateAndCient(trxDateDate, toDate, client, insuredName, pageable);
		}
		
		for (ViewInqTr0006Entity viewInqTr0006Entity : pageViewInqTr0006Entity.getContent()) {
			if(isEndorsement) {
				viewInqTr0006Entity.setAction("<button class=\"btn btn-secondary\" onclick=\"edit('"+viewInqTr0006Entity.getTrxVoucherId()+"', '11', '"+viewInqTr0006Entity.getTr6VoucherId()+"')\">" 
						+ "<i class=\"fa fa-edit\"></i>" 
						+ "	</button>");
			}else if(isModifyFacultative) {
				//for get data with same transaction (closing)
				Long millisTr12CreateOn = viewInqTr0006Entity.getTr12CreateOn().getTime();
				viewInqTr0006Entity.setAction("<button class=\"btn btn-secondary\" onclick=\"edit('"+viewInqTr0006Entity.getTrxVoucherId()+"', '11', '"+viewInqTr0006Entity.getTr6VoucherId()+"', '"+millisTr12CreateOn+"')\">" 
						+ "<i class=\"fa fa-edit\"></i>" 
						+ "	</button> &nbsp;"
						+ "<button class=\"btn btn-danger\" onclick=\"remove('"+viewInqTr0006Entity.getIdKey()+"', '"+millisTr12CreateOn+"')\">" 
						+ "<i class=\"fa fa-trash\"></i>" 
						+ "</button>");	
			}else if(isReportProductionDi) {
				ViewInqTr0006Entity newViewEntity = reportProdService.generateNewViewTr6Entity(viewInqTr0006Entity);
				newViewEntity.setAction("<button class=\"btn btn-secondary\" onclick=\"edit('"+newViewEntity.getTrxVoucherId()+"', '11', '"+viewInqTr0006Entity.getTr6VoucherId()+"')\">" 
						+ "<i class=\"fa fa-edit\"></i>" 
						+ "	</button> &nbsp;"
						+ "<button class=\"btn btn-danger\" onclick=\"remove('"+newViewEntity.getIdKey()+"')\">" 
						+ "<i class=\"fa fa-trash\"></i>" 
						+ "</button>");		
			}else {
				viewInqTr0006Entity.setAction("<button class=\"btn btn-secondary\" onclick=\"edit('"+viewInqTr0006Entity.getTrxVoucherId()+"', '11', '"+viewInqTr0006Entity.getTr6VoucherId()+"')\">" 
						+ "<i class=\"fa fa-edit\"></i>" 
						+ "	</button> &nbsp;"
						+ "<button class=\"btn btn-danger\" onclick=\"remove('"+viewInqTr0006Entity.getIdKey()+"')\">" 
						+ "<i class=\"fa fa-trash\"></i>" 
						+ "</button>");		
			}
		}

		return new DatatableSet(pageViewInqTr0006Entity.getTotalElements(), pageViewInqTr0006Entity.getTotalElements(), pageViewInqTr0006Entity.getContent());
	}
	
	@Override
	public Object tr0006Inquiry2(Map<String, Object> param) {
		logger.info("Param tr0006Inquiry2 : {}.", param);
		
		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		String typeOfCover = Param.getStr(param, "typeOfCover");
		String client = "%"+Param.getStrWithDef(param, "client")+"%";

		Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);
		Page<ViewInqTr0006Entity> pageViewInqTr0006Entity = null;
		if(!typeOfCover.equals("ALL"))
			pageViewInqTr0006Entity = viewInqTr0006Repo.findByTrxDateAndCientAndCoverCode2(client, typeOfCover, pageable);
		else
			pageViewInqTr0006Entity = viewInqTr0006Repo.findByTrxDateAndCient2(client, pageable);
		
		return new DatatableSet(pageViewInqTr0006Entity.getTotalElements(), pageViewInqTr0006Entity.getTotalElements(), pageViewInqTr0006Entity.getContent());
	}

	@Override
	public List<DropdownIdText> getDropdownFormat() {
		return ma0014Repo.getFormat();
	}

	@Override
	public List<DropdownIdText> getDropdownClientType() {
		return ma0014Repo.getClientType();
	}

	@Override
	public List<DropdownIdText> dropdownMenu() {
		return null;
	}
	
	@Override
	public String getParameter(Map<String, String> p_Param) {
		if(!p_Param.containsKey(PARENT_CODE) || !p_Param.containsKey(CHILD_CODE))
			return null;
			
		MA0014Entity param = ma0014Repo.findByParentCodeAndChildCode(
				p_Param.get(PARENT_CODE), p_Param.get(CHILD_CODE));
		return (param == null) ? null : param.getPaChildValue();
	}

	@Override
	public BigDecimal getTaxRateh003() {
		return getTaxRateh(CHILDCODE.TAXRATEH003).divide(BIGDECIMAL.VAL100, 4, RoundingMode.HALF_UP);
	}

	@Override
	public BigDecimal getTaxRateh009() {
		return getTaxRateh(CHILDCODE.TAXRATEH009);
	}
	
	@Override
	public BigDecimal getTaxRateh011() {
		return getTaxRateh(CHILDCODE.TAXRATEH011).divide(BIGDECIMAL.VAL100, 4, RoundingMode.HALF_UP);
	}
	
	@Override
	public BigDecimal getTaxRateh012() {
		return getTaxRateh(CHILDCODE.TAXRATEH012);
	}
	
	private BigDecimal getTaxRateh(String childCode) {
		Map<String, String> paramTax = new HashMap<String, String>();
		paramTax.put(PARENT_CODE, PARENTCODE.TAXRATEH);
		paramTax.put(CHILD_CODE, childCode);
		
		String tax = getParameter(paramTax);					
		if(StringUtils.isBlank(tax))
			tax = "0.00";
			
		BigDecimal taxRate = new BigDecimal(tax);
		
		return taxRate;
	}

	@Override
	public Boolean isAlreadyClosing(String trxId, String trxVoucherId) {
		Boolean result = Boolean.FALSE;
		
		List<TR0006Entity> prods = tR0006Repo
				.findByTrxTrxIdAndTrxVoucherId(trxId, trxVoucherId);
		if(prods.size() > 0 && prods.get(0).getTrxDataStatus().equals("11"))	//data production already closing
			result = Boolean.TRUE;
		
		return result;
	}

}
