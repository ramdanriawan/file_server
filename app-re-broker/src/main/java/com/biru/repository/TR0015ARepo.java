package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0015AEntity;

public interface TR0015ARepo extends PagingAndSortingRepository<TR0015AEntity, Long> {
	
	@Query("select new com.biru.entity.TR0015AEntity(t.idKey, t.txType, t.txVoucherId, t.txCobGroup, "
			+ "t.txCoverId, t.txClaimType, t.txClaimRemarks, t.txCurrId, t.txClaimPla, t.txClaimDla, "
			+ "t.txPaidDate, t.txDataStatus, t.txReinsNo, "
			+ "(select m14.paChildDesc from MA0014Entity m14 where m14.paParentCode = 'COBGROUP' and m14.paChildValue = t.txCobGroup), "
			+ "(select m11.tcDesc from MA0011Entity m11 where m11.tcCode = t.txCoverId), "
			+ "m14Type.paChildDesc, m14Status.paChildDesc, DATE_FORMAT(t.txPaidDate, '%d/%m/%Y'), t.txLayer, t.txMemo, "
			+ "t.txRowNo) "
		+ "from TR0015AEntity t, MA0014Entity m14Type, MA0014Entity m14Status "
		+ "where t.txType = ?1 "
		+ "and t.txVoucherId = ?2 "
		+ "and m14Type.paChildValue = t.txClaimType "
		+ "and m14Type.paParentCode = 'CLAIMTP' "
		+ "and m14Status.paChildValue = t.txDataStatus "
		+ "and m14Status.paParentCode = 'STDATA' "
		+ "order by t.txRowNo asc, t.idKey asc")
	public List<TR0015AEntity> findValue(String txType, String txVoucherId);
	
	@Modifying
	@Transactional
	@Query("delete from TR0015AEntity t "
			+ "where t.txType = ?1 "
			+ "and t.txVoucherId = ?2 "
			+ "and t.idKey not in ?3")
	public int deleteByTxTypeAndTxVoucherIdAndIdKeyNotIn(String txType, String txVoucherId, List<Long> idKeys);
	
	@Query("select t from TR0015AEntity t "
		+ "where t.txType = ?1 "
		+ "and t.txVoucherId = ?2 "
		+ "and t.txDataStatus <> ?3")
	public List<TR0015AEntity> findByTxTypeAndTxVoucherIdAndTxDataStatusNot(
			String txType, String txVoucherId, String txDataStatus);
	
	@Query("select t from TR0015AEntity t "
		+ "where t.txType = ?1 "
		+ "and t.txVoucherId = ?2")
	public List<TR0015AEntity> findByTxTypeAndTxVoucherId(
			String txType, String txVoucherId);
	
}
