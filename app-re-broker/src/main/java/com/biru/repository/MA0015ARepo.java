package com.biru.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.MA0015AEntity;

public interface MA0015ARepo extends PagingAndSortingRepository<MA0015AEntity, Long> {

	@Query("select m from MA0015AEntity m "
			+ "where m.exCurrId like %?1% ")
	public Page<MA0015AEntity> findByFilter(String filterValue, Pageable pageable);
	
	@Query("select m15A from MA0015AEntity m15A "
			+ "where DATE(m15A.exDate) = DATE(?1) "
			+ "and m15A.exCurrId = ?2")
	public MA0015AEntity findByExDateAndExCurrId(Date exDate, String exCurrId);
	
	public MA0015AEntity findByIdKey(Long p_IdKey);
	
	@Query("select m15A from MA0015AEntity m15A "
			+ "where m15A.exCurrId = (select m04.coaCurrId from MA0004Entity m04 where m04.coaCode = ?1) "
			+ "and m15A.exDate = DATE(?2)")
	public MA0015AEntity findByCoaCodeAndExDate(String p_CoaCode, Date p_ExDate);
	
}
