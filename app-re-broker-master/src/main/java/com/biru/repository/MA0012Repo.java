package com.biru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.biru.common.entity.DropdownIdText;
import com.biru.entity.MA0012Entity;

public interface MA0012Repo extends PagingAndSortingRepository<MA0012Entity, Long> {
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.saCode, m.saName) from MA0012Entity m "
			+ "where m.saType = '0' "
			+ "and m.saDataStatus = '11' "
			+ "order by m.saName")	
	public List<DropdownIdText> getDropdownOfficer();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.saCode, m.saName) from MA0012Entity m "
			+ "where m.saType = '2' "
			+ "and m.saDataStatus = '11' "
			+ "order by m.saName")	
	public List<DropdownIdText> getDropdownFounder();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.saCode, m.saName) from MA0012Entity m "
			+ "where m.saType <> '2' "
			+ "and m.saDataStatus = '11' "
			+ "order by m.saName")	
	public List<DropdownIdText> getDropdownOfficerTypeNot2();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.saCode, m.saName) from MA0012Entity m "
			+ "where m.saType = '2' "
			+ "and m.saDataStatus = '11' "
			+ "order by m.saName")	
	public List<DropdownIdText> getDropdownOfficerType2();
	
	public MA0012Entity findBySaCode(String saCode);
	
	@Query("select m from MA0012Entity m "
			+ "where m.saCode like %?1% "
			+ "or m.saName like %?1% "
			+ "or m.saEmail like %?1% "
			+ "or m.saPhone like %?1% "
			+ "or m.saLicense like %?1% "
			+ "or m.saTaxId like %?1% "
			+ "order by m.saCode ")
	public Page<MA0012Entity> findAll(String filterValue, Pageable pageable);
	
	public MA0012Entity findByIdKey(Long idKey);

	
}
