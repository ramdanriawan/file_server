package com.biru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ViewInqUploadTreatyEntity;

public interface ViewInqUploadTreatyRepo extends PagingAndSortingRepository<ViewInqUploadTreatyEntity, Long> {

	@Query("select v from ViewInqUploadTreatyEntity v "
			+ "where v.trxClient like %?1% ")
	public Page<ViewInqUploadTreatyEntity> getDataForUploadTreaty(String p_Client, Pageable pageable);
}
