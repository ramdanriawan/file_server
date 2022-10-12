package com.biru.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biru.common.param.Param;
import com.biru.component.EndorcementComponent;
import com.biru.component.ResultComponent;
import com.biru.entity.MA0005Entity;
import com.biru.entity.TR0003Entity;
import com.biru.entity.TR0006BEntity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0012Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0006BRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0012Repo;
import com.biru.service.DCNotesService;
import com.biru.service.EndorsementService;

@Service
public class EndorsementServiceImpl implements EndorsementService {

	@Autowired
	private EndorcementComponent endorcementComponent;

	@Autowired
	private ResultComponent resultComponent;

	@Autowired
	private TR0003Repo tR0003Repo;

	@Autowired
	private DCNotesService dcNotesService;
	
	@Autowired
	private TR0006Repo tR0006Repo;
	
	@Autowired
	private TR0006BRepo tR0006BRepo;
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private TR0012Repo tr0012Repo;

	private SimpleDateFormat sdfDateTime;
	
	private static final String RQ 		= "RQ";
	private static final String SE 		= "SE";
	private static final String PU 		= "PU";
	private static final String S_11 	= "11";
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	public EndorsementServiceImpl() {
		this.sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	}
	
	@Override
	public Object cancelClosing(Map<String, Object> param) {
		try {
			logger.info("Cancel closing (Modify Facultative) with param : {}.", param);
			
			return endorcementComponent.cancelClosing(param);
		}catch(Exception e) {
			e.printStackTrace();
			return resultComponent.createResponse(e);
		}
	}

	@Override
	public Object save(Map<String, Object> param) {
		try {
			endorcementComponent.save(param);
			return resultComponent.createResponse(null);
		}catch(Exception e) {
			e.printStackTrace();
			return resultComponent.createResponse(e);
		}
	}

	@Override
	public Object createClosingReport(Map<String, Object> param) throws Exception {
		String trxVoucherId = Param.getStr(param, "trxVoucherId");
		String createOn = Param.getStrWithDef(param, "createOn");
		
		logger.info("Param createClosingReport = {}", param);
		
		Date dateCreateOn = sdfDateTime.parse(createOn);
		List<TR0003Entity> listTR0003Entity = tR0003Repo.findByTrxDescriptionContainsAndTrxStatusDataEqualsAndCreateOnEquals(trxVoucherId, "11", dateCreateOn);
		
		List<String> report = new ArrayList<String>();
		for (TR0003Entity tR0003Entity : listTR0003Entity) {
			Map<String, String> paramReport = new HashMap<String, String>();
			paramReport.put("voucherId", tR0003Entity.getTrxVoucherId());
			paramReport.put("type", tR0003Entity.getTrxType());
			paramReport.put("trxVoucherId", trxVoucherId);
			report.add(dcNotesService.createDCNotesHtml(paramReport));
		}

		Map<String, Object> result = new HashMap<String, Object>();
		result.put("report", report);

		return result;
	}

	@Override
	public Object inquirySendTable(Map<String, Object> param) throws Exception {
		String trxVoucherId = Param.getStrWithDef(param, "trxVoucherId");
		String trxOldVoucherId = Param.getStrWithDef(param, "trxOldVoucherId");
		
		//for get data closing one transaction
		Long millisInqTr12CreateOn = Param.getLong(param, "millisInqTr12CreateOn");
		Calendar calInqTr12CreateOn = Calendar.getInstance();
		
		Map<String, Object> result = new HashMap<String, Object>();
		List<Map<String, Object>> table = new ArrayList<Map<String, Object>>();
		
		List<TR0006Entity> tClientInformation = tR0006Repo.findByTrxTrxIdAndTrxVoucherId(RQ, trxVoucherId);
		for(TR0006Entity clientInformation : tClientInformation) {
			MA0005Entity client = mA0005Repo.findByCliCode(clientInformation.getTrxClient());
			
			//Cover - Client
			Map<String, Object> coverObj = new HashMap<String, Object>();
			coverObj.put("name", client.getCliName());
			coverObj.put("document", "Cover - Client");
			
			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'evidence', 'cover')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'evidence', 'cover', 'Cover - " + client.getCliName() + "')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			coverObj.put("action", action);
			table.add(coverObj);
			
			//Evidence of Cover
			Map<String, Object> evidenceObj = new HashMap<String, Object>();
			evidenceObj.put("name", client.getCliName());
			evidenceObj.put("document", "Evidence of Cover");
			
