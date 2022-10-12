package com.biru.service.impl;

import com.biru.ReBrokerConstants;
import com.biru.common.entity.DatatableSet;
import com.biru.common.entity.DropdownIdText;
import com.biru.common.entity.DropdownDash;
import com.biru.common.param.Param;
import com.biru.component.ResultComponent;
import com.biru.entity.*;
import com.biru.repository.MA0004Repo;
import com.biru.repository.OJKRptHeaderRepo;
import com.biru.repository.OJKRulesLK01Repo;
import com.biru.repository.OJKRulesLK02Repo;
import com.biru.repository.OJKRulesLK03Repo;
import com.biru.repository.OJKRulesLK04Repo;
import com.biru.repository.OJKRulesPK01Repo;
import com.biru.repository.OJKRulesPK02Repo;
import com.biru.service.OJKSetupDirectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.*;

@Service
public class OJKSetupDirectServiceImpl implements OJKSetupDirectService {
    @Autowired
    private OJKRptHeaderRepo ojkRptHeaderRepo;
    @Autowired
    private MA0004Repo ma0004Repo;
    @Autowired
    private OJKRulesLK01Repo ojkRulesLK01Repo;
    @Autowired
    private OJKRulesLK02Repo ojkRulesLK02Repo;
    @Autowired
    private OJKRulesLK03Repo ojkRulesLK03Repo;
    @Autowired
    private OJKRulesLK04Repo ojkRulesLK04Repo;
    @Autowired
    private OJKRulesPK01Repo ojkRulesPK01Repo;
    @Autowired
    private OJKRulesPK02Repo ojkRulesPK02Repo;

    @Autowired
    private ResultComponent resultComponent;

    @Override
    public DatatableSet inquiry(Map<String, Object> param) {
        Integer limit = Param.getInt(param, ReBrokerConstants.PARAM.LIMIT);
        Integer offset = Param.getInt(param, ReBrokerConstants.PARAM.OFFSET);
        String order = Param.getStr(param, ReBrokerConstants.PARAM.ORDER);
        String sort = Param.getStr(param, ReBrokerConstants.PARAM.SORT);
        String reportCode = Param.getStr(param, "reportcode");
        Page page;
        Pageable pageable = PageRequest.of((offset==null?0:offset)/(limit==null?0:limit), limit,
                                            Sort.Direction.fromString(order==null?"asc":order), sort);

        switch (reportCode==null?"":reportCode) {
            case "LK01": page = setActions(ojkRulesLK01Repo.findAll(pageable)); break;
            case "LK02": page = setActions(ojkRulesLK02Repo.findAll(pageable)); break;
            case "LK03": page = setActions(ojkRulesLK03Repo.findAll(pageable)); break;
            case "LK04": page = setActions(ojkRulesLK04Repo.findAll(pageable)); break;
            case "PK01": page = setActions(ojkRulesPK01Repo.findAll(pageable)); break;
            case "PK02": page = setActions(ojkRulesPK02Repo.findAll(pageable)); break;
            default:     page = null;
        }
        return (page==null?null:new DatatableSet(page.getTotalElements(), page.getTotalElements(), page.getContent()));
    }

    private Page setActions(Page page) {
        for (Object ojkRulesEntity : page.getContent()) {
            ((OJKRulesEntity)ojkRulesEntity).setAction("<button class=\"btn btn-danger\" onclick=\"remove('" +
                    ((OJKRulesEntity) ojkRulesEntity).getIdKey() + "')\"><i class=\"fa fa-trash\"></i></button>");
        }
        return page;
    }

    @Override
    public Object save(Map<String, Object> param) {
        try {
            saveProcess(param);
            return resultComponent.createResponse(null);
        } catch (Exception e) {
            return resultComponent.createResponse(e);
        }
    }

    @Transactional
    @SuppressWarnings("unchecked")
    protected void saveProcess(Map<String, Object> param) {
        String reportCode = Param.getStr(param, "reportCode");
        String sheetRow, rCoa, rOper;
        Integer kolLevel;
        OJKRptHeaderEntity ojkRptHeaderEntity;
        OJKRulesEntity ojkRulesEntity;
        MA0004Entity ma0004Entity;

        ArrayList<HashMap<String, Object>> rows = (ArrayList<HashMap<String, Object>>) param.get("rows");
        List<Object> listOfOJKRulesEntity = new ArrayList<>();

        for (HashMap<String, Object> rowsData : rows) {
            sheetRow = rowsData.get("sheetRow").toString();
            ojkRptHeaderEntity = ojkRptHeaderRepo.findBySheetIdAndSheetRow(reportCode, sheetRow);
            if (ojkRptHeaderEntity == null) continue;
            rCoa = rowsData.get("rCoa")==null?"":rowsData.get("rCoa").toString();
            rOper = rowsData.get("rOper")==null?"":rowsData.get("rOper").toString();
            kolLevel = Integer.parseInt(rowsData.get("kolLevel").toString());
            ojkRulesEntity = checkRecord(reportCode==null?"":reportCode, sheetRow, rCoa);
            ma0004Entity = ma0004Repo.findByCoaCode(rCoa);
            if (ojkRulesEntity != null) {   // Update
                fillOJKRulesEntity(sheetRow, rCoa, rOper, kolLevel, ojkRptHeaderEntity, ma0004Entity, ojkRulesEntity);
            } else {                        // Insert
                ojkRulesEntity = createNewEntity(reportCode==null?"":reportCode);
                fillOJKRulesEntity(sheetRow, rCoa, rOper, kolLevel, ojkRptHeaderEntity, ma0004Entity, ojkRulesEntity);
            }
            listOfOJKRulesEntity.add(ojkRulesEntity);
        }
        saveAll(reportCode==null?"":reportCode, listOfOJKRulesEntity);
    }

