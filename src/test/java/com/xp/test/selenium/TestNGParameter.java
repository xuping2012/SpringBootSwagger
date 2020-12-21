package com.xp.test.selenium;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestNGParameter {

	WebDriver driver=null;
	
	@Parameters({"url","browser"})
	@BeforeClass
	void bf(String url,String browser){
		if(browser.equals("Chrome")){
			System.out.println("当前启动的是"+browser+"浏览器!!!");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"/src/test/resources/chromedriver.exe");
			driver=new ChromeDriver();
			driver.get(url);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		}
	}
	
	@Test
	void test_selenium() throws InterruptedException{
		driver.findElement(By.id("kw")).sendKeys("selenium3");
		driver.findElement(By.id("su")).click();
		Thread.sleep(3000);
	}
	
	
	@AfterClass
	void af(){
		driver.quit();
	}
	
	
}
