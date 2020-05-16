package com.lagou.entity;

/**
 * @author 程  林
 * @date 2020-03-25 23:42
 * @description //
 * @since V1.0.0
 */
public class User {

	private Integer id;
	private String username;
	private String password;
	private String birthday;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Override
	public String toString() {
		return "com.lagou.entity.User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", password='" + password + '\'' +
				", birthday='" + birthday + '\'' +
				'}';
	}
}
