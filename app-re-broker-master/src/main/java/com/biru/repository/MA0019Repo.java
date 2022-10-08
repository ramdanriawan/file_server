package com.biru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.MA0019Entity;

public interface MA0019Repo extends PagingAndSortingRepository<MA0019Entity, String>{
	
	@Query("select m from MA0019Entity m "
			+ "where m.tcCode = ?1 "
			+ "order by m.clDesc asc")
	public List<MA0019Entity> findByTcCode(String tcCode);
	
	public List<MA0019Entity> findByClCode(Integer clCode);
	
}
