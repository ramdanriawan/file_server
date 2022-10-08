package com.biru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ViewInqExtensionEntity;

public interface ViewInqExtensionRepo extends PagingAndSortingRepository<ViewInqExtensionEntity, Long> {

	@Query("select v from ViewInqExtensionEntity v "
			+ "where v.trxCoverCode like %?1% "
			+ "and v.trxClient like %?2% ")
	public Page<ViewInqExtensionEntity> getDataForExtension(String p_CoverCode, String p_Client, Pageable pageable);
}
