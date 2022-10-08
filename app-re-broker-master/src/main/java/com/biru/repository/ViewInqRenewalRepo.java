package com.biru.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ViewInqRenewalEntity;

public interface ViewInqRenewalRepo extends PagingAndSortingRepository<ViewInqRenewalEntity, Long>{
	public Page<ViewInqRenewalEntity> findByTrxCoverCodeLikeAndCliNameLikeAndTrxInsEndBetweenAndTcRenewableLike(String coverCode, String trxClient, Date exp, Date expTo, String status, Pageable p);
}
