package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.RPT001Entity;

public interface RPT001Repo extends PagingAndSortingRepository<RPT001Entity, Long> {
	
	public List<RPT001Entity> findByIdRequest(String p_IdRequest);
	
	public RPT001Entity findByIdRequestAndCoaCode(String p_IdRequest, String p_CoaCode);
	
	@Query("select new com.biru.entity.RPT001Entity"
				+ "(c.coaCode, c.coaDescript, c.coaHeader, " 
				+ "c.coaNormal, c.coaRoleUp, c.coaLevel, g.glBalDebit0, g.glBalCredit0, " 
				+ "g.glBalDIdr0, g.glBalCIdr0) from GL0001Entity g, MA0004Entity c "
			+ "where g.coaCode = c.coaCode and g.glBalYear = ?1")
	public List<RPT001Entity> getBeginingBalance(String p_Year);
	
	@Query("select new com.biru.entity.RPT001Entity"
			+ "(r.coaRollUp, sum(r.bbDebitOrg), sum(r.bbCreditOrg), "
			+ "sum(r.bbDebitIdr), sum(r.bbCreditIdr)) "
		+ "from RPT001Entity r where r.idRequest = ?1 "
			+ "and r.coaLevel = ?2 "
		+ "group by r.coaRollUp")
	public List<RPT001Entity> summaryRollUpBb(String p_IdRequest, Integer p_CoaLevel);
	
	@Query("select new com.biru.entity.RPT001Entity"
				+ "(r.coaRollUp, sum(r.mutDebitOrg), sum(r.mutCreditOrg), "
				+ "sum(r.mutDebitIdr), sum(r.mutCreditIdr)) "
			+ "from RPT001Entity r where r.idRequest = ?1 "
				+ "and r.coaLevel = ?2 "
			+ "group by r.coaRollUp")
	public List<RPT001Entity> summaryRollUpMut(String p_IdRequest, Integer p_CoaLevel);
	
	@Query("select new com.biru.entity.RPT001Entity"
			+ "(r.coaRollUp, sum(r.ebDebitOrg), sum(r.ebCreditOrg), "
			+ "sum(r.ebDebitIdr), sum(r.ebCreditIdr)) "
		+ "from RPT001Entity r where r.idRequest = ?1 "
			+ "and r.coaLevel = ?2 "
		+ "group by r.coaRollUp")
	public List<RPT001Entity> summaryRollUpEb(String p_IdRequest, Integer p_CoaLevel);
	
	@Modifying
	@Transactional
	@Query("delete from RPT001Entity r where r.idRequest = ?1")
	public int deleteByIdRequest(String p_IdRequest);
	
	@Modifying
	@Transactional
	@Query("delete from RPT001Entity r where r.idRequest = ?1 "
			+ "and r.bbDebitOrg = 0 "
			+ "and r.bbCreditOrg = 0 "
			+ "and r.bbDebitIdr = 0 "
			+ "and r.bbCreditIdr = 0 "
			+ "and r.mutDebitOrg = 0 "
			+ "and r.mutCreditOrg = 0 "
			+ "and r.mutDebitIdr = 0 "
			+ "and r.mutCreditIdr = 0")
	public int deleteZeroValue(String p_IdRequest);
	
}
