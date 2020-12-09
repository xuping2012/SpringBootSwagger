package com.xp.test.common.config;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * 封装常用的状态码 构造读取配置文件方法
 * 
 * @author qguan
 *
 */
public class BasicConfig {

	public static Properties prop;
	// 这些常量数据，可以提取出去
	public int RESPNSE_STATUS_CODE_200 = 200;
	public int RESPNSE_STATUS_CODE_201 = 201;
	public int RESPNSE_STATUS_CODE_404 = 404;
	public int RESPNSE_STATUS_CODE_500 = 500;

	// 构造函数,读取配置文件
	public BasicConfig() {

		try {
			prop = new Properties();
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir")
							+ "/src/test/resources/config.properties");

			prop.load(fis);
			// 使用Exception 类集合，去掉了IOException、FileNotFoundException异常
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getValueByKey(String key) {

		prop = new Properties();
		try {
			FileInputStream fis = new FileInputStream(
					System.getProperty("user.dir")
							+ "/src/test/resources/config.properties");
			prop.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return prop.getProperty(key);
	}

}