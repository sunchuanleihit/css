package com.loukou.css.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.loukou.css.entity.MenuEntity;

public interface MenuDao extends CrudRepository<MenuEntity, Integer> {
List<MenuEntity> findByMenuIdInAndIsUseOrderByShowIndexDesc(List<Integer> menuIds,int isUse);
List<MenuEntity> findByParentIdAndIsUseOrderByShowIndexDesc(int parentId,int isUse);
List<MenuEntity> findByIsUseOrderByShowIndexDesc(int isUse);
}
