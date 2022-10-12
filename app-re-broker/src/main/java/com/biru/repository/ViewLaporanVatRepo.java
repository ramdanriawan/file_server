package com.biru.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ViewLaporanVatEntity;

public interface ViewLaporanVatRepo extends PagingAndSortingRepository<ViewLaporanVatEntity, String>{
	public Page<ViewLaporanVatEntity> findAll(Specification<ViewLaporanVatEntity> specification, Pageable pageable);
}
