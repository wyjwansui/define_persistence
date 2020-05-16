package com.lagou.config;

import com.lagou.entity.Configuration;
import com.lagou.io.Resources;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * @author 程  林
 * @date 2020-03-26 00:11
 * @description //
 * @since V1.0.0
 */
public class XMLConfigBuilder {

	private Configuration configuration;

	public XMLConfigBuilder() {
		this.configuration = new Configuration();
	}


	public Configuration paresConfigurationXml(InputStream inputStream) {

		try {
			Document document = new SAXReader().read(inputStream);

			Element rootElement = document.getRootElement();

			Element dataSource = rootElement.element("dataSource");
			List propertys = dataSource.elements("property");
			Properties properties = new Properties();
			for (Object o : propertys) {
				Element property = (Element) o;
				String name = property.attributeValue("name");
				String value = property.attributeValue("value");
				properties.setProperty(name, value);
			}

			//创建连接池对象
			ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
			try {
				comboPooledDataSource.setDriverClass(properties.getProperty("driverClass"));
				comboPooledDataSource.setJdbcUrl(properties.getProperty("jdbcUrl"));
				comboPooledDataSource.setUser(properties.getProperty("username"));
				comboPooledDataSource.setPassword(properties.getProperty("password"));
			} catch (PropertyVetoException e) {
				e.printStackTrace();
			}


			//mapper解析, 拿到mapper路径，解析
			List mappers = rootElement.elements("mapper");
			for (Object o : mappers) {
				Element mapper = (Element) o;
				String resource = mapper.attributeValue("resource");
				InputStream resourceAsStream = Resources.getResourceAsStream(resource);
				XMlMapperBuilder xMlMapperBuilder = new XMlMapperBuilder(configuration);
				xMlMapperBuilder.paresXMl(resourceAsStream);
			}

			configuration.setDataSource(comboPooledDataSource);
		} catch (DocumentException e) {

		}

		return configuration;
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