    private OJKRulesEntity checkRecord(String reportCode, String sheetRow, String rCoa) {
        switch (reportCode) {
            case "LK01": return ojkRulesLK01Repo.findBySheetRowAndRCoa(sheetRow, rCoa);
            case "LK02": return ojkRulesLK02Repo.findBySheetRowAndRCoa(sheetRow, rCoa);
            case "LK03": return ojkRulesLK03Repo.findBySheetRowAndRCoa(sheetRow, rCoa);
            case "LK04": return ojkRulesLK04Repo.findBySheetRowAndRCoa(sheetRow, rCoa);
            case "PK01": return ojkRulesPK01Repo.findBySheetRowAndRCoa(sheetRow, rCoa);
            case "PK02": return ojkRulesPK02Repo.findBySheetRowAndRCoa(sheetRow, rCoa);
            default: return null;
        }
    }

    private OJKRulesEntity createNewEntity(String reportCode) {
        switch (reportCode) {
            case "LK01": return new OJKRulesLK01Entity();
            case "LK02": return new OJKRulesLK02Entity();
            case "LK03": return new OJKRulesLK03Entity();
            case "LK04": return new OJKRulesLK04Entity();
            case "PK01": return new OJKRulesPK01Entity();
            case "PK02": return new OJKRulesPK02Entity();
            default: return null;
        }
    }

    @SuppressWarnings("unchecked")
    private void saveAll(String reportCode, List listOfOJKRulesEntity) {
        switch (reportCode) {
            case "LK01": ojkRulesLK01Repo.saveAll(listOfOJKRulesEntity); break;
            case "LK02": ojkRulesLK02Repo.saveAll(listOfOJKRulesEntity); break;
            case "LK03": ojkRulesLK03Repo.saveAll(listOfOJKRulesEntity); break;
            case "LK04": ojkRulesLK04Repo.saveAll(listOfOJKRulesEntity); break;
            case "PK01": ojkRulesPK01Repo.saveAll(listOfOJKRulesEntity); break;
            case "PK02": ojkRulesPK02Repo.saveAll(listOfOJKRulesEntity); break;
        }
    }

    private void fillOJKRulesEntity(String sheetRow, String rCoa, String rOper, Integer kolLevel,
                                        OJKRptHeaderEntity ojkRptHeaderEntity, MA0004Entity ma0004Entity,
                                        OJKRulesEntity ojkRulesEntity) {
        if (ojkRulesEntity!=null) {
            ojkRulesEntity.setSheetRow(sheetRow);
            ojkRulesEntity.setRowType(ojkRptHeaderEntity.getRowType());
            ojkRulesEntity.setKolomA(ojkRptHeaderEntity.getKolomA());
            ojkRulesEntity.setKolomB(ojkRptHeaderEntity.getKolomB());
            ojkRulesEntity.setKolomC(ojkRptHeaderEntity.getKolomC());
            ojkRulesEntity.setKolAkun(ojkRptHeaderEntity.getKolAkun());
            ojkRulesEntity.setKolRollUp(ojkRptHeaderEntity.getKolRollUp());
            ojkRulesEntity.setKolLevel(kolLevel);
            ojkRulesEntity.setKolNormal(ma0004Entity==null?"":ma0004Entity.getCoaNormal().toString());
            ojkRulesEntity.setrCoa(rCoa);
            ojkRulesEntity.setrOper(rOper);
        }
    }

    @Override
    public Object delete(Map<String, Object> param) {
        try {
            deleteProcess(param);
            return resultComponent.createResponse(null);
        } catch (Exception e) {
            return resultComponent.createResponse(e);
        }
    }

    @Transactional
    protected void deleteProcess(Map<String, Object> param) {
        Long idKey = Param.getLong(param, "idKey");
        String reportCode = Param.getStr(param, "reportCode");
        switch(reportCode) {
            case "LK01":    ojkRulesLK01Repo.deleteById(idKey);
                            break;
            case "LK02":    ojkRulesLK02Repo.deleteById(idKey);
                            break;
            case "LK03":    ojkRulesLK03Repo.deleteById(idKey);
                            break;
            case "LK04":    ojkRulesLK04Repo.deleteById(idKey);
                            break;
            case "PK01":    ojkRulesPK01Repo.deleteById(idKey);
                            break;
            case "PK02":    ojkRulesPK02Repo.deleteById(idKey);
                            break;
        }
    }

    @Override
    public List<DropdownIdText> getReportCodes() {
        return ojkRptHeaderRepo.getDropDownReportCodes();
    }

    @Override
    public List<DropdownDash> getDropDownRow(Map<String, Object> param){
        return ojkRptHeaderRepo.getDropDownRow(Param.getStr(param, "reportCode"));
    }

    @Override
    public List<DropdownIdText> getDropDownCOA() {
        return ma0004Repo.getAllCOAs();
    }
}
