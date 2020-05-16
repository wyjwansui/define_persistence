package com.lagou.sqlsession;

import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-26 01:02
 * @description //
 * @since V1.0.0
 */
public interface SqlSession {

	public <E> List<E> selectList(String statementId, Object... params);

	public <E> E selectOne(String statementId, Object... params);

	public int add(String statementId, Object... params);

	public int update(String statementId, Object... params);

	public int delete(String statementId, Object... params);

	public <T> T getMapper(Class<?> classType);
}
