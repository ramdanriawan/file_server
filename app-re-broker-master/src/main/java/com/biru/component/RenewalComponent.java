package com.biru.component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.transaction.Transactional;

import org.apache.commons.lang3.SerializationUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.biru.common.param.Param;
import com.biru.entity.TR0003Entity;
import com.biru.entity.TR0006AEntity;
import com.biru.entity.TR0006BEntity;
import com.biru.entity.TR0006CEntity;
import com.biru.entity.TR0006DEntity;
import com.biru.entity.TR0006EEntity;
import com.biru.entity.TR0006Entity;
import com.biru.entity.TR0006FEntity;
import com.biru.entity.TR0006GEntity;
import com.biru.entity.TR0006HEntity;
import com.biru.entity.TR0006IEntity;
import com.biru.entity.TR0019Entity;
import com.biru.repository.TR0003Repo;
import com.biru.repository.TR0006ARepo;
import com.biru.repository.TR0006BRepo;
import com.biru.repository.TR0006CRepo;
import com.biru.repository.TR0006DRepo;
import com.biru.repository.TR0006ERepo;
import com.biru.repository.TR0006FRepo;
import com.biru.repository.TR0006GRepo;
import com.biru.repository.TR0006HRepo;
import com.biru.repository.TR0006IRepo;
import com.biru.repository.TR0006Repo;
import com.biru.repository.TR0019Repo;
import com.biru.service.CommonService;

@Component
public class RenewalComponent {

	@Autowired
	private TR0003Repo tR0003Repo;
	
	@Autowired
	private TR0019Repo tR0019Repo;
	
	@Autowired
	private TR0006Repo tR0006Repo;
	@Autowired
	private TR0006ARepo tR0006ARepo;
	@Autowired
	private TR0006BRepo tR0006BRepo;
	@Autowired
	private TR0006CRepo  tR0006CRepo;
	@Autowired
	private TR0006DRepo tR0006DRepo;
	@Autowired
	private TR0006ERepo tR0006ERepo;
	@Autowired
	private TR0006FRepo tR0006FRepo;
	@Autowired
	private TR0006GRepo tR0006GRepo;
	@Autowired
	private TR0006HRepo tR0006HRepo;
	@Autowired
	private TR0006IRepo tR0006IRepo;
	
	@Autowired
	private VoucherComponent voucherComponent;
	
	@Autowired
	private CommonService commonService;
	
	@Transactional
	public void remove(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String idKey = Param.getStr(param, "idKey");
		TR0003Entity tR0003Entity = tR0003Repo.findById(Long.parseLong(idKey)).get();

		TR0019Entity tR0019Entity = tR0019Repo.findByTrxOldVoucherId(tR0003Entity.getTrxVoucherId());
		tR0019Entity.setRnwStatus("1");
		tR0019Repo.save(tR0019Entity);
	}
	
