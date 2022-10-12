package com.biru.repository;

import com.biru.entity.OJKRulesLK01Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface OJKRulesLK01Repo extends PagingAndSortingRepository<OJKRulesLK01Entity, Long> {
    @Query("SELECT NEW com.biru.entity.OJKRulesLK01Entity(o.idKey, o.sheetRow, o.rowType, o.kolomA, o.kolomB, o.kolomC, "+
            "o.kolAkun, o.kolRollUp, o.kolLevel, o.kolNormal, o.rOper, o.rCoa, m.coaDescript) FROM OJKRulesLK01Entity o " +
            "LEFT JOIN MA0004Entity m ON TRIM(o.rCoa) = TRIM(m.coaCode)")
    Page<OJKRulesLK01Entity> findAll(Pageable pageable);

    OJKRulesLK01Entity findBySheetRowAndRCoa(String sheetRow, String rCoa);
}
