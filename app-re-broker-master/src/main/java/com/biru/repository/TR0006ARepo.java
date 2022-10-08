package com.biru.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006AEntity;

public interface TR0006ARepo extends PagingAndSortingRepository<TR0006AEntity, String> {
	
	@Transactional
	@Modifying
	@Query("delete TR0006AEntity t where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public int deleteByTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006AEntity(t.trxCombined, t.trxCoverClass, t.trxCoverCode, t.trxCurrId, "
				+ "t.trxCurrRate, t.trxInsInsured, t.trxInsLocation, t.trxInsSub, t.trxPremiumRate, t.trxRemarks, "
				+ "t.trxSumInsured, t.trxPremiumAmt, t.trxTrxId, t.trxVoucherId, t.trxWeightRate, DATE_FORMAT(t.trxInsStart, '%d/%m/%Y'), "
				+ "DATE_FORMAT(t.trxInsEnd, '%d/%m/%Y'), DATE_FORMAT(t.trxReinsEnd, '%d/%m/%Y'), DATE_FORMAT(t.trxReinsStart, '%d/%m/%Y'), "
				+ "t.trxBfeeSell, t.trxDeducPct) "
			+ "from TR0006AEntity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public List<TR0006AEntity> getDataInterest(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006AEntity(t.trxCombined, t.trxCoverClass, t.trxCoverCode, t.trxCurrId, "
				+ "t.trxCurrRate, t.trxInsInsured, t.trxInsLocation, t.trxInsSub, t.trxPremiumRate, t.trxRemarks, "
				+ "t.trxSumInsured, t.trxTrxId, t.trxVoucherId, t.trxWeightRate, "
				+ "DATE_FORMAT(t.trxInsStart, '%d/%m/%Y'), "
				+ "DATE_FORMAT(t.trxInsEnd, '%d/%m/%Y'), "
				+ "t.trxInsDur, t.trxBfeeSell, t.trxDeducPct, t.trxPpw, t.trxQuoValid) "
			+ "from TR0006AEntity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public List<TR0006AEntity> getDataInterestDirect(String p_TrxId, String p_TrxVoucherId);
	
	public List<TR0006AEntity> findByTrxTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	public List<TR0006AEntity> findByTrxTrxIdAndTrxVoucherIdAndTrxInsSub(String p_TrxId, String p_TrxVoucherId, Integer p_InsSub);
	
	@Query("select max(t.trxCoverCode) from TR0006AEntity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public String findTypeOfCoverByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select max(t.trxRemarks) from TR0006AEntity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public String findTrxRemarksByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006AEntity(max(t.trxInsStart), max(t.trxInsEnd), max(t.trxReinsStart), max(t.trxReinsEnd)) from TR0006AEntity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2")
	public TR0006AEntity findPeriodByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t from TR0006AEntity t "
			+ "where t.idKey = (select min(a.idKey) from TR0006AEntity a where a.trxTrxId = ?1 "
							+ "and a.trxVoucherId = ?2)")
	public TR0006AEntity findOneIIByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t from TR0006AEntity t "
			+ "where t.idKey = (select max(a.idKey) from TR0006AEntity a where a.trxTrxId = ?1 "
							+ "and a.trxVoucherId = ?2 order by a.idKey desc)")
	public TR0006AEntity findOneIIIByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select sum(t.trxSumInsured) from TR0006AEntity t "
			+ "where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public BigDecimal findTotalSumInsuredByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	public List<TR0006AEntity> findByTrxVoucherId(String p_TrxVoucherId);
	
	@Transactional
	@Modifying
	@Query("update TR0006AEntity t set t.trxRemarks = ?1 where t.trxTrxId = ?2 and t.trxVoucherId = ?3")
	public int updateTrxRemarks(String trxRemarks, String trxTrxId, String trxVoucherId);
	
	@Query("select min(t.trxInsStart) from TR0006AEntity t "
			+ "where t.trxVoucherId in (?1)")
	public Date findMinTrxInsStartByTrxVoucherId(List<String>rxVoucherId);
	
	@Query("select max(t.trxInsEnd) from TR0006AEntity t "
			+ "where t.trxVoucherId in (?1)")
	public Date findMaxTrxInsEndByTrxVoucherId(List<String>rxVoucherId);
}
