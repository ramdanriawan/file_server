package com.biru.repository;

import com.biru.entity.OJKRulesLK04Entity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OJKRulesLK04Repo extends PagingAndSortingRepository<OJKRulesLK04Entity, Long> {
    @Query("SELECT NEW com.biru.entity.OJKRulesLK04Entity(o.idKey, o.sheetRow, o.rowType, o.kolomA, o.kolomB, o.kolomC, "+
            "o.kolAkun, o.kolRollUp, o.kolLevel, o.kolNormal, o.rOper, o.rCoa, m.coaDescript) FROM OJKRulesLK04Entity o " +
            "LEFT JOIN MA0004Entity m ON TRIM(o.rCoa) = TRIM(m.coaCode)")
    Page<OJKRulesLK04Entity> findAll(Pageable pageable);

    OJKRulesLK04Entity findBySheetRowAndRCoa(String sheetRow, String rCoa);
}
