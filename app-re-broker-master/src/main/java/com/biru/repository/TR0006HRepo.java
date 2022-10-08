package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0006HEntity;

public interface TR0006HRepo extends PagingAndSortingRepository<TR0006HEntity, String> {

	@Query("select t from TR0006HEntity t "
		+ "where t.trxOldType = ?1 "
		+ "and t.trxOldVoucherId = ?2")
	public Page<TR0006HEntity> getDataClaim(String trxTrxId, String trxVoucherId, Pageable pageable);
	
	public List<TR0006HEntity> findByTrxVoucherId(String p_TrxVoucherId);
	
	public List<TR0006HEntity> findByTrxFileNameEndsWith(String p_FileName);
	
	@Query("select new com.biru.entity.TR0006HEntity(t.idKey, t.trxBrdxId, t.trxPartyName, "
			+ "DATE_FORMAT(t.trxNewStart, '%d %b %Y'), "
			+ "DATE_FORMAT(t.trxNewEnd, '%d %b %Y'), "
			+ "t.trxCurrId, t.trxTsiAmount, "
			+ "t.trxBenefit, t.trxClaimStatus, m.paChildDesc, t.trxInterest, t.trxFileName) "
		+ "from TR0006HEntity t, MA0014Entity m "
		+ "where t.trxOldType = ?1 "
		+ "and t.trxOldVoucherId = ?2 "
		+ "and t.trxClaimStatus = '0' "
		+ "and m.paParentCode = 'STDATA' "
		+ "and m.paChildValue = t.trxClaimStatus "
		+ "and ("
			+ "t.trxBrdxId like %?3% or "
			+ "t.trxPartyName like %?3% or "
			+ "DATE_FORMAT(t.trxNewStart, '%d %b %Y') like %?3% or "
			+ "DATE_FORMAT(t.trxNewEnd, '%d %b %Y') like %?3% or "
			+ "t.trxCurrId like %?3% or "
			+ "FORMAT(t.trxTsiAmount, 2) like %?3% or "
			+ "t.trxBenefit like %?3% or "
			+ "m.paChildDesc like %?3%"
		+ ")")
	public Page<TR0006HEntity> getDataClaims(String trxTrxId, String trxVoucherId, String search, Pageable pageable);
	
	public List<TR0006HEntity>findByTrxOldVoucherIdEqualsAndTrxFileNameEquals(String trxOldVoucherId, String trxfileName);

	@Transactional
	@Modifying
	@Query("update TR0006HEntity t set t.trxDataStatus = ?3 where t.trxVoucherId = ?1 and t.trxTrxId = ?2")
	public int updateTrxDataStatusByTrxVoucherIdAndTrxTrxId(String trxVoucherId, String trxTrxId, String trxDataStatus);

	@Query(value = "select * from TR0006H t where t.TRX_OLD_VOUCHER_ID = ?1 "
			+ "ORDER BY ID_KEY DESC LIMIT 1", nativeQuery = true)
	public TR0006HEntity findByTrxOldVoucherId(String p_TrxOldVoucherId);
	
	@Query(value = "select * from TR0006H t where t.TRX_OLD_VOUCHER_ID = ?1 "
			+ "and t.TRX_CLIENT_ID = ?2 "
			+ "ORDER BY ID_KEY DESC LIMIT 1", nativeQuery = true)
	public TR0006HEntity findByTrxOldVoucherIdAndCliCode(String p_TrxOldVoucherId, String p_CliCode);
} 
