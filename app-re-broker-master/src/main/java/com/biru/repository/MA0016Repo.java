package com.biru.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.MA0016Entity;

public interface MA0016Repo extends PagingAndSortingRepository<MA0016Entity, Long> {
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.msgNo, m.msgEnglish) from MA0016Entity m "
			+ "where m.msgNo in ('0001', '0002', '0005', '0006', '0008', '0036')")
	public List<DropdownIdText> getMessageSave();
	
}
