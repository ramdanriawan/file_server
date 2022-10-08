package com.biru.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.biru.common.entity.DropdownIdText;
import com.biru.entity.US0003Entity;

public interface US0003Repo extends PagingAndSortingRepository<US0003Entity, Long>{

	@Query("select new com.biru.helper.UserHelper(t.idKey, t.groupId, t.groupName, t.uiId, e.uiName, t.createBy, t.createOn, " + 
			"t.modifyBy, t.modifyOn) "
			+ "from US0003Entity t left join US0004Entity e "
			+ "on t.uiId = e.uiId "
			+ "where t.groupId = ?1")
	public Page<US0003Entity> findByGroupId(String groupId, Pageable pageable);
	
	@Query("select m from US0003Entity m group by m.groupId")
	public Page<US0003Entity> findAlls(Pageable pageable);
	US0003Entity findByUiIdAndGroupId(String uiId, String groupId);
	
	@Query("select new com.biru.common.entity.DropdownIdText(m.groupId, m.groupName) from US0003Entity m "
			+ "group by m.groupName")	
	public List<DropdownIdText> getDropDown();
	
	@Query("select us3 from US0003Entity us3 where us3.groupId = ?1 and us3.uiId in "
			+ "(select us4.uiId from US0004Entity us4 where us4.uiType = '0')")
	public List<US0003Entity>findParentMenu(String groupId);
	
	@Query("select us3 from US0003Entity us3 where us3.groupId = ?1 and us3.uiId in "
			+ "(select us4.uiId from US0004Entity us4 where us4.uiParent = ?2)")
	public List<US0003Entity>findSubMenu(String groupId, String uiParent);
	
}
