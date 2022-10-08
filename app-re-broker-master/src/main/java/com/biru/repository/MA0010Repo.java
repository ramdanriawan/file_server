package com.biru.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.MA0010Entity;

public interface MA0010Repo extends PagingAndSortingRepository<MA0010Entity, Long> {
	
	@Query("select new com.biru.entity.MA0010Entity(m.coPicEmail, m.coPicEmailPass, m.coPicEmailAlias) "
			+ "from MA0010Entity m where m.idKey='1'")
	public MA0010Entity getEmail();
	
}
