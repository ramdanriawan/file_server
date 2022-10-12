package com.biru.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006BEntity;
import com.biru.entity.TR0006Entity;

public interface TR0006BRepo extends PagingAndSortingRepository<TR0006BEntity, String> {
	
	@Transactional
	@Modifying
	@Query("delete TR0006BEntity t where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public int deleteByTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006BEntity(t.idKey, t.trxCurrId, t.trxInsAmt, t.trxPremAmt, t.trxInsConfirmdate, "
			+ "t.trxInsConfirmid, t.trxInsId, t.trxInsPaymethd, t.trxInsRemarks,t.trxInsShare, t.trxInsType, "
			+ "t.trxTrxId, t.trxVoucherId, t.trxInsBfee, t.trxInsPremium, t.trxRicommAmt, "
			+ "(select m.cliName from MA0005Entity m where m.cliCode = t.trxInsId), "
			+ "(select paChildDesc from MA0014Entity where paParentCode = 'PAYMTHD' "
						+ "and paChildStatus = '11' and paChildValue = t.trxInsPaymethd), t.trxPremPortion) "
	+ "from TR0006BEntity t "
	+ "where t.trxTrxId = ?1 "
	+ "and t.trxVoucherId = ?2")
	public List<TR0006BEntity> getDataReinsurance(String p_TrxId, String p_TrxVoucherId);
	
	public List<TR0006BEntity> findByTrxTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	public List<TR0006BEntity> findByTrxTrxIdAndTrxVoucherIdAndTrxInsId(String p_TrxId, String p_TrxVoucherId, String p_TrxInsId);
	public List<TR0006BEntity> findByTrxVoucherIdAndTrxInsId(String p_TrxVoucherId, String p_TrxInsId);
	
	@Query("select t from TR0006BEntity t "
			+ "where t.idKey = (select min(b.idKey) from TR0006BEntity b "
							+ "where b.trxTrxId = ?1 "
							+ "and b.trxVoucherId = ?2 "
							+ "and b.trxInsType = ?3)")
	public TR0006BEntity findOneReinsByTrxIdAndTrxVoucherIdAndTrxInsType(String p_TrxId, String p_TrxVoucherId, String p_TrxInsType);
	
	@Query("select t from TR0006BEntity t "
			+ "where t.idKey = (select min(b.idKey) from TR0006BEntity b "
							+ "where b.trxTrxId = ?1 "
							+ "and b.trxVoucherId = ?2)")
	public TR0006Entity findOneReinsByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t from TR0006BEntity t "
			+ "where t.idKey = (select min(b.idKey) from TR0006BEntity b "
							+ "where b.trxTrxId = ?1 "
							+ "and b.trxVoucherId = ?2 "
							+ "and b.trxInsType = '0')")
	public TR0006BEntity findLeadByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t from TR0006BEntity t where t.trxInsType = '0'")
	public TR0006BEntity findGlTrxClient();
	
	@Query("select sum(t.trxInsShare) from TR0006BEntity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2")
	public BigDecimal getSummaryTrxInsShareByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	public List<TR0006BEntity> findByTrxVoucherId(String p_TrxVoucherId);
	
}
