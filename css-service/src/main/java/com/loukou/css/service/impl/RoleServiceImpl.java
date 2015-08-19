package com.loukou.css.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaBuilder.In;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.loukou.css.bo.BaseRes;
import com.loukou.css.bo.PageListEntity;
import com.loukou.css.dao.RoleDao;
import com.loukou.css.entity.PickingOrderEntity;
import com.loukou.css.entity.RoleEntity;
import com.loukou.css.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;
	
	@Override
	public RoleEntity saveRole(RoleEntity entity) {
		// TODO Auto-generated method stub
		return roleDao.save(entity);
	}

	@Override
	public List<RoleEntity> getAll() {
		// TODO Auto-generated method stub
		return roleDao.findByIsUseOrderByCreateTimeDesc(0);
	}

	@Override
	public boolean exitsRole(String roleName) {
		// TODO Auto-generated method stub
		RoleEntity role = roleDao.findByRoleName(roleName);
		if(role != null){
			return true;
		}
		return false;
	}

	@Override
	public boolean closeRole(int roleId) {
		// TODO Auto-generated method stub
		if(roleDao.closeRoleByRoleId(roleId, 1)>0)
        return true;
		return false;
	}

	@Override
	public List<RoleEntity> getRolesByIds(List<Integer> roleIds) {
		// TODO Auto-generated method stub
		return roleDao.findByRoleIdInAndIsUse(roleIds, 0);
	}

	@Override
	public Page<RoleEntity> getRolesNotInRoleIds(final List<Integer> roleIds,final int pageIndex,final int pageSize) {
		// TODO Auto-generated method stub
		Page<RoleEntity> pageRole =  roleDao.findAll(
		    new Specification<RoleEntity>() {
			@Override
			public Predicate toPredicate(Root<RoleEntity> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicate = new ArrayList<Predicate>();
				Path<Integer> roleIdPath = root.get("roleId");
				if(roleIds!=null && roleIds.size()>1)
				{
					Iterator iterator = roleIds.iterator();
			        In in = cb.in(roleIdPath);
			        while (iterator.hasNext()) {
			            in.value(iterator.next());
			        }
					predicate.add(in);
				}
				
				Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
			}
		}, new PageRequest(pageIndex-1, pageSize, Sort.Direction.DESC, "createTime"));
		return null;
	}

}
