package com.xp.test.http.ddt;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xp.test.common.config.BasicConfig;
import com.xp.test.common.utils.HandleMySQLUtils;

public class DataProviderByMySQL extends BasicConfig {

	private HandleMySQLUtils handleMySQLUtils;

	/**
	 * @DataProvider：获取数据源
	 * @return
	 */
	@DataProvider(name = "testData")
	Object[][] words() {
		return handleMySQLUtils.getTestData("mysql.user");
	}

	/**
	 * 数据源接受数据的集合
	 * 
	 * @param searchWord1
	 * @param searchWord2
	 * @param searchResult
	 */
	@Test(dataProvider = "testData")
	public void testDataDrivenByMysqlDatabase(String searchWord1,
			String searchWord2, String searchResult) {

		System.out.println("测试参数：" + searchWord1);

	}

}