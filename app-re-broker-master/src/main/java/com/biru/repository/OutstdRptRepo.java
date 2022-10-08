package com.biru.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.OutstdRptEntity;

public interface OutstdRptRepo extends PagingAndSortingRepository<OutstdRptEntity, String>{
	@Transactional
	@Modifying
	@Query("DELETE FROM OutstdRptEntity ore "
			+ "WHERE ore.userid = ?1")
	public void deletAllByUserId(String userId);
}
