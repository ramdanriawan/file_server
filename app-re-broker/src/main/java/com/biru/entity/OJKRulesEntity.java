package com.biru.entity;

public abstract class OJKRulesEntity {
    public abstract Long getIdKey();
    public abstract String getSheetRow();
    public abstract void setSheetRow(String sheetRow);
    public abstract String getRowType();
    public abstract void setRowType(String rowType);
    public abstract String getKolomA();
    public abstract void setKolomA(String kolomA);
    public abstract String getKolomB();
    public abstract void setKolomB(String kolomB);
    public abstract String getKolomC();
    public abstract void setKolomC(String kolomC);
    public abstract String getKolAkun();
    public abstract void setKolAkun(String kolAkun);
    public abstract String getKolRollUp();
    public abstract void setKolRollUp(String kolRollUp);
    public abstract Integer getKolLevel();
    public abstract void setKolLevel(Integer kolLevel);
    public abstract String getKolNormal();
    public abstract void setKolNormal(String kolNormal);
    public abstract String getrOper();
    public abstract void setrOper(String rOper);
    public abstract String getrCoa();
    public abstract void setrCoa(String rCoa);
    public abstract String getCoaDescript();
    public abstract void setCoaDescript(String coaDescript);
    public abstract String getAction();
    public abstract void setAction(String action);
}
