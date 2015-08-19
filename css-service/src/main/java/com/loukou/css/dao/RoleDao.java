package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.RoleEntity;

public interface RoleDao extends CrudRepository<RoleEntity, Integer>,JpaSpecificationExecutor<RoleEntity>  {
List<RoleEntity> findByIsUseOrderByCreateTimeDesc(int isUse);
RoleEntity findByRoleName(String roleName);

@Query("update RoleEntity set isUse=?2 where roleId=?1")
int closeRoleByRoleId(int roleId,int isUse);

List<RoleEntity> findByRoleIdInAndIsUse(List<Integer> roleIds,int isUse);

Page<RoleEntity> findByRoleIdNotInAndIsUse(List<Integer> roleIds,int isUse,Pageable pageRequest);
}
