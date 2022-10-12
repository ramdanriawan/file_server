package com.biru.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.GL0001Entity;
import com.biru.helper.BalanceHelper;
import com.biru.helper.GL0001Helper;

public interface GL0001Repo extends PagingAndSortingRepository<GL0001Entity, Long> {
	
	@Query("select new com.biru.common.entity.DropdownIdText(g.glBalYear, g.glBalYear) "
			+ "from GL0001Entity g group by g.glBalYear")
	public List<DropdownIdText> findYear();
	
	@Query("select new com.biru.helper.GL0001Helper(g.coaCode, m.coaDescript, g.glBalYear, "
			+ "g.glBalDebit0, g.glBalCredit0) "
			+ "from GL0001Entity g, MA0004Entity m "
			+ "where g.coaCode = m.coaCode "
			+ "and g.glBalYear like ?1")
	public Page<GL0001Helper> findBeginBal(String p_GlBalYear, Pageable p_Pageable);
	
	
	public GL0001Entity findByCoaCodeAndGlBalYear(String p_CoaCode, String p_GlBalYear);
	
	@Query("select sum(g.glBalDebit0) from GL0001Entity g "
			+ "where g.glBalYear = ?1 "
			+ "group by g.glBalDebit0")
	public BigDecimal sumGlBalDebit0(String p_GlBalYear);
	
	@Query("select sum(g.glBalCredit0) from GL0001Entity g "
			+ "where g.glBalYear = ?1 "
			+ "group by g.glBalCredit0")
	public BigDecimal sumGlBalCredit0(String p_GlBalYear);
	
	@Query("select new com.biru.helper.GL0001Helper('', 'Total', g.glBalYear, "
			+ "sum(g.glBalDebit0), sum(g.glBalCredit0)) "
			+ "from GL0001Entity g "
			+ "where g.glBalYear = ?1 "
			+ "group by g.glBalYear")
	public GL0001Helper summaryByYear(String p_GlBalYear);
	
	@Query("select new com.biru.helper.GL0001Helper('', 'Total', '', "
			+ "sum(g.glBalDebit0), sum(g.glBalCredit0)) "
			+ "from GL0001Entity g")
	public GL0001Helper summaryAll();
	
