package com.xp.test.common.param;

/**
 * 一些http接口请求的参数封装 根据接口名称来设置成员变量 其他接口，可以仿照如此设计
 * 
 * @author qguan
 *
 */

public class Users {

	public String name;
	public String job;

	public Users() {
		super();
	}

	public Users(String name, String job) {
		super();
		this.name = name;
		this.job = job;
	}

}
