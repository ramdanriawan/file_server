package com.biru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ViewInqClaims;

public interface ViewInqClaimsRepo extends PagingAndSortingRepository<ViewInqClaims, Long> {

	@Query("select v from ViewInqClaims v "
			+ "where (v.txDataStatus = ?1 or '' = ?1) "
			+ "and (DATE_FORMAT(v.txAdviceDate,'%Y') like %?3% or '' = ?3) "
			+ "and ("
				+ "DATE_FORMAT(v.createOn,'%d/%m/%Y') like %?2% or "
				+ "v.txVoucherId like %?2% or "
				+ "v.txClientCode like %?2% or "
				+ "v.txInsCode like %?2% or "
				+ "v.txPartyName like %?2% or "
				+ "v.txCurrId like %?2% or "
				+ "FORMAT(v.txSumIns, 2) like %?2% or "
				+ "v.txDataStatusVal like %?2%"
			+ ")")
	public Page<ViewInqClaims> getDataClaims(String txDataStatus, String search, String year, Pageable pageable);
	
}
