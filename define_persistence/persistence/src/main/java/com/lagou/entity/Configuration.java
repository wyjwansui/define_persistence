package com.lagou.entity;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 程  林
 * @date 2020-03-25 23:57
 * @description //
 * @since V1.0.0
 */
public class Configuration {

	/**
	 * 存放数据库的配置信息
	 */
	private DataSource dataSource;

	/**
	 * key: namespace + "." + id --> statementId
	 */
	private Map<String, MappedStatement> mappedStatementMap = new HashMap<String, MappedStatement>(16);

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map<String, MappedStatement> getMappedStatementMap() {
		return mappedStatementMap;
	}

	public void setMappedStatementMap(Map<String, MappedStatement> mappedStatementMap) {
		this.mappedStatementMap = mappedStatementMap;
	}
}
