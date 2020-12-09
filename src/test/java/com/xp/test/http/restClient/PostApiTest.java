package com.xp.test.http.restClient;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xp.test.base.client.RestClient;
import com.xp.test.common.config.BasicConfig;
import com.xp.test.common.param.Users;
import com.xp.test.common.utils.JSONExtractor;

/**
 * testng测试框架，接口测试用例demo
 * 
 * @author qguan
 *
 */
public class PostApiTest extends BasicConfig {

	String host;
	String url;
	RestClient restClient;
	HashMap<String, String> headermap;
	CloseableHttpResponse closeableHttpResponse;

	@BeforeClass
	public void bf() {
		// 初始化http请求
		restClient = new RestClient();
		// 构造请求地址
		host = prop.getProperty("HOST");
		url = host + "/api/users";
		// 构造请求头
		headermap = new HashMap<String, String>();
		headermap.put("Content-Type", "application/json"); // 这个在postman中可以查询到

	}

	@Test
	public void test_postApi() throws ClientProtocolException, IOException {
		// 构造请求参数json对象
		Users user = new Users("Anthony", "leader");
		// 对象转换成Json字符串
		String userJsonString = JSON.toJSONString(user);
		// 发送请求
		closeableHttpResponse = restClient.sendPost(url, userJsonString,
				headermap);

		// 验证状态码是不是200
		int statusCode = closeableHttpResponse.getStatusLine().getStatusCode();

		Assert.assertEquals(statusCode, RESPNSE_STATUS_CODE_201,
				"status code is not 201");

		// 断言响应json内容中name和job是不是期待结果
		String responseString = EntityUtils.toString(closeableHttpResponse
				.getEntity());

		JSONObject responseJson = JSON.parseObject(responseString);

		String name = JSONExtractor.getValueByJPath(responseJson, "name");

		String job = JSONExtractor.getValueByJPath(responseJson, "job");

		Assert.assertEquals(name, "Anthony", "name is not same");
		Assert.assertEquals(job, "leader", "job is not same");

	}

}
