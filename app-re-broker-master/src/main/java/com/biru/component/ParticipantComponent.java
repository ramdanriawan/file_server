package com.biru.component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biru.ReBrokerConstants.SIMPLE_DATE_FORMAT;
import com.biru.common.param.Param;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0014Entity;
import com.biru.repository.MA0005Repo;
@Component
public class ParticipantComponent {
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Transactional
	public void save(Map<String, Object> param) throws ParseException {
		// TODO Auto-generated method stub
		String idKey = Param.getStr(param, "idKey");
		MA0005Entity mA0005Entity = null;
		Date now = Calendar.getInstance().getTime();
		
		if(idKey == null){
			mA0005Entity = new MA0005Entity();
			mA0005Entity.setCreateBy(Param.getStrWithDef(param, Param.CREATE_BY));
			mA0005Entity.setCreateOn(now);
			mA0005Entity.setModifyBy(Param.getStr(param, Param.MODIFY_BY));
			mA0005Entity.setModifyOn(now);
			mA0005Entity.setCliCode(generateCliCode());
		}else {
			mA0005Entity = mA0005Repo.findById(idKey).get();
			mA0005Entity.setModifyBy(Param.getStr(param, Param.MODIFY_BY));
			mA0005Entity.setModifyOn(now);
		}
		mA0005Entity.setCliAddress(Param.getStrWithDef(param, "cliAddress"));
		mA0005Entity.setCliCountryId(Param.getStrWithDef(param, "cliCountryId"));
		mA0005Entity.setCliDataStatus(Param.getStrWithDef(param, "cliDataStatus"));
		mA0005Entity.setCliEmail(Param.getStrWithDef(param, "cliEmail"));
		mA0005Entity.setCliFax(Param.getStrWithDef(param, "cliFax"));
		mA0005Entity.setCliIndustry(Param.getStrWithDef(param, "cliIndustry"));
		mA0005Entity.setCliJointDate(SIMPLE_DATE_FORMAT.formatDateId.parse(Param.getStrWithDef(param, "cliJointDate")));
		mA0005Entity.setCliLastTrx(SIMPLE_DATE_FORMAT.formatDateId.parse(Param.getStrWithDef(param, "cliLastTrx")));
		mA0005Entity.setCliName(Param.getStrWithDef(param, "cliName"));
		mA0005Entity.setCliOwn("1");
		mA0005Entity.setCliPic1(Param.getStrWithDef(param, "cliPic1"));
		mA0005Entity.setCliPic1Email(Param.getStrWithDef(param, "cliPic1Email"));
		mA0005Entity.setCliPic1Telp(Param.getStrWithDef(param, "cliPic1Telp"));
		mA0005Entity.setCliPic2(Param.getStrWithDef(param, "cliPic2"));
		mA0005Entity.setCliPic2Email(Param.getStrWithDef(param, "cliPic2Email"));
		mA0005Entity.setCliPic2Telp(Param.getStrWithDef(param, "cliPic12elp"));
		mA0005Entity.setCliPostCode(Param.getStrWithDef(param, "cliPostCode"));
		mA0005Entity.setCliRemarks(Param.getStrWithDef(param, "cliRemarks"));
		mA0005Entity.setCliTaxId(Param.getStrWithDef(param, "cliTaxId"));
		mA0005Entity.setCliTaxPkp(Param.getStrWithDef(param, "cliTaxPkp"));
		mA0005Entity.setCliTelepon(Param.getStrWithDef(param, "cliTelepon"));
		mA0005Entity.setCliType(Param.getStrWithDef(param, "cliType"));
		mA0005Entity.setCliWeb(Param.getStr(param, "cliWeb"));
		mA0005Repo.save(mA0005Entity);
	}

	public String cliDataStatusStr(List<MA0014Entity> listMA0014Entity, String cliDataStatus){
		for (MA0014Entity ma0014Entity : listMA0014Entity) {
			if(ma0014Entity.getPaChildValue().equals(cliDataStatus)) {
				return ma0014Entity.getPaChildDesc();
			}
		}
		return null;
	}
	
	private String generateCliCode() {
		String code = String.valueOf(Integer.parseInt(mA0005Repo.findMaxCliCode())+1);
		while(true) {
			if(code.length() == 5) {
				break;
			}
			code="0".concat(code);
		}
		return code;
	}
}
