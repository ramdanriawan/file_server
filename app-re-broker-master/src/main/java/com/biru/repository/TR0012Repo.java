package com.biru.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0012Entity;
import com.biru.helper.OutstandingHelper;
import com.biru.helper.SettlementHelper;

public interface TR0012Repo extends PagingAndSortingRepository<TR0012Entity, Long> {
	
	@Query("select new com.biru.helper.SettlementHelper(tr12.idKey, tr12.trxType, tr12.trxVoucherId, "
			+ "DATE_FORMAT(tr12.trxDate, '%d/%m/%Y'), DATE_FORMAT(tr12.trxDueDate, '%d/%m/%Y'), "
			+ "DATEDIFF(STR_TO_DATE(?1, '%m-%d-%Y'), tr12.trxDueDate), "
			+ "tr12.trxCountInv, tr12.trxClient, ma5.cliName, tr12.trxCoverCode, tr12.trxDescription, "
			+ "tr12.trxCurrId, tr12.trxOrgAmount, tr12.trxSetAmount, tr12.trxInvcAmount-tr12.trxSetAmount, tr12.trxComAmount, tr6a.trxRemarks, tr12.trxCurrRate) "
			+ "from TR0012Entity tr12 left join TR0006AEntity tr6a "
			+ "on tr12.trxOldVoucherId = tr6a.trxVoucherId, MA0005Entity ma5 "
				+ "where tr12.trxClient = ma5.cliCode "
				+ "and tr12.trxDataStatus = '11' "
				+ "and tr12.trxInvcAmount > tr12.trxSetAmount "
				+ "and ?1 = ?1 "
				+ "and tr12.trxType in ?2 "
				+ "and LOWER(ma5.cliName) like %?3%")
	public Page<SettlementHelper> findSettlement(String p_AppDate, List<String> p_Types, String p_Client, Pageable p_Pageable);
	
	@Query("select new com.biru.helper.OutstandingHelper(tr12.idKey, DATE_FORMAT(tr12.trxDate, '%d/%m/%Y'), DATE_FORMAT(tr12.trxDueDate, '%d/%m/%Y'), "
			+ "DATEDIFF(STR_TO_DATE(ma14.paChildValue,'%m-%d-%Y'), tr12.trxDueDate), tr12.trxType, tr12.trxVoucherId, "
			+ "tr12.trxCountInv, tr12.trxCoverCode, tr12.trxOldType, tr12.trxOldVoucherId, tr12.trxClient, ma5.cliName, tr12.trxDescription, "
			+ "tr12.trxCurrId, tr12.trxInvcAmount-tr12.trxSetAmount) "
			+ "from TR0012Entity tr12, MA0005Entity ma5, MA0014Entity ma14 "
				+ "where tr12.trxClient = ma5.cliCode "
				+ "and ma14.paParentCode = 'SYSDATE' "
				+ "and ma14.paChildCode = 'SYSDATE001' "
				+ "and tr12.trxDataStatus = 11 "
				+ "and tr12.trxSetAmount < tr12.trxInvcAmount "
				+ "and tr12.trxCoverCode like %?1% "
				+ "and tr12.trxClient like %?2% "
				+ "and tr12.trxType in ?3 "
				+ "and tr12.trxDueDate <= ?4 "
				)
public Page<OutstandingHelper> findOutstanding(String p_TypeOfCover, String p_Client, List<String> p_Type, Date p_Start_Date, Pageable pageable);

	@Query("select new com.biru.entity.TR0012Entity(t.idKey, DATE_FORMAT(t.trxDate, '%d/%m/%Y'), "
			+ "t.trxType, t.trxVoucherId, m.cliName, t.trxDescription, t.trxDataStatus, "
			+ "t.trxCurrId, t.trxInvcAmount, t.trxSetAmount, (t.trxInvcAmount-t.trxSetAmount) as trxInvoiceAmount,"
			+ "t.trxRemarks) "
			+ "from TR0012Entity t, MA0005Entity m " 
			+ "where t.trxClient = m.cliCode "
			+ "and t.trxDataStatus = '11' "
			+ "and t.trxType in ('PU', 'PO', 'SE', 'SO') "
			+ "and t.trxInvcAmount <> t.trxSetAmount "
			+ "and (DATE_FORMAT(t.trxDueDate, '%d/%m/%Y') like %?1% "
			+ "or t.trxVoucherId like %?1% "
			+ "or LOWER(m.cliName) like %?1% "
			+ "or LOWER(t.trxDescription) like %?1% "
			+ "or LOWER(t.trxCurrId) like %?1% "
			+ "or (t.trxInvcAmount-t.trxSetAmount) like %?1%)")
	public Page<TR0012Entity> findAdjustment(String p_Filter, Pageable pageable);
	
	public TR0012Entity findByIdKey(Long p_idKey);
	
