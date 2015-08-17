package com.loukou.css.service.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.loukou.css.service.entity.User;

public interface UserDao extends PagingAndSortingRepository<User,Integer>{

}
