package com.biru.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.MA0011Entity;

public interface MA0011Repo extends PagingAndSortingRepository<MA0011Entity, Long> {
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.tcCode, m.tcCode) from MA0011Entity m "
			+ "order by m.tcDesc")	
	public List<DropdownIdText> getDropdownProduct();
	
	@Query("select m from MA0011Entity m "
			+ "where m.tcCode like %?1% "
			+ "or m.tcDesc like %?1% "
			+ "or m.tcOjkGroup like %?1%")
	public Page<MA0011Entity> findByFilter(String filterValue, Pageable pageable);
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.tcCode, m.tcDesc) from MA0011Entity m "
			+ "where m.tcDataStatus = '11' "
			+ "order by m.tcDesc")	
	public List<DropdownIdText> getDropdownTypeOfCover();
	
	public MA0011Entity findByTcCode(String tcCode);
}
