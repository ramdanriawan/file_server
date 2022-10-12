package com.biru.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006DEntity;

public interface TR0006DRepo extends PagingAndSortingRepository<TR0006DEntity, String> {

	@Transactional
	@Modifying
	@Query("delete TR0006DEntity t where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public int deleteByTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	
	@Transactional
	@Modifying
	@Query("delete TR0006DEntity t where t.trxClass = ?1 and t.trxTrxId = ?2 and t.trxVoucherId = ?3")
	public int deleteByTrxClassAndTrxIdAndTrxVoucherId(String trxClass, String trxTrxId, String trxVoucherId);
	
	@Query("select t from TR0006DEntity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2")
			public List<TR0006DEntity> getDataTc(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t from TR0006DEntity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2 "
			+ "and t.trxTcCode in "
			+ "('Period of Insurance','Total Sum Insured','Interest Insured','Reinsured/Cedant' , 'type of cover' , 'The Insured' , 'Order Hereon') ")
	public List<TR0006DEntity> getDataTcEndorsement(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006DEntity(t.idKey, t.trxClass, t.trxTcCode, t.trxTcData, "
			+ "t.trxTrxId, t.trxVoucherId, t.trxNonPro, t.trxCobGroup, t.trxCoverCode, "
			+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.trxCobGroup), "
			+ "(select m11.tcDesc from MA0011Entity m11 where m11.tcCode = t.trxCoverCode)) "
			+ " from TR0006DEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3 ")
	public List<TR0006DEntity> getDataTcTreaty(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	public List<TR0006DEntity> findByTrxVoucherId(String p_TrxVoucherId);
}
