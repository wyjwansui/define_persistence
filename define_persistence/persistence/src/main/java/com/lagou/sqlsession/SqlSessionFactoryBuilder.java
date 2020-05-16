package com.lagou.sqlsession;

import com.lagou.config.XMLConfigBuilder;
import com.lagou.entity.Configuration;

import java.io.InputStream;

/**
 * @author 程  林
 * @date 2020-03-26 00:07
 * @description // 解析配置文件和创建sqlSessionFactory
 * @since V1.0.0
 */
public class SqlSessionFactoryBuilder {

	public SqlSessionFactory build(InputStream in) {

		//解析配置文件，封装
		XMLConfigBuilder xmlConfigBuilder = new XMLConfigBuilder();
		Configuration configuration = xmlConfigBuilder.paresConfigurationXml(in);


		//创建sqlSessionFactory对象,工厂类，生成sqlSession:会话对象
		SqlSessionFactory sqlSessionFactory = new DefaultSqlSessionFactory(configuration);

		return sqlSessionFactory;

	}
}
