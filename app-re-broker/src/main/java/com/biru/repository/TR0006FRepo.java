package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006FEntity;

public interface TR0006FRepo extends PagingAndSortingRepository<TR0006FEntity, String> {

	@Transactional
	@Modifying
	@Query("delete TR0006FEntity t where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public int deleteByTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	
	@Transactional
	@Modifying
	@Query("delete TR0006FEntity t where t.trxClass = ?1 and t.trxTrxId = ?2 and t.trxVoucherId = ?3")
	public int deleteByTrxClassAndTrxIdAndTrxVoucherId(String trxClass, String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006FEntity(t.trxDocName, t.trxDocFolder, "
			+ "t.trxPrClient, t.trxDocId, m.clDesc, m.clStatus) "
			+ "from TR0006FEntity t, MA0019Entity m "
			+ "where t.trxPrClient = m.tcCode "
			+ "and t.trxDocId = m.clCode "
			+ "and t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2")
	public List<TR0006FEntity> getDataChecklist2(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006FEntity(t.trxDocName, t.trxDocFolder, "
			+ "t.trxPrClient, t.trxDocId, m.clDesc, m.clStatus) "
			+ "from TR0006FEntity t, MA0019Entity m "
			+ "where t.trxPrClient = m.tcCode "
			+ "and t.trxDocId = m.clCode "
			+ "and t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3")
	public List<TR0006FEntity> getDataChecklist2Treaty(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	public List<TR0006FEntity> findByTrxVoucherId(String p_TrxVoucherId);
}
