package com.biru.service;

import java.util.List;
import java.util.Map;

import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.entity.DropdownDash;

public interface OJKSetupDirectService {
    DatatableSet inquiry(Map<String, Object> param);
    Object save(Map<String, Object> param);
    Object delete(Map<String, Object> param);
    List<DropdownIdText> getReportCodes();
    List<DropdownDash> getDropDownRow(Map<String, Object> param);
    List<DropdownIdText> getDropDownCOA();
}
