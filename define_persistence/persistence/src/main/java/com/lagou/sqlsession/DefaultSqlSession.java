package com.lagou.sqlsession;

import com.lagou.entity.Configuration;

import java.lang.reflect.*;
import java.sql.SQLException;
import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-26 01:03
 * @description //
 * @since V1.0.0
 */
public class DefaultSqlSession implements SqlSession {

	private Configuration configuration;

	public DefaultSqlSession(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public <E> List<E> selectList(String statementId, Object... params) {

		//调用SimpleExecutor中query()
		SimpleExecutor simpleExecutor = new SimpleExecutor();
		List list = null;
		try {
			list = simpleExecutor.query(configuration, configuration.getMappedStatementMap().get(statementId), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	@Override
	public <E> E selectOne(String statementId, Object... params) {

		List<Object> objects = null;
		try {
			objects = selectList(statementId, params);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (objects != null && objects.size() == 1) {
			return (E) objects.get(0);
		} else {
			if (objects != null && objects.size() == 0) {
				return null;
			}
			throw new RuntimeException("数据不止一个");
		}

	}

	@Override
	public int add(String statementId, Object... params) {
		int i = 0;
		SimpleExecutor simpleExecutor = new SimpleExecutor();
		try {
			i = simpleExecutor.update(configuration, configuration.getMappedStatementMap().get(statementId), params);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	@Override
	public int update(String statementId, Object... params) {
		int i;
		SimpleExecutor simpleExecutor = new SimpleExecutor();
		try {
			i = simpleExecutor.update(configuration, configuration.getMappedStatementMap().get(statementId), params);
		} catch (SQLException e) {
			throw new RuntimeException("更新失败");
		}
		return i;
	}

	@Override
	public int delete(String statementId, Object... params) {
		int i;
		SimpleExecutor simpleExecutor = new SimpleExecutor();
		try {
			i = simpleExecutor.update(configuration, configuration.getMappedStatementMap().get(statementId), params);
		} catch (SQLException e) {
			throw new RuntimeException("删除失败");
		}
		return i;
	}

	/**
	 * 为dao接口生成代理对象，并返回
	 */
	@Override
	public <T> T getMapper(Class<?> classType) {
		Object proxyInstance = Proxy.newProxyInstance(classType.getClassLoader(), new Class[]{classType}, new InvocationHandler() {
			/**
			 * 代理对象调用接口中任意方法都会调用invoke方法
			 * @param proxy 当前代理对象
			 * @param method 当前被调用的方法的对象
			 * @param args 当前被调用方法的参数
			 * @return proxyInstance
			 * @throws Throwable
			 */
			@Override
			public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
				// 根据不同的调用方法，准备参数（statementId、参数、返回参数的类型）

				// 怎么才能做到namespace 和 id 唯一？ （namespace:用接口的权限订名称，id:用户接口中的方法名称，但是同一个接口中不能出现重载方法）
				// statementId
				String className = method.getDeclaringClass().getName();
				String methodName = method.getName();
				String statementId = className + "." + methodName;

				//参数params:args
				String toLowerCaseMethodName = methodName.toLowerCase();
				if (toLowerCaseMethodName.contains("add") ||
						toLowerCaseMethodName.contains("insert")) {
					return add(statementId, args);
				}
				if (toLowerCaseMethodName.contains("update")) {
					return update(statementId, args);
				}
				if (toLowerCaseMethodName.contains("delete")) {
					return delete(statementId, args);
				}
				if (toLowerCaseMethodName.contains("find") ||
						toLowerCaseMethodName.contains("select") ||
						toLowerCaseMethodName.contains("get") ||
						toLowerCaseMethodName.contains("query")) {
					// 返回类型，是集合还是一个
					Type returnType = method.getGenericReturnType();
					// 判断是否实现了 泛型类参数化 <?>
					if (returnType instanceof ParameterizedType) {
						return selectList(statementId, args);
					} else {
						return selectOne(statementId, args);
					}
				}
				throw new RuntimeException("id定义错误");
			}
		});
		return (T) proxyInstance;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
