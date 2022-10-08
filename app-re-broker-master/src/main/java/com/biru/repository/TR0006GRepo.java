package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006GEntity;

public interface TR0006GRepo extends PagingAndSortingRepository<TR0006GEntity, String> {

	@Transactional
	@Modifying
	@Query("delete TR0006GEntity t where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public int deleteByTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006GEntity(t.idKey, t.trxTrxId, t.trxVoucherId, t.trxSaCode, "
				+ "t.trxSaTaxId, t.trxCommPct, t.trxCommAmt, (select saName from MA0012Entity where saCode = t.trxSaCode)) "
		+ "from TR0006GEntity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2 "
		+ "order by t.idKey")
	public List<TR0006GEntity> getDataCommOut(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006GEntity(t.idKey, t.trxTrxId, t.trxVoucherId, t.trxSaCode, "
				+ "t.trxSaTaxId, t.trxCommPct, t.trxCommAmt, (select cliName from MA0005Entity where cliCode = t.trxSaCode)) "
		+ "from TR0006GEntity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2 "
		+ "order by t.idKey")
	public List<TR0006GEntity> getDataCommOutV2(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t from TR0006GEntity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public List<TR0006GEntity> findByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	public List<TR0006GEntity> findByTrxVoucherId(String p_TrxVoucherId);
}
