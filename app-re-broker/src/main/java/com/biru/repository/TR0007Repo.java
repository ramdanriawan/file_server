package com.biru.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0007Entity;

public interface TR0007Repo extends PagingAndSortingRepository<TR0007Entity, Long> {
	
	public List<TR0007Entity> findByTrxTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	public List<TR0007Entity> findByTrxVoucherIdEqualsAndTrxOldVoucherIdEquals(String trxVoucherId, String trxOldVoucherId);
	
}