	@Query("select new com.biru.entity.TR0012Entity(t.idKey, DATE_FORMAT(t.trxDate, '%d/%m/%Y'), "
			+ "t.trxType, t.trxVoucherId, m.cliName, t.trxDescription, t.trxDataStatus, "
			+ "t.trxCurrId, t.trxInvcAmount, t.trxSetAmount, (t.trxInvcAmount-t.trxSetAmount) as trxInvoiceAmount,"
			+ "t.trxRemarks) "
			+ "from TR0012Entity t, MA0005Entity m "
			+ "where t.trxClient = m.cliCode "
			+ "and (t.trxDate >= ?1 "
			+ "and t.trxDate <= ?2) "
			+ "and t.trxType = ?3 "
			+ "and m.cliCode like %?4% "
			+ "and t.trxDataStatus = '11'")
	public Page<TR0012Entity> findSettlementToBeCancelled(Date p_StartDate, Date p_EndDate, String p_Type, String p_Client, Pageable pageable);
	
	public TR0012Entity findByTrxVoucherIdAndTrxType(String p_VoucherId, String p_Type);
	
	public TR0012Entity findByTrxVoucherId(String p_VoucherId);
	
	public TR0012Entity findByTrxVoucherIdAndTrxTypeAndTrxCountInv(String p_VoucherId, String p_Type, Integer p_TrxCountInv);
	
//	public List<TR0012Entity> findAll(Specification<TR0012Entity> spec);
	
	public List<TR0012Entity> findByTrxOldTypeEqualsAndTrxOldVoucherIdEqualsAndTrxTypeInAndTrxSetAmountNot(String trxOldType, String trxOldVoucherId, List<String> listTrxType, BigDecimal trxSetAmount);
	
	public List<TR0012Entity> findByTrxOldTypeEqualsAndTrxOldVoucherIdEqualsAndTrxTypeIn(String trxOldType, String trxOldVoucherId, List<String>listTrxType);
	
	public List<TR0012Entity> findByTrxOldTypeEqualsAndTrxOldVoucherIdEqualsAndTrxDataStatusEquals(String trxOldType, String trxOldVoucherId, String trxDataStatus);
	
	@Transactional
	@Modifying
	@Query("UPDATE TR0012Entity tr12 "
			+ "SET tr12.trxDataStatus='12' "
			+ "WHERE tr12.trxOldType = ?1 "
			+ "AND tr12.trxOldVoucherId = ?2")
	public void updateDataStatus12(String trxOldType, String trxOldVoucherId);
	
	public List<TR0012Entity> findByTrxTypeInAndTrxOldVoucherIdEquals (Set<String> setTrxType, String voucherId);
	public List<TR0012Entity> findByTrxOldTypeInAndTrxOldVoucherIdIn(Set<String>trxOldType, List<String> trxOldVoucherId);
	
	@Query("select tr12 from TR0012Entity tr12 "
				+ "where tr12.trxNxType = ?1 "
				+ "and tr12.trxNxVoucherId = ?2")
	public List<TR0012Entity> findOldTR0012(String p_OldType, String p_OldVoucherId);
	
	@Transactional
	@Modifying
	@Query("update TR0012Entity tr12 set tr12.trxDataStatus = '12', tr12.modifyBy = ?1, tr12.modifyOn = ?2 "
			+ "where tr12.trxOldType = ?3 "
			+ "and tr12.trxOldVoucherId = ?4 "
			+ "and tr12.trxDescription like 'Adjustment%'")
	public void cancelAdjustment(String user, Date modifyOn, String trxOldType, String trxOldVoucherId);
	
	@Query("select tr12 from TR0012Entity tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxDescription like 'Adjustment%'")
	public List<TR0012Entity> getDataAdjustment(String p_OldType, String p_OldVoucherId);
	
	@Query("select new com.biru.entity.TR0012Entity(tr12.idKey, tr12.trxType, tr12.trxVoucherId, "
			+ "tr12.trxCountInv, tr12.trxClient, m.cliName, tr12.trxInvcAmount, tr12.trxDueDate) "
			+ " from TR0012Entity tr12, MA0005Entity m "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxDataStatus = '11' "
			+ "and tr12.trxClient = m.cliCode")
	public List<TR0012Entity> findDetailDirectModify(String trxTrxId, String trxVoucherId);
	
	@Query("select t2 from TR0012Entity t1, TR0012Entity t2 "
			+ "where t1.trxOldType = ?1 "
			+ "and t1.trxOldVoucherId = ?2 "
			+ "and t1.trxDataStatus in ('11','13') "
			+ "and t2.trxOldType = t1.trxType "
			+ "and t2.trxOldVoucherId = t1.trxVoucherId "
			+ "and t2.trxDataStatus in ('11','13')")
	public List<TR0012Entity> findUnderwriting(String trxTrxId, String trxVoucherId);
	
	@Query("select new com.biru.entity.TR0012Entity"
			+ "(t12.idKey, t12.createOn, t12.trxDate, t12.trxVoucherId, t12.trxClient, "
					+ "m5.cliName, t12.trxDescription, t12.trxCurrId, t12.trxInvcAmount, "
					+ "t12.trxDataStatus, t12.trxOldVoucherId, t12.trxSource) "
				+ "from TR0012Entity t12, MA0005Entity m5 "
				+ "where t12.trxClient = m5.cliCode "
				+ "and DATE_FORMAT(t12.trxDate, '%d/%m/%Y') = ?1 "
				+ "and t12.trxDataStatus in ('11','13') "
				+ "and t12.trxSource in ?2")
	public Page<TR0012Entity> findDCNotesByTrxDate(String p_Date, List<String> trxSource, Pageable p_Pageable);
	
