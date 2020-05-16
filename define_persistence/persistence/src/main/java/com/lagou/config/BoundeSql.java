package com.lagou.config;

import com.lagou.utils.ParameterMapping;

import java.util.List;

/**
 * @author 程  林
 * @date 2020-03-26 01:50
 * @description //
 * @since V1.0.0
 */
public class BoundeSql {

	private String sqlText;

	private List<ParameterMapping> parameterMappingList;

	public BoundeSql(String sqlText, List<ParameterMapping> parameterMappingList) {
		this.sqlText = sqlText;
		this.parameterMappingList = parameterMappingList;
	}

	public String getSqlText() {
		return sqlText;
	}

	public void setSqlText(String sqlText) {
		this.sqlText = sqlText;
	}

	public List<ParameterMapping> getParameterMappingList() {
		return parameterMappingList;
	}

	public void setParameterMappingList(List<ParameterMapping> parameterMappingList) {
		this.parameterMappingList = parameterMappingList;
	}
}
