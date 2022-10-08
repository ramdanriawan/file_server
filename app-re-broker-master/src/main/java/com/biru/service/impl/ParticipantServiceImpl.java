package com.biru.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biru.common.param.Param;
import com.biru.component.ParticipantComponent;
import com.biru.component.ResultComponent;
import com.biru.entity.MA0005Entity;
import com.biru.entity.MA0014Entity;
import com.biru.repository.MA0005Repo;
import com.biru.repository.MA0014Repo;
import com.biru.service.ParticipantService;

@Service
public class ParticipantServiceImpl implements ParticipantService {
	
	@Autowired
	private ParticipantComponent participantComponent;
	
	@Autowired 
	private ResultComponent resultComponent;
	
	@Autowired
	private MA0005Repo mA0005Repo;
	
	@Autowired
	private MA0014Repo mA0014Repo;
	
	@Override
	public Object inquiry() {
		Iterable<MA0005Entity> listMA0005Entity = mA0005Repo.findAll();
		List<MA0014Entity> listMA0014Entity =  mA0014Repo.findByPaParentCodeAndPaChildStatus("STCLIENT", "11");
		
		List<MA0014Entity> paramTypes = mA0014Repo.findByPaParentCode("TYPECO");
		for(MA0005Entity ma0005Entity : listMA0005Entity) {
			MA0014Entity type = paramTypes.stream()
					.filter(param -> param.getPaChildValue().equals(ma0005Entity.getCliType()))
					.findFirst().orElse(null);
			if(type != null)
				ma0005Entity.setCliTypeDesc(type.getPaChildDesc());
			
			ma0005Entity.setCliDataStatusStr(participantComponent.cliDataStatusStr(listMA0014Entity, ma0005Entity.getCliDataStatus()));
		}
		
		return listMA0005Entity;
	}

	@Override
	public Object getParticipant(Map<String, Object> param) {
		String cliCode = Param.getStrWithDef(param, "cliCode");
		MA0005Entity participant = mA0005Repo.findByCliCode(cliCode);
		
		return participant;
	}

	@Override
	public Object save(Map<String, Object> param) {
		try {
			participantComponent.save(param);
		}catch(Exception e) {
			e.printStackTrace();
			resultComponent.createResponse(e);
		}
		
		return resultComponent.createResponse(null);
	}
	
}	
