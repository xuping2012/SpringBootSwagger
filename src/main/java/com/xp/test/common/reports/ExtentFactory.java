package com.xp.test.common.reports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.NetworkMode;

/**
 * 
 * TODO:创建报告类
 *
 * @author Joe-Tester
 * @time 2021年4月28日
 * @file ExtentFactory.java
 */
public class ExtentFactory {

	public static ExtentReports getInstance() {
		ExtentReports extent;
		MySystemInfo mySystemInfo;
		// 声明报告路径
		String directory = System.getProperty("user.dir") + "\\reports\\";
		// 如何路径不存在则创建
		File reportDir = new File(directory);
		if (!reportDir.exists() && !reportDir.isDirectory()) {
			reportDir.mkdir();
		}
		// 声明报告名称
		Date date = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String filename = "report_index_" + df.format(date) + ".html";

		// 创建报告初始化对象
		extent = new ExtentReports(directory + filename, false,
				NetworkMode.OFFLINE);

		// 获取配置信息
		mySystemInfo = new MySystemInfo();
		// 这部分可以写入配置文件
		extent.addSystemInfo(mySystemInfo.getSystemInfo());
		return extent;
	}
}
