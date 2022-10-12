package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.TR0015Entity;

public interface TR0015Repo extends PagingAndSortingRepository<TR0015Entity, Long> {
	
	@Query("select t from TR0015Entity t where t.txType = ?1 and t.txVoucherId = ?2")
	public TR0015Entity findByTxTypeAndTxVoucherId(String txType, String txVoucherId);
	
	@Query("select new com.biru.entity.TR0015Entity(t.txClientId, (select m.cliName from MA0005Entity m where t.txClientId = m.cliCode), t.txPolicyNo, t.txCoverId, "
				+ "DATE_FORMAT((select max(t6a.trxInsStart) from TR0006AEntity t6a where t.txOldType = t6a.trxTrxId and t.txOldVoucherId = t6a.trxVoucherId), '%d %b %Y'), "
				+ "DATE_FORMAT((select max(t6a.trxInsEnd) from TR0006AEntity t6a where t.txOldType = t6a.trxTrxId and t.txOldVoucherId = t6a.trxVoucherId), '%d %b %Y'), "
				+ "DATE_FORMAT(t.txDateFrom, '%d %b %Y'), DATE_FORMAT(t.txDateTo, '%d %b %Y'), "
				+ "t.txPartyName, t.txBrdxId, t.txInterest, t.txCurrId, t.txSumIns, t.txDataStatus, "
				+ "m14.paChildDesc, t.txAttClient, t.txType, t.txVoucherId, "
				+ "t.txOldType, t.txOldVoucherId, t.relatedIdKey, t.createBy, t.createOn, "
				+ "t.txOrAmt, t.txQsAmt, t.txReinsNo, t.txReinsCl, t.txReinsUw, t.txDcReal, "
				+ "t.txUrAmt, t.txUwclAmt, t.txClclAmt, t.txUwprAmt) "
			+ "from TR0015Entity t, MA0014Entity m14 "
			+ "where t.txType = ?1 "
			+ "and t.txVoucherId = ?2 "
			+ "and m14.paChildValue = t.txDataStatus "
			+ "and m14.paParentCode = 'STDATA'")
	public TR0015Entity findClient(String txType, String txVoucherId);
	
	@Query("select new com.biru.entity.TR0015Entity(t.txType, t.txVoucherId, "
			+ "DATE_FORMAT(t.txLostDate, '%d/%m/%Y'), DATE_FORMAT(t.txAdviceDate, '%d/%m/%Y'), t.txUwReff, t.txMaxPaid, t.txCause, t.txDetails) "
		+ "from TR0015Entity t, MA0014Entity m14 "
		+ "where t.txType = ?1 "
		+ "and t.txVoucherId = ?2 "
		+ "and m14.paChildValue = t.txDataStatus "
		+ "and m14.paParentCode = 'STDATA'")
	public TR0015Entity findClaim(String txType, String txVoucherId);
	
	@Modifying
	@Transactional
	@Query("update TR0015Entity t set t.txDataStatus = ?2 "
		+ "where t.txVoucherId = ?1")
	public int updateStatus(String txVoucherId, String status);
	
	@Query("select new com.biru.common.entity.DropdownIdText(DATE_FORMAT(t.txAdviceDate, '%Y'), DATE_FORMAT(t.txAdviceDate, '%Y')) "
			+ "from TR0015Entity t "
			+ "group by DATE_FORMAT(t.txAdviceDate, '%Y')")	
	public List<DropdownIdText> dropdownYear();
	
}
