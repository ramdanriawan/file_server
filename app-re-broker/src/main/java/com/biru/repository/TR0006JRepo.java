package com.biru.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006JEntity;

public interface TR0006JRepo extends PagingAndSortingRepository<TR0006JEntity, String> {
	
	@Transactional
	@Modifying
	@Query("delete TR0006JEntity t where t.trxClass = ?1 and t.trxTrxId = ?2 and t.trxVoucherId = ?3")
	public int deleteByTrxClassAndTrxIdAndTrxVoucherId(String trxClass, String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006JEntity(t.trxNonPro, t.trxInsStart, t.trxInsEnd, "
			+ "DATE_FORMAT(t.trxInsStart, '%d/%m/%Y'), DATE_FORMAT(t.trxInsEnd, '%d/%m/%Y'), "
			+ "t.trxCoverCode, t.trxLayer, t.trxBasicCover, t.trxCurrId, t.trxExchange, t.trxUsdRate, "
			+ "t.trxCobGroup, t.trxRemarks, t.trxReInst, t.trxReinsRate, "
			+ "t.trxLimitAmt, t.trxDeducAmt, t.trxXolRate, t.trxDepositRate, "
			+ "t.trxOgnrpi, t.trxOgnrpiAct, t.trxMaxAccpt, t.trxOwnAmt, "
			+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.trxCobGroup), "
			+ "(select m11.tcDesc from MA0011Entity m11 where m11.tcCode = t.trxCoverCode), "
			+ "t.trxXolReas, t.trxDepositReas) "
		+ "from TR0006JEntity t "
	+ "where t.trxClass = ?1 "
	+ "and t.trxTrxId = ?2 "
	+ "and t.trxVoucherId = ?3")
	public List<TR0006JEntity> getAdditionalTreaty(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	@Query("select max(t.trxNonPro) from TR0006JEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3 "
			+ "group by t.trxVoucherId")
	public String getTypeOfTreaty(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006JEntity(t.trxCobGroup, t.trxCoverCode, t.trxLayer, "
				+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.trxCobGroup), "
				+ "(select m11.tcDesc from MA0011Entity m11 where m11.tcCode = t.trxCoverCode)) "
			+ "from TR0006JEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3 "
			+ "group by t.trxCoverCode, t.trxLayer "
			+ "order by t.trxCoverCode, t.trxLayer")
	public List<TR0006JEntity> getPlacingProp(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006JEntity(t.trxCobGroup, "
			+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.trxCobGroup)) "
			+ "from TR0006JEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3 "
			+ "group by t.trxCobGroup "
			+ "order by t.trxCobGroup asc")
	public List<TR0006JEntity> getPlacingNoProp(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006JEntity(t.trxNonPro, "
				+ "date_format(t.trxInsStart, '%D %M %Y'), "
				+ "date_format(t.trxInsEnd, '%D %M %Y')) "
			+ "from TR0006JEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3")
	public List<TR0006JEntity> getPeriod(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	@Query("select DISTINCT m11.tcDesc from TR0006JEntity t, MA0011Entity m11 "
		+ "where m11.tcCode = t.trxCoverCode "
		+ "and t.trxClass = ?1 "
		+ "and t.trxTrxId = ?2 "
		+ "and t.trxVoucherId = ?3 "
		+ "and t.trxCobGroup = ?4")
	public List<String> getListCoverByGroup(String p_TrxClass, String p_TrxId, String p_TrxVoucherId, String p_TrxCobGroup);
	
	public List<TR0006JEntity> findByTrxVoucherId(String p_VoucherId);
	
	@Query("select new com.biru.entity.TR0006JEntity(t.idKey, t.trxCobGroup, "
			+ "t.trxCoverCode, t.trxLayer, "
			+ "t.trxOgnrpi, t.trxOgnrpiAct, "
			+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.trxCobGroup), "
			+ "(select m11.tcDesc from MA0011Entity m11 where m11.tcCode = t.trxCoverCode)) "
		+ "from TR0006JEntity t "
	+ "where t.trxClass = ?1 "
	+ "and t.trxTrxId = ?2 "
	+ "and t.trxVoucherId = ?3")
	public List<TR0006JEntity> getDetailAdjustment(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);

	@Query("select max(t.trxDlaValue) from TR0006JEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3 "
			+ "and t.trxCobGroup = ?4 "
			+ "and t.trxCoverCode = ?5 "
			+ "and t.trxLayer = ?6")
	public BigDecimal getDlaValue(String p_TrxClass, String p_TrxId, String p_TrxVoucherId, 
			String p_TrxCobGroup, String p_TrxCoverCode, String p_TrxLayer);
	
	@Transactional
	@Modifying
	@Query("update TR0006JEntity t set t.trxDlaValue = (t.trxDlaValue + ?1) "
			+ "where t.trxClass = ?2 "
			+ "and t.trxTrxId = ?3 "
			+ "and t.trxVoucherId = ?4 "
			+ "and t.trxCobGroup = ?5 "
			+ "and t.trxCoverCode = ?6 "
			+ "and t.trxLayer = ?7")
	public int updateDla(BigDecimal dla, String p_TrxClass, String p_TrxId, String p_TrxVoucherId,
			String p_TrxCobGroup, String p_TrxCoverCode, String p_TrxLayer);
	
	@Query("select t from TR0006JEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3 "
			+ "and t.trxCobGroup = ?4 "
			+ "and t.trxCoverCode = ?5 "
			+ "and t.trxLayer = ?6")
	public List<TR0006JEntity> getDataUpdatedDla(String p_TrxClass, String p_TrxId, String p_TrxVoucherId,
			String p_TrxCobGroup, String p_TrxCoverCode, String p_TrxLayer);
	
	@Query("select sum(t.trxReInst) from TR0006JEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3 "
			+ "and t.trxCobGroup = ?4 "
			+ "and t.trxLayer = ?5")
	public BigDecimal sumTrxReInst(String p_TrxClass, String p_TrxId, String p_TrxVoucherId, 
			String p_TrxCobGroup, String p_TrxLayer);
	
	@Query("select new com.biru.entity.TR0006JEntity(max(t.trxReinsNo), sum(t.trxDeducAmt), "
			+ "sum(t.trxLimitAmt), sum(trxOgnrpi), "
			+ "sum(t.trxXolRate), sum(t.trxDepositRate)) "
		+ "from TR0006JEntity t "
	+ "where t.trxVoucherId = ?1 "
	+ "and t.trxCobGroup = ?2 "
	+ "and t.trxLayer = ?3")
	public TR0006JEntity getTR6JClaims(String p_TrxVoucherId, String p_TrxCobGroup, String p_TrxLayer);
	
}