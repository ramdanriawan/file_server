package com.biru.repository;

import com.biru.entity.OJKRulesPK01Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OJKRulesPK01Repo extends PagingAndSortingRepository<OJKRulesPK01Entity, Long> {
    @Query("SELECT NEW com.biru.entity.OJKRulesPK01Entity(o.idKey, o.sheetRow, o.rowType, o.kolomA, o.kolomB, o.kolomC, "+
            "o.kolAkun, o.kolRollUp, o.kolLevel, o.kolNormal, o.rOper, o.rCoa, m.coaDescript) FROM OJKRulesPK01Entity o " +
            "LEFT JOIN MA0004Entity m ON TRIM(o.rCoa) = TRIM(m.coaCode)")
    Page<OJKRulesPK01Entity> findAll(Pageable pageable);

    OJKRulesPK01Entity findBySheetRowAndRCoa(String sheetRow, String rCoa);
}
