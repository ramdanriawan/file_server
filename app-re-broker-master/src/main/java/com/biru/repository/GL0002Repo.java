package com.biru.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.GL0002Entity;


public interface GL0002Repo extends PagingAndSortingRepository<GL0002Entity, Long> {
	
	@Query("select new com.biru.common.entity.DropdownIdText(g.tbYear, g.tbYear) "
			+ "from GL0002Entity g group by g.tbYear")
	public List<DropdownIdText> findYear();
	
	public Page<GL0002Entity> findByTbYear(String tbYear, Pageable pageable);
	
	public GL0002Entity findByCoaCodeAndTbYear(String p_CoaCode, String p_tbYear);
	
	@Query("select sum(g.tbAmount) "
			+ "from GL0002Entity g where g.saCode = ?1 and g.tbYear = ?2 "
			+ "group by g.saCode, g.tbYear")
	public String sumTbAmount(String saCode, String tbYear);
	
	@Transactional
	@Modifying
	@Query("delete GL0002Entity t where t.tbYear = ?1 "
			+ "and t.coaCode = ?2 "
			+ "and t.saCode = ?3")
	public void deleteByTbYearAndCoaCodeAndSaCode(String year, String coaCode, String saCode);
	
	@Transactional
	@Modifying
	@Query("delete GL0002Entity t where t.tbYear = ?1 and t.tbMonth = ?2 and t.coaCode = ?3 "
			+ "and t.saCode = ?4")
	public void deleteByTbYearAndTbMonthAndCoaCodeAndSaCode(String year, String month, String coaCode, String saCode);
	

	@Query("select sum(g.tbAmount) "
			+ "from GL0002Entity g where g.coaCode = ?1 and g.tbYear = ?2 ")
	public BigDecimal sumTbAmountByCoaCodeAndTbYear(String coaCode, String tbYear);
}