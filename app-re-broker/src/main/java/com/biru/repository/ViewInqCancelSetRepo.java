package com.biru.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ViewInqCancelSet;

public interface ViewInqCancelSetRepo extends PagingAndSortingRepository<ViewInqCancelSet, Long> {
	
	@Query("select v from ViewInqCancelSet v "
			+ "where (v.trxDate >= ?1 "
			+ "and v.trxDate <= ?2) "
			+ "and v.trxType = ?3 "
			+ "and v.trxClient like %?4%")
	public Page<ViewInqCancelSet> findCancelSettlement(Date p_StartDate, Date p_EndDate, 
			String p_Type, String p_Client, Pageable pageable);
		
}
