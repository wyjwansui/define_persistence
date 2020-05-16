package com.lagou.entity;

/**
 * @author 程  林
 * @date 2020-03-25 23:58
 * @description //
 * @since V1.0.0
 */
public class MappedStatement {

	private String id;
	private String resultType;
	private String paramType;
	private String sql;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getResultType() {
		return resultType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
}
