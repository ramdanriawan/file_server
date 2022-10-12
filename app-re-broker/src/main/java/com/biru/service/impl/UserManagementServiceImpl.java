package com.biru.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.param.Param;
import com.biru.component.EmailUtils;
import com.biru.component.ResultComponent;
import com.biru.entity.MA0013Entity;
import com.biru.entity.MA0014Entity;
import com.biru.entity.MA0017Entity;
import com.biru.entity.MA0018Entity;
import com.biru.entity.US0001Entity;
import com.biru.entity.US0003Entity;
import com.biru.repository.MA0012Repo;
import com.biru.repository.MA0013Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.MA0017Repo;
import com.biru.repository.MA0018Repo;
import com.biru.repository.US0001Repo;
import com.biru.repository.US0003Repo;
import com.biru.repository.US0004Repo;
import com.biru.service.BusinessRulesService;
import com.biru.service.ParameterService;
import com.biru.service.UserManagementService;

@Service
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	private MA0013Repo ma0013repo;

	@Autowired
	private MA0014Repo ma0014repo;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private MA0012Repo ma0012repo;

	@Autowired
	private US0003Repo us3Repo;

	@Autowired
	private US0004Repo us4Repo;

	@Autowired
	private US0001Repo us1Repo;

	@Autowired
	private ResultComponent resultComponent;
	
	@Autowired
	private EmailUtils emailUtils;

	@Override
	public Object save(Map<String, Object> p_Param) {
		String groupId = Param.getStr(p_Param, "groupId");
		String groupName = Param.getStr(p_Param, "groupName");
		String uiId = Param.getStr(p_Param, "uiId");

		Long idKey = Param.getLong(p_Param, "paIdKey");
		idKey = (idKey == null) ? -99l : idKey;
		US0003Entity us3 = null;
		System.out.println(uiId);
		String user = Param.getStr(p_Param, Param.USER);

		if (uiId.equals("ALL")) {
			List<DropdownIdText> lss = us4Repo.getDropDown();
			System.out.println(lss.size());
			for (int i = 0; i < lss.size(); i++) {
				us3 = new US0003Entity(groupId, groupName, lss.get(i).getId(), user, new Date(), user, new Date());
				if (us3Repo.findByUiIdAndGroupId(lss.get(i).getId(), groupId) == null) {
					us3Repo.save(us3);
				}
			}
		} else {
			us3 = us3Repo.findByUiIdAndGroupId(uiId, groupId);
			if (us3 == null) {
				us3 = new US0003Entity(groupId, groupName, uiId, user, new Date(), user, new Date());
			}
			us3.setGroupId(groupId);
			us3.setGroupName(groupName);
			us3.setUiId(uiId);

			us3Repo.save(us3);

		}

		HashMap<String, Object> returnValue = new HashMap<String, Object>();
		returnValue.put("status", "success");

		return returnValue;
	}

	@Override
	public DatatableSet inquiry(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		Pageable pageable = PageRequest.of(offset / limit, limit, Sort.Direction.fromString(order), sort);
		Page<US0003Entity> data = null;
		data = us3Repo.findAlls(pageable);
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public DatatableSet inquiryChild(Map<String, Object> p_Param) {
		System.out.println(p_Param);
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		String groupId = Param.getStr(p_Param, "groupId");
		Pageable pageable = PageRequest.of(offset / limit, limit, Sort.Direction.fromString(order), sort);

		Page<US0003Entity> data = us3Repo.findByGroupId(groupId, pageable);
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object getDropDown() {
		return us4Repo.getDropDown();
	}

	@Override
	public Object delete(Map<String, Object> param) {
		// TODO Auto-generated method stub
		try {
			deleteProcess(param);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			// TODO: handle exception
			return resultComponent.createResponse(e);
		}

	}

	private void deleteProcess(Map<String, Object> param) {
		Long idKey = Long.parseLong(Param.getStr(param, "idKey"));
		us3Repo.deleteById(idKey);
	}

	@Override
	public DatatableSet userInquiry(Map<String, Object> p_Param) {
		Integer limit = Param.getInt(p_Param, PARAM.LIMIT);
		Integer offset = Param.getInt(p_Param, PARAM.OFFSET);
		String order = Param.getStr(p_Param, PARAM.ORDER);
		String sort = Param.getStr(p_Param, PARAM.SORT);
		Pageable pageable = PageRequest.of(offset / limit, limit, Sort.Direction.fromString(order), sort);
		Page<US0001Entity> data = null;
		data = us1Repo.findAlls(pageable);
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	/**
	 * @throws Exception 
	 *
	 */
	@Override
	@Transactional
	public Object userSave(Map<String, Object> p_Param) throws Exception {
		String user = Param.getStr(p_Param, Param.USER);
		String usCode = Param.getStr(p_Param, "userId");
		String usName = Param.getStr(p_Param, "userName");
		String groupId = Param.getStr(p_Param, "groupId");
		String usLevel = Param.getStr(p_Param, "level");
		String usUnit = Param.getStr(p_Param, "unit");
		String usStatus = Param.getStr(p_Param, "status");
		String create = Param.getStr(p_Param, "createdDate");
		String usEmail = Param.getStr(p_Param, "email");
		String ex = Param.getStr(p_Param, "expiredDate");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date createOn = sdf.parse(create);
		Date usExpiredDate = sdf.parse(ex);
		Long idKey = Param.getLong(p_Param, "idKey");
		String pass = generateCommonLangPassword();
		String usPass = bCryptPasswordEncoder.encode(pass);
		Boolean nullkah = false;
		US0001Entity us1;
		HashMap<String, Object> returnValue = new HashMap<String, Object>();
		if (idKey == null) {
			nullkah = true;
			us1 = new US0001Entity();
		} else {
			us1 = us1Repo.findById(idKey).get();
		}

		if (nullkah == true && us1Repo.findByUsCode(usCode) == null) {
			us1.setUsCode(usCode);
			us1.setCreateOn(createOn);
			us1.setCreateBy(user);
			us1.setUsErrCount(0);
		} else if (nullkah == true && us1Repo.findByUsCode(usCode) != null) {
			returnValue.put("status", "failed | duplicate User Code");
			return returnValue;
		}
		us1.setUsName(usName);
		us1.setGroupId(groupId);
		us1.setUsLevel(usLevel);
		us1.setUsUnit(usUnit);
		us1.setUsStatus(usStatus);
		us1.setUsEmail(usEmail);
		if(us1.getUsPass() == null) {
			us1.setUsPass(usPass);
		}
		us1.setUsExpiredDate(usExpiredDate);
		us1.setModifyBy(user);
		us1.setModifyOn(new Date());

		us1Repo.save(us1);

		returnValue.put("status", "success");
		if(usStatus.equals("11")) {
			returnValue.put("userStatus", usStatus);
			returnValue.put("password", pass);
			returnValue.put("groupId", groupId);
			returnValue.put("username", usCode);
			System.out.println("Password untuk User "+usCode+" berhasil di-create atau diaktifkan kembali. Silahkan login kembali menggunakan password : "+pass);
			
			JavaMailSender sender = emailUtils.getMailSender();
			MimeMessage msg = sender.createMimeMessage();
			
			MimeMessageHelper message = emailUtils.getMimeMessageHelper(msg);
			message.setTo(usEmail);
	        message.setSubject("Create / Acivate User"); 
	        message.setText("Password untuk User "+usCode+" berhasil di-create atau diaktifkan kembali. Silahkan login menggunakan password : "+pass);
	        sender.send(msg);
		}else {
			returnValue.put("username", usCode);
		}
		

		return returnValue;

	}

	@Override
	public Object getGroupDropDown() {
		return us3Repo.getDropDown();
	}

	@Override
	public Object getUserLevelDropDown() {
		return ma0014repo.findUserLevelDropDown();
	}

	@Override
	public Object getMplDropDown() {
		return ma0014repo.mplUnitDropDown();
	}

	@Override
	public Object getStdataDropDown() {
		return ma0014repo.stDataDropDown();
	}

	@Override
	public Object getSaDropDown() {
		return ma0012repo.getDropdownFounder();
	}

	public String generateCommonLangPassword() {
		return UUID.randomUUID().toString().substring(0, 8);
	}
}