	@Query("select new com.biru.helper.BalanceHelper(case "
			+ "when (?1 = '01' and m.coaNormal = 'C') then (g.glBalCredit1-g.glBalDebit1) "
			+ "when (?1 = '02' and m.coaNormal = 'C') then (g.glBalCredit2-g.glBalDebit2) "
			+ "when (?1 = '03' and m.coaNormal = 'C') then (g.glBalCredit3-g.glBalDebit3) "
			+ "when (?1 = '04' and m.coaNormal = 'C') then (g.glBalCredit4-g.glBalDebit4) "
			+ "when (?1 = '05' and m.coaNormal = 'C') then (g.glBalCredit5-g.glBalDebit5) "
			+ "when (?1 = '06' and m.coaNormal = 'C') then (g.glBalCredit6-g.glBalDebit6) "
			+ "when (?1 = '07' and m.coaNormal = 'C') then (g.glBalCredit7-g.glBalDebit7) "
			+ "when (?1 = '08' and m.coaNormal = 'C') then (g.glBalCredit8-g.glBalDebit8) "
			+ "when (?1 = '09' and m.coaNormal = 'C') then (g.glBalCredit9-g.glBalDebit9) "
			+ "when (?1 = '10' and m.coaNormal = 'C') then (g.glBalCredit10-g.glBalDebit10) "
			+ "when (?1 = '11' and m.coaNormal = 'C') then (g.glBalCredit11-g.glBalDebit11) "
			+ "when (?1 = '12' and m.coaNormal = 'C') then (g.glBalCredit12-g.glBalDebit12) "
			+ "when (?1 = '01' and m.coaNormal = 'D') then (g.glBalDebit1-g.glBalCredit1) "
			+ "when (?1 = '02' and m.coaNormal = 'D') then (g.glBalDebit2-g.glBalCredit2) "
			+ "when (?1 = '03' and m.coaNormal = 'D') then (g.glBalDebit3-g.glBalCredit3) "
			+ "when (?1 = '04' and m.coaNormal = 'D') then (g.glBalDebit4-g.glBalCredit4) "
			+ "when (?1 = '05' and m.coaNormal = 'D') then (g.glBalDebit5-g.glBalCredit5) "
			+ "when (?1 = '06' and m.coaNormal = 'D') then (g.glBalDebit6-g.glBalCredit6) "
			+ "when (?1 = '07' and m.coaNormal = 'D') then (g.glBalDebit7-g.glBalCredit7) "
			+ "when (?1 = '08' and m.coaNormal = 'D') then (g.glBalDebit8-g.glBalCredit8) "
			+ "when (?1 = '09' and m.coaNormal = 'D') then (g.glBalDebit9-g.glBalCredit9) "
			+ "when (?1 = '10' and m.coaNormal = 'D') then (g.glBalDebit10-g.glBalCredit10) "
			+ "when (?1 = '11' and m.coaNormal = 'D') then (g.glBalDebit11-g.glBalCredit11) "
			+ "when (?1 = '12' and m.coaNormal = 'D') then (g.glBalDebit12-g.glBalCredit12) "
		+ "end, "
		+ "case " 
			+ "when (?1 = '01' and m.coaNormal = 'C') then (g.glBalCIdr1-g.glBalDIdr1) "
			+ "when (?1 = '02' and m.coaNormal = 'C') then (g.glBalCIdr2-g.glBalDIdr2) "
			+ "when (?1 = '03' and m.coaNormal = 'C') then (g.glBalCIdr3-g.glBalDIdr3) "
			+ "when (?1 = '04' and m.coaNormal = 'C') then (g.glBalCIdr4-g.glBalDIdr4) "
			+ "when (?1 = '05' and m.coaNormal = 'C') then (g.glBalCIdr5-g.glBalDIdr5) "
			+ "when (?1 = '06' and m.coaNormal = 'C') then (g.glBalCIdr6-g.glBalDIdr6) "
			+ "when (?1 = '07' and m.coaNormal = 'C') then (g.glBalCIdr7-g.glBalDIdr7) "
			+ "when (?1 = '08' and m.coaNormal = 'C') then (g.glBalCIdr8-g.glBalDIdr8) "
			+ "when (?1 = '09' and m.coaNormal = 'C') then (g.glBalCIdr9-g.glBalDIdr9) "
			+ "when (?1 = '10' and m.coaNormal = 'C') then (g.glBalCIdr10-g.glBalDIdr10) "
			+ "when (?1 = '11' and m.coaNormal = 'C') then (g.glBalCIdr11-g.glBalDIdr11) "
			+ "when (?1 = '12' and m.coaNormal = 'C') then (g.glBalCIdr12-g.glBalDIdr12) "
			+ "when (?1 = '01' and m.coaNormal = 'D') then (g.glBalDIdr1-g.glBalCIdr1) "
			+ "when (?1 = '02' and m.coaNormal = 'D') then (g.glBalDIdr2-g.glBalCIdr2) "
			+ "when (?1 = '03' and m.coaNormal = 'D') then (g.glBalDIdr3-g.glBalCIdr3) "
			+ "when (?1 = '04' and m.coaNormal = 'D') then (g.glBalDIdr4-g.glBalCIdr4) "
			+ "when (?1 = '05' and m.coaNormal = 'D') then (g.glBalDIdr5-g.glBalCIdr5) "
			+ "when (?1 = '06' and m.coaNormal = 'D') then (g.glBalDIdr6-g.glBalCIdr6) "
			+ "when (?1 = '07' and m.coaNormal = 'D') then (g.glBalDIdr7-g.glBalCIdr7) "
			+ "when (?1 = '08' and m.coaNormal = 'D') then (g.glBalDIdr8-g.glBalCIdr8) "
			+ "when (?1 = '09' and m.coaNormal = 'D') then (g.glBalDIdr9-g.glBalCIdr9) "
			+ "when (?1 = '10' and m.coaNormal = 'D') then (g.glBalDIdr10-g.glBalCIdr10) "
			+ "when (?1 = '11' and m.coaNormal = 'D') then (g.glBalDIdr11-g.glBalCIdr11) "
			+ "when (?1 = '12' and m.coaNormal = 'D') then (g.glBalDIdr12-g.glBalCIdr12) "
		+ "end, m.coaClass) from GL0001Entity g, MA0004Entity m "
		+ "where g.coaCode = m.coaCode "
			+ "and g.glBalYear = ?2 "
			+ "and m.coaRoleUp = '' "
			+ "and m.coaClass in ('3','4')")
	public List<BalanceHelper> findByYearAndClassAndRoleUpNull(String p_Month, String p_Year);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit1 = 0, g.glBalDebit1 = 0, "
			+ "g.glBalCIdr1 = 0, g.glBalDIdr1 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomJanuary(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit2 = 0, g.glBalDebit2 = 0, "
			+ "g.glBalCIdr2 = 0, g.glBalDIdr2 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomFebruary(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit3 = 0, g.glBalDebit3 = 0, "
			+ "g.glBalCIdr3 = 0, g.glBalDIdr3 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomMarch(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit4 = 0, g.glBalDebit4 = 0, "
			+ "g.glBalCIdr4 = 0, g.glBalDIdr4 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomApril(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit5 = 0, g.glBalDebit5 = 0, "
			+ "g.glBalCIdr5 = 0, g.glBalDIdr5 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomMay(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit6 = 0, g.glBalDebit6 = 0, "
			+ "g.glBalCIdr6 = 0, g.glBalDIdr6 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomJune(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit7 = 0, g.glBalDebit7 = 0, "
			+ "g.glBalCIdr7 = 0, g.glBalDIdr7 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomJuly(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit8 = 0, g.glBalDebit8 = 0, "
			+ "g.glBalCIdr8 = 0, g.glBalDIdr8 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomAugust(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit9 = 0, g.glBalDebit9 = 0, "
			+ "g.glBalCIdr9 = 0, g.glBalDIdr9 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomSeptember(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit10 = 0, g.glBalDebit10 = 0, "
			+ "g.glBalCIdr10 = 0, g.glBalDIdr10 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomOctober(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit11 = 0, g.glBalDebit11 = 0, "
			+ "g.glBalCIdr11 = 0, g.glBalDIdr11 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomNovember(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit12 = 0, g.glBalDebit12 = 0, "
			+ "g.glBalCIdr12 = 0, g.glBalDIdr12 = 0, "
			+ "g.glBalCredit13 = 0, g.glBalDebit13 = 0, "
			+ "g.glBalCIdr13 = 0, g.glBalDIdr13 = 0, "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int initBalanceEomDecember(String p_Year, String p_User);
	
