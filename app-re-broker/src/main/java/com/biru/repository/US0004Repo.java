package com.biru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.US0004Entity;

public interface US0004Repo extends PagingAndSortingRepository<US0004Entity, Long>{

	
	@Query("select new com.biru.common.entity.DropdownIdText(m.uiId, m.uiName) from US0004Entity m "
			+ "order by m.uiId")	
	public List<DropdownIdText> getDropDown();
	
	public US0004Entity findByUiId(String uiId);
	
}
