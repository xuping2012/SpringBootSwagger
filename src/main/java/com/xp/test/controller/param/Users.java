package com.xp.test.controller.param;

/**
 * 一些http接口请求的参数封装
 * 根据接口名称来设置成员变量
 * @author qguan
 *
 */

public class Users {
	 
	private String name;
	private String job;
	
	public Users() {
		super();
	}
 
	public Users(String name, String job) {
		super();
		this.name = name;
		this.job = job;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getJob() {
		return job;
	}
 
	public void setJob(String job) {
		this.job = job;
	}
	
}
