package com.biru.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.MA0004Entity;

public interface MA0004Repo extends PagingAndSortingRepository<MA0004Entity, Long> {
	
	public Page<MA0004Entity> findByCoaCodeContains(String p_CoaCode, Pageable pageable);
	public Page<MA0004Entity> findByCoaDescriptContains(String p_CoaDescript, Pageable pageable);
	public Page<MA0004Entity> findByCoaHeaderAndCoaDataStatus(Character p_CoaHeader, String p_CoaDataStatus, Pageable pageable);
	public Page<MA0004Entity> findByCoaHeaderAndCoaDataStatusAndCoaCodeContains(Character p_CoaHeader, String p_CoaDataStatus, String p_CoaCode, Pageable pageable);
	public Page<MA0004Entity> findByCoaHeaderAndCoaDataStatusAndCoaDescriptContains(Character p_CoaHeader, String p_CoaDataStatus, String p_CoaDescript, Pageable pageable);
	public MA0004Entity findByCoaCode(String p_CoaCode);
	
	@Query("select min(m.coaCode) from MA0004Entity m")
	public String findMinCoaCode();
	@Query("select max(m.coaCode) from MA0004Entity m")
	public String findMaxCoaCode();
	
	@Query("select m from MA0004Entity m "
			+ "where m.coaCode = (select mc.paChildValue from MA0014Entity mc " 
										+ "where mc.paChildCode = ?1)")
	public MA0004Entity findAccountEoy(String p_Code);
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.coaCode, m.coaDescript) from MA0004Entity m "
			+ "where m.coaBankBk = '1' "
			+ "and m.coaDataStatus = '11' "
			+ "order by m.coaDescript")	
	public List<DropdownIdText> getBank();
	
	public List<MA0004Entity> findByCoaCodeInAndCoaClassInAndCoaHeaderEquals(List<String> listCoaCode, List<String> coaClass, Character coaHeader);

	public List<MA0004Entity> findByCoaHeaderAndCoaDataStatus(Character p_CoaHeader, String p_CoaDataStatus);
	
	@Query("select m.coaCurrId from MA0004Entity m "
			+ "where m.coaCode = ?1")
	public String findCoaCurrIdByCoaCode(String p_CoaCode);

	@Query("SELECT NEW com.biru.common.entity.DropdownIdText(m.coaCode, m.coaDescript) FROM MA0004Entity m "
			+ "ORDER BY m.coaCode")
	List<DropdownIdText> getAllCOAs();
}