	@Query("select new com.biru.entity.TR0012Entity"
			+ "(t12.idKey, t12.createOn, t12.trxDate, t12.trxVoucherId, t12.trxClient, "
					+ "m5.cliName, t12.trxDescription, t12.trxCurrId, t12.trxInvcAmount, "
					+ "t12.trxDataStatus, t12.trxOldVoucherId, t12.trxSource) "
				+ "from TR0012Entity t12, MA0005Entity m5 "
				+ "where t12.trxClient = m5.cliCode "
				+ "and DATE_FORMAT(t12.createOn, '%d/%m/%Y') = ?1 "
				+ "and t12.trxDataStatus in ('11','13') "
				+ "and t12.trxSource in ?2")
	public Page<TR0012Entity> findDCNotesByEntryDate(String p_Date, List<String> trxSource, Pageable p_Pageable);
	
	@Transactional
	@Modifying
	@Query("update TR0012Entity tr12 "
			+ "set tr12.trxDataStatus = ?2, tr12.modifyOn = ?3 "
			+ "where tr12.trxVoucherId = ?1")
	public void updateTrxDataStatus(String trxVoucherId, String trxDataStatus, Date modifyOn);

	@Query(value = "SELECT COUNT(1) FROM TR0012 WHERE TRX_TYPE='SE' AND TRX_DATA_STATUS=11 AND TRX_OLD_VOUCHER_ID = ?1", nativeQuery = true)
	public Long countSE11(String trxOldVoucherId);
	
	public Page<TR0012Entity> findAll(Specification<TR0012Entity> specification, Pageable pageable);
	
	public List<TR0012Entity> findByTrxOldTypeEqualsAndTrxOldVoucherIdEquals(String trxOldType, String trxOldVoucherId);
	
	public Page<TR0012Entity> findByTrxOldTypeEqualsAndTrxOldVoucherIdEquals(String trxOldType, String trxOldVoucherId, Pageable p_Pageable);

	public TR0012Entity findByTrxVoucherIdAndTrxDataStatus(String p_VoucherId, String dataStatus);
	
	@Query("select tr12 from TR0012Entity tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType = ?3 "
			+ "and tr12.trxDataStatus = ?4 "
			+ "and tr12.trxClient = ?5 "
			+ "and DATE_FORMAT(tr12.createOn, '%d-%m-%Y %H:%i:%s') = DATE_FORMAT(?6, '%d-%m-%Y %H:%i:%s')")
	public List<TR0012Entity> findTr12ReprintModifyFacultative(String trxOldType, String trxOldVoucherId, 
			String trxType, String trxDataStatus, String trxClient, Date createOn);
	
	@Query("select tr12 from TR0012Entity tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType in ?3 "
			+ "and tr12.trxSetAmount <> ?4 "
			+ "and DATE_FORMAT(tr12.createOn, '%d-%m-%Y %H:%i:%s') = DATE_FORMAT(?5, '%d-%m-%Y %H:%i:%s') "
			+ "and tr12.trxSource = '2' "
			+ "and tr12.trxDataStatus <> '12' "
			+ "order by tr12.trxVoucherId asc")
	public List<TR0012Entity> findSettleEndors(String trxOldType, String trxOldVoucherId, 
			List<String> trxTypes, BigDecimal trxSetAmount, Date createOn);
	
	@Query("select tr12 from TR0012Entity tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType in ?3 "
			+ "and tr12.trxSetAmount = ?4 "
			+ "and DATE_FORMAT(tr12.createOn, '%d-%m-%Y %H:%i:%s') = DATE_FORMAT(?5, '%d-%m-%Y %H:%i:%s') "
			+ "and tr12.trxSource = '2' "
			+ "and tr12.trxDataStatus <> '12' "
			+ "order by tr12.trxVoucherId asc")
	public List<TR0012Entity> findCancelEndors(String trxOldType, String trxOldVoucherId, 
			List<String> trxTypes, BigDecimal trxSetAmount, Date createOn);
	
	@Query("select tr12 from TR0012Entity tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType in ?3 "
			+ "and tr12.trxSetAmount <> ?4 "
			+ "and tr12.trxDataStatus <> '12' "
			+ "order by tr12.trxVoucherId asc")
	public List<TR0012Entity> findSettleProd(String trxOldType, String trxOldVoucherId, 
			List<String> trxTypes, BigDecimal trxSetAmount);
	
	@Query("select tr12 from TR0012Entity tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType in ?3 "
			+ "and tr12.trxSetAmount = ?4 "
			+ "and tr12.trxDataStatus <> '12' "
			+ "order by tr12.trxVoucherId asc")
	public List<TR0012Entity> findCancelProd(String trxOldType, String trxOldVoucherId, 
			List<String> trxTypes, BigDecimal trxSetAmount);
	
}
