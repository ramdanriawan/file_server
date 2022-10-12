package com.biru.repository;

import com.biru.entity.TR0012EntityRamdan;
import com.biru.helper.SettlementHelper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface TR0012RepoRamdan extends PagingAndSortingRepository<TR0012EntityRamdan, Long> {

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

	@Query(value = "select *, e.set_amount set_amount, DATEDIFF(a.TRX_DATE, a.TRX_DUE_DATE) overdue, sum(a.TRX_INVC_AMOUNT) invoice, sum(a.TRX_SET_AMOUNT) settlement, sum(a.TRX_INVC_AMOUNT - a.TRX_SET_AMOUNT) outstanding" +
			" from TR0012 a" +
			" JOIN (select sum(TRX_SET_AMOUNT) set_amount FROM TR0012 WHERE TRX_TYPE = 'SE' AND TRX_DATA_STATUS <> 12 AND TRX_OLD_VOUCHER_ID = TR0012.TRX_OLD_VOUCHER_ID) e" +
			" JOIN tr0006a b ON a.TRX_OLD_VOUCHER_ID = b.TRX_VOUCHER_ID " +
			" JOIN ma0005 c ON a.TRX_CLIENT = C.CLI_CODE" +
			" JOIN ma0012 d ON a.TRX_INS_OFFICER = d.SA_CODE" +
			" where a.TRX_DATA_STATUS <> 12 and a.TRX_SET_AMOUNT < a.TRX_INVC_AMOUNT AND" +
			" a.TRX_DUE_DATE <= :transactionDate and a.TRX_TYPE LIKE %:typeOfTransaction% and a.TRX_CLIENT LIKE %:client%" +
			" AND a.TRX_COVER_CODE LIKE %:typeOfCover% and a.TRX_BRANCH LIKE %:branch%" +
			" AND a.TRX_INS_OFFICER LIKE %:officer% " +
			" group by a.TRX_OLD_VOUCHER_ID", nativeQuery = true)
	List<TR0012EntityRamdan> findOutstandingDirect(
			@Param("typeOfCover") String typeOfCover,
			@Param("client") String client,
			@Param("typeOfTransaction") String typeOfTransaction,
			@Param("transactionDate") String transactionDate,
//			@Param("to") String to, // belum d gunakan SDF SDF
			@Param("officer") String officer,
			@Param("branch") String branch,

			Pageable pageable
	);


	@Query(value = "select *, e.set_amount, DATEDIFF(a.TRX_DATE, a.TRX_DUE_DATE) overdue, sum(a.TRX_INVC_AMOUNT) invoice, sum(a.TRX_SET_AMOUNT) settlement, sum(a.TRX_INVC_AMOUNT - a.TRX_SET_AMOUNT) outstanding" +
			" from TR0012 a" +
			" JOIN (select sum(TRX_SET_AMOUNT) set_amount FROM TR0012 WHERE TRX_TYPE = 'SE' AND TRX_DATA_STATUS <> 12 AND TRX_OLD_VOUCHER_ID = TR0012.TRX_OLD_VOUCHER_ID) e" +
			" JOIN tr0006a b ON a.TRX_OLD_VOUCHER_ID = b.TRX_VOUCHER_ID " +
			" JOIN ma0005 c ON a.TRX_CLIENT = C.CLI_CODE" +
			" JOIN ma0012 d ON a.TRX_INS_OFFICER = d.SA_CODE" +
			" where a.TRX_DATA_STATUS <> 12 and a.TRX_SET_AMOUNT < a.TRX_INVC_AMOUNT AND" +
			" a.TRX_DUE_DATE <= :transactionDate and a.TRX_TYPE LIKE %:typeOfTransaction% and a.TRX_CLIENT LIKE %:client%" +
			" AND a.TRX_COVER_CODE LIKE %:typeOfCover% and a.TRX_BRANCH LIKE %:branch%" +
			" AND a.TRX_INS_OFFICER LIKE %:officer% " +
			" group by a.TRX_OLD_VOUCHER_ID", nativeQuery = true)
	List<TR0012EntityRamdan> findOutstandingDirectFull(
			@Param("typeOfCover") String typeOfCover,
			@Param("client") String client,
			@Param("typeOfTransaction") String typeOfTransaction,
			@Param("transactionDate") String transactionDate,
//			@Param("to") String to, // belum d gunakan SDF SDF
			@Param("officer") String officer,
			@Param("branch") String branch
	);


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
	public Page<TR0012EntityRamdan> findAdjustment(String p_Filter, Pageable pageable);

	public TR0012EntityRamdan findByIdKey(Long p_idKey);

	@Query("select new com.biru.entity.TR0012EntityRamdan(t.idKey, DATE_FORMAT(t.trxDate, '%d/%m/%Y'), "
			+ "t.trxType, t.trxVoucherId, m.cliName, t.trxDescription, t.trxDataStatus, "
			+ "t.trxCurrId, t.trxInvcAmount, t.trxSetAmount, (t.trxInvcAmount-t.trxSetAmount) as trxInvoiceAmount,"
			+ "t.trxRemarks) "
			+ "from TR0012EntityRamdan t, MA0005Entity m "
			+ "where t.trxClient = m.cliCode "
			+ "and (t.trxDate >= ?1 "
			+ "and t.trxDate <= ?2) "
			+ "and t.trxType = ?3 "
			+ "and m.cliCode like %?4% "
			+ "and t.trxDataStatus = '11'")
	public Page<TR0012EntityRamdan> findSettlementToBeCancelled(Date p_StartDate, Date p_EndDate, String p_Type, String p_Client, Pageable pageable);

	public TR0012EntityRamdan findByTrxVoucherIdAndTrxType(String p_VoucherId, String p_Type);

	public TR0012EntityRamdan findByTrxVoucherId(String p_VoucherId);

	public TR0012EntityRamdan findByTrxVoucherIdAndTrxTypeAndTrxCountInv(String p_VoucherId, String p_Type, Integer p_TrxCountInv);

//	public List<TR0012EntityRamdan> findAll(Specification<TR0012EntityRamdan> spec);

	public List<TR0012EntityRamdan> findByTrxOldTypeEqualsAndTrxOldVoucherIdEqualsAndTrxTypeInAndTrxSetAmountNot(String trxOldType, String trxOldVoucherId, List<String> listTrxType, BigDecimal trxSetAmount);

	public List<TR0012EntityRamdan> findByTrxOldTypeEqualsAndTrxOldVoucherIdEqualsAndTrxTypeIn(String trxOldType, String trxOldVoucherId, List<String>listTrxType);

	public List<TR0012EntityRamdan> findByTrxOldTypeEqualsAndTrxOldVoucherIdEqualsAndTrxDataStatusEquals(String trxOldType, String trxOldVoucherId, String trxDataStatus);

	@Transactional
	@Modifying
	@Query("UPDATE TR0012EntityRamdan tr12 "
			+ "SET tr12.trxDataStatus='12' "
			+ "WHERE tr12.trxOldType = ?1 "
			+ "AND tr12.trxOldVoucherId = ?2")
	public void updateDataStatus12(String trxOldType, String trxOldVoucherId);

	public List<TR0012EntityRamdan> findByTrxTypeInAndTrxOldVoucherIdEquals (Set<String> setTrxType, String voucherId);
	public List<TR0012EntityRamdan> findByTrxOldTypeInAndTrxOldVoucherIdIn(Set<String>trxOldType, List<String> trxOldVoucherId);

	@Query("select tr12 from TR0012EntityRamdan tr12 "
				+ "where tr12.trxNxType = ?1 "
				+ "and tr12.trxNxVoucherId = ?2")
	public List<TR0012EntityRamdan> findOldTR0012(String p_OldType, String p_OldVoucherId);

	@Transactional
	@Modifying
	@Query("update TR0012EntityRamdan tr12 set tr12.trxDataStatus = '12', tr12.modifyBy = ?1, tr12.modifyOn = ?2 "
			+ "where tr12.trxOldType = ?3 "
			+ "and tr12.trxOldVoucherId = ?4 "
			+ "and tr12.trxDescription like 'Adjustment%'")
	public void cancelAdjustment(String user, Date modifyOn, String trxOldType, String trxOldVoucherId);

	@Query("select tr12 from TR0012EntityRamdan tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxDescription like 'Adjustment%'")
	public List<TR0012EntityRamdan> getDataAdjustment(String p_OldType, String p_OldVoucherId);

	@Query("select new com.biru.entity.TR0012EntityRamdan(tr12.idKey, tr12.trxType, tr12.trxVoucherId, "
			+ "tr12.trxCountInv, tr12.trxClient, m.cliName, tr12.trxInvcAmount, tr12.trxDueDate) "
			+ " from TR0012EntityRamdan tr12, MA0005Entity m "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxDataStatus = '11' "
			+ "and tr12.trxClient = m.cliCode")
	public List<TR0012EntityRamdan> findDetailDirectModify(String trxTrxId, String trxVoucherId);

	@Query("select t2 from TR0012EntityRamdan t1, TR0012EntityRamdan t2 "
			+ "where t1.trxOldType = ?1 "
			+ "and t1.trxOldVoucherId = ?2 "
			+ "and t1.trxDataStatus in ('11','13') "
			+ "and t2.trxOldType = t1.trxType "
			+ "and t2.trxOldVoucherId = t1.trxVoucherId "
			+ "and t2.trxDataStatus in ('11','13')")
	public List<TR0012EntityRamdan> findUnderwriting(String trxTrxId, String trxVoucherId);

	@Query("select new com.biru.entity.TR0012EntityRamdan"
			+ "(t12.idKey, t12.createOn, t12.trxDate, t12.trxVoucherId, t12.trxClient, "
					+ "m5.cliName, t12.trxDescription, t12.trxCurrId, t12.trxInvcAmount, "
					+ "t12.trxDataStatus, t12.trxOldVoucherId, t12.trxSource) "
				+ "from TR0012EntityRamdan t12, MA0005Entity m5 "
				+ "where t12.trxClient = m5.cliCode "
				+ "and DATE_FORMAT(t12.trxDate, '%d/%m/%Y') = ?1 "
				+ "and t12.trxDataStatus in ('11','13') "
				+ "and t12.trxSource in ?2")
	public Page<TR0012EntityRamdan> findDCNotesByTrxDate(String p_Date, List<String> trxSource, Pageable p_Pageable);

	@Query("select new com.biru.entity.TR0012EntityRamdan"
			+ "(t12.idKey, t12.createOn, t12.trxDate, t12.trxVoucherId, t12.trxClient, "
					+ "m5.cliName, t12.trxDescription, t12.trxCurrId, t12.trxInvcAmount, "
					+ "t12.trxDataStatus, t12.trxOldVoucherId, t12.trxSource) "
				+ "from TR0012EntityRamdan t12, MA0005Entity m5 "
				+ "where t12.trxClient = m5.cliCode "
				+ "and DATE_FORMAT(t12.createOn, '%d/%m/%Y') = ?1 "
				+ "and t12.trxDataStatus in ('11','13') "
				+ "and t12.trxSource in ?2")
	public Page<TR0012EntityRamdan> findDCNotesByEntryDate(String p_Date, List<String> trxSource, Pageable p_Pageable);

	@Transactional
	@Modifying
	@Query("update TR0012EntityRamdan tr12 "
			+ "set tr12.trxDataStatus = ?2, tr12.modifyOn = ?3 "
			+ "where tr12.trxVoucherId = ?1")
	public void updateTrxDataStatus(String trxVoucherId, String trxDataStatus, Date modifyOn);

	@Query(value = "SELECT COUNT(1) FROM TR0012 WHERE TRX_TYPE='SE' AND TRX_DATA_STATUS=11 AND TRX_OLD_VOUCHER_ID = ?1", nativeQuery = true)
	public Long countSE11(String trxOldVoucherId);

	public Page<TR0012EntityRamdan> findAll(Specification<TR0012EntityRamdan> specification, Pageable pageable);

	public List<TR0012EntityRamdan> findByTrxOldTypeEqualsAndTrxOldVoucherIdEquals(String trxOldType, String trxOldVoucherId);

	public Page<TR0012EntityRamdan> findByTrxOldTypeEqualsAndTrxOldVoucherIdEquals(String trxOldType, String trxOldVoucherId, Pageable p_Pageable);

	public TR0012EntityRamdan findByTrxVoucherIdAndTrxDataStatus(String p_VoucherId, String dataStatus);

	@Query("select tr12 from TR0012EntityRamdan tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType = ?3 "
			+ "and tr12.trxDataStatus = ?4 "
			+ "and tr12.trxClient = ?5 "
			+ "and DATE_FORMAT(tr12.createOn, '%d-%m-%Y %H:%i:%s') = DATE_FORMAT(?6, '%d-%m-%Y %H:%i:%s')")
	public List<TR0012EntityRamdan> findTr12ReprintModifyFacultative(String trxOldType, String trxOldVoucherId,
			String trxType, String trxDataStatus, String trxClient, Date createOn);

	@Query("select tr12 from TR0012EntityRamdan tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType in ?3 "
			+ "and tr12.trxSetAmount <> ?4 "
			+ "and DATE_FORMAT(tr12.createOn, '%d-%m-%Y %H:%i:%s') = DATE_FORMAT(?5, '%d-%m-%Y %H:%i:%s') "
			+ "and tr12.trxSource = '2' "
			+ "and tr12.trxDataStatus <> '12' "
			+ "order by tr12.trxVoucherId asc")
	public List<TR0012EntityRamdan> findSettleEndors(String trxOldType, String trxOldVoucherId,
			List<String> trxTypes, BigDecimal trxSetAmount, Date createOn);

	@Query("select tr12 from TR0012EntityRamdan tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType in ?3 "
			+ "and tr12.trxSetAmount = ?4 "
			+ "and DATE_FORMAT(tr12.createOn, '%d-%m-%Y %H:%i:%s') = DATE_FORMAT(?5, '%d-%m-%Y %H:%i:%s') "
			+ "and tr12.trxSource = '2' "
			+ "and tr12.trxDataStatus <> '12' "
			+ "order by tr12.trxVoucherId asc")
	public List<TR0012EntityRamdan> findCancelEndors(String trxOldType, String trxOldVoucherId,
			List<String> trxTypes, BigDecimal trxSetAmount, Date createOn);

	@Query("select tr12 from TR0012EntityRamdan tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType in ?3 "
			+ "and tr12.trxSetAmount <> ?4 "
			+ "and tr12.trxDataStatus <> '12' "
			+ "order by tr12.trxVoucherId asc")
	public List<TR0012EntityRamdan> findSettleProd(String trxOldType, String trxOldVoucherId,
			List<String> trxTypes, BigDecimal trxSetAmount);

	@Query("select tr12 from TR0012EntityRamdan tr12 "
			+ "where tr12.trxOldType = ?1 "
			+ "and tr12.trxOldVoucherId = ?2 "
			+ "and tr12.trxType in ?3 "
			+ "and tr12.trxSetAmount = ?4 "
			+ "and tr12.trxDataStatus <> '12' "
			+ "order by tr12.trxVoucherId asc")
	public List<TR0012EntityRamdan> findCancelProd(String trxOldType, String trxOldVoucherId,
			List<String> trxTypes, BigDecimal trxSetAmount);

}
