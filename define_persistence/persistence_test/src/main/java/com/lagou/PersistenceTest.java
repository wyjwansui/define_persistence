package com.lagou;

import com.lagou.dao.IUserDao;
import com.lagou.dao.UserDaoIpml;
import com.lagou.dao.UserMapper;
import com.lagou.entity.User;
import com.lagou.io.Resources;
import com.lagou.sqlsession.SqlSession;
import com.lagou.sqlsession.SqlSessionFactory;
import com.lagou.sqlsession.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.InputStream;
import java.util.Collections;
import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-25 23:55
 * @description //
 * @since V1.0.0
 */
public class PersistenceTest {

	@Test
	public void test() {

		InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

		User u = new User();
		u.setId(1);
		u.setUsername("lucy");
		User user = sqlSession.selectOne("user.selectOne", u);
		System.out.println(user);

		List<User> users = sqlSession.selectList("user.selectList");
		System.out.println(Collections.singleton(users));

	}

	/**
	 * dao
	 */
	@Test
	public void testDao() {
		IUserDao userDao = new UserDaoIpml();

		User u = new User();
		u.setId(1);
		u.setUsername("lucy");
		User user = userDao.findOne(u);
		System.out.println(user);

		List<User> users = userDao.queryList();
		System.out.println(Collections.singleton(users));

	}

	/**
	 * getMapper
	 */
	@Test
	public void testMapper() {
		InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		User u = new User();
		u.setId(1);
		u.setUsername("lucy");
		User user = mapper.findOne(u);
		System.out.println(user);

		List<User> users = mapper.findAll();
		System.out.println(Collections.singleton(users));

	}

	@Test
	public void testAddMapper() {
		InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		User u = new User();
		u.setId(mapper.findMaxId() + 1);
		u.setUsername("刘亦菲");
		u.setPassword("124568");
		u.setBirthday("2020-09-08");
		int i = mapper.add(u);
		System.out.println(i);
	}

	@Test
	public void testUpdateMapper() {
		InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		User u = new User();
		u.setId(5);
		u.setUsername("刘亦菲");

		User user = mapper.findOne(u);
		System.out.println(user);

		if (null != user) {
			user.setBirthday("1987-2-19");
			int i = mapper.updateById(user);
			System.out.println(i);
		}

	}

	@Test
	public void testDeleteMapper() {
		InputStream resourceAsStream = Resources.getResourceAsStream("sqlMapConfig.xml");
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(resourceAsStream);
		SqlSession sqlSession = sqlSessionFactory.openSession();

		UserMapper mapper = sqlSession.getMapper(UserMapper.class);

		User u = new User();
		u.setId(6);

		int i = mapper.deleteById(u);
		System.out.println(i);

	}

}
