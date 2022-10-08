package com.biru.repository;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006Entity;

public interface TR0006Repo extends PagingAndSortingRepository<TR0006Entity, String> {
	
	@Transactional
	@Modifying
	@Query("delete TR0006Entity t where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public int deleteByTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	
	@Transactional
	@Modifying
	@Query("delete TR0006Entity t where t.trxClass = ?1 and t.trxTrxId = ?2 and t.trxVoucherId = ?3")
	public int deleteByTrxClassAndTrxIdAndTrxVoucherId(String trxClass, String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006Entity(t.createBy, t.createOn, t.trxClient, t.trxCurrId, t.trxInsuredName, "
				+ "t.trxCurrRate, t.trxDiretPy, t.trxOfficer, t.trxPayMthd, t.trxPeriodVld, t.trxPpwCli, "
				+ "t.trxRemarks, t.trxShare, t.trxSumInsured, t.trxTrxId, t.trxVoucherId, t.trxFeeClient, "
				+ "t.trxTsiAmount, m.cliName, DATE_FORMAT(t.trxDate, '%d/%m/%Y')) "
			+ "from TR0006Entity t, MA0005Entity m "
			+ "where t.trxClient = m.cliCode "
			+ "and t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2")
	public List<TR0006Entity> getDataClient(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006Entity(t.createBy, t.createOn, t.trxClient, t.trxCurrId, t.trxInsuredName, "
			+ "t.trxCurrRate, t.trxDiretPy, t.trxOfficer, t.trxPayMthd, t.trxPeriodVld, t.trxPpwCli, "
			+ "t.trxRemarks, t.trxShare, t.trxSumInsured, t.trxTrxId, t.trxVoucherId, t.trxFeeClient, "
			+ "t.trxTsiAmount, m.cliName, DATE_FORMAT(t.trxDate, '%d/%m/%Y')) "
		+ "from TR0006Entity t, MA0005Entity m "
		+ "where t.trxClient = m.cliCode "
		+ "and t.trxClass = ?1 "
		+ "and t.trxTrxId = ?2 "
		+ "and t.trxVoucherId = ?3")
	public List<TR0006Entity> getDataClient(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	public TR0006Entity findByTrxVoucherIdAndTrxDataStatus (String p_TrxVoucherId, String trxDataStatus);
	public List<TR0006Entity> findByTrxTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	public List<TR0006Entity> findByTrxClassAndTrxTrxIdAndTrxVoucherId(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	public List<TR0006Entity> findByTrxTrxIdAndTrxVoucherIdAndTrxClient(String p_TrxId, String p_TrxVoucherId, String p_TrxClient);
	
	@Transactional
	@Modifying
	@Query("update TR0006Entity t set t.trxDataStatus = ?1, t.trxRemarks = ?2 where t.trxTrxId = ?3 and t.trxVoucherId = ?4")
	public int updateTrxDataStatus(String p_TrxDataStatus, String p_TrxRemarks, String trxTrxId, String trxVoucherId);
	
	@Transactional
	@Modifying
	@Query("update TR0006Entity t set t.trxDataStatus = ?1, t.trxRemarks = ?2 where t.trxClass = ?3 and t.trxTrxId = ?4 and t.trxVoucherId = ?5")
	public int updateTrxDataStatus(String p_TrxDataStatus, String p_TrxRemarks, String p_TrxClass, String trxTrxId, String trxVoucherId);
	
	@Transactional
	@Modifying
	@Query("update TR0006Entity t set t.trxDataStatus = ?1, t.trxRemarks = ?2, t.trxClosingDate = ?3 "
			+ "where t.trxTrxId = ?4 and t.trxVoucherId = ?5")
	public int updateTrxDataStatus(String p_TrxDataStatus, String p_TrxRemarks, Date p_TrxClosingDate,
			String trxTrxId, String trxVoucherId);
	
	@Transactional
	@Modifying
	@Query("update TR0006Entity t set t.trxDataStatus = ?1, t.trxRemarks = ?2, t.trxClosingDate = ?3 "
			+ "where t.trxClass = ?4 and t.trxTrxId = ?5 and t.trxVoucherId = ?6")
	public int updateTrxDataStatus(String p_TrxDataStatus, String p_TrxRemarks, Date p_TrxClosingDate,
			String trxClass, String trxTrxId, String trxVoucherId);
	
	@Transactional
	@Modifying
	@Query("update TR0006Entity t set t.trxTreAdj = ?1 "
			+ "where t.trxClass = ?2 and t.trxTrxId = ?3 and t.trxVoucherId = ?4")
	public int updateTrxTreAdj(String trxTreAdj, String trxClass, String trxTrxId, String trxVoucherId);
	
	@Query("select t from TR0006Entity t "
			+ "where t.idKey = (select min(b.idKey) from TR0006Entity b "
							+ "where b.trxTrxId = ?1 "
							+ "and b.trxVoucherId = ?2)")
	public TR0006Entity findOneClientByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select max(t.trxInsuredName) from TR0006Entity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public String findTrxInsuredNameByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select max(t.trxTsiAmount) from TR0006Entity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public BigDecimal findTotalSumInsuredByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	public Page<TR0006Entity> findByTrxTrxIdInAndTrxVoucherIdIn(Collection<String>trxId, Collection<String>trxVoucherId, Pageable pageable);
	
	
	@Transactional
	@Modifying
	@Query("UPDATE TR0006Entity tr6 "
			+ "SET tr6.trxDataStatus = '0', tr6.modifyBy = ?3, tr6.modifyOn = ?4 "
			+ "WHERE tr6.trxTrxId = ?1 and tr6.trxVoucherId = ?2")
	public void updateTrxDataStatus0(String trxTrxId, String trxVoucherId, String user, Date now);
	
	@Query("select sum(t.trxShare) from TR0006Entity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2")
	public BigDecimal getSummaryTrxShareByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t.trxDataStatus from TR0006Entity t "
			+ "where t.idKey = (select min(b.idKey) from TR0006Entity b "
							+ "where b.trxTrxId = ?1 "
							+ "and b.trxVoucherId = ?2)")
	public String findTrxDataStatusByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select max(t.trxPayMthd) from TR0006Entity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public String findTrxPayMthdByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Transactional
	@Modifying
	@Query("update TR0006Entity t set t.trxTreAdj = '0', t.modifyBy = ?1, t.modifyOn = ?2 "
			+ "where t.trxClass = ?3 and t.trxTrxId = ?4 and t.trxVoucherId = ?5")
	public int cancelAdjustment(String user, Date modifyOn, String trxClass, String trxTrxId, String trxVoucherId);
	
	public TR0006Entity findByTrxVoucherId(String trxVoucherId);
	
}