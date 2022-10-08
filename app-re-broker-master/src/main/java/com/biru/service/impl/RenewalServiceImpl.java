package com.biru.service.impl;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.MimeMessage;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.biru.ReBrokerConstants.PARAM;
import com.biru.common.entity.DatatableSet;
import com.biru.common.param.Param;
import com.biru.component.EmailUtils;
import com.biru.component.RenewalComponent;
import com.biru.component.ReportUtils;
import com.biru.component.ResultComponent;
import com.biru.component.VoucherComponent;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0010Entity;
import com.biru.entity.TR0019Entity;
import com.biru.entity.ViewInqRenewalEntity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0010Repo;
import com.biru.repository.MA0014Repo;
import com.biru.repository.TR0019Repo;
import com.biru.repository.ViewInqRenewalRepo;
import com.biru.service.CommonService;
import com.biru.service.RenewalService;
@Service
public class RenewalServiceImpl implements RenewalService {
	@Autowired
	private ViewInqRenewalRepo viewInqRenewalRepo;

	@Autowired
	private TR0019Repo tR0019Repo;

	@Autowired
	private VoucherComponent voucherComponent;

	@Autowired
	private CommonService  commonService;

	@Autowired
	private ResultComponent resultComponent;

	@Autowired
	private RenewalComponent renewalComponent;
	
	@Autowired
	private EmailUtils emailUtils;
	
	@Autowired
	private ReportUtils reportUtils;
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private MA0014Repo mA0014Repo;
	
	@Autowired
	private MA0010Repo mA0010Repo;

	@Override
	@Transactional
	public Object inquiry(Map<String, Object> param) throws Exception {
		SimpleDateFormat formatDateId = new SimpleDateFormat("dd/MM/yyyy");



		Integer limit = Param.getInt(param, PARAM.LIMIT);
		Integer offset = Param.getInt(param, PARAM.OFFSET);
		String order = Param.getStr(param, PARAM.ORDER);
		String sort = Param.getStr(param, PARAM.SORT);
		if(sort.equals("trxInsEndStr")) {
			sort = sort.replace("Str", "");
		}
		String typeOfCover = Param.getStr(param, "typeOfCover");
		Date expiryDate = formatDateId.parse(Param.getStr(param, "expiryDate"));
		Date to = formatDateId.parse(Param.getStr(param, "to"));
		String client = Param.getStrWithDef(param, "client");
		String status = Param.getStr(param, "status");

		System.out.println(param);
		if(typeOfCover.equals("ALL")){
			typeOfCover = "%%";
		}

		if(client.equals("ALL")){
			client = "%%";
		}

		if (status.equals("ALL")) {
			status = "%%";
		}


		Pageable pageable = PageRequest.of(offset / limit, limit, Sort.Direction.fromString(order), sort);

		Page<ViewInqRenewalEntity> data = viewInqRenewalRepo.findByTrxCoverCodeLikeAndCliNameLikeAndTrxInsEndBetweenAndTcRenewableLike(typeOfCover, client, expiryDate, to, status, pageable);

		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		for (ViewInqRenewalEntity viewInqRenewalEntity : data.getContent()) {
			TR0019Entity tR0019Entity = tR0019Repo.findByTrxOldVoucherId(viewInqRenewalEntity.getTrxVoucherId());
			if(tR0019Entity == null) {
				System.out.println("findByTrxOldVoucherId ("+viewInqRenewalEntity.getTrxVoucherId()+") is null");
				TR0019Entity tr19 = new TR0019Entity();
				tr19.setRnwDate(commonService.getAppDate());
				tr19.setTrxOldVoucherId(viewInqRenewalEntity.getTrxVoucherId());
				tr19.setCoCode("");
				tr19.setTrxType(viewInqRenewalEntity.getTrxType());
				tr19.setTrxVoucherId(voucherComponent.saveVoucherCounter(sdf.format(commonService.getAppDate())));
				tr19.setRnwStatus("0");
				tR0019Repo.save(tr19);
				
				viewInqRenewalEntity.setAction("<button class=\"btn btn-secondary\" onclick=\"edit('"+viewInqRenewalEntity.getIdKey()+"')\">" 
						+ "<i class=\"fa fa-edit\"></i>" 
						+ "</button>"
						+ "&nbsp;<button class=\"btn btn-success\" onclick=\"send('"+viewInqRenewalEntity.getIdKey()+"')\">" 
						+ "<i class=\"fa fa-send\"></i>" 
						+ "</button>"
						+ "&nbsp;<button class=\"btn btn-danger\" onclick=\"remove('"+viewInqRenewalEntity.getIdKey()+"')\">" 
						+ "<i class=\"fa fa-trash\"></i>" 
						+ "</button>");
			}else {
				if(tR0019Entity.getRnwStatus().equals("0") ) {
					viewInqRenewalEntity.setAction("<button class=\"btn btn-secondary\" onclick=\"edit('"+viewInqRenewalEntity.getIdKey()+"')\">" 
							+ "<i class=\"fa fa-edit\"></i>" 
							+ "</button>"
							+ "&nbsp;<button class=\"btn btn-success\" onclick=\"send('"+viewInqRenewalEntity.getIdKey()+"')\">" 
							+ "<i class=\"fa fa-send\"></i>" 
							+ "</button>"
							+ "&nbsp;<button class=\"btn btn-danger\" onclick=\"remove('"+viewInqRenewalEntity.getIdKey()+"')\">" 
							+ "<i class=\"fa fa-trash\"></i>" 
							+ "</button>");
				}else {
					viewInqRenewalEntity.setAction("<button class=\"btn btn-secondary\" disabled=\"true\"onclick=\"edit('"+viewInqRenewalEntity.getIdKey()+"')\">" 
							+ "<i class=\"fa fa-edit\"></i>" 
							+ "</button>"
							+ "&nbsp;<button class=\"btn btn-success\" disabled=\"true\" onclick=\"send('"+viewInqRenewalEntity.getIdKey()+"')\">" 
							+ "<i class=\"fa fa-send\"></i>" 
							+ "</button>"
							+ "&nbsp;<button class=\"btn btn-danger\" disabled=\"true\" onclick=\"remove('"+viewInqRenewalEntity.getIdKey()+"')\">" 
							+ "<i class=\"fa fa-trash\"></i>" 
							+ "</button>");
				}
			}

		}
		return new DatatableSet(data.getTotalElements(), data.getTotalElements(), data.getContent());
	}

