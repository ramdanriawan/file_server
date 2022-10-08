package com.biru.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.view.ViewInqProduction;

public interface ViewInqProductionRepo extends PagingAndSortingRepository<ViewInqProduction, String>,
	JpaSpecificationExecutor<ViewInqProduction> {
	
	@Query("select new com.biru.view.ViewInqProduction(v.idKey, v.requestId, v.client, v.typeOfCover, "
					+ "v.curr, v.amount, v.trxDataStatus, v.status, v.createBy, v.createOn, v.trxNonPro, DATEDIFF(?1, STR_TO_DATE(SUBSTRING(v.requestId, 1, 8), '%d%m%Y'))) "
				+ "from ViewInqProduction v "
				+ "where v.trxClass = ?2 and v.trxDataStatus <> '11' "
				+ "and (LOWER(v.requestId) like %?3% "
						+ "or DATEDIFF(?1, v.createOn) like %?3% "
						+ "or LOWER(v.client) like %?3% "
						+ "or LOWER(v.typeOfCover) like %?3% "
						+ "or LOWER(v.curr) like %?3% "
						+ "or v.amount like %?3% "
						+ "or LOWER(v.status) like %?3%)")
	public Page<ViewInqProduction> findProduction(Date p_AppDate, String p_TrxClass, String p_Filter, Pageable p_Pageable);
	
}
