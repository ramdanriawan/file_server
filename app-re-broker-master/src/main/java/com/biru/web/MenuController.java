package com.biru.web;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.biru.service.MenuService;

@Controller
public class MenuController {
	@Autowired
	private MenuService menuService;
	
	@RequestMapping(value = "/menu",  method = RequestMethod.POST)
    public @ResponseBody Object getMenu(@RequestBody Map<String, Object> param) {
		return menuService.getMenu(param);
		
    }
}
