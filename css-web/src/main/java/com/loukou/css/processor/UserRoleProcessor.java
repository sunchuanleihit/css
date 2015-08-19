package com.loukou.css.processor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.loukou.css.bo.PageListEntity;
import com.loukou.css.bo.PurchaseListBo;
import com.loukou.css.entity.UserRoleEntity;
import com.loukou.css.service.UserRoleService;

@Service
public class UserRoleProcessor {
@Autowired
private UserRoleService userRoleService;
//根据userId获取人员角色关系
public List<UserRoleEntity> getUserRoleByUserId(int userId){
	return userRoleService.getUserRoleByUserId(userId);
}

}
