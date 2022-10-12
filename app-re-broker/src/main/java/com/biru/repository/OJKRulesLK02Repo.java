package com.biru.repository;

import com.biru.entity.OJKRulesLK02Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OJKRulesLK02Repo extends PagingAndSortingRepository<OJKRulesLK02Entity, Long> {
    @Query("SELECT NEW com.biru.entity.OJKRulesLK02Entity(o.idKey, o.sheetRow, o.rowType, o.kolomA, o.kolomB, o.kolomC, "+
            "o.kolAkun, o.kolRollUp, o.kolLevel, o.kolNormal, o.rOper, o.rCoa, m.coaDescript) FROM OJKRulesLK02Entity o " +
            "LEFT JOIN MA0004Entity m ON TRIM(o.rCoa) = TRIM(m.coaCode)")
    Page<OJKRulesLK02Entity> findAll(Pageable pageable);

    OJKRulesLK02Entity findBySheetRowAndRCoa(String sheetRow, String rCoa);
}
