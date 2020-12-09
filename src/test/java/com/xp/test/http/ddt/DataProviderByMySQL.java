package com.xp.test.http.ddt;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xp.test.common.config.BasicConfig;
import com.xp.test.common.utils.HandleMySQLUtils;

/**
 * 从数据库读取数据
 * 
 * @author qguan
 *
 */
public class DataProviderByMySQL extends BasicConfig {

	/**
	 * @DataProvider：获取数据源
	 * @return
	 */
	@DataProvider(name = "testData")
	Object[][] words() {
		return HandleMySQLUtils.resultBySQL();
	}

	/**
	 * 数据源接受数据的集合
	 * 
	 * @param searchWord1
	 * @param searchWord2
	 * @param searchResult
	 */
	@Test(dataProvider = "testData")
	public void testDataDrivenByMysqlDatabase(String host, String user) {

		System.out.println("测试参数：" + host);

	}

}