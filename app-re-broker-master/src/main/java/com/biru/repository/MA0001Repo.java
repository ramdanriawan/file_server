package com.biru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.MA0001Entity;

public interface MA0001Repo extends PagingAndSortingRepository<MA0001Entity, Long> {
	
	@Query("select new com.biru.entity.MA0001Entity"
			+ "("
				+ "m.idKey, date_format(m.holiDate, '%d-%m-%Y'), m.holiDesc, m.holiDataStatus, p.paChildDesc"
			+ ") "
			+ "from MA0001Entity m, MA0014Entity p "
			+ "where m.holiDataStatus = p.paChildValue "
			+ "and p.paParentCode = 'STDATA' "
			+ "and m.holiDataStatus in ('11', '12')")	//11 = active & 12 = inactive
	public Page<MA0001Entity> findHoliday(Pageable p_Pageable);
	
	@Query("select m from MA0001Entity m "
			+ "where date_format(m.holiDate, '%d-%m-%Y') = ?1")
	public MA0001Entity findByDate(String p_Date);
	
	@Query("select count(m.holiDate) from MA0001Entity m "
			+ "where date_format(m.holiDate, '%d-%m-%Y') = ?1 "
			+ "and m.holiDataStatus = '11'")
	public Integer isActiveExsist(String p_Date);
	
}
