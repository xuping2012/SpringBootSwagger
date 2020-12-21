package com.xp.test.http.ddt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.xp.test.base.client.RestClient;
import com.xp.test.common.utils.HandleCsvUtils;

/**
 * 
 * csv数据源
 * 
 * @author qguan
 *
 */
public class DataProviderByCSV {

	static String destFile = "D:\\javaworkspace\\springbootSwagger\\src\\test\\resources\\test.csv";

	static HandleCsvUtils handleCsvUtils;

	static RestClient restClient;

	static Map<String, String> headerMap;

	@BeforeClass
	void bf() {
		restClient = new RestClient();
		headerMap = new HashMap<String, String>();
		headerMap.put("content-type", "application/json");
	}

	/**
	 * 通過傳入文件路徑參數，獲取文件數據
	 * 
	 * @param fileName
	 * @return
	 * @throws IOException
	 */
	@DataProvider(name = "destData")
	static Object[][] getTestData() throws IOException {
		return HandleCsvUtils.getDatasByCSV(destFile);
	}

	/**
	 * 如果用例有多少列就需要傳入多少參數
	 * 
	 * @param searchWord1
	 * @param searchWord2
	 * @param searchResult
	 */
	@Test(dataProvider = "destData", enabled = false)
	void testDataDrivenByCSVFile(String caseId, String title, String url,
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