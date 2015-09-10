package com.loukou.css.processor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loukou.css.bo.CssBaseRes;
import com.loukou.css.bo.PageListEntity;
import com.loukou.css.bo.RoleActionRes;
import com.loukou.css.entity.RoleEntity;
import com.loukou.css.entity.UserRoleEntity;
import com.loukou.css.service.RoleService;
import com.loukou.css.service.UserRoleService;

@Service
public class RoleProcessor {
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserRoleService userRoleService;
	
	public List<RoleEntity> getRoleList(){
		return roleService.getAll();//获取启用的角色列表
	}
	
	//新增角色
	public CssBaseRes<RoleEntity> addRole(String roleName){
		CssBaseRes<RoleEntity> res = new CssBaseRes<RoleEntity>();
		if(roleService.exitsRole(roleName)){
			//角色名已存在
			res.setCode("201");
			res.setMessage("角色名称已存在");
			return res;
		}
		RoleEntity role = new RoleEntity();
		role.setRoleName(roleName);
		role.setIsUse(0);
		role.setCreateTime(new Date());
		role = roleService.saveRole(role);
		if(role != null){
			res.setCode("200");
			res.setMessage("保存成功");
		}else{
			res.setCode("202");
			res.setMessage("保存失败");
		}
		return res;
	}
	//删除角色
	public CssBaseRes<RoleEntity> delRole(int roleId){
		CssBaseRes<RoleEntity> res = new CssBaseRes<RoleEntity>();
		List<UserRoleEntity> lstUserRole = userRoleService.getUserRoleByRoleId(roleId);
		if(lstUserRole !=null && lstUserRole.size()>0){
			//该角色下有用户
			res.setCode("201");
			res.setMessage("该角色下尚有用户，不可删除");
			return res;
		}
		if(!roleService.closeRole(roleId)){
			res.setCode("202");
			res.setMessage("删除失败");
			return res;
		}
		res.setCode("200");
		res.setMessage("删除成功");
		return res;
	}
	//获取角色列表
	public List<RoleEntity> getRolesByRoleIds(List<Integer> roleIds){
		if(roleIds == null || roleIds.size()<1){
			return null;
		}
		return roleService.getRolesByIds(roleIds);
	}
	//获取角色权限
	public RoleActionRes getRoleActionsByRoleIdAndRootMenuId(int roleId,int rootMenuId){
		RoleActionRes roleActions = new RoleActionRes();
		
		return roleActions;
	}
	
	//获取用户没有的角色
	public PageListEntity<RoleEntity> getOtherRoles(int userId){
		PageListEntity<RoleEntity> res = new PageListEntity<RoleEntity>();
		//获取已有的角色id
		List<UserRoleEntity> lstUserRole = userRoleService.getUserRoleByUserId(userId);
		List<Integer> lstRoleIds = new ArrayList<Integer>();
		for(UserRoleEntity userRole:lstUserRole){
			lstRoleIds.add(userRole.getRoleId());
		}
		
		return res;
	}
}
