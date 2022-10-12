package com.biru.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="OJK_RPT_HEADER")
@NamedQuery(name="OJKRptHeaderEntity.findAll", query="SELECT m FROM OJKRptHeaderEntity m")
public class OJKRptHeaderEntity implements Serializable {
    @Id
    @Column(name = "ID_KEY")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long idKey;

    @Column(name = "SHEET_ID")
    private String sheetId;

    @Column(name = "SHEET_ROW")
    private String sheetRow;

    @Column(name = "ROW_TYPE")
    private String rowType;

    @Column(name = "KOLOM_A")
    private String kolomA;

    @Column(name = "KOLOM_B")
    private String kolomB;

    @Column(name = "KOL_AKUN")
    private String kolAkun;

    @Column(name = "KOL_ROLLUP")
    private String kolRollUp;

    @Column(name = "KOLOM_C")
    private String kolomC;

    @Column(name = "KOLOM_D")
    private String kolomD;

    @Column(name = "KOLOM_E")
    private String kolomE;

    @Column(name = "KOLOM_F")
    private String kolomF;

    @Column(name = "KOLOM_G")
    private String kolomG;

    @Column(name = "KOLOM_H")
    private String kolomH;

    @Column(name = "KOLOM_I")
    private String kolomI;

    @Column(name = "KOLOM_J")
    private String kolomJ;

    @Column(name = "KOLOM_K")
    private String kolomK;

    @Column(name = "KOLOM_L")
    private String kolomL;

    @Column(name = "KOLOM_M")
    private String kolomM;

    @Column(name = "KOLOM_N")
    private String kolomN;

    @Column(name = "KOLOM_O")
    private String kolomO;

    public OJKRptHeaderEntity() {}

    public OJKRptHeaderEntity(String sheetId, String sheetRow, String rowType, String kolAkun,
                              String kolRollUp, String kolomA, String kolomB, String kolomC,
                              String kolomD, String kolomE, String kolomF, String kolomG,
                              String kolomH, String kolomI, String kolomJ, String kolomK,
                              String kolomL, String kolomM, String kolomN, String kolomO) {
        super();
        this.sheetId = sheetId;
        this.sheetRow = sheetRow;
        this.rowType = rowType;
        this.kolAkun = kolAkun;
        this.kolRollUp = kolRollUp;
        this.kolomA = kolomA;
        this.kolomB = kolomB;
        this.kolomC = kolomC;
        this.kolomD = kolomD;
        this.kolomE = kolomE;
        this.kolomF = kolomF;
        this.kolomG = kolomG;
        this.kolomH = kolomH;
        this.kolomI = kolomI;
        this.kolomJ = kolomJ;
        this.kolomK = kolomK;
        this.kolomL = kolomL;
        this.kolomM = kolomM;
        this.kolomN = kolomN;
        this.kolomO = kolomO;
    }

    public Long getIdKey() {
        return idKey;
    }

    public String getSheetId() {
        return sheetId;
    }

    public void setSheetId(String sheetId) {
        this.sheetId = sheetId;
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

    public String getKolomD() {
        return kolomD;
    }

    public void setKolomD(String kolomD) {
        this.kolomD = kolomD;
    }

    public String getKolomE() {
        return kolomE;
    }

    public void setKolomE(String kolomE) {
        this.kolomE = kolomE;
    }

    public String getKolomF() {
        return kolomF;
    }

    public void setKolomF(String kolomF) {
        this.kolomF = kolomF;
    }

    public String getKolomG() {
        return kolomG;
    }

    public void setKolomG(String kolomG) {
        this.kolomG = kolomG;
    }

    public String getKolomH() {
        return kolomH;
    }

    public void setKolomH(String kolomH) {
        this.kolomH = kolomH;
    }

    public String getKolomI() {
        return kolomI;
    }

    public void setKolomI(String kolomI) {
        this.kolomI = kolomI;
    }

    public String getKolomJ() {
        return kolomJ;
    }

    public void setKolomJ(String kolomJ) {
        this.kolomJ = kolomJ;
    }

    public String getKolomK() {
        return kolomK;
    }

    public void setKolomK(String kolomK) {
        this.kolomK = kolomK;
    }

    public String getKolomL() {
        return kolomL;
    }

    public void setKolomL(String kolomL) {
        this.kolomL = kolomL;
    }

    public String getKolomM() {
        return kolomM;
    }

    public void setKolomM(String kolomM) {
        this.kolomM = kolomM;
    }

    public String getKolomN() {
        return kolomN;
    }

    public void setKolomN(String kolomN) {
        this.kolomN = kolomN;
    }

    public String getKolomO() {
        return kolomO;
    }

    public void setKolomO(String kolomO) {
        this.kolomO = kolomO;
    }

    @Override
    public String toString() {
        return "OJKRptHeaderEntity [idKey=" + idKey + ", sheetId=" + sheetId + ", sheetRow=" + sheetRow +
                ", rowType=" + rowType + ", kolAkun=" + kolAkun + ", kolRollUp=" + kolRollUp +
                ", kolomA=" + kolomA + ", kolomB=" + kolomB + ", kolomC=" + kolomC +
                ", kolomD=" + kolomD + ", kolomE=" + kolomE + ", kolomF=" + kolomF +
                ", kolomG=" + kolomG + ", kolomH=" + kolomH + ", kolomI=" + kolomI +
                ", kolomJ=" + kolomJ + ", kolomK=" + kolomK + ", kolomL=" + kolomL +
                ", kolomM=" + kolomM + ", kolomN=" + kolomN + ", kolomO=" + kolomO + "]";
    }
}
