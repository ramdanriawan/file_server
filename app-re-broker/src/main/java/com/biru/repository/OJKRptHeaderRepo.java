package com.biru.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.entity.DropdownDash;
import com.biru.entity.OJKRptHeaderEntity;

public interface OJKRptHeaderRepo extends PagingAndSortingRepository<OJKRptHeaderEntity, Long> {
    @Query("SELECT DISTINCT NEW com.biru.common.entity.DropdownIdText(o.sheetId, o.sheetId)" +
            "FROM OJKRptHeaderEntity o ORDER BY o.sheetId ASC")
    List<DropdownIdText> getDropDownReportCodes();

    @Query("SELECT NEW com.biru.common.entity.DropdownDash(o.sheetRow, o.kolomA)" +
            "FROM OJKRptHeaderEntity o WHERE o.sheetId = ?1 ORDER BY o.sheetRow, o.kolomA ASC")
    List<DropdownDash> getDropDownRow(String reportCode);

    OJKRptHeaderEntity findBySheetIdAndSheetRow(String sheetID, String sheetRow);
}
