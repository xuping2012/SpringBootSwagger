package com.xp.test.http.ddt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;

import com.xp.test.base.client.RestClient;
import com.xp.test.common.utils.HandleExcelUtils;

/**
 * excel作为数据源驱动
 * 
 * @author qguan
 *
 */
public class DataProviderByExcel {

	final static Logger Log = Logger.getLogger(DataProviderByExcel.class);

	static String sourceFile = System.getProperty("user.dir")
			+ "/src/test/resources/test.xls";

	static HandleExcelUtils handleExcelUtils;

	static RestClient restClient;

	static Map<String, String> headerMap;

	@BeforeClass
	void bf() {
		restClient = new RestClient();
		headerMap = new HashMap<String, String>();
		headerMap.put("content-type", "application/json");
	}

	@DataProvider(name = "testData")
	static Object[][] dataProvider() throws IOException {
		System.out.println(sourceFile);
		return HandleExcelUtils.getDataByExcel(sourceFile);

	}

	/**
	 * 参数化测试用例
	 * 
	 * @param searchWord1
	 * @param searchWord2
	 * @param searchResult
	 */
	@Test(dataProvider = "testData", enabled = true)
	void testDataDrivenByExcelFile(String caseId, String title, String url,
			String data, String expected) {

		String Host = "https://reqres.in" + url;
		// String
		// param="{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";
		CloseableHttpResponse res = restClient.sendPost(Host, data, headerMap);
		int statusCode = res.getStatusLine().getStatusCode();
		// String类型转int类型
		Assert.assertEquals(statusCode, Integer.parseInt(expected), "断言失败");

	}

}