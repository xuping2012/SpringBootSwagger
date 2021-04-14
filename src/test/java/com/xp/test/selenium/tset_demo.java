package com.xp.test.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class tset_demo {

	public static void main(String[] args) throws InterruptedException {
		// 测试创建不同浏览器驱动方式
		String browserType = "firefox";
		WebDriver driver = null;

		if (browserType.equals("chrome")) {
			// chrome谷歌浏览器
			System.out.println("++++++我是Chrome浏览器!!!");
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir")
							+ "/src/test/resources/chromedriver.exe");
			driver = new ChromeDriver();

		} else if (browserType.equals("firefox")) {
			// 火狐浏览器
			System.out.println("++++++我是FireFox浏览器!!!");

			// DesiredCapabilities ffcapability = DesiredCapabilities.firefox();
			// ffcapability.setCapability("firefox_binary",
			// "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");

			System.setProperty("webdriver.gecko.driver",
					System.getProperty("user.dir")
							+ "/src/test/resources/geckodriver64.exe");

			driver = new FirefoxDriver();

		} else if (browserType.equals("ie")) {
			// ie 浏览器
			System.out.println("++++++我是IE浏览器!!!");

			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir")
							+ "/src/test/resources/IEDriverServer.exe");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			// ie比较特别，需要忽略它的安全等级设置
			capabilities
					.setCapability(
							InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
							true);
			capabilities.setCapability(
					InternetExplorerDriver.IGNORE_ZOOM_SETTING, true);
			driver = new InternetExplorerDriver(capabilities);

		}
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

		// 打开网址
		driver.get("https://www.baidu.com");

		System.out.println("++++++++++打开网址：" + driver.getCurrentUrl());
		Thread.sleep(2);
		System.out.println("++++++++等待2秒，然后关闭浏览器!");

		driver.quit();

	}
}
