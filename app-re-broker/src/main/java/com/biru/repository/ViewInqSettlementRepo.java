package com.biru.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.ViewInqSettlement;
import com.biru.helper.SettlementHelper;

public interface ViewInqSettlementRepo extends PagingAndSortingRepository<ViewInqSettlement, Long> {
	
	@Query("select new com.biru.helper.SettlementHelper(v.idKey, v.trxType, v.trxVoucherId, "
			+ "DATE_FORMAT(v.trxDate, '%d/%m/%Y'), DATE_FORMAT(v.trxDueDate, '%d/%m/%Y'), "
			+ "DATEDIFF(STR_TO_DATE(?1, '%m-%d-%Y'), v.trxDueDate), "
			+ "v.trxCountInv, v.trxClient, v.cliName, v.trxCoverCode, v.trxDescription, "
			+ "v.trxCurrId, v.trxOrgAmount, v.trxSetAmount, v.trxInvcAmount-v.trxSetAmount, "
			+ "v.trxComAmount, v.trxRemarks, v.trxCurrRate, v.dcNotesVoucherId) "
			+ "from ViewInqSettlement v "
				+ "where ?1 = ?1 "
				+ "and v.trxType in ?2 "
				+ "and v.trxClient like %?3%")
	public Page<SettlementHelper> findSettlement(String p_AppDate, List<String> p_Types, String p_Client, Pageable p_Pageable);
		
}
