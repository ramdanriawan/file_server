package com.biru.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.view.ViewInqTr0006Entity;
import org.springframework.data.repository.query.Param;

public interface ViewInqTr0006Repo extends PagingAndSortingRepository<ViewInqTr0006Entity, String> {
	
	@Query("from ViewInqTr0006Entity v "
			+ "where v.trxDate >= ?1 "
			+ "and v.trxDate <= ?2 "
			+ "and v.cliName like ?3 "
			+ "and v.trxInsuredName like ?4")
	public Page<ViewInqTr0006Entity> findByTrxDateAndCient(Date trxDate, Date toTrxDate, String client, String trxInsuredName, Pageable pageable);

	@Query("from ViewInqTr0006Entity v "
			+ "where v.trxDate >= ?1 "
			+ "and v.trxDate <= ?2 "
			+ "and v.cliName like ?3 "
			+ "and v.trxCoverCode = ?4 "
			+ "and v.trxInsuredName like ?5 "
			+ "and v.trxSource != 'Endorsement'")
	public Page<ViewInqTr0006Entity> findByTrxDateAndCientAndCoverCodeEndorsement(Date trxDate, Date toTrxDate, String client, String typeOfCover, String trxInsuredName, Pageable pageable);
	
	@Query("from ViewInqTr0006Entity v "
			+ "where v.trxDate >= ?1 "
			+ "and v.trxDate <= ?2 "
			+ "and v.cliName like ?3 "
			+ "and v.trxInsuredName like ?4 "
			+ "and v.trxSource != 'Endorsement'")
	public Page<ViewInqTr0006Entity> findByTrxDateAndCientEndorsement(Date trxDate, Date toTrxDate, String client, String trxInsuredName, Pageable pageable);

	@Query("from ViewInqTr0006Entity v "
			+ "where v.trxDate >= ?1 "
			+ "and v.trxDate <= ?2 "
			+ "and v.cliName like ?3 "
			+ "and v.trxCoverCode = ?4 "
			+ "and v.trxInsuredName like ?5")
	public Page<ViewInqTr0006Entity> findByTrxDateAndCientAndCoverCode(Date trxDate, Date toTrxDate, String client, String typeOfCover, String trxInsuredName, Pageable pageable);
	
	@Query("from ViewInqTr0006Entity v "
			+ "where v.cliName like ?1 "
			+ "and v.trxCoverCode = ?2")
	public Page<ViewInqTr0006Entity> findByTrxDateAndCientAndCoverCode2(String client, String typeOfCover, Pageable pageable);
	
	@Query("from ViewInqTr0006Entity v "
			+ "where v.cliName like ?1")
	public Page<ViewInqTr0006Entity> findByTrxDateAndCient2(String client, Pageable pageable);
	
	public List<ViewInqTr0006Entity> findByIdKey(String idKey);

    @Query(value = "select * from view_inq_tr0006 where TRX_VOUCHER_ID like %:trxVoucherId%")
    public List<ViewInqTr0006Entity> findByTrxVoucherId(@Param("trxVoucherId") String trxVoucherId);

    @Query(value = "select * from view_inq_tr0006",  nativeQuery = true)
    List<ViewInqTr0006Entity> findAllByTrxVoucherId();
}
