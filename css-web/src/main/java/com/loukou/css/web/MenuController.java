package com.loukou.css.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.loukou.auth.core.annotation.AuthCheck;
import com.loukou.css.entity.MenuEntity;
import com.loukou.css.processor.MenuProcessor;
import com.loukou.css.resp.MenuRespDto;

@Controller
@RequestMapping("/menu")
@AuthCheck(privileges = {"css.login"}, isRedirect = true)
public class MenuController {
	@Autowired
	private MenuProcessor menuProcessor;
	
	@RequestMapping(value = "/getMenus",method = RequestMethod.GET)
	@ResponseBody
	public List<MenuEntity> GetMenus(int parentId) {
		//根据父菜单获取菜单列表
		return menuProcessor.getMenuListByParentId(parentId);
	}
	
	@RequestMapping(value = "/getAllMenus")
	@ResponseBody
	public List<MenuRespDto> getAllMenus(){
		List<MenuEntity> menuList = menuProcessor.getAllMenus();
		Map<Integer, MenuRespDto> menuMap = new HashMap<Integer, MenuRespDto>();
		menuMap.put(0, new MenuRespDto());
		for(MenuEntity menu: menuList){
			MenuRespDto respDto = new MenuRespDto();
			BeanUtils.copyProperties(menu, respDto);
			menuMap.put(respDto.getMenuId(), respDto);
		}
		for(MenuEntity menu: menuList){
			MenuRespDto respDto = menuMap.get(menu.getMenuId());
			MenuRespDto parent = menuMap.get(menu.getParentId());
			if(parent != null){
				parent.getChildMenus().add(respDto);
			}
		}
		return menuMap.get(0).getChildMenus();
	}
}
