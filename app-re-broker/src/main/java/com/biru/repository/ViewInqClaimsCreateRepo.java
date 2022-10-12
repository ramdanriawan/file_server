package com.biru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ViewInqClaimsCreate;

public interface ViewInqClaimsCreateRepo extends PagingAndSortingRepository<ViewInqClaimsCreate, Long> {

	@Query("select v from ViewInqClaimsCreate v "
			+ "where v.trxClient like %?1% "
			+ "and v.trxCoverCode like %?2%")
	public Page<ViewInqClaimsCreate> findByClientAndCob(String client, String cob, Pageable pageable);
	
}
