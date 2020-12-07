package com.xp.test.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.TestNG;

public class ReRunCases {
	
	final static Logger Log=Logger.getLogger(ReRunCases.class);
	
	public void runCase(){
	    TestNG testNG = new TestNG();
	    List<String> suites = new ArrayList<String>();
	    Log.info("在項目根目錄加載測試用例集");
	    suites.add(".\\testng.xml");
	    testNG.setTestSuites(suites);
	    testNG.run();
	    Log.info("所有測試用例執行完成");

	}
	
	public void reRunCases() throws InterruptedException{

    // 可以在@Test测试注解之后，@AfterClass跑失败的用例
    TestNG testNG = new TestNG();
    List<String> suites = new ArrayList<String>();
    Thread.sleep(1000);
    Log.info("在項目測試用例集執行完成之後，加載失敗的測試用例");
    suites.add(".\\test-output\\testng-failed.xml");
    testNG.setTestSuites(suites);
    testNG.run();
    Log.info("失敗的用例執行完成");
	}

	public static void main(String args[]){
		ReRunCases re=new ReRunCases();
		try {
			re.reRunCases();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
