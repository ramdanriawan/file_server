package com.biru.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.MA0007Entity;

public interface MA0007Repo extends PagingAndSortingRepository<MA0007Entity, String>{
	@Query("select new com.biru.common.entity.DropdownIdText(m.idKey, m.detailsName) from MA0007Entity m "
			+ "where m.groupName = ?1 "
			+ "and m.detailStatus = '11' "
			+ "order by m.idKey")	
	public List<DropdownIdText> findTcDetails(String groupName);
	
	public MA0007Entity findByGroupNameAndDetailsName(String groupName, String detailsName);
	
	public Page<MA0007Entity> findByGroupName(String groupName, Pageable pageable);
}
