package com.biru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.TR0004Entity;

public interface TR0004Repo extends PagingAndSortingRepository<TR0004Entity, String> {

	@Query("select new com.biru.common.entity.DropdownIdText(t.proCode, t.proDesc) from TR0004Entity t "
			+ "order by t.proCode")	
	public List<DropdownIdText> getProject();
	
}
