package com.biru.service.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.biru.entity.US0003Entity;
import com.biru.entity.US0004Entity;
import com.biru.repository.US0003Repo;
import com.biru.repository.US0004Repo;
import com.biru.service.MenuService;

@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private US0003Repo uS0003Repo;
	
	@Autowired
	private US0004Repo uS0004Repo;
	
	private Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Override
	public Object getMenu(Map<String, Object> param) {
		String groupId = param.get("groupId").toString();
		logger.info("Get menu for group id : {}.", groupId);
		
		String html = "";
		List<US0003Entity> listMainMenu =  uS0003Repo.findParentMenu(groupId);
		for (US0003Entity us0003Entity : listMainMenu) {
			US0004Entity mainMenu = uS0004Repo.findByUiId(us0003Entity.getUiId());
			html = html.concat(
			"<li class=\"nav-item dropdown\">"
				+ "<a class=\"nav-link dropdown-toggle\" href=\"#\" role=\"button\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\"> "
					+ "<i class=\""+mainMenu.getUiIcon()+"\"></i> "
					+ "<span>"+mainMenu.getUiName()+"</span>"
				+ "</a>"
			);
			
			List<US0003Entity> listSubMenuEntities = uS0003Repo.findSubMenu(groupId, mainMenu.getUiId());
			if(!listSubMenuEntities.isEmpty()) {
				html = html.concat(
					"<div class=\"dropdown-menu\" aria-labelledby=\"pagesDropdown\">"
				);
				for (US0003Entity us0003Entity2 : listSubMenuEntities) {
					US0004Entity subMenu = uS0004Repo.findByUiId(us0003Entity2.getUiId());
					html = html.concat(
							"<a class=\"dropdown-item\" href=\""+subMenu.getUiUrl()+"\">"+subMenu.getUiName()+"</a>"
					);
				}
				html = html.concat(
					"</div>"
				);
			}
			html = html.concat(
				"</li>"
			);
		}
		
		return html;
	}
	
	

}
