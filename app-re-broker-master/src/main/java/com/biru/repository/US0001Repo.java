package com.biru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.US0001Entity;

public interface US0001Repo extends PagingAndSortingRepository<US0001Entity, Long> {
	US0001Entity findByUsCode(String uscode);

	@Query("select m from US0001Entity m order by m.usCode")
	public Page<US0001Entity> findAlls(Pageable pageable);
	
	@Query("select m from US0001Entity m where m.usStatus = '11' order by m.usCode")
	public Page<US0001Entity> findAllActive(Pageable pageable);
	
	@Query("select count(m) from US0001Entity m where m.usStatus = '11'")
	public int countActiveUser();
	
}
