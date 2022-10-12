package com.biru.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0002Entity;

public interface TR0002Repo extends PagingAndSortingRepository<TR0002Entity, String> {
	
	public List<TR0002Entity> findByGlVoucherId(String glVoucherId);
	
	@Query("select t.glOrgDebit from TR0002Entity t "
			+ "where t.idKey = (select min(tr2.idKey) from TR0002Entity tr2 "
			+ "where tr2.glVoucherId = ?1 "
			+ "and tr2.glDescription = ?2 "
			+ "and tr2.glOrgDebit > 0 group by tr2.glVoucherId)")
	public BigDecimal findByGlVoucherIdAndGlDescriptionPY(String glVoucherId, String glDescription);
	
	@Query("select t.glOrgCredit from TR0002Entity t "
			+ "where t.idKey = (select min(tr2.idKey) from TR0002Entity tr2 "
			+ "where tr2.glVoucherId = ?1 "
			+ "and tr2.glDescription = ?2 "
			+ "and tr2.glOrgCredit > 0 group by tr2.glVoucherId)")
	public BigDecimal findByGlVoucherIdAndGlDescriptionRC(String glVoucherId, String glDescription);
	
	@Query("select new com.biru.entity.TR0002Entity"
			+ "(t2.glAccount, sum(t2.glIdrCredit), sum(t2.glIdrDebit), "
			+ "sum(t2.glOrgCredit), sum(t2.glOrgDebit)) "
			+ "from TR0001Entity t1, TR0002Entity t2, MA0004Entity m "
			+ "where t1.glVoucherId = t2.glVoucherId "
			+ "and t2.glAccount = m.coaCode "
			+ "and t1.glTrxMonth = ?1 "
			+ "and t1.glTrxYear = ?2 "
			+ "and t1.glDataStatus = '11' "
			+ "and m.coaHeader = 'D' "
			+ "group by t2.glAccount")
	public List<TR0002Entity> summaryByAccount(Byte p_Month, Short p_Year);
	
	@Query("select t from TR0002Entity t "
			+ "where t.glTrxClass = ?1 "
			+ "and t.glType = ?2 "
			+ "and t.glVoucherId = ?3")
	public List<TR0002Entity> findTR0002(String p_TrxClass, String p_GlType, String p_VoucherId);
	
	public List<TR0002Entity> findByGlTypeAndGlVoucherId(String p_Type, String p_VoucherId);
	
	@Query("select sum(t2.glIdrCredit) from TR0002Entity t2 "
			+ "where t2.glVoucherId = ?1 "
			+ "group by t2.glVoucherId")
	public BigDecimal summaryIdrCreditByGlVoucherId(String p_GlVoucherId);
	
	@Query("select sum(t2.glIdrDebit) from TR0002Entity t2 "
			+ "where t2.glVoucherId = ?1 "
			+ "group by t2.glVoucherId")
	public BigDecimal summaryIdrDebitByGlVoucherId(String p_GlVoucherId);
	
	@Query("select new com.biru.entity.TR0002Entity(t2Entity.glOrgCredit, t2Entity.glOrgDebit, t2Entity.glCurrRate, coa.coaDescript) "
			+ "from TR0002Entity t2Entity, MA0004Entity coa "
			+ "where t2Entity.glAccount = coa.coaCode "
			+ "and t2Entity.idKey = (select max(t2.idKey) from TR0002Entity t2, MA0004Entity m4 "
			+ "where t2.glAccount = m4.coaCode "
			+ "and t2.glVoucherId = ?1 "
			+ "and m4.coaBankBk = '1')")
	public TR0002Entity getBank(String p_GlVoucherId);
	
	@Query("select t2Entity.glCurrId from TR0002Entity t2Entity, MA0004Entity coa "
			+ "where t2Entity.glAccount = coa.coaCode "
			+ "and t2Entity.idKey = (select max(t2.idKey) from TR0002Entity t2, MA0004Entity m4 "
			+ "where t2.glAccount = m4.coaCode "
			+ "and t2.glVoucherId = ?1 "
			+ "and m4.coaBankBk = '1')")
	public String getBankCurrId(String p_GlVoucherId);
	
	@Transactional
	@Modifying
	@Query("delete TR0002Entity t where t.glVoucherId = ?1")
	public int deleteByGlVoucherId(String p_GlVoucherId);
	
	@Query("select new com.biru.entity.TR0002Entity"
			+ "(t.glType, t.glAccount, coa.coaDescript, t.glCurrId, t.glCurrRate, "
			+ "t.glOrgCredit, t.glOrgDebit, t.glIdrCredit, t.glIdrDebit, t.glDescription) from TR0002Entity t, MA0004Entity coa "
			+ "where t.glAccount = coa.coaCode "
			+ "and t.glVoucherId = ?1 "
			+ "and t.idKey <> (select max(tr2.idKey) from TR0002Entity tr2, MA0004Entity m4 "
								+ "where tr2.glAccount = m4.coaCode and m4.coaBankBk = '1' and tr2.glVoucherId = ?1)")
	public List<TR0002Entity> findDetailBankBook(String p_GlVoucherId);
	
	@Query("select t from TR0002Entity t "
			+ "where t.glVoucherId = ?1 "
			+ "and t.idKey = (select max(tr2.idKey) from TR0002Entity tr2, MA0004Entity m4 "
								+ "where tr2.glAccount = m4.coaCode and m4.coaBankBk = '1' and tr2.glVoucherId = ?1)")
	public TR0002Entity findBank(String p_GlVoucherId);
	
	@Query("select new com.biru.entity.TR0002Entity"
				+ "(t2.glAccount, sum(t2.glIdrCredit), sum(t2.glIdrDebit), "
				+ "sum(t2.glOrgCredit), sum(t2.glOrgDebit)) "
			+ "from TR0001Entity t1, TR0002Entity t2, MA0004Entity m "
			+ "where t1.glVoucherId = t2.glVoucherId "
				+ "and t2.glAccount = m.coaCode "
				+ "and t1.glTrxYear = ?1 "
				+ "and t1.glTrxDate <= ?2 "
				+ "and t1.glDataStatus = '11' "
				+ "and m.coaHeader = 'D' "
			+ "group by t2.glAccount")
	public List<TR0002Entity> getMutationTB(Short p_Year, Date p_AsAt);
	
}