	@Query("select g from GL0001Entity g "
			+ "where g.glBalYear = ?3 "
			+ "and exists ("
				+ "select t2 from TR0001Entity t1, TR0002Entity t2, MA0004Entity m "
					+ "where t1.glVoucherId = t2.glVoucherId "
					+ "and t2.glAccount = m.coaCode "
					+ "and t2.glAccount = g.coaCode "
					+ "and t1.glTrxMonth = ?1 "
					+ "and t1.glTrxYear = ?2 "
					+ "and t1.glDataStatus = '11' "
					+ "and m.coaHeader = 'D'"
			+ ")")
	public List<GL0001Entity> findBalanceEom(Byte p_Month, Short p_Year, String p_YearStr);
	
	@Query("select g from GL0001Entity g, MA0004Entity m "
			+ "where g.coaCode = m.coaCode "
			+ "and g.glBalYear = ?1 "
			+ "and m.coaHeader = 'D' "
			+ "and m.coaRevalt = '1'")
	public List<GL0001Entity> findBalanceAllClassEom(String p_Year);
	
	@Query("select new com.biru.entity.GL0001Entity"
				+ "(m.coaRoleUp, "
				+ "sum(g.glBalCredit1), sum(g.glBalDebit1), sum(g.glBalCIdr1), sum(g.glBalDIdr1), "
				+ "sum(g.glBalCredit2), sum(g.glBalDebit2), sum(g.glBalCIdr2), sum(g.glBalDIdr2), "
				+ "sum(g.glBalCredit3), sum(g.glBalDebit3), sum(g.glBalCIdr3), sum(g.glBalDIdr3), "
				+ "sum(g.glBalCredit4), sum(g.glBalDebit4), sum(g.glBalCIdr4), sum(g.glBalDIdr4), "
				+ "sum(g.glBalCredit5), sum(g.glBalDebit5), sum(g.glBalCIdr5), sum(g.glBalDIdr5), "
				+ "sum(g.glBalCredit6), sum(g.glBalDebit6), sum(g.glBalCIdr6), sum(g.glBalDIdr6), "
				+ "sum(g.glBalCredit7), sum(g.glBalDebit7), sum(g.glBalCIdr7), sum(g.glBalDIdr7), "
				+ "sum(g.glBalCredit8), sum(g.glBalDebit8), sum(g.glBalCIdr8), sum(g.glBalDIdr8), "
				+ "sum(g.glBalCredit9), sum(g.glBalDebit9), sum(g.glBalCIdr9), sum(g.glBalDIdr9), "
				+ "sum(g.glBalCredit10), sum(g.glBalDebit10), sum(g.glBalCIdr10), sum(g.glBalDIdr10), "
				+ "sum(g.glBalCredit11), sum(g.glBalDebit11), sum(g.glBalCIdr11), sum(g.glBalDIdr11), "
				+ "sum(g.glBalCredit12), sum(g.glBalDebit12), sum(g.glBalCIdr12), sum(g.glBalDIdr12)) "
			+ "from GL0001Entity g, MA0004Entity m "
			+ "where g.coaCode = m.coaCode "
			+ "and g.glBalYear = ?1 "
			+ "and m.coaLevel = ?2 "
			+ "group by m.coaRoleUp")
	public List<GL0001Entity> summaryRollUp(String p_Year, Integer p_CoaLevel);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit1 = (g.glBalCredit0 + g.glBalCredit13), "
			+ "g.glBalDebit1 = (g.glBalDebit0 + g.glBalDebit13), "
			+ "g.glBalCIdr1 = (g.glBalCIdr0 + g.glBalCIdr13), "
			+ "g.glBalDIdr1 = (g.glBalDIdr0 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomJanuary(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit2 = (g.glBalCredit1 + g.glBalCredit13), "
			+ "g.glBalDebit2 = (g.glBalDebit1 + g.glBalDebit13), "
			+ "g.glBalCIdr2 = (g.glBalCIdr1 + g.glBalCIdr13), "
			+ "g.glBalDIdr2 = (g.glBalDIdr1 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomFebruary(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit3 = (g.glBalCredit2 + g.glBalCredit13), "
			+ "g.glBalDebit3 = (g.glBalDebit2 + g.glBalDebit13), "
			+ "g.glBalCIdr3 = (g.glBalCIdr2 + g.glBalCIdr13), "
			+ "g.glBalDIdr3 = (g.glBalDIdr2 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomMarch(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit4 = (g.glBalCredit3 + g.glBalCredit13), "
			+ "g.glBalDebit4 = (g.glBalDebit3 + g.glBalDebit13), "
			+ "g.glBalCIdr4 = (g.glBalCIdr3 + g.glBalCIdr13), "
			+ "g.glBalDIdr4 = (g.glBalDIdr3 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomApril(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit5 = (g.glBalCredit4 + g.glBalCredit13), "
			+ "g.glBalDebit5 = (g.glBalDebit4 + g.glBalDebit13), "
			+ "g.glBalCIdr5 = (g.glBalCIdr4 + g.glBalCIdr13), "
			+ "g.glBalDIdr5 = (g.glBalDIdr4 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomMay(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit6 = (g.glBalCredit5 + g.glBalCredit13), "
			+ "g.glBalDebit6 = (g.glBalDebit5 + g.glBalDebit13), "
			+ "g.glBalCIdr6 = (g.glBalCIdr5 + g.glBalCIdr13), "
			+ "g.glBalDIdr6 = (g.glBalDIdr5 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomJune(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit7 = (g.glBalCredit6 + g.glBalCredit13), "
			+ "g.glBalDebit7 = (g.glBalDebit6 + g.glBalDebit13), "
			+ "g.glBalCIdr7 = (g.glBalCIdr6 + g.glBalCIdr13), "
			+ "g.glBalDIdr7 = (g.glBalDIdr6 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomJuly(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit8 = (g.glBalCredit7 + g.glBalCredit13), "
			+ "g.glBalDebit8 = (g.glBalDebit7 + g.glBalDebit13), "
			+ "g.glBalCIdr8 = (g.glBalCIdr7 + g.glBalCIdr13), "
			+ "g.glBalDIdr8 = (g.glBalDIdr7 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomAugust(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit9 = (g.glBalCredit8 + g.glBalCredit13), "
			+ "g.glBalDebit9 = (g.glBalDebit8 + g.glBalDebit13), "
			+ "g.glBalCIdr9 = (g.glBalCIdr8 + g.glBalCIdr13), "
			+ "g.glBalDIdr9 = (g.glBalDIdr8 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomSeptember(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit10 = (g.glBalCredit9 + g.glBalCredit13), "
			+ "g.glBalDebit10 = (g.glBalDebit9 + g.glBalDebit13), "
			+ "g.glBalCIdr10 = (g.glBalCIdr9 + g.glBalCIdr13), "
			+ "g.glBalDIdr10 = (g.glBalDIdr9 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomOctober(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit11 = (g.glBalCredit10 + g.glBalCredit13), "
			+ "g.glBalDebit11 = (g.glBalDebit10 + g.glBalDebit13), "
			+ "g.glBalCIdr11 = (g.glBalCIdr10 + g.glBalCIdr13), "
			+ "g.glBalDIdr11 = (g.glBalDIdr10 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomNovember(String p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalCredit12 = (g.glBalCredit11 + g.glBalCredit13), "
			+ "g.glBalDebit12 = (g.glBalDebit11 + g.glBalDebit13), "
			+ "g.glBalCIdr12 = (g.glBalCIdr11 + g.glBalCIdr13), "
			+ "g.glBalDIdr12 = (g.glBalDIdr11 + g.glBalDIdr13), "
			+ "g.modifyBy = ?2, g.modifyOn = sysdate() "
			+ "where g.glBalYear = ?1")
	public int updateEomDecember(String p_Year, String p_User);
	
	@Query("select sum(g.glBalDIdr13)-sum(g.glBalCIdr13) from GL0001Entity g, MA0004Entity m "
			+ "where g.glBalYear = ?1 "
			+ "and m.coaHeader = 'D'")
	public BigDecimal getDifferentIdr13(String p_GlBalYear);
	
	@Query("select new com.biru.entity.GL0001Entity(g.coaCode, m.coaNormal, "
			+ "g.glBalCIdr1, g.glBalDIdr1, "
			+ "g.glBalCIdr2, g.glBalDIdr2, "
			+ "g.glBalCIdr3, g.glBalDIdr3, "
			+ "g.glBalCIdr4, g.glBalDIdr4, "
			+ "g.glBalCIdr5, g.glBalDIdr5, "
			+ "g.glBalCIdr6, g.glBalDIdr6, "
			+ "g.glBalCIdr7, g.glBalDIdr7, "
			+ "g.glBalCIdr8, g.glBalDIdr8, "
			+ "g.glBalCIdr9, g.glBalDIdr9, "
			+ "g.glBalCIdr10, g.glBalDIdr10, "
			+ "g.glBalCIdr11, g.glBalDIdr11, "
			+ "g.glBalCIdr12, g.glBalDIdr12) "
			+ "from GL0001Entity g, MA0004Entity m "
			+ "where g.coaCode = m.coaCode "
			+ "and g.glBalYear = ?1 "
			+ "and m.coaHeader = 'D'")
	public List<GL0001Entity> getBalanceDetailByYear(String p_Year);
	
	@Query("select count(g) from GL0001Entity g where g.glBalYear = ?1")
	public Long countByGlBalYear(String year);
	
	public  Page<GL0001Entity> findByGlBalYear(String p_GlBalYear, Pageable p);
	
	@Query("select sum(g.glBalDIdr0) = sum(g.glBalCIdr0) from GL0001Entity g where g.glBalYear = ?1")
	public Boolean comperDIdrCIdrEoy(String glBalYear);
	
	@Query("select sum(g.glBalCIdr0) from GL0001Entity g where g.glBalYear = ?1")
	public String sumCIdr0(String glBalYear);
	
	@Query("select sum(g.glBalDIdr0) from GL0001Entity g where g.glBalYear = ?1")
	public String comperDIdr0(String glBalYear);
	
	@Transactional
	@Modifying
	@Query("update GL0001Entity g "
			+ "set g.glBalDebit0 = 0, "
			+ "g.glBalCredit0 = 0, "
			+ "g.glBalDIdr0 = 0, "
			+ "g.glBalCIdr0 = 0 "
			+ "where g.glBalYear = ?1 "
			+ "and g.coaCode in ("
				+ "select m.coaCode from MA0004Entity m where m.coaHeader = 'H' "
				+ "and m.coaDataStatus = '11'"
			+ ")")
	public void updateCoaH0ForEoy(String glBalYear);
	
	@Transactional
	@Modifying
	@Query("delete from GL0001Entity g "
			+ "where g.glBalYear = ?1")
	public void deleteGl001Eoy(String p_Year);
	
	public  GL0001Entity findByGlBalYear(String p_GlBalYear);
}
