package com.lagou.sqlsession;

import com.lagou.entity.Configuration;

/**
 * @author 程  林
 * @date 2020-03-26 00:58
 * @description //默认的SqlSession工厂
 * @since V1.0.0
 */
public class DefaultSqlSessionFactory implements SqlSessionFactory {

	private Configuration configuration;

	public DefaultSqlSessionFactory(Configuration configuration) {
		this.configuration = configuration;
	}

	@Override
	public SqlSession openSession() {
		return new DefaultSqlSession(configuration);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
