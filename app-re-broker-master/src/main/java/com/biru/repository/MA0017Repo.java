package com.biru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.MA0017Entity;

public interface MA0017Repo extends PagingAndSortingRepository<MA0017Entity, Long> {

	@Query("select new com.biru.entity.MA0017Entity"
			+ "("
				+ "m.idKey, m.brCode, m.brParentDesc, m.brCurrId, "
				+ "m.brDataStatus, p.paChildDesc as brDataStatusDesc"
			+ ") "
			+ "from MA0017Entity m, MA0014Entity p "
			+ "where m.brDataStatus = p.paChildValue "
			+ "and p.paParentCode = 'STDATA' "
			+ "and m.brDataStatus in ('11', '12') "
			+ "and ("
			+ "m.brCode like %?1% "
			+ "or m.brParentDesc like %?1% "
			+ "or m.brCurrId like %?1% "
			+ "or m.brDataStatus like %?1%)")	//11 = active & 12 = inactive
	public Page<MA0017Entity> findBusinessRules(String p_Filter, Pageable pageable);
	
	public MA0017Entity findByIdKey(Long idKey);
}
