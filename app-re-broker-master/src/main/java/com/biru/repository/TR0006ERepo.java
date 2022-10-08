package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006EEntity;

public interface TR0006ERepo extends PagingAndSortingRepository<TR0006EEntity, String> {

	@Transactional
	@Modifying
	@Query("delete TR0006EEntity t where t.trxTrxId = ?1 and t.trxVoucherId = ?2")
	public int deleteByTrxIdAndTrxVoucherId(String trxTrxId, String trxVoucherId);
	
	@Transactional
	@Modifying
	@Query("delete TR0006EEntity t where t.trxClass = ?1 and t.trxTrxId = ?2 and t.trxVoucherId = ?3")
	public int deleteByTrxClassAndTrxIdAndTrxVoucherId(String trxClass, String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0006EEntity(t.trxPrAmt, t.trxPrShare, "
			+ "DATE_FORMAT(t.trxPrDate, '%d/%m/%Y'), t.trxPrClient) "
			+ "from TR0006EEntity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2")
	public List<TR0006EEntity> getDataChecklist1(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006EEntity(t.trxPrAmt, t.trxPrShare, "
			+ "DATE_FORMAT(t.trxPrDate, '%d/%m/%Y'), t.trxPrClient, m.cliName) "
			+ "from TR0006EEntity t "
			+ "left join MA0005Entity m on (t.trxPrClient = m.cliCode) "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2 "
			+ "order by t.trxPrDate asc")
	public List<TR0006EEntity> getDataChecklist1V2(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select new com.biru.entity.TR0006EEntity(t.trxPrAmt, t.trxPrShare, "
			+ "DATE_FORMAT(t.trxPrDate, '%d/%m/%Y'), t.trxPrClient) "
			+ "from TR0006EEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3")
	public List<TR0006EEntity> getDataChecklist1Treaty(String p_TrxClass, String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t from TR0006EEntity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2 "
			+ "and t.trxPrClient = ?3")
	public List<TR0006EEntity> findByTrxIdAndTrxVoucherIdAndTrxPrClient(String p_TrxId, String p_TrxVoucherId, String p_TrxPrClient);
	
	@Query("select t from TR0006EEntity t "
			+ "where t.trxClass = ?1 "
			+ "and t.trxTrxId = ?2 "
			+ "and t.trxVoucherId = ?3 "
			+ "and t.trxPrClient = ?4")
	public List<TR0006EEntity> findByTrxIdAndTrxVoucherIdAndTrxPrClient(String p_TrxClass, String p_TrxId, String p_TrxVoucherId, String p_TrxPrClient);
	
	public List<TR0006EEntity> findByTrxVoucherId(String p_TrxVoucherId);
	
	@Query("select t from TR0006EEntity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2 "
			+ "and exists (select t6 from TR0006Entity t6 "
							+ "where t6.trxTrxId = t.trxTrxId "
							+ "and t6.trxVoucherId = t.trxVoucherId "
							+ "and t6.trxClient = t.trxPrClient)")
	public List<TR0006EEntity> findByChecklistClient(String p_TrxId, String p_TrxVoucherId);
	
	@Query("select t from TR0006EEntity t "
			+ "where t.trxTrxId = ?1 "
			+ "and t.trxVoucherId = ?2 "
			+ "and exists (select t6b from TR0006BEntity t6b "
							+ "where t6b.trxTrxId = t.trxTrxId "
							+ "and t6b.trxVoucherId = t.trxVoucherId "
							+ "and t6b.trxInsId = t.trxPrClient)")
	public List<TR0006EEntity> findByChecklistReins(String p_TrxId, String p_TrxVoucherId);
	
}