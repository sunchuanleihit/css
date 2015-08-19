package com.loukou.css.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.loukou.css.bo.BaseRes;
import com.loukou.css.bo.LoginUser;
import com.loukou.css.bo.PageListEntity;
import com.loukou.css.entity.UserEntity;

public interface UserService {
   /**
    * 获取所有用户
    * */
   public List<UserEntity> getAllUsers();
   
   /**
    * 通过登录名获取一个用户
    * */
   public UserEntity findByUserCode(String userCode);
   
   /**
    * 更新用户
    * */
   public int updateUser(UserEntity user);
   
   /**
    * 根据用户id启用或停用用户
    * userId:用户id
    * isOpen:是否启用 true：启用  false：停用
    * */
   public int closeOrOpenUser(int userId,boolean isOpen);
   
   /**
    * 修改密码
    * userId:用户id
    * oldPwd:原密码
    * newPwd:新密码
    * */
   public int updateUserPwd(int userId,String oldPwd,String newPwd);
   /**
    * 通过主键获取用户
    * */
   public UserEntity findByUserId(int userId);
   
   /**
    * 验证用户名密码
    * */
   public LoginUser checkLoginUser(String userCode,String userPwd);
   
   /**
    * 获取用户仓库分页信息
    * userCode:登录名
    * userName:姓名
    * userType:用户状态
    * warehouseId:仓库Id
    * pageRequest:分页信息
    * Return：PageListEntity分页信息
    * */   
   public List<UserEntity> findByUserIdIn(List<Integer> ids);
   
   /**
    * 保存用户
    * */
   public UserEntity saveUser(UserEntity user);
   
   public List<UserEntity> findByUserNameLike(String userName);
}
