package com.biru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ViewDocumentDeleteEntity;

public interface ViewDocumentDeleteRepo extends PagingAndSortingRepository<ViewDocumentDeleteEntity, Integer>{
	public Page<ViewDocumentDeleteEntity> findByTrxClientIdEquals(String clientId, Pageable p);
}
