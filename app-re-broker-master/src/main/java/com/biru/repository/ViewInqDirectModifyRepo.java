package com.biru.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.view.ViewInqDirectModify;

public interface ViewInqDirectModifyRepo extends PagingAndSortingRepository<ViewInqDirectModify, String>,
	JpaSpecificationExecutor<ViewInqDirectModify> {
	
}
