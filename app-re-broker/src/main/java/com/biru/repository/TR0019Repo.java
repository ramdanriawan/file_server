package com.biru.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0019Entity;

public interface TR0019Repo extends PagingAndSortingRepository<TR0019Entity, Long>{
	TR0019Entity findByTrxOldVoucherId(String voucherId);
}
