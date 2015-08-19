package com.loukou.css.web;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.loukou.css.processor.LoginProcessor;
import com.loukou.css.bo.LoginUser;
import com.loukou.css.contains.PageUrls;
import com.loukou.css.entity.UserEntity;
import com.loukou.css.entity.UserRoleEntity;
import com.loukou.css.processor.RoleProcessor;
import com.loukou.css.processor.UserRoleProcessor;
import com.loukou.css.service.redis.entity.SessionEntity;

@Controller
@RequestMapping("/login")
public class LoginController extends BaseController {

	@Autowired
	private LoginProcessor loginProcessor;
//	@Autowired
//	private UserWareHouseProcessor userWarehouseProcessor;
//	@Autowired
//	private WareHouseProcessor warehouseProcessor;
	@Autowired
	private UserRoleProcessor userRoleProcessor;
	@Autowired
	private RoleProcessor roleProcessor;
	
	@RequestMapping(method = RequestMethod.GET)
	public String Login() {
        return PageUrls.LOGIN_URL;
	}
	
	@RequestMapping(value="/checklogin",method = RequestMethod.POST)
	@ResponseBody
	public LoginUser CheckLogin(String userCode, String userPwd,ModelMap modelMap) {
		LoginUser loginUser=new LoginUser();
		loginUser = loginProcessor.Login(userCode, userPwd);
		UserEntity userEntity=loginUser.getUser();
		if(userEntity!=null)
		{
			SessionEntity SessionEntity=new SessionEntity();
			SessionEntity.setSessionId(getSessionId());
			SessionEntity.setUserId(userEntity.getUserId());
			SessionEntity.setUserName(userEntity.getUserName());
			SessionEntity.setIsRoot(userEntity.getIsRoot());

			//取出用户角色
			List<UserRoleEntity> lstUserRole = userRoleProcessor.getUserRoleByUserId(userEntity.getUserId());
			List<Integer> roleIds = new ArrayList<Integer>();
			for(UserRoleEntity userRole :lstUserRole){
				roleIds.add(userRole.getRoleId());
			}
			if(roleIds.size()>1){
				SessionEntity.setLstRole(roleProcessor.getRolesByRoleIds(roleIds));
			}
			//whSessionEntity.setWarehouseId(warehouseId);
			sessionRedisService.save(SessionEntity);
			modelMap.put("loginUser", loginUser);
	    }
		return loginUser;
	}
	
	@RequestMapping(value="/logout",method = RequestMethod.GET)
	@ResponseBody
	public String logout() {
		//登出操作
		sessionRedisService.deleteWhSessionEntity(getSessionId());
		return "1";
	}
}
	
