package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.MA0004Entity;
import com.biru.entity.MA0013Entity;
import com.biru.entity.MA0017Entity;

public interface MA0013Repo extends PagingAndSortingRepository<MA0013Entity, Long> {

	public List<MA0013Entity> findByPaParentCode(String paParentCode);

	@Query("select new com.biru.entity.MA0013Entity"
			+ "("
			+ "m.idKey, m.paParentCode, m.paParentDesc, m.paParentStatus"
			+ ") "
			+ "from MA0013Entity m "
			+ "where m.paParentCode like %?1% or "
			+ "m.paParentDesc like %?1% ")	//11 = active & 12 = inactive
	public Page<MA0013Entity> findByFilter(String filterValue, Pageable pageable);
	
	@Query("select new com.biru.entity.MA0013Entity"
			+ "("
				+ "m.idKey, m.paParentCode, m.paParentDesc, m.paParentStatus"
			+ ") "
			+ "from MA0013Entity m "
			+ "where m.paParentCode like %?1% ")
	public Page<MA0013Entity> findByPaParentCodeContains(String paParentCode, Pageable pageable);
	
	@Query("select new com.biru.entity.MA0013Entity"
			+ "("
				+ "m.idKey, m.paParentCode, m.paParentDesc, m.paParentStatus"
			+ ") "
			+ "from MA0013Entity m "
			+ "where m.paParentDesc like %?1% ")
	public Page<MA0013Entity> findByPaParentDescContains(String paParentDesc, Pageable pageable);
	
	public MA0013Entity findByIdKey(Long idKey);
	
	
}
