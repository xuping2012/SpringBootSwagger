package com.xp.test.common.reports;

import com.vimalselvam.testng.SystemInfo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @Auther: xup
 * @Date: 2018/6/7 10:54
 * @Description:
 */
public class MySystemInfo implements SystemInfo {

	@Override
	public Map<String, String> getSystemInfo() {
		Properties properties = new Properties();
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(System.getProperty("user.dir")
					+ "/src/main/resources/env.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Map<String, String> systemInfo = new HashMap<>();
		try {
			properties.load(inputStream);
			systemInfo.put("Environment:",
					properties.getProperty("Environment"));
			systemInfo.put("Host:", properties.getProperty("Host"));
			systemInfo.put("责任人:", properties.getProperty("Tester"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return systemInfo;
	}
}
