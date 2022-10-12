package com.biru.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ReportProductionEntity;

public interface ReportProducitionRepo extends PagingAndSortingRepository<ReportProductionEntity, String>{
	
}
