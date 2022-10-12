package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0015CEntity;

public interface TR0015CRepo extends PagingAndSortingRepository<TR0015CEntity, Long> {
	
	@Modifying
	@Transactional
	@Query("delete from TR0015CEntity "
			+ "where trxVoucherId = ?1 "
			+ "and trxTrxId = ?2")
	public int deleteByTxVoucherIdAndTrxTrxId(String voucherId, String trxTrxId);
	
	public List<TR0015CEntity> findByTrxVoucherIdAndTrxTrxId(String voucherId, String trxTrxId);
	
}
