package com.lagou.dao;

import com.lagou.entity.User;
import com.lagou.io.Resources;
import com.lagou.sqlsession.SqlSession;
import com.lagou.sqlsession.SqlSessionFactory;
import com.lagou.sqlsession.SqlSessionFactoryBuilder;

import java.io.InputStream;
import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-27 20:51
 * @description //
 * @since V1.0.0
 */
public class UserDaoIpml implements IUserDao {

	public List<User> queryList() {

		InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

		SqlSession sqlSession = sqlSessionFactory.openSession();

		return sqlSession.selectList("user.selectList");
	}

	public User findOne(User user) {

		InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);

		SqlSession sqlSession = sqlSessionFactory.openSession();

		return sqlSession.selectOne("user.selectOne", user);
	}
}
