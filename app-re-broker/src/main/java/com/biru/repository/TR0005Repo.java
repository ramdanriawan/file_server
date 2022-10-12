package com.biru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.entity.TR0005Entity;

public interface TR0005Repo extends PagingAndSortingRepository<TR0005Entity, String> {
	
	@Query("select t from TR0005Entity t "
			+ "where t.proId = 0 "
			+ "and t.proStatus = '6' "
			+ "order by proYear desc, proMonth desc" )
	public List<TR0005Entity> findLastProMonthAndProYear();
	
	public List<TR0005Entity> findByProIdAndProYearAndProStatus(String proId, int proYear, String proStatus);
	
	public TR0005Entity findByProMonthAndProYearAndProStatusAndProId(Short month, Integer year, String proStatus, String proId);

	public TR0005Entity findByProIdAndProYear(String proId, int proYear);
	
	public TR0005Entity findByProIdAndProMonthAndProYear(String p_ProId, Short p_Month, Integer p_Year);
	
}
