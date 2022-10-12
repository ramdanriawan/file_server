package com.biru.component;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.biru.common.param.Param;
import com.biru.entity.TR0001Entity;
import com.biru.entity.TR0002Entity;
import com.biru.entity.TR0006HEntity;
import com.biru.entity.TR0012Entity;
import com.biru.entity.ViewDocumentDeleteEntity;
import com.biru.repository.MA0014Repo;
import com.biru.repository.TR0001Repo;
import com.biru.repository.TR0002Repo;
import com.biru.repository.TR0006HRepo;
import com.biru.repository.TR0012Repo;
import com.biru.repository.ViewDocumentDeleteRepo;
import com.biru.service.CommonService;

@Component
public class CancelUploadComponent {

	@Autowired
	private ViewDocumentDeleteRepo viewDocumentDeleteRepo;

	@Autowired
	private TR0012Repo tR0012Repo;

	@Autowired
	private VoucherComponent voucherComponent;

	@Autowired
	private TR0001Repo tR0001Repo;

	@Autowired
	private TR0002Repo tR0002Repo;

	@Autowired
	private TR0006HRepo tR0006HRepo;

	@Value("${document.path}")
	private String path;

	@PersistenceContext
	private EntityManager em;
	
	@Autowired
	private CommonService commonService;

	@Transactional
	public void delete(Map<String, Object> param) throws Exception {
		Integer idKey = Param.getInt(param, "idKey");
		ViewDocumentDeleteEntity viewDocumentDeleteEntity =  viewDocumentDeleteRepo.findById(idKey).get();

		if(viewDocumentDeleteEntity != null) {
			TR0012Entity tR0012Entity = tR0012Repo.findByTrxVoucherId(
					String.valueOf(viewDocumentDeleteEntity.getTrxVoucherId()));

			if(tR0012Entity != null) {
				tR0012Entity.setTrxDataStatus("12");
				tR0012Repo.save(tR0012Entity);

				String newVoucherId = voucherComponent.saveVoucherCounter(commonService.getAppDateStr());

				TR0001Entity tR0001Entity = tR0001Repo.findByGlTypeAndGlVoucherId(tR0012Entity.getTrxType(), tR0012Entity.getTrxVoucherId());
				em.detach(tR0001Entity);
				if(tR0001Entity != null) {
					tR0001Entity.setIdKey(UUID.randomUUID().toString());
					tR0001Entity.setGlVoucherId(newVoucherId);
					tR0001Entity.setGlTrxDesc("CANCEL - ".concat(tR0001Entity.getGlTrxDesc()));
					tR0001Repo.save(tR0001Entity);
				}


				List<TR0002Entity> listTR0002Entity = tR0002Repo.findByGlTypeAndGlVoucherId(tR0012Entity.getTrxType(), tR0012Entity.getTrxVoucherId());
				for (TR0002Entity tR0002Entity : listTR0002Entity) {
					em.detach(tR0002Entity);
					BigDecimal glOrgDebit = tR0002Entity.getGlOrgDebit();
					BigDecimal glOrgCredit = tR0002Entity.getGlOrgCredit();
					BigDecimal glIdrDebit = tR0002Entity.getGlIdrDebit();
					BigDecimal glIdrCredit = tR0002Entity.getGlIdrCredit();
					tR0002Entity.setIdKey(UUID.randomUUID().toString());
					tR0002Entity.setGlVoucherId(newVoucherId);
					tR0002Entity.setGlOrgDebit(glOrgCredit);
					tR0002Entity.setGlOrgCredit(glOrgDebit);
					tR0002Entity.setGlIdrDebit(glIdrCredit);
					tR0002Entity.setGlIdrCredit(glIdrDebit);
				}
				tR0002Repo.saveAll(listTR0002Entity);
			}
		}

		List<TR0006HEntity> listTR0006HEntity = tR0006HRepo.findByTrxOldVoucherIdEqualsAndTrxFileNameEquals(viewDocumentDeleteEntity.getTrxOldVoucherId(), viewDocumentDeleteEntity.getTrxFileName());
		tR0006HRepo.deleteAll(listTR0006HEntity);


		File myObj = new File(path.concat(viewDocumentDeleteEntity.getTrxFileName())); 
		if(myObj.exists()) {
			if (myObj.delete()) { 
				System.out.println("Deleted the file: " + myObj.getName());
			} else {
				throw new Exception("Failed to delete the file." + myObj.getName());
			} 
		}else {
			throw new Exception("File " + myObj.getAbsolutePath() + "not exist");
		}

	}
}

