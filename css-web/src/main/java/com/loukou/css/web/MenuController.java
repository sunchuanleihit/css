package com.loukou.css.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loukou.css.entity.MenuEntity;
import com.loukou.css.processor.MenuProcessor;

@Controller
@RequestMapping("/menu")
public class MenuController {
	@Autowired
	private MenuProcessor menuProcessor;
	
	@RequestMapping(value = "/getMenus",method = RequestMethod.GET)
	@ResponseBody
	public List<MenuEntity> GetMenus(int parentId) {
		//根据父菜单获取菜单列表
		return menuProcessor.getMenuListByParentId(parentId);
	}
}
