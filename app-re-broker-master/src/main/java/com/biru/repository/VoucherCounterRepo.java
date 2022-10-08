package com.biru.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.VoucherCounterEntity;

public interface VoucherCounterRepo extends PagingAndSortingRepository<VoucherCounterEntity, Integer>{
	public VoucherCounterEntity findByDate(String date);
}
