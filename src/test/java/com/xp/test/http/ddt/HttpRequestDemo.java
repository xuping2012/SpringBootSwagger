package com.xp.test.http.ddt;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xp.test.base.client.RestClient;
import com.xp.test.common.config.BasicConfig;
import com.xp.test.common.utils.HandleParamUtils;
import com.xp.test.common.utils.JSONExtractor;

/**
 * 集成配置类，读取文件参数
 * 
 * @author qguan
 *
 */
public class HttpRequestDemo extends BasicConfig {

	private RestClient restClient;
	private Map<String, String> headerMap;
	private SoftAssert sa;

	// private BasicConfig baseconfig;

	/**
	 * 提取公共部分
	 */
	@BeforeClass
	void bf() {
		// 创建http请求对象
		restClient = new RestClient();
		// baseconfig = new BasicConfig();
		// 构造请求头
		headerMap = new HashMap<String, String>();
		headerMap.put("content-type", "application/json");
		// 软断言
		sa = new SoftAssert();

	}

	@Test
	void test_method() {
		String mobile = "13266515340";
		String passwd = "110022";
		String text = mobile + "," + passwd;
		String[] strings = HandleParamUtils.method(text);
		// System.out.println(strings);
		String content = "{\"appVersion\":\"V10.3\",\"channel\":0,\"deviceName\":\"iOS\",\"deviceType\":\"1\",\"deviceid\":\"123456\",\"loginType\":0,\"mobile\":\"#mobile#\",\"password\":\"#passwd#\",\"pushToken\":\"string\",\"systemVersion\":\"1\",\"verifyCode\":\"1\",\"zone\":\"86\"}";
		String string1 = HandleParamUtils.replaceParam(content, strings);
		System.out.println(string1);
	}

	@Test
	void test_login() throws ParseException, IOException {

		// # jsonStr标准的json字符串
		String content = "{\"email\":\"eve.holt@reqres.in\",\"password\":\"cityslicka\"}";

		// 请求地址url
		String url = prop.getProperty("HOST") + "/api/login";

		// 接收http响应结果
		CloseableHttpResponse closeableHttpResponse = restClient.sendPost(url,
				content, headerMap);

		// 接收http响应状态码
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

		sa.assertEquals(statusCode, 200, "status code is not 200");

		// 接收http请求响应体转字符串
		String responseString = EntityUtils.toString(closeableHttpResponse
				.getEntity());
		// 解析json字符串转json对象，提取参数
		JSONObject responseJson = JSON.parseObject(responseString);
		// System.out.println(responseString);
		String name = JSONExtractor.getValueByJPath(responseJson, "token");
		sa.assertEquals(name, "QpwL5tke4Pnpja7X4", "断言失败");

		sa.assertAll();
	}

}