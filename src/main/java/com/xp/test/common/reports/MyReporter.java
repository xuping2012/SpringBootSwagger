package com.xp.test.common.reports;

import com.aventstack.extentreports.ExtentTest;

/**
 * @Auther: xup
 * @Date: 2018/6/27 17:18
 * @Description:
 */
public class MyReporter {
	public static ExtentTest report;
	public static String testName;
	
	public static String getTestName() {
		return testName;
	}
	public static void setTestName(String testName) {
		MyReporter.testName = testName;
	}
}
