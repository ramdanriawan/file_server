package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0015BEntity;

public interface TR0015BRepo extends PagingAndSortingRepository<TR0015BEntity, Long> {
	
	@Modifying
	@Transactional
	@Query("delete from TR0015BEntity "
			+ "where txVoucherId = ?1 "
			+ "and trxTrxId = ?2")
	public int deleteByTxVoucherIdAndTrxTrxId(String voucherId, String trxTrxId);
		
	public List<TR0015BEntity> findByTxVoucherIdAndTrxTrxId(String voucherId, String trxTrxId);
	
}
