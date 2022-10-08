package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006IEntity;

public interface TR0006IRepo extends PagingAndSortingRepository<TR0006IEntity, String> {
	
	public List<TR0006IEntity> findByTrxVoucherId(String p_TrxVoucherId);
	
	@Transactional
	@Modifying
	@Query("delete TR0006IEntity t where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public int deleteByTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006IEntity(t.idKey, t.trxBankCode, t.trxClass, t.trxCommAmt, "
			+ "t.trxTrxId, t.trxVoucherId, m5.cliName) from TR0006IEntity t, MA0005Entity m5 "
		+ "where t.trxBankCode = m5.cliCode "
		+ "and t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public List<TR0006IEntity> findByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
} 
