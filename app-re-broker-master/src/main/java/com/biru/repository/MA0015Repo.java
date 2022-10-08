package com.biru.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.MA0015Entity;
public interface MA0015Repo extends PagingAndSortingRepository<MA0015Entity, Long> {

	@Query("select m15 from MA0015Entity m15 "
			+ "where m15.exCurrId = (select m04.coaCurrId from MA0004Entity m04 where m04.coaCode = ?1) "
			+ "and m15.exMonth = ?2 "
			+ "and m15.exYear = ?3")
	public MA0015Entity findByCoaCodeAndExMonthAndExYear(String p_CoaCode, Integer p_ExMonth, Integer p_ExYear);
	
	public MA0015Entity findByExMonthAndExYearAndExCurrId (Integer exMonth, Integer exYear, String exCurrId);
	
	@Query("select m from MA0015Entity m "
			+ "where m.exCurrId like %?1% "
			+ "or LOWER(elt(m.exMonth,'January','February','March',"
			+ "'April','May','June','July','August','September',"
			+ "'October','November','December')) like %?1% "
			+ "or m.exYear like %?1% "
			+ "or m.exRateEom like %?1% "
			+ "or m.exRateValue like %?1% "
			+ "or m.exRatePro like %?1% "
			+ "or m.exRateTax like %?1%")
	public Page<MA0015Entity> inquiryER(String p_Filter, Pageable p_Pageable);
	
	/*@Query("select new com.biru.entity.MA0015Entity from MA0001Entity m")
	public Page<MA0015Entity> findAll(Pageable p_Pageable);*/
	
	public MA0015Entity findByIdKey(Long p_IdKey);
	
	public MA0015Entity findByExCurrIdAndExMonthAndExYear(String p_CurrId, Integer p_ExMonth, Integer p_ExYear);
	
	@Query("select DISTINCT m4.coaCurrId from GL0001Entity g, MA0004Entity m4 "
			+ "where g.glBalYear = ?2 "
			+ "and g.coaCode = m4.coaCode "
			+ "and not exists (select m15 from MA0015Entity m15 "
								+ "where m4.coaCurrId = m15.exCurrId "
								+ "and g.glBalYear = CONVERT(m15.exYear, CHAR(10)) "
								+ "and m15.exMonth = ?1)")
	public List<String> findExchangeEomNotExists(Integer p_ExMonth, String p_Year);
	
	public Page<MA0015Entity> findLastByExCurrId(String exCurrId, Pageable p_Pageable);
}
