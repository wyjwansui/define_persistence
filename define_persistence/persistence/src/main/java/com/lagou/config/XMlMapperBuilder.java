package com.lagou.config;

import com.lagou.entity.Configuration;
import com.lagou.entity.MappedStatement;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-26 00:44
 * @description //
 * @since V1.0.0
 */
public class XMlMapperBuilder {

	private Configuration configuration;

	public XMlMapperBuilder(Configuration configuration) {
		this.configuration = configuration;
	}

	public void paresXMl(InputStream inputStream) {

		try {
			Document document = new SAXReader().read(inputStream);

			Element rootElement = document.getRootElement();
			String namespace = rootElement.attributeValue("namespace");

			List selectSqls = rootElement.elements("select");
			for (Object o : selectSqls) {
				parseMapperLable(namespace, (Element) o);

			}

			List insertSqls = rootElement.elements("insert");
			for (Object o : insertSqls) {
				parseMapperLable(namespace, (Element) o);
			}
			List updateSqls = rootElement.elements("update");
			for (Object o : updateSqls) {
				parseMapperLable(namespace, (Element) o);
			}

			List deleteSqls = rootElement.elements("delete");
			for (Object o : deleteSqls) {
				parseMapperLable(namespace, (Element) o);
			}

		} catch (DocumentException e) {

		}
	}

	private void parseMapperLable(String namespace, Element o) {
		Element selectSql = o;
		String id = selectSql.attributeValue("id");
		String resultType = selectSql.attributeValue("resultType");
		String paramType = selectSql.attributeValue("paramType");
		String sqlText = selectSql.getTextTrim();

		MappedStatement mappedStatement = new MappedStatement();
		mappedStatement.setId(id);
		mappedStatement.setParamType(paramType);
		mappedStatement.setResultType(resultType);
		mappedStatement.setSql(sqlText);

		configuration.getMappedStatementMap().put(namespace + "." + id, mappedStatement);
	}

	public Configuration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(Configuration configuration) {
		this.configuration = configuration;
	}
}