			action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'evidenceofcover', 'evidenceofcover')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + clientInformation.getTrxClient() + "', 'evidenceofcover', 'evidenceofcover', 'Evidence of Cover " + client.getCliName() + "')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			evidenceObj.put("action", action);
			table.add(evidenceObj);

			int insSub = 1;
			calInqTr12CreateOn.setTimeInMillis(millisInqTr12CreateOn);
			//trxVoucherId = trxOldVoucherId (production)
			//trxVoucherId != trxOldVoucherId (endorsement)
			String voucherId = trxVoucherId.equals(trxOldVoucherId) ? trxVoucherId : trxOldVoucherId;
			while(Boolean.TRUE) {	//handle multiple interest
				Date inqTr12CreateOn = calInqTr12CreateOn.getTime();		
				List<TR0012Entity> tr12Entities = tr0012Repo
						.findTr12ReprintModifyFacultative(RQ, voucherId, SE, S_11, 
								clientInformation.getTrxClient(), inqTr12CreateOn);

				if(tr12Entities.size() == 0)
					break;
				logger.info("Client '{}' with inqTr12CreateOn : '{}', insSub : '{}', size : '{}'.", 
						client.getCliName(), sdfDateTime.format(inqTr12CreateOn), insSub, tr12Entities.size());
				
				for(TR0012Entity tr12 : tr12Entities) {					
					List<TR0003Entity> tr3Entities = tR0003Repo
							.findTr3ReprintModifyFacultative(voucherId, tr12.getTrxClient(), tr12.getCreateOn());
					for(TR0003Entity tr3 : tr3Entities) {
						Map<String, Object> journal = new HashMap<String, Object>();
						journal.put("voucherId", tr3.getTrxVoucherId());
						journal.put("name", client.getCliName());
						journal.put("document", "Closing - DN");
						journal.put("insSub", insSub);
						journal.put("type", "DEBIT NOTE");
						journal.put("isComm", Boolean.FALSE);
						
						String fn = "Closing - DN - " + client.getCliName();
						
						action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + insSub + "', '" + client.getCliName() + "', 'DEBIT NOTE')\">" 
								+ "<i class=\"fa fa-print\"></i>" 
								+ "</button>&nbsp;";
						action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" +  tr3.getTrxVoucherId() + "', '" + insSub + "', '" + client.getCliName() + "', '" + fn + "', 'DEBIT NOTE')\">" 
								+ "<i class=\"fas fa-file-word\"></i>" 
								+ "</button>";
						
						journal.put("action", action);
						table.add(journal);
					}
				}
				
				insSub++;
				calInqTr12CreateOn.add(Calendar.SECOND, 1);
			}
		}
		
		List<TR0006BEntity> tReinsurance = tR0006BRepo.findByTrxTrxIdAndTrxVoucherId(RQ, trxVoucherId);
		for(TR0006BEntity reins : tReinsurance) {
			MA0005Entity client = mA0005Repo.findByCliCode(reins.getTrxInsId());
			
			//Cover - Reinsurance
			Map<String, Object> coverObj = new HashMap<String, Object>();
			coverObj.put("name", client.getCliName());
			coverObj.put("document", "Cover - Reinsurance");
			
			String action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + reins.getTrxInsId() + "', 'placing', 'cover')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + reins.getTrxInsId() + "', 'placing', 'cover', 'Cover - " + client.getCliName() + "')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			coverObj.put("action", action);
			table.add(coverObj);
			
			Map<String, Object> placingObj = new HashMap<String, Object>();
			placingObj.put("name", client.getCliName());
			placingObj.put("document", "Closing Slip");
			
			action = "<button class=\"btn btn-secondary\" onclick=\"printPreview('" + trxVoucherId + "', '" + reins.getTrxInsId() + "', 'closingslip', 'closingslip')\">" 
					+ "<i class=\"fa fa-print\"></i>" 
					+ "</button>&nbsp;";
			action += "<button class=\"btn btn-primary\" onclick=\"exportPreview('" + trxVoucherId + "', '" + reins.getTrxInsId() + "', 'closingslip', 'closingslip', 'Closing Slip - " + client.getCliName() + "')\">" 
					+ "<i class=\"fas fa-file-word\"></i>" 
					+ "</button>";
			
			placingObj.put("action", action);
			table.add(placingObj);
			
			int insSub = 1;
			calInqTr12CreateOn.setTimeInMillis(millisInqTr12CreateOn);
			//trxVoucherId = trxOldVoucherId (production)
			//trxVoucherId != trxOldVoucherId (endorsement)
			String voucherId = trxVoucherId.equals(trxOldVoucherId) ? trxVoucherId : trxOldVoucherId;
			while(Boolean.TRUE) {	//handle multiple interest
				Date inqTr12CreateOn = calInqTr12CreateOn.getTime();	
				List<TR0012Entity> tr12Entities = tr0012Repo
						.findTr12ReprintModifyFacultative(RQ, voucherId, PU, S_11, 
								reins.getTrxInsId(), inqTr12CreateOn);

				if(tr12Entities.size() == 0)
					break;
				logger.info("Reins '{}' with inqTr12CreateOn : '{}', insSub : '{}', size : '{}'.", 
						client.getCliName(), sdfDateTime.format(inqTr12CreateOn), insSub, tr12Entities.size());
				
				for(TR0012Entity tr12 : tr12Entities) {					
					List<TR0003Entity> tr3Entities = tR0003Repo
							.findTr3ReprintModifyFacultative(voucherId, tr12.getTrxClient(), tr12.getCreateOn());
					for(TR0003Entity tr3 : tr3Entities) {
						Map<String, Object> journal = new HashMap<String, Object>();
						journal.put("voucherId", tr3.getTrxVoucherId());
						journal.put("name", client.getCliName());
						journal.put("document", "Closing - CN");
						journal.put("insSub", insSub);
						journal.put("type", "CREDIT NOTE");
						journal.put("isComm", Boolean.FALSE);
						
						String fn = "Closing - CN - " + client.getCliName();
						
						action = "<button class=\"btn btn-secondary\" onclick=\"printPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + insSub + "', '" + client.getCliName() + "', 'CREDIT NOTE')\">" 
								+ "<i class=\"fa fa-print\"></i>" 
								+ "</button>&nbsp;";
						action += "<button class=\"btn btn-primary\" onclick=\"exportPreviewClosing('" + tr3.getTrxVoucherId() + "', '" + insSub + "', '" + client.getCliName() + "', '" + fn + "', 'CREDIT NOTE')\">" 
								+ "<i class=\"fas fa-file-word\"></i>" 
								+ "</button>";
						
						journal.put("action", action);
						table.add(journal);
					}
				}
				
				insSub++;
				calInqTr12CreateOn.add(Calendar.SECOND, 1);
			}
		}
		
		result.put("table", table);
		
		String remarks = Param.getStr(param, "remarks");
		tR0006Repo.updateTrxDataStatus(S_11, remarks, RQ, trxVoucherId);
		
		return result;
	}

}
