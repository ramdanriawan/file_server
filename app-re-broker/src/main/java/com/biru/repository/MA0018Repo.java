package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.MA0018Entity;

public interface MA0018Repo extends PagingAndSortingRepository<MA0018Entity, Long> {
	
	public List<MA0018Entity> findByBrCode(String p_BrCode);
	
	@Query("select new com.biru.entity.MA0018Entity(m.idKey, "
			+ "m.brCode, m.brChildDc, m.brChildCoa, m.brChildValue, "
			+ "p.coaDescript) "
			+ "from MA0018Entity m, MA0004Entity p "
			+ "where m.brChildCoa = p.coaCode "
			+ "and m.brCode = ?1 ")
	public Page<MA0018Entity> findByBrCode(String p_brCode, Pageable pageable);
	
	@Modifying
	@Transactional
	@Query("delete MA0018Entity m where m.brCode = ?1")
	public void deleteByBrCode(String p_brCode);
	
}
