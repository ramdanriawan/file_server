package com.biru.repository;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.RptBsEntity;

public interface RptBsRepo extends PagingAndSortingRepository<RptBsEntity, Integer>{
	
	@Modifying
	@Query(nativeQuery = true, value = "UPDATE RPT_BS  SET BLOCK_1_L = '0',  BLOCK_1_R = '0', "
			+ "BLOCK_2_L = '0',  BLOCK_2_R = '0', "
			+ "BLOCK_3_L = '0',  BLOCK_3_R = '0', "
			+ "BLOCK_4_L = '0',  BLOCK_4_R = '0', "
			+ "BLOCK_5_L = '0',  BLOCK_5_R = '0' ")
	public void resetRptBs();
	
	
	@Query("SELECT sum(r.block1L) from RptBsEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock1(String coaGroup);
	
	@Query("SELECT sum(r.block2L) from RptBsEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock2(String coaGroup);
	
	@Query("SELECT sum(r.block3L) from RptBsEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock3(String coaGroup);
	
	@Query("SELECT sum(r.block4L) from RptBsEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock4(String coaGroup);
	
	@Query("SELECT sum(r.block5L) from RptBsEntity r where r.coaGroup = ?1")
	public BigDecimal sumBlock5(String coaGroup);
	
	public RptBsEntity findByCoaGroup(String coaGroup);
	
	public RptBsEntity findByCoaCode(String coaCode);
	
}
