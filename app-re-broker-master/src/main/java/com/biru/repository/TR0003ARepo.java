package com.biru.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0003AEntity;

public interface TR0003ARepo extends PagingAndSortingRepository<TR0003AEntity, String> {
	List<TR0003AEntity> findByTrxDueAmountAndTrxDueDateAndTrxNoRow(BigDecimal trxDueAmout, Date trxDueDate, Integer trxNoRow);
}
