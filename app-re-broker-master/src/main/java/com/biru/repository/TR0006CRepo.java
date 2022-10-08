package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006CEntity;

public interface TR0006CRepo extends PagingAndSortingRepository<TR0006CEntity, String> {
	
	@Transactional
	@Modifying
	@Query("delete TR0006CEntity t where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public int deleteByTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006CEntity(t.idKey, t.trxBfeeBuy, t.trxBfeeSell, t.trxCurrId, "
				+ "t.trxCurrRate, t.trxDiscSell, t.trxInsSub, t.trxPremiumBuy, t.trxPremiumSell, t.trxSumInsured, "
				+ "t.trxTrxId, t.trxVatBuy, t.trxVatSell, t.trxVoucherId, "
				+ "(select distinct tr6a.trxInsInsured from TR0006AEntity tr6a "
						+ "where t.trxTrxId = tr6a.trxTrxId and t.trxVoucherId = tr6a.trxVoucherId "
						+ "and t.trxInsSub = tr6a.trxInsSub), t.trxNetTou, t.trxNetTtl) "
			+ "from TR0006CEntity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2")
	public List<TR0006CEntity> getDataValue(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t from TR0006CEntity t "
		+ "where t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2")
	public List<TR0006CEntity> findByTrxIdAndTrxVoucherId(String p_TrxId, String p_TrxVoucherId);
	
	public List<TR0006CEntity> findByTrxVoucherId(String p_TrxVoucherId);
	
}
