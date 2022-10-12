package com.biru.service.impl;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hpsf.Decimal;
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
import com.biru.entity.MA0012Entity;
import com.biru.repository.MA0012Repo;
import com.biru.repository.MA0014Repo;
import com.biru.service.AgentOfficerService;

@Service
public class AgentOfficeServiceImpl extends AbstractCommon implements AgentOfficerService  {

	@Autowired
	MA0012Repo ma0012; 
	
	@Autowired
	MA0014Repo ma0014;

	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {
		final Map<String, String> mapOfType = new HashMap<String, String>(){{put("0", "Agent");put("1","Internal");put("2","Others");}};
		try {
			Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
			Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
			String order = Param.getStr(p_Param, PARAM.ORDER);
			String sort = Param.getStr(p_Param, PARAM.SORT);
			String filterValue = Param.getStrWithDef(p_Param, PARAM.FILTER_VALUE); //find something?
			Pageable pageable = PageRequest.of(offset/limit, limit, Sort.Direction.fromString(order), sort);

			Page<MA0012Entity> data = ma0012.findAll(filterValue.toLowerCase(), pageable);

			List<MA0012Entity> listOfData = data.getContent();

			for (MA0012Entity ma0012Entity : listOfData) {
//				String action = "<button class=\"btn btn-danger\" onclick=\"tr12Edit('"+ma0012Entity.getIdKey()+"')\">" 
//						+ "<i class=\"fa fa-trash\"></i>" 
//						+ "</button>";
//				ma0012Entity.setAction(action);
				
				String officeLabel = ma0014.findByPaParentCodeAndPaChildStatusAndPaChildValue("OFFCODE","11",ma0012Entity.getSaOfficeId()).getPaChildDesc();
				
				ma0012Entity.setSaOfficeLabel(officeLabel);
				ma0012Entity.setSaDataStatusDesc(ma0012Entity.getSaDataStatus().equalsIgnoreCase("11") ? "Active": "Inactive");
				ma0012Entity.setSaTypeDesc(mapOfType.get(ma0012Entity.getSaType()));
			}

			return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return new DatatableSet();
	}

	@Override
	public Object save(Map<String, Object> p_Param) throws ParseException{
//		System.out.println("param "+ p_Param);

		Long idKey = Param.getLong(p_Param, "idKey");
		String saCode = Param.getStrWithDef(p_Param, "saCode");
		String saOfficeId = Param.getStrWithDef(p_Param, "saOfficeId");
		String saType = Param.getStrWithDef(p_Param, "saType");
		String saName = Param.getStrWithDef(p_Param, "saName");
		String saTaxId = Param.getStrWithDef(p_Param, "saTaxId");
		String saEmail = Param.getStrWithDef(p_Param, "saEmail");
		String saPhone = Param.getStrWithDef(p_Param, "saPhone");
		String saJointDate = Param.getStrWithDef(p_Param, "saJointDate");
		String saLicense = Param.getStrWithDef(p_Param, "saLicense");
		String saDataStatus = Param.getStrWithDef(p_Param, "saDataStatus");
		BigDecimal saCommision = Param.getBdWithDef(p_Param, "saCommision");
		

		Calendar cal = Calendar.getInstance();
		Date now = cal.getTime();

		String user = Param.getStr(p_Param, Param.USER);
		MA0012Entity ma0012Entity = null;

		if (idKey == null) {
			System.out.println("Generating New");
			ma0012Entity = new MA0012Entity();
			ma0012Entity.setCreateBy(user);
			ma0012Entity.setCreateOn(now);
			ma0012Entity.setModifyBy(user);
			ma0012Entity.setModifyOn(now);
			ma0012Entity.setSaCode(getNextCode());
		} else {
			System.out.println("Generating exist");
			ma0012Entity = ma0012.findByIdKey(idKey);
			ma0012Entity.setModifyBy(user);
			ma0012Entity.setModifyOn(now);
		}

		ma0012Entity.setSaOfficeId(saOfficeId);
		ma0012Entity.setSaType(saType);
		ma0012Entity.setSaCommision(saCommision);
		ma0012Entity.setSaName(saName);
		ma0012Entity.setSaTaxId(saTaxId);
		ma0012Entity.setSaEmail(saEmail);
		ma0012Entity.setSaPhone(saPhone);
		ma0012Entity.setSaJoint(formatDate.parse(saJointDate));
		ma0012Entity.setSaLicense(saLicense);
		ma0012Entity.setSaDataStatus(saDataStatus);

		ma0012.save(ma0012Entity);

		HashMap<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put("idKey", String.valueOf(idKey));


		return returnValue;
	}

	public String getNextCode() {
		Pageable pageable = PageRequest.of(0, 1, Sort.Direction.fromString("DESC"), "idKey");
		List<MA0012Entity> data = ma0012.findAll(pageable).getContent();

		int max = 0;
		int count = 0;
		if (!data.isEmpty()) {
			// jika tabel tidak kosong
			max = Integer.parseInt(data.get(0).getSaCode());
			max += 1;
		} else {
			max = 1;
		}

		
		int temp = max;
		while(temp > 0) {
			temp = temp / 10;
			count = count + 1; 
		}
		
		switch(count) {
			case 1 : return "0000"+max;
			case 2 : return "000"+max;
			case 3 : return "00"+max;
			case 4 : return "0"+max;
			case 5 :
			default : return ""+max;
		}
	}
}
