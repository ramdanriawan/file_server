package com.biru.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0003Entity;

public interface TR0003Repo extends PagingAndSortingRepository<TR0003Entity, Long> {
	
	public TR0003Entity findByTrxVoucherIdAndTrxType(String p_TrxVoucherId, String p_TrxType);
	
	public List<TR0003Entity> findByTrxVoucherId(String p_TrxVoucherId);
	
	public List<TR0003Entity> findByTrxDescriptionContainsAndTrxStatusDataEqualsAndCreateOnEquals(String trxVoucherId, String trxStatusData, Date createOn);
	
	@Query(value ="select * " + 
			"from TR0003 tr3 " + 
			"where tr3.TRX_DESCRIPTION like ?1 " +
			"and tr3.TRX_CLIENT = ?2 " + 
			"and tr3.TRX_ASSURED = ?3 " + 
			"and tr3.TRX_STATUS_DATA = '11' "+ 
			"and tr3.CREATE_ON = STR_TO_DATE(?4,'%Y-%m-%d %H:%i:%s') "+ 
			"order by tr3.ID_KEY LIMIT 1", nativeQuery = true)
	public TR0003Entity findClosingDataParameterized(String voucherID, String trxClient, String trxAssured, String createOn);
	
	@Query(value = "select * from TR0003 t where t.TRX_OLD_VOUCHER_ID = ?1 "
			+ "ORDER BY ID_KEY DESC LIMIT 1", nativeQuery = true)
	public TR0003Entity findByTrxOldVoucherId(String p_TrxOldVoucherId);
	
	public List<TR0003Entity> findByTrxOldVoucherIdAndCreateOnAndTrxClient(String trxOldVoucherId, Date createOn, String trxClient);
	
	@Query("select tr3 from TR0003Entity tr3 "
			+ "where tr3.trxOldVoucherId = ?1 "
			+ "and tr3.trxClient = ?2 "
			+ "and tr3.createOn = ?3 "
			+ "and tr3.trxStatusData = '11'")
	public List<TR0003Entity> findTr3ReprintModifyFacultative(String trxOldVoucherId, 
			String trxClient, Date createOn);
	
}
