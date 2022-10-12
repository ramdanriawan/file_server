package com.biru.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.MA0014Entity;

public interface MA0014Repo extends PagingAndSortingRepository<MA0014Entity, Long> {
	
	@Query("select m from MA0014Entity m "
			+ "where m.paParentCode = ?1 "
			+ "and m.paChildCode = ?2")	
	public MA0014Entity findByParentCodeAndChildCode(String p_ParentCode, String p_ChildCode);
	
	@Query("select m from MA0014Entity m "
			+ "where m.paParentCode = ?1 "
			+ "and m.paChildDesc = ?2")	
	public MA0014Entity findByParentCodeAndChildDesc(String p_ParentCode, String p_ChildDesc);
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'STDATA' "
			+ "and m.paChildCode in ('STDATA012','STDATA013') "
			+ "order by m.paChildValue")	
	public List<DropdownIdText> getStatusParamSD();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'STDATA' "
			+ "and m.paChildCode in ('STDATA017','STDATA018','STDATA019') "
			+ "order by m.paChildValue")	
	public List<DropdownIdText> getStatusClaims();
	
	@Query("select m from MA0014Entity m "
			+ "where m.paParentCode = 'SYSDATE' "
			+ "and m.paChildCode in ('SYSDATE004','SYSDATE005') "
			+ "and m.paChildStatus = '11'")	
	public List<MA0014Entity> getHolidayParam();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'COACLASS' "
			+ "and m.paChildStatus = '11'")
	public List<DropdownIdText> getCoaClass();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildValue) from MA0014Entity m "
			+ "where m.paParentCode = 'CURRCODE' "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildCode")	
	public List<DropdownIdText> getCurrency();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = ?1 "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildDesc")	
	public List<DropdownIdText> getParam(String paParentCode);
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = ?1 "
			+ "and m.paChildValue in ?2 "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildDesc")	
	public List<DropdownIdText> getParam(String paParentCode, List<String> paChildValues);
	
	@Modifying
	@Transactional
	@Query("delete MA0014Entity m where m.paParentCode = ?1")
	public void deleteByPaParentCode(String paParentCode);
	
	public MA0014Entity findByPaParentCodeAndPaChildStatusAndPaChildValue(String p_PaParentCode, String p_PaChildStatus, String p_PaChildValue);

	public MA0014Entity findByPaChildCodeAndPaChildStatus(String p_PaChildCode, String p_PaChildStatus);
	
	public Page<MA0014Entity> findByPaParentCodeAndPaChildStatus (String p_PaParentCode, String p_PaChildStatus, Pageable p);
	
	public List<MA0014Entity> findByPaParentCodeAndPaChildStatus (String p_PaParentCode, String p_PaChildStatus);
	
	@Query("select m.paChildValue from MA0014Entity m "
			+ "where m.paChildCode = 'COAEOY006'")
	public String findCoaCodeEoy();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'OFFCODE' "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildValue")	
	public List<DropdownIdText> getOffice();
	
	@Query("select m.paChildValue from MA0014Entity m "
			+ "where m.paParentCode = 'OFFCODE' "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildValue")	
	public List<String> getOfficeCode();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'DCNOTES' "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildCode")	
	public List<DropdownIdText> getDCNotes();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'PAYMTHD' "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildCode")	
	public List<DropdownIdText> getPaymentMethod();
	
	public List<MA0014Entity> findByPaParentCodeAndPaChildDescIn(String paParentCode, List<String> paChildDesc);
	public List<MA0014Entity> findByPaChildCodeIn(List<String> paChildCode);
	public MA0014Entity findByPaChildCode(String paChildCode);
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'TCCLASS' "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildCode")	
	public List<DropdownIdText> getClasification();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'LISTVALUE' "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildCode")	
	public List<DropdownIdText> getValue();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'TCGROUP' "
			+ "and m.paChildStatus = '11' "
			+ "order by m.paChildCode")	
	public List<DropdownIdText> findTcGroup();
	
	public Page<MA0014Entity> findByPaParentCode(String paParentCode, Pageable pageable);
	
	@Query("select date_format(str_to_date(m.paChildValue, '%m-%d-%Y'), '%D %M %Y') from MA0014Entity m "
			+ "where m.paParentCode = 'SYSDATE' and m.paChildCode = 'SYSDATE001'")
	public String getAppDateReportProduction1();
	
	@Query("select date_format(str_to_date(m.paChildValue, '%m-%d-%Y'), '%d/%m/%Y') from MA0014Entity m "
			+ "where m.paParentCode = 'SYSDATE' and m.paChildCode = 'SYSDATE001'")
	public String getAppDateReportProduction2();
	
	@Query("select date_format(str_to_date(m.paChildValue, '%m-%d-%Y'), '%d %M %Y') from MA0014Entity m "
			+ "where m.paParentCode = 'SYSDATE' and m.paChildCode = 'SYSDATE001'")
	public String getAppDateReportProductionDirect();
	
	@Query("select date_format(str_to_date(m.paChildValue, '%m-%d-%Y'), '%d%m%Y') from MA0014Entity m "
			+ "where m.paParentCode = 'SYSDATE' and m.paChildCode = 'SYSDATE001'")
	public String getAppDateFmtNumber();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'UPLFOR' "
			+ "order by m.paChildDesc")	
	public List<DropdownIdText> getFormat();
  
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'TYPECO' "
			+ "order by m.paChildCode")	
	public List<DropdownIdText> getClientType();
	
	public List<MA0014Entity> findByPaParentCode(String paParentCode);
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'USERLEVEL' "
			+ "order by m.paChildCode")
	public List<DropdownIdText> findUserLevelDropDown();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'EMPLUNIT' "
			+ "order by m.paChildCode")
	public List<DropdownIdText> mplUnitDropDown();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'STDATA' "
			+ "and m.paChildValue in ('2','11','12') "
			+ "order by m.paChildCode")
	public List<DropdownIdText> stDataDropDown();
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.paChildValue, m.paChildDesc) from MA0014Entity m "
			+ "where m.paParentCode = 'FILECLI' "
			+ "and m.paChildStatus in ('11') "
			+ "order by m.paChildDesc")
	public List<DropdownIdText> getDropDownPaChild();
	
	@Query("select m from MA0014Entity m "
			+ "where m.paChildDesc = ?1")	
	public MA0014Entity findChildCodeByChildDesc(String p_ChildDesc);
}