	@Transactional
	public void edit(Map<String, Object> param) throws Exception {
		// TODO Auto-generated method stub
		String idKey = Param.getStr(param, "idKey");
		String user = Param.getStr(param, "user");
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String newVoucherId = voucherComponent.saveVoucherCounter(sdf.format(commonService.getAppDate()));
		
		TR0003Entity tR0003Entity = tR0003Repo.findById(Long.parseLong(idKey)).get();
		
		TR0019Entity tR0019Entity = tR0019Repo.findByTrxOldVoucherId(tR0003Entity.getTrxVoucherId());
		tR0019Entity.setRnwStatus("2");
		tR0019Repo.save(tR0019Entity);
		
		TR0006Entity tR0006Entity = tR0006Repo.findByTrxVoucherIdAndTrxDataStatus(tR0003Entity.getTrxOldVoucherId(), "11");
		if(tR0006Entity !=null) {
			TR0006Entity tR0006EntityClone = SerializationUtils.clone(tR0006Entity);
			tR0006EntityClone.setIdKey(UUID.randomUUID().toString());
			tR0006EntityClone.setTrxDataStatus("0");
			tR0006EntityClone.setCreateBy(user);
			tR0006EntityClone.setCreateOn(Calendar.getInstance().getTime());
			tR0006EntityClone.setModifyBy(null);
			tR0006EntityClone.setModifyOn(null);
			tR0006EntityClone.setTrxVoucherId(newVoucherId);
			tR0006Repo.save(tR0006EntityClone);
		}
		
		List<TR0006AEntity> listTR0006AEntity = tR0006ARepo.findByTrxVoucherId(tR0003Entity.getTrxOldVoucherId());
		if(!listTR0006AEntity.isEmpty()) {
			for (TR0006AEntity tR0006AEntity : listTR0006AEntity) {
				TR0006AEntity tR0006AEntityClone = SerializationUtils.clone(tR0006AEntity);
				tR0006AEntityClone.setIdKey(UUID.randomUUID().toString());
				tR0006AEntityClone.setTrxVoucherId(newVoucherId);
				tR0006ARepo.save(tR0006AEntityClone);
			}
			
		}
		
		List<TR0006BEntity> listTR0006BEntity = tR0006BRepo.findByTrxVoucherId(tR0003Entity.getTrxOldVoucherId());
		if(!listTR0006BEntity.isEmpty()) {
			for (TR0006BEntity tR0006BEntity : listTR0006BEntity) {
				TR0006BEntity tR0006BEntityClone = SerializationUtils.clone(tR0006BEntity);
				tR0006BEntityClone.setIdKey(UUID.randomUUID().toString());
				tR0006BEntityClone.setTrxVoucherId(newVoucherId);
				tR0006BRepo.save(tR0006BEntityClone);
			}
			
		}
		List<TR0006CEntity> listTR0006CEntity = tR0006CRepo.findByTrxVoucherId(tR0003Entity.getTrxOldVoucherId());
		if(!listTR0006CEntity.isEmpty()) {
			for (TR0006CEntity tR0006CEntity : listTR0006CEntity) {
				TR0006CEntity tR0006CEntityClone = SerializationUtils.clone(tR0006CEntity);
				tR0006CEntityClone.setIdKey(UUID.randomUUID().toString());
				tR0006CEntityClone.setTrxVoucherId(newVoucherId);
				tR0006CRepo.save(tR0006CEntityClone);
			}
			
		}
		List<TR0006DEntity> listTR0006DEntity = tR0006DRepo.findByTrxVoucherId(tR0003Entity.getTrxOldVoucherId());
		if(!listTR0006DEntity.isEmpty()) {
			for (TR0006DEntity tR0006DEntity : listTR0006DEntity) {
				TR0006DEntity tR0006DEntityClone = SerializationUtils.clone(tR0006DEntity);
				tR0006DEntityClone.setIdKey(UUID.randomUUID().toString());
				tR0006DEntityClone.setTrxVoucherId(newVoucherId);
				tR0006DRepo.save(tR0006DEntityClone);
			}
			
		}
		List<TR0006EEntity> listTR0006EEntity = tR0006ERepo.findByTrxVoucherId(tR0003Entity.getTrxOldVoucherId());
		if(!listTR0006EEntity.isEmpty()) {
			for (TR0006EEntity tR0006EEntity : listTR0006EEntity) {
				TR0006EEntity tR0006EEntityClone = SerializationUtils.clone(tR0006EEntity);
				tR0006EEntityClone.setIdKey(UUID.randomUUID().toString());
				tR0006EEntityClone.setTrxVoucherId(newVoucherId);
				tR0006ERepo.save(tR0006EEntityClone);
			}
			
		}
		List<TR0006FEntity> listTR0006FEntity = tR0006FRepo.findByTrxVoucherId(tR0003Entity.getTrxOldVoucherId());
		if(!listTR0006FEntity.isEmpty()) {
			for (TR0006FEntity tR0006FEntity : listTR0006FEntity) {
				TR0006FEntity tR0006FEntityClone = SerializationUtils.clone(tR0006FEntity);
				tR0006FEntityClone.setIdKey(UUID.randomUUID().toString());
				tR0006FEntityClone.setTrxVoucherId(newVoucherId);
				tR0006FRepo.save(tR0006FEntityClone);
			}
			
		}
		List<TR0006GEntity> listTR0006GEntity = tR0006GRepo.findByTrxVoucherId(tR0003Entity.getTrxOldVoucherId());
		if(!listTR0006GEntity.isEmpty()) {
			for (TR0006GEntity tR0006AEntity : listTR0006GEntity) {
				TR0006GEntity tR0006GEntityClone = SerializationUtils.clone(tR0006AEntity);
				tR0006GEntityClone.setIdKey(UUID.randomUUID().toString());
				tR0006GEntityClone.setTrxVoucherId(newVoucherId);
				tR0006GRepo.save(tR0006GEntityClone);
			}
			
		}
		List<TR0006HEntity> listTR0006HEntity = tR0006HRepo.findByTrxVoucherId(tR0003Entity.getTrxOldVoucherId());
		if(!listTR0006HEntity.isEmpty()) {
			for (TR0006HEntity tR0006HEntity : listTR0006HEntity) {
				TR0006HEntity tR0006HEntityClone = SerializationUtils.clone(tR0006HEntity);
				tR0006HEntityClone.setIdKey(UUID.randomUUID().toString());
				tR0006HEntityClone.setTrxVoucherId(newVoucherId);
				tR0006HRepo.save(tR0006HEntityClone);
			}
			
		}
		List<TR0006IEntity> listTR0006IEntity = tR0006IRepo.findByTrxVoucherId(tR0003Entity.getTrxOldVoucherId());
		if(!listTR0006IEntity.isEmpty()) {
			for (TR0006IEntity tR0006IEntity : listTR0006IEntity) {
				TR0006IEntity tR0006IEntityClone = SerializationUtils.clone(tR0006IEntity);
				tR0006IEntityClone.setIdKey(UUID.randomUUID().toString());
				tR0006IEntityClone.setTrxVoucherId(newVoucherId);
				tR0006IRepo.save(tR0006IEntityClone);
			}
			
		}
		
		
	}
	
	
}