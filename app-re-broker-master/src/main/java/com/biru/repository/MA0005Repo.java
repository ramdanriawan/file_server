package com.biru.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.MA0005Entity;

public interface MA0005Repo extends PagingAndSortingRepository<MA0005Entity, String>, 
		JpaSpecificationExecutor<MA0005Entity> {
	public Page<MA0005Entity> findBycliDataStatus (String cliDataStatus, Pageable pageable);
	public Page<MA0005Entity> findBycliCodeContainsAndCliDataStatusEquals(String cliCode, String cliDataStatus, Pageable pageable);
	public Page<MA0005Entity> findBycliNameContainsAndCliDataStatusEquals(String cliName, String cliDataStatus, Pageable pageable);
	public MA0005Entity findByCliCode(String cliCode);
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.cliCode, m.cliName) from MA0005Entity m "
			+ "where m.cliType = ?1 order by m.cliName")	
	public List<DropdownIdText> getDropdownClient(String p_CliType);
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.cliCode, m.cliName) from MA0005Entity m "
			+ "where m.cliType=\'0\' "
			+ "and m.cliDataStatus=\'0\' order by m.cliName")
	public List<DropdownIdText> getDropdownForProduct();
	
	@Query("select max(m.cliCode) from MA0005Entity m")
	public String findMaxCliCode();
	
	@Query("select m.cliName from MA0005Entity m "
			+ "where m.cliCode=?1")
	public String findCliNameByCliCode(String cliCode);
	
	@Query("select m.cliCode from MA0005Entity m "
			+ "where m.cliName=?1")
	public String findCliCodeByCliName(String cliName);
	
}
