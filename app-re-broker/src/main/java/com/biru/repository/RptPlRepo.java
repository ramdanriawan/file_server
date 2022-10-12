package com.biru.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.RptPlEntity;

public interface RptPlRepo extends PagingAndSortingRepository<RptPlEntity, Integer>{
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE RPT_PL  SET BLOCK_1 = '0', "
			+ "BLOCK_2 = '0', "
			+ "BLOCK_3 = '0', "
			+ "BLOCK_4 = '0', "
			+ "BLOCK_5 = '0', "
			+ "BLOCK_6 = '0'")
	public void resetRptpl();
	
	public RptPlEntity findByCoaGroup(String coaGroup);
	
	@Query("SELECT sum(r.block1) from RptPlEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock1(String coaGroup);
	
	@Query("SELECT sum(r.block2) from RptPlEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock2(String coaGroup);
	
	@Query("SELECT sum(r.block3) from RptPlEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock3(String coaGroup);
	
	@Query("SELECT sum(r.block4) from RptPlEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock4(String coaGroup);
	
	@Query("SELECT sum(r.block5) from RptPlEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock5(String coaGroup);
	
	@Query("SELECT sum(r.block6) from RptPlEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock6(String coaGroup);
	
	Page<RptPlEntity> findByCoaGroupIn(List<String>listCoaGroup, Pageable p);
	
	public RptPlEntity findByCoaCode(String coaCode);
	
	public List<RptPlEntity> findByCoaCodeAndCoaGroup(String coaCode, String coaGroup);
	
}
