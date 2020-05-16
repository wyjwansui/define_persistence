package com.lagou.sqlsession;

import com.lagou.entity.Configuration;
import com.lagou.entity.MappedStatement;

import java.sql.SQLException;
import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-26 01:15
 * @description //
 * @since V1.0.0
 */
public interface Executor {

	public <E> List query(Configuration configuration, MappedStatement mappedStatement, Object... params) throws SQLException;

	int update(Configuration configuration, MappedStatement statement, Object... params) throws SQLException;
}
