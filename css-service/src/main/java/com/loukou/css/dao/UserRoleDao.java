package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.UserRoleEntity;

public interface UserRoleDao extends CrudRepository<UserRoleEntity, Integer>{
List<UserRoleEntity> findByRoleId(int roleId);
List<UserRoleEntity> findByUserId(int userId);
}
