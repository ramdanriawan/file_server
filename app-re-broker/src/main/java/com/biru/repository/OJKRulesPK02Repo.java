package com.biru.repository;

import com.biru.entity.OJKRulesPK02Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OJKRulesPK02Repo extends PagingAndSortingRepository<OJKRulesPK02Entity, Long> {
    @Query("SELECT NEW com.biru.entity.OJKRulesPK02Entity(o.idKey, o.sheetRow, o.rowType, o.kolomA, o.kolomB, o.kolomC, "+
            "o.kolAkun, o.kolRollUp, o.kolLevel, o.kolNormal, o.rOper, o.rCoa, m.coaDescript) FROM OJKRulesPK02Entity o " +
            "LEFT JOIN MA0004Entity m ON TRIM(o.rCoa) = TRIM(m.coaCode)")
    Page<OJKRulesPK02Entity> findAll(Pageable pageable);

    OJKRulesPK02Entity findBySheetRowAndRCoa(String sheetRow, String rCoa);
}
