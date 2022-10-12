package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.MA0006Entity;

public interface MA0006Repo extends PagingAndSortingRepository<MA0006Entity, String>{
	public List<MA0006Entity> findByTcCode(String tcCode);
	public Page<MA0006Entity> findByTcCode(String tcCode, Pageable pageable);
	
//	@Transactional
//	@Modifying
//	@Query("delete TR0006FEntity t "
//			+ "where trxVoucherId = ?1 ")
//	public void deleteByIdKey(String trxVoucherId);
}
