package com.lagou.sqlsession;


import com.lagou.config.BoundeSql;
import com.lagou.entity.Configuration;
import com.lagou.entity.MappedStatement;
import com.lagou.utils.GenericTokenParser;
import com.lagou.utils.ParameterMapping;
import com.lagou.utils.ParameterMappingTokenHandler;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-26 01:17
 * @description //
 * @since V1.0.0
 */
public class SimpleExecutor implements Executor {

	@Override
	public <E> List query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException {

		//1 注册驱动，获取连接
		Connection connection = configuration.getDataSource().getConnection();


		//2 获取sql
		// select * from user where id = #{id} and username = {username}
		String sql = mappedStatement.getSql();
		BoundeSql boundeSql = getBounDeSql(sql);

		//3 获取预处理对象 prepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(boundeSql.getSqlText());

		//4 设置参数
		// 获取参数的全限定名称
		String paramType = mappedStatement.getParamType();

		//参数的集合 id、username
		List<ParameterMapping> parameterMappingList = boundeSql.getParameterMappingList();
		for (int i = 0; i < parameterMappingList.size(); i++) {

			ParameterMapping parameterMapping = parameterMappingList.get(i);
			// #{}里面解析出来的名称的集合 id,username
			String content = parameterMapping.getContent();

			try {
				// 利用反射
				Class<?> clazz = getClassType(paramType);

				Field declaredField = clazz.getDeclaredField(content);
				declaredField.setAccessible(true);
				Object o = declaredField.get(params[0]);

				preparedStatement.setObject(i + 1, o);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		//5 执行sql
		ResultSet resultSet = preparedStatement.executeQuery();
		//6 封装结果集
		String resultType = mappedStatement.getResultType();
		Class<?> classType = getClassType(resultType);
		List<Object> resultList = new ArrayList<Object>();
		while (resultSet.next()) {
			Object resultObj = null;
			if (classType.equals(Integer.class) || int.class.equals(classType)) {
				resultObj = resultSet.getInt(1);
			} else {
				try {
					resultObj = classType.newInstance();
					//获取元数据，包含字段名称
					ResultSetMetaData metaData = resultSet.getMetaData();
					for (int i = 1; i <= metaData.getColumnCount(); i++) {
						//表的字段
						String columnName = metaData.getColumnName(i);
						//下标从1开始，
						Object value = resultSet.getObject(columnName);

						//使用反射和内省，根据实体类和表的对应关系，完成封装
						PropertyDescriptor propertyDescriptor = new PropertyDescriptor(columnName, classType);
						Method writeMethod = propertyDescriptor.getWriteMethod();
						writeMethod.invoke(resultObj, value);
					}
				} catch (InstantiationException | InvocationTargetException | IntrospectionException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
			resultList.add(resultObj);
		}

		return resultList;
	}

	@Override
	public int update(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException {
		//1 注册驱动，获取连接
		Connection connection = configuration.getDataSource().getConnection();
		//2 获取sql
		// insert into user values(#{id},#{name})
		String sql = mappedStatement.getSql();
		BoundeSql boundeSql = getBounDeSql(sql);

		//3 获取预处理对象 prepareStatement
		PreparedStatement preparedStatement = connection.prepareStatement(boundeSql.getSqlText());

		//4 设置参数
		// 获取参数的全限定名称
		String paramType = mappedStatement.getParamType();

		//参数的集合 id、username
		List<ParameterMapping> parameterMappingList = boundeSql.getParameterMappingList();
		for (int i = 0; i < parameterMappingList.size(); i++) {

			ParameterMapping parameterMapping = parameterMappingList.get(i);
			// #{}里面解析出来的名称的集合 id,username
			String content = parameterMapping.getContent();

			try {
				// 利用反射
				Class<?> clazz = getClassType(paramType);

				Field declaredField = clazz.getDeclaredField(content);
				declaredField.setAccessible(true);
				Object o = declaredField.get(params[0]);

				preparedStatement.setObject(i + 1, o);
			} catch (NoSuchFieldException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}

		//5 执行sql
		return preparedStatement.executeUpdate();
	}

	private Class<?> getClassType(String paramType) {
		Class<?> aClass = null;
		if (paramType != null && !"".equals(paramType.trim())) {
			try {
				aClass = Class.forName(paramType);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return aClass;
	}

	/**
	 * 对sql解析。#{} ，1 #{} 使用 ? 代替
	 *
	 * @param sql select * from user where id = #{id} and username = {username}
	 * @return
	 */
	private BoundeSql getBounDeSql(String sql) {
		//标记处理类：配置标记处理类来完成对占位符的解析
		ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
		GenericTokenParser genericTokenParser = new GenericTokenParser("#{", "}", tokenHandler);

		//解析出来的sql #{id} --> ?
		String sqlText = genericTokenParser.parse(sql);
		//#{}里面解析出来的名称的集合 id,username
		List<ParameterMapping> parameterMappings = tokenHandler.getParameterMappings();

		BoundeSql boundeSql = new BoundeSql(sqlText, parameterMappings);

		return boundeSql;


	}
}
