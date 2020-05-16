package com.lagou.sqlsession;

/**
 * @author 程  林
 * @date 2020-03-26 00:10
 * @description //
 * @since V1.0.0
 */
public interface SqlSessionFactory {

	SqlSession openSession();
}