	@Override
	public Object remove(Map<String, Object> param) throws Exception {
		try {
			renewalComponent.remove(param);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return resultComponent.createResponse(e);
		}
	}

	@Override
	public Object edit(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		try {
			renewalComponent.edit(param);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return resultComponent.createResponse(e);
		}
	}

	@Override
	public Object send(Map<String, Object> param) throws Exception {
		try {
			// TODO Auto-generated method stub
			String idKey = param.get("idKey").toString();
			ViewInqRenewalEntity viewInqRenewalEntity = viewInqRenewalRepo.findById(Long.parseLong(idKey)).get();
			MA0005Entity mA0005Entity= mA0005Repo.findByCliCode(viewInqRenewalEntity.getTrxClient());
			
			String htmlContent = "";
			Path path = Paths.get(reportUtils.exportHtml("Renewal.jrxml", param));
			String content = new String(Files.readAllBytes(path));
			htmlContent += content;
			
			Path pathDox = Paths.get(reportUtils.exportDocx("Renewal.jrxml", param));
			byte[] byteDox = Files.readAllBytes(pathDox);
			
			JavaMailSender jms = emailUtils.getMailSender();
			MimeMessage message = jms.createMimeMessage();
			MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, true, "utf-8");
			mimeMessageHelper.setSubject("Reminder Letter"); 
			
//			mimeMessageHelper.setTo("hakimlukman9@gmail.com");
			List<String> addresses = new ArrayList<String>();
			if(mA0005Entity.getCliEmail() != null && !mA0005Entity.getCliEmail().equals("")) {
				addresses.add(mA0005Entity.getCliEmail());
			}
			if(mA0005Entity.getCliPic1Email() != null && !mA0005Entity.getCliPic1Email().equals("")) {
				addresses.add(mA0005Entity.getCliPic1Email());
			}
			
			if(!addresses.isEmpty()) {
				String[] addressesAray = addresses.toArray(new String[addresses.size()]);
				mimeMessageHelper.setTo(addressesAray);
			}
			
//			mimeMessageHelper.setCc("lukmanulh0@gmail.com");
			mimeMessageHelper.setCc(mA0014Repo.findByPaChildCode("EMAILRC001").getPaChildValue());
			mimeMessageHelper.setText(htmlContent, true);
			mimeMessageHelper.addAttachment("Renewal.docx", new ByteArrayResource(byteDox));
	        
	        
			jms.send(message);
	        
	        Files.delete(path);
			return resultComponent.createResponse(null);
		} catch (Exception e) {
			e.printStackTrace();
			return resultComponent.createResponse(e);
		}
		
	}

	@Override
	public Object export(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		
		SimpleDateFormat formatDateId = new SimpleDateFormat("dd/MM/yyyy");
		Optional<MA0010Entity> company = mA0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		
		param.put("companyName", companyName);
		
		String typeOfCover = Param.getStr(param, "typeOfCover");
		Date expiryDate = formatDateId.parse(Param.getStr(param, "expiryDate"));
		Date to = formatDateId.parse(Param.getStr(param, "to"));
		String client = Param.getStrWithDef(param, "client");
		String status = Param.getStr(param, "status");

		System.out.println(param);
		if(typeOfCover.equals("ALL")){
			typeOfCover = "%%";
		}

		if(client.equals("ALL")){
			client = "%%";
		}

		if (status.equals("ALL")) {
			status = "%%";
		}

		List<String> listIdKeys = new ArrayList<String>();
		int page = 0;
		while (true) {
			Pageable pageable = PageRequest.of(page, 5, Sort.Direction.fromString("asc"), "trxInsEnd");
			Page<ViewInqRenewalEntity> data = viewInqRenewalRepo.findByTrxCoverCodeLikeAndCliNameLikeAndTrxInsEndBetweenAndTcRenewableLike(typeOfCover, client, expiryDate, to, status, pageable);
			if(data.isEmpty()) {
				break;
			}
			
			for (ViewInqRenewalEntity viewInqRenewalEntity : data) {
				listIdKeys.add(viewInqRenewalEntity.getIdKey().toString());
			}
			page++;
		}
		
		String idKey = "";
		System.out.println(listIdKeys.size());
		for (int i = 0; i < listIdKeys.size(); i++) {
			idKey = idKey.concat(listIdKeys.get(i));
			if(i < listIdKeys.size()-1) {
				idKey = idKey.concat(",");
			}
		}
		param.put("idKey", idKey);
		param.put("expiryDate", Param.getStr(param, "expiryDate") + " - "+Param.getStr(param, "to"));
		
		System.out.println(param);
		reportUtils.compileReport("RenewalSubExcel.jrxml");
		return reportUtils.exportExcel("RenewalExcel.jrxml", param);
	}

	@Override
	public Object print(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub

		SimpleDateFormat formatDateId = new SimpleDateFormat("dd/MM/yyyy");
		Optional<MA0010Entity> company = mA0010Repo.findById(1l);
		String companyName = company.get().getCoName();
		
		param.put("companyName", companyName);
		
		String typeOfCover = Param.getStr(param, "typeOfCover");
		Date expiryDate = formatDateId.parse(Param.getStr(param, "expiryDate"));
		Date to = formatDateId.parse(Param.getStr(param, "to"));
		String client = Param.getStrWithDef(param, "client");
		String status = Param.getStr(param, "status");

		System.out.println(param);
		if(typeOfCover.equals("ALL")){
			typeOfCover = "%%";
		}

		if(client.equals("ALL")){
			client = "%%";
		}

		if (status.equals("ALL")) {
			status = "%%";
		}

		List<String> listIdKeys = new ArrayList<String>();
		int page = 0;
		while (true) {
			Pageable pageable = PageRequest.of(page, 5, Sort.Direction.fromString("asc"), "trxInsEnd");
			Page<ViewInqRenewalEntity> data = viewInqRenewalRepo.findByTrxCoverCodeLikeAndCliNameLikeAndTrxInsEndBetweenAndTcRenewableLike(typeOfCover, client, expiryDate, to, status, pageable);
			if(data.isEmpty()) {
				break;
			}
			
			for (ViewInqRenewalEntity viewInqRenewalEntity : data) {
				listIdKeys.add(viewInqRenewalEntity.getIdKey().toString());
			}
			page++;
		}
		
		String idKey = "";
		System.out.println(listIdKeys.size());
		for (int i = 0; i < listIdKeys.size(); i++) {
			idKey = idKey.concat(listIdKeys.get(i));
			if(i < listIdKeys.size()-1) {
				idKey = idKey.concat(",");
			}
		}
		param.put("idKey", idKey);
		param.put("expiryDate", Param.getStr(param, "expiryDate") + " - "+Param.getStr(param, "to"));
		
		System.out.println(param);
		reportUtils.compileReport("RenewalSubExcel.jrxml");
		return reportUtils.exportPdf("RenewalExcel.jrxml", param);
	}
}
