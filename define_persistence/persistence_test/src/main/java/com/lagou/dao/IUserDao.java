package com.lagou.dao;

import com.lagou.entity.User;

import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-27 20:50
 * @description //
 * @since V1.0.0
 */
public interface IUserDao {

	List<User> queryList();

	User findOne(User user);

}
