package com.biru.entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name="OJK_RULES_LK03")
@NamedQuery(name="OJKRulesLK03Entity.findAll", query="SELECT r FROM OJKRulesLK03Entity r")
public class OJKRulesLK03Entity extends OJKRulesEntity implements Serializable {
    @Id
    @Column(name = "ID_KEY")
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long idKey;
    @Column(name = "SHEET_ROW")
    protected String sheetRow;
    @Column(name = "ROW_TYPE")
    protected String rowType;
    @Column(name = "KOLOM_A")
    protected String kolomA;
    @Column(name = "KOLOM_B")
    protected String kolomB;
    @Column(name = "KOLOM_C")
    protected String kolomC;
    @Column(name = "KOL_AKUN")
    protected String kolAkun;
    @Column(name = "KOL_ROLLUP")
    protected String kolRollUp;
    @Column(name = "KOL_LEVEL")
    protected Integer kolLevel;
    @Column(name = "KOL_NORMAL")
    protected String kolNormal;
    @Column(name = "R_OPER")
    protected String rOper;
    @Column(name = "R_COA")
    protected String rCoa;
    @Transient
    protected String coaDescript;
    @Transient
    private String action;

    public static final String reportCode = "LK03";

    public OJKRulesLK03Entity() {
    }

    public OJKRulesLK03Entity(Long idKey, String sheetRow, String rowType, String kolomA, String kolomB, String kolomC,
                              String kolAkun, String kolRollUp, Integer kolLevel, String kolNormal, String rOper,
                              String rCoa, String coaDescript) {
        this.idKey = idKey;
        this.sheetRow = sheetRow;
        this.rowType = rowType;
        this.kolomA = kolomA;
        this.kolomB = kolomB;
        this.kolomC = kolomC;
        this.kolAkun = kolAkun;
        this.kolRollUp = kolRollUp;
        this.kolLevel = kolLevel;
        this.kolNormal = kolNormal;
        this.rOper = rOper;
        this.rCoa = rCoa;
        this.coaDescript = coaDescript;
    }

    public Long getIdKey() {
        return idKey;
    }

    public String getSheetRow() {
        return sheetRow;
    }

    public void setSheetRow(String sheetRow) {
        this.sheetRow = sheetRow;
    }

    public String getRowType() {
        return rowType;
    }

    public void setRowType(String rowType) {
        this.rowType = rowType;
    }

    public String getKolomA() {
        return kolomA;
    }

    public void setKolomA(String kolomA) {
        this.kolomA = kolomA;
    }

    public String getKolomB() {
        return kolomB;
    }

    public void setKolomB(String kolomB) {
        this.kolomB = kolomB;
    }

    public String getKolomC() {
        return kolomC;
    }

    public void setKolomC(String kolomC) {
        this.kolomC = kolomC;
    }

    public String getKolAkun() {
        return kolAkun;
    }

    public void setKolAkun(String kolAkun) {
        this.kolAkun = kolAkun;
    }

    public String getKolRollUp() {
        return kolRollUp;
    }

    public void setKolRollUp(String kolRollUp) {
        this.kolRollUp = kolRollUp;
    }

    public Integer getKolLevel() {
        return kolLevel;
    }

    public void setKolLevel(Integer kolLevel) {
        this.kolLevel = kolLevel;
    }

    public String getKolNormal() {
        return kolNormal;
    }

    public void setKolNormal(String kolNormal) {
        this.kolNormal = kolNormal;
    }

    public String getrOper() {
        return rOper;
    }

    public void setrOper(String rOper) {
        this.rOper = rOper;
    }

    public String getrCoa() {
        return rCoa;
    }

    public void setrCoa(String rCoa) {
        this.rCoa = rCoa;
    }

    public String getCoaDescript() {
        return coaDescript;
    }

    public void setCoaDescript(String coaDescript) {
        this.coaDescript = coaDescript;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    @Override
    public String toString() {
        return "OJKRulesLK03Entity [idKey=" + idKey + ", sheetRow=" + sheetRow + ", rowType=" +
                rowType + ", kolomA=" + kolomA + ", kolomB=" + kolomB + ", kolomC=" + kolomC +
                ", kolAkun=" + kolAkun + ", kolRollUp=" + kolRollUp + ", kolLevel=" + kolLevel +
                ", kolNormal=" + kolNormal + ", rOper=" + rOper + ", rCoa=" + rCoa + "]";
    }
}
