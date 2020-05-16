package com.lagou.dao;

import com.lagou.entity.User;

import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-27 21:57
 * @description //
 * @since V1.0.0
 */
public interface UserMapper {

	List<User> findAll();

	User findOne(User user);

	int add(User user);

	Integer findMaxId();

	int updateById(User user);

	int deleteById(User user);

}
