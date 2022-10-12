package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006KEntity;

public interface TR0006KRepo extends PagingAndSortingRepository<TR0006KEntity, String> {
	
	@Transactional
	@Modifying
	@Query("delete TR0006KEntity t where t.trxClass = ?1 and t.trxTrxId = ?2 and t.trxVoucherId = ?3")
	public int deleteByTrxClassAndTrxIdAndTrxVoucherId(String trxClass, String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006KEntity(t.idKey, t.trxClass, t.trxTrxId, t.trxVoucherId, "
			+ "t.trxCobGroup, t.trxCoverCode, t.trxLayer, t.trxInsId, t.trxInsShare, t.trxPremium, t.trxRiComm, m5.cliName, "
			+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.trxCobGroup), "
			+ "(select m11.tcDesc from MA0011Entity m11 where m11.tcCode = t.trxCoverCode)) "
		+ "from TR0006KEntity t, MA0005Entity m5 "
		+ "where t.trxInsId = m5.cliCode "
		+ "and t.trxClass = ?1 "
		+ "and t.trxTrxId = ?2 "
		+ "and t.trxVoucherId = ?3")
	public List<TR0006KEntity> getReinsuranceTreaty(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006KEntity(t.idKey, t.trxClass, t.trxTrxId, t.trxVoucherId, "
			+ "t.trxCobGroup, t.trxCoverCode, t.trxLayer, t.trxInsId, t.trxInsShare, t.trxPremium, t.trxRiComm, m5.cliName, "
			+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.trxCobGroup), "
			+ "(select m11.tcDesc from MA0011Entity m11 where m11.tcCode = t.trxCoverCode)) "
		+ "from TR0006KEntity t, MA0005Entity m5 "
		+ "where t.trxInsId = m5.cliCode "
		+ "and t.trxClass = ?1 "
		+ "and t.trxTrxId = ?2 "
		+ "and t.trxVoucherId = ?3 "
		+ "and t.trxCoverCode = ?4 "
		+ "and t.trxLayer = ?5")
	public List<TR0006KEntity> getReinsuranceTreaty(String p_TrxClass, String p_TrxId, String p_TrxVoucherId,
			String p_TrxCoverCode, String p_TrxLayer);
	
	@Query("select new com.biru.entity.TR0006KEntity(t.idKey, t.trxClass, t.trxTrxId, t.trxVoucherId, "
			+ "t.trxCobGroup, t.trxCoverCode, t.trxLayer, t.trxInsId, t.trxInsShare, t.trxPremium, t.trxRiComm, m5.cliName, "
			+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.trxCobGroup), "
			+ "(select m11.tcDesc from MA0011Entity m11 where m11.tcCode = t.trxCoverCode)) "
		+ "from TR0006KEntity t, MA0005Entity m5 "
		+ "where t.trxInsId = m5.cliCode "
		+ "and t.trxClass = ?1 "
		+ "and t.trxTrxId = ?2 "
		+ "and t.trxVoucherId = ?3 "
		+ "and t.trxCobGroup = ?4 "
		+ "and t.trxLayer = ?5 "
		+ "order by t.trxInsShare desc")
	public List<TR0006KEntity> findReinsByGroupAndLayer(String p_TrxClass, String p_TrxId, String p_TrxVoucherId, 
			String p_TrxCobGroup, String p_TrxLayer);
	
	@Query("select new com.biru.entity.TR0006KEntity(t.idKey, t.trxClass, t.trxTrxId, t.trxVoucherId, "
			+ "t.trxCobGroup, t.trxCoverCode, t.trxLayer, t.trxInsId, t.trxInsShare, t.trxPremium, t.trxRiComm, m5.cliName, "
			+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.trxCobGroup), "
			+ "(select m11.tcDesc from MA0011Entity m11 where m11.tcCode = t.trxCoverCode)) "
		+ "from TR0006KEntity t, MA0005Entity m5 "
		+ "where t.trxInsId = m5.cliCode "
		+ "and t.trxClass = ?1 "
		+ "and t.trxTrxId = ?2 "
		+ "and t.trxVoucherId = ?3 "
		+ "and t.trxCobGroup = ?4 "
		+ "and t.trxLayer = ?5 "
		+ "and t.trxInsId = ?6 "
		+ "order by t.trxInsShare desc")
	public List<TR0006KEntity> findReinsByGroupAndLayerAndTrxInsId(String p_TrxClass, String p_TrxId, String p_TrxVoucherId, 
			String p_TrxCobGroup, String p_TrxLayer, String p_TrxInsId);
	
	@Query("select t from TR0006KEntity t "
		+ "where t.trxClass = ?1 "
		+ "and t.trxTrxId = ?2 "
		+ "and t.trxVoucherId = ?3 "
		+ "and t.trxCobGroup = ?4 "
		+ "and t.trxInsId = ?5 "
		+ "order by t.trxLayer asc")
	public List<TR0006KEntity> findReinsByGroupAndTrxInsId(String p_TrxClass, String p_TrxId, String p_TrxVoucherId, 
			String p_TrxCobGroup, String p_TrxInsId);
	
	@Query("select t from TR0006KEntity t "
			+ "where t.trxVoucherId = ?1 "
			+ "and t.trxCobGroup = ?2 "
			+ "and t.trxLayer = ?3 "
			+ "order by t.trxInsShare desc")
	public List<TR0006KEntity> findLeadReinsByGroupAndLayer(String p_TrxVoucherId, String p_TrxCobGroup, String p_TrxLayer);
	
	@Query("select t from TR0006KEntity t "
			+ "where t.trxVoucherId = ?1 "
			+ "order by t.trxInsShare desc")
	public List<TR0006KEntity> findLeadReinsForTreaty(String p_TrxVoucherId);
	
	@Query("select t from TR0006KEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3 "
			+ "and t.trxCobGroup = ?4 "
			+ "order by t.trxLayer asc")
		public List<TR0006KEntity> findReinsByGroup(String p_TrxClass, String p_TrxId, String p_TrxVoucherId, 
				String p_TrxCobGroup);
	
	@Query("select new com.biru.entity.TR0006KEntity(t.idKey, t.trxClass, t.trxTrxId, t.trxVoucherId, "
			+ "t.trxInsId, t.trxInsShare, m5.cliName) "
		+ "from TR0006KEntity t, MA0005Entity m5 "
		+ "where t.trxInsId = m5.cliCode "
		+ "and t.trxTrxId = ?1 "
		+ "and t.trxVoucherId = ?2 "
		+ "order by t.idKey asc")
	public List<TR0006KEntity> getReinsuranceClaims(String p_TrxId, String p_TrxVoucherId);
	
}