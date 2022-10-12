package com.biru.repository;

import com.biru.entity.OJKRulesLK03Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OJKRulesLK03Repo extends PagingAndSortingRepository<OJKRulesLK03Entity, Long> {
    @Query("SELECT NEW com.biru.entity.OJKRulesLK03Entity(o.idKey, o.sheetRow, o.rowType, o.kolomA, o.kolomB, o.kolomC, "+
            "o.kolAkun, o.kolRollUp, o.kolLevel, o.kolNormal, o.rOper, o.rCoa, m.coaDescript) FROM OJKRulesLK03Entity o " +
            "LEFT JOIN MA0004Entity m ON TRIM(o.rCoa) = TRIM(m.coaCode)")
    Page<OJKRulesLK03Entity> findAll(Pageable pageable);

    OJKRulesLK03Entity findBySheetRowAndRCoa(String sheetRow, String rCoa);
}
