package com.biru.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0015DEntity;
import com.biru.helper.InternalMemoHelper;

public interface TR0015DRepo extends PagingAndSortingRepository<TR0015DEntity, Long> {
	
	@Query("select new com.biru.entity.TR0015DEntity(t.idKey, t.txVoucherId, t.txInsId, "
			+ "m.cliName, t.txCurrId, t.txInsAmount, t.txInsReceivedRc, t.txInsReceivedPy, t.txInsReffIn, "
			+ "t.txInsReffOt, t.txInsAtt) from TR0015DEntity t, MA0005Entity m "
			+ "where t.txVoucherId = ?1 and t.txType = ?2 "
			+ "and t.txInsId = m.cliCode ")
	public List<TR0015DEntity> findByTxVoucherIdAndTxType(String txVoucherId, String txType);
	
	public TR0015DEntity findByTxVoucherIdAndTxInsIdAndTxInsShare(String txVoucherId, String txInsId, BigDecimal txInsShare);
	
	@Query("select new com.biru.helper.InternalMemoHelper(t15d.txCurrId, t15d.txInsId, m5.cliName) "
			+ "from TR0015DEntity t15d, MA0005Entity m5 "
			+ "where t15d.txInsId = m5.cliCode "
			+ "and t15d.txInsReceivedPy <> t15d.txInsReceivedRc "
			+ "and t15d.txInsRdate >= ?1 "
			+ "and t15d.txInsRdate <= ?2 "
			+ "group by t15d.txCurrId, t15d.txInsId")
	public List<InternalMemoHelper> getListInternalMemo(Date startDate, Date endDate);
	
}
