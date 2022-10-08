package com.biru.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.MA0021Entity;

public interface MA0021Repo extends PagingAndSortingRepository<MA0021Entity, Long> {
	
	@Query("select m from MA0021Entity m "
			+ "where m.tcCode like %?1% "
			+ "or m.insId like %?1%")
	public Page<MA0021Entity> findByFilter(String filterValue, Pageable pageable);
	
	public Page<MA0021Entity> findByTcCode(String tcCode, Pageable pageable);
	
	public List<MA0021Entity> findByTcCode(String tcCode);
	
	public MA0021Entity findByInsIdAndTcCode(String insId, String tcCode);
	
	public MA0021Entity findByIdKey(Long idKey);
}
