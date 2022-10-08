package com.biru.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0001Entity;
import com.biru.helper.TrHelper;

public interface TR0001Repo extends PagingAndSortingRepository<TR0001Entity, String>, JpaSpecificationExecutor<TR0001Entity>{
	public Page<TR0001Entity> findByGlTrxStatusAndGlDataStatus(String glTrxStatus, String glDataStatus, Pageable p);
	public Page<TR0001Entity> findByGlTrxStatusAndGlDataStatusAndGlTypeContains(String glTrxStatus, String glDataStatus, String glType, Pageable p);
	public TR0001Entity findByGlVoucherId(String voucherId);
	public Page<TR0001Entity> findByGlVoucherIdContains(String voucherId, Pageable p);
	
	@Query("select sum(T2.glIdrDebit) from TR0001Entity T1, TR0002Entity T2, MA0004Entity M "
			+ "where T1.glVoucherId = T2.glVoucherId "
			+ "and T2.glAccount = M.coaCode "
			+ "and T1.glTrxClass in ?1 "
			+ "and T1.glType = 'RC' "
			+ "and date_format(T1.glTrxDate, '%Y') = ?2 "
			+ "and T1.glDataStatus = '11'")
	public BigDecimal sumRcByClassesAndYear(List<String> p_TrxClass, String p_Year);
	
	@Query("select sum(T2.glIdrCredit) from TR0001Entity T1, TR0002Entity T2, MA0004Entity M "
			+ "where T1.glVoucherId = T2.glVoucherId "
			+ "and T2.glAccount = M.coaCode "
			+ "and T1.glTrxClass in ?1 "
			+ "and T1.glType = 'PY' "
			+ "and date_format(T1.glTrxDate, '%Y') = ?2 "
			+ "and T1.glDataStatus = '11' "
			+ "and M.coaPrintCf = '1'")
	public BigDecimal sumPyByClassesAndYear(List<String> p_TrxClass, String p_Year);
	
	public Page<TR0001Entity> findAll(Specification<TR0001Entity> specification, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("update TR0001Entity t set t.glTrxStatus = '0', t.modifyBy = ?3, t.modifyOn = sysdate() "
			+ "where t.glTrxMonth = ?1 "
			+ "and t.glTrxYear = ?2 "
			+ "and t.glDataStatus = '11'")
	public int updateCancelEom(Byte p_Month, Short p_Year, String p_User);
	
	@Transactional
	@Modifying
	@Query("update TR0001Entity t set t.glTrxStatus = '1', t.modifyBy = ?3, t.modifyOn = sysdate() "
			+ "where t.glTrxMonth = ?1 "
			+ "and t.glTrxYear = ?2 "
			+ "and t.glDataStatus = '11'")
	public int updateSuccessEom(Byte p_Month, Short p_Year, String p_User);
	
	@Query("select new com.biru.helper.TrHelper"
			+ "(TR2.glVoucherId, (sum(TR2.glIdrDebit)-sum(TR2.glIdrCredit)), sum(TR2.glIdrCredit), sum(TR2.glIdrDebit)) "
			+ "from TR0001Entity TR1, TR0002Entity TR2 "
			+ "where TR1.glVoucherId = TR2.glVoucherId "
			+ "and TR1.glTrxMonth = ?1 "
			+ "and TR1.glTrxYear = ?2 "
			+ "and TR1.glDataStatus = '11' "
			+ "and TR1.glType not in ('CM','CY') "
			+ "group by TR2.glVoucherId "
			+ "having (ceiling(sum(TR2.glIdrDebit))-ceiling(sum(TR2.glIdrCredit))) <> 0")
	public List<TrHelper> findDataEom(Byte p_Month, Short p_Year);
	
	@Query("select max(t.glVoucherId) from TR0001Entity t "
			+ "where t.glTrxClass = ?1 "
			+ "and t.glType = ?2 "
			+ "and t.glTrxMonth = ?3 "
			+ "and t.glTrxYear = ?4 "
			+ "and t.glTrxDesc = ?5")
	public String findVoucherId(String p_TrxClass, String p_GlType, Byte p_Month,
			Short p_Year, String p_Desc);
	
	@Query("select t from TR0001Entity t "
			+ "where t.glTrxClass = ?1 "
			+ "and t.glType = ?2 "
			+ "and t.glTrxMonth = ?3 "
			+ "and t.glTrxYear = ?4 "
			+ "and t.glTrxDesc = ?5 "
			+ "and t.glVoucherId = ?6")
	public List<TR0001Entity> findTR0001(String p_TrxClass, String p_GlType, Byte p_Month,
			Short p_Year, String p_Desc, String p_VoucherId);
	
	public TR0001Entity findByGlTypeAndGlVoucherId(String p_GlType, String p_GlVoucherId);
	
	@Query("select new com.biru.entity.TR0001Entity"
			+ "(t1.idKey, t1.glTrxDate, t1.glVoucherId, t1.glReffId, t1.glTrxDesc, t1.glTrxStatus) "
				+ "from TR0001Entity t1 "
				+ "where DATE_FORMAT(t1.glTrxDate, '%d/%m/%Y') = ?1 "
				+ "and t1.glReffId is not null "
				+ "and t1.glTrxDesc not like 'CANCEL%' "
				+ "and not exists "
					+ "(select t12 from TR0012Entity t12 "
						+ "where t12.trxVoucherId = t1.glVoucherId "
						+ "and t12.trxDataStatus = '13')")
	public Page<TR0001Entity> findBankBookByTrxDate(String p_GlTrxDate, Pageable p_Pageable);
	
	@Query("select new com.biru.entity.TR0001Entity"
			+ "(t1.idKey, t1.glTrxDate, t1.glVoucherId, t1.glReffId, t1.glTrxDesc, t1.glTrxStatus) "
				+ "from TR0001Entity t1 "
				+ "where DATE_FORMAT(t1.createOn, '%d/%m/%Y') = ?1 "
				+ "and t1.glReffId is not null "
				+ "and t1.glTrxDesc not like 'CANCEL%' "
				+ "and not exists "
					+ "(select t12 from TR0012Entity t12 "
						+ "where t12.trxVoucherId = t1.glVoucherId "
						+ "and t12.trxDataStatus = '13')")
	public Page<TR0001Entity> findBankBookByEntryDate(String p_GlTrxDate, Pageable p_Pageable);
	
	@Transactional
	@Modifying
	@Query("delete TR0001Entity t where t.glVoucherId = ?1")
	public int deleteByGlVoucherId(String p_GlVoucherId);
	
} 
