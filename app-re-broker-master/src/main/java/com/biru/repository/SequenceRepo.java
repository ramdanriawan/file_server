package com.biru.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.SequenceEntity;

public interface SequenceRepo extends PagingAndSortingRepository<SequenceEntity, Integer> {

	SequenceEntity findBySequenceName(String sequenceName);
	
}
